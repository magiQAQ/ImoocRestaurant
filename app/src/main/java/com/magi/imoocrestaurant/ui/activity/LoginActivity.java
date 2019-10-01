package com.magi.imoocrestaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.magi.imoocrestaurant.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username_editText,password_editText;
    private Button login_button;
    private TextView register_textView;

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
                // TODO: 2019/9/29 先检查登录是否成功
                toOrderActivity();
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
        startActivity(new Intent(this,OrderActivity.class));
        finish();
    }

    private void toRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
