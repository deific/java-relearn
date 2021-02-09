package learn.algorithm.recursion;


import java.util.ArrayList;
import java.util.List;

public class RecursionPrintAllSubSequence {

    /**
     * 给定字符串，求该字符串所有的子字串符组成
     */
    public static List<String> printAllSequence(String str) {
        List<String> allSubStr = new ArrayList<>();
        printAllSubSequence(str, 0, allSubStr, "");
        return allSubStr;
    }

    /**
     * 暴力递归方法
     * 1、从第一个字符开始，子字符串组成选择存在 包含 or 不包含 该字符两种结果
     * 2、循环每一个字符串，都有存在包含 or 不包含 该字符
     * 3、当所有字符选择完成，所有路径
     */
    public static void printAllSubSequence(String str, int index, List<String> subStrList, String path) {
        if (index == str.length()) {
            subStrList.add(path);
            return;
        }

        char c = str.charAt(index);
        // 选择包含字符串
        printAllSubSequence(str, index + 1, subStrList, path);
        // 不包含该字符串
        printAllSubSequence(str, index + 1, subStrList, path + c);
    }

    // = ==============================================================
    /**
     * 给定字符串，求该字符串所有的子字串符组成
     */
    public static List<String> printAllComplex(String str) {
        List<String> allSubStr = new ArrayList<>();
        printAllComplexLimit(str.toCharArray(), 0, allSubStr);
        return allSubStr;
    }

    /**
     * 打印字符串的所有排列组合
     * 思路：字符串的每一个字符从0位置开始，都可能出现，按照每一个位置，尝试所有组合
     * @param str
     * @param index
     * @param subStrList
     * @param path
     */
    public static void printAllComplex(char[] str, int index, List<String> subStrList, String path) {
        if (index == str.length) {
            subStrList.add(path);
            return;
        }

        for(int i = index; i < str.length; i++) {
            // 尝试每一个字符在i位置
            swap(str, index, i);
            printAllComplex(str, index + 1, subStrList, path + str[index]);
            swap(str, i, i);
        }
    }

    /**
     * 打印字符串的所有排列组合
     * 思路：字符串的每一个字符从0位置开始，都可能出现，按照每一个位置，尝试所有组合
     * 不包含重复排列
     * 分析：
     * 如果存在重复排列，说明每一个位置，会多次出现同一个字符串，那么，限定每一个位置的字符只能出现一次，所组成的排列肯定不包含重复。这种方法，叫做分支限界
     * @param str
     * @param index
     * @param subStrList
     */
    public static void printAllComplexLimit(char[] str, int index, List<String> subStrList) {
        if (index == str.length) {
            subStrList.add(String.valueOf(str));
            return;
        }

        // 字符串限定为字母，最多只有26个字符，出现过时，设置为ture.
        boolean[] visted = new boolean[26];
        for(int i = index; i < str.length; i++) {
            // 判断，当前字符是否访问过，
            // str[i] - 'a'的目的实际上是为了做字符的ascii计算后，得到每个字符的坐标落在0-25之间
            if (!visted[str[i] - 'a']) {
                visted[str[i] - 'a'] = true;
                // 尝试每一个字符在i位置
                swap(str, index, i);
                printAllComplexLimit(str, index + 1, subStrList);
                swap(str, i, i);
            }
        }
    }

    public static void swap(char[] str, int i, int j) {
        char a = str[i];
        str[i] = str[j];
        str[j] = a;
    }

    /**
     * 数字1-> A,2->B...,那么给定一个数字，可以转换了字符串，如：111，可以转为AAA, AK, KA三种。
     * 求，给定一个数字，有多少种可转换的字符串结果
     * 分析：给定一个数字，从左到右尝试来说，只存在两种可以，第一种是 左侧第1个数字独自转换，后续其他数字转换形成的结果。第二种是 左侧第1个数字和第2个数字组合起来<=26时，可以转换，再和后续数字转换形成结果。
     * 递归按这两种方式进行查找，最终可以尝试出所有的转换可能
     * @param nums
     */
    public static int printAllComplexWithNumber(String nums) {
        return printAllComplexWithNumber(nums.toCharArray(), 0);
    }


