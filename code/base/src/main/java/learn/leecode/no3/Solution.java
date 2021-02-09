
package learn.leecode.no3;


import java.util.*;

public class Solution {

    /**
     * 利用定长数组和字符串的ASCII码记录字符重复状态
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int maxSize = 0;
        //记录ASCII 码字符出现的位置，以字符作为下标
        // 所有的字符由128个ASCII编码构成，所以定义一个数组，每个字符的ASCII编码作为数组下标，值是当前字符在字符串中的实际位置下标，默认值为0
        int[] dict = new int[128];

        //为了方便理解，这里把数组内容全部设为 -1，之后在记录的时候就可以从 0 开始，方便理解
        Arrays.fill(dict, -1);
        //用于记录重复 ASCII 码字符出现的位置的值
        int repeatIndex = -1;
        // 当前下标
        int i = 0;
        int ASCII;
        // 遍历每个字符
        while (i < s.length()) {
            // 取出当前字符的ASCII值
            ASCII = s.charAt(i);
            //如果当前位置的值 > 0,说明之前已经赋值了，也说明当前字符重复
            if (dict[ASCII] > repeatIndex) {
                repeatIndex = dict[ASCII];
            }
            dict[ASCII] = i;
            maxSize = Math.max(maxSize, i - (repeatIndex));
            if (maxSize >= s.length() - repeatIndex - 1) {
                break;
            }
            i++;
        }
        System.out.println("input: " + s + " output: " + maxSize);
        return maxSize;
    }

    /**
     * 利用list保存字符，通过list的indexOf方法判断字符是否重复
     * 重复后利用list计算子字符串长度
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring1(String s) {
         if (s == null || "".equals(s)) {
             return 0;
         }
         char[] chars = s.toCharArray();
         int result = 1;
         List list = new ArrayList();
         for (int i = 0; i < chars.length; i++) {
             if (list.contains(chars[i])) {
                 result = list.size() > result?list.size():result;
                 list.add(chars[i]);
                 list = list.subList(list.indexOf(chars[i]) + 1, list.size());
             } else {
                 list.add(chars[i]);
             }
         }
         result = list.size() > result?list.size():result;
        System.out.println("input: " + s + " output: " + result);
         return result;
    }

    /**
     * 利用Map保存字符的重复状态和重复前的下标
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {
         Map<Character, Integer> map = new HashMap<>();
         char[] str = s.toCharArray();
         int len = str.length;
         if (len == 0 || len == 1)
             return len;
         int max = 0;
         map.put(str[0], 0);
         int length = 1;
         int begin = 0;
         for (int i = 1; i < len; i++) {
             char c = str[i];
             if (map.containsKey(c) && map.get(c) >= begin) {
                 begin = map.get(c) + 1;
                 length = i - map.get(c);
             } else {
                 length++;
             }
             map.put(c, i);
             max = Math.max(max, length);
         }
        System.out.println("input: " + s + " output: " + max);
         return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.lengthOfLongestSubstring("pwwkew");
        solution.lengthOfLongestSubstring("bbbbbbbbbbbbb");
        solution.lengthOfLongestSubstring("abcabcbb");
    }
}