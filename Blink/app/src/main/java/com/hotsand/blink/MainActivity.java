package com.hotsand.blink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.hotsand.blink.constant.ConstantInterface;
import com.hotsand.blink.service.ActionInterface;
import com.hotsand.blink.service.TranProtocol;
import com.hotsand.blink.service.XService;

public class MainActivity extends AppCompatActivity implements ActionInterface, ConstantInterface {

    private LinearLayout ll_container;
    private LayoutInflater inflater;

    private void addActionTestButton(String ActionStrName, View.OnClickListener listener){
        final Button btn = (Button) inflater.inflate(R.layout.inflater_button, null);
        btn.setText(ActionStrName);
        btn.setOnClickListener(listener);
        ll_container.addView(btn);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_container = (LinearLayout)findViewById(R.id.ll_container);
        inflater = getLayoutInflater();
        initActionStrTestButton();
        //放在最后防止mReceive里的处理用到的组件还未初始化.
        this.registerReceiver(mReceiver, getFilter());
        this.registerReceiver(mReceiver, getFilter());
    }

    private void initActionStrTestButton(){
        //获取短信验证码
        addActionTestButton("获取短信验证码API", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject json = new JSONObject();
                json.put(Key.ACTION, ActionInt.USER_GET_SMS);
                json.put(Key.TAG, "获取短信验证码");
                json.put(Key.PHONE, "15062239769");
                XService.sendMessage(new TranProtocol(json.toJSONString()));
            }
        });
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getAction()){
                case ActionStr.USER_GET_SMS:

                    break;
                case ActionStr.BLINK__BLINK:

                    break;
                case ActionStr.BLINK__MY_BLINKERS:

                    break;
                case ActionStr.BLINK__MY_BLINKS:

                    break;
                case ActionStr.BLINK__UNBLINK:

                    break;
                case ActionStr.BOOK__DELETE:

                    break;
                case ActionStr.BOOK__INSERT:

                    break;
                case ActionStr.BOOK__QUERY:

                    break;
                case ActionStr.BOOK__UPDATE:

                    break;
                case ActionStr.DYNAMIC__DELETE:

                    break;
                case ActionStr.DYNAMIC__INSERT:

                    break;
                case ActionStr.DYNAMIC__QUERY:

                    break;
                case ActionStr.DYNAMIC__UPFATE:

                    break;
                case ActionStr.MOVIE__DELETE:

                    break;
                case ActionStr.MOVIE__INSERT:

                    break;
                case ActionStr.MOVIE__QUERY:

                    break;
                case ActionStr.MOVIE__UPDATE:

                    break;
                case ActionStr.MUSIC__DELETE:

                    break;
                case ActionStr.MUSIC__INSERT:

                    break;
                case ActionStr.MUSIC__QUERY:

                    break;
                case ActionStr.MUSIC__UPDATE:

                    break;
                case ActionStr.MV__DELETE:

                    break;
                case ActionStr.MV__INSERT:

                    break;
                case ActionStr.MV__QUERY:

                    break;
                case ActionStr.MV__UPDATE:

                    break;
                case ActionStr.TOPIC__DELETE:

                    break;
                case ActionStr.TOPIC__INSERT:

                    break;
                case ActionStr.TOPIC__QUERY:

                    break;
                case ActionStr.TOPIC__UPDATE:

                    break;
                case ActionStr.TOPIC_REPLY2__DELETE:

                    break;
                case ActionStr.TOPIC_REPLY2__INSERT:

                    break;
                case ActionStr.TOPIC_REPLY2__QUERY:

                    break;
                case ActionStr.TOPIC_REPLY2__UPDATE:

                    break;
                case ActionStr.TOPIC_REPLY__DELETE:

                    break;
                case ActionStr.TOPIC_REPLY__INSERT:

                    break;
                case ActionStr.TOPIC_REPLY__QUERY:

                    break;
                case ActionStr.TOPIC_REPLY__UPDATE:

                    break;
                case ActionStr.USER__AGREEMENT:

                    break;
                case ActionStr.USER__LOGIN:

                    break;
                case ActionStr.USER__QUERY:

                    break;
                case ActionStr.USER__REGIST:

                    break;
                case ActionStr.USER__UPDATE:

                    break;
                case ActionStr.USER_DETAIL__INSERT:

                    break;
                case ActionStr.USER_DETAIL__QUERY:

                    break;
                case ActionStr.USER_DETAIL__UPDATE:

                    break;
                default:
                    break;
            }
        }
    };

    private IntentFilter getFilter(){
        final IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ActionStr.USER_GET_SMS);
        mFilter.addAction(ActionStr.BLINK__BLINK);
        mFilter.addAction(ActionStr.BLINK__MY_BLINKERS);
        mFilter.addAction(ActionStr.BLINK__MY_BLINKS);
        mFilter.addAction(ActionStr.BLINK__UNBLINK);
        mFilter.addAction(ActionStr.BOOK__DELETE);
        mFilter.addAction(ActionStr.BOOK__INSERT);
        mFilter.addAction(ActionStr.BOOK__QUERY);
        mFilter.addAction(ActionStr.BOOK__UPDATE);
        mFilter.addAction(ActionStr.DYNAMIC__DELETE);
        mFilter.addAction(ActionStr.DYNAMIC__INSERT);
        mFilter.addAction(ActionStr.DYNAMIC__QUERY);
        mFilter.addAction(ActionStr.DYNAMIC__UPFATE);
        mFilter.addAction(ActionStr.MOVIE__DELETE);
        mFilter.addAction(ActionStr.MOVIE__INSERT);
        mFilter.addAction(ActionStr.MOVIE__QUERY);
        mFilter.addAction(ActionStr.MOVIE__UPDATE);
        mFilter.addAction(ActionStr.MUSIC__DELETE);
        mFilter.addAction(ActionStr.MUSIC__INSERT);
        mFilter.addAction(ActionStr.MUSIC__QUERY);
        mFilter.addAction(ActionStr.MUSIC__UPDATE);
        mFilter.addAction(ActionStr.MV__DELETE);
        mFilter.addAction(ActionStr.MV__INSERT);
        mFilter.addAction(ActionStr.MV__QUERY);
        mFilter.addAction(ActionStr.MV__UPDATE);
        mFilter.addAction(ActionStr.TOPIC__DELETE);
        mFilter.addAction(ActionStr.TOPIC__INSERT);
        mFilter.addAction(ActionStr.TOPIC__QUERY);
        mFilter.addAction(ActionStr.TOPIC__UPDATE);
        mFilter.addAction(ActionStr.TOPIC_REPLY2__DELETE);
        mFilter.addAction(ActionStr.TOPIC_REPLY2__INSERT);
        mFilter.addAction(ActionStr.TOPIC_REPLY2__QUERY);
        mFilter.addAction(ActionStr.TOPIC_REPLY2__UPDATE);
        mFilter.addAction(ActionStr.TOPIC_REPLY__DELETE);
        mFilter.addAction(ActionStr.TOPIC_REPLY__INSERT);
        mFilter.addAction(ActionStr.TOPIC_REPLY__QUERY);
        mFilter.addAction(ActionStr.TOPIC_REPLY__UPDATE);
        mFilter.addAction(ActionStr.USER__AGREEMENT);
        mFilter.addAction(ActionStr.USER__LOGIN);
        mFilter.addAction(ActionStr.USER__QUERY);
        mFilter.addAction(ActionStr.USER__REGIST);
        mFilter.addAction(ActionStr.USER__UPDATE);
        mFilter.addAction(ActionStr.USER_DETAIL__INSERT);
        mFilter.addAction(ActionStr.USER_DETAIL__QUERY);
        mFilter.addAction(ActionStr.USER_DETAIL__UPDATE);
        return mFilter;
    }

}
