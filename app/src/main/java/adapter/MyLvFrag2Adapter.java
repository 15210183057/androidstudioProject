package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a123456.zhonggu.R;

import java.util.List;

import bean.CarBean;

public class MyLvFrag2Adapter extends BaseAdapter {
    private List<CarBean> list;
    private Context ctx;
    public SelectCallBack call;
    public MyLvFrag2Adapter (List<CarBean>list,Context ctx){
        this.ctx=ctx;
        this.list=list;
    }
    public void setCall(SelectCallBack call){
        this.call=call;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
       ViewHolder holder;
        if(view==null){
            view= View.inflate(ctx, R.layout.item_mylv,null);
            holder=new ViewHolder();
            holder.tv_name=view.findViewById(R.id.tv_name_mylvitem);
            holder.tv_num1=view.findViewById(R.id.tv_num_mylvitem);
            holder.tv_company=view.findViewById(R.id.tv_company_mylvitem);
            holder.tv_num2=view.findViewById(R.id.tv_num2_mylvitem);
            holder.img_select=view.findViewById(R.id.img_item_mylv_select);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tv_name.setText(list.get(i).tv_name);
        holder.tv_company.setText(list.get(i).tv_company_name);
        holder.tv_num1.setText(list.get(i).tv_num1);
        holder.tv_num2.setText(list.get(i).tv_num2);
        if(list.get(i).Flag){
            holder.img_select.setImageResource(R.mipmap.icon02);
        }else{
            holder.img_select.setImageResource(R.mipmap.icon01);
        }
        holder.img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call.call(i);
            }
        });
        return view;
    }
    public class ViewHolder{
        TextView tv_name,tv_company,tv_num1,tv_num2;
        ImageView img_select;
    }
    public interface SelectCallBack{
      void  call(int i);
    }
}
