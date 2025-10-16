package com.fintech.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product-pricing")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProductPricingController {
    
    @GetMapping("/products")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getProducts() {
        try {
            Map<String, Object> products = new HashMap<>();
            products.put("savingsAccount", Map.of(
                "name", "Premium Savings Account",
                "interestRate", "4.5%",
                "minimumBalance", "₹10,000",
                "features", "Free ATM transactions, Online banking"
            ));
            products.put("fixedDeposit", Map.of(
                "name", "Fixed Deposit",
                "interestRate", "7.2%",
                "minimumAmount", "₹5,000",
                "tenure", "1-5 years"
            ));
            
            log.info("Products retrieved successfully");
            return ResponseEntity.ok(products);
            
        } catch (Exception e) {
            log.error("Error retrieving products: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve products");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/pricing")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getPricing() {
        try {
            Map<String, Object> pricing = new HashMap<>();
            pricing.put("transactionFees", Map.of(
                "atmWithdrawal", "₹20 per transaction",
                "onlineTransfer", "Free",
                "chequeBook", "₹2 per leaf"
            ));
            pricing.put("accountFees", Map.of(
                "maintenanceFee", "₹500 per annum",
                "statementFee", "₹50 per statement"
            ));
            
            log.info("Pricing information retrieved successfully");
            return ResponseEntity.ok(pricing);
            
        } catch (Exception e) {
            log.error("Error retrieving pricing: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve pricing information");
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
