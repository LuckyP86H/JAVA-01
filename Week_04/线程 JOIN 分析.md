# 线程 JOIN 分析

## 示例一

### 代码：

```java
public class MyThread extends Thread {

    private String name;
    private Object object;

    public MyThread(String name,Object object) {
        this.name = name;
        this.object =object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 3; i++) {
                System.out.println(name + "-" + i);
            }
        }
    }
}
```

**注意：run()方法中，同步块加锁的是this对象，也就是线程对象自己。**

```java
@Test
public void testJoinThread() {
	MyThread thread = new MyThread("thread",new Object());
    thread.start();
    synchronized (thread) { //主线程main获取到了object的锁
    	for (int i = 0; i < 10; i++) {
        	if (i == 5) {
            	try {
                	thread.join();
                } catch (InterruptedException e) {
                	e.printStackTrace();
                 }
            	}
            System.out.println(Thread.currentThread().getName() + " -- " + i);
        }
    }
}
```

### 执行结果：

```shell
main -- 0
main -- 1
main -- 2
main -- 3
main -- 4
thread-0
thread-1
thread-2
main -- 5
main -- 6
main -- 7
main -- 8
main -- 9
```

### 分析：

主线程main线程轮询打印，直到i为5的时候，thread线程插队，主线程等待，直到thread线程执行完毕，主线程继续执行直到结束。

那thread.join()是如何做到让主线程main进行等待的呢？分析一下join的源码。

```java
public final void join() throws InterruptedException {
	join(0);
}
```

```java
public final synchronized void join(long millis) throws InterruptedException {
	long base = System.currentTimeMillis();
    long now = 0;
    
    if (millis < 0) {
        throw new IllegalArgumentException("timeout value is negative");
    }

    if (millis == 0) {
        while (isAlive()) {
            wait(0);
        }
    } else {
        while (isAlive()) {
            long delay = millis - now;
            if (delay <= 0) {
                break;
            }
            wait(delay);
            now = System.currentTimeMillis() - base;
        }
     }
}
```

核心代码为第二个方法，注意：

1、方法上加了同步块synchronized

2、while (isAlive()) {
            wait(0);
        }

wait()是Object类的一个native本地方法，具体实现是由JVM通过C++/C代码实现的，一般是和synchronized搭配使用，当object.wait()的时候，表示持有该对象的线程主动放弃锁以及CPU，进入了阻塞状态，等待该对象通过notify()/notifyAll()进行唤醒。

在这里，通过了while自旋的方式，判断thread本身的状态是否存货，如果是存活的，则让拥有该线程对象thread的主线程main进行wait（），也就达到了主线程一直等待直到thread线程执行完毕再唤醒主线程的效果。

此处未看到有对主线程的唤醒操作，该操作再jvm的代码中执行，jdk代码中没有相关逻辑展现。

## 示例二

### 代码

```java
public class MyThread extends Thread {

    private String name;
    private Object object;

    public MyThread(String name,Object object) {
        this.name = name;
        this.object =object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public void run() {
        synchronized (this.object) {
            for (int i = 0; i < 3; i++) {
                System.out.println(name + "-" + i);
            }
        }
    }
}
```

注意，此处和示例一的代码不同，此处同步块synchronized中的获取到的是对象thread.object的锁。

```java
@Test
public void testJoinObject() {
    MyThread thread = new MyThread("thread",new Object());
    thread.start();
    Object oo = thread.getObject();
    synchronized (oo) { 
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " -- " + i);
        }
    }
}
```

### 执行结果

```shell
main -- 0
main -- 1
main -- 2
main -- 3
main -- 4
```

### 分析

也就是主线程main执行到了i=5的时候，当thread要执行run()方法的时候，无法继续执行，原因是：主线程main代码中同步块synchronized中获取到了oo的锁，当thread执行到了join方法的时候，执行了thread.wait()，实际效果是主线程main释放了对thread对象的锁，但是并未释放对oo的锁，所以线程thread在执行run的时候，遇到了阻塞。
