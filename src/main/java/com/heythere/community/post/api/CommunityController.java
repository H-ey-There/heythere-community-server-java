package com.heythere.community.post.api;

import com.heythere.community.post.dto.request.CommentRegisterRequestDto;
import com.heythere.community.post.dto.request.LargeCommentRegisterRequestDto;
import com.heythere.community.post.mapper.PostResponseMapper;
import com.heythere.community.post.service.impl.CommunityServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<PostResponseMapper>> findAllPost(@RequestParam("id") final Long id) {
        return ResponseEntity.ok(communityService.findAllPost(id));
    }

    @GetMapping("v1/post/{id}")
    public ResponseEntity<PostResponseMapper> findPostById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(communityService.findPostById(id));
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
        return ResponseEntity.ok(communityService.registerComment(payload));
    }

    @PostMapping("v1/registration/largeCommnet")
    public ResponseEntity<Long> registerLargeComment(@RequestBody @Valid final LargeCommentRegisterRequestDto payload) {
        return ResponseEntity.ok(communityService.registerLargeComment(payload));
    }
}
