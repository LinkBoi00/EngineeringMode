package com.mediatek.engineermode.hqanfc;

import android.content.Context;
import android.os.RemoteException;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import vendor.mediatek.hardware.engineermode.V1_3.IEmNfcResponse;
import vendor.mediatek.hardware.engineermode.V1_3.IEmd;

public class NfcClient extends IEmNfcResponse.Stub {
    private static final boolean DEBUG = false;
    private static NfcClient sInstance;
    private Context mContext;
    private IEmd mEmService;
    private boolean mIsConnected = false;

    private static void dumpHex(List<Byte> list) {
    }

    public static NfcClient getInstance() {
        if (sInstance == null) {
            IEmd srv = EmUtils.getEmHidlService();
            if (srv == null) {
                return null;
            }
            NfcClient r = new NfcClient();
            try {
                srv.setNfcResponseFunction(r);
                r.mEmService = srv;
                sInstance = r;
            } catch (RemoteException e) {
                return null;
            }
        }
        return sInstance;
    }

    public void response(ArrayList<Byte> message) {
        NfcCommand mainMessage;
        dumpHex(message);
        byte[] raw = new byte[message.size()];
        for (int i = 0; i < raw.length; i++) {
            raw[i] = message.get(i).byteValue();
        }
        ByteBuffer buffer = ByteBuffer.wrap(raw);
        byte[] type = new byte[4];
        buffer.get(type);
        int msgType = NfcCommand.DataConvert.byteToInt(type);
        Elog.d(NfcMainPage.TAG, "[NfcClient] rmsg type: " + msgType);
        if (msgType > 204) {
            Elog.v(NfcMainPage.TAG, "[NfcClient] incorrect rmsg: " + new String(raw));
            return;
        }
        byte[] lenght = new byte[4];
        buffer.get(lenght);
        int msgLen = NfcCommand.DataConvert.byteToInt(lenght);
        Elog.d(NfcMainPage.TAG, "[NfcClient] length: " + msgLen);
        if (msgLen == 0) {
            mainMessage = new NfcCommand(msgType, (ByteBuffer) null);
        } else {
            byte[] bufferCont = new byte[msgLen];
            buffer.get(bufferCont);
            mainMessage = new NfcCommand(msgType, ByteBuffer.wrap(bufferCont));
        }
        NfcCommandHandler ch = NfcCommandHandler.getInstance(this.mContext);
        if (ch == null) {
            Elog.d(NfcMainPage.TAG, "[NfcClient] Cannot allocate NfcCommandHandler");
        } else {
            ch.execute(mainMessage);
        }
    }

    public synchronized int sendCommand(int msgType, NfcEmReqRsp.NfcEmReq cmdReq) {
        ByteBuffer buffer;
        if (!this.mIsConnected) {
            Elog.e(NfcMainPage.TAG, "[NfcClient] Not connected");
            return -1;
        }
        Elog.d(NfcMainPage.TAG, "[NfcClient] Send command type: " + msgType);
        if (cmdReq == null) {
            buffer = ByteBuffer.allocate(8);
            if (buffer == null) {
                Elog.e(NfcMainPage.TAG, "[NfcClient] Cannot alloc memory");
                return -1;
            }
            buffer.put(NfcCommand.DataConvert.intToLH(msgType));
            buffer.put(NfcCommand.DataConvert.intToLH(0));
        } else {
            buffer = ByteBuffer.allocate(cmdReq.getContentSize() + 8);
            if (buffer == null) {
                Elog.e(NfcMainPage.TAG, "[NfcClient] Cannot alloc memory");
                return -1;
            }
            buffer.put(NfcCommand.DataConvert.intToLH(msgType));
            buffer.put(NfcCommand.DataConvert.intToLH(cmdReq.getContentSize()));
            cmdReq.writeRaw(buffer);
        }
        byte[] raw = buffer.array();
        Elog.d(NfcMainPage.TAG, "[NfcClient] Send command type: " + msgType + ",len: " + raw.length);
        ArrayList<Byte> data = new ArrayList<>(raw.length);
        for (int i = 0; i < raw.length; i++) {
            data.add(i, Byte.valueOf(raw[i]));
        }
        dumpHex(data);
        try {
            this.mEmService.sendNfcRequest(msgType, data);
            return msgType;
        } catch (RemoteException e) {
            Elog.w(NfcMainPage.TAG, "[NfcClient] sendCommand RemoteException: " + e.getMessage());
            return -1;
        }
    }

    public boolean closeConnection() {
        Elog.d(NfcMainPage.TAG, "[NfcClient] closeConnection().");
        if (!this.mIsConnected) {
            return false;
        }
        try {
            this.mEmService.disconnect();
            this.mIsConnected = false;
            return true;
        } catch (RemoteException e) {
            Elog.w(NfcMainPage.TAG, "[NfcClient] createConnection finally RemoteException: " + e.getMessage());
            return false;
        }
    }

    public boolean createConnection(Context context) {
        if (this.mIsConnected) {
            return false;
        }
        Elog.d(NfcMainPage.TAG, "[NfcClient] createConnection().");
        try {
            if (this.mEmService.connect()) {
                this.mIsConnected = true;
                this.mContext = context;
                return true;
            }
        } catch (RemoteException e) {
            Elog.w(NfcMainPage.TAG, "[NfcClient] createConnection finally RemoteException: " + e.getMessage());
        }
        return false;
    }

    public boolean isConnected() {
        return this.mIsConnected;
    }
}
