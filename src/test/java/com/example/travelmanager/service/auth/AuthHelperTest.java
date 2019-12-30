package com.example.travelmanager.service.auth;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class AuthHelperTest {
    @Test
    void encryptTest() {
        System.out.println("=======encryptTest============");
        String text = "this is a test string,this is a test string";
        System.out.println("before encrypt :" + text);
        String encrypted =  AuthHelper.encrypt(text);
        System.out.println("after encrypt :" + encrypted);
        Assert.isTrue(!text.equals(encrypted), "should be encrypted");
    }

    @Test
    void decryptedTest() {
        System.out.println("=======decryptedTest============");
        String text = "this is a test string,this is a test string";
        String encrypted = AuthHelper.encrypt(text);
        System.out.println("before decrypted:"+ encrypted);
        String decrypted = AuthHelper.decrypt(encrypted);
        System.out.println("after decrypted:"+ decrypted);
        Assert.isTrue(text.equals(decrypted), "should be encrypted");
    }

    @Test
    void generateTokenTest(){
        System.out.println("========generateTokenTest=======");

        UserInfo userInfo = new UserInfo();
        userInfo.setDepartmentId(0);
        userInfo.setEmail("text@example.com");
        userInfo.setName("name");
        userInfo.setRole(0);
        userInfo.setTelephone("12345678901");

        String token = AuthHelper.generateToken(userInfo);

        Assert.isTrue(!token.isEmpty(), "token should not be null");
        System.out.println("token:" + token);
        String json = AuthHelper.decrypt(token);
        System.out.println(json);
        UserInfo userInfoFromJson = JSON.parseObject(json, UserInfo.class);
        Assert.isTrue(userInfoFromJson != null, "user info from json should not be null");
    }
}
