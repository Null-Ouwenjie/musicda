package com.ouwenjie.musicda.utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * 网络访问帮助类，使用Volley框架。
 * Created by 文杰 on 2015/1/19.
 */
public class NetWorkAssist {

    public static void StringRequest(String url,Response.Listener<String> listener,Response.ErrorListener errorlistener){
        StringRequest request = new StringRequest(url,listener,errorlistener);
    }

    public static void StringRequest(int method,String url,Response.Listener<String> listener,Response.ErrorListener errorlistener){
        StringRequest request = new StringRequest(method,url,listener,errorlistener){

        };

    }


}
