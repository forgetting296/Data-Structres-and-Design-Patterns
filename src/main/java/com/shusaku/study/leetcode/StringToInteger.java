package com.shusaku.study.leetcode;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-01-14 10:17
 */
public class StringToInteger {

    public static void main(String[] args) {
        char c = '6';
        System.out.println(c + 10 - '0');
    }

    public static int myAtoi(String str) {

        if(str == null || str.length() == 0)
            return 0;

        //第一步  去掉两侧空字符串
        str = str.trim();

        //第二步  判断首位字符是否为 + -
        int start = 0;
        int sign = 1;
        long res = 0L;
        char firstChar = str.charAt(0);

        if(firstChar == '+'){
            start ++;
            sign = 1;
        }

        if(firstChar == '-'){
            start ++;
            sign = -1;
        }

        for(int i = start;i < str.length();i ++) {
            if(!Character.isDigit(str.charAt(i))){
                return (int) res * sign;
            }
            res = res * 10 + str.charAt(i) - '0';

            if(sign > 0 && res > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            if(sign < 0 && res < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }

        }
        return (int)res * sign;
    }

}
