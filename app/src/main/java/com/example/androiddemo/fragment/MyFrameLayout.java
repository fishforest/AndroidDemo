package com.example.androiddemo.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.androiddemo.motionevent.MotionEventActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyFrameLayout extends FrameLayout {
    public MyFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener((v)-> {
            MotionEventActivity.startForResult(v.getContext());
        });
    }
}
