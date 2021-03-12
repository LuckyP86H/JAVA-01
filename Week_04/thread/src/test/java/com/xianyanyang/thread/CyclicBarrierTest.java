package com.xianyanyang.thread;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    private static int THREAD_COUNT = 10;

    /**
     * 子线程X执行preAction，到达等待点cyclicBarrier.await()，等待其他的线程执行...
     * 子线程X执行preAction,到达等待点cyclicBarrier.await()，等待其他的线程执行...
     * 。。。
     * 子线程X执行preAction,到达等待点cyclicBarrier.await(),所有线程到达等待点，执行unionTask
     * 子线程各自继续从等待点向下执行
     *
     */
    @Test
    public void testCyclicBarrier() throws InterruptedException {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT, unionTask());

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Runnable() {
                public void run() {
                    preAction("到达会议室");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "开始开会");
                }

                private void preAction(String 到达会议室) {
                    System.out.println(Thread.currentThread().getName() + 到达会议室);
                }
            }).start();
        }
        Thread.sleep(3000);
    }

    private Runnable unionTask() {
        return new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + "说：人都已到达会议室");
            }
        };
    }
}
