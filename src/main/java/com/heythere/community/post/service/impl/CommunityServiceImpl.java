package com.heythere.community.post.service.impl;

import com.heythere.community.post.mapper.CommentResponseMapper;
import com.heythere.community.post.dto.CommentRegisterRequestDto;
import com.heythere.community.post.dto.CommunityLookupRequestDto;
import com.heythere.community.post.dto.LargeCommentRegisterRequestDto;
import com.heythere.community.post.dto.PressGoodOrBadButtonRequestDto;
import com.heythere.community.post.exception.BadRequestException;
import com.heythere.community.post.mapper.GoodOrBadPressedStatusResponseMapper;
import com.heythere.community.post.mapper.PostResponseMapper;
import com.heythere.community.post.exception.ResourceNotFoundException;
import com.heythere.community.post.model.*;
import com.heythere.community.post.repository.*;
import com.heythere.community.post.service.CommunityService;
import com.heythere.community.post.service.FileCompressionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommunityServiceImpl extends CommunityService {

    public CommunityServiceImpl(RestTemplate restTemplate,
                                UserRepository userRepository,
                                PostRepository postRepository,
                                PictureRepository pictureRepository,
                                CommentRepository commentRepository,
                                LargeCommentRepository largeCommentRepository,
                                PostAndUserRepository postAndUserRepository,
                                FileCompressionService fileCompressionService,
                                AmazonS3StorageService amazonS3StorageService) {
        super(restTemplate,
                userRepository,
                postRepository,
                pictureRepository,
                commentRepository,
                largeCommentRepository,
                postAndUserRepository,
                fileCompressionService,
                amazonS3StorageService);
    }

    @Override
    @Transactional
    public List<PostResponseMapper> findAllPost(final CommunityLookupRequestDto payload, final Pageable pageable) {
        final User communityOwner = getUser(payload.getCommunityOwnerId());
        final User requestUser = getUser(payload.getRequestUserId());

        return postRepository.findAllByUserOrderByCreatedAtDesc(communityOwner, pageable).stream()
                .map(post -> PostResponseMapper.of(post, requestUser))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostResponseMapper findPostById(final Long postId, final CommunityLookupRequestDto payload) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        final User requestUser = getUser(payload.getRequestUserId());
        return PostResponseMapper.of(post, requestUser);
    }

    @Override
    @Transactional
    public List<CommentResponseMapper> findAllComments(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId))
                .getComments().stream()
                .map(CommentResponseMapper::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long registerPostWithFiles(final Long requestUserId,
                                      final String title,
                                      final String content,
                                      final List<MultipartFile> images) throws IOException {

        final Post post = postRepository.saveAndFlush(
                Post.builder()
                        .user(getUser(requestUserId))
                        .title(title)
                        .content(content)
                        .build()
        );


        for (final MultipartFile img : images) {
            pictureRepository.saveAndFlush(Picture.builder()
                    .url(amazonS3StorageService.upload(img, post, "post"))
                    .post(post)
                    .build());
        }
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
                .user(getUser(requestUserId))
                .build()).getId();
    }

    @Override
    @Transactional
    public Long registerComment(final CommentRegisterRequestDto payload) {
        final Post post = postRepository.findById(payload.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", payload.getPostId()));

        final Comment comment = commentRepository.save(Comment.builder()
                .comment(payload.getComment())
                .user(getUser(payload.getRequestUserId()))
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
                .user(getUser(payload.getRequestUserId()))
                .comment(comment)
                .build());

        return largeComment.addLargeCommentToComment(comment).getId();
    }

    @Override
    @Transactional
    public void updateComment(final CommentRegisterRequestDto payload) {

    }

    @Override
    @Transactional
    public void updateLargeComment(final LargeCommentRegisterRequestDto payload) {


    }

    @Override
    @Transactional
    public void deletePost(final Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void deleteLargeComment(final Long largeCommentId) {
        largeCommentRepository.deleteById(largeCommentId);
    }

    @Override
    @Transactional
    public GoodOrBadPressedStatusResponseMapper pressedLikeOrDislikeButtonOnPost(final PressGoodOrBadButtonRequestDto payload) {
        if (payload.getIsGood().equals(true) && payload.getIsBad().equals(true)) {
            new BadRequestException("Like And Dislike status must be different!");
        }

        final User user = getUser(payload.getRequestUserId());
        final Post post = postRepository.findById(payload.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", payload.getPostId()));

        final PostAndUser status = !postAndUserRepository.existsByUserAndPost(user, post)
                ?
                postAndUserRepository.save(PostAndUser.builder()
                        .user(user)
                        .post(post)
                        .goodStatus(payload.getIsGood())
                        .badStatus(payload.getIsBad())
                        .build())
                :
                postAndUserRepository.findByUserAndPost(user, post)
                        .updateStatus(payload.getIsGood(), payload.getIsBad())
                        .addStatusToUserAndPost(user, post);

        final int goodCount = postAndUserRepository.countAllByUserAndPostAndGoodStatusIsTrue(user, post);
        final int badCount = postAndUserRepository.countAllByUserAndPostAndBadStatusIsTrue(user, post);
        post.updateGoodOrBadCount(goodCount, badCount);

        return new GoodOrBadPressedStatusResponseMapper(goodCount, badCount, status.getGoodStatus(), status.getBadStatus());
    }
}
