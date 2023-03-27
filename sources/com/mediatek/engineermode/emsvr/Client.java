package com.mediatek.engineermode.emsvr;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import com.mediatek.engineermode.Elog;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class Client {
    private static final String EMPTY = "";
    private static final String EM_SERVER_NAME = "EngineerModeServer";
    private static final String ERROR_NO_INIT = "NOT INIT";
    private static final String ERROR_PARAM_NUM = "param < 0";
    private static final int MAX_BUFFER_SIZE = 1024;
    private static final int PARAM_INT_LENGTH = 4;
    public static final int PARAM_TYPE_INT = 2;
    public static final int PARAM_TYPE_STRING = 1;
    private static final int STATUS_ERROR = -1;
    private static final int STATUS_SUCCESS = 0;
    private static final String TAG = "EmSvr";
    private DataInputStream mInputStream = null;
    private DataOutputStream mOutputStream = null;
    private LocalSocket mSocket = null;
    private int mStatus = 0;

    public void startClient() {
        try {
            LocalSocket localSocket = new LocalSocket();
            this.mSocket = localSocket;
            localSocket.connect(new LocalSocketAddress(EM_SERVER_NAME));
            this.mInputStream = new DataInputStream(this.mSocket.getInputStream());
            this.mOutputStream = new DataOutputStream(this.mSocket.getOutputStream());
            this.mStatus = 0;
        } catch (IOException e) {
            Elog.e(TAG, "startclient IOException " + e.getMessage());
            this.mStatus = -1;
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized String read() throws IOException {
        DataInputStream dataInputStream;
        String result;
        if (-1 == this.mStatus || (dataInputStream = this.mInputStream) == null) {
            throw new IOException(ERROR_NO_INIT);
        }
        int len = dataInputStream.readInt();
        if (len > 1024) {
            len = 1024;
        }
        byte[] bb = new byte[len];
        if (-1 == this.mInputStream.read(bb, 0, len)) {
            result = EMPTY;
        } else {
            result = new String(bb, Charset.defaultCharset());
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void writeFunctionNo(java.lang.String r5) throws java.io.IOException {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = -1
            int r1 = r4.mStatus     // Catch:{ all -> 0x003a }
            if (r0 == r1) goto L_0x0032
            java.io.DataOutputStream r0 = r4.mOutputStream     // Catch:{ all -> 0x003a }
            if (r0 == 0) goto L_0x0032
            if (r5 == 0) goto L_0x0030
            int r0 = r5.length()     // Catch:{ all -> 0x003a }
            if (r0 != 0) goto L_0x0013
            goto L_0x0030
        L_0x0013:
            java.io.DataOutputStream r0 = r4.mOutputStream     // Catch:{ all -> 0x003a }
            int r1 = r5.length()     // Catch:{ all -> 0x003a }
            r0.writeInt(r1)     // Catch:{ all -> 0x003a }
            java.io.DataOutputStream r0 = r4.mOutputStream     // Catch:{ all -> 0x003a }
            java.nio.charset.Charset r1 = java.nio.charset.Charset.defaultCharset()     // Catch:{ all -> 0x003a }
            byte[] r1 = r5.getBytes(r1)     // Catch:{ all -> 0x003a }
            r2 = 0
            int r3 = r5.length()     // Catch:{ all -> 0x003a }
            r0.write(r1, r2, r3)     // Catch:{ all -> 0x003a }
            monitor-exit(r4)
            return
        L_0x0030:
            monitor-exit(r4)
            return
        L_0x0032:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x003a }
            java.lang.String r1 = "NOT INIT"
            r0.<init>(r1)     // Catch:{ all -> 0x003a }
            throw r0     // Catch:{ all -> 0x003a }
        L_0x003a:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.emsvr.Client.writeFunctionNo(java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    public synchronized void writeParamNo(int paramNum) throws IOException {
        DataOutputStream dataOutputStream;
        if (-1 == this.mStatus || (dataOutputStream = this.mOutputStream) == null) {
            throw new IOException(ERROR_NO_INIT);
        } else if (paramNum >= 0) {
            dataOutputStream.writeInt(paramNum);
        } else {
            throw new IOException(ERROR_PARAM_NUM);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void writeParamInt(int param) throws IOException {
        DataOutputStream dataOutputStream;
        if (-1 == this.mStatus || (dataOutputStream = this.mOutputStream) == null) {
            throw new IOException(ERROR_NO_INIT);
        }
        dataOutputStream.writeInt(2);
        this.mOutputStream.writeInt(4);
        this.mOutputStream.writeInt(param);
    }

    /* access modifiers changed from: package-private */
    public synchronized void writeParamString(String param) throws IOException {
        DataOutputStream dataOutputStream;
        if (-1 == this.mStatus || (dataOutputStream = this.mOutputStream) == null) {
            throw new IOException(ERROR_NO_INIT);
        }
        dataOutputStream.writeInt(1);
        this.mOutputStream.writeInt(param.length());
        this.mOutputStream.write(param.getBytes(Charset.defaultCharset()), 0, param.length());
    }

    public void stopClient() {
        DataOutputStream dataOutputStream;
        if (this.mInputStream != null && (dataOutputStream = this.mOutputStream) != null && this.mSocket != null) {
            try {
                dataOutputStream.close();
                this.mInputStream.close();
                this.mSocket.close();
            } catch (IOException e) {
                Elog.e(TAG, "stop client IOException: " + e.getMessage());
            }
        }
    }
}
