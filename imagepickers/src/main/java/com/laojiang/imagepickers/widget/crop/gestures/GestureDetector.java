package com.laojiang.imagepickers.widget.crop.gestures;

import android.view.MotionEvent;

public interface GestureDetector
{
    boolean onTouchEvent(MotionEvent ev);

    boolean isDragging();

    boolean isScaling();

    void setOnGestureListener(com.laojiang.imagepickers.widget.crop.gestures.OnGestureListener listener);
}