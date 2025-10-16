package com.fintech.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CustomerController {
    
    @GetMapping("/profile")
    public ResponseEntity<?> getCustomerProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            // Mock customer profile data with PII masking
            Map<String, Object> profile = new HashMap<>();
            profile.put("customerId", "CUST001");
            profile.put("name", "John Doe");
            profile.put("email", "john.doe@example.com");
            profile.put("phone", "****-****-1234"); // Masked phone
            profile.put("aadhar", "****-****-5678"); // Masked AADHAR
            profile.put("address", "123 Main St, City, State");
            profile.put("accountStatus", "ACTIVE");
            profile.put("kycStatus", "VERIFIED");
            
            log.info("Customer profile retrieved successfully");
            return ResponseEntity.ok(profile);
            
        } catch (Exception e) {
            log.error("Error retrieving customer profile: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve customer profile");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateCustomerProfile(@RequestBody Map<String, Object> profileData) {
        try {
            // Mock profile update
            Map<String, String> response = new HashMap<>();
            response.put("message", "Customer profile updated successfully");
            response.put("timestamp", java.time.LocalDateTime.now().toString());
            
            log.info("Customer profile updated successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error updating customer profile: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update customer profile");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/accounts")
    public ResponseEntity<?> getCustomerAccounts() {
        try {
            // Mock account data
            Map<String, Object> accounts = new HashMap<>();
            accounts.put("savingsAccount", Map.of(
                "accountNumber", "****-****-1234",
                "balance", "₹50,000.00",
                "status", "ACTIVE"
            ));
            accounts.put("currentAccount", Map.of(
                "accountNumber", "****-****-5678",
                "balance", "₹25,000.00",
                "status", "ACTIVE"
            ));
            
            log.info("Customer accounts retrieved successfully");
            return ResponseEntity.ok(accounts);
            
        } catch (Exception e) {
            log.error("Error retrieving customer accounts: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve customer accounts");
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
