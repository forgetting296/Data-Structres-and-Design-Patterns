package com.shusaku.study.patterns.factory;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-02-28 19:16
 */
public class FactoryTest {

    public static void main(String[] args) {

        VehicleFactory factory = new CarFactory();

        //factory.createVehicle("large");

        factory.orderVehicle("large","blue");

        VehicleFactory factory1 = new VehicleFactory() {
            @Override
            protected Vehicle createVehicle(String item) {
                if("small".equals(item)){
                    return new SmallTurck();
                } else if("large".equals(item)) {
                    return new LargeTurck();
                }
                return null;
            }
        };

        factory1.orderVehicle("small","blue");
    }

}

class SmallTurck extends Vehicle{
    public SmallTurck() {
        System.out.println("SmallTurck init!!!");
    }
}

class LargeTurck extends Vehicle{
    public LargeTurck() {
        System.out.println("LargeTurck init!!!");
    }
}
