package com.ouwenjie.musicda.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.ouwenjie.musicda.utils.DisplayAnimUtils;

/**
 * Activity基类:右滑返回
 * Created by 文杰 on 2014/12/30.
 */
public class BaseActivity extends Activity implements View.OnTouchListener{

    private boolean enableSlide = true;//是否允许侧滑返回

    //手指向右滑动时的最小速度
    private static final int XSPEED_MIN = 300;
    //手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;
    //记录手指按下时的横坐标。
    private float xDown;
    //记录手指移动时的横坐标。
    private float xMove;
    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 检测该页面是否支持右滑返回
     * @return
     */
    public boolean isEnableSlide() {
        return enableSlide;
    }

    /**
     * 设置该界面的右滑返回，true:enable;false:disable
     * @param enableSlide
     */
    public void setEnableSlide(boolean enableSlide) {
        this.enableSlide = enableSlide;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
            if (enableSlide) {
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    xMove = event.getRawX();
                    //活动的距离
                    int distanceX = (int) (xMove - xDown);
                    //获取顺时速度
                    int xSpeed = getScrollVelocity();
                    //当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
                    if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
                        goback();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    recycleVelocityTracker();
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * 返回上一个Activity
     */
    protected void goback() {
        finish();
        DisplayAnimUtils.slideLeftInRightOut(this);
    }

    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }
    /**
     * 获取手指在content界面滑动的速度。
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }
}
