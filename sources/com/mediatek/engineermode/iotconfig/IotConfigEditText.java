package com.mediatek.engineermode.iotconfig;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class IotConfigEditText extends EditText {
    public IotConfigEditText(Context context) {
        super(context, (AttributeSet) null);
    }

    public IotConfigEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IotConfigEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onTextContextMenuItem(int id) {
        if (id == 16908322) {
            return true;
        }
        return super.onTextContextMenuItem(id);
    }
}
