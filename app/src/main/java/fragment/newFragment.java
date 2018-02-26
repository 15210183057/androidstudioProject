package fragment;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.bumptech.glide.Glide;
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

import bean.BeanFlag;
import bean.BrandBean;
import bean.CartMsgPrice;
import bean.JaShiZhengBean;
import bean.ModelNameandID;
import bean.MyNewUpdate;
import bean.UserBean;
import bean.ZHFBean;
import bean.ZQBean;
import bean.ZQFBean;
import camera.CameraActivity;
import camera.FileUtil;
import jiekou.getInterface;
import utils.MyModelDialog;
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
    private EditText edt_licheng,edt_price,tv_tel,edt_name;//里程，价格,联系电话
    private ImageView img_newfragment,img2_newfragment,img3_newfragment;
    private Button btn_commit;
    private LinearLayout linear3_newfragment,linear_nameandtel;
    private ArrayAdapter arrayAdapter;
    private LinearLayout linear_celiang;
    private String serise_id,model_id;
    private TextView tv_quyue;
    CommonPopupWindow window2;
    private TextView tv_cartmodel;
    List imgListPath=new ArrayList();
    Mydialog mydialog;
    String zqfPath,zqPath,zhfPath;
    String quyuID,brandid,modelid,seriesid,cartName;//商家信息ID,品牌ID，车系ID,车型
    MySuccess mySuccess;
    private TextView tv_getprice,tv_getmodel;
    Mydialog mydialog1;
    String quyuTelName;//车商信息对于的用户和电话
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Log.e("TAG","onCreate==");
        view=inflater.inflate(R.layout.fragment_new, container, false);
        initView();
        MyRegistReciver();
        mydialog1=new Mydialog(getContext(),"正在获取请稍后");
        mydialog=new Mydialog(getContext(),"正在上传.....");
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            edt_licheng.setText("");
            edit_num.setText("");
            tv_tel.setText("");
            edt_name.setText("");
            edt_price.setText("");
            tv_quyue.setText("请选择车商信息");
            tv_time.setText("请选择日期");
            tv_cartmodel.setText("请选择品牌，车系和车型");
            linear_nameandtel.setVisibility(View.VISIBLE);
            edit_num.setFocusableInTouchMode(true);
            edit_num.setFocusable(true);
            edit_num.requestFocus();
            BeanFlag.Flag=false;
            zqfPath="";zqPath="";zhfPath="";
            img_newfragment.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.zq45d));
            img2_newfragment.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.zqf));
            img3_newfragment.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.zhf));
        }
        getSubStr(edt_licheng);
        getSubStr(edt_price);
        linear3_newfragment.setVisibility(View.VISIBLE);
    }

    private void initView() {
        mySuccess=new MySuccess(getContext(),"提交成功");
        img_topleft=view.findViewById(R.id.img_left);
        img_topright=view.findViewById(R.id.img_right);
        tv_topcenter=view.findViewById(R.id.tv_center);
        tv_topcenter.setText("待补充车源");
        img_topleft.setVisibility(View.GONE);
        img_topright.setVisibility(View.GONE);
        linear_celiang=view.findViewById(R.id.linear_celiang);

        tv_getprice=view.findViewById(R.id.tv_getprice);
        tv_getprice.setOnClickListener(this);
        //所有要填写的控件
        //一下三个控件，当vin码识别识别，需要手动填写
        linear3_newfragment=view.findViewById(R.id.linear3_newfragment);
        tv_cartmodel=view.findViewById(R.id.tv_cartmodel);
        tv_time=view.findViewById(R.id.tv_time);//日期
        linear_nameandtel=view.findViewById(R.id.linear_nameandtel);
        edt_name=view.findViewById(R.id.edt_name);
        tv_tel=view.findViewById(R.id.tv_tel);//联系电话
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
        tv_tel.addTextChangedListener(new MyEditTextChangeListener(tv_tel));
        edt_name.addTextChangedListener(new MyEditTextChangeListener(edt_name));

        img_newfragment.setOnClickListener(this);
        img2_newfragment.setOnClickListener(this);
        img3_newfragment.setOnClickListener(this);
        tv_cartmodel.setOnClickListener(this);
        tv_quyue.setOnClickListener(this);

        tv_getmodel=view.findViewById(R.id.tv_getmodel);
        tv_getmodel.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_getmodel:
                //获取车型车系车牌
                if (!TextUtils.isEmpty(edit_num.getText().toString())
                        && !TextUtils.isEmpty(tv_time.getText().toString())
                        && !tv_time.getText().toString().equals("请选择日期")) {
                    getPrice("model");
                } else {
                    Toast.makeText(getContext(), "vin码或注册日期不能为空", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_getprice:
                //获取价格
                Log.e("TAG", "TextUtils.isEmpty(tv_time.getText().toString()==" + TextUtils.isEmpty(tv_time.getText().toString()));
                if (!TextUtils.isEmpty(edit_num.getText().toString())
                        && !TextUtils.isEmpty(tv_time.getText().toString())
                        && !tv_time.getText().toString().equals("请选择日期")) {
                    getPrice("price");
                } else {
                    Toast.makeText(getContext(), "vin或者注册时间不能为空", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_quyue:
                Intent intentS = new Intent(getContext(), MySerchActvity.class);
//                intentS.putExtra("myserch","update");
                startActivity(intentS);
                break;
            case R.id.img_paizhao:
                getPopView();
                break;
            case R.id.tv_paizhao:
                if(setPermissions()) {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra("height", "70");
                    startActivity(intent);
                    window.dismiss();
                }
                break;
            case R.id.tv_xiangce:
                if (setPermissions()) {
                    Intent intent1 = new Intent(getContext(), CameraActivity.class);
                    intent1.putExtra("height", "300");
                    startActivity(intent1);
                window.dismiss();
               }
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
                }else if (!IsNullEdit(edit_num)||edit_num.getText().toString().length()!=17){
                    edit_num.setBackgroundResource(R.drawable.rednull);
                    Toast.makeText(getContext(),"VIN码不能为空并且只能为17位",Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(tv_time.getText().toString())||tv_time.getText().toString().trim().equals("请选择日期")) {
                    tv_time.setBackgroundResource(R.drawable.rednull);
                    Toast.makeText(getContext(),"注册日期不能为空",Toast.LENGTH_LONG).show();
                }
                else if(!IsNullEdit(edt_name)){
                    edt_name.setBackgroundResource(R.drawable.rednull);
                    Toast.makeText(getContext(),"姓名不能为空",Toast.LENGTH_LONG).show();
                }else if(!IsNullEdit(tv_tel)){
                    tv_tel.setBackgroundResource(R.drawable.rednull);
                    Toast.makeText(getContext(),"联系电话不能为空",Toast.LENGTH_LONG).show();
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

                else{
                     if(BeanFlag.Flag){
                         mydialog=new Mydialog(getContext(),"正在修改，请稍等...");
                         Log.e("TAG","先上传图片===="+zqfPath);
                         Log.e("TAG","先上传图片"+(TextUtils.isEmpty(zqfPath)&&TextUtils.isEmpty(zqPath)&&TextUtils.isEmpty(zhfPath)));
                         if(TextUtils.isEmpty(zqfPath)&&TextUtils.isEmpty(zqPath)&&TextUtils.isEmpty(zhfPath)){
                             //走修改接口
                             setCartMsg();
                             mydialog.show();
                             Log.e("TAG","走修改接口");
                         }else{
                             mydialog.show();
                             if(!TextUtils.isEmpty(zhfPath)){
                                 updateImag(zhfPath);
                             }else if(!TextUtils.isEmpty(zqPath)){
                                 updateImag(zqPath);
                             }else if(!TextUtils.isEmpty(zqfPath)){
                                 updateImag(zqfPath);
                             }
                         }
                     }else {
                         if (TextUtils.isEmpty(zqfPath)){
                             //img_newfragment.getDrawable().getCurrent().getConstantState()==getResources().getDrawable(R.drawable.zq45d).getConstantState()
                             img_newfragment.setImageDrawable(getActivity().getDrawable(R.drawable.rednull));
                             Toast.makeText(getContext(),"左前45°图片不能为空",Toast.LENGTH_LONG).show();
                         }
                         else if (TextUtils.isEmpty(zqPath)){
                             img2_newfragment.setImageDrawable(getActivity().getDrawable(R.drawable.rednull));
                             Toast.makeText(getContext(),"正前图片不能为空",Toast.LENGTH_LONG).show();
                         }
                         else if (TextUtils.isEmpty(zhfPath)){
                             img3_newfragment.setImageDrawable(getActivity().getDrawable(R.drawable.rednull));
                             Toast.makeText(getContext(),"正后图片不能为空",Toast.LENGTH_LONG).show();
                         }else {
                             mydialog.show();
                             Log.e("TAG", "zqfPath==" + zqfPath);
                             updateImag(zqfPath);
                             updateImag(zqPath);
                             updateImag(zhfPath);
                         }
                     }
                }
                break;
            case R.id.img_newfragment:
                img_newfragment.setBackgroundResource(0);
               if( setPermissions()) {
                Intent intent3=new Intent(getContext(),CameraActivity.class);
                intent3.putExtra("name","zuoqian");
                intent3.putExtra("height","350");
                startActivity(intent3);
               }
                break;
            case R.id.img2_newfragment:
                img2_newfragment.setBackgroundResource(0);
                if(setPermissions()){
                    Intent intent4=new Intent(getContext(),CameraActivity.class);
                    intent4.putExtra("name","zhengqian");
                    intent4.putExtra("height","350");
                    startActivity(intent4);
                }

                break;
            case R.id.img3_newfragment:
                img3_newfragment.setBackgroundResource(0);
                if(setPermissions()) {
                    Intent intent5 = new Intent(getContext(), CameraActivity.class);
                    intent5.putExtra("name", "zhenghou");
                    intent5.putExtra("height", "350");
                    startActivity(intent5);
                }
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
        intentFilter.addAction("goon");
        intentFilter.addAction("modelname");
        intentFilter.addAction("updataCart");//获取收到添加车商信息
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
                        String month="";
                        String day="";
                        if((monthOfYear+1)<10){
                            month="0"+(monthOfYear+1);
                        }else{
                            month=""+(monthOfYear+1);
                        }
                        if(dayOfMonth<10){
                            day="0"+dayOfMonth;
                        }else{
                           day=dayOfMonth+"";
                        }
                        Tv.setText(year+"-"+month+"-"+day);
                        Tv.setBackgroundResource(R.drawable.juxingnull);
                    }
                }, year, month, day);
        dialog3.show();
    }
    //判断小数点后面是否都为"0",截取字符串
    private void getSubStr(EditText editText){
        String editStr=editText.getText().toString();
        Log.e("TAG","editStr==="+editStr);
        if(editStr.contains(".")) {
//            if(editStr.length()>5){
//                Log.e("TAG","一共长的=="+editStr.length());
//                Log.e("TAG","小数点的位置"+editStr.indexOf(".")+"hhh=="+editStr.substring(0,editStr.indexOf(".")+3));
//                editStr=editStr.substring(0,5);
//             }
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
                    editText.setText(editStr.substring(0,editStr.indexOf(".")+3));
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
                tv_tel.setText(MyNewUpdate.tel);
                edt_name.setText(MyNewUpdate.contact_name);
                edit_num.setText(MyNewUpdate.vinnum);
                edit_num.setFocusableInTouchMode(false);
                edit_num.setFocusable(false);
                tv_quyue.setText(MyNewUpdate.quyu);
                Log.e("TAG","品牌，车型=="+MyNewUpdate.cartmodel);
                tv_cartmodel.setText(MyNewUpdate.cartmodel);
                tv_time.setText(MyNewUpdate.time);
                edt_licheng.setText(MyNewUpdate.licheng);
                edt_price.setText(MyNewUpdate.price);
                ZQFBean.zqpath=MyNewUpdate.img1;
                Log.e("TAG","修改接受到的图片地址=="+ZQFBean.zqpath);
                ZQBean.zqpath=MyNewUpdate.img2;
                ZHFBean.zhfpath=MyNewUpdate.img3;
                if(!TextUtils.isEmpty(MyNewUpdate.img1)) {
                     Glide.with(getContext()).load(MyNewUpdate.img1).placeholder(R.drawable.zq45d).error(R.drawable.zq45d).into(img_newfragment);
                }
                if(!TextUtils.isEmpty(MyNewUpdate.img2)) {
                      Glide.with(getContext()).load(MyNewUpdate.img2).placeholder(R.drawable.zqf).error(R.drawable.zqf).into(img2_newfragment);
                }
                if(!TextUtils.isEmpty(MyNewUpdate.img3)) {
                    Glide.with(getContext()).load(MyNewUpdate.img3).placeholder(R.drawable.zhf).error(R.drawable.zhf).into(img3_newfragment);
                }
                quyuID=MyNewUpdate.quyuID;
                seriesid=MyNewUpdate.seriseID;
                modelid=MyNewUpdate.modelID;
                brandid=MyNewUpdate.brandid;
                cartName=MyNewUpdate.cartmodel;
                BeanFlag.Flag=true;
            }
            else if(intent.getAction().equals("quyu")){
                String name=intent.getStringExtra("name");
                quyuID=intent.getStringExtra("ID");
                quyuTelName=intent.getStringExtra("tel");
                Log.e("TAG","车商信息对应的用户名和电话=="+quyuTelName+"="+(TextUtils.isEmpty(quyuTelName)));
                Log.e("TAG","车商信息对应的用户名和电话=="+quyuTelName+"="+(quyuTelName.equals("")));
                if(TextUtils.isEmpty(quyuTelName)||quyuTelName.equals("null&null")){
                    linear_nameandtel.setVisibility(View.VISIBLE);
                }else{
                    linear_nameandtel.setVisibility(View.GONE);
                    String []arr=quyuTelName.split("&");
                    tv_tel.setText(arr[1]);
                    edt_name.setText(arr[0]);
                }
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
                        Log.e("TAG","path=="+path);
                        Log.e("TAG","拍照返回=="+zqfPath);
                        img_newfragment.setImageBitmap(BitmapFactory.decodeFile(path));
//                        img_newfragment.setImageBitmap(fileUtil.readBitmap(path));
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
                            Log.e("TAG","MyodelIDandName=="+ModelNameandID.list.size());
                            MyModelDialog myModelDialog=new MyModelDialog(getContext(),ModelNameandID.list);
                            myModelDialog.show();
                            linear3_newfragment.setVisibility(View.GONE);
                            edit_num.setText(vinStr);
                            edt_licheng.setText(intent.getStringExtra("licheng"));
                            edt_price.setText(intent.getStringExtra("price"));
                            tv_time.setText(intent.getStringExtra("data"));
                            brandid=intent.getStringExtra("vinbrand_id");
                            seriesid=intent.getStringExtra("vinseries_id");
                            List list=new ArrayList();
                            cartName=intent.getStringExtra("CartName");
                            list.add(cartName);
                            modelid=intent.getStringExtra("model_id");
                            Log.e("TAG","model=="+cartName);
                            tv_cartmodel.setText(cartName);
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
                modelid=intent.getStringExtra("modelID");
                tv_cartmodel.setBackgroundResource(R.drawable.juxingnull);
            }else if(intent.getAction().equals("goon")){
                edit_num.setText("");
                edt_price.setText("");
                edt_licheng.setText("");
                tv_time.setText("");
                tv_cartmodel.setText("");
                ZQFBean.zqpath="";ZQBean.zqpath="";ZHFBean.zhfpath="";
                zqfPath="";zqPath="";zhfPath="";
                edit_num.setFocusableInTouchMode(true);
                edit_num.setFocusable(true);
                img_newfragment.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.zq45d));
                img2_newfragment.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.zqf));
                img3_newfragment.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.zhf));
            }else if(intent.getAction().equals("modelname")){
                cartName=intent.getStringExtra("modelname");
                linear3_newfragment.setVisibility(View.VISIBLE);
                tv_cartmodel.setText(cartName);
                modelid=intent.getStringExtra("modelID");
                Log.e("TAG","接收广播---"+intent.getStringExtra("modelname")+"==ID=="+intent.getStringExtra("modelID"));
            }else if(intent.getAction().equals("updataCart")){
                quyuID=intent.getStringExtra("quyuID");
                tv_quyue.setText(intent.getStringExtra("quyuName"));
                linear_nameandtel.setVisibility(View.VISIBLE);
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
//            Log.e("TAG","编辑框的内容发生改变之前的回调方法");
        }

        /**
         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
         * 我们可以在这里实时地 通过搜索匹配用户的输入
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            Log.e("TAG","发生改变");
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
           params.setMaxRetryCount(2);
            Log.e("TAG","参数--"+params.getParams("imgdata"));
            Log.e("TAG","params=="+params);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("TAG","图片上传结果---"+result);
                    if(!TextUtils.isEmpty(path)&&path.equals(zqfPath)){
                      ZQFBean.zqpath=GetJsonUtils.getZQF(getContext(),result);
                    }else if(!TextUtils.isEmpty(path)&&path.equals(zqPath)){
                        //正前方图pain
                        ZQBean.zqpath=GetJsonUtils.getZQF(getContext(),result);

                    }else if(!TextUtils.isEmpty(path)&&path.equals(zhfPath)){
                        //正前方图pain
                        ZHFBean.zhfpath=GetJsonUtils.getZQF(getContext(),result);
                    }
                    Log.e("TAG","左前方图片=="+zqfPath+ ZQFBean.zqpath);
                    Log.e("TAg","正后方图片="+ ZHFBean.zhfpath);
                    Log.e("TAg","正前方图片="+zqPath+ZQBean.zqpath);
                    Log.e("TAG","三张大图上传结果=="+(!TextUtils.isEmpty(ZHFBean.zhfpath)&&!TextUtils.isEmpty(ZQBean.zqpath)&&!TextUtils.isEmpty(ZQFBean.zqpath)));
                    if(!TextUtils.isEmpty(ZHFBean.zhfpath)&&!TextUtils.isEmpty(ZQBean.zqpath)&&!TextUtils.isEmpty(ZQFBean.zqpath)){
                        //上传全部信息
                        Log.e("TAG","上传补录信息");
                        if(BeanFlag.Flag){
                            //修改接口
                            Log.e("TAG","修改接口");
                            setCartMsg();
                        }else {
                            Log.e("TAG","上传接口");
                            updateCartMsg();
                        }
                    }
//                    else{
//                        mydialog.dismiss();
//                        Toast.makeText(getContext(),"图片上传失败",Toast.LENGTH_LONG).show();
//                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("TAG","上传失败===");
                    if(!TextUtils.isEmpty(ex.getMessage().toString())) {
                        Log.e("TAG","ex.getMessage().toString()=="+ex.getMessage().toString());
                        mydialog.dismiss();
                        Toast.makeText(getContext(),"图片上传失败",Toast.LENGTH_LONG).show();
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
        Log.e("TAG","开始上传");
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

        requestParams.addBodyParameter("modelid",modelid);//modelid
        requestParams.addBodyParameter("carName",cartName.replace(" ",""));
        //上传电话和名字
        requestParams.addBodyParameter("tel",tv_tel.getText().toString());
        requestParams.addBodyParameter("name",edt_name.getText().toString());
        requestParams.setMaxRetryCount(2);
        Log.e("TAG","上传地址=="+requestParams.getUri());
        Log.e("TAG","上传参数=="+requestParams.getBodyParams());
        Log.e("TAG","上传URL=="+requestParams);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","上传成功=="+result);
                mydialog.dismiss();
//                {"status":1,"msg":"添加成功","id":"2261"}
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String status=jsonObject.getString("status");
                    String msg;
                    if (status.equals("1")){
                       msg=jsonObject.getString("msg");
                        mySuccess=new MySuccess(getContext(),"上传成功");
                        mySuccess.show();
                    }else{
                        msg=jsonObject.getString("msg");
                        Toast.makeText(getContext(),""+msg,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if(!TextUtils.isEmpty(ex.getMessage().toString())){
                    mydialog.dismiss();
                    Toast.makeText(getContext(),"上传信息失败",Toast.LENGTH_LONG).show();
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
    //修改补录信息车辆
    private void setCartMsg(){
        Log.e("TAG","开始修改");
        RequestParams requestParams=new RequestParams(getInterface.UpCartData);
        //    参数 时间   regDate  merchant_code 商家ID  vin,groupid,carName(车型),
        // mileage,target_price,userid,brandid,seriesid,zhengqian45,zhengqian,zhenghou
        requestParams.addBodyParameter("id",MyNewUpdate.ItemID);
//        requestParams.addBodyParameter("vin",edit_num.getText().toString().trim());//vin码不可修改
        Log.e("TAG","quyuID=="+quyuID);
        requestParams.addBodyParameter("merchant_code",quyuID);
        requestParams.addBodyParameter("groupid",UserBean.groupids);
        requestParams.addBodyParameter("userid",UserBean.id);
        requestParams.addBodyParameter("brandid",brandid);
        requestParams.addBodyParameter("seriesid",seriesid);
        requestParams.addBodyParameter("regDate",tv_time.getText().toString().trim());
        requestParams.addBodyParameter("mileage",edt_licheng.getText().toString().trim());
        requestParams.addBodyParameter("target_price",edt_price.getText().toString().trim());
        Log.e("TAG","修改接受到的图片地址上传=="+ZQFBean.zqpath);
        requestParams.addBodyParameter("zhengqian45",ZQFBean.zqpath);
        requestParams.addBodyParameter("zhengqian",ZQBean.zqpath);
        requestParams.addBodyParameter("zhenghou",ZHFBean.zhfpath);
        requestParams.addBodyParameter("modelid",modelid);//modelid
        requestParams.addBodyParameter("carName",cartName.replace(" ",""));
        //上传电话和名字
        requestParams.addBodyParameter("tel",tv_tel.getText().toString());
        requestParams.addBodyParameter("name",edt_name.getText().toString());
        requestParams.setMaxRetryCount(2);
//        requestParams.addBodyParameter("status","0");
        Log.e("TAG","修改地址=="+requestParams);
        Log.e("TAG","修改上传参数=="+requestParams.getBodyParams());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","修改成功=="+result);
//                {"status":1,"msg":"添加成功","id":"2261"}
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String status=jsonObject.getString("status");
                    String msg;
                    if (status.equals("1")){
                        msg=jsonObject.getString("msg");
                        Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
                        mydialog.dismiss();
                        mySuccess=new MySuccess(getContext(),"修改成功");
                        mySuccess.show();
                    }else{
                        msg=jsonObject.getString("msg");
                        Toast.makeText(getContext(),""+msg,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if(!TextUtils.isEmpty(ex.getMessage().toString())){
                    mydialog.dismiss();
                    Toast.makeText(getContext(),"上传信息失败",Toast.LENGTH_LONG).show();
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
    //获取价格
    private void getPrice(final String string){
        mydialog1.show();
        final RequestParams requestParams=new RequestParams(getInterface.getPrice);
        requestParams.addBodyParameter("vin",edit_num.getText().toString());
        Log.e("TAG","string.equals(\"model\")&&!tv_time.equals(\"请选择日期\")=="+(!tv_time.equals("请选择日期")));
        if(string.equals("model")&&!tv_time.equals("请选择日期")){
            requestParams.addBodyParameter("regdate",tv_time.getText().toString());
        }
        requestParams.setMaxRetryCount(5);
        Log.e("TAG","获取价格params="+requestParams);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAg", "获取价格为：" + result);
//                {"status":0,"vin":"12345678901472589","msg":"获取VIN信息失败"}
                mydialog1.dismiss();
                List<JaShiZhengBean>list=new ArrayList<JaShiZhengBean>();
                list=GetJsonUtils.getCartMsg(getContext(),result);
                if(!TextUtils.isEmpty(string)&&string.equals("model")){
                    edt_price.setText(list.get(0).price.toString());
                    edt_licheng.setText(list.get(0).licheng.toString());
                    getSubStr(edt_price);
                    getSubStr(edt_licheng);
//                    tv_time.setText(list.get(0).data.toString());
                    MyModelDialog myModelDialog=new MyModelDialog(getContext(),ModelNameandID.list);
                    myModelDialog.show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if(!TextUtils.isEmpty(ex.getMessage().toString())){
                    mydialog.dismiss();
                    Log.e("TAG","ex.getMessage().toString()="+ex.getMessage().toString());
                    Toast.makeText(getContext(),"获取失败",Toast.LENGTH_LONG).show();
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
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myBroadcastReceiver);
    }

    static final String[] PERMISSION = new String[]{
            Manifest.permission.READ_CONTACTS,// 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
            Manifest.permission.WRITE_CALL_LOG,        //读取设备信息
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /**
     * 设置Android6.0的权限申请
     */
    private boolean setPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //Android 6.0申请权限
            Toast.makeText(getActivity(),"没有相关权限，请先开启",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(),PERMISSION,1);
        }else{
            return true;
        }
        return false;
    }
}
