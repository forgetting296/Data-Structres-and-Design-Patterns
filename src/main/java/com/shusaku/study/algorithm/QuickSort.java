package com.shusaku.study.algorithm;

import java.util.Arrays;

/**
 * @program: Java8Test
 * @description:快速排序： 选择一个关键值 作为基准值  比基准值小的都在左边序列 比基准值大的都在右边  左右两边也一般都是无序的  基准值一般选择第一个元素 不断递归
 *                  一次循环：从后往前比较，用基准值和最后一个值比较，如果比基准值小则交换位置  如果没有 继续比较下一个 直到找到第一个比基准值小的  就开始交换，
 *                  找到这个值之后，又从前往后开始比较，如果有比基准值大的，交换位置，如果没有继续比较下一个  直到找到第一个比基准值大的才交换  直到从前往后比较的索引 》 从后往前比较的索引
 *                  结束第一次循环，此时对于基准值来说两边都是有序的
 * @author: Shusaku
 * @create: 2019-12-25 17:52
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {5, 3, 6, 3, 7, 8, 5, 345, 665, 15, 457, 78, 223};
        quickSort(arr,0,arr.length - 1);
        Arrays.stream(arr).forEach(i -> System.out.print(i + " "));
    }

    public static void quickSort(int[] arr, int low, int high){

        int start = low;
        int end = high;

        int key = arr[low];
        while(end > start) {
            //
            while(end > start && arr[end] >= key){
                //从最后元素开始比较  直到找到小于基准值的元素  进行交换  否则不断比较
                end --;
            }
            //arr[end]的值小于基准值  进行位置的交换
            if(arr[end] < key) {
                int temp = arr[end];
                arr[end] = arr[start];
                arr[start] = temp;
            }

            //从前往后开始比较
            while(start < end && arr[start] <= key) {
                start ++;
            }

            if(arr[start] > key) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            }
        }

        if(start > low) {
            quickSort(arr,low,start - 1);
        }

        if(end < high) {
            quickSort(arr,end + 1,high);
        }

    }

}
