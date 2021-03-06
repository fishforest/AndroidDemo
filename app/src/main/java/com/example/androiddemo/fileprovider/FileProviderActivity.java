package com.example.androiddemo.fileprovider;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.androiddemo.MainActivity;
import com.example.androiddemo.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

public class FileProviderActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, FileProviderActivity.class);
        context.startActivity(intent);
    }

    private String imageName = "myImage.jpg";
    private String videoName = "myVideo.jpg";
    private String txtName = "myTxt.txt";

    private String external_filePath;
    private String external_app_filePath;
    private String inner_app_filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileprovider);

        findViewById(R.id.btn_open).setOnClickListener((v) -> {
            openByOther();
//            openByOtherForN();
        });

        findViewById(R.id.btn_share).setOnClickListener((v) -> {
            share2Other();
        });

        findViewById(R.id.btn_choose).setOnClickListener((v) -> {
            chooseApp2();
        });

        external_filePath = Environment.getExternalStorageDirectory() + File.separator + "fish/" + txtName;
        external_app_filePath = getExternalFilesDir("") + File.separator + "myfile/" + txtName;
        inner_app_filePath = getFilesDir() + File.separator + txtName;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //??????
                testPermission(FileProviderActivity.this);
            }
        }).start();
    }

    private void chooseApp(Intent intent) {
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);
        if (resInfo != null && resInfo.size() > 0) {
            List<Intent> intentList = new ArrayList<>();
            for (ResolveInfo resolveInfo : resInfo) {
                String packageName = resolveInfo.activityInfo.packageName;
                Log.d("name:", packageName);
                if (packageName.contains("com.android") || packageName.contains("huawei"))
                    continue;
                Intent targetIntent = new Intent();
                targetIntent.setComponent(new ComponentName(packageName, resolveInfo.activityInfo.name));
                targetIntent.setAction(Intent.ACTION_SEND);
                targetIntent.setType("text/*");
                targetIntent.setComponent(new ComponentName(packageName, resolveInfo.activityInfo.name));
                intentList.add(targetIntent);
            }

            List<Intent> intentList2 = new ArrayList<>();
            for (int i = intentList.size() - 1; i>=0;i--) {
                intentList2.add(intentList.get(i));
            }
            Intent chooserIntent = Intent.createChooser(intentList2.remove(1), "????????????APP");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList2.toArray(new Parcelable[]{}));

            startActivity(chooserIntent);
        }
    }

    private boolean checkDefaultSelect(Intent intent) {
        PackageManager packageManager = getPackageManager();
        ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        Log.d("default", info.activityInfo.packageName);
        packageManager.clearPackagePreferredActivities(getPackageName());
        return false;
    }

    private void chooseApp2() {
        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_SEND);

        intent.setType("text/plain");

        Intent intent2 = new Intent();

        intent2.setAction(Intent.ACTION_CHOOSER);

        intent2.putExtra(Intent.EXTRA_TITLE, "please selete a app");

        intent2.putExtra(Intent.EXTRA_INTENT, intent);

        startActivity(intent2);
    }

    private void writeFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                String content = "content:" + filePath;
                bufferedOutputStream.write(content.getBytes(), 0, content.getBytes().length);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void openByOther() {
        //?????????????????????
        String extension = external_filePath.substring(external_filePath.lastIndexOf(".") + 1);
        //?????????????????????mimeType
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        //??????Intent
        Intent intent = new Intent();
        //??????????????????
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //???????????????????????????
        intent.setAction(Intent.ACTION_VIEW);
        File file = new File(external_filePath);
        //?????????????????????????????????ContentProvider?????????????????????AndroidManifest.xml????????????
        Uri uri = MyFileProvider.getUriForFile(this, "com.fish.fileprovider", file);
        //???Intent ??????
        intent.setDataAndType(uri, mimeType);
        try {
            //??????????????????
            startActivity(intent);
        } catch (Exception e) {
            //????????????????????????????????????????????????mimeType??????????????????
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

//        checkDefaultSelect(intent);
    }

    private void openByOtherForN() {
        Intent intent = new Intent();
        //??????Action???????????????????????????
        intent.setAction(Intent.ACTION_VIEW);
        //?????????????????????Uri
        Uri uri = Uri.fromFile(new File(external_filePath));
        //??????Intent?????????Uri
        intent.setData(uri);
        //???????????????Intent
        startActivity(intent);
    }

    private void share2Other() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");
        chooseApp(intent);
    }

    //???????????????????????????????????????????????????
    private List<String> checkPermission(Context context, String[] checkList) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < checkList.length; i++) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, checkList[i])) {
                list.add(checkList[i]);
            }
        }
        return list;
    }

    //????????????
    private void requestPermission(Activity activity, String requestPermissionList[]) {
        ActivityCompat.requestPermissions(activity, requestPermissionList, 100);
    }

    //?????????????????????????????????????????????
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
                        writeFile();
                    } else {
                        Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    //????????????????????????
    private void testPermission(Activity activity) {
        String[] checkList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        List<String> needRequestList = checkPermission(activity, checkList);
        if (needRequestList.isEmpty()) {
//            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
            writeFile();
        } else {
            requestPermission(activity, needRequestList.toArray(new String[needRequestList.size()]));
        }
    }

    private void writeFile() {
        writeFile(external_filePath);
        writeFile(external_app_filePath);
        writeFile(inner_app_filePath);
    }
}
