package com.example.vehicle.api;

import com.example.vehicle.service.Vehicle;
import com.example.vehicle.service.VehicleService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class VehicleGraphQLApi {

    private final VehicleService vehicleService;

    public VehicleGraphQLApi(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @QueryMapping
    public Optional<Vehicle> vehicle(@Argument String identifier) {
        return vehicleService.findOneByIdentifier(identifier);
    }

    @QueryMapping
    public List<Vehicle> vehicles() {
        return vehicleService.findAll();
    }

}
