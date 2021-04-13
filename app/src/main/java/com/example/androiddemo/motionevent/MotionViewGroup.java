package com.example.androiddemo.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androiddemo.util.LogTag;

public class MotionViewGroup extends FrameLayout {
    public MotionViewGroup(@NonNull Context context) {
        super(context);
    }

    public MotionViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(LogTag.TAG, "MotionViewGroup dispatchTouchEvent:" + MotionEvent.actionToString(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(LogTag.TAG, "MotionViewGroup onTouchEvent:" + MotionEvent.actionToString(event.getAction()));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(LogTag.TAG, "MotionViewGroup onInterceptTouchEvent:" + MotionEvent.actionToString(ev.getAction()));
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            return true;
//        }
        return false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(LogTag.TAG, "MotionViewGroup onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    public void onViewRemoved(View child) {
        Log.d(LogTag.TAG, "MotionViewGroup onViewRemoved");
        super.onViewRemoved(child);
    }
}
