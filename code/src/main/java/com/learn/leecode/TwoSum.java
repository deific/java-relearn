
package com.learn.leecode;

public class TwoSum {



    public static int[] twoSum(int[] nums, int target) {
        int[] index = new int[nums.length];
        int[] targetIndex = new int[2];
        int secode = 0;
        int secodeIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            secode = target - nums[i];
            secodeIndex = secode % nums.length;
            if (secodeIndex > 0) {
                targetIndex[0] = i;
                targetIndex[1] = secodeIndex;
                return targetIndex;
            } else {
                index[secodeIndex] = i;
            }
        }

        return targetIndex;
    }


    public static void main(String[] args) {
        int[] nums = new int[4];
        nums[0] = 2;
        nums[1] = 7;
        nums[2] = 4;
        nums[3] = 7;

        int[] result = twoSum(nums, 9);
        System.out.println("[" + result[0] + ", " + result[1] + "]");
    }
}