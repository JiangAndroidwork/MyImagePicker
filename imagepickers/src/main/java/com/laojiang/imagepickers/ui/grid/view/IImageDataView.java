package com.laojiang.imagepickers.ui.grid.view;


import com.laojiang.imagepickers.base.activity.IImageBaseView;
import com.laojiang.imagepickers.data.MediaDataBean;
import com.laojiang.imagepickers.data.ImageFloderBean;
import com.laojiang.imagepickers.data.ImagePickerOptions;

import java.util.List;

/**
 *
 * TODO ImageDataActivity的View层接口
 */

public interface IImageDataView extends IImageBaseView
{
    ImagePickerOptions getOptions();

    void startTakePhoto();

    void showLoading();

    void hideLoading();

    void onDataChanged(List<MediaDataBean> dataList);

    void onFloderChanged(ImageFloderBean floderBean);

    void onImageClicked(MediaDataBean mediaDataBean, int position);

    void onSelectNumChanged(int curNum);

    void warningMaxNum();
}
