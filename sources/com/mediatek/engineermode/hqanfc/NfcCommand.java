package com.mediatek.engineermode.hqanfc;

import android.widget.CheckBox;
import android.widget.RadioButton;
import java.nio.ByteBuffer;

public class NfcCommand {
    public static final String ACTION_PRE = "com.mediatek.hqanfc.";
    public static final int EM_ALS_CARD_M_SW_NUM_SWIO1 = 1;
    public static final int EM_ALS_CARD_M_SW_NUM_SWIO2 = 2;
    public static final int EM_ALS_CARD_M_SW_NUM_SWIOSE = 4;
    public static final int EM_ALS_READER_M_CODING_MODE_256 = 1;
    public static final int EM_ALS_READER_M_CODING_MODE_4 = 0;
    public static final int EM_ALS_READER_M_DUAL_SUB_CARRIER = 1;
    public static final int EM_ALS_READER_M_SPDRATE_106 = 1;
    public static final int EM_ALS_READER_M_SPDRATE_212 = 2;
    public static final int EM_ALS_READER_M_SPDRATE_2648 = 32;
    public static final int EM_ALS_READER_M_SPDRATE_424 = 4;
    public static final int EM_ALS_READER_M_SPDRATE_662 = 16;
    public static final int EM_ALS_READER_M_SPDRATE_848 = 8;
    public static final int EM_ALS_READER_M_SUB_CARRIER = 0;
    public static final int EM_ALS_READER_M_TYPE_A = 1;
    public static final int EM_ALS_READER_M_TYPE_B = 2;
    public static final int EM_ALS_READER_M_TYPE_BPRIME = 16;
    public static final int EM_ALS_READER_M_TYPE_F = 4;
    public static final int EM_ALS_READER_M_TYPE_KOVIO = 32;
    public static final int EM_ALS_READER_M_TYPE_V = 8;
    public static final int EM_ENABLE_FUNC_P2P_MODE = 4;
    public static final int EM_ENABLE_FUNC_RCARDR_MODE = 2;
    public static final int EM_ENABLE_FUNC_READER_MODE = 1;
    public static final int EM_P2P_MODE_ACTIVE_MODE = 2;
    public static final int EM_P2P_MODE_PASSIVE_MODE = 1;
    public static final int EM_P2P_ROLE_INITIATOR_MODE = 1;
    public static final int EM_P2P_ROLE_TARGET_MODE = 2;
    public static final int INT_SIZE = 4;
    public static final int MAIN_MESSAGE_SIZE = 8;
    public static final String MESSAGE_CONTENT_KEY = "content";
    private static final int MOVE_BIT_16 = 16;
    private static final int MOVE_BIT_24 = 24;
    private static final int MOVE_BIT_8 = 8;
    private static final int NUMBER_OXFF = 255;
    private static final int POS_3 = 3;
    private static final int POS_4 = 4;
    private static final int POS_5 = 5;
    public static final int RECEIVE_DATA_SIZE = 1024;
    private ByteBuffer mMessageContent;
    private int mMessageType;

    public static class CommandType {
        public static final int MTK_EM_LOOPBACK_TEST_REQ = 129;
        public static final int MTK_EM_LOOPBACK_TEST_RSP = 130;
        public static final int MTK_NFC_EM_ALS_CARD_MODE_REQ = 107;
        public static final int MTK_NFC_EM_ALS_CARD_MODE_RSP = 108;
        public static final int MTK_NFC_EM_ALS_P2P_MODE_NTF = 119;
        public static final int MTK_NFC_EM_ALS_P2P_MODE_REQ = 105;
        public static final int MTK_NFC_EM_ALS_P2P_MODE_RSP = 106;
        public static final int MTK_NFC_EM_ALS_READER_MODE_NTF = 118;
        public static final int MTK_NFC_EM_ALS_READER_MODE_OPT_REQ = 103;
        public static final int MTK_NFC_EM_ALS_READER_MODE_OPT_RSP = 104;
        public static final int MTK_NFC_EM_ALS_READER_MODE_REQ = 101;
        public static final int MTK_NFC_EM_ALS_READER_MODE_RSP = 102;
        public static final int MTK_NFC_EM_DEACTIVATE_CMD = 135;
        public static final int MTK_NFC_EM_MSG_END = 204;
        public static final int MTK_NFC_EM_PNFC_CMD_REQ = 115;
        public static final int MTK_NFC_EM_PNFC_CMD_RSP = 116;
        public static final int MTK_NFC_EM_POLLING_MODE_NTF = 117;
        public static final int MTK_NFC_EM_POLLING_MODE_REQ = 109;
        public static final int MTK_NFC_EM_POLLING_MODE_RSP = 110;
        public static final int MTK_NFC_EM_START_CMD = 100;
        public static final int MTK_NFC_EM_STOP_CMD = 120;
        public static final int MTK_NFC_EM_TX_CARRIER_ALS_ON_REQ = 111;
        public static final int MTK_NFC_EM_TX_CARRIER_ALS_ON_RSP = 112;
        public static final int MTK_NFC_EM_VIRTUAL_CARD_REQ = 113;
        public static final int MTK_NFC_EM_VIRTUAL_CARD_RSP = 114;
        public static final int MTK_NFC_FM_SWP_TEST_NTF = 202;
        public static final int MTK_NFC_FM_SWP_TEST_REQ = 201;
        public static final int MTK_NFC_FM_SWP_TEST_RSP = 203;
        public static final int MTK_NFC_SW_VERSION_QUERY = 131;
        public static final int MTK_NFC_SW_VERSION_RESPONSE = 132;
        public static final int MTK_NFC_TESTMODE_SETTING_REQ = 127;
        public static final int MTK_NFC_TESTMODE_SETTING_RSP = 128;
    }

