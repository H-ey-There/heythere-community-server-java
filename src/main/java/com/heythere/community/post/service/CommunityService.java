package com.heythere.community.post.service;

import com.heythere.community.post.exception.ResourceNotFoundException;
import com.heythere.community.post.mapper.CommentResponseMapper;
import com.heythere.community.post.dto.CommunityLookupRequestDto;
import com.heythere.community.post.dto.PressGoodOrBadButtonRequestDto;
import com.heythere.community.post.mapper.GoodOrBadPressedStatusResponseMapper;
import com.heythere.community.post.dto.CommentRegisterRequestDto;
import com.heythere.community.post.dto.LargeCommentRegisterRequestDto;
import com.heythere.community.post.mapper.PostResponseMapper;
import com.heythere.community.post.model.User;
import com.heythere.community.post.repository.*;
import com.heythere.community.post.service.impl.AmazonS3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class CommunityService {
    protected final RestTemplate restTemplate;
    protected final UserRepository userRepository;
    protected final PostRepository postRepository;
    protected final PictureRepository pictureRepository;
    protected final CommentRepository commentRepository;
    protected final LargeCommentRepository largeCommentRepository;
    protected final PostAndUserRepository postAndUserRepository;
    protected final FileCompressionService fileCompressionService;
    protected final AmazonS3StorageService amazonS3StorageService;

    public abstract List<PostResponseMapper> findAllPost(final CommunityLookupRequestDto payload, final Pageable pageable);
    public abstract PostResponseMapper findPostById(final Long postId, final CommunityLookupRequestDto payload);
    public abstract List<CommentResponseMapper> findAllComments(Long postId);
    public abstract Long registerPostWithFiles(final Long requestUserId,
                                               final String title,
                                               final String content,
                                               final List<MultipartFile> img) throws IOException;
    public abstract Long registerPostWithoutFiles(final Long requestUserId,
                                                  final String title,
                                                  final String content);
    public abstract Long registerComment(final CommentRegisterRequestDto payload);
    public abstract Long registerLargeComment(final LargeCommentRegisterRequestDto payload);

    public abstract void updateComment(final CommentRegisterRequestDto payload);
    public abstract void updateLargeComment(final LargeCommentRegisterRequestDto payload);

    public abstract void deletePost(final Long postId);
    public abstract void deleteComment(final Long commentId);
    public abstract void deleteLargeComment(final Long largeCommentId);

    public abstract GoodOrBadPressedStatusResponseMapper pressedLikeOrDislikeButtonOnPost(PressGoodOrBadButtonRequestDto payload);


    protected User getUser(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));
    }

}
