package com.xianyanyang.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * A synchronization aid that allows one or more threads to wait until
 * a set of operations being performed in other threads completes.
 * 它是一个同步工具类，允许一个或多个线程一直等待，直到其他线程运行完成后再执行。
 *
 * countDown():计数-1
 * await():当计数为0，就往下走
 */
public class CountDownLatchTest {

    private static int THREAD_COUNT = 10;

    /**
     * 在多个线程任务完成后，进行汇总合并测试
     *
     * @throws InterruptedException
     */
    @Test
    public void testCountDownLatch() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            Executor executor = new Executor() {
                public void execute(Runnable command) {
                    new Thread(command).start();

                }
            };
            executor.execute(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "到达会议室");
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "说：开会！");
    }


    /**
     * 子线程等待主线程通过CountDownLatch当作信号，并发执行。
     */
    @Test
    public void testConcurrency() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        countDownLatch.await();
                        System.out.println(Thread.currentThread().getName() + "到达终点！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println(Thread.currentThread().getName() + "说：各就各位，预备...");
        Thread.sleep(1000);
        System.out.println("跑！");
        countDownLatch.countDown();
    }
}
