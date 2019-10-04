package com.magi.imoocrestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.UserInfoHolder;
import com.magi.imoocrestaurant.bean.User;
import com.magi.imoocrestaurant.biz.UserBiz;
import com.magi.imoocrestaurant.net.CommonCallback;
import com.magi.imoocrestaurant.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

public class LoginActivity extends BaseActivity {

    public static final int RegisterActivity_REQUEST_CODE = 1;
    private UserBiz userBiz = new UserBiz();

    private EditText username_editText, password_editText;
    private Button login_button;
    private TextView register_textView;

    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //清空cookie
        CookieJarImpl cookieJar = (CookieJarImpl) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        cookieJar.getCookieStore().removeAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RegisterActivity_REQUEST_CODE && resultCode ==RegisterActivity.RESULT_CODE && data!=null){
            String username = data.getStringExtra(KEY_USERNAME);
            String password = data.getStringExtra(KEY_PASSWORD);
            username_editText.setText(username);
            password_editText.setText(password);
        }
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


    private void toOrderActivity() {
        startActivity(new Intent(this, OrderActivity.class));
        finish();
    }

    private void toRegisterActivity() {
        startActivityForResult(new Intent(this, RegisterActivity.class), RegisterActivity_REQUEST_CODE);
    }
}
