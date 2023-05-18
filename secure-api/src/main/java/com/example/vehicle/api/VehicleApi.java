package com.example.vehicle.api;

import com.example.user.service.User;
import com.example.vehicle.service.Vehicle;
import com.example.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleApi {

    private final List<URI> validPartnerUrls;
    private final VehicleService vehicleService;

    public VehicleApi(@Value("${vehicle.workshop.partner-urls:}") List<URI> validPartnerUrls, VehicleService vehicleService) {
        this.validPartnerUrls = validPartnerUrls;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{vehicleIdentifier}")
    public ResponseEntity<Vehicle> findVehicleByIdentifier(@AuthenticationPrincipal User user, @PathVariable("vehicleIdentifier") UUID identifier) {
        return vehicleService.findOneByIdentifierAndOwner(identifier, user.getIdentifier())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Vehicle> registerVehicle(@AuthenticationPrincipal User user, @RequestBody Vehicle vehicle, UriComponentsBuilder uriBuilder) {
        if (user.getIdentifier().equals(vehicle.getOwner())) {
            Vehicle registeredVehicle = vehicleService.register(vehicle, user.getIdentifier());
            return ResponseEntity.created(
                    uriBuilder.path("/vehicles/{id}").buildAndExpand(registeredVehicle.getIdentifier())
                            .toUri()).body(registeredVehicle);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
        }
    }

    @PostMapping("/{vehicleIdentifier}/contact-workshop")
    public ResponseEntity<String> contactWorkshop(
            @AuthenticationPrincipal User user,
            @PathVariable("vehicleIdentifier") UUID identifier,
            @RequestBody WorkshopNotification notification) {

        if (validPartnerUrls.contains(notification.getPartnerApi())) {

            Optional<Vehicle> optionalVehicle = vehicleService.findOneByIdentifierAndOwner(identifier, user.getIdentifier());
            if (optionalVehicle.isPresent()) {
                Vehicle vehicle = optionalVehicle.get();
                RestTemplate restTemplate = new RestTemplate();
                return ResponseEntity.ok(restTemplate.getForObject(
                        notification.getPartnerApi() + "?vin=" + vehicle.getVin() + "&problem=" + notification.getProblem(),
                        String.class));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid partner URL");
        }
    }
}
