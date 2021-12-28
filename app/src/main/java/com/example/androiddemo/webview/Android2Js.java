package com.example.androiddemo.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class Android2Js extends Object{
    @JavascriptInterface
    public void say() {
        Log.d("fish", "nihao");
    }
}
