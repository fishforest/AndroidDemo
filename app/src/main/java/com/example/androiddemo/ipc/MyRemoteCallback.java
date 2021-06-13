package com.example.androiddemo.ipc;

import android.app.Activity;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.fish.ipcserver.RemoteCallback;
import com.fish.ipcserver.Student;

public class MyRemoteCallback extends RemoteCallback.Stub {
    Activity activity;
    ExtraData extraData;

    public MyRemoteCallback(Activity activity, ExtraData extraData) {
        this.activity = activity;
        this.extraData = extraData;
    }
    @Override
    public void onCallback(Student student) throws RemoteException {
        Log.d("fish", "call back student:" + student);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "client receive change:" + student.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
