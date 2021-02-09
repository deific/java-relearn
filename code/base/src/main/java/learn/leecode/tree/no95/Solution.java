
package learn.leecode.tree.no95;


import learn.leecode.tree.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class Solution {

    /**
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。
     * 递归解法
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generateSubTrees(1, n);
    }


    public List<TreeNode> generateSubTrees(int left, int right) {
        List<TreeNode> allTreeNodes = new LinkedList<TreeNode>();
        if (left > right) {
            allTreeNodes.add(null);
            return allTreeNodes;
        }

        for (int i = left; i <= right; i++) {
            // 以i为根节点，递归得到小于i的区间所有左侧树
            List<TreeNode> leftTree = generateSubTrees(left, i -1);

            // 以i为根节点，递归得到大于i区间所有右侧树
            List<TreeNode> rightTree = generateSubTrees(i + 1, right);

            // 以i为根节点，根据以上的左右子树，遍历构建所有的搜索二叉树
            for (TreeNode l : leftTree) {
                for (TreeNode r : rightTree) {
                    TreeNode tree = new TreeNode(i);
                    tree.left = l;
                    tree.right = r;
                    allTreeNodes.add(tree);
                }
            }
        }
        return allTreeNodes;
    }



    private void printData(List<TreeNode> nodes) {
        learn.leecode.tree.no94.Solution solution = new learn.leecode.tree.no94.Solution();
        for (TreeNode node : nodes) {
            System.out.println("");
            solution.printData(solution.inorderTraversal(node));
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        List<TreeNode> all = solution.generateTrees(3);
        solution.printData(all);

    }
}