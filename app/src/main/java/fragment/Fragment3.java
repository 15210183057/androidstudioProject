package fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import View.GetJsonUtils;
import com.example.a123456.zhonggu.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.MyLvAdapter;
import adapter.MyLvAdapter3;
import bean.BUCartListBeanNUm;
import bean.BuCartListBean;
import bean.CarBean;
import bean.UserBean;
import jiekou.getInterface;
import utils.Mydialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment implements AdapterView.OnItemClickListener{

    private View view;
    private ImageView img_topleft,img_topright;
    private TextView tv_topcenter;

    RefreshLayout refreshLayout;
    ListView lv;
    private List<BuCartListBean> list;
    private MyLvAdapter3 adapter;
    private int count;
    private int i=1;//默认加载第一页数据
    Mydialog mydialog;
    public Fragment3() {
        // Required empty public constructor
    }
    BroadcastReceiver my;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'
        my=new MyBroadcastReceiver();
        //注册广播
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("delete");
        getActivity().registerReceiver(my,intentFilter);
        list=new ArrayList<BuCartListBean>();

        getBuCartList(i);
        view=inflater.inflate(R.layout.fragment_fragment3, container, false);
        img_topleft=view.findViewById(R.id.img_left);
        img_topright=view.findViewById(R.id.img_right);
        tv_topcenter=view.findViewById(R.id.tv_center);

        img_topleft.setVisibility(View.GONE);
        tv_topcenter.setText("已上传车源");
        img_topright.setVisibility(View.GONE);
        initView();
        mydialog=new Mydialog(getContext(),"正在加载请稍后.....");
        mydialog.show();
        Log.e("TAG","标题；"+tv_topcenter.getText().toString());
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(my);
    }

    private void initView() {
        setDate();
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);

        lv=view.findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        adapter=new MyLvAdapter3(list,getActivity());
        lv.setAdapter(adapter);
//        refreshLayout.setEnableAutoLoadmore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                Log.e("TAG","上拉刷新");
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i>1){
                            i--;
                            getBuCartList(i);
                        }
                    }
                },3000);

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                Log.e("TAG","上拉加载");
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        CarBean carBean=new CarBean();
//                        for(int i=count;i<=count+20;i++){
//                            carBean.tv_name="大众---"+i;
//                            carBean.tv_company_name="中古测试---"+i;
//                            carBean.tv_num1="12321sdfsfdsfsfs---"+i;
//                            carBean.tv_num2="进123rew---"+i;
//                            list.add(carBean);
//                        }
//                        count=list.size();
                        i++;
                        getBuCartList(i);
//                        if(!TextUtils.isEmpty(BUCartListBeanNUm.last_page)&&i<Integer.parseInt(BUCartListBeanNUm.last_page)) {
//                            i++;
//                            getBuCartList(i);
//                        }else{
//                            Toast.makeText(getContext(),"数据加载完毕",Toast.LENGTH_SHORT).show();
//                        }
                    }
                },3000);

                refreshlayout.finishLoadmore(2000);
            }
        });
    }
    //设置数据源
    private void setDate(){
//        list=new ArrayList<CarBean>();
//        for(int i=0;i<20;i++){
//            CarBean carBean=new CarBean();
//            carBean.tv_name="大众---"+i;
//            carBean.tv_company_name="中古测试---"+i;
//            carBean.tv_num1="12321sdfsfdsfsfs---"+i;
//            carBean.tv_num2="进123rew---"+i;
//            list.add(carBean);
//            count=i;
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),"点击第+"+i+"条数据",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.setAction("new");
        intent.putExtra("Flag","true");
        intent.putExtra("vinnum",list.get(i).vin);
        intent.putExtra("time",list.get(i).time);
        intent.putExtra("quyu",list.get(i).name);
        intent.putExtra("cartmodel",list.get(i).brandName+list.get(i).seriseName+list.get(i).modelName);
        intent.putExtra("licheng",list.get(i).mileage);
        Log.e("TAG","家啊风格==="+list.get(i).price);
        intent.putExtra("price",list.get(i).price);

        intent.putExtra("quyuID",list.get(i).quyuID);
        intent.putExtra("brandID",list.get(i).brandid);
        intent.putExtra("seriseID",list.get(i).seriseID);
        intent.putExtra("modelID",list.get(i).modelID);
        intent.putExtra("modelName",list.get(i).modelName);
        intent.putExtra("seriseName",list.get(i).seriseName);
        intent.putExtra("brandName",list.get(i).brandName);
        intent.putExtra("img1",list.get(i).img1);
        intent.putExtra("img2",list.get(i).img2);
        intent.putExtra("img3",list.get(i).img3);
        intent.putExtra("ID",list.get(i).ListID);
        getActivity().sendBroadcast(intent);
    }
    private void getBuCartList(int current_page){
        RequestParams requestParams=new RequestParams(getInterface.getList);
        requestParams.addBodyParameter("userid",UserBean.id);
        requestParams.addBodyParameter("page",current_page+"");
        Log.e("TAG","requestParams接口拼接地址为=="+requestParams+"");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","resulr=="+result);
                mydialog.dismiss();
                List<BuCartListBean>listBeans=new ArrayList<BuCartListBean>();
                listBeans= GetJsonUtils.getCartList(getActivity(),result);
//                listBeans= GetJsonUtils.getBuCartList(getActivity(),result);
                list.clear();
                list.addAll(listBeans);
                if (list!=null) {
                    adapter = new MyLvAdapter3(list, getActivity());
                    lv.setAdapter(adapter);
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
                Log.e("TAG","list=="+list.size());
                tv_topcenter.setText(list.size()+"");//设置title
                Log.e("TAG","title=="+BUCartListBeanNUm.total);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            getBuCartList(i);
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            getBuCartList(1);
        }
    }
}