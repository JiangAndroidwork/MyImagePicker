package com.laojiang.imagepickers.ui.grid.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.laojiang.imagepickers.ImagePicker;
import com.laojiang.imagepickers.R;
import com.laojiang.imagepickers.base.activity.ImagePickerBaseActivity;
import com.laojiang.imagepickers.data.ImageContants;
import com.laojiang.imagepickers.data.ImageDataModel;
import com.laojiang.imagepickers.data.ImageFloderBean;
import com.laojiang.imagepickers.data.ImagePickType;
import com.laojiang.imagepickers.data.ImagePickerOptions;
import com.laojiang.imagepickers.data.MediaDataBean;
import com.laojiang.imagepickers.ui.camera.DiyCameraActivity;
import com.laojiang.imagepickers.ui.video.VideoDetailActivity;
import com.laojiang.imagepickers.utils.ImagePickerComUtils;
import com.laojiang.imagepickers.utils.PermissionChecker;
import com.laojiang.imagepickers.utils.TakePhotoCompatUtils;

import java.util.ArrayList;
import java.util.List;

import static com.laojiang.imagepickers.data.ImageContants.REQUEST_CODE_PERMISSION_CAMERA;
import static com.laojiang.imagepickers.data.ImageContants.REQUEST_CODE_PERMISSION_SDCARD;
import static com.laojiang.imagepickers.data.ImageContants.REQUEST_CODE_VIDEO;
import static com.laojiang.imagepickers.data.ImageContants.RESULT_CODE_OK;
import static com.laojiang.imagepickers.utils.PermissionChecker.checkPermissions;


/**
 * 展示图片数据的Activity
 */
