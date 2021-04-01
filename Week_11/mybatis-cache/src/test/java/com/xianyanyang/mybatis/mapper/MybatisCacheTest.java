package com.xianyanyang.mybatis.mapper;

import com.xianyanyang.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

public class MybatisCacheTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() throws Exception {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    //region testOneSqlSession

    /**
     * 执行结果:
     * first query...
     * 2021-04-01 11:41:15,575 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 11:41:15 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 11:41:16,083 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER
     * 2021-04-01 11:41:16,153 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters:
     * 2021-04-01 11:41:16,196 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
     * 2021-04-01 11:41:16,196 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 1
     * 2021-04-01 11:41:16,198 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
     * 2021-04-01 11:41:16,198 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
     * 2021-04-01 11:41:16,198 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
     * com.xianyanyang.mybatis.entity.User@48d61b48
     * com.xianyanyang.mybatis.entity.User@68d279ec
     * com.xianyanyang.mybatis.entity.User@258d79be
     * second query...
     * 2021-04-01 11:41:16,200 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * com.xianyanyang.mybatis.entity.User@48d61b48
     * com.xianyanyang.mybatis.entity.User@68d279ec
     * com.xianyanyang.mybatis.entity.User@258d79be
     * <p>
     * 说明：
     * 第一次查询发送了查询SQL语句后返回了执行结果；
     * 第二次查询并未发送查询SQL语句，而是从内存中返回了执行结果；
     * 从断言中可以看出两次查询结果相同。
     */
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

    //endregion

    //region testOneSqlSession_changeMemory

    /**
     * 执行结果：
     * first query user...
     * 2021-04-01 14:28:48,404 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 14:28:48 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 14:28:49,002 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=?
     * 2021-04-01 14:28:49,049 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 2(String)
     * 2021-04-01 14:28:49,090 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
     * 2021-04-01 14:28:49,090 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 2, 2
     * 2021-04-01 14:28:49,091 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
     * second query user...
     * 2021-04-01 14:28:49,092 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * 说明：
     * 第一次查询向数据库发送了SQL语句；
     * 修改了缓存中的对象属性
     * 第二次查询未向数据库发送SQL语句，而是从缓存中拿了上次的查询结果对象，注意看，本次拿到的结果的姓名已经被修改。
     */
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
            Assert.assertEquals(user, user2);
            Assert.assertEquals("xiao xianxian", user2.getName());

        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //endregion

    //region testDifferentSqlSession

