package com.example.vehicle.entity;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;

@Entity
public class VehicleEntity extends AbstractPersistable<Long> {

    private UUID identifier;
    private UUID vin;
    private String model;
    private Manufacturer manufacturer;
    private String location;
    private UUID owner;

    public VehicleEntity() {
    }

    public VehicleEntity(UUID identifier, UUID vin, String model, Manufacturer manufacturer, String location, UUID owner) {
        this.identifier = identifier;
        this.vin = vin;
        this.model = model;
        this.manufacturer = manufacturer;
        this.location = location;
        this.owner = owner;
    }

    public UUID getVin() {
        return vin;
    }

    public void setVin(UUID vin) {
        this.vin = vin;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "VehicleEntity{" +
                "identifier='" + identifier + '\'' +
                ", vin='" + vin + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer=" + manufacturer +
                ", location=" + location +
                ", owner=" + owner +
                '}';
    }
}
