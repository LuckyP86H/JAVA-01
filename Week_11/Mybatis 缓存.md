# Mybatis 缓存

缓存的重要性是不言而喻的。使用缓存，我们可以避免频繁地与数据库进行交互，尤其是查询越多，缓存命中率越高的情况下，使用缓存对性能的提高越是明显。

Mybatis也提供了对缓存的支持，分为一级缓存和二级缓存。在**默认的情况**下，**只开启了一级缓存**，一级缓存是对同一个**SqlSession**而言的。

> ## 什么是SqlSession？
>
> ### 概念
>
> 在Mybatis中，SqlSession 是其核心接口。在Mybatis中有两个实现类，DefaultSqlSession 和SqlSessionManager。
>
> DefaultSqlSession 是单线程使用的，而SqlSessionManager是多线程环境下使用的。
>
> ### 作用
>
> SqlSession的作用类似于一个JDBC中的Connection 对象，代表了一个连接资源的启用，具体而言有3个作用：
>
> 1、获取Mapper接口；
>
> 2、发送SQL给数据库；
>
> 3、控制数据库事务；
>
> ### 构建
>
> 通过SqlSessionFactory创建SqlSession非常简单，如下所示：
>
> ```java
> SqlSession sqlSession=SqlSessionFactory.openSession();
> ```
>
> 注意：SqlSession 只是一个门面接口，他有很多的方法，可以直接发送SQL，在Mybatis中，真正干活的是Executor。
>
> ### 事务控制
>
> SqlSession控制数据库事务的方法，如下所示：
>
> ```java
> // 定义SqlSession
> SqlSession sqlSession=null;
> try{
>     // 打开SqlSession会话
> 	sqlSession=SqlSessionFactory.openSession();
> 	//execut sql
> 	sqlSession.commit();   //提交事务
> }catch(IOException e){
> 	sqlSession.rollback(); //回滚事务
> }finally{
> 	//	在finally语句中确保资源被顺利关闭
> 	if(sqlSession!=null){ 
> 		sqlSession.close();
> 	}
> }
> ```
>
> 这里是哟个commit方法提交事务或者使用rollback方法回滚事务。
>
> 因为它代表了一个数据库的连接资源，使用以后需要及时关闭它，如果不关闭数据库的资源会很快被消耗完。
>
> 引用： http://c.biancheng.net/view/4316.html 

## 一级缓存

**同一个SqlSession对象，在参数和SQL完全相同的情况下，如果缓存没有过期，则只执行一次SQL语句。**

注意：只有在参数和SQL完全一样的情况下，才会有这样的情况。

### 示例一：相同的sqlSession重复查询

#### 测试用例：

```java
    @Test
    public void testOneSqlSession() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            System.out.println("first query...");
            List<User> first = userMapper.listAll();
            first.forEach(user -> System.out.println(user.toString()));
            System.out.println("second query...");
            List<User> second = userMapper.listAll();
            second.forEach(user -> System.out.println(user.toString()));
            Assert.assertEquals(first, second);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
```

#### 执行结果：

```shell
first query...
2021-04-01 11:46:01,036 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 11:46:01 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 11:46:01,507 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER 
2021-04-01 11:46:01,589 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters: 
2021-04-01 11:46:01,650 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
2021-04-01 11:46:01,651 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 1
2021-04-01 11:46:01,652 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
2021-04-01 11:46:01,653 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
2021-04-01 11:46:01,653 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
com.xianyanyang.mybatis.entity.User@14f9390f
com.xianyanyang.mybatis.entity.User@6c0d7c83
com.xianyanyang.mybatis.entity.User@176b75f7
second query...
2021-04-01 11:46:01,656 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
com.xianyanyang.mybatis.entity.User@14f9390f
com.xianyanyang.mybatis.entity.User@6c0d7c83
com.xianyanyang.mybatis.entity.User@176b75f7
```

#### 说明：

```shell
第一次查询发送了查询SQL语句后返回了执行结果；
第二次查询并未发送查询SQL语句，从一级缓存中拿到数据。
从断言中可以看出两次查询结果相同。
```

### 示例二：相同的sqlSession查询，且修改了内存中的对象属性

#### 测试用例：

