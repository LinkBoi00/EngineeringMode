package com.mediatek.mdml;

import android.os.RemoteException;
import android.os.SystemProperties;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.mcfconfig.FileUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.NoSuchElementException;
import vendor.mediatek.hardware.mdmonitor.V1_0.IMDMonitorService;

public class MonitorTrapReceiver implements PayloadHandlerInterface {
    private static final String TAG = "MDML/MonitorTrapReceiver";
    private PlainDataDecoder m_decoder;
    private MDMonitorClientCallback m_hidlCallback;
    private MCPReceiver m_mcpReceiver;
    private SocketListener m_socketListener;
    private TrapHandlerInterface m_trapHandler;
    private long psTimeCounter;
    private long timeInMillisecond;

    public boolean ProcessPayload(MCPInfo trap, MCPInfo not_used) {
        TrapPacker trapPacker = new TrapPacker(trap.GetData(), trap.GetLen());
        if (trapPacker.GetTrapType() == TRAP_TYPE.TRAP_TYPE_PSTIME) {
            this.psTimeCounter = MonitorUtils.ByteArrayToLong(trapPacker.GetRawData(), trapPacker.GetTrapDataOffset() + 2, 4);
            if (SystemProperties.getInt(FeatureSupport.FK_MD_WM_SUPPORT, 0) == 1) {
                this.timeInMillisecond = (long) ((((double) this.psTimeCounter) * 64.0d) / 1000.0d);
            } else {
                this.timeInMillisecond = (long) (((double) this.psTimeCounter) * 5.0d);
            }
            return true;
        }
        if (this.m_trapHandler != null) {
            printDebugLog(this.psTimeCounter, this.timeInMillisecond, trapPacker);
            this.m_trapHandler.ProcessTrap(this.timeInMillisecond, trapPacker.GetTrapType(), trapPacker.GetTrapLen(), trapPacker.GetRawData(), trapPacker.GetTrapDataOffset());
        }
        return true;
    }

