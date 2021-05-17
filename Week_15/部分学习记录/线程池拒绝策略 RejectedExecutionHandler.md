# 线程池拒绝策略 RejectedExecutionHandler

## CallerRunsPolicy

任务将会被任务的调用方线程执行，如果线程池关闭，那么任务将会被抛弃。

```java
public class CallerRunsPolicyTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new LinkedBlockingDeque(2));
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "[" + finalI + "]");
            });
        }
    }
}
```

**执行结果：由主线程接管多余的任务。**

```
main[4]
main[5]
main[6]
main[7]
main[8]
main[9]
pool-1-thread-2[1]
pool-1-thread-2[2]
pool-1-thread-1[0]
pool-1-thread-2[3]
```

## AbortPolicy

再有任务进来直接抛出RejectedExecutionException异常。

```java
public class AbortPolicyTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new LinkedBlockingDeque(2));
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "[" + finalI + "]");
            });
        }
    }
}
```

**执行结果：抛出拒绝任务异常。**

```
Exception in thread "main" pool-1-thread-1[0]
pool-1-thread-2[1]
pool-1-thread-1[2]
pool-1-thread-2[3]java.util.concurrent.RejectedExecutionException: Task com.xianyanyang.thread.AbortPolicyTest$$Lambda$1/17290025@14c265e rejected from java.util.concurrent.ThreadPoolExecutor@8139f0[Running, pool size = 2, active threads = 2, queued tasks = 2, completed tasks = 0]
	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2047)
	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:823)
	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1369)
	at com.xianyanyang.thread.AbortPolicyTest.main(AbortPolicyTest.java:15)
```

### DiscardPolicy

将任务抛弃。

```java
public static void main(String[] args) {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new LinkedBlockingDeque(2));
    threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

    for (int i = 0; i < 10; i++) {
        int finalI = i;
        threadPoolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "[" + finalI + "]");
        });
    }
}
```

**执行结果：抛弃新任务，注意index是0-3*

```
pool-1-thread-1[0]
pool-1-thread-1[2]
pool-1-thread-1[3]
pool-1-thread-2[1]
```

### DiscardOldestPolicy

摒弃最老没有处理的任务，重试新进来的任务，如果线程池关闭，则丢弃。

```java
public static void main(String[] args) {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new LinkedBlockingDeque(2));
    threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

    for (int i = 0; i < 10; i++) {
        int finalI = i;
        threadPoolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "[" + finalI + "]");
        });
    }
}
```

**执行结果：新的任务争夺线程。**

```
pool-1-thread-1[0]
pool-1-thread-1[8]
pool-1-thread-2[1]
pool-1-thread-1[9]
```

