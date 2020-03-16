package com.shusaku.study.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzi
 */
public class MyHashTable {

    public static void main(String[] args){

        System.out.println(hashASCII("a",5));
        Map<String,String> map = new HashMap<>();

    }

    /**
     * 将字符串中每个字符的ASCII值加起来  最后对tableSize取余
     * @param arg
     * @param tableSize
     * @return
     */
    public static int hashASCII(String arg,int tableSize){

        int hashVal = 0;
        for(int i = 0,j = arg.length();i < j;i ++){
            hashVal += arg.charAt(i);
        }

        return hashVal % tableSize;
    }

    /**
     * 这个散列函数假设key至少有3个字符
     * 值27表示26个英文字母加上一个空格  729 = 27*27
     * @param arg
     * @param tableSize
     * @return
     */
    public static int hashThreeCodesRandomAsccortment(String arg,int tableSize){

        return (arg.charAt(0) + 27 * arg.charAt(1) + 729 * arg.charAt(2) % tableSize);
    }

    /**
     * 这个散列函数涉及到关键字中所有字符，并且一般可以分布的很好  Σ Key[KeySize - i - 1] * (37的i次方)  --    0<= i <= KeySize - 1
     * 根据Horner法则计算一个（37的）多项式函数  例如：Hn = n0 + 37n1 + 37 * 37 n2
     * @param key
     * @param tableSize
     * @return
     */
    public static int hashSimpleAnFast(String key,int tableSize){

        int hashVal = 0;
        for(int i = 0,j = key.length();i < j;i ++){
            hashVal = 37 * hashVal + key.charAt(i);
        }
        hashVal %= tableSize;
        if(hashVal < 0){
            hashVal += tableSize;
        }
        return hashVal;
    }

}
