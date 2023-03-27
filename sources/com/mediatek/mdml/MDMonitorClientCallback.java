package com.mediatek.mdml;

import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import vendor.mediatek.hardware.mdmonitor.V1_0.IMDMonitorClientCallback;

class MDMonitorClientCallback extends IMDMonitorClientCallback.Stub {
    private static final String TAG = "MDML/MDMonitorClientCallback";
    private HIDLHandlerInterface m_mcpHandlerInstance = null;

    public void SetMCPHandler(HIDLHandlerInterface handlerInstance) {
        this.m_mcpHandlerInstance = handlerInstance;
    }

    public boolean MCPReceiver(ArrayList<Byte> data) throws RemoteException {
        HIDLHandlerInterface hIDLHandlerInterface = this.m_mcpHandlerInstance;
        if (hIDLHandlerInterface != null) {
            hIDLHandlerInterface.DataIn(data);
            Log.d(TAG, "MCPReceiver end");
            return true;
        }
        Log.d(TAG, "MCPReceiver end without handler");
        return false;
    }
}
