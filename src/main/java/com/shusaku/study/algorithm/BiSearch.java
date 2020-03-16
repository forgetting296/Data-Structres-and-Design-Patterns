package com.shusaku.study.algorithm;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Java8Test
 * @description: 二分查找  要求查找的数组或者集合是有序的
 * @author: Shusaku
 * @create: 2019-12-25 13:59
 */
public class BiSearch {

    public static void main(String[] args) {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        System.out.println(biSearch(3,list));
    }

    public static int biSearch(int a, List<Integer> list) {

        int start = 0;
        int mid;
        int end = list.size() - 1;

        while(start <= end) {
            mid = (start + end) / 2;
            if(list.get(mid) > a){//向左查找
                end = mid - 1;
            }else if(list.get(mid) < a){//向右查找
                start = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

}
