package com.fintech.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fixed-deposit")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class FixedDepositController {
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createFD(@RequestBody Map<String, Object> request) {
        try {
            // Mock FD creation
            Map<String, Object> fd = new HashMap<>();
            fd.put("fdId", "FD" + System.currentTimeMillis());
            fd.put("principal", request.get("principal"));
            fd.put("tenure", request.get("tenure"));
            fd.put("interestRate", request.get("interestRate"));
            fd.put("maturityDate", java.time.LocalDate.now().plusYears(Integer.parseInt(request.get("tenure").toString())));
            fd.put("status", "ACTIVE");
            fd.put("createdDate", java.time.LocalDateTime.now().toString());
            
            log.info("FD created successfully with ID: {}", fd.get("fdId"));
            return ResponseEntity.ok(fd);
            
        } catch (Exception e) {
            log.error("Error creating FD: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create FD");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getFDList() {
        try {
            Map<String, Object> fdList = new HashMap<>();
            fdList.put("fds", new Object[]{
                Map.of(
                    "fdId", "FD001",
                    "principal", "₹1,00,000",
                    "tenure", "3 years",
                    "interestRate", "7.0%",
                    "maturityDate", "2027-01-15",
                    "status", "ACTIVE"
                ),
                Map.of(
                    "fdId", "FD002",
                    "principal", "₹50,000",
                    "tenure", "1 year",
                    "interestRate", "6.5%",
                    "maturityDate", "2025-01-15",
                    "status", "ACTIVE"
                )
            });
            
            log.info("FD list retrieved successfully");
            return ResponseEntity.ok(fdList);
            
        } catch (Exception e) {
            log.error("Error retrieving FD list: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve FD list");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/premature-closure/{fdId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> prematureClosure(@PathVariable String fdId) {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("message", "FD " + fdId + " closed prematurely");
            response.put("penalty", "1% of principal amount");
            response.put("closureDate", java.time.LocalDateTime.now().toString());
            
            log.info("FD {} closed prematurely", fdId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error closing FD {}: {}", fdId, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to close FD");
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
