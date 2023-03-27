package android.support.v4.os;

import android.os.Handler;
import android.os.Message;

public final class HandlerCompat {
    public static boolean postDelayed(Handler handler, Runnable r, Object token, long delayMillis) {
        if (BuildCompat.isAtLeastP()) {
            return handler.postDelayed(r, token, delayMillis);
        }
        Message message = Message.obtain(handler, r);
        message.obj = token;
        return handler.sendMessageDelayed(message, delayMillis);
    }

    private HandlerCompat() {
    }
}
