package com.xianyanyang;

import java.util.concurrent.CountDownLatch;

/**
 * 懒汉模式03
 * 缺点：锁的粒度变小了，会出现线程不安全的问题，参见CAS原理
 */
public class LazySingleton03 {

    private static LazySingleton03 INSTANCE;

    private LazySingleton03() {

    }

    public static LazySingleton03 getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton03.class) {
                INSTANCE = new LazySingleton03();
            }
        }
        return INSTANCE;
    }
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(LazySingleton03.getInstance());
                }
            }).start();
        }
        countDownLatch.countDown();
    }
}
