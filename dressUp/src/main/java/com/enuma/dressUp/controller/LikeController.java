package com.enuma.dressUp.controller;

import com.enuma.dressUp.service.LikesServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikesServices likesService;

    public LikeController(LikesServices likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/toggle/{postId}")
    public ResponseEntity<String> toggleLike(@PathVariable("postId") Long postId, HttpServletRequest request) {
        likesService.toggleLike(postId, request);
        return ResponseEntity.ok("Liked/Unliked post with ID: " + postId);
    }

    @DeleteMapping("/delete/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable("likeId") Long likeId) {
        likesService.deleteLike(likeId);
        return ResponseEntity.ok("Deleted like with ID: " + likeId);
    }
}
