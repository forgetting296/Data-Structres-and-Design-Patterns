package com.shusaku.study.patterns.factory;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-02-28 19:07
 */
public abstract class VehicleFactory {
    protected abstract Vehicle createVehicle(String item);

    public Vehicle orderVehicle(String size, String color){
        Vehicle vehicle = createVehicle(size);
        vehicle.setColor(color);
        return vehicle;
    }
}

class Vehicle {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
