package com.shusaku.study.patterns.explain;

import java.util.Stack;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 10:35
 */
public class Evaluator {

    public float evaluator(String expression) {

        Stack<Expression> stack = new Stack<Expression>();
        float result = 0;
        for(String token : expression.split(" ")) {
            if(isOperator(token)) {
                Expression exp = null;
                if("+".equals(token)) {
                    exp = stack.push(new Plus(stack.pop(),stack.pop()));
                }else if ("-".equals(token)) {
                    exp = stack.push(new Minus(stack.pop(),stack.pop()));
                }
                if(null != exp) {
                    result = exp.interpret();
                    stack.push(new Number(result));
                }
            }

            if(isNumber(token)) {
                stack.push(new Number(Float.parseFloat(token)));
            }
        }

        return result;
    }

    private boolean isNumber(String token) {

        try {
            Float.parseFloat(token);
            return Boolean.TRUE;
        } catch (NumberFormatException e) {
            return Boolean.FALSE;
        }
    }

    private boolean isOperator(String token) {

        if("+".equals(token) || "-".equals(token)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        float result = evaluator.evaluator("1 2 + 3 -");

        System.out.println("result: " + result);
    }


}
