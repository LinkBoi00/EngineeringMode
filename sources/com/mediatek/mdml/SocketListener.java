package com.mediatek.mdml;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.util.Log;
import java.io.IOException;

class SocketListener {
    private static final String TAG = "MDML/SocketListener";
    /* access modifiers changed from: private */
    public ConnectionHandlerInterface m_connHandlerInstance = null;
    /* access modifiers changed from: private */
    public boolean m_notifyListenThreadStop = false;
    /* access modifiers changed from: private */
    public LocalServerSocket m_socket;
    private Thread m_threadId = null;

    public SocketListener(String szServerName) {
        try {
            this.m_socket = new LocalServerSocket(szServerName);
            Thread thread = new Thread(new ListenRunnable());
            this.m_threadId = thread;
            thread.start();
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize SocketListener.");
            e.printStackTrace();
        }
    }

    public void finalize() {
        this.m_notifyListenThreadStop = true;
        Thread thread = this.m_threadId;
        if (thread != null) {
            thread.interrupt();
        }
        LocalServerSocket localServerSocket = this.m_socket;
        if (localServerSocket != null) {
            try {
                localServerSocket.close();
            } catch (Exception e) {
                Log.e(TAG, "Failed to close socket.");
                e.printStackTrace();
            }
        }
    }

    public void SetConnectionHandler(ConnectionHandlerInterface handlerInstance) {
        this.m_connHandlerInstance = handlerInstance;
    }

    private class ListenRunnable implements Runnable {
        private ListenRunnable() {
        }

        public void run() {
            while (!SocketListener.this.m_notifyListenThreadStop) {
                try {
                    Log.d(SocketListener.TAG, "ListenRunnable.run");
                    LocalSocket receiver = SocketListener.this.m_socket.accept();
                    if (SocketListener.this.m_notifyListenThreadStop) {
                        SocketListener.this.m_socket.close();
                        return;
                    }
                    SocketConnection socketConnection = new SocketConnection(receiver);
                    boolean bIsConnected = true;
                    while (!SocketListener.this.m_notifyListenThreadStop) {
                        Log.d(SocketListener.TAG, "ListenRunnable.run. Ready to read");
                        if (SocketListener.this.m_connHandlerInstance != null) {
                            bIsConnected = SocketListener.this.m_connHandlerInstance.DataIn(socketConnection);
                        }
                        Log.d(SocketListener.TAG, "ListenRunnable.run. Ready to read done!");
                        if (!bIsConnected) {
                            break;
                        }
                    }
                    Log.d(SocketListener.TAG, "ListenRunnable will be stopped.");
                    SocketListener.this.m_socket.close();
                } catch (IOException e) {
                    Log.d(SocketListener.TAG, "ListenRunnable will be stopped.");
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
