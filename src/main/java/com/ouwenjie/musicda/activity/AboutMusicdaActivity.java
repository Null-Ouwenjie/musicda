package com.ouwenjie.musicda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ouwenjie.musicda.R;
import com.ouwenjie.musicda.base.BaseActivity;
import com.ouwenjie.musicda.obj.Constant;
import com.ouwenjie.musicda.utils.ActionBarHelper;
import com.ouwenjie.musicda.utils.DisplayAnimUtils;

/**
 *
 * Created by 文杰 on 2015/1/14.
 */
public class AboutMusicdaActivity extends BaseActivity implements View.OnClickListener {

    public static final String LOG_TAG = "AboutMusicdaActivity";

    private ViewGroup mRootLayout;

    private ViewGroup mRateMusicda;
    private ViewGroup mFeatures;
    private ViewGroup mHelp;
    private ViewGroup mCheckforUpdates;
    private ViewGroup mDeveloper;
    private TextView  mAboutLink;

    private ActionBarHelper mActionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_musicda);

        initLayout();

    }

    private void initLayout() {

        mRootLayout = (ViewGroup) findViewById(R.id.about_musicda_layout);
        mRootLayout.setOnTouchListener(this);

        mActionBarHelper = new ActionBarHelper(this);
        mActionBarHelper.onPanelClosed();

        mRateMusicda = (ViewGroup) findViewById(R.id.RateMusicda);
        mFeatures = (ViewGroup) findViewById(R.id.Features);
        mHelp = (ViewGroup) findViewById(R.id.Help);
        mCheckforUpdates = (ViewGroup) findViewById(R.id.CheckforUpdates);
        mDeveloper = (ViewGroup) findViewById(R.id.Developer);
        mAboutLink = (TextView) findViewById(R.id.about_musicda_link);

        mRateMusicda.setOnClickListener(this);
        mFeatures.setOnClickListener(this);
        mHelp.setOnClickListener(this);
        mCheckforUpdates.setOnClickListener(this);
        mDeveloper.setOnClickListener(this);
        mAboutLink.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                startActivity(new Intent(AboutMusicdaActivity.this, MainActivity.class));
                DisplayAnimUtils.slideLeftInRightOut(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v,event);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RateMusicda:
                Toast.makeText(this,"新功能正在开发中...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Features:
                startActivity(new Intent(AboutMusicdaActivity.this, FeaturesActivity.class));
                DisplayAnimUtils.slideRightInLeftOut(this);
                break;
            case R.id.Help:
                startActivity(new Intent(AboutMusicdaActivity.this, HelpActivity.class));
                DisplayAnimUtils.slideRightInLeftOut(this);
                break;
            case R.id.CheckforUpdates:
                Toast.makeText(this,"新功能正在开发中...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Developer:
                startActivity(new Intent(AboutMusicdaActivity.this, AboutMeActivity.class));
                DisplayAnimUtils.slideRightInLeftOut(this);
                break;
            case R.id.about_musicda_link:
                String url = Constant.LINK_TO_ABOUT;
                Intent intent = new Intent(AboutMusicdaActivity.this, BrowserActivity.class);
                intent.putExtra("POST_MOBILE_URL", url);
                startActivity(intent);
                DisplayAnimUtils.slideRightInLeftOut(this);
                break;
            default:
                break;

        }
    }
}
