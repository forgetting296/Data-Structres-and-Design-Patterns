package com.shusaku.study.patterns.memorandum;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 14:45
 */
public class CarCaretaker {

    public static void main(String[] args) {
        new CarCaretaker().runMechainTest();
    }

    public void runMechainTest() {
        CarOriginator.Memento memento = new CarOriginator.Memento("");

        CarOriginator originator = new CarOriginator();
        originator.setState("state1");
        originator.setState("state2");

        memento = originator.saveState();

        originator.setState("state3");
        originator.restoreState(memento);

        System.out.println("finally state: " + originator.getState());
    }

}
