package com.qf.wmj.day24myprojectfortest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qf.wmj.day24myprojectfortest.R;

/**
 * Created by gaolei on 16/10/9.
 */
public class NetActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        findViewById(R.id.btn_yes).setOnClickListener(this);
        findViewById(R.id.btn_no).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_yes:
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
                NetActivity.this.finish();
                break;
            case R.id.btn_no:
                NetActivity.this.finish();

                break;
        }
    }
}
