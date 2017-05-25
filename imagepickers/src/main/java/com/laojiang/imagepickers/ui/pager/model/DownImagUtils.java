package com.laojiang.imagepickers.ui.pager.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.laojiang.imagepickers.utils.BitMapFile;
import com.laojiang.imagepickers.utils.ImagePickerFileProvider;
import com.laojiang.imagepickers.utils.T;

import java.io.File;


/**
 * 下载图片的工具类
 */

public class DownImagUtils {

    private static DownImagUtils model;

    public void setDownImagModel(DownImagModel downImagModel) {
        this.downImagModel = downImagModel;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private DownImagModel downImagModel;
    private Context mContext;
    private String imageUrl;

    private static class Instance {
        public static DownImagUtils downImagModel = new DownImagUtils();
    }

    public static DownImagUtils getInstance() {
        if (model == null) {
            synchronized (DownImagUtils.class) {
                if (model == null) {
                    model = Instance.downImagModel;
                }
            }

        }
        return model;
    }

    private DownImagUtils() {
    }

    public void startDown() {
        Glide.with(mContext).load(imageUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null) {
                    boolean b = false;
                    boolean booleanExist = getBooleanExist(downImagModel.getDownUrl() + downImagModel.getFileName());
                    if (booleanExist) {
                        T.showThort(mContext, "图片已下载");
                        if (downImagModel.getCallBack() != null) {
                            downImagModel.getCallBack().onFail("图片已下载");
                        }
                    } else {
                        b = BitMapFile.saveBitmap2file(resource, downImagModel.getDownUrl() + downImagModel.getFileName());
                    }
                    if (b) {
                        //成功回调
                        if (downImagModel.getCallBack() != null) {
                            downImagModel.getCallBack().onSuccess(downImagModel.getDownUrl() + downImagModel.getFileName());
                        }
                        T.showThort(mContext, "保存到" + downImagModel.getDownUrl() + "目录中");
                        File file = new File(downImagModel.getDownUrl() + downImagModel.getFileName());
                        Uri uri = Build.VERSION.SDK_INT >= 24 ? FileProvider.getUriForFile(mContext, ImagePickerFileProvider.getAuthorities(mContext), file) : Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(file));
                        mContext.sendBroadcast(intent);
                    }
                }
            }
        });

    }

    public boolean getBooleanExist(String fileUrl) {
        File file = new File(fileUrl);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }

    }

}
