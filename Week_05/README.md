
## 学习笔记

### 作业1：

写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）,提交到 Github。

#### 通过xml装配的bean

* 使用默认的xml装配Bean Instantiation with a constructor/properties 参见单元测试 bean.xml.FooServiceImplTest 
* 使用xml装配Bean 实例工厂 Instantiation using an instance factory method 参见单元测试 bean.xml.FooServiceImplTest 
* 使用xml装配Bean 静态工厂 Instantiation with a static factory method 参见单元测试 bean.xml.FooServiceImplTest 

#### 通过annotation装配bean
参见单元测试 bean.annotation.FeeServiceImplTest


#### 通过java config 装配bean
参见单元测试bean.config.FuuServiceTest


#### 通过BeanRegister注册bean
参见单元测试 bean.beanregister.FiiBeanRegisterTest


### 作业2：
给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。

starter 项目源码参见：002/startergen
starter 引用项目参见：002/starter-use

### 作业3：

研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：

1. 使用 JDBC 原生接口，实现数据库的增删改查操作。
2. 使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
3. 配置 Hikari 连接池，改进上述操作。提交代码到 Github。

参见单元测试：001/jdbc/JdbcTest 和 001/jdbc/HikariCpDataSourceJdbcTest
