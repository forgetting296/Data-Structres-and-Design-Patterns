package com.shusaku.study.leetcode;


/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-01-10 15:03
 */
public class GetSumNoAdd {

    public static int getSum(int a, int b) {

        while(b != 0) {
            int temp = a ^ b;
            b = (a & b) << 1;
            a = temp;
        }

        return a;
    }

    public static void main(String[] args) {
        System.out.println(getSum(1,3));
    }

}
