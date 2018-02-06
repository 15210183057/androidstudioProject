package com.example.a123456.zhonggu;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseActivity;

public class CartListInfoMsgActivity extends BaseActivity implements View.OnClickListener{
    private ImageView img_msg_back;
    private TextView tv_msg_title;
    private TextView tv_msg_save;
    private WebView webView;
    private ProgressBar progressBar;
    String UrlStr="http://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartlistinfomsg);
        initView();
    }

    private void initView() {
        img_msg_back=findViewById(R.id.img_msg_back);
        tv_msg_title=findViewById(R.id.tv_msg_title);
        tv_msg_save=findViewById(R.id.tv_msg_save);

        img_msg_back.setOnClickListener(this);
        tv_msg_save.setOnClickListener(this);

        webView=findViewById(R.id.web_msg);
        progressBar=findViewById(R.id.pro);
        WebSettings webSettings=webView.getSettings();
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e("TAG","error这个方法=="+error.getPrimaryError());

                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) {
                    view.loadUrl(url);
                } else {
                    // 不是网址时，可能是打开app的链接，可以打开链接或者忽略
                    // 打开app的方法
                    /*try {
                        startActivity(Intent.parseUri(url, Intent.URI_INTENT_SCHEME));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }*/
                }
                return true;
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(request.toString());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Log.e("TAG","7.0路径=="+request.getUrl().toString());
//                    view.loadUrl(request.getUrl().toString());
//                } else {
//                    Log.e("TAG","路径222222=="+request.toString());
//                    view.loadUrl(request.toString());
//                }
//
//                return true;
//            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
        //加载URL
        webView.loadUrl(UrlStr);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_msg_back:
                finish();
                break;
            case R.id.tv_msg_save:
                Toast.makeText(CartListInfoMsgActivity.this,"保存",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
