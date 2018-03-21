package com.laojiang.imagepickers.ui.pager.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.laojiang.imagepickers.R;
import com.laojiang.imagepickers.base.activity.ImagePickerBaseActivity;
import com.laojiang.imagepickers.data.MediaDataBean;
import com.laojiang.imagepickers.data.ImageContants;
import com.laojiang.imagepickers.data.ImageDataModel;
import com.laojiang.imagepickers.data.ImagePickerOptions;
import com.laojiang.imagepickers.ui.pager.adapter.ImagePagerAdapter;
import com.laojiang.imagepickers.ui.pager.model.DownImagModel;
import com.laojiang.imagepickers.ui.pager.model.DownImagUtils;
import com.laojiang.imagepickers.utils.ImagePickerComUtils;
import com.laojiang.imagepickers.widget.ImagePickerActionBar;
import com.laojiang.imagepickers.widget.ImagePickerViewPager;

import java.util.ArrayList;

/**
 * 滑动查看图片的基类Activity
 */
public class ImagePagerActivity extends ImagePickerBaseActivity {
    private ArrayList<MediaDataBean> mDataList;
    private int mCurPosition;
    private boolean mIsPreview;
    private ImagePickerOptions mOptions;
    private ImagePickerViewPager mViewPager;
    private ImagePickerActionBar mActionBar;
    private View mViewBottom;
    private CheckBox mCkSelected;
    private Button mBtnOk;
    private ImagePagerAdapter mAdapter;
    private boolean booleanExtra;
    private View tvPreview;
    private boolean isNeedDown;
    private static DownImagModel downImagModel;

