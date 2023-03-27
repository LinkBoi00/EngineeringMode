package com.mediatek.mdml;

import android.content.Context;
import android.util.Log;

public class MonitorCmdProxy implements CommandInterface {
    private static final String TAG = "MDML/MonitorCmdProxy";
    private JsonCmdEncoder mJsonCmdEncoder;

    public MonitorCmdProxy(String strServerName, Context context) {
        Log.d(TAG, "MonitorCmdProxy Constructor start ");
        this.mJsonCmdEncoder = new JsonCmdEncoder(new HIDLMCPTransmitter(strServerName), context);
        Log.d(TAG, "MonitorCmdProxy Constructor end");
    }

    public MonitorCmdProxy(Context context) {
        this("com.mediatek.mdmonitor.command", context);
    }

    public long onCreateSession() {
        return this.mJsonCmdEncoder.createSession();
    }

    public MONITOR_CMD_RESP onCloseSession(long sessionId) {
        return this.mJsonCmdEncoder.closeSession(sessionId);
    }

    public MONITOR_CMD_RESP onSubscribeTrap(long sessionId, TRAP_TYPE type, long msgId) {
        return this.mJsonCmdEncoder.subscribeTrap(sessionId, type, msgId);
    }

    public MONITOR_CMD_RESP onSubscribeMultiTrap(long sessionId, TRAP_TYPE type, long[] msgId) {
        return this.mJsonCmdEncoder.subscribeMultiTrap(sessionId, type, msgId);
    }

    public MONITOR_CMD_RESP onUnsubscribeTrap(long sessionId, TRAP_TYPE type, long msgId) {
        return this.mJsonCmdEncoder.unsubscribeTrap(sessionId, type, msgId);
    }

    public MONITOR_CMD_RESP onUnsubscribeMultiTrap(long sessionId, TRAP_TYPE type, long[] msgId) {
        return this.mJsonCmdEncoder.unsubscribeMultiTrap(sessionId, type, msgId);
    }

    public MONITOR_CMD_RESP onEnableTrap(long sessionId) {
        return this.mJsonCmdEncoder.enableTrap(sessionId);
    }

    public MONITOR_CMD_RESP onDisableTrap(long sessionId) {
        return this.mJsonCmdEncoder.disableTrap(sessionId);
    }

    public MONITOR_CMD_RESP onSetTrapReceiver(long sessionId, String szServerName) {
        return this.mJsonCmdEncoder.setTrapReceiver(sessionId, szServerName);
    }
}
