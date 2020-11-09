package com.heythere.community.post.model;

import com.heythere.community.post.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Picture> pictures = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    private Integer good;
    private Integer bad;

    @OneToMany(mappedBy = "post")
    private List<PostAndUser> status = new ArrayList<>();

    @PrePersist
    public void perPersist() {
        good = good == null ? 0 : good;
        bad = bad == null ? 0 : bad;
    }

    @Builder
    public Post(Long id,
                String title,
                String content,
                List<Picture> pictures,
                User user, List<Comment> comments,
                Integer good,
                Integer bad,
                List<PostAndUser> status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.user = user;
        this.comments = comments;
        this.good = good;
        this.bad = bad;
        this.status = status;
    }

    public void updateGoodOrBadCount(final int good, final int bad) {
        this.good = good;
        this.bad = bad;
    }
}
