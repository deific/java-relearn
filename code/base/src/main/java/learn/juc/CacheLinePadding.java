
package learn.juc;

public class CacheLinePadding {

    // 缓存行是为了追求极致的效率

    static class T {
//        private  long p1, p2, p3, p4, p5, p6, p7;
        public long x = 0L;
//        private  long p11, p12, p13, p14, p15, p16, p17;
    }

    public static T[] t = new T[2];

    static {
        t[0] = new T();
        t[1] = new T();
    }

    public static void main(String[] args) {
        long count = 10000000000L;

        long startTime = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < count; i++) {
                t[0].x = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < count; i++) {
                t[1].x = i;
            }
        });

        t1.start();
        t2.start();

        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }
 }