package com.mediatek.engineermode.mcfconfig;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class McfFileSelectListView extends ListView implements View.OnTouchListener, AbsListView.OnScrollListener {
    private static final int MAXIMUM_LIST_ITEMS_VIEWABLE = 99;
    private int listViewTouchAction = -1;

    public McfFileSelectListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
        setOnTouchListener(this);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getAdapter() != null && getAdapter().getCount() > 99 && this.listViewTouchAction == 2) {
            scrollBy(0, -1);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newHeight = 0;
        int newWidth = 0;
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode != 1073741824) {
            ListAdapter listAdapter = getAdapter();
            if (listAdapter != null && !listAdapter.isEmpty()) {
                int listPosition = 0;
                while (listPosition < listAdapter.getCount() && listPosition < 99) {
                    View listItem = listAdapter.getView(listPosition, (View) null, this);
                    if (listItem instanceof ViewGroup) {
                        listItem.setLayoutParams(new AbsListView.LayoutParams(-2, -2));
                    }
                    listItem.measure(widthMeasureSpec, heightMeasureSpec);
                    newHeight += listItem.getMeasuredHeight();
                    if (newWidth < listItem.getMeasuredWidth()) {
                        newWidth = listItem.getMeasuredWidth();
                    }
                    listPosition++;
                }
                newHeight += getDividerHeight() * listPosition;
            }
            if (heightMode == Integer.MIN_VALUE && newHeight > heightSize && newHeight > heightSize) {
                newHeight = heightSize;
            }
        } else {
            newHeight = getMeasuredHeight();
            newWidth = getMeasuredWidth();
        }
        setMeasuredDimension(newWidth, newHeight);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (getAdapter() != null && getAdapter().getCount() > 99 && this.listViewTouchAction == 2) {
            scrollBy(0, 1);
        }
        return false;
    }
}
