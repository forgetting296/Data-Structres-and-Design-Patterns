package com.shusaku.study.algorithm;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Java8Test
 * @description: 冒泡排序 比较相邻的两个数据 前面的数据大于后面的  那么就将这两个数交换
 *                      这样对数组的第0个数据到N-1个数据进行一次遍历之后 最大的一个数就沉到了N-1的位置
 * @author: Shusaku
 * @create: 2019-12-25 14:12
 */
public class BubbleSort {

    public static void main(String[] args) {
        ArrayList<Integer> list = Lists.newArrayList(4, 3, 5, 2, 7, 2, 1);
        bubbleSort(list);
        System.out.println(list);
    }

    public static void bubbleSort(List<Integer> list) {

        for(int i = 0;i < list.size();i ++) {
            for(int j = 1;j < list.size() - i;j ++) {
                int temp;
                if(list.get(j) < list.get(j - 1)) {
                    temp = list.get(j - 1);
                    list.set(j-1,list.get(j));
                    list.set(j,temp);
                }
            }
        }
    }

}
