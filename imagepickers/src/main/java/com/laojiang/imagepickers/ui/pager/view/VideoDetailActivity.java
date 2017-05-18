package com.laojiang.imagepickers.ui.pager.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.laojiang.imagepickers.R;
import com.laojiang.imagepickers.data.ImageBean;
import com.laojiang.imagepickers.data.ImageContants;
import com.laojiang.imagepickers.data.ImageDataModel;
import com.laojiang.imagepickers.widget.ImagePickerActionBar;
import com.superplayer.library.SuperPlayer;

import static com.laojiang.imagepickers.data.ImageContants.REQUEST_CODE_VIDEO;
import static com.laojiang.imagepickers.utils.HHYBusinessUtils.handler;

/**
 * 类介绍（必填）：视频播放界面
 * Created by Jiang on 2017/5/17 14:58.
 */

public class VideoDetailActivity extends Activity implements SuperPlayer.OnNetChangeListener, View.OnClickListener {

    private static final String VIDEO_URL = "video_url";
    private static final String IMAGEBEAN = "video_bean";
    private static final String ISCALLBACK = "isCallback";//是否需要返回数据
    private SuperPlayer player;
    private String url;
    private int TIME = 120000;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };
    private boolean isFull;
    private ImagePickerActionBar mActionBar;
    private ImageBean videoBean;
    private boolean isCallBack;

    /**
     *
     * @param activity
     * @param imageBean
     * @param requestCode
     */
    public static void start(Activity activity, ImageBean imageBean, int requestCode) {
        Intent intent = new Intent(activity, VideoDetailActivity.class);
        intent.putExtra(VIDEO_URL, imageBean.getImagePath());
        intent.putExtra(IMAGEBEAN, imageBean);
        intent.putExtra(ISCALLBACK, requestCode == REQUEST_CODE_VIDEO);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 不需要返回数据 进入到视频详情页
     * @param activity
     * @param imageBean
     */
    public static void start(Activity activity, ImageBean imageBean) {
        Intent intent = new Intent(activity, VideoDetailActivity.class);
        intent.putExtra(VIDEO_URL, imageBean.getImagePath());
        intent.putExtra(IMAGEBEAN, imageBean);
        intent.putExtra(ISCALLBACK, false);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        player = (SuperPlayer) findViewById(R.id.video);
        initData();
        initView();
    }

    private void initData() {
        try {
            Intent intent = getIntent();
            url = intent.getStringExtra(VIDEO_URL);
            videoBean = intent.getParcelableExtra(IMAGEBEAN);
            isCallBack = intent.getBooleanExtra(ISCALLBACK, true);

        } catch (Exception e) {
            Log.i("播放视频==", e.getMessage());
        }
    }

    private void initView() {
        mActionBar = (ImagePickerActionBar) findViewById(R.id.acb_image_data);
        mActionBar.setBtComplete(getString(R.string.btn_video_ok));
        mActionBar.setEnabled(true);
        mActionBar.enablePreview(true);
        mActionBar.setOnPreviewClickListener(this);
        if (isCallBack) {
            mActionBar.showPreview();
        } else {
            mActionBar.hidePreview();
        }
        player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                }).onComplete(new Runnable() {
            @Override
            public void run() {
                /**
                 * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                 */
            }
        }).onInfo(new SuperPlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                /**
                 * 监听视频的相关信息。
                 */

            }
        }).onError(new SuperPlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                /**
                 * 监听视频播放失败的回调
                 */

            }
        }).setTitle(url)//设置视频的titleName
                .play(url);//开始播放视频
        player.setScaleType(SuperPlayer.SCALETYPE_FITXY);
        player.setPlayerWH(0, player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置

    }

    @Override
    public void onWifi() {
        Toast.makeText(this, "当前网络环境是WIFI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobile() {
        Toast.makeText(this, "当前网络环境是手机网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        Toast.makeText(this, "网络链接断开", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {


        Toast.makeText(this, "无网络链接", Toast.LENGTH_SHORT).show();
    }

    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.onDestroy();
        }
        handler.removeCallbacks(runnable);
        //plateId
        //changeStudentStates(String.valueOf(plateId) , String.valueOf(parentId), "离线" );
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }

        /*if(!isFull){
            super.onBackPressed();
        }*/
        super.onBackPressed();
        if (Integer.valueOf(android.os.Build.VERSION.SDK) > 5) {
            overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (mActionBar.getVisibility()==View.VISIBLE){
                mActionBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.imagepicker_actionbar_dismiss));
                mActionBar.setVisibility(View.GONE);
            }
            //横屏
            isFull = true;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mActionBar.getVisibility()==View.GONE){
                mActionBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.imagepicker_actionbar_show));
                mActionBar.setVisibility(View.VISIBLE);
            }
            isFull = false;
        }
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onClick(View v) {
        ImageDataModel.getInstance().addDataToVideoResult(videoBean);
        setResult(ImageContants.RESULT_CODE_OK);
        finish();
    }
}