```
@Test
public void testOneSqlSession_changeMemory() {
    SqlSession sqlSession = null;
    try {
        sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println("first query user...");
        User user = userMapper.findUser("2");
        user.setName("xiao xianxian");

        System.out.println("second query user...");
        User user2 = userMapper.findUser("2");
        Assert.assertEquals("xiao xianxian",user2.getName());

    } finally {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
```

#### 执行结果：

```
first query user...
2021-04-01 14:32:53,711 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 14:32:53 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 14:32:54,158 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 14:32:54,220 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 2(String)
2021-04-01 14:32:54,266 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
2021-04-01 14:32:54,266 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 2, 2
2021-04-01 14:32:54,267 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
second query user...
2021-04-01 14:32:54,268 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0

```

#### 说明：

```
说明：
第一次查询向数据库发送了SQL语句；
修改了缓存中的对象属性；
第二次查询未向数据库发送SQL语句，而是从缓存中拿了上次的查询结果对象，注意看，本次拿到的结果的姓名已经被修改。
```

### 示例三：不同的sqlSession分别查询

#### 测试用例：

```java
@Test
public void testOneSqlSession() {
    SqlSession sqlSession = null;
    try {
        sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println("first query...");
        List<User> first = userMapper.listAll();
        first.forEach(user -> System.out.println(user.toString()));
        System.out.println("second query...");
        List<User> second = userMapper.listAll();
        second.forEach(user -> System.out.println(user.toString()));
        Assert.assertEquals(first, second);
    } finally {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
```

#### 执行结果：

```shell
first query...
2021-04-01 11:47:55,168 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 11:47:55 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 11:47:55,627 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER 
2021-04-01 11:47:55,711 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters: 
2021-04-01 11:47:55,755 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
2021-04-01 11:47:55,755 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 1
2021-04-01 11:47:55,756 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
2021-04-01 11:47:55,757 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
2021-04-01 11:47:55,757 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
com.xianyanyang.mybatis.entity.User@7d898981
com.xianyanyang.mybatis.entity.User@48d61b48
com.xianyanyang.mybatis.entity.User@68d279ec
second query...
2021-04-01 11:47:55,759 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
com.xianyanyang.mybatis.entity.User@7d898981
com.xianyanyang.mybatis.entity.User@48d61b48
com.xianyanyang.mybatis.entity.User@68d279ec
```

#### 说明：

```shell
说明：
第一次查询发送了查询SQL语句后返回了执行结果；
第二次查询并未发送查询SQL语句，而是从内存中返回了缓存后的执行结果；
从断言中可以看出两次查询结果的值虽然相同，但是是属于不同的对象。
```

### 示例四：相同的sqlSession关闭了缓存

#### 测试用例：

```xml
<select id="findById" flushCache="true" parameterType="java.lang.String" resultMap="User">
  SELECT ID,NAME FROM USER WHERE ID=#{id, jdbcType=VARCHAR}
</select>
```

```java
@Test
public void nocache() {
    SqlSession sqlSession = null;
    try {
        sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println("first query...");
        User first = userMapper.findById("1");
        System.out.println(first.toString());
        System.out.println("second query...");
        User second = userMapper.findById("1");
        System.out.println(second.toString());
        Assert.assertNotEquals(first, second);
    } finally {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
```

#### 执行结果：

```shell
first query...
2021-04-01 11:57:15,075 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 11:57:15 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 11:57:15,814 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findById] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 11:57:15,883 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findById] - ==> Parameters: 1(String)
2021-04-01 11:57:15,949 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findById] - <==    Columns: ID, NAME
2021-04-01 11:57:15,949 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findById] - <==        Row: 1, 1
2021-04-01 11:57:15,950 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findById] - <==      Total: 1
com.xianyanyang.mybatis.entity.User@176b75f7
second query...
2021-04-01 11:57:15,951 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
2021-04-01 11:57:15,951 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findById] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 11:57:15,951 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findById] - ==> Parameters: 1(String)
2021-04-01 11:57:15,963 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findById] - <==    Columns: ID, NAME
2021-04-01 11:57:15,963 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findById] - <==        Row: 1, 1
2021-04-01 11:57:15,963 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findById] - <==      Total: 1
com.xianyanyang.mybatis.entity.User@409c54f
```

#### 说明：

在userMapper.xml中设置了该查询语句的配置项flushCache="true"后，缓存失效。两次查询均是发送了sql语句进行了查询，查询的两次结果也都是不同的对象。

### 示例五：执行查询过程中执行了update语句

