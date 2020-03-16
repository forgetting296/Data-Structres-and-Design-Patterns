package com.shusaku.study.patterns.explain;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 10:29
 */
public class Plus implements Expression {

    Expression left;
    Expression right;

    public Plus(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public float interpret() {
        return left.interpret() + right.interpret();
    }
}
