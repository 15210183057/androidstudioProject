package utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by 123456 on 2018/3/12.
 */

public class MyBitmap {
    /**
     * 处理旋转后的图片
     * @param originpath 原图路径
    //     * @param context 上下文
     * @return 返回修复完毕后的图片路径
     */
    public static Bitmap amendRotatePhoto(String originpath, Bitmap bit) {

        // 取得图片旋转角度
        int angle = readPictureDegree(originpath);

        // 把原图压缩后得到Bitmap对象
//        Bitmap bmp = getCompressPhoto(originpath);;
        Bitmap bmp=bit;
        // 修复图片被旋转的角度
        Bitmap bitmap = rotaingImageView(angle, bmp);

        // 保存修复后的图片并返回保存后的图片路径
//        return savePhotoToSD(bitmap, context);
//        return saveBmpToPath(bitmap,originpath);
        return bitmap;
    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     * @param angle 被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        if(angle == 0){
            return bitmap;
        }
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }
}
