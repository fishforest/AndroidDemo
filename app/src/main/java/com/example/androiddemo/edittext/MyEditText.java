package com.example.androiddemo.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.logging.Handler;

import androidx.appcompat.widget.AppCompatEditText;

import static android.view.inputmethod.InputMethod.SHOW_FORCED;

public class MyEditText extends AppCompatEditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View focusSearch(int direction) {
        return super.focusSearch(direction);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            InputMethodManager methodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.showSoftInput(this, SHOW_FORCED);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
