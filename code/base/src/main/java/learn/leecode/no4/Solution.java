
package learn.leecode.no4;


import java.util.*;

public class Solution {

    /**
     * 查找有序数组的中位数
     * 合并数组进行查找，算法复杂度为O(m+n)
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double result = 0.0;
        if ((nums1 == null && nums2 == null) || (nums1.length == 0 && nums2.length == 0)) {
            return result;
        }

        // 有1个元素，或2个元素时，相加
        if (nums1.length < 2 && nums2.length < 2) {
            return (double)((nums1.length == 1?nums1[0]:0) + (nums2.length == 1?nums2[0]:0)) / (nums1.length + nums2.length);
        }

        // 计算合并后的数组长度
        int totalLength = nums1.length + nums2.length;
        int[] mid = new int[2];
        // 计算中位数下标
        int midIndex = (totalLength / 2);

        // 构建长度为合并后中位数下标长度
        int midLength = midIndex + 1;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < midLength; i++) {
            if ((index1 >= nums1.length)) {
                mid[i%2] = nums2[index2];
                index2 ++;
            } else if ((index2 >= nums2.length)) {
                mid[i%2] = nums1[index1];
                index1 ++;
            } else {
                if (nums1[index1] - nums2[index2] > 0) {
                    mid[i%2] = nums2[index2];
                    index2 ++;
                } else {
                    mid[i%2] = nums1[index1];
                    index1 ++;
                }
            }
        }
        return totalLength % 2 == 0?(double) (mid[0] + mid[1]) / 2:mid[midIndex%2];
    }

    /**
     * 分别从两个数组中各自查找其中位数a,b，并比较大小，
     * 如果a>b，则a所在数组中，比a小的元素肯定不是合并后的中位数,在b所在数组中，比b大的则肯定也不是中位数,反之亦然。
     * 继续从a所在数组中，以a开始到结尾，再查找中位数a。从b所在数组中，以b为结尾，再查找中位数b,重复上一步比较。
     * 当a或者b已最后一个元素时，如果另一数组可比较元素不为1，继续折半查中位数，如果都为1，则a和b为双中位数。
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        double result = 0d;
        // 保证nums1数组的长度一定小于nums2的长度
        if (n1 > n2) {
            result = findMedianSortedArrays(nums2, nums1);
            printData(nums1, nums2, result);
            return result;
        }

        // 求数组总长度的第k个元素，k是中位数的下标
        int k = (n1 + n2 + 1)/2;

        int left = 0;
        int right = n1;
        while(left < right){
            int m1 = left +(right - left)/2;
            int m2 = k - m1;
            if (nums1[m1] < nums2[m2-1]) {
                left = m1 + 1;
            } else {
                right = m1;
            }
        }
        int m1 = left;
        int m2 = k - left;
        int c1 = Math.max(m1 <= 0 ? Integer.MIN_VALUE : nums1[m1-1],
                m2 <= 0 ? Integer.MIN_VALUE : nums2[m2-1]);
        if ((n1 + n2) % 2 == 1) {
            result =  c1;
            printData(nums1, nums2, result);
            return result;
        }

        int c2 = Math.min( m1 >= n1 ? Integer.MAX_VALUE :nums1[m1],
                m2 >= n2 ? Integer.MAX_VALUE : nums2[m2]);
        result = (c1 + c2) * 0.5;
        printData(nums1, nums2, result);
        return result;
    }

    private void printData(int[] nums1, int[] nums2, double mid) {
        System.out.printf("input1:");
        Arrays.stream(nums1).forEach(value -> System.out.printf("%s ", value));
        System.out.println();
        System.out.printf("input2:");
        Arrays.stream(nums2).forEach(value -> System.out.printf("%s ", value));
        System.out.println();
        System.out.printf("out: %s \n", mid);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums1 = new int[2];
        nums1[0] = 1;
        nums1[1] = 2;

        int[] nums2 = new int[2];
        nums2[0] = 3;
        nums2[1] = 4;
        double result = solution.findMedianSortedArrays2(nums1, nums2);


    }
}