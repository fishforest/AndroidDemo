package com.example.androiddemo.clippadding;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;

public class ClipPaddingView extends View {

    private float lastY;
    private float lastX;

    public ClipPaddingView(Context context) {
        super(context);
    }

    public ClipPaddingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getRawY();
                lastX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                float curY = event.getRawY();
                float curX = event.getRawX();
                Log.d("fish", curY + " " + lastY);
                Log.d("fish", (curY - lastY) + "");
                if (Math.abs(curY - lastY) >= ViewConfiguration.getTouchSlop())
                    setTranslationY(curY - lastY);

                if (Math.abs(curX - lastX) >= ViewConfiguration.getTouchSlop())
                    setTranslationX(curX - lastX);
//                lastY = curY;
                break;
        }
        return super.onTouchEvent(event);
    }
}
