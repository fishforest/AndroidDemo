package com.example.androiddemo.ams;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AMSTargetBroadcast extends BroadcastReceiver {

    public static String MY_ACTION = "my_action";

    public AMSTargetBroadcast() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
    }
}
