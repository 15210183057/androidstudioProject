package Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment implements AdapterView.OnItemClickListener{

    private View view;
    private ImageView img_topleft,img_topright;
    private TextView tv_topcenter;

    RefreshLayout refreshLayout;
    ListView lv;
    private List<CarBean> list;
    private MyLvAdapter adapter;
    private int count;
    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_fragment3, container, false);
        img_topleft=view.findViewById(R.id.img_left);
        img_topright=view.findViewById(R.id.img_right);
        tv_topcenter=view.findViewById(R.id.tv_center);

        img_topleft.setVisibility(View.GONE);
        tv_topcenter.setText("已上传车源");
        img_topright.setVisibility(View.GONE);
        initView();
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
}
