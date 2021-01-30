## 学习笔记

### 作业1：

基础代码可以fork: https://github.com/kimmking/JavaCourseCodes02的nio/nio02文件夹下，实现以后，代码提交到 GitHub。
1.（必做）整合你上次作业的 httpclient/okhttp；

#### 说明： 

1.  源代码已上传；

1.  **OkHttpClientService** 为基于OkHttp的请求服务核心类； 

1.  **HttpHandler** 为代理网关请求核心代码。

#### 访问结果：
1. 启动HttpServer01单线程的socket程序，使用浏览器访问 http://127.0.0.1:8801/，返回 
hello,nio ，表示请求服务正常；

1. 启动NettyHttpServer的Netty服务，使用浏览器访问http://127.0.0.1:8808/test ，返回
hello,nio，网关代理转发成功。