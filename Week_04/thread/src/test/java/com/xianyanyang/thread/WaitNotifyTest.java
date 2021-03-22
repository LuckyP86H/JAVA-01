package com.xianyanyang.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class WaitNotifyTest {

    @Test
    public void testWaitNotify() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final Object object = new Object();

        Thread thread1 = createWaitThread(countDownLatch, object, "thread1");

        Thread thread2 = createNotifyThread(countDownLatch, object, "thread2");
        System.out.println("thread1启动...");
        thread1.start();
        System.out.println("thread2启动...");
        thread2.start();
        countDownLatch.await();
    }

    @Test
    public void bothWait() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final Object object = new Object();

        Thread thread1 = createWaitThread(countDownLatch, object, "thread1");

        Thread thread2 = createWaitThread(countDownLatch, object, "thread2");
        System.out.println("thread1启动...");
        thread1.start();
        System.out.println("thread2启动...");
        thread2.start();
        countDownLatch.await();
    }

    @Test
    public void testGroupWaitNotify() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(4);
        final Object object = new Object();
        Thread thread1 = createWaitThread(countDownLatch, object, "thread1--wait");
        Thread thread2 = createNotifyThread(countDownLatch, object, "thread2--notify");
        Thread thread3 = createWaitThread(countDownLatch, object, "thread3--wait");
        Thread thread4 = createNotifyThread(countDownLatch, object, "thread4--notify");
        System.out.println("thread1启动...");
        thread1.start();
        System.out.println("thread2启动...");
        thread2.start();
        System.out.println("thread3启动...");
        thread3.start();
        System.out.println("thread4启动...");
        thread4.start();
        countDownLatch.await();
    }

    @Test
    public void testOnlyNotify() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object object = new Object();

        Thread thread1 = createNotifyThread(countDownLatch, object, "thread1");
        System.out.println("thread1启动...");
        thread1.start();
        countDownLatch.await();
    }

    private Thread createNotifyThread(final CountDownLatch countDownLatch, final Object object, final String name) {
        return new Thread() {
            public void run() {
                System.out.println(name + "获得CPU时间片...");
                System.out.println(name + "等待object的锁");
                synchronized (object) {
                    System.out.println("object的锁被" + name + "占有");
                    System.out.println(name + " start");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + "唤醒等待中的线程");
                    object.notify();
                    System.out.println(name + "唤醒完毕");
                    System.out.println(name + " end");
                }
                countDownLatch.countDown();
            }
        };
    }

    private Thread createWaitThread(final CountDownLatch countDownLatch, final Object object, final String name) {
        return new Thread() {
            public void run() {
                System.out.println(name + "获得CPU时间片...");
                System.out.println(name + "等待object的锁");
                synchronized (object) {
                    System.out.println("object的锁被" + name + "占有");
                    System.out.println(name + " start");
                    try {
                        Thread.sleep(5000);
                        System.out.println(name + "让出了锁和CPU");
                        object.wait();
                        System.out.println(name + "重新获得锁和CPU");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + " end");
                }
                countDownLatch.countDown();
            }
        };
    }
}
