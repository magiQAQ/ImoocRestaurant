package com.magi.imoocrestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.bean.Order;
import com.magi.imoocrestaurant.config.Config;
import com.magi.imoocrestaurant.utils.ToastUtils;

import java.io.File;

public class OrderDetailActivity extends BaseActivity {

    private ImageView icon_imageView;
    private TextView name_textView, description_textView, price_textView;

    private Order order;
    public static final String KEY_ORDER = "key_order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        setupToolbar();
        setTitle("订单详情");
        Intent data = getIntent();
        if (data!=null){
            order = (Order) data.getSerializableExtra(KEY_ORDER);
        }
        if (order==null){
            ToastUtils.showToast("参数传递错误");
            return;
        }
        initView();
    }

    private void initView() {
        icon_imageView = findViewById(R.id.icon_imageView);
        name_textView = findViewById(R.id.name_textView);
        description_textView = findViewById(R.id.description_textView);
        price_textView = findViewById(R.id.price_textView);

        Glide.with(this)
                .load(Config.baseUrl+ File.separator+order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(icon_imageView);
        name_textView.setText(order.getRestaurant().getName());
        price_textView.setText(String.format("共消费:%s元",order.getPrice()));

        StringBuilder stringBuilder = new StringBuilder();
        for (Order.ProductVo productVo : order.getPs()){
            stringBuilder.append(productVo.product.getName()).append(" * ").append(productVo.count);
            stringBuilder.append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        description_textView.setText(stringBuilder.toString());
    }
}
