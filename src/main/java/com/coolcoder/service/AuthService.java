package com.coolcoder.service;

import com.coolcoder.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String generateToken(String username) {
        return JwtUtil.generateToken(username);
    }


    public boolean validateToken(String token) {
        return JwtUtil.validateToken(token);
    }

    public String extractUsername(String token) {
        return JwtUtil.getUsername(token);
    }
}
