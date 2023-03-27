package com.mediatek.engineermode.boot;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.mediatek.engineermode.Elog;
import java.util.HashMap;
import java.util.Map;

public class EmBootStartService extends Service {
    private static final String KEY_REQ_START_SERV = "req_start_serv";
    private static final int MSG_CHECK_STOP_SERVICE = 111;
    private static final String TAG = "BootStartService";
    /* access modifiers changed from: private */
    public static Map<String, IBootServiceHandler> sStartReqHandlerMap = new HashMap();
    private Handler mMainHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    synchronized (EmBootStartService.class) {
                        Elog.d(EmBootStartService.TAG, "size: " + EmBootStartService.sStartReqHandlerMap.size());
                        if (EmBootStartService.sStartReqHandlerMap.size() == 0) {
                            EmBootStartService.this.stopSelf();
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    };

    public IBinder onBind(Intent intent) {
        return null;
    }

    private void stopStartedService(IBootServiceHandler handler) {
        if (handler != null) {
            String handlerName = handler.getClass().getCanonicalName();
            synchronized (EmBootStartService.class) {
                sStartReqHandlerMap.remove(handlerName);
            }
        }
        this.mMainHandler.sendEmptyMessage(111);
    }

    private int onHandleRequest(String handlerName) {
        IBootServiceHandler handler = sStartReqHandlerMap.get(handlerName);
        if (handler == null) {
            return 100;
        }
        return handler.handleStartRequest(this);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return 2;
        }
        String handlerName = intent.getStringExtra(KEY_REQ_START_SERV);
        if (onHandleRequest(handlerName) == 1) {
            return 2;
        }
        stopStartedService(sStartReqHandlerMap.get(handlerName));
        return 2;
    }
}
