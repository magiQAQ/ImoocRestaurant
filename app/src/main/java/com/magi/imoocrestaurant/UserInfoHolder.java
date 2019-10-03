package com.magi.imoocrestaurant;

import com.magi.imoocrestaurant.bean.User;
import com.magi.imoocrestaurant.utils.SharedPreferencesUtils;

public class UserInfoHolder {
    private static UserInfoHolder instance = new UserInfoHolder();
    private User user;
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PASSWORD = "key_password";

    public static UserInfoHolder getInstance() {
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            SharedPreferencesUtils.getInstance().put(KEY_USERNAME, user.getUsername());
            SharedPreferencesUtils.getInstance().put(KEY_PASSWORD, user.getPassword());
        }
    }

    public User getUser() {
        if (user == null){
            String username = (String) SharedPreferencesUtils.getInstance().get(KEY_USERNAME,"");
            String password = (String) SharedPreferencesUtils.getInstance().get(KEY_PASSWORD, "");
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
        }
        return user;
    }
}
