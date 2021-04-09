package com.xianyanyang;

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
                if (INSTANCE != null) {
                    INSTANCE = new LazySingleton05();
                }
            }
        }
        return INSTANCE;
    }
}
