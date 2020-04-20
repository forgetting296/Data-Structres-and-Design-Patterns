package com.shusaku.study.leetcode;

/**
 * @program: Data Structures And Design Patterns
 * @description:编写一个函数来查找字符串数组中的最长公共前缀。
 *
 *              如果不存在公共前缀，返回空字符串 ""。
 *              输入: ["flower","flow","flight"]
 *              输出: "fl"
 *
 * @author: Shusaku
 * @create: 2020-04-20 16:48
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {
        String[] strs = {"flower","flow","flight"};
        System.out.println(longestCommonPrefix1(strs));
    }

    //如果数组为空　直接返回""
    //取数组第一个元素　不断截取自身字符 直到是下一个元素的子串　再与之后元素进行比较\截取
    private static String longestCommonPrefix1(String[] strs) {

        if(strs.length == 0) {
            return "";
        }

        String prefix = strs[0];
        for(int i = 0;i < strs.length;i ++) {
            //前缀　要保证indexOf是0
            while(strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if(prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }

    private static String longestCommonPrefix2(String[] strs) {

        if(strs == null || strs.length == 0) {
            return "";
        }

        //也是以第一个元素为基准　比较每个列的字符
        //一旦字符不相同　或者　超过了比对字符的长度　就返回
        //如果上边所有的都满足　那么strs[0]就是最大公共前缀
        for(int i = 0;i < strs.length;i ++) {
            char c = strs[0].charAt(i);
            for(int j = 1;j < strs.length;j ++) {
                if(i == strs[j].length() || c != strs[j].charAt(i)) {
                    return strs[0].substring(0,i);
                }
            }
        }
        return strs[0];
    }

}
