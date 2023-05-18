package com.example;

import com.example.community.entity.CommunityPostEntity;
import com.example.community.entity.CommunityPostEntityRepository;
import com.example.vehicle.entity.Manufacturer;
import com.example.vehicle.entity.VehicleEntity;
import com.example.vehicle.entity.VehicleEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);
    private static final String WAYNE_ID = "c52bf7db-db55-4f89-ac53-82b40e8c57c2";
    private static final String KENT_ID = "52a14872-ba6b-488f-aa4d-453b11f9ddce";
    private static final String PARKER_ID = "3a73ef49-c671-4d66-b6f2-7725ccde5c2b";
    private static final String WAYNE_VEHICLE_ID = "ed6d9c4a-ac3f-4060-9d7c-9cdbfe82b0ea";
    private static final String PARKER_VEHICLE_ID = "2d20c657-c3d5-434e-ac5c-dcac711c73aa";
    private static final String KENT_VEHICLE_ID = "957e7f95-5876-435e-8aa6-936e566fa2c3";
    private final VehicleEntityRepository vehicleEntityRepository;
    private final CommunityPostEntityRepository communityPostEntityRepository;

    public DataInitializer(VehicleEntityRepository vehicleEntityRepository,
                           CommunityPostEntityRepository communityPostEntityRepository) {
        this.vehicleEntityRepository = vehicleEntityRepository;
        this.communityPostEntityRepository = communityPostEntityRepository;
    }

    @Transactional
    @Override
    public void run(String... args) {
        createVehicles();
        createCommunityPosts();
    }

    private void createVehicles() {
        List<VehicleEntity> vehicles = Stream.of(
                new VehicleEntity(UUID.fromString(WAYNE_VEHICLE_ID), UUID.randomUUID(), "330xi", Manufacturer.BMW, "943H+V4 L’Hospitalet de Llobregat, Spain", UUID.fromString(WAYNE_ID)),
                new VehicleEntity(UUID.fromString(PARKER_VEHICLE_ID), UUID.randomUUID(), "Model 3", Manufacturer.TESLA, "GVPV+6X London, UK", UUID.fromString(PARKER_ID)),
                new VehicleEntity(UUID.fromString(KENT_VEHICLE_ID), UUID.randomUUID(), "330xi", Manufacturer.BMW, "943H+V4 L’Hospitalet de Llobregat, Spanien", UUID.fromString(KENT_ID))
        ).map(vehicleEntityRepository::save).toList();
        LOG.info("Created {} number of vehicles", vehicles.size());
    }

    private void createCommunityPosts() {
        List<CommunityPostEntity> posts = Stream.of(
                new CommunityPostEntity(UUID.randomUUID(), "New car", "Yeesss, I have finally my new car", UUID.fromString(WAYNE_ID), "bruce.wayne@example.com", WAYNE_VEHICLE_ID),
                new CommunityPostEntity(UUID.randomUUID(), "Car problem", "My car suddenly stops from time to time. Please any help", UUID.fromString(KENT_ID), "clark.kent@example.com", KENT_VEHICLE_ID),
                new CommunityPostEntity(UUID.randomUUID(), "Hints for new car", "My employer will give me a new car. Which model would you recommend", UUID.fromString(PARKER_ID), "peter.parker@example.com", PARKER_VEHICLE_ID),
                new CommunityPostEntity(UUID.randomUUID(), "Software Update", "What are your experiences with latest software update?", UUID.fromString(PARKER_ID), "peter.parker@example.com", PARKER_VEHICLE_ID),
                new CommunityPostEntity(UUID.randomUUID(), "Car range", "What is your maximum range with one tank filling so far?", UUID.fromString(WAYNE_ID), "bruce.wayne@example.com", WAYNE_VEHICLE_ID)
        ).map(communityPostEntityRepository::save).toList();
        LOG.info("Created {} number of community posts", posts.size());
    }
}
