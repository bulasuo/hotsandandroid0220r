package com.hotsand.blink;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("192.168.1.107", 8001), 1000);
                    socket.setKeepAlive(true);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    byte[] buf = new byte[]{(byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02,
                            (byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02,
                            (byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02,
                            (byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02,(byte)0x01,(byte)0x02};

                    while(true){
                        dos.write(buf);
                        Thread.sleep(2000);
                    }
                } catch (IOException e) {
                    System.out.println("bulasuo11:"+e);
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    System.out.println("bulasuo22:"+e);
                    e.printStackTrace();
                }

            }
        }).start();*/
    }
}
