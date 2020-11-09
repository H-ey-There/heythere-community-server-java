package com.heythere.community.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class PressGoodOrBadButtonRequestDto {
    @NotNull
    private final Long requestUserId;
    @NotNull
    private final Long postId;
    @NotNull
    private final Boolean isGood;
    @NotNull
    private final Boolean isBad;

    public Boolean getIsGood() {
        return isGood;
    }

    public Boolean getIsBad() {
        return isBad;
    }
}
