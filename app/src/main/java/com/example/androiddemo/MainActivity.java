package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androiddemo.motionevent.MotionEventActivity;
import com.example.androiddemo.window.WindowActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_motion).setOnClickListener((v)->{
            MotionEventActivity.start(MainActivity.this);
        });

        findViewById(R.id.btn_window).setOnClickListener((v)->{
            WindowActivity.start(MainActivity.this);
        });
    }
}