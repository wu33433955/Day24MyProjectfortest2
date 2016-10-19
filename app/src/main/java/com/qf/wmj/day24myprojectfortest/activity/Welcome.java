package com.qf.wmj.day24myprojectfortest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.qf.wmj.day24myprojectfortest.R;

/**
 * Created by JB on 2016/10/15.
 */
public class Welcome extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lead_welcome);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);Intent intent = new Intent(Welcome.this,MainActivity.class);
                    startActivity(intent);
                    Welcome.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
