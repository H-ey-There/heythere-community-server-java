package com.heythere.community.post.repository;

import com.heythere.community.post.model.Comment;
import com.heythere.community.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByCreatedAtDesc(final Post post);
}
