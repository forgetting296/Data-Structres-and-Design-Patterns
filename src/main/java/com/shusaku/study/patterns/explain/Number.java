package com.shusaku.study.patterns.explain;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 10:27
 */
public class Number implements Expression {

    private float number;

    public Number(float number) {
        this.number = number;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    @Override
    public float interpret() {
        return number;
    }
}
