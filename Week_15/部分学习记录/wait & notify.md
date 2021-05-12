# 【原创】 wait & notify 

## 线程同步

> 线程同步：当有一个线程在对内存进行操作时，其他线程都不可以对这个内存地址进行操作，直到该线程完成操作， 其他线程才能对该内存地址进行操作，而其他线程又处于等待状态，实现线程同步的方法有很多，临界区对象就是其中一种。

一般情况下，**创建一个线程是不能提高程序的执行效率的，所以要创建多个线程**。但是**多个线程同时运行的时候可能调用线程函数，在多个线程同时对同一个内存地址进行写入，由于CPU时间调度上的问题，写入数据会被多次的覆盖，所以就要使线程能够同步**。**同步就是协同步调，按预定的先后次序进行运行。如：你说完，我再说**。

“同”字从字面上容易理解为一起动作，其实不是，“同”字应是指协同、协助、互相配合。如进程、线程同步，可理解为进程或线程A和B一块配合，A执行到一定程度时要依靠B的某个结果，于是停下来，示意B运行；B依言执行，再将结果给A；A再继续操作。

所谓同步，就是在发出一个功能调用时，在没有得到结果之前该调用就不返回，同时其它线程也不能调用这个方法。

## synchronized

wait()和notify()一系列的方法，是属于对象的，不是属于线程的。它们用在线程同步时，synchronized语句块中。

我们都知道，在synchronized语句块中，同一个对象，一个线程在执行完这一块代码之前，另一个线程如果传进来的是同一个object，是不能进入这个语句块的。也就是说，同一个对象是不能同时被两个线程用来进入synchronized中的。这就是线程同步。

先用通俗一点的语言来解释一下wait()和notify()：

**wait()意思是说，我等会儿再用这把锁，CPU也让给你们，我先休息一会儿！**

**notify()意思是说，我用完了，你们谁用？**

> **wait()** 会让出对象锁，同时，当前线程休眠等待被唤醒，如果不被唤醒，就一直等在那儿。
>
> **notify()** 并不会让当前线程休眠，但会唤醒休眠的线程。



### 第一个例子 thread1 wait()  | thread2 notify()

```java
package com.xianyanyang.thread;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;

public class WaitNotifyTest {
    
    @Test
    public void testWaitNotify() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final Object object = new Object();
        Thread thread1 = new Thread() {
            public void run() {
                System.out.println("thread1获得CPU时间片...");
                synchronized (object) {
                    System.out.println("object的锁被thread1占有");
                    System.out.println("thread1执行中...");
                    try {
                        Thread.sleep(5000);
                        System.out.println("thread1让出了锁和CPU");
                        object.wait();
                        System.out.println("thread1重新获得锁和CPU");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread1执行完成");
                }
                countDownLatch.countDown();
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                System.out.println("thread2获得CPU时间片...");
                synchronized (object) {
                    System.out.println("object的锁被thread2占有");
                    System.out.println("thread2执行中...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread2唤醒等待中的线程");
                    object.notify();
                    System.out.println("thread2唤醒完毕");
                    System.out.println("thread2继续执行中...");
                }
                countDownLatch.countDown();
            }
        };
        System.out.println("thread1启动...");
        thread1.start();
        System.out.println("thread2启动...");
        thread2.start();
        countDownLatch.await();
    }
}
```

这第一个例子很简单，写了两个线程，他们都用了同一个object，thread1里面写了wait()，而thread2里面写了notify()。

执行结果如下：

```sh
thread1启动...
thread2启动...
thread1获得CPU时间片...
object的锁被thread1占有
thread2获得CPU时间片...
thread1执行中...
thread1让出了锁和CPU
object的锁被thread2占有
thread2执行中...
thread2唤醒等待中的线程
thread2唤醒完毕
thread2继续执行中...
thread1重新获得锁和CPU
thread1执行完成
```

流程可以这样解释：

thread1启动了，thread2启动了；

thread1让出了锁和CPU；

thread2获得了CPU后运行，唤醒使用object的休眠线程，thread1被唤醒后继续等待启动，thread2继续执行直到完毕，thread1获取到CPU继续执行；

值得一提的是，再强调一遍：

**wait()会让出CPU而notify()不会让出CPU**

