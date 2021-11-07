package com.fish.sslserver;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSLDouble {
    static int port = 12345;
    static String ip = "172.0.0.1";
    public static SSLServerSocket getServerSocket() {
        try {
            String storePassword = "qinfuqian";
            String trustPassword = "qinfuqian";
            String kStore = "kserver.keystore";
            String ckStore = "tserver.keystore";
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
            String kStore = "kclient.keystore";
            String ckStore = "tclient.keystore";
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
