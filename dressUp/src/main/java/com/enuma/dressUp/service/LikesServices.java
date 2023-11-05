package com.enuma.dressUp.service;

import jakarta.servlet.http.HttpServletRequest;

public interface LikesServices {
    void toggleLike(Long postId, HttpServletRequest request);
    void deleteLike(Long likeId);
}
