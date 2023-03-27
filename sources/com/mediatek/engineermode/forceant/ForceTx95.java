package com.mediatek.engineermode.forceant;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;

public class ForceTx95 extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final int DIG_WARING_WRITE_NVRAM = 1;
    private static final int FORCE_TX_DISABLED_BAND = 3;
    private static final int FORCE_TX_DISABLE_RAT = 0;
    private static final int FORCE_TX_ENABLE_BAND = 4;
    private static final int FORCE_TX_ENABLE_RAT = 1;
    private static final int FORCE_TX_QUERY_BAND = 5;
    private static final int FORCE_TX_QUERY_RAT = 2;
    private static final int FORCE_TX_RAT_C2K = 4;
    private static final int FORCE_TX_RAT_GSM = 1;
    private static final int FORCE_TX_RAT_LTE = 3;
    private static final int FORCE_TX_RAT_UMTS = 2;
    private static final int FORCE_TYPE_BY_BAND = 1;
    private static final int FORCE_TYPE_BY_RAT = 0;
    private static final int QUERY_ANT_INDEX_BY_STATUS = 6;
    private static final int QUERY_ANT_STATUS_BY_INDEX = 7;
    private static final String SHREDPRE_NAME = "ForceAnt95";
    private static final String TAG = "ForceAnt95";
    private static final String TAS_BAND = "TasBand";
    private static final String TAS_MODE = "TasMode";
    private static final String TAS_NVRAM = "TasNvram";
    private static final String TAS_RAT = "TasRat";
    private static final String TAS_TYPE = "TasType";
    /* access modifiers changed from: private */
    public static int mForceType = 0;
    private static String[] mModeArray = {"Disable", "Enable", "Read"};
    private static String[] mRatArray = {"GSM", "UTMS", "LTE", "C2K"};
    /* access modifiers changed from: private */
    public static int mTasModeEnableStates = 0;
    private static String[] mTypeArray = {"by rat", "by band"};
    private static String mtasBand = "1";
    /* access modifiers changed from: private */
    public static int mtasMode = 0;
    private static String mtasNvram = "false";
    /* access modifiers changed from: private */
    public static int mtasRat = 1;
    /* access modifiers changed from: private */
    public static String mtasStates = "1";
    private Handler mATCmdHander = new Handler() {
        public void handleMessage(Message msg) {
            int value;
            AsyncResult ar = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 0:
                case 1:
                case 3:
                case 4:
                    if (ar.exception != null) {
                        EmUtils.showToast("AT cmd failed.");
                        Elog.d("ForceAnt95", "AT cmd failed.");
                        return;
                    }
                    EmUtils.showToast("AT cmd successfully.");
                    Elog.d("ForceAnt95", "AT cmd successfully.");
                    return;
                case 2:
                case 5:
                    if (ar.exception == null) {
                        String[] receiveDate = (String[]) ar.result;
                        if (receiveDate == null) {
                            EmUtils.showToast("Warning: Received data is null!");
                            Elog.e("ForceAnt95", "Received data is null");
                            return;
                        }
                        Elog.d("ForceAnt95", "receiveDate[0] = " + receiveDate[0]);
                        try {
                            value = Integer.parseInt(receiveDate[0].split(",")[1]);
                        } catch (Exception e) {
                            value = 0;
                        }
                        Elog.d("ForceAnt95", "value = " + value);
                        EmUtils.showToast("Query ANT states succeed,value = " + value);
                        if (value < 0 || value >= 8) {
                            ForceTx95.this.mTasAntStatesSpinner.setSelection(8);
                            return;
                        } else {
                            ForceTx95.this.mTasAntStatesSpinner.setSelection(value);
                            return;
                        }
                    } else {
                        EmUtils.showToast("AT cmd failed.");
                        Elog.e("ForceAnt95", "Received data is null");
                        return;
                    }
                case 6:
                    if (ar.exception != null) {
                        EmUtils.showToast("The states not support");
                        Elog.d("ForceAnt95", "AT cmd failed,The States not support");
                        return;
                    }
                    String[] receiveDate2 = (String[]) ar.result;
                    Elog.d("ForceAnt95", "receiveDate[0] = " + receiveDate2[0]);
                    TextView access$000 = ForceTx95.this.mTasAntTest;
                    access$000.append("\r\n" + receiveDate2[0]);
                    if (ForceTx95.this.parseAntIndex(receiveDate2[0]) < 0) {
                        EmUtils.showToast("query ANT index by states failed.");
                        Elog.d("ForceAnt95", "query ANT index by states failed.");
                        return;
                    }
                    EmUtils.showToast("query ANT index by states successfully.");
                    Elog.d("ForceAnt95", "query ANT index by states successfully.");
                    return;
                case 7:
                    if (ar.exception != null) {
                        EmUtils.showToast("AT cmd failed or The Index not support");
                        Elog.d("ForceAnt95", "AT cmd failed or The Index not supportz");
                        return;
                    }
                    String[] receiveDate3 = (String[]) ar.result;
                    if (receiveDate3.length == 0) {
                        Elog.e("ForceAnt95", "The Index not support");
                        EmUtils.showToast("The Index not support, Please check the paramer");
                        return;
                    }
                    Elog.d("ForceAnt95", "receiveDate[0] = " + receiveDate3[0]);
                    TextView access$0002 = ForceTx95.this.mTasAntTest;
                    access$0002.append("\r\n" + receiveDate3[0]);
                    if (ForceTx95.this.parseAntStaus(receiveDate3[0]) < 0) {
                        EmUtils.showToast("Query ANT states by index failed.");
                        Elog.d("ForceAnt95", "Query ANT states by index failed.");
                        return;
                    }
                    Elog.d("ForceAnt95", "Query ANT states by index successfully.");
                    EmUtils.showToast("Query ANT states by index successfully.");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public TableRow mAntIndexDRxTableRow;
    private Button mEnableStatesBtn;
    /* access modifiers changed from: private */
    public Spinner mForceAntModeSpinner;
    private RadioButton mForceBandRadioBtn = null;
    private RadioButton mForceRatRadioBtn = null;
    /* access modifiers changed from: private */
    public Button mQueryIndexOrStatesBtn;
    private String mQueryMode = "";
    private RadioGroup mRGForceType = null;
    private AdapterView.OnItemSelectedListener mSpinnerListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
            int i = 3;
            int i2 = 1;
            if (parent == ForceTx95.this.mForceAntModeSpinner) {
                Elog.d("ForceAnt95", "mForceAntModeSpinner changed, pos = " + pos);
                if (pos == 0) {
                    ForceTx95.this.mQueryIndexOrStatesBtn.setEnabled(false);
                    if (ForceTx95.mForceType != 1) {
                        i = 0;
                    }
                    int unused = ForceTx95.mtasMode = i;
                    ForceTx95.this.mTasAntTxPrx.setSelection(6);
                    ForceTx95.this.mTasAntDrx.setSelection(6);
                    ForceTx95.this.mTasAntDrx1.setSelection(6);
                    ForceTx95.this.mTasAntDrx2.setSelection(6);
                    ForceTx95.this.mTasAntStatesSpinner.setSelection(9);
                } else if (pos == 1) {
                    ForceTx95.this.mQueryIndexOrStatesBtn.setEnabled(true);
                    if (ForceTx95.mForceType == 1) {
                        i2 = 4;
                    }
                    int unused2 = ForceTx95.mtasMode = i2;
                } else {
                    int i3 = 2;
                    if (pos == 2) {
                        ForceTx95.this.mQueryIndexOrStatesBtn.setEnabled(false);
                        if (ForceTx95.mForceType == 1) {
                            i3 = 5;
                        }
                        int unused3 = ForceTx95.mtasMode = i3;
                        ForceTx95.this.mTasAntTxPrx.setSelection(6);
                        ForceTx95.this.mTasAntDrx.setSelection(6);
                        ForceTx95.this.mTasAntDrx1.setSelection(6);
                        ForceTx95.this.mTasAntDrx2.setSelection(6);
                        ForceTx95.this.mTasNvramEdit.setText("");
                        ForceTx95.this.mTasAntStatesSpinner.setSelection(9);
                    }
                }
                ForceTx95.this.forceTypeUpdate();
            } else if (parent == ForceTx95.this.mTasRatSpinner) {
                Elog.d("ForceAnt95", "mTasRatSpinner changed, pos = " + pos);
                int unused4 = ForceTx95.mtasRat = pos + 1;
                if (ForceTx95.mtasRat == 3) {
                    ForceTx95.this.mAntIndexDRxTableRow.setVisibility(0);
                } else {
                    ForceTx95.this.mAntIndexDRxTableRow.setVisibility(8);
                }
            } else if (parent == ForceTx95.this.mTasAntStatesSpinner) {
                Elog.d("ForceAnt95", "mTasAntStatesSpinner changed, pos = " + pos);
                String unused5 = ForceTx95.mtasStates = String.valueOf(pos);
                if (ForceTx95.mTasModeEnableStates == 1) {
                    ForceTx95.this.mTasAntTxPrx.setSelection(6);
                    ForceTx95.this.mTasAntDrx.setSelection(6);
                    ForceTx95.this.mTasAntDrx1.setSelection(6);
                    ForceTx95.this.mTasAntDrx2.setSelection(6);
                }
            }
        }

        public void onNothingSelected(AdapterView parent) {
        }
    };
    /* access modifiers changed from: private */
    public Spinner mTasAntDrx;
    /* access modifiers changed from: private */
    public Spinner mTasAntDrx1;
    /* access modifiers changed from: private */
    public Spinner mTasAntDrx2;
    /* access modifiers changed from: private */
    public Spinner mTasAntStatesSpinner;
    private TextView mTasAntSwitchLabel;
    /* access modifiers changed from: private */
    public TextView mTasAntTest;
    /* access modifiers changed from: private */
    public Spinner mTasAntTxPrx;
    private EditText mTasBandEdit;
    /* access modifiers changed from: private */
    public EditText mTasNvramEdit;
    /* access modifiers changed from: private */
    public Spinner mTasRatSpinner;

    /* access modifiers changed from: package-private */
    public int parseAntStaus(String info) {
        try {
            int states_value = Integer.parseInt(info.split(",")[1]);
            if (states_value < 0 || states_value >= 6) {
                this.mTasAntStatesSpinner.setSelection(6);
                return 0;
            }
            this.mTasAntStatesSpinner.setSelection(states_value);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    /* access modifiers changed from: package-private */
    public int parseAntIndex(String info) {
        try {
            String[] index = info.split(",");
            for (String value : index) {
                Elog.d("ForceAnt95", "value = " + value);
            }
            if (index[2].equals("255")) {
                this.mTasAntTxPrx.setSelection(5);
            } else {
                this.mTasAntTxPrx.setSelection(Integer.parseInt(index[2]));
            }
            if (index[3].equals("255")) {
                this.mTasAntDrx.setSelection(5);
            } else {
                this.mTasAntDrx.setSelection(Integer.parseInt(index[3]));
            }
            if (index[4].equals("255")) {
                this.mTasAntDrx1.setSelection(5);
            } else {
                this.mTasAntDrx1.setSelection(Integer.parseInt(index[4]));
            }
            if (index[5].equals("255")) {
                this.mTasAntDrx2.setSelection(5);
            } else {
                this.mTasAntDrx2.setSelection(Integer.parseInt(index[5]));
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.force_tx_95);
        this.mQueryMode = getIntent().getStringExtra("MODE");
        Elog.d("ForceAnt95", "MODE = " + this.mQueryMode);
        this.mRGForceType = (RadioGroup) findViewById(R.id.force_type);
        this.mForceRatRadioBtn = (RadioButton) findViewById(R.id.force_rat);
        this.mForceBandRadioBtn = (RadioButton) findViewById(R.id.force_band);
        this.mForceAntModeSpinner = (Spinner) findViewById(R.id.force_ant_mode);
        this.mTasRatSpinner = (Spinner) findViewById(R.id.tas_rat);
        this.mTasBandEdit = (EditText) findViewById(R.id.tas_band);
        this.mTasNvramEdit = (EditText) findViewById(R.id.tas_nvram);
        this.mTasAntStatesSpinner = (Spinner) findViewById(R.id.tas_ant_states);
        this.mQueryIndexOrStatesBtn = (Button) findViewById(R.id.get_ant_index_states);
        this.mEnableStatesBtn = (Button) findViewById(R.id.enable_ant_states);
        this.mTasAntTxPrx = (Spinner) findViewById(R.id.tas_ant_tx_prx);
        this.mTasAntDrx = (Spinner) findViewById(R.id.tas_ant_drx);
        this.mTasAntDrx1 = (Spinner) findViewById(R.id.tas_ant_drx1);
        this.mTasAntDrx2 = (Spinner) findViewById(R.id.tas_ant_drx2);
        this.mTasAntTest = (TextView) findViewById(R.id.tas_ant_test);
        this.mTasAntSwitchLabel = (TextView) findViewById(R.id.Ant_switch_label);
        this.mAntIndexDRxTableRow = (TableRow) findViewById(R.id.TableRow08);
        this.mTasNvramEdit.setEnabled(false);
        this.mQueryIndexOrStatesBtn.setOnClickListener(this);
        this.mEnableStatesBtn.setOnClickListener(this);
        this.mRGForceType.setOnCheckedChangeListener(this);
        this.mForceAntModeSpinner.setOnItemSelectedListener(this.mSpinnerListener);
        this.mTasRatSpinner.setOnItemSelectedListener(this.mSpinnerListener);
        this.mTasAntStatesSpinner.setOnItemSelectedListener(this.mSpinnerListener);
    }

    public void onResume() {
        super.onResume();
        Elog.d("ForceAnt95", "onResume");
        getTasSettingStates();
        Spinner spinner = this.mForceAntModeSpinner;
        int i = mtasMode;
        if (i >= 3) {
            i -= 3;
        }
        spinner.setSelection(i, true);
        this.mTasRatSpinner.setSelection(mtasRat - 1, true);
        forceTypeUpdate();
        if (this.mQueryMode.equals("by_states")) {
            this.mTasAntSwitchLabel.setText(getString(R.string.get_ant_index));
            this.mQueryIndexOrStatesBtn.setText(getString(R.string.get_ant_index));
            mTasModeEnableStates = 1;
            this.mTasAntTxPrx.setEnabled(false);
            this.mTasAntDrx.setEnabled(false);
            this.mTasAntDrx1.setEnabled(false);
            this.mTasAntDrx2.setEnabled(false);
            this.mTasAntStatesSpinner.setEnabled(true);
        } else if (this.mQueryMode.equals("by_index")) {
            this.mTasAntSwitchLabel.setText(getString(R.string.get_ant_states));
            this.mQueryIndexOrStatesBtn.setText(getString(R.string.get_ant_states));
            mTasModeEnableStates = 2;
            this.mTasAntTxPrx.setEnabled(true);
            this.mTasAntDrx.setEnabled(true);
            this.mTasAntDrx1.setEnabled(true);
            this.mTasAntDrx2.setEnabled(true);
            this.mTasAntStatesSpinner.setEnabled(false);
        }
    }

    public void onClick(View arg0) {
        if (arg0 == this.mEnableStatesBtn) {
            Elog.d("ForceAnt95", "Set ANT States click:.");
            mTasModeEnableStates = 0;
        } else if (arg0 == this.mQueryIndexOrStatesBtn) {
            if (this.mQueryMode.equals("by_states")) {
                mTasModeEnableStates = 1;
                Elog.d("ForceAnt95", "Query ANT index by states click.");
            } else {
                mTasModeEnableStates = 2;
                Elog.d("ForceAnt95", "Query ANT states by index click.");
            }
        }
        if (!mtasNvram.equals("true") || mTasModeEnableStates != 0) {
            execTheCmd();
        } else {
            showDialog(1);
        }
    }

    public void execTheCmd() {
        String tasBand;
        if (mForceType == 1) {
            mtasBand = this.mTasBandEdit.getText().toString();
            tasBand = mtasBand;
            if (tasBand.equals("")) {
                EmUtils.showToast("By band mode,the band value should not be empty");
                return;
            }
        } else {
            tasBand = "";
        }
        int selectedItemPosition = this.mForceAntModeSpinner.getSelectedItemPosition() + (mForceType == 1 ? 3 : 0);
        mtasMode = selectedItemPosition;
        String tasStates = mtasStates;
        if (selectedItemPosition == 0 || selectedItemPosition == 2 || selectedItemPosition == 3 || selectedItemPosition == 5) {
            tasStates = "";
        } else if (mTasModeEnableStates != 2 && ((selectedItemPosition == 1 || selectedItemPosition == 4) && (mtasStates.equals("8") || mtasStates.equals("9")))) {
            EmUtils.showToast("The ANT states should be 0~7");
            return;
        }
        String tasNvram = mtasNvram.equals("true") ? "1" : "0";
        Elog.d("ForceAnt95", "mForceType = " + mForceType + "," + mTypeArray[mForceType]);
        StringBuilder sb = new StringBuilder();
        sb.append("mtasMode = ");
        sb.append(mtasMode);
        sb.append(",");
        String[] strArr = mModeArray;
        int i = mtasMode;
        if (i >= 3) {
            i -= 3;
        }
        sb.append(strArr[i]);
        Elog.d("ForceAnt95", sb.toString());
        Elog.d("ForceAnt95", "mtasRat = " + mtasRat + "," + mRatArray[mtasRat - 1]);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("mtasNvram = ");
        sb2.append(tasNvram);
        Elog.d("ForceAnt95", sb2.toString());
        Elog.d("ForceAnt95", "tasBand = " + tasBand);
        Elog.d("ForceAnt95", "mtasStates = " + tasStates);
        writeTasSettingStates();
        int i2 = mTasModeEnableStates;
        if (i2 == 1) {
            queryIndexByStates(mtasRat, tasBand, tasStates);
        } else if (i2 == 2) {
            queryStatesByIndex(mtasRat, tasBand);
        } else {
            setTasForceIdx(mtasMode, mtasRat, tasStates, tasBand, tasNvram);
        }
    }

    public void setTasForceIdx(int mode, int rat, String antenna_idx, String band, String nvram_write) {
        String[] cmd = new String[2];
        if (mForceType == 1) {
            cmd[0] = "AT+ETXANT=" + mode + "," + rat + "," + antenna_idx + "," + band;
        } else {
            cmd[0] = "AT+ETXANT=" + mode + "," + rat + "," + antenna_idx + "," + band + "," + nvram_write;
        }
        cmd[1] = "+ETXANT:";
        Elog.d("ForceAnt95", "sendAtCommand: " + cmd[0]);
        this.mTasAntTest.setText(cmd[0]);
        senAtCommand(cmd, mode);
    }

    /* access modifiers changed from: package-private */
    public boolean isTasStatesValid(Spinner mTasAntTxPrx2) {
        if (mTasAntTxPrx2.getSelectedItemPosition() == 5 || mTasAntTxPrx2.getSelectedItemPosition() == 6) {
            return false;
        }
        return true;
    }

    public void queryStatesByIndex(int rat, String band) {
        int tasAntDrx;
        int tx_count;
        int tasAntDrx2;
        int tasAntDrx1;
        int tasAntDrx12;
        int tasAntDrx3;
        int tasAntDrx4;
        int tx_count2;
        String str = band;
        String[] cmd = new String[2];
        String[] configureName = {"\"gsm_utas\"", "\"wcdma_utas\"", "\"t_utas_query\"", "\"c2k_utas\"", "\"l_utas_query\""};
        String[] ratMapping = {"", "0", "1", "4", "3"};
        if (ModemCategory.getModemType() == 1) {
            ratMapping[2] = "1";
            Elog.d("ForceAnt95", "3G WCDMA");
        } else {
            ratMapping[2] = "2";
            Elog.d("ForceAnt95", "3G TDSCDMA");
        }
        int ratIndex = Integer.parseInt(ratMapping[rat]);
        int tasAntDrx5 = 0;
        int tasAntDrx13 = 255;
        int tasAntDrx14 = 255;
        int tasAntDrx22 = 255;
        if (!isTasStatesValid(this.mTasAntTxPrx)) {
            EmUtils.showToast("The TxPrxshould not be 255 or null");
            return;
        }
        int tasAntTxPrx = this.mTasAntTxPrx.getSelectedItemPosition();
        if (ratIndex == 0) {
            int tasAntTxPrx2 = tasAntTxPrx + 1;
            if (!isTasStatesValid(this.mTasAntDrx)) {
                tasAntDrx4 = 0;
                tx_count2 = 1;
            } else {
                tasAntDrx4 = this.mTasAntDrx.getSelectedItemPosition() + 1;
                tx_count2 = 2;
            }
            cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + "," + str + "," + tx_count2 + ",";
            if (tx_count2 == 1) {
                cmd[0] = cmd[0] + tasAntTxPrx2 + ",," + tasAntTxPrx2;
            } else {
                cmd[0] = cmd[0] + tasAntTxPrx2 + "," + tasAntDrx4 + "," + tasAntTxPrx2;
            }
            String[] strArr = ratMapping;
        } else {
            String[] strArr2 = ratMapping;
            if (ratIndex == 1) {
                int tasAntTxPrx3 = tasAntTxPrx + 1;
                if (!isTasStatesValid(this.mTasAntDrx)) {
                    tasAntDrx2 = 0;
                    tasAntDrx1 = 0;
                    tasAntDrx12 = 255;
                    tasAntDrx3 = 1;
                } else {
                    tasAntDrx2 = 0;
                    tasAntDrx1 = 0;
                    tasAntDrx12 = this.mTasAntDrx.getSelectedItemPosition() + 1;
                    tasAntDrx3 = 2;
                }
                cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + ",1," + str + "," + tasAntDrx3 + ",";
                cmd[0] = cmd[0] + tasAntTxPrx3 + "," + tasAntDrx12 + "," + tasAntDrx1 + "," + tasAntDrx2 + "," + tasAntTxPrx3;
            } else if (ratIndex == 2) {
                if (isTasStatesValid(this.mTasAntDrx)) {
                    EmUtils.showToast("The tdscdma only support 1 path： prx trx");
                    return;
                }
                cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + "," + str + "," + 1 + "," + tasAntTxPrx;
            } else if (ratIndex == 3) {
                int tasAntTxPrx4 = tasAntTxPrx + 1;
                if (!isTasStatesValid(this.mTasAntDrx)) {
                    tx_count = 1;
                    tasAntDrx = 255;
                } else {
                    tx_count = 2;
                    tasAntDrx = this.mTasAntDrx.getSelectedItemPosition() + 1;
                }
                cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + "," + str + "," + tx_count + ",";
                StringBuilder sb = new StringBuilder();
                sb.append(cmd[0]);
                sb.append(tasAntTxPrx4);
                sb.append(",");
                sb.append(tasAntDrx);
                cmd[0] = sb.toString();
            } else if (ratIndex == 4) {
                if (!isTasStatesValid(this.mTasAntDrx)) {
                    EmUtils.showToast("The mTasAntDrx not be 255 or null in lte mode");
                    return;
                }
                int tasAntTxPrx5 = tasAntTxPrx + 1;
                if ((isTasStatesValid(this.mTasAntDrx1) && !isTasStatesValid(this.mTasAntDrx2)) || (!isTasStatesValid(this.mTasAntDrx1) && isTasStatesValid(this.mTasAntDrx2))) {
                    EmUtils.showToast("TasAntDrx1 or TasAntDrx2 should both 255 or not");
                } else if (!isTasStatesValid(this.mTasAntDrx1) || !isTasStatesValid(this.mTasAntDrx2)) {
                    tasAntDrx22 = 0;
                    tasAntDrx14 = 0;
                    tasAntDrx13 = this.mTasAntDrx.getSelectedItemPosition() + 1;
                    tasAntDrx5 = 2;
                } else {
                    tasAntDrx22 = this.mTasAntDrx2.getSelectedItemPosition() + 1;
                    tasAntDrx14 = this.mTasAntDrx1.getSelectedItemPosition() + 1;
                    tasAntDrx13 = this.mTasAntDrx.getSelectedItemPosition() + 1;
                    tasAntDrx5 = 4;
                }
                cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + ",1," + str + "," + tasAntDrx5 + ",";
                cmd[0] = cmd[0] + tasAntTxPrx5 + "," + tasAntDrx13 + "," + tasAntDrx14 + "," + tasAntDrx22 + "," + tasAntTxPrx5;
            }
        }
        cmd[1] = "+EGMC:";
        Elog.d("ForceAnt95", "sendAtCommand: " + cmd[0]);
        this.mTasAntTest.setText(cmd[0]);
        senAtCommand(cmd, 7);
    }

    public void queryIndexByStates(int rat, String band, String tasStates) {
        String[] cmd = {"AT+EGMC=1,\"utas_query_ant_port\"," + new String[]{"", "0", "1", "4", "3"}[rat] + "," + band + "," + tasStates, "+EGMC:"};
        StringBuilder sb = new StringBuilder();
        sb.append("sendAtCommand: ");
        sb.append(cmd[0]);
        Elog.d("ForceAnt95", sb.toString());
        this.mTasAntTest.setText(cmd[0]);
        senAtCommand(cmd, 6);
    }

    public void senAtCommand(String[] atCommand, int msg) {
        EmUtils.invokeOemRilRequestStringsEm(atCommand, this.mATCmdHander.obtainMessage(msg));
    }

    public void getTasSettingStates() {
        SharedPreferences tasVersionSh = getSharedPreferences("ForceAnt95", 0);
        mForceType = tasVersionSh.getInt(TAS_TYPE, 0);
        mtasMode = tasVersionSh.getInt(TAS_MODE, 0);
        mtasRat = tasVersionSh.getInt(TAS_RAT, 1);
        mtasNvram = tasVersionSh.getString(TAS_NVRAM, "true");
        mtasBand = tasVersionSh.getString(TAS_BAND, "1");
        Elog.d("ForceAnt95", "\ngetTasSettingStates: ");
        Elog.d("ForceAnt95", "mForceType = " + mForceType);
        Elog.d("ForceAnt95", "mtasMode = " + mtasMode);
        Elog.d("ForceAnt95", "mtasRat = " + mtasRat);
        Elog.d("ForceAnt95", "mtasNvram = " + mtasNvram);
        Elog.d("ForceAnt95", "mtasBand = " + mtasBand);
    }

    public void writeTasSettingStates() {
        SharedPreferences.Editor editor = getSharedPreferences("ForceAnt95", 0).edit();
        editor.putInt(TAS_TYPE, mForceType);
        editor.putInt(TAS_MODE, mtasMode);
        editor.putInt(TAS_RAT, mtasRat);
        editor.putString(TAS_NVRAM, mtasNvram);
        editor.putString(TAS_BAND, mtasBand);
        editor.commit();
    }

    /* access modifiers changed from: package-private */
    public void forceTypeUpdate() {
        int i;
        int i2 = mForceType;
        if (i2 == 0) {
            this.mForceRatRadioBtn.setChecked(true);
            this.mTasNvramEdit.setText("true");
            mtasNvram = "true";
            this.mTasBandEdit.setEnabled(false);
            this.mTasBandEdit.setText("all band");
        } else if (i2 == 1) {
            this.mForceBandRadioBtn.setChecked(true);
            this.mTasNvramEdit.setText("false");
            mtasNvram = "false";
            this.mTasBandEdit.setEnabled(true);
            this.mTasBandEdit.setText(mtasBand);
        }
        int i3 = mtasMode;
        if (i3 == 2 || i3 == 5) {
            this.mTasNvramEdit.setText("");
            mtasNvram = "false";
        }
        if (mForceType == 1 && ((i = mtasMode) == 1 || i == 4)) {
            this.mQueryIndexOrStatesBtn.setEnabled(true);
        } else {
            this.mQueryIndexOrStatesBtn.setEnabled(false);
        }
    }

    public void onCheckedChanged(RadioGroup arg0, int checkedId) {
        if (checkedId == R.id.force_rat) {
            mForceType = 0;
        } else if (checkedId == R.id.force_band) {
            mForceType = 1;
        }
        Elog.d("ForceAnt95", "forceTypeChanged, mForceType = " + mForceType + "," + mTypeArray[mForceType]);
        forceTypeUpdate();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this).setTitle("Warning").setMessage("Enable force state for all bands and write force state to NVRAM\n").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {
                            ForceTx95.this.execTheCmd();
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create();
            default:
                return super.onCreateDialog(id);
        }
    }
}
