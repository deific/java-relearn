package learn.algorithm.sort;


public class QuickSort {

    /**
     * 快速排序
     * 分而治之，递归分解，然后合并
     * 将排序数组分为2部分，分别进行排序，2部分排序完成后，拼接在一起
     * @param nums
     * @return
     */
    public static int[] quickSort(int[] nums) {

        return nums;
    }

    public static int[] quickSort(int[] nums, int start, int end) {
        if (start == end) {
            return nums;
        }

        int mid = nums.length / 2 + 1;
        quickSort(nums, start, mid);
        quickSort(nums, mid + 1, end);

        while(start < end) {
            if (start < mid && nums[start] > nums[mid]) {
                int tmp = nums[start];
                nums[start] = nums[mid];
                nums[mid] = tmp;
            }
            if (start > mid && nums[start] < nums[mid]) {
                int tmp = nums[start];
                nums[start] = nums[mid];
                nums[mid] = tmp;
            }
        }
        return nums;
    }
}