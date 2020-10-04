package com.heythere.community.post.repository;

import com.heythere.community.post.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
