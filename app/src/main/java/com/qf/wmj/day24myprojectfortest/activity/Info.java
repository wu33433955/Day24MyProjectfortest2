package com.qf.wmj.day24myprojectfortest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qf.wmj.day24myprojectfortest.R;

/**
 * Created by JB on 2016/10/15.
 */
public class Info extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);
        WebView webv = (WebView) findViewById(R.id.item_info_webv);
        WebSettings settings = webv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        Intent intent = getIntent();
        String path=intent.getStringExtra("id2");
        //String path=Path.PATHINFO+id;
        webv.loadUrl(path);
        webv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
