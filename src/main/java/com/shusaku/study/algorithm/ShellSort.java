package com.shusaku.study.algorithm;

import java.util.Arrays;

/**
 * @program: Java8Test
 * @description: 先将整个待排序的记录序列分割成若干的子序列  对这若干的子序列进行插入排序  然后再对全体记录进行依次插入排序
 * @author: Shusaku
 * @create: 2019-12-25 15:16
 */
public class ShellSort {

    public static void main(String[] args) {

        int[] arr = {8,4,9,245,567,33,456,2345345,34,66,11,66,33452345,55555};
        Arrays.stream(arr).forEach(i -> System.out.print(i + " "));
        System.out.println();
        shellSort(arr);
        Arrays.stream(arr).forEach(i -> System.out.print(i + " "));

    }

    public static void shellSort(int[] arr) {
        int dk = arr.length / 2;
        while(dk >= 1) {
            shellInsertSort2(arr,dk);
            dk = dk / 2;
        }
    }

    private static void shellSort3(int[] arr, int dk) {
        for(int i = dk;i < arr.length;i ++) {
            if(arr[i] < arr[i - dk]){
                int x = arr[i];//待插入元素
                arr[i] = arr[i - dk];
                int j = i - dk;
                while((j) >= 0 && arr[j] > x) {
                    arr[j + dk] = arr[j];
                    j -= dk;
                }
                arr[j + dk] = x;
            }
        }
    }



    //希尔排序的子序列  使用插入排序  只是要将普通插入排序的1 换成dk
    private static void shellInsertSort(int[] arr, int dk) {
        for(int i = dk;i < arr.length;i ++) {
            if(arr[i] < arr[i - dk]) {
                int j = i - dk;
                int x = arr[i];//x就是那个待插入的元素
                arr[i] = arr[i - dk];
                while(j >= 0 && arr[j] > x) {
                    arr[j + dk] = arr[j];
                    j -= dk;
                }
                arr[j + dk] = x;
            }
        }
    }

    private static void shellInsertSort2(int[] arr, int dk) {

        for(int i = dk;i < arr.length;i ++) {
            if(arr[i] < arr[i - dk]){
                int  j;
                int x = arr[i];
                arr[i] = arr[i - dk];
                for(j = i - dk;j >= 0 && arr[j] > x;j -= dk) {
                    arr[j+ dk] = arr[j];
                }
                arr[j + dk] = x;
            }
        }
    }

}