    public static int printAllComplexWithNumber(char[] nums, int index) {
        // 如果尝试位置已到末尾，说明存在一种转换方式
        if (index == nums.length) {
            return 1;
        }

        // 如果尝试位置的数字=‘0’，无论是独立转换和还是和下一位组合转换，都不成立
        if (nums[index] == '0') {
            return 0;
        }

        // 如果尝试位置的数字=‘1‘，那么存在独立转换 和 与下一位数组组合转换两种方式
        if (nums[index] == '1') {
            int cnt = printAllComplexWithNumber(nums, index + 1);
            // 下雨
            if (index + 1 < nums.length) {
                cnt += printAllComplexWithNumber(nums, index + 2);
            }
            return cnt;
        }

        // 如果尝试位置的数字=’2‘，那么存在独立转换 和 与下一位置数组组合 < 26时，两种转换方式。
        if (nums[index] == '2') {
            int cnt = printAllComplexWithNumber(nums, index + 1);
            // 下雨
            if (index + 1 < nums.length && nums[index]  > '0' && nums[index + 1] < '6') {
                cnt += printAllComplexWithNumber(nums, index + 2);
            }
            return cnt;
        }
        // 如果尝试位置数字是3~9,只存在独立转换1种可能
        return printAllComplexWithNumber(nums, index + 1);
    }


    /**
     * weight的长度与value的长度相等。weight表示货物的重量，value表示价值，给定一个bag表示承重，请在给bag时，可以装入货品的最大价值是多少
     * @param weight
     * @param value
     * @param bag
     * @return
     */
    public static int printMaxBagValue(int[] weight, int[] value, int bag) {
        return printMaxBagValue(weight, value, 0 , 0, bag);
    }
    /**
     * weight的长度与value的长度相等。weight表示货物的重量，value表示价值，给定一个bag表示承重，请在给bag时，可以装入货品的最大价值是多少
     * @param nums
     */
    public static int printMaxBagValue2(int[] weight, int[] value, int bag) {
        return printMaxBagValue(weight, value, 0 , bag);
    }

    /**
     * 分析：从左到右，尝试每一个货物开始，每一个货物存在两种可能，
     * 1 要当前获取，装入bag，那么价值=当前获取价值+后续获取的最大价值
     * 2 不要当前货物，那么价值=当前获取价值+后续获取的最大价值
     * 根据1和2两种请求得到价值，取最大值
     * 那么，递归尝试每一个货物，最终获得最大值可能
     * @param weight
     * @param value
     * @param index
     * @param alreadyW
     * @param bag
     * @return
     */
    public static int printMaxBagValue(int[] weight, int[] value, int index, int alreadyW, int bag) {
        // 已到达最后位置，返回0
        if (index == weight.length) {
            return 0;
        }
        // 如果已占用重量 > 包的承重，返回-1，表示无法装入
        if (alreadyW > bag) {
            return -1;
        }

        // 第一种情况，不要当前货物，直接求后续获取的最大价值装法
        int v1 = printMaxBagValue(weight, value, index + 1, alreadyW, bag);

        // 第二种情况，要当前货物且存在这种装法，当前价值+后续获取最大价值
        int v2 = printMaxBagValue(weight, value, index + 1, alreadyW +  weight[index], bag);
        if (v2 != -1) {
            v2 = v2 + value[index];
        }
        return Math.max(v1, v2);
    }

