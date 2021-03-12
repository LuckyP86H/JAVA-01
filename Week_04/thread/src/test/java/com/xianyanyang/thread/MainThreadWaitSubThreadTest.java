package com.xianyanyang.thread;

import org.junit.Test;

import java.util.concurrent.*;

public class MainThreadWaitSubThreadTest {

    //region 1、sleepTest

    /**
     * 异步计算结果为：24157817
     * 使用时间：1 ms
     *
     * @throws InterruptedException
     */
    @Test
    public void sleepTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        new Thread(new Runnable() {
            public void run() {
                int result = sum();
                System.out.println("异步计算结果为：" + result);
            }
        }).start();
        Thread.sleep(100);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start - 100) + " ms");
    }

    //endregion sleepTest

    //region 2、countDownLatchTest

    /**
     * 异步计算结果为：24157817
     * 使用时间：80 ms
     *
     * @throws InterruptedException
     */
    @Test
    public void countDownLatchTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            public void run() {
                int result = sum();
                System.out.println("异步计算结果为：" + result);
                countDownLatch.countDown();
            }
        }).start();
        countDownLatch.await();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    //endregion

    //region 3、joinTest

    /**
     * 异步计算结果为：24157817
     * 使用时间：83 ms
     *
     * @throws InterruptedException
     */
    @Test
    public void joinTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                int result = sum();
                System.out.println("异步计算结果为：" + result);
            }
        });
        thread.start();
        thread.join();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    //endregion

    //region 4、cyclicBarrierTest

    /**
     * 异步计算结果为：24157817
     * 使用时间：81 ms
     *
     * @throws InterruptedException
     */
    @Test
    public void cyclicBarrierTest() throws InterruptedException {
        final long start = System.currentTimeMillis();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(1, new Runnable() {
            public void run() {
                System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            }
        });
        new Thread(new Runnable() {
            public void run() {
                int result = sum();
                System.out.println("异步计算结果为：" + result);
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
        Thread.sleep(100);
    }

    //endregion

    //region 5、semaphoreTest

    /**
     * 异步计算结果为：24157817
     * 使用时间：81 ms
     *
     * @throws InterruptedException
     */
    @Test
    public void semaphoreTest() throws InterruptedException {
        final long start = System.currentTimeMillis();
        final Semaphore semaphore = new Semaphore(0);
        new Thread(new Runnable() {
            public void run() {
                int result = sum();
                System.out.println("异步计算结果为：" + result);
                semaphore.release();
            }
        }).start();
        semaphore.acquire();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    //endregion semaphoreTest

    //region 6、futureTest

    /**
     * 异步计算结果为：24157817
     * 使用时间：89 ms
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void futureTest() throws ExecutionException, InterruptedException {
        final long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(new Runnable() {
            public void run() {
                int result = sum();
                System.out.println("异步计算结果为：" + result);
            }
        });
        future.get();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        executor.shutdown();
    }

    //endregion

    //region 7、blockingQueueTest

    /**
     * 异步计算结果为：24157817
     * 使用时间：92 ms
     *
     * @throws InterruptedException
     */
    @Test
    public void blockingQueueTest() throws InterruptedException {
        final long start = System.currentTimeMillis();
        final BlockingQueue queue = new ArrayBlockingQueue(1);
        new Thread(new Runnable() {
            public void run() {
                int result = sum();
                System.out.println("异步计算结果为：" + result);
                try {
                    queue.put(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        queue.take();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    //endregion

    //region 8、whileThreadIsAliveTest

    @Test
    public void whileThreadIsAliveTest() {
        final long start = System.currentTimeMillis();
        Thread t = new Thread(new Runnable() {
            public void run() {
                int result = sum();
                System.out.println("异步计算结果为：" + result);
            }
        });
        t.start();
        while (t.isAlive()) {
        }
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    //endregion

    //region private Method

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    //endregion
}
