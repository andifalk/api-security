package com.example.vehicle.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findOneByIdentifier(String identifier);

}
