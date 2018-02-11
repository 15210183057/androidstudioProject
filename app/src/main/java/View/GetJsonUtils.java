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
import bean.BrandBean;
import bean.BuCartListBean;
import bean.JaShiZhengBean;
import bean.ModelBean;
import bean.SeriseBean;
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
                    buCartListBean.id=jsonObject2.getString("id");
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
        Log.e("TAG","listbu0=="+list.get(0).cardType);
        Log.e("TAG","listbu5=="+list.get(5).cardType);
        return list;
    }
    //获取vin码
    public static List getVinMegList(Context ctx,String result){
        List list=new ArrayList();
        return list;
    }

    /**
     * 获取驾驶证
     * @param ctx
     * @param result
     * @return
     * "status":0,"msg":"要识别的图片不能为空"}
     */
    public static List getJSZMsgList(Context ctx,String result){
        List list=new ArrayList();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JaShiZhengBean jaShiZhengBean=new JaShiZhengBean();
            jaShiZhengBean.status=jsonObject.getString("status");
            if(jaShiZhengBean.status.equals("0")){
                 jaShiZhengBean.msg=jsonObject.getString("msg");
            }else if(jaShiZhengBean.status.equals("1")){
                jaShiZhengBean.vin=jsonObject.getString("vin");
                jaShiZhengBean.licheng=jsonObject.getString("mileage");
                jaShiZhengBean.data=jsonObject.getString("regdate");
                jaShiZhengBean.price=jsonObject.getString("price");
                list.add(jaShiZhengBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取品牌
     * @param ctx
     * @param result
     * @return
     */
//    "status": 1,
//            "result": [{
//        "brand_id": "2",
//                "brand_name": "阿斯顿·马丁",
//                "brand_initial": "A"
//    }, {

    public static List getBrand(Context ctx,String result){
        List<BrandBean>list=new ArrayList<BrandBean>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            String status=jsonObject.getString("status");
            if(status.equals("1")){
                JSONArray jsonArray=jsonObject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    BrandBean brandBean=new BrandBean();
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    brandBean.brand_id=jsonObject1.getString("brand_id");
                    brandBean.brand_name=jsonObject1.getString("brand_name");
                    brandBean.brand_initial=jsonObject1.getString("brand_initial");
                    list.add(brandBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("TAG","list0=="+list.get(0).brand_name);
        Log.e("TAG","list5=="+list.get(5).brand_name);
        return list;
    }
    /**
     * 获取车系
     * "status":1,
     "result":[
     {
     "series_id":"2192",
     "brand_id":"152",
     "brand_name":"中客华北",
     "series_name":"腾狮",
     "series_group_name":"中客华北",
     "level_name":"中大型SUV",
     "maker_type":"国产"
     },
     */
    public static List getSerise(Context ctx,String result){
        List<SeriseBean>list=new ArrayList<SeriseBean>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            String status=jsonObject.getString("status");
            if(status.equals("1")){
                JSONArray jsonArray=jsonObject.getJSONArray("result");
                for(int i=0;i<jsonArray.length();i++){
                    SeriseBean seriseBean=new SeriseBean();
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    seriseBean.serise=jsonObject1.getString("series_name");
                    seriseBean.serise_id=jsonObject1.getString("series_id");
                    list.add(seriseBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
    /**
     * 获取车型
     * "status":1,
     "result":[
     {
     "model_id":"508",
     "brand_id":"2",
     "brand_name":"阿斯顿·马丁",
     "series_id":"31",
     "series_name":"阿斯顿马丁ONE-77",
     "series_group_name":"阿斯顿·马丁",
     "model_name":"2011款 阿斯顿马丁ONE-77",
     "price":"4700",
     "liter":"7.3L",
     "gear_type":"自动",
     "model_year":"2011",
     "maker_type":"进口",
     "discharge_standard":"欧4",
     "seat_number":"2",
     "min_reg_year":"2010",
     "max_reg_year":"2015"
     },
     */
    public static List getModel(Context ctx,String result){
        List<ModelBean>list=new ArrayList<ModelBean>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            String status=jsonObject.getString("status");
            if(status.equals("1")){
                JSONArray jsonArray=jsonObject.getJSONArray("result");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    ModelBean modelBean=new ModelBean();
                    modelBean.model_id=jsonObject1.getString("model_id");
                    modelBean.model_name=jsonObject1.getString("model_name");
                    list.add(modelBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
    //获取区域信息

    /**
     * \
     * @param context
     * @param result
     * @return
     * 	"status": 1,
    "result": {
    "total": 1,
    "per_page": "100",
    "current_page": 1,
    "last_page": 1,
    "data": [{
    "id": "61",
     */
    public static List getQuYu(Context context,String result){
        List list=new ArrayList();
        try {
            JSONObject jsonObject=new JSONObject(result);
            String status=jsonObject.getString("status");
            if(status.equals("1")){
                String str=jsonObject.getString("result");
                JSONObject jsonObject1=new JSONObject(str);
                JSONArray jsonArray=jsonObject1.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2=jsonArray.getJSONObject(i);
                    String name=jsonObject2.getString("name");
                    list.add(name);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
