# MyImagePicker
> 照片选择器仿照微信，适配安卓7.0。
## 1，添加依赖
**在工程的build.gradle文件中添加:**
``` 

    
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
**在moudle 中build.gradle中添加依赖：**
```
 compile 'com.github.JiangAndroidwork:MyImagePicker:1.3.0'
 修复图片预览crash问题
 ```
## 2,用法：
> 基本用法：
```
ImagePicker build = new ImagePicker.Builder()
                .pickType(ImagePickType.MUTIL) //设置选取类型(拍照ONLY_CAMERA、单选SINGLE、多选MUTIL)
                .maxNum(9) //设置最大选择数量(此选项只对多选生效，拍照和单选都是1，修改后也无效)
                .needCamera(true) //是否需要在界面中显示相机入口(类似微信那样)
                .cachePath(cachePath) //自定义缓存路径(拍照和裁剪都需要用到缓存)
                .doCrop(1, 1, 300, 300) //裁剪功能需要调用这个方法，多选模式下无效，参数：aspectX,aspectY,outputX,outputY
                .displayer(new GlideImagePickerDisplayer()) //自定义图片加载器，默认是Glide实现的,可自定义图片加载器
                .needVido(true)//是否显示视频
                .build();
      
        build.start(this, REQUEST_CODE, RESULT_CODE); //自定义RequestCode和ResultCode
```
>接收数据：
```
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE && data != null) {
            //获取选择的图片数据
            List<MediaDataBean> resultList = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
        }
    }
```
### 点击进入已选图片列表轮播详情：

####按是否需要下载到本地为条件有两种构造方法：
> 第一种，只需要传递三个参数，不需要有下载功能
```
  ImagePagerActivity.start(MainActivity.this, selectedPhotos, position);
```
**第一个参数：activity** 

**第二个参数:照片集合(类型：ImageBean)**

**第三个参数：当前点击图片的位置**
> 第二种，传递四个参数：

有下载到本地功能默认下载到Pictures目录下
```
DownImagModel model = ImagePagerActivity.start(MainActivity.this, imageList, imageList.get(position).getPosition(), true);
```
如果需要设置下载的路径和图片名称需要设置参数并设置监听：
```
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
```

### 进入到视频播放详情页：
```
VideoDetailActivity.start(MainActivity.this,imageBean);
```

库依赖：
 >compile 'com.github.chrisbanes:PhotoView:1.3.1'
 
 >compile 'com.github.bumptech.glide:glide:3.7.0'
 
 >compile 'com.github.JiangAndroidwork:MyCamera:1.1'


### 版本：
1，compile 'com.github.JiangAndroidwork:MyImagePicker:1.3.0'  ：细化功能，区分拍照和摄像。