
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

**结论：串行的吞吐量较小，后三种实验数据势均力敌。**

#### GC执行时间
##### 串行   -XX:+UseSerialGC

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps  GCLogAnalysis

running...

2021-01-24T20:17:49.282+0800: [GC (Allocation Failure) 2021-01-24T20:17:49.282+0800: [DefNew: 279616K->34944K(314560K), 0.0456696 secs] 279616K->82186K(1013632K), 0.0462900 secs] [Times: user=0.03 sys=0.01, real=0.05 secs]

2021-01-24T20:17:49.388+0800: [GC (Allocation Failure) 2021-01-24T20:17:49.388+0800: [DefNew: 314560K->34943K(314560K), 0.0689942 secs] 361802K->167910K(1013632K), 0.0695958 secs] [Times: user=0.02 sys=0.06, real=0.07 secs]

2021-01-24T20:17:49.514+0800: [GC (Allocation Failure) 2021-01-24T20:17:49.515+0800: [DefNew: 314559K->34941K(314560K), 0.0482317 secs] 447526K->247330K(1013632K), 0.0489129 secs] [Times: user=0.02 sys=0.03, real=0.05 secs]

2021-01-24T20:17:49.619+0800: [GC (Allocation Failure) 2021-01-24T20:17:49.620+0800: [DefNew: 314557K->34944K(314560K), 0.0526693 secs] 526946K->331395K(1013632K), 0.0534340 secs] [Times: user=0.00 sys=0.05, real=0.05 secs]

2021-01-24T20:17:49.775+0800: [GC (Allocation Failure) 2021-01-24T20:17:49.775+0800: [DefNew: 314560K->34942K(314560K), 0.0581535 secs] 611011K->404113K(1013632K), 0.0589090 secs] [Times: user=0.03 sys=0.03, real=0.06 secs]

2021-01-24T20:17:49.893+0800: [GC (Allocation Failure) 2021-01-24T20:17:49.893+0800: [DefNew: 314558K->34941K(314560K), 0.0491665 secs] 683729K->484737K(1013632K), 0.0499001 secs] [Times: user=0.03 sys=0.02, real=0.05 secs]

2021-01-24T20:17:50.042+0800: [GC (Allocation Failure) 2021-01-24T20:17:50.043+0800: [DefNew: 314557K->34943K(314560K), 0.0501636 secs] 764353K->566306K(1013632K), 0.0509638 secs] [Times: user=0.03 sys=0.00, real=0.05 secs]

finished!all times:7717

Heap

def new generation   total 314560K, used 164371K [0x04e00000, 0x1a350000, 0x1a350000)

eden space 279616K,  46% used [0x04e00000, 0x0cc64d20, 0x15f10000)

from space 34944K,  99% used [0x18130000, 0x1a34ffb8, 0x1a350000)

to   space 34944K,   0% used [0x15f10000, 0x15f10000, 0x18130000)

tenured generation   total 699072K, used 531362K [0x1a350000, 0x44e00000, 0x44e00000)

the space 699072K,  76% used [0x1a350000, 0x3aa38928, 0x3aa38a00, 0x44e00000)

Metaspace       used 1742K, capacity 2244K, committed 2368K, reserved 4480K

**总时间：0.3730484ms**

##### 并行   -XX:+UseParallelGC
C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps  GCLogAnalysis

running...

2021-01-24T20:25:38.055+0800: [GC (Allocation Failure) [PSYoungGen: 262400K->43518K(305920K)] 262400K->80230K(1005056K), 0.0187417 secs] [Times: user=0.02 sys=0.06, real=0.02 secs]

2021-01-24T20:25:38.128+0800: [GC (Allocation Failure) [PSYoungGen: 305918K->43515K(305920K)] 342630K->152542K(1005056K), 0.0262335 secs] [Times: user=0.03 sys=0.08, real=0.03 secs]

2021-01-24T20:25:38.204+0800: [GC (Allocation Failure) [PSYoungGen: 305915K->43519K(305920K)] 414942K->221558K(1005056K), 0.0247156 secs] [Times: user=0.14 sys=0.09, real=0.03 secs]

2021-01-24T20:25:38.284+0800: [GC (Allocation Failure) [PSYoungGen: 305919K->43516K(305920K)] 483958K->298844K(1005056K), 0.0269412 secs] [Times: user=0.03 sys=0.09, real=0.03 secs]

2021-01-24T20:25:38.365+0800: [GC (Allocation Failure) [PSYoungGen: 305916K->43518K(305920K)] 561244K->369608K(1005056K), 0.0266414 secs] [Times: user=0.03 sys=0.08, real=0.03 secs]

