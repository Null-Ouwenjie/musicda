package com.ouwenjie.musicda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;

public class LoadingActivity extends Activity {

    private static final int MSG_NONETWORK = 0; // 没有网络
    private static final int MSG_PASS = 1; // 进入主页

    private ImageView loadingImg;

    private Handler mHandler = new LoadingHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading);

        initLayout();
        loadAnimation();
    }

    private void initLayout() {

        loadingImg = (ImageView) findViewById(R.id.loading_image);
        loadingImg.setImageResource(R.drawable.loading_pic);
    }

    @Override
    protected void onResume(){
        super.onResume();

        mHandler.sendEmptyMessageDelayed(MSG_PASS,3000);
    }

    private void loadAnimation(){

        Animation loadingAnim = AnimationUtils.loadAnimation(this, R.anim.scale_1_to_2);
        loadingAnim.setDuration(3000);
        loadingAnim.setFillAfter(true);
        loadingImg.startAnimation(loadingAnim);
    }
    private void passLoading(){
        Intent intent = new Intent(LoadingActivity.this,MainActivity.class);

        startActivity(intent);
        finish();
        DisplayAnimUtils.alphaExit(this);
    }

    private class LoadingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what){
                case MSG_PASS:
                    passLoading();
                    break;
                default:
                    break;
            }
        }
    }
}
