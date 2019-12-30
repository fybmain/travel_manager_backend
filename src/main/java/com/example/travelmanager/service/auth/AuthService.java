package com.example.travelmanager.service.auth;

import com.example.travelmanager.payload.RegisterPayload;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity register(RegisterPayload registerPayload);
}
