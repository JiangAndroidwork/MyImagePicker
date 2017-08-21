package com.laojiang.imagepickers.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Function:图片和视频实体类
 */
public class MediaDataBean implements Parcelable {
    /**
     * 图片id【扫描sd卡后才有的】
     */
    private String imageId="_";
    /**
     * 原图地址
     */
    private String mediaPath;
    /**
     * 最后修改时间
     */
    private Long lastModified;
    /**
     * 图片宽
     */
    private int width;
    /**
     * 图片高
     */
    private int height;
    /**
     * 所在文件夹的id【扫描sd卡后才有的】
     */
    private String floderId;
    /**
     * 文件的类型：0 是图片 1是 视频
     */
    private int type;
    /**
     * 具体adapter的位置
     */
    private int position;

    private int videoLength;

    public int getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MediaDataBean() {
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public String getFloderId() {
        return floderId;
    }

    public void setFloderId(String floderId) {
        this.floderId = floderId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "MediaDataBean{" +
                "imageId='" + imageId + '\'' +
                ", mediaPath='" + mediaPath + '\'' +
                ", lastModified=" + lastModified +
                ", width=" + width +
                ", height=" + height +
                ", floderId='" + floderId + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MediaDataBean && ((MediaDataBean) obj).getImageId().equals(imageId));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageId);
        dest.writeString(this.mediaPath);
        dest.writeValue(this.lastModified);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.floderId);
        dest.writeInt(this.type);
    }

    protected MediaDataBean(Parcel in) {
        this.imageId = in.readString();
        this.mediaPath = in.readString();
        this.lastModified = (Long) in.readValue(Long.class.getClassLoader());
        this.width = in.readInt();
        this.height = in.readInt();
        this.floderId = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<MediaDataBean> CREATOR = new Creator<MediaDataBean>() {
        @Override
        public MediaDataBean createFromParcel(Parcel source) {
            return new MediaDataBean(source);
        }

        @Override
        public MediaDataBean[] newArray(int size) {
            return new MediaDataBean[size];
        }
    };
}
