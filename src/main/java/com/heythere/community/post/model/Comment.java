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
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<LargeComment> largeComments = new ArrayList<>();

    @Builder
    public Comment(Long id, String comment, User user, Post post, List<LargeComment> largeComments) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.post = post;
        this.largeComments = largeComments;
    }
}
