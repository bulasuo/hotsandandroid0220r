package com.hotsand.blink.service;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 写消息线程
 *@author   abu   2016/9/8   16:22
 */
public class OutputThread extends Thread {
	private DataOutputStream dos;
	private TranProtocol tranProtocol;
	private boolean tryDestroy = false;
	private Socket socket;
	public byte[] keyBytesAES;//AES口令bytes 用于加密数据


	public OutputThread(Socket socket) {
		try {
			this.socket = socket;
			dos = new DataOutputStream(socket.getOutputStream());// 在构造器里面实例化对象输出流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void sendMessage(TranProtocol tranProtocol) {
		this.tranProtocol = tranProtocol;
		this.notifyAll();
	}

	@Override
	public void run() {
		try {
			while (!socket.isClosed() && !tryDestroy) {
				synchronized (this) {
					if (tranProtocol != null) {
						if(keyBytesAES != null)
							tranProtocol.keyBytesAES = this.keyBytesAES;
						tranProtocol.sendData(dos);
						dos.flush();
					}
					this.wait();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dos != null)
					dos.close();
				if (socket != null)
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
