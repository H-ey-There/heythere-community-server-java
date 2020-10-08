package com.heythere.community.post.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LargeCommentRegisterRequestDto {
    private final Long requestUserId;
    private final Long commentId;
    private final String largeComment;
}
