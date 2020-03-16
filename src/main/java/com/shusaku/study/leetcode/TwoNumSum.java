package com.shusaku.study.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: Java8Test
 * @description: 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *               你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *               来源：力扣（LeetCode） 链接：https://leetcode-cn.com/problems/two-sum 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: Shusaku
 * @create: 2020-01-10 10:41
 */
public class TwoNumSum {

    public int[] twoSum(int[] nums, int target) {
        int[] ints = new int[2];
        label:
        for(int i = 1,j = nums.length;i < j;i ++) {
            for(int x = 0;x < i;x ++) {
                if(nums[x] + nums[i] == target) {
                    ints[0] = x;
                    ints[1] = i;
                    break label;
                }
            }
        }
        return ints;
    }

    public int[] twoSum2(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0,j = nums.length;i < j;i ++) {
            int temp = target - nums[i];
            if(map.containsKey(temp)) {
                return new int[]{map.get(temp),i};
            }
            map.put(nums[i],i);
        }
        throw new IllegalArgumentException("No two sum solution!");
    }

}
