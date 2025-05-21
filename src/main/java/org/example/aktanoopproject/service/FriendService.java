package org.example.aktanoopproject.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.aktanoopproject.model.FriendRequest;
import org.example.aktanoopproject.model.RequestStatus;
import org.example.aktanoopproject.model.Student;
import org.example.aktanoopproject.model.User;
import org.example.aktanoopproject.repository.FriendRequestRepository;
import org.example.aktanoopproject.repository.StudentRepository;
import org.example.aktanoopproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<FriendRequest> getMyInviting(User currentUser) {
        return friendRequestRepository.getAllBySender(currentUser);
    }
    public List<FriendRequest> getRequests(User currentUser) {
        return friendRequestRepository.getAllByRecipient(currentUser);
    }

    public void addFriend(Long friendId, User currentUser) {
        Optional<Student> friendUser = studentRepository.findById(friendId);
        if (!friendUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        FriendRequest friendRequest = new FriendRequest(currentUser, friendUser.get());
        friendRequestRepository.save(friendRequest);
    }

    public void deleteFriend(Long friendId, User currentUser) {
        // Найти запись по friendId и текущему пользователю (в любом порядке)
        Optional<FriendRequest> request = friendRequestRepository.findByUsersInEitherOrder(friendId, currentUser.getId());

        if (request.isPresent()) {
            friendRequestRepository.delete(request.get());
        } else {
            throw new EntityNotFoundException("Friend relationship not found.");
        }
    }

    public void acceptInviting(Long friendId, User currentUser) {
        Optional<FriendRequest> request = friendRequestRepository.findByUsersInEitherOrder(friendId, currentUser.getId());
        if (request.isPresent()) {
            FriendRequest friendRequest = request.get();
            friendRequest.setStatus(RequestStatus.ACCEPTED);
        } else {
            throw new EntityNotFoundException("Friend relationship not found.");
        }
    }


}
