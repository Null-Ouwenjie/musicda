package com.ouwenjie.musicda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.model.Post;

import java.util.List;

/**
 * Post列表 数据适配器
 * Created by 文杰 on 2014/12/8.
 */
public class PostsAdapter extends BaseAdapter {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context,List<Post> posts) {
        this.context = context;
        this.posts = posts;
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

        viewHolder.musicName.setText(posts.get(position).getMusic().getName());
        viewHolder.musicArtist.setText(posts.get(position).getMusic().getArtist());
        viewHolder.postContent.setText(posts.get(position).getContent());
        viewHolder.postLikes.setText("("+posts.get(position).getLikes()+")");
        viewHolder.postComments.setText("("+posts.get(position).getReplies()+")");
        viewHolder.postUserName.setText(posts.get(position).getUser().getName());
        viewHolder.postCreateAt.setText(posts.get(position).getCreatedAt());

        return convertView;
    }

    class ViewHolder{
        TextView musicName;
        TextView musicArtist;
        TextView postContent;
        TextView postLikes;
        TextView postComments;
        TextView postUserName;
        TextView postCreateAt;

    }
}