    /**
     * 执行结果：
     * first query...
     * 2021-04-01 11:42:59,998 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 11:43:00 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 11:43:00,471 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER
     * 2021-04-01 11:43:00,552 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters:
     * 2021-04-01 11:43:00,593 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
     * 2021-04-01 11:43:00,593 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 1
     * 2021-04-01 11:43:00,595 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
     * 2021-04-01 11:43:00,595 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
     * 2021-04-01 11:43:00,595 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
     * com.xianyanyang.mybatis.entity.User@14f9390f
     * com.xianyanyang.mybatis.entity.User@6c0d7c83
     * com.xianyanyang.mybatis.entity.User@176b75f7
     * second query...
     * 2021-04-01 11:43:00,598 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 11:43:00 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 11:43:00,747 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER
     * 2021-04-01 11:43:00,748 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters:
     * 2021-04-01 11:43:00,759 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
     * 2021-04-01 11:43:00,759 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 1
     * 2021-04-01 11:43:00,760 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
     * 2021-04-01 11:43:00,760 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
     * 2021-04-01 11:43:00,760 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
     * com.xianyanyang.mybatis.entity.User@7d286fb6
     * com.xianyanyang.mybatis.entity.User@3eb77ea8
     * com.xianyanyang.mybatis.entity.User@7b8b43c7
     * 说明：
     * 第一次查询发送了查询SQL语句后返回了执行结果；
     * 第一次查询发送了查询SQL语句后返回了执行结果；
     * 从断言中可以看出两次查询结果值相同，但是是不同的对象
     */
    @Test
    public void testDifferentSqlSession() {
        SqlSession sqlSession = null;
        UserMapper userMapper;
        try {
            System.out.println("first query...");
            sqlSession = sqlSessionFactory.openSession();
            userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> first = userMapper.listAll();
            first.forEach(user -> System.out.println(user.toString()));

            System.out.println("second query...");
            sqlSession = sqlSessionFactory.openSession();
            userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> second = userMapper.listAll();
            second.forEach(user -> System.out.println(user.toString()));
            Assert.assertNotEquals(first, second);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //endregion

    //region testOneSqlSessionFlashCache

    @Test
    public void testOneSqlSessionFlashCache() {
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

    //endregion

    //region testOneSqlSessionWithUpdate

    /**
     * first query...
     * 2021-04-01 13:59:35,154 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 13:59:35 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 13:59:35,602 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER
     * 2021-04-01 13:59:35,680 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters:
     * 2021-04-01 13:59:35,721 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
     * 2021-04-01 13:59:35,721 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 1
     * 2021-04-01 13:59:35,723 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
     * 2021-04-01 13:59:35,723 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
     * 2021-04-01 13:59:35,723 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
     * com.xianyanyang.mybatis.entity.User@48d61b48
     * com.xianyanyang.mybatis.entity.User@68d279ec
     * com.xianyanyang.mybatis.entity.User@258d79be
     * second query...
     * 2021-04-01 13:59:35,725 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * com.xianyanyang.mybatis.entity.User@48d61b48
     * com.xianyanyang.mybatis.entity.User@68d279ec
     * com.xianyanyang.mybatis.entity.User@258d79be
     * update...
     * 2021-04-01 13:59:35,730 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - ==>  Preparing: UPDATE USER SET NAME=? WHERE ID=?
     * 2021-04-01 13:59:35,731 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - ==> Parameters: 2222(String), 1(String)
     * 2021-04-01 13:59:35,753 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - <==    Updates: 1
     * third query...
     * 2021-04-01 13:59:35,753 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * 2021-04-01 13:59:35,753 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==>  Preparing: SELECT ID,NAME FROM USER
     * 2021-04-01 13:59:35,753 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - ==> Parameters:
     * 2021-04-01 13:59:35,775 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==    Columns: ID, NAME
     * 2021-04-01 13:59:35,775 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 1, 2222
     * 2021-04-01 13:59:35,776 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 2, 2
     * 2021-04-01 13:59:35,776 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==        Row: 3, 3
     * 2021-04-01 13:59:35,776 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.listAll] - <==      Total: 3
     * com.xianyanyang.mybatis.entity.User@7d286fb6
     * com.xianyanyang.mybatis.entity.User@3eb77ea8
     * com.xianyanyang.mybatis.entity.User@7b8b43c7
     * 说明：
     * 第一次查询发送了查询SQL语句后返回了执行结果；
     * 第二次查询并未发送查询SQL语句，而是从内存中返回了执行结果；
     * 执行了修改的sql语句后第三次查询，缓存失效，依旧进行了SQL语句的数据库查询。
     */
    @Test
    public void testOneSqlSessionWithUpdate() {
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

            System.out.println("update...");
            userMapper.changeUserName("1", "2222");

            System.out.println("third query...");
            List<User> three = userMapper.listAll();
            three.forEach(user -> System.out.println(user.toString()));
            Assert.assertNotEquals(first, three);

        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //endregion testOneSqlSessionWithUpdate

    //region secondCache_differentSqlSession_close

    /**
     *first query...
     * 2021-04-01 16:13:03,329 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 16:13:03 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 16:13:03,798 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=?
     * 2021-04-01 16:13:03,878 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
     * 2021-04-01 16:13:03,926 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
     * 2021-04-01 16:13:03,926 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, foo
     * 2021-04-01 16:13:03,927 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
     * com.xianyanyang.mybatis.entity.User@258d79be ->name:xiaoxianxian
     * second query...
     * 2021-04-01 16:13:03,941 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.5
     * com.xianyanyang.mybatis.entity.User@3081f72c ->name:xiaoxianxian
     */
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

    //endregion

    //region secondCache_differentSqlSession_noCommit

    /**
     * first query...
     * 2021-04-01 15:29:07,802 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 15:29:08 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 15:29:08,293 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=?
     * 2021-04-01 15:29:08,351 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
     * 2021-04-01 15:29:08,398 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
     * 2021-04-01 15:29:08,398 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, 1
     * 2021-04-01 15:29:08,400 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
     * com.xianyanyang.mybatis.entity.User@258d79be ->name:xiaoxianxian
     * second query...
     * 2021-04-01 15:29:08,401 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 15:29:08 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 15:29:08,540 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=?
     * 2021-04-01 15:29:08,540 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
     * 2021-04-01 15:29:08,552 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
     * 2021-04-01 15:29:08,552 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, 1
     * 2021-04-01 15:29:08,553 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
     * com.xianyanyang.mybatis.entity.User@4f6f416f ->name:1
     * 第一次查询，sqlsession向数据库提交了查询SQL；
     * 第二次查询，新的sqlsession向数据库提交了查询SQL，因为第一个sqlsession并没有commit，所以二级缓存没有生效。
     */
    @Test
    public void secondCache_differentSqlSession_noCommit() {
        SqlSession sqlSession1 = null;
        SqlSession sqlSession2 = null;
        UserMapper userMapper1;
        UserMapper userMapper2;
        try {
            System.out.println("first query...");
            sqlSession1 = sqlSessionFactory.openSession(true);
            userMapper1 = sqlSession1.getMapper(UserMapper.class);
            User first = userMapper1.findUser("1");
            first.setName("xiaoxianxian");
            System.out.println(first + " ->name:" + first.getName());
            System.out.println("second query...");

            sqlSession2 = sqlSessionFactory.openSession(true);
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

    //endregion

    //region secondCache_differentSqlSession_commited

    /**
     * first query...
     * 2021-04-01 15:33:04,150 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 15:33:04 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 15:33:04,602 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=?
     * 2021-04-01 15:33:04,670 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
     * 2021-04-01 15:33:04,708 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
     * 2021-04-01 15:33:04,708 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, 1
     * 2021-04-01 15:33:04,710 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
     * com.xianyanyang.mybatis.entity.User@258d79be ->name:xiaoxianxian
     * second query...
     * 2021-04-01 15:33:04,722 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.5
     * com.xianyanyang.mybatis.entity.User@3081f72c ->name:xiaoxianxian
     */
    @Test
    public void secondCache_differentSqlSession_commited() {
        SqlSession sqlSession1 = null;
        SqlSession sqlSession2 = null;
        UserMapper userMapper1;
        UserMapper userMapper2;
        try {
            System.out.println("first query...");
            sqlSession1 = sqlSessionFactory.openSession(true);
            userMapper1 = sqlSession1.getMapper(UserMapper.class);
            User first = userMapper1.findUser("1");
            first.setName("xiaoxianxian");
            System.out.println(first + " ->name:" + first.getName());
            System.out.println("second query...");
            sqlSession1.commit();

            sqlSession2 = sqlSessionFactory.openSession(true);
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

    //endregion

    //region secondCacheDifferentSqlSessionWithUpdate

    /**
     * first query...
     * 2021-04-01 15:53:26,016 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.0
     * Thu Apr 01 15:53:26 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 15:53:26,488 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=?
     * 2021-04-01 15:53:26,558 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
     * 2021-04-01 15:53:26,609 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
     * 2021-04-01 15:53:26,609 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, foo
     * 2021-04-01 15:53:26,611 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
     * com.xianyanyang.mybatis.entity.User@14f9390f ->name:xiaoxianxian
     * second query...
     * 2021-04-01 15:53:26,623 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.5
     * com.xianyanyang.mybatis.entity.User@3148f668 ->name:xiaoxianxian
     * update user...
     * Thu Apr 01 15:53:26 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 15:53:26,770 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - ==>  Preparing: UPDATE USER SET NAME=? WHERE ID=?
     * 2021-04-01 15:53:26,770 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - ==> Parameters: foo(String), 1(String)
     * 2021-04-01 15:53:26,794 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.changeUserName] - <==    Updates: 1
     * third query...
     * 2021-04-01 15:53:26,795 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper] - Cache Hit Ratio [com.xianyanyang.mybatis.mapper.UserMapper]: 0.3333333333333333
     * Thu Apr 01 15:53:26 CST 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
     * 2021-04-01 15:53:26,934 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==>  Preparing: SELECT ID,NAME FROM USER WHERE ID=?
     * 2021-04-01 15:53:26,934 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - ==> Parameters: 1(String)
     * 2021-04-01 15:53:26,945 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==    Columns: ID, NAME
     * 2021-04-01 15:53:26,945 [main] TRACE [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==        Row: 1, foo
     * 2021-04-01 15:53:26,945 [main] DEBUG [com.xianyanyang.mybatis.mapper.UserMapper.findUser] - <==      Total: 1
     * com.xianyanyang.mybatis.entity.User@71329995 ->name:foo
     */
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

    //endregion secondCacheDifferentSqlSessionWithUpdate
}
