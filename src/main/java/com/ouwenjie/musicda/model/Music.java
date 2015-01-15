package com.ouwenjie.musicda.model;

import org.litepal.crud.DataSupport;

/**
 * 音乐 类
 * Created by 文杰 on 2014/12/4.
 */
public class Music extends DataSupport {

    String id;      // 歌曲的Id
    private String name;    // 歌曲的名字
    private String artist;  // 歌曲的演唱者
    private String xiamiId; // 歌曲的虾米Id

    //==============================================//

    public Music(){

    }

    public Music(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

//=======================================================================================//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getXiamiId() {
        return xiamiId;
    }

    public void setXiamiId(String xiamiId) {
        this.xiamiId = xiamiId;
    }

    public String getMusicIdUrl(){
        return "http://musicda.avosapps.com/api/posts?musicid="+id;
    }

    //========================================================================================//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Music music = (Music) o;

        if (artist != null ? !artist.equals(music.artist) : music.artist != null) return false;
        if (name != null ? !name.equals(music.name) : music.name != null) return false;
        if (xiamiId != null ? !xiamiId.equals(music.xiamiId) : music.xiamiId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (xiamiId != null ? xiamiId.hashCode() : 0);
        return result;
    }
}
