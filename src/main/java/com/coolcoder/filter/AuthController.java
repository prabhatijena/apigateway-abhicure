package com.coolcoder.filter;

import com.coolcoder.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password) {

        // 1. Validate username/password
        if ("admin".equals(username) && "password".equals(password)) {
            // 2. Generate JWT (this is just a placeholder)
            String token = JwtUtil.generateToken(username);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
