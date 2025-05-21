package org.example.aktanoopproject.controller;

import org.example.aktanoopproject.dto.PostDTO;
import org.example.aktanoopproject.model.Post;
import org.example.aktanoopproject.repository.PostRepository;
import org.example.aktanoopproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<String> post(@RequestBody PostDTO post) {
        postService.save(post);
        return ResponseEntity.ok("Post is saved");
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok("Post is deleted");
    }
    @GetMapping("/get-all")
    public List<Post> getAllPosts() {
        return postService.getAll();
    }

    @GetMapping("/get-byFilter")
    public List<Post> getPostsByFilter(@RequestParam Set<String> activities) {
        return postService.getByActivities(activities);
    }
}
