package com.heythere.community.post.repository;

import com.heythere.community.post.model.LargeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LargeCommentRepository extends JpaRepository<LargeComment, Long> {
}
