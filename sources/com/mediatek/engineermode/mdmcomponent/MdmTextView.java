package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MdmTextView extends TextView {
    private Context mContext;

    public MdmTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    public MdmTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MdmTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (getLayout() != null) {
            setMeasuredDimension(getMeasuredWidth(), ((int) Math.ceil((double) getMaxLineHeight(getText().toString(), View.MeasureSpec.getSize(widthMeasureSpec), mode))) + getCompoundPaddingTop() + getCompoundPaddingBottom());
        }
    }

    private float getMaxLineHeight(String str, int widthSpec, int mode) {
        float height;
        double d;
        float height2 = 0.0f;
        float screenW = (float) ((Activity) this.mContext).getWindowManager().getDefaultDisplay().getWidth();
        float pLeft = (float) ((LinearLayout) getParent()).getPaddingLeft();
        float pRight = (float) ((LinearLayout) getParent()).getPaddingRight();
        float widthPixels = (float) this.mContext.getResources().getDisplayMetrics().widthPixels;
        int k = 0;
        int childNum = ((LinearLayout) getParent()).getChildCount();
        for (int i = 0; i < childNum; i += 2) {
            if (((LinearLayout) getParent()).getChildAt(i).getVisibility() == 0 && i % 2 == 0) {
                k++;
            }
        }
        int k2 = k > 0 ? k : 1;
        String[] contentArray = str.replace("\\r", "\n").replace("\\n", "\n").replace("\\t", "        ").split("\n");
        int line = 0;
        int length = contentArray.length;
        int i2 = 0;
        while (i2 < length) {
            String content = contentArray[i2];
            if (mode == 0) {
                height = height2;
                d = Math.ceil((double) (getPaint().measureText(content) / (((((widthPixels - ((float) getPaddingLeft())) - pLeft) - pRight) - ((float) getPaddingRight())) / ((float) k2))));
            } else {
                height = height2;
                d = Math.ceil((double) (getPaint().measureText(content) / (((screenW - ((float) getPaddingLeft())) - ((float) getPaddingRight())) / ((float) k2))));
            }
            line += (int) d;
            i2++;
            height2 = height;
        }
        return ((getPaint().getFontMetrics().descent - getPaint().getFontMetrics().ascent) * ((float) line)) + dip2px(3.0f);
    }

    private float dip2px(float dipValue) {
        return dipValue * this.mContext.getResources().getDisplayMetrics().density;
    }
}