public class ImageDataActivity extends ImagePickerBaseActivity implements IImageDataView
        , com.laojiang.imagepickers.ui.grid.view.ImageFloderPop.onFloderItemClickListener {


    private com.laojiang.imagepickers.ui.grid.presenter.ImageDataPresenter mPresenter;
    private ImagePickerOptions mOptions;
    //    private ImagePickType mPickType;
    private int mResultCode;

    private com.laojiang.imagepickers.widget.ImagePickerActionBar mActionBar;
    private GridView mGridView;
    private ProgressBar mPgbLoading;
    private View mViewBottom;
    private View mViewFloder;
    private TextView mTvFloderName;
    //    private Button mBtnOk;
    private com.laojiang.imagepickers.ui.grid.adapter.ImageDataAdapter mAdapter;
    private ImageFloderBean mCurFloder;
    private String mPhotoPath;
    private View tvPreview;

    @Override
    protected void beforSetContentView(Bundle savedInstanceState) {
        super.beforSetContentView(savedInstanceState);
        Intent intent = getIntent();
        mOptions = intent.getParcelableExtra(ImageContants.INTENT_KEY_OPTIONS);
        mResultCode = intent.getIntExtra(ImageContants.INTENT_KEY_RESULTCODE, ImagePicker.DEF_RESULT_CODE);
    }

    @Override
    protected int getContentViewResId() {
        mPresenter = new com.laojiang.imagepickers.ui.grid.presenter.ImageDataPresenter(mOptions,this);
        return R.layout.activity_image_data;
    }

    @Override
    protected void initUI(View contentView) {
        if (mOptions == null) {
            showShortToast(R.string.error_imagepicker_lack_params);
            finish();
            return;
        }

        ViewStub viewStub = findView(R.id.vs_image_data);
        viewStub.inflate();
        mActionBar = findView(R.id.acb_image_data);
        tvPreview = findView(R.id.tv_imagepicker_actionbar_preview);
        if (mOptions.getType() == ImagePickType.ONLY_CAMERA) {
            if (mOptions.isNeedVideo()) {
                mActionBar.setTitle(R.string.imagepicker_title_take_photo);
            }else {
                mActionBar.setTitle(R.string.imagepicker_only_camera);
            }
//            mActionBar.hidePreview();
            tvPreview.setVisibility(View.GONE);
            startTakePhoto();
        } else {
            mActionBar.setTitle(R.string.imagepicker_title_select_image);

            mGridView = findView(R.id.gv_image_data);
            mPgbLoading = findView(R.id.pgb_image_data);
            mViewBottom = findView(R.id.fl_image_data_bottom);
            mViewFloder = findView(R.id.ll_image_data_bottom_floder);
            mTvFloderName = findView(R.id.tv_image_data_bottom_flodername);
//            mBtnOk = findView(R.id.btn_image_data_ok);

            mViewFloder.setOnClickListener(this);
            if (mOptions.getType() == ImagePickType.SINGLE) {
                tvPreview.setVisibility(View.GONE);
                mActionBar.hidePreview();
                tvPreview.setVisibility(View.GONE);
            } else {
                mActionBar.showPreview();
                mActionBar.setOnPreviewClickListener(this);
                tvPreview.setVisibility(View.VISIBLE);
                tvPreview.setOnClickListener(this);
                onSelectNumChanged(0);
            }
        }
    }

    @Override
    protected void initData() {
        if (mOptions == null)
            return;

        if (mOptions.getType() != ImagePickType.ONLY_CAMERA) {
            mAdapter = new com.laojiang.imagepickers.ui.grid.adapter.ImageDataAdapter(this, this);
            mGridView.setAdapter(mAdapter);
            doScanData();
        }
    }

    @Override
    public ImagePickerOptions getOptions() {
        return mOptions;
    }

    @Override
    public void startTakePhoto() {
        if (!ImagePickerComUtils.isSdExist()) {
            showShortToast(R.string.error_no_sdcard);
            return;
        }

        boolean hasPermissions = checkPermissions(this
                , new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , REQUEST_CODE_PERMISSION_CAMERA, R.string.dialog_imagepicker_permission_camera_message);
        //有权限就直接拍照
        if (hasPermissions)
            doTakePhoto();
    }

    //执行拍照的方法
    private void doTakePhoto() {
        if (mOptions.isNeedVideo()){
            DiyCameraActivity.start(this,mOptions.getCachePath());
        }else {
            //调用系统拍照
        mPhotoPath = TakePhotoCompatUtils.takePhoto(this, ImageContants.REQUEST_CODE_TAKE_PHOTO, mOptions.getCachePath());
        }


    }

    //执行扫描sd卡的方法
    private void doScanData() {
        if (!ImagePickerComUtils.isSdExist()) {
            showShortToast(R.string.error_no_sdcard);
            return;
        }

        boolean hasPermission = checkPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_PERMISSION_SDCARD, R.string.dialog_imagepicker_permission_sdcard_message);
        //有权限直接扫描
        if (hasPermission)
            mPresenter.scanData(this);
    }

    @Override
    public void showLoading() {
        if (mPgbLoading != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mPgbLoading.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideLoading() {
        if (mPgbLoading != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mPgbLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onDataChanged(final List<MediaDataBean> dataList) {
        if (mGridView != null && mAdapter != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mGridView.setVisibility(View.VISIBLE);
                    mAdapter.refreshDatas(dataList);
                    mGridView.setSelection(0);
                }
            });
        }
    }

    @Override
    public void onFloderChanged(ImageFloderBean floderBean) {
        if (mCurFloder != null && floderBean != null && mCurFloder.equals(floderBean))
            return;

        mCurFloder = floderBean;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mTvFloderName != null)
                    mTvFloderName.setText(mCurFloder.getFloderName());
            }
        });
        mPresenter.checkDataByFloder(floderBean);
    }

    @Override
    public void onImageClicked(MediaDataBean mediaDataBean, int position) {
        if (mOptions.getType() == ImagePickType.SINGLE) {
            if (mOptions.isNeedCrop()) {
                if (mediaDataBean.getMediaPath().contains(".gif")||mediaDataBean.getMediaPath().contains(".GIF")){
                    returnSingleImage(mediaDataBean);
                }else {
                    //执行裁剪
                    com.laojiang.imagepickers.ui.crop.ImageCropActivity.start(this, mediaDataBean.getMediaPath(), mOptions);
                }
            } else {
                returnSingleImage(mediaDataBean);
            }
        } else {
            //当 类型是图片的时候
            if (mediaDataBean.getType() == 0) {
                //去查看大图的界面
                //如果有相机入口需要调整传递的数据
                int p = position;
                ArrayList<MediaDataBean> dataList = new ArrayList<>();
                List<MediaDataBean> datas = mAdapter.getDatas();

                if (mOptions.isNeedCamera()) {
                    p--;
                    for (int i = 1; i < datas.size(); i++) {
                        if (datas.get(i).getType() == 0) {
                            dataList.add(datas.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < datas.size(); i++) {
                        if (datas.get(i).getType() == 0) {
                            dataList.add(datas.get(i));
                        }
                    }
                }
                com.laojiang.imagepickers.ui.pager.view.ImagePagerActivity.start(this, dataList, p, mOptions, ImageContants.REQUEST_CODE_DETAIL);
            }else {//点击 进入视频
                VideoDetailActivity.start(this, mediaDataBean,REQUEST_CODE_VIDEO);
            }
        }
    }

    @Override
    public void onSelectNumChanged(int curNum) {
        mActionBar.setBtComplete(getString(R.string.btn_imagepicker_ok, String.valueOf(curNum), String.valueOf(mOptions.getMaxNum())));
        if (curNum == 0) {
            mActionBar.setEnabled(false);
            mActionBar.enablePreview(false);
            tvPreview.setEnabled(false);
        } else {
            mActionBar.setEnabled(true);
            mActionBar.enablePreview(true);
            tvPreview.setEnabled(true);
        }
    }

    @Override
    public void warningMaxNum() {
        showShortToast(getString(R.string.warning_imagepicker_max_num, String.valueOf(mOptions.getMaxNum())));
    }

    @Override
    protected void onClick(View v, int id) {
        if (id == R.id.tv_imagepicker_actionbar_preview) {
            //去预览界面
            com.laojiang.imagepickers.ui.pager.view.ImagePagerActivity.start(this, (ArrayList<MediaDataBean>) ImageDataModel.getInstance().getResultList()
                    , 0, mOptions, ImageContants.REQUEST_CODE_PREVIEW);
        } else if (id == R.id.ll_image_data_bottom_floder) {
            //弹出文件夹切换菜单
            new com.laojiang.imagepickers.ui.grid.view.ImageFloderPop().showAtBottom(this, mContentView, mCurFloder, this);
        } else if (id == R.id.btn_image_data_ok) {
            //返回选中的图片
            returnAllSelectedImages();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("ImagePicker", "ImageDataActivity.onActivityResult--->requestCode=" + requestCode
                + ",resultCode=" + resultCode + ",data=" + data);

        //拍照返回
        if (requestCode == ImageContants.REQUEST_CODE_TAKE_PHOTO) {
            if (resultCode != RESULT_OK) {
                Log.e("ImagePicker", "ImageDataActivity take photo result not OK !!!");
                if (mOptions.getType() == ImagePickType.ONLY_CAMERA)
                    finish();
                return;
            }

            Log.i("ImagePicker", "ImageDataActivity take photo result OK--->" + mPhotoPath);
            //非多选模式下需要判断是否有裁剪的需求
            if (mOptions.getType() != ImagePickType.MUTIL && mOptions.isNeedCrop()) {
                //执行裁剪
                com.laojiang.imagepickers.ui.crop.ImageCropActivity.start(this, mPhotoPath, mOptions);
            } else {
                returnSingleImage(mPresenter.getImageBeanByPath(mPhotoPath));
            }
        }
        //裁剪返回
        if (requestCode == ImageContants.REQUEST_CODE_CROP) {
            if (resultCode == ImageContants.RESULT_CODE_CROP_OK) {
                //裁剪成功返回数据
                String cropPath = data.getStringExtra(ImageContants.INTENT_KEY_CROP_PATH);
                returnSingleImage(mPresenter.getImageBeanByPath(cropPath));
            } else if (mOptions.getType() == ImagePickType.ONLY_CAMERA) {
                finish();
            }
        }
        //预览或者大图界面返回
        else if (requestCode == ImageContants.REQUEST_CODE_PREVIEW
                || requestCode == ImageContants.REQUEST_CODE_DETAIL) {
            if (resultCode == RESULT_CODE_OK) {
                returnAllSelectedImages();
            } else {
                //刷新视图
                mAdapter.notifyDataSetChanged();
                onSelectNumChanged(ImageDataModel.getInstance().getResultNum());
            }
        }else if (requestCode ==ImageContants.REQUEST_CODE_VIDEO){
            if (resultCode == RESULT_CODE_OK) {
                retunVideoBack();
            } else {
                //刷新视图
                mAdapter.notifyDataSetChanged();
                onSelectNumChanged(ImageDataModel.getInstance().getResultNum());
            }
        }else if (resultCode==ImageContants.RESULT_CODE_IMAGE){//自定义 拍照请求码
            if (data==null) return;
            String path = data.getStringExtra(ImageContants.DIY_CAMERA_PATH);
            MediaDataBean bean = new MediaDataBean();
            bean.setType(0);
            bean.setMediaPath(path);
            mPhotoPath = path;
            returnSingleImage(bean);
            if (mOptions.getType() != ImagePickType.MUTIL && mOptions.isNeedCrop()) {
                //执行裁剪
                com.laojiang.imagepickers.ui.crop.ImageCropActivity.start(this, mPhotoPath, mOptions);
            } else {
                returnSingleImage(bean);
            }
        }else if (resultCode==ImageContants.CAMERA_SHEXIANG_REQUEST){//自定义 摄像请求码
            if (data==null) return;
            String path = data.getStringExtra(ImageContants.DIY_CAMERA_SHEXIANG_PATH);
            ArrayList<MediaDataBean> resultList = new ArrayList<>();
            MediaDataBean bean = new MediaDataBean();
            bean.setType(1);
            bean.setMediaPath(path);
            resultList.add(bean);
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA, resultList);
            setResult(mResultCode, intent);
            finish();
        }
    }

    /**
     * 视频选择完毕返回
     */
    private void retunVideoBack() {
        ArrayList<MediaDataBean> resultList = new ArrayList<>();
        resultList.addAll(ImageDataModel.getInstance().getmResultVideoList());

        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA, resultList);
        setResult(mResultCode, intent);
        finish();
    }

    @Override
    public void onFloderItemClicked(ImageFloderBean floderBean) {
        onFloderChanged(floderBean);
    }

    //返回单张图片数据
    private void returnSingleImage(MediaDataBean mediaDataBean) {
        ArrayList<MediaDataBean> list = new ArrayList<>();
        list.add(mediaDataBean);
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA, list);
        setResult(mResultCode, intent);
        finish();
    }

    //返回所有已选中的图片
    private void returnAllSelectedImages() {
        ArrayList<MediaDataBean> resultList = new ArrayList<>();
        resultList.addAll(ImageDataModel.getInstance().getResultList());

        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA, resultList);
        setResult(mResultCode, intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean[] result;
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_CAMERA:
                if (mOptions.getType() == ImagePickType.ONLY_CAMERA) {
                    result = PermissionChecker.onRequestPermissionsResult(this, permissions, grantResults, true
                            , R.string.dialog_imagepicker_permission_camera_nerver_ask_message);
                    if (result[0])
                        doTakePhoto();
                    else if (!result[1])
                        finish();
                } else {
                    result = PermissionChecker.onRequestPermissionsResult(this, permissions, grantResults, false
                            , R.string.dialog_imagepicker_permission_camera_nerver_ask_message);
                    if (result[0])
                        doTakePhoto();
                }
                break;
            case REQUEST_CODE_PERMISSION_SDCARD:
                result = PermissionChecker.onRequestPermissionsResult(this, permissions, grantResults, false
                        , R.string.dialog_imagepicker_permission_sdcard_nerver_ask_message);
                //                if (result[0])
                //                    mPresenter.scanData(this);
                //无论成功失败都去扫描，以便更新视图
                mPresenter.scanData(this);
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }
}
