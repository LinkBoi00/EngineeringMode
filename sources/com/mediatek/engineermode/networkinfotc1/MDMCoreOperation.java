package com.mediatek.engineermode.networkinfotc1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.os.SystemService;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.mdmcomponent.Utils;
import com.mediatek.mdml.MONITOR_CMD_RESP;
import com.mediatek.mdml.MonitorCmdProxy;
import com.mediatek.mdml.MonitorTrapReceiver;
import com.mediatek.mdml.Msg;
import com.mediatek.mdml.PlainDataDecoder;
import com.mediatek.mdml.TRAP_TYPE;
import com.mediatek.mdml.TrapHandlerInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MDMCoreOperation {
    private static final String MDM_PROP = "persist.vendor.mdmmonitor";
    public static final int MDM_SERVICE_INIT = 0;
    public static final int MSG_UPDATE_UI_URC = 7;
    private static final String SERVICE_NAME = "md_monitor";
    public static final int SUBSCRIBE_DONE = 1;
    private static final String SZ_SERVER_NAME = "demo_receiver";
    private static final String TAG = "MDMCoreOperation";
    public static final int UNSUBSCRIBE_DONE = 4;
    public static final int UPDATE_INTERVAL = 50;
    private static final int WAIT_TIMEOUT = 3000;
    private static MDMCoreOperation single = null;
    private List<Long> mCheckedEmInteger = new ArrayList();
    private List<MdmBaseComponent> mComponents = null;
    /* access modifiers changed from: private */
    public IMDMSeiviceInterface mMDMChangedListener;
    /* access modifiers changed from: private */
    public int mSimTypeShow = 0;
    public Handler mUpdateUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 7:
                    String name = MDMCoreOperation.this.m_plainDataDecoder.msgInfo_getMsgName(Integer.valueOf(msg.arg1));
                    Msg msgObj = (Msg) msg.obj;
                    if (name == null || msgObj == null) {
                        Elog.e(MDMCoreOperation.TAG, "msgObj/name is null");
                        return;
                    } else {
                        MDMCoreOperation.this.mMDMChangedListener.onUpdateMDMData(name, msgObj);
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private MonitorCmdProxy m_cmdProxy;
    /* access modifiers changed from: private */
    public PlainDataDecoder m_plainDataDecoder;
    private long m_sid;
    private MonitorTrapReceiver m_trapReceiver;

    public interface IMDMSeiviceInterface {
        void onUpdateMDMData(String str, Msg msg);

        void onUpdateMDMStatus(int i);
    }

    public static synchronized MDMCoreOperation getInstance() {
        MDMCoreOperation mDMCoreOperation;
        synchronized (MDMCoreOperation.class) {
            if (single == null) {
                single = new MDMCoreOperation();
            }
            mDMCoreOperation = single;
        }
        return mDMCoreOperation;
    }

    public void mdmParametersSeting(List<MdmBaseComponent> components, int simTypeShow) {
        this.mComponents = components;
        this.mSimTypeShow = simTypeShow;
    }

    public void setOnMDMChangedListener(IMDMSeiviceInterface onMDMChangedListener) {
        this.mMDMChangedListener = onMDMChangedListener;
    }

    public void mdmInitialize(Context context) {
        new LoadingTask().execute(new Context[]{context});
    }

    public void mdmlSubscribe() {
        new SubscribeTask().execute(new Void[0]);
    }

    public void mdmlUnSubscribe() {
        new UnSubscribeTask().execute(new Void[0]);
    }

    public void mdmlEnableSubscribe() {
        try {
            this.m_cmdProxy.onEnableTrap(this.m_sid);
        } catch (Exception e) {
            Elog.e(TAG, "MdmCoreOperation.m_cmdProxy.onEnableTrap failed ");
        }
    }

    public List<MdmBaseComponent> getSelectedComponents() {
        return this.mComponents;
    }

    public boolean mdmlClosing() {
        if (MONITOR_CMD_RESP.MONITOR_CMD_RESP_SUCCESS != this.m_cmdProxy.onDisableTrap(this.m_sid)) {
            Elog.d(TAG, "Disable Trap time out");
            return false;
        }
        if (MONITOR_CMD_RESP.MONITOR_CMD_RESP_SUCCESS == this.m_cmdProxy.onCloseSession(this.m_sid)) {
            return true;
        }
        Elog.e(TAG, "Close Session fail");
        return false;
    }

    /* access modifiers changed from: private */
    public void StartMDLService() {
        if (!SystemService.isRunning(SERVICE_NAME)) {
            Elog.v(TAG, "start md_monitor prop");
            SystemProperties.set(MDM_PROP, "1");
            try {
                SystemService.waitForState(SERVICE_NAME, SystemService.State.RUNNING, 3000);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            if (!SystemService.isRunning(SERVICE_NAME)) {
                Elog.e(TAG, "start md_monitor failed time out");
            }
        }
    }

    /* access modifiers changed from: private */
    public void InitMDML(Context context) {
        MonitorCmdProxy monitorCmdProxy = new MonitorCmdProxy(context);
        this.m_cmdProxy = monitorCmdProxy;
        long onCreateSession = monitorCmdProxy.onCreateSession();
        this.m_sid = onCreateSession;
        MonitorTrapReceiver monitorTrapReceiver = new MonitorTrapReceiver(onCreateSession, SZ_SERVER_NAME);
        this.m_trapReceiver = monitorTrapReceiver;
        monitorTrapReceiver.SetTrapHandler(new DemoHandler());
        this.m_cmdProxy.onSetTrapReceiver(this.m_sid, SZ_SERVER_NAME);
    }

    /* access modifiers changed from: private */
    public void InitDecoder(Context context) {
        try {
            this.m_plainDataDecoder = PlainDataDecoder.getInstance((String) null, context);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void subscribeTrap() {
        this.mCheckedEmInteger.clear();
        for (MdmBaseComponent com2 : this.mComponents) {
            String[] emComponentName = com2.getEmComponentName();
            for (int j = 0; j < emComponentName.length; j++) {
                Integer msgIdInt = this.m_plainDataDecoder.msgInfo_getMsgID(emComponentName[j]);
                if (msgIdInt != null) {
                    long msgId = Long.valueOf((long) msgIdInt.intValue()).longValue();
                    if (!this.mCheckedEmInteger.contains(Long.valueOf(msgId))) {
                        this.mCheckedEmInteger.add(Long.valueOf(msgId));
                        Elog.d(TAG, "onSubscribeTrap msg Name = " + emComponentName[j]);
                        this.m_cmdProxy.onSubscribeTrap(this.m_sid, TRAP_TYPE.TRAP_TYPE_EM, msgId);
                    }
                } else {
                    Elog.e(TAG, "The msgid is not support, msgName = " + emComponentName[j]);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void unSubscribeTrap() {
        Elog.d(TAG, "Before unSubscribeTrap..");
        this.mCheckedEmInteger.clear();
        for (MdmBaseComponent com2 : this.mComponents) {
            String[] emComponentName = com2.getEmComponentName();
            for (int j = 0; j < emComponentName.length; j++) {
                Integer msgIdInt = this.m_plainDataDecoder.msgInfo_getMsgID(emComponentName[j]);
                if (msgIdInt != null) {
                    long msgId = Long.valueOf((long) msgIdInt.intValue()).longValue();
                    if (!this.mCheckedEmInteger.contains(Long.valueOf(msgId))) {
                        this.mCheckedEmInteger.add(Long.valueOf(msgId));
                        this.m_cmdProxy.onUnsubscribeTrap(this.m_sid, TRAP_TYPE.TRAP_TYPE_EM, msgId);
                        Elog.d(TAG, "unSubscribeTrap[" + msgIdInt + "] = " + emComponentName[j]);
                    }
                } else {
                    Elog.e(TAG, "The msgid is not support, msgName = " + emComponentName[j]);
                }
            }
        }
    }

    public int getFieldValue(Msg data, String msgName) {
        return (int) Utils.getIntFromByte(data.getFieldValue(msgName));
    }

    public int getFieldValue(Msg data, String msgName, boolean sign) {
        if (!sign) {
            return getFieldValue(data, msgName);
        }
        return (int) Utils.getIntFromByte(data.getFieldValue(msgName), true);
    }

    private class DemoHandler implements TrapHandlerInterface {
        private DemoHandler() {
        }

        public void ProcessTrap(long timestamp, TRAP_TYPE type, int len, byte[] data, int offset) {
            Message msgToUi = MDMCoreOperation.this.mUpdateUiHandler.obtainMessage();
            msgToUi.what = 7;
            int msgID = (int) Utils.getIntFromByte(data, offset + 6, 2);
            Msg msg = MDMCoreOperation.this.m_plainDataDecoder.msgInfo_getMsg(data, offset);
            if (msg == null || msg.getSimIdx() != MDMCoreOperation.this.mSimTypeShow + 1) {
                Elog.d(MDMCoreOperation.TAG, "msg is null in ProcessTrap");
                return;
            }
            Elog.d(MDMCoreOperation.TAG, "frame is incoming: len = [" + len + "], msgID = [" + msgID + "]");
            msgToUi.arg1 = msgID;
            msgToUi.obj = msg;
            MDMCoreOperation.this.mUpdateUiHandler.sendMessageDelayed(msgToUi, 50);
        }
    }

    private class LoadingTask extends AsyncTask<Context, Void, Boolean> {
        private LoadingTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Context... params) {
            Elog.d(MDMCoreOperation.TAG, "before InitMDML");
            MDMCoreOperation.this.StartMDLService();
            MDMCoreOperation.this.InitMDML(params[0]);
            Elog.d(MDMCoreOperation.TAG, "after InitMDML");
            MDMCoreOperation.this.InitDecoder(params[0]);
            Elog.d(MDMCoreOperation.TAG, "after InitDecoder");
            return true;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            Elog.d(MDMCoreOperation.TAG, "LoadingTask done");
            MDMCoreOperation.this.mMDMChangedListener.onUpdateMDMStatus(0);
        }
    }

    private class SubscribeTask extends AsyncTask<Void, Void, Boolean> {
        private SubscribeTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            MDMCoreOperation.this.subscribeTrap();
            return true;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            if (result.booleanValue()) {
                Elog.d(MDMCoreOperation.TAG, "onSubscribeTrap done");
                MDMCoreOperation.this.mMDMChangedListener.onUpdateMDMStatus(1);
            }
        }
    }

    private class UnSubscribeTask extends AsyncTask<Void, Void, Boolean> {
        private UnSubscribeTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            MDMCoreOperation.this.unSubscribeTrap();
            return true;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            Elog.d(MDMCoreOperation.TAG, "onUnSubscribeTrap done");
            MDMCoreOperation.this.mMDMChangedListener.onUpdateMDMStatus(4);
        }
    }
}
