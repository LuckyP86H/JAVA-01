package com.xianyanyang.thread.mainwait;

import com.xianyanyang.thread.mainwait.utils.SumUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class LockHomework09 {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Lock lock = new ReentrantLock(true);
        Condition condition = lock.newCondition();
        new Thread(() -> {
            try {
                lock.lock();
                int result = SumUtils.sum();
                System.out.println("异步计算结果为：" + result);
                condition.signalAll();
            } finally {
                lock.unlock();
            }

        }).start();

        lock.lock();
        condition.await();
        try {
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } finally {
            lock.unlock();
        }
    }
}
