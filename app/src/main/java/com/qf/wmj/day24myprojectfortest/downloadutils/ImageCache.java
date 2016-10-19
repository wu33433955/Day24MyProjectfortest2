package com.qf.wmj.day24myprojectfortest.downloadutils;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by gaolei on 16/10/12.
 */

public class ImageCache {
    /**
     * 使用强引用的缓存区,即使内存不足,也不会被释放
     * 保证了当前经常使用的图片不被JVM回收
     */
    private LruCache<String,Bitmap> lruCache;
    /**
     * 使用内存缓存
     * 因为HashMap可以持续添加数据,如果使用强引用可能会导致内存溢出
     */
    private HashMap<String,SoftReference<Bitmap>> map;
    private ImageCache(){
        map = new LinkedHashMap<String,SoftReference<Bitmap>>();

        Runtime runtime = Runtime.getRuntime();
        /**
         * 获得JVM的当前内存总量
         */
        long totalMemory = runtime.totalMemory();
        /**
         * 获得JVM的可用内存大小
         */
        long freeMemory = runtime.freeMemory();
        /**
         * 获得JVM的最大内存
         */
        long maxMemory = runtime.maxMemory();
        /**
         * 设置LruCache当前的内存为系统总量的1/8
         */
        lruCache = new LruCache<String,Bitmap>((int)(totalMemory/8));
    }
    private static ImageCache imageCache;
    public static ImageCache newInstance() {
        if (imageCache == null){
            imageCache = new ImageCache();
        }
        return imageCache;
    }
    public void putBitmap(String url,Bitmap bitmap){
        SoftReference<Bitmap> sr = new SoftReference<>(bitmap);
        map.put(url,sr);
        lruCache.put(url,bitmap);
    }
    public Bitmap getBitmap(String url){
        Bitmap bitmap = null;
        if (url != null){
            bitmap = lruCache.get(url);
            if (bitmap == null) {
                SoftReference<Bitmap> sr = map.get(url);
                if (sr != null){
                    bitmap = sr.get();
                    if (bitmap != null){
                        lruCache.put(url,bitmap);
                    }
                }
            }
        }
        return bitmap;
    }
}
