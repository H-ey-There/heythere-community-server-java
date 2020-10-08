package com.heythere.community.post.model;

import com.heythere.community.post.shared.CurrentUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String name;
    private String img;

    @Builder
    public User(Long id, String email, String name, String img) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
    }

    public User updateUserEntityAndReturn(final CurrentUser currentUser) {
        if (!currentUser.getEmail().equals(email))
            this.email = currentUser.getEmail();
        if(!currentUser.getName().equals(name))
            this.name = currentUser.getName();
        if (currentUser.getImg() != null && !currentUser.getImg().equals(img))
            this.img = currentUser.getImg();
        return this;
    }
}