    public static class EmAction {
        public static final int ACTION_RUNINBG = 2;
        public static final int ACTION_START = 0;
        public static final int ACTION_STOP = 1;
    }

    public static class EmOptAction {
        public static final int FORMAT = 2;
        public static final int READ = 0;
        public static final int WRITE = 1;
    }

    public static class P2pDisableCardM {
        public static final int DISABLE = 1;
        public static final int NOT_DISABLE = 0;
    }

    public static class ReaderModeRspResult {
        public static final int CONNECT = 0;
        public static final int DISCONNECT = 2;
        public static final int FAIL = 1;
    }

    public static class RspResult {
        public static final int FAIL = 1;
        public static final int NFC_STATUS_INVALID_FORMAT = 33;
        public static final int NFC_STATUS_INVALID_NDEF_FORMAT = 32;
        public static final int NFC_STATUS_LINK_DOWN = 0;
        public static final int NFC_STATUS_LINK_UP = 1;
        public static final int NFC_STATUS_NDEF_EOF_REACHED = 34;
        public static final int NFC_STATUS_NOT_SUPPORT = 10;
        public static final int NFC_STATUS_NO_SIM = 225;
        public static final int NFC_STATUS_REMOVE_SE = 227;
        public static final int SUCCESS = 0;
    }

    public NfcCommand(int msgType, ByteBuffer bufferCont) {
        this.mMessageType = msgType;
        this.mMessageContent = bufferCont;
    }

    public ByteBuffer getMessageContent() {
        return this.mMessageContent;
    }

    public void setMessageContent(ByteBuffer mMessageLenContent) {
        this.mMessageContent = mMessageLenContent;
    }

    public int getMessageType() {
        return this.mMessageType;
    }

    public void setMessageType(int messageType) {
        this.mMessageType = messageType;
    }

    public static class DataConvert {
        public static byte[] intToLH(int n) {
            return new byte[]{(byte) (n & 255), (byte) ((n >> 8) & 255), (byte) ((n >> 16) & 255), (byte) ((n >> 24) & 255)};
        }

        public static int byteToInt(byte[] b) {
            int n = 0;
            for (int i = 0; i < 4; i++) {
                n = (n << 8) + (b[3 - i] & 255);
            }
            return n;
        }

        static int byte2uint16(byte[] b) {
            if (b != null && b.length == 2) {
                return (b[0] + b[1]) << 8;
            }
            throw new IllegalArgumentException("invalid uint16 byte array");
        }

        public static byte[] shortToLH(short n) {
            return new byte[]{(byte) (n & 255), (byte) ((n >> 8) & 255)};
        }

        public static byte[] getByteArr(ByteBuffer buffer) {
            byte[] b = new byte[4];
            buffer.get(b);
            return b;
        }

        public static String printHexString(byte[] b, int length) {
            String string = "";
            int leng = b.length;
            if (length != 0) {
                leng = length;
            }
            for (int i = 0; i < leng; i++) {
                String hex = Integer.toHexString(b[i] & 255);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                string = string + "0x" + hex.toUpperCase() + " ";
            }
            return string;
        }

        public static String printHexString(byte b) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            return "" + "0x" + hex.toUpperCase() + " ";
        }

        public static long readUnsignedInt(byte[] bytes) {
            return ((long) (bytes[0] & 255)) | (((long) (bytes[1] & 255)) << 8) | (((long) (bytes[2] & 255)) << 16) | (((long) (bytes[3] & 255)) << 24);
        }
    }

    public static class BitMapValue {
        public static int getTypeValue(CheckBox[] checkBoxs) {
            int i = 0;
            int result = 0 | checkBoxs[0].isChecked() | (checkBoxs[1].isChecked() ? 2 : 0) | (checkBoxs[2].isChecked() ? 4 : 0) | (checkBoxs[3].isChecked() ? 8 : 0);
            if (checkBoxs[4].isChecked()) {
                i = 32;
            }
            return (int) (result | i);
        }

        public static int getTypeAbDataRateValue(RadioButton[] checkBoxs) {
            int i = 0;
            int result = 0 | checkBoxs[0].isChecked() | (checkBoxs[1].isChecked() ? 2 : 0) | (checkBoxs[2].isChecked() ? 4 : 0);
            if (checkBoxs[3].isChecked()) {
                i = 8;
            }
            return (int) (result | i);
        }

        public static int getTypeFDataRateValue(RadioButton[] checkBoxs) {
            int i = 0;
            int result = 0 | (checkBoxs[0].isChecked() ? 2 : 0);
            if (checkBoxs[1].isChecked()) {
                i = 4;
            }
            return result | i;
        }

        public static int getTypeVDataRateValue(RadioButton[] checkBoxs) {
            int i = 0;
            int result = 0 | (checkBoxs[0].isChecked() ? 16 : 0);
            if (checkBoxs[1].isChecked()) {
                i = 32;
            }
            return result | i;
        }

        public static int getFunctionValue(CheckBox[] checkBoxs) {
            int i = 0;
            int result = 0 | checkBoxs[0].isChecked() | (checkBoxs[1].isChecked() ? 2 : 0);
            if (checkBoxs[2].isChecked()) {
                i = 4;
            }
            return (int) (result | i);
        }

        public static int getSwioValue(CheckBox[] checkBoxs) {
            int i = 0;
            int result = 0 | checkBoxs[0].isChecked() | (checkBoxs[1].isChecked() ? 2 : 0);
            if (checkBoxs[2].isChecked()) {
                i = 4;
            }
            return (int) (result | i);
        }
    }
}
