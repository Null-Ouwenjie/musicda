package com.ouwenjie.musicda.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ouwenjie.musicda.model.Music;
import com.ouwenjie.musicda.model.Post;
import com.ouwenjie.musicda.model.User;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * LitePal是开源的Android库，让开发者使用SQLite变得十分简单
 * 此类是帮助开发者使用LitePal
 * Created by 文杰 on 2014/12/25.
 */
public class LitePalHelper {

    public SQLiteDatabase getDB(){
        return Connector.getDatabase();
    }

    //  将postList保存到数据库中
    public void saveAllPosts(List<Post> postList){
        List<Music> musicList = new ArrayList<>();
        List<User> userList = new ArrayList<>();

        for(Post p : postList){
            musicList.add(p.getMusic());
            userList.add(p.getUser());
        }

        DataSupport.saveAll(musicList);
        DataSupport.saveAll(userList);
        DataSupport.saveAll(postList);
    }

    //  从newPost中，选出数据库中没有的post
    public List<Post> selectNewPost(List<Post> newpost,List<Post> allPost){
        List<Post> nPost = new ArrayList<>();
        for(Post p : newpost){      //  因为如果newpost是asList返回的的数据，是不可以直接调用removeAll的，因为没有实现此方法的子类。
            nPost.add(p);
        }
        nPost.removeAll(allPost);   //除掉 postList中与allPost相同的元素
        return nPost;
    }

    // 获取数据库中所有的Post
    public List<Post> loadAllPosts(){
        List<Music> musicList = DataSupport.findAll(Music.class);
        List<User> userList = DataSupport.findAll(User.class);
        List<Post> postList = DataSupport.findAll(Post.class);

        int id=1;
        for(Post p : postList){
            p.setMusic(musicList.get(id-1));
            p.setUser(userList.get(id-1));
            id++;
        }
        return postList;
    }

    /**
     * 更新该Post的收藏状态
     * @param post
     * 要更改状态的Post
     * @param collect
     * true 收藏；false 取消收藏
     */
    public void setCollectionPost(Post post,boolean collect){
            ContentValues values = new ContentValues();
        if(collect) {
            values.put("collection", 1);
        }else{
            values.put("collection", 0);
        }
            DataSupport.updateAll(Post.class, values, "url = ?", post.getUrl());
    }

    /**
     * 获取所有collection的Post
     */
    public List<Post> getCollectionPost(){

        List<Post> cPost = new ArrayList<>();
        List<Post> posts = loadAllPosts();
        for(Post p : posts){
            if(p.getCollection() != 0){
                cPost.add(p);
            }
        }
        return cPost;
    }




}
