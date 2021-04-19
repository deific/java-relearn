package learn.aop.proxy;


import learn.aop.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: deific
 * @createDate: 2021-04-19 20:40
 * @version: 1.0
 */
public class DynamicProxyTest implements InvocationHandler {

    private Test test;

    public DynamicProxyTest(Test test) {
        this.test = test;
    }

    public static Test newProxyInstance(Test test) {
        return (Test) Proxy.newProxyInstance(DynamicProxyTest.class.getClassLoader(),
                new Class[]{Test.class}, new DynamicProxyTest(test));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(test, args);
    }
}
