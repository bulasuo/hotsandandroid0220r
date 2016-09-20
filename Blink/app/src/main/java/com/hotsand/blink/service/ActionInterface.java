package com.hotsand.blink.service;

/**
 * Created by abu on 2016/9/6 14:32.
 */
public interface ActionInterface {

    /**
     * 数据传输的字段
     *@author   abu   2016/9/6   15:47
     */
    interface KEY{
        String
                CODE = "code"
                , MESSAGE = "message"
                , TAG = "tag"
                , ACTION = "action"
                , OBJ = "obj"
                ;
    }

    /**
     * c端请求的action值 int类型
     *@author   abu   2016/9/6   15:47
     */
    interface VALUE{
        int
                USER__REGIST = 1                  //注册_无需权限
                , USER__LOGIN = 2                   //登录_个人权限
                , USER__AGREEMENT = 3               //用户协议_无需权限
                , BLINK__MY_BLINKS = 4              //被我眨眼的人list_个人权限
                , BLINK__MY_BLINKERS = 5            //对我眨眼的人list_个人权限
                , BLINK__BLINK = 6                  //用户对一个人眨眼_个人权限
                , BLINK__UNBLINK = 7                //用户对一个人闭眼(取消眨眼)_个人权限
                , BOOK__INSERT = 8             //书的增加_个人权限
                , BOOK__DELETE =9              //书的删除_个人权限
                , BOOK__UPDATE = 10            //书的修改_个人权限
                , BOOK__QUERY = 11             //书的查询_无需权限
                , DYNAMIC__INSERT = 12         //动态增加_个人权限
                , DYNAMIC__DELETE = 13         //话动态删除_个人权限
                , DYNAMIC__UPFATE = 14         //动态修改_个人权限
                , DYNAMIC__QUERY = 15          //动态查询list_无需权限
                , MOVIE__INSERT = 16           //电影增加_个人权限
                , MOVIE__DELETE = 17           //电影删除_个人权限
                , MOVIE__UPDATE = 18           //电影修改_个人权限
                , MOVIE__QUERY = 19            //电影查询_无需权限
                , MUSIC__INSERT = 20           //音乐增加_个人权限
                , MUSIC__DELETE = 21           //音乐删除_个人权限
                , MUSIC__UPDATE = 22           //音乐修改_个人权限
                , MUSIC__QUERY = 23            //音乐查询_无需权限
                , MV__INSERT = 24              //MV增加_个人权限
                , MV__DELETE = 25              //MV删除_个人权限
                , MV__UPDATE = 26              //MV修改_个人权限
                , MV__QUERY = 27               //MV查询_无需权限
                , TOPIC__INSERT = 28           //话题增加_个人权限
                , TOPIC__DELETE = 29           //话题删除_个人权限
                , TOPIC__UPDATE = 30           //话题修改_个人权限
                , TOPIC__QUERY = 31            //话题查询_无需权限
                , TOPIC_REPLY__INSERT = 32     //话题一级回复增加_个人权限
                , TOPIC_REPLY__DELETE = 33     //话题一级回复删除_个人权限
                , TOPIC_REPLY__UPDATE = 34     //话题一级回复修改_个人权限
                , TOPIC_REPLY__QUERY = 35      //话题一级回复查询_无需权限
                , TOPIC_REPLY2__INSERT = 36    //话题二级回复增加_个人权限
                , TOPIC_REPLY2__DELETE = 37    //话题二级回复删除_个人权限
                , TOPIC_REPLY2__UPDATE = 38    //话题二级回复修改_个人权限
                , TOPIC_REPLY2__QUERY = 39     //话题二级回复查询_无需权限
                , USER__UPDATE = 40              //用户信息修改
                , USER__QUERY = 41               //用户信息查询
                , USER_DETAIL__INSERT = 42       //用户详细信息增加
                , USER_DETAIL__DELETE = 43       //用户详细信息删除<(user和user_detail表)不进行删除活动>
                , USER_DETAIL__UPDATE = 44       //用户详细信息修改
                , USER_DETAIL__QUERY = 45        //用户详细查询
                , USER_GET_SMS = 46             //用户获取短信验证码
                ;
    }
}
