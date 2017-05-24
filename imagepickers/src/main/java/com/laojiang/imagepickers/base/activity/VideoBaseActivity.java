package com.laojiang.imagepickers.base.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.laojiang.imagepickers.R;

/**
 * 视频基类
 */

public abstract class VideoBaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected View mContentView;
    protected Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        beforSetContentView(savedInstanceState);
        if (mContentView == null)
            mContentView = getLayoutInflater().inflate(getContentViewResId(), null);
        setContentView(mContentView);

        mHandler = new Handler(getMainLooper());
        initUI(mContentView);
        initData();
    }

    protected void beforSetContentView(Bundle savedInstanceState)
    {
        //去掉ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //改变状态栏颜色
        com.laojiang.imagepickers.utils.ImagePickerComUtils.changeStatusBarColor(this, getResources().getColor(R.color.imagepicker_statusbar));
    }

    /**
     * 查找View
     */
    protected <T extends View> T findView(int resId)
    {
        if (mContentView != null)
            return (T) mContentView.findViewById(resId);
        else
            return null;
    }

    /**
     * 添加点击监听到onClick()中
     */
    protected void addClick(View view)
    {
        if (view != null)
            view.setOnClickListener(this);
    }

    /**
     * 添加点击监听到onClick()中
     */
    protected void addClick(int id)
    {
        View view = findViewById(id);
        addClick(view);
    }

    /**
     * 弹出Toast
     *
     * @param resId 文字提示的资源id
     */
    public void showShortToast(final int resId)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(VideoBaseActivity.this, resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 弹出Toast
     *
     * @param msg 文字提示的字符串
     */
    public void showShortToast(final String msg)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(VideoBaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        onClick(v, v.getId());
    }

    protected abstract int getContentViewResId();

    protected abstract void initUI(View contentView);

    protected abstract void initData();

    protected abstract void onClick(View v, int id);
}
