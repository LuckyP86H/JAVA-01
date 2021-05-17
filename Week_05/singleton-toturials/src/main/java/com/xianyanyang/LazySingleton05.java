package com.xianyanyang;

import java.util.concurrent.CountDownLatch;

/**
 * 懒汉模式05
 * 优点：volatile禁止了对象操作的指令重排
 */
public class LazySingleton05 {

    private static volatile LazySingleton05 INSTANCE;

    private LazySingleton05() {

    }

    public static LazySingleton05 getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton05.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton05();
                }
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
                    System.out.println(LazySingleton05.getInstance().toString());
                }
            }).start();
        }
        countDownLatch.countDown();
    }
}
