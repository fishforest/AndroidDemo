package com.example.androiddemo.Application;

import android.app.Application;

public class App extends Application {

    private static volatile App ins;

    @Override
    public void onCreate() {
        super.onCreate();
        ins = this;
    }

    public static Application getApplication() {
        return ins;
    }
}
