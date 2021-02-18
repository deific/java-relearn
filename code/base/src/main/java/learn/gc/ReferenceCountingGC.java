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

    public Object instance = null;

    private static final int _1MB = 1024 * 2014;

    // 唯一的作用就是该对象实例占用1MB的内存
    private byte[] bigSize = new byte[7 * _1MB];

    private static List<Object> objList = new ArrayList<>();

    public static void testGC() {

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

    public static void main(String[] args) {
        testGC();
    }
}
