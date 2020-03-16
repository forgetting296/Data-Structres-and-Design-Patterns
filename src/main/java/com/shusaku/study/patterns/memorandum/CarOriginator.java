package com.shusaku.study.patterns.memorandum;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 14:38
 */
public class CarOriginator {

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class Memento {
        private final String state;

        public String getState() {
            return state;
        }

        public Memento(String state) {
            this.state = state;
        }
    }

    public Memento saveState() {
        return new Memento(this.state);
    }

    public void restoreState(Memento memento) {

        this.state = memento.getState();
    }
}
