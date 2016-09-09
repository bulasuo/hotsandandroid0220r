package com.hotsand.blink.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *@author   abu   2016/9/8   11:35
 */
public class XService extends Service {
    private static Socket socket;
    public static InputThread in;
    public static OutputThread out;

    public XService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service_onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service_onStartCommand::"+flags);
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void initSocket() {
        if(socket == null) {
            socket = new Socket();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.connect(new InetSocketAddress("192.168.1.107", 8080), 1000);
                        if(socket.isConnected())
                            socket.setKeepAlive(true);
                        ss

                    } catch (Exception e) {
                        socket = null;
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        System.out.println("Service_onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
