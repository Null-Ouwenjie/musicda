package com.ouwenjie.musicda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.ouwenjie.musicda.MusicdaApplication;
import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.adapter.PostsAdapter;
import com.ouwenjie.musicda.base.BaseActivity;
import com.ouwenjie.musicda.db.LitePalHelper;
import com.ouwenjie.musicda.model.Post;
import com.ouwenjie.musicda.obj.Constant;
import com.ouwenjie.musicda.utils.ActionBarHelper;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏夹界面
 * Created by 文杰 on 2014/12/29.
 */
public class CollectionActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "CollectionActivity";

    private ListView mListView;
    private List<Post> mPostList;
    private PostsAdapter mAdapter;

    private LitePalHelper litePalHelper;

    private LinearLayout collectLayout;

    private ActionBarHelper actionBarHelper;

    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_collection);

        mQueue = ((MusicdaApplication)getApplication()).getRequestQueue();

        initLayout();
        initList();
    }

    private void initLayout() {
        collectLayout = (LinearLayout) findViewById(R.id.collect_layout);
        collectLayout.setOnTouchListener(this);

        actionBarHelper = new ActionBarHelper(this);
        actionBarHelper.onPanelClosed();
    }

    private void initList(){
        mListView = (ListView) findViewById(R.id.collect_listview);
        mListView.setOnItemClickListener(this);
        mListView.setOnTouchListener(this);
        registerForContextMenu(mListView);

        litePalHelper = new LitePalHelper();
        mPostList = new ArrayList<>();
        mPostList = litePalHelper.getCollectionPost();
        Log.e("CollectionActivity  == ", mPostList.size() + "");

        if(mPostList.size() == 0) {
            Toast.makeText(this,"收藏夹为空",Toast.LENGTH_LONG).show();
        }else{
            mAdapter = new PostsAdapter(this, mPostList,mQueue);// 初始化适配器
            mListView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openPost(position);
    }

    private void openPost(int position) {
        String url = mPostList.get(position).getMobileUrl();
        Intent intent = new Intent(CollectionActivity.this, BrowserActivity.class);
        intent.putExtra(Constant.POST_MOBILE_URL, url);
        startActivity(intent);
        DisplayAnimUtils.slideRightInLeftOut(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                startActivity(new Intent(CollectionActivity.this, MainActivity.class));
                DisplayAnimUtils.slideLeftInRightOut(this);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v,event);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                startActivity(new Intent(CollectionActivity.this, MainActivity.class));
                DisplayAnimUtils.slideLeftInRightOut(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_collection_listview_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        Log.e(TAG,"=== "+ position +" ===");
        switch (item.getItemId()) {
            case R.id.copy_context:
                Log.e(TAG, "==复制==");
                return true;
            case R.id.collect_context:
                Log.e(TAG, "==取消收藏==");
                litePalHelper.setCollectionPost(mPostList.get(position),false);
                mPostList.remove(position);
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.share_context:
                Log.e(TAG, "==分享==");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
