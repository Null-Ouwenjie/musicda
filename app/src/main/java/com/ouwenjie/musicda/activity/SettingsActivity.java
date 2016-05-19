package com.ouwenjie.musicda.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.base.BaseActivity;
import com.ouwenjie.musicda.obj.Constant;
import com.ouwenjie.musicda.utils.ActionBarHelper;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;
import com.ouwenjie.musicda.utils.SysUtils;
import com.umeng.fb.FeedbackAgent;


public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    public static final String LOG_TAG = "SettingsActivity";

    private ViewGroup mRootLayout;
    private ActionBarHelper mActionBarHelper;

    private CheckBox mCheckAudioEnable;
    private ViewGroup mPushNotification;
    private ViewGroup mFeedback;

    private SysUtils mUtils;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mUtils = SysUtils.getInstance(this);
        preferences = mUtils.getPreferences();
        editor = mUtils.getEditor();

        initLayout();

    }

    private void initLayout() {
        mRootLayout = (ViewGroup) findViewById(R.id.settings_layout);
        mRootLayout.setOnTouchListener(this);

        mActionBarHelper = new ActionBarHelper(this);
        mActionBarHelper.onPanelClosed();

        mCheckAudioEnable = (CheckBox) findViewById(R.id.checkbox_audio_enable);
        mCheckAudioEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(Constant.KEY_AUDIO_ENABLE,isChecked);
                editor.commit();
            }
        });

        mPushNotification = (ViewGroup) findViewById(R.id.push_notification);
        mPushNotification.setOnClickListener(this);

        mFeedback = (ViewGroup) findViewById(R.id.feedback);
        mFeedback.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                DisplayAnimUtils.slideLeftInRightOut(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                DisplayAnimUtils.slideLeftInRightOut(this);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v,event);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feedback:
                FeedbackAgent agent = new FeedbackAgent(this);
                agent.startFeedbackActivity();
                break;
        }
    }
}