    public void DecodeICDPacketHeader(TrapPacker trapPacker) {
        byte[] data = trapPacker.GetRawData();
        int offset = trapPacker.GetTrapDataOffset();
        ByteBuffer icdPacket = ByteBuffer.wrap(Arrays.copyOfRange(data, offset, trapPacker.GetTrapLen() + offset));
        int type = icdPacket.get(0) & 15;
        int version = (icdPacket.get(0) & 240) >> 4;
        int msg_id = 0;
        switch (type) {
            case 0:
                switch (version) {
                    case 1:
                        int total_size = (icdPacket.get(1) & 255) | ((icdPacket.get(2) & 255) << 8);
                        int timestamp_type = icdPacket.get(3) & 15;
                        int protocol_id = (icdPacket.get(3) & 240) >> 4;
                        msg_id = (icdPacket.get(4) & 255) | ((icdPacket.get(5) & 255) << 8);
                        break;
                    case 2:
                        int total_size2 = (icdPacket.get(1) & 255) | ((icdPacket.get(2) & 255) << 8) | ((icdPacket.get(2) & 255) << 16);
                        int timestamp_type2 = icdPacket.get(4) & 15;
                        int protocol_id2 = (icdPacket.get(4) & 240) >> 4;
                        msg_id = (icdPacket.get(6) & 255) | ((icdPacket.get(7) & 255) << 8);
                        break;
                }
                Log.d(TAG, "Get ICD event: version = " + version + "   msg_id = 0x" + Long.toHexString((long) msg_id).toUpperCase());
                return;
            case 1:
                int total_size3 = (icdPacket.get(1) & 255) | (icdPacket.get(2) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (icdPacket.get(3) & 16711680);
                int timestamp_type3 = icdPacket.get(4) & 15;
                int protocol_id3 = (icdPacket.get(4) & 240) >> 4;
                int msg_id2 = (icdPacket.get(6) & 255) | ((icdPacket.get(7) & 255) << 8);
                Log.d(TAG, "Get ICD record: version = " + version + "  msg_id = 0x" + Long.toHexString((long) msg_id2).toUpperCase());
                return;
            default:
                Log.e(TAG, "default type = " + Integer.toHexString(type));
                return;
        }
    }

    /* renamed from: com.mediatek.mdml.MonitorTrapReceiver$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$mdml$TRAP_TYPE;

        static {
            int[] iArr = new int[TRAP_TYPE.values().length];
            $SwitchMap$com$mediatek$mdml$TRAP_TYPE = iArr;
            try {
                iArr[TRAP_TYPE.TRAP_TYPE_OTA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_EM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_ICD_RECORD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_ICD_EVENT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_DISCARDINFO.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private void printDebugLog(long psTimeCount, long psTime, TrapPacker trapPacker) {
        switch (AnonymousClass1.$SwitchMap$com$mediatek$mdml$TRAP_TYPE[trapPacker.GetTrapType().ordinal()]) {
            case 1:
                PlainDataDecoder plainDataDecoder = this.m_decoder;
                if (plainDataDecoder != null) {
                    Msg msg = plainDataDecoder.otaInfo_getMsg(trapPacker.GetRawData(), trapPacker.GetTrapDataOffset());
                    if (msg == null) {
                        Log.d(TAG, "printDebuglog OTA type null");
                        return;
                    }
                    String traceIdName = msg.getMsgName();
                    String globalIdName = msg.getOtaMsgGlobalID(msg.getOtaMsgFieldValue());
                    Log.d(TAG, "PSTIME: counter/millisecond = " + psTimeCount + FileUtils.SEPARATOR + psTime + ", SIM index = " + msg.getSimIdx() + ", Trace id = " + traceIdName + "(" + this.m_decoder.otaInfo_getMsgID(traceIdName) + "), Global id = " + globalIdName + "(" + this.m_decoder.globalId_getValue(globalIdName) + "), Trap len = " + trapPacker.GetTrapLen());
                    return;
                }
                return;
            case 2:
                PlainDataDecoder plainDataDecoder2 = this.m_decoder;
                if (plainDataDecoder2 != null) {
                    Msg msg2 = plainDataDecoder2.msgInfo_getMsg(trapPacker.GetRawData(), trapPacker.GetTrapDataOffset());
                    if (msg2 == null) {
                        Log.d(TAG, "printdebuglog EM type null");
                        return;
                    }
                    String EmMsgName = msg2.getMsgName();
                    Log.d(TAG, "PSTIME: counter/millisecond = " + psTimeCount + FileUtils.SEPARATOR + psTime + ", SIM index = " + msg2.getSimIdx() + ", EM id = " + EmMsgName + "(" + this.m_decoder.msgInfo_getMsgID(EmMsgName) + "), Trap len = " + trapPacker.GetTrapLen());
                    return;
                }
                return;
            case 3:
            case 4:
                DecodeICDPacketHeader(trapPacker);
                return;
            case 5:
                Log.d(TAG, "Discard info");
                return;
            default:
                return;
        }
    }

    public MonitorTrapReceiver(String szServerName) {
        this.timeInMillisecond = 0;
        this.psTimeCounter = 0;
        this.m_trapHandler = null;
        MCPReceiver mCPReceiver = new MCPReceiver();
        this.m_mcpReceiver = mCPReceiver;
        mCPReceiver.setPayloadHandler(this);
        MDMonitorClientCallback mDMonitorClientCallback = new MDMonitorClientCallback();
        this.m_hidlCallback = mDMonitorClientCallback;
        mDMonitorClientCallback.SetMCPHandler(this.m_mcpReceiver);
        try {
            IMDMonitorService.getService(true).registerTrapCallback(szServerName, this.m_hidlCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "IMDMonitorService RemoteException ...");
        } catch (NoSuchElementException e2) {
            e2.printStackTrace();
            Log.e(TAG, "IMDMonitorService NoSuchElementException ...");
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            Log.e(TAG, "IMDMonitorService NullPointerException ...");
        }
        try {
            this.m_decoder = PlainDataDecoder.getInstance((String) null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.d(TAG, "MonitorTrapReceiver Constructor done! Server name is " + szServerName);
    }

    public MonitorTrapReceiver(long sessionId, String szServerName) {
        this(String.valueOf(sessionId) + szServerName);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        Log.d(TAG, "MonitorTrapReceiver destructor done!");
        this.m_socketListener = null;
        this.m_mcpReceiver = null;
    }

    public void SetTrapHandler(TrapHandlerInterface handlerInterfaceInstance) {
        this.m_trapHandler = handlerInterfaceInstance;
    }
}
