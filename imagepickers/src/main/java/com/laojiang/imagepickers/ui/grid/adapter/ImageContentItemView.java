package com.laojiang.imagepickers.ui.grid.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.laojiang.imagepickers.R;
import com.laojiang.imagepickers.base.adapter.IImagePickerItemView;
import com.laojiang.imagepickers.base.adapter.ImagePickerViewHolder;
import com.laojiang.imagepickers.data.MediaDataBean;
import com.laojiang.imagepickers.data.ImageContants;
import com.laojiang.imagepickers.data.ImageDataModel;
import com.laojiang.imagepickers.data.ImagePickType;
import com.laojiang.imagepickers.data.ImagePickerOptions;
import com.laojiang.imagepickers.ui.grid.view.IImageDataView;


/**
 *
 * TODO 显示图片的GridItem
 */
public class ImageContentItemView implements IImagePickerItemView<MediaDataBean> {
    private IImageDataView mViewImpl;
    private ImagePickerOptions mOptions;
    private com.laojiang.imagepickers.ui.grid.adapter.ImageDataAdapter mAdapter;

    public ImageContentItemView(IImageDataView viewImpl, com.laojiang.imagepickers.ui.grid.adapter.ImageDataAdapter adapter) {
        this.mViewImpl = viewImpl;
        this.mOptions = mViewImpl.getOptions();
        this.mAdapter = adapter;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.layout_image_data_content_listitem;
    }

    @Override
    public boolean isForViewType(MediaDataBean item, int position) {
        return mOptions != null && (!mOptions.isNeedCamera() || (mOptions.isNeedCamera() && position != 0));
    }

    @Override
    public void setData(ImagePickerViewHolder holder, final MediaDataBean mediaDataBean, final int position, ViewGroup parent) {
        ImageView imgContent = holder.findView(R.id.img_imagepicker_grid_content);
        View viewIndicator = holder.findView(R.id.ck_imagepicker_grid_content);

        //显示UI
        if (mediaDataBean != null)
            ImageDataModel.getInstance().getDisplayer()
                    .display(holder.getContext(), mediaDataBean.getMediaPath(), imgContent
                            , R.drawable.glide_default_picture, R.drawable.glide_default_picture
                            , ImageContants.DISPLAY_THUMB_SIZE, ImageContants.DISPLAY_THUMB_SIZE);
        //判断照片还是视频
        int type = mediaDataBean.getType();
        imgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewImpl != null)
                    mViewImpl.onImageClicked(mediaDataBean, position);
            }
        });

        if (mOptions.getType() == ImagePickType.SINGLE) {
            viewIndicator.setVisibility(View.GONE);
        } else {
            viewIndicator.setVisibility(View.VISIBLE);
            if (ImageDataModel.getInstance().hasDataInResult(mediaDataBean))
                viewIndicator.setBackgroundResource(R.drawable.ck_imagepicker_grid_selected);
            else
                viewIndicator.setBackgroundResource(R.drawable.ck_imagepicker_grid_normal);

            viewIndicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curNum = ImageDataModel.getInstance().getResultNum();
                    if (curNum == mOptions.getMaxNum()) {
                        mViewImpl.warningMaxNum();
                        return;
                    } else if (mediaDataBean.getType()==0){
                        if (ImageDataModel.getInstance().hasDataInResult(mediaDataBean))
                            ImageDataModel.getInstance().delDataFromResult(mediaDataBean);
                        else
                            ImageDataModel.getInstance().addDataToResult(mediaDataBean);
                        mAdapter.notifyDataSetChanged();
                        mViewImpl.onSelectNumChanged(ImageDataModel.getInstance().getResultNum());
                    }
                }
            });
        }
        if (type==1){
            viewIndicator.setVisibility(View.GONE);
        }else {
            if (mOptions.getType() == ImagePickType.SINGLE)
                viewIndicator.setVisibility(View.GONE);
            else
                viewIndicator.setVisibility(View.VISIBLE);
        }
    }
}
