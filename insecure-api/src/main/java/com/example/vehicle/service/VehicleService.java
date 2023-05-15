package com.example.vehicle.service;

import com.example.vehicle.entity.VehicleEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;

    public VehicleService(VehicleEntityRepository vehicleEntityRepository) {
        this.vehicleEntityRepository = vehicleEntityRepository;
    }

    public Optional<Vehicle> findOneByIdentifier(String identifier) {
        return vehicleEntityRepository.findOneByIdentifier(identifier).map(Vehicle::new);
    }

    public List<Vehicle> findAll() {
        return vehicleEntityRepository.findAll().stream().map(Vehicle::new).toList();
    }

    @Transactional
    public Vehicle register(Vehicle vehicle) {
        return new Vehicle(vehicleEntityRepository.save(vehicle.toEntity()));
    }


}
