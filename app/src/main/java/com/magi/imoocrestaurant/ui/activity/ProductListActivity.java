package com.magi.imoocrestaurant.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.bean.Order;
import com.magi.imoocrestaurant.bean.Product;
import com.magi.imoocrestaurant.bean.ProductModel;
import com.magi.imoocrestaurant.biz.OrderBiz;
import com.magi.imoocrestaurant.biz.ProductBiz;
import com.magi.imoocrestaurant.net.CommonCallback;
import com.magi.imoocrestaurant.ui.adapter.ProductListAdapter;
import com.magi.imoocrestaurant.ui.view.refresh.SwipeRefresh;
import com.magi.imoocrestaurant.ui.view.refresh.SwipeRefreshLayout;
import com.magi.imoocrestaurant.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends BaseActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView count_textView;
    private Button pay_button;

    private ProductListAdapter mAdapter;
    private ProductBiz productBiz = new ProductBiz();
    private OrderBiz orderBiz = new OrderBiz();
    private List<ProductModel> mList = new ArrayList<>();

    private int currentPage = 0;
    private float mTotalPrice;
    private int mTotalCount;

    private Order order = new Order();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        setupToolbar();
        setTitle("订餐");

        initView();
        initEvent();
        loadData();
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

        mAdapter = new ProductListAdapter(this, mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        swipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });

        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();

                if (mTotalCount <= 0) {
                    ToastUtils.showToast("您还没有选择菜品~~~");
                    return;
                }

                order.setCount(mTotalCount);
                order.setPrice(mTotalPrice);
                order.setRestaurant(mList.get(0).getRestaurant());

                orderBiz.add(order, new CommonCallback<String>() {
                    @Override
                    public void onFail(Exception e) {
                        stopLoadingProgress();
                        ToastUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        stopLoadingProgress();
                        ToastUtils.showToast("订单支付成功");

                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });

        mAdapter.setOnProductListener(new ProductListAdapter.OnProductListener() {
            @Override
            public void onCountAdd(ProductModel model) {
                mTotalCount++;
                mTotalPrice += model.getPrice();
                count_textView.setText(String.format("数量:%s", mTotalCount));
                pay_button.setText(String.format("%.2f元 立刻支付", mTotalPrice));

                order.addProduct(model);
            }

            @Override
            public void onCountSub(ProductModel model) {
                mTotalCount--;
                mTotalPrice -= model.getPrice();
                count_textView.setText(String.format("数量:%s", mTotalCount));
                if (mTotalCount == 0) {
                    mTotalPrice = 0;
                }
                pay_button.setText(String.format("%.2f元 立刻支付", mTotalPrice));

                order.removeProduct(model);
            }
        });
    }

    private void loadData() {
        startLoadingProgress();
        productBiz.listByPage(0, new CommonCallback<List<Product>>() {
            @Override
            public void onFail(Exception e) {
                stopLoadingProgress();
                ToastUtils.showToast(e.getMessage());
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

                String message = e.getMessage();
                if (message.contains("用户未登录")) {
                    toLoginActivity();
                }
            }

            @Override
            public void onSuccess(List<Product> response) {
                stopLoadingProgress();
                ToastUtils.showToast("加载成功");
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                currentPage = 0;
                mList.clear();
                for (Product product : response) {
                    mList.add(new ProductModel(product));
                }
                mAdapter.notifyDataSetChanged();
                mTotalCount = 0;
                mTotalPrice = 0;

                count_textView.setText(String.format("数量:%s", mTotalCount));
                pay_button.setText(String.format("%s元 立刻支付", mTotalPrice));
            }
        });

    }

    private void loadMore() {
        startLoadingProgress();
        productBiz.listByPage(++currentPage, new CommonCallback<List<Product>>() {
            @Override
            public void onFail(Exception e) {
                stopLoadingProgress();
                ToastUtils.showToast(e.getMessage());
                currentPage--;
                swipeRefreshLayout.setPullUpRefreshing(false);

                String message = e.getMessage();
                if (message.contains("用户未登录")) {
                    toLoginActivity();
                }

            }

            @Override
            public void onSuccess(List<Product> response) {
                stopLoadingProgress();
                if (response.size() == 0) {
                    ToastUtils.showToast("木有更多了~");
                    swipeRefreshLayout.setPullUpRefreshing(false);
                    return;
                }
                ToastUtils.showToast("加载成功");
                swipeRefreshLayout.setPullUpRefreshing(false);
                for (Product product : response) {
                    mList.add(new ProductModel(product));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }


}
