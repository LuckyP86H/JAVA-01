# 作业



## 第一周 

### 第1课作业实践

1、（可选）自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和for，然后自己分析一下对应的字节码，有问题群里讨论。

2、（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法， 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。

3、（必做）画一张图，展示 Xmx、Xms、Xmn、Metaspache、DirectMemory、Xss这些内存参数的关系。

4、（可选）检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。

注意：

1、对于线上有流量的系统，慎重使用jmap命令。

2、如果没有线上系统，可以自己 run 一个 web/java 项目。或者直接查看idea进程。

课堂重点知识笔记和答疑链接，请在群里找或者向班主任索要。



###  第2课作业实践

1、本机使用 G1 GC 启动一个程序，仿照课上案例分析一下 JVM 情况 ，可以使用gateway-server-0.0.1-SNAPSHOT.jar ，注意关闭自适应参数：-XX:-UseAdaptiveSizePolicy 。

```sh
\> java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC -jar target/gateway-server-0.0.1-SNAPSHOT.jar 

\> java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseParallelGC -jar target/gateway-server-0.0.1-SNAPSHOT.jar 

\> java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseConcMarkSweepGC -jar target/gateway-server-0.0.1-SNAPSHOT.jar 

\> java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseG1GC -XX:MaxGCPauseMillis=50 -jar target/gateway-server-0.0.1-SNAPSHOT.jar 
```

使用jmap，jstat，jstack，以及可视化工具，查看jvm情况。 

mac上可以用wrk，windows上可以按照superbenchmark压测 http://localhost:8088/api/hello 查看jvm。



## 第二周 

### 第3课作业实践 

1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。 

2、使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar示例。 

3、(选做)如果自己本地有可以运行的项目，可以按照2的方式进行演练。 

4、(必做)根据上述自己对于1和2的演示，写一段对于不同GC和堆内存的总结，提交到 github。



### **第4课作业实践** 

1、（可选）运行课上的例子，以及 Netty 的例子，分析相关现象。 

2、（必做）写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801，代码提交到Github。



## 第三周

### **第5课作业实践** 

1、按今天的课程要求，实现一个网关， 

基础代码可以 fork：https://github.com/kimmking/JavaCourseCodes 02nio/nio02 文件夹下 ，实现以后，代码提交到 Github。 

1）（必做）整合你上次作业的httpclient/okhttp； 

2）（可选）:使用netty实现后端http访问（代替上一步骤）； 

3）（必做）实现过滤器 

4）（可选）：实现路由



### **第6课作业实践** 

1、（可选）跑一跑课上的各个例子，加深对多线程的理解 

2、（可选）完善网关的例子，试着调整其中的线程池参数



## 第四周

### **第7课作业实践** 

1、（选做）把示例代码，运行一遍，思考课上相关的问题。也可以做一些比较。 

2、**（必做）**思考有多少种方式，在main函数启动一个新线程，运行一个方法，拿到这 个方法的返回值后，退出主线程？ 写出你的方法，越多越好，提交到github。 

一个简单的代码参考： 

https://github.com/kimmking/JavaCourseCodes/tree/main/03concurrency/0301/src/main/java/java0/conc0303/Homework03.java



### **第8课作业实践**

1、（选做）列举常用的并发操作API和工具类，简单分析其使用场景和优缺点。

2、（选做）请思考：什么是并发？什么是高并发？实现高并发高可用系统需要考虑哪些因素，对于这些你是怎么理解的？ 

3、（选做）请思考：还有哪些跟并发类似/有关的场景和问题，有哪些可以借鉴的解决办法。

4、（必做）把多线程和并发相关知识梳理一遍，画一个脑图，截图上传到github上。可选工具：xmind，百度脑图，wps，MindManage，或其他。



## 第五周

### **第** **9** **课作业实践**

1、（选做）使 Java 里的动态代理，实现一个简单的 AOP。 

2、（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）,提交到 Github。 

3、（选做）实现一个 Spring XML 自定义配置，配置一组 Bean，例如：Student/Klass/School。 

4、（*选做，会添加到高手附加题*）

4.1 （挑战）讲网关的 frontend/backend/filter/router 线程池都改造成 Spring 配置方式；

4.2 （挑战）基于 AOP 改造 Netty 网关，filter 和 router 使用 AOP 方式实现；

4.3 （中级挑战）基于前述改造，将网关请求前后端分离，中级使用 JMS 传递消息；

4.4 （中级挑战）尝试使用 ByteBuddy 实现一个简单的基于类的 AOP；

4.5 （超级挑战）尝试使用 ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入下的 AOP。



### **第**10课作业实践

1. （选做）总结一下，单例的各种写法，比较它们的优劣。

2. （选做）maven/spring 的 profile 机制，都有什么用法？

3. （必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。

4. （选做）总结 Hibernate 与 MyBatis 的各方面异同点。

5. （选做）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。

6. （必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：

   1）使用 JDBC 原生接口，实现数据库的增删改查操作。

   2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。

   3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。

附加题（可以后面上完数据库的课再考虑做）：

1. (挑战)基于 AOP 和自定义注解，实现 @MyCache(60) 对于指定方法返回值缓存60秒。

2. (挑战)自定义实现一个数据库连接池，并整合 Hibernate/Mybatis/Spring/SpringBoot。

3. (挑战)基于 MyBatis 实现一个简单的分库分表+读写分离+分布式 ID 生成方案。



## 第六周

### 第11课作业实践

1、（选做）尝试使用 Lambda/Stream/Guava 优化之前作业的代码。 

2、（选做）尝试使用 Lambda/Stream/Guava 优化工作中编码的代码。 

3、（选做）根据课上提供的材料，系统性学习一遍设计模式，并在工作学习中思考如何用设计模式解决问题。

4、（选做）根据课上提供的材料，深入了解 Google 和 Alibaba 编码规范，并根据这些规范，检查自己写代码是否符合规范，有什么可以改进的。

### 第12课作业实践

1、（选做）：基于课程中的设计原则和最佳实践，分析是否可以将自己负责的业务系统进行数据库设计或是数据库服务器方面的优化。

2、（必做）：基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交DDL 的 SQL 文件到 Github（后面2周的作业依然要是用到这个表结构）。

3、（选做）：尽可能多的从“常见关系数据库”中列的清单，安装运行，并使用上一题的SQL 测试简单的增删改查。

4、（选做）：基于上一题，尝试对各个数据库测试100万订单数据的增删改查性能。

5、（选做）：尝试对 MySQL 不同引擎下测试100万订单数据的增删改查性能。

6、（选做）：模拟1000万订单数据，测试不同方式下导入导出（数据备份还原）MySQL 的速度，包括 jdbc 程序处理和命令行处理。思考和实践，如何提升处理效率。

7、（选做）：对 MySQL 配置不同的数据库连接池（DBCP、C3P0、Druid、Hikari），测试增删改查100万次，对比性能，生成报告。



## 第七周

### 第13课作业实践

1、（选做）用今天课上学习的知识，分析自己系统的 SQL 和表结构

2、（必做）按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。

3、（选做）按自己设计的表结构，插入1000万订单模拟数据，测试不同方式的插入效率。

4、（选做）使用不同的索引或组合，测试不同方式查询效率。

5、（选做）调整测试数据，使得数据尽量均匀，模拟1年时间内的交易，计算一年的销售报表：销售总额，订单数，客单价，每月销售量，前十的商品等等（可以自己设计更多指标）。

6、（选做）尝试自己做一个 ID 生成器（可以模拟 Seq 或 Snowflake）。

7、（选做）尝试实现或改造一个非精确分页的程序。



### 第14课作业实践

1、（选做）配置一遍异步复制，半同步复制、组复制。 

2、（必做）读写分离-动态切换数据源版本1.0

3、（必做）读写分离-数据库框架版本2.0

4、（选做）读写分离-数据库中间件版本3.0

5、（选做）配置 MHA，模拟 master 宕机

6、（选做）配置 MGR，模拟 master 宕机

7、（选做）配置 Orchestrator，模拟 master 宕机，演练 UI 调整拓扑结构



## 第八周

### 第15课作业实践

1、（选做）分析前面作业设计的表，是否可以做垂直拆分。 

2、（必做）设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。 

3、（选做）模拟1000万的订单单表数据，迁移到上面作业2的分库分表中。 

4、（选做）重新搭建一套4个库各64个表的分库分表，将作业2中的数据迁移到新分库。



### **第** **16** 课作业实践

1、（选做）列举常见的分布式事务，简单分析其使用场景和优缺点。

2、（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。 

3、（选做）基于 ShardingSphere narayana XA 实现一个简单的分布式事务 demo。 

4、（选做）基于 seata 框架实现 TCC 或 AT 模式的分布式事务 demo。 

5、（选做☆）设计实现一个简单的 XA 分布式事务框架 demo，只需要能管理和调用2 个 MySQL 的本地事务即可，不需要考虑全局事务的持久化和恢复、高可用等。

6、（选做☆）设计实现一个 TCC 分布式事务框架的简单 Demo，需要实现事务管理器，不需要实现全局事务的持久化和恢复、高可用等。

7、（选做☆）设计实现一个 AT 分布式事务框架的简单 Demo，仅需要支持根据主键 id进行的单个删改操作的 SQL 或插入操作的事务。
