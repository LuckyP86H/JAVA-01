以下是对下列技术的关键点思考和经验认识，由于技术点很多，日常的一些总结和学习过程可见相应的连接，文中提出的一些技术点以及原理等均用较为简介的方式做表达。

## JVM

1、JVM的组成和特征：支持跨平台、内存自动回收GC；

2、字节码文件分析
	字节码文件组成：版本、常量池、类信息、方法信息、属性信息；  
	操作数栈运行时过程：栈帧主要包括 操作数栈+本地变量表

3、类生命周期（加载、连接、初始化）：
   加载：通过类加载器将class文件加载进入JVM内存方法区，并在堆中创建一个Class类型的对象（注意，不是Class实例对象）
   连接：校验、解析字节码文件内容，并给Class对象中的静态变量和常量赋默认值，引用连接
   初始化：构造+静态变量和常量初始化

4、类加载器：双亲委派模型+自定义类加载器（实现通过class文件、归档文件、网络地址+加解密方式 加载类）
5、JMM JVM内存模型（
	包括：线程栈+堆+程序计数器+本地方法栈+方法区
	线程栈：栈帧（操作数栈、局部变量表+返回值）+class指针
	堆：Young（Eden+from+to）+Old
6、垃圾回收
	垃圾差找方法：可达性算法+GCRoot（静态变量+栈变量+常量+本地栈变量）
	垃圾清除算法：标记清除+标记压缩+标记复制
	GC分类：串行（Serials，单GC线程）+并行（Parallel，多GC线程）+并发（CMS，工作线程+GC线程）+分区（G1，增量回收）
    优化：STW,YoungGC,FullGC,参数,分代回收
    
 学习过程总结：

1）JVM内存模型

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/JVM%E5%86%85%E5%AD%98%E7%BB%93%E6%9E%84-%E5%90%AF%E5%8A%A8%E5%8F%82%E6%95%B0.png" alt="JVM内存模型" style="zoom: 25%;" />

2）JVM组成

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/JVM%E7%BB%84%E6%88%90.jpg" style="zoom: 25%;" />

3）Java虚拟机类加载器双亲委托加载流程图

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/%E5%8F%8C%E4%BA%B2%E5%A7%94%E6%B4%BE%E6%9C%BA%E5%88%B6.jpg" style="zoom: 25%;" />

3）对象头结构

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/%E5%AF%B9%E8%B1%A1%E5%A4%B4.jpg" style="zoom:33%;" />

1、[什么是可回收垃圾回收对象](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/%E4%BB%80%E4%B9%88%E6%98%AF%E5%8F%AF%E5%9B%9E%E6%94%B6%E5%9E%83%E5%9C%BE%E5%9B%9E%E6%94%B6%E5%AF%B9%E8%B1%A1%EF%BC%9F.md)

2、[JVM和类](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/JVM%E5%92%8C%E7%B1%BB.md)

3、[JAVA字节码文件分析](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/JAVA%E5%AD%97%E8%8A%82%E7%A0%81%E6%96%87%E4%BB%B6%E5%88%86%E6%9E%90.md)

一直觉得JVM的学习是一个难点，这几年好几次想要去走第一步，通过了这次的学习，收货特别大。了解了一个java文件->class文件->Class对象->Class实例对象的演化和生命周期，了解了通过编译为字节码这个中间层+JVM虚拟机将字节码编译为不同操作系统指令实现了跨平台的优雅之处，还有很多种种JVM原理知识如上列出来的，都是我在这几个月学习到的，但是还有很多需要后续扩展的：1）很多底层基本原理的细节 2）JVM的调优实践，GC日志的分析实战。

**离技术本质不断靠近是IT从业人员的必修课。**

## NIO

1、服务端客户端交互过程：
	客户端请求服务端，经过TCP三次握手，连接建立成功；
	内核等待网卡数据到位；
	内核将缓冲区数据拷贝到用户空间缓冲区；

2、对于IO的这一块内容，理解起来相对困难，以下是自己对于IO多路复用的推演的理解，关于Netty的知识点，暂时没有罗列。

学习总结：

1）socket通信模型

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/socket.jpg" style="zoom:80%;" />

1、[IO多路复用推演](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/IO%E5%A4%9A%E8%B7%AF%E5%A4%8D%E7%94%A8%E6%8E%A8%E6%BC%94.md)


## 并发编程

1、线程（多线程的来由、线程定义、java线程和os线程的关系）

