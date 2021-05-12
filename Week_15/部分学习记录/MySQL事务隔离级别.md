# MySQL事务隔离级别

## 隔离级别

背景：顾客A（买家）账户有1000元，淘宝商户B（卖家）账户有1000元

### 读未提交 READ UNCOMMITTED

> MySQL默认的隔离级别是可重复读**REPEATABLE READ**。
>
> 设置隔离级别为读未提交 read uncommited;
>
> ```mysql
> select @@tx_isolation; -- REPEATABLE-READ
> set session transaction isolation level Read uncommitted; -- 在商户B的会话中设置
> ```

**1、A账户 -100元， B账户 +100元，A还未提交事务，打电话给B，让其发货；**

```mysql
start transaction;
update account set amount= amount-100 where name= 'A';  -- A账户900
update account set amount= amount+100 where name= 'B';  -- B账户1100
```

> MySQL通过**set autocommit、 start transaction 、commit 、rollback**等语句支持本地事务；
>
> 默认情况下，MySQL是自动提交的（autocommit），如果需要明确的**commit**和rollback来提交和回滚事务，就需要明确的事务控制领命来开始事务。

**2、B查询账户收到了100元（脏读），把货物给A；**

```mysql
start transaction;
select * from account where name='B';  -- B账户1100
```

**3、顾客A收到货以后，A撤销回滚；**

```mysql
rollback;  -- A账户1000 B账户1000
```

**4、B再查询没有收到100元 ；**

```mysql
start transaction;
select * from acctount where name= 'b'; -- B账户1000
```

#### **脏读：在一个事务中读取到了另一个事务未提交的数据。**



### 读已提交 READ COMMITTED



### 可重复读 REPEATABLE READ

可以避免脏读。

**1、A账户 -100元， B账户 +100元，A还未提交事务，打电话给B，让其发货；**

```mysql
start transaction;
update account set amount= amount-100 where name= 'A';  -- A账户900
update account set amount= amount+100 where name= 'B';  -- B账户1100
```

**2、B查询账户未收到了100元，拒绝把货物发给A；**

```mysql
start transaction;
select * from account where name='B';  -- B账户1000
```

**3、顾客提交事务，确认汇款；**

```mysql
commit;
```

**4、B再查询确认收到100元，给顾客A进行发货 ；**

```mysql
start transaction;
select * from acctount where name= 'b'; -- B账户1100
```



### 串行化 SERIALIZABLE