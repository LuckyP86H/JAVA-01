## 第一周 学习笔记

### 作业1：

自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。

#### 说明： 

1.  源代码已上传；

2.  **EncryptionClassLoader** 为加密类类加载器核心类； 

3.  **EncryptionClassLoaderTest** 为加密类类加载器测试用例。

### 作业2：

画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。

#### 说明：

**图片地址**

https://github.com/sofia2013/JAVA-01/blob/main/Week_01/src/main/resources/jvm/JVM%E5%86%85%E5%AD%98%E7%BB%93%E6%9E%84-%E5%90%AF%E5%8A%A8%E5%8F%82%E6%95%B0.png

### 作业3：

自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和for，然后自己分析一下对应的字节码。

#### 说明：

1、待分析的相关源码请参考https://github.com/sofia2013/JAVA-01/tree/main/Week_01/2021-02-28/week1/src/main/java/com/xianyanyang/jvm/bytecode ；

2、字节码分析文档参见：https://github.com/sofia2013/JAVA-01/blob/main/Week_01/2021-02-28/week1/doc/JAVA%E5%AD%97%E8%8A%82%E7%A0%81%E6%96%87%E4%BB%B6%E5%88%86%E6%9E%90.md 。



## 个人收获

1. 了解了JVM字节码、类加载器、JVM内存模型、GC等基本知识；
2. 在项目开发过程中结合策略模式堆类加载器进行了实际应用；
3. 对JVM内存模型进行了仔细的学习，并结合JVM参数的作用域画出了整体的内存模型图；
4. 字节码和GC相关的知识还没有进行深入学习和实践，需要抽时间进行。
5. 感谢老师诚意满满的教授和耐心指导。
