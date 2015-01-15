package com.ouwenjie.musicda.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.ouwenjie.musicda.model.Comment;
import com.ouwenjie.musicda.model.Post;

import java.util.Arrays;
import java.util.List;

/**
 * Json相关工具类,使用Gson
 * Created by 文杰 on 2014/12/8.
 */
public class JsonUtils {

    private JsonUtils(){

    }

    /**
     *  将服务器返回的最新的20条Post数据 byte ， 转换成Post对象数组,返回一个post的集合
     * @param res
     * @return
     *  List<Post>
     */
    public static List<Post> parseNewestPostsJson(String res) {
        return parsePostsJson(res);
    }

    /**
     *  解析服务器返回的Json数据，返回某一条Post的评论(Comment)的集合
     * @param res
     * @return
     */
    public static List<Comment> parseCommentJson(String res){
        Comment[] comments = new Gson().fromJson(res,Comment[].class);
        return Arrays.asList(comments);
    }


    /**
     * 解析服务器返回的Post的Json数据，返回Post集合
     * @param res
     * @return
     */
    public static List<Post> parsePostsJson(String res){
        Post[] posts = new Gson().fromJson(res, Post[].class);
        Log.e("JsonUtil","get result posts`s item is : "+posts.length+"............");
        return Arrays.asList(posts);
    }
}
