package com.mediatek.engineermode.antenna;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import java.util.Arrays;

public class AntennaActivity extends Activity {
    private static final int ANT_INDEX_4G = 0;
    private static final int ANT_INDEX_NR = 1;
    private static final int CELL_2RX_LENGTH = 2;
    private static final int CELL_4RX_LENGTH = 4;
    private static final String CMD_INIT_EGMC_4G = "AT+EGMC=1,\"rx_path\",1,0,3,15,3,15";
    private static final String CMD_QUERY_EGMC_4G = "AT+EGMC=0,\"rx_path\"";
    private static final String CMD_QUERY_EGMC_NR = "AT+EGMC=0,\"nr_rx_path\"";
    private static final String CMD_SET_EGMC_4G = "AT+EGMC=1,\"rx_path\"";
    private static final String CMD_SET_EGMC_NR = "AT+EGMC=1,\"nr_rx_path\"";
    private static final int DIALOG_EGMC_SET_FAIL = 0;
    private static final int MODE_2G_COUNT = 4;
    private static final int MODE_EPCM_VALID = 255;
    private static final int MODE_INDEX_BASE_2G = 20;
    private static final int MODE_INDEX_BASE_3G = 10;
    private static final int MSG_CONFIRM_ANTENNA_EGMC_NR = 10;
    private static final int MSG_INIT_ANTENNA_EGMC_4G = 9;
    private static final int MSG_QUERY_ANTENNA_EGMC_4G = 5;
    private static final int MSG_QUERY_ANTENNA_EGMC_NR = 6;
    private static final int MSG_QUERY_ANTENNA_MODE = 1;
    private static final int MSG_QUERY_ANTENNA_MODE_C2K = 4;
    private static final int MSG_SET_ANTENNA_EGMC_4G = 7;
    private static final int MSG_SET_ANTENNA_EGMC_NR = 8;
    private static final int MSG_SET_ANTENNA_MODE = 2;
    private static final String TAG = "AntennaActivity";
    private boolean G95Valid = false;
    private RadioButton SfpDisable;
    private RadioButton SfpEnable;
    /* access modifiers changed from: private */
    public AntennaEgmcInfo antEgmc4gObj;
    private RadioGroup.OnCheckedChangeListener antEgmcModeChangeListener;
    /* access modifiers changed from: private */
    public AntennaEgmcInfo antEgmcNrObj;
    private CompoundButton.OnCheckedChangeListener antEgmcStatusChangeListener;
    /* access modifiers changed from: private */
    public LinearLayout antennaLayout2G;
    private LinearLayout antennaLayout4G;
    private LinearLayout antennaLayoutEgmc;
    private RadioGroup egmcForceRxMode;
    /* access modifiers changed from: private */
    public int emgcView = 0;
    /* access modifiers changed from: private */
    public LinearLayout mAntennaLayout;
    private RadioButton mAntennaSim1;
    private RadioButton mAntennaSim2;
    private final Handler mCommandHander = new Handler() {
        public void handleMessage(Message msg) {
            AntennaEgmcInfo antennaEgmcInfo;
            switch (msg.what) {
                case 1:
                    AsyncResult asyncResult = (AsyncResult) msg.obj;
                    if (asyncResult == null || asyncResult.exception != null || asyncResult.result == null) {
                        EmUtils.showToast("Query 3G/4G/NR antenna currect mode failed.", 0);
                        Elog.e(AntennaActivity.TAG, "Query 3G/4G/NR antenna currect mode failed.");
                        return;
                    }
                    String[] result = (String[]) asyncResult.result;
                    Elog.d(AntennaActivity.TAG, "Query Ant Mode return: " + Arrays.toString(result));
                    AntennaActivity.this.antennaLayout2G.setVisibility(8);
                    for (String access$1700 : result) {
                        if (AntennaActivity.this.isMode2G(AntennaActivity.this.parseCurrentMode(access$1700))) {
                            AntennaActivity.this.antennaLayout2G.setVisibility(0);
                        }
                    }
                    return;
                case 2:
                    if (((AsyncResult) msg.obj).exception == null) {
                        EmUtils.showToast("Set successful.", 0);
                        Elog.d(AntennaActivity.TAG, "set antenna mode succeed.");
                        return;
                    }
                    EmUtils.showToast("Set failed.", 0);
                    Elog.e(AntennaActivity.TAG, "set antenna mode failed.");
                    return;
                case 4:
                    AsyncResult asyncResult2 = (AsyncResult) msg.obj;
                    if (asyncResult2 == null || asyncResult2.exception != null || asyncResult2.result == null) {
                        EmUtils.showToast("Query cdma antenna currect mode failed.", 0);
                        Elog.e(AntennaActivity.TAG, "Query cdma antenna currect mode failed.");
                        return;
                    }
                    String[] result2 = (String[]) asyncResult2.result;
                    Elog.d(AntennaActivity.TAG, "Query Ant Mode C2K return: " + Arrays.toString(result2));
                    for (String access$1900 : result2) {
                        int unused = AntennaActivity.this.parseCurrentCdmaMode(access$1900);
                    }
                    return;
                case 5:
                case 6:
                    AsyncResult asyncResult3 = (AsyncResult) msg.obj;
                    if (asyncResult3 == null || asyncResult3.exception != null || asyncResult3.result == null) {
                        if (msg.what - 5 == AntennaActivity.this.emgcView) {
                            AntennaActivity antennaActivity = AntennaActivity.this;
                            if (msg.what == 5) {
                                antennaEgmcInfo = AntennaActivity.this.antEgmc4gObj;
                            } else {
                                antennaEgmcInfo = AntennaActivity.this.antEgmcNrObj;
                            }
                            antennaActivity.resetAntEgmcView(antennaEgmcInfo);
                        }
                        EmUtils.showToast("Query EGMC currect mode failed.", 0);
                        Elog.e(AntennaActivity.TAG, "Query EGMC ant" + AntennaActivity.this.emgcView + " currect mode failed.");
                        return;
                    }
                    String[] result3 = (String[]) asyncResult3.result;
                    Elog.d(AntennaActivity.TAG, "Query Ant Mode EGMC ant " + AntennaActivity.this.emgcView + " return: " + Arrays.toString(result3) + " for : " + msg.what);
                    for (String access$2000 : result3) {
                        AntennaActivity.this.parseCurEgmcMode(msg.what - 5, access$2000);
                    }
                    return;
                case 7:
                case 8:
                    AsyncResult asyncResult4 = (AsyncResult) msg.obj;
                    if (asyncResult4.exception == null) {
                        EmUtils.showToast("Set EGMC ant " + AntennaActivity.this.emgcView + " successful.", 0);
                        Elog.d(AntennaActivity.TAG, "set EGMC ant" + AntennaActivity.this.emgcView + " antenna mode succeed.");
                        return;
                    }
                    if (msg.what == 8) {
                        AntennaActivity.this.confirmEgmcAntNR();
                    } else {
                        EmUtils.showToast("Set EGMC ant " + AntennaActivity.this.emgcView + " failed " + asyncResult4.exception, 0);
                    }
                    Elog.e(AntennaActivity.TAG, "set EGMC ant" + AntennaActivity.this.emgcView + " antenna mode failed: " + asyncResult4.exception);
                    return;
                case 9:
                    if (((AsyncResult) msg.obj).exception == null && AntennaActivity.this.emgcView == 0) {
                        AntennaActivity.this.setEgmcAntForUser(0);
                        Elog.d(AntennaActivity.TAG, "Init EGMC antenna mode for ant" + AntennaActivity.this.emgcView + " succeed.");
                        return;
                    }
                    EmUtils.showToast("Init EGMC ant" + AntennaActivity.this.emgcView + " failed.", 0);
                    Elog.e(AntennaActivity.TAG, "Init EGMC antenna mode for ant" + AntennaActivity.this.emgcView + " failed.");
                    return;
                case 10:
                    AsyncResult asyncResult5 = (AsyncResult) msg.obj;
                    boolean ret = false;
                    if (!(asyncResult5 == null || asyncResult5.exception != null || asyncResult5.result == null)) {
                        String[] result4 = (String[]) asyncResult5.result;
                        Elog.d(AntennaActivity.TAG, "Query Ant Mode EGMC ant " + AntennaActivity.this.emgcView + " return: " + Arrays.toString(result4) + " for : " + msg.what);
                        for (String access$2100 : result4) {
                            if (AntennaActivity.this.confirmCurrentMode(1, access$2100)) {
                                ret = true;
                            }
                        }
                    }
                    if (ret) {
                        EmUtils.showToast("Set EGMC ant " + AntennaActivity.this.emgcView + " succeed!", 0);
                        Elog.e(AntennaActivity.TAG, "Confirm EGMC ant" + AntennaActivity.this.emgcView + " succeed.");
                        return;
                    }
                    EmUtils.showToast("Set EGMC ant " + AntennaActivity.this.emgcView + " failed!", 0);
                    Elog.e(AntennaActivity.TAG, "Confirm EGMC ant" + AntennaActivity.this.emgcView + " currect mode failed.");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public int mCurrent2GPos = 0;
    /* access modifiers changed from: private */
    public int mCurrent3GPos = 0;
    /* access modifiers changed from: private */
    public int mCurrentPos = 0;
    /* access modifiers changed from: private */
    public int mCurrentPosCdma = 0;
    private final AdapterView.OnItemSelectedListener mItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
            if (arg0 == AntennaActivity.this.mSpinner4G) {
                if (AntennaActivity.this.mCurrentPos != arg0.getSelectedItemPosition()) {
                    int unused = AntennaActivity.this.mCurrentPos = arg0.getSelectedItemPosition();
                    AntennaActivity.this.setMode(pos);
                }
            } else if (arg0 == AntennaActivity.this.mSpinner3G) {
                if (AntennaActivity.this.mCurrent3GPos != arg0.getSelectedItemPosition() && pos != 0) {
                    int unused2 = AntennaActivity.this.mCurrent3GPos = arg0.getSelectedItemPosition();
                    AntennaActivity.this.setMode((pos + 10) - 1);
                }
            } else if (arg0 == AntennaActivity.this.mSpinnerC2kMode) {
                if (AntennaActivity.this.mCurrentPosCdma != arg0.getSelectedItemPosition()) {
                    int unused3 = AntennaActivity.this.mCurrentPosCdma = arg0.getSelectedItemPosition();
                    AntennaActivity.this.setCdmaMode(pos);
                }
            } else if (arg0 == AntennaActivity.this.mSpinner2G) {
                if (AntennaActivity.this.mCurrent2GPos != arg0.getSelectedItemPosition() && pos != 0) {
                    int unused4 = AntennaActivity.this.mCurrent2GPos = arg0.getSelectedItemPosition();
                    AntennaActivity.this.setMode((pos + 20) - 1);
                }
            } else if (arg0 == AntennaActivity.this.mSpAntSelect && AntennaActivity.this.emgcView != arg0.getSelectedItemPosition()) {
                Elog.d(AntennaActivity.TAG, "onItemSelected : " + arg0.getSelectedItemPosition());
                int unused5 = AntennaActivity.this.emgcView = arg0.getSelectedItemPosition();
                if (AntennaActivity.this.emgcView == 0) {
                    AntennaActivity antennaActivity = AntennaActivity.this;
                    antennaActivity.updateAntEgmcView(antennaActivity.antEgmc4gObj);
                    AntennaActivity.this.queryCurrentMode();
                    AntennaActivity.this.antEgmc4gObj.show();
                    AntennaActivity.this.antEgmcNrObj.hide();
                    AntennaActivity.this.mAntennaLayout.setVisibility(8);
                } else if (AntennaActivity.this.emgcView == 1) {
                    AntennaActivity antennaActivity2 = AntennaActivity.this;
                    antennaActivity2.updateAntEgmcView(antennaActivity2.antEgmcNrObj);
                    AntennaActivity.this.queryEgmcAntNR();
                    AntennaActivity.this.antEgmc4gObj.hide();
                    AntennaActivity.this.antEgmcNrObj.show();
                    AntennaActivity.this.mAntennaLayout.setVisibility(0);
                }
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private View.OnClickListener mOnClickListener;
    /* access modifiers changed from: private */
    public Spinner mSpAntSelect = null;
    /* access modifiers changed from: private */
    public Spinner mSpinner2G = null;
    /* access modifiers changed from: private */
    public Spinner mSpinner3G = null;
    /* access modifiers changed from: private */
    public Spinner mSpinner4G = null;
    /* access modifiers changed from: private */
    public Spinner mSpinnerC2kMode = null;
    private Toast mToast = null;
    private CheckBox[] pCell2Rx = new CheckBox[2];
    private CheckBox[] pCell4Rx = new CheckBox[4];
    private Button queryEgmcBtn;
    private RadioButton rxDisable;
    private RadioButton rxEnable;
    private CheckBox[] sCell2Rx = new CheckBox[2];
    private CheckBox[] sCell4Rx = new CheckBox[4];
    private RadioGroup sccFollowPccMode;
    private Button setEgmcBtn;

    /* access modifiers changed from: private */
    public boolean confirmCurrentMode(int ant_index, String data) {
        AntennaEgmcInfo antEgmcObj;
        if (ant_index == 0) {
            antEgmcObj = this.antEgmc4gObj;
        } else {
            antEgmcObj = this.antEgmcNrObj;
        }
        String[] origArray = data.substring("+EGMC:".length()).trim().replace(" ", "").split(",");
        if (origArray.length < 4 || origArray.length > 7) {
            return false;
        }
        int[] iArr = new int[6];
        int start = origArray.length % 2 == 0 ? 0 : 1;
        Elog.d(TAG, "confirmCurrentMode: " + antEgmcObj.getPCell2Rx() + "," + antEgmcObj.getPCell4Rx() + "," + antEgmcObj.getSCell2Rx() + "," + antEgmcObj.getSCell4Rx());
        String str = origArray[start + 0];
        if (str.equals(antEgmcObj.getPCell2Rx() + "")) {
            String str2 = origArray[start + 1];
            if (str2.equals(antEgmcObj.getPCell4Rx() + "")) {
                String str3 = origArray[start + 2];
                if (str3.equals(antEgmcObj.getSCell2Rx() + "")) {
                    String str4 = origArray[start + 3];
                    if (str4.equals(antEgmcObj.getSCell4Rx() + "")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public int parseCurrentMode(String data) {
        int mode = -1;
        Elog.i(TAG, "parseCurrentMode data= " + data);
        try {
            mode = Integer.valueOf(data.substring("+ERXPATH:".length()).trim()).intValue();
        } catch (Exception e) {
            Elog.e(TAG, "Wrong current mode format: " + data);
        }
        if (mode < 0 || ((mode >= 24 || ((mode >= this.mSpinner4G.getCount() && mode < 10) || (mode >= this.mSpinner3G.getCount() + 10 && mode < 20))) && mode != 255)) {
            EmUtils.showToast("Modem returned invalid mode: " + data, 0);
            return -1;
        }
        if (mode == 255) {
            this.antennaLayout4G.setVisibility(8);
            if (this.emgcView == 0) {
                this.antEgmc4gObj.show();
                this.antEgmcNrObj.hide();
                this.antennaLayoutEgmc.setVisibility(0);
                this.mAntennaLayout.setVisibility(8);
            }
            Elog.d(TAG, "parseCurrentMode is: " + mode + " index is: " + this.emgcView);
            queryEgmcAnt4G();
        } else if (mode >= 20) {
            this.mCurrent2GPos = (mode - 20) + 1;
            Elog.d(TAG, "parseCurrent2GMode is: " + this.mCurrent2GPos);
            this.mSpinner2G.setSelection(this.mCurrent2GPos);
        } else if (mode >= 10) {
            this.mCurrent3GPos = (mode - 10) + 1;
            Elog.d(TAG, "parseCurrent3GMode is: " + this.mCurrent3GPos);
            this.mSpinner3G.setSelection(this.mCurrent3GPos);
        } else {
            if (this.emgcView == 0) {
                this.antennaLayout4G.setVisibility(0);
                this.antennaLayoutEgmc.setVisibility(8);
                this.antEgmc4gObj.hide();
                this.antEgmcNrObj.hide();
            }
            Elog.d(TAG, "parseCurrentLteMode is: " + mode);
            this.mCurrentPos = mode;
            this.mSpinner4G.setSelection(mode);
            this.mSpinner4G.setEnabled(true);
        }
        return mode;
    }

    /* access modifiers changed from: private */
    public boolean isMode2G(int mode) {
        if (mode < 20 || mode >= (this.mSpinner2G.getCount() + 20) - 1) {
            return false;
        }
        return true;
    }

    private boolean isMode3G(int mode) {
        if (mode < 20 || mode >= this.mSpinner2G.getCount() + 20) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public int parseCurrentCdmaMode(String data) {
        int mode = -1;
        Elog.d(TAG, "parseCurrentCdmaMode data= " + data);
        try {
            mode = Integer.valueOf(data.substring("+ERXTESTMODE:".length()).trim()).intValue();
        } catch (Exception e) {
            Elog.e(TAG, "Wrong current mode format: " + data);
        }
        if (mode < 0 || mode >= this.mSpinnerC2kMode.getCount()) {
            EmUtils.showToast("Modem returned invalid mode: " + data, 0);
            return -1;
        }
        this.mCurrentPosCdma = mode;
        Elog.d(TAG, "parseCurrentCDMAMode is: " + this.mCurrentPosCdma);
        this.mSpinnerC2kMode.setSelection(mode);
        this.mSpinnerC2kMode.setEnabled(true);
        return mode;
    }

    /* access modifiers changed from: private */
    public void parseCurEgmcMode(int ant_index, String data) {
        AntennaEgmcInfo antEgmcObj;
        if (ant_index == 0) {
            antEgmcObj = this.antEgmc4gObj;
        } else {
            antEgmcObj = this.antEgmcNrObj;
        }
        Elog.d(TAG, "parseCurEgmcMode data= " + data);
        try {
            String[] origArray = data.substring("+EGMC:".length()).replace(" ", "").split(",");
            if (origArray.length < 4) {
                return;
            }
            if (origArray.length <= 7) {
                int[] rxDataArray = new int[6];
                int start = origArray.length % 2 == 0 ? 0 : 1;
                Elog.d(TAG, "start: " + start + ", origArray: " + Arrays.toString(origArray));
                if (origArray.length < 6) {
                    rxDataArray[0] = 0;
                    rxDataArray[1] = 0;
                    for (int i = start; i < origArray.length; i++) {
                        if (!origArray[i].equals("0")) {
                            rxDataArray[0] = 1;
                        }
                        rxDataArray[(rxDataArray.length - origArray.length) + i] = Integer.valueOf(origArray[i]).intValue();
                    }
                } else {
                    for (int i2 = start; i2 < origArray.length; i2++) {
                        rxDataArray[i2 - start] = Integer.valueOf(origArray[i2]).intValue();
                    }
                }
                Elog.d(TAG, "" + Arrays.toString(rxDataArray));
                if (antEgmcObj == null) {
                    antEgmcObj = new AntennaEgmcInfo(rxDataArray);
                } else {
                    antEgmcObj.updateAntennaEgmcInfo(rxDataArray);
                }
                updateAntEgmcView(antEgmcObj);
            }
        } catch (Exception e) {
            Elog.e(TAG, "Wrong current mode format: " + data + "\n" + e.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.antena_test);
        initAntLteNrView();
        initAnt3GView();
        initAntC2KView();
        initAnt2GView();
    }

    public void initAntEgmcView() {
        this.antEgmcModeChangeListener = new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AntennaEgmcInfo antEgmcObj;
                if (AntennaActivity.this.emgcView == 0) {
                    antEgmcObj = AntennaActivity.this.antEgmc4gObj;
                } else {
                    antEgmcObj = AntennaActivity.this.antEgmcNrObj;
                }
                if (antEgmcObj != null) {
                    switch (checkedId) {
                        case R.id.antenna_sim1:
                            antEgmcObj.setSim(0);
                            break;
                        case R.id.antenna_sim2:
                            antEgmcObj.setSim(1);
                            break;
                        case R.id.force_rx_disable:
                            antEgmcObj.setForceRx(false);
                            break;
                        case R.id.force_rx_enable:
                            antEgmcObj.setForceRx(true);
                            break;
                        case R.id.scc_follow_pcc_disable:
                            antEgmcObj.setCssFollowPcc(false);
                            break;
                        case R.id.scc_follow_pcc_enable:
                            antEgmcObj.setCssFollowPcc(true);
                            break;
                    }
                    if (AntennaActivity.this.emgcView == 0) {
                        AntennaEgmcInfo unused = AntennaActivity.this.antEgmc4gObj = antEgmcObj;
                    } else {
                        AntennaEgmcInfo unused2 = AntennaActivity.this.antEgmcNrObj = antEgmcObj;
                    }
                }
            }
        };
        this.antEgmcStatusChangeListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AntennaEgmcInfo antEgmcObj;
                if (AntennaActivity.this.emgcView == 0) {
                    antEgmcObj = AntennaActivity.this.antEgmc4gObj;
                } else {
                    antEgmcObj = AntennaActivity.this.antEgmcNrObj;
                }
                if (antEgmcObj == null) {
                    antEgmcObj = new AntennaEgmcInfo();
                }
                switch (buttonView.getId()) {
                    case R.id.pcell_2rx_rx1:
                        antEgmcObj.updatePCell2Rx(isChecked, 0);
                        break;
                    case R.id.pcell_2rx_rx2:
                        antEgmcObj.updatePCell2Rx(isChecked, 1);
                        break;
                    case R.id.pcell_4rx_rx1:
                        antEgmcObj.updatePCell4Rx(isChecked, 0);
                        break;
                    case R.id.pcell_4rx_rx2:
                        antEgmcObj.updatePCell4Rx(isChecked, 1);
                        break;
                    case R.id.pcell_4rx_rx3:
                        antEgmcObj.updatePCell4Rx(isChecked, 2);
                        break;
                    case R.id.pcell_4rx_rx4:
                        antEgmcObj.updatePCell4Rx(isChecked, 3);
                        break;
                    case R.id.scell_2rx_rx1:
                        antEgmcObj.updateSCell2Rx(isChecked, 0);
                        break;
                    case R.id.scell_2rx_rx2:
                        antEgmcObj.updateSCell2Rx(isChecked, 1);
                        break;
                    case R.id.scell_4rx_rx1:
                        antEgmcObj.updateSCell4Rx(isChecked, 0);
                        break;
                    case R.id.scell_4rx_rx2:
                        antEgmcObj.updateSCell4Rx(isChecked, 1);
                        break;
                    case R.id.scell_4rx_rx3:
                        antEgmcObj.updateSCell4Rx(isChecked, 2);
                        break;
                    case R.id.scell_4rx_rx4:
                        antEgmcObj.updateSCell4Rx(isChecked, 3);
                        break;
                }
                if (AntennaActivity.this.emgcView == 0) {
                    AntennaEgmcInfo unused = AntennaActivity.this.antEgmc4gObj = antEgmcObj;
                } else {
                    AntennaEgmcInfo unused2 = AntennaActivity.this.antEgmcNrObj = antEgmcObj;
                }
            }
        };
        this.mOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.antenna_query_4g:
                        if (AntennaActivity.this.emgcView == 0) {
                            AntennaActivity.this.queryEgmcAnt4G();
                            return;
                        } else {
                            AntennaActivity.this.queryEgmcAntNR();
                            return;
                        }
                    case R.id.antenna_set_4g:
                        if (AntennaActivity.this.emgcView == 0) {
                            AntennaActivity.this.setEgmcAnt4G();
                            return;
                        } else {
                            AntennaActivity.this.setEgmcAntNR();
                            return;
                        }
                    default:
                        Elog.d(AntennaActivity.TAG, "OnClickListener: " + v.getLabelFor());
                        return;
                }
            }
        };
        Button button = (Button) findViewById(R.id.antenna_query_4g);
        this.queryEgmcBtn = button;
        button.setOnClickListener(this.mOnClickListener);
        Button button2 = (Button) findViewById(R.id.antenna_set_4g);
        this.setEgmcBtn = button2;
        button2.setOnClickListener(this.mOnClickListener);
        this.egmcForceRxMode = (RadioGroup) findViewById(R.id.force_rx_mode);
        this.sccFollowPccMode = (RadioGroup) findViewById(R.id.scc_follow_pcc_mode);
        this.egmcForceRxMode.setOnCheckedChangeListener(this.antEgmcModeChangeListener);
        this.sccFollowPccMode.setOnCheckedChangeListener(this.antEgmcModeChangeListener);
        ((RadioGroup) findViewById(R.id.sim_group)).setOnCheckedChangeListener(this.antEgmcModeChangeListener);
        this.pCell2Rx[0] = (CheckBox) findViewById(R.id.pcell_2rx_rx1);
        this.pCell2Rx[1] = (CheckBox) findViewById(R.id.pcell_2rx_rx2);
        this.pCell4Rx[0] = (CheckBox) findViewById(R.id.pcell_4rx_rx1);
        this.pCell4Rx[1] = (CheckBox) findViewById(R.id.pcell_4rx_rx2);
        this.pCell4Rx[2] = (CheckBox) findViewById(R.id.pcell_4rx_rx3);
        this.pCell4Rx[3] = (CheckBox) findViewById(R.id.pcell_4rx_rx4);
        this.sCell2Rx[0] = (CheckBox) findViewById(R.id.scell_2rx_rx1);
        this.sCell2Rx[1] = (CheckBox) findViewById(R.id.scell_2rx_rx2);
        this.sCell4Rx[0] = (CheckBox) findViewById(R.id.scell_4rx_rx1);
        this.sCell4Rx[1] = (CheckBox) findViewById(R.id.scell_4rx_rx2);
        this.sCell4Rx[2] = (CheckBox) findViewById(R.id.scell_4rx_rx3);
        this.sCell4Rx[3] = (CheckBox) findViewById(R.id.scell_4rx_rx4);
        for (int i = 0; i < 2; i++) {
            this.pCell2Rx[i].setOnCheckedChangeListener(this.antEgmcStatusChangeListener);
            this.sCell2Rx[i].setOnCheckedChangeListener(this.antEgmcStatusChangeListener);
        }
        for (int i2 = 0; i2 < 4; i2++) {
            this.pCell4Rx[i2].setOnCheckedChangeListener(this.antEgmcStatusChangeListener);
            this.sCell4Rx[i2].setOnCheckedChangeListener(this.antEgmcStatusChangeListener);
        }
    }

    public void resetAntEgmcView() {
        this.rxEnable.setChecked(false);
        this.rxDisable.setChecked(false);
        this.SfpEnable.setChecked(false);
        this.SfpDisable.setChecked(false);
        this.mAntennaSim1.setChecked(false);
        this.mAntennaSim2.setChecked(false);
        for (int i = 0; i < 2; i++) {
            this.pCell2Rx[i].setChecked(false);
            this.sCell2Rx[i].setChecked(false);
        }
        for (int i2 = 0; i2 < 4; i2++) {
            this.pCell4Rx[i2].setChecked(false);
            this.sCell4Rx[i2].setChecked(false);
        }
    }

    public void updateAntEgmcView(AntennaEgmcInfo antEgmcObj) {
        if (antEgmcObj == null) {
            resetAntEgmcView();
        } else if (antEgmcObj.isShow()) {
            boolean z = false;
            this.antennaLayoutEgmc.setVisibility(0);
            this.antennaLayout4G.setVisibility(8);
            Elog.d(TAG, "updateAntEgmcView: " + antEgmcObj.getForceRx() + "," + antEgmcObj.getCssFollowPcc() + ", " + Arrays.toString(antEgmcObj.getBoolArrayPCell2Rx()) + ", " + Arrays.toString(antEgmcObj.getBoolArraySCell2Rx()) + ", " + Arrays.toString(antEgmcObj.getBoolArrayPCell4Rx()) + ", " + Arrays.toString(antEgmcObj.getBoolArraySCell4Rx()));
            this.rxEnable.setChecked(antEgmcObj.getForceRx());
            this.rxDisable.setChecked(antEgmcObj.getForceRx() ^ true);
            this.SfpEnable.setChecked(antEgmcObj.getCssFollowPcc());
            this.SfpDisable.setChecked(antEgmcObj.getCssFollowPcc() ^ true);
            if (this.mAntennaLayout.getVisibility() == 0) {
                this.mAntennaSim1.setChecked(antEgmcObj.getSim() == 0);
                RadioButton radioButton = this.mAntennaSim2;
                if (antEgmcObj.getSim() == 1) {
                    z = true;
                }
                radioButton.setChecked(z);
            }
            for (int i = 0; i < 2; i++) {
                this.pCell2Rx[i].setChecked(antEgmcObj.getBoolArrayPCell2Rx()[i]);
                this.sCell2Rx[i].setChecked(antEgmcObj.getBoolArraySCell2Rx()[i]);
            }
            for (int i2 = 0; i2 < 4; i2++) {
                this.pCell4Rx[i2].setChecked(antEgmcObj.getBoolArrayPCell4Rx()[i2]);
                this.sCell4Rx[i2].setChecked(antEgmcObj.getBoolArraySCell4Rx()[i2]);
            }
        }
    }

    public void resetAntEgmcView(AntennaEgmcInfo antEgmcObj) {
        Elog.d(TAG, "resetAntEgmcView");
        if (antEgmcObj.isShow()) {
            this.antennaLayoutEgmc.setVisibility(0);
            this.antennaLayout4G.setVisibility(8);
            ((RadioButton) findViewById(R.id.force_rx_enable)).setChecked(false);
            ((RadioButton) findViewById(R.id.force_rx_disable)).setChecked(false);
            ((RadioButton) findViewById(R.id.scc_follow_pcc_enable)).setChecked(false);
            ((RadioButton) findViewById(R.id.scc_follow_pcc_disable)).setChecked(false);
            this.mAntennaSim1.setChecked(false);
            this.mAntennaSim1.setChecked(false);
            for (int i = 0; i < 2; i++) {
                this.pCell2Rx[i].setChecked(false);
                this.sCell2Rx[i].setChecked(false);
            }
            for (int i2 = 0; i2 < 4; i2++) {
                this.pCell4Rx[i2].setChecked(false);
                this.sCell4Rx[i2].setChecked(false);
            }
        }
    }

    public void queryEgmcAnt4G() {
        sendCommand(new String[]{CMD_QUERY_EGMC_4G, "+EGMC:"}, 5);
    }

    public void queryEgmcAntNR() {
        sendCommand(new String[]{CMD_QUERY_EGMC_NR, "+EGMC:"}, 6);
    }

    public void confirmEgmcAntNR() {
        sendCommand(new String[]{CMD_QUERY_EGMC_NR, "+EGMC:"}, 10);
    }

    public void setEgmcAnt4G() {
        Elog.d(TAG, "setEgmcAnt4G");
        if (this.antEgmc4gObj == null) {
            this.antEgmc4gObj = new AntennaEgmcInfo();
        }
        if (!this.antEgmc4gObj.getForceRx()) {
            this.antEgmc4gObj.CloseForceRxFor();
            updateAntEgmcView(this.antEgmc4gObj);
        }
        initEgmcAnt4G();
    }

    public void setEgmcAntNR() {
        Elog.d(TAG, "setEgmcAntNR");
        if (this.antEgmcNrObj == null) {
            this.antEgmcNrObj = new AntennaEgmcInfo();
        }
        if (!this.antEgmcNrObj.getForceRx()) {
            this.antEgmcNrObj.CloseForceRxFor();
            updateAntEgmcView(this.antEgmcNrObj);
        }
        setEgmcAntForUser(1);
    }

    public void setEgmcAntForUser(int ant_index) {
        String antCmd;
        AntennaEgmcInfo antEgmcObj;
        int i;
        if (ant_index == 0) {
            antCmd = CMD_SET_EGMC_4G;
        } else {
            antCmd = CMD_SET_EGMC_NR;
        }
        if (ant_index == 0) {
            antEgmcObj = this.antEgmc4gObj;
        } else {
            antEgmcObj = this.antEgmcNrObj;
        }
        if (checkValidEgmcInfo(antEgmcObj)) {
            String[] cmd = new String[2];
            if (antEgmcObj.getForceRx() || ant_index != 0) {
                cmd[0] = antCmd + "," + antEgmcObj.getIntForceRx() + "," + antEgmcObj.getIntCssFollowPcc() + "," + antEgmcObj.getPCell2Rx() + "," + antEgmcObj.getPCell4Rx() + "," + antEgmcObj.getSCell2Rx() + "," + antEgmcObj.getSCell4Rx();
                if (ant_index == 1) {
                    cmd[0] = cmd[0] + "," + antEgmcObj.getSim();
                }
            } else {
                cmd[0] = antCmd + "," + 0;
            }
            cmd[1] = "";
            Elog.d(TAG, "setEgmcAntForUser : " + cmd[0]);
            if (ant_index == 0) {
                i = 7;
            } else {
                i = 8;
            }
            sendCommand(cmd, i);
        }
    }

    private void initEgmcAnt4G() {
        sendCommand(new String[]{CMD_INIT_EGMC_4G, ""}, 9);
    }

    private boolean checkValidEgmcInfo(AntennaEgmcInfo antEgmcObj) {
        String antEmgcTitle;
        if (antEgmcObj == null) {
            return false;
        }
        if (this.emgcView == 0) {
            antEmgcTitle = getResources().getString(R.string.antenna_title_4g);
        } else {
            antEmgcTitle = getResources().getString(R.string.antenna_title_nr);
        }
        if (!antEgmcObj.getForceRx()) {
            return true;
        }
        if (antEgmcObj.getPCell4Rx() == 0 || antEgmcObj.getPCell2Rx() == 0) {
            showDialog(0, antEmgcTitle, getResources().getString(R.string.pcell_set_rx0_warn));
            return false;
        } else if (antEgmcObj.getSCell4Rx() == 0 || antEgmcObj.getSCell2Rx() == 0) {
            showDialog(0, antEmgcTitle, getResources().getString(R.string.scell_set_rx0_warn));
            return false;
        } else if (antEgmcObj.getPCell4Rx() == 7 || antEgmcObj.getPCell4Rx() == 11 || antEgmcObj.getPCell4Rx() == 13 || antEgmcObj.getPCell4Rx() == 14) {
            showDialog(0, antEmgcTitle, getResources().getString(R.string.pcell_set_rx3_warn));
            return false;
        } else if (antEgmcObj.getSCell4Rx() != 7 && antEgmcObj.getSCell4Rx() != 11 && antEgmcObj.getSCell4Rx() != 13 && antEgmcObj.getSCell4Rx() != 14) {
            return true;
        } else {
            showDialog(0, antEmgcTitle, getResources().getString(R.string.scell_set_rx3_warn));
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public Dialog showDialog(int id, String title, String info) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this).setTitle(title).setMessage(info).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).show();
            default:
                return null;
        }
    }

    public void initAntLteNrView() {
        this.antennaLayout4G = (LinearLayout) findViewById(R.id.antenna_4g);
        this.antennaLayoutEgmc = (LinearLayout) findViewById(R.id.antanna_egmc);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_antenna_4g);
        this.mSpinner4G = spinner;
        spinner.setOnItemSelectedListener(this.mItemSelectedListener);
        this.mSpinner4G.setEnabled(false);
        this.antennaLayout4G.setVisibility(8);
        this.mSpAntSelect = (Spinner) findViewById(R.id.spinner_antenna_select);
        this.rxEnable = (RadioButton) findViewById(R.id.force_rx_enable);
        this.rxDisable = (RadioButton) findViewById(R.id.force_rx_disable);
        this.SfpEnable = (RadioButton) findViewById(R.id.scc_follow_pcc_enable);
        this.SfpDisable = (RadioButton) findViewById(R.id.scc_follow_pcc_disable);
        this.mAntennaLayout = (LinearLayout) findViewById(R.id.sim_layout);
        this.mAntennaSim1 = (RadioButton) findViewById(R.id.antenna_sim1);
        this.mAntennaSim2 = (RadioButton) findViewById(R.id.antenna_sim2);
        initAntLteNrData();
        initAntEgmcView();
        if (ModemCategory.isLteSupport() || ModemCategory.isNrSupport()) {
            boolean isLteSupport = ModemCategory.isLteSupport();
            int i = R.string.antenna_title_nr;
            int i2 = 1;
            if (!isLteSupport || !ModemCategory.isNrSupport()) {
                this.mSpAntSelect.setVisibility(8);
                if (ModemCategory.isLteSupport()) {
                    i2 = 0;
                }
                this.emgcView = i2;
                TextView textView = (TextView) findViewById(R.id.antenna_title_4g);
                Resources resources = getResources();
                if (this.emgcView == 0) {
                    i = R.string.antenna_title_4g;
                }
                textView.setText(resources.getString(i));
                if (this.emgcView == 0) {
                    this.mAntennaLayout.setVisibility(8);
                } else {
                    this.mAntennaLayout.setVisibility(0);
                }
            } else {
                findViewById(R.id.antenna_title_4g).setVisibility(8);
                ArrayAdapter<String> adapterPattern = new ArrayAdapter<>(this, 17367048, new String[]{getResources().getString(R.string.antenna_title_4g), getResources().getString(R.string.antenna_title_nr)});
                adapterPattern.setDropDownViewResource(17367049);
                this.emgcView = 0;
                this.mSpAntSelect.setAdapter(adapterPattern);
                this.mSpAntSelect.setOnItemSelectedListener(this.mItemSelectedListener);
            }
        } else {
            this.mSpAntSelect.setVisibility(8);
            findViewById(R.id.antenna_title_4g).setVisibility(8);
            findViewById(R.id.antenna_hint).setVisibility(8);
            this.antennaLayout4G.setVisibility(8);
            this.antennaLayoutEgmc.setVisibility(8);
        }
    }

    public void initAntLteNrData() {
        this.emgcView = 0;
        this.antEgmc4gObj = new AntennaEgmcInfo();
        this.antEgmcNrObj = new AntennaEgmcInfo();
    }

    public void initAnt3GView() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_antenna_3g);
        this.mSpinner3G = spinner;
        spinner.setOnItemSelectedListener(this.mItemSelectedListener);
        if (ModemCategory.getModemType() == 2) {
            findViewById(R.id.antenna_title_3g).setVisibility(8);
            this.mSpinner3G.setVisibility(8);
        }
    }

    public void initAnt2GView() {
        this.antennaLayout2G = (LinearLayout) findViewById(R.id.antenna_2g);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_antenna_2g);
        this.mSpinner2G = spinner;
        spinner.setOnItemSelectedListener(this.mItemSelectedListener);
        this.antennaLayout2G.setVisibility(8);
    }

    public void initAntC2KView() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_antenna_c2k_mode);
        this.mSpinnerC2kMode = spinner;
        spinner.setOnItemSelectedListener(this.mItemSelectedListener);
        if (!ModemCategory.isCdma() || FeatureSupport.is90Modem()) {
            findViewById(R.id.antenna_title_c2k).setVisibility(8);
            this.mSpinnerC2kMode.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        queryCurrentMode();
        if (ModemCategory.isCdma() && !FeatureSupport.is90Modem()) {
            queryCurrentCdmaMode();
        }
        if (this.emgcView == 1 && ModemCategory.isNrSupport()) {
            queryEgmcAntNR();
        }
    }

    /* access modifiers changed from: private */
    public void queryCurrentMode() {
        sendCommand(new String[]{"AT+ERXPATH?", "+ERXPATH:"}, 1);
    }

    private void queryCurrentCdmaMode() {
        sendCdmaCommand(ModemCategory.getCdmaCmdArr(new String[]{"AT+ERXTESTMODE?", "+ERXTESTMODE:", "DESTRILD:C2K"}), 4);
    }

    /* access modifiers changed from: private */
    public void setMode(int mode) {
        Elog.i(TAG, "Set mode " + mode);
        sendCommand(new String[]{"AT+ERXPATH=" + mode, ""}, 2);
    }

    /* access modifiers changed from: private */
    public void setCdmaMode(int mode) {
        sendCdmaCommand(ModemCategory.getCdmaCmdArr(new String[]{"AT+ERXTESTMODE=" + mode, "+ERXTESTMODE:", "DESTRILD:C2K"}), 2);
    }

    private void sendCommand(String[] command, int msg) {
        Elog.d(TAG, "sendCommand " + msg + "," + Arrays.toString(command));
        EmUtils.invokeOemRilRequestStringsEm(command, this.mCommandHander.obtainMessage(msg));
    }

    private void sendCdmaCommand(String[] command, int msg) {
        Elog.d(TAG, "send cdma cmd: " + msg + "," + Arrays.toString(command) + ",command.length = " + command.length);
        EmUtils.invokeOemRilRequestStringsEm(true, command, this.mCommandHander.obtainMessage(msg));
    }
}