状态：new、runnable（ready+running）、 waiting 、timed_waiting、 blocked、 Terminated

Thread:start()和run()区别、join（插队）、sleep（抱着锁睡觉）、wait&notify(Object的方法、扔掉锁等待)、volatile（线程可见性+内存屏障防止指令重排，单例的演化，不保证原子性）、synchronized（1.5前后对比、锁升级、无锁+偏向锁+轻量级锁+重量级锁、属于非公平锁、CAS自旋（乐观锁机制）、对象头WordMark（锁信息、hashcode、分代年龄、偏向锁的线程ID） ）

2、线程池
  核心要素：核心线程+队列+非核心队列+拒绝策略+超时时间
  自带的线程池类型：newFixedThreadPool（队列无限制，OOM）、newSingleThreadExecutor（单线程，性能低）、newCachedThreadPool（非核心线程无限制，CPU打满）、自定义线程池

3、并发编程

1）锁：Lock（对比synchronized和Lock的区别）、ReentrantLock（默认为非公平锁，可以设置为公平锁）、Condition（模拟消费者生产者模式中的指定线程调度）、AQS（state+CAS自旋锁+队列）

2）原子操作类：AutomicLong（CAS）、LongAdder（分段锁机制，高并发下提高性能，那就是说如果现在有５个线程竞争A，如果是原子类的话，就会出现１个成功了，４个在自旋，而使用Long Addｅｒ的话，就会让５个并发操作，其实就是分段的概念，类似用空间换时间）

4）线程调度：CountDownLatch、CycleicBarrier、Semaphore

5）ConCurrentHashMap（1.7 分段锁机制、桶、扩容）

4、生产者消费者

5、主线程等待子线程结束的方式(sleep、CountDownLatch、CycleicBarrier、Semaphore、join)

6、全局计数。

 学习过程总结：

1）线程状态迁移图

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/%E7%BA%BF%E7%A8%8B%E7%8A%B6%E6%80%81%E8%BF%81%E7%A7%BB%E5%9B%BE.jpg" style="zoom:33%;" />

2）线程池任务提交流程图

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/%E7%BA%BF%E7%A8%8B%E6%B1%A0%E4%BB%BB%E5%8A%A1%E6%8F%90%E4%BA%A4%E6%B5%81%E7%A8%8B.jpg" style="zoom: 25%;" />

3）锁升级过程

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/%E9%94%81%E5%8D%87%E7%BA%A7%E8%BF%87%E7%A8%8B.jpg" style="zoom:33%;" />

1、[ReentrantLock源代码分析](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/ReentrantLock%20JDK1.8%E6%BA%90%E4%BB%A3%E7%A0%81%E5%88%86%E6%9E%90.md)

2、[wait & notify](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/wait%20%26%20notify.md)

3、[线程JOIN分析](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/%E7%BA%BF%E7%A8%8B%20JOIN%20%E5%88%86%E6%9E%90.md)

4、[对象头分析以及锁的状态](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/%E7%BA%BF%E7%A8%8B%20-%20%E5%AF%B9%E8%B1%A1%E5%A4%B4%E5%88%86%E6%9E%90%E4%BB%A5%E5%8F%8A%E9%94%81%E7%8A%B6%E6%80%81.md)

5、[多线程和并发](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%92%8C%E5%B9%B6%E5%8F%91.xmind)

**并发编程这一块和JVM是花时间最多的内容，因为抽象+知识点多+日常使用较少，但是我深知想要做高级编程工作，并发编程是入门知识，包括后续中间件和框架的学习，涉及到并发编程中的线程、锁等种种的基础知识，打牢基础，路虽漫漫却定是光明大道。**


## Spring 和 ORM 等框架

1、Spring优势：是一个开源免费的框架，支持轻量级无侵入式的企业级应用开发，其中IOC控制反转和AOP面向切面编程是其特点。

2、Spring的IOC容器：容器的加载过程、Bean的生命周期、三级缓存（解决属性循环依赖的问题）、控制反转的理念

3、生态：

Spring boot ：加速Web开发的依赖Spring框架的脚手架（约定大于配置，管理依赖）

Spring Cloud：提供分布式开发各种分布式组件的集成

1）Bean的生命周期

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/Bean%E7%9A%84%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F.jpg" alt="Bean的生命周期" style="zoom: 50%;" />



4、ORM框架：

spring-data-jpa:基于 hibernate

mybatis 

5、Collection框架：Map、Collection

总结：

1、[Mybatis 缓存](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/Mybatis%20%E7%BC%93%E5%AD%98.md)

