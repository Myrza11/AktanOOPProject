package org.example.aktanoopproject.controller;

import org.example.aktanoopproject.dto.UpdateOrganisationDTO;
import org.example.aktanoopproject.dto.UpdateStudentDTO;
import org.example.aktanoopproject.model.Organisation;
import org.example.aktanoopproject.model.Student;
import org.example.aktanoopproject.model.User;
import org.example.aktanoopproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping("/student-update")
    public void updateStudent(@RequestBody UpdateStudentDTO updateStudentDTO, @AuthenticationPrincipal User currentUser) {
        userService.update(updateStudentDTO, (Student) currentUser);
    }
    @PatchMapping("/organisation-update")
    public void updateOrganisation(@RequestBody UpdateOrganisationDTO updateOrganisationDTO, @AuthenticationPrincipal User currentUser) {
        userService.updateOrganisation(updateOrganisationDTO, (Organisation) currentUser);
    }

    @PatchMapping(value = "/avatar-update", consumes = "multipart/form-data")
    public void updateAvatar(@RequestParam("avatar") MultipartFile avatar, @AuthenticationPrincipal User currentUser) throws IOException {
        userService.updateAvatar(currentUser.getEmail(), avatar);
    }

    @GetMapping("/filter")
    public List<Student> filterStudents(@RequestParam Set<String> interests) {
        return userService.filter(interests);
    }
    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/organisations")
    public List<Organisation> getAllOrganisations() {
        return userService.getAllOrganisations();
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return userService.getAllStudents();
    }

    @GetMapping("/friends")
    public List<Student> getFriends(@AuthenticationPrincipal User currentUser) {
        return userService.getFriendsOfUser(currentUser);
    }

    @GetMapping("/search")
    public List<User> searchUsersByName(@RequestParam String query) {
        return userService.searchUsersByName(query);
    }
}
