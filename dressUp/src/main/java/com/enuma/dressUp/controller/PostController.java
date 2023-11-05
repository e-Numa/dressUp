package com.enuma.dressUp.controller;

import com.enuma.dressUp.dto.PostDto;
import com.enuma.dressUp.dto.PostResponse;
import com.enuma.dressUp.service.PostService;
import com.enuma.dressUp.utility.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all post rest Api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id" )Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //update post
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto updatedPost) {
        // Load the existing post
        PostDto postDto = postService.getPostById(id);

        if (postDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Update the specific field(s)
        if (updatedPost.getTitle() != null) {
            postDto.setTitle(updatedPost.getTitle());
        }
        if (updatedPost.getDescription() != null) {
            postDto.setDescription(updatedPost.getDescription());
        }
        if (updatedPost.getContent() != null) {
            postDto.setContent(updatedPost.getContent());
        }
        // Save the updated post
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }



    //    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}