#### 测试用例：

#### 执行结果：

```
first query...
2021-04-01 13:59:35,154 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 13:59:35 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 13:59:35,602 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER 
2021-04-01 13:59:35,680 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters: 
2021-04-01 13:59:35,721 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
2021-04-01 13:59:35,721 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 1
2021-04-01 13:59:35,723 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
2021-04-01 13:59:35,723 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
2021-04-01 13:59:35,723 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
com.xianyanyang.mybatis.entity.User@48d61b48
com.xianyanyang.mybatis.entity.User@68d279ec
com.xianyanyang.mybatis.entity.User@258d79be
second query...
2021-04-01 13:59:35,725 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
com.xianyanyang.mybatis.entity.User@48d61b48
com.xianyanyang.mybatis.entity.User@68d279ec
com.xianyanyang.mybatis.entity.User@258d79be
update...
2021-04-01 13:59:35,730 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - ==>  Preparing: UPDATE USER SET NAME=? WHERE ID=? 
2021-04-01 13:59:35,731 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - ==> Parameters: 2222(String), 1(String)
2021-04-01 13:59:35,753 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - <==    Updates: 1
third query...
2021-04-01 13:59:35,753 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
2021-04-01 13:59:35,753 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER 
2021-04-01 13:59:35,753 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters: 
2021-04-01 13:59:35,775 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
2021-04-01 13:59:35,775 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 2222
2021-04-01 13:59:35,776 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
2021-04-01 13:59:35,776 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
2021-04-01 13:59:35,776 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
com.xianyanyang.mybatis.entity.User@7d286fb6
com.xianyanyang.mybatis.entity.User@3eb77ea8
com.xianyanyang.mybatis.entity.User@7b8b43c7
```

#### 说明：

```
第一次查询发送了查询SQL语句后返回了执行结果；
第二次查询并未发送查询SQL语句，而是从内存中返回了缓存后的执行结果；
执行了修改的sql语句后第三次查询，缓存失效，依旧进行了SQL语句的数据库查询。
```



### 总结

1、开启了一级缓存后，缓存范围为会话级别，同一个sqlsession下，查询命中一级缓存内存数据；

2、不同的 SqlSession 之间的缓存是相互隔离的；

3、用一个 SqlSession， 可以通过配置使得在查询前清空缓存；

4、执行了 UPDATE, INSERT, DELETE 语句后，一级缓存失效。

## 二级缓存

二级缓存存在于SqlSessionFactory生命周期中。二级缓存开启后，同一个namespace下的所有操作语句，都影响着同一个Cache，即二级缓存被多个SqlSession共享，是一个全局的变量。当开启缓存后，数据的查询执行的流程就是 二级缓存 -> 一级缓存 -> 数据库。

### 配置

要正确的使用二级缓存，需完成如下配置的。

1、在MyBatis的配置文件中开启二级缓存。

```xml
<setting name="cacheEnabled" value="true"/>
```

2、在MyBatis的映射XML中配置cache或者 cache-ref 。

cache标签用于声明这个namespace使用二级缓存，并且可以自定义配置。

```xml
<cache/>
```

- type：cache使用的类型，默认是PerpetualCache，这在一级缓存中提到过。
- eviction： 定义回收的策略，常见的有FIFO，LRU。
- flushInterval： 配置一定时间自动刷新缓存，单位是毫秒。
- size： 最多缓存对象的个数。
- readOnly： 是否只读，若配置可读写，则需要对应的实体类能够序列化。
- blocking： 若缓存中找不到对应的key，是否会一直blocking，直到有对应的数据进入缓存。

### 示例一：

测试二级缓存效果，sqlSession1查询完数据后关闭会话，sqlSession2相同的查询是否会从缓存中获取数据。

#### 测试用例：

```
    @Test
    public void secondCache_differentSqlSession_close() {
        System.out.println("first query...");
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        User first = userMapper1.findUser("1");
        first.setName("xiaoxianxian");
        System.out.println(first + " ->name:" + first.getName());
        sqlSession1.close();

        System.out.println("second query...");
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User second = userMapper2.findUser("1");
        System.out.println(second + " ->name:" + second.getName());
        Assert.assertEquals(second.getName(), "xiaoxianxian");
        sqlSession2.close();
    }
```

执行结果：

