package com.heythere.community.post.repository;

import com.heythere.community.post.model.Post;
import com.heythere.community.post.model.PostAndUser;
import com.heythere.community.post.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAndUserRepository extends JpaRepository<PostAndUser, Long> {
    boolean existsByUserAndPost(final User user, final Post post);
    PostAndUser findByUserAndPost(final User user, final Post post);

    int countAllByUserAndPostAndGoodStatusIsTrue(final User user, final Post post);
    int countAllByUserAndPostAndBadStatusIsTrue(final User user, final Post post);
}
