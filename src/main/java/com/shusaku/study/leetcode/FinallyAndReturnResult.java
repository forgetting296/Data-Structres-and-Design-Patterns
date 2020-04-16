package com.shusaku.study.leetcode;

/**
 * @program: Data Structures And Design Patterns
 * @description:
 * @author: Shusaku
 * @create: 2020-04-16 10:37
 */
public class FinallyAndReturnResult {

    private static int value = 0;

    private static int inc() {
        return value ++;
    }

    private static int desc() {
        return value --;
    }

    private static int test1() {
        try {
            return inc();
        } finally {
            return desc();
        }
    }

    private static int test2() {
        try {
            return value;
        } finally {
            value ++;
        }
    }

    public static void main(String[] args) {
        //单独运行下面这组返回　0  1
        System.out.println(test2());
        System.out.println(value);

        //单独运行下面这组返回 1  0
        //System.out.println(test1());
        //System.out.println(value);
    }

}
