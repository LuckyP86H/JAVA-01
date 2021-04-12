package com.xianyanyang;

import com.xianyanyang.cache.User;
import com.xianyanyang.cache.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({Application.class})
public class SpringAnnotationCacheTest {

    @Autowired
    private UserService userService;

    private String name = "xianyanyang";
    private String userId = "1";

    @Test
    public void testCacheable() {
        User result = this.userService.getUser(userId);
        Assert.assertEquals(userId, result.getId());
        Assert.assertEquals(name, result.getName());

        User cacheResult = this.userService.getUser(userId);
        Assert.assertEquals(userId, cacheResult.getId());
        Assert.assertEquals(name, cacheResult.getName());
    }

    private String newName = "xianyanyang-foo";

    @Test
    public void testCachePut() {
        User result = this.userService.getUser("1");
        Assert.assertEquals(userId, result.getId());
        Assert.assertEquals(name, result.getName());

        User user = this.userService.updateUserName(userId, newName);
        Assert.assertEquals(userId, user.getId());
        Assert.assertEquals(newName, user.getName());

        User cacheResult = this.userService.getUser(userId);
        Assert.assertEquals(userId, cacheResult.getId());
        Assert.assertEquals(newName, cacheResult.getName());
    }

    @Test
    public void testCacheEvict() {
        User result = this.userService.getUser("1");
        Assert.assertEquals(userId, result.getId());
        Assert.assertEquals(name, result.getName());

        User cacheResult = this.userService.getUser(userId);
        Assert.assertEquals(userId, cacheResult.getId());
        Assert.assertEquals(name, cacheResult.getName());

        userService.deleteUser("1");
        User deletedUser = this.userService.getUser("1");
        Assert.assertEquals(deletedUser, null);
    }
}
