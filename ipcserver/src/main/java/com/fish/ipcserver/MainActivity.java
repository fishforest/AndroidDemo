package com.fish.ipcserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    StudentService.MyStudent myStudent;
    private boolean isConnected = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myStudent = (StudentService.MyStudent)IStudentInfo.Stub.asInterface(iBinder);
            isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isConnected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected && myStudent != null) {
                    myStudent.changeScore();
                } else {
                    Intent intent = new Intent(MainActivity.this, StudentService.class);
                    view.getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                }
            }
        });
    }
}