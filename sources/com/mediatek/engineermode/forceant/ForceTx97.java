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
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;

public class ForceTx97 extends Activity implements View.OnClickListener {
    private static final int BUTTON_BY_INDEX = 3;
    private static final int BUTTON_BY_STATE = 2;
    private static final int BUTTON_SET_BY_BAND = 0;
    private static final int BUTTON_SET_BY_RET = 1;
    private static final int DIG_WARING_WRITE_NVRAM = 1;
    private static final int FORCE_TX_DISABLE = 0;
    private static final int FORCE_TX_ENABLE = 1;
    private static final int FORCE_TX_RAT_C2K = 4;
    private static final int FORCE_TX_RAT_GSM = 1;
    private static final int FORCE_TX_RAT_LTE = 3;
    private static final int FORCE_TX_RAT_LTE_TDD = 6;
    private static final int FORCE_TX_RAT_NR = 5;
    private static final int FORCE_TX_RAT_UMTS = 2;
    private static final int FORCE_TX_READ = 2;
    private static final int QUERY_ANT_INDEX_BY_STATUS = 6;
    private static final int QUERY_ANT_STATUS_BY_INDEX = 7;
    private static final String SHREDPRE_NAME = "ForceAnt97";
    private static final String TAG = "ForceAnt97";
    private static final String TAS_BAND = "TasBand";
    private static final String TAS_MODE = "TasMode";
    private static final String TAS_NVRAM = "TasNvram";
    private static final String TAS_RAT = "TasRat";
    private static final String TAS_TYPE = "TasType";
    private static String[] mModeArray = {"Disable", "Enable", "Read"};
    private static String[] mRatArray = {"GSM", "UTMS", "LTE", "C2K", "NR", "LTE TDD"};
    private static int mTasModeEnableStates = 0;
    private static String[] mTypeArray = {"by rat", "by band"};
    private static String mtasBand = "1";
    /* access modifiers changed from: private */
    public static int mtasMode = 0;
    private static String mtasNvram = "false";
    /* access modifiers changed from: private */
    public static int mtasRat = 1;
    /* access modifiers changed from: private */
    public static String mtasStatesRx = "1";
    /* access modifiers changed from: private */
    public static String mtasStatesTx = "1";
    private Handler mATCmdHander = new Handler() {
        public void handleMessage(Message msg) {
            int value_tx;
            AsyncResult ar = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 0:
                case 1:
                    if (ar.exception != null) {
                        EmUtils.showToast("AT cmd failed.");
                        Elog.d("ForceAnt97", "AT cmd failed.");
                        return;
                    }
                    EmUtils.showToast("AT cmd successfully.");
                    Elog.d("ForceAnt97", "AT cmd successfully.");
                    return;
                case 2:
                    if (ar.exception == null) {
                        String[] receiveDate = (String[]) ar.result;
                        if (receiveDate == null) {
                            EmUtils.showToast("Warning: Received data is null!");
                            Elog.e("ForceAnt97", "Received data is null");
                            return;
                        } else if (receiveDate[0].length() < 1) {
                            Elog.e("ForceAnt97", "Received data is 0");
                            return;
                        } else {
                            Elog.d("ForceAnt97", "receiveDate[0] = " + receiveDate[0]);
                            int value_rx = -1;
                            if ("+ETXANT".startsWith(receiveDate[0])) {
                                try {
                                    value_tx = Integer.parseInt(receiveDate[0].split(",")[1]);
                                } catch (Exception e) {
                                    value_tx = 0;
                                }
                            } else {
                                try {
                                    String[] getDigitalVal = receiveDate[0].split(",");
                                    value_tx = Integer.parseInt(getDigitalVal[1]);
                                    value_rx = Integer.parseInt(getDigitalVal[2]);
                                } catch (Exception e2) {
                                    value_tx = 0;
                                    value_rx = 0;
                                }
                            }
                            Elog.d("ForceAnt97", "value_tx = " + value_tx);
                            EmUtils.showToast("Query ANT state succeed,value_tx = " + value_tx);
                            if (value_tx < 0 || value_tx >= 24) {
                                ForceTx97.this.mTasAntStatesSpinnerTx.setSelection(24);
                            } else {
                                ForceTx97.this.mTasAntStatesSpinnerTx.setSelection(value_tx);
                            }
                            Elog.d("ForceAnt97", "value_rx = " + value_rx);
                            EmUtils.showToast("Query ANT state succeed,value_rx = " + value_rx);
                            if (value_rx < 0 || value_rx >= 24) {
                                ForceTx97.this.mTasAntStatesSpinnerRx.setSelection(24);
                                return;
                            } else {
                                ForceTx97.this.mTasAntStatesSpinnerRx.setSelection(value_rx);
                                return;
                            }
                        }
                    } else {
                        EmUtils.showToast("AT cmd failed.");
                        Elog.e("ForceAnt97", "Received data is null");
                        return;
                    }
                case 6:
                    if (ar.exception != null) {
                        EmUtils.showToast("The state not support");
                        Elog.d("ForceAnt97", "AT cmd failed,The state not support");
                        return;
                    }
                    String[] receiveDate2 = (String[]) ar.result;
                    Elog.d("ForceAnt97", "receiveDate[0] = " + receiveDate2[0]);
                    TextView access$000 = ForceTx97.this.mTasAntTest;
                    access$000.append("\r\n" + receiveDate2[0]);
                    if (ForceTx97.this.parseAntIndex(receiveDate2[0]) < 0) {
                        EmUtils.showToast("query ANT index by state failed.");
                        Elog.d("ForceAnt97", "query ANT index by state failed.");
                        return;
                    }
                    EmUtils.showToast("query ANT index by state successfully.");
                    Elog.d("ForceAnt97", "query ANT index by state successfully.");
                    return;
                case 7:
                    if (ar.exception != null) {
                        EmUtils.showToast("AT cmd failed or The Index not support");
                        Elog.d("ForceAnt97", "AT cmd failed or The Index not supportz");
                        return;
                    }
                    String[] receiveDate3 = (String[]) ar.result;
                    if (receiveDate3.length == 0) {
                        Elog.e("ForceAnt97", "Received data is 0");
                        Elog.e("ForceAnt97", "The Index not support");
                        EmUtils.showToast("The Index not support, Please check the paramer");
                        return;
                    }
                    Elog.d("ForceAnt97", "receiveDate[0] = " + receiveDate3[0]);
                    TextView access$0002 = ForceTx97.this.mTasAntTest;
                    access$0002.append("\r\n" + receiveDate3[0]);
                    if (ForceTx97.this.parseAntStaus(receiveDate3[0]) < 0) {
                        EmUtils.showToast("Query ANT state by index failed.");
                        Elog.d("ForceAnt97", "Query ANT state by index failed.");
                        return;
                    }
                    Elog.d("ForceAnt97", "Query ANT state by index successfully.");
                    EmUtils.showToast("Query ANT state by index successfully.");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public TableRow mAntIndexMIMOTableRow;
    /* access modifiers changed from: private */
    public TableRow mAntIndexTxTableRow;
    private Button mEnableStatesBtnBand;
    /* access modifiers changed from: private */
    public Button mEnableStatesBtnRet;
    /* access modifiers changed from: private */
    public Spinner mForceAntModeSpinner;
    private int mModemType;
    /* access modifiers changed from: private */
    public Button mQueryIndexOrStatesBtn;
    private String mQueryMode = "";
    private AdapterView.OnItemSelectedListener mSpinnerListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
            if (parent == ForceTx97.this.mForceAntModeSpinner) {
                Elog.d("ForceAnt97", "mForceAntModeSpinner changed, pos = " + pos);
                if (pos == 0) {
                    ForceTx97.this.mQueryIndexOrStatesBtn.setEnabled(false);
                } else if (pos == 1) {
                    ForceTx97.this.mQueryIndexOrStatesBtn.setEnabled(true);
                } else if (pos == 2) {
                    ForceTx97.this.mQueryIndexOrStatesBtn.setEnabled(false);
                }
                int unused = ForceTx97.mtasMode = pos;
            } else if (parent == ForceTx97.this.mTasRatSpinner) {
                Elog.d("ForceAnt97", "mTasRatSpinner changed, pos = " + pos);
                int unused2 = ForceTx97.mtasRat = pos + 1;
                ForceTx97.this.mTextViewHint.setVisibility(8);
                if (ForceTx97.mtasRat == 5 || ForceTx97.mtasRat == 6) {
                    ForceTx97.this.mAntIndexTxTableRow.setVisibility(0);
                    ForceTx97.this.mAntIndexMIMOTableRow.setVisibility(0);
                    ForceTx97.this.mTasAntStatesSpinnerRx.setVisibility(0);
                    ForceTx97.this.mTasAntStatesSpinnerRxView.setVisibility(0);
                    ForceTx97.this.mTasAntStatesSpinnerTxView.setText(R.string.tas_ant_states_tx);
                    ForceTx97.this.mTextViewTx1.setVisibility(0);
                    ForceTx97.this.mTasAntTx1.setVisibility(0);
                    if (ForceTx97.mtasRat == 6) {
                        ForceTx97.this.mTextViewHint.setVisibility(0);
                        ForceTx97.this.mEnableStatesBtnRet.setVisibility(8);
                        return;
                    }
                    ForceTx97.this.mEnableStatesBtnRet.setVisibility(0);
                } else if (ForceTx97.mtasRat == 3) {
                    ForceTx97.this.mAntIndexTxTableRow.setVisibility(0);
                    ForceTx97.this.mAntIndexMIMOTableRow.setVisibility(0);
                    ForceTx97.this.mTasAntStatesSpinnerRx.setVisibility(8);
                    ForceTx97.this.mTasAntStatesSpinnerRxView.setVisibility(8);
                    ForceTx97.this.mTasAntStatesSpinnerTxView.setText(R.string.tas_ant_states_tx);
                    ForceTx97.this.mTextViewTx1.setVisibility(8);
                    ForceTx97.this.mTasAntTx1.setVisibility(8);
                    ForceTx97.this.mEnableStatesBtnRet.setVisibility(0);
                } else {
                    ForceTx97.this.mAntIndexTxTableRow.setVisibility(8);
                    ForceTx97.this.mAntIndexMIMOTableRow.setVisibility(8);
                    ForceTx97.this.mTasAntStatesSpinnerRx.setVisibility(8);
                    ForceTx97.this.mTasAntStatesSpinnerRxView.setVisibility(8);
                    ForceTx97.this.mTasAntStatesSpinnerTxView.setText(R.string.tas_ant_states);
                    ForceTx97.this.mEnableStatesBtnRet.setVisibility(0);
                }
            } else if (parent == ForceTx97.this.mTasAntStatesSpinnerTx) {
                Elog.d("ForceAnt97", "mTasAntStatesTxSpinner changed, pos = " + pos);
                String unused3 = ForceTx97.mtasStatesTx = String.valueOf(pos);
            } else if (parent == ForceTx97.this.mTasAntStatesSpinnerRx) {
                Elog.d("ForceAnt97", "mTasAntStatesRxSpinner changed, pos = " + pos);
                String unused4 = ForceTx97.mtasStatesRx = String.valueOf(pos);
            }
        }

        public void onNothingSelected(AdapterView parent) {
        }
    };
    private Spinner mTasAntDrx;
    private Spinner mTasAntMIMO1;
    private Spinner mTasAntMIMO2;
    private Spinner mTasAntPrx;
    /* access modifiers changed from: private */
    public Spinner mTasAntStatesSpinnerRx;
    /* access modifiers changed from: private */
    public TextView mTasAntStatesSpinnerRxView;
    /* access modifiers changed from: private */
    public Spinner mTasAntStatesSpinnerTx;
    /* access modifiers changed from: private */
    public TextView mTasAntStatesSpinnerTxView;
    private TextView mTasAntSwitchLabel;
    /* access modifiers changed from: private */
    public TextView mTasAntTest;
    private Spinner mTasAntTx0;
    /* access modifiers changed from: private */
    public Spinner mTasAntTx1;
    private EditText mTasBandEdit;
    /* access modifiers changed from: private */
    public Spinner mTasRatSpinner;
    /* access modifiers changed from: private */
    public TextView mTextViewHint;
    /* access modifiers changed from: private */
    public TextView mTextViewTx1;
    private String[] ratMapping = {"", "0", "1", "4", "3", "5", "4"};

