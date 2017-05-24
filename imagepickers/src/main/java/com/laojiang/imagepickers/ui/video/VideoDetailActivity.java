package com.laojiang.imagepickers.ui.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.laojiang.imagepickers.R;
import com.laojiang.imagepickers.base.activity.VideoBaseActivity;
import com.laojiang.imagepickers.data.ImageBean;
import com.laojiang.imagepickers.data.ImageContants;
import com.laojiang.imagepickers.data.ImageDataModel;
import com.laojiang.imagepickers.widget.ImagePickerActionBar;

import static com.laojiang.imagepickers.data.ImageContants.REQUEST_CODE_VIDEO;

/**
 * 视频详情页
 */

public class VideoDetailActivity extends VideoBaseActivity {
    private static final String VIDEO_URL = "video_url";
    private static final String IMAGEBEAN = "imageBean";
    private static final String ISCALLBACK = "call_back";
    private String videoStr;
    private ImagePickerActionBar mActionBar;
    private ImageView videoBG;
    private ImageView mPlayer;
    private ImageBean videoBean;
    private boolean isCallBack;
    private static Activity activity;
    /**
     *
     * @param activity
     * @param imageBean  图片基类
     * @param requestCode  请求码
     */
    public static void start(Activity activity, ImageBean imageBean, int requestCode) {

        Intent intent = new Intent(activity, VideoDetailActivity.class);
        intent.putExtra(VIDEO_URL, imageBean.getImagePath());
        intent.putExtra(IMAGEBEAN, imageBean);
        intent.putExtra(ISCALLBACK, requestCode == REQUEST_CODE_VIDEO);
        activity.startActivityForResult(intent, requestCode);
    }



    public static void start(Activity activity,ImageBean imageBean){
        Intent intent = new Intent(activity,VideoDetailActivity.class);
        intent.putExtra(VIDEO_URL, imageBean.getImagePath());
        intent.putExtra(IMAGEBEAN, imageBean);
        intent.putExtra(ISCALLBACK, false);
        activity.startActivity(intent);
    }
    @Override
    protected void beforSetContentView(Bundle savedInstanceState) {
        super.beforSetContentView(savedInstanceState);
        Intent intent = getIntent();
        try {
            videoStr = intent.getStringExtra(VIDEO_URL);
            videoBean = intent.getParcelableExtra(IMAGEBEAN);
            isCallBack = intent.getBooleanExtra(ISCALLBACK, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initUI(View contentView) {
        mActionBar = findView(R.id.acb_image_data);
        videoBG = findView(R.id.iv_video);
        mPlayer = findView(R.id.iv_play);
        mActionBar.setBtComplete(getString(R.string.btn_video_ok));
        mActionBar.setEnabled(true);
        mActionBar.enablePreview(true);
        mActionBar.setOnPreviewClickListener(this);
        //默认图片
        Glide.with(this).load(videoStr).into(videoBG);
        if (isCallBack) {
            mActionBar.showPreview();
        } else {
            mActionBar.hidePreview();
        }
       addClick(R.id.iv_play);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onClick(View v, int id) {
        if (id == R.id.iv_play) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(videoStr);
            intent.setDataAndType(uri,"video/*");
            startActivity(intent);
        }else {
            ImageDataModel.getInstance().addDataToVideoResult(videoBean);
            setResult(ImageContants.RESULT_CODE_OK);
            finish();
        }
    }



}
