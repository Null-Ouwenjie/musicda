package com.ouwenjie.musicda.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.base.BaseActivity;
import com.ouwenjie.musicda.utils.ActionBarHelper;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;

/**
 *
 * Created by 文杰 on 2015/1/14.
 */
public class HelpActivity extends BaseActivity {

    public static final String LOG_TAG = "HelpActivity";

    private ViewGroup mRootLayout;

    private ActionBarHelper mActionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initLayout();
    }

    private void initLayout() {
        mRootLayout = (ViewGroup) findViewById(R.id.help_layout);
        mRootLayout.setOnTouchListener(this);

        mActionBarHelper = new ActionBarHelper(this);
        mActionBarHelper.onPanelClosed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
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
                finish();
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
}
