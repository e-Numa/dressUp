package com.enuma.dressUp.repository;


import com.enuma.dressUp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);

}
