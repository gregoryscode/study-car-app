package com.example.carapp;

import java.util.UUID;

/**
 * Vehicle.java - Classe responsável pelo veículo
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class Vehicle {

    private String id, plate, manufacturer, model, color, year;

    public Vehicle() {

    }

    public Vehicle(String plate, String manufacturer, String model, String color, String year) {
        this.id = UUID.randomUUID().toString();
        this.plate = plate;
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