    /* access modifiers changed from: package-private */
    public int parseAntStaus(String info) {
        try {
            String[] state = info.split(",");
            int states_tx = Integer.parseInt(state[1]);
            if (states_tx < 0 || states_tx >= 24) {
                this.mTasAntStatesSpinnerTx.setSelection(24);
            } else {
                this.mTasAntStatesSpinnerTx.setSelection(states_tx);
            }
            if (state.length <= 2) {
                return 0;
            }
            int states_rx = Integer.parseInt(state[2]);
            if (states_rx < 0 || states_rx >= 24) {
                this.mTasAntStatesSpinnerRx.setSelection(24);
                return 0;
            }
            this.mTasAntStatesSpinnerRx.setSelection(states_rx);
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
                Elog.d("ForceAnt97", "value = " + value);
            }
            if (index[1].equals("255")) {
                this.mTasAntTx0.setSelection(10);
            } else {
                this.mTasAntTx0.setSelection(Integer.parseInt(index[1]));
            }
            if (index[2].equals("255")) {
                this.mTasAntTx1.setSelection(10);
            } else {
                this.mTasAntTx1.setSelection(Integer.parseInt(index[2]));
            }
            if (index[3].equals("255")) {
                this.mTasAntPrx.setSelection(10);
            } else {
                this.mTasAntPrx.setSelection(Integer.parseInt(index[3]));
            }
            if (index[4].equals("255")) {
                this.mTasAntDrx.setSelection(10);
            } else {
                this.mTasAntDrx.setSelection(Integer.parseInt(index[4]));
            }
            if (index[5].equals("255")) {
                this.mTasAntMIMO1.setSelection(10);
            } else {
                this.mTasAntMIMO1.setSelection(Integer.parseInt(index[5]));
            }
            if (index[6].equals("255")) {
                this.mTasAntMIMO2.setSelection(10);
            } else {
                this.mTasAntMIMO2.setSelection(Integer.parseInt(index[6]));
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.force_tx_97);
        this.mModemType = ModemCategory.getModemType();
        this.mQueryMode = getIntent().getStringExtra("MODE");
        Elog.d("ForceAnt97", "MODE = " + this.mQueryMode);
        this.mForceAntModeSpinner = (Spinner) findViewById(R.id.force_ant_mode);
        this.mTasRatSpinner = (Spinner) findViewById(R.id.tas_rat);
        this.mTasBandEdit = (EditText) findViewById(R.id.tas_band);
        this.mTasAntStatesSpinnerTxView = (TextView) findViewById(R.id.tas_ant_states_tx_view);
        this.mTasAntStatesSpinnerRxView = (TextView) findViewById(R.id.tas_ant_states_rx_view);
        this.mTextViewTx1 = (TextView) findViewById(R.id.TextViewTx1);
        this.mTextViewHint = (TextView) findViewById(R.id.tas_ant_hint);
        this.mTasAntStatesSpinnerTx = (Spinner) findViewById(R.id.tas_ant_states_tx);
        this.mTasAntStatesSpinnerRx = (Spinner) findViewById(R.id.tas_ant_states_rx);
        this.mQueryIndexOrStatesBtn = (Button) findViewById(R.id.get_ant_index_states);
        this.mEnableStatesBtnBand = (Button) findViewById(R.id.enable_ant_states);
        this.mEnableStatesBtnRet = (Button) findViewById(R.id.enable_ant_states_all);
        this.mAntIndexTxTableRow = (TableRow) findViewById(R.id.TableRowTx);
        this.mAntIndexMIMOTableRow = (TableRow) findViewById(R.id.TableRowMIM0);
        this.mTasAntTx0 = (Spinner) findViewById(R.id.tas_ant_tx0);
        this.mTasAntTx1 = (Spinner) findViewById(R.id.tas_ant_tx1);
        this.mTasAntPrx = (Spinner) findViewById(R.id.tas_ant_tx_prx);
        this.mTasAntDrx = (Spinner) findViewById(R.id.tas_ant_drx);
        this.mTasAntMIMO1 = (Spinner) findViewById(R.id.tas_ant_mimo1);
        this.mTasAntMIMO2 = (Spinner) findViewById(R.id.tas_ant_mimo2);
        this.mTasAntTest = (TextView) findViewById(R.id.tas_ant_test);
        this.mTasAntSwitchLabel = (TextView) findViewById(R.id.Ant_switch_label);
        this.mQueryIndexOrStatesBtn.setOnClickListener(this);
        this.mEnableStatesBtnBand.setOnClickListener(this);
        this.mEnableStatesBtnRet.setOnClickListener(this);
        this.mForceAntModeSpinner.setOnItemSelectedListener(this.mSpinnerListener);
        this.mTasRatSpinner.setOnItemSelectedListener(this.mSpinnerListener);
        this.mTasAntStatesSpinnerTx.setOnItemSelectedListener(this.mSpinnerListener);
        this.mTasAntStatesSpinnerRx.setOnItemSelectedListener(this.mSpinnerListener);
    }

