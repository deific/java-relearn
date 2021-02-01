
package com.learn.leecode.tree.no96;


public class Solution {

    /**
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。
     * 动态规划法
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;

        for(int i = 2; i < n + 1; i++) {
            for(int j = 1; j < i + 1; j++) {
                dp[i] += dp[j-1] * dp[i-j];
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int count = solution.numTrees(3);
        System.out.println(count);

    }
}