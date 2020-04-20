package com.shusaku.study.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断一个数是不是回文数
 */
public class IsPalindrome {

    public static void main(String[] args){
        System.out.println(notChangeProcess(12321));
        System.out.println(toStringProcess(12321));
        System.out.println(reverse(1534236469));
        int[] nums = {0,0,0};
        System.out.println(threeSum(nums));
    }

    public static boolean toStringProcess(int x){
        String tmp = new StringBuilder(String.valueOf(x)).reverse().toString();
        return tmp.equals(String.valueOf(x));
    }

    public static boolean notChangeProcess(int x){
        if(x < 0)
            return false;
        int nums = 0;
        int temp = x;
        int left,right;
        while(temp != 0){
            temp /= 10;
            nums ++;
        }
        left = nums - 1;
        right = 0;
        while(getDigit(x,left --) != getDigit(x,right ++)){
            return false;
        }
        return true;
    }

    public static int getDigit(int x,int i){
        x = x / (int)Math.pow(10,i);
        return x % 10;
    }

    private boolean isPalindrome4(int x) {
        if(x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int rev = 0;
        //这个方式的关键就是　如何判断已经达到了中位
        while(x > rev) {
            rev = rev * 10 + x %10;
            x /= 10;
        }
        return x == rev || x == rev/10;
    }

    public static int reverse(int x) {
        if(x >= 0){
            try {
                return Integer.valueOf(new StringBuilder(String.valueOf(x)).reverse().toString());
            }catch (Exception e){
                return 0;
            }
        }else{
            try {
                return Integer.valueOf("-" + new StringBuilder(String.valueOf(x).substring(1)).reverse().toString());
            }catch (Exception e){
                return 0;
            }
        }
    }

    public static int myAtoi(String str) {
        str = str.trim();
        char[] chars = str.toCharArray();
        for(int i = 0;i < chars.length;i ++){

        }
        return 0;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<Integer> ints;
        List<List<Integer>> lists = new ArrayList<>();
        for(int i = 0,j = nums.length - 2;i < j;i ++){
            for(int a = 1,b = nums.length - 1;a < b;a ++){
                for(int start = 2;start < nums.length && ((nums[i] + nums[a] + nums[start]) == 0); ){
                    ints = new ArrayList<>();
                    ints.add(nums[i]);
                    ints.add(nums[a]);
                    ints.add(nums[start]);
                    lists.add(ints);
                    start ++;
                }
            }
        }
        return lists;
    }
}
