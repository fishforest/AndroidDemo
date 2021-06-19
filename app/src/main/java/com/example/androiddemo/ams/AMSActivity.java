package com.example.androiddemo.ams;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.example.androiddemo.R;
import com.example.androiddemo.ipc.IPCActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AMSActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AMSActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ams);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AMSTargetBroadcast.MY_ACTION);
        registerReceiver(new AMSTargetBroadcast(), intentFilter);

        findViewById(R.id.btn_test_activity).setOnClickListener((v) -> {
            AMSTargetActivity.start(v.getContext());
        });

        findViewById(R.id.btn_test_service).setOnClickListener((v) -> {
            Intent intent = new Intent(AMSActivity.this, AMSTargetService.class);
//            startService(intent);
            bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    int a = 5;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            }, BIND_AUTO_CREATE);
        });

        findViewById(R.id.btn_test_broadcast).setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.setAction(AMSTargetBroadcast.MY_ACTION);
            //静态注册需要指定Component
//            intent.setComponent(new ComponentName(getPackageName(), AMSTargetBroadcast.class.getName()));
            sendBroadcast(intent);
        });

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull  Message msg) {
                super.handleMessage(msg);
            }
        };
        Uri uri = Uri.parse("content://" + AMSTargetProvider.AUTHORITY + "/ams");
        getContentResolver().registerContentObserver(uri, false, new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
            }
        });
        findViewById(R.id.btn_test_content).setOnClickListener((v) -> {
            ContentValues contentValues = new ContentValues();
            getContentResolver().insert(uri, contentValues);
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

