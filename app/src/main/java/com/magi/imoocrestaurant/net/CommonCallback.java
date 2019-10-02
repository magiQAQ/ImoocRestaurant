package com.magi.imoocrestaurant.net;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

public abstract class CommonCallback<T> extends StringCallback {

    private Type type;

    public CommonCallback(){
        Class<? extends CommonCallback> clazz = getClass();
        //返回包含泛型参数的超类
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (type instanceof Class){//说明没有给泛型
            throw new RuntimeException("Miss Type Params");
        }

        //参数化类型
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        if (parameterizedType != null) {
            this.type = parameterizedType.getActualTypeArguments()[0];
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        onFail(e);
    }

    @Override
    public void onResponse(String response, int id) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int resultCode = jsonObject.getInt("resultCode");
            if(resultCode==1){
                String data = jsonObject.getString("data");
                Gson gson = new Gson();
                onSuccess(gson.<T>fromJson(data,type));
            } else {
                onFail(new RuntimeException(jsonObject.getString("resultMessage")));
            }
        } catch (JSONException e) {
            onFail(e);
            e.printStackTrace();
        }
    }

    abstract void onFail(Exception e);
    abstract void onSuccess(T response);

}
