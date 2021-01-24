package com.learn.struct;

import java.util.Random;
import java.util.Stack;
/**
 * 跳表节点
 * @param <T>
 */
class SkipNode<T> {
    /**
     * 层级 level=0 是最底层
     */
    int level;
    /**
     * key值 key从大到小
     */
    int key;
    /**
     * 数据
     */
    T value;
    /**
     * 下一节点指针
     */
    SkipNode right;
    /**
     * 下一层节点指针
     */
    SkipNode down;

    public SkipNode(int key, T value) {
        this.key = key;
        this.value = value;
    }
}

/**
 * 跳表
 * 跳表本质是
 */
public class SkipList<T> {
    //最大的层
    final int MAX_LEVEL = 32;
    // 层高
    private int highLevel;
    /**
     * 头结点
     */
    private SkipNode head;
    // 用于投掷硬币
    private Random random;

    SkipList() {
        random = new Random();
        head = new SkipNode(Integer.MIN_VALUE, null);
        highLevel = 0;
    }



    /**
     * 获取
     *
     * @param key
     */
    public void get(int key) {

    }

    /**
     * 跳表多层级有序链表结构
     * 搜索时从上到下搜索
     *
     * @param key
     * @return
     */
    private SkipNode<T> search(int key) {
        SkipNode temp = head;
        while (temp != null) {
            // 找到了
            if (temp.key == key) {
                if (temp.down == null) {
                    return temp;
                } else {
                    temp = temp.down;
                }
            }
            // 右侧没有了，下降查找
            else if (temp.right == null) {
                temp = temp.down;
            }
            // 如果右侧节点大于当前key,说明到底边界，下降搜索
            else if (temp.right.key > key) {
                temp = temp.down;
            } else {
                // 小于右侧，继续向右查找
                temp = temp.right;
            }
        }
        return null;
    }

    public void add(SkipNode node) {
        SkipNode existNode = search(node.key);
        // 如果存在，则更新
        if (existNode != null) {
            existNode.value = node.value;
            return;
        }

        // 没找到，则寻找合适的位置进行插入
        // 插入时，会调整各层的索引节点
        // 每次向下查询，记录每层的节点，当找到最底层时，得到一个每一层合适节点的堆栈
        Stack<SkipNode> levelNodes = new Stack<>();
        SkipNode temp = head;
        while (temp != null) {
            // 如果右侧没有了，则下降
            if (temp.right == null) {
                levelNodes.add(temp);
                temp = temp.down;
            }
            // 如果右侧的key大于key,说明已到边界,继续向下
            else if (temp.right.key > node.key ) {
                levelNodes.add(temp);
                temp = temp.down;
            } else {
                temp = temp.right;
            }
        }
        // 通过堆栈中的每一层合适节点，插入需要插入的数据
        int level = 1;
        SkipNode downNode = null;
        while (!levelNodes.isEmpty()) {
            // 取出每一层节点待插入的左侧节点
            temp = levelNodes.pop();
            SkipNode newNode = new SkipNode(node.key, null);
            newNode.level = level;
            newNode.down = downNode;

            // 记录本次下层节点
            downNode = temp;

            // 如果是最底层，保存数据，其他层不保存数据
            if (newNode.down == null) {
                newNode.value = node.value;
            }

            // 如果右侧为空，则直接插入右侧
            if (temp.right == null) {
                temp.right = newNode;
            }
            // 右侧还有节点，插入在两者之间
            else {
                newNode.right = temp.right;
                temp.right = newNode;
            }

            // 如果层数已经很大，则结束
            if (level > MAX_LEVEL) {
                break;
            }
            // 随机抽选，有1/2的机会被抽中作为上一层的索引
            if (random.nextDouble() > 0.5) {
                break;
            }
            // 被抽中，进入下一个循环
            level++;

            // 如果层数已经超出当前现有层级，重新构建head
            if (level > highLevel) {
                highLevel = level;
                SkipNode newHead = new SkipNode(Integer.MIN_VALUE, null);
                newHead.down = head;
                head = newHead;
                // 将新head,加入到堆栈中，进入上一层节点插入循环
                levelNodes.add(newHead);
            }
        }
    }

    /**
     * 删除
     */
    public void delete(int key) {
        SkipNode temp = head;
        while (temp != null) {
            if (temp.right == null) {
                temp = temp.down;
            }
            // 如果右侧是key,则右侧需要删除，并继续向下查找
            else if (temp.right.key == key) {
                temp.right = temp.right.right;
                temp = temp.down;
            } else if (temp.right.key > key) {
                temp = temp.down;
            } else {
                temp = temp.right;
            }
        }
    }

    public void printList() {
        System.out.println();
        SkipNode teamNode = head;
        SkipNode last = teamNode;
        while (last.down != null) {
            last = last.down;
        }
        int index = 0;
        while (teamNode != null) {
            SkipNode enumNode = teamNode.right;
            SkipNode enumLast = last.right;
            System.out.printf("head%s->", enumNode.level);
            while (enumLast != null && enumNode != null) {
                if (enumLast.key == enumNode.key) {
                    System.out.printf("%-5s", enumLast.key + "->");
                    enumLast = enumLast.right;
                    enumNode = enumNode.right;
                } else {
                    enumLast = enumLast.right;
                    System.out.printf("%-5s", "");
                }
            }
            teamNode = teamNode.down;
            index++;
            System.out.println("\n----------------------------------");
        }
    }

    public static void main(String[] args) {
        SkipList<Integer> list = new SkipList<Integer>();
        for (int i = 1; i < 20; i++) {
            list.add(new SkipNode(i, 666));
            list.printList();
        }

        list.delete(4);
        list.delete(8);
        list.printList();
    }
}