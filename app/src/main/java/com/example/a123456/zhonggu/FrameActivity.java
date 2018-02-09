package com.example.a123456.zhonggu;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import fragment.Fragment1;
import fragment.Fragment2;
import fragment.Fragment3;
import fragment.Fragment4;
import base.BaseActivity;
import fragment.newFragment;

public class FrameActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout TopView_Bottem1,TopView_Bottem2,TopView_Bottem3,TopView_Bottem4;
    private LinearLayout TopView_FragmentGroup;
    private ImageView img1,img2,img3,img4;
    private TextView tv1,tv2,tv3,tv4;
    private Fragment fragment1, fragment2,fragment3,fragment4,newfragment;
    private FragmentManager fragmentManager;
    FragmentTransaction ft;
    MyBroadcastReceiver myBroadcastReceiver;
    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_frame);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        fragmentManager=getSupportFragmentManager();

        initView();
        setOnClick();
        MyRegistReciver();
        setPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG","这里走吗？？？？？？？？？？？？？？");
    }

    private void setOnClick() {
        TopView_Bottem1.setOnClickListener(this);
        TopView_Bottem2.setOnClickListener(this);
        TopView_Bottem3.setOnClickListener(this);
        TopView_Bottem4.setOnClickListener(this);
    }

    private void initView() {
        TopView_FragmentGroup=findViewById(R.id.TopView_FragmentGroup);
        TopView_Bottem1=findViewById(R.id.TopView_Bottem1);
        TopView_Bottem2=findViewById(R.id.TopView_Bottem2);
        TopView_Bottem3=findViewById(R.id.TopView_Bottem3);
        TopView_Bottem4=findViewById(R.id.TopView_Bottem4);
        img1=findViewById(R.id.Img1);
        img2=findViewById(R.id.Img2);
        img3=findViewById(R.id.Img3);
        img4=findViewById(R.id.Img4);
        tv1=findViewById(R.id.Tv1);
        tv2=findViewById(R.id.Tv2);
        tv3=findViewById(R.id.Tv3);
        tv4=findViewById(R.id.Tv4);

       // 默认显示第一个fragment
      FragmentTransaction   ft=fragmentManager.beginTransaction();
      tv1.setTextColor(Color.WHITE);
      img1.setImageResource(R.mipmap.banyuan_c);
//        if(fragment1==null){
//            fragment1=new Fragment1();
//            ft.add(R.id.TopView_FragmentGroup,fragment1);
//        }else{
//            ft.show(fragment1);
//        }
        if(newfragment==null){
            newfragment=new newFragment();
            ft.add(R.id.TopView_FragmentGroup,newfragment);
        }else{
            ft.show(newfragment);
        }
        ft.commit();

    }

    @Override
    public void onClick(View view) {
        HideFragement();
        clearBottem();
        ft=fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.TopView_Bottem1:
                tv1.setTextColor(Color.WHITE);
                img1.setImageResource(R.mipmap.banyuan_c);
//                if(fragment1==null){
//                    fragment1=new Fragment1();
//                    ft.add(R.id.TopView_FragmentGroup,fragment1);
//                }else{
//                    ft.show(fragment1);
//                }
                if(newfragment==null){
                    newfragment=new newFragment();
                    ft.add(R.id.TopView_FragmentGroup,newfragment);
                }else{
                    ft.show(newfragment);
                }
                break;
            case R.id.TopView_Bottem2:
                tv2.setTextColor(Color.WHITE);
                img2.setImageResource(R.mipmap.shangchuan_c);
                if(fragment2==null){
                    fragment2=new Fragment2();
                    ft.add(R.id.TopView_FragmentGroup,fragment2);
                }else{
                    ft.show(fragment2);
                }
                break;
            case R.id.TopView_Bottem3:
                tv3.setTextColor(Color.WHITE);
                img3.setImageResource(R.mipmap.yshangchua_c);
                if(fragment3==null){
                    fragment3=new Fragment3();
                    ft.add(R.id.TopView_FragmentGroup,fragment3);
                }else{
                    ft.show(fragment3);
                }
                break;
            case R.id.TopView_Bottem4:
                tv4.setTextColor(Color.WHITE);
                img4.setImageResource(R.mipmap.wode_c);
                if(fragment4==null){
                    fragment4=new Fragment4();
                    ft.add(R.id.TopView_FragmentGroup,fragment4);
                }else{
                    ft.show(fragment4);
                }
                break;
        }
        ft.commit();

    }
    //隐藏所有Fragement
    private void HideFragement(){
        ft=fragmentManager.beginTransaction();
        if(fragment1!=null){
            ft.hide(fragment1);
        }
        if(newfragment!=null){
            ft.hide(newfragment);
        }
        if(fragment2!=null){
            ft.hide(fragment2);
        }
        if(fragment3!=null){
            ft.hide(fragment3);
        }
        if(fragment4!=null){
            ft.hide(fragment4);
        }
        ft.commit();
    }
    //所有底部图片以及textview编程灰色
    private void clearBottem(){
        img1.setImageResource(R.mipmap.banyuan);
        img2.setImageResource(R.mipmap.shangchuan);
        img3.setImageResource(R.mipmap.yshangchuan);
        img4.setImageResource(R.mipmap.wode);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
    }
    //注册广播
    private void MyRegistReciver(){
      myBroadcastReceiver=new MyBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("closeSetting");
        this.registerReceiver(myBroadcastReceiver,intentFilter);
    }
    //接受广播退出APP
    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG","接收广播11111");
//            Intent intent1=new Intent();
//            intent1.setAction("close");
//            sendBroadcast(intent1);
            Log.e("TAG","发送广播111");
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    /**
     * 第二种办法
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(FrameActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finish();
                Log.e("TAG","这里走了吗");
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    static final String[] PERMISSION = new String[]{
            Manifest.permission.READ_CONTACTS,// 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
            Manifest.permission.WRITE_CALL_LOG,        //读取设备信息
            Manifest.permission.CAMERA
    };
    /**
     * 设置Android6.0的权限申请
     */
    private void setPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //Android 6.0申请权限
            ActivityCompat.requestPermissions(this,PERMISSION,1);
        }else{
        }
    }
}
