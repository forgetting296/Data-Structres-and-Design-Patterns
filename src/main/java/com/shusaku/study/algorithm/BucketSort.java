package com.shusaku.study.algorithm;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @program: Java8Test
 * @description: 桶排序： 把数组arr划分为n个大小相同的子区间(桶) 每个子区间各自排序 最后合并
 * @author: Shusaku
 * @create: 2019-12-25 19:08
 */
public class BucketSort {

    public static void main(String[] args) {

        int[] arr = {4,2,6,7,5,4,34,6,48,34,1,47,88};
        bucketSort(arr);
    }

    public static void bucketSort(int[] arr) {

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for(int i = 0;i < arr.length;i ++) {
            max = Math.max(max,arr[i]);
            min = Math.min(min,arr[i]);
        }

        int bucketNum = (max - min) / arr.length + 1;
        System.out.println(bucketNum + " " + max + " " + min);
        List<List<Integer>> buckArr = new ArrayList<>(bucketNum);
        for(int i = 0;i < bucketNum;i ++) {
            buckArr.add(new ArrayList<>());
        }

        //余数一致的数据放到一个桶中
        for(int i = 0;i < arr.length;i ++) {
            int index = (arr[i] - min) / arr.length;
            buckArr.get(index).add(arr[i]);
        }

        //对每个桶进行排序
        for(int i = 0;i < bucketNum;i ++) {
            Collections.sort(buckArr.get(i));
        }

        List<Integer> result = Lists.newArrayList();
        buckArr.stream()
                .filter(list -> Optional.ofNullable(list).isPresent())
                .filter(list -> list.size() > 0)
                .forEach(list -> {
                    //System.out.println(list);
                    result.addAll(list);
                });
        System.out.println(result);
    }

}
