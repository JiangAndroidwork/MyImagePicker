package com.laojiang.myimagepicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.laojiang.imagepickers.ImagePicker;
import com.laojiang.imagepickers.data.ImageBean;
import com.laojiang.imagepickers.data.ImagePickType;
import com.laojiang.imagepickers.data.ImagePickerOptions;
import com.laojiang.imagepickers.ui.pager.callback.DownImagCallBack;
import com.laojiang.imagepickers.ui.pager.model.DownImagModel;
import com.laojiang.imagepickers.ui.pager.view.ImagePagerActivity;
import com.laojiang.imagepickers.ui.pager.view.VideoDetailActivity;
import com.laojiang.imagepickers.utils.GlideImagePickerDisplayer;
import com.laojiang.myimagepicker.adapter.PhotoAdapter;
import com.laojiang.myimagepicker.interfaces.CallBackCloseLisenter;
import com.laojiang.myimagepicker.interfaces.CallBackItemLisenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private static final int RESULT_CODE = 202;
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<ImageBean> selectedPhotos = new ArrayList<>();
    private String cachePath;
    private ImagePickerOptions imagePickerOptions;
    private List<ImageBean> resultList;
    private int j= 0;
    private int i = 0;
    private ArrayList<ImageBean> imageList;
    private ArrayList<ImageBean> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        cachePath = Environment.getExternalStorageDirectory() + "/bjhj/file/";
        File file = new File(cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //初始化
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_picker);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoAdapter = new PhotoAdapter(9,this, selectedPhotos);
        recyclerView.setAdapter(photoAdapter);
        photoAdapter.setCallBackLisenter(new CallBackCloseLisenter() {
            @Override
            public void onColseButton(View view,int position) {
               selectedPhotos.remove(position);
                photoAdapter.notifyDataSetChanged();
            }
        });
        imageList = new ArrayList<ImageBean>();
        videoList = new ArrayList<ImageBean>();

        photoAdapter.setCallBackItemLisenter(new CallBackItemLisenter() {
            @Override
            public void onItemLisenter(View view, int position) {
                if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                    addImage();
                } else {

                    ImageBean imageBean = selectedPhotos.get(position);
                    if (imageBean.getType()==0) {
                        //进入照片列表轮播详情
                        DownImagModel model = ImagePagerActivity.start(MainActivity.this, imageList, imageList.get(position).getPosition(), true);
                        model.setFileName("111.jpg");
                        model.setDownUrl(Environment.getExternalStorageDirectory()+"/hh/");
                        model.setCallBack(new DownImagCallBack() {
                            @Override
                            public void onSuccess(String url) {
                                Log.i("下载成功==",url);
                            }

                            @Override
                            public void onFail(String message) {
                                Log.i("下载失败==",message);
                            }
                        });
                    }else {
                        //进入到视频详情页，不需要返回数据
                        VideoDetailActivity.start(MainActivity.this,imageBean);
                    }
                }
            }
        });


    }
    private void addImage() {
        ImagePicker build = new ImagePicker.Builder()
                .pickType(ImagePickType.MUTIL) //设置选取类型(拍照ONLY_CAMERA、单选SINGLE、多选MUTIL)
                .maxNum(9) //设置最大选择数量(此选项只对多选生效，拍照和单选都是1，修改后也无效)
                .needCamera(true) //是否需要在界面中显示相机入口(类似微信那样)
                .cachePath(cachePath) //自定义缓存路径(拍照和裁剪都需要用到缓存)
                .doCrop(1, 1, 300, 300) //裁剪功能需要调用这个方法，多选模式下无效，参数：aspectX,aspectY,outputX,outputY
                .displayer(new GlideImagePickerDisplayer()) //自定义图片加载器，默认是Glide实现的,可自定义图片加载器
                .build();
        build.start(this, REQUEST_CODE, RESULT_CODE); //自定义RequestCode和ResultCode
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE && data != null) {
            //获取选择的图片数据
            resultList = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            Log.i("获取到的图片数据===", resultList.toString());
            selectedPhotos.addAll(resultList);
            photoAdapter.notifyDataSetChanged();
            if (imageList!=null)
                imageList.clear();
            if (videoList!=null)
                videoList.clear();
            i = 0;
            j = 0;
            //区分图片和视频
            for (ImageBean bean:selectedPhotos){
                if (bean.getType()==0){//图片
                    bean.setPosition(i);
                    imageList.add(bean);
                    i++;
                }else{//视频
                    bean.setPosition(j);
                    videoList.add(bean);
                    j++;
                }
            }
        }
    }
}
