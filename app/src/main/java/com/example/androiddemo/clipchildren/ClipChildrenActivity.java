package com.example.androiddemo.clipchildren;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiddemo.R;

public class ClipChildrenActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3;
    private Button curClickBtn = null;
    private int translationY = -200;
    private LinearLayout llParent;

    public static void start(Context context) {
        Intent intent = new Intent(context, ClipChildrenActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_children);

        llParent = findViewById(R.id.ll_parent);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener((v) -> {
            if (btn1.equals(curClickBtn)) {
                btn1.setTranslationY(0);
                curClickBtn = null;
            } else {
                btn2.setTranslationY(0);
                btn3.setTranslationY(0);
                btn1.setTranslationY(translationY);
                curClickBtn = btn1;
            }
        });


        btn2.setOnClickListener((v) -> {
            if (btn2.equals(curClickBtn)) {
                btn2.setTranslationY(0);
                curClickBtn = null;
            } else {
                btn1.setTranslationY(0);
                btn3.setTranslationY(0);
                btn2.setTranslationY(translationY);
                curClickBtn = btn2;
            }
        });

        btn3.setOnClickListener((v) -> {
            if (btn3.equals(curClickBtn)) {
                btn3.setTranslationY(0);
                curClickBtn = null;
            } else {
                btn1.setTranslationY(0);
                btn2.setTranslationY(0);
                btn3.setTranslationY(translationY);
                curClickBtn = btn3;
            }
        });

        //expand touch area
        llParent.post(() -> {
            Rect hitRect = new Rect();
            //获取父布局当前有效可点击区域
            llParent.getHitRect(hitRect);
            //扩大父布局点击区域
            hitRect.top += translationY;
            TouchDelegate touchDelegate = new SimpleTouchDelegate(hitRect, llParent);
            llParent.setClickable(true);
            ViewParent viewParent = llParent.getParent();
            if (viewParent instanceof ViewGroup) {
                ((ViewGroup) viewParent).setClickable(true);
                //在爷爷布局里拦截事件分发
                ((ViewGroup) viewParent).setTouchDelegate(touchDelegate);
            }

//            Rect rect = new Rect();
//            btn1.getHitRect(rect);
//            rect.top += translationY;
//            TouchDelegate touchDelegate1 = new TouchDelegate(rect, btn1);
//            llParent.setClickable(true);
//            llParent.setTouchDelegate(touchDelegate1);
        });
    }
}
