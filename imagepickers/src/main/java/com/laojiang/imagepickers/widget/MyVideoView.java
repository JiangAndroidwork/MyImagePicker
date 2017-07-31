package com.laojiang.imagepickers.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 类介绍（必填）：
 * Created by Jiang on 2017/7/31 16:22.
 */

public class MyVideoView extends VideoView {
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);

    }
}
