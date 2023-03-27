package com.mediatek.mdml;

import android.util.Log;

class MCPTransmitter {
    private static final String TAG = "MDML/MCPTransmitter";
    private SocketConnection m_connection;
    private String m_serverName;

    public MCPTransmitter(String szServerName) {
        if (szServerName != null) {
            this.m_serverName = szServerName;
            SocketConnection socketConnection = new SocketConnection();
            this.m_connection = socketConnection;
            socketConnection.Connect(szServerName);
        }
    }

    public synchronized boolean Send(MCPInfo cmd, MCPInfo reply) {
        if (cmd == null) {
            return false;
        }
        if (MCPHandler.WriteToConnection(this.m_connection, cmd, reply)) {
            return true;
        }
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            if (this.m_connection.Connect(this.m_serverName) && MCPHandler.WriteToConnection(this.m_connection, cmd, reply)) {
                return true;
            }
            Log.e(TAG, "Reconnect to [" + this.m_serverName + "] failed.");
        }
        return true;
    }
}
