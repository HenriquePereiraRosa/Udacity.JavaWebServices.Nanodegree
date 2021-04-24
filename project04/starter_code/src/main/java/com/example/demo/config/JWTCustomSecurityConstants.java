package com.example.demo.config;

public class JWTCustomSecurityConstants {

    public static final String SECRET = "oursecretkey";
    public static final long EXPIRATION_TIME = 60_000 * 20; // 20 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
}