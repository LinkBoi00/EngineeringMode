package com.mediatek.engineermode.anttunerdebug;

import android.app.Activity;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.List;

public class AntTunerDebugDAT extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final int FEATRUE_IDX = 0;
    public static final int OP_DAT_READ = 0;
    public static final int OP_DAT_WRITE1 = 1;
    public static final int OP_DAT_WRITE2 = 2;
    public static final String TAG = "AntTunerDebugDAT";
    private ArrayAdapter<String> adapterPattern = null;
    /* access modifiers changed from: private */
    public String[] cmd;
    /* access modifiers changed from: private */
    public int datIndex = 0;
    private List<String> datIndexList = new ArrayList<String>() {
        {
            add("0");
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
        }
    };
    private boolean enableDat = true;
    private RadioGroup groupDatStatus;
    private Handler mATHandler = new Handler() {
        private String[] mReturnData = new String[2];

        public void handleMessage(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            if (msg.what == 0) {
                if (ar.exception == null) {
                    this.mReturnData = (String[]) ar.result;
                    Elog.d(AntTunerDebugDAT.TAG, "DAT Index read successful.");
                    Elog.d(AntTunerDebugDAT.TAG, "mReturnData = " + this.mReturnData[0]);
                    return;
                }
                EmUtils.showToast("DAT Index read failed.");
                Elog.d(AntTunerDebugDAT.TAG, "DAT Index read failed.");
            } else if (msg.what == 1) {
                if (ar.exception == null) {
                    Elog.d(AntTunerDebugDAT.TAG, "DAT Index write successful for slot 1");
                    if (TelephonyManager.getDefault().getPhoneCount() > 1) {
                        AntTunerDebugDAT antTunerDebugDAT = AntTunerDebugDAT.this;
                        antTunerDebugDAT.sendAtCommand(1, antTunerDebugDAT.cmd, 2);
                        return;
                    }
                    return;
                }
                EmUtils.showToast("DAT Index write failed for slot 1");
                Elog.e(AntTunerDebugDAT.TAG, "DAT Index write failed for slot 1");
            } else if (msg.what != 2) {
            } else {
                if (ar.exception == null) {
                    Elog.d(AntTunerDebugDAT.TAG, "DAT Index write successful for slot 2");
                    return;
                }
                EmUtils.showToast("DAT Index write failed for slot 2");
                Elog.e(AntTunerDebugDAT.TAG, "DAT Index write failed for slot 2");
            }
        }
    };
    private Button mBtnDatWrite;
    private Spinner mSpDatIndex;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ant_tuner_debug_dat);
        Button button = (Button) findViewById(R.id.ant_tuner_debug_dat_write);
        this.mBtnDatWrite = button;
        button.setOnClickListener(this);
        this.mSpDatIndex = (Spinner) findViewById(R.id.ant_tuner_debug_dat_index);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.datIndexList);
        this.adapterPattern = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mSpDatIndex.setAdapter(this.adapterPattern);
        this.mSpDatIndex.setSelection(0);
        this.mSpDatIndex.setOnItemSelectedListener(new SpinnerSelectedListener());
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.dat_status_rgroup);
        this.groupDatStatus = radioGroup;
        radioGroup.setOnCheckedChangeListener(this);
    }

    public String[] prepareDatWriteATCmd() {
        String[] cmd2 = new String[2];
        if (!this.enableDat) {
            cmd2[0] = "AT+ERFIDX=0,-1";
        } else {
            cmd2[0] = "AT+ERFIDX=0," + this.datIndex;
        }
        cmd2[1] = "";
        return cmd2;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.dat_disable:
                this.adapterPattern.insert("-1", 0);
                this.mSpDatIndex.setSelection(0);
                this.mSpDatIndex.setSelected(false);
                this.mSpDatIndex.setEnabled(false);
                this.enableDat = false;
                return;
            case R.id.dat_enable:
                this.enableDat = true;
                this.adapterPattern.remove("-1");
                this.mSpDatIndex.setSelection(this.datIndex);
                this.mSpDatIndex.setSelected(true);
                this.mSpDatIndex.setEnabled(true);
                return;
            default:
                return;
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.ant_tuner_debug_dat_write:
                String[] prepareDatWriteATCmd = prepareDatWriteATCmd();
                this.cmd = prepareDatWriteATCmd;
                sendAtCommand(0, prepareDatWriteATCmd, 1);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void sendAtCommand(int sim_index, String[] command, int msg) {
        Elog.d(TAG, "sendAtCommand() " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(sim_index, command, this.mATHandler.obtainMessage(msg));
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        SpinnerSelectedListener() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            int unused = AntTunerDebugDAT.this.datIndex = arg2;
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
}
