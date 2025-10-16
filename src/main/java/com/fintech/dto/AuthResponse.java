package com.fintech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String username;
    private String email;
    private String role;
    private Long expiresIn;
    private String message;
    private String preferredLanguage;
    private String preferredCurrency;
    
    public AuthResponse(String token, String refreshToken, String username, String email, String role, Long expiresIn) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.email = email;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    public AuthResponse(String token, String refreshToken, String username, String email, String role, Long expiresIn,
                        String message, String preferredLanguage, String preferredCurrency) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.email = email;
        this.role = role;
        this.expiresIn = expiresIn;
        this.message = message;
        this.preferredLanguage = preferredLanguage;
        this.preferredCurrency = preferredCurrency;
    }
}
