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
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.base.BaseActivity;
import com.ouwenjie.musicda.utils.ActionBarHelper;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;

public class SharedActivity extends BaseActivity {

    public static final String LOG_TAG = "SharedActivity.class";

    private ViewGroup mRootLayout;

    private ActionBarHelper actionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        mRootLayout = (ViewGroup) findViewById(R.id.shared_layout);
        mRootLayout.setOnTouchListener(this);

        actionBarHelper = new ActionBarHelper(this);
        actionBarHelper.onPanelClosed();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ptrlistview_context,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        Log.e("SharedActivity", "=== " + position + " ===");

        return super.onContextItemSelected(item);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                startActivity(new Intent(SharedActivity.this, MainActivity.class));
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
                startActivity(new Intent(SharedActivity.this, MainActivity.class));
                DisplayAnimUtils.slideLeftInRightOut(this);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v,event);
        return true;//因为该事件无需往下传
    }

}
