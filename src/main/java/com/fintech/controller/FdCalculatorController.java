package com.fintech.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fd-calculator")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class FdCalculatorController {
    
    @PostMapping("/calculate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> calculateFD(@RequestBody Map<String, Object> request) {
        try {
            double principal = Double.parseDouble(request.get("principal").toString());
            int tenure = Integer.parseInt(request.get("tenure").toString());
            double interestRate = Double.parseDouble(request.get("interestRate").toString());
            
            // Calculate compound interest
            double amount = principal * Math.pow(1 + (interestRate / 100), tenure);
            double interest = amount - principal;
            
            Map<String, Object> result = new HashMap<>();
            result.put("principal", principal);
            result.put("tenure", tenure);
            result.put("interestRate", interestRate);
            result.put("maturityAmount", Math.round(amount * 100.0) / 100.0);
            result.put("interestEarned", Math.round(interest * 100.0) / 100.0);
            result.put("calculationDate", java.time.LocalDateTime.now().toString());
            
            log.info("FD calculation completed for principal: {}, tenure: {} years", principal, tenure);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("Error calculating FD: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to calculate FD. Please check your inputs.");
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/rates")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getFDRates() {
        try {
            Map<String, Object> rates = new HashMap<>();
            rates.put("rates", Map.of(
                "1Year", "6.5%",
                "2Years", "6.8%",
                "3Years", "7.0%",
                "5Years", "7.2%"
            ));
            rates.put("lastUpdated", java.time.LocalDateTime.now().toString());
            
            log.info("FD rates retrieved successfully");
            return ResponseEntity.ok(rates);
            
        } catch (Exception e) {
            log.error("Error retrieving FD rates: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve FD rates");
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
