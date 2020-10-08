package com.heythere.community.post.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRegisterRequestDto {
    private final Long requestUserId;
    private final Long postId;
    private final String comment;
}
