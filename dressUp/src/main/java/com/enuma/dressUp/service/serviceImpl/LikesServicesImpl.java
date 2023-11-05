package com.enuma.dressUp.service.serviceImpl;


import com.enuma.dressUp.entity.Likes;
import com.enuma.dressUp.entity.Post;
import com.enuma.dressUp.entity.User;
import com.enuma.dressUp.exception.BlogApiException;
import com.enuma.dressUp.exception.LikeNotFoundException;
import com.enuma.dressUp.repository.LikesRepository;
import com.enuma.dressUp.repository.PostRepository;
import com.enuma.dressUp.service.LikesServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class LikesServicesImpl implements LikesServices {
    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    public LikesServicesImpl(LikesRepository likesRepository, PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
    }

    public void toggleLike(Long postId, HttpServletRequest request) {
        // Get the current user from the session
        HttpSession session = request.getSession();
        User currentUser = (User)  session.getAttribute("user");
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Check if the user has already liked the post
            boolean hasLiked = likesRepository.existsByPostAndUser(post, currentUser);

            if (hasLiked) {
                // User has already liked the post, so decrement the like count and delete the like
                post.setLikeCount(post.getLikeCount() - 1);
                likesRepository.deleteByPostAndUser(post, currentUser);
            } else {
                // User hasn't liked the post, so increment the like count and save a new like
                post.setLikeCount(post.getLikeCount() + 1);
                Likes like = new Likes();
                like.setPost(post);
                like.setUser(currentUser);
                likesRepository.save(like);
            }

            postRepository.save(post);
        } else {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Like does not belongs to post");
        }
    }

    public void deleteLike(Long likeId) {
        Optional<Likes> optionalLike = likesRepository.findById(likeId);
        if (optionalLike.isPresent()) {
            Likes like = optionalLike.get();
            likesRepository.delete(like);

            // Update the like count in the associated post
            Post post = like.getPost();
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
        } else {
            throw new LikeNotFoundException("Like not found with ID: " + likeId);
        }
    }

}
