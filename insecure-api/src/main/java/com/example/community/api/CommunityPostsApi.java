package com.example.community.api;

import com.example.community.service.CommunityPost;
import com.example.community.service.CommunityPostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/community")
public class CommunityPostsApi {

    private final CommunityPostsService communityPostsService;

    public CommunityPostsApi(CommunityPostsService communityPostsService) {
        this.communityPostsService = communityPostsService;
    }

    @GetMapping("/{postIdentifier}")
    public ResponseEntity<CommunityPost> findOneByIdentifier(@PathVariable("postIdentifier") UUID identifier) {
        return communityPostsService
                .findOneByIdentifier(identifier)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CommunityPost> findAll() {
        return communityPostsService.findAll();
    }
}
