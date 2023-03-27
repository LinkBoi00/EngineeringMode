package android.support.v4.database;

import android.database.CursorWindow;
import android.os.Build;
import android.support.v4.os.BuildCompat;

public final class CursorWindowCompat {
    private CursorWindowCompat() {
    }

    public CursorWindow create(String name, long windowSizeBytes) {
        if (BuildCompat.isAtLeastP()) {
            return new CursorWindow(name, windowSizeBytes);
        }
        if (Build.VERSION.SDK_INT >= 15) {
            return new CursorWindow(name);
        }
        return new CursorWindow(false);
    }
}
