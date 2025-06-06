package com.pp.sort;

/**
 * 给定一个整数数组，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 初始化两个变量，maxSoFar和maxEndingHere，maxSoFar用于存储全局最大子数组和，maxEndingHere记录当前子数组的和。
 * 遍历数组，对每个元素，更新maxEndingHere的值为当前元素和maxEndingHere的较大值，表示当前子数组的和。
 * 更新maxSoFar为maxSoFar与maxEndingHere的较大值，表示当前子数组的和和全局最大子数组和的较大值。
 * 最终maxSoFar即为全局最大子数组和。
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/6       create this file
 * </pre>
 */
public class MaximumSubarraySum {

    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }

        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // 更新当前子数组的最大和
            maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
            // 更新全局最大子数组和
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        System.out.println("Maximum subarray sum:" + maxSubArray(nums));
    }


}
