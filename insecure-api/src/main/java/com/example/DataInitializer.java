package com.example;

import com.example.community.entity.CommunityPostEntity;
import com.example.community.entity.CommunityPostEntityRepository;
import com.example.user.entity.UserEntity;
import com.example.user.entity.UserEntityRepository;
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
    private static final String WAYNE_ID = "978db26c-05ab-451b-8e61-f99e2de1c196";
    private static final String PARKER_ID = "6bb97946-233a-4fc5-be9e-df7ad954a268";
    private static final String KENT_ID = "f8e66c0e-5898-4fa8-9d99-fe0e2a445ad7";
    private static final String WAYNE_VEHICLE_ID = "1";
    private static final String PARKER_VEHICLE_ID = "2";
    private static final String KENT_VEHICLE_ID = "3";
    private final UserEntityRepository userEntityRepository;
    private final VehicleEntityRepository vehicleEntityRepository;
    private final CommunityPostEntityRepository communityPostEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserEntityRepository userEntityRepository, VehicleEntityRepository vehicleEntityRepository,
                           CommunityPostEntityRepository communityPostEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.vehicleEntityRepository = vehicleEntityRepository;
        this.communityPostEntityRepository = communityPostEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) {
        createUsers();
        createVehicles();
        createCommunityPosts();
    }

    private void createUsers() {
        List<UserEntity> users = Stream.of(
                new UserEntity(UUID.fromString(WAYNE_ID), "Bruce", "Wayne", "bruce.wayne@example.com", passwordEncoder.encode("wayne"), List.of("USER")),
                new UserEntity(UUID.fromString(PARKER_ID), "Peter", "Parker", "peter.parker@example.com", passwordEncoder.encode("parker"), List.of("USER")),
                new UserEntity(UUID.fromString(KENT_ID), "Clark", "Kent", "clark.kent@example.com", passwordEncoder.encode("kent"), List.of("USER", "ADMIN"))
        ).map(userEntityRepository::save).toList();
        LOG.info("Created {} number of users", users.size());
    }

    private void createVehicles() {
        List<VehicleEntity> vehicles = Stream.of(
                new VehicleEntity(WAYNE_VEHICLE_ID, UUID.randomUUID(), "330xi", Manufacturer.BMW, "943H+V4 L’Hospitalet de Llobregat, Spain", UUID.fromString(WAYNE_ID)),
                new VehicleEntity(PARKER_VEHICLE_ID, UUID.randomUUID(), "Model 3", Manufacturer.TESLA, "GVPV+6X London, UK", UUID.fromString(PARKER_ID)),
                new VehicleEntity(KENT_VEHICLE_ID, UUID.randomUUID(), "330xi", Manufacturer.BMW, "943H+V4 L’Hospitalet de Llobregat, Spanien", UUID.fromString(KENT_ID))
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
