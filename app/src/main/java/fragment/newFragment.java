package fragment;


import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a123456.zhonggu.CartModelActivity;
import com.example.a123456.zhonggu.MySerchActvity;
import com.example.a123456.zhonggu.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.BrandBean;
import bean.MyNewUpdate;
import bean.UserBean;
import bean.ZHFBean;
import bean.ZQBean;
import bean.ZQFBean;
import camera.CameraActivity;
import camera.FileUtil;
import jiekou.getInterface;
import utils.MySuccess;
import utils.Mydialog;
import View.GetJsonUtils;
import View.CommonPopupWindow;
/**
 * A simple {@link Fragment} subclass.
 */
public class newFragment extends Fragment implements View.OnClickListener{
    private ImageView img_paizhao;
    private EditText edit_num;
    private View view;
    private PopupWindow window;
    private View popView,BrandPopView,SerisePopView;
    private TextView tv_paizhao,tv_canle,tv_xiangce;
    private ImageView img_topleft,img_topright;
    private TextView tv_topcenter;
    private TextView tv_time;//注册日期
    private EditText edt_licheng,edt_price;//里程，价格
    private ImageView img_newfragment,img2_newfragment,img3_newfragment;
    private Button btn_commit;
    private LinearLayout linear3_newfragment;
    private ArrayAdapter arrayAdapter;
    private LinearLayout linear_celiang;
    private String serise_id,model_id;
    private TextView tv_quyue;
    CommonPopupWindow window2;
    private TextView tv_cartmodel;
    List imgListPath=new ArrayList();
    Mydialog mydialog;
    String zqfPath,zqPath,zhfPath;
    String quyuID,brandid,seriesid,cartName;//商家信息ID,品牌ID，车系ID,车型
    MySuccess mySuccess;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("TAG","onCreate==");
        view=inflater.inflate(R.layout.fragment_new, container, false);
        initView();
        MyRegistReciver();
        mydialog=new Mydialog(getContext(),"正在上传.....");
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            edt_licheng.setText("");
            edit_num.setText("");
            edt_price.setText("");
            tv_quyue.setText("请选择车商信息");
            tv_time.setText("请选择日期");
            tv_cartmodel.setText("请选择品牌，车系和车型");
            edit_num.setFocusableInTouchMode(true);
            edit_num.setFocusable(true);
            edit_num.requestFocus();
        }
        getSubStr(edt_licheng);
        getSubStr(edt_price);
    }

    private void initView() {
        mySuccess=new MySuccess(getContext());
        img_topleft=view.findViewById(R.id.img_left);
        img_topright=view.findViewById(R.id.img_right);
        tv_topcenter=view.findViewById(R.id.tv_center);
        tv_topcenter.setText("待补充车源");
        img_topleft.setVisibility(View.GONE);
        img_topright.setVisibility(View.GONE);
        linear_celiang=view.findViewById(R.id.linear_celiang);
        //所有要填写的控件
        //一下三个控件，当vin码识别识别，需要手动填写
        linear3_newfragment=view.findViewById(R.id.linear3_newfragment);
        tv_cartmodel=view.findViewById(R.id.tv_cartmodel);
        tv_time=view.findViewById(R.id.tv_time);//日期

        edt_price=view.findViewById(R.id.edt_price);//价格
        edt_licheng=view.findViewById(R.id.edt_licheng);//里程
        tv_quyue=view.findViewById(R.id.tv_quyue);
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

        edit_num.addTextChangedListener(new MyEditTextChangeListener(edit_num));
        edt_licheng.addTextChangedListener(new MyEditTextChangeListener(edt_licheng));
        edt_price.addTextChangedListener(new MyEditTextChangeListener(edt_price));

        img_newfragment.setOnClickListener(this);
        img2_newfragment.setOnClickListener(this);
        img3_newfragment.setOnClickListener(this);
        tv_cartmodel.setOnClickListener(this);
        tv_quyue.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_quyue:
                Intent intentS=new Intent(getContext(), MySerchActvity.class);
                startActivity(intentS);
                break;
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
            case R.id.btn_commit:
                if (TextUtils.isEmpty(tv_quyue.getText().toString())||tv_quyue.getText().toString().trim().equals("请选择车商信息")){
                    tv_quyue.setBackgroundResource(R.drawable.rednull);
                    Toast.makeText(getContext(),"车商信息不能为空",Toast.LENGTH_LONG).show();
                }else if (!IsNullEdit(edit_num)){
                    Toast.makeText(getContext(),"VIN码不能为空",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(tv_cartmodel.getText().toString())||tv_cartmodel.getText().toString().trim().equals("请选择品牌，车系和车型")){
                    tv_cartmodel.setBackgroundResource(R.drawable.rednull);
                    Toast.makeText(getContext(),"品牌，车系，车型不能为空",Toast.LENGTH_LONG).show();
                }else if(!IsNullEdit(edt_licheng)){
                    Toast.makeText(getContext(),"里程不能为空",Toast.LENGTH_LONG).show();
                }
                else if(!IsNullEdit(edt_price)){
                    Toast.makeText(getContext(),"价格不能为空",Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(tv_time.getText().toString())||tv_time.getText().toString().trim().equals("请选择日期")) {
                    tv_time.setBackgroundResource(R.drawable.rednull);
                    Toast.makeText(getContext(),"注册日期不能为空",Toast.LENGTH_LONG).show();
                }
                else if (img_newfragment.getDrawable().getCurrent().getConstantState()==getResources().getDrawable(R.drawable.zq45d).getConstantState()){
                    img_newfragment.setImageDrawable(getActivity().getDrawable(R.drawable.rednull));
                    Toast.makeText(getContext(),"左前45°图片不能为空",Toast.LENGTH_LONG).show();
                }
                else if (img2_newfragment.getDrawable().getCurrent().getConstantState()==getResources().getDrawable(R.drawable.zqf).getConstantState()){
                    img2_newfragment.setImageDrawable(getActivity().getDrawable(R.drawable.rednull));
                    Toast.makeText(getContext(),"正前图片不能为空",Toast.LENGTH_LONG).show();
                }
                 else if (img3_newfragment.getDrawable().getCurrent().getConstantState()==getResources().getDrawable(R.drawable.zhf).getConstantState()){
                    img3_newfragment.setImageDrawable(getActivity().getDrawable(R.drawable.rednull));
                    Toast.makeText(getContext(),"正后图片不能为空",Toast.LENGTH_LONG).show();
                }
                else{
                    mydialog.show();
                    updateImag(zqfPath);
                    updateImag(zqPath);
                    updateImag(zhfPath);
                }
                break;
            case R.id.img_newfragment:
                img_newfragment.setBackgroundResource(0);
                Intent intent3=new Intent(getContext(),CameraActivity.class);
                intent3.putExtra("name","zuoqian");
                intent3.putExtra("height","350");
                startActivity(intent3);
                break;
            case R.id.img2_newfragment:
                img2_newfragment.setBackgroundResource(0);
                Intent intent4=new Intent(getContext(),CameraActivity.class);
                intent4.putExtra("name","zhengqian");
                intent4.putExtra("height","350");
                startActivity(intent4);
                break;
            case R.id.img3_newfragment:
                img3_newfragment.setBackgroundResource(0);
                Intent intent5=new Intent(getContext(),CameraActivity.class);
                intent5.putExtra("name","zhenghou");
                intent5.putExtra("height","350");
                startActivity(intent5);
                break;
            case R.id.tv_cartmodel:
                Intent intent6=new Intent(getContext(), CartModelActivity.class);
                startActivity(intent6);
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
        intentFilter.addAction("quyu");
        intentFilter.addAction("cartmodel");
        intentFilter.addAction("update");
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
                        Tv.setBackgroundResource(R.drawable.juxingnull);
                    }
                }, year, month, day);
        dialog3.show();
    }
    //判断小数点后面是否都为"0",截取字符串
    private void getSubStr(EditText editText){
        String editStr=editText.getText().toString();

        if(editStr.contains(".")) {
            if(editStr.length()>5){
                editStr=editStr.substring(0,5);
             }
                int i = editStr.indexOf(".");
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
                }else{
                    editText.setText(editStr);
                }
            }
    }
    //接受广播退出APP
    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG","接收广播newFragment===="+intent.getStringExtra("path"));
            if(intent.getAction().equals("update")){
                tv_quyue.setBackgroundResource(R.drawable.juxingnull);
                tv_cartmodel.setBackgroundResource(R.drawable.juxingnull);
                tv_time.setBackgroundResource(R.drawable.juxingnull);
                img_newfragment.setBackgroundResource(R.drawable.zq45d);
                img2_newfragment.setBackgroundResource(R.drawable.zqf);
                img3_newfragment.setBackgroundResource(R.drawable.zhf);
                MyNewUpdate myNewUpdate=new MyNewUpdate();
                edit_num.setText(MyNewUpdate.vinnum);
                edit_num.setFocusableInTouchMode(false);
                edit_num.setFocusable(false);
                Log.e("TAG","MyNewUpdate.quyu=="+MyNewUpdate.quyu);
                tv_quyue.setText(MyNewUpdate.quyu);
//                edt_licheng.setText(intent.getStringExtra("licheng"));
//                edt_price.setText(myNewUpdate.);
                tv_cartmodel.setText(MyNewUpdate.cartmodel);
                tv_time.setText(MyNewUpdate.time);
                edt_licheng.setText(MyNewUpdate.licheng);
                edt_price.setText(MyNewUpdate.price);
//                img_newfragment.setBackgroundResource(0);
            }
            else if(intent.getAction().equals("quyu")){
                String name=intent.getStringExtra("name");
                quyuID=intent.getStringExtra("ID");
                tv_quyue.setText(name);
                tv_quyue.setBackgroundResource(R.drawable.juxingnull);
            }else if(intent.getAction().equals("vin")){
                String name = intent.getStringExtra("name");
                if (!TextUtils.isEmpty(name)) {
                    String path = intent.getStringExtra("path");
                    imgListPath.add(path);
                    FileUtil fileUtil = new FileUtil(context);
                    if (name.equals("zhengqian")) {
                        zqPath=path;
                        img2_newfragment.setImageBitmap(fileUtil.readBitmap(path));
                    } else if (name.equals("zuoqian")) {
                        zqfPath=path;
                        img_newfragment.setImageBitmap(fileUtil.readBitmap(path));
                    } else if (name.equals("zhenghou")) {
                        zhfPath=path;
                        img3_newfragment.setImageBitmap(fileUtil.readBitmap(path));
                    }
                } else {
                    //上传vin码返回
                    Log.e("TAG", "广播接受上传vin码返回====" + intent.getStringExtra("vinnum"));
                    String str = intent.getStringExtra("vinnum");
                    if (TextUtils.isEmpty(str)) {
                        linear3_newfragment.setVisibility(View.GONE);
                    } else {
                        String vinStr = intent.getStringExtra("vinnum");
                        if (TextUtils.isEmpty(vinStr)) {
                            linear3_newfragment.setVisibility(View.VISIBLE);
                        } else {
                            linear3_newfragment.setVisibility(View.GONE);
                            edit_num.setText(vinStr);
                            edt_licheng.setText(intent.getStringExtra("licheng"));
                            edt_price.setText(intent.getStringExtra("price"));
                            tv_time.setText(intent.getStringExtra("data"));
                            brandid=intent.getStringExtra("vinbrand_id");
                            seriesid=intent.getStringExtra("vinseries_id");
                            cartName=intent.getStringExtra("CartName");
                        }
                    }
                }
            }else if(intent.getAction().equals("cartmodel")){
                tv_cartmodel.setText(intent.getStringExtra("brand")+
                        intent.getStringExtra("serise")+
                intent.getStringExtra("model"));
                seriesid=intent.getStringExtra("seriseID");
                brandid=intent.getStringExtra("barndID");
                cartName=intent.getStringExtra("model");
                tv_cartmodel.setBackgroundResource(R.drawable.juxingnull);
            }
        }
    }
    //点击提交判断是否有空的Edittext
    private boolean IsNullEdit(EditText editText){
        if(editText!=null) {
            String str = editText.getText().toString();
            if (TextUtils.isEmpty(str)) {
                editText.setBackgroundResource(R.drawable.rednull);
                return false;
            }else{
                return true;
            }
        }
        return false;
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
    //上传三张大图
    private void updateImag(final String path){
            final RequestParams params=new RequestParams(getInterface.UpdateImag);
            params.setMultipart(true);
            params.setConnectTimeout(80000);
            params.setMaxRetryCount(5);//
            params.addBodyParameter("imgdata",new File(path));
            Log.e("TAG","参数--"+params.getParams("imgdata"));
            Log.e("TAG","params=="+params);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("TAG","图片上传结果---"+result);
                    if(!TextUtils.isEmpty(path)&&path.equals(zqfPath)){
                      ZQFBean.zqpath=  GetJsonUtils.getZQF(getContext(),result);
                        Log.e("TAG","左前方=="+ ZQFBean.zqpath);
                    }else if(!TextUtils.isEmpty(path)&&path.equals(zqPath)){
                        //正前方图pain
                        ZQBean.zqpath=GetJsonUtils.getZQF(getContext(),result);
                        Log.e("TAg","正前方图pain="+ZQBean.zqpath);
                    }else if(!TextUtils.isEmpty(path)&&path.equals(zhfPath)){
                        //正前方图pain
                        ZHFBean.zhfpath=GetJsonUtils.getZQF(getContext(),result);
                        Log.e("TAg","正前方图pain="+ ZHFBean.zhfpath);
                    }
                    Log.e("TAG","=="+(!TextUtils.isEmpty(ZHFBean.zhfpath)&&!TextUtils.isEmpty(ZQBean.zqpath)&&!TextUtils.isEmpty(ZQFBean.zqpath)));
                    if(!TextUtils.isEmpty(ZHFBean.zhfpath)&&!TextUtils.isEmpty(ZQBean.zqpath)&&!TextUtils.isEmpty(ZQFBean.zqpath)){
                        //上传全部信息
                        Log.e("TAG","上传信息");
                        updateCartMsg();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("TAG","上传失败===");
                    if(!TextUtils.isEmpty(ex.getMessage().toString())) {
                        mydialog.dismiss();
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
    //上传补录车辆信息
    private void updateCartMsg(){

        RequestParams requestParams=new RequestParams(getInterface.UpCartData);
        //    参数 时间   regDate  merchant_code 商家ID  vin,groupid,carName(车型),
        // mileage,target_price,userid,brandid,seriesid,zhengqian45,zhengqian,zhenghou
        requestParams.addBodyParameter("vin",edit_num.getText().toString().trim());
        requestParams.addBodyParameter("merchant_code",quyuID);
        requestParams.addBodyParameter("groupid",UserBean.groupids);
        requestParams.addBodyParameter("userid",UserBean.id);
        requestParams.addBodyParameter("brandid",brandid);
        requestParams.addBodyParameter("seriesid",seriesid);
        requestParams.addBodyParameter("regDate",tv_time.getText().toString().trim());
        requestParams.addBodyParameter("mileage",edt_licheng.getText().toString().trim());
        requestParams.addBodyParameter("target_price",edt_price.getText().toString().trim());
        requestParams.addBodyParameter("zhengqian45",ZQFBean.zqpath);
        requestParams.addBodyParameter("zhengqian",ZQBean.zqpath);
        requestParams.addBodyParameter("zhenghou",ZHFBean.zhfpath);
        requestParams.addBodyParameter("carName",cartName.replace(" ",""));
        Log.e("TAG","上传地址=="+requestParams.getUri());
        Log.e("TAG","上传参数=="+requestParams.getBodyParams());
        Log.e("TAG","上传URL=="+requestParams);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","上传成功=="+result);
//                {"status":1,"msg":"添加成功","id":"2261"}
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String status=jsonObject.getString("status");
                    String msg;
                    if (status.equals("1")){
                       msg=jsonObject.getString("msg");
                    }else{
                        msg=jsonObject.getString("msg");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mydialog.dismiss();
                mySuccess.show();
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
//        requestParams.addBodyParameter("carName",);
    }
}
