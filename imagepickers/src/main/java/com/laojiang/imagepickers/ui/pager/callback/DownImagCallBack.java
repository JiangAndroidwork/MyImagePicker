package com.laojiang.imagepickers.ui.pager.callback;

/**
 * 下载 回调接口
 */

public interface DownImagCallBack  {
    void onSuccess(String url);
    void onFail(String message);
}
