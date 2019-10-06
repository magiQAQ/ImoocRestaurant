package com.magi.imoocrestaurant.net;

import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.magi.imoocrestaurant.utils.GsonUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

public abstract class CommonCallback<T> extends StringCallback {

    private Type type;

    public CommonCallback(){
        type = getSuperclassTypeParameter(getClass());
    }

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        //返回包含泛型参数的超类
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {//说明没有给泛型
            throw new RuntimeException("Missing type parameter.");
        }
        //参数化类型
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    @Override
    public void onError(Call call, Exception e, int id) {
        onFail(e);
    }

    public abstract void onFail(Exception e);
    public abstract void onSuccess(T response);

    @Override
    public void onResponse(String response, int id) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int resultCode = jsonObject.getInt("resultCode");
            if(resultCode==1){
                String data = jsonObject.getString("data");
                onSuccess(GsonUtils.getGson().<T>fromJson(data,type));
            } else {
                onFail(new RuntimeException(jsonObject.getString("resultMessage")));
            }
        } catch (JSONException e) {
            Log.i("json",response);
            onFail(e);
            e.printStackTrace();
        }
    }



}
