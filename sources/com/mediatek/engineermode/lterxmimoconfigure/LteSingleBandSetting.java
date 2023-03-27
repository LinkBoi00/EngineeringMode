package com.mediatek.engineermode.lterxmimoconfigure;

import android.app.Activity;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class LteSingleBandSetting extends Activity implements View.OnClickListener {
    public static final String CMD_SB_4RX_SET = "\"sb_4rx_set\"";
    public static final String CMD_SB_MIMO_SET = "\"sb_mimo_set\"";
    private static final int LTE_BAND_NUM = 256;
    private static final int LTE_BAND_QUERY_MODE = 0;
    public static final int LTE_BAND_SETTING_MODE_4RX = 1;
    public static final int LTE_BAND_SETTING_MODE_4X4MIMO = 0;
    /* access modifiers changed from: private */
    public static int LteBandSettingMode = 0;
    public static int LteBandSettingModeHistory = 0;
    private static final String TAG = "LteRx/SingleBandSetting";
    public static String[] mSbMimoSetString = new String[2];
    private Button mBtnSet;
    private final ArrayList<BandModeMap> mModeArray = new ArrayList<>();
    private Handler mResponseHander = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AsyncResult asyncResult = (AsyncResult) msg.obj;
                    if (asyncResult.exception == null) {
                        String[] result = (String[]) asyncResult.result;
                        if (result == null || result.length < 0) {
                            EmUtils.showToast("Query lte band mode error1");
                            Elog.e(LteSingleBandSetting.TAG, "Query lte band mode error1");
                            return;
                        }
                        Elog.d(LteSingleBandSetting.TAG, "Query lte band mode succeed");
                        LteSingleBandSetting.this.updateCurrentMode(result[0]);
                        return;
                    }
                    if (LteSingleBandSetting.LteBandSettingMode == 0) {
                        LteSingleBandSetting.this.updateCurrentMode("0,1,2,3,4,5,6,7,8");
                    } else {
                        LteSingleBandSetting.this.updateCurrentMode("0,8,7,6,5,4,3,2,1");
                    }
                    EmUtils.showToast("Query lte band mode error2");
                    Elog.e(LteSingleBandSetting.TAG, "Query lte band mode error2");
                    return;
                default:
                    return;
            }
        }
    };
    private long[][] mSbMimoSet = ((long[][]) Array.newInstance(long.class, new int[]{2, 8}));

    private ArrayList<CheckBox> addCheckboxToTable(int tableResId) {
        TableLayout table = (TableLayout) findViewById(tableResId);
        ArrayList<CheckBox> ret = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            TableRow row = new TableRow(this);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText("band " + (i + 1));
            row.addView(checkBox);
            table.addView(row);
            ret.add(checkBox);
        }
        return ret;
    }

    private void initLteArray() {
        ArrayList<CheckBox> checkBox = addCheckboxToTable(R.id.TableLayout_LTE_Band_Configure);
        for (int i = 0; i < checkBox.size(); i++) {
            this.mModeArray.add(new BandModeMap(checkBox.get(i), i));
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lte_rx_single_band_setting);
        this.mBtnSet = (Button) findViewById(R.id.BandSel_Btn_Set);
        int intExtra = getIntent().getIntExtra("band_setting_type", 0);
        LteBandSettingMode = intExtra;
        LteBandSettingModeHistory = (1 << intExtra) | LteBandSettingModeHistory;
        Elog.d(TAG, "LteBandSettingMode = " + LteBandSettingMode);
        Elog.d(TAG, "LteBandSettingModeHistory = " + LteBandSettingModeHistory);
        initLteArray();
        this.mBtnSet.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.v(TAG, "onResume");
        queryCurrentMode();
    }

    private void queryCurrentMode() {
        String queryCmd;
        if (LteBandSettingMode == 0) {
            queryCmd = CMD_SB_MIMO_SET;
        } else {
            queryCmd = CMD_SB_4RX_SET;
        }
        sendATCommand(new String[]{"AT+EGMC=0," + queryCmd, "+EGMC:"}, 0);
    }

    private void sendATCommand(String[] atCommand, int msg) {
        Elog.d(TAG, "atCommand = " + atCommand[0]);
        EmUtils.invokeOemRilRequestStringsEm(atCommand, this.mResponseHander.obtainMessage(msg));
    }

    private void getValFromBox() {
        int count = 0;
        Elog.d(TAG, "getValFromBox:");
        Iterator<BandModeMap> it = this.mModeArray.iterator();
        while (it.hasNext()) {
            BandModeMap next = it.next();
            long[][] jArr = this.mSbMimoSet;
            int i = LteBandSettingMode;
            jArr[i][0] = 0;
            mSbMimoSetString[i] = "";
        }
        Iterator<BandModeMap> it2 = this.mModeArray.iterator();
        while (it2.hasNext()) {
            BandModeMap m = it2.next();
            if (m.mChkBox.isChecked()) {
                long[] jArr2 = this.mSbMimoSet[LteBandSettingMode];
                jArr2[count] = jArr2[count] | (1 << m.mBit);
            }
            if (m.mBit == 31) {
                StringBuilder sb = new StringBuilder();
                String[] strArr = mSbMimoSetString;
                int i2 = LteBandSettingMode;
                sb.append(strArr[i2]);
                sb.append(",");
                sb.append(this.mSbMimoSet[LteBandSettingMode][count]);
                strArr[i2] = sb.toString();
                count++;
            }
        }
        Elog.d(TAG, "mSbMimoSetString: " + mSbMimoSetString[LteBandSettingMode]);
    }

    /* access modifiers changed from: private */
    public void updateCurrentMode(String result) {
        int count = 1;
        try {
            String[] info = result.split(",");
            Elog.v(TAG, "LteBandSettingMode = " + LteBandSettingMode);
            mSbMimoSetString[LteBandSettingMode] = "";
            for (int i = 0; i < info.length - 1; i++) {
                this.mSbMimoSet[LteBandSettingMode][i] = Long.valueOf(info[i + 1]).longValue();
                StringBuilder sb = new StringBuilder();
                String[] strArr = mSbMimoSetString;
                int i2 = LteBandSettingMode;
                sb.append(strArr[i2]);
                sb.append(",");
                sb.append(this.mSbMimoSet[LteBandSettingMode][i]);
                strArr[i2] = sb.toString();
            }
            Elog.v(TAG, "mSbMimoSetString = " + mSbMimoSetString[LteBandSettingMode]);
            Iterator<BandModeMap> it = this.mModeArray.iterator();
            while (it.hasNext()) {
                BandModeMap m = it.next();
                if ((Long.valueOf(info[count]).longValue() & (1 << m.mBit)) == 0) {
                    m.mChkBox.setChecked(false);
                } else {
                    m.mChkBox.setChecked(true);
                }
                if (m.mBit == 31) {
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View arg0) {
        Elog.d(TAG, "Band setting button click");
        getValFromBox();
    }

    public void onDestroy() {
        Elog.v(TAG, "onDestroy");
        super.onDestroy();
    }

    private static class BandModeMap {
        public int mBit;
        public CheckBox mChkBox;

        BandModeMap(CheckBox chkbox, int bit) {
            this.mChkBox = chkbox;
            this.mBit = bit % 32;
        }
    }
}
