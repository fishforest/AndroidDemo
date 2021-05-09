package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androiddemo.clipchildren.ClipChildrenActivity;
import com.example.androiddemo.clippadding.ClipPaddingActivity;
import com.example.androiddemo.edittext.EditTextActivity;
import com.example.androiddemo.motionevent.MotionEventActivity;
import com.example.androiddemo.storagepermission.StorageActivity;
import com.example.androiddemo.window.WindowActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_motion).setOnClickListener((v) -> {
            MotionEventActivity.start(MainActivity.this);
        });

        findViewById(R.id.btn_window).setOnClickListener((v) -> {
            WindowActivity.start(MainActivity.this);
        });

        findViewById(R.id.btn_clipchildren).setOnClickListener((v) -> {
            ClipChildrenActivity.start(MainActivity.this);
        });

        findViewById(R.id.btn_storage_permission).setOnClickListener((v) -> {
            StorageActivity.start(v.getContext());
        });

        findViewById(R.id.btn_clippadding).setOnClickListener((v) -> {
            ClipPaddingActivity.start(v.getContext());
        });

        findViewById(R.id.btn_edit_text).setOnClickListener((v) -> {
            EditTextActivity.start(v.getContext());
        });
    }
}