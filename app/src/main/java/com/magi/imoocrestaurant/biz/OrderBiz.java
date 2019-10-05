package com.magi.imoocrestaurant.biz;

import com.magi.imoocrestaurant.bean.Order;
import com.magi.imoocrestaurant.bean.Product;
import com.magi.imoocrestaurant.config.Config;
import com.magi.imoocrestaurant.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class OrderBiz {
    public void listByPage(int currentPage, CommonCallback<List<Order>> commonCallback) {
        OkHttpUtils.post()
                .url(Config.baseUrl + File.separator + "order_find")
                .addParams("currentPage", currentPage + "")
                .build()
                .execute(commonCallback);
    }

    public void add(Order order, CommonCallback<String> commonCallback) {

        StringBuilder stringBuilder = new StringBuilder();
        Map<Product, Integer> productMap = order.productMap;
        for (Product p : productMap.keySet()) {
            stringBuilder.append(p.getId()).append("_").append(productMap.get(p));
            stringBuilder.append("|");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);


        OkHttpUtils.post()
                .url(Config.baseUrl + File.separator + "order_add")
                .addParams("res_id", order.getRestaurant().getId() + "")
                .addParams("product_str", stringBuilder.toString())
                .addParams("count", order.getCount() + "")
                .addParams("price", order.getPrice() + "")
                .tag(this)
                .build()
                .execute(commonCallback);

    }

    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
