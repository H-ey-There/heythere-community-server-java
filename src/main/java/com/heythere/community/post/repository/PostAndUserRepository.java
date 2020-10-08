package com.heythere.community.post.repository;

import com.heythere.community.post.model.PostAndUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAndUserRepository extends JpaRepository<PostAndUser, Long> {
}
