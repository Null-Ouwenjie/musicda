package com.ouwenjie.musicda.model;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ouwenjie.musicda.utils.HttpUtils;

import org.litepal.crud.DataSupport;

/**
 * 一条Post
 * Created by 文杰 on 2014/12/4.
 */
public class Post extends DataSupport {

    String id;          // Post的Id
    private String url;         // Post的URL
    private String mobileUrl;   // Post的移动设备URL
    private String content;     // Post的内容
    private int replies;        // Post的回复数
    private int likes;          // Post的红心数
    private String createdAt;    // Post的创建时间

    private Music music;        // Post的音乐
    private User user;          // 发布Post的User


    private int collection;     // 标志该Post是否被收藏（>0）

    public Post(){

    }

    public Post(String id, String url, String mobileUrl, String content, int replies, int likes, String createdAt, Music music, User user) {
        this.id = id;
        this.url = url;
        this.mobileUrl = mobileUrl;
        this.content = content;
        this.replies = replies;
        this.likes = likes;
        this.createdAt = createdAt;
        this.music = music;
        this.user = user;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCreatedAt() {
        String date = new String(createdAt.toCharArray(),0,10);
        return date;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void getNewestPosts(AsyncHttpResponseHandler asyncHttpResponseHandler){
        String url = "http://musicda.avosapps.com/api/posts/newest";
        HttpUtils.get(url, asyncHttpResponseHandler);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (mobileUrl != null ? !mobileUrl.equals(post.mobileUrl) : post.mobileUrl != null)
            return false;
        if (url != null ? !url.equals(post.url) : post.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (mobileUrl != null ? mobileUrl.hashCode() : 0);
        return result;
    }
}
