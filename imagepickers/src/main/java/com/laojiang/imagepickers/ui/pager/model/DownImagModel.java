package com.laojiang.imagepickers.ui.pager.model;

import android.os.SystemClock;

import com.laojiang.imagepickers.data.ImageContants;
import com.laojiang.imagepickers.ui.pager.callback.DownImagCallBack;


/**
 */

public class DownImagModel {


    private static DownImagModel model;
    private String downUrl = ImageContants.DEFAULT_DOWN_URL;
    private String fileName = "IMG"+ SystemClock.currentThreadTimeMillis()+".jpg";
    private DownImagCallBack callBack;

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DownImagCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(DownImagCallBack callBack) {
        this.callBack = callBack;
    }

    public  DownImagModel() {
    }
//    private static class Instance {
//        public static DownImagModel downImagModel = new DownImagModel();
//    }
//    public static DownImagModel getInstance(){
//        if (model ==null) {
//            synchronized (DownImagModel.class) {
//                if (model == null) {
//                    model = Instance.downImagModel;
//                }
//            }
//
//        }
//        return model;
//    }
}
