package com.qf.wmj.day24myprojectfortest.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qf.wmj.day24myprojectfortest.R;
import com.qf.wmj.day24myprojectfortest.activity.NetActivity;
import com.qf.wmj.day24myprojectfortest.bean.Bean;
import com.qf.wmj.day24myprojectfortest.downloadutils.BitmapDownloadUtil;
import com.qf.wmj.day24myprojectfortest.fragment.AFragment;

import java.util.ArrayList;

/**
 * Created by JB on 2016/10/14.
 */
public class FragmentAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<Bean> list;
    private Context context;
    private Bean bean;

    public FragmentAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
        this.context=context;
    }
    //用来往集合中添加数据
    public void addData(ArrayList<Bean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    //用来清除集合中数据
    public void clearData() {
        this.list.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private viewHolder holder = null;
    // 返回指定位置的带数据的控件（item视图对象）
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //如果convertview为null，加载item的布局文件
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_bean, null);
            holder = new viewHolder();
            //得到当前行需要更新的子View对象
            holder.description = (TextView) convertView
                    .findViewById(R.id.list_item_bean_tv_info);
            holder.title = (TextView) convertView
                    .findViewById(R.id.list_item_bean_tv_title);
            holder.img = (ImageView) convertView
                    .findViewById(R.id.list_item_bean_iv);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        //得到当前行的数据对象
        bean = (Bean) getItem(position);
        //Bean bean1 = list.get(position);
        //给视图设置数据
        holder.description.setText(bean.getDescription());
        holder.title.setText(bean.getTitle());

        holder.img.setImageResource(R.drawable.load01);
        SharedPreferences isWIFI = context.getSharedPreferences("isWIFI",Context.MODE_PRIVATE);
        boolean wifi = isWIFI.getBoolean("isWIFI", false);
        if(wifi){
            Glide.with(context).load(bean.getCover_url()).into(holder.img);
        }
        //返回当前带有数据的视图
        return convertView;
    }

    class viewHolder {
        TextView description;
        TextView title;
        ImageView img;
    }

}
