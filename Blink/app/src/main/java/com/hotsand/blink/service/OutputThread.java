package com.hotsand.blink.service;

import com.hotsand.blink.util.XUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 写消息线程
 *
 * @author abu   2016/9/8   16:22
 */
public class OutputThread extends Thread {
    private DataOutputStream dos;
    private OnSocketChangeListener onSocketChangeListener;
    private TranProtocol tranProtocol;
    public boolean tryDestroy = false;
    private Socket socket;
    public byte[] keyBytesAES;//AES口令bytes 用于加密数据

    public void tryDestroy() {
        tryDestroy = true;
        XUtil.closeDataOutputStream(dos);
        XUtil.closeSocket(socket);
        this.interrupt();
    }


    public OutputThread(Socket socket, OnSocketChangeListener onSocketChangeListener) {
        this.onSocketChangeListener = onSocketChangeListener;
        try {
            this.socket = socket;
            dos = new DataOutputStream(socket.getOutputStream());// 在构造器里面实例化对象输出流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果这里有消息发送 则只发这一条消息 不发送XService里的消息(一般这里只发三次握手的消息)
     *@author   abu   2016/9/10   9:41
     */
    public void sendMessage(TranProtocol tranProtocol) {
        synchronized (this) {
            synchronized (XService.tranProtocolList) {
                this.tranProtocol = tranProtocol;
                this.notifyAll();
            }
        }
    }

    /**
     * 发送XService  tranProtocolList里存放的消息
     */
    public void sendXServiceStackMessage() {
        synchronized (this) {
                this.notifyAll();
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                synchronized (this) {
                    while(tranProtocol == null && XService.tranProtocolList.size() <= 0){
                        this.wait();
                        if(isInterrupted() || socket.isClosed() || tryDestroy)
                            throw new Exception();
                    }
                    synchronized (XService.tranProtocolList) {

                        if (tranProtocol != null) {
                            if (keyBytesAES != null)
                                tranProtocol.keyBytesAES = this.keyBytesAES;
                            tranProtocol.sendData(dos);
                            dos.flush();
                            tranProtocol = null;
                        } else if (XService.tranProtocolList.size() > 0) {
                            TranProtocol tranProtocol;
                            for(int i=0;i<XService.tranProtocolList.size();i++){
                                tranProtocol = XService.tranProtocolList.get(i);
                                if (keyBytesAES != null)
                                    tranProtocol.keyBytesAES = this.keyBytesAES;
                                tranProtocol.sendData(dos);
                                dos.flush();
                                XService.tranProtocolList.remove(tranProtocol);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            XService.closeSocket();
            XUtil.closeDataOutputStream(dos);
            XUtil.closeSocket(socket);
            onSocketChangeListener.onSocketDisConnect();
        }
    }
}
