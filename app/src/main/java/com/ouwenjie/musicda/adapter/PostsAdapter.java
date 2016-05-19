package com.ouwenjie.musicda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.model.BitmapCache;
import com.ouwenjie.musicda.model.Music;
import com.ouwenjie.musicda.model.Post;
import com.ouwenjie.musicda.model.User;

import java.util.List;

/**
 * Post列表 数据适配器
 * Created by 文杰 on 2014/12/8.
 */
public class PostsAdapter extends BaseAdapter {

    private Context context;
    private List<Post> posts;
    RequestQueue mQueue;

    public PostsAdapter(Context context, List<Post> posts, RequestQueue queue) {
        this.context = context;
        this.posts = posts;
        this.mQueue = queue;

    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_post_item,null);

            viewHolder.userAvatar = (NetworkImageView) convertView.findViewById(R.id.post_user_avatar);
            viewHolder.musicName = (TextView) convertView.findViewById(R.id.music_name);
            viewHolder.musicArtist = (TextView) convertView.findViewById(R.id.music_artist);
            viewHolder.postContent = (TextView) convertView.findViewById(R.id.post_content);
            viewHolder.postLikes = (TextView) convertView.findViewById(R.id.post_likes);
            viewHolder.postComments = (TextView) convertView.findViewById(R.id.post_comments);
            viewHolder.postUserName = (TextView) convertView.findViewById(R.id.post_user_name);
            viewHolder.postCreateAt = (TextView) convertView.findViewById(R.id.post_create_at);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Post post = posts.get(position);
        Music music = post.getMusic();
        User user = post.getUser();
        String avatarUrl = user.getAvatar();
        if(avatarUrl.startsWith("//")){
            avatarUrl = "https:"+avatarUrl;
        }
        if(avatarUrl.contains("{size}")){
            avatarUrl = avatarUrl.replace("{size}",256+"");
        }

        viewHolder.userAvatar.setDefaultImageResId(R.drawable.musicda_icon);
        viewHolder.userAvatar.setErrorImageResId(R.drawable.musicda_icon);
        viewHolder.userAvatar.setImageUrl(avatarUrl,new ImageLoader(mQueue,new BitmapCache()));

        viewHolder.postUserName.setText(user.getName());
        viewHolder.musicName.setText(music.getName());
        viewHolder.musicArtist.setText(music.getArtist());
        viewHolder.postContent.setText(post.getContent());
        viewHolder.postLikes.setText("("+post.getLikes()+")");
        viewHolder.postComments.setText("("+post.getReplies()+")");
        viewHolder.postCreateAt.setText(post.getCreatedAt());

        return convertView;
    }

    class ViewHolder{
        NetworkImageView userAvatar;
        TextView postUserName;
        TextView musicName;
        TextView musicArtist;
        TextView postContent;
        TextView postLikes;
        TextView postComments;
        TextView postCreateAt;

    }
}
