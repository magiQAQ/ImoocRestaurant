package com.magi.imoocrestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.bean.ProductModel;
import com.magi.imoocrestaurant.config.Config;
import com.magi.imoocrestaurant.utils.ToastUtils;

import java.io.File;

public class ProductDetailActivity extends BaseActivity {

    private ImageView icon_imageView;
    private TextView name_textView, description_textView, price_textView;

    private ProductModel productModel;
    public static final String KEY_PRODUCT = "key_product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        setupToolbar();
        setTitle("菜品详情");
        Intent data = getIntent();
        if (data != null) {
            productModel = (ProductModel) data.getSerializableExtra(KEY_PRODUCT);
        }
        if (productModel == null) {
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
                .load(Config.baseUrl + File.separator + productModel.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(icon_imageView);
        name_textView.setText(productModel.getName());
        description_textView.setText(productModel.getDescription());
        price_textView.setText(String.format("%s元/份", productModel.getPrice()));

    }


}
