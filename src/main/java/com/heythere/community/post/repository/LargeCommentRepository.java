package com.heythere.community.post.repository;

import com.heythere.community.post.model.Comment;
import com.heythere.community.post.model.LargeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LargeCommentRepository extends JpaRepository<LargeComment, Long> {
    List<LargeComment> findAllByCommentOrderByCreatedAtDesc(final Comment comment);
}
