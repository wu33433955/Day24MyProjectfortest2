package com.qf.wmj.day24myprojectfortest.downloadutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by gaolei on 16/10/12.
 */

public class BitmapUtil {
    /**
     * 图片二次采样
     */
    public static Bitmap getSmallBitmap(Bitmap bitmap){
        /**
         * 创建图片工厂选项
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         * 将options的值设为Config.RGB_565，会比默认的Config.ARGB_8888减少一半内存
         */
        options.inPreferredConfig= Bitmap.Config.RGB_565;
//        解析图片边界
//        options.inJustDecodeBounds = true;
        /**
         * 设置宽高压缩比例
         */
        options.inSampleSize = 4;
        /**
         * 将图片转换为byte数组,并压缩其50%的质量
         */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
        if (b){
            byte[] bytes = bos.toByteArray();
            Bitmap smallBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            return smallBitmap;
        }
        return null;
    }
}
