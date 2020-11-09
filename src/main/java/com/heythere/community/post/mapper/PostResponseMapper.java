package com.heythere.community.post.mapper;

import com.heythere.community.post.model.Post;
import com.heythere.community.post.model.PostAndUser;
import com.heythere.community.post.model.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class PostResponseMapper {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long userId;
    private final String email;
    private final String name;
    private final String userImg;
    private final int good;
    private final int bad;
    private final Boolean pressedGood;
    private final Boolean pressedBad;
    private final List<PictureResponseMapper> imgUrls;
    private final List<CommentResponseMapper> comments;
    
    @Builder
    public PostResponseMapper(Long id,
                              String title,
                              String content,
                              LocalDateTime createdAt,
                              LocalDateTime modifiedAt,
                              Long userId,
                              String email,
                              String name,
                              String userImg,
                              int good,
                              int bad,
                              Boolean pressedGood,
                              Boolean pressedBad,
                              List<PictureResponseMapper> imgUrls,
                              List<CommentResponseMapper> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.userImg = userImg;
        this.good = good;
        this.bad = bad;
        this.pressedGood = pressedGood;
        this.pressedBad = pressedBad;
        this.imgUrls = imgUrls;
        this.comments = comments;
    }

    public static PostResponseMapper of(final Post post, final User requestUser) {
        final User communityOwner = post.getUser();

        final Optional<PostAndUser> status = requestUser.getStatus().stream()
                .filter(s -> s.equals(post))
                .findFirst();

        return PostResponseMapper.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .userId(communityOwner.getId())
                .email(communityOwner.getEmail())
                .name(communityOwner.getName())
                .userImg(communityOwner.getImg())
                .good(post.getGood())
                .bad(post.getBad())
                .pressedGood(status != null ? status.get().getGoodStatus() : false)
                .pressedBad(status != null ? status.get().getBadStatus() : false)
                .imgUrls(PictureResponseMapper.of(post))
                .comments(post.getComments().stream()
                        .map(CommentResponseMapper::of)
                        .sorted((c1,c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                        .collect(Collectors.toList()))
                .build();
    }
}
