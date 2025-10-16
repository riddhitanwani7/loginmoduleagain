package com.fintech.controller;

import com.fintech.dto.AuthResponse;
import com.fintech.dto.LoginRequest;
import com.fintech.dto.RegisterRequest;
import com.fintech.entity.User;
import com.fintech.i18n.LocalizationService;
import com.fintech.service.UserService;
import com.fintech.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final LocalizationService localizationService;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request,
                                      @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage,
                                      jakarta.servlet.http.HttpServletRequest httpRequest) {
        try {
            User user = userService.registerUser(request);
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

            String token = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            String lang = request.getPreferredLanguage() != null ? request.getPreferredLanguage() :
                    (acceptLanguage != null ? acceptLanguage : user.getPreferredLanguage());
            String message = localizationService.getMessage(lang, "register.success", "Registration successful");

            AuthResponse response = new AuthResponse(
                    token,
                    refreshToken,
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().name(),
                    jwtExpiration,
                    message,
                    user.getPreferredLanguage(),
                    user.getPreferredCurrency()
            );

            String ip = httpRequest.getRemoteAddr();
            log.info("User registered successfully: {} from IP {}", user.getUsername(), ip);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected registration error: ", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Registration failed. Please try again."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,
                                   @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage,
                                   jakarta.servlet.http.HttpServletRequest httpRequest) {
        try {
            User user = userService.authenticateUser(request);
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

            String token = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            String lang = request.getLanguage() != null ? request.getLanguage() :
                    (acceptLanguage != null ? acceptLanguage : user.getPreferredLanguage());
            String message = localizationService.getMessage(lang, "login.success", "Login successful");

            AuthResponse response = new AuthResponse(
                    token,
                    refreshToken,
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().name(),
                    jwtExpiration,
                    message,
                    user.getPreferredLanguage(),
                    user.getPreferredCurrency()
            );

            String ip = httpRequest.getRemoteAddr();
            log.info("User logged in successfully: {} from IP {}", user.getUsername(), ip);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("Login failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected login error: ", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Login failed. Please try again."));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid refresh token");
            }

            String refreshToken = authHeader.substring(7);

            if (!jwtUtil.isRefreshToken(refreshToken)) {
                throw new IllegalArgumentException("Invalid refresh token");
            }

            if (!jwtUtil.validateToken(refreshToken)) {
                throw new IllegalArgumentException("Refresh token expired");
            }

            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = userService.loadUserByUsername(username);
            User user = userService.findByUsername(username);

            String newToken = jwtUtil.generateToken(userDetails);
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

            String message = localizationService.getMessage(user.getPreferredLanguage(), "token.refreshed", "Token refreshed successfully");

            AuthResponse response = new AuthResponse(
                    newToken,
                    newRefreshToken,
                    username,
                    user.getEmail(),
                    user.getRole().name(),
                    jwtExpiration,
                    message,
                    user.getPreferredLanguage(),
                    user.getPreferredCurrency()
            );

            log.info("Token refreshed successfully for user: {}", username);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("Token refresh failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected token refresh error: ", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Token refresh failed. Please try again."));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        log.info("User logged out successfully");
        return ResponseEntity.ok(response);
    }
}

