package com.heythere.community.post.mapper;

import com.heythere.community.post.model.Picture;
import com.heythere.community.post.model.Post;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PictureResponseMapper {
    private final Long id;
    private final String url;

    public PictureResponseMapper(final Picture picture) {
        this.id = picture.getId();
        this.url = picture.getUrl();
    }

    public static List<PictureResponseMapper> of(final Post post) {
        final List<Picture> pictures = post.getPictures();

        return post.getPictures().stream()
                .map(p -> new PictureResponseMapper(p))
                .collect(Collectors.toList());
    }
}