    public void onResume() {
        super.onResume();
        Elog.d("ForceAnt97", "onResume");
        getTasSettingStates();
        this.mForceAntModeSpinner.setSelection(mtasMode, true);
        this.mTasRatSpinner.setSelection(mtasRat - 1, true);
        if (this.mQueryMode.equals("by_states")) {
            this.mTasAntSwitchLabel.setText(getString(R.string.get_ant_index));
            this.mQueryIndexOrStatesBtn.setText(getString(R.string.get_ant_index));
            mTasModeEnableStates = 1;
            this.mTasAntTx0.setEnabled(false);
            this.mTasAntTx1.setEnabled(false);
            this.mTasAntPrx.setEnabled(false);
            this.mTasAntDrx.setEnabled(false);
            this.mTasAntMIMO1.setEnabled(false);
            this.mTasAntMIMO2.setEnabled(false);
            this.mTasAntStatesSpinnerTx.setEnabled(true);
            this.mTasAntStatesSpinnerRx.setEnabled(true);
        } else if (this.mQueryMode.equals("by_index")) {
            this.mTasAntSwitchLabel.setText(getString(R.string.get_ant_states));
            this.mQueryIndexOrStatesBtn.setText(getString(R.string.get_ant_states));
            mTasModeEnableStates = 2;
            this.mTasAntTx0.setEnabled(true);
            this.mTasAntTx1.setEnabled(true);
            this.mTasAntPrx.setEnabled(true);
            this.mTasAntDrx.setEnabled(true);
            this.mTasAntMIMO1.setEnabled(true);
            this.mTasAntMIMO2.setEnabled(true);
            this.mTasAntStatesSpinnerTx.setEnabled(false);
            this.mTasAntStatesSpinnerRx.setEnabled(false);
        }
        if (this.mModemType == 1) {
            this.ratMapping[2] = "1";
            Elog.d("ForceAnt97", "FDD");
            return;
        }
        this.ratMapping[2] = "2";
        Elog.d("ForceAnt97", "tdd");
    }

