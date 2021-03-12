package com.xianyanyang.thread;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/*  Semaphore 维护的是一个计数器,如果计数器值小于等于0,线程进入休眠。
    当某个线程使用完共享资源后,释放信号量,并将信号量内部的计数器加1,当计数器的值大于0时,之前进入休眠的线程将被唤醒并再次试图获得信号量，
    所以我们只要控制主线程堵塞，在子线程释放许可证，等待一个可用的许可证时，唤醒主线程。
    */
public class SemaphoreTest {

    private static int THREAD_COUNT = 10;

    @Test
    public void semaphoreTest() throws InterruptedException {
        System.out.println("begin");
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    //释放一个信号, 每释放一个信号就会+1，最后会得到一个可用的许可证，外面会停止等待，继续执行
                    semaphore.release();
                }
            }.start();
        }
        /**
         *  当前线程尝试去阻塞的获取1个许可证,此过程是阻塞的，它会一直等待许可证，直到发生以下任意一件事：
         *  当前线程获取了1个可用的许可证，则会停止等待，继续执行。
         *	当前线程被中断，则会抛出InterruptedException异常，并停止等待，继续执行。
         */
        semaphore.acquire();
        System.out.println("end");
    }
}
