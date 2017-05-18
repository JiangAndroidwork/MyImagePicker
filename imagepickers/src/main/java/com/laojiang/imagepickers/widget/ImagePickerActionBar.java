package com.laojiang.imagepickers.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.laojiang.imagepickers.R;


/**
 * 自定义ActionBar
 */
public class ImagePickerActionBar extends FrameLayout {
    private TextView mTvTitle;
    private Button btComplete;
    private TextView btDown;
    //    private TextView mTvPreview;

    public ImagePickerActionBar(Context context) {
        super(context);
        init(context, null);
    }

    public ImagePickerActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        inflate(context, R.layout.layout_image_picker_actionbar, this);
        setWillNotDraw(false);

        mTvTitle = (TextView) findViewById(R.id.tv_imagepicker_actionbar_title);
//        mTvPreview = (TextView) findViewById(R.id.tv_imagepicker_actionbar_preview);
        btComplete = (Button) findViewById(R.id.btn_image_data_ok);
        btDown = (TextView) findViewById(R.id.bt_down);

        TextView tvBack = (TextView) findViewById(R.id.tv_imagepicker_actionbar_back);

        if (context instanceof Activity) {
            tvBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });


        }
    }

    /**
     * 是否需要下载
     *
     * @param isNeed
     */
    public void setNeedDown(boolean isNeed) {
        if (isNeed) {
            btComplete.setVisibility(GONE);
            btDown.setVisibility(VISIBLE);
        } else {
            btComplete.setVisibility(VISIBLE);
            btDown.setVisibility(GONE);
        }

    }

    /**
     * 设置标题
     */
    public void setTitle(String s) {
        if (mTvTitle != null)
            mTvTitle.setText(s);
    }

    /**
     * 设置标题
     */
    public void setTitle(int resId) {
        if (mTvTitle != null)
            mTvTitle.setText(resId);
    }

    public void setBtComplete(String resId) {
        if (btComplete != null) {
            btComplete.setText(resId);
        }
    }

    /**
     * 隐藏预览入口
     */
    public void hidePreview() {
        if (btComplete != null)
            btComplete.setVisibility(View.GONE);
    }

    /**
     * 显示预览入口
     */
    public void showPreview() {
        if (btComplete != null)
            btComplete.setVisibility(View.VISIBLE);
    }

    /**
     * 设置预览入口是否可点击
     */
    public void enablePreview(boolean b) {
        if (btComplete != null)
            btComplete.setEnabled(b);
    }

    /**
     * 预览入口点击监听
     */
    public void setOnPreviewClickListener(OnClickListener listener) {
        if (btComplete != null)
            btComplete.setOnClickListener(listener);
    }

    /**
     * 下载按钮的点击事件监听
     * @param listener
     */
    public void setOnDownClickListener(OnClickListener listener) {
        if (btDown!=null)
            btDown.setOnClickListener(listener);
    }
}
