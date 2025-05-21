package org.example.aktanoopproject.controller;

import org.example.aktanoopproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify")

public class VerificationController {

    @Autowired
    private EmailService emailService;


    @GetMapping
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok("Email подтвержден, теперь вы можете войти    n/n   " + emailService.verifyEmail(token));

    }

    @GetMapping("/exists")
    public boolean isEmailExist(@RequestParam String email) {
        return emailService.isEmailExist(email);
    }

}
