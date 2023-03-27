package com.mediatek.mdml;

import android.util.Log;

class TrapPacker {
    private static final String TAG = "MDML/TrapPacker";
    private int[] m_discardCount = new int[TRAP_TYPE.TRAP_TYPE_SIZE.ordinal()];
    private boolean m_isInitialized = false;
    private byte[] m_rawData = null;
    private int m_rawLen = 0;
    private int m_trapDataOffset = 0;
    private int m_trapLen = 0;
    private TRAP_TYPE m_trapType = TRAP_TYPE.TRAP_TYPE_UNDEFINED;

    public TrapPacker(byte[] rawData, int rawLen) {
        int i = 0;
        while (true) {
            int[] iArr = this.m_discardCount;
            if (i >= iArr.length) {
                break;
            }
            iArr[i] = 0;
            i++;
        }
        if (rawData == null || rawLen == 0 || rawLen > MonitorDefs.MAX_RAWDATA_LEN) {
            Log.e(TAG, "Bad parameters. rawData is null or raw len = [" + rawLen + "]");
            return;
        }
        byte[] bArr = new byte[rawLen];
        this.m_rawData = bArr;
        System.arraycopy(rawData, 0, bArr, 0, rawLen);
        this.m_rawLen = rawLen;
        if (Parse()) {
            this.m_isInitialized = true;
        } else {
            Log.e(TAG, "Parse rawData failed.");
        }
    }

    public TrapPacker(TRAP_TYPE type, byte[] trapData, int trapLen) {
        int i = 0;
        while (true) {
            int[] iArr = this.m_discardCount;
            if (i >= iArr.length) {
                break;
            }
            iArr[i] = 0;
            i++;
        }
        if (trapData != null && trapLen != 0 && trapLen <= 65536) {
            switch (AnonymousClass1.$SwitchMap$com$mediatek$mdml$TRAP_TYPE[type.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    this.m_trapType = type;
                    this.m_trapLen = trapLen;
                    byte[] bArr = new byte[((MonitorDefs.MAX_RAWDATA_LEN - 65536) + trapLen)];
                    this.m_rawData = bArr;
                    MonitorUtils.SetIntToByteArray(bArr, 0, type.ordinal(), 1);
                    int currentOffset = 0 + 1;
                    MonitorUtils.SetIntToByteArray(this.m_rawData, currentOffset, trapLen, 4);
                    int currentOffset2 = currentOffset + 4;
                    this.m_trapDataOffset = currentOffset2;
                    System.arraycopy(trapData, 0, this.m_rawData, currentOffset2, trapLen);
                    this.m_rawLen = currentOffset2 + trapLen;
                    Log.d(TAG, "After TrapPacker::TrapPacker: type: [" + type + "], len: [" + trapLen + "]");
                    this.m_isInitialized = true;
                    return;
                default:
                    Log.e(TAG, "Invalid trap type: [" + type.ordinal() + "]");
                    return;
            }
        }
    }

    /* renamed from: com.mediatek.mdml.TrapPacker$1  reason: invalid class name */
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
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_PHASEOUT_1.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_PSTIME.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_ICD_RECORD.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_ICD_EVENT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_DISCARDINFO.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public byte[] GetRawData() {
        return this.m_rawData;
    }

    public int GetRawLen() {
        if (this.m_isInitialized) {
            return this.m_rawLen;
        }
        return 0;
    }

    public int GetTrapDataOffset() {
        if (this.m_isInitialized) {
            return this.m_trapDataOffset;
        }
        return -1;
    }

    public int GetTrapLen() {
        if (this.m_isInitialized) {
            return this.m_trapLen;
        }
        return 0;
    }

    public TRAP_TYPE GetTrapType() {
        return this.m_isInitialized ? this.m_trapType : TRAP_TYPE.TRAP_TYPE_UNDEFINED;
    }

    public int GetDiscardInfo(TRAP_TYPE type) {
        if (type.ordinal() < 0 || type.ordinal() >= TRAP_TYPE.TRAP_TYPE_SIZE.ordinal()) {
            return 0;
        }
        return this.m_discardCount[type.ordinal()];
    }

    private boolean Parse() {
        int currentOffset = 0;
        int totalLen = this.m_rawLen;
        while (currentOffset < totalLen) {
            int type = MonitorUtils.ByteArrayToInt(this.m_rawData, currentOffset, 1);
            if (type < 0 || type >= TRAP_TYPE.TRAP_TYPE_SIZE.ordinal()) {
                Log.e(TAG, "Undefined type: [" + type + "]");
                return false;
            }
            int currentOffset2 = currentOffset + 1;
            switch (AnonymousClass1.$SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.values()[type].ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    this.m_trapType = TRAP_TYPE.values()[type];
                    this.m_trapLen = MonitorUtils.ByteArrayToInt(this.m_rawData, currentOffset2, 4);
                    int currentOffset3 = currentOffset2 + 4;
                    Log.d(TAG, "Trap parsed: type: [" + this.m_trapType.ordinal() + "], len: [" + this.m_trapLen + "]");
                    this.m_trapDataOffset = currentOffset3;
                    currentOffset = currentOffset3 + this.m_trapLen;
                    break;
                case 7:
                    int discard_count = MonitorUtils.ByteArrayToInt(this.m_rawData, currentOffset2, 4);
                    currentOffset = currentOffset2 + 4;
                    for (int i = 0; i < discard_count; i++) {
                        int discard_type = MonitorUtils.ByteArrayToInt(this.m_rawData, currentOffset, 1);
                        int currentOffset4 = currentOffset + 1;
                        if (discard_type < TRAP_TYPE.TRAP_TYPE_SIZE.ordinal()) {
                            this.m_discardCount[discard_type] = MonitorUtils.ByteArrayToInt(this.m_rawData, currentOffset4, 4);
                        } else {
                            Log.e(TAG, "Invalid discard info type: [" + discard_type + "]");
                        }
                        currentOffset = currentOffset4 + 4;
                    }
                    Log.d(TAG, "Parse discard info done! count = [" + discard_count + "]");
                    break;
                default:
                    Log.e(TAG, "Undefined type: [" + MonitorUtils.ByteArrayToInt(this.m_rawData, currentOffset2, 1) + "]");
                    return false;
            }
        }
        return true;
    }

    public boolean IsInitialized() {
        return this.m_isInitialized;
    }
}
