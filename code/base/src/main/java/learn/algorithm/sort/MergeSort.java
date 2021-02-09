package learn.algorithm.sort;

public class MergeSort {


    /**
     * 归并排序
     * 分而治之，递归分解，然后合并
     * 将排序数组分为2部分，分别进行排序，2部分排序完成后，拼接在一起
     * @param nums
     * @return
     */
    public static int[] mergeSort(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    public static int[] mergeSort(int[] nums, int start, int end) {
        if (end - start == 1) {
            int[] sortNums = new int[2];
            sortNums[0] = nums[start] > nums[end]?nums[end]:nums[start];
            sortNums[1] = nums[start] > nums[end]?nums[start]:nums[end];
            return sortNums;
        }

        if (end == start) {
            int[] sortNums = new int[1];
            sortNums[0] = nums[start];
            return sortNums;
        }

        // 从中间划分
        int mid = (end - start) / 2 + start;
        // 对左侧数据排序
        int[] leftNums = mergeSort(nums, start, mid);
        // 对右侧数据排序
        int[] rightNums = mergeSort(nums, mid + 1, end);

        // 合并数据
        return merge(leftNums, rightNums);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] nums = new int[left.length + right.length];
        int leftIndex = 0, rightIndex = 0, index = 0;
        while (leftIndex < left.length || rightIndex < right.length) {
            // 如果左侧数据为空，直接复制剩余右侧数据
            if (leftIndex >= left.length && rightIndex < right.length) {
                while(rightIndex < right.length) {
                    nums[index] = right[rightIndex];
                    index ++;
                    rightIndex ++;
                }
                break;
            }
            // 如果右侧数据为空了，直接复制剩余的左侧数据
            if (rightIndex >= right.length && leftIndex < left.length) {
                while(leftIndex < left.length) {
                    nums[index] = left[leftIndex];
                    index ++;
                    leftIndex ++;
                }
                break;
            }

            // 如果不为空，则比较大小，小的先复制进新数据
            if (left[leftIndex] < right[rightIndex]) {
                nums[index] = left[leftIndex];
                index ++;
                leftIndex ++;
            } else {
                nums[index] = right[rightIndex];
                index ++;
                rightIndex ++;
            }
        }
        return nums;
    }


    public static void main(String[] args) {
        int[] nums = new int[15];
        int index = 0;
        for (int i = 14; i > -1; i--) {
            nums[index] = i;
            index ++;
        }

        for (int i = 0; i < nums.length; i++) {
            System.out.printf("%d ", nums[i]);
        }
        System.out.println();

        // 排序
        int[] sortNums = mergeSort(nums);
        // 排序后
        for (int i = 0; i < sortNums.length; i++) {
            System.out.printf("%d ", sortNums[i]);
        }
        System.out.println();
    }

}