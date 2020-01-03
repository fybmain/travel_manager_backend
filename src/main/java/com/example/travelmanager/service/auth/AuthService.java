package com.example.travelmanager.service.auth;

import com.example.travelmanager.config.WebException.AuthControllerException;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.RegisterPayload;
import com.example.travelmanager.payload.ResetPasswordPayload;

import response.TokenResponse;

public interface AuthService {

    TokenResponse generateTokenResponse(User user);

    void register(RegisterPayload registerPayload);

    int authorize(String tokenString, UserRoleEnum... userRoleEnums);

    int authorize(String tokenString);

    void resetPassword(int uid, ResetPasswordPayload resetPasswordPayload);
}
