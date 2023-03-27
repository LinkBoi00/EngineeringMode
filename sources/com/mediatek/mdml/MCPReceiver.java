package com.mediatek.mdml;

import android.util.Log;
import java.util.ArrayList;

class MCPReceiver implements ConnectionHandlerInterface, HIDLHandlerInterface {
    private static final String TAG = "MDML/MCPReceiver";
    private MCPInfo mMcpInfo = new MCPInfo();
    private PayloadHandlerInterface mPayloadHandler = null;

    public boolean DataIn(SocketConnection connection) {
        int nPayloadProcessed = 0;
        for (int i = 0; i <= 5; i++) {
            if (!connection.ReadToBuffer()) {
                Log.e(TAG, "Failed to read from connection. (read error)");
                return false;
            }
            while (true) {
                DataQueue dataQ = connection.GetDataQueue();
                if (dataQ.Size() == 0) {
                    break;
                } else if (this.mMcpInfo.GetLen() == 0) {
                    if (!MCPHandler.SearchHeader(this.mMcpInfo, dataQ)) {
                        Log.e(TAG, "Header is not found!");
                        break;
                    }
                } else if (dataQ.Size() < this.mMcpInfo.GetLen()) {
                    Log.d(TAG, "Data size [" + dataQ.Size() + "] < Payload length [" + this.mMcpInfo.GetLen() + "]. Wait for next time.");
                    break;
                } else {
                    dataQ.Pop(0);
                    MCPInfo mCPInfo = this.mMcpInfo;
                    mCPInfo.SetData(mCPInfo.GetType(), this.mMcpInfo.GetLen(), dataQ.GetData());
                    MCPInfo reply = new MCPInfo();
                    PayloadHandlerInterface payloadHandlerInterface = this.mPayloadHandler;
                    if (payloadHandlerInterface != null) {
                        payloadHandlerInterface.ProcessPayload(this.mMcpInfo, reply);
                        if (reply.GetLen() > 0) {
                            Log.d(TAG, "Send command reply: type:[" + reply.GetType() + "], len:[" + reply.GetLen() + "], data:");
                            if (!MCPHandler.WriteToConnection(connection, reply, (MCPInfo) null)) {
                                Log.e(TAG, "Failed to write socket. (write error)");
                                return false;
                            }
                        }
                    }
                    nPayloadProcessed++;
                    dataQ.Pop(this.mMcpInfo.GetLen());
                    this.mMcpInfo.SetData(MCP_TYPE.MCP_TYPE_UNDEFINED, 0, (byte[]) null);
                }
            }
            if (nPayloadProcessed > 0) {
                break;
            }
            Log.d(TAG, "No packet is processed. RETRY.");
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
        if (nPayloadProcessed == 0 && this.mMcpInfo.GetLen() > 0) {
            connection.GetDataQueue().Clear();
            this.mMcpInfo.SetData(MCP_TYPE.MCP_TYPE_UNDEFINED, 0, (byte[]) null);
        }
        if (nPayloadProcessed != 0) {
            return true;
        }
        return false;
    }

    public boolean DataIn(ArrayList<Byte> data) {
        DataQueue dataQ = new DataQueue();
        dataQ.PushArrayList(data);
        this.mMcpInfo.SetData(MCP_TYPE.MCP_TYPE_UNDEFINED, 0, (byte[]) null);
        if (dataQ.Size() == 0) {
            Log.e(TAG, "Trap data is empty");
            return false;
        } else if (!MCPHandler.SearchHeader(this.mMcpInfo, dataQ)) {
            Log.e(TAG, "Trap data has no MCP header");
            return false;
        } else if (dataQ.Size() < this.mMcpInfo.GetLen()) {
            Log.e(TAG, "Data size [" + dataQ.Size() + "] < Payload length [" + this.mMcpInfo.GetLen() + "]. ");
            return false;
        } else {
            dataQ.Pop(0);
            MCPInfo mCPInfo = this.mMcpInfo;
            mCPInfo.SetData(mCPInfo.GetType(), this.mMcpInfo.GetLen(), dataQ.GetData());
            MCPInfo reply = new MCPInfo();
            PayloadHandlerInterface payloadHandlerInterface = this.mPayloadHandler;
            if (payloadHandlerInterface == null) {
                return true;
            }
            payloadHandlerInterface.ProcessPayload(this.mMcpInfo, reply);
            reply.GetLen();
            return true;
        }
    }

    public void setPayloadHandler(PayloadHandlerInterface payloadHandler) {
        this.mPayloadHandler = payloadHandler;
    }
}
