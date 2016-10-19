package com.qf.wmj.day24myprojectfortest.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.qf.wmj.day24myprojectfortest.R;
import com.qf.wmj.day24myprojectfortest.adapter.LeaderPagerAdapter;

import java.util.ArrayList;

/**
 * Created by JB on 2016/10/14.
 */
public class Lead extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private RadioGroup rg;
    private Button btn;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lead);
        sp = getSharedPreferences("data", MODE_PRIVATE);
        boolean b = sp.getBoolean("first", false);
        if (b) {
            toWelcome();
        } else {
            setContentView(R.layout.lead);
            vp = (ViewPager) findViewById(R.id.lead_vp);
            rg = (RadioGroup) findViewById(R.id.lead_rg);
            btn = (Button) findViewById(R.id.lead_btn);
            btn.setOnClickListener(this);
            /**
             * 设置页面转态改变监听器
             */
            vp.setOnPageChangeListener(this);
            ArrayList<View> list = new ArrayList<>();
            LayoutInflater inflater = getLayoutInflater();
            int[] background = {R.drawable.bg06, R.drawable.bg17,
                    R.drawable.bg13, R.drawable.bg14};
            for (int i = 0; i < background.length; i++) {
                View view = inflater.inflate(R.layout.lead_image, null);
                ImageView iv = (ImageView) view.findViewById(R.id.lead_image_iv);
                iv.setBackgroundResource(background[i]);
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                list.add(view);
            }
            LeaderPagerAdapter adapter = new LeaderPagerAdapter(list);
            vp.setAdapter(adapter);
        }

    }

    private void toWelcome() {
        Intent intent = new Intent(Lead.this, Welcome.class);
        //启动activity跳转
        startActivity(intent);
        Lead.this.finish();
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("first", true);
        editor.commit();
        toWelcome();
    }

    // 页面滑动中调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 选中后调用
    @Override
    public void onPageSelected(int position) {
        RadioButton rb = (RadioButton) rg.getChildAt(position);
        rb.setChecked(true);
        if (position == 3) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    // 页面滑动改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