    public void onClick(View arg0) {
        if (arg0 == this.mEnableStatesBtnBand) {
            Elog.d("ForceAnt97", "Set ANT state click by band");
            mTasModeEnableStates = 0;
            mtasNvram = "false";
        } else if (arg0 == this.mEnableStatesBtnRet) {
            Elog.d("ForceAnt97", "Set ANT state click by ret");
            mTasModeEnableStates = 1;
            mtasNvram = "true";
        } else if (arg0 == this.mQueryIndexOrStatesBtn) {
            if (this.mQueryMode.equals("by_states")) {
                mTasModeEnableStates = 2;
                Elog.d("ForceAnt97", "Query ANT index by state click");
            } else {
                mTasModeEnableStates = 3;
                Elog.d("ForceAnt97", "Query ANT state by index click");
            }
        }
        if (mTasModeEnableStates == 1) {
            showDialog(1);
        } else {
            execTheCmd();
        }
    }

    public void execTheCmd() {
        String tasBand = "";
        int i = mTasModeEnableStates;
        if (i == 0 || i == 2 || i == 3) {
            mtasBand = this.mTasBandEdit.getText().toString();
            tasBand = mtasBand;
            if (tasBand.equals("")) {
                EmUtils.showToast("By band mode,the band value should not be empty");
                return;
            }
        }
        String tasStatesTx = mtasStatesTx;
        String tasStatesRx = mtasStatesRx;
        int selectedItemPosition = this.mForceAntModeSpinner.getSelectedItemPosition();
        mtasMode = selectedItemPosition;
        if (selectedItemPosition == 0 || selectedItemPosition == 2) {
            tasStatesTx = "";
            tasStatesRx = "";
        }
        String tasNvram = mtasNvram.equals("true") ? "1" : "0";
        Elog.d("ForceAnt97", "mtasMode = " + mtasMode + "," + mModeArray[mtasMode]);
        Elog.d("ForceAnt97", "mtasRat = " + mtasRat + "," + mRatArray[mtasRat + -1]);
        StringBuilder sb = new StringBuilder();
        sb.append("mtasNvram = ");
        sb.append(tasNvram);
        Elog.d("ForceAnt97", sb.toString());
        Elog.d("ForceAnt97", "tasBand = " + tasBand);
        Elog.d("ForceAnt97", "mtasStatesTx = " + tasStatesTx);
        Elog.d("ForceAnt97", "mtasStatesRx = " + tasStatesRx);
        writeTasSettingStates();
        int i2 = mTasModeEnableStates;
        if (i2 == 2) {
            queryIndexByStates(mtasRat, tasBand, tasStatesTx, tasStatesRx);
        } else if (i2 == 3) {
            queryStatesByIndex(mtasRat, tasBand);
        } else {
            setTasForceIdx(mtasMode, mtasRat, tasStatesTx, tasStatesRx, tasBand, tasNvram);
        }
    }

