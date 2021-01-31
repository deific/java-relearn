
package com.learn.leecode.tree.no94;


import com.learn.leecode.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {

    /**
     * 中序遍历一个树
     * 中序遍历的顺序先遍历左子树，根节点，右子树
     * 递归解法
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> vals = new ArrayList<>();
        // 无数返回
        if (root == null) return vals;

        // 遍历左子树数
        List<Integer> lefts = inorderTraversal(root.left);
        if (lefts != null) {
            vals.addAll(lefts);
        }
        // 遍历根节点
        vals.add(root.val);
        // 遍历右子树
        List<Integer> rights = inorderTraversal(root.right);
        if (rights != null) {
            vals.addAll(rights);
        }
        return vals;
    }

    /**
     * 中序遍历一个树
     * 中序遍历的顺序先遍历左子树，根节点，右子树
     * 迭代解法
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> vals = new ArrayList<>();
        // 无数返回
        if (root == null) return vals;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode head = root;
        while (head != null || !stack.isEmpty()) {
            // 先走左子树
            if (head != null) {
                // 如果左子树不为空，入栈元素，继续向左走
                stack.push(head);
                head = head.left;
            } else {
                // 左侧走完了，出栈记录输入，再走右侧
                head = stack.pop();
                vals.add(head.val);
                head = head.right;
            }
        }
        return vals;
    }

    public void printData(List<Integer> vals) {
        for (Integer val : vals) {
            System.out.printf("%s ", val);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode treeNode = new TreeNode(1);
        treeNode.right = new TreeNode(2);
        treeNode.right.left = new TreeNode(3);

        solution.printData(solution.inorderTraversal2(treeNode));

        TreeNode treeNode1 = new TreeNode();
        solution.printData(solution.inorderTraversal2(treeNode1));

        TreeNode treeNode2 = new TreeNode(1);
        treeNode2.left = new TreeNode(2);
        solution.printData(solution.inorderTraversal2(treeNode2));
    }
}