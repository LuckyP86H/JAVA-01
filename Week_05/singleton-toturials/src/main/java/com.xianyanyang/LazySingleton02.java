package com.xianyanyang;

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
}
