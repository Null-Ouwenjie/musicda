package com.ouwenjie.musicda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.base.BaseActivity;
import com.ouwenjie.musicda.utils.ActionBarHelper;

/**
 * 关于我
 * Created by 文杰 on 2014/12/18.
 */
public class AboutMeActivity extends BaseActivity{

    private ActionBarHelper actionBarHelper;

    private RelativeLayout aboutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        initLayout();

    }

    private void initLayout() {
        aboutLayout = (RelativeLayout) findViewById(R.id.about_me_layout);
        aboutLayout.setOnTouchListener(this);

        actionBarHelper = new ActionBarHelper(this);
        actionBarHelper.onPanelClosed();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v,event);
        return true;//因为该事件无需往下传
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                startActivity(new Intent(AboutMeActivity.this, AboutMusicdaActivity.class));
                finish();
                overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
