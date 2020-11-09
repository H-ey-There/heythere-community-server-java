package com.heythere.community.post.model;

import com.heythere.community.post.message.domain.UserEventDto;
import com.heythere.community.post.message.domain.UserMessageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Unique
    private String email;
    private String name;
    private String img;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<PostAndUser> status = new ArrayList<>();

    @Builder
    public User(Long id, String email, String name, String img, List<PostAndUser> status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
        this.status = status;
    }


    public User update(final UserEventDto userEvent) {
        final UserMessageDto message = userEvent.getUserMessageDto();

        this.email = message.getEmail();
        this.name = message.getName();
        this.img = message.getImg();

        return this;
    }
}