    /**
     * 分析：从左到右，尝试每一个货物开始，每一个货物存在两种可能，
     * 1 要当前获取，装入bag，那么价值=当前获取价值+后续获取的最大价值
     * 2 不要当前货物，那么价值=当前获取价值+后续获取的最大价值
     * 根据1和2两种请求得到价值，取最大值
     * 那么，递归尝试每一个货物，最终获得最大值可能
     * @param weight
     * @param value
     * @param index
     * @return
     */
    public static int printMaxBagValue(int[] weight, int[] value, int index, int rest) {
        // 已到达最后位置，返回0
        if (index == weight.length) {
            return 0;
        }
        // 如果无空余了，说明不存在这种装法
        if (rest < 0) {
            return -1;
        }

        // 第一种情况，不要当前货物，直接求后续获取的最大价值装法
        int v1 = printMaxBagValue(weight, value, index + 1, rest);

        // 第二种情况，要当前货物且存在这种装法，当前价值+后续获取最大价值
        int v2 = printMaxBagValue(weight, value, index + 1, rest - weight[index] );
        if (v2 != -1) {
            v2 = v2 + value[index];
        }
        return Math.max(v1, v2);
    }

    /**
     * 给定一组数字牌，代表分数。每次只能从左边或右边拿一张牌，
     * 假设甲乙都绝顶聪明，那么，甲乙如果要赢得游戏，可获得的最大分数是多少？
     * @param nums
     * @return
     */
    public static int printMaxValue(int[] nums) {
        return Math.max(getFirstMaxValue(nums, 0, nums.length - 1), getSecondMaxValue(nums, 0, nums.length - 1));
    }

    public static int getFirstMaxValue(int[] nums, int l, int r) {
        if (l == r) {
            return nums[l];
        }
        // 先手拿牌，有两种情况
        // 先拿左侧，后手的最大值
        int leftFirst = nums[l] + getSecondMaxValue(nums, l + 1, r);
        // 先拿右侧，
        int rightFirst = nums[r] + getSecondMaxValue(nums, l, r - 1);
        return Math.max(leftFirst, rightFirst);
    }

    public static int getSecondMaxValue(int[] nums, int l, int r) {
        // 这是对方拿牌，如果l==r，说明没牌了，只能拿到0
        if (l == r) {
            return 0;
        }

        // 在后手的情况下，对手先要拿掉一张牌，留给自己再次先手一定是对方认为最小的结果
        // 对手先拿左侧时，那自己再次先手可拿的牌的值如下
        int leftFirst = getFirstMaxValue(nums, l + 1, r);
        // 先拿右侧，那自己再次先手可拿的牌的值如下
        int rightFirst = getFirstMaxValue(nums, l, r - 1);
        // 对手只可能留给自己能拿到的是最小的值
        return Math.min(leftFirst, rightFirst);
    }

    public static void main(String[] args) {
        String str = "abc";
        List<String> allSubStr = printAllSequence(str);
        for (String s : allSubStr) {
            System.out.printf("%s ", s);
        }
        System.out.println("\n子序列数：" + (str.length() + 1) * 2 + " 实际数量：" + allSubStr.size());

        allSubStr = printAllComplex("aaa");
        for (String s : allSubStr) {
            System.out.printf("%s ", s);
        }
        System.out.println("\n子序列数：" + (str.length() + 1) * 2 + " 实际数量：" + allSubStr.size());

        int cnt = printAllComplexWithNumber("111");
        System.out.println("\n转换数：" + cnt);

        int[] weight = new int[3];
        weight[0] = 2;
        weight[1] = 3;
        weight[2] = 5;
        int[] value = new int[3];
        value[0] = 4;
        value[1] = 4;
        value[2] = 2;


        int maxValue = printMaxBagValue(weight, value, 6);
        int maxValue2 = printMaxBagValue2(weight, value, 6);

        System.out.println("\n可装入最大价值为：1=" + maxValue + "  2=" + maxValue2);

        int[] scoreNums = { 4, 7, 9, 5};
        int maxScore = printMaxValue(scoreNums);
        System.out.println("\n最大分数：maxScore=" + maxScore);
    }
}