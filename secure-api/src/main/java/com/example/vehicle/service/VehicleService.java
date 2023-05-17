package com.example.vehicle.service;

import com.example.user.service.User;
import com.example.vehicle.entity.VehicleEntityRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;

    public VehicleService(VehicleEntityRepository vehicleEntityRepository) {
        this.vehicleEntityRepository = vehicleEntityRepository;
    }

    public Optional<Vehicle> findOneByIdentifierAndOwner(UUID identifier, UUID userIdentifier) {
        return vehicleEntityRepository.findOneByIdentifierAndOwner(identifier, userIdentifier).map(Vehicle::new);
    }

    public List<Vehicle> findAll() {
        return vehicleEntityRepository.findAll().stream().map(Vehicle::new).toList();
    }

    @Transactional
    public Vehicle register(Vehicle vehicle) {
        if (vehicle.getIdentifier() == null) {
            vehicle.setIdentifier(UUID.randomUUID());
        }
        return new Vehicle(vehicleEntityRepository.save(vehicle.toEntity()));
    }


}
