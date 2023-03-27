package com.mediatek.engineermode.forceant;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class ForceTx extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
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
    private static final String SHREDPRE_NAME = "ForceTx";
    private static final String TAG = "ForceTx";
    private static final String TAS_BAND = "TasBand";
    private static final String TAS_DPDT = "TasDpdt";
    private static final String TAS_MODE = "TasMode";
    private static final String TAS_NVRAM = "TasNvram";
    private static final String TAS_RAT = "TasRat";
    private static final String TAS_TYPE = "TasType";
    private static final String TAS_VERSION = "TasVersion";
    private static String[] mDpdtArray = {"signal dpdt", "double dpdt"};
    private static int mForceDpdt = 0;
    private static int mForceType = 0;
    private static String[] mModeArray = {"OFF", "ON", "QUERY"};
    private static String[] mRatArray = {"GSM", "UTMS", "LTE", "C2K"};
    private static int[][] mTasDpdt1 = {new int[]{0, 2}, new int[]{1, 3}, new int[]{1, 3}, new int[]{1, 3}};
    private static String[][] mTasDpdt1Label = {new String[]{"LANT  X", "UANT  X"}, new String[]{"LANT  UANT", "UANT LANT"}, new String[]{"LANT X", "UANT X", "LANT(') X"}, new String[]{"LANT  UANT", "UANT  LANT", "LANT(') UANT", "UANT LANT(') "}, new String[]{"LANT  X  X  X", "UANT  X  X  X"}, new String[]{"LANT  X  UANT  X", "UANT  X  UANT  X"}, new String[]{"LANT  LANT  UANT  UANT", "LANT  UANT  UANT  LANT", "UANT  LANT  LANT  UANT", "UANT  UANT  LANT  LANT"}, new String[]{"LANT  X  UANT  X", "UANT  X  LANT  X", "LANT(')  X  UANT  X", "UANT  X  LANT(')  X"}, new String[]{"LANT  LANT  UANT  UANT", "LANT  UANT  UANT  LANT", "UANT  LANT  LANT  UANT", "UANT  UANT  LANT  LANT", "LANT(')  LANT(')  UANT  UANT", "LANT(')  UANT  UANT  LANT(')", "UANT  LANT(')  LANT(')  UANT", "UANT  UANT  LANT(')  LANT(')"}};
    private static int[][] mTasDpdt2 = {new int[]{4, 2}, new int[]{5, 7}, new int[]{6, 8}, new int[]{5, 7}};
    private static String[] mTypeArray = {"by rat", "by band"};
    private static String[] mVersionArray = {"V1.0", "V2.0"};
    private static String mtasBand = "1";
    private static int mtasMode = 0;
    private static String mtasNvram = "0";
    /* access modifiers changed from: private */
    public static int mtasRat = 1;
    /* access modifiers changed from: private */
    public static int mtasVersion = 0;
    private static String mtasidx = "1";
    private final Handler mATCmdHander = new Handler() {
        public void handleMessage(Message msg) {
            int value;
            AsyncResult ar = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 0:
                case 1:
                case 3:
                case 4:
                    if (ar.exception != null) {
                        Toast.makeText(ForceTx.this, " AT cmd failed.", 1).show();
                        return;
                    } else {
                        Toast.makeText(ForceTx.this, " AT cmd successfully.", 1).show();
                        return;
                    }
                case 2:
                case 5:
                    if (ar.exception == null) {
                        String[] receiveDate = (String[]) ar.result;
                        int[] iArr = new int[4];
                        if (receiveDate == null) {
                            Toast.makeText(ForceTx.this, "Warning: Received data is null!", 0).show();
                            return;
                        }
                        Elog.d("ForceTx", "receiveDate[0] = " + receiveDate[0]);
                        try {
                            value = Integer.parseInt(receiveDate[0].split(",")[1]);
                        } catch (Exception e) {
                            value = 0;
                        }
                        Elog.d("ForceTx", "value = " + value);
                        if (value != 255) {
                            ForceTx.this.queryTasIdxLabels();
                            if (value < ForceTx.this.mIdxSpinner.getCount()) {
                                ForceTx.this.mIdxSpinner.setSelection(value);
                                return;
                            }
                            String msg1 = "The return idx == " + value + " not match the version or dpdt ";
                            Toast.makeText(ForceTx.this, msg1, 1).show();
                            Elog.e("ForceTx", msg1);
                            return;
                        }
                        ForceTx.this.mTasAntIdxEdit.setText("disabled");
                        return;
                    }
                    Toast.makeText(ForceTx.this, " AT cmd failed.", 1).show();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public Spinner mForceAntSpinner;
    /* access modifiers changed from: private */
    public Spinner mIdxSpinner;
    private RadioGroup mRGForceDpdt = null;
    private RadioGroup mRGForceType = null;
    private Button mSetButton;
    private AdapterView.OnItemSelectedListener mSpinnerListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
            int i = 2;
            if (parent == ForceTx.this.mForceAntSpinner) {
                Elog.d("ForceTx", "mForceAntModeSpinner changed, pos = " + pos);
                if (!(pos == 0 || pos == 1 || pos != 2)) {
                    ForceTx.this.mTasAntIdxEdit.setText("");
                    return;
                }
            } else if (parent == ForceTx.this.mTasVerSpinner) {
                Elog.d("ForceTx", "mtasVersion changed, pos = " + pos);
                if (pos == 0) {
                    i = 1;
                }
                int unused = ForceTx.mtasVersion = i;
            } else if (parent == ForceTx.this.mTasRatSpinner) {
                Elog.d("ForceTx", "mTasRatSpinner changed, pos = " + pos);
                int unused2 = ForceTx.mtasRat = pos + 1;
            } else if (parent == ForceTx.this.mIdxSpinner) {
                Elog.d("ForceTx", "mIdxSpinner changed, pos = " + pos);
                ForceTx.this.mTasAntIdxEdit.setText(pos + "");
                return;
            }
            ForceTx.this.queryTasIdxLabels();
        }

        public void onNothingSelected(AdapterView parent) {
        }
    };
    /* access modifiers changed from: private */
    public EditText mTasAntIdxEdit;
    private TextView mTasAntIdxView;
    private EditText mTasBandEdit;
    private TextView mTasBandText;
    private TextView mTasNvramText;
    /* access modifiers changed from: private */
    public Spinner mTasRatSpinner;
    /* access modifiers changed from: private */
    public Spinner mTasVerSpinner;
    private Spinner mTasnvramSpinner;
    private ArrayAdapter<String> mVersionAdatper1;
    private RadioButton radioBtnForceDpdtDouble = null;
    private RadioButton radioBtnForceDpdtSingle = null;
    private RadioButton radioBtnForceRat = null;
    private RadioButton radioBtnforceBand = null;

    public void queryTasIdxLabels() {
        String[] values;
        int status_index;
        this.mVersionAdatper1.clear();
        Elog.d("ForceTx", "\nupdate TAS Idx: ");
        Elog.d("ForceTx", "mtasRat = " + mtasRat);
        Elog.d("ForceTx", "mtasVersion = " + mtasVersion);
        if (mForceDpdt == 0) {
            status_index = mTasDpdt1[mtasRat - 1][mtasVersion - 1];
            values = mTasDpdt1Label[status_index];
        } else {
            status_index = mTasDpdt2[mtasRat - 1][mtasVersion - 1];
            values = mTasDpdt1Label[status_index];
        }
        Elog.d("ForceTx", "status_index = " + status_index);
        if (status_index > 3) {
            this.mTasAntIdxView.setText("LM_Main H_Main LM_DRX H_DRX");
        } else {
            this.mTasAntIdxView.setText("LMH_Main LMH_DRX");
        }
        for (int i = 0; i < values.length; i++) {
            Elog.d("ForceTx", "mTasDpdt1Label = " + values[i]);
        }
        this.mVersionAdatper1.addAll(values);
        this.mIdxSpinner.setAdapter(this.mVersionAdatper1);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.force_tx);
        this.mTasRatSpinner = (Spinner) findViewById(R.id.tas_rat);
        this.mTasnvramSpinner = (Spinner) findViewById(R.id.tas_nvram);
        this.mTasBandEdit = (EditText) findViewById(R.id.tas_band);
        this.mTasBandText = (TextView) findViewById(R.id.tas_band_text);
        this.mTasNvramText = (TextView) findViewById(R.id.tas_nvram_text);
        EditText editText = (EditText) findViewById(R.id.tas_ant_idx);
        this.mTasAntIdxEdit = editText;
        editText.setEnabled(false);
        this.mForceAntSpinner = (Spinner) findViewById(R.id.force_ant1);
        this.mTasVerSpinner = (Spinner) findViewById(R.id.tas_ver1);
        this.mIdxSpinner = (Spinner) findViewById(R.id.dpdt1_idx);
        this.mTasAntIdxView = (TextView) findViewById(R.id.tas_ant_idx_View);
        Button button = (Button) findViewById(R.id.force_tx_set);
        this.mSetButton = button;
        button.setOnClickListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048);
        this.mVersionAdatper1 = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mIdxSpinner.setAdapter(this.mVersionAdatper1);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.force_type);
        this.mRGForceType = radioGroup;
        radioGroup.setOnCheckedChangeListener(this);
        this.radioBtnForceRat = (RadioButton) findViewById(R.id.force_rat);
        this.radioBtnforceBand = (RadioButton) findViewById(R.id.force_band);
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.force_dpdt);
        this.mRGForceDpdt = radioGroup2;
        radioGroup2.setOnCheckedChangeListener(this);
        this.radioBtnForceDpdtSingle = (RadioButton) findViewById(R.id.force_dpdt_single);
        this.radioBtnForceDpdtDouble = (RadioButton) findViewById(R.id.force_dpdt_double);
    }

    public void onResume() {
        super.onResume();
        this.mForceAntSpinner.setOnItemSelectedListener(this.mSpinnerListener);
        this.mTasVerSpinner.setOnItemSelectedListener(this.mSpinnerListener);
        this.mTasRatSpinner.setOnItemSelectedListener(this.mSpinnerListener);
        this.mIdxSpinner.setOnItemSelectedListener(this.mSpinnerListener);
        getTasSettingStatus();
        this.mVersionAdatper1.clear();
        this.mVersionAdatper1.addAll(mTasDpdt1Label[mTasDpdt1[mtasRat - 1][mtasVersion - 1]]);
        this.mIdxSpinner.setAdapter(this.mVersionAdatper1);
        if (mForceType == 0) {
            this.radioBtnForceRat.setChecked(true);
        } else {
            this.radioBtnforceBand.setChecked(true);
        }
        if (mForceDpdt == 0) {
            this.radioBtnForceDpdtSingle.setChecked(true);
        } else {
            this.radioBtnForceDpdtDouble.setChecked(true);
        }
        Spinner spinner = this.mForceAntSpinner;
        int i = mtasMode;
        if (i >= 3) {
            i -= 3;
        }
        spinner.setSelection(i, true);
        this.mTasVerSpinner.setSelection(mtasVersion - 1, true);
        this.mTasRatSpinner.setSelection(mtasRat - 1, true);
        this.mTasnvramSpinner.setSelection(Integer.parseInt(mtasNvram), true);
        this.mTasBandEdit.setText(mtasBand);
        if (!FeatureSupport.is93ModemAndAbove()) {
            this.mRGForceType.setVisibility(8);
            this.mTasBandEdit.setVisibility(8);
            this.mTasBandText.setVisibility(8);
            this.mTasNvramText.setVisibility(8);
            this.mTasnvramSpinner.setVisibility(8);
            this.radioBtnForceRat.setChecked(true);
        }
    }

    public void onClick(View arg0) {
        if (arg0 == this.mSetButton) {
            Elog.d("ForceTx", "forceAnt set: ");
            mtasMode = this.mForceAntSpinner.getSelectedItemPosition() + (mForceType == 1 ? 3 : 0);
            mtasBand = this.mTasBandEdit.getText().toString();
            mtasidx = this.mTasAntIdxEdit.getText().toString();
            mtasNvram = this.mTasnvramSpinner.getSelectedItemPosition() == 0 ? "0" : "1";
            writeTasSettingStatus();
            int i = mtasMode;
            String str = "";
            if (i == 0 || i == 2 || i == 3 || i == 5) {
                mtasidx = str;
                mtasNvram = str;
            }
            int i2 = mForceType;
            mtasBand = i2 == 1 ? mtasBand : str;
            if (i2 != 1) {
                str = mtasNvram;
            }
            mtasNvram = str;
            Elog.d("ForceTx", "mForceType = " + mForceType + "," + mTypeArray[mForceType]);
            Elog.d("ForceTx", "mForceDpdt = " + mForceDpdt + "," + mDpdtArray[mForceDpdt]);
            StringBuilder sb = new StringBuilder();
            sb.append("mtasMode = ");
            sb.append(mtasMode);
            sb.append(",");
            String[] strArr = mModeArray;
            int i3 = mtasMode;
            if (i3 >= 3) {
                i3 -= 3;
            }
            sb.append(strArr[i3]);
            Elog.d("ForceTx", sb.toString());
            Elog.d("ForceTx", "mtasVersion = " + mtasVersion + "," + mVersionArray[mtasVersion - 1]);
            Elog.d("ForceTx", "mtasRat = " + mtasRat + "," + mRatArray[mtasRat - 1]);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("mtasNvram = ");
            sb2.append(mtasNvram);
            Elog.d("ForceTx", sb2.toString());
            Elog.d("ForceTx", "mtasBand = " + mtasBand);
            setTasForceIdx(mtasMode, mtasRat, mtasidx, mtasBand, mtasNvram);
        }
    }

    public void getTasSettingStatus() {
        SharedPreferences tasVersionSh = getSharedPreferences("ForceTx", 0);
        mForceType = tasVersionSh.getInt(TAS_TYPE, 0);
        mForceDpdt = tasVersionSh.getInt(TAS_DPDT, 0);
        mtasMode = tasVersionSh.getInt(TAS_MODE, 0);
        mtasVersion = tasVersionSh.getInt(TAS_VERSION, 1);
        mtasRat = tasVersionSh.getInt(TAS_RAT, 1);
        mtasNvram = tasVersionSh.getString(TAS_NVRAM, "0");
        mtasBand = tasVersionSh.getString(TAS_BAND, "1");
        Elog.d("ForceTx", "\ngetTasSettingStatus: ");
        Elog.d("ForceTx", "mForceType = " + mForceType);
        Elog.d("ForceTx", "mForceDpdt = " + mForceDpdt);
        Elog.d("ForceTx", "mtasMode = " + mtasMode);
        Elog.d("ForceTx", "mtasVersion = " + mtasVersion);
        Elog.d("ForceTx", "mtasRat = " + mtasRat);
        Elog.d("ForceTx", "mtasNvram = " + mtasNvram);
        Elog.d("ForceTx", "mtasBand = " + mtasBand);
    }

    public void writeTasSettingStatus() {
        SharedPreferences.Editor editor = getSharedPreferences("ForceTx", 0).edit();
        editor.putInt(TAS_TYPE, mForceType);
        editor.putInt(TAS_DPDT, mForceDpdt);
        editor.putInt(TAS_MODE, mtasMode);
        editor.putInt(TAS_VERSION, mtasVersion);
        editor.putInt(TAS_RAT, mtasRat);
        editor.putString(TAS_NVRAM, mtasNvram);
        editor.putString(TAS_BAND, mtasBand);
        editor.commit();
    }

    public void setTasForceIdx(int mode, int rat, String antenna_idx, String band, String nvram_write) {
        String[] cmd = new String[2];
        if (FeatureSupport.is93ModemAndAbove()) {
            if (mForceType == 1) {
                cmd[0] = "AT+ETXANT=" + mode + "," + rat + "," + antenna_idx + "," + band;
            } else {
                cmd[0] = "AT+ETXANT=" + mode + "," + rat + "," + antenna_idx + "," + band + "," + nvram_write;
            }
        } else if (antenna_idx.equals("")) {
            cmd[0] = "AT+ETXANT=" + mode + "," + rat;
        } else {
            cmd[0] = "AT+ETXANT=" + mode + "," + rat + "," + antenna_idx;
        }
        cmd[1] = "+ETXANT:";
        if (rat == 4) {
            String[] cmd_s = ModemCategory.getCdmaCmdArr(new String[]{cmd[0], cmd[1], "DESTRILD:C2K"});
            Log.d("ForceTx", "sendAtCommand: " + cmd_s[0] + ",cmd_s.length = " + cmd_s.length);
            sendCdmaAtCommand(cmd_s, mode);
            return;
        }
        Log.d("ForceTx", "sendAtCommand: " + cmd[0]);
        sendLteAtCommand(cmd, mode);
    }

    public void sendLteAtCommand(String[] atCommand, int msg) {
        EmUtils.invokeOemRilRequestStringsEm(atCommand, this.mATCmdHander.obtainMessage(msg));
    }

    public void sendCdmaAtCommand(String[] atCommand, int msg) {
        EmUtils.invokeOemRilRequestStringsEm(true, atCommand, this.mATCmdHander.obtainMessage(msg));
    }

    public void onCheckedChanged(RadioGroup arg0, int checkedId) {
        if (checkedId == R.id.force_rat) {
            mForceType = 0;
            this.mTasBandEdit.setVisibility(8);
            this.mTasBandText.setVisibility(8);
            this.mTasnvramSpinner.setVisibility(0);
            this.mTasNvramText.setVisibility(0);
        } else if (checkedId == R.id.force_band) {
            mForceType = 1;
            this.mTasBandEdit.setVisibility(0);
            this.mTasBandText.setVisibility(0);
            this.mTasnvramSpinner.setVisibility(8);
            this.mTasNvramText.setVisibility(8);
        } else if (checkedId == R.id.force_dpdt_single) {
            mForceDpdt = 0;
            queryTasIdxLabels();
        } else if (checkedId == R.id.force_dpdt_double) {
            mForceDpdt = 1;
            queryTasIdxLabels();
        }
    }
}
