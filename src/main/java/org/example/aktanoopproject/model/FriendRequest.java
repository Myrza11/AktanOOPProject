package org.example.aktanoopproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(
        name = "friend_request",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "reciepent_id"})
)
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "reciepent_id")
    private User recipient;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private LocalDateTime requestedAt;

    public FriendRequest(User sender, User recipient) {
        this.sender = sender;
        this.recipient = recipient;
        this.status = RequestStatus.PENDING;
    }

    public FriendRequest() {

    }
    @PrePersist
    protected void onCreate() {
        this.requestedAt = LocalDateTime.now();
    }
}
