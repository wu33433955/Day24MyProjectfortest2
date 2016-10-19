package com.qf.wmj.day24myprojectfortest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by JB on 2016/10/19.
 */
public class boardcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //网络管理者
        ConnectivityManager service = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取连网信息
        NetworkInfo info = service.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            //获取网络信息类型
            int type = info.getType();
            switch (type){
                //移动网络
                case ConnectivityManager.TYPE_MOBILE:
                    Intent intent1 = new Intent(context, NetActivity.class);
                    context.startActivity(intent1);
                    SharedPreferences sp = context.getSharedPreferences("isWIFI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isWIFI",false);
                    editor.commit();
                break;
                //WIFI网络
                case ConnectivityManager.TYPE_WIFI:
                    SharedPreferences sp1 = context.getSharedPreferences("isWIFI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sp1.edit();
                    editor1.putBoolean("isWIFI",true);
                    editor1.commit();
                    break;
            }
        }else {
            Toast.makeText(context,"请检查您的网络",Toast.LENGTH_SHORT).show();
        }
    }
}
