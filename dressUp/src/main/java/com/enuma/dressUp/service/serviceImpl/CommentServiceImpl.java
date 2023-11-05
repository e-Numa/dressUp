package com.enuma.dressUp.service.serviceImpl;


import com.enuma.dressUp.dto.CommentDto;
import com.enuma.dressUp.entity.Comment;
import com.enuma.dressUp.entity.Post;
import com.enuma.dressUp.exception.BlogApiException;
import com.enuma.dressUp.exception.ResourceNotFoundException;
import com.enuma.dressUp.repository.CommentRepository;
import com.enuma.dressUp.repository.PostRepository;
import com.enuma.dressUp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow
                (() -> new ResourceNotFoundException("Post", "id", postId));

        // set post to comment entity
        comment.setPost(post);

        //comment entity to DB
        Comment newComment = commentRepository.save(comment);
        return mapToDo(newComment);
    }


    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        // Retrieve comments by post
        List<Comment> comments = commentRepository.findByPostPostId(postId);

        return comments.stream().map(this::mapToDo).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id

        Post post = postRepository.findById(postId).orElseThrow
                (() -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getPostId().equals(post.getPostId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDo(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow
                (() -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getPostId().equals(post.getPostId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDo(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getPostId().equals(post.getPostId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);

    }
    private CommentDto mapToDo(Comment comment) {

//        CommentDto commentDto = new CommentDto();
//        commentDto.setUserId(comment.getUserId());
//        commentDto.setName(commentDto.getName());
//        commentDto.setEmail(commentDto.getEmail());
//        commentDto.setBody(comment.getBody());

        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
  //       Comment comment = new Comment();
  //       comment.setUserId(commentDto.getUserId());
 //        comment.setName(commentDto.getName());
 //        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return mapper.map(commentDto, Comment.class);
    }


}
