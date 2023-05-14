package com.example.vehicle.service;

import com.example.vehicle.entity.Manufacturer;
import com.example.vehicle.entity.VehicleEntity;

import java.io.Serializable;
import java.util.UUID;

public class Vehicle implements Serializable {

    private String identifier;
    private UUID vin;
    private String model;
    private Manufacturer manufacturer;
    private String location;
    private UUID owner;

    public Vehicle() {
    }

    public Vehicle(VehicleEntity vehicleEntity) {
        this.identifier = vehicleEntity.getIdentifier();
        this.vin = vehicleEntity.getVin();
        this.model = vehicleEntity.getModel();
        this.manufacturer = vehicleEntity.getManufacturer();
        this.location = vehicleEntity.getLocation();
        this.owner = vehicleEntity.getOwner();
    }

    public Vehicle(String identifier, UUID vin, String model, Manufacturer manufacturer, String location, UUID owner) {
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
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

    public VehicleEntity toEntity() {
        return new VehicleEntity(this.identifier, this.vin,
                this.model, this.manufacturer, this.location, this.owner);
    }
}
