package com.laojiang.imagepickers.ui.grid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.laojiang.imagepickers.base.adapter.IImagePickerItemView;
import com.laojiang.imagepickers.base.adapter.ImagePickerBaseAdapter;
import com.laojiang.imagepickers.base.adapter.ImagePickerViewHolder;
import com.laojiang.imagepickers.data.MediaDataBean;
import com.laojiang.imagepickers.data.ImagePickerOptions;
import com.laojiang.imagepickers.ui.grid.view.IImageDataView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * TODO GridView适配器
 */
public class ImageDataAdapter extends ImagePickerBaseAdapter<MediaDataBean>
{
    private ImagePickerOptions mOptions;
    //每个元素的宽高
    private int mImageLayoutSize;

    public ImageDataAdapter(Context context, IImageDataView viewImpl)
    {
        super(context, null);
        mOptions = viewImpl.getOptions();

    //创建子布局
        if (mOptions.isNeedCamera())
            addItemView(new ImageCameraItemView(viewImpl));
        addItemView((IImagePickerItemView<MediaDataBean>) new ImageContentItemView(viewImpl, this));

        //计算每个元素的宽高
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        int cols = screenWidth / densityDpi;
        cols = cols < 3 ? 3 : cols;
        int columnSpace = (int) (2 * context.getResources().getDisplayMetrics().density);
        mImageLayoutSize = (screenWidth - columnSpace * (cols - 1)) / cols;
    }

    @Override
    protected void onCreateConvertView(int position, View convertView, ImagePickerViewHolder holder, ViewGroup parent)
    {
        super.onCreateConvertView(position, convertView, holder, parent);
        //设置每个item为正方形
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mImageLayoutSize));
    }

    @Override
    public void refreshDatas(List<MediaDataBean> datas)
    {
        List<MediaDataBean> datalist = new ArrayList<>();

        datalist.addAll(datas);
        //如果有相机入口要在数据集合0号位插入一条空数据
        if (mOptions.isNeedCamera())
            datalist.add(0, null);
        super.refreshDatas(datalist);
    }
}
