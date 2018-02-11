package camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import View.GetJsonUtils;
import com.example.a123456.zhonggu.R;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpResponse;
import org.xutils.http.body.FileBody;
import org.xutils.http.body.StringBody;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.JaShiZhengBean;
import jiekou.getInterface;
import mycamare.utils.Utils;
import utils.BitToByte;
import utils.Mydialog;

public class ShowImageActivity extends Activity {
	
	private ImageView mImage;
    private Uri mSavedPicUri;
    private Button cancel;
    private Button send;
	private String name;
	private String thumbPath = null;
	
	private Uri uri ;
	private List<JaShiZhengBean> list=new ArrayList<JaShiZhengBean>();
	Mydialog mydialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_image);
		name=getIntent().getStringExtra("name");
		Log.e("TAG","name=="+name);
		getIntentData();
		findViews();
		setViewData();
	}


	private void getIntentData() {
		uri = getIntent().getData();
		thumbPath=getIntent().getStringExtra("thumbPath");
	}
	
	private void setViewData() {
		if(uri != null){
//			mImage.setImageURI(uri);
			mImage.setImageBitmap(BitmapFactory.decodeFile(thumbPath));
		}
	}

	private void findViews() {
		// TODO Auto-generated method stub
    	cancel = (Button)this.findViewById(R.id.rc_back);
        send = (Button)this.findViewById(R.id.rc_send);
        
        mImage = (ImageView)this.findViewById(R.id.rc_img);
        cancel.setOnClickListener(clickListener);
        send.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			switch (id) {
			case R.id.rc_back:
				finish();
				break;
				
			case R.id.rc_send:
//				Log.e("TAG","上传图片"+(name!=null)+"===="+name.equals("zhengqian"));

				if(!TextUtils.isEmpty(name)){
					sendMyReciver(name);
					finish();
				}
//				FileUtil fileUtil=new FileUtil(ShowImageActivity.this);
//				Bitmap bitmap=fileUtil.readBitmap(uri.toString());
//				Bitmap bitmap=fileUtil.readBitmap(thumbPath);
//				Log.e("TAG","bitmap=="+(bitmap!=null));
//				if(bitmap!=null){
//					mImage.setImageBitmap(bitmap);
//					Log.e("TAG","设置图片");
//				}
//				String str=Base64.encodeToString(BitToByte.bmpToByteArray(bitmap,false),Base64.DEFAULT);
				else {
					mydialog=new Mydialog(ShowImageActivity.this,"正在识别请稍等......");
					mydialog.show();
					String url;
					if (CameraActivity.DST_CENTER_RECT_HEIGHT == 70) {
						url = getInterface.getVin;
					} else {
						url = getInterface.getJaShiZheng;
					}
					RequestParams params = new RequestParams(url);
//					for(int i=0;i<3;i++){
//
//					}
					params.addBodyParameter("imgdata", new File(thumbPath));
					params.setConnectTimeout(80000);
					params.setMaxRetryCount(5);//
					params.setMultipart(true);
					Log.e("TAG","params.getUri()=="+params.getUri());
					Log.e("TAG","params.getFileParams()=="+params.getFileParams());
					Log.e("TAG","params.getBodyParams()=="+params.getBodyParams());
					Log.e("TAG","params.getParams(\"imgdata\")=="+params.getParams("imgdata"));
					Log.e("TAG", "params==" + params);
					x.http().post(params, new Callback.CommonCallback<String>() {
						@Override
						public void onSuccess(String result) {
							Log.e("TAG", "请求成功==" + result);
							mydialog.dismiss();
							//{"status":0,"msg":"要识别的图片不能为空"}
							list=GetJsonUtils.getJSZMsgList(ShowImageActivity.this, result);
							if(list.size()==0){
								Toast.makeText(ShowImageActivity.this,""+list.get(0).msg,Toast.LENGTH_LONG).show();
							}else {
								//发送广播
								sendMyReciver("");
								Log.e("TAG", "发送广播vin");
							}
							finish();
						}

						@Override
						public void onError(Throwable ex, boolean isOnCallback) {
							Log.e("TAG", "请求失败===");
							if(!TextUtils.isEmpty(ex.getMessage().toString())){
								mydialog.dismiss();
								Toast.makeText(ShowImageActivity.this, "识别失败", Toast.LENGTH_SHORT).show();
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
				break;
			default:
				break;
			}
		}
	};

	//发送广播传递数据给newfragment
	private void sendMyReciver(String name){
		Intent intent = new Intent();
		intent.setAction("vin");
		intent.putExtra("path", thumbPath);
		if(name!=null&&name.equals("zhengqian")) {
			intent.putExtra("name","zhengqian");
		}else if(name!=null&&name.equals("zuoqian")){
			intent.putExtra("name","zuoqian");
		}else if(name!=null&&name.equals("zhenghou")){
			intent.putExtra("name","zhenghou");
		}else{
			intent.putExtra("vinnum", list.get(0).vin);
			intent.putExtra("data", list.get(0).data);
			intent.putExtra("licheng", list.get(0).licheng);
			intent.putExtra("price", list.get(0).price);
		}
		sendBroadcast(intent);
	}
}
