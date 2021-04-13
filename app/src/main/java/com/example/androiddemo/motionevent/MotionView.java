package com.example.androiddemo.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androiddemo.util.LogTag;

public class MotionView extends View {
    public MotionView(Context context) {
        super(context);
    }

    public MotionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        Log.d(LogTag.TAG, "MotionView dispatchTouchEvent:" + MotionEvent.actionToString(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(LogTag.TAG, "MotionView onTouchEvent:" + MotionEvent.actionToString(event.getAction()) + event.getPointerCount());
        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    @Override
    protected void onDetachedFromWindow() {
        Log.d(LogTag.TAG, "MotionView onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

}
