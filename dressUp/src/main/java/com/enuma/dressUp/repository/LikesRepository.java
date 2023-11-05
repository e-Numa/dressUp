package com.enuma.dressUp.repository;

import com.enuma.dressUp.entity.Likes;
import com.enuma.dressUp.entity.Post;
import com.enuma.dressUp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByPostAndUser(Post post, User user);
    void deleteByPostAndUser(Post post, User user);
}
