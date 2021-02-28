题目：

从Classloader到模块化，动态加载的插件机制。

1. 10-使用自定义Classloader机制，实现xlass的加载：xlass是作业材料。
2. 20-实现xlass打包的xar（类似class文件打包的jar）的加载：xar里是xlass。
3. 30-基于自定义Classloader实现类的动态加载和卸载：需要设计加载和卸载。
4. 30-基于自定义Classloader实现模块化机制：需要设计模块化机制。
5. 30-使用xar作为模块，实现xar动态加载和卸载：综合应用前面的内容。



作业说明：

请参见2021-02-28中的代码。



**10-使用自定义Classloader机制，实现xlass的加载：xlass是作业材料。**

源代码： com.xianyanyang.jvm.classloader.homework1.EncryptionClassLoader

单元测试：com.xianyanyang.jvm.classloader.homework1.EncryptionClassLoaderTest



**20-实现xlass打包的xar（类似class文件打包的jar）的加载：xar里是xlass。**

**1）方案1：通过JarFile解析xar包**

源代码：com.xianyanyang.jvm.classloader.homework2.XarClassLoader

单元测试：com.xianyanyang.jvm.classloader.homework2.XarClassLoaderTest

**2）方案2：通过xlass文件再xar中的路径进行解析**

源代码：com.xianyanyang.jvm.classloader.homework2.XarClassLoaderExt

单元测试：com.xianyanyang.jvm.classloader.homework2.XarClassLoaderExtTest



**30-基于自定义Classloader实现类的动态加载和卸载：需要设计加载和卸载。**

**30-基于自定义Classloader实现模块化机制：需要设计模块化机制。**

**30-使用xar作为模块，实现xar动态加载和卸载：综合应用前面的内容。**

详细说明：

1）com.xianyanyang.jvm.classloader.salary.FileChangeTest工具类，生成src\\main\\resources\\jvm\\hello2\\Hello.xlass文件（逻辑为解密原xlass文件），使用的类加载器为XarClassLoader

2）通过jar cvfM hello2.xar Hello.xlass 命令生成hello2.xar，该xar包的类加载器为XarClassLoader2

3）源代码：com.xianyanyang.jvm.classloader.homework3.XarPluginClassLoader

​	 单元测试：com.xianyanyang.jvm.classloader.homework3.XarPluginClassLoaderTest



**另外：com.xianyanyang.jvm.classloader.plugin中实现了插件抽象接口的多个实现的动态插拔。**

其中涉及项目为：

**common-plugin**：抽象接口com.xianyanyang.common.plugin.CommonPlugin所在项目

**my-plugin**：实现了com.xianyanyang.common.plugin.CommonPlugin的项目1

**your-plugin**：com.xianyanyang.common.plugin.CommonPlugin的项目2

**week1**：应用系统，引用了common-plugin依赖，PluginLoader接口为插件加载接口

​	方案1：通过自定义策略模式获取指定的类加载器，通过类加载器反射加载出执行的类

​			     源代码：com.xianyanyang.jvm.classloader.plugin.my.PluginLoaderImpl 

​				单元测试：com.xianyanyang.jvm.classloader.plugin.my.PluginLoaderImplTest

​	方案2：通过SPI机制获取指定的服务类

​				源代码：com.xianyanyang.jvm.classloader.plugin.spi.SPIPluginLoader

​				单元测试：com.xianyanyang.jvm.classloader.plugin.spi.SPIPluginLoaderTest



