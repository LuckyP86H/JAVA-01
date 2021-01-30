package nio;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

/**
 * 基于OkHttp的请求服务
 */
public class OkHttpClientService {

    private static OkHttpClient singletonInstance;

    private OkHttpClientService() {
    }

    public static OkHttpClient instance() {
        if (singletonInstance == null) {
            singletonInstance = new OkHttpClient.Builder()
                    .connectTimeout(6L, TimeUnit.SECONDS)
                    .readTimeout(6L, TimeUnit.SECONDS)
                    .writeTimeout(6L, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(50, 60, TimeUnit.SECONDS))
                    .build();
        }
        return singletonInstance;
    }

    /**
     * GET 请求
     *
     * @param url 请求地址
     * @return 响应体字符串
     */
    public static String doGet(String url) {
        try {
            Request request = new Request.Builder().get().url(url).build();
            Response response = OkHttpClientService.instance().newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            throw new RuntimeException("do request error", e);
        }
        return null;
    }
}
