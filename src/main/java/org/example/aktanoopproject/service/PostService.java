package org.example.aktanoopproject.service;

import org.example.aktanoopproject.dto.PostDTO;
import org.example.aktanoopproject.model.Activity;
import org.example.aktanoopproject.model.InterestType;
import org.example.aktanoopproject.model.Post;
import org.example.aktanoopproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public void save(PostDTO postDTO) {
        Post post = new Post();
        post.setName(postDTO.getName());
        post.setDescription(postDTO.getDescription());
        Set<Activity> activities = postDTO.getActivities().stream()
                .map(String::toUpperCase)
                .map(Activity::valueOf)
                .collect(Collectors.toSet());
        post.setActivities(activities);
        postRepository.save(post);
    }
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
    public List<Post> getAll() {
        return postRepository.findAll();
    }
    public List<Post> getByActivities(Set<String> activityStrings) {
        Set<Activity> activities = activityStrings.stream()
                .map(String::toUpperCase)
                .map(Activity::valueOf)
                .collect(Collectors.toSet());

        return postRepository.findByActivitiesIn(activities);
    }

}
