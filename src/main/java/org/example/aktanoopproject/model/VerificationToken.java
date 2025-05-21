package org.example.aktanoopproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private LocalDateTime expirationDate;
    @OneToOne
    @JoinColumn(name = "varification_user_id")
    private User user;

    public VerificationToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.expirationDate = LocalDateTime.now().plusHours(24);
        this.user = user;
    }

    public VerificationToken() {

    }
}
