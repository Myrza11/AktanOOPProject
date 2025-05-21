package org.example.aktanoopproject.repository;

import org.example.aktanoopproject.model.FriendRequest;
import org.example.aktanoopproject.model.RequestStatus;
import org.example.aktanoopproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> getAllBySender(User currentUser);

    List<FriendRequest> getAllByRecipient(User currentUser);

    void deleteByRecipientId(Long friendId);

    @Query("SELECT fr FROM FriendRequest fr WHERE " +
            "(fr.sender.id = :id1 AND fr.recipient.id = :id2) OR " +
            "(fr.sender.id = :id2 AND fr.recipient.id = :id1)")
    Optional<FriendRequest> findByUsersInEitherOrder(@Param("id1") Long id1, @Param("id2") Long id2);

    List<FriendRequest> findBySenderOrRecipientAndStatus(User sender, User recipient, RequestStatus status);

}
