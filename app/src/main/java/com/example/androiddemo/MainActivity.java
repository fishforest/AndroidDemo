package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;

import com.example.androiddemo.ams.AMSActivity;
import com.example.androiddemo.clipchildren.ClipChildrenActivity;
import com.example.androiddemo.clippadding.ClipPaddingActivity;
import com.example.androiddemo.edittext.EditTextActivity;
import com.example.androiddemo.fileprovider.FileProviderActivity;
import com.example.androiddemo.fragment.FragmentActivity;
import com.example.androiddemo.fragment.StaticFragmentActivity;
import com.example.androiddemo.ipc.IPCActivity;
import com.example.androiddemo.motionevent.MotionEventActivity;
import com.example.androiddemo.ssl.SSLTestActivity;
import com.example.androiddemo.storagepermission.StorageActivity;
import com.example.androiddemo.webview.WebviewActivity;
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

        findViewById(R.id.btn_test_ipc).setOnClickListener((v) -> {
            IPCActivity.start(v.getContext());
        });

        findViewById(R.id.btn_test_ams).setOnClickListener((v) -> {
            AMSActivity.start(v.getContext());
        });

        findViewById(R.id.btn_test_fileProvider).setOnClickListener((v) -> {
            FileProviderActivity.start(v.getContext());
        });

        findViewById(R.id.btn_test_fragment_static).setOnClickListener((v) -> {
            StaticFragmentActivity.start(v.getContext());
        });
        findViewById(R.id.btn_test_fragment_dynamic).setOnClickListener((v) -> {
            FragmentActivity.start(v.getContext());
        });
        findViewById(R.id.btn_test_ssl).setOnClickListener((v) -> {
            SSLTestActivity.start(v.getContext());
        });

        findViewById(R.id.btn_test_webview).setOnClickListener((v) -> {
            WebviewActivity.start(v.getContext());
        });
    }
}