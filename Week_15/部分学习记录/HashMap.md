# HashMap

## 负载因子

负载因子和扩容机制有关，如果当前容器的容量达到了设定的最大值就要开始进行扩容操作。他的作用很简单，相当于是一个扩容机制的阈值，当超过了这个阈值，就会触发扩容机制，HashMap源码负载因子默认为0.75。

> 举例：当前的容器的容量是16，负载因子是0.75，16*0.75=12，也就是说，当容量达到了12的时候就会进行扩容的操作。



## 底层数据结构

![HashMap](F:\jvm\md\pic\HashMap.jpeg)

HashMap的数据一开始是保存在数组里面的，当发生了Hash碰撞的时候，就会在这个数据的节点上，生成出一个链表，当链表长度达到一定数值后，就会把链表转化为红黑树。

> **Hash冲突/碰撞**
>
> 不同的关键字，通过相同的hash运算方式计算出相同的hash地址，这种现象叫做hash冲突或者hash碰撞。

**1、负载因子为1.0的时候**，当数组全部被填充了，才会发生扩容。这就带来了很大的问题，因为Hash冲突是避免不了的，当负载因子是1.0的时候，意味着会出现大量的Hash冲突。底层的红黑树变得异常复杂，对于查询效率极其不利，这种情况就是牺牲了时间来保证了空间的利用率。

![负载因子和hash冲突率](F:\jvm\md\pic\负载因子和hash冲突率.png)

**2、负载因子为0.5的时候**，也就意味着，数组的元组达到了一半就开始扩容，既然填充的元素少了，冲突也就少了，那么底层的链表长度或者是红黑树的高度就会降低，查询效率会增加。但是，这时候空间利用率就大大降低了，原本只要存储1M的数据需要2M的空间。

**总结：负载因子太少的话，时间效率提高了，但是空间利用率降低了。**

3、负载因子为0.75的时候，经过以上分析，0.75是时间和空间的权衡，通过源码可以看出。

```java
 * As a general rule, the default load factor (.75) offers a good
 * tradeoff between time and space costs.  Higher values decrease the
 * space overhead but increase the lookup cost (reflected in most of
 * the operations of the {@code HashMap} class, including
 * {@code get} and {@code put}).  The expected number of entries in
 * the map and its load factor should be taken into account when
 * setting its initial capacity, so as to minimize the number of
 * rehash operations.  If the initial capacity is greater than the
 * maximum number of entries divided by the load factor, no rehash
 * operations will ever occur.
```

大致意思是：负载因子为0.75的时候，空间利用率比较高，而且可以避免相当多的Hash冲突，使得底层的链表长度和是红黑树的高度军比较低，提高了空间的效率。

参考网址：

> http://baijiahao.baidu.com/s?id=1656137152537394906&wfr=spider&for=pc
>
> https://blog.csdn.net/weixin_45285317/article/details/104272545