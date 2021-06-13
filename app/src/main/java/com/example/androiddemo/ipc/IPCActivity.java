package com.example.androiddemo.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiddemo.R;
import com.fish.ipcserver.IStudentInfo;
import com.fish.ipcserver.RemoteCallback;
import com.fish.ipcserver.Student;

public class IPCActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, IPCActivity.class);
        context.startActivity(intent);
    }

    boolean isConnected;

    IStudentInfo iStudentInfo;

    //声明回调
    RemoteCallback remoteCallback = new MyRemoteCallback(this, new com.example.androiddemo.ipc.ExtraData());

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(IPCActivity.this, "成功连接服务端", Toast.LENGTH_SHORT).show();
            isConnected = true;
            //转为对应接口
            iStudentInfo = IStudentInfo.Stub.asInterface(service);
            try {
                Toast.makeText(IPCActivity.this, "注册回调接口", Toast.LENGTH_SHORT).show();
                iStudentInfo.register(remoteCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isConnected && iStudentInfo != null) {
                        try {
                            Student student = iStudentInfo.getStudentInfo();
                            Toast.makeText(v.getContext(), "主动获学生信息：" + student.toString(), Toast.LENGTH_SHORT).show();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //参数1：运行远程服务的包名
                        //参数2：远程服务全限定类名
                        ComponentName componentName = new ComponentName("com.fish.ipcserver", "com.fish.ipcserver.StudentService");
                        Intent intent = new Intent();
                        intent.setComponent(componentName);
                        //绑定远程服务
                        v.getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                    }
                } catch (Exception e) {
                    Log.d("fish", e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

