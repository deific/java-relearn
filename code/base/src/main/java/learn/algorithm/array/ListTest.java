package learn.algorithm.array;

import java.util.*;

/**
 * @description:
 * @author: deific
 * @createDate: 2021-04-25 17:07
 * @version: 1.0
 */
public class ListTest {



    public static void testList() {
        List linkedList = new LinkedList<String>();
        linkedList.add("22");


        Set treeSet = new TreeSet();
        treeSet.add("22");
        treeSet.add("11");
        treeSet.add("1.1");
        treeSet.add("12");
        treeSet.add("21");
        treeSet.add("31");

        treeSet.forEach(value -> {
            System.out.println(value);
        });


        Map treeMap = new TreeMap();
        treeMap.put("a", "sasaa");
        treeMap.put("33", "sa1sa");
        treeMap.put("cas", "sas32a");
        treeMap.put("2", "sa4sa");

        treeMap.forEach((key, value) -> {
            System.out.println(key + " -> " + value);
        });
    }

    private static int f(int n, int m){
        return n == 1 ? n : (f(n - 1, m) + m - 1) % n + 1;
    }

    public static void main(String[] args) {
        testList();

        int a = f(10, 3);
        System.out.println(a);
    }
}
