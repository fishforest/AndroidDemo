package com.example.androiddemo.window;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androiddemo.util.LogTag;

public class WindowViewGroup extends FrameLayout {
    public WindowViewGroup(@NonNull Context context) {
        super(context);
    }

    public WindowViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WindowViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(LogTag.TAG, "onTouchEvent action:" + MotionEvent.actionToString(event.getAction()));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(LogTag.TAG, "dispatchTouchEvent action:" + MotionEvent.actionToString(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }
}
