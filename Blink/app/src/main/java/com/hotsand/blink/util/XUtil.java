package com.hotsand.blink.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by abu on 2016/9/1 11:10.
 */
public class XUtil {

    /**
     * get请求
     * @param url
     * @param param
     * @return
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 删除文件list
     * @param fileList
     */
    public static void deleteDir(ArrayList<String> fileList){
        File file;
        for(String fileStr:fileList) {
            file = new File(fileStr);
            deleteDirAndFile(file);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     */
    public static boolean deleteDirAndFile(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (null != children)
                //递归删除目录中的子目录下
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDirAndFile(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static boolean isBytesEqual(byte[] buf0, int offset0, byte[] buf1, int offset1, int length){
        for(int i=0;i<length;i++)
            if(buf0[offset0 + i] != buf1[offset1 + i])
                return false;
        return true;
    }

    public static byte[] int2ByteArray(int i) {
        byte[] r = new byte[4];
        r[0] = (byte) ((i >> 24) & 0xFF);
        r[1] = (byte) ((i >> 16) & 0xFF);
        r[2] = (byte) ((i >> 8) & 0xFF);
        r[3] = (byte) (i & 0xFF);
        return r;
    }

    public static int byteArray2Int(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xff) << 24)
                + ((bytes[offset + 1] & 0xff) << 16)
                + ((bytes[offset + 2] & 0xff) << 8)
                + (bytes[offset + 3] & 0xff);
    }

    public final static byte[] hex = "0123456789abcdef".getBytes();

    public static String bytes2HexString(byte[] bb) {
        int i, len;
        len = bb.length;
        byte[] b = new byte[len];
        for (i = 0; i < len; i++)
            b[i] = bb[len - i - 1];

        byte[] buff = new byte[2 * b.length];

        for (i = 0; i < b.length; i++) {

            buff[2 * i] = hex[(b[b.length - i - 1] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[b[b.length - i - 1] & 0x0f];
        }
        return new String(buff);
    }

    public static byte[] hexString2Bytes(String s) {
        String ss = s.replace(" ", "");
        int string_len = ss.length();
        int len = string_len / 2;
        if (string_len % 2 == 1) {
            ss = "0" + ss;
            string_len++;
            len++;
        }
        byte[] a = new byte[len];
        try {
            for (int i = 0; i < len; i++) {
                a[i] = (byte) Integer.parseInt(ss.substring(2 * i, 2 * i + 2), 16);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return a;
    }
}
