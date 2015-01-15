package com.ouwenjie.musicda.model;

import org.litepal.crud.DataSupport;

/**
 * Post的评论类
 * Created by 文杰 on 2014/12/4.
 */
public class Comment extends DataSupport {

    private String content;
    private User user;

    //==========================================================/

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //=========================================================//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (content != null ? !content.equals(comment.content) : comment.content != null)
            return false;
        if (user != null ? !user.equals(comment.user) : comment.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
