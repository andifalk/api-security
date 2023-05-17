package com.example.vehicle.api;

import com.example.user.service.User;
import com.example.vehicle.entity.Manufacturer;
import com.example.vehicle.service.Vehicle;
import com.example.vehicle.service.VehicleService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VehicleGraphQLApi {

    private final VehicleService vehicleService;

    public VehicleGraphQLApi(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @QueryMapping
    public Optional<Vehicle> vehicle(@AuthenticationPrincipal User user, @Argument UUID identifier) {
        return vehicleService.findOneByIdentifierAndOwner(identifier, user.getIdentifier());
    }

    @QueryMapping
    public List<Vehicle> vehicles() {
        return vehicleService.findAll();
    }

    @MutationMapping
    public Vehicle registerVehicle(@AuthenticationPrincipal User user,
        @Argument UUID vin, @Argument  String model,
        @Argument  Manufacturer manufacturer, @Argument  String location, @Argument  UUID owner) {

        if (user.getIdentifier().equals(owner)) {
            Vehicle vehicle = new Vehicle(null, vin, model, manufacturer, location, owner);
            return vehicleService.register(vehicle);
        } else {
            throw new AccessDeniedException("You are not authorized to register another user's vehicle");
        }
    }

}
