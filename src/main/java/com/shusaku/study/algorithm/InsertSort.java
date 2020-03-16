package com.shusaku.study.algorithm;

import java.util.Arrays;

/**
 * @program: Java8Test
 * @description: 插入排序 通过构建有序序列  对未排序数据 在已经排序的序列中从后向前扫描  找到相应的位置并且插入
 * @author: Shusaku
 * @create: 2019-12-25 14:32
 */
public class InsertSort {

    public static void insertSort2(int[] arr) {
        for(int i = 1;i < arr.length;i ++) {
            //要插入的数据
            int insertValue = arr[i];
            //要插入的位置
            int index = i - 1;
            while(index >= 0 && arr[index] > insertValue) {
                arr[index + 1] = arr[index];
                index --;
            }
            arr[index + 1] = insertValue;
        }
    }



    public static void main(String [] args) {

        int[] arr = new int[]{3,4,2,9,6,7,3,1};
        insertSort(arr);
        Arrays.stream(arr).forEach(i -> System.out.print(i + " "));
    }

    public static void insertSort(int[] arr) {

        for(int i = 1;i < arr.length;i ++ ) {
            //插入的数
            int insertValue = arr[i];
            //被插入的位置  这个位置上的数据与插入的数进行比较
            int index = i - 1;
            //如果插入的数小于被插入位置的数  将arr[index]向后移动  让index 向前移动
            while(index >= 0 && insertValue < arr[index]) {
                arr[index + 1] = arr[index];
                index --;
            }
            arr[index + 1] = insertValue;
        }
    }


}
