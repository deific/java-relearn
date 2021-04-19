package learn.aop;

/**
 * @description:
 * @author: deific
 * @createDate: 2021-04-19 20:39
 * @version: 1.0
 */
public class TestImpl implements Test {
    @Override
    public int test(int i) {
        return i + 1;
    }
}
