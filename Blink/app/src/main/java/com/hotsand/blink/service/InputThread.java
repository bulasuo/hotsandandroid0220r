package com.hotsand.blink.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hotsand.blink.security.SecurityHS;
import com.hotsand.blink.util.XUtil;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.UUID;


/**
 * 读消息线程和处理方法
 *@author   abu   2016/9/8   16:21
 */
public class InputThread extends Thread {
    private Socket socket;
    private OutputThread out;
    private DataInputStream dis;
    private OnSocketChangeListener onSocketChangeListener;

    private byte[] keyBytesRSA;
    private byte[] keyBytesAES;//AES口令bytes 用于加密数据

    public boolean tryDestroy = false;
    private static final int BUFFER_MAX_LENGTH = 1024;

    private byte[] buffer = new byte[BUFFER_MAX_LENGTH];
    private int bufferIndex = 0;//buffer实际数据长度,也是实际数据最后一位索引加1


    private int readLength;

    public void tryDestroy(){
        tryDestroy = true;
        XUtil.closeDataInputStream(dis);
        XUtil.closeSocket(socket);
        this.interrupt();
    }

    public InputThread(Socket socket, OutputThread out, OnSocketChangeListener onSocketChangeListener) {
        this.onSocketChangeListener = onSocketChangeListener;
        this.socket = socket;
        this.out = out;
        try {
            dis = new DataInputStream(socket.getInputStream());// 实例化对象输入流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            while (!isInterrupted() && !socket.isClosed() && !tryDestroy) {
                // TODO: 2016/9/5 心跳包
                //增加一个5分钟没有连接就断开 防止客户端意外断开
                readMessage();
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            XService.closeSocket();
            out = null;
            XUtil.closeDataInputStream(dis);
            XUtil.closeSocket(socket);
            onSocketChangeListener.onSocketDisConnect();
        }

    }

    /**
     * 解析协议类型
     *
     * @param type 第41个字节
     */
    private void goProtocolType(byte type) throws Exception {
        switch (type) {
            case TranProtocol.TP_JSONSTR:
                if (keyBytesRSA == null || keyBytesAES == null) {
                    reConnect();
                    return;
                }
                readData(bufferIndex + 2);
                final int fileCount = buffer[42] & 0xff;//这边fileCount应该为0x00
                if(fileCount != 0)
                    reConnect();
                readData(bufferIndex + 4 + fileCount * 4);
                JSONObject json = readJson(XUtil.byteArray2Int(buffer, 43));
                //暂时服务器传图片给客户端不会与jspnStr混传,图片的传输另外写短连接协议
                /*ArrayList<String> fileList = new ArrayList<>();
                for (int i = 0; i < fileCount; i++)
                    fileList.add(readImg(XUtil.byteArray2Int(buffer, 47 + i * 4)));*/
                if (isPackLegal()) {
                    // TODO: 2016/9/5  如果合法则广播json
                    System.out.println("response_jsonStr:"+json.toJSONString());
                } else {
                    reConnect();
                }
                break;
            case TranProtocol.TP_SSH:
                readData(bufferIndex + 4);
                this.keyBytesRSA = readRSAKey(XUtil.byteArray2Int(buffer, bufferIndex - 4));
                if(isPackLegal()){
                    this.keyBytesAES = UUID.randomUUID().toString().getBytes();
                    out.keyBytesAES = this.keyBytesAES;
                    // TODO: 2016/9/9 out 发送RSA编码后的AESkey
                    out.sendMessage(new TranProtocol(SecurityHS.formRSAPublicKey(this.keyBytesRSA)));
//                    out.sendXServiceStackMessage();//三次握手后发送堆栈的信息
                } else {
                    this.keyBytesAES = null;
                    reConnect();
                }
                break;
        }
    }

    /**
     * 判断数据包是否合法
     *
     * @return
     */
    private boolean isPackLegal() throws Exception {
        readData(bufferIndex + 42);
        if (buffer[bufferIndex - 1] == TranProtocol.LINE[1]
                && buffer[bufferIndex - 2] == TranProtocol.LINE[0]
                && buffer[bufferIndex - 3] == TranProtocol.HEAD[0]
                && buffer[bufferIndex - 4] == TranProtocol.HEAD[0]
                && XUtil.isBytesEqual(buffer, bufferIndex - 40, buffer, 2, 36)
                && buffer[bufferIndex - 41] == TranProtocol.HEAD[0]
                && buffer[bufferIndex - 42] == TranProtocol.HEAD[0])
            return true;
        return false;
    }

    /**
     * use when bufferIndex指向RSAKey第一个字节
     *@author   abu   2016/9/5   14:53
     */
    private byte[] readRSAKey(int length) throws Exception {
        byte[] temp = new byte[length];
        readDataIntoBuffer(temp, length);
        return temp;
    }

    /**
     * use when bufferIndex指向json断第一个字节
     *@author   abu   2016/9/5   14:49
     */
    private JSONObject readJson(int length) throws Exception {
        byte[] jsonBytes = new byte[length];
        readDataIntoBuffer(jsonBytes, length);
        return JSON.parseObject(new String(SecurityHS.AESDecode(jsonBytes, keyBytesAES)));
    }

    /**
     * 读取数据到buffer 到buffer长度为length
     *
     * @param length 应该小于等于BUFFER_MAX_LENGTH
     */
    private void readData(int length) throws Exception {
        if (bufferIndex >= length || length > BUFFER_MAX_LENGTH)
            throw new Exception();
        while (!tryDestroy) {
            while ((readLength = dis.read(buffer, bufferIndex, length - bufferIndex)) > 0) {
                System.out.println("readLength:" + readLength);
                bufferIndex += readLength;
                if (bufferIndex >= length)
                    return;
            }
            if(readLength == -1)
                throw new Exception();
        }
    }

    /**
     * 把数据读到buffer
     *
     * @param buf
     * @param length 应该小于等于buf的长度
     */
    private void readDataIntoBuffer(byte[] buf, int length) throws Exception {
        int index = 0;
        int readl = 0;
        while (!tryDestroy) {
            while ((readl = dis.read(buf, index, length - index)) > 0) {
                System.out.println("readLength:" + readl);
                index += readl;
                if (index >= length)
                    return;
            }
            if(readl == -1)
                throw new Exception();
        }
    }

    /**
     * 读消息以及处理消息，抛出异常
     *
     * @throws ClassNotFoundException
     */
    private void readMessage() throws Exception {
        bufferIndex = 0;//准备读一个完整的包,先指针初始化
        readData(41);//至少读到41个字节
        if (buffer[0] == TranProtocol.HEAD[0] && buffer[1] == TranProtocol.HEAD[0]
                && buffer[38] == TranProtocol.LINE[0] && buffer[39] == TranProtocol.LINE[1]) {
            goProtocolType(buffer[40]);//当有包头后进入协议类型解析
        } else {
            //不是包头,数据错误 断开等待重连
            reConnect();

        }

    }

    /**
     * 数据协议错误,没有经过三次握手就传数据, 则重新连接
     *@author   abu   2016/9/5   15:15
     */
    private void reConnect() throws Exception {
        // TODO: 2016/9/9 socket重连

        throw new Exception();
    }
}
