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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    @Column(nullable = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(Long id,
                String title,
                String content,
                List<Picture> pictures,
                User user,
                List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.user = user;
        this.comments = comments;
    }
}
