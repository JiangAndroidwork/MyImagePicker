package com.laojiang.imagepickers.utils;


import com.laojiang.imagepickers.data.MediaDataBean;

import java.util.Comparator;

/**
 * Function:对本地图片先后顺序排列的比较器
 */
public class ImageComparator implements Comparator<MediaDataBean>
{
    @Override
    public int compare(MediaDataBean first, MediaDataBean second)
    {
        long fModify = first.getLastModified();
        long sModify = second.getLastModified();
        if (fModify > sModify)
            return -1;
        else if (fModify < sModify)
            return 1;
        else
            return 0;
    }
}
