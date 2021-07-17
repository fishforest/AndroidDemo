package com.fish.fileprovider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fish.ipcserver.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReceiveActivity extends AppCompatActivity {

    private TextView tvContent;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileprovider);
        tvContent = findViewById(R.id.tv_content);
        findViewById(R.id.btn_write).setOnClickListener((v) -> {
            writeUri(uri);
        });
        handleIntent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getAction().equals(Intent.ACTION_VIEW)) {
                //从Intent里获取uri
                uri = intent.getData();
                String content = handleUri(uri);
                if (!TextUtils.isEmpty(content)) {
                    tvContent.setText("打开文件内容：" + content);
                }
            }
        }
    }

    private String handleUri(Uri uri) {
        if (uri == null)
            return null;

        String scheme = uri.getScheme();
        if (!TextUtils.isEmpty(scheme)) {
            if (scheme.equals("content")) {
                try {
                    //从uri构造流
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    try {
                        //有流之后即可读取内容
                        byte[] content = new byte[inputStream.available()];
                        inputStream.read(content);
                        return new String(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void writeUri(Uri uri) {
        if (uri == null) {
            return;
        }

        try {
            //从uri构造输出流
            OutputStream outputStream = getContentResolver().openOutputStream(uri);
            //待写入的内容
            String content = "write from server";
            //写入文件
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT);
        } catch (Exception e) {
            Log.d("test", e.getLocalizedMessage());
        }
    }
}
