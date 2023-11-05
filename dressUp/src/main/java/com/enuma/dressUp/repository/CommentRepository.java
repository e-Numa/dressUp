package com.enuma.dressUp.repository;

import com.enuma.dressUp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

        List<Comment> findByCommentId(Long commentId);
        List<Comment> findByPostPostId(Long postId);
}
