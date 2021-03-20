# 本周作业

分成三个项目：

**1、rpc-domain-api  业务API**

代码地址：https://github.com/sofia2013/JAVA-01/tree/main/Week_09/rpc/rpc-domain-api 

**2、rpc-socket 基于socket通信的RPC简易框架**

代码地址：https://github.com/sofia2013/JAVA-01/tree/main/Week_09/rpc/rpc-socket

通信协议：采用了Socket API 实现了TCP通信；

序列化：采用了二进制序列化传输；

客户端代理：基于Java 的 InvocationHandler进行动态代理实现了远程调用。

**3、rpc-spring 基于Spring框架的HTTP为通信协议的RPC建议框架**

代码地址：https://github.com/sofia2013/JAVA-01/tree/main/Week_09/rpc/rpc-spring

其中RPC框架的核心概念模型在https://github.com/sofia2013/JAVA-01/tree/main/Week_09/rpc/rpc-spring/rpc-spring-core 模块中。

代理模式实现了：动态代理（DynamicProxy.java）和CGLIb字节码增强（CGLibProxy.java）两种。

通信协议：采用了HTTP协议，通过Spring框架的RestOperations接口实现了客户端的远程调用；

序列化：采用了JSON序列化传输；

客户端代理：实现了动态代理（DynamicProxy.java）和CGLIb字节码增强（CGLibProxy.java）两种，可通过配置进行选择。