    public void setTasForceIdx(int mode, int rat, String tasStatesTx, String tasStatesRx, String band, String nvram_write) {
        String[] cmd = new String[2];
        if (rat == 5) {
            if (mTasModeEnableStates == 0) {
                cmd[0] = "AT+EGMC=1,\"NrForceTxRx\"," + (mode + 3) + "," + tasStatesTx + "," + tasStatesRx + "," + band;
            } else {
                cmd[0] = "AT+EGMC=1,\"NrForceTxRx\"," + mode + "," + tasStatesTx + "," + tasStatesRx + "," + nvram_write;
            }
            cmd[1] = "+EGMC:";
        } else if (rat != 6) {
            if (mTasModeEnableStates == 0) {
                cmd[0] = "AT+ETXANT=" + (mode + 3) + "," + rat + "," + tasStatesTx + "," + band;
            } else {
                cmd[0] = "AT+ETXANT=" + mode + "," + rat + "," + tasStatesTx + "," + band + "," + nvram_write;
            }
            cmd[1] = "+ETXANT:";
        } else if (mode == 2) {
            EmUtils.showToast("LTE TDD doesn't support mode read, please select again");
            return;
        } else {
            cmd[0] = "AT+EGMC=1,\"lte_tdd_tx_rx\"," + mode + "," + tasStatesTx + "," + tasStatesRx;
            cmd[1] = "+EGMC:";
        }
        Elog.d("ForceAnt97", "sendAtCommand: " + cmd[0]);
        this.mTasAntTest.setText(cmd[0]);
        senAtCommand(cmd, mode);
    }

