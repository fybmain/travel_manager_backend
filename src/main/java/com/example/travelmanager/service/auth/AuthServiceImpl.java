package com.example.travelmanager.service.auth;

import com.alibaba.fastjson.JSON;
import com.example.travelmanager.config.Constant;
import com.example.travelmanager.config.WebException.ForbiddenException;
import com.example.travelmanager.config.WebException.UnauthorizedException;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.RegisterErrorEnum;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.RegisterPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import response.TokenResponse;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Override
    public int authorize(String tokenString, UserRoleEnum... userRoleEnums) {
        Token token = decryptToken(tokenString);
        Date now = new Date();
        if (token == null || now.after(token.getExpire())) {
            throw new UnauthorizedException();
        }
        System.out.println(JSON.toJSONString(token));
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
    public RegisterErrorEnum register(RegisterPayload registerPayload) {

        if (userDao.findByWorkId(registerPayload.getWorkId()) != null) {
            return RegisterErrorEnum.WORKIDEXIST;
        }
        User user = new User();
        user.setName(registerPayload.getName());
        user.setEmail(registerPayload.getEmail());
        user.setTelephone(registerPayload.getTelephone());
        user.setPassword(registerPayload.getPassword());
        user.setWorkId(registerPayload.getWorkId());
        user.setRole(UserRoleEnum.Employee.getRoleId());
        user.setStatus(true);
        userDao.save(user);
        return RegisterErrorEnum.SUCCESS;
    }

    @Override
    public TokenResponse generateTokenResponse(User user) {
        UserInfo userInfo = new UserInfo(user);

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
        System.out.println(text);
        if (text == null) {
            return null;
        }
        return JSON.parseObject(text, Token.class);
    }

    private static String generateToken(Token token){
        String json = JSON.toJSONString(token);
        System.out.println(json);
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
            System.err.println(e.toString());
            return null;
        }

    }

    private static String decrypt(String encodeText){
        try{
            Base64.Decoder decoder = Base64.getDecoder();
            String text = new String(decoder.decode(encodeText));
            Key aesKey = new SecretKeySpec(Constant.SIGNING_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return new String(cipher.doFinal(decoder.decode(encodeText)));
        }
        catch (Exception e){
            System.err.println(e.toString());
            return null;
        }
    }

    private static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
