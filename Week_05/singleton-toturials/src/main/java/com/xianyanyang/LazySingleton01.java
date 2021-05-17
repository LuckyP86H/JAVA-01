package com.xianyanyang;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 懒汉模式01
 * 缺点：多线程会创建多个对象
 */
public class LazySingleton01 {

    private static LazySingleton01 INSTANCE;

    private LazySingleton01() {

    }

    public static LazySingleton01 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazySingleton01();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(LazySingleton01.getInstance());
                }
            }).start();
        }
        countDownLatch.countDown();
    }
}
