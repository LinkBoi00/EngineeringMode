package com.mediatek.mdml;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;
import java.io.OutputStream;

class SocketConnection {
    private static final String TAG = "MDML/SocketConnection";
    protected DataQueue m_dataQ;
    private boolean m_isValid;
    private LocalSocket m_socket;

    public SocketConnection() {
        this.m_isValid = false;
        this.m_socket = null;
        this.m_dataQ = new DataQueue();
    }

    public SocketConnection(LocalSocket socket) {
        this.m_isValid = true;
        this.m_socket = socket;
        this.m_dataQ = new DataQueue();
    }

    public void finalize() throws Throwable {
        Log.d(TAG, "~SocketConnection");
        Reset();
        super.finalize();
    }

    public boolean Connect(Object host) {
        Log.d(TAG, "SocketConnection::Connect");
        if (host == null) {
            Log.e(TAG, "Input arg is null");
            return false;
        }
        Reset();
        LocalSocket localSocket = new LocalSocket();
        this.m_socket = localSocket;
        try {
            localSocket.connect(new LocalSocketAddress((String) host, LocalSocketAddress.Namespace.ABSTRACT));
            this.m_isValid = true;
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to connect to " + ((String) host));
            e.printStackTrace();
            return false;
        }
    }

    public int Read(byte[] buffer, int offset, int bufferSize) {
        if (buffer == null || offset < 0 || bufferSize <= 0) {
            return -2;
        }
        LocalSocket localSocket = this.m_socket;
        if (localSocket == null) {
            Log.e(TAG, "SocketConnection is not connected.");
            return -1;
        }
        try {
            try {
                int readBytes = localSocket.getInputStream().read(buffer, offset, bufferSize);
                if (readBytes == 0) {
                    Log.e(TAG, "Socket is closed.");
                    this.m_isValid = false;
                    return -1;
                }
                Log.d(TAG, "SocketConnection::Read done! read [" + readBytes + "] bytes.");
                return readBytes;
            } catch (Exception e) {
                Log.e(TAG, "Failed to get input stream");
                e.printStackTrace();
                return -1;
            }
        } catch (Exception e2) {
            Log.e(TAG, "Failed to get input stream");
            e2.printStackTrace();
            return -1;
        }
    }

    public boolean Write(byte[] data, int len) {
        if (data == null) {
            return false;
        }
        if (len == 0) {
            return true;
        }
        LocalSocket localSocket = this.m_socket;
        if (localSocket == null) {
            Log.e(TAG, "SocketConnection is not connected.");
            return false;
        }
        try {
            OutputStream outputStream = localSocket.getOutputStream();
            if (!IsValid()) {
                return false;
            }
            try {
                outputStream.write(data, 0, len);
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Failed to write to output stream.");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e2) {
            Log.e(TAG, "Failed to get output stream.");
            return false;
        }
    }

    public void Reset() {
        this.m_isValid = false;
        LocalSocket localSocket = this.m_socket;
        if (localSocket != null) {
            try {
                if (!localSocket.isClosed()) {
                    this.m_socket.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to close socket.");
                e.printStackTrace();
            }
            this.m_socket = null;
        }
    }

    public boolean ReadToBuffer() {
        Log.d(TAG, "BufferedConnection::ReadToBuffer. There're " + this.m_dataQ.SpaceSize() + " bytes of space in dataQ.");
        int Read = Read(this.m_dataQ.GetData(), this.m_dataQ.SpaceStartOffset(), this.m_dataQ.SpaceSize());
        int readBytes = Read;
        if (Read < 0) {
            Log.e(TAG, "Failed to read from connection");
            return false;
        }
        this.m_dataQ.IncSize(readBytes);
        return true;
    }

    public DataQueue GetDataQueue() {
        return this.m_dataQ;
    }

    public boolean IsValid() {
        return this.m_isValid;
    }
}