```
first query...
2021-04-01 16:13:03,329 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 16:13:03 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 16:13:03,798 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 16:13:03,878 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
2021-04-01 16:13:03,926 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
2021-04-01 16:13:03,926 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, foo
2021-04-01 16:13:03,927 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
com.xianyanyang.mybatis.entity.User@258d79be ->name:xiaoxianxian
second query...
2021-04-01 16:13:03,941 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.5
com.xianyanyang.mybatis.entity.User@3081f72c ->name:xiaoxianxian
```

说明：

```
开启了二级缓存后，sqlsession1查询完成数据后，执行close，sqlsession2相同的查询会命中二级缓存。
```



### 示例二：

测试二级缓存效果，不关闭会话也不提交事务，sqlSession1查询完数据后，sqlSession2相同的查询是否会从缓存中获取数据。

#### 测试用例：

```java
@Test
public void secondCache_differentSqlSession_noCommit() {
    SqlSession sqlSession1 = null;
    SqlSession sqlSession2 = null;
    UserMapper userMapper1;
    UserMapper userMapper2;
    try {
        System.out.println("first query...");
        sqlSession1 = sqlSessionFactory.openSession();
        userMapper1 = sqlSession1.getMapper(UserMapper.class);
        User first = userMapper1.findUser("1");
        first.setName("xiaoxianxian");
        System.out.println(first + " ->name:" + first.getName());
        System.out.println("second query...");
        sqlSession2 = sqlSessionFactory.openSession();
        userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User second = userMapper2.findUser("1");
        System.out.println(second + " ->name:" + second.getName());
        Assert.assertNotEquals(second.getName(), "xiaoxianxian");
    } finally {
        if (sqlSession1 != null) {
            sqlSession1.close();
        }
        if (sqlSession2 != null) {
            sqlSession2.close();
        }
    }
}
```

#### 执行结果：

```
first query...
2021-04-01 15:29:07,802 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 15:29:08 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 15:29:08,293 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 15:29:08,351 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
2021-04-01 15:29:08,398 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
2021-04-01 15:29:08,398 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, 1
2021-04-01 15:29:08,400 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
com.xianyanyang.mybatis.entity.User@258d79be ->name:xiaoxianxian
second query...
2021-04-01 15:29:08,401 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 15:29:08 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 15:29:08,540 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 15:29:08,540 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
2021-04-01 15:29:08,552 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
2021-04-01 15:29:08,552 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, 1
2021-04-01 15:29:08,553 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
com.xianyanyang.mybatis.entity.User@4f6f416f ->name:1
```

#### 说明：

```
开启了二级缓存后，sqlSession1查询完语句后，没有进行会话结束也没有提交事务，则二级缓存不生效，sqlSession2查询无法命中缓存。
```



### 示例三：

测试二级缓存效果，当提交事务时，sqlSession1查询完数据后，sqlSession2相同的查询是否会从缓存中获取数据。

#### 测试用例：

```
<cache readOnly="false"/>
```

```
@Test
public void secondCache_differentSqlSession_commited() {
    SqlSession sqlSession1 = null;
    SqlSession sqlSession2 = null;
    UserMapper userMapper1;
    UserMapper userMapper2;
    try {
        System.out.println("first query...");
        sqlSession1 = sqlSessionFactory.openSession();
        userMapper1 = sqlSession1.getMapper(UserMapper.class);
        User first = userMapper1.findUser("1");
        first.setName("xiaoxianxian");
        System.out.println(first + " ->name:" + first.getName());
        System.out.println("second query...");
        sqlSession1.commit();

        sqlSession2 = sqlSessionFactory.openSession();
        userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User second = userMapper2.findUser("1");
        System.out.println(second + " ->name:" + second.getName());
        Assert.assertEquals(second.getName(), "xiaoxianxian");
    } finally {
        if (sqlSession1 != null) {
            sqlSession1.close();
        }
        if (sqlSession2 != null) {
            sqlSession2.close();
        }
    }
}
```

#### 执行结果：

```
first query...
2021-04-01 15:33:04,150 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 15:33:04 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 15:33:04,602 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 15:33:04,670 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
2021-04-01 15:33:04,708 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
2021-04-01 15:33:04,708 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, 1
2021-04-01 15:33:04,710 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
com.xianyanyang.mybatis.entity.User@258d79be ->name:xiaoxianxian
second query...
2021-04-01 15:33:04,722 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.5
com.xianyanyang.mybatis.entity.User@3081f72c ->name:xiaoxianxian
```

