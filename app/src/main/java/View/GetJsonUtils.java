package View;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.BUCartListBeanNUm;
import bean.BuCartListBean;
import bean.CarBean;
import bean.UserBean;

/**
 * Created by 123456 on 2018/2/5.
 */

public class GetJsonUtils {
    //登陆接口
//    成功
//    {
//        "status": 1,
//            "user": {
//                "id": "2",
//                "username": "admin",
//                "password": "8940249fbb327af9b5ee2836425cdbc2",
//                "headimg": "20170918\\732f97fa0c543a3d1150d7b1ee302631.jpg",
//                "loginip": "127.0.0.1",
//                "logintime": "1478751964",
//                "groupid": "0",
//                "status": "1"
//    },
//        "groups": [0]
//    }
//    失败：{"status":0,"msg":"没有此用户"}
    public static List getLogin(Context ctx,String result){
        List<UserBean>list=new ArrayList<UserBean>();
        try {
            JSONObject object=new JSONObject(result);
            UserBean.status=object.getString("status");
            Log.e("TAG","status=="+UserBean.status);
            UserBean userBean=new UserBean();
            if(UserBean.status.equals("1")){
                String user=object.getString("user");
//                JSONObject object1=new JSONObject(user);
                userBean.id=object.getString("userid");
                userBean.username=object.getString("user");
//                userBean.password=object.getString("password");
//                userBean.headimg=object1.getString("headimg");
//                userBean.loginip=object1.getString("loginip");
//                userBean.logintime=object1.getString("logintime");
//                userBean.groupid=object1.getString("groupid");
               userBean.groupids=object.getString("groupids");
               Log.e("TAG","group==="+userBean.groupids);
                Toast.makeText(ctx,"登陆成功",Toast.LENGTH_SHORT).show();
            }else{
                userBean.msg=object.getString("msg");
                Log.e("TAG","msg=="+userBean.msg);
                Toast.makeText(ctx,"登陆失败："+userBean.msg,Toast.LENGTH_SHORT).show();
            }
            list.add(userBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    //待补充车源
    public static List getBuCartList(Context ctx,String result){
        List<BuCartListBean> list=new ArrayList<BuCartListBean>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            String status=jsonObject.getString("status");
            if(status.equals("1")) {
//            String data=jsonObject.getString("data");
                String resultStr=jsonObject.getString("result");
                JSONObject jsonObject1=new JSONObject(resultStr);
                BUCartListBeanNUm.total = jsonObject1.getString("total");
                Log.e("TAG","total=="+BUCartListBeanNUm.total);
                BUCartListBeanNUm.last_page=jsonObject1.getString("last_page");
                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    BuCartListBean buCartListBean=new BuCartListBean();
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    buCartListBean.vin = jsonObject2.getString("vin");//右上角编码
                    String car_info=jsonObject2.getString("car_info");

                    JSONObject jsonObject3=new JSONObject(car_info);
                    buCartListBean.licensePlate=jsonObject3.getString("licensePlate");//车拍照
                    buCartListBean.cardType=jsonObject3.getString("cardType");//车名
                    //公司merchant_info
                    String merchant_info=jsonObject2.getString("merchant_info");
                    JSONObject jsonObject4=new JSONObject(merchant_info);
                    buCartListBean.name=jsonObject4.getString("name");//公司名称
                    list.add(buCartListBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
