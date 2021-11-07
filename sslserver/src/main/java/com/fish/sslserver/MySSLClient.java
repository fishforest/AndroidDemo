package com.fish.sslserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class MySSLClient {
    public static void main(String args[]) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("客户端请求连接");
                SSLSocket sslSocket = SSLDouble1.getSSLSocket();
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

    static class SSLDouble1 {
        static int port = 12345;
        static String ip = "127.0.0.1";
        public static SSLServerSocket getServerSocket() {
            try {
                String storePassword = "qinfuqian";
                String trustPassword = "qinfuqian";
                String kStore = "com/fish/sslserver/client.jks";
                String ckStore = "com/fish/sslserver/clientTrust.jks";
                SSLContext sslContext = SSLContext.getInstance("TLSV1");

//                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                KeyManagerFactory kmf=KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore tks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream(kStore), storePassword.toCharArray());
                tks.load(new FileInputStream(ckStore), trustPassword.toCharArray());

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
                String kStore = "com/fish/sslserver/client.jks";
                String ckStore = "com/fish/sslserver/clientTrust.jks";
                SSLContext sslContext = SSLContext.getInstance("TLSV1");

                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore tks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream(kStore), storePassword.toCharArray());
                tks.load(new FileInputStream(ckStore), trustPassword.toCharArray());

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
