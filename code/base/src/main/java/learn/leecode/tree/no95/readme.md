### 95. 不同的二叉搜索树 II

给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。

### 示例 1：

```
输入：3
输出：
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
解释：
以上的输出对应以下 5 种不同结构的二叉搜索树：

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3


```

### 提示：

0 <= n <= 8

### 解题思路
1.递归解法
搜索二叉树的特性是，左子树的元素值一定是小于根节点的，右子树的值是大于根节点的。左右子树也同样是搜索二叉树。那对于1...n的元素，假如i为根节点，那么左侧子树一定是1...i-1的元素，右侧为i+1...n的元素，对于以i为根节点的搜索二叉树，可能树就是1...i-1的所有可能的子树，拼接上i，再拼接上i+1...n的所有可能的右子树。



> 来源：力扣（LeetCode）
> 链接：https://leetcode-cn.com/problems/unique-binary-search-trees-ii
> 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。