package com.shusaku.study.leetcode;

/**
 * @program: Data Structures And Design Patterns
 * @description:
 * @author: Shusaku
 * @create: 2020-04-19 14:04
 */
public class HuaWeiTest {

    public static void main(String[] args) {
        System.out.println(f1(10));
        System.out.println(f2(15,2));
    }

    private static int f1(int n) {
        if(n == 1) {
            return 0;
        }
        if(n == 2) {
            return 1;
        }
        return f1(n-2) + 1;
    }

    private static String f2(int num, int n) {

        return Integer.toString(num,n);
    }

}
