
## 学习笔记

### 作业1：

基础代码可以fork: https://github.com/kimmking/JavaCourseCodes02 的nio/nio02文件夹下，实现以后，代码提交到 GitHub。
1.（必做）整合你上次作业的 httpclient/okhttp；

#### 说明： 

1.  源代码已上传；

1.  **nio.OkHttpClientService** 为基于OkHttp的请求服务核心类（依据之前助教的提示已经修改：双验证+非静态方法，请助教老师再次核实一下）； 

1.  **gateway.netty.HttpHandler** 为代理网关请求核心类。

#### 访问结果：
1. 启动HttpServer01单线程的socket程序，使用浏览器访问 http://127.0.0.1:8801/，返回 
hello,nio ，表示请求服务正常；

1. 启动NettyHttpServer的Netty服务，使用浏览器访问http://127.0.0.1:8808/test ，返回
hello,nio，网关代理转发成功。

### 作业2：
1.（必做）实现过滤器。

#### 说明： 

1.  源代码已上传；

1.  **gateway.filter.HttpRequestFilter** 为过滤器接口； 

1.  **gateway.filter.HttpRequestFilterImpl** 为过滤器实现类；

1.  **gateway.netty.HttpHandler** 为代理网关请求核心类，其中调用了过滤器的接口方法，模拟实现了对键值为accessId的插入与传递。
