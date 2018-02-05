package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a123456.zhonggu.R;

import java.util.List;

import bean.BuCartListBean;
import bean.CarBean;

public class MyLvAdapter3 extends BaseAdapter{
    private List<CarBean> list;
    private Context ctx;
    public MyLvAdapter3(List<CarBean>list, Context ctx){
        this.ctx=ctx;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view= View.inflate(ctx, R.layout.item_mylv2,null);
           holder=new ViewHolder();
            holder.tv_name=view.findViewById(R.id.tv_name_mylvitem);
            holder.tv_num1=view.findViewById(R.id.tv_num_mylvitem);
            holder.tv_company=view.findViewById(R.id.tv_company_mylvitem);
            holder.tv_num2=view.findViewById(R.id.tv_num2_mylvitem);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tv_name.setText(list.get(i).tv_name);
        holder.tv_company.setText(list.get(i).tv_company_name);
        holder.tv_num1.setText(list.get(i).tv_num1);
        holder.tv_num2.setText(list.get(i).tv_num2);
        return view;
    }
    public class ViewHolder{
        TextView tv_name,tv_company,tv_num1,tv_num2;
    }
}
