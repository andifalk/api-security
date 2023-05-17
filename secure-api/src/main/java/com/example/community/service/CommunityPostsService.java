package com.example.community.service;

import com.example.community.entity.CommunityPostEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CommunityPostsService {

    private final CommunityPostEntityRepository communityPostEntityRepository;

    public CommunityPostsService(CommunityPostEntityRepository communityPostEntityRepository) {
        this.communityPostEntityRepository = communityPostEntityRepository;
    }

    public Optional<CommunityPost> findOneByIdentifier(UUID identifier) {
        return communityPostEntityRepository.findOneByIdentifier(identifier).map(CommunityPost::new);
    }

    public List<CommunityPost> findAll() {
        return communityPostEntityRepository.findAll().stream().map(CommunityPost::new).toList();
    }

    @Transactional
    public CommunityPost save(CommunityPost communityPost) {
        return new CommunityPost(communityPostEntityRepository.save(communityPost.toEntity()));
    }
}
