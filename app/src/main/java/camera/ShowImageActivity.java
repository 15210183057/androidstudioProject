package camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a123456.zhonggu.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import utils.BitToByte;

public class ShowImageActivity extends Activity {
	
	private ImageView mImage;
    private Uri mSavedPicUri;
    private Button cancel;
    private Button send;
	
	private String thumbPath = null;
	
	private Uri uri ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_image);
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
				Log.e("TAG","上传图片");
				FileUtil fileUtil=new FileUtil(ShowImageActivity.this);
//				Bitmap bitmap=fileUtil.readBitmap(uri.toString());
				Bitmap bitmap=fileUtil.readBitmap(thumbPath);
				Log.e("TAG","bitmap=="+(bitmap!=null));
				if(bitmap!=null){
					mImage.setImageBitmap(bitmap);
					Log.e("TAG","设置图片");
				}
				//发送广播
				Intent intent=new Intent();
				intent.setAction("vin");
				intent.putExtra("vinnum","vinnum");
				sendBroadcast(intent);
				Log.e("TAG","发送广播vin");
				String str=Base64.encodeToString(BitToByte.bmpToByteArray(bitmap,false),Base64.DEFAULT);
				RequestParams params=new RequestParams();
				params.addBodyParameter("",str);
				x.http().post(params, new Callback.CommonCallback<String>() {
					@Override
					public void onSuccess(String result) {
						Log.e("TAG","请求成功");


					}

					@Override
					public void onError(Throwable ex, boolean isOnCallback) {
						Log.e("TAG","请求失败");
					}

					@Override
					public void onCancelled(CancelledException cex) {

					}

					@Override
					public void onFinished() {

					}
				});
				finish();
				break;
			default:
				break;
			}
		}
	};
	
}
