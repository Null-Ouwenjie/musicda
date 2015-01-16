package com.ouwenjie.musicda.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.base.BaseActivity;
import com.ouwenjie.musicda.db.LitePalHelper;
import com.ouwenjie.musicda.model.Post;
import com.ouwenjie.musicda.utils.ActionBarHelper;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索：根据歌曲名字搜索Post
 * Created by 文杰 on 2015/1/14.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String LOG_TAG = "SearchActivity";

    private ViewGroup mRootLayout;
    private ActionBarHelper mActionBarHelper;

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    private List<String> mAllTitle;

    private EditText mSearchEdit;
    private Button mSearchBtn;
    private List<String> resultList;
    private List<Integer> resultIndex;

    private List<Post> mPosts;

    private LitePalHelper mLitePalHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        resultList = new ArrayList<>();
        resultIndex = new ArrayList<>();

        initLayout();
        mAllTitle = getMusicTitle();  //将数据库中的Post的Music的名字 取出来

    }

    private List<String> getMusicTitle() {

        List<String> lists = new ArrayList<>();
        mLitePalHelper = new LitePalHelper();
        mPosts = mLitePalHelper.loadAllPosts();
        for(Post p : mPosts) {
            lists.add(p.getMusic().getName());
        }
        return lists;
    }

    private void initLayout() {
        mRootLayout = (ViewGroup) findViewById(R.id.search_layout);
        mRootLayout.setOnTouchListener(this);

        mActionBarHelper = new ActionBarHelper(this);
        mActionBarHelper.onPanelClosed();

        mListView = (ListView) findViewById(R.id.search_result_listview);
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,resultList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mSearchEdit = (EditText) findViewById(R.id.search_song_et);
        mSearchBtn = (Button) findViewById(R.id.search_song_btn);
        mSearchBtn.setOnClickListener(this);

    }

    @Override
      public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                DisplayAnimUtils.slideLeftInRightOut(this);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v, event);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                DisplayAnimUtils.slideLeftInRightOut(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        clearResult();
        hideKeyBoard();
        searchingTitle();
        updateList();
    }

    /**
     * 清空之前保存的搜索结果
     */
    private void clearResult() {
        resultList.removeAll(resultList);
        resultIndex.removeAll(resultIndex);
    }

    /**
     * 隐藏键盘
     */
    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
    }

    /**
     * 检索歌曲
     */
    private void searchingTitle() {
        String searchInfo = mSearchEdit.getText().toString().trim();

        for(int i=0;i<mAllTitle.size();i++){
            if(mAllTitle.get(i).contains(searchInfo)){
                resultList.add(mAllTitle.get(i));
                resultIndex.add(i);
            }
        }
    }

    /**
     * 更新搜索结果的List
     */
    private void updateList() {
        if(resultList.size() == 0){
            Toast.makeText(this, "没有该歌曲的Post...", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int index = resultIndex.get(position);
        String url = mPosts.get(index).getMobileUrl();
        Intent intent = new Intent(SearchActivity.this, BrowserActivity.class);
        intent.putExtra("POST_MOBILE_URL", url);
        startActivity(intent);
        DisplayAnimUtils.slideRightInLeftOut(this);
    }
}
