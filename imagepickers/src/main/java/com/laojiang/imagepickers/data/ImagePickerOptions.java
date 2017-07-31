package com.laojiang.imagepickers.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片选择各参数
 */

public class ImagePickerOptions implements Parcelable {
    private com.laojiang.imagepickers.data.ImagePickType type = com.laojiang.imagepickers.data.ImagePickType.SINGLE;
    private int maxNum = 1;
    private boolean needCamera = true;
    private boolean needCrop;
    private com.laojiang.imagepickers.data.ImagePickerCropParams cropParams;
    private String cachePath = com.laojiang.imagepickers.data.ImageContants.DEF_CACHE_PATH;
    private boolean needVideo = true;//是否需要视频

    public com.laojiang.imagepickers.data.ImagePickType getType() {
        return type;
    }
    public void setType(com.laojiang.imagepickers.data.ImagePickType type) {
        this.type = type;
    }
    public int getMaxNum() {
        return maxNum;
    }
    public void setMaxNum(int maxNum) {
        if (maxNum > 0)
            this.maxNum = maxNum;
    }

    public boolean isNeedCamera() {
        return needCamera;
    }

    public void setNeedCamera(boolean needCamera) {
        this.needCamera = needCamera;
    }

    public boolean isNeedCrop() {
        return needCrop;
    }

    public void setNeedCrop(boolean needCrop) {
        this.needCrop = needCrop;
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public com.laojiang.imagepickers.data.ImagePickerCropParams getCropParams() {
        return cropParams;
    }

    public void setCropParams(com.laojiang.imagepickers.data.ImagePickerCropParams cropParams) {
        this.cropParams = cropParams;
    }

    public boolean isNeedVideo() {
        return needVideo;
    }

    public void setNeedVideo(boolean needVideo) {
        this.needVideo = needVideo;
    }

    @Override
    public String toString() {
        return "ImagePickerOptions{" +
                "type=" + type +
                ", maxNum=" + maxNum +
                ", needCamera=" + needCamera +
                ", needCrop=" + needCrop +
                ", cropParams=" + cropParams +
                ", cachePath='" + cachePath + '\'' +
                ", needVideo=" + needVideo +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeInt(this.maxNum);
        dest.writeByte(this.needCamera ? (byte) 1 : (byte) 0);
        dest.writeByte(this.needCrop ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.cropParams, flags);
        dest.writeString(this.cachePath);
        dest.writeByte(this.needVideo ? (byte) 1 : (byte) 0);
    }

    public ImagePickerOptions() {
    }

    protected ImagePickerOptions(Parcel in) {
        int tmpMode = in.readInt();
        this.type = tmpMode == -1 ? null : com.laojiang.imagepickers.data.ImagePickType.values()[tmpMode];
        this.maxNum = in.readInt();
        this.needCamera = in.readByte() != 0;
        this.needCrop = in.readByte() != 0;
        this.cropParams = in.readParcelable(com.laojiang.imagepickers.data.ImagePickerCropParams.class.getClassLoader());
        this.cachePath = in.readString();
        this.needVideo = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ImagePickerOptions> CREATOR = new Parcelable.Creator<ImagePickerOptions>() {
        @Override
        public ImagePickerOptions createFromParcel(Parcel source) {
            return new ImagePickerOptions(source);
        }

        @Override
        public ImagePickerOptions[] newArray(int size) {
            return new ImagePickerOptions[size];
        }
    };
}
