package com.hotsand.blink.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 写消息线程
 *
 * @author abu   2016/9/8   16:22
 */
public class OutputThread extends Thread {
    private DataOutputStream dos;
    private ArrayList<TranProtocol> tranProtocolList = new ArrayList<>();
    public boolean tryDestroy = false;
    private Socket socket;
    public byte[] keyBytesAES;//AES口令bytes 用于加密数据

    public void tryDestroy() {
        try {
            tryDestroy = true;
            if (dos != null)
                dos.close();
            if (socket != null)
                socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public OutputThread(Socket socket) {
        try {
            this.socket = socket;
            dos = new DataOutputStream(socket.getOutputStream());// 在构造器里面实例化对象输出流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(TranProtocol tranProtocol) {
        tranProtocolList.add(tranProtocol);
        this.notifyAll();
    }

    public synchronized void sendMessage(ArrayList<TranProtocol> tranProtocollist) {
        tranProtocolList.addAll(tranProtocollist);
        this.notifyAll();
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed() && !tryDestroy) {
                synchronized (this) {
                    if (tranProtocolList.size() > 0) {
                        for (TranProtocol tranProtocol : tranProtocolList) {
                            if (keyBytesAES != null)
                                tranProtocol.keyBytesAES = this.keyBytesAES;
                            tranProtocol.sendData(dos);
                            dos.flush();
                            tranProtocolList.remove(tranProtocol);
                        }
                    }
                    this.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                XService.out = null;
                if (dos != null)
                    dos.close();
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
