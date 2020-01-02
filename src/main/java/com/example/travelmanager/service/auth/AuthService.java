package com.example.travelmanager.service.auth;

import com.example.travelmanager.config.WebException.AuthControllerException;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.RegisterPayload;
import org.springframework.web.client.HttpClientErrorException;
import response.TokenResponse;

public interface AuthService {

    TokenResponse generateTokenResponse(User user);

    void register(RegisterPayload registerPayload) throws AuthControllerException;

    int authorize(String tokenString, UserRoleEnum... userRoleEnums) throws HttpClientErrorException.Unauthorized, HttpClientErrorException.Forbidden;
    int authorize(String tokenString);
}
