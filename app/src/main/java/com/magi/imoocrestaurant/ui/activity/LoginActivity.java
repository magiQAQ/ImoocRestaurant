package com.magi.imoocrestaurant.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.UserInfoHolder;
import com.magi.imoocrestaurant.bean.User;
import com.magi.imoocrestaurant.biz.UserBiz;
import com.magi.imoocrestaurant.net.CommonCallback;
import com.magi.imoocrestaurant.utils.ToastUtils;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {

    private UserBiz userBiz = new UserBiz();

    private EditText username_editText, password_editText;
    private Button login_button;
    private TextView register_textView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initEvent();
    }

    private void initView() {
        username_editText = findViewById(R.id.username_editText);
        password_editText = findViewById(R.id.password_editText);
        login_button = findViewById(R.id.login_button);
        register_textView = findViewById(R.id.register_textView);
    }

    private void initEvent() {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_editText.getText().toString();
                String password = password_editText.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ToastUtils.showToast("账号或者密码不能为空");
                    return;
                }
                startLoadingProgress();
                userBiz.login(username, password, new CommonCallback<User>() {
                    @Override
                    public void onFail(Exception e) {
                        stopLoadingProgress();
                        ToastUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        ToastUtils.showToast("登录成功");
                        //保存用户信息
                        UserInfoHolder.getInstance().setUser(response);
                        toOrderActivity();
                    }
                });
            }
        });

        register_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterActivity();
            }
        });

    }

    private void startLoadingProgress() {
        if (progressDialog != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载中...");
        }
    }

    private void stopLoadingProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void toOrderActivity() {
        startActivity(new Intent(this, OrderActivity.class));
        finish();
    }

    private void toRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
