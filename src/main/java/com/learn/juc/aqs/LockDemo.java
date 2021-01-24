package com.learn.juc.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁demo
 */
public class LockDemo {

    private int count;

    private MyReentrantLock lock = new MyReentrantLock(false);
    private ReentrantLock lock1 = new ReentrantLock(false);

    public void testLock() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (this) {
                    count++;
                }
//                lock.lock();
//                try {
//                    count ++;
//                } finally {
//                    lock.unlock();
//                }
            }
            System.out.println("t1=" + count);
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (this) {
                    count++;
                }

//                lock.lock();
//                try {
//                    count ++;
//                } finally {
//                    lock.unlock();
//                }
            }
            System.out.println("t2=" + count);
        });
        t1.start();
        t2.start();
    }


    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo();
        lockDemo.testLock();
    }

}