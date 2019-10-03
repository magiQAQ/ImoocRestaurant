package com.magi.imoocrestaurant.utils;

import com.google.gson.Gson;

/**
 * Created by zhy on 16/10/23.
 */
public class GsonUtils {

    public static Gson mGson = new Gson();

    public static Gson getGson() {
        return mGson;
    }

}
