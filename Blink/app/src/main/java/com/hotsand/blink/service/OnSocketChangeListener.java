package com.hotsand.blink.service;

/**
 * Created by abu on 2016/9/10 10:10.
 */
public interface OnSocketChangeListener {

    /**
     * socket连接成功
     * 连接异常的情况下,最多尝试5次重新连接,这里应该与连接成功后断开区分开
     *@author   abu   2016/9/10   10:59
     */
    void onSocketConnect();

    /**
     * 无法连接服务器,服务器宕机,不在外网,c端没有网 等等
     *@author   abu   2016/9/10   10:58
     */
    void onSocketUnableConnect();

    /**
     * socket断开
     *@author   abu   2016/9/10   10:58
     */
    void onSocketDisConnect();
}
