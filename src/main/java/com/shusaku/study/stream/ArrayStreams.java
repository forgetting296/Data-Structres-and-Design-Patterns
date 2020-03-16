package com.shusaku.study.stream;

import java.util.Arrays;

public class ArrayStreams {

    public static void main(String[] args){

        Arrays.stream(new double[]{ 3.14159, 2.718, 1.618 }).forEach(d -> System.out.format("%f ",d));

        System.out.println();

        Arrays.stream(new int[]{1, 3, 5 }).forEach(i -> System.out.format("%d ",i));

        System.out.println();

        //选择一个子域  索引范围  包左不包右
        Arrays.stream(new int[]{1,2,3,4,5,6},2,5).forEach(i -> System.out.print(i + " "));

    }


}
