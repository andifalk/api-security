package com.example.community.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommunityPostEntityRepository extends JpaRepository<CommunityPostEntity, Long> {

    Optional<CommunityPostEntity> findOneByIdentifier(UUID identifier);

}