#### 说明：

```
开启了二级缓存后，sqlSession1查询完成后，执行了事务的提交，则二级缓存生效，sqlsession2的查询会命中缓存，缓存的命中率是0.5。

注意：
com.xianyanyang.mybatis.entity.User@258d79be ->name:xiaoxianxian
com.xianyanyang.mybatis.entity.User@3081f72c ->name:xiaoxianxian
命中的结果对象和之前的不是一个对象，是因为配置了：
<cache readOnly="false"/>
readOnly 为只读属性，默认为 false
false: 可读写，在创建对象时，会通过反序列化得到缓存对象的拷贝。因此在速度上会相对慢一点，但重在安全。
true: 只读，只读的缓存会给所有调用者返回缓存对象的相同实例。因此性能很好，但如果修改了对象，有可能会导致程序出问题。

如果配置了readOnly="true",执行结果为：
com.xianyanyang.mybatis.entity.User@282cb7c7 ->name:xiaoxianxian
com.xianyanyang.mybatis.entity.User@282cb7c7 ->name:xiaoxianxian
```

### 示例四：

测试update操作是否会刷新该namespace下的二级缓存。

#### 测试用例：

```
@Test
public void secondCacheDifferentSqlSessionWithUpdate() {
    SqlSession sqlSession1 = null;
    SqlSession sqlSession2 = null;
    SqlSession sqlSession3 = null;
    UserMapper userMapper1;
    UserMapper userMapper2;
    UserMapper userMapper3;
    try {
        System.out.println("first query...");
        sqlSession1 = sqlSessionFactory.openSession();
        userMapper1 = sqlSession1.getMapper(UserMapper.class);
        User first = userMapper1.findUser("1");
        first.setName("xiaoxianxian");
        System.out.println(first + " ->name:" + first.getName());
        sqlSession1.commit();


        System.out.println("second query...");
        sqlSession2 = sqlSessionFactory.openSession();
        userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User second = userMapper2.findUser("1");
        System.out.println(second + " ->name:" + second.getName());

        System.out.println("update user...");
        sqlSession3 = sqlSessionFactory.openSession(true);
        userMapper3 = sqlSession3.getMapper(UserMapper.class);
        userMapper3.changeUserName("1", "foo");
        sqlSession3.commit();

        System.out.println("third query...");
        User third = userMapper2.findUser("1");
        System.out.println(third + " ->name:" + third.getName());

    } finally {
        if (sqlSession1 != null) {
            sqlSession1.close();
        }
        if (sqlSession2 != null) {
            sqlSession2.close();
        }
        if (sqlSession3 != null) {
            sqlSession3.close();
        }
    }
}
```

#### 执行结果：

```
first query...
2021-04-01 15:53:26,016 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
Thu Apr 01 15:53:26 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 15:53:26,488 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 15:53:26,558 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
2021-04-01 15:53:26,609 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
2021-04-01 15:53:26,609 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, foo
2021-04-01 15:53:26,611 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
com.xianyanyang.mybatis.entity.User@14f9390f ->name:xiaoxianxian
second query...
2021-04-01 15:53:26,623 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.5
com.xianyanyang.mybatis.entity.User@3148f668 ->name:xiaoxianxian
update user...
Thu Apr 01 15:53:26 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 15:53:26,770 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - ==>  Preparing: UPDATE USER SET NAME=? WHERE ID=? 
2021-04-01 15:53:26,770 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - ==> Parameters: foo(String), 1(String)
2021-04-01 15:53:26,794 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - <==    Updates: 1
third query...
2021-04-01 15:53:26,795 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.3333333333333333
Thu Apr 01 15:53:26 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2021-04-01 15:53:26,934 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=? 
2021-04-01 15:53:26,934 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
2021-04-01 15:53:26,945 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
2021-04-01 15:53:26,945 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, foo
2021-04-01 15:53:26,945 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
com.xianyanyang.mybatis.entity.User@71329995 ->name:foo
```

#### 说明：

```
在sqlSession3更新数据库，并提交事务后，sqlsession2的查询走了数据库，没有走Cache。
```

参考：

https://blog.csdn.net/weixin_37139197/article/details/82908377

https://blog.csdn.net/bjweimengshu/article/details/79988252