package com.mediatek.socket.base;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.IHwBinder;
import android.os.RemoteException;
import com.mediatek.socket.base.SocketUtils;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import vendor.mediatek.hardware.lbs.V1_0.ILbs;
import vendor.mediatek.hardware.lbs.V1_0.ILbsCallback;

public class UdpServer implements SocketUtils.UdpServerInterface {
    /* access modifiers changed from: private */
    public SocketUtils.BaseBuffer mBuff;
    /* access modifiers changed from: private */
    public String mChannelName;
    private DataInputStream mIn;
    private boolean mIsLocalSocket = false;
    private LbsHidlDeathRecipient mLLbsHidlDeathRecipient = new LbsHidlDeathRecipient();
    private LbsHidlCallback mLbsHidlCallback = new LbsHidlCallback();
    ILbs mLbsHidlClient;
    private LocalSocket mLocalSocket;
    private LocalSocketAddress.Namespace mNamespace;
    private DatagramSocket mNetSocket;
    private DatagramPacket mPacket;
    private int mPort;
    SocketUtils.ProtocolHandler mReceiver;

    public static void covertArrayListToByteArray(ArrayList<Byte> in, byte[] out) {
        int i = 0;
        while (i < in.size() && i < out.length) {
            out[i] = in.get(i).byteValue();
            i++;
        }
    }

    class LbsHidlCallback extends ILbsCallback.Stub {
        LbsHidlCallback() {
        }

        public boolean callbackToClient(ArrayList<Byte> data) {
            if (UdpServer.this.mReceiver == null) {
                return true;
            }
            UdpServer.covertArrayListToByteArray(data, UdpServer.this.mBuff.getBuff());
            UdpServer.this.mReceiver.decode(UdpServer.this);
            return true;
        }
    }

    class LbsHidlDeathRecipient implements IHwBinder.DeathRecipient {
        LbsHidlDeathRecipient() {
        }

        public void serviceDied(long cookie) {
            UdpServer.this.mLbsHidlClient = null;
            boolean ret = false;
            while (!ret) {
                UdpServer.this.msleep(300);
                UdpServer udpServer = UdpServer.this;
                ret = udpServer.lbsHidlInit(udpServer.mChannelName);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean lbsHidlInit(String name) {
        try {
            ILbs service = ILbs.getService(name);
            this.mLbsHidlClient = service;
            service.linkToDeath(this.mLLbsHidlDeathRecipient, 0);
            this.mLbsHidlClient.setCallback(this.mLbsHidlCallback);
            return true;
        } catch (RemoteException | RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setReceiver(SocketUtils.ProtocolHandler receiver) {
        this.mReceiver = receiver;
    }

    public UdpServer(int port, int recvBuffSize) {
        this.mBuff = new SocketUtils.BaseBuffer(recvBuffSize);
        this.mPort = port;
    }

    public UdpServer(String channelName, LocalSocketAddress.Namespace namespace, int recvBuffSize) {
        this.mBuff = new SocketUtils.BaseBuffer(recvBuffSize);
        this.mChannelName = channelName;
        this.mNamespace = namespace;
        lbsHidlInit(channelName);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005e, code lost:
        r0 = r0 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean bind() {
        /*
            r9 = this;
            r0 = 0
        L_0x0001:
            r1 = 5
            if (r0 >= r1) goto L_0x0067
            boolean r1 = r9.mIsLocalSocket
            r2 = 4
            r3 = 200(0xc8, double:9.9E-322)
            r5 = 1
            if (r1 == 0) goto L_0x003a
            android.net.LocalSocket r1 = new android.net.LocalSocket     // Catch:{ IOException -> 0x002d }
            r1.<init>(r5)     // Catch:{ IOException -> 0x002d }
            r9.mLocalSocket = r1     // Catch:{ IOException -> 0x002d }
            android.net.LocalSocketAddress r6 = new android.net.LocalSocketAddress     // Catch:{ IOException -> 0x002d }
            java.lang.String r7 = r9.mChannelName     // Catch:{ IOException -> 0x002d }
            android.net.LocalSocketAddress$Namespace r8 = r9.mNamespace     // Catch:{ IOException -> 0x002d }
            r6.<init>(r7, r8)     // Catch:{ IOException -> 0x002d }
            r1.bind(r6)     // Catch:{ IOException -> 0x002d }
            java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch:{ IOException -> 0x002d }
            android.net.LocalSocket r6 = r9.mLocalSocket     // Catch:{ IOException -> 0x002d }
            java.io.InputStream r6 = r6.getInputStream()     // Catch:{ IOException -> 0x002d }
            r1.<init>(r6)     // Catch:{ IOException -> 0x002d }
            r9.mIn = r1     // Catch:{ IOException -> 0x002d }
            return r5
        L_0x002d:
            r1 = move-exception
            if (r0 == r2) goto L_0x0034
            r9.msleep(r3)
            goto L_0x005e
        L_0x0034:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            r2.<init>(r1)
            throw r2
        L_0x003a:
            java.net.DatagramSocket r1 = new java.net.DatagramSocket     // Catch:{ SocketException -> 0x0058 }
            int r6 = r9.mPort     // Catch:{ SocketException -> 0x0058 }
            r1.<init>(r6)     // Catch:{ SocketException -> 0x0058 }
            r9.mNetSocket = r1     // Catch:{ SocketException -> 0x0058 }
            java.net.DatagramPacket r1 = new java.net.DatagramPacket     // Catch:{ SocketException -> 0x0058 }
            com.mediatek.socket.base.SocketUtils$BaseBuffer r6 = r9.mBuff     // Catch:{ SocketException -> 0x0058 }
            byte[] r6 = r6.getBuff()     // Catch:{ SocketException -> 0x0058 }
            com.mediatek.socket.base.SocketUtils$BaseBuffer r7 = r9.mBuff     // Catch:{ SocketException -> 0x0058 }
            byte[] r7 = r7.getBuff()     // Catch:{ SocketException -> 0x0058 }
            int r7 = r7.length     // Catch:{ SocketException -> 0x0058 }
            r1.<init>(r6, r7)     // Catch:{ SocketException -> 0x0058 }
            r9.mPacket = r1     // Catch:{ SocketException -> 0x0058 }
            return r5
        L_0x0058:
            r1 = move-exception
            r9.msleep(r3)
            if (r0 == r2) goto L_0x0061
        L_0x005e:
            int r0 = r0 + 1
            goto L_0x0001
        L_0x0061:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            r2.<init>(r1)
            throw r2
        L_0x0067:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.socket.base.UdpServer.bind():boolean");
    }

    public boolean read() {
        this.mBuff.clear();
        if (this.mIsLocalSocket) {
            return true;
        }
        try {
            this.mNetSocket.receive(this.mPacket);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SocketUtils.BaseBuffer getBuff() {
        return this.mBuff;
    }

    public void close() {
        if (!this.mIsLocalSocket) {
            this.mNetSocket.close();
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 2 */
    public int available() {
        if (this.mIsLocalSocket) {
            try {
                return this.mIn.available();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        } else {
            try {
                throw new RuntimeException("Network Type does not support available() API");
            } catch (Exception e2) {
                e2.printStackTrace();
                return -1;
            }
        }
    }

    public boolean setSoTimeout(int timeout) {
        if (this.mIsLocalSocket) {
            try {
                this.mLocalSocket.setSoTimeout(timeout);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                this.mNetSocket.setSoTimeout(timeout);
                return true;
            } catch (SocketException e2) {
                e2.printStackTrace();
                return false;
            }
        }
    }

    /* access modifiers changed from: private */
    public void msleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
