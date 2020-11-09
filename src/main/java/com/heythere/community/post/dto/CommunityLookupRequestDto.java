package com.heythere.community.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

@Getter
@RequiredArgsConstructor
public class CommunityLookupRequestDto {
    @NotNull
    private final Long requestUserId;
    @NotNull
    private final Long communityOwnerId;


    //@ConstructorProperties({"requestUserId", "communityOwnerId"})
//    public CommunityLookupRequestDto(@NotNull Long requestUserId, @NotNull Long communityOwnerId) {
//        this.requestUserId = requestUserId;
//        this.communityOwnerId = communityOwnerId;
//    }
//
//    public Long getRequestUserId() {
//        return requestUserId;
//    }
//
//    public Long getCommunityOwnerId() {
//        return communityOwnerId;
//    }
}
