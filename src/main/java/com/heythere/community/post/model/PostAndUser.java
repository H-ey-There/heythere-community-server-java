package com.heythere.community.post.model;

import com.heythere.community.post.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(
        name = "post_and_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "post_id"}
                )
        }
)
@Entity
public class PostAndUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Boolean goodStatus;
    private Boolean badStatus;

    @PrePersist
    public void prePersist() {
        if (goodStatus == null && badStatus == null) {
            goodStatus = false;
            badStatus = false;
        }
    }

    @Builder
    public PostAndUser(Long id,
                       User user,
                       Post post,
                       Boolean goodStatus,
                       Boolean badStatus) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.goodStatus = goodStatus;
        this.badStatus = badStatus;
    }

    public PostAndUser updateStatus(final Boolean goodStatus, final Boolean badStatus) {
        this.goodStatus = goodStatus;
        this.badStatus = badStatus;
        return this;
    }

    public PostAndUser addStatusToUserAndPost(final User user, final Post post) {
        user.getStatus().add(this);
        post.getStatus().add(this);
        return this;
    }
}
