package learn.aop;

import learn.aop.proxy.CglibProxyTest;
import learn.aop.proxy.DecoratorTest;
import learn.aop.proxy.DynamicProxyTest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description:
 * @author: deific
 * @createDate: 2021-04-19 20:49
 * @version: 1.0
 */
public class ProxyPerfTester {

    private static int a;

    public static void main(String[] args) {

        ProxyPerfTester test = new ProxyPerfTester();
        //创建测试对象；
        Test nativeTest = new TestImpl();
        Test decorator = new DecoratorTest(nativeTest);
        Test dynamicProxy = DynamicProxyTest.newProxyInstance(new TestImpl());
        Test cglibProxy = CglibProxyTest.newProxyInstance(new TestImpl());

        //预热一下；
        int preRunCount = 10000;
        runWithoutMonitor(nativeTest, preRunCount);
        runWithoutMonitor(decorator, preRunCount);
        runWithoutMonitor(cglibProxy, preRunCount);
        runWithoutMonitor(dynamicProxy, preRunCount);

        //执行测试；
        Map<String, Test> tests = new LinkedHashMap<String, Test>();
        tests.put("Native   ", nativeTest);
        tests.put("Decorator", decorator);
        tests.put("Dynamic  ", dynamicProxy);
        tests.put("Cglib    ", cglibProxy);
        int repeatCount = 10;
        int runCount = 1000000;
        runTest(repeatCount, runCount, tests);
        runCount = 50000000;
        runTest(repeatCount, runCount, tests);
    }

    private static void runTest(int repeatCount, int runCount, Map<String, Test> tests){
        Map<String, Long> costMap = new LinkedHashMap<String, Long>();
        System.out.println(String.format("\n==================== run test : [repeatCount=%s] [runCount=%s] [java.version=%s] ====================", repeatCount, runCount, System.getProperty("java.version")));
        for (int i = 0; i < repeatCount; i++) {
            System.out.println(String.format("\n--------- test : [%s] ---------", (i+1)));
            for (String key : tests.keySet()) {
                long cost = runWithMonitor(tests.get(key), runCount, key);
                if (costMap.containsKey(key)) {
                    costMap.put(key, costMap.get(key) + cost);
                } else {
                    costMap.put(key, cost);
                }
            }
        }
        System.out.println(String.format("\n--------- test [%s] average time ---------", repeatCount));
        for (String key : costMap.keySet()) {
            System.out.println("["+key + "] Elapsed Average Time:" + costMap.get(key) / repeatCount + "ms");
        }
    }

    private static void runWithoutMonitor(Test test, int runCount) {
        for (int i = 0; i < runCount; i++) {
            test.test(i);
        }
    }

    private static long runWithMonitor(Test test, int runCount, String tag) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < runCount; i++) {
            test.test(i);
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println("["+tag + "] Elapsed Time:" + cost + "ms");
        return cost;
    }
}