    /**
     * 跳转到该界面的公共方法
     *
     * @param activity      发起跳转的Activity
     * @param dataList      数据List
     * @param startPosition 初始展示的位置
     * @param options       核心参数
     * @param requestCode   请求码
     */
    public static void start(Activity activity, ArrayList<MediaDataBean> dataList, int startPosition, ImagePickerOptions options, int requestCode) {
        Intent intent = new Intent(activity, ImagePagerActivity.class);
        intent.putExtra(ImageContants.INTENT_KEY_START_POSITION, startPosition);
        intent.putExtra(ImageContants.INTENT_KEY_OPTIONS, options);
        intent.putExtra(ImageContants.INTENT_KEY_IS_PREVIEW, requestCode == ImageContants.REQUEST_CODE_PREVIEW);
        intent.putParcelableArrayListExtra(ImageContants.INTENT_KEY_DATA, dataList);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * @param isShowBottom  是否显示底部选中栏
     * @param activity
     * @param dataList
     * @param startPosition
     * @param options
     * @param requestCode
     */
    public static void start(boolean isShowBottom, Activity activity, ArrayList<MediaDataBean> dataList, int startPosition, ImagePickerOptions options, int requestCode) {
        Intent intent = new Intent(activity, ImagePagerActivity.class);
        intent.putExtra(ImageContants.INTENT_KEY_START_POSITION, startPosition);
        intent.putExtra(ImageContants.INTENT_KEY_OPTIONS, options);
        intent.putExtra(ImageContants.INTENT_KEY_IS_PREVIEW, requestCode == ImageContants.REQUEST_CODE_PREVIEW);
        intent.putParcelableArrayListExtra(ImageContants.INTENT_KEY_DATA, dataList);
        intent.putExtra(ImageContants.IS_SHOW_BOTTOM, isShowBottom);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(Activity activity, ArrayList<MediaDataBean> dataList, int startPosition) {
        Intent intent = new Intent(activity, ImagePagerActivity.class);
        intent.putExtra(ImageContants.INTENT_KEY_START_POSITION, startPosition);
//        intent.putExtra(ImageContants.INTENT_KEY_OPTIONS, options);
        intent.putExtra(ImageContants.INTENT_KEY_IS_PREVIEW, true);
        intent.putParcelableArrayListExtra(ImageContants.INTENT_KEY_DATA, dataList);
        intent.putExtra(ImageContants.IS_SHOW_BOTTOM, false);
        activity.startActivityForResult(intent, 114);
    }

    /**
     * @param activity
     * @param dataList      图片集合
     * @param startPosition 当前的位置
     * @param
     */
    public static DownImagModel start(Activity activity, ArrayList<MediaDataBean> dataList, int startPosition, boolean isNeedDown) {
        Intent intent = new Intent(activity, ImagePagerActivity.class);
        intent.putExtra(ImageContants.INTENT_KEY_START_POSITION, startPosition);
//        intent.putExtra(ImageContants.INTENT_KEY_OPTIONS, options);
        intent.putExtra(ImageContants.INTENT_KEY_IS_PREVIEW, true);
        intent.putParcelableArrayListExtra(ImageContants.INTENT_KEY_DATA, dataList);
        intent.putExtra(ImageContants.IS_SHOW_BOTTOM, false);
        intent.putExtra(ImageContants.IS_NEED_DOWN, true);
        activity.startActivityForResult(intent, 114);
        downImagModel = new DownImagModel();
        return downImagModel;
    }

    @Override
    protected void beforSetContentView(Bundle savedInstanceState) {
        super.beforSetContentView(savedInstanceState);
        Intent intent = getIntent();
        mCurPosition = intent.getIntExtra(ImageContants.INTENT_KEY_START_POSITION, 0);
        mIsPreview = intent.getBooleanExtra(ImageContants.INTENT_KEY_IS_PREVIEW, false);
        mDataList = intent.getParcelableArrayListExtra(ImageContants.INTENT_KEY_DATA);
        mOptions = intent.getParcelableExtra(ImageContants.INTENT_KEY_OPTIONS);
        booleanExtra = intent.getBooleanExtra(ImageContants.IS_SHOW_BOTTOM, true);
        isNeedDown = intent.getBooleanExtra(ImageContants.IS_NEED_DOWN, false);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_image_pager;
    }

    @Override
    protected void initUI(View contentView) {
        mActionBar = findView(R.id.acb_image_pager);
        mViewPager = findView(R.id.vp_image_pager);
        mViewBottom = findView(R.id.fl_image_pager_bottom);
        mCkSelected = findView(R.id.ck_image_pager);
        tvPreview = findView(R.id.tv_imagepicker_pager_preview);
        //是否是照片选择器所用的
        if (booleanExtra) {
            mViewBottom.setVisibility(View.VISIBLE);
            mActionBar.showPreview();
        } else {
            mViewBottom.setVisibility(View.GONE);
            if (isNeedDown) {
                mActionBar.setNeedDown(true);
                mActionBar.setOnDownClickListener(this);
            } else {
                mActionBar.hidePreview();
            }

        }
        //本身是预览界面就需要关闭预览窗口
        if (mIsPreview) {
            tvPreview.setVisibility(View.GONE);
        } else {
            tvPreview.setVisibility(View.VISIBLE);
            tvPreview.setOnClickListener(this);
        }
        mActionBar.setVisibility(View.GONE);
        mActionBar.setOnPreviewClickListener(this);
    }

    @Override
    protected void initData() {
        mAdapter = new ImagePagerAdapter(this, mDataList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setCurrentItem(mCurPosition, false);
        mAdapter.setPhotoViewClickListener(new ImagePagerAdapter.PhotoViewClickListener() {
            @Override
            public void OnPhotoTapListener(View view, float v, float v1, int position) {
                onImageSingleTap(position);
            }
        });

        updateCheckBoxStatus();
        updateActionbarTitle();
        onSelectNumChanged();
    }

    @Override
    protected void onClick(View v, int id) {
        if (id == R.id.tv_imagepicker_pager_preview) {
            //去预览界面
            ImagePagerActivity.start(this, (ArrayList<MediaDataBean>) ImageDataModel.getInstance().getResultList()
                    , 0, mOptions, ImageContants.REQUEST_CODE_PREVIEW);

        } else if (id == R.id.btn_image_data_ok) {
            //返回上级界面选择完毕
            setResult(ImageContants.RESULT_CODE_OK);
            finish();
        }else if (id==R.id.bt_down){
            //下载按钮
            DownImagUtils utils = DownImagUtils.getInstance();
            utils.setDownImagModel(downImagModel);
            utils.setImageUrl(mDataList.get(mCurPosition).getMediaPath());
            utils.setmContext(this);
            downImagModel.setFileName(mDataList.get(mCurPosition).getImageId()+".jpg");
            utils.startDown();


        }
    }

    private ViewPager.SimpleOnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mCurPosition = position;
            if (mDataList != null && position < mDataList.size()) {
                updateActionbarTitle();
                updateCheckBoxStatus();
            }
            Log.i("轮播详情图片类型==", mDataList.get(position).getType() + "");
        }
    };

    //更新Title
    private void updateActionbarTitle() {
        if (mActionBar != null) {
            mActionBar.setTitle(String.valueOf(mCurPosition + 1)+"/"+ String.valueOf(mDataList.size()));
        }
    }

    //更新当前图片选中状态
    private void updateCheckBoxStatus() {
        if (mCkSelected != null) {
            mCkSelected.setOnCheckedChangeListener(null);//取消监听，以免冲突
            mCkSelected.setChecked(ImageDataModel.getInstance().hasDataInResult(mDataList.get(mCurPosition)));
            mCkSelected.setOnCheckedChangeListener(mCkChangeListener);
        }
    }

    //更新按钮的文案
    private void onSelectNumChanged() {
        int resultNum = ImageDataModel.getInstance().getResultNum();
        if (booleanExtra) {
            mActionBar.setBtComplete(getString(R.string.btn_imagepicker_ok, String.valueOf(resultNum), String.valueOf(mOptions.getMaxNum())));
        }
        if (resultNum == 0) {
            tvPreview.setEnabled(false);
            mActionBar.enablePreview(false);
        } else {
            tvPreview.setEnabled(true);
            mActionBar.enablePreview(true);
        }
    }

    private CompoundButton.OnCheckedChangeListener mCkChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (ImageDataModel.getInstance().getResultNum() == mOptions.getMaxNum()) {
                    showShortToast(getString(R.string.warning_imagepicker_max_num, String.valueOf(mOptions.getMaxNum())));
                    mCkSelected.setOnCheckedChangeListener(null);//取消监听，以免冲突
                    mCkSelected.setChecked(false);
                    mCkSelected.setOnCheckedChangeListener(mCkChangeListener);
                } else {
                    ImageDataModel.getInstance().addDataToResult(mDataList.get(mCurPosition));
                    onSelectNumChanged();
                }
            } else {
                ImageDataModel.getInstance().delDataFromResult(mDataList.get(mCurPosition));
                onSelectNumChanged();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageContants.REQUEST_CODE_PREVIEW) {
            if (resultCode == ImageContants.RESULT_CODE_OK) {
                setResult(ImageContants.RESULT_CODE_OK);
                finish();
            } else {
                //从预览界面回来需要刷新视图
                updateCheckBoxStatus();
                onSelectNumChanged();
            }
        }
    }

    //根据单击来隐藏/显示头部和底部的布局
    private void onImageSingleTap(int type) {
        if (mActionBar == null || mViewBottom == null)
            return;
        if (mActionBar.getVisibility() == View.VISIBLE) {
            mActionBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.imagepicker_actionbar_dismiss));
            mViewBottom.setAnimation(AnimationUtils.loadAnimation(this, R.anim.imagepicker_bottom_dismiss));
            mActionBar.setVisibility(View.GONE);
            mViewBottom.setVisibility(View.GONE);
            //更改状态栏为透明
            ImagePickerComUtils.changeStatusBarColor(this, getResources().getColor(R.color.imagepicker_transparent));
            //给最外层布局加上这个属性表示，Activity全屏显示，且状态栏被隐藏覆盖掉。
            if (Build.VERSION.SDK_INT >= 16)
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            mActionBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.imagepicker_actionbar_show));
            mViewBottom.setAnimation(AnimationUtils.loadAnimation(this, R.anim.imagepicker_bottom_show));
            mActionBar.setVisibility(View.VISIBLE);

            if (booleanExtra) {
                mViewBottom.setVisibility(View.VISIBLE);
            } else {
                mViewBottom.setVisibility(View.GONE);
            }

            MediaDataBean mediaDataBean = mDataList.get(type);

            //改回状态栏颜色
            ImagePickerComUtils.changeStatusBarColor(this, getResources().getColor(R.color.imagepicker_statusbar));
            //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
            if (Build.VERSION.SDK_INT >= 16)
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }


}
