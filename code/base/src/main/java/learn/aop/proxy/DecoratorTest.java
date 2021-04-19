package learn.aop.proxy;

import learn.aop.Test;

/**
 * @description:
 * @author: deific
 * @createDate: 2021-04-19 20:39
 * @version: 1.0
 */
public class DecoratorTest implements Test {

    private Test test;

    public DecoratorTest(Test test) {
        this.test = test;
    }

    @Override
    public int test(int i) {
        return test.test(i);
    }
}
