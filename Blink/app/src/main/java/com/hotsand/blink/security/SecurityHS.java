package com.hotsand.blink.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by abu on 2016/8/26 10:53.
 * https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html
 * Cipher编码方式:
 * AES/CBC/NoPadding (128)
 * AES/CBC/PKCS5Padding (128)
 * AES/ECB/NoPadding (128)
 * AES/ECB/PKCS5Padding (128)   ==
 * DES/CBC/NoPadding (56)
 * DES/CBC/PKCS5Padding (56)
 * DES/ECB/NoPadding (56)
 * DES/ECB/PKCS5Padding (56)
 * DESede/CBC/NoPadding (168)
 * DESede/CBC/PKCS5Padding (168)
 * DESede/ECB/NoPadding (168)
 * DESede/ECB/PKCS5Padding (168)
 * RSA/ECB/PKCS1Padding (1024, 2048)    ==
 * RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
 * RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
 */
public class SecurityHS {
    public static final String AES = "AES";
    public static final String RSA = "RSA";
    public static final String MD5 = "MD5";
    public static final String RSA_ECB_PKCS1PADDING = "RSA/ECB/PKCS1Padding";
    public static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";

    /**
     * class 生成RSA秘钥对
     */
    public static class RSAKeyParMaker{
        public RSAPrivateKey privateKey;
        public RSAPublicKey publicKey;
        private int KEY_SIZE = 1024;

        public RSAKeyParMaker() throws NoSuchAlgorithmException, NoSuchProviderException {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
            keyPairGen.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            publicKey = (RSAPublicKey) keyPair.getPublic();
        }

    }

    /**
     * RSA加密(服务器用不到,给android端写的方法)
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] RSAEncode(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * RSA解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] RSADecode(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * @param keyBytes
     * @return RSA公钥
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key formRSAPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * @param keyBytes
     * @return RSA私钥
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key formRSAPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException{
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * AES加密
     */
    public static byte[] AESEncode(byte[] data, byte[] key) throws Exception {
        SecretKeySpec securekey = new SecretKeySpec(key, AES);
        Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        data = cipher.doFinal(data);
        return data;
    }

    /**
     * AES解密
     */
    public static byte[] AESDecode(byte[] data, byte[] key) throws Exception{
        SecretKeySpec securekey = new SecretKeySpec(key, AES);
        Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        data = cipher.doFinal(data);
        return data;
    }

    public static String MD5Encode(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance(MD5);// 获取一个实例，并传入加密方式
            digest.reset();// 清空一下
            digest.update(content.getBytes());// 写入内容,可以指定编码方式content.getBytes("utf-8");
            StringBuilder builder = new StringBuilder();
            for (byte b : digest.digest()) {
                builder.append(Integer.toHexString((b >> 4) & 0xf));
                builder.append(Integer.toHexString(b & 0xf));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * @param args
     */
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        System.out.println("start:"+startTime);

        /**
         * AES加密解密
         */
		String message = "8888999999887775";
        String key = "5555555555555555";//UUID.randomUUID().toString();
        byte[] entryptedMsg;
		try {
			System.out.println("result--"+message.getBytes().length+"\nkey.getBytes().length:"+key.getBytes().length);
			entryptedMsg = AESEncode(message.getBytes(),key.getBytes());

	        System.out.println("111::"+entryptedMsg.length);

	        byte[] decryptedMsg;

			decryptedMsg = AESDecode(entryptedMsg,key.getBytes());
			 System.out.println("2222::"+decryptedMsg.length);

			 System.out.println("result--"+new String(decryptedMsg));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("---"+message.length());


        /*try {
            *//**RSA
             * 公钥加密,私钥解密
             *//*
            String message1 = UUID.randomUUID().toString();//"bulasuo:腰包满是银子,米加德遍地鲜花!!";
            RSAKeyParMaker mRSAKeyParMaker = new RSAKeyParMaker();
            byte[] encodeB = RSAEncode(message1.getBytes(), formRSAPublicKey(mRSAKeyParMaker.publicKey.getEncoded()));
//            byte[] encodeB = RSAEncode(message.getBytes(), mRSAKeyParMaker.publicKey);
            byte[] temp = new byte[encodeB.length];
            for(int i = 0;i<encodeB.length;i++)
                temp[i] = encodeB[i];
//            byte[] decodeB = RSADecode(temp, formRSAPrivateKey(mRSAKeyParMaker.privateKey.getEncoded()));
            byte[] decodeB = RSADecode(temp, mRSAKeyParMaker.privateKey);
            System.out.println("result::"+new String(decodeB)+"-encodeB.length:"+encodeB.length);

            *//**RSA
             * 私钥加密,公钥解密
             *//*
            *//*String message1 = "bulasuo:腰包满是银子,米加德遍地鲜花!!";//UUID.randomUUID().toString();
            RSAKeyParMaker mRSAKeyParMaker1 = new RSAKeyParMaker();
            byte[] encodeB1 = RSAEncode(message1.getBytes(), formRSAPrivateKey(mRSAKeyParMaker1.privateKey.getEncoded()));
            byte[] decodeB1 = RSADecode(encodeB1, formRSAPublicKey(mRSAKeyParMaker1.publicKey.getEncoded()));
            System.out.println("result::"+new String(decodeB1));*//*

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        final long endTime = System.currentTimeMillis();
        System.out.println("end  :"+endTime+"\nuse time:"+(endTime - startTime));

    }
}
