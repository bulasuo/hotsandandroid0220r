package com.hotsand.blink.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.net.Socket;

/**
 *@author   abu   2016/9/8   11:35
 */
public class XService extends Service {
    private Socket socket;
    private InputThread in;
    private OutputThread out;

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

    private void initSocket(){
        socket = new Socket();

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
