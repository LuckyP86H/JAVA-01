package com.xianyanyang.thread;

import org.junit.Test;

public class SleepTest {
    @Test
    public void name() throws InterruptedException {
        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName()+" task completed");
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+" exit ");
    }



}
