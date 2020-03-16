package com.shusaku.study.stream;

import java.util.stream.LongStream;

public class Prime {

    //用于检测质数
    public static Boolean isPrime(long n){

        //出现任何余数为0的  返回false  在rangeClose范围中余数不为0  则返回true
        return LongStream.rangeClosed(2,(long)Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }

    //iterate 从第一个参数开始迭代  第二个参数引用第一个参数  操作后  返回流
    public LongStream numbers(){
        return LongStream.iterate(2,i -> i + 1).filter(Prime::isPrime);
    }

    public static void main(String[] args){
        System.out.println(Math.sqrt(3));
        System.out.println(isPrime(3));
    }


}
