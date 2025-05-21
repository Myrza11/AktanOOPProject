package org.example.aktanoopproject.service;

import org.example.aktanoopproject.config.JwtUtil;
import org.example.aktanoopproject.dto.LoginDTO;
import org.example.aktanoopproject.dto.OrganisationDTO;
import org.example.aktanoopproject.dto.StudentDTO;
import org.example.aktanoopproject.model.*;
import org.example.aktanoopproject.repository.UserRepository;
import org.example.aktanoopproject.repository.VerificationTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, VerificationTokenRepository tokenRepository, EmailService emailService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
    }

    public void registerStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setPassword(new BCryptPasswordEncoder().encode(studentDTO.getPassword()));
        student.setEmailVerified(false);
        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setEmail(studentDTO.getEmail());

        Set<InterestType> interests = studentDTO.getInterests().stream()
                .map(String::toUpperCase)
                .map(InterestType::valueOf)
                .collect(Collectors.toSet());

        student.setInterests(interests);

        userRepository.save(student);

        VerificationToken token = new VerificationToken(student);
        tokenRepository.save(token);

        emailService.sendVerificationEmail(student.getEmail(), token.getToken());
    }

    public void registerOrganisation(OrganisationDTO dto) {
        Organisation organisation = new Organisation();
        organisation.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        organisation.setEmailVerified(false);
        organisation.setName(dto.getName());
        organisation.setSurname(dto.getSurname());
        organisation.setOrganisationName(dto.getOrganisationName());
        organisation.setOrganisationType(dto.getOrganisationType());
        organisation.setDescription(dto.getDescription());
        organisation.setPosition(dto.getPosition());
        organisation.setLinkToSite(dto.getLinkToSite());
        organisation.setEmail(dto.getEmail());
        organisation.setTelegramNickname(dto.getTelegramNickname());

        userRepository.save(organisation);

        VerificationToken token = new VerificationToken(organisation);
        tokenRepository.save(token);

        emailService.sendVerificationEmail(dto.getEmail(), token.getToken());
    }

    public String authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new RuntimeException("user not found or not verified");
        }
        if (!user.get().isEmailVerified()){
            throw new RuntimeException("user is not verified");
        }
        if (!new BCryptPasswordEncoder().matches(password, user.get().getPassword())) {
            throw new RuntimeException("password not match");
        }
        return jwtUtil.generateToken(user.get());
    }
}
