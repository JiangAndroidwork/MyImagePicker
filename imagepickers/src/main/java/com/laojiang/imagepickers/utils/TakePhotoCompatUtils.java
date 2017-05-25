package com.laojiang.imagepickers.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.laojiang.imagepickers.data.ImageContants;

import java.io.File;

/**
 * TODO 拍照帮助类
 */

public class TakePhotoCompatUtils
{
    /**
     * 拍照
     *
     * @param activity    发起拍照的帮助类
     * @param requestCode 请求码
     * @param cachePath   缓存地址
     * @return 拍照后图片地址
     */
    public static String takePhoto(Activity activity, int requestCode, String cachePath)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //自定义缓存路径
        File tempFile = getPhotoTempFile(cachePath);
        //7.0以上需要适配StickMode
        if (Build.VERSION.SDK_INT >= 24)
        {
            Uri imageUri = FileProvider.getUriForFile(activity, com.laojiang.imagepickers.utils.ImagePickerFileProvider.getAuthorities(activity), tempFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
            activity.startActivityForResult(intent, requestCode);
        } else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));//将拍取的照片保存到指定URI
            activity.startActivityForResult(intent, requestCode);
        }
        return tempFile.getAbsolutePath();
    }

    /**
     * 获取临时存储文件的File
     *
     * @param cachePath 缓存文件夹
     * @return 临时文件File
     */
    private static File getPhotoTempFile(String cachePath)
    {
        String name = new StringBuilder().append(ImageContants.PHOTO_NAME_PREFIX)
                .append(String.valueOf(System.currentTimeMillis()))
                .append(ImageContants.IMG_NAME_POSTFIX).toString();
        return new File(cachePath, name);
    }
}
