package View;

import android.content.Context;
import android.text.TextUtils;
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
import bean.CartMsgBean;
import bean.JaShiZhengBean;
import bean.ModelBean;
import bean.SeriseBean;
import bean.UserBean;
import bean.ZQFBean;
import camera.ShowImageActivity;

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
                    buCartListBean.time=jsonObject2.getString("create_time");//时间
                    buCartListBean.id=jsonObject2.getString("id");
                    String car_info=jsonObject2.getString("car_info");

                    JSONObject jsonObject3=new JSONObject(car_info);
                    buCartListBean.licensePlate=jsonObject3.getString("licensePlate");//车拍照
                    buCartListBean.price=jsonObject3.getString("target_price");//价格
                    buCartListBean.cardType=jsonObject3.getString("cardType");//车名
                    buCartListBean.carName=jsonObject3.getString("carName");//车系
                    buCartListBean.mileage=jsonObject3.getString("mileage");

                    //公司merchant_info
                    String merchant_info=jsonObject2.getString("merchant_info");
                    JSONObject jsonObject4 = null;
                    if(!TextUtils.isEmpty(merchant_info)&&!merchant_info.equals("null")) {
                        jsonObject4= new JSONObject(merchant_info);
                        buCartListBean.name=jsonObject4.getString("name");//公司名称
                    }

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
     * {
    "status": 1,
    "vin": "LFV4A24F1C3012971",
    "model": [{
    "series_group_name": "一汽奥迪",
    "color": "",
    "model_liter": "2.4L",
    "model_year": 2011,
    "brand_name": "奥迪",
    "model_id": 72,
    "brand_id": 1,
    "series_id": 3,
    "model_name": "2011款 奥迪A6L 2.4 技术型",
    "model_price": 44.1,
    "model_emission_standard": "国4",
    "model_gear": "自动",
    "series_name": "奥迪A6L",
    "min_reg_year": 2011,
    "max_reg_year": 2013,
    "ext_model_id": 72
    },
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
                Toast.makeText(ctx,""+jaShiZhengBean.msg,Toast.LENGTH_LONG).show();
            }else if(jaShiZhengBean.status.equals("1")){
                jaShiZhengBean.vin=jsonObject.getString("vin");
                jaShiZhengBean.licheng=jsonObject.getString("mileage");
                jaShiZhengBean.data=jsonObject.getString("regdate");
                jaShiZhengBean.price=jsonObject.getString("price");
                jaShiZhengBean.brand_id=jsonObject.getString("brand_id");//品牌
                jaShiZhengBean.series_id=jsonObject.getString("series_id");//车系id
                jaShiZhengBean.CartName=jsonObject.getString("model_name");//车型
//                "brand_id": 1,
//                        "series_id": 3,
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
        List<CartMsgBean> list=new ArrayList<CartMsgBean>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            String status=jsonObject.getString("status");
            if(status.equals("1")){
                String str=jsonObject.getString("result");
                JSONObject jsonObject1=new JSONObject(str);
                JSONArray jsonArray=jsonObject1.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    CartMsgBean cartMsgBean=new CartMsgBean();
                    JSONObject jsonObject2=jsonArray.getJSONObject(i);
                    cartMsgBean.cartmsgname=jsonObject2.getString("name");
                    cartMsgBean.cartMsgId=jsonObject2.getString("id");
                    list.add(cartMsgBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取左前方图pain
     * "server": "AliyunOSS",
     "date": "Mon, 12 Feb 2018 00:57:55 GMT",
     "content-length": "0",
     "connection": "keep-alive",
     "x-oss-request-id": "5A80E693CB3DB17F740A5AED",
     "etag": "\"8F54C5374638B2D3A45B8557C9DB1D3E\"",
     "x-oss-hash-crc64ecma": "16482371403549542864",
     "content-md5": "j1TFN0Y4stOkW4VXydsdPg==",
     "x-oss-server-time": "160",
     "info": {
     "url": "http:\/\/imgwang.oss-cn-beijing.aliyuncs.com\/2018-02-12\/3c5844ed25abc69bb2480808b7f7876c.jpg",
     "content_type": null,
     "http_code": 200,
     "header_size": 336,
     "request_size": 479,
     "filetime": -1,
     "ssl_verify_result": 0,
     "redirect_count": 0,
     "total_time": 0.16791,
     "namelookup_time": 0.004138
     * @param context
     * @param result
     */
    public static String  getZQF(Context context,String result){
        String path="";
        try {
            JSONObject jsonObject=new JSONObject(result);
            String info=jsonObject.getString("info");
            JSONObject jsonObject1=new JSONObject(info);
            ZQFBean.zqpath=jsonObject1.getString("url");
            path=jsonObject1.getString("url");
            Log.e("TAG","zqf=="+ZQFBean.zqpath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return path;
    }
}
