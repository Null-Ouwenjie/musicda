package com.ouwenjie.musicda;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.litepal.LitePalApplication;

/**
 *
 * Created by 文杰 on 2015/1/19.
 */
public class MusicdaApplication extends LitePalApplication {

    private RequestQueue mQueue;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public RequestQueue getRequestQueue(){

        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(this);
        }

        return mQueue;
    }
}
