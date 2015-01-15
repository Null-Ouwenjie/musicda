package com.ouwenjie.musicda.utils;

import android.app.ActionBar;
import android.app.Activity;

/**
 * ActionBar的帮助类
 * Created by 文杰 on 2014/12/23.
 */
public class ActionBarHelper{

    private ActionBar mActionBar;
    private CharSequence mTitle;
    private Activity mActivity;

    public ActionBarHelper(Activity activity){
        mActivity = activity;
        mActionBar = activity.getActionBar();
    }

    public void init() {
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mTitle = mActivity.getTitle();
    }

    public void onPanelClosed() {
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setTitle(mActivity.getTitle());

    }

    public void onPanelOpened() {
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setHomeButtonEnabled(false);
        mActionBar.setTitle("Musicda");
    }
    public void setTitle(CharSequence title) {
        mTitle = title;
    }
}
