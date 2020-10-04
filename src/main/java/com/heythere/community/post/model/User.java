package com.heythere.community.post.model;

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
}