2021-01-24T20:25:38.443+0800: [GC (Allocation Failure) [PSYoungGen: 305365K->43516K(160000K)] 631455K->450578K(859136K), 0.0305355 secs] [Times: user=0.05 sys=0.16, real=0.03 secs]

2021-01-24T20:25:38.502+0800: [GC (Allocation Failure) [PSYoungGen: 159996K->76123K(232960K)] 567058K->490210K(932096K), 0.0141397 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

2021-01-24T20:25:38.548+0800: [GC (Allocation Failure) [PSYoungGen: 192537K->94964K(232960K)] 606624K->519348K(932096K), 0.0165380 secs] [Times: user=0.13 sys=0.00, real=0.02 secs]

2021-01-24T20:25:38.597+0800: [GC (Allocation Failure) [PSYoungGen: 211444K->103418K(232960K)] 635828K->543491K(932096K), 0.0196172 secs] [Times: user=0.05 sys=0.06, real=0.02 secs]

2021-01-24T20:25:38.648+0800: [GC (Allocation Failure) [PSYoungGen: 219898K->74353K(232960K)] 659971K->573561K(932096K), 0.0241602 secs] [Times: user=0.05 sys=0.05, real=0.02 secs]

2021-01-24T20:25:38.700+0800: [GC (Allocation Failure) [PSYoungGen: 190556K->36594K(232960K)] 689763K->602727K(932096K), 0.0219509 secs] [Times: user=0.11 sys=0.09, real=0.02 secs]

2021-01-24T20:25:38.749+0800: [GC (Allocation Failure) [PSYoungGen: 152564K->42339K(232960K)] 718697K->640346K(932096K), 0.0140744 secs] [Times: user=0.03 sys=0.06, real=0.01 secs]

2021-01-24T20:25:38.764+0800: [Full GC (Ergonomics) [PSYoungGen: 42339K->0K(232960K)] [ParOldGen: 598006K->339447K(699136K)] 640346K->339447K(932096K), [Metaspace: 1738K->1738K(4480K)], 0.0666692 secs] [Times: user=0.44 sys=0.03, real=0.07 secs]

2021-01-24T20:25:38.870+0800: [GC (Allocation Failure) [PSYoungGen: 116480K->34688K(232960K)] 455927K->374136K(932096K), 0.0060953 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

finished!all times:9552

Heap

PSYoungGen      total 232960K, used 137277K [0x2f2c0000, 0x44800000, 0x44800000)

eden space 116480K, 88% used [0x2f2c0000,0x356ef1a0,0x36480000)

from space 116480K, 29% used [0x36480000,0x386603c0,0x3d640000)

to   space 116480K, 0% used [0x3d640000,0x3d640000,0x44800000)

ParOldGen       total 699136K, used 339447K [0x04800000, 0x2f2c0000, 0x2f2c0000)

object space 699136K, 48% used [0x04800000,0x1937dd20,0x2f2c0000)

Metaspace       used 1742K, capacity 2244K, committed 2368K, reserved 4480K

**总时间：0.3370538**

##### 并发CMS   -XX:+UseConcMarkSweepGC

C:\Users\xianyanyang>java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps  GCLogAnalysis

running...

2021-01-24T20:29:28.687+0800: [GC (Allocation Failure) 2021-01-24T20:29:28.688+0800: [ParNew: 279616K->34944K(314560K), 0.0175582 secs] 279616K->89251K(1013632K), 0.0193615 secs] [Times: user=0.06 sys=0.13, real=0.02 secs]

2021-01-24T20:29:28.766+0800: [GC (Allocation Failure) 2021-01-24T20:29:28.766+0800: [ParNew: 314560K->34943K(314560K), 0.0221482 secs] 368867K->169222K(1013632K), 0.0228518 secs] [Times: user=0.17 sys=0.05, real=0.02 secs]

2021-01-24T20:29:28.845+0800: [GC (Allocation Failure) 2021-01-24T20:29:28.846+0800: [ParNew: 314559K->34944K(314560K), 0.0492137 secs] 448838K->252622K(1013632K), 0.0497276 secs] [Times: user=0.42 sys=0.01, real=0.05 secs]

2021-01-24T20:29:28.946+0800: [GC (Allocation Failure) 2021-01-24T20:29:28.947+0800: [ParNew: 314560K->34943K(314560K), 0.0446310 secs] 532238K->331513K(1013632K), 0.0452822 secs] [Times: user=0.34 sys=0.03, real=0.05 secs]

2021-01-24T20:29:29.044+0800: [GC (Allocation Failure) 2021-01-24T20:29:29.045+0800: [ParNew: 314559K->34942K(314560K), 0.0441935 secs] 611129K->412139K(1013632K), 0.0447728 secs] [Times: user=0.38 sys=0.00, real=0.05 secs]

2021-01-24T20:29:29.092+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 377196K(699072K)] 418485K(1013632K), 0.0011683 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

2021-01-24T20:29:29.093+0800: [CMS-concurrent-mark-start]

2021-01-24T20:29:29.099+0800: [CMS-concurrent-mark: 0.005/0.005 secs] [Times: user=0.03 sys=0.02, real=0.01 secs]

2021-01-24T20:29:29.100+0800: [CMS-concurrent-preclean-start]

2021-01-24T20:29:29.102+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

2021-01-24T20:29:29.111+0800: [CMS-concurrent-abortable-preclean-start]

2021-01-24T20:29:29.150+0800: [GC (Allocation Failure) 2021-01-24T20:29:29.151+0800: [ParNew: 314558K->34944K(314560K), 0.0438986 secs] 691755K->490448K(1013632K), 0.0445243 secs] [Times: user=0.34 sys=0.03, real=0.04 secs]

2021-01-24T20:29:29.248+0800: [GC (Allocation Failure) 2021-01-24T20:29:29.248+0800: [ParNew: 314560K->34943K(314560K), 0.0439414 secs] 770064K->568101K(1013632K), 0.0444934 secs] [Times: user=0.34 sys=0.03, real=0.04 secs]

2021-01-24T20:29:29.346+0800: [GC (Allocation Failure) 2021-01-24T20:29:29.347+0800: [ParNew2021-01-24T20:29:29.396+0800: [CMS-concurrent-abortable-preclean: 0.006/0.285 secs] [Times: user=1.20 sys=0.08, real=0.29 secs]
: 314145K->34943K(314560K), 0.0542008 secs] 847302K->645004K(1013632K), 0.0548991 secs] [Times: user=0.34 sys=0.01, real=0.06 secs]

2021-01-24T20:29:29.402+0800: [GC (CMS Final Remark) [YG occupancy: 35710 K (314560 K)]

2021-01-24T20:29:29.402+0800: [Rescan (parallel) , 0.0009611 secs]

2021-01-24T20:29:29.404+0800: [weak refs processing, 0.0001493 secs]

2021-01-24T20:29:29.404+0800: [class unloading, 0.0003821 secs]

2021-01-24T20:29:29.404+0800: [scrub symbol table, 0.0005227 secs]

2021-01-24T20:29:29.406+0800: [scrub string table, 0.0006611 secs][1 CMS-remark: 610060K(699072K)] 645771K(1013632K), 0.0050852 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

2021-01-24T20:29:29.407+0800: [CMS-concurrent-sweep-start]

2021-01-24T20:29:29.415+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]

2021-01-24T20:29:29.417+0800: [CMS-concurrent-reset-start]

2021-01-24T20:29:29.431+0800: [CMS-concurrent-reset: 0.014/0.014 secs] [Times: user=0.02 sys=0.02, real=0.01 secs]

2021-01-24T20:29:29.473+0800: [GC (Allocation Failure) 

2021-01-24T20:29:29.474+0800: [ParNew: 314559K->34943K(314560K), 0.0173705 secs] 784669K->585073K(1013632K), 0.0180516 secs] [Times: user=0.13 sys=0.00, real=0.02 secs]

2021-01-24T20:29:29.492+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 550130K(699072K)] 591093K(1013632K), 0.0011737 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

