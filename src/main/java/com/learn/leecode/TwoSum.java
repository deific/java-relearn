/*******************************************************************************
 * @(#)TwoSum.java 2021年01月13日 10:42
 * Copyright 2021 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.learn.leecode;

/**
 * <b>Application name：</b> TwoSum.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2021 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2021年01月13日 10:42 <br>
 * <b>@author：</b> <a href="mailto:chensg@miyzh.com"> chensg </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
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