package com.ouwenjie.musicda.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.base.BaseActivity;
import com.ouwenjie.musicda.obj.Constant;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;
import com.ouwenjie.musicda.utils.SysUtils;

/**
 * 一个Post详情页面 webView
 * Created by 文杰 on 2014/12/10.
 */
public class BrowserActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressDialog dialog;

    private SysUtils mUtils;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView mErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post_detail);

        mErrorText = (TextView) findViewById(R.id.error_textview);

        mUtils = SysUtils.getInstance(this);
        preferences = mUtils.getPreferences();
        editor = mUtils.getEditor();

        //  获取post移动版所要加载的url
        String url = getIntent().getExtras().getString(Constant.POST_MOBILE_URL);
        initWebView(url);
    }

    protected void initWebView(String url) {
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setOnTouchListener(this);
        mWebView.setWebViewClient(new WebViewClient() {
            // 在客户端中打开web页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();   //页面加载完毕，关闭dialog显示
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                dialog.dismiss();       // 网页加载失败
                Toast.makeText(BrowserActivity.this,"网页链接失败...",Toast.LENGTH_LONG).show();
                mWebView.setVisibility(View.GONE);
                mErrorText.setVisibility(View.VISIBLE);
            }
        });

        //如果系统设置了非WIFI环境下允许播放音乐，就启用js
        if(preferences.getBoolean(Constant.KEY_AUDIO_ENABLE, false)){
            mWebView.getSettings().setJavaScriptEnabled(true);
        }else if (mUtils.isWifiConnect()) {   // 若系统设置是不允许在非WIFI环境下播放音乐，则检测到WIFI环境下，启用js
            mWebView.getSettings().setJavaScriptEnabled(true);
        }
        mWebView.loadUrl(url);
        //正在load页面，弹出dialog提示用户...
        dialog = ProgressDialog.show(this, null, "页面加载中，请稍等...",false,true);
//        mWebView.reload();
    }

    // 消耗掉back按钮事件，使back可在网页中返回上一个网页。
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                return true;
            } else if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                finish();
                DisplayAnimUtils.slideLeftInRightOut(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v,event);

        return false;//因为该事件需要传给webview
    }

}
