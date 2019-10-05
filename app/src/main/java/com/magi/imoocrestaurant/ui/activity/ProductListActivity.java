package com.magi.imoocrestaurant.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.biz.ProductBiz;
import com.magi.imoocrestaurant.ui.view.refresh.SwipeRefresh;
import com.magi.imoocrestaurant.ui.view.refresh.SwipeRefreshLayout;

public class ProductListActivity extends BaseActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView count_textView;
    private Button pay_button;

    private ProductBiz productBiz = new ProductBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        setupToolbar();
        setTitle("订餐");

        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productBiz.onDestroy();
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        count_textView = findViewById(R.id.count_textView);
        pay_button = findViewById(R.id.pay_button);

        swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW);
    }

    private void initEvent() {
    }


}
