package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a123456.zhonggu.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import adapter.MyLvAdapter;
import bean.CarBean;

public class Fragment1 extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{
    private View view;
    private ImageView img_topleft,img_topright;
    private TextView tv_topcenter;
    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    ListView lv;
    private List<CarBean> list;
    private MyLvAdapter adapter;
    private int count;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_fragment1, container, false);
         img_topleft=view.findViewById(R.id.img_left);
         img_topright=view.findViewById(R.id.img_right);
         tv_topcenter=view.findViewById(R.id.tv_center);

         img_topleft.setVisibility(View.GONE);
         tv_topcenter.setText("待补充车源");
         img_topright.setImageResource(R.mipmap.saoyisao);
         initView();

         img_topright.setOnClickListener(this);
         return view;
    }

    private void initView() {
        setDate();
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);

        lv=view.findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        adapter=new MyLvAdapter(list,getActivity());
        lv.setAdapter(adapter);
//        refreshLayout.setEnableAutoLoadmore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                Log.e("TAG","上拉刷新");
//                for(int i=count;i<=count+20;i++){
//                    list.add("第+"+i+"条数据");
//                    adapter.notifyDataSetChanged();
//                    count=i;
//                }
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                Log.e("TAG","上拉加载");
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CarBean carBean=new CarBean();
                        for(int i=count;i<=count+20;i++){
                            carBean.tv_name="大众---"+i;
                            carBean.tv_company_name="中古测试---"+i;
                            carBean.tv_num1="12321sdfsfdsfsfs---"+i;
                            carBean.tv_num2="进123rew---"+i;
                            list.add(carBean);
                        }
                        count=list.size();
                        adapter.notifyDataSetChanged();
                    }
                },3000);

                refreshlayout.finishLoadmore(2000);
            }
        });
    }
    //设置数据源
    private void setDate(){
        list=new ArrayList<CarBean>();
        for(int i=0;i<20;i++){
            CarBean carBean=new CarBean();
            carBean.tv_name="大众---"+i;
            carBean.tv_company_name="中古测试---"+i;
            carBean.tv_num1="12321sdfsfdsfsfs---"+i;
            carBean.tv_num2="进123rew---"+i;
            list.add(carBean);
            count=i;
        }
}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),"点击第+"+i+"条数据",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_right:
                Toast.makeText(getContext(),"扫描二维码",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //设置刷新
//    private void setPullRefresher(){
////        //设置 Header 为 MaterialHeader
////        refreshLayout.setRefreshHeader(new MaterialHeader(this));
////        //设置 Footer 为 经典样式
////        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
//
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
//
//                //模拟网络请求到的数据
//                ArrayList<ItemBean> newList = new ArrayList<ItemBean>();
//                for (int i=0;i<20;i++){
//                    newList.add(new ItemBean(
//                            R.mipmap.ic_launcher,
//                            "newTitle"+i,
//                            System.currentTimeMillis()+""
//                    ));
//                }
//                myAdapter.refresh(newList);
//                refreshlayout.finishRefresh(2000/*,false*/);
//                //不传时间则立即停止刷新    传入false表示刷新失败
//            }
//        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//
//                //模拟网络请求到的数据
//                ArrayList<ItemBean> newList = new ArrayList<ItemBean>();
//                for (int i=0;i<20;i++){
//                    newList.add(new ItemBean(
//                            R.mipmap.ic_launcher,
//                            "addTitle"+i,
//                            System.currentTimeMillis()+""
//                    ));
//                }
//                myAdapter.add(newList);
//                //在这里执行下拉加载时的具体操作(网络请求、更新UI等)
//                refreshlayout.finishLoadmore(2000/*,false*/);//不传时间则立即停止刷新    传入false表示加载失败
//            }
//        });
//    }
}