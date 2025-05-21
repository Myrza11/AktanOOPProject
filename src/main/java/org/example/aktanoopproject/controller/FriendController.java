package org.example.aktanoopproject.controller;

import org.example.aktanoopproject.model.FriendRequest;
import org.example.aktanoopproject.model.User;
import org.example.aktanoopproject.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/invite/{friendId}")
    public void addFriend(@PathVariable Long friendId, @AuthenticationPrincipal User currentUser) {
        friendService.addFriend(friendId, currentUser);
    }

    @GetMapping("/get-my-inviting")
    public List<FriendRequest> getMyInviting(@AuthenticationPrincipal User currentUser) {
        return friendService.getMyInviting(currentUser);
    }

    @GetMapping("/get-request")
    public List<FriendRequest> getRequest(@AuthenticationPrincipal User currentUser) {
        return friendService.getRequests(currentUser);
    }

    @DeleteMapping("/delete/{friendId}")
    public void deleteFriend(@PathVariable Long friendId, @AuthenticationPrincipal User currentUser) {
        friendService.deleteFriend(friendId, currentUser);
    }

    @PostMapping("/accept/{userId}")
    public void acceptInviting(@PathVariable Long userId, @AuthenticationPrincipal User currentUser) {
        friendService.acceptInviting(userId, currentUser);
    }

}
