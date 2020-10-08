package com.heythere.community.post.service.impl;

import com.heythere.community.post.dto.request.CommentRegisterRequestDto;
import com.heythere.community.post.dto.request.LargeCommentRegisterRequestDto;
import com.heythere.community.post.mapper.PostResponseMapper;
import com.heythere.community.post.exception.ResourceNotFoundException;
import com.heythere.community.post.model.*;
import com.heythere.community.post.repository.*;
import com.heythere.community.post.service.CommunityService;
import com.heythere.community.compression.service.FileCompressionService;
import com.heythere.community.s3.service.AmazonS3StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CommunityServiceImpl extends CommunityService {

    public CommunityServiceImpl(RestTemplate restTemplate,
                                UserRepository userRepository,
                                PostRepository postRepository,
                                PictureRepository pictureRepository,
                                CommentRepository commentRepository,
                                LargeCommentRepository largeCommentRepository,
                                FileCompressionService fileCompressionService,
                                AmazonS3StorageService amazonS3StorageService) {
        super(  restTemplate,
                userRepository,
                postRepository,
                pictureRepository,
                commentRepository,
                largeCommentRepository,
                fileCompressionService,
                amazonS3StorageService);
    }

    @Override
    public List<PostResponseMapper> findAllPost(Long userId) {
        return null;
    }

    @Override
    public PostResponseMapper findPostById(Long postId) {
        return null;
    }

    @Override
    @Transactional
    public Long registerPostWithFiles(final Long requestUserId,
                                      final String title,
                                      final String content,
                                      final List<MultipartFile> img) throws IOException {
        log.info("file : {}" , img);

        final User user = getCurrentUser(requestUserId);

        final Post post = postRepository.save(Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .build());

        img.forEach(file -> {
            try {
                final Picture picture = pictureRepository.save(Picture.builder()
                        .url(amazonS3StorageService.upload(file, "post"))
                        .post(post)
                        .build());

                picture.addPictureToPost(post);
                log.info("get picture url : {}", picture.getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return post.getId();
    }

    @Override
    @Transactional
    public Long registerPostWithoutFiles(final Long requestUserId,
                                         final String title,
                                         final String content) {
        return postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .user(getCurrentUser(requestUserId))
                .build()).getId();
    }

    @Override
    @Transactional
    public Long registerComment(final CommentRegisterRequestDto payload) {
        final Post post = postRepository.findById(payload.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", payload.getPostId()));

        final Comment comment = commentRepository.save(Comment.builder()
                .comment(payload.getComment())
                .user(getCurrentUser(payload.getRequestUserId()))
                .post(post)
                .build());

        return comment.addCommentToPost(post).getId();
    }

    @Override
    @Transactional
    public Long registerLargeComment(final LargeCommentRegisterRequestDto payload) {
        final Comment comment = commentRepository.findById(payload.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", payload.getCommentId()));

        final LargeComment largeComment = largeCommentRepository.save(LargeComment.builder()
                .largeComment(payload.getLargeComment())
                .user(getCurrentUser(payload.getRequestUserId()))
                .comment(comment)
                .build());

        return largeComment.addLargeCommentToComment(comment).getId();
    }
}
