package com.fish.sslclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startClient();
    }

    private void startClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("客户端请求连接");
                SSLSocket sslSocket = SSLDouble2.getSSLSocket(MainActivity.this);
                if (sslSocket == null)
                    return;
                try {
                    OutputStream outputStream = sslSocket.getOutputStream();
                    int index = 0;
                    while (true) {
                        String msg = "hello" + index++ + "\n";
                        outputStream.write(msg.getBytes());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static class SSLDouble2 {
        static int port = 12345;
        static String ip = "192.168.2.25";

        public static SSLSocket getSSLSocket(Context context) {
            try {
                String storePassword = "qinfuqian";
                String trustPassword = "qinfuqian";
                String kStore = "com/fish/sslserver/client.jks";
                String ckStore = "com/fish/sslserver/clientTrust.jks";
                SSLContext sslContext = SSLContext.getInstance("TLSV1");

                KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");

                KeyStore ks = KeyStore.getInstance("BKS");
                KeyStore tks = KeyStore.getInstance("BKS");

                InputStream inputStreamClient = context.getResources().openRawResource(R.raw.client);
                InputStream inputStreamServer = context.getResources().openRawResource(R.raw.clienttrust);
                ks.load(inputStreamClient, storePassword.toCharArray());
                tks.load(inputStreamServer, trustPassword.toCharArray());

                kmf.init(ks, storePassword.toCharArray());
                tmf.init(tks);

                sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                System.out.println("ip:" + ip + " port:" + port);
                return (SSLSocket) (sslContext.getSocketFactory().createSocket(ip, port));

            } catch (Exception e) {
                System.out.println("error:" + e.getLocalizedMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
}