package com.heythere.community.post.mapper;

import com.heythere.community.post.model.Comment;
import com.heythere.community.post.model.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseMapper {
    private final Long id;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long userId;
    private final String email;
    private final String name;
    private final String userImg;
    private final List<LargeCommentResponseMapper> largeComments;

    @Builder
    public CommentResponseMapper(Long id,
                                 String comment,
                                 LocalDateTime createdAt,
                                 LocalDateTime modifiedAt,
                                 Long userId,
                                 String email,
                                 String name,
                                 String userImg,
                                 List<LargeCommentResponseMapper> largeComments) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.userImg = userImg;
        this.largeComments = largeComments;
    }

    public static CommentResponseMapper of(final Comment comment) {
        final User user = comment.getUser();

        return CommentResponseMapper.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .largeComments(comment.getLargeComments().stream()
                .map(LargeCommentResponseMapper::of)
                        .sorted((c1,c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                        .collect(Collectors.toList()))
                .build();
    }
}