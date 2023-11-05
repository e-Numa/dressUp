package com.enuma.dressUp.service;

import com.enuma.dressUp.dto.PostDto;
import com.enuma.dressUp.dto.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePostById(Long id);

}
