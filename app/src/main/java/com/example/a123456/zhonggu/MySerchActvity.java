package com.example.a123456.zhonggu;
import base.BaseActivity;
import bean.CartMsgBean;
import bean.UserBean;
import jiekou.getInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import View.GetJsonUtils;
import utils.Mydialog;

public class MySerchActvity extends BaseActivity {
private SearchView search;
private List<String>list=new ArrayList<String>();
private List<String>findList=new ArrayList<String>();
private List<CartMsgBean>cartList=new ArrayList<CartMsgBean>();
private ListView listView;
private ArrayAdapter adapter,findAdapter;
Mydialog mydialog;
String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_serch_actvity);
        mydialog=new Mydialog(this,"正在获取车商信息请稍后");
        mydialog.show();
        str=getIntent().getStringExtra("f3");
        initView();
    }

    private void initView() {
//        setData();
        getData();
        search=findViewById(R.id.search);
        listView=findViewById(R.id.lv);

        search.setIconifiedByDefault(false);

//        search.setSubmitButtonEnabled();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e("TAG","s=="+s);
                if(TextUtils.isEmpty(s)){
                    listView.setAdapter(adapter);
                    Log.e("TAG","请输入查找内容");
                    Toast.makeText(MySerchActvity.this, "请输入查找内容！", Toast.LENGTH_SHORT).show();
                }else{
                    findList.clear();
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).equals(s)){
                            findList.add(list.get(i));
                            break;
                        }
                    }
                    if(findList.size()==0){
                        Log.e("TAG","查找的商品不在列表中");
                        Toast.makeText(MySerchActvity.this, "查找的商品不在列表中", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e("TAG","查找成功");
                        Toast.makeText(MySerchActvity.this, "查找成功", Toast.LENGTH_SHORT).show();
                        adapter=new ArrayAdapter(MySerchActvity.this,R.layout.item,R.id.tvitem_xiala,findList);
                        listView.setAdapter(findAdapter);
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.e("TAG","onQueryTextChange=="+s);
                if(TextUtils.isEmpty(s)){
                    adapter=new ArrayAdapter(MySerchActvity.this,R.layout.item,R.id.tvitem_xiala,list);
//                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }else{
                    findList.clear();
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).contains(s)){
                            findList.add(list.get(i));
                            Log.e("TAG","onQueryTextChangelist.get(i)=="+list.get(i));
                        }
                    }
                    adapter=new ArrayAdapter(MySerchActvity.this,R.layout.item,R.id.tvitem_xiala,findList);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("TAG","i=="+list.get(i).toString());
                Intent intent=new Intent();
                if(!TextUtils.isEmpty(str)&&str.equals("f3")){
                    intent.setAction("f3");
                }else {
                    intent.setAction("quyu");
                }
                intent.putExtra("name",list.get(i).toString());
                intent.putExtra("ID",cartList.get(i).cartMsgId.toString());
                sendBroadcast(intent);
                finish();
            }
        });
    }
    private void getData(){
        //：json=1&pagesize=10&where=blu=1 and groupid in(5,3,2) and status = 1
        RequestParams params=new RequestParams(getInterface.getA);
        params.addBodyParameter("json","1");
        params.addBodyParameter("pagesize","100");
        params.addBodyParameter("where","groupid in(2) and status=1");
        Log.e("TAG","params=="+params);
        Log.e("TAG","车商-=="+params.getParams("where"));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","reulst=="+result);
                mydialog.dismiss();
                cartList= GetJsonUtils.getQuYu(MySerchActvity.this,result);
                Log.e("TAG","list=="+list.size());
                list.clear();
                for(int i=0;i<cartList.size();i++){
                    list.add(cartList.get(i).cartmsgname);
                }
                if(list.size()>0) {
                    adapter = new ArrayAdapter(MySerchActvity.this, R.layout.item, R.id.tvitem_xiala, list);
                    listView.setAdapter(adapter);
                }
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
    }
}
