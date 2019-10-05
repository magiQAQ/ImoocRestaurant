package com.magi.imoocrestaurant.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.magi.imoocrestaurant.R;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void startLoadingProgress() {
        if (progressDialog != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载中...");
        }
    }

    protected void stopLoadingProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getWindow().setStatusBarColor(0xff0000);
    }

    protected void toLoginActivity() {
        startActivity(new Intent(this,LoginActivity.class));
    }
}
