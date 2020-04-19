package com.shusaku.study.leetcode;

/**
 * @author liuzi
 */
public class PublicSubstring {

    public static String maxSubstring(String strOne,String strTwo){

        if(strOne == null || strTwo == null){
            return null;
        }
        if("".equals(strOne) || "".equals(strTwo)){
            return null;
        }

        String max = "";
        String min = "";
        String current = "";

        if(strOne.length() < strTwo.length()){
            max = strTwo;
            min = strOne;
        }else{
            max = strOne;
            min = strTwo;
        }

        for(int i = 0;i < min.length();i ++){
            for(int begin = 0,end = min.length() - i;end <= min.length();begin ++, end ++){
                current = min.substring(begin,end);
                if(max.contains(current)){
                    return current;
                }
            }
        }

        return null;
    }

    private String test(String one, String two) {
        if(one == null || two == null){
            return null;
        }

        if("".equals(one) || "".equals(two)) {
            return null;
        }

        String max = "";
        String min = "";
        String current = "";

        if(one.length() > two.length()) {
            max = one;
            min = two;
        } else {
            max = two;
            min = one;
        }

        for(int i = 0;i < min.length();i ++) {
            for(int begin = 0,end = min.length() - i;end <= min.length();begin ++, end ++) {
                current = min.substring(begin,end);
                if(max.contains(current)) {
                    return current;
                }
            }
        }

        return null;
    }

}
