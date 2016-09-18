package com.hotsand.blink.app;

import android.app.Application;
import android.content.Intent;

import com.hotsand.blink.service.XService;

/**
 * Created by abu on 2016/9/10 11:34.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, XService.class));
    }

}
