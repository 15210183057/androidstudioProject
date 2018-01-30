package com.example.a123456.zhonggu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private ImageView img_back;
    private RelativeLayout relative1,relative11,relative2,relative22,relative3,relative33;
    private TextView tv_tuichu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        initView();
    }

    private void initView() {
        img_back=findViewById(R.id.img_back);

        relative1=findViewById(R.id.relative1);
        relative11=findViewById(R.id.relative11);
        relative2=findViewById(R.id.relative2);
        relative22=findViewById(R.id.relative22);
        relative3=findViewById(R.id.relative3);
        relative33=findViewById(R.id.relative33);
        tv_tuichu=findViewById(R.id.tv_tuichu);

         img_back.setOnClickListener(this);
         relative1.setOnClickListener(this);
        relative11.setOnClickListener(this);
        relative2.setOnClickListener(this);
        relative22.setOnClickListener(this);
        relative3.setOnClickListener(this);
        relative33.setOnClickListener(this);
        tv_tuichu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.relative1:
                if(relative1.getVisibility()==View.VISIBLE){
                    relative1.setVisibility(View.GONE);
                    relative11.setVisibility(View.VISIBLE);
                }else{
                    relative1.setVisibility(View.VISIBLE);
                    relative11.setVisibility(View.GONE);
                }
                break;
            case R.id.relative2:
                if(relative2.getVisibility()==View.VISIBLE){
                    relative2.setVisibility(View.GONE);
                    relative22.setVisibility(View.VISIBLE);
                }else{
                    relative2.setVisibility(View.VISIBLE);
                    relative22.setVisibility(View.GONE);
                }
                break;
            case R.id.relative3:
                if(relative3.getVisibility()==View.VISIBLE){
                    relative3.setVisibility(View.GONE);
                    relative33.setVisibility(View.VISIBLE);
                }else{
                    relative3.setVisibility(View.VISIBLE);
                    relative33.setVisibility(View.GONE);
                }
                break;
            case R.id.relative11:
                if(relative11.getVisibility()==View.VISIBLE){
                    relative11.setVisibility(View.GONE);
                    relative1.setVisibility(View.VISIBLE);
                }else{
                    relative11.setVisibility(View.VISIBLE);
                    relative1.setVisibility(View.GONE);
                }
                break;
            case R.id.relative22:
                if(relative22.getVisibility()==View.VISIBLE){
                    relative22.setVisibility(View.GONE);
                    relative2.setVisibility(View.VISIBLE);
                }else{
                    relative22.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.GONE);
                }
                break;
            case R.id.relative33:
                if(relative33.getVisibility()==View.VISIBLE){
                    relative33.setVisibility(View.GONE);
                    relative3.setVisibility(View.VISIBLE);
                }else{
                    relative33.setVisibility(View.VISIBLE);
                    relative3.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_tuichu:
//                System.exit(0);
                //Toast.makeText(this,"推出");
                Intent intent=new Intent();
                intent.setAction("closeSetting");
                sendBroadcast(intent);
                Log.e("TAG","发送广播");
                finish();
                break;
        }
    }
}
