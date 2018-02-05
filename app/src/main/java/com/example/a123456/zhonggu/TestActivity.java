package com.example.a123456.zhonggu;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{
TextView tv1,tv2;
ImageView img;
    String imgUrl="http://images-shichai.test.cnfol.com/original/201606/4_1464745054_1.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        img=findViewById(R.id.img);
        tv2.setOnClickListener(this);
        tv1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv1:
                RequestParams params=new RequestParams("https://www.baidu.com/s");
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("TAG","请求成共="+result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.tv2:
                //加载图pain
                Glide.with(this).load(imgUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(img);
                break;
        }
    }
}
