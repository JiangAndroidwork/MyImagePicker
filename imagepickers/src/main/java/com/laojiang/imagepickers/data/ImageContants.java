package com.laojiang.imagepickers.data;


import android.os.Environment;

import com.laojiang.imagepickers.utils.ImagePickerComUtils;

/**
 * 常量
 */
public class ImageContants
{
    /**
     * 界面跳转options的键值
     */
    public static final String INTENT_KEY_OPTIONS = "options";

    /**
     * 界面跳转resultCode的键值
     */
    public static final String INTENT_KEY_RESULTCODE = "resultCode";

    /**
     * 传递数据的key
     */
    public static final String INTENT_KEY_DATA = "dataList";

    /**
     * 传递起始位置的key
     */
    public static final String INTENT_KEY_START_POSITION = "startP";

    /**
     * 传递Pager页面中是否为预览模式的key
     */
    public static final String INTENT_KEY_IS_PREVIEW = "isPreview";

    /**
     * 传递待裁剪图片路径的key
     */
    public static final String INTENT_KEY_ORIGIN_PATH = "originPath";

    /**
     * 裁剪后图片路径的key
     */
    public static final String INTENT_KEY_CROP_PATH = "cropPath";

    /**
     * “所有图片”文件夹的id
     */
    public static final String ID_ALL_IMAGE_FLODER = "-100";
    public static final String ID_ALL_VIDEO_FLODER = "-200";

    /**
     * 默认缓存路径
     */
    public static final String DEF_CACHE_PATH = ImagePickerComUtils.getSdPath() + "/";

    /**
     * 展示小图时最大分辨率
     */
    public static final int DISPLAY_THUMB_SIZE = 300;

    /**
     * sdk23获取sd卡读写权限的requestCode
     */
    public static final int REQUEST_CODE_PERMISSION_SDCARD = 110;

    /**
     * sdk23获取sd卡拍照权限的requestCode
     */
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 111;

    /**
     * 拍照请求码
     */
    public static final int REQUEST_CODE_TAKE_PHOTO = 112;

    /**
     * 裁剪请求码
     */
    public static final int REQUEST_CODE_CROP = 113;

    /**
     * 预览请求码
     */
    public static final int REQUEST_CODE_PREVIEW = 114;

    /**
     * 看大图请求码
     */
    public static final int REQUEST_CODE_DETAIL = 115;
    /**
     * 视频详情返回请求码
     */
    public static final int REQUEST_CODE_VIDEO = 220;
    /**
     * 裁剪结果码
     */
    public static final int RESULT_CODE_CROP_OK = 116;

    /**
     * 代表操作完成的ResultCode
     */
    public static final int RESULT_CODE_OK = 123;
    /**
     * 自定义照相请求code
     *
     */
    public static final int CAMERA_REQUEST = 124;
    /**
     * 自定义摄像请求code
     *
     */
    public static final int CAMERA_SHEXIANG_REQUEST = 125;
    //自定义摄像 返回吗
    public static final int RESULT_CODE_IMAGE = 126;

    /**
     * 拍照后图片名字前缀
     */
    public static final String PHOTO_NAME_PREFIX = "IMG_";

    /**
     * 裁剪后图片名字前缀
     */
    public static final String CROP_NAME_PREFIX = "CROP_";

    /**
     * 图片文件名后缀
     */
    public static final String IMG_NAME_POSTFIX = ".jpg";
    /**
     * 是否显示底部选中栏
     */
    public static final String IS_SHOW_BOTTOM = "showbottom";
    /**
     * 是否需要下载功能
     */
    public static final String IS_NEED_DOWN = "isneeddown";
    /**
     * 默认下载路径
     */
    public static final String DEFAULT_DOWN_URL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/";
    /**
     * 自定义照相
     */
    public static final String DIY_CAMERA_PATH = "diycamerapath";
    /**
     * 自定义摄像
     */
    public static final String DIY_CAMERA_SHEXIANG_PATH = "diycamerapath";

}