    /* access modifiers changed from: package-private */
    public boolean isTasStatesValid(Spinner tasAntSpinner) {
        if (tasAntSpinner.getSelectedItemPosition() == 10) {
            return false;
        }
        return true;
    }

    public void queryStatesByIndex(int rat, String band) {
        int tx_count;
        int tx_count2;
        int tx_count3;
        int tx_count4;
        int tasAntDrx;
        int tx_count5;
        String str = band;
        String[] cmd = new String[2];
        String[] configureName = {"\"gsm_utas\"", "\"wcdma_utas\"", "\"t_utas_query\"", "\"c2k_utas\"", "\"l_utas_query\"", "\"n_utas_query\""};
        int ratIndex = Integer.parseInt(this.ratMapping[rat]);
        if (ratIndex == 0) {
            int tasAntPrx = getAnt(this.mTasAntPrx);
            if (!isTasStatesValid(this.mTasAntDrx)) {
                tx_count5 = 1;
                tasAntDrx = 0;
            } else {
                tx_count5 = 2;
                tasAntDrx = getAnt(this.mTasAntDrx);
            }
            cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + "," + str + "," + tx_count5 + ",";
            if (tx_count5 == 1) {
                cmd[0] = cmd[0] + tasAntPrx + "," + tasAntPrx;
            } else {
                cmd[0] = cmd[0] + tasAntPrx + "," + tasAntDrx + "," + tasAntPrx;
            }
        } else if (ratIndex == 1) {
            int tasAntPrx2 = getAnt(this.mTasAntPrx);
            int tasAntDrx2 = getAnt(this.mTasAntDrx);
            if (!isTasStatesValid(this.mTasAntDrx)) {
                tx_count4 = 1;
            } else {
                tx_count4 = 2;
            }
            cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + ",1," + str + "," + tx_count4 + ",";
            cmd[0] = cmd[0] + tasAntPrx2 + "," + tasAntDrx2 + "," + 0 + "," + 0 + "," + tasAntPrx2;
        } else if (ratIndex == 2) {
            int tasAntPrx3 = getAnt(this.mTasAntPrx);
            if (isTasStatesValid(this.mTasAntDrx)) {
                EmUtils.showToast("The tdscdma only support 1 path： prx trx");
                return;
            }
            cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + "," + str + "," + 1 + "," + tasAntPrx3;
        } else if (ratIndex == 3) {
            int tasAntPrx4 = getAnt(this.mTasAntPrx);
            int tasAntDrx3 = getAnt(this.mTasAntDrx);
            if (!isTasStatesValid(this.mTasAntDrx)) {
                tx_count3 = 1;
            } else {
                tx_count3 = 2;
            }
            cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + "," + str + "," + tx_count3 + ",";
            StringBuilder sb = new StringBuilder();
            sb.append(cmd[0]);
            sb.append(tasAntPrx4);
            sb.append(",");
            sb.append(tasAntDrx3);
            cmd[0] = sb.toString();
        } else if (ratIndex == 4) {
            int tasAntTx0 = getAnt(this.mTasAntTx0);
            int tasAntTx1 = getAnt(this.mTasAntTx1);
            int tasAntPrx5 = getAnt(this.mTasAntPrx);
            int tasAntDrx4 = getAnt(this.mTasAntDrx);
            int tasAntMIMO1 = getAnt(this.mTasAntMIMO1);
            int tasAntMIMO2 = getAnt(this.mTasAntMIMO2);
            if (!isTasStatesValid(this.mTasAntDrx)) {
                EmUtils.showToast("The mTasAntDrx should not be 255 or null in lte mode");
                return;
            }
            if (isTasStatesValid(this.mTasAntMIMO1) && isTasStatesValid(this.mTasAntMIMO2)) {
                tx_count2 = 4;
            } else if (isTasStatesValid(this.mTasAntMIMO1) || isTasStatesValid(this.mTasAntMIMO2)) {
                EmUtils.showToast("TasAntMimo1 or TasAntMimo2 should be both 255 or nor");
                return;
            } else {
                tx_count2 = 2;
            }
            cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + ",1," + str + "," + tx_count2 + ",";
            cmd[0] = cmd[0] + tasAntPrx5 + "," + tasAntDrx4 + "," + tasAntMIMO1 + "," + tasAntMIMO2 + "," + tasAntTx0;
        } else if (ratIndex == 5) {
            int tasAntTx02 = getAnt(this.mTasAntTx0);
            int tasAntTx12 = getAnt(this.mTasAntTx1);
            int tasAntPrx6 = getAnt(this.mTasAntPrx);
            int tasAntDrx5 = getAnt(this.mTasAntDrx);
            int tasAntMIMO12 = getAnt(this.mTasAntMIMO1);
            int tasAntMIMO22 = getAnt(this.mTasAntMIMO2);
            if (!isTasStatesValid(this.mTasAntDrx)) {
                EmUtils.showToast("The mTasAntDrx should not be 255 or null in lte mode");
                return;
            }
            if (isTasStatesValid(this.mTasAntMIMO1) && isTasStatesValid(this.mTasAntMIMO2)) {
                tx_count = 4;
            } else if (isTasStatesValid(this.mTasAntMIMO1) || isTasStatesValid(this.mTasAntMIMO2)) {
                EmUtils.showToast("TasAntMimo1 or TasAntMimo2 should be both 255 or nor");
                return;
            } else {
                tx_count = 2;
            }
            cmd[0] = "AT+EGMC=1," + configureName[ratIndex] + ",1," + str + "," + tx_count + ",";
            cmd[0] = cmd[0] + tasAntPrx6 + "," + tasAntDrx5 + "," + tasAntMIMO12 + "," + tasAntMIMO22 + "," + tasAntTx02 + "," + tasAntTx12;
        }
        cmd[1] = "+EGMC:";
        Elog.d("ForceAnt97", "sendAtCommand: " + cmd[0]);
        this.mTasAntTest.setText(cmd[0]);
        senAtCommand(cmd, 7);
    }

