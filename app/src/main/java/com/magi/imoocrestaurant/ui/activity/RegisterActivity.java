package com.magi.imoocrestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.bean.User;
import com.magi.imoocrestaurant.biz.UserBiz;
import com.magi.imoocrestaurant.net.CommonCallback;
import com.magi.imoocrestaurant.utils.ToastUtils;

public class RegisterActivity extends BaseActivity {

    public static final int RESULT_CODE = 1;
    private UserBiz userBiz = new UserBiz();

    private EditText username_editText,password_editText,repassword_editText;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("手机注册");
        setupToolbar();
        initView();
        initEvent();
    }

    private void initView() {
        username_editText = findViewById(R.id.username_editText);
        password_editText = findViewById(R.id.password_editText);
        repassword_editText = findViewById(R.id.repassword_editText);
        register_button = findViewById(R.id.register_button);
    }

    private void initEvent() {
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_editText.getText().toString();
                String password = password_editText.getText().toString();
                final String repassword = repassword_editText.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ToastUtils.showToast("账号或者密码不能为空");
                    return;
                }

                if (!password.equals(repassword)){
                    ToastUtils.showToast("两次输入的密码不一致");
                    return;
                }
                startLoadingProgress();
                userBiz.register(username, password, new CommonCallback<User>() {
                    @Override
                    public void onFail(Exception e) {
                        stopLoadingProgress();
                        ToastUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        ToastUtils.showToast("注册成功,用户名为:"+response.getUsername());
                        //LoginActivity.launch(RegisterActivity.this,response.getUsername(),response.getPassword());
                        Intent data = new Intent();
                        data.putExtra(LoginActivity.KEY_USERNAME,response.getUsername());
                        data.putExtra(LoginActivity.KEY_PASSWORD, response.getPassword());
                        setResult(RESULT_CODE,data);
                        finish();
                    }
                });
            }
        });
    }

}