**notify(重在于通知使用object的对象“我用完了！”**

wait重在通知其它同用一个object的线程“我暂时不用了”并且让出CPU。

所以说，看上面的顺序，

```shell
thread2执行中...
thread2唤醒等待中的线程
thread2唤醒完毕
thread2继续执行中...
```

thread2执行notify()以及下面的代码是连续的，说明它并没有因调用了notify而暂停。

###  第二个例子 thread1 wait() | thread2 wait()

那么，如果两个线程都写wait没有线程写notify会有什么现象呢？试一下就知道了。

```
    @Test
    public void bothWait() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final Object object = new Object();

        Thread thread1 = new Thread() {
            public void run() {
                System.out.println("thread1获得CPU时间片...");
                synchronized (object) {
                    System.out.println("object的锁被thread1占有");
                    System.out.println("thread1执行中...");
                    try {
                        System.out.println("thread1让出了锁和CPU");
                        object.wait();
                        System.out.println("thread1重新获得锁和CPU");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread1执行完成");
                }
                countDownLatch.countDown();
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                System.out.println("thread2获得CPU时间片...");
                synchronized (object) {
                    System.out.println("object的锁被thread2占有");
                    System.out.println("thread2执行中...");
                    try {
                        System.out.println("thread2让出了锁和CPU");
                        object.wait();
                        System.out.println("thread2重新获得锁和CPU");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread2执行完成");
                }
                countDownLatch.countDown();
            }
        };
        System.out.println("thread1启动...");
        thread1.start();
        System.out.println("thread2启动...");
        thread2.start();
        countDownLatch.await();
    }
```

执行结果如下：

```
thread1启动...
thread2启动...
thread1获得CPU时间片...
object的锁被thread1占有
thread1执行中...
thread1让出了锁和CPU
thread2获得CPU时间片...
object的锁被thread2占有
thread2执行中...
thread2让出了锁和CPU
```

然后就是一直等待。

道理很显然，T1先启动，然后wait了，T2获得了锁和CPU，在没有其它线程与它竞争的情况下，T2执行了，然后也wait了。在这里，两个线程都在等待，没有其它线程将它们notify，所以结果就是无休止地等下去。说明了一点，**wait()后如果没有其它线程将它notify()，是绝不可能重新启动的**。不可能因为目前没有线程占用CPU，某一个正在等待的线程就自动重启。

###  第三个例子 thread1 wait() |  thread2 notify() |  thread3 wait()| thread4 notify()

下面，我再把它改一下，写四个线程，分别是:

```
package com.xianyanyang.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class WaitNotifyTest {

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
```

执行结果：

```
thread1启动...
thread2启动...
thread3启动...
thread4启动...
thread2--notify获得CPU时间片...
thread3--wait获得CPU时间片...
thread1--wait获得CPU时间片...
thread3--wait等待object的锁
thread4--notify获得CPU时间片...
thread2--notify等待object的锁
thread4--notify等待object的锁
object的锁被thread3--wait占有
thread1--wait等待object的锁

thread3--waitstart

thread3--wait让出了锁和CPU
object的锁被thread1--wait占有

thread1--waitstart

thread1--wait让出了锁和CPU
object的锁被thread4--notify占有

thread4--notifystart
thread4--notify唤醒等待中的线程
thread4--notify唤醒完毕
thread4--notifyend

object的锁被thread2--notify占有

thread2--notifystart
thread2--notify唤醒等待中的线程
thread2--notify唤醒完毕
thread2--notifyend

thread1--wait重新获得锁和CPU
thread1--waitend
thread3--wait重新获得锁和CPU
thread3--waitend
```

简化日志：

```
thread3--waitstart

thread1--waitstart

thread4--notifystart
thread4--notifyend

thread2--notifystart
thread2--notifyend

thread1--waitend
thread3--waitend
```

thread2和thread4由于是写的notify()，它们的start和end总是成对出现。

thread1和thread4由于是写的wait()，它们start后，下一个绝不可能是它的end。

**我们的wait和notify是针对同一个object的，而非线程。我们这一篇都在讲对象锁，而不是线程。**

### 第四个例子 thread notify()

```
    @Test
    public void testOnlyNotify() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object object = new Object();

        Thread thread1 = createNotifyThread(countDownLatch, object, "thread1");
        System.out.println("thread1启动...");
        thread1.start();
        countDownLatch.await();
    }
```

执行结果：

```
thread1启动...
thread1获得CPU时间片...
thread1等待object的锁
object的锁被thread1占有
thread1 start
thread1唤醒等待中的线程
thread1唤醒完毕
thread1 end
```

如果没有线程在wait，调用notify是不会有什么问题的。

参考博客： https://blog.csdn.net/u013517229/article/details/56671117

