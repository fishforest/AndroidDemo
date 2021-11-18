package com.fish.sslserver;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSslServer();
    }

    private void startSslServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SSLServerSocket server = SSLDouble2.getServerSocket(MainActivity.this);
                    System.out.println("在 port:"+ SSLDouble2.port + "端口等待连接...ip:" + SSLDouble2.ip);

                    while (true) {
                        SSLSocket socket = (SSLSocket) server.accept();
                        System.out.println("连接来了...");

                        //将得到的socket交给CreateThread对象处理,主线程继续监听
                        new CreateThread2(socket, MainActivity.this);

                    }
                } catch (Exception e) {
                    System.out.println("main方法错误80:" + e);
                }
            }
        }).start();
    }
}

class SSLDouble2 {
    static int port = 12345;
    static String ip = "192.168.2.25";

    public static SSLServerSocket getServerSocket(Context context) {
        try {
            String storePassword = "qinfuqian";
            String trustPassword = "qinfuqian";
            String kStore = "com/fish/sslserver/server.jks";
            String ckStore = "com/fish/sslserver/serverTrust.jks";
            SSLContext sslContext = SSLContext.getInstance("TLSV1");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");

            KeyStore ks = KeyStore.getInstance("BKS");
            KeyStore tks = KeyStore.getInstance("BKS");

            InputStream inputStreamServer = context.getResources().openRawResource(R.raw.toolserver);
            InputStream inputStreamClient = context.getResources().openRawResource(R.raw.toolservertrust);

            ks.load(inputStreamServer, storePassword.toCharArray());
            tks.load(inputStreamClient, trustPassword.toCharArray());

            kmf.init(ks, storePassword.toCharArray());
            tmf.init(tks);

            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            return (SSLServerSocket) (sslContext.getServerSocketFactory().createServerSocket(port));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SSLSocket getSSLSocket() {
        try {
            String storePassword = "qinfuqian";
            String trustPassword = "qinfuqian";
            String kStore = "com/fish/sslserver/kclient.keystore";
            String ckStore = "com/fish/sslserver/tclient.keystore";
            SSLContext sslContext = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream(kStore), storePassword.toCharArray());
            tks.load(new FileInputStream(ckStore), trustPassword.toCharArray());

            kmf.init(ks, storePassword.toCharArray());
            tmf.init(tks);

            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            return (SSLSocket) (sslContext.getSocketFactory().createSocket(ip, port));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

class CreateThread2 extends Thread {
    static BufferedReader in;
    static PrintWriter out;
    static Socket s;
    Activity activity;

    /*
     *构造函数,获得socket连接,初始化in和out对象
     */

    public CreateThread2(Socket socket, Activity activity) {
        try {
            s = socket;
            in = new BufferedReader(new InputStreamReader(s.getInputStream(), "gb2312"));

            out = new PrintWriter(s.getOutputStream(), true);
            this.activity = activity;

            start();  //开新线程执行run方法

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /*
     *线程方法,处理socket传递过来的数据
     */

    public void run() {
        try {
            writeMsg(s);
            while (true) {
                String msg = in.readLine();
                if (msg == null) {
                    System.out.println("msg is null!!");
                    break;
                }
                System.out.println(msg);
                toastMsg(msg);
            }
        } catch (Exception e) {
            System.out.println(e);
            toastMsg(e.getLocalizedMessage());
        }
    }

    private void toastMsg(String msg) {
        activity.runOnUiThread(()->{
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
        });
    }

    private void writeMsg(Socket socket) {
        String msg = "I'm fine";
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes());
            Log.d("qfq", "write msg:" + msg);
        } catch (IOException e) {
            Log.e("qfq", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

}