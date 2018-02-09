package fragment;


import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a123456.zhonggu.FrameActivity;
import com.example.a123456.zhonggu.R;

import java.util.Calendar;

import camera.CameraActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class newFragment extends Fragment implements View.OnClickListener{
    private ImageView img_paizhao;
    private EditText edit_num;
    private View view;
    private PopupWindow window;
    private View popView;
    private TextView tv_paizhao,tv_canle,tv_xiangce;
    private ImageView img_topleft,img_topright;
    private TextView tv_topcenter;
    private TextView tv_time;
    private EditText edt_licheng,edt_price;//里程，价格
    private TextView tv2_newFragment,tv1_newFragment,tv3_newFragment,tv4_newFragment,tv5_newFragment;
    private ImageView img_newfragment,img2_newfragment,img3_newfragment;
    private Button btn_commit;
    private RelativeLayout relative1_newFragment,relative2_newFragment,relative3_newFragment,relative4_newFragment,relative5_newFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("TAG","onCreate==");
        view=inflater.inflate(R.layout.fragment_new, container, false);
        initView();
        MyRegistReciver();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("TAG","onHiddenChanged");
        getSubStr(edt_licheng);
        getSubStr(edt_price);
    }

    private void initView() {
        img_topleft=view.findViewById(R.id.img_left);
        img_topright=view.findViewById(R.id.img_right);
        tv_topcenter=view.findViewById(R.id.tv_center);
        img_topleft.setVisibility(View.GONE);
        img_topright.setVisibility(View.GONE);
        //所有要填写的控件
        tv1_newFragment=view.findViewById(R.id.tv1_newFragment);//二级菜单
        tv2_newFragment=view.findViewById(R.id.tv2_newFragment);//一级菜单
        relative1_newFragment=view.findViewById(R.id.relative1_newFragment);
        relative2_newFragment=view.findViewById(R.id.relative2_newFragment);
        //一下三个控件，当vin码识别识别，需要手动填写
        tv3_newFragment=view.findViewById(R.id.tv3_newFragment);//品牌
        tv4_newFragment=view.findViewById(R.id.tv4_newFragment);//车系
        tv5_newFragment=view.findViewById(R.id.tv5_newFragment);//车型
        relative3_newFragment=view.findViewById(R.id.relative1_newFragment);
        relative4_newFragment=view.findViewById(R.id.relative1_newFragment);
        relative5_newFragment=view.findViewById(R.id.relative1_newFragment);
        tv_time=view.findViewById(R.id.tv_time);//日期

        edt_price=view.findViewById(R.id.edt_price);//价格
        edt_licheng=view.findViewById(R.id.edt_licheng);//里程
        //设置里程和价格的数据，小数点后为0的话不现实0
        getSubStr(edt_price);
        getSubStr(edt_licheng);
        img_newfragment=view.findViewById(R.id.img_newfragment);//左前45°
        img2_newfragment=view.findViewById(R.id.img2_newfragment);//正前
        img3_newfragment=view.findViewById(R.id.img3_newfragment);//正后

        img_paizhao=view.findViewById(R.id.img_paizhao);//vin拍照
        edit_num=view.findViewById(R.id.edt_vinnum);//vin码显示

        btn_commit=view.findViewById(R.id.btn_commit);//提交按钮

        img_paizhao.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        relative1_newFragment.setOnClickListener(this);
        relative2_newFragment.setOnClickListener(this);
        relative3_newFragment.setOnClickListener(this);
        relative4_newFragment.setOnClickListener(this);
        relative5_newFragment.setOnClickListener(this);

        edit_num.addTextChangedListener(new MyEditTextChangeListener(edit_num));
        edt_licheng.addTextChangedListener(new MyEditTextChangeListener(edt_licheng));
        edt_price.addTextChangedListener(new MyEditTextChangeListener(edt_price));

        img_newfragment.setOnClickListener(this);
        img2_newfragment.setOnClickListener(this);
        img3_newfragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_paizhao:
                getPopView();
                break;
            case R.id.tv_paizhao:
                Intent intent=new Intent(getContext(), CameraActivity.class);
                intent.putExtra("height","70");
                startActivity(intent);
                window.dismiss();
                break;
            case R.id.tv_xiangce:
                Intent intent1=new Intent(getContext(),CameraActivity.class);
                intent1.putExtra("height","300");
                startActivity(intent1);
                window.dismiss();
                break;
            case R.id.tv_canle:
                if(window!=null&&window.isShowing()){
                    window.dismiss();
                }
                break;
            case R.id.tv_time:
                showDate(tv_time);
                break;
            case R.id.relative1_newFragment:
                tv2_newFragment.setBackgroundResource(R.drawable.juxingnull);
                break;
            case R.id.relative2_newFragment:
                tv1_newFragment.setBackgroundResource(R.drawable.juxingnull);
                break;
            case R.id.relative3_newFragment:
                tv3_newFragment.setBackgroundResource(R.drawable.juxingnull);
                break;
            case R.id.relative4_newFragment:
                tv4_newFragment.setBackgroundResource(R.drawable.juxingnull);
                break;
            case R.id.relative5_newFragment:
                tv5_newFragment.setBackgroundResource(R.drawable.juxingnull);
                break;
            case R.id.btn_commit:
                IsNull(tv1_newFragment);
                IsNull(tv2_newFragment);
                IsNull(tv3_newFragment);
                IsNull(tv4_newFragment);
                IsNull(tv5_newFragment);
                IsNull(tv_time);
                IsNullEdit(edit_num);
                IsNull(edt_licheng);
                IsNull(edt_price);
                IsNullImg(img_newfragment);
                IsNullImg(img2_newfragment);
                break;
            case R.id.img_newfragment:
                img_newfragment.setBackgroundResource(0);
                break;
            case R.id.img2_newfragment:
                break;
            case R.id.img3_newfragment:
                break;
        }
    }
    //获取popwindow
    private void getPopView(){
        popView= View.inflate(getContext(),R.layout.popwiew,null);
        LinearLayout pop_linear=popView.findViewById(R.id.pop_linear);
        tv_paizhao=popView.findViewById(R.id.tv_paizhao);
        tv_xiangce=popView.findViewById(R.id.tv_xiangce);
        tv_canle=popView.findViewById(R.id.tv_canle);
        window=new PopupWindow(getContext());
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        pop_linear.measure(w, h);
        int pop_height = pop_linear.getMeasuredHeight();
        int pop_width = pop_linear.getMeasuredWidth();
        Log.e("TAG","测量h="+pop_height);
        int width=getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height=getActivity().getWindowManager().getDefaultDisplay().getHeight();
        window.setWidth(width/3);
        window.setHeight(pop_height);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.setContentView(popView);
        window.setAnimationStyle(R.style.animTranslate);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getActivity().getWindow().getAttributes();
                lp.alpha=1.0f;
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);
            }
        });
