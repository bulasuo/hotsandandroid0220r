package com.hotsand.blink.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 *@author   abu   2016/9/8   11:35
 */
public class XService extends Service implements OnSocketChangeListener{
    private static Socket socket;
    public static InputThread in;
    public static OutputThread out;
    public static ArrayList<TranProtocol> tranProtocolList = new ArrayList<>();//存放message的待发送队列
    private int reConnectCount = 0;//尝试重新连接的计数器
    public static final int TIME_OUT = 1000;//连接的超时时间
    public static final int RECONNECT_DELAY = 1000;//尝试重连的延迟时间


    public XService() {
    }

    public void sendMessage(TranProtocol tranProtocol){
        synchronized (XService.tranProtocolList) {
            tranProtocolList.add(tranProtocol);
            if(initSocket())
                out.sendXServiceStackMessage();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service_onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initSocket();
        System.out.println("Service_onStartCommand::"+flags);
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("Service_onDestroy");
        closeSocket();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean initSocket() {
        synchronized (XService.tranProtocolList) {
            if (socket != null && out != null)
                return true;
            if (socket == null) {
                socket = new Socket();
                new InitSocketThread(this).start();
                return false;
            }
            return false;
        }
    }

    public static void closeSocket(){
        synchronized (XService.tranProtocolList) {
            try {
                if (in != null) {
                    in.tryDestroy = true;
                    in.tryDestroy();
                    in = null;
                }
                if (out != null) {
                    out.tryDestroy = true;
                    out.tryDestroy();
                    out = null;
                }
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSocketConnect() {
        synchronized (XService.tranProtocolList) {
            System.out.println("XService:onSocketConnect");
            reConnectCount = 0;
        }
    }

    /**
     * 连接异常的情况下,最多尝试5次重新连接,这里应该与连接成功后断开区分开
     *@author   abu   2016/9/10   11:01
     */
    @Override
    public void onSocketUnableConnect() {
        synchronized (XService.tranProtocolList) {
            System.out.println("XService:onSocketConnectDisable");
            if (reConnectCount == -1)
                return;
            reConnectCount++;
            if (reConnectCount < 5) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initSocket();
                    }
                }, RECONNECT_DELAY);
            } else {
                reConnectCount = -1;
                Toast.makeText(getApplicationContext(), "Unable to connect to the server !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSocketDisConnect() {
        synchronized (XService.tranProtocolList) {
            System.out.println("XService:onSocketDisConnect");
            initSocket();
        }
    }

    private class InitSocketThread extends Thread {
        private OnSocketChangeListener onSocketChangeListener;

        public InitSocketThread(OnSocketChangeListener onSocketChangeListener){
            this.onSocketChangeListener = onSocketChangeListener;
        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress("192.168.1.107", 8080), TIME_OUT);
                if(socket.isConnected()){
                    System.out.println("Connected......");
                    onSocketChangeListener.onSocketConnect();
                    socket.setKeepAlive(true);
                    out = new OutputThread(socket, onSocketChangeListener);
                    in = new InputThread(socket, out, onSocketChangeListener);
                    in.start();
                    out.start();
                }
            } catch (Exception e) {
                closeSocket();
                onSocketChangeListener.onSocketUnableConnect();
                e.printStackTrace();
            }
        }
    }
}
