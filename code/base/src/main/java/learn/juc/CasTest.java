package learn.juc;

/**
 * @description:
 * @author: deific
 * @createDate: 2021-04-15 17:56
 * @version: 1.0
 */
public class CasTest {


    private volatile long count = 0;


    public void test() {
        
        Runnable run = () -> {
            for (int i = 0; i < 100000000; i++) {
                count++;
            }
            System.out.println(Thread.currentThread().getName() + " is done.");
        };

        Thread t1 = new Thread(run);
        Thread t2 = new Thread(run);
        t1.start();
        t2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count is = " + count);
    }

    public static void main(String[] args) {
        CasTest casTest = new CasTest();
        casTest.test();
    }

}
