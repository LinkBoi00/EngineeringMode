package com.mediatek.engineermode.mcfconfig;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class LinkedHorizontalScrollView extends HorizontalScrollView {
    private LinkScrollChangeListener listener;

    public interface LinkScrollChangeListener {
        void onscroll(LinkedHorizontalScrollView linkedHorizontalScrollView, int i, int i2, int i3, int i4);
    }

    public LinkedHorizontalScrollView(Context context) {
        super(context);
    }

    public LinkedHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkedHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollChangeListener(LinkScrollChangeListener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LinkScrollChangeListener linkScrollChangeListener = this.listener;
        if (linkScrollChangeListener != null) {
            linkScrollChangeListener.onscroll(this, l, t, oldl, oldt);
        }
    }

    public void fling(int velocityY) {
        super.fling(velocityY / 2);
    }
}
