package com.laojiang.imagepickers.ui.grid.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laojiang.imagepickers.R;
import com.laojiang.imagepickers.base.adapter.IImagePickerItemView;
import com.laojiang.imagepickers.base.adapter.ImagePickerViewHolder;
import com.laojiang.imagepickers.data.ImagePickerOptions;
import com.laojiang.imagepickers.ui.grid.view.IImageDataView;


/**
 *
 * TODO 相机入口ItemView
 */
public class ImageCameraItemView implements IImagePickerItemView
{
    private IImageDataView mViewImpl;
    private ImagePickerOptions mOptions;

    public ImageCameraItemView(IImageDataView viewImpl)
    {
        this.mViewImpl = viewImpl;
        this.mOptions = mViewImpl.getOptions();
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.layout_image_data_camera_listitem;
    }

    @Override
    public boolean isForViewType(Object item, int position)
    {
        return mOptions != null && mOptions.isNeedCamera() && position == 0;
    }

    @Override
    public void setData(ImagePickerViewHolder holder, Object o, int position, ViewGroup parent)
    {
        TextView tvShow = holder.getConvertView().findViewById(R.id.tv_first_text);
        if (mOptions!=null&&mOptions.isNeedVideo()){
            tvShow.setText("拍照或者摄像");
        }else {
            tvShow.setText("拍照");
        }
        holder.setClickListener(R.id.fl_image_data_camera, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mViewImpl.startTakePhoto();
            }
        });
    }
}
