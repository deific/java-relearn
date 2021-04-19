package learn.aop.proxy;

import learn.aop.Test;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: deific
 * @createDate: 2021-04-19 20:45
 * @version: 1.0
 */
public class CglibProxyTest implements MethodInterceptor {

    private Test test;

    public CglibProxyTest(Test test) {
        this.test = test;
    }

    public static <T extends Test> Test newProxyInstance(Test test) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(test.getClass());
        enhancer.setCallback(new CglibProxyTest(test));
        return (Test) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        return method.invoke(test, objects);
        return methodProxy.invokeSuper(o, objects);
    }
}
