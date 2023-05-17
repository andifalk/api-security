package com.example.vehicle.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findOneByIdentifierAndOwner(UUID identifier, UUID owner);
}
