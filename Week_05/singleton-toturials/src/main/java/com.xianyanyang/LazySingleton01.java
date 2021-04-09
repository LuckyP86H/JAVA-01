package com.xianyanyang;

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
}
