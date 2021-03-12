package com.xianyanyang.thread;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    @Test
    public void testBlockingQueue() throws InterruptedException {
        final BlockingQueue queue = new ArrayBlockingQueue(1);

        new Thread(new Runnable() {
            public void run() {
                try {
                    queue.put(Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        queue.take();
        System.out.println(Thread.currentThread().getName());
    }
}
