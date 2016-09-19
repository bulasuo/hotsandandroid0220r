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

import com.hotsand.blink.constant.Constant;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_container;
    private LayoutInflater inflater;

    private void addActionTestButton(String actionName, View.OnClickListener listener){
        final Button btn = (Button) inflater.inflate(R.layout.inflater_button, null);
        btn.setText(actionName);
        btn.setOnClickListener(listener);
        ll_container.addView(btn);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_container = (LinearLayout)findViewById(R.id.ll_container);
        inflater = getLayoutInflater();
        initActionTestButton();
        //放在最后防止mReceive里的处理用到的组件还未初始化.
        this.registerReceiver(mReceiver, getFilter());
    }

    private void initActionTestButton(){
        //获取短信验证码
        addActionTestButton("获取短信验证码API", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getAction()){
                case Constant.Action.USER_GET_SMS:

                    break;
                case Constant.Action.BLINK__BLINK:

                    break;
                case Constant.Action.BLINK__MY_BLINKERS:

                    break;
                case Constant.Action.BLINK__MY_BLINKS:

                    break;
                case Constant.Action.BLINK__UNBLINK:

                    break;
                case Constant.Action.BOOK__DELETE:

                    break;
                case Constant.Action.BOOK__INSERT:

                    break;
                case Constant.Action.BOOK__QUERY:

                    break;
                case Constant.Action.BOOK__UPDATE:

                    break;
                case Constant.Action.DYNAMIC__DELETE:

                    break;
                case Constant.Action.DYNAMIC__INSERT:

                    break;
                case Constant.Action.DYNAMIC__QUERY:

                    break;
                case Constant.Action.DYNAMIC__UPFATE:

                    break;
                case Constant.Action.MOVIE__DELETE:

                    break;
                case Constant.Action.MOVIE__INSERT:

                    break;
                case Constant.Action.MOVIE__QUERY:

                    break;
                case Constant.Action.MOVIE__UPDATE:

                    break;
                case Constant.Action.MUSIC__DELETE:

                    break;
                case Constant.Action.MUSIC__INSERT:

                    break;
                case Constant.Action.MUSIC__QUERY:

                    break;
                case Constant.Action.MUSIC__UPDATE:

                    break;
                case Constant.Action.MV__DELETE:

                    break;
                case Constant.Action.MV__INSERT:

                    break;
                case Constant.Action.MV__QUERY:

                    break;
                case Constant.Action.MV__UPDATE:

                    break;
                case Constant.Action.TOPIC__DELETE:

                    break;
                case Constant.Action.TOPIC__INSERT:

                    break;
                case Constant.Action.TOPIC__QUERY:

                    break;
                case Constant.Action.TOPIC__UPDATE:

                    break;
                case Constant.Action.TOPIC_REPLY2__DELETE:

                    break;
                case Constant.Action.TOPIC_REPLY2__INSERT:

                    break;
                case Constant.Action.TOPIC_REPLY2__QUERY:

                    break;
                case Constant.Action.TOPIC_REPLY2__UPDATE:

                    break;
                case Constant.Action.TOPIC_REPLY__DELETE:

                    break;
                case Constant.Action.TOPIC_REPLY__INSERT:

                    break;
                case Constant.Action.TOPIC_REPLY__QUERY:

                    break;
                case Constant.Action.TOPIC_REPLY__UPDATE:

                    break;
                case Constant.Action.USER__AGREEMENT:

                    break;
                case Constant.Action.USER__LOGIN:

                    break;
                case Constant.Action.USER__QUERY:

                    break;
                case Constant.Action.USER__REGIST:

                    break;
                case Constant.Action.USER__UPDATE:

                    break;
                case Constant.Action.USER_DETAIL__INSERT:

                    break;
                case Constant.Action.USER_DETAIL__QUERY:

                    break;
                case Constant.Action.USER_DETAIL__UPDATE:

                    break;
                default:
                    break;
            }
        }
    };

    private IntentFilter getFilter(){
        final IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Constant.Action.USER_GET_SMS);
        mFilter.addAction(Constant.Action.BLINK__BLINK);
        mFilter.addAction(Constant.Action.BLINK__MY_BLINKERS);
        mFilter.addAction(Constant.Action.BLINK__MY_BLINKS);
        mFilter.addAction(Constant.Action.BLINK__UNBLINK);
        mFilter.addAction(Constant.Action.BOOK__DELETE);
        mFilter.addAction(Constant.Action.BOOK__INSERT);
        mFilter.addAction(Constant.Action.BOOK__QUERY);
        mFilter.addAction(Constant.Action.BOOK__UPDATE);
        mFilter.addAction(Constant.Action.DYNAMIC__DELETE);
        mFilter.addAction(Constant.Action.DYNAMIC__INSERT);
        mFilter.addAction(Constant.Action.DYNAMIC__QUERY);
        mFilter.addAction(Constant.Action.DYNAMIC__UPFATE);
        mFilter.addAction(Constant.Action.MOVIE__DELETE);
        mFilter.addAction(Constant.Action.MOVIE__INSERT);
        mFilter.addAction(Constant.Action.MOVIE__QUERY);
        mFilter.addAction(Constant.Action.MOVIE__UPDATE);
        mFilter.addAction(Constant.Action.MUSIC__DELETE);
        mFilter.addAction(Constant.Action.MUSIC__INSERT);
        mFilter.addAction(Constant.Action.MUSIC__QUERY);
        mFilter.addAction(Constant.Action.MUSIC__UPDATE);
        mFilter.addAction(Constant.Action.MV__DELETE);
        mFilter.addAction(Constant.Action.MV__INSERT);
        mFilter.addAction(Constant.Action.MV__QUERY);
        mFilter.addAction(Constant.Action.MV__UPDATE);
        mFilter.addAction(Constant.Action.TOPIC__DELETE);
        mFilter.addAction(Constant.Action.TOPIC__INSERT);
        mFilter.addAction(Constant.Action.TOPIC__QUERY);
        mFilter.addAction(Constant.Action.TOPIC__UPDATE);
        mFilter.addAction(Constant.Action.TOPIC_REPLY2__DELETE);
        mFilter.addAction(Constant.Action.TOPIC_REPLY2__INSERT);
        mFilter.addAction(Constant.Action.TOPIC_REPLY2__QUERY);
        mFilter.addAction(Constant.Action.TOPIC_REPLY2__UPDATE);
        mFilter.addAction(Constant.Action.TOPIC_REPLY__DELETE);
        mFilter.addAction(Constant.Action.TOPIC_REPLY__INSERT);
        mFilter.addAction(Constant.Action.TOPIC_REPLY__QUERY);
        mFilter.addAction(Constant.Action.TOPIC_REPLY__UPDATE);
        mFilter.addAction(Constant.Action.USER__AGREEMENT);
        mFilter.addAction(Constant.Action.USER__LOGIN);
        mFilter.addAction(Constant.Action.USER__QUERY);
        mFilter.addAction(Constant.Action.USER__REGIST);
        mFilter.addAction(Constant.Action.USER__UPDATE);
        mFilter.addAction(Constant.Action.USER_DETAIL__INSERT);
        mFilter.addAction(Constant.Action.USER_DETAIL__QUERY);
        mFilter.addAction(Constant.Action.USER_DETAIL__UPDATE);
        return mFilter;
    }

}
