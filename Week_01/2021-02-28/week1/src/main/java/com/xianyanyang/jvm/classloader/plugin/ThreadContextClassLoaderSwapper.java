package com.xianyanyang.jvm.classloader.plugin;

public class ThreadContextClassLoaderSwapper {

    private static final ThreadLocal<ClassLoader> classLoader = new ThreadLocal<>();

    // 替换线程上下文类加载器会指定的类加载器，并备份当前的线程上下文类加载器
    public static void replace(ClassLoader newClassLoader) {
        classLoader.set(Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(newClassLoader);
    }

    // 还原线程上下文类加载器
    public static void restore() {
        if (classLoader.get() == null) {
            return;
        }
        Thread.currentThread().setContextClassLoader(classLoader.get());
        classLoader.set(null);
    }
}
