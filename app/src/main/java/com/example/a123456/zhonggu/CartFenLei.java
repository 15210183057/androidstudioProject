package com.example.a123456.zhonggu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.CartFenLeiAdapter;
import base.BaseActivity;
import bean.CartFenleiBean;
import utils.MyModelDialog;
import utils.Mydialog;

public class CartFenLei extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ListView lv;
    private Button btn_okmodel;
    List<CartFenleiBean>list=new ArrayList<CartFenleiBean>();
    CartFenLeiAdapter  adapter;
    RelativeLayout relative3_newFragment;
    TextView tv3_newFragment;
    String CartFenleiID;
    Mydialog mydialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_fen_lei);
        initView();
    }

    private void initView() {
        mydialog=new Mydialog(this,"正在获取车辆分类信息，请稍等....");
        mydialog.show();
        lv=findViewById(R.id.lv1);
        btn_okmodel=findViewById(R.id.btn_okmodel);
        relative3_newFragment=findViewById(R.id.relative3_newFragment);
        tv3_newFragment=findViewById(R.id.tv3_newFragment);

        btn_okmodel.setOnClickListener(this);
        relative3_newFragment.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        getData();
    }
    private void getData(){
        CartFenleiBean cartFenleiBean=new CartFenleiBean();
        cartFenleiBean.fenleiName="1";
        cartFenleiBean.fenleiName_id="1";
        CartFenleiBean cartFenleiBean2=new CartFenleiBean();
        cartFenleiBean2.fenleiName="2";
        cartFenleiBean2.fenleiName_id="2";
        CartFenleiBean cartFenleiBean3=new CartFenleiBean();
        cartFenleiBean3.fenleiName="3";
        cartFenleiBean3.fenleiName_id="3";
        list.add(cartFenleiBean);
        list.add(cartFenleiBean2);
        list.add(cartFenleiBean3);
        adapter=new CartFenLeiAdapter(this,list);
        lv.setAdapter(adapter);
        RequestParams params=new RequestParams("");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.e("TAG","获取车辆分类--"+result);
                mydialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if(!TextUtils.isEmpty(ex.getMessage().toString())){
                    mydialog.dismiss();
                    Toast.makeText(CartFenLei.this,"获取车辆分类信息失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_okmodel){
            //发送广播
            Intent intent= new Intent();
            intent.setAction("cartfenlei");
            intent.putExtra("fenleiname","name");
            intent.putExtra("fenleiID",CartFenleiID);
            sendBroadcast(intent);
            finish();
        }
        if(view.getId()==R.id.relative3_newFragment){
            btn_okmodel.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.lv1){
            tv3_newFragment.setText(list.get(i).fenleiName);
            CartFenleiID=list.get(i).fenleiName_id.toString();
            btn_okmodel.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
        }
    }
}
