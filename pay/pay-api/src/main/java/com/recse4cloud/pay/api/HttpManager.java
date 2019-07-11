package com.recse4cloud.pay.api;

import okhttp3.*;
import okhttp3.internal.tls.OkHostnameVerifier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;

public class HttpManager {

    /**
     * 请求客户端
     */
    private static final OkHttpClient client = new OkHttpClient.Builder()
//            .connectTimeout(Duration.ofSeconds(50))
//            .readTimeout(Duration.ofSeconds(50))
            .retryOnConnectionFailure(true)
            .connectionPool(new ConnectionPool()).build();

    /**
     * post form表单请求
     *
     * @param url
     * @param params
     * @param heads
     * @param callback
     * @return
     */
    public static void post(String url, Map<String, Object> params, Map<String, Object> heads, Callback callback) {
        Assert.notNull(url, "请求URL不能为空");

        FormBody.Builder body = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> body.add(k, String.valueOf(v)));
        }
        Request.Builder request = new Request.Builder().url(url);
        request.post(body.build());
        if (heads != null && heads.size() > 0) {
            heads.forEach((k, v) -> request.addHeader(k, String.valueOf(v)));
        }
        client.newCall(request.build()).enqueue(callback);
    }

    /**
     * @param url
     * @param params
     * @param callback
     */
    public static void post(String url, Map<String, Object> params, Callback callback) {
        post(url, params, null, callback);
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @param heads
     * @param callback
     */
    public static void get(String url, Map<String, Object> params, Map<String, Object> heads, Callback callback) {
        Assert.notNull(url, "请求URL不能为空");
        Request.Builder request = new Request.Builder();
        if (heads != null && heads.size() > 0) {
            heads.forEach((k, v) -> request.addHeader(k, String.valueOf(v)));
        }
        StringBuilder sb = new StringBuilder();
        if (params != null && params.size() > 0) {
            if (!url.contains("?")) {
                sb.append("?");
            }
            if (url.contains("&")) {
                sb.append("&");
            }
            params.forEach((k, v) -> sb.append(k + "=" + v).append("&"));
        }
        request.url(url + sb.toString()).get().build();
        client.newCall(request.build()).enqueue(callback);
    }

    /**
     * @param url
     * @param callback
     */
    public static void get(String url, Callback callback) {
        get(url, null, null, callback);
    }

    /**
     * 腾讯退款业务需要加密证书
     *
     * @param url
     * @param xml
     * @param keyPath
     * @param keyPsw
     */
    public static void postSSL(String url, String xml, String keyPath, String keyPsw, Callback callback) throws Exception {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream in = new FileInputStream(keyPath);
        try {
            keyStore.load(in, keyPsw.toCharArray());
        } finally {
            in.close();
        }
        SSLContext ctx = SSLContext.getInstance(TlsVersion.TLS_1_0.javaName());
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore, keyPsw.toCharArray());
        ctx.init(factory.getKeyManagers(), null, new SecureRandom());
        SSLSocketFactory sslf = ctx.getSocketFactory();
        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(Duration.ofSeconds(50))
//                .readTimeout(Duration.ofSeconds(50))
                .sslSocketFactory(sslf)
                .hostnameVerifier(OkHostnameVerifier.INSTANCE)
                .connectionPool(new ConnectionPool()).build();
        RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request.Builder request = new Request.Builder()
                .url(url).post(body);
//        client.newCall(requst.build()).enqueue(callback);
        Call call = client.newCall(request.build());
        try {
            callback.onResponse(call, call.execute());
        } catch (IOException e) {
            callback.onFailure(call, e);
        }
    }

    public static void postXmlExecute(String url, String xml, Callback callback) {
        MediaType type = MediaType.parse("application/xml; charset=utf-8");
        RequestBody body = RequestBody.create(type, xml);
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);
        try {
            callback.onResponse(call, call.execute());
        } catch (IOException e) {
            callback.onFailure(call, e);
        }
    }

    /**
     * 同步post请求
     *
     * @param url
     * @param map
     * @param callback
     */
    public static void postFormExecute(String url, Map<String, Object> map, @NotNull Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!StringUtils.isBlank(String.valueOf(entry.getValue())) && !StringUtils.equals(String.valueOf(entry.getValue()), "null")) {
                builder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = client.newCall(request);
        try {
            callback.onResponse(call, call.execute());
        } catch (IOException e) {
            callback.onFailure(call, e);
        }
    }

    /**
     * 模拟http发送get请求
     * 同步
     *
     * @param url
     * @param callback
     */
    public static void getExecute(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            callback.onResponse(call, response);
        } catch (IOException e) {
            callback.onFailure(call, e);
        }
    }
}
