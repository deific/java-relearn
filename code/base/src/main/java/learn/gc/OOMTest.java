package learn.gc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 内存溢出
 * @author: deific
 * @createDate: 2021-03-28 14:39
 * @version: 1.0
 */
public class OOMTest {

    private List list = new ArrayList();

    public static void main(String[] args) {
        OOMTest oomTest = new OOMTest();
        for (;;) {
            oomTest.list.add(new BigDecimal(100));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
