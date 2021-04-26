package learn.juc.thread;

import java.util.concurrent.*;

/**
 * @description:
 * @author: deific
 * @createDate: 2021-04-25 20:17
 * @version: 1.0
 */
public class ThreadPool {


    static class MyExecutor implements Runnable {
        private String name;

        public MyExecutor(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(this.name + "start");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(this.name + "end");
            }
        }
    }

    public static void test() throws InterruptedException {
        // 直接提交
        ThreadPoolExecutor pool1 = new ThreadPoolExecutor(2, 20, 1000l, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(r + "  rejected ");
            }
        });

        for (int i = 0; i < 10; i++) {
            pool1.submit(new MyExecutor("t" + i));
        }

        System.out.println(" getActiveCount = " + pool1.getActiveCount());
        System.out.println(" getCorePoolSize = " + pool1.getCorePoolSize());
        System.out.println(" getPoolSize = " + pool1.getPoolSize());

        Thread.sleep(2000);

        System.out.println(" getActiveCount = " + pool1.getActiveCount());
        System.out.println(" getCorePoolSize = " + pool1.getCorePoolSize());
        System.out.println(" getPoolSize = " + pool1.getPoolSize());

        pool1.awaitTermination(3000, TimeUnit.MILLISECONDS);
    }

    public static void test2() throws InterruptedException {
        // 无界队列
        ThreadPoolExecutor pool1 = new ThreadPoolExecutor(2, 4, 1000l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(2), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(r + "  rejected ");
            }
        });

        for (int i = 0; i < 7; i++) {
            pool1.submit(new MyExecutor("t" + i));
        }



//        pool1.allowCoreThreadTimeOut(true);
        System.out.println(" getActiveCount = " + pool1.getActiveCount());
        System.out.println(" getCorePoolSize = " + pool1.getCorePoolSize());
        System.out.println(" getPoolSize = " + pool1.getPoolSize());

        Thread.sleep(2000);

        System.out.println(" getActiveCount = " + pool1.getActiveCount());
        System.out.println(" getCorePoolSize = " + pool1.getCorePoolSize());
        System.out.println(" getPoolSize = " + pool1.getPoolSize());

        pool1.shutdown();

    }

    public static void test3() throws InterruptedException {
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(1);
        Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(1);

    }

    public static void main(String[] args) throws InterruptedException {
        test2();
    }
}
