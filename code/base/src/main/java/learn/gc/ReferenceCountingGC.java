package learn.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 引用计数器gc回收算法
 * @author: deific
 * @createDate: 2021-02-18 17:07
 * @version: 1.0
 */
public class ReferenceCountingGC {

    private static final int _1MB = 1024 * 2014;


    public static void testGC() {
        // 唯一的作用就是该对象实例占用1MB的内存
        List<Object> objList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            System.out.println("循环次数：" + i);
            ReferenceCountingGC a = new ReferenceCountingGC();
            objList.add(a);
            // 循环引用情况下，引用计数器算法将变得复杂
//            a.instance = b;
//            b.instance = a;

//            a = null;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 回收垃圾，a,b是否被回收？
//            System.gc();
        }
    }


    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[1 * _1MB];
        allocation2 = new byte[1 * _1MB];
//        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 *_1MB];
    }
    public static void main(String[] args) {
//        testGC();
        testAllocation();
    }
}
