package com.heythere.community.post.service;

import com.heythere.community.compression.service.FileCompressionService;
import com.heythere.community.post.shared.CurrentUser;
import com.heythere.community.post.dto.request.CommentRegisterRequestDto;
import com.heythere.community.post.dto.request.LargeCommentRegisterRequestDto;
import com.heythere.community.post.mapper.PostResponseMapper;
import com.heythere.community.post.model.User;
import com.heythere.community.post.repository.*;
import com.heythere.community.s3.service.AmazonS3StorageService;
import lombok.RequiredArgsConstructor;
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
    protected final FileCompressionService fileCompressionService;
    protected final AmazonS3StorageService amazonS3StorageService;

    public abstract List<PostResponseMapper> findAllPost(final Long userId);
    public abstract PostResponseMapper findPostById(final Long postId);
    public abstract Long registerPostWithFiles(final Long requestUserId,
                                               final String title,
                                               final String content,
                                               final List<MultipartFile> img) throws IOException;
    public abstract Long registerPostWithoutFiles(final Long requestUserId,
                                                  final String title,
                                                  final String content);
    public abstract Long registerComment(final CommentRegisterRequestDto payload);
    public abstract Long registerLargeComment(final LargeCommentRegisterRequestDto payload);


    protected User getCurrentUser(final Long id) {
        final String GET_AUTH_USER_URL = "http://heythere-zuul-server/auth/user/%s";

        final CurrentUser user =
                restTemplate.postForObject(String.format(GET_AUTH_USER_URL,id), null, CurrentUser.class);

        final Optional<User> target = userRepository.findById(id);
        if (target.isPresent()) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User Not Found"))
                    .updateUserEntityAndReturn(user);
        }

        return userRepository.save(User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImg())
                .build());
    }

}
