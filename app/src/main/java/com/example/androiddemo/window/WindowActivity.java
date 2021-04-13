package com.example.androiddemo.window;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiddemo.Application.App;
import com.example.androiddemo.R;
import com.example.androiddemo.util.LogTag;

import java.lang.reflect.Method;

public class WindowActivity extends AppCompatActivity {

    private WindowViewGroup windowViewGroup;
    private WindowManager wm;
    private WindowManager.LayoutParams layoutParams;

    public static void start(Context context) {
        Intent intent = new Intent(context, WindowActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        initView();

        initWindow();

        findViewById(R.id.btn_show_window).setOnClickListener((v) -> {
            checkPermission(v);
        });


        findViewById(R.id.btn_hide_window).setOnClickListener((v) -> {
            hideView();
        });
    }

    private void initView() {
        windowViewGroup = new WindowViewGroup(this);
        windowViewGroup.setBackgroundColor(Color.BLUE);
    }

    private void initWindow() {
        //获取WindowManager实例，这里的App是继承自Application
        wm = (WindowManager) App.getApplication().getSystemService(Context.WINDOW_SERVICE);

        //设置LayoutParams属性
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = 400;
        layoutParams.width = 400;
        layoutParams.format = PixelFormat.RGBA_8888;

        //窗口标记属性
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //Window类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
    }

    private void showView() {
        wm.addView(windowViewGroup, layoutParams);
    }

    private void hideView() {
        wm.removeView(windowViewGroup);
    }


    public void checkPermission(View view) {
        if (checkPermission(this)) {
            showView();
        } else {
            Intent intent = getPermissionIntent(this);
            if (intent != null) {
                try {
                    startActivityForResult(intent, 100);
                } catch (Exception e) {
                    Log.d(LogTag.TAG, "error");
                }
            } else {
            }
        }
    }

    public static boolean checkPermission(@NonNull Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int op = 24;
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class clazz = AppOpsManager.class;
                Method method = clazz.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                return false;
            }
        } else {
            return true;
        }
    }

    public static Intent getPermissionIntent(@NonNull Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String brand = Build.BRAND;
            if (TextUtils.isEmpty(brand)) {
                return null;
            }
            return null;
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (checkPermission(this)) {
                showView();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(LogTag.TAG, "activity dispatchTouchEvent:" + MotionEvent.actionToString(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }
}