2021-01-24T20:29:29.493+0800: [CMS-concurrent-mark-start]

2021-01-24T20:29:29.497+0800: [CMS-concurrent-mark: 0.003/0.003 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

2021-01-24T20:29:29.497+0800: [CMS-concurrent-preclean-start]

2021-01-24T20:29:29.499+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

2021-01-24T20:29:29.500+0800: [CMS-concurrent-abortable-preclean-start]

finished!all times:10239

Heap

par new generation   total 314560K, used 238554K [0x04c00000, 0x1a150000, 0x1a150000)

eden space 279616K,  72% used [0x04c00000, 0x112d6f38, 0x15d10000)

from space 34944K,  99% used [0x17f30000, 0x1a14fc58, 0x1a150000)

to   space 34944K,   0% used [0x15d10000, 0x15d10000, 0x17f30000)

concurrent mark-sweep generation total 699072K, used 550130K [0x1a150000, 0x44c00000, 0x44c00000)

Metaspace       used 1742K, capacity 2244K, committed 2368K, reserved 4480K
 
##### G1   -XX:+UseG1GC
日志较大，略。

结论：GC消耗时间 串行>并行>CMS>G1

### 作业2：

写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到Github。


#### 说明： 

1.  源代码已上传；

1.  **OkHttpClientService** 为基于OkHttp的请求服务核心类； 

1.  **OkHttpClientServiceTest** 为基于OkHttp的请求服务测试用例。
