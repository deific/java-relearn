/*******************************************************************************
 * @(#)CacheLinePadding.java 2021年01月12日 21:34
 * Copyright 2021 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.learn.juc;

/**
 * <b>Application name：</b> CacheLinePadding.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2021 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2021年01月12日 21:34 <br>
 * <b>@author：</b> <a href="mailto:chensg@miyzh.com"> chensg </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
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