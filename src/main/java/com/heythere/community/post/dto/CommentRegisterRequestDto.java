package com.heythere.community.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class CommentRegisterRequestDto {
    @NotNull
    private final Long requestUserId;
    @NotNull
    private final Long postId;
    @NotNull
    private final String comment;
}
