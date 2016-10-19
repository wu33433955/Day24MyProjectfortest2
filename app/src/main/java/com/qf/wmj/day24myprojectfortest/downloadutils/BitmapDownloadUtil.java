package com.qf.wmj.day24myprojectfortest.downloadutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JB on 2016/10/12.
 */
public class BitmapDownloadUtil {
     public static void downloadBitmap(final String path, final ImageView iv){
         final Handler handler = new Handler();
         ExecutorService service = Executors.newCachedThreadPool();

         service.execute(new Runnable() {
             @Override
             public void run() {
                 try {
                     URL url = new URL(path);
                     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                     conn.connect();
                     if(conn.getResponseCode()==200){
                         InputStream is = conn.getInputStream();
                         final Bitmap bitmap = BitmapFactory.decodeStream(is);
                         final Bitmap smallBitmap = BitmapUtil.getSmallBitmap(bitmap);
                         ImageCache.newInstance().putBitmap(path,smallBitmap);
                         handler.post(new Runnable() {
                             @Override
                             public void run() {
                                 String tag = iv.getTag().toString();
                                 if(tag!=null&&tag.equals(path)){
                                     iv.setImageBitmap(smallBitmap);
                                 }
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
