package com.qf.wmj.day24myprojectfortest.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.wmj.day24myprojectfortest.R;
import com.qf.wmj.day24myprojectfortest.adapter.MainAdapter;
import com.qf.wmj.day24myprojectfortest.fragment.AFragment;
import com.qf.wmj.day24myprojectfortest.fragment.BFragment;
import com.qf.wmj.day24myprojectfortest.fragment.CFragment;
import com.qf.wmj.day24myprojectfortest.fragment.DFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager main_vp;
    private LinearLayout main_ll;
    private View main_view;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
//    private Button cehua_btn_return;
    private Button cehua_btn_exit;
    private TextView cehua_tv1;
    private TextView cehua_tv2;
    private TextView cehua_tv3;
    private TextView cehua_tv4;
    private ScrollView scrollView_cehua;
    private boardcast bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        judgeNet(this,"data");

/**
 * 动态发送广播，发送给自定义广播接收者
 */
        //意图过滤器
        IntentFilter filter=new IntentFilter();
        //实例化广播接收者
        bd = new boardcast();
        //添加意图过滤器行为
        filter.addAction("isWIFI");
        //动态注册广播
        registerReceiver(bd,filter);
        //意图
        Intent intent = new Intent();
        //意图行为
        intent.setAction("isWIFI");
        //发送广播
        sendBroadcast(intent);



        setContentView(R.layout.activity_main);
        initView();
        toolbar.setTitle("热门应用");
        toolbar.setTitleTextColor(Color.WHITE);

        new PullToRefreshListView(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                0,
                0);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);

        //定义集合添加碎片（fragment）
        ArrayList<Fragment> list = new ArrayList<>();
        FragmentManager manager = getSupportFragmentManager();
        AFragment aFragment = new AFragment();
        BFragment bFragment = new BFragment();
        CFragment cFragment = new CFragment();
        DFragment dFragment = new DFragment();
        list.add(aFragment);
        list.add(bFragment);
        list.add(cFragment);
        list.add(dFragment);
        MainAdapter adapter = new MainAdapter(manager,list);
        main_vp.setAdapter(adapter);

    }

    private void initView() {
//        cehua_btn_return = (Button) findViewById(R.id.main_cehua_btn_return);
        cehua_btn_exit = (Button) findViewById(R.id.main_cehua_btn_exit);
        cehua_tv1 = (TextView) findViewById(R.id.main_cehua_tv1);
        cehua_tv2 = (TextView) findViewById(R.id.main_cehua_tv2);
        cehua_tv3 = (TextView) findViewById(R.id.main_cehua_tv3);
        cehua_tv4 = (TextView) findViewById(R.id.main_cehua_tv4);
        scrollView_cehua = (ScrollView) findViewById(R.id.scrollView_cehua);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        main_ll = (LinearLayout) findViewById(R.id.main_ll);
        main_vp = (ViewPager) findViewById(R.id.main_vp);
        main_view = findViewById(R.id.main_view);
        findViewById(R.id.main_tv1).setOnClickListener(this);
        findViewById(R.id.main_tv2).setOnClickListener(this);
        findViewById(R.id.main_tv3).setOnClickListener(this);
        findViewById(R.id.main_tv4).setOnClickListener(this);
        cehua_tv1.setOnClickListener(this);
        cehua_tv2.setOnClickListener(this);
        cehua_tv3.setOnClickListener(this);
        cehua_tv4.setOnClickListener(this);
//        cehua_btn_return.setOnClickListener(this);
        cehua_btn_exit.setOnClickListener(this);
        main_vp.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tv1:
                main_vp.setCurrentItem(0, true);
                break;
            case R.id.main_tv2:
                main_vp.setCurrentItem(1, true);
                break;
            case R.id.main_tv3:
                main_vp.setCurrentItem(2, true);
                break;
            case R.id.main_tv4:
                main_vp.setCurrentItem(3, true);
                break;
//            case R.id.main_cehua_btn_return:
//                drawerLayout.closeDrawer(scrollView_cehua);
//                drawerLayout.closeDrawer(Gravity.LEFT);
//                drawerLayout.cancelLongPress();
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                break;
            case R.id.main_cehua_btn_exit:
                System.exit(0);
                break;
            case R.id.main_cehua_tv1:
                main_vp.setCurrentItem(0, true);
                break;
            case R.id.main_cehua_tv2:
                main_vp.setCurrentItem(1, true);
                break;
            case R.id.main_cehua_tv3:
                main_vp.setCurrentItem(2, true);
                break;
            case R.id.main_cehua_tv4:
                main_vp.setCurrentItem(3, true);
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
private boolean isBack=true;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK==keyCode&&isBack){
            if(isBack){
                isBack=false;
                Toast.makeText(this,"再按一次返回键退出",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        isBack=true;
                    }
                }).start();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) main_view.getLayoutParams();
        params.leftMargin = (int) ((position + positionOffset) * main_view.getWidth());
        main_view.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    /**
     * 该方法用于判断网络状态
     * 注意使用该方法是请将NetActivity也拷贝
     * @param context:环境
     * @param name:数据存储的文件名称(SharedPreferences)
     */
    private void judgeNet(Context context, String name) {
        //网路状态管理者
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获得当前网络信息
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
            //已经连上网络
            int type = info.getType();
            switch (type){
                case ConnectivityManager.TYPE_MOBILE://移动网络状态
                    Intent i = new Intent(context, NetActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
                case ConnectivityManager.TYPE_WIFI://WIFI状态
                    SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("isWIFI",true);
                    edit.commit();
                    break;
            }
        }else{
            Toast.makeText(context,"请检查您的网路",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解绑(解除注册状态)
        unregisterReceiver(bd);
    }
}
