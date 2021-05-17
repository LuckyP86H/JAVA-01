package com.xianyanyang;

import java.util.concurrent.CountDownLatch;

/**
 * 懒汉模式04
 * 缺点：double check ，解决了多线程的安全问题，
 * 但是会出现指令重排的问题，在第一个线程INSTANCE=new LazySingleton04()后，
 * 指令重排了以后，INSTANCE提前做了栈指向堆的指令
 * 此时另一个线程判断是否有INSTANCE的时候，会获取到一个半初始化的对象
 */
public class LazySingleton04 {

    private static int value = 888;

    private static LazySingleton04 INSTANCE;

    private LazySingleton04() {

    }

    public static LazySingleton04 getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton04.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton04();
                }
            }
        }
        return INSTANCE;
    }
}
