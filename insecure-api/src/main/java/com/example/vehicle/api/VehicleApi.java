package com.example.vehicle.api;

import com.example.vehicle.service.Vehicle;
import com.example.vehicle.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleApi {

    private final VehicleService vehicleService;

    public VehicleApi(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{vehicleIdentifier}")
    public ResponseEntity<Vehicle> findVehicleByIdentifier(@PathVariable("vehicleIdentifier") String identifier) {
        return vehicleService.findOneByIdentifier(identifier)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.findAll();
    }

    @PostMapping("/{vehicleIdentifier}/contact-workshop")
    public ResponseEntity<String> contactWorkshop(
            @PathVariable("vehicleIdentifier") String identifier,
            @RequestBody WorkshopNotification notification) {

        Optional<Vehicle> optionalVehicle = vehicleService.findOneByIdentifier(identifier);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            RestTemplate restTemplate = new RestTemplate();
            return ResponseEntity.ok(restTemplate.getForObject(
                    notification.getPartnerApi() + "?vin=" + vehicle.getVin() + "&problem=" + notification.getProblem(),
                    String.class));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
