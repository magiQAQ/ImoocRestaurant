package com.magi.imoocrestaurant.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.magi.imoocrestaurant.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText phone_editText,password_editText,repassword_editText;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("手机注册");
        getWindow().setStatusBarColor(0xff0000);
        setupToolbar();
        initView();
        initEvent();
    }

    private void initView() {
        phone_editText = findViewById(R.id.phone_editText);
        password_editText = findViewById(R.id.password_editText);
        repassword_editText = findViewById(R.id.repassword_editText);
        register_button = findViewById(R.id.register_button);
    }

    private void initEvent() {
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
