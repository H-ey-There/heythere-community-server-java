package com.heythere.community.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
//@RequiredArgsConstructor
public class LargeCommentRegisterRequestDto {
    @NotNull
    private final Long requestUserId;
    @NotNull
    private final Long commentId;
    @NotNull
    private final String largeComment;

    public LargeCommentRegisterRequestDto(@NotNull Long requestUserId, @NotNull Long commentId, @NotNull String largeComment) {
        this.requestUserId = requestUserId;
        this.commentId = commentId;
        this.largeComment = largeComment;
    }
}
