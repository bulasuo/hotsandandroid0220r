package com.hotsand.blink.service;


import com.hotsand.blink.security.SecurityHS;
import com.hotsand.blink.util.XUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by abu on 2016/9/2 11:09.
 * 传输协议类,,可以根据此类封装传输data
 */
public class TranProtocol {
    public static final byte[] HEAD = "--".getBytes();
    public static final byte[] LINE = "\r\n".getBytes();
    public static final byte TP_JSONSTR = (byte)0x01;
    public static final byte TP_SSH = (byte)0xff;

    ss把协议写在这,开始写接口

    private byte protocolType;//协议类型
    private Key keyPublicRSA;//RSA公钥 用于加密AESkey
    public byte[] keyBytesAES;//AES口令bytes 用于加密数据

    private String jsonStr;//请求参数,json只有一级

    private byte fileCounts = (byte)0x00;

    private String[] filePatch;

    public TranProtocol(byte protocolType, Key keyPublicRSA){
        this.protocolType = protocolType;
        this.keyPublicRSA = keyPublicRSA;
    }

    /**
     * @param protocolType
     * @param jsonStr 封装了请求参数
     */
    public TranProtocol(byte protocolType, String jsonStr){
        this.protocolType = protocolType;
        this.jsonStr = jsonStr;
    }

    /**
     *
     * @param protocolType
     * @param jsonStr      封装了请求参数
     * @param filePatch     图片路径
     */
    public TranProtocol(byte protocolType, String jsonStr, String... filePatch){
        this.protocolType = protocolType;
        this.jsonStr = jsonStr;
        this.filePatch = filePatch;
    }


    public void sendData(DataOutputStream dos) throws Exception {
        switch(protocolType) {
            case TP_JSONSTR:
                sendJsonStrAndImg(dos);
                break;
            case TP_SSH:
                sendAESKey(dos);
                break;
            default:
                break;


        }
    }

    public final void sendAESKey(DataOutputStream dos) throws Exception {
        if(keyBytesAES == null || keyPublicRSA == null) {
            throw new RuntimeException("null point exception on keyBytesAES OR keyPublicRSA!!");
        }
        final byte[] AESKeyBytesRSAEncode = SecurityHS.RSAEncode(keyBytesAES, keyPublicRSA);
        final byte[] boundaryBytes = UUID.randomUUID().toString().getBytes();
        dos.write(HEAD);
        dos.write(boundaryBytes);
        dos.write(LINE);
        //协议类型 0xff服务端给客户端的RSA公钥
        dos.write(TP_SSH);
        dos.write(XUtil.int2ByteArray(AESKeyBytesRSAEncode.length));
        dos.write(AESKeyBytesRSAEncode);
        dos.write(HEAD);
        dos.write(boundaryBytes);
        dos.write(HEAD);
        dos.write(LINE);
    }

    /**
     * 协议类型0x00:JsonStr 和 img 文件
     *
     * @param dos
     * @throws IOException
     */
    public final void sendJsonStrAndImg(DataOutputStream dos) throws Exception {
        if(keyBytesAES == null){
            throw new RuntimeException("null point exception on keyBytesAES !!");
        }
        final byte[] boundaryBytes = UUID.randomUUID().toString().getBytes();
        final byte[] jsonStrEncodeBytes
                = SecurityHS.AESEncode(jsonStr.getBytes(), keyBytesAES);
        dos.write(HEAD);
        dos.write(boundaryBytes);
        dos.write(LINE);
        //协议类型 0x01
        dos.write(TP_JSONSTR);
        //jsonStr个数
        dos.write((byte)0x01);
        //文件个数为0, 下面省略文件长度
        if(filePatch != null)
            fileCounts = (byte)filePatch.length;
        if(fileCounts < 0)
            throw new RuntimeException("fileCounts should <= 127");
        dos.write(fileCounts);
        //jsonStr 长度,单位字节
        dos.write(XUtil.int2ByteArray(jsonStrEncodeBytes.length));
        ArrayList<File> fileList = new ArrayList<>();
        if(fileCounts > 0){
            File img;
            for(String path : filePatch){
                img = new File(path);
                dos.write(XUtil.int2ByteArray((int) img.length()));//这边file.length不知道对不对啊?
                fileList.add(img);
            }
        }
        dos.write(jsonStrEncodeBytes);
        if(fileList.size() > 0){
            FileInputStream fis = null;
            byte[] fileBuff = null;
            int length = 0;
            for(File file : fileList){
                fis = new FileInputStream(file);
                fileBuff = new byte[1024];
                while ((length = fis.read(fileBuff, 0, 1024)) > 0) {
                    dos.write(fileBuff, 0, length);
                    dos.flush();
                }
            }
        }
        dos.write(HEAD);
        dos.write(boundaryBytes);
        dos.write(HEAD);
        dos.write(LINE);
    }

}
