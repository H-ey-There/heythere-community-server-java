package com.heythere.community.post.api;

import com.heythere.community.post.mapper.CommentResponseMapper;
import com.heythere.community.post.dto.CommentRegisterRequestDto;
import com.heythere.community.post.dto.CommunityLookupRequestDto;
import com.heythere.community.post.dto.LargeCommentRegisterRequestDto;
import com.heythere.community.post.dto.PressGoodOrBadButtonRequestDto;
import com.heythere.community.post.mapper.GoodOrBadPressedStatusResponseMapper;
import com.heythere.community.post.mapper.PostResponseMapper;
import com.heythere.community.post.service.impl.CommunityServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class CommunityController {
    private final CommunityServiceImpl communityService;

    @GetMapping("v1/posts")
    public ResponseEntity<List<PostResponseMapper>> findAllPost(@RequestBody @Valid final CommunityLookupRequestDto payload,
                                                                final Pageable pageable) {
        return ResponseEntity.ok(communityService.findAllPost(payload, pageable));
    }

    // gonna unused...
    @GetMapping("v1/post/{id}")
    public ResponseEntity<PostResponseMapper> findPostById(@PathVariable("id") final Long id,
                                                           @RequestBody @Valid final CommunityLookupRequestDto payload) {
        return ResponseEntity.ok(communityService.findPostById(id, payload));
    }

    @GetMapping("v1/comments")
    public ResponseEntity<List<CommentResponseMapper>> findAllComment(@RequestParam("postId") final Long postId) {
        return ResponseEntity.ok(communityService.findAllComments(postId));
    }

    @PostMapping(value = "v1/registration/post")
    public ResponseEntity<Long> registerPost(@RequestParam("requestUserId") final Long requestUserId,
                                             @RequestParam("title") final String title,
                                             @RequestParam("content") final String content,
                                             @RequestParam(value = "img", required = false) List<MultipartFile> img) throws IOException {
        final Long savedPostId = img != null
                ? communityService.registerPostWithFiles(requestUserId, title, content, img)
                : communityService.registerPostWithoutFiles(requestUserId, title, content);

        return new ResponseEntity(savedPostId, HttpStatus.CREATED);
    }

    @PostMapping("v1/registration/comment")
    public ResponseEntity<Long> registerComment(@RequestBody @Valid final CommentRegisterRequestDto payload) {
        return new ResponseEntity(communityService.registerComment(payload), HttpStatus.CREATED);
    }

    @PostMapping("v1/registration/largeComment")
    public ResponseEntity<Long> registerLargeComment(@RequestBody @Valid final LargeCommentRegisterRequestDto payload) {
        return new ResponseEntity(communityService.registerLargeComment(payload), HttpStatus.CREATED);
    }


    @PostMapping("v1/select/goodOrBad")
    public GoodOrBadPressedStatusResponseMapper pressedLikeOrDislikeButtonOnPost(@RequestBody @Valid final PressGoodOrBadButtonRequestDto payload) {
        return communityService.pressedLikeOrDislikeButtonOnPost(payload);
    }

    @DeleteMapping("v1/post/{postId}")
    public void deletePost(@PathVariable("postId") final Long postId) {
        communityService.deletePost(postId);
    }

    @DeleteMapping("v1/comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") final Long commentId) {
        communityService.deleteComment(commentId);
    }

    @DeleteMapping("v1/largeComment/{largeCommentId}")
    public void deleteLargeComment(@PathVariable("largeCommentId") final Long largeCommentId) {
        communityService.deleteLargeComment(largeCommentId);
    }
}
