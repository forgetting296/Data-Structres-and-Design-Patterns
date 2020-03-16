package com.shusaku.study.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: Java8Test
 * @description: 基数排序算法： 将所有待比较数值（正整数） 统一为同样的数位长度 数为较短的前面补零  然后从低位开始  依次进行排序
 * @author: Shusaku
 * @create: 2019-12-25 19:43
 */
public class BaseNumSort {

    public static void main(String[] args) {
        int a[]={49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,101,56,17,18,23,34,15,35,25,53,51};
        baseNumSort(a);
        Arrays.stream(a).forEach(i -> System.out.print(i + " "));
    }

    public static void baseNumSort(int[] arr) {
        sort(arr);
    }

    private static void sort(int[] arr) {

        int max = arr[0];
        for(int i = 1;i < arr.length;i ++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }
        int time = 0;
        //判断位数
        while(max > 0) {
            max /= 10;
            time ++;
        }

        //建立10个队列
        List<ArrayList> queue = new ArrayList<>();
        for(int i = 0;i < 10;i ++) {
            queue.add(new ArrayList());
        }

        //进行time分配和收集  i越来越大  位数越来越高  排序权重越来越高  所以count一直从0开始  高权重的数据会将之前的数据覆盖
        for(int i = 0;i < time;i ++) {
            for(int j = 0;j < arr.length;j ++) {
                //得到数字的第time + 1位
                int x = arr[j] % (int)Math.pow(10,i+1) / (int)Math.pow(10,i);
                ArrayList arrayList = queue.get(x);
                arrayList.add(arr[j]);
                queue.set(x,arrayList);
            }
            int count = 0;
            for(int k = 0;k < 10;k ++) {
                while(queue.get(k).size() > 0) {
                    ArrayList arrayList = queue.get(k);
                    arr[count] = (Integer) arrayList.get(0);
                    arrayList.remove(0);
                    count ++;
                }
            }
        }
    }
}
