package com.fintech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/public")
    public Map<String, Object> publicEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a public endpoint - no authentication required");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        response.put("status", "SUCCESS");
        return response;
    }
    
    @GetMapping("/protected")
    public Map<String, Object> protectedEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a protected endpoint - authentication required");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        response.put("status", "SUCCESS");
        return response;
    }
}