    private int getAnt(Spinner antSpinner) {
        int ant = antSpinner.getSelectedItemPosition();
        if (ant == 10) {
            return 255;
        }
        return ant;
    }

    public void queryIndexByStates(int rat, String band, String tasStatesTx, String tasStatesRx) {
        String[] cmd = new String[2];
        if (!(rat == 5 || rat == 6)) {
            tasStatesRx = tasStatesTx;
        }
        cmd[0] = "AT+EGMC=1,\"utas_query_ant_port\"," + this.ratMapping[rat] + "," + band + "," + tasStatesTx + "," + tasStatesRx;
        cmd[1] = "+EGMC:";
        StringBuilder sb = new StringBuilder();
        sb.append("sendAtCommand: ");
        sb.append(cmd[0]);
        Elog.d("ForceAnt97", sb.toString());
        this.mTasAntTest.setText(cmd[0]);
        senAtCommand(cmd, 6);
    }

    public void senAtCommand(String[] atCommand, int msg) {
        EmUtils.invokeOemRilRequestStringsEm(atCommand, this.mATCmdHander.obtainMessage(msg));
    }

    public void getTasSettingStates() {
        SharedPreferences tasVersionSh = getSharedPreferences("ForceAnt97", 0);
        mtasMode = tasVersionSh.getInt(TAS_MODE, 0);
        mtasRat = tasVersionSh.getInt(TAS_RAT, 1);
        mtasNvram = tasVersionSh.getString(TAS_NVRAM, "true");
        mtasBand = tasVersionSh.getString(TAS_BAND, "1");
        Elog.d("ForceAnt97", "\ngetTasSettingStates: ");
        Elog.d("ForceAnt97", "mtasMode = " + mtasMode);
        Elog.d("ForceAnt97", "mtasRat = " + mtasRat);
        Elog.d("ForceAnt97", "mtasNvram = " + mtasNvram);
        Elog.d("ForceAnt97", "mtasBand = " + mtasBand);
    }

    public void writeTasSettingStates() {
        SharedPreferences.Editor editor = getSharedPreferences("ForceAnt97", 0).edit();
        editor.putInt(TAS_MODE, mtasMode);
        editor.putInt(TAS_RAT, mtasRat);
        editor.putString(TAS_NVRAM, mtasNvram);
        editor.putString(TAS_BAND, mtasBand);
        editor.commit();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this).setTitle("Warning").setMessage("Enable force state for all bands and write force state to NVRAM\n").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {
                            ForceTx97.this.execTheCmd();
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create();
            default:
                return super.onCreateDialog(id);
        }
    }
}
