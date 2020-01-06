package com.example.travelmanager.service.auth;

import com.alibaba.fastjson.JSON;
import com.example.travelmanager.config.Constant;
import com.example.travelmanager.config.exception.AuthControllerException;
import com.example.travelmanager.config.exception.BadRequestException;
import com.example.travelmanager.config.exception.ForbiddenException;
import com.example.travelmanager.config.exception.UnauthorizedException;
import com.example.travelmanager.dao.DepartmentDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.EditUserPaylod;
import com.example.travelmanager.payload.ForgetPasswordPayload;
import com.example.travelmanager.payload.LoginPayload;
import com.example.travelmanager.payload.RegisterPayload;
import com.example.travelmanager.payload.ResetPasswordPayload;
import com.example.travelmanager.payload.SetPasswordPayload;
import com.example.travelmanager.response.TokenResponse;
import com.example.travelmanager.service.email.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmailService emailservice;

    @Override
    public int authorize(String tokenString) {
        Token token = decryptToken(tokenString);
        Date now = new Date();
        if (token == null || now.after(token.getExpire())) {
            throw new UnauthorizedException();
        }
        return token.getId();
    }

    @Override
    public int authorize(String tokenString, UserRoleEnum... userRoleEnums) {
        Token token = decryptToken(tokenString);
        Date now = new Date();
        if (token == null || now.after(token.getExpire())) {
            throw new UnauthorizedException();
        }
        UserInfo userInfo = token.getUserInfo();
        int roleId = userInfo.getRole();
        for (UserRoleEnum role: userRoleEnums) {
            if (roleId == role.getRoleId()) {
                return token.getId();
            }
        }
        throw new ForbiddenException();
    }

    @Override
    public void register(RegisterPayload registerPayload) {
        if (userDao.findByWorkId(registerPayload.getWorkId()) != null) {
            throw AuthControllerException.WorkIdExistException;
        }
        User user = new User();
        user.setName(registerPayload.getName());
        user.setEmail(registerPayload.getEmail());
        user.setTelephone(registerPayload.getTelephone());
        user.setPassword(registerPayload.getPassword());
        user.setWorkId(registerPayload.getWorkId());
        user.setRole(UserRoleEnum.Employee.getRoleId());
        user.setStatus(false);
        userDao.save(user);
    }

    @Override
    public TokenResponse getToken(LoginPayload loginPayload) {
        User user = userDao.findByWorkId(loginPayload.getWorkId());
        if (user == null || !user.validPassword(loginPayload.getPassword())) {
            throw AuthControllerException.workIdOrPasswordErrorException;
        }
        return generateTokenResponse(user);
    }

    @Override
    public TokenResponse refershToken(String tokenStr) {
        Token token = decryptToken(tokenStr);
        var userQuery = userDao.findById(token.getId());
        if (userQuery.isEmpty()) {
            throw new UnauthorizedException();
        }
        return generateTokenResponse(userQuery.get());
    }

    @Override
    public void setPassword(int uid, SetPasswordPayload setPasswordPayload){
        User user = userDao.findById(uid).get();
        if (user.validPassword(setPasswordPayload.getOldPassword())) {
            user.setPassword(setPasswordPayload.getNewPassword());
        }
        else {
            throw AuthControllerException.OldPasswordErrorException;
        }
        userDao.save(user);
    }

    @Override
    public void editUser(int uid, EditUserPaylod editUserPaylod) {
        User user = userDao.findById(uid).get();
        String email = editUserPaylod.getEmail();
        String phone = editUserPaylod.getTelephone();
        String name = editUserPaylod.getName();

        if (name != null && !(email.isEmpty() || email.isBlank())){
            user.setEmail(email);
        }
        if (phone != null && !(phone.isEmpty() || phone.isBlank())){
            user.setTelephone(phone);
        }
        if (name != null && !(name.isEmpty() || name.isBlank())){
            user.setName(name);
        }
        userDao.save(user);
    }

    @Override
    public void forgetPassword(ForgetPasswordPayload forgetPasswordPayload) {
        User user = userDao.findByWorkId(forgetPasswordPayload.getWorkId());
        if (user == null) {
            throw AuthControllerException.userNotExistErrorException;
        }
        if (!user.getEmail().equalsIgnoreCase(forgetPasswordPayload.getEmail())) {
            throw AuthControllerException.emailErrorException;
        }
        sentResetPasswordEmail(user);
    }

    @Override
    public void resetPasswoed(ResetPasswordPayload resetPasswordPayload) {
        ApiToken apiToken = decryptApiToken(resetPasswordPayload.getToken());
        System.out.println(JSON.toJSONString(apiToken));
        if (apiToken == null || !apiToken.valid()){
            throw new ForbiddenException();
        }
        var userQuery = userDao.findById(apiToken.getUserId());
        if (userQuery.isEmpty()) {
            // 理论上不可能到这里
            throw AuthControllerException.userNotExistErrorException;
        }
        User user = userQuery.get();
        user.setPassword(resetPasswordPayload.getNewPassword());
        userDao.save(user);
    }

    private void sentResetPasswordEmail(User user) {
        String url = "http://xxxx/to/path?token=" + generateApiToken(user);

        String subject = "差旅报销系统-找回密码";
        String content = user.getName() + "(工号"+ user.getWorkId() + ")," + 
            "请在2小时内访问以下链接重置密码" + url;
        String to = user.getEmail();
        emailservice.sendSimpleMail(to, subject, content);
    }

    private ApiToken decryptApiToken(String token) {
        String str = decrypt(token);
        if (str == null) {
            return null;
        }
        try {
            return JSON.parseObject(str, ApiToken.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String generateApiToken(User user) {
        ApiToken apiToken = new ApiToken();
        apiToken.setUserId(user.getId());
        Date expire = new Date(new Date().getTime() + Constant.ACCESS_TOKEN_VALIDITY_SECONDS * 1000);
        apiToken.setExpire(expire);
        return encrypt(JSON.toJSONString(apiToken));
    }

    private TokenResponse generateTokenResponse(User user) {
        if(!user.getStatus()) {
            throw AuthControllerException.notAllowedLoginErrorException;
        }
        
        UserInfo userInfo = new UserInfo(user);
        Integer departmetnId = user.getDepartmentId();
        userInfo.setDepartmentName("未知部门");

        if (departmetnId != null) {
            var query = departmentDao.findById(user.getDepartmentId());
            if (! query.isEmpty()) {
                userInfo.setDepartmentName(query.get().getName());
            }
        }

        Token token = new Token();
        token.setId(user.getId());
        token.setUserInfo(userInfo);
        
        Date expire = new Date(new Date().getTime() + Constant.ACCESS_TOKEN_VALIDITY_SECONDS * 1000);
        token.setExpire(expire);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setExpire(expire);
        tokenResponse.setToken(generateToken(token));
        tokenResponse.setUserInfo(userInfo);

        return tokenResponse;
    }

    

    private static Token decryptToken(String tokenStr) {
        String text = decrypt(tokenStr);
        if (text == null) {
            return null;
        }
        return JSON.parseObject(text, Token.class);
    }

    private static String generateToken(Token token){
        String json = JSON.toJSONString(token);
        return encrypt(json);
    }

    private static String encrypt(String text) {
        try{
            Base64.Encoder encoder = Base64.getEncoder();
            Key aesKey = new SecretKeySpec(Constant.SIGNING_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            return encoder.encodeToString(encrypted);
        }
        catch (Exception e){
            return null;
        }

    }

    private static String decrypt(String encodeText){
        try{
            Base64.Decoder decoder = Base64.getDecoder();
            Key aesKey = new SecretKeySpec(Constant.SIGNING_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return new String(cipher.doFinal(decoder.decode(encodeText)));
        }
        catch (Exception e){
            return null;
        }
    }
}
