package com.heythere.community.post.mapper;

import lombok.Getter;

@Getter
public class GoodOrBadPressedStatusResponseMapper {
    private final int like;
    private final int hate;
    private final Boolean isGood;
    private final Boolean isBad;

    public GoodOrBadPressedStatusResponseMapper(int like, int hate, Boolean isGood, Boolean isBad) {
        this.like = like;
        this.hate = hate;
        this.isGood = isGood;
        this.isBad = isBad;
    }
}
