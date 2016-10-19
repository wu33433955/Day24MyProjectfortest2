package com.qf.wmj.day24myprojectfortest.downloadutils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.qf.wmj.day24myprojectfortest.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JB on 2016/10/12.
 */
public class JsonDownloadUtil {
    public interface onDownloadJsonListener{
        void onSendJson(String json);
    }
    private static ExecutorService service = Executors.newCachedThreadPool();
    public static void downloadJson(Context context, final String path, final onDownloadJsonListener listener) {
        final Handler handler = new Handler();
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("加载中请稍后");
        dialog.setInverseBackgroundForced(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在加载...");
        dialog.setIcon(R.drawable.load01);
        dialog.show();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    if(conn.getResponseCode()==200){
                        InputStream is = conn.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] b = new byte[1024];
                        while((len=is.read(b))!=-1){
                            bos.write(b,0,len);
                        }
                        bos.flush();
                        final String json = new String(bos.toByteArray());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSendJson(json);
                                dialog.cancel();
                                dialog.dismiss();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
