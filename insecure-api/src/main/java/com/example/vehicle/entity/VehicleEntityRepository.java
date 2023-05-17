package com.example.vehicle.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findOneByIdentifier(String identifier);

    @Query("select max(v.identifier) from VehicleEntity v")
    String findMaxIdentifier();

}
