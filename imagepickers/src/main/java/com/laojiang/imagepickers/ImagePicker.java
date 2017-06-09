package com.laojiang.imagepickers;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.laojiang.imagepickers.data.ImagePickerOptions;


/**
 * TODO 调用方法的入口
 */

public class ImagePicker {
    /**
     * 默认的ResultCode
     */
    public static final int DEF_RESULT_CODE = 136;

    /**
     * 返回结果中包含图片数据的Intent的键值
     */
    public static final String INTENT_RESULT_DATA = "ImageBeans";

    private com.laojiang.imagepickers.data.ImagePickerOptions mOptions;

    public ImagePickerOptions getmOptions() {
        return mOptions;
    }

    private ImagePicker() {
    }

    private ImagePicker(com.laojiang.imagepickers.data.ImagePickerOptions options) {
        this.mOptions = options;
    }

    /**
     * 发起选择图片
     *
     * @param activity    发起的Activity
     * @param requestCode 请求码
     * @param resultCode  结果码
     */
    public void start(Activity activity, int requestCode, int resultCode) {
        Intent intent = new Intent(activity, com.laojiang.imagepickers.ui.grid.view.ImageDataActivity.class);
        intent.putExtra(com.laojiang.imagepickers.data.ImageContants.INTENT_KEY_OPTIONS, mOptions);
        intent.putExtra(com.laojiang.imagepickers.data.ImageContants.INTENT_KEY_RESULTCODE, resultCode);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 发起选择图片
     *
     * @param fragment    发起的Fragment
     * @param requestCode 请求码
     * @param resultCode  结果码
     */
    public void start(Fragment fragment, int requestCode, int resultCode) {
        Intent intent = new Intent(fragment.getActivity(), com.laojiang.imagepickers.ui.grid.view.ImageDataActivity.class);
        intent.putExtra(com.laojiang.imagepickers.data.ImageContants.INTENT_KEY_OPTIONS, mOptions);
        intent.putExtra(com.laojiang.imagepickers.data.ImageContants.INTENT_KEY_RESULTCODE, resultCode);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static final class Builder {
        private com.laojiang.imagepickers.data.ImagePickerOptions mOptions;

        public Builder() {
            mOptions = new com.laojiang.imagepickers.data.ImagePickerOptions();
        }

        public Builder pickType(com.laojiang.imagepickers.data.ImagePickType mode) {
            mOptions.setType(mode);
            return this;
        }

        public Builder maxNum(int maxNum) {
            mOptions.setMaxNum(maxNum);
            return this;
        }

        public Builder needCamera(boolean b) {
            mOptions.setNeedCamera(b);
            return this;
        }

        public Builder cachePath(String path) {
            mOptions.setCachePath(path);
            return this;
        }

        public Builder doCrop(com.laojiang.imagepickers.data.ImagePickerCropParams cropParams) {
            mOptions.setNeedCrop(cropParams != null);
            mOptions.setCropParams(cropParams);
            return this;
        }

        public Builder doCrop(int aspectX, int aspectY, int outputX, int outputY) {
            mOptions.setNeedCrop(true);
            mOptions.setCropParams(new com.laojiang.imagepickers.data.ImagePickerCropParams(aspectX, aspectY, outputX, outputY));
            return this;
        }

        public Builder displayer(com.laojiang.imagepickers.utils.IImagePickerDisplayer displayer) {
            com.laojiang.imagepickers.data.ImageDataModel.getInstance().setDisplayer(displayer);
            return this;
        }
        public Builder needVideo(boolean need){
            mOptions.setNeedVideo(need);
            return this;
        }
        public ImagePicker build() {
            return new ImagePicker(mOptions);
        }
    }
}
