package com.laojiang.imagepickers.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 工具类
 */
public class HHYBusinessUtils {

    private static HHYBusinessUtils instance;

    private HHYBusinessUtils(){}

    public static HHYBusinessUtils getInstance(){
        if(instance == null){
            instance = new HHYBusinessUtils();
        }
        return instance;
    }

    public final int SDK_VERSION_ECLAIR = 5;
    public final int SDK_VERSION_DONUT = 4;
    public final int SDK_VERSION_CUPCAKE = 3;






   public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                List<Object> list = (List<Object>) msg.obj;
                ((ImageView)list.get(0)).setImageBitmap((Bitmap)list.get(1));
                ((TextView)list.get(3)).setText(list.get(2).toString());
            }catch (Exception e){

            }
        }
   };
    //String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void setVideoThumbnail(final String url, final int width, final int height , final ImageView imageView , final TextView durationTxt) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                int kind = MediaStore.Video.Thumbnails.MINI_KIND;
                String duration = "";
                try {
                    if (Build.VERSION.SDK_INT >= 14) {
                        //http:////182.92.158.132//cloudfile//public//outlesson//1//video_20160624133000651.mp4
                        //http://182.92.158.132/cloudfile/public/microclass/files20160530133418182/app通知_20160530133418410.mp4
                        retriever.setDataSource(url, new HashMap<String, String>());
                    } else {
                        retriever.setDataSource(url);
                    }
                    bitmap = retriever.getFrameAtTime();
                    duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        retriever.release();
                    } catch (RuntimeException ex) {
                        ex.printStackTrace();
                    }
                }

                String d2 = "";
                try{
                    d2= DateUtil.timeStr(Long.parseLong(duration)/1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(bitmap == null){
                }
                if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                            ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                }
                Message msg = new Message();
                List<Object> list = new ArrayList<Object>();
                list.add(imageView);
                list.add(bitmap);
                list.add(d2);
                list.add(durationTxt);
                msg.obj = list;
                handler.sendMessage(msg);
            }
        }).start();
    }



    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }






}
