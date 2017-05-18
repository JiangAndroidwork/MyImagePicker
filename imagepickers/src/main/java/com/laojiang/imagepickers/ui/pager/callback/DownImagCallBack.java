package com.laojiang.imagepickers.ui.pager.callback;

/**
 * 类介绍（必填）：下载 回调接口
 * Created by Jiang on 2017/5/18 14:31.
 */

public interface DownImagCallBack  {
    void onSuccess(String url);
    void onFail(String message);
}
