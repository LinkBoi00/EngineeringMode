package com.mediatek.mdml;

import android.os.IHwBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.NoSuchElementException;
import vendor.mediatek.hardware.mdmonitor.V1_0.IMDMonitorService;

class HIDLMCPTransmitter {
    private static final String TAG = "MDML/HIDLMCPTransmitter";
    TransmitterDeathRecipient mHidlDeathRecepient = new TransmitterDeathRecipient();
    private IMDMonitorService m_connection;
    private String m_serviceName;

    public HIDLMCPTransmitter(String szServiceName) {
        Log.d(TAG, "HIDLMCPTransmitter constructor");
        if (szServiceName != null) {
            this.m_serviceName = szServiceName;
        }
        connectMDMHIDL();
        Log.d(TAG, "HIDLMCPTransmitter constructor done");
    }

    public void connectMDMHIDL() {
        try {
            Log.d(TAG, "connectMDMHIDL");
            IMDMonitorService service = IMDMonitorService.getService(true);
            this.m_connection = service;
            service.linkToDeath(this.mHidlDeathRecepient, 0);
            Log.d(TAG, "connectMDMHIDL done!");
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "IMDMonitorService RemoteException ...");
            this.m_connection = null;
        } catch (NoSuchElementException e2) {
            e2.printStackTrace();
            Log.e(TAG, "IMDMonitorService NoSuchElementException ...");
            this.m_connection = null;
        }
    }

    public synchronized boolean Send(MCPInfo cmd, MCPInfo reply) {
        if (cmd == null) {
            return false;
        }
        Log.d(TAG, "Send HIDL MCP CMD start ...");
        if (MCPHandler.WriteToHIDLService(this.m_connection, cmd, reply)) {
            Log.d(TAG, "Send HIDL MCP CMD ok");
            return true;
        }
        Log.d(TAG, "Send HIDL MCP CMD error");
        return false;
    }

    class TransmitterDeathRecipient implements IHwBinder.DeathRecipient {
        TransmitterDeathRecipient() {
        }

        public void serviceDied(long cookie) {
            Log.e(HIDLMCPTransmitter.TAG, "MDM HIDL Service died.");
        }
    }
}
