package com.example.androiddemo.clippadding;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClipPaddingViewGroup extends FrameLayout {
    public ClipPaddingViewGroup(@NonNull Context context) {
        super(context);
    }

    public ClipPaddingViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}