2、[HashMap](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/HashMap.md)



## MySQL 数据库和 SQL

1、执行引擎（Innodb+myisam）
2、索引（B+Tree，B-Tree主键索引的推演+聚簇索引+非聚簇索引+回表+主键的实现策略（大小、顺序、页分裂）+hash索引、覆盖索引、联合索引、最左匹配原则、前缀索引、倒排索引）
3、锁：行锁、表锁、间隙锁、临键锁、乐观锁、悲观锁、死锁
4、优化（索引优化、MySQL优化）
5、事务：

ACID：原子性、一致性、隔离性、持久性

隔离级别：未提交读（脏读、幻读、重复读）、已提交读（重复读、幻读）、不可重复读（幻读，但是在mysql中通过临键锁解决了，不存在幻读）、串行读

MVCC：MVCC解决的是不同的事务之间读写版本冲突的问题；MVCC没办法解决不同事务之间写写冲突的问题，如果要解决可以通过两种方式，应用层面做到并发控制，串行写，不让数据库层面出现并发写；还有一种就是在数据库层面显示加锁；

6、日志文件：undolog（事务的原子性保证、MVCC的实现关键点之一）、 redolog（保证了mysql数据的持久性，采用了内部的XA两阶段提交）、 binlog（主从复制）
7、读写分离：主从复制（异步复制、半同步复制）

1）MySQL页结构

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/MySQL%E9%A1%B5%E7%BB%93%E6%9E%84.jpg" style="zoom: 33%;" />

2）主键索引行存储

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/%E4%B8%BB%E9%94%AE%E7%B4%A2%E5%BC%95%E8%A1%8C%E5%AD%98%E5%82%A8.jpg" style="zoom: 25%;" />

学习过程总结：

1、[SQL事务隔离级别](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/MySQL%E4%BA%8B%E5%8A%A1%E9%9A%94%E7%A6%BB%E7%BA%A7%E5%88%AB.md)

2、[MVCC多版本并发控制](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/MVCC%E5%A4%9A%E7%89%88%E6%9C%AC%E5%B9%B6%E5%8F%91%E6%8E%A7%E5%88%B6.md)

3、[MySQL主从复制原理](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/MySQL%E4%B8%BB%E4%BB%8E%E5%A4%8D%E5%88%B6%E5%8E%9F%E7%90%86.md)

4、[数据库事务模型](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/%E6%95%B0%E6%8D%AE%E5%BA%93%E4%BA%8B%E5%8A%A1%E6%A8%A1%E5%9E%8B.md)

以上列出的知识点都是重点进行学习的，特别是索引+ACID+MVCC+各种log+主从复制机制+锁，可以对主键索引做出推演。

## 分库分表

目前针对分库分表的实践没有参与过，所以只是从概念上去了解了什么时候需要做分库分表以及分库分表过程中需要考虑的事情，针对互联网的架构的演进做了一个流程图，分库分表也是其中一部分。

1）互联网架构演化

<img src="https://sakura521yz.oss-cn-shanghai.aliyuncs.com/java/%E4%BA%92%E8%81%94%E7%BD%91%E6%9E%B6%E6%9E%84%E6%BC%94%E5%8C%96.jpg" style="zoom:50%;" />

## RPC和微服务

1、RPC基本原理：

 - RPC ：Remote Procedure Call 远程过程调用
   - 核心机制：网络传输通信方式和协议、序列化方式、调用的方法标识
     2、RPC框架：Dubbo、Thrift、gRPC、Hessian
     3、RPC调用流程：

 - Client端通过本地的代理存根ClientStub生成代理类ProxyService；

 	1）泛型
 	2）动态代理:JDK 的InvocationHandler、CGlib字节码增强、AOP方式

 - ProxyService实例访问调用请求

 	1）ClientStub接收到调用请求后将Class全限定类名、方法名、参数等组装为指定序列化方式的网络传输消息体；
 	2）ClientStub调用远程服务地址，将消息通过某种传输协议发送到服务端；

 - ServerSkeleton处理远程调用

 	1）通过远程方式获取到请求后，通过协商的序列化方式进行反序列化；
 	2）通过解码后的请求体通过反射、差找Bean单例池（Spring框架）等方式找到本地的服务并执行；
 	3）将返回结果序列化返回给调用方；

 - ClientStub接收到信息后，将结果反序列化，返回给调用端

