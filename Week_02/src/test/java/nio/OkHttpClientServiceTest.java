package nio;

import org.junit.Assert;
import org.junit.Test;

/**
 * 基于OkHttp的请求服务测试
 */
public class OkHttpClientServiceTest {

    private String url = "http://localhost:8803";

    @Test
    public void testDoGet() {
        String result = OkHttpClientService.doGet(url);
        Assert.assertEquals("hello,nio", result);
    }
}
