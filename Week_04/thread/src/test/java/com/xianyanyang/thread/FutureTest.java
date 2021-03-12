package com.xianyanyang.thread;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

        future.get();
        executor.shutdown();
        System.out.println(Thread.currentThread().getName());
    }
}
