package com.heythere.community.post.repository;

import com.heythere.community.post.model.Post;
import com.heythere.community.post.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByCreatedAtDesc(final User user, final Pageable pageable);
}
