package com.ouwenjie.musicda.model;

import org.litepal.crud.DataSupport;

/**
 * 用户类
 * Created by 文杰 on 2014/12/4.
 */
public class User extends DataSupport {

    String id;      // 用户的Id
    private String name;    // 用户名
    private String avatar;  // 用户头像

//===========================================================//
    public User(){

    }

    public User(String name) {
        this.name = name;
    }
//===========================================================//

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

//============================================================//


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }
}
