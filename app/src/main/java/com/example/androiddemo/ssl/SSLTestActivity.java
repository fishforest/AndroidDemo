package com.example.androiddemo.ssl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.androiddemo.R;
import com.example.androiddemo.fragment.FragmentActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import androidx.appcompat.app.AppCompatActivity;

public class SSLTestActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SSLTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssl);

        findViewById(R.id.btn_http).setOnClickListener((v)->{
            testHttp();
        });

        findViewById(R.id.btn_https).setOnClickListener((v)->{
            testHttps();
        });

    }

    private void testHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://sandu.gov.cn/");
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.connect();
                        httpURLConnection.getResponseCode();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void testHttps() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TrustManager trustManager[] = {new MyTrustManager()};
                try {
                    URL url = new URL("https://www.baidu.com");
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    try {
                        sslContext.init(null, trustManager, new java.security.SecureRandom());
                        SSLSocketFactory ssf = sslContext.getSocketFactory();

                        //创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
                        HttpsURLConnection httpsConn = (HttpsURLConnection)url.openConnection();
                        httpsConn.setSSLSocketFactory(ssf);
                        httpsConn.connect();
                        httpsConn.getResponseCode();
                        InputStreamReader in = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");
                        BufferedReader bfreader = new BufferedReader(in);
                        String result = "";
                        String line = "";
                        while ((line = bfreader.readLine()) != null) {
                            result += line;
                        }
                        Log.d("qfq", result);
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }  catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
