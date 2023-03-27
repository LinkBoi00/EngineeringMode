package com.mediatek.engineermode;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Elog {
    public static final void v(String tag, String content) {
        Log.v("EM/" + tag, content);
    }

    public static final void i(String tag, String content) {
        Log.i("EM/" + tag, content);
    }

    public static final void w(String tag, String content) {
        Log.w("EM/" + tag, content);
    }

    public static final void e(String tag, String content) {
        Log.e("EM/" + tag, content);
    }

    public static final void d(String tag, String content) {
        Log.d("EM/" + tag, content);
    }

    public static final void d(String tag, String[] logSet, String content) {
        int length = logSet.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (logSet[i] == null) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss SSS");
                logSet[i] = sdf.format(Long.valueOf(System.currentTimeMillis())) + ":" + content;
                break;
            } else {
                i++;
            }
        }
        if (i >= logSet.length - 1) {
            Log.d("EM/" + tag, Arrays.toString(logSet));
            for (int i2 = 0; i2 < length; i2++) {
                logSet[i2] = null;
            }
        }
    }
}
