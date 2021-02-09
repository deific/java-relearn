package learn.leecode.no2;

/**
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 *  
 *
 * 示例 1：
 *
 *
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * 示例 2：
 *
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 * 示例 3：
 *
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 输出：[8,9,9,9,0,0,0,1]
 *  
 *
 * 提示：
 *
 * 每个链表中的节点数在范围 [1, 100] 内
 * 0 <= Node.val <= 9
 * 题目数据保证列表表示的数字不含前导零
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class Solution {


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        printData(l1);
        printData(l2);
        // 递归调用
        // return nextSum(l1, l2, 0);
        // 循环调用
        ListNode result = addTwoNumbers2(l1, l2, 0);
        printData(result);
        return result;
    }

    /**
     * 递归调用，从头开始计算每一位的值，并进位
     * @param l1
     * @param l2
     * @param carray
     * @return
     */
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2, int carray) {
        if ( l1 == null && l2 == null) {
            return carray > 0?new ListNode(carray):null;
        }
        int nextSum = (l1 != null?l1.val:0) + (l2 != null?l2.val:0) + carray;
        return new ListNode(nextSum % 10, addTwoNumbers1(l1 != null?l1.next:null, l2 != null?l2.next:null, nextSum/10));
    }


    /**
     * 循环调用，从头开始计算每一位的值，并进位
     * @param l1
     * @param l2
     * @param carray
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2, int carray) {
        ListNode head = new ListNode(0);
        ListNode sumNode = head;
        // 只要任一操作数节点不为空，进行计算
        while(l1 != null || l2 != null) {
            // 计算该节点，如果为空则按0计算
            int nextSum = (l1 != null?l1.val:0) + (l2 != null?l2.val:0) + carray;
            // 计算进位
            carray = nextSum / 10;
            // 计算计算后的余值
            sumNode.val = nextSum % 10;

            // 移动节点
            l1 = l1 != null?l1.next:null;
            l2 = l2 != null?l2.next:null;

            if (carray > 0) {
                sumNode.next = new ListNode(carray);
            }
            // 移动和
            sumNode = sumNode.next;
        }

        return head;
    }

    private void printData(ListNode node1) {
        ListNode tmp = node1;
        System.out.printf("[");
        while (tmp != null) {
            System.out.printf("%s ", tmp.val);
            tmp = tmp.next;
        }
        System.out.printf("]\n");
    }


    public static void main(String[] args) {
        Solution solution = new Solution();

        // l1 = [9,9,9,9,9,9,9]
        ListNode node1 = new ListNode(9);
        ListNode tail = node1;
        for (int i = 0; i < 7; i++) {
            tail.next = new ListNode(9);
            tail = tail.next;
        }

        // l2 = [9,9,9,9]
        ListNode node2 = new ListNode(9);
        ListNode tail2 = node2;
        for (int i = 0; i < 4; i++) {
            tail2.next = new ListNode(9);
            tail2 = tail2.next;
        }
        solution.addTwoNumbers(node1, node2);
    }
}