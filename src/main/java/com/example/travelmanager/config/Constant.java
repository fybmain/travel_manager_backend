package com.example.travelmanager.config;

public class Constant {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 2 * 60 * 60;
    public static final String SIGNING_KEY = "PBKDF2WithHmacSH";   // mut be 16,24 or 32 bytes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
