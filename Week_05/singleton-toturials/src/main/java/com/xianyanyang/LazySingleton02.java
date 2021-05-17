package com.xianyanyang;

import java.util.concurrent.CountDownLatch;

/**
 * 懒汉模式02
 * 缺点：锁的粒度较大
 */
public class LazySingleton02 {

    private static LazySingleton02 INSTANCE;

    private LazySingleton02() {

    }

    public static synchronized LazySingleton02 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazySingleton02();
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
                    System.out.println(LazySingleton02.getInstance());
                }
            }).start();
        }
        countDownLatch.countDown();
    }
}
