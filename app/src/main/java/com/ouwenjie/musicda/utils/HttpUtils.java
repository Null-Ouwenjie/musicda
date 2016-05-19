package com.ouwenjie.musicda.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Http工具类
 * 封装了android-async-http开源框架
 * Created by 文杰 on 2014/12/4.
 *
 * 根据音乐 id 获取该音乐下的所有 Posts
 * 如：http://musicda.avosapps.com/api/posts?musicid=54797673e4b05889875bf322
 *
 * 获取 Post 的所有评论
 * 需要一个 postid 作为参数
 * 如：http://musicda.avosapps.com/comments?postid=54797673e4b072561816aca1
 *
 * 获取最新 20 条 Posts
 * http://musicda.avosapps.com/api/posts/newest
 *
 */
public class HttpUtils {

    private static final String MUSICDA_BASE_URL = "http://musicda.avosapps.com/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private HttpUtils(){

    }
    /**
     * 一系列get方法
     * @param url
     *              访问地址
     * @param responseHandler
     *              请求响应回调
     */
    public static void get(String url,AsyncHttpResponseHandler responseHandler){        // 不带参数的get方法
        client.get(url,responseHandler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {    //带参数的get方法
        client.get(url, params, responseHandler);
    }

    public static void get(Context context,String url,AsyncHttpResponseHandler responseHandler){        // 带上下文、不带参数的get方法
        client.get(context,url,responseHandler);
    }

    public static void get(Context context,String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {    // 带上下文、带参数的get方法
        client.get(context,url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return MUSICDA_BASE_URL + relativeUrl;
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

}
