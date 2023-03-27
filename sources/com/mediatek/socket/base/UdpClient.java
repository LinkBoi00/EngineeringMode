package com.mediatek.socket.base;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.IHwBinder;
import android.os.RemoteException;
import com.mediatek.socket.base.SocketUtils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import vendor.mediatek.hardware.lbs.V1_0.ILbs;

public class UdpClient {
    private SocketUtils.BaseBuffer mBuff;
    private String mChannelName;
    private String mHost;
    private InetAddress mInetAddress;
    private boolean mIsLocalSocket = false;
    LbsHidlDeathRecipient mLLbsHidlDeathRecipient = new LbsHidlDeathRecipient();
    ILbs mLbsHidlClient = null;
    private LocalSocket mLocalSocket;
    private LocalSocketAddress.Namespace mNamespace;
    private DatagramSocket mNetSocket;
    private DataOutputStream mOut;
    private DatagramPacket mPacket;
    private int mPort;

    public UdpClient(String host, int port, int sendBuffSize) {
        this.mBuff = new SocketUtils.BaseBuffer(sendBuffSize);
        this.mHost = host;
        this.mPort = port;
    }

    public UdpClient(String channelName, LocalSocketAddress.Namespace namesapce, int sendBuffSize) {
        this.mBuff = new SocketUtils.BaseBuffer(sendBuffSize);
        this.mChannelName = channelName;
        this.mNamespace = namesapce;
    }

    class LbsHidlDeathRecipient implements IHwBinder.DeathRecipient {
        LbsHidlDeathRecipient() {
        }

        public void serviceDied(long cookie) {
            UdpClient.this.mLbsHidlClient = null;
        }
    }

    public boolean lbsHidlInit(String name) {
        if (this.mLbsHidlClient != null) {
            return true;
        }
        try {
            ILbs service = ILbs.getService(name);
            this.mLbsHidlClient = service;
            service.linkToDeath(this.mLLbsHidlDeathRecipient, 0);
            return true;
        } catch (RemoteException | RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Byte> convertByteArrayToArrayList(byte[] data, int size) {
        if (data == null) {
            return null;
        }
        int max = size < data.length ? size : data.length;
        ArrayList<Byte> ret = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            ret.add(Byte.valueOf(data[i]));
        }
        return ret;
    }

    public boolean connect() {
        if (this.mIsLocalSocket) {
            return lbsHidlInit(this.mChannelName);
        }
        try {
            this.mNetSocket = new DatagramSocket();
            if (this.mInetAddress == null) {
                this.mInetAddress = InetAddress.getByName(this.mHost);
            }
            this.mPacket = new DatagramPacket(this.mBuff.getBuff(), this.mBuff.getBuff().length, this.mInetAddress, this.mPort);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public SocketUtils.BaseBuffer getBuff() {
        return this.mBuff;
    }

    public boolean write() {
        if (this.mIsLocalSocket) {
            try {
                this.mLbsHidlClient.sendToServer(convertByteArrayToArrayList(this.mBuff.getBuff(), this.mBuff.getOffset()));
                this.mBuff.setOffset(0);
                return true;
            } catch (RemoteException | RuntimeException e) {
                this.mBuff.setOffset(0);
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                this.mPacket.setLength(this.mBuff.getOffset());
                this.mNetSocket.send(this.mPacket);
                this.mBuff.setOffset(0);
                return true;
            } catch (IOException e2) {
                this.mBuff.setOffset(0);
                e2.printStackTrace();
                return false;
            }
        }
    }

    public void close() {
        DatagramSocket datagramSocket;
        if (!this.mIsLocalSocket && (datagramSocket = this.mNetSocket) != null) {
            datagramSocket.close();
        }
    }
}
