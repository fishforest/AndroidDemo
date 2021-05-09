package com.example.androiddemo.edittext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.EditText;

import com.example.androiddemo.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditTextActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, EditTextActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        EditText editText = findViewById(R.id.edit);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        String hint = "myname";
        editText.setText(hint);
        editText.setSelectAllOnFocus(true);

        editText.setSelection(0, hint.length());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}
