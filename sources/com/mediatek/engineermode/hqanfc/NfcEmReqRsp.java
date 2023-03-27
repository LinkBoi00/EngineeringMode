package com.mediatek.engineermode.hqanfc;

import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class NfcEmReqRsp {
    private static final int TAG_WRITE_MAXDATA = 512;

    public static class EXTTagT {
        public static final int CONTENT_SIZE = 578;
        public static final int DATA_LENGTH = 64;
        public byte[] mExtData = new byte[512];
        public short mExtLength;
        public byte[] mExtTagType = new byte[64];
    }

    public static class NfcEmAlsCardmRsp extends NfcEmRsp {
    }

    public static class NfcEmAlsP2pRsp extends NfcEmRsp {
    }

    public static class NfcEmAlsReadermRsp extends NfcEmRsp {
    }

    public static class NfcEmPollingRsp extends NfcEmRsp {
    }

    public static class NfcEmSwpRsp extends NfcEmRsp {
    }

    public static class NfcEmTxCarrAlsOnRsp extends NfcEmRsp {
    }

    public static class NfcEmVirtualCardRsp extends NfcEmRsp {
    }

    interface RawOperation {
        int getContentSize();

        void readRaw(ByteBuffer byteBuffer) throws NullPointerException, BufferUnderflowException;

        void writeRaw(ByteBuffer byteBuffer) throws NullPointerException, BufferOverflowException;
    }

    public static class NfcEmVersionRsp implements RawOperation {
        public static final int CONTENT_SIZE = 25;
        private static final int DATA_LENGTH = 19;
        public int mChipVersion;
        public int mFwVersion;
        public int mHwVersion;
        public byte[] mMwVersion = new byte[19];

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            buffer.get(this.mMwVersion, 0, 19);
            int version = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mFwVersion = version & 65535;
            this.mHwVersion = 65535 & (version >> 16);
            byte[] u16buffer = new byte[2];
            buffer.get(u16buffer, 0, 2);
            this.mChipVersion = NfcCommand.DataConvert.byte2uint16(u16buffer);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(this.mMwVersion, 0, 19);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mFwVersion));
            buffer.put(NfcCommand.DataConvert.intToLH(this.mHwVersion));
        }

        public int getContentSize() {
            return 25;
        }
    }

    public static class NfcEmOptionReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 4;
        public short mAutoCheck;
        public short mForceDownload;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            int option = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mForceDownload = (short) (option & 65535);
            this.mAutoCheck = (short) (65535 & (option >> 16));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.shortToLH(this.mForceDownload));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_test_mode_setting_req forceDownload: " + this.mForceDownload);
            buffer.put(NfcCommand.DataConvert.shortToLH(this.mAutoCheck));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_test_mode_setting_req tagAutoPresenceChk: " + this.mAutoCheck);
        }

        public int getContentSize() {
            return 4;
        }
    }

    public static class NfcEmOptionRsp {
        public static final int CONTENT_SIZE = 1;
        public byte mResult;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mResult = buffer.get();
            Elog.d(NfcMainPage.TAG, "NfcEmOptionRsp result: " + this.mResult);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(this.mResult);
        }

        public int getContentSize() {
            return 1;
        }
    }

    public static class NfcEmLoopbackReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 1;
        public byte mAction;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = buffer.get();
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(this.mAction);
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_loopback_test_req action: " + this.mAction);
        }

        public int getContentSize() {
            return 1;
        }
    }

    public static class NfcEmLoopbackRsp {
        public static final int CONTENT_SIZE = 1;
        public byte mResult;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mResult = buffer.get();
            Elog.d(NfcMainPage.TAG, "NfcEmLoopbackRsp result: " + this.mResult);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(this.mResult);
        }

        public int getContentSize() {
            return 1;
        }
    }

    public static class NfcEmSwpReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 4;
        public int mAction;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_fm_swp_test_req action: " + this.mAction);
        }

        public int getContentSize() {
            return 4;
        }
    }

    public static class NfcEmPnfcReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 264;
        public static final int DATA_MAX_LENGTH = 256;
        public int mAction;
        public byte[] mData = new byte[256];
        public int mDataLen;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            int byteToInt = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mDataLen = byteToInt;
            buffer.get(this.mData, 0, byteToInt);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_em_pnfc_req action: " + this.mAction);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mDataLen));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_em_pnfc_req datalen: " + this.mDataLen);
            buffer.put(this.mData, 0, this.mDataLen);
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_em_pnfc_req data: " + new String(this.mData));
        }

        public int getContentSize() {
            return 264;
        }
    }

    public static class NfcEmPnfcRsp implements RawOperation {
        public static final int CONTENT_SIZE = 264;
        private static final int DATA_LENGTH = 256;
        public byte[] mData = new byte[256];
        public int mLength;
        public int mResult;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mResult = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            int byteToInt = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mLength = byteToInt;
            buffer.get(this.mData, 0, byteToInt);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mResult));
            buffer.put(NfcCommand.DataConvert.intToLH(this.mLength));
            buffer.put(this.mData, 0, 256);
        }

        public int getContentSize() {
            return 264;
        }
    }

    public static class NfcEmVirtualCardReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 12;
        public int mAction;
        public int mSupportType;
        public int mTypeFDataRate;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mSupportType = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeFDataRate = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_victual_card_req action: " + this.mAction);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mSupportType));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_victual_card_req supporttype: " + this.mSupportType);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeFDataRate));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_victual_card_req typeF_datarate: " + this.mTypeFDataRate);
        }

        public int getContentSize() {
            return 12;
        }
    }

    public static class NfcEmTxCarrAlsOnReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 4;
        public int mAction;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_tx_carr_als_on_req action: " + this.mAction);
        }

        public int getContentSize() {
            return 4;
        }
    }

    public static class NfcEmPollingReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 92;
        public int mAction;
        public NfcEmAlsCardmReq mCardmReq = new NfcEmAlsCardmReq();
        public int mEnableFunc;
        public NfcEmAlsP2pReq mP2pmReq = new NfcEmAlsP2pReq();
        public int mPeriod;
        public int mPhase;
        public NfcEmAlsReadermReq mReadermReq = new NfcEmAlsReadermReq();

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mPhase = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mPeriod = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mEnableFunc = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mP2pmReq.readRaw(buffer);
            this.mCardmReq.readRaw(buffer);
            this.mReadermReq.readRaw(buffer);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_polling_req action: " + this.mAction);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mPhase));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_polling_req phase: " + this.mPhase);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mPeriod));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_polling_req period: " + this.mPeriod);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mEnableFunc));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_polling_req enablefunc: " + this.mEnableFunc);
            this.mP2pmReq.writeRaw(buffer);
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_polling_req write p2p.");
            this.mCardmReq.writeRaw(buffer);
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_polling_req write card mode.");
            this.mReadermReq.writeRaw(buffer);
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_polling_req write reader mode.");
        }

        public int getContentSize() {
            return 92;
        }
    }

    public static class NfcEmPollingNty implements RawOperation {
        public static final int CONTENT_SIZE = 48;
        public byte[] mData = new byte[44];
        public int mDetectType;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mDetectType = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_polling_ntf detecttype: " + this.mDetectType);
            buffer.get(this.mData, 0, 44);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mDetectType));
            buffer.put(this.mData, 0, 44);
        }

        public int getContentSize() {
            return 48;
        }
    }

    public static class NfcEmAlsP2pReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 28;
        public int mAction;
        public int mIsDisableCardM;
        public int mMode;
        public int mRole;
        public int mSupportType;
        public int mTypeADataRate;
        public int mTypeFDataRate;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mSupportType = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeADataRate = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeFDataRate = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mMode = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mRole = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mIsDisableCardM = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_p2p_req action: " + this.mAction);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mSupportType));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_p2p_req supporttype: " + this.mSupportType);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeADataRate));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_p2p_req typeA_speedrate: " + this.mTypeADataRate);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeFDataRate));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_p2p_req typeV_speedrate: " + this.mTypeFDataRate);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mMode));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_p2p_req mode: " + this.mMode);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mRole));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_p2p_req role: " + this.mRole);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mIsDisableCardM));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_p2p_req isDisableCardM: " + this.mIsDisableCardM);
        }

        public int getContentSize() {
            return 28;
        }
    }

    public static class NfcEmAlsP2pNtf implements RawOperation {
        public static final int CONTENT_SIZE = 4;
        public static final int DATA_MAX_LENGTH = 256;
        public int mResult;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mResult = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_em_als_p2p_ntf result: " + this.mResult);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mResult));
        }

        public int getContentSize() {
            return 4;
        }
    }

    public static class NfcEmAlsCardmReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 16;
        public int mAction;
        public int mFgVirtualCard;
        public int mSupportType;
        public int mSwNum;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mSwNum = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mSupportType = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_cardm_req action: " + this.mAction);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mSwNum));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_cardm_req SWNum: " + this.mSwNum);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mSupportType));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_cardm_req supporttype: " + this.mSupportType);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mFgVirtualCard));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_cardm_req fgvirtualcard: " + this.mFgVirtualCard);
        }

        public int getContentSize() {
            return 16;
        }
    }

    public static class NfcEmAlsReadermReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 32;
        public int mAction;
        public int mSupportType;
        public int mTypeADataRate;
        public int mTypeBDataRate;
        public int mTypeFDataRate;
        public int mTypeVCodingMode;
        public int mTypeVDataRate;
        public int mTypeVSubcarrier;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mSupportType = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeADataRate = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeBDataRate = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeVDataRate = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeFDataRate = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeVSubcarrier = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTypeVCodingMode = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_req action: " + this.mAction);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mSupportType));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_req supporttype: " + this.mSupportType);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeADataRate));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_req typeA_datarate: " + this.mTypeADataRate);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeBDataRate));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_req typeB_datarate: " + this.mTypeBDataRate);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeVDataRate));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_req typeV_datarate: " + this.mTypeVDataRate);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeFDataRate));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_req typeF_datarate: " + this.mTypeFDataRate);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeVSubcarrier));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_req typeV_subcarrier: " + this.mTypeVSubcarrier);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mTypeVCodingMode));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_req typeV_codingmode: " + this.mTypeVCodingMode);
        }

        public int getContentSize() {
            return 32;
        }
    }

    public static class NfcEmAlsReadermNtf implements RawOperation {
        public static final int CONTENT_SIZE = 44;
        public static final int DATA_MAX_LENGTH = 32;
        public int mIsNdef;
        public int mResult;
        public byte[] mUid = new byte[32];
        public int mUidLen;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mResult = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_em_als_readerm_ntf result: " + this.mResult);
            this.mIsNdef = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_em_als_readerm_ntf isNDEF: " + this.mIsNdef);
            this.mUidLen = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_em_als_readerm_ntf UidLen: " + this.mUidLen);
            buffer.get(this.mUid, 0, this.mUidLen);
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]s_mtk_nfc_em_als_readerm_ntf mUid: " + new String(this.mUid));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mResult));
            buffer.put(NfcCommand.DataConvert.intToLH(this.mIsNdef));
            buffer.put(NfcCommand.DataConvert.intToLH(this.mUidLen));
            buffer.put(this.mUid, 0, this.mUidLen);
        }

        public int getContentSize() {
            return 44;
        }
    }

    public static class NfcEmAlsReadermOptReq extends NfcEmReq {
        public static final int CONTENT_SIZE = 594;
        public int mAction;
        public NfcTagWriteNdef mTagWriteNdef = new NfcTagWriteNdef();

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mAction = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mTagWriteNdef.readRaw(buffer);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mAction));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_opt_req action: " + this.mAction);
            this.mTagWriteNdef.writeRaw(buffer);
        }

        public int getContentSize() {
            return CONTENT_SIZE;
        }
    }

    public static class NfcEmAlsReadermOptRsp implements RawOperation {
        public static final int CONTENT_SIZE = 561;
        public int mResult;
        public NfcTagReadNdef mTagReadNdef = new NfcTagReadNdef();

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mResult = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            Elog.d(NfcMainPage.TAG, "[NfcEmReqRsp]mtk_nfc_em_als_readerm_opt_rsp result: " + this.mResult);
            this.mTagReadNdef.readRaw(buffer);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mResult));
            this.mTagReadNdef.writeRaw(buffer);
        }

        public int getContentSize() {
            return CONTENT_SIZE;
        }
    }

    public static class NfcTagReadNdef implements RawOperation {
        public static final int CONTENT_SIZE = 557;
        private static final int DATA_LENGTH = 512;
        private static final int LANG_LENGTH = 3;
        private static final int RECORD_ID_LENGTH = 32;
        public byte[] mData = new byte[512];
        public byte[] mLang = new byte[3];
        public int mLength;
        public NfcNdefType mNdefType = new NfcNdefType();
        public byte mRecordFlags;
        public byte[] mRecordId = new byte[32];
        public byte mRecordTnf;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mNdefType.readRaw(buffer);
            buffer.get(this.mLang, 0, 3);
            this.mRecordFlags = buffer.get();
            buffer.get(this.mRecordId, 0, 32);
            this.mRecordTnf = buffer.get();
            this.mLength = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            buffer.get(this.mData, 0, 512);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            this.mNdefType.readRaw(buffer);
            buffer.put(this.mLang, 0, 3);
            buffer.put(this.mRecordFlags);
            buffer.put(this.mRecordId, 0, 32);
            buffer.put(this.mRecordTnf);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mLength));
            buffer.put(this.mData, 0, 512);
            this.mNdefType.writeRaw(buffer);
        }

        public int getContentSize() {
            return CONTENT_SIZE;
        }
    }

    public static class NfcTagWriteNdef implements RawOperation {
        public static final int CONTENT_SIZE = 590;
        public int mLength;
        public NfcTagWriteNdefData mNdefData = new NfcTagWriteNdefData();
        public NfcNdefLangType mNdefLangType = new NfcNdefLangType();
        public NfcNdefType mNdefType = new NfcNdefType();

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mNdefType.readRaw(buffer);
            this.mNdefLangType.readRaw(buffer);
            this.mLength = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            this.mNdefData.readRaw(buffer);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            this.mNdefType.writeRaw(buffer);
            this.mNdefLangType.writeRaw(buffer);
            buffer.put(NfcCommand.DataConvert.intToLH(this.mLength));
            this.mNdefData.writeRaw(buffer);
        }

        public int getContentSize() {
            return CONTENT_SIZE;
        }
    }

    public static class NfcNdefType implements RawOperation {
        public static final int CONTENT_SIZE = 4;
        public static final int OTHERS = 3;
        public static final int SP = 2;
        public static final int TEXT = 1;
        public static final int URI = 0;
        public int mEnumValue;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mEnumValue = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mEnumValue));
        }

        public int getContentSize() {
            return 4;
        }
    }

    public static class NfcNdefLangType implements RawOperation {
        public static final int CONTENT_SIZE = 4;
        public static final int DE = 1;
        public static final int DEFAULT = 0;
        public static final int EN = 2;
        public static final int FR = 3;
        public int mEnumValue;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mEnumValue = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mEnumValue));
        }

        public int getContentSize() {
            return 4;
        }
    }

    public static class NfcTagWriteNdefData implements RawOperation {
        public static final int CONTENT_SIZE = 578;
        public byte[] mData = new byte[578];

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            buffer.get(this.mData, 0, 578);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(this.mData, 0, 578);
        }

        public int getContentSize() {
            return 578;
        }
    }

    public static class SmartPosterT {
        public static final int COMPANY_LENGTH = 64;
        public static final int COMPANY_URL_LENGTH = 64;
        public static final int CONTENT_SIZE = 132;
        public byte[] mCompany = new byte[64];
        public short mCompanyLength;
        public byte[] mCompanyUrl = new byte[64];
        public short mCompanyUrlLength;

        public byte[] getByteArray() {
            byte[] array = new byte[132];
            byte[] bArr = this.mCompany;
            System.arraycopy(bArr, 0, array, 0, bArr.length);
            byte[] shortArray = NfcCommand.DataConvert.shortToLH(this.mCompanyLength);
            System.arraycopy(shortArray, 0, array, 64, shortArray.length);
            byte[] bArr2 = this.mCompanyUrl;
            System.arraycopy(bArr2, 0, array, shortArray.length + 64, bArr2.length);
            byte[] shortArray2 = NfcCommand.DataConvert.shortToLH(this.mCompanyUrlLength);
            System.arraycopy(shortArray2, 0, array, shortArray2.length + 64 + 64, shortArray2.length);
            return array;
        }
    }

    public static class TextT {
        public static final int CONTENT_SIZE = 514;
        public static final int DATA_LENGTH = 512;
        public byte[] mData = new byte[512];
        public short mDataLength;

        public byte[] getByteArray() {
            byte[] array = new byte[514];
            byte[] bArr = this.mData;
            System.arraycopy(bArr, 0, array, 0, bArr.length);
            byte[] shortArray = NfcCommand.DataConvert.shortToLH(this.mDataLength);
            System.arraycopy(shortArray, 0, array, 512, shortArray.length);
            return array;
        }
    }

    public static class UrlT {
        public static final int CONTENT_SIZE = 66;
        public static final int DATA_LENGTH = 64;
        public byte[] mUrlData = new byte[64];
        public short mUrlDataLength;

        public byte[] getByteArray() {
            byte[] array = new byte[66];
            byte[] bArr = this.mUrlData;
            System.arraycopy(bArr, 0, array, 0, bArr.length);
            byte[] shortArray = NfcCommand.DataConvert.shortToLH(this.mUrlDataLength);
            System.arraycopy(shortArray, 0, array, 64, shortArray.length);
            return array;
        }
    }

    public static abstract class NfcEmReq implements RawOperation {
        public static final int CONTENT_SIZE = 0;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
        }

        public int getContentSize() {
            return 0;
        }
    }

    public static abstract class NfcEmRsp implements RawOperation {
        public static final int CONTENT_SIZE = 4;
        public int mResult;

        public void readRaw(ByteBuffer buffer) throws NullPointerException, BufferUnderflowException {
            this.mResult = NfcCommand.DataConvert.byteToInt(NfcCommand.DataConvert.getByteArr(buffer));
            Elog.d(NfcMainPage.TAG, "NfcEmRsp result: " + this.mResult);
        }

        public void writeRaw(ByteBuffer buffer) throws NullPointerException, BufferOverflowException {
            buffer.put(NfcCommand.DataConvert.intToLH(this.mResult));
        }

        public int getContentSize() {
            return 4;
        }
    }
}