3、RPC框架实现：
	1）定义共享接口和类
	2）使用动态代理实现远程访问：JDK的InvocationHandler、CGLib、Spring的AOP
	3）选择序列化方式：文本、JSON、二进制流等
	4）传输协议：TCP、HTTP，通过Netty、RestTemplate、HTTPClient等方式远程调用
	5) 使用反射或者差找Bean单例池等方式在服务端查找到Class

4、微服务
概念：服务自治、HTTP协议、数据库分离、康威定理、不规定技术栈、自动化CI/CD基础设施、Devops

框架：Spring Cloud
服务治理：注册中心+路由+熔断限流+配置中心

学习过程总结：

1、[RPC基本原理](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/RPC%E5%9F%BA%E6%9C%AC%E5%8E%9F%E7%90%86.md)


## 分布式缓存

1、缓存的本质：系统的各级处理速度不匹配
2、作用：是提高系统性能的首选方案、面对高并发请求环节数据库的压力
3、适用场景：静态数据、聚合数据、读多写少的数据、热点数据
4、加载时机：

系统启动全量加载：应用系统需要延迟提供对外服务，否则会出现雪崩

懒加载：
1）同步加载：业务服务读时加载并返回
2）异步加载：1）读先返回空，异步线程加载缓存，适用于重要性不高的数据2）定时更新缓存，无需业务干涉

5、缓存使用的演化：本地缓存（内存+ORM框架  内存容量有限、集群同步方案复杂、缓存线程和业务线程抢占资源）、中间件（Redis/Memcached）
6、过期策略：LUR、按照业务权重加权过期、先进先出、定时过期
7、异常场景：
缓存穿透（缓存不存在数据+数据库也不存在数据 ）：缓存存储空值、缓存不存在则返回空同时开启异步线程同步数据（适合非实时性数据）
缓存击穿（缓存失效、数据库有数据）：1）key的更新添加全局互斥锁等待更新完成后才提供访问2）缓存不存在则返回空同时开启异步线程同步数据（适合非实时性数据）
缓存雪崩（大批量缓存数据失效）：设置不同的key的过期时间

8、中间件产品
redis：Redis 是开源免费的高性能key-value分布式内存数据库，是基于内存且支持持久化的NOSQL数据库

redis特别快的原因：1）内存操作2）指令执行为单线程，无需将时间小号在线程创建、调度等事情上3）采用了非阻塞的IO多路复用机制（事件驱动）

redis的基础数据：string、hash、list（链表）、set（去重）、sortedSet

一致性hash：针对集群的扩缩容需要保证一致性hash

日常开发过程中，使用到缓存的地方有CDN、反向代理、Redis+Spring boot，针对Redis数据的一些高级功能。

学习过程总结：

1、[Redis主从复制](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/Redis%E4%B8%BB%E4%BB%8E%E5%A4%8D%E5%88%B6.md)

2、[Redis持久化原理](https://github.com/sofia2013/JAVA-01/blob/main/Week_15/%E9%83%A8%E5%88%86%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95/Redis%E6%8C%81%E4%B9%85%E5%8C%96%E5%8E%9F%E7%90%86.md)


## 分布式消息队列

1、系统间通信方式：目前主流的为 数据库+文件+Socket+RPC+消息

2、消息中间件通用模型：procuder+MQ(exchange->queue)+consumer 

3、消息队列通信的特性： 解耦+异步+削峰填谷+内置各种场景的高效通信机制（以rabbitmq为例：点对点+workQueue+发布订阅+路由+主题）+延时队列（TTL+DeadQueue）

4、消息的可靠性保证：确认机制、分布式事务、服务质量（最多发一次+最少发一次+有且仅有一次）、服务幂等、消息重试、死信队列

5、业界主流的消息中间件：RabbitMQ、ActiveMQ、Kafka、RocketMQ

6、与Spring-boot的整合

7、业务场景：电商系统微服务解耦、日志收集、秒杀系统削峰限流、订单过期优惠券过期等延时处理、汇率同步订单状态同步等有规律的定时同步。

日常业务开发中，用到RabbitMq较多，但是在日常工作中针对消息的可靠性这一块没有做多过的考虑，是后续可以改进的地方。

半年学习已经尾声，只有感谢，感谢秦老师诚意满满的教授和人生解惑，感谢助教大明老师认真批改和日常技术的帮助，感谢班主任爽爽和烦烦负责的后勤工作，知识是无价的，时间是珍贵的，唯有珍惜这一切不停向前，方不枉此生。
感谢大家，大家一起努力！
