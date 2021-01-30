package nio;

import io.netty.handler.codec.http.HttpHeaders;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

/**
 * 基于OkHttp的请求服务(懒汉模式+双检查+volatile)
 */
public class OkHttpClientService {

    private static volatile OkHttpClientService singletonInstance;

    private static OkHttpClient httpClient;

    public OkHttpClientService setHttpClient(OkHttpClient okHttpClient) {
        OkHttpClientService.httpClient = okHttpClient;
        return this;
    }

    private OkHttpClientService() {
    }

    public static OkHttpClientService instance() {
        if (singletonInstance == null) {
            synchronized (OkHttpClientService.class) {
                if (singletonInstance == null) {
                    httpClient = new OkHttpClient.Builder()
                            .connectTimeout(6L, TimeUnit.SECONDS)
                            .readTimeout(6L, TimeUnit.SECONDS)
                            .writeTimeout(6L, TimeUnit.SECONDS)
                            .connectionPool(new ConnectionPool(50, 60, TimeUnit.SECONDS))
                            .build();
                    singletonInstance = new OkHttpClientService().setHttpClient(httpClient);
                }
            }
        }
        return singletonInstance;
    }

    /**
     * GET 请求
     *
     * @param url 请求地址
     * @return 响应体字符串
     */
    public String doGet(String url) {
        try {
            Request request = new Request.Builder().get().url(url).build();
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            throw new RuntimeException("do request error", e);
        }
        return null;
    }

    public String doGet(String url, HttpHeaders headers) {
        try {
            Request.Builder builder = new Request.Builder();
            if (headers != null) {
                headers.forEach(header -> {
                    builder.addHeader(header.getKey(), header.getValue());
                });
            }
            Request request = new Request.Builder().get().url(url).build();
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            throw new RuntimeException("do request error", e);
        }
        return null;
    }
}
