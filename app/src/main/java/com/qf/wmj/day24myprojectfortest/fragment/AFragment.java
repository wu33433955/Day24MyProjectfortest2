package com.qf.wmj.day24myprojectfortest.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.wmj.day24myprojectfortest.R;
import com.qf.wmj.day24myprojectfortest.activity.Info;
import com.qf.wmj.day24myprojectfortest.adapter.FragmentAdapter;
import com.qf.wmj.day24myprojectfortest.adapter.LeaderPagerAdapter;
import com.qf.wmj.day24myprojectfortest.bean.Bean;
import com.qf.wmj.day24myprojectfortest.dbutils.DBManager;
import com.qf.wmj.day24myprojectfortest.downloadutils.JsonDownloadUtil;
import com.qf.wmj.day24myprojectfortest.path.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JB on 2016/10/12.
 */
public class AFragment extends Fragment implements AdapterView.OnItemClickListener, JsonDownloadUtil.onDownloadJsonListener,
        ViewPager.OnPageChangeListener, PullToRefreshBase.OnLastItemVisibleListener,PullToRefreshBase.OnRefreshListener {
    private int page=1;
    private PullToRefreshListView fregment_listview_lv;
    // 是否在底部
    boolean isBottom = false;
    // 是否在顶部
    boolean isTop = false;
    private FragmentAdapter adapter;
    private ArrayList<Bean> list1=new ArrayList<Bean>();
    private ViewPager vp;
    private RadioGroup rg;
    private Handler handler=new Handler();
    private Timer timer;
    private boolean isSend = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fregment_listview,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fregment_listview_lv = (PullToRefreshListView) view.findViewById(R.id.fregment_listview_lv);
        //添加顶部试图
        addTopView(fregment_listview_lv);
        //获取当前页数据
        getData(page);
        adapter= new FragmentAdapter(getActivity());
        fregment_listview_lv.setAdapter(adapter);
        //上拉刷新监听
        fregment_listview_lv.setOnRefreshListener(this);
        //下拉加载监听
        fregment_listview_lv.setOnLastItemVisibleListener(this);
        // 设置Item点击监听器
        fregment_listview_lv.setOnItemClickListener(this);

    }

    private void getData(int page) {
        // 设置path路径
        String path = Path.getPath1;
        // 获取json下载工具类要下载的路径及页数
        String p = String.format(path, page);
        // 实例化json下载工具类对象，启动下载
        JsonDownloadUtil.downloadJson(getActivity(),p,this);
    }

    private void addTopView(PullToRefreshListView fregment_listview_lv) {
        int[] imgs = {R.drawable.top1,R.drawable.top5,
                R.drawable.top3,R.drawable.top4};
        ArrayList<View> vpData = new ArrayList< >();
        for (int i = 0; i < imgs.length; i++) {
            ImageView iv = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,
                    ViewPager.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imgs[i]);
            vpData.add(iv);
        }
        LeaderPagerAdapter pagerAdapter = new LeaderPagerAdapter(vpData);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View topView = inflater.inflate(R.layout.fragment_topview,null);
        vp = (ViewPager) topView.findViewById(R.id.fragment_top_vp);
        rg = (RadioGroup) topView.findViewById(R.id.fragment_top_rg);
        //头部视图设置适配器
        vp.setAdapter(pagerAdapter);
        //头部视图设置滑动监听
        vp.setOnPageChangeListener(this);
        //添加头部视图
        fregment_listview_lv.getRefreshableView().addHeaderView(topView);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position>0){
            String id2 = list1.get(position-1).getId();
            String path =Path.getPathInfo+id2;
            Intent intent = new Intent(AFragment.this.getActivity(),Info.class);
            //传递当前详情界面需要拼凑的URL
            intent.putExtra("id2", path);
            startActivity(intent);
        }
    }
    private int num =0;
    //头部视图轮播
    @Override
    public void onResume() {

        super.onResume();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (vp!=null) {
                            vp.setCurrentItem(num);
                        }
                        num++;
                        if(num==4){
                            num=0;
                        }
                    }
                });
            }
        },0,3000);

    }
    @Override
    public void onSendJson(String json) {
        DBManager manager = new DBManager(getActivity());
        //实例化一个泛型为javabean的数组用来存储数据
        ArrayList<Bean> list = new ArrayList<Bean>();
        //判断json对象不为空或json字符串部位空字符串，为空则直接结束
        if (json != null || !"".equals(json)) {
            if (page == 1) {
                manager.clear("beans");
            }
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String id = (String) object.opt("id");
                    String title = (String) object.opt("title");
                    String dp = (String) object.opt("description");
                    String ImgUrl = (String) object.opt("cover_url");
                    Bean beans = new Bean(id, title, dp, ImgUrl);
                    list.add(beans);
                    if (page == 1) {
                        ContentValues values = new ContentValues();
                        values.put("id", id);
                        values.put("title", title);
                        values.put("dp", dp);
                        values.put("imgUrl", ImgUrl);
                        manager.insert("beans", values);
                    }
                }
                // 将数据设置进入适配器
                adapter.addData(list);
                list1.addAll(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Cursor cursor = manager.queryAll("beans");
            ArrayList<Bean> list2 = new ArrayList<>();
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String dp = cursor.getString(cursor.getColumnIndex("dp"));
                String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
                Bean beans = new Bean(id, title, dp, imgUrl);
                list2.add(beans);
            }
            adapter.addData(list2);
            fregment_listview_lv.setAdapter(adapter);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    //设置滑动与选中的联动
    @Override
    public void onPageSelected(int position) {
        RadioButton rb = (RadioButton) rg.getChildAt(position);
        rb.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 上拉加载时调用
     */
    @Override
    public void onLastItemVisible() {
        page++;
        getData(page);
    }

    /**
     * 下拉刷新时调用
     * @param refreshView
     */
    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        page = 1;
        adapter.clearData();
        getData(page);
        /**
         * 隐藏刷新试图
         */
        fregment_listview_lv.postDelayed(new Runnable() {
            @Override
            public void run() {
                fregment_listview_lv.onRefreshComplete();
            }
        }, 1000);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer!=null) {
            timer.cancel();
        }
    }
}
