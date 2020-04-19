package com.shusaku.study.leetcode;

import java.util.*;

/**
 * @program: Java8Test
 * @description:给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/3sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: Shusaku
 * @create: 2020-01-10 11:15
 */
public class ThreeNumSum {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length < 3){
            return result;
        }
        int len = nums.length;
        Arrays.sort(nums);  // 对数组进行排序
        for(int i = 0;i < len;i ++) {
            if(nums[i] > 0)
                break;
            if(i > 0 && nums[i] == nums[i - 1])
                continue;
            int l = i + 1;
            int r = len - 1;
            while(l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if(sum == 0){
                    result.add(Arrays.asList(nums[i],nums[l],nums[r]));
                    while(l < r && nums[l] == nums[l + 1]) {
                        l ++;
                    }
                    while(l < r && nums[r] == nums[r - 1]) {
                        r --;
                    }
                    l ++;
                    r --;
                }else if(sum > 0)
                    r --;
                else if(sum < 0)
                    l ++;
            }
        }
        return result;
    }

    private List<List<Integer>> threeSum3(int[] sum) {
        List<List<Integer>> result = new ArrayList<>();
        if(sum == null || sum.length < 3) {
            return result;
        }
        int len = sum.length;
        Arrays.sort(sum);
        for(int i = 0;i < len;i ++) {
            if(sum[i] > 0) {
                break;
            }
            if(i > 0 && sum[i] == sum[i + 1]) {
                continue;
            }
            int l = i + 1;
            int r = len -1;
            while(l < r) {
                int num = sum[i] + sum[l] + sum[r];
                if(num == 0) {
                    result.add(Arrays.asList(i,l,r));
                    while(l < r && sum[l] == sum[l ++]) {
                        l ++;
                    }
                    while(l < r && sum[r] == sum[r --]) {
                        r --;
                    }
                    l ++;
                    r --;
                } else if (num > 0) {
                    r --;
                } else {
                    l ++;
                }
            }
        }
        return result;
    }

    //这种方式不行  无法去重
    public List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for(int i = 0,j = nums.length;i < j;i ++) {
            int i1 = 0 - nums[i];
            if(!set.contains(i1)) {
                List<Integer> list = twoSum(nums,i1);
                if(list.size() > 0){
                    result.add(list);
                }
            }
            set.add(i1);
        }

        return result;
    }

    private List<Integer> twoSum(int[] nums,int target) {
        List<Integer> twoResult = new ArrayList<>(3);
        Map<Integer,Integer> map = new HashMap<>();
        for(int x = 0,y = nums.length;x < y;x ++) {
            int temp = target - nums[x];
            if(map.containsKey(temp)) {
                twoResult.add(nums[x]);
                twoResult.add(temp);
                twoResult.add(-(target));
                return twoResult;
            }
            map.put(nums[x],x);
        }

        return twoResult;
    }


}
