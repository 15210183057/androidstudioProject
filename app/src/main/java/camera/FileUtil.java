package camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
//	private static final  String TAG = "FileUtil";
//
	public static final String ACTION_SERVICE = "cameraservice";
//
//	private static final File parentPath = Environment.getExternalStorageDirectory();
//	private static   String storagePath = "";
//	private static final String DST_FOLDER_NAME = "Water365Crew";
//
	private static String jpegName ="";
//
//	/**初始化保存路径
//	 * @return
//	 */
//	private static String initPath(){
//		if(storagePath.equals("")){
//			storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
//			File f = new File(storagePath);
//			if(!f.exists()){
//				f.mkdir();
//			}
//		}
//		return storagePath;
//	}
//
	public static String getJpegName() {
		return jpegName;
	}

	public static void setJpegName(String jpegName) {
		FileUtil.jpegName = jpegName;
	}
//
//	/**保存Bitmap到sdcard
//	 * @param b
//	 */
//	public static void saveBitmap(Bitmap b){
//
//		String path = initPath();
//		long dataTake = System.currentTimeMillis();
//		jpegName = path + "/" + dataTake +".jpg";
//		Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
//		try {
//			FileOutputStream fout = new FileOutputStream(jpegName);
//			BufferedOutputStream bos = new BufferedOutputStream(fout);
//			b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//			bos.flush();
//			bos.close();
//			Log.i(TAG, "saveBitmap成功==成功地址=="+jpegName);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			Log.i(TAG, "saveBitmap:失败");
//			e.printStackTrace();
//		}
//
//	}
//
////从本地
//	public Bitmap getFileBitmap(String path){
//		Bitmap bitmap=null;
//
//		Log.e("TAG","读取地址为path=="+path);
//		//若该文件存在
//		if(!path.isEmpty()) {
//			bitmap = BitmapFactory.decodeFile(initPath()+path);
////			try {
////				bitmap=BitmapFactory.decodeStream(new FileInputStream(path));
////			} catch (FileNotFoundException e) {
////				e.printStackTrace();
////			}
//		}
//		return bitmap;
//	}


	private Context ctx;

	public FileUtil(Context ctx){
		this.ctx = ctx;
	}

	//判断sdcard是否存在方法
	private boolean IsSDCard(){
		if(Environment.getExternalStorageState().
				equals(Environment.MEDIA_MOUNTED)){
			return true;
		}
		return false;
	}

	//获取缓存文件夹目录方法->/mnt/sdcard/android/data/包名/catch
	private String getFile(){
		String str = null;
		str = ctx.getExternalCacheDir().getAbsolutePath();
		return str;
	}

	/**
	 * 保存图片到本地
	 * bitmap->保存图片
	 * name->图片名称
	 * */
	public void saveBitmap(Bitmap bitmap){
		if(!IsSDCard())
			return;
		//保存完整路径
		long dataTake = System.currentTimeMillis();
		String fileName = getFile()+"/"+dataTake+".jpg";
//
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100, buffer);

		File file = new File(fileName);
		Log.e("TAG","保存图片=="+fileName);
		jpegName = fileName;
		if(file.exists()){
			return;
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(buffer.toByteArray());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 本地读取图片
	 * name->图片名称
	 * return->本地获取的图片->无图片时为null
	 * */
	public Bitmap readBitmap(String name){
		Bitmap bitmap = null;
		if(!IsSDCard())
			return bitmap;
		String fileName = name;
		bitmap = BitmapFactory.decodeFile(fileName);
		Log.e("TAG","获取图片地址bitmap=="+fileName);
		return bitmap;
	}

}
