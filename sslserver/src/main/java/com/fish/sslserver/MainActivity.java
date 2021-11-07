package com.fish.sslserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

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
                SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
                try {
                    SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketFactory.createServerSocket();
                    SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}