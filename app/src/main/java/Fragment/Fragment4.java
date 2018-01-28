package Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a123456.zhonggu.R;
import com.example.a123456.zhonggu.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment implements View.OnClickListener{

    private View view;
    private ImageView img_topleft,img_topright;
    private TextView tv_topcenter,tv_all_num,tv_yue_num,tv_bucong_num,tv_shangchuan_num;
    public Fragment4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_fragment4, container, false);
        img_topleft=view.findViewById(R.id.img_left);
        img_topright=view.findViewById(R.id.img_right);
        tv_topcenter=view.findViewById(R.id.tv_center);

        img_topleft.setVisibility(View.GONE);
        tv_topcenter.setText("我的");
        img_topright.setImageResource(R.mipmap.sz);

        img_topright.setOnClickListener(this);
        initView();
        return view;
    }

    private void initView() {
        tv_all_num=view.findViewById(R.id.tv_all_num);
        tv_bucong_num=view.findViewById(R.id.tv_bucong_num);
        tv_shangchuan_num=view.findViewById(R.id.tv_shangchuan_num);
        tv_yue_num=view.findViewById(R.id.tv_yue_num);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_right:
                Intent intent=new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
