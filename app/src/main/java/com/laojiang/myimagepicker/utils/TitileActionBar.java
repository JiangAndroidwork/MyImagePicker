package com.laojiang.myimagepicker.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.laojiang.myimagepicker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类介绍（必填）：
 * Created by Jiang on 2017/5/24 17:19.
 */

public class TitileActionBar extends FrameLayout {
    private int photoSelect = 0;//0只有相机 1，单图片 2，多图片
    private int cameraState = 0;//0不需要相机，1需要
    private int cropState = 0;//0,不需要裁剪,1需要
    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;
    private ArrayAdapter<String> adapter_1;
    private ArrayAdapter<String> adapter_2;
    private ArrayAdapter<String> adapter_3;

    public TitileActionBar(Context context) {
        super(context);
        init(context,null);
    }

    public TitileActionBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.layout_demo_action_bar, this);
        setWillNotDraw(false);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);

        List<String> data_1 = new ArrayList<>();
        data_1.add("只有相机");
        data_1.add("单图片");
        data_1.add("多图片");
        List<String> data_2 = new ArrayList<>();
        data_2.add("不需相机");
        data_2.add("需要相机");
        List<String> data_3 = new ArrayList<>();
        data_3.add("不需裁减");
        data_3.add("需要裁减");

        //适配器
        adapter_1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data_1);
        //设置样式
        adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(adapter_1);

        //适配器
        adapter_2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data_2);
        //设置样式
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner2.setAdapter(adapter_2);

        //适配器
        adapter_3 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data_3);
        //设置样式
        adapter_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner3.setAdapter(adapter_3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photoSelect = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cameraState= position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cropState = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取照片选择状态
     * @return
     */
    public int getPhotoSelect() {
        return photoSelect;
    }

    /**
     * 获取是否需要相机状态
     * @return
     */
    public int getCameraState() {
        return cameraState;
    }

    /**
     * 获取是否需要截取状态
     * @return
     */
    public int getCropState() {
        return cropState;
    }
}
