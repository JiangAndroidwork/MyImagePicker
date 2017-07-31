package com.laojiang.myimagepicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.laojiang.imagepickers.data.MediaDataBean;
import com.laojiang.myimagepicker.R;
import com.laojiang.myimagepicker.interfaces.CallBackCloseLisenter;
import com.laojiang.myimagepicker.interfaces.CallBackItemLisenter;
import com.laojiang.myimagepicker.utils.AndroidLifecycleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by donglua on 15/5/31.
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MediaDataBean> photoPaths = new ArrayList<MediaDataBean>();
    private LayoutInflater inflater;

    private Context mContext;
    private CallBackCloseLisenter callBackLisenter;
    private CallBackItemLisenter callBackItemLisenter;
    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;

    public  static int MAX = 9;


    public PhotoAdapter(Context mContext, List<MediaDataBean> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }
    public PhotoAdapter(int maxNum,Context mContext,List<MediaDataBean> photoPaths) {
        this.MAX = maxNum;
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setCallBackLisenter(CallBackCloseLisenter callBackLisenter) {
        this.callBackLisenter = callBackLisenter;
    }

    public void setCallBackItemLisenter(CallBackItemLisenter callBackItemLisenter) {
        this.callBackItemLisenter = callBackItemLisenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType==TYPE_ADD){
            itemView = inflater.inflate(R.layout.item_add, parent, false);
            return new AddViewHolder(itemView);
        }else {
            itemView = inflater.inflate(R.layout.picker_item_photo, parent, false);
            return new PhotoViewHolder(itemView);
        }


    }




    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_PHOTO) {
            Uri uri = Uri.fromFile(new File(photoPaths.get(position).getMediaPath()));

            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(((PhotoViewHolder)holder).ivPhoto.getContext());
            ((PhotoViewHolder)holder).ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackLisenter.onColseButton(v,position);
                }
            });
            ((PhotoViewHolder)holder).ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackItemLisenter.onItemLisenter(v,position);
                }
            });
            if (canLoadImage) {
                Glide.with(mContext)
                        .load(uri)
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                        .error(R.drawable.__picker_ic_broken_image_black_48dp)
                        .into(((PhotoViewHolder)holder).ivPhoto);
            }
        }else if (getItemViewType(position) == TYPE_ADD){
            ((AddViewHolder)holder).addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackItemLisenter.onItemLisenter(v,position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vSelected;
        private ImageView ivClose;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            ivClose = (ImageView) itemView.findViewById(R.id.iv_colse);
            if (vSelected != null) vSelected.setVisibility(View.GONE);
        }
    }
    public static class AddViewHolder extends RecyclerView.ViewHolder{
        private View addItem;
        public AddViewHolder(View itemView) {
            super(itemView);
            addItem = itemView;
        }
    }
}
