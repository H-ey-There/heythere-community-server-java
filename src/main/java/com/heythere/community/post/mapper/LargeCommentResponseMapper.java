package com.heythere.community.post.mapper;

import com.heythere.community.post.model.LargeComment;
import com.heythere.community.post.model.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LargeCommentResponseMapper {
    private final Long id;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long userId;
    private final String email;
    private final String name;
    private final String userImg;

    @Builder
    public LargeCommentResponseMapper(Long id,
                                      String comment,
                                      LocalDateTime createdAt,
                                      LocalDateTime modifiedAt,
                                      Long userId,
                                      String email,
                                      String name,
                                      String userImg) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.userImg = userImg;
    }

    public static LargeCommentResponseMapper of(final LargeComment largeComment) {
        final User user = largeComment.getUser();

        return LargeCommentResponseMapper.builder()
                .id(largeComment.getId())
                .comment(largeComment.getLargeComment())
                .createdAt(largeComment.getCreatedAt())
                .modifiedAt(largeComment.getModifiedAt())
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .userImg(user.getImg())
                .build();
    }
}
