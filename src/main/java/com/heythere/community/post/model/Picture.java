package com.heythere.community.post.model;

import com.heythere.community.post.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Picture extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Picture(Long id, String url, Post post) {
        this.id = id;
        this.url = url;
        this.post = post;
    }
}
