package com.shusaku.study.leetcode;

import java.util.LinkedList;

/**
 * @program: Java8Test
 * @description: 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * @author: Shusaku
 * @create: 2020-01-10 09:44
 */
public class NumberReverse {

    public static void main(String[] args) {
        System.out.println(reverse(-120));
    }

    public static int reverse(int num) {
        if(num >= Math.pow(-2,31) && num <= (Math.pow(2,31) - 1)) {
            String s = Integer.valueOf(num).toString();
            LinkedList<Character> list = new LinkedList<Character>();
            LinkedList<Character> list2 = new LinkedList<Character>();
            for(int i = 0,j = s.length() - 1;j >= i;j --) {
                char c = s.charAt(j);
                if(c >= 48 && c <= 57) {
                    list.addLast(c);
                }else{
                    list2.addFirst(c);
                }
            }
            while(list.size() > 0 && list.getFirst().equals('0')){
                list.remove(list.getFirst());
            }
            list2.addAll(list);
            StringBuffer sb = new StringBuffer();
            for(int i = 0,j = list2.size();i < j;i ++) {
                sb.append(list2.get(i));
            }
            String s1 = sb.toString();
            if(!"".equals(s1)) {
                try {
                    int i = Integer.parseInt(s1);
                    return i;
                }catch (NumberFormatException e){
                    return 0;
                }
            }
        }
        return 0;
    }

    public int reverse2(int num) {
        int term;
        long b = 0L;
        while(num != 0){
            term = num % 10;
            num = num / 10;
            b = b * 10 + term;
        }
        if(b >= Integer.MIN_VALUE && b <= Integer.MAX_VALUE) {
            return (int) b;
        }
        return 0;
    }

}
