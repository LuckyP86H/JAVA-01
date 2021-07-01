# Kafka集群搭建

**1、确认JDK版本**

```shell
[root@kafka001 ~]# java -version
openjdk version "1.8.0_292"
OpenJDK Runtime Environment (build 1.8.0_292-b10)
OpenJDK 64-Bit Server VM (build 25.292-b10, mixed mode)
```

**2、解压缩安装包**

```shell
[root@kafka001 ~]# ls
kafka_2.12-2.7.0.tgz
[root@kafka001 ~]# tar zxvf kafka_2.12-2.7.0.tgz
```

**3、创建集群的日志文件夹**

```shell
[root@kafka001 ~]# ls
kafka_2.12-2.7.0  kafka_2.12-2.7.0.tgz
[root@kafka001 ~]# cd kafka_2.12-2.7.0/
[root@kafka001 kafka_2.12-2.7.0]# ls
bin  config  libs  LICENSE  NOTICE  site-docs
[root@kafka001 kafka_2.12-2.7.0]# mkdir -p /tmp/kafka/kafka-logs1
[root@kafka001 kafka_2.12-2.7.0]# mkdir -p /tmp/kafka/kafka-logs2
[root@kafka001 kafka_2.12-2.7.0]# mkdir -p /tmp/kafka/kafka-logs3
```

**4、启动zookeeper**

```shell
[root@kafka001 kafka_2.12-2.7.0]# bin/zookeeper-server-start.sh config/zookeeper.properties
```

**5、启动kafka服务**

1）第一台服务

```shell
[root@kafka001 ~]# cd ~/kafka_2.12-2.7.0
[root@kafka001 kafka_2.12-2.7.0]# ./bin/kafka-server-start.sh config/kafka9001.properties
```

2）第二台服务

```sh
[root@kafka001 ~]# cd ~/kafka_2.12-2.7.0
[root@kafka001 kafka_2.12-2.7.0]# ./bin/kafka-server-start.sh config/kafka9002.properties
```

3）第三台服务

```shell
[root@kafka001 ~]# cd ~/kafka_2.12-2.7.0
[root@kafka001 kafka_2.12-2.7.0]# ./bin/kafka-server-start.sh config/kafka9003.properties
```

**6、创建topic**

```shell
[root@kafka001 ~]# cd ~/kafka_2.12-2.7.0
[root@kafka001 kafka_2.12-2.7.0]# bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic test32 --partitions 3 --replication-factor 2
Created topic test32.
```

**7、生产者生产消息**

```shell
[root@kafka001 kafka_2.12-2.7.0]# bin/kafka-console-producer.sh --bootstrap-server localhost:9003 --topic test32
>love
>hi nuomi
```

**8、消费者消费消息**

```shell
[root@kafka001 kafka_2.12-2.7.0]# bin/kafka-console-consumer.sh --bootstrap-server localhost:9001 --topic test32 --from-beginning
love
hi nuomi
```