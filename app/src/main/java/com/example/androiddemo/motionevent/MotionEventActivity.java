package com.example.androiddemo.motionevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiddemo.R;

public class MotionEventActivity extends AppCompatActivity {

    private ViewGroup viewGroup;

    public static void start(Context context) {
        Intent intent = new Intent(context, MotionEventActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);

        viewGroup = findViewById(R.id.motion_group);
        viewGroup.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewGroup.removeAllViews();
//                getWindowManager().removeView(getWindow().getDecorView());
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
