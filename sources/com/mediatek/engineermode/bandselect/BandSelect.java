package com.mediatek.engineermode.bandselect;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.RadioStateManager;
import java.util.ArrayList;
import java.util.Iterator;

public class BandSelect extends Activity implements View.OnClickListener {
    private static final int INDEX_BAND_MAX = 10;
    private static final int INDEX_CDMA_BAND = 10;
    private static final int INDEX_GSM_BAND = 0;
    private static final int INDEX_LTE_BAND_128 = 5;
    private static final int INDEX_LTE_BAND_160 = 6;
    private static final int INDEX_LTE_BAND_192 = 7;
    private static final int INDEX_LTE_BAND_224 = 8;
    private static final int INDEX_LTE_BAND_256 = 9;
    private static final int INDEX_LTE_BAND_96 = 4;
    private static final int INDEX_LTE_FDD_BAND = 2;
    private static final int INDEX_LTE_TDD_BAND = 3;
    private static final int INDEX_NR = 11;
    private static final int INDEX_UMTS_BAND = 1;
    private static final String PREF_FILE = "band_select_";
    private static final String[] PREF_KEYS = {"gsm", "umts", "lte_fdd", "lte_tdd", "lte_96", "lte_128", "lte_160", "lte_192", "lte_224", "lte_256", "cdma", "nr"};
    private static final String TAG = "BandSelect";
    private static final int TDSCDMA = 2;
    private static final int WCDMA = 1;
    /* access modifiers changed from: private */
    public static int mSimType;
    private Button mBtnReset;
    private Button mBtnSet;
    private final ArrayList<BandModeMap> mCdmaModeArray = new ArrayList<>();
    /* access modifiers changed from: private */
    public long mCurrentCdmaValues = 0;
    /* access modifiers changed from: private */
    public long[] mCurrentGsmValues = new long[10];
    /* access modifiers changed from: private */
    public long[] mCurrentNRValues = null;
    private final ArrayList<BandModeMap> mGsmModeArray = new ArrayList<>();
    private boolean mIsCdmaValid = true;
    private boolean mIsLteExtend = false;
    private boolean mIsLteValid = true;
    private boolean mIsNRValid = true;
    /* access modifiers changed from: private */
    public boolean mIsThisAlive = true;
    private int mNRIndexBandMax = -1;
    private final ArrayList<BandModeMap> mNRModeArray = new ArrayList<>();
    /* access modifiers changed from: private */
    public RadioStateManager mRadioStateManager;
    private final Handler mResponseHander = new Handler() {
        public void handleMessage(Message msg) {
            if (BandSelect.this.mIsThisAlive) {
                switch (msg.what) {
                    case 100:
                        AsyncResult asyncResult = (AsyncResult) msg.obj;
                        if (asyncResult.exception == null) {
                            BandSelect.this.showBandModeGsm(asyncResult, 100);
                            return;
                        } else {
                            EmUtils.showToast("Query GSM Supported Mode Failed.");
                            return;
                        }
                    case 101:
                        AsyncResult asyncResult2 = (AsyncResult) msg.obj;
                        if (asyncResult2.exception == null) {
                            BandSelect.this.showBandModeGsm(asyncResult2, 101);
                            return;
                        } else {
                            EmUtils.showToast("Query GSM Current Mode Failed.");
                            return;
                        }
                    case 102:
                        AsyncResult asyncResult3 = (AsyncResult) msg.obj;
                        if (asyncResult3.exception == null) {
                            BandSelect.this.showBandModeCdma(asyncResult3, 102);
                            return;
                        } else {
                            EmUtils.showToast("Query CDMA Current Mode Failed.", true);
                            return;
                        }
                    case 103:
                        AsyncResult asyncResult4 = (AsyncResult) msg.obj;
                        if (asyncResult4.exception == null) {
                            BandSelect.this.showBandModeNR(asyncResult4, 103);
                            return;
                        } else {
                            EmUtils.showToast("Query NR Supported Mode Failed.", true);
                            return;
                        }
                    case 104:
                        AsyncResult asyncResult5 = (AsyncResult) msg.obj;
                        if (asyncResult5.exception == null) {
                            BandSelect.this.showBandModeNR(asyncResult5, 104);
                            return;
                        } else {
                            EmUtils.showToast("Query NR Current Mode Failed.", true);
                            return;
                        }
                    case 110:
                        if (((AsyncResult) msg.obj).exception == null) {
                            BandSelect bandSelect = BandSelect.this;
                            long[] unused = bandSelect.mCurrentGsmValues = bandSelect.mSetGsmValues;
                            EmUtils.showToast("set Gsm BandMode succeed", true);
                            Elog.v(BandSelect.TAG, "set Gsm BandMode succeed");
                            if (ModemCategory.CheckViceSimNRCapability(BandSelect.mSimType)) {
                                BandSelect bandSelect2 = BandSelect.this;
                                bandSelect2.setBandModeNR(bandSelect2.mSetNRValues);
                            }
                            if (ModemCategory.isCdma() && !FeatureSupport.is90Modem() && ModemCategory.CheckViceSimCdmaCapability(BandSelect.mSimType) && BandSelect.this.mSetCdmaValues != BandSelect.this.mCurrentCdmaValues) {
                                BandSelect bandSelect3 = BandSelect.this;
                                bandSelect3.setBandModeCdma(bandSelect3.mSetCdmaValues);
                                return;
                            }
                            return;
                        }
                        EmUtils.showToast("set Gsm BandMode failed", true);
                        Elog.v(BandSelect.TAG, "set Gsm BandMode failed");
                        return;
                    case 111:
                        if (((AsyncResult) msg.obj).exception == null) {
                            BandSelect bandSelect4 = BandSelect.this;
                            long unused2 = bandSelect4.mCurrentCdmaValues = bandSelect4.mSetCdmaValues;
                            EmUtils.showToast("set CDMA BandMode succeed", true);
                            Elog.v(BandSelect.TAG, "set CDMA BandMode succeed");
                        } else {
                            EmUtils.showToast("set CDMA BandMode failed", true);
                            Elog.v(BandSelect.TAG, "set CDMA BandMode failed");
                        }
                        if (FeatureSupport.is93ModemAndAbove()) {
                            BandSelect.this.mRadioStateManager.rebootModem();
                            return;
                        }
                        return;
                    case 112:
                        if (((AsyncResult) msg.obj).exception == null) {
                            BandSelect bandSelect5 = BandSelect.this;
                            long[] unused3 = bandSelect5.mCurrentNRValues = bandSelect5.mSetNRValues;
                            EmUtils.showToast("set NR BandMode succeed", true);
                            Elog.v(BandSelect.TAG, "set NR BandMode succeed");
                            return;
                        }
                        EmUtils.showToast("set NR BandMode failed", true);
                        Elog.v(BandSelect.TAG, "set NR BandMode failed");
                        return;
                    default:
                        return;
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public long mSetCdmaValues = 0;
    /* access modifiers changed from: private */
    public long[] mSetGsmValues = new long[10];
    /* access modifiers changed from: private */
    public long[] mSetNRValues = null;

    private CheckBox addCheckboxToTable(String name, int tableResId, String bandName) {
        TableRow row = new TableRow(this);
        TextView textView = new TextView(this);
        textView.setText(name);
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(bandName);
        row.addView(textView);
        row.addView(checkBox);
        ((TableLayout) findViewById(tableResId)).addView(row);
        return checkBox;
    }

    private void initGsmArray() {
        findViewById(R.id.TableLayout_GSM).setVisibility(0);
    }

    private void initUtmsArray() {
        findViewById(R.id.TableLayout_UTMS).setVisibility(0);
    }

    private void initCdmaArray() {
        findViewById(R.id.TableLayout_CDMA).setVisibility(0);
    }

    private void initNRArray() {
        findViewById(R.id.TableLayout_NR).setVisibility(0);
    }

    private void initLteArray() {
        findViewById(R.id.TableLayout_LTE).setVisibility(0);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimType = getIntent().getIntExtra("mSimType", 0);
        int modemType = ModemCategory.getModemType();
        setContentView(R.layout.bandmodeselect);
        this.mBtnSet = (Button) findViewById(R.id.BandSel_Btn_Set);
        this.mBtnReset = (Button) findViewById(R.id.BandSel_Btn_Reset);
        this.mBtnSet.setOnClickListener(this);
        this.mBtnReset.setOnClickListener(this);
        initGsmArray();
        if (!ModemCategory.isCapabilitySim(mSimType)) {
            if (ModemCategory.checkViceSimCapability(mSimType, 4)) {
                initUtmsArray();
            }
            if (ModemCategory.checkViceSimCapability(mSimType, 4096) && ModemCategory.isLteSupport()) {
                initLteArray();
            }
        } else if (modemType == 2 || modemType == 1) {
            initUtmsArray();
            if (ModemCategory.isLteSupport()) {
                initLteArray();
            }
        }
        if (ModemCategory.isCdma() && !FeatureSupport.is90Modem()) {
            initCdmaArray();
            queryCurrentModeCdma();
            if (!ModemCategory.CheckViceSimCdmaCapability(mSimType)) {
                findViewById(R.id.TableLayout_CDMA).setVisibility(8);
            }
        }
        querySupportModeGsm();
        queryCurrentModeGsm();
        if (ModemCategory.CheckViceSimNRCapability(mSimType)) {
            initNRArray();
            querySupportModeNR();
            queryCurrentModeNR();
        }
        RadioStateManager radioStateManager = new RadioStateManager(this);
        this.mRadioStateManager = radioStateManager;
        radioStateManager.registerRadioStateChanged((RadioStateManager.RadioListener) null);
    }

    /* access modifiers changed from: private */
    public void showBandModeGsm(AsyncResult aSyncResult, int msg) {
        int i = msg;
        for (String value : (String[]) aSyncResult.result) {
            if (i == 100) {
                Elog.v(TAG, "gsm support --.>" + value);
            } else {
                Elog.v(TAG, "gsm current --.>" + value);
            }
            String[] getDigitalVal = value.substring(BandModeContent.SAME_COMMAND.length()).split(",");
            if (getDigitalVal != null && getDigitalVal.length > 1) {
                long[] values = new long[10];
                for (int i2 = 0; i2 < values.length; i2++) {
                    if (getDigitalVal.length <= i2 || getDigitalVal[i2] == null) {
                        values[i2] = 0;
                    } else {
                        try {
                            values[i2] = Long.valueOf(getDigitalVal[i2].trim()).longValue();
                        } catch (NumberFormatException e) {
                            values[i2] = 0;
                        }
                    }
                }
                if (i == 100) {
                    setSupportedModeGsm(values);
                    if (getDigitalVal.length > 5) {
                        Elog.v(TAG, "The Modem support Lte extend band");
                        this.mIsLteExtend = true;
                    } else {
                        Elog.v(TAG, "The Modem not support Lte extend band");
                        this.mIsLteExtend = false;
                    }
                } else {
                    setCurrentModeGsm(values);
                    this.mCurrentGsmValues = getValFromBoxGsm(false);
                    saveDefaultValueIfNeedGsm(values);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void showBandModeNR(AsyncResult aSyncResult, int msg) {
        int i = msg;
        String[] result = (String[]) aSyncResult.result;
        int length = result.length;
        int i2 = 0;
        while (i2 < length) {
            String value = result[i2];
            if (i == 103) {
                Elog.v(TAG, "NR support --.>" + value);
            } else {
                Elog.v(TAG, "NR current --.>" + value);
            }
            try {
                String info = value.substring(BandModeContent.SAME_NR_COMMAND.length()).split(",")[3].replace("\"", "");
                if (this.mNRIndexBandMax < 0) {
                    int len = info.length() / 8;
                    if (info.length() % 8 != 0) {
                        len++;
                    }
                    Elog.v(TAG, "NR array len = " + len);
                    this.mNRIndexBandMax = len;
                }
                int i3 = this.mNRIndexBandMax;
                long[] bandNRValue = new long[i3];
                String info2 = EmUtils.addZeroForNum(info, i3 * 8);
                for (int i4 = 0; i4 < this.mNRIndexBandMax; i4++) {
                    bandNRValue[i4] = Long.parseLong(info2.substring(i4 * 8, (i4 + 1) * 8), 16);
                }
                if (i == 103) {
                    setSupportedModeNR(bandNRValue);
                } else {
                    setCurrentModeNR(bandNRValue);
                    this.mCurrentNRValues = bandNRValue;
                    saveDefaultValueNR(bandNRValue);
                }
                i2++;
            } catch (Exception e) {
                Elog.e(TAG, "NR parse failed," + e.getMessage());
                return;
            }
        }
    }

    private void querySupportModeGsm() {
        String[] modeString = {BandModeContent.QUERY_SUPPORT_COMMAND, BandModeContent.SAME_COMMAND};
        Elog.v(TAG, "querySupportGsmMode AT String:" + modeString[0]);
        sendATCommand(modeString, 100);
    }

    private void queryCurrentModeGsm() {
        String[] modeString = {BandModeContent.QUERY_CURRENT_COMMAND, BandModeContent.SAME_COMMAND};
        Elog.v(TAG, "queryCurrentGSMMode AT String:" + modeString[0]);
        sendATCommand(modeString, 101);
    }

    /* access modifiers changed from: private */
    public void setBandModeGsm(long[] values) {
        String[] modeString = {BandModeContent.SET_COMMAND + values[0] + "," + values[1], ""};
        if (ModemCategory.isLteSupport()) {
            modeString[0] = modeString[0] + "," + values[2] + "," + values[3];
            if (this.mIsLteExtend) {
                for (int i = 4; i < 10; i++) {
                    modeString[0] = modeString[0] + "," + values[i];
                }
            }
        }
        Elog.v(TAG, "setGsmBandMode AT String:" + modeString[0]);
        sendATCommand(modeString, 110);
    }

    private void sendATCommand(String[] atCommand, int msg) {
        EmUtils.invokeOemRilRequestStringsEm(mSimType, atCommand, this.mResponseHander.obtainMessage(msg));
    }

    private void querySupportModeNR() {
        String[] modeString = {BandModeContent.QUERY_SUPPORT_NR_COMMAND, BandModeContent.SAME_NR_COMMAND};
        Elog.v(TAG, "querySupportNRMode AT String:" + modeString[0]);
        sendATCommand(modeString, 103);
    }

    private void queryCurrentModeNR() {
        String[] modeString = {BandModeContent.QUERY_CURRENT_NR_COMMAND, BandModeContent.SAME_NR_COMMAND};
        Elog.v(TAG, "queryCurrentNRMode AT String:" + modeString[0]);
        sendATCommand(modeString, 104);
    }

    /* access modifiers changed from: private */
    public void setBandModeNR(long[] value) {
        String[] cmd = new String[2];
        cmd[0] = BandModeContent.SET_NR_COMMAND;
        cmd[0] = cmd[0] + "\"" + String.format("%08x", new Object[]{Long.valueOf(this.mSetGsmValues[0])}) + "\",\"" + String.format("%08x", new Object[]{Long.valueOf(this.mSetGsmValues[1])}) + "\",\"" + String.format("%08x", new Object[]{Long.valueOf(this.mSetGsmValues[2])}) + String.format("%08x", new Object[]{Long.valueOf(this.mSetGsmValues[3])}) + String.format("%08x", new Object[]{Long.valueOf(this.mSetGsmValues[4])}) + "\",\"";
        for (int i = 0; i < this.mSetNRValues.length; i++) {
            cmd[0] = cmd[0] + String.format("%08x", new Object[]{Long.valueOf(this.mSetNRValues[i])});
            Elog.v(TAG, "setBandModeNR i=" + i + ": " + cmd[0]);
        }
        cmd[0] = cmd[0] + "\"";
        cmd[1] = "";
        Elog.v(TAG, "setNrBandMode AT String:" + cmd[0]);
        sendATCommand(cmd, 112);
    }

    private long[] getValFromBoxGsm(boolean judge) {
        long[] values = new long[10];
        long[] values_temp = new long[10];
        Iterator<BandModeMap> it = this.mGsmModeArray.iterator();
        while (it.hasNext()) {
            BandModeMap m = it.next();
            if (m.mChkBox.isChecked()) {
                int i = m.mIndex;
                values[i] = values[i] | (1 << m.mBit);
                int i2 = m.mIndex;
                values_temp[i2] = values_temp[i2] | (1 << m.mBit);
            }
        }
        if (judge) {
            for (int i3 = 4; i3 <= 9; i3++) {
                values_temp[2] = values_temp[2] | values_temp[i3];
            }
            values_temp[2] = values_temp[2] | values_temp[3];
            values_temp[3] = values_temp[2];
            int i4 = 0;
            while (true) {
                if (i4 > 3) {
                    break;
                }
                Elog.v(TAG, "mCurrentGsmValues[" + i4 + "] = " + this.mCurrentGsmValues[i4] + ", to values[" + i4 + "] = " + values[i4]);
                if (values_temp[i4] == 0 && this.mCurrentGsmValues[i4] != 0) {
                    this.mIsLteValid = false;
                    break;
                }
                if (!this.mIsLteValid) {
                    this.mIsLteValid = true;
                }
                i4++;
            }
            if (values[0] == 0) {
                values[0] = 255;
            }
            if (values[1] == 0) {
                values[1] = 65535;
            }
            if (values_temp[2] == 0 && values_temp[3] == 0) {
                values[2] = 4294967295L;
                values[3] = 4294967295L;
                Elog.v(TAG, "lte not to null");
            }
        }
        return values;
    }

    private long[] getValFromBoxNR() {
        long[] values = new long[this.mNRIndexBandMax];
        Iterator<BandModeMap> it = this.mNRModeArray.iterator();
        while (it.hasNext()) {
            BandModeMap m = it.next();
            if (m.mChkBox.isChecked()) {
                int i = m.mIndex;
                values[i] = values[i] | (1 << m.mBit);
            }
        }
        boolean isCurrentNRValid = false;
        this.mIsNRValid = false;
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i2 >= this.mNRIndexBandMax) {
                break;
            }
            boolean z2 = this.mIsNRValid | (values[i2] != 0);
            this.mIsNRValid = z2;
            if (z2) {
                break;
            }
            if (this.mCurrentNRValues[i2] == 0) {
                z = false;
            }
            isCurrentNRValid |= z;
            i2++;
        }
        if (!this.mIsNRValid && !isCurrentNRValid) {
            this.mIsNRValid = true;
        }
        Elog.v(TAG, "mIsNRValid:" + this.mIsNRValid);
        return values;
    }

    private long getValFromBoxCdma() {
        long value = 0;
        Iterator<BandModeMap> it = this.mCdmaModeArray.iterator();
        while (it.hasNext()) {
            BandModeMap m = it.next();
            if (m.mChkBox.isChecked()) {
                value |= 1 << m.mBit;
            }
            if (!this.mIsCdmaValid) {
                this.mIsCdmaValid = true;
            }
        }
        if (value == 0 && this.mCurrentCdmaValues != 0) {
            this.mIsCdmaValid = false;
            Elog.v(TAG, "mIsCdmaValid:" + this.mIsCdmaValid);
        }
        return value;
    }

    private void setCurrentModeGsm(long[] values) {
        Iterator<BandModeMap> it = this.mGsmModeArray.iterator();
        while (it.hasNext()) {
            BandModeMap m = it.next();
            if ((values[m.mIndex] & (1 << m.mBit)) == 0) {
                m.mChkBox.setChecked(false);
            } else {
                m.mChkBox.setChecked(true);
            }
        }
    }

    private void setSupportedModeGsm(long[] values) {
        long j;
        long[] jArr = values;
        String[] labels = getResources().getStringArray(R.array.band_mode_gsm);
        int i = 0;
        while (true) {
            j = 0;
            if (i >= labels.length) {
                break;
            }
            int bit = BandModeContent.GSM_BAND_BIT[i];
            if (((1 << bit) & jArr[0]) != 0) {
                this.mGsmModeArray.add(new BandModeMap(addCheckboxToTable("GSM", R.id.TableLayout_GSM, labels[i]), 0, bit));
            }
            i++;
        }
        int modemType = ModemCategory.getModemType();
        String ratName = modemType == 2 ? "TDSCDMA" : "WCDMA";
        String[] labels2 = getResources().getStringArray(modemType == 2 ? R.array.band_mode_tdscdma : R.array.band_mode_wcdma);
        for (int bit2 = 0; bit2 < labels2.length; bit2++) {
            if ((jArr[1] & (1 << bit2)) != 0) {
                this.mGsmModeArray.add(new BandModeMap(addCheckboxToTable(ratName, R.id.TableLayout_UTMS, labels2[bit2]), 1, bit2));
            }
        }
        int len = jArr.length;
        int index = 2;
        while (index < len) {
            String ratName2 = index == 3 ? "LTE_TDD" : "LTE_FDD";
            int bit3 = 0;
            while (bit3 < 32) {
                if ((jArr[index] & (1 << bit3)) != j) {
                    this.mGsmModeArray.add(new BandModeMap(addCheckboxToTable(ratName2, R.id.TableLayout_LTE, "Band " + (((index - 2) * 32) + bit3 + 1)), index, bit3));
                }
                bit3++;
                j = 0;
            }
            index++;
            j = 0;
        }
    }

    private void setCurrentModeNR(long[] values) {
        Iterator<BandModeMap> it = this.mNRModeArray.iterator();
        while (it.hasNext()) {
            BandModeMap m = it.next();
            if ((values[m.mIndex] & (1 << m.mBit)) == 0) {
                m.mChkBox.setChecked(false);
            } else {
                m.mChkBox.setChecked(true);
            }
        }
    }

    private void setSupportedModeNR(long[] values) {
        int len = values.length;
        for (int index = 0; index < len; index++) {
            for (int bit = 0; bit < 32; bit++) {
                if ((values[index] & (1 << bit)) != 0) {
                    this.mNRModeArray.add(new BandModeMap(addCheckboxToTable("NR", R.id.TableLayout_NR, "Band " + ((index * 32) + bit + 1)), index, bit));
                }
            }
        }
    }

    private void setCurrentModeCdma(long value) {
        Iterator<BandModeMap> it = this.mCdmaModeArray.iterator();
        while (it.hasNext()) {
            BandModeMap m = it.next();
            if (((1 << m.mBit) & value) == 0) {
                m.mChkBox.setChecked(false);
            } else {
                m.mChkBox.setChecked(true);
            }
        }
    }

    private void setSupportedModeCdma(long value) {
        String[] labels = getResources().getStringArray(R.array.band_mode_cdma);
        for (int bit = 0; bit < labels.length; bit++) {
            if (((1 << bit) & value) != 0) {
                this.mCdmaModeArray.add(new BandModeMap(addCheckboxToTable("CDMA", R.id.TableLayout_CDMA, labels[bit]), 0, bit));
            }
        }
    }

    public void onClick(View arg0) {
        if (arg0.getId() == this.mBtnSet.getId()) {
            this.mSetCdmaValues = getValFromBoxCdma();
            this.mSetNRValues = getValFromBoxNR();
            long[] valFromBoxGsm = getValFromBoxGsm(true);
            this.mSetGsmValues = valFromBoxGsm;
            if (!this.mIsLteValid || !this.mIsCdmaValid || !this.mIsNRValid) {
                Elog.d(TAG, "!mIsLteValid || !mIsCdmaValid || !mIsNRValid");
                EmUtils.showToast("the band settings is forbid");
                return;
            }
            setBandModeGsm(valFromBoxGsm);
        } else if (arg0.getId() == this.mBtnReset.getId()) {
            showDialog(2);
        }
    }

    public void onDestroy() {
        Elog.v(TAG, "onDestroy");
        this.mIsThisAlive = false;
        this.mRadioStateManager.unregisterRadioStateChanged();
        super.onDestroy();
    }

    private void queryCurrentModeCdma() {
        String[] cmd_s = ModemCategory.getCdmaCmdArr(new String[]{BandModeContent.QUERY_CURRENT_COMMAND_CDMA, BandModeContent.SAME_COMMAND_CDMA, "DESTRILD:C2K"});
        Elog.v(TAG, "queryCurrentModeCdma: ");
        sendATCommandCdma(cmd_s, 102);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0089  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showBandModeCdma(android.os.AsyncResult r18, int r19) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            java.lang.Object r0 = r2.result
            r3 = r0
            java.lang.String[] r3 = (java.lang.String[]) r3
            int r4 = r3.length
            r6 = 0
        L_0x000b:
            if (r6 >= r4) goto L_0x0097
            r7 = r3[r6]
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r8 = "cdma --.>"
            r0.append(r8)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            java.lang.String r8 = "BandSelect"
            com.mediatek.engineermode.Elog.v(r8, r0)
            java.lang.String r0 = "+ECBANDCFG:"
            int r0 = r0.length()
            java.lang.String r8 = r7.substring(r0)
            r0 = 102(0x66, float:1.43E-43)
            r9 = r19
            if (r9 != r0) goto L_0x0090
            java.lang.String r0 = ","
            java.lang.String[] r10 = r8.split(r0)
            r0 = 2
            long[] r11 = new long[r0]
            r12 = 0
        L_0x003f:
            r13 = 0
            if (r12 >= r0) goto L_0x006a
            r15 = r10[r12]     // Catch:{ Exception -> 0x0063 }
            if (r15 == 0) goto L_0x005b
            r15 = r10[r12]     // Catch:{ Exception -> 0x0063 }
            java.lang.String r15 = r15.substring(r0)     // Catch:{ Exception -> 0x0063 }
            r0 = 16
            int r0 = java.lang.Integer.parseInt(r15, r0)     // Catch:{ Exception -> 0x0063 }
            r16 = r6
            long r5 = (long) r0
            r11[r12] = r5     // Catch:{ Exception -> 0x0059 }
            goto L_0x005d
        L_0x0059:
            r0 = move-exception
            goto L_0x0066
        L_0x005b:
            r16 = r6
        L_0x005d:
            int r12 = r12 + 1
            r6 = r16
            r0 = 2
            goto L_0x003f
        L_0x0063:
            r0 = move-exception
            r16 = r6
        L_0x0066:
            r5 = 0
            r11[r5] = r13
            goto L_0x006e
        L_0x006a:
            r16 = r6
            r5 = 0
        L_0x006e:
            r13 = r11[r5]
            r1.setSupportedModeCdma(r13)
            r0 = 1
            r5 = r11[r0]
            r1.setCurrentModeCdma(r5)
            r5 = r11[r0]
            r1.mCurrentCdmaValues = r5
            r5 = r11[r0]
            r12 = 0
            int r5 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r5 != 0) goto L_0x0089
            r5 = 0
            r12 = r11[r5]
            goto L_0x008c
        L_0x0089:
            r5 = 0
            r12 = r11[r0]
        L_0x008c:
            r1.saveDefaultValueIfNeedCdma(r12)
            goto L_0x0093
        L_0x0090:
            r16 = r6
            r5 = 0
        L_0x0093:
            int r6 = r16 + 1
            goto L_0x000b
        L_0x0097:
            r9 = r19
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.bandselect.BandSelect.showBandModeCdma(android.os.AsyncResult, int):void");
    }

    private void sendATCommandCdma(String[] atCommand, int msg) {
        Elog.v(TAG, "c2k AT String:" + atCommand[0] + ",atCommand.size = " + atCommand.length);
        EmUtils.invokeOemRilRequestStringsEm(true, atCommand, this.mResponseHander.obtainMessage(msg));
    }

    /* access modifiers changed from: private */
    public void setBandModeCdma(long value) {
        Elog.v(TAG, "setCdmaBandMode: ");
        if (this.mIsCdmaValid || this.mIsLteValid) {
            sendATCommandCdma(ModemCategory.getCdmaCmdArr(new String[]{BandModeContent.SET_COMMAND_CDMA + value, "", "DESTRILD:C2K"}), 111);
        }
    }

    /* access modifiers changed from: private */
    public long[] getDefaultValueGsm() {
        SharedPreferences pref = getSharedPreferences(PREF_FILE + mSimType, 0);
        long[] values = new long[10];
        long[] values_temp = new long[10];
        Elog.v(TAG, "getDefaultValueGsm: ");
        for (int i = 0; i < 10; i++) {
            String[] strArr = PREF_KEYS;
            values[i] = pref.getLong(strArr[i], 0);
            values_temp[i] = pref.getLong(strArr[i], 0);
            Elog.v(TAG, "values[" + i + "] = " + values[i]);
        }
        for (int i2 = 4; i2 <= 9; i2++) {
            values_temp[2] = values_temp[2] | values_temp[i2];
        }
        if (values[0] == 0) {
            values[0] = 255;
        }
        if (values[1] == 0) {
            values[1] = 65535;
        }
        if (values_temp[2] == 0 && values_temp[3] == 0) {
            values[2] = 4294967295L;
            values[3] = 4294967295L;
            Elog.v(TAG, "getDefaultValue,lte not to null");
        }
        setCurrentModeGsm(values);
        return values;
    }

    private void saveDefaultValueIfNeedGsm(long[] values) {
        SharedPreferences pref = getSharedPreferences(PREF_FILE + mSimType, 0);
        SharedPreferences.Editor editor = pref.edit();
        for (int i = 0; i < 10; i++) {
            String[] strArr = PREF_KEYS;
            if (!pref.contains(strArr[i])) {
                editor.putLong(strArr[i], values[i]);
                Elog.v(TAG, "save gsm default values[" + i + "] = " + values[i]);
            }
        }
        editor.commit();
    }

    /* access modifiers changed from: private */
    public long getDefaultValueCdma() {
        long value = getSharedPreferences(PREF_FILE + mSimType, 0).getLong(PREF_KEYS[10], 0);
        Elog.v(TAG, "getDefaultValueCdma: " + value);
        setCurrentModeCdma(value);
        return value;
    }

    private void saveDefaultValueIfNeedCdma(long value) {
        SharedPreferences pref = getSharedPreferences(PREF_FILE + mSimType, 0);
        SharedPreferences.Editor editor = pref.edit();
        String[] strArr = PREF_KEYS;
        if (!pref.contains(strArr[10])) {
            editor.putLong(strArr[10], value);
            Elog.v(TAG, "save cdam default value: " + value);
        }
        editor.commit();
    }

    /* access modifiers changed from: private */
    public long[] getDefaultValueNR() {
        SharedPreferences pref = getSharedPreferences(PREF_FILE + mSimType, 0);
        long[] values = new long[this.mNRIndexBandMax];
        for (int i = 0; i < this.mNRIndexBandMax; i++) {
            values[i] = pref.getLong(PREF_KEYS[11] + i, 0);
        }
        setCurrentModeNR(values);
        return values;
    }

    private void saveDefaultValueNR(long[] values) {
        SharedPreferences pref = getSharedPreferences(PREF_FILE + mSimType, 0);
        SharedPreferences.Editor editor = pref.edit();
        int len = values.length;
        for (int i = 0; i < len; i++) {
            StringBuilder sb = new StringBuilder();
            String[] strArr = PREF_KEYS;
            sb.append(strArr[11]);
            sb.append(i);
            if (!pref.contains(sb.toString())) {
                editor.putLong(strArr[11] + i, values[i]);
                Elog.v(TAG, "save NR default values[" + i + "] = " + values[i]);
            }
        }
        editor.commit();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int dialogId) {
        if (2 == dialogId) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.reset_title);
            builder.setMessage(R.string.reset_message);
            builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    BandSelect bandSelect = BandSelect.this;
                    long[] unused = bandSelect.mSetGsmValues = bandSelect.getDefaultValueGsm();
                    BandSelect bandSelect2 = BandSelect.this;
                    long unused2 = bandSelect2.mSetCdmaValues = bandSelect2.getDefaultValueCdma();
                    BandSelect bandSelect3 = BandSelect.this;
                    long[] unused3 = bandSelect3.mSetNRValues = bandSelect3.getDefaultValueNR();
                    BandSelect bandSelect4 = BandSelect.this;
                    bandSelect4.setBandModeGsm(bandSelect4.mSetGsmValues);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
            builder.create().show();
        } else if (1 == dialogId) {
            Elog.d(TAG, "band set failed");
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(R.string.wait_finish);
            builder2.setMessage("please wait for reboot airplane finish and try again");
            builder2.setPositiveButton(R.string.set_fail_text, (DialogInterface.OnClickListener) null);
            builder2.create().show();
        } else if (1001 == dialogId) {
            return this.mRadioStateManager.getRebootModemDialog();
        } else {
            AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
            builder3.setTitle(R.string.set_fail_title);
            builder3.setMessage(R.string.set_fail_message);
            builder3.setPositiveButton(R.string.set_fail_text, (DialogInterface.OnClickListener) null);
            builder3.create().show();
        }
        return super.onCreateDialog(dialogId);
    }

    private static class BandModeMap {
        public int mBit;
        public CheckBox mChkBox;
        public int mIndex;

        BandModeMap(CheckBox chkbox, int index, int bit) {
            this.mChkBox = chkbox;
            this.mIndex = index;
            this.mBit = bit;
        }
    }
}
