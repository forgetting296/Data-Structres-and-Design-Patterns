package com.shusaku.study.patterns.factory;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-02-28 19:12
 */
public class CarFactory extends VehicleFactory {
    @Override
    protected Vehicle createVehicle(String item) {
        if("small".equals(item)){
            return new SportCar();
        } else if("large".equals(item)) {
            return new SedanCar();
        }
        return null;
    }
}

class SportCar extends Vehicle{
    public SportCar() {
        System.out.println("SportCar init!!!");
    }
}

class SedanCar extends Vehicle{
    public SedanCar() {
        System.out.println("SedanCar init!!!");
    }
}
