package com.xianyanyang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 饿汉式单例模式
 * 缺点：在类加载的时候就完成了对象的初始化，占用了内存空间
 * 优点：不会出现多线程线程安全问题
 */
public class HungrySingleton {

    private static final HungrySingleton INSTANCE = new HungrySingleton();

    private HungrySingleton() {

    }

    public static HungrySingleton getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                public void run() {
                    HungrySingleton hungrySingleton = HungrySingleton.getInstance();
                    System.out.println(hungrySingleton.toString());
                }
            });
        }
    }
}
