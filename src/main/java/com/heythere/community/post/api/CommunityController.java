package com.heythere.community.post.api;

import com.heythere.community.post.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class CommunityController {
    private final CommunityService communityService;


}
