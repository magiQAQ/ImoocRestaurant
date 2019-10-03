package com.magi.imoocrestaurant.biz;

import com.magi.imoocrestaurant.bean.User;
import com.magi.imoocrestaurant.config.Config;
import com.magi.imoocrestaurant.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

public class UserBiz {

    public void login(String username, String password, CommonCallback<User> commonCallback){
        OkHttpUtils.post()
                .url(Config.baseUrl + "user_login")
                .tag(this)
                .addParams("username", username)
                .addParams("password", password)
                .build()
                .execute(commonCallback);
    }

    public void register(String username, String password, CommonCallback<User> commonCallback){
        OkHttpUtils.post()
                .url(Config.baseUrl + "user_register")
                .tag(this)
                .addParams("username", username)
                .addParams("password", password)
                .build()
                .execute(commonCallback);
    }
}
