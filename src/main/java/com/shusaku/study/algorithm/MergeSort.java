package com.shusaku.study.algorithm;

import java.util.Arrays;

/**
 * @program: Java8Test
 * @description: 把待排序的序列分为若干的子序列  每个子序列进行排序  然后把子序列合并为整体有序序列
 * @author: Shusaku
 * @create: 2019-12-25 16:11
 */
public class MergeSort {

    private static void sort2(int[] arr, int left, int right){

        if(left >= right)
            return;

        int center = (left + right) / 2;
        sort(arr,left,center);
        sort(arr,center + 1,right);
        merge2(arr,left,center,right);
    }

    private static void merge2(int[] arr, int left, int center, int right) {
        int[] tempArr = new int[arr.length];
        int tempIndex = left;//临时索引
        int temp = left;//最终临时数组向原数组同步数据需要用到
        int rightFirst = center + 1;

        while(left <= center && rightFirst <= right){
            if(arr[left] <= arr[rightFirst]){
                tempArr[tempIndex ++] = arr[left ++];
            }else{
                tempArr[tempIndex ++] = arr[rightFirst ++];
            }
        }

        while(left <= center) {
            tempArr[tempIndex ++] = arr[left ++];
        }

        while(rightFirst <= right) {
            tempArr[tempIndex ++] = arr[rightFirst ++];
        }

        while(temp <= right) {
            arr[temp] = tempArr[temp ++];
        }
    }


    public static void merge3(int[] arr, int left, int center, int right) {

        int[] tempArr = new int[arr.length];
        int temp = left;//归并时的起始位置
        int tempIndex = left;//临时索引  临时数组存储数据时使用
        int rightFirst = center + 1;

        while(left <= center && rightFirst <= right) {
            if(arr[left] <= arr[rightFirst]) {
                tempArr[tempIndex ++] = arr[left ++];
            } else {
                tempArr[tempIndex ++] = arr[rightFirst ++];
            }
        }

        while(left <= center) {
            tempArr[tempIndex ++] = arr[left ++];
        }

        while(rightFirst <= right) {
            tempArr[tempIndex ++] = arr[rightFirst ++];
        }

        while(temp <= right) {
            arr[temp] = tempArr[temp ++];
        }

    }


    public static void main(String[] args) {
        mergeSort(new int[]{5,3,6,3,7,8,5,345,665,15,457,78,223});
    }

    public static void mergeSort(int[] arr) {
        sort2(arr,0,arr.length - 1);
    }

    private static void sort(int[] arr,int left, int right) {
        if(left >= right)
            return;
        int center = (left + right) / 2;

        //对左边数据进行递归
        sort(arr,left,center);

        //对右边数据进行递归
        sort(arr,center + 1,right);

        //合并
        merge2(arr,left,center,right);

        print(arr);
    }

    private static void merge(int[] arr, int left, int center, int right) {

        int[] tempArr = new int[arr.length];
        //数组右边的第一个元素索引
        int rightFirst = center + 1;
        //记录临时数组的索引
        int tempIndex = left;

        //缓存左数组第一个索引
        int tmp = left;

        while(left <= center && rightFirst <= right) {
            //从两个数组中取出最小的放入临时数组
            if(arr[left] <= arr[rightFirst]) {
                tempArr[tempIndex ++] = arr[left ++];
            } else {
                tempArr[tempIndex ++] = arr[rightFirst ++];
            }
        }
        //剩余部分依次放入临时数组  因为上边的原因  下边的两个while只会执行一个
        while(rightFirst <= right) {
            tempArr[tempIndex ++] = arr[rightFirst ++];
        }

        while(left <= center) {
            tempArr[tempIndex ++] = arr[left ++];
        }

        //将原left-right范围内的内容复制回原数组
        while(tmp <= right){
            arr[tmp] = tempArr[tmp ++];
        }

    }

    private static void print(int[] arr){
        Arrays.stream(arr).forEach(i -> System.out.print(i + " "));
        System.out.println();
    }
}
