package com.fish.sslserver;

/*
 *SSL Socket的服务器端
 *@Author Bromon
 */

import java.net.*;
import javax.net.ssl.*;
import java.io.*;
import java.security.*;

public class MySSLServer
{
    static int port=8266;  //系统将要监听的端口号,82.6.6是偶以前女朋友的生日^_^
    static SSLServerSocket server;

    /*
     *构造函数
     */

    public MySSLServer()
    {

    }


    /*
     *@param port 监听的端口号
     *@return 返回一个SSLServerSocket对象
     */

    private static SSLServerSocket getServerSocket2(int thePort)
    {
        SSLServerSocket s=null;
        try
        {
            String key="SSLKey";  //要使用的证书名

            char keyStorePass[]="qinfuqian".toCharArray();  //证书密码

            char keyPassword[]="qinfuqian".toCharArray();  //证书别称所使用的主要密码

            KeyStore ks=KeyStore.getInstance("JKS");  //创建JKS密钥库

            ks.load(new FileInputStream(key),keyStorePass);

            //创建管理JKS密钥库的X.509密钥管理器
            KeyManagerFactory kmf=KeyManagerFactory.getInstance("SunX509");
//            KeyManagerFactory kmf=KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

            kmf.init(ks,keyPassword);

            SSLContext sslContext=SSLContext.getInstance("TLSV1");

            sslContext.init(kmf.getKeyManagers(),null,null);

            //根据上面配置的SSL上下文来产生SSLServerSocketFactory,与通常的产生方法不同
            SSLServerSocketFactory factory=sslContext.getServerSocketFactory();

            s=(SSLServerSocket)factory.createServerSocket(thePort);

        }catch(Exception e)
        {
            System.out.println(e);
        }
        return(s);
    }


    public static void main(String args[])
    {
        try
        {
            server= SSLDouble1.getServerSocket();
            System.out.println("在”+port+”端口等待连接...");

            while(true)
            {
                SSLSocket socket=(SSLSocket)server.accept();
                System.out.println("连接来了...");

                //将得到的socket交给CreateThread对象处理,主线程继续监听
                new CreateThread(socket);

            }
        }catch(Exception e)
        {
            System.out.println("main方法错误80:"+e);
        }
    }
}

/*
 *内部类,获得主线程的socket连接,生成子线程来处理
 */

class SSLDouble1 {
    static int port = 12345;
    static String ip = "172.0.0.1";
    public static SSLServerSocket getServerSocket() {
        try {
            String storePassword = "qinfuqian";
            String trustPassword = "qinfuqian";
            String kStore = "com/fish/sslserver/server.jks";
            String ckStore = "com/fish/sslserver/serverTrust.jks";
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

class CreateThread extends Thread
{
    static BufferedReader in;
    static PrintWriter out;
    static Socket s;

    /*
     *构造函数,获得socket连接,初始化in和out对象
     */

    public CreateThread(Socket socket)
    {
        try
        {
            s=socket;
            in=new BufferedReader(new InputStreamReader(s.getInputStream(),"gb2312"));

            out=new PrintWriter(s.getOutputStream(),true);

            start();  //开新线程执行run方法

        }catch(Exception e)
        {
            System.out.println(e);
        }

    }

    /*
     *线程方法,处理socket传递过来的数据
     */

    public void run()
    {
        try
        {
            while (true) {
                String msg=in.readLine();
                if (msg == null) {
                    System.out.println("msg is null!!");
                    break;
                }
                System.out.println(msg);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

}