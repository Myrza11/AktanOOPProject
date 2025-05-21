package org.example.aktanoopproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.aktanoopproject.config.RevokedTokenService;
import org.example.aktanoopproject.dto.LoginDTO;
import org.example.aktanoopproject.dto.OrganisationDTO;
import org.example.aktanoopproject.dto.StudentDTO;
import org.example.aktanoopproject.model.*;
import org.example.aktanoopproject.repository.UserRepository;
import org.example.aktanoopproject.repository.VerificationTokenRepository;
import org.example.aktanoopproject.service.AuthService;
import org.example.aktanoopproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RevokedTokenService revokedTokenService;


    @PostMapping("/register/student")
    public ResponseEntity<String> registerStudent(@RequestBody StudentDTO student) {
        authService.registerStudent(student);
        return ResponseEntity.ok("Регистрация успешна. Проверьте email.");
    }

    @PostMapping("/register/organisation")
    public ResponseEntity<String> registerOrganisation(@RequestBody OrganisationDTO organisation) {
        authService.registerOrganisation(organisation);
        return ResponseEntity.ok("Регистрация успешна. Проверьте email.");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        return ResponseEntity.ok(Collections.singletonMap("token", authService.authenticate(login.getEmail(), login.getPassword())));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        revokedTokenService.revokeToken(jwt);
        return ResponseEntity.ok("Logged out successfully");
    }

}
