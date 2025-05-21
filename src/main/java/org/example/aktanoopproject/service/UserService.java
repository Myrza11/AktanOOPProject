package org.example.aktanoopproject.service;

import jakarta.transaction.Transactional;
import org.example.aktanoopproject.dto.UpdateOrganisationDTO;
import org.example.aktanoopproject.dto.UpdateStudentDTO;
import org.example.aktanoopproject.model.*;
import org.example.aktanoopproject.repository.FriendRequestRepository;
import org.example.aktanoopproject.repository.OrganisationRepository;
import org.example.aktanoopproject.repository.StudentRepository;
import org.example.aktanoopproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public void update(UpdateStudentDTO updateStudentDTO, Student currentUser) {
        if (updateStudentDTO.getName() != null) currentUser.setName(updateStudentDTO.getName());
        if (updateStudentDTO.getPassword() != null) currentUser.setPassword(passwordEncoder.encode(updateStudentDTO.getPassword()));
        if (updateStudentDTO.getSurname() != null) currentUser.setSurname(updateStudentDTO.getSurname());
        if (updateStudentDTO.getTelegramNickname()!= null) currentUser.setTelegramNickname(updateStudentDTO.getTelegramNickname());
        if (updateStudentDTO.getInterests() != null) {
            Set<InterestType> interests = updateStudentDTO.getInterests().stream()
                    .map(String::toUpperCase)
                    .map(InterestType::valueOf)
                    .collect(Collectors.toSet());

            currentUser.setInterests(interests);
        }
        userRepository.save(currentUser);
    }

    public void updateOrganisation(UpdateOrganisationDTO updateOrganisationDTO, Organisation currentUser) {
        if (updateOrganisationDTO.getName() != null) currentUser.setName(updateOrganisationDTO.getName());
        if (updateOrganisationDTO.getPassword() != null) currentUser.setPassword(passwordEncoder.encode(updateOrganisationDTO.getPassword()));
        if (updateOrganisationDTO.getSurname() != null) currentUser.setSurname(updateOrganisationDTO.getSurname());
        if (updateOrganisationDTO.getTelegramNickname()!= null) currentUser.setTelegramNickname(updateOrganisationDTO.getTelegramNickname());
        if (updateOrganisationDTO.getOrganisationType() != null) currentUser.setOrganisationType(updateOrganisationDTO.getOrganisationType());
        if (updateOrganisationDTO.getLinkToSite() != null) currentUser.setLinkToSite(updateOrganisationDTO.getLinkToSite());
        if (updateOrganisationDTO.getDescription() != null) currentUser.setDescription(updateOrganisationDTO.getDescription());
        if (updateOrganisationDTO.getPosition() != null) currentUser.setPosition(updateOrganisationDTO.getPosition());
        if (updateOrganisationDTO.getOrganisationName() != null) currentUser.setOrganisationName(updateOrganisationDTO.getOrganisationName());
        userRepository.save(currentUser);
    }

    public List<Student> filter(Set<String> interests) {
        Set<InterestType> interestsType = interests.stream()
                .map(String::toUpperCase)
                .map(InterestType::valueOf)
                .collect(Collectors.toSet());

        return studentRepository.filterByInterests(interestsType);
    }
    // Метод для обновления аватарки
    @Transactional
    public void updateAvatar(String email, MultipartFile avatarFile) throws IOException {
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (currentUser == null) {
            throw new RuntimeException("Пользователь не найден или не аутентифицирован.");
        }
        byte[] avatarBytes = avatarFile.getBytes();
        currentUser.setAvatar(avatarBytes);
        userRepository.save(currentUser);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getFriendsOfUser(User user) {
        List<FriendRequest> acceptedRequests = friendRequestRepository
                .findBySenderOrRecipientAndStatus(user, user, RequestStatus.ACCEPTED);

        Set<Student> friends = new HashSet<>();
        for (FriendRequest request : acceptedRequests) {
            if (request.getSender().equals(user) && request.getRecipient() instanceof Student) {
                friends.add((Student) request.getRecipient());
            } else if (request.getRecipient().equals(user) && request.getSender() instanceof Student) {
                friends.add((Student) request.getSender());
            }
        }

        return new ArrayList<>(friends);
    }


    public List<User> searchUsersByName(String name) {
        return userRepository.findByNameLikeIgnoreCase(name);
    }

}
