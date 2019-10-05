package com.magi.imoocrestaurant.biz;

import com.magi.imoocrestaurant.bean.Product;
import com.magi.imoocrestaurant.config.Config;
import com.magi.imoocrestaurant.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.List;

public class ProductBiz {
    public void listByPage(int currentPage, CommonCallback<List<Product>> commonCallback){
        OkHttpUtils.post()
                .url(Config.baseUrl + File.separator + "product_find")
                .addParams("currentPage",currentPage+"")
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
