package com.example.androiddemo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.androiddemo.R;
import com.example.androiddemo.fileprovider.FileProviderActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, FragmentActivity.class);
        context.startActivity(intent);
    }

    private List<Fragment> list = new ArrayList<>();

    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        frameLayout = findViewById(R.id.container);

        findViewById(R.id.btn_switch1).setOnClickListener((v)->{
//            replaceFragment(new MyFragment("fragment1"));
//            list.get(0)
            hide(list.get(0));
        });
        findViewById(R.id.btn_switch2).setOnClickListener((v)->{
//            addFragment(new MyFragment("fragment2"));
            show(list.get(0));
        });

        addFragment(new MyFragment("fragment1"));
//        addFragment(new MyFragment("fragment2"));
//        replaceFragment(new MyFragment("fragment1"));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   // 开启一个事务
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void hide(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   // 开启一个事务
        transaction.hide(fragment);
        transaction.commit();
    }

    private void show(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   // 开启一个事务
        transaction.show(fragment);
        transaction.commit();
    }


    private void addFragment(Fragment fragment) {
        list.add(fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   // 开启一个事务
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("hello", "world");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
