package com.xianyanyang.thread;

import org.junit.Test;

public class JoinTest {

    @Test
    public void testJoin() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "等女票下班中...");
        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + "下班");
            }
        });
        thread.start();
        thread.join();
        System.out.println(Thread.currentThread().getName() + "和女票一起回家！");
    }
}
