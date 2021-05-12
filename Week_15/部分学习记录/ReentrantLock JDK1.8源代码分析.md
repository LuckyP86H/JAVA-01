# ReentrantLock JDK1.8源代码分析

从**JDK1.5**开始，java支持由**Doug Lea**开发的java.util.concurrent.locks.ReentrantLock，可用其替代java自带的synchronized同步块关键字实现线程同步。

## 加锁

```java
@Test
public void testReentrantLock() {
    ReentrantLock lock = new ReentrantLock();
    lock.lock();
}
```

首先，ReentrantLock的构造源代码如下：

```java
private final Sync sync;
public ReentrantLock() {
    sync = new NonfairSync();
}
```

ReentrantLock有公平锁（FairSync）和非公平锁（NonfairSync）两种，构造默认为非公平锁NonfairSync；公平锁和非公平锁均实现了Sync接口，该接口集成了大名鼎鼎的AbstractQueuedSynchronizer（AQS）。

ReentrantLock的lock方法源代码如下：

```java
    public void lock() {
        sync.lock();
    }
```

进入NonfairSync的lock源代码：

```java
final void lock() {
    if (compareAndSetState(0, 1)) //以cas自旋的方式尝试将AQS的state设置为1
        setExclusiveOwnerThread(Thread.currentThread());//如果AQS的state设置成功，将该线程设置为当前拥有该对象锁的线程
    else
        acquire(1);
}
```

 

```java
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
```



```java
protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}
```

```java
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread(); //获取当前线程
    int c = getState(); //获取状态，即当前的锁被重入的次数
    if (c == 0) { // 如果是0，表示当前的锁没有被任何线程持有
        if (compareAndSetState(0, acquires)) { //以cas自旋的方式获取锁
            setExclusiveOwnerThread(current); //将当前线程标记为锁的持有者
            return true; //返回true，则tryAcquire返回true,则!tryAcquire(arg)返回false，则无需执行acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) 的代码，获得锁成功。
        selfInterrupt();
        }
    }//如果当前state状态不是0，则该锁已经被某个线程持有
    else if (current == getExclusiveOwnerThread()) {//如果当前线程和持有该锁的线程为同一个线程
        int nextc = c + acquires; //则state++
        if (nextc < 0) // overflow 
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;//返回true，则tryAcquire返回true,则!tryAcquire(arg)返回false，则无需执行acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) 的代码，获得锁成功。
    }
    //当前的state不是0，且该锁也不是被当前线程所持有
    return false;//返回fase，则tryAcquire返回false，则!tryAcquire(arg)返回true，继续执行acquireQueued(addWaiter(Node.EXCLUSIVE), arg))代码
}
```



```java
private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode); //
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    if (pred != null) { //如果尾节点不是空
        node.prev = pred;
        if (compareAndSetTail(pred, node)) { //cas自旋将node节点加到为尾节点
            pred.next = node;
            return node;
        }
    }
    enq(node);
    return node;
}
```

```
private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode); 
    Node version = tail;
    if (version != null) { //如果尾节点不是空
        node.prev = version;
        if (compareAndSetTail(version, node)) { //cas自旋将node节点加到为尾节点
            version.next = node;
            return node;
        }
    }
    enq(node);
    return node;
}
```



```java
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

```

```



**它提供了objectFieldOffset()方法用于获取某个字段相对Java对象的“起始地址”的偏移量，也提供了getInt、getLong、getObject之类的方法可以使用前面获取的偏移量来访问某个Java对象的某个字段。**



CAS包含三个操作数，需要独写的内存位置V（0001），进行比较的值A和拟写入的新的值B，当且仅当V（0001）的值等于A的时候，CAS才会通过原子方式用新值来更新V的值。



CAS上边获得 theUnsafe 对象是java内部使用的，因为 JDK源码中对这个类进行了严格限制，我们不能通过常规new的方式去获取该类的实例，也不能通过Unsafe.getUnsafe 获得Unsafe对象实例；

那么我们通过什么方式获得该对象实例，这里就用到 强大的反射机制 自带暴力访问buff ：

在Unsafe类中有一个私有成员变量`theUnsafe`，因此我们可以通过反射将private单例实例的accessible设置为true，然后通过Field的get方法获取，如下。

```
Field f = Unsafe.class.getDeclaredField("theUnsafe"); //获得名为 theUnsafe 的属性 即使为私有 同样获得
f.setAccessible(true);
Unsafe unsafe = (Unsafe) f.get(null);  //如果为静态属性 则get 参数为null
```

