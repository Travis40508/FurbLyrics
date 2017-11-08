package com.tressler.travistressler.lyricspal.Util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by travistressler on 11/7/17.
 */

public class CustomViewPager extends ViewPager {
    private boolean swipeable;



    public CustomViewPager(Context context) {

        super(context);

    }


    public CustomViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

        this.swipeable = true;

    }


    @Override

    public boolean onTouchEvent(MotionEvent event) {

        if (this.swipeable) {

            return super.onTouchEvent(event);

        }


        return false;

    }


    @Override

    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (this.swipeable) {

            return super.onInterceptTouchEvent(event);

        }


        return false;

    }


    public void setSwipeable(boolean swipeable) {

        this.swipeable = swipeable;

    }

}
