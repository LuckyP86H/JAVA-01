package com.xianyanyang;

import java.util.concurrent.CountDownLatch;

/**
 * 懒汉模式07
 * 内部类
 */
public enum LazySingleton07 {
    INSTANCE;

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
                    System.out.println(LazySingleton07.INSTANCE.toString());
                }
            }).start();
        }
        countDownLatch.countDown();
    }
}


