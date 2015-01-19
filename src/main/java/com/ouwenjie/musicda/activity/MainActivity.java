package com.ouwenjie.musicda.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ouwenjie.musicda.MusicdaApplication;
import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.adapter.PostsAdapter;
import com.ouwenjie.musicda.db.LitePalHelper;
import com.ouwenjie.musicda.model.Post;
import com.ouwenjie.musicda.obj.Constant;
import com.ouwenjie.musicda.utils.ActionBarHelper;
import com.ouwenjie.musicda.utils.DebugUtils;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;
import com.ouwenjie.musicda.utils.JsonUtils;
import com.ouwenjie.musicda.utils.SysUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String LOG_TAG = "MainActivity";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListview;
    private List<Post> mPostlist;               // 存储当前显示的Post的集合
    private PostsAdapter mAdapter;              // listview的数据适配器

    private LitePalHelper litePalHelper = new LitePalHelper();  // 数据库帮助类
    private SQLiteDatabase db;

    private RequestQueue mQueue;

    private SlidingPaneLayout mSlidePaneLayout;
    private ActionBarHelper actionBarHelper;

    private SysUtils mSysUtils;

    private long lastTime_Back; //记录上一次按Back键的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = litePalHelper.getDB(); // 初始化数据库
        mSysUtils = SysUtils.getInstance(this);
        mQueue = ((MusicdaApplication)getApplication()).getRequestQueue();

        initLayout();   // 初始化界面
        initPosts();    // 初始化Post列表
    }

    @Override
    protected void onStart(){
        super.onStart();
        mSlidePaneLayout.closePane();   // 关闭左侧菜单
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化ActionBar 和 SlidePaneLayout
     */
    private void initLayout() {
        actionBarHelper = new ActionBarHelper(this);
        actionBarHelper.init();

        initSlidePaneLayout();
        initSwipeRefreshLayout();

    }

    /**
     * 初始化左侧菜单
     */
    private void initSlidePaneLayout(){
        mSlidePaneLayout = (SlidingPaneLayout) findViewById(R.id.slide_menu_layout);
        mSlidePaneLayout.closePane();
        mSlidePaneLayout.setPanelSlideListener(new SlidingPaneLayout.SimplePanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                super.onPanelSlide(panel, slideOffset);
            }
            @Override
            public void onPanelOpened(View panel) {
                actionBarHelper.onPanelOpened();
            }
            @Override
            public void onPanelClosed(View panel) {
                actionBarHelper.onPanelClosed();
            }
        });

        LinearLayout menuCollect = (LinearLayout) findViewById(R.id.menu_collect);
        LinearLayout menuSearch = (LinearLayout) findViewById(R.id.menu_search);
        LinearLayout menuShare = (LinearLayout) findViewById(R.id.menu_share);
        LinearLayout menuSetting = (LinearLayout) findViewById(R.id.menu_setting);
        LinearLayout menuAbout = (LinearLayout) findViewById(R.id.menu_about);

        menuCollect.setOnClickListener(this);
        menuSearch.setOnClickListener(this);
        menuShare.setOnClickListener(this);
        menuSetting.setOnClickListener(this);
        menuAbout.setOnClickListener(this);
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefreshLayout(){

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        //  设置上拉刷新事件
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPosts();// 执行上拉的刷新的操作
            }
        });

    }

    /**
     * 初始化ListView的数据和适配器
     */
    private void initPosts() {

        mListview = (ListView) findViewById(R.id.posts_list);//  初始化ListView
        mListview.setOnItemClickListener(this);//  设置Item点击事件

        mPostlist = new ArrayList<>();
        mPostlist = litePalHelper.loadAllPosts();//从数据库中读取以存储的Post

        if (mPostlist.size() == 0) {    //数据库中没有内容,则启动网络请求数据

            Post.getNewestPosts(new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    mPostlist.addAll(JsonUtils.parseNewestPostsJson(new String(bytes))); // 初始化数据源
                    litePalHelper.saveAllPosts(mPostlist);// 将Post数据保存到数据库
                    mAdapter.notifyDataSetChanged();        // 数据集被更改，通知Adapter更新ListViewtrue
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
        mAdapter = new PostsAdapter(this, mPostlist);// 初始化适配器
        mListview.setAdapter(mAdapter);

        // 注册上下文菜单
        registerForContextMenu(mListview);
    }

    /**
     * 上拉刷新执行的异步任务，网络申请新的数据，并更新数据集和适配器
     */
    private void refreshPosts() {

        Post.getNewestPosts(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                onRefreshComplete(result);       // 规定的方法， Call onRefreshComplete when the list has been refreshed.
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(MainActivity.this, "更新失败...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
     * ListAdapter and turns off the progress bar.
     */
    private void onRefreshComplete(String result) {
        DebugUtils.e(LOG_TAG, "onRefreshComplete");
        List<Post> posts = (JsonUtils.parseNewestPostsJson(result));
        Collections.reverse(posts);//反转数序，让最新的Post显示在最顶端
        List<Post> newPost = litePalHelper.selectNewPost(posts, mPostlist);//选出新加载的Post当中，当前列表不存在的新Post
        if (newPost.size() > 0) {
            litePalHelper.saveAllPosts(newPost);    // 将新增 的Post存入数据库
            mPostlist.addAll(newPost);              // 更新当前List的数据集合
            mAdapter.notifyDataSetChanged();        // 数据集被更改，通知Adapter更新ListView
        }
        // Stop the refreshing indicator
        mSwipeRefreshLayout.setRefreshing(false);
    }

    // 后退键 响应事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long currentTime_Back = System.currentTimeMillis();
                if (currentTime_Back - lastTime_Back > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    lastTime_Back = currentTime_Back;
                    return true;
                } else {
                    finish();
                    DisplayAnimUtils.exitApp(this);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openPost(position);
    }

    /**
     * 根据position,打开post的详细内容
     * @param position
     */
    private void openPost(int position) {
        String url = mPostlist.get(position).getMobileUrl();
        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
        intent.putExtra(Constant.POST_MOBILE_URL, url);
        startActivity(intent);
        DisplayAnimUtils.slideRightInLeftOut(this);
    }

    /**
     * 左侧菜单的点击事件
     */
    @Override
    public void onClick(View v) {
        mSlidePaneLayout.closePane();
        switch (v.getId()) {
            case R.id.menu_collect:
                startActivity(new Intent(MainActivity.this, CollectionActivity.class));
                break;
            case R.id.menu_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.menu_share:
                startActivity(new Intent(MainActivity.this, SharedActivity.class));
                break;
            case R.id.menu_setting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.menu_about:
                startActivity(new Intent(MainActivity.this, AboutMusicdaActivity.class));
                break;
            default:
                break;
        }
        DisplayAnimUtils.slideRightInLeftOut(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (mSlidePaneLayout.isOpen()) {
                    mSlidePaneLayout.closePane();
                } else {
                    mSlidePaneLayout.openPane();
                }
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
        inflater.inflate(R.menu.menu_ptrlistview_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        DebugUtils.e(LOG_TAG,"=== "+ position +" ===");
        switch (item.getItemId()) {
            case R.id.copy_context:     // 复制内容到系统剪贴板
                String text = new String();
                text = mPostlist.get(position).getMusic().getName()+"\n"+mPostlist.get(position).getContent();
                DebugUtils.e(LOG_TAG, "==复制==" + "  " + text);
                mSysUtils.copyToSysClipboard(text);
                return true;
            case R.id.collect_context:
                DebugUtils.e(LOG_TAG, "==收藏==");
                litePalHelper.setCollectionPost(mPostlist.get(position),true);
                Toast.makeText(this,R.string.collect_success,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share_context:
                DebugUtils.e(LOG_TAG, "==分享==");
                Toast.makeText(this,"该功能将在新版本中添加...",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
