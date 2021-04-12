package com.xianyanyang;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * redis实现分数排名或者排行榜
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class SortedSetRedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    private String key = "geek-score";
    private String top = "xianyanyang";
    private String last = "xiaozhang";

    /**
     * 对应redis客户端命令：
     * 127.0.0.1:6379> ZADD geek-score 100 xianyanyang
     * (integer) 1
     * 127.0.0.1:6379> ZADD geek-score 90 jone
     * (integer) 1
     * 127.0.0.1:6379> ZADD geek-score 79 xiaozhang
     * (integer) 1
     * 127.0.0.1:6379> ZADD geek-score 97 kitty
     * (integer) 1
     * 127.0.0.1:6379> ZINCRBY geek-score 102 xianyanyang
     * "202"
     * 127.0.0.1:6379> ZINCRBY geek-score 1 xiaozhang
     * "80"
     */
    @Before
    public void setUp() {
        this.redisTemplate.opsForZSet().add(key, top, 100);
        this.redisTemplate.opsForZSet().add(key, "jone", 90);
        this.redisTemplate.opsForZSet().add(key, last, 79);
        this.redisTemplate.opsForZSet().add(key, "kitty", 97);

        this.redisTemplate.opsForZSet().incrementScore(key, top, 102);
        this.redisTemplate.opsForZSet().incrementScore(key, last, 1);
    }

    /**
     * 从高到低排序
     * 获取设置的 RedisZSetCommands.Tuple ，其中得分之间 min 并 max 从下令从高分到低分排序集
     *
     * 对应redis客户端命令：
     * 127.0.0.1:6379> ZREVRANGEBYSCORE geek-score 10000 0 withscores
     * 1) "xianyanyang"
     * 2) "202"
     * 3) "kitty"
     * 4) "97"
     * 5) "jone"
     * 6) "90"
     * 7) "xiaozhang"
     * 8) "80"
     *
     * 执行结果：
     * name-->xianyanyang>>>>>202.0
     * name-->kitty>>>>>97.0
     * name-->jone>>>>>90.0
     * name-->xiaozhang>>>>>80.0
     */
    @Test
    public void reverseRangeByScoreWithScores() {
        Set<ZSetOperations.TypedTuple> rangeByScoreWithScores = this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, 0, 100000);
        rangeByScoreWithScores.forEach(item -> {
            Object value = item.getValue();
            Double score = item.getScore();
            System.out.println("name-->" + value + ">>>>>" + score);
        });
    }


    /**
     * 列出排行榜前三
     * 获取 RedisZSetCommands.Tuple 范围从 start 以及 end 得分在哪里 min 和  max 从有序集合中排序的高 - >低。
     *
     * redis客户端命令：
     * 127.0.0.1:6379> ZREVRANGEBYSCORE  geek-score 100000 0 withscores limit 0 3
     * 1) "xianyanyang"
     * 2) "202"
     * 3) "kitty"
     * 4) "97"
     * 5) "jone"
     * 6) "90"
     *
     * 执行结果：
     * name-->xianyanyang>>>>>202.0
     * name-->kitty>>>>>97.0
     * name-->jone>>>>>90.0
     */
    @Test
    public void reverseRangeByScoreWithScores_limit() {
        Set<ZSetOperations.TypedTuple<String>> rangeByLex = this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, 0, 10000, 0, 3);
        rangeByLex.forEach(item -> {
            Object value = item.getValue();
            Double score = item.getScore();
            System.out.println("name-->" + value + ">>>>>" + score);
        });
    }
}
