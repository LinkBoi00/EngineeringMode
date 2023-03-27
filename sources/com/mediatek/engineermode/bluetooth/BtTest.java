package com.mediatek.engineermode.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class BtTest {
    static final int ANT0_INDEX = 0;
    static final int ANT1_INDEX = 1;
    private static final int ANT_SWAP_RES_LENGTH = 9;
    private static final String PROP_CONN_CHIPID = "persist.vendor.connsys.chipid";
    private static final String PROP_INVALID = "-1";
    private static final String TAG = "BtTest";
    private static final int TYPE_ANT_SWITCH = 2;
    private static final int TYPE_DO_TX_ONLY = 0;
    private static final int TYPE_ENABLE_TEST_MODE = 1;
    private static final int VALUE_NOT_USE = -1;

    enum BtTestResult {
        RESULT_SUCCESS,
        RESULT_CHANNEL_HOP_20_NO_SUPPORT,
        RESULT_CHANNEL_HOP_20_SUPPORT,
        RESULT_TEST_FAIL
    }

    public boolean init() {
        try {
            return EmUtils.getEmHidlService().btInit() == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isBLESupport() {
        try {
            return EmUtils.getEmHidlService().btIsBLESupport() == 1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isBLEEnhancedSupport() {
        try {
            return EmUtils.getEmHidlService().btIsBLEEnhancedSupport();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean enableTestMode(int power) {
        try {
            return EmUtils.getEmHidlService().btDoTest(1, power, -1, -1, -1, -1, -1) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean doTxOnlyTest(int pattern, int channel, int pocketType, int pocketTypeLen, int freq) {
        try {
            return EmUtils.getEmHidlService().btDoTest(0, pattern, channel, pocketType, pocketTypeLen, freq, -1) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean noSigRxTestStart(int nPatternIdx, int nPocketTypeIdx, int nFreq, int nAddress) {
        try {
            return EmUtils.getEmHidlService().btStartNoSigRxTest(nPatternIdx, nPocketTypeIdx, nFreq, nAddress);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public int[] noSigRxTestResult() {
        try {
            ArrayList<Integer> result = EmUtils.getEmHidlService().btEndNoSigRxTest();
            int size = result.size();
            int[] data = new int[size];
            for (int i = 0; i < size; i++) {
                data[i] = result.get(i).intValue();
            }
            return data;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean unInit() {
        try {
            return EmUtils.getEmHidlService().btUninit() == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public char[] hciCommandRun(char[] hciCmd, int cmdLength) {
        ArrayList<Byte> input = new ArrayList<>();
        for (char cmdByte : hciCmd) {
            input.add(Byte.valueOf((byte) cmdByte));
        }
        try {
            ArrayList<Byte> result = EmUtils.getEmHidlService().btHciCommandRun(input);
            int size = result.size();
            char[] data = new char[size];
            for (int i = 0; i < size; i++) {
                Elog.i(TAG, "result.get(i):" + result.get(i));
                Elog.i(TAG, "result.after:" + (result.get(i).byteValue() & 255));
                data[i] = (char) (result.get(i).byteValue() & 255);
            }
            return data;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void runHCIResetCmd() {
        hciCommandRun(new char[]{1, 3, 12, 0}, 4);
    }

    /* access modifiers changed from: package-private */
    public boolean isComboSupport() {
        String strConnChipId = SystemProperties.get(PROP_CONN_CHIPID);
        Elog.i(TAG, "strConnChipId:" + strConnChipId);
        return strConnChipId != null && !strConnChipId.isEmpty() && !strConnChipId.equals(PROP_INVALID);
    }

    public int pollingStart() {
        try {
            return EmUtils.getEmHidlService().btPollingStart();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int pollingStop() {
        try {
            return EmUtils.getEmHidlService().btPollingStop();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* access modifiers changed from: package-private */
    public int getAntNum() {
        char[] cmd = {1, 148, 253, 1, 0};
        char[] res = hciCommandRun(cmd, cmd.length);
        if (res == null || res.length < 9) {
            return 1;
        }
        return res[8];
    }

    /* access modifiers changed from: package-private */
    public boolean switchAnt(int index, boolean checkRes) {
        Elog.i(TAG, "index:" + index + " checkRes:" + checkRes);
        try {
            return EmUtils.getEmHidlService().btDoTest(2, index, checkRes ? 1 : 0, -1, -1, -1, -1) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean startRelayerMode(int port, int baudrate) {
        try {
            return EmUtils.getEmHidlService().btStartRelayer(port, baudrate) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean stopRelayerMode() {
        try {
            return EmUtils.getEmHidlService().btStopRelayer() == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean checkInitState(BluetoothAdapter adapter, Context context) {
        if (adapter == null) {
            Toast.makeText(context, R.string.bt_no_dev, 1).show();
            return false;
        }
        int btState = adapter.getState();
        Elog.i(TAG, "btState:" + btState);
        if (btState != 10) {
            Toast.makeText(context, R.string.bt_turn_bt_off, 1).show();
            return false;
        }
        int bleState = adapter.getLeState();
        Elog.i(TAG, "bleState:" + bleState);
        if (bleState == 10) {
            return true;
        }
        Toast.makeText(context, R.string.bt_turn_ble_off, 1).show();
        return false;
    }
}
