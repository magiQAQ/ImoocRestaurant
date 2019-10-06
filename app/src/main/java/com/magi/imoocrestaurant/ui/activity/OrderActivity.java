package com.magi.imoocrestaurant.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.UserInfoHolder;
import com.magi.imoocrestaurant.bean.Order;
import com.magi.imoocrestaurant.bean.User;
import com.magi.imoocrestaurant.biz.OrderBiz;
import com.magi.imoocrestaurant.net.CommonCallback;
import com.magi.imoocrestaurant.ui.adapter.OrderAdapter;
import com.magi.imoocrestaurant.ui.view.refresh.SwipeRefresh;
import com.magi.imoocrestaurant.ui.view.refresh.SwipeRefreshLayout;
import com.magi.imoocrestaurant.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

    public static final int REQUEST_CODE = 1001;
    private Button order_button;
    private TextView username_textView;
    private ImageView icon_imageView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Order> mList = new ArrayList<>();

    private OrderBiz orderBiz = new OrderBiz();
    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initEvent();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orderBiz.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            loadData();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            try {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        order_button = findViewById(R.id.order_button);
        username_textView = findViewById(R.id.username_textView);
        icon_imageView = findViewById(R.id.icon_imageView);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        User user = UserInfoHolder.getInstance().getUser();
        if (user != null) {
            username_textView.setText(user.getUsername());
        } else {
            toLoginActivity();
            finish();
            return;
        }

        swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW);

        adapter = new OrderAdapter(this, mList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Glide.with(this)
                .load(R.drawable.icon)
                .placeholder(R.drawable.pictures_no)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(icon_imageView);
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

        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toProductListActivity();
            }
        });
    }

    private void loadData() {
        startLoadingProgress();
        orderBiz.listByPage(0, new CommonCallback<List<Order>>() {
            @Override
            public void onFail(Exception e) {
                stopLoadingProgress();
                ToastUtils.showToast(e.getMessage());

                String message = e.getMessage();
                if (message.contains("用户未登录")) {
                    toLoginActivity();
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                ToastUtils.showToast("订单更新成功");
                mList.clear();
                mList.addAll(response);
                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void loadMore() {
        startLoadingProgress();
        orderBiz.listByPage(++mCurrentPage, new CommonCallback<List<Order>>() {
            @Override
            public void onFail(Exception e) {
                stopLoadingProgress();
                ToastUtils.showToast(e.getMessage());
                swipeRefreshLayout.setPullUpRefreshing(false);
                mCurrentPage--;

                String message = e.getMessage();
                if (message.contains("用户未登录")) {
                    toLoginActivity();
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                if (response.size() == 0) {
                    ToastUtils.showToast("木有订单了~");
                    swipeRefreshLayout.setPullUpRefreshing(false);
                    return;
                }
                ToastUtils.showToast("订单加载成功");
                mList.addAll(response);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setPullUpRefreshing(false);
            }
        });
    }

    private void toProductListActivity() {
        startActivityForResult(new Intent(this,ProductListActivity.class), REQUEST_CODE);

    }


}