//        window.showAtLocation(img_circle, Gravity.BOTTOM,0,0);
        window.showAsDropDown(img_paizhao,0,0);
        WindowManager.LayoutParams lp=getActivity().getWindow().getAttributes();
        lp.alpha=0.3f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);
        tv_xiangce.setOnClickListener(this);
        tv_paizhao.setOnClickListener(this);
        tv_canle.setOnClickListener(this);
        Log.e("TAG","window=="+window.getWidth()+"height=="+window.getHeight());
    }

    MyBroadcastReceiver myBroadcastReceiver;
    //注册广播
    private void MyRegistReciver(){
        myBroadcastReceiver=new MyBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("vin");
        getActivity().registerReceiver(myBroadcastReceiver,intentFilter);
    }

    //显示日期
    private void showDate(final TextView Tv) {
        Calendar calend1 = Calendar.getInstance();
        calend1.setTimeInMillis(System.currentTimeMillis());
        int year = calend1.get(Calendar.YEAR);
        int month = calend1.get(Calendar.MONTH) + 1;
        int day = calend1.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog3 = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view,
                                          int year, int monthOfYear,
                                          int dayOfMonth) {
                        Tv.setText(year + "-" + (monthOfYear + 1)
                                + "-" + dayOfMonth );
                    }
                }, year, month, day);
        dialog3.show();
    }
    //判断小数点后面是否都为"0",截取字符串
    private void getSubStr(EditText editText){
        String editStr=editText.getText().toString();
        Log.e("TAG","editStr=="+editStr);
        if(editStr.contains(".")) {
            int i = editStr.indexOf(".");
            Log.e("TAG", "editStr.length()==." + editStr.length() + ".是第几位=" + i);
            String subStr = editStr.substring(i, editStr.length() - 1);
            int count = 0;
            for (int h = 0; h < subStr.length(); h++) {
                char item = subStr.charAt(h);
                if ((String.valueOf(item)).equals("0")) {
                    count++;
                }
            }
            if (count == subStr.length() - 1) {
                editText.setText(editStr.substring(0, i));
            }
        }
    }
    //接受广播退出APP
    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG","接收广播newFragment===="+intent.getStringExtra("vinnum"));
//            Intent intent1=new Intent();
//            intent1.setAction("close");
//            sendBroadcast(intent1);
        }
    }
    //点击提交判断是否有空的Textview
    private void IsNull(TextView Tv){
        if(Tv!=null) {
            String str = Tv.getText().toString();
            if (TextUtils.isEmpty(str)) {
                Tv.setBackgroundResource(R.drawable.rednull);
                Toast.makeText(getActivity(),"请填写完整，不可为空",Toast.LENGTH_SHORT).show();
            }
        }
    }
    //点击提交判断是否有空的Edittext
    private void IsNullEdit(EditText editText){
        if(editText!=null) {
            String str = editText.getText().toString();
            if (TextUtils.isEmpty(str)) {
                editText.setBackgroundResource(R.drawable.rednull);
                Toast.makeText(getActivity(),"请填写完整，不可为空",Toast.LENGTH_SHORT).show();
            }
        }
    }
    //点击提交判断图片是否为null
    private  void IsNullImg(ImageView imageView){
        if(imageView!=null) {
            Resources res = this.getResources();
            imageView.setDrawingCacheEnabled(true);
            imageView.getDrawingCache();
            Log.e("TAG","imageView.getResources()=="+imageView.getDrawable());
            Log.e("TAG","img=="+(imageView.getDrawable().toString() .equals( "android.graphics.drawable.BitmapDrawable@54097a6")) );
            if (imageView.getDrawable().toString() .equals( "android.graphics.drawable.BitmapDrawable@54097a6")
                    ||imageView.getDrawable().toString() .equals( "android.graphics.drawable.BitmapDrawable@9a02594")) {
                imageView.setBackgroundResource(R.drawable.rednull);
            }
        }
    }
   class MyEditTextChangeListener implements TextWatcher {
        EditText editText;
        public MyEditTextChangeListener(EditText editText){
            this.editText=editText;
        }
        /**
         * 编辑框的内容发生改变之前的回调方法
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.e("TAG","编辑框的内容发生改变之前的回调方法");
        }

        /**
         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
         * 我们可以在这里实时地 通过搜索匹配用户的输入
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.e("TAG","发生改变");
            editText.setBackgroundResource(R.drawable.juxingnull);
        }

        /**
         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
         */
        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

}
