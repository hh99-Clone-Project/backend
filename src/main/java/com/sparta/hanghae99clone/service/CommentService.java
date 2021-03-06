package com.sparta.hanghae99clone.service;

import com.sparta.hanghae99clone.dto.CommentDto;
import com.sparta.hanghae99clone.dto.request.CommentRequestDto;
import com.sparta.hanghae99clone.dto.response.CommentListResponseDto;
import com.sparta.hanghae99clone.model.Comment;
import com.sparta.hanghae99clone.model.Image;
import com.sparta.hanghae99clone.model.Post;
import com.sparta.hanghae99clone.model.User;
import com.sparta.hanghae99clone.repository.CommentRepository;
import com.sparta.hanghae99clone.repository.ImageRepository;
import com.sparta.hanghae99clone.repository.PostRepository;
import com.sparta.hanghae99clone.utill.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final Calculator calculator;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, ImageRepository imageRepository, Calculator calculator){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.calculator = calculator;
    }



    public CommentDto createComment(CommentRequestDto requestDto, Post post, User user) {

        Comment comment = new Comment(requestDto,post,user);
        commentRepository.save(comment);

        CommentDto commentDto = new CommentDto(comment.getId(),comment.getNickname(),comment.getContents());

        return commentDto;
    }

    public CommentListResponseDto showallcomment(Long postId,Integer pageId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("null")
        );

        Image image = imageRepository.findByPost(post).orElseThrow(
                () -> new IllegalArgumentException("null")
        );

        String imageFile = image.getImageFile();
        String content = post.getContent();
//        String dayBefore = post ????....
        // toDo: fix commentCnt
        Long commentCnt = Long.valueOf(commentRepository.findByPost(post).size());
        String nickname = post.getUser().getNickname();
        Pageable pageable= PageRequest.of(pageId,10);
        Page<Comment> commentList= commentRepository.findAllByPost(post,pageable);

        List<CommentDto> comments =new ArrayList<>();
        for(Comment comment:commentList){
            CommentDto commentDto = new  CommentDto(comment.getId(),comment.getNickname(),comment.getContents());
            comments.add(commentDto);
        }
        long dayBefore = ChronoUnit.MINUTES.between(post.getCreatedAt(), LocalDateTime.now());
        CommentListResponseDto commentListResponseDto =
                new CommentListResponseDto(postId,imageFile,content,calculator.time(dayBefore),commentCnt,nickname,comments);
        return commentListResponseDto;


    }
}