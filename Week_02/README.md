
## 学习笔记
### 作业1：

首先使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例，然后使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar示例，最后根据上述演示，写一段对于不同GC和堆内存的总结，提交到github。

#### GC日志说明
https://github.com/sofia2013/JAVA-01/blob/main/Week_02/src/main/resources/gc/GC%E6%97%A5%E5%BF%97%E8%AF%B4%E6%98%8E.png

#### 吞吐量对比
##### 串行   -XX:+UseSerialGC
C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseSerialGC GCLogAnalysis

running...

finished!all times:8837

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseSerialGC GCLogAnalysis

running...

finished!all times:8432

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseSerialGC GCLogAnalysis

running...

finished!all times:9126

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseSerialGC GCLogAnalysis

running...

finished!all times:9467

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseSerialGC GCLogAnalysis

running...

finished!all times:9369

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseSerialGC GCLogAnalysis

running...

finished!all times:8309
##### 并行   -XX:+UseParallelGC
C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseParallelGC GCLogAnalysis

running...

finished!all times:11021

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseParallelGC GCLogAnalysis

running...

finished!all times:10823

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseParallelGC GCLogAnalysis

running...

finished!all times:10758

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseParallelGC GCLogAnalysis

running...

finished!all times:10834

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseParallelGC GCLogAnalysis

running...

finished!all times:10473
##### 并发CMS   -XX:+UseConcMarkSweepGC
C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC GCLogAnalysis

running...

finished!all times:11399

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC GCLogAnalysis

running...

finished!all times:11518

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC GCLogAnalysis

running...

finished!all times:11274

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC GCLogAnalysis

running...

finished!all times:11492

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC GCLogAnalysis

running...

finished!all times:10841

##### G1   -XX:+UseG1GC
C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseG1GC GCLogAnalysis

running...

finished!all times:11610

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseG1GC GCLogAnalysis

running...

finished!all times:10895

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseG1GC GCLogAnalysis

running...

finished!all times:10169

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseG1GC GCLogAnalysis

running...

finished!all times:11739

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseG1GC GCLogAnalysis

running...

finished!all times:11087

结论：串行的吞吐量较小，后三种实验数据势均力敌。

### 作业2：

写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到Github。


#### 说明： 

1.  源代码已上传；

1.  **OkHttpClientService** 为基于OkHttp的请求服务核心类； 

1.  **OkHttpClientServiceTest** 为基于OkHttp的请求服务测试用例。
