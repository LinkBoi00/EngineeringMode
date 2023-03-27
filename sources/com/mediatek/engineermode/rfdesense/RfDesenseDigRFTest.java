package com.mediatek.engineermode.rfdesense;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.telephony.Phone;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RfDesenseDigRFTest extends Activity {
    private static final String AT_CMD_START = "AT+EGCMD=4896,1,\"04\"";
    private static final String AT_CMD_STOP = "AT+EGCMD=4896,1,\"05\"";
    private static final int CLOSE = 2;
    private static final int HINT = 1;
    public static final String KEY_PERIOD = "digrf_period";
    public static final String KEY_PERIOD_STATUS = "digrf_period_status";
    public static final String KEY_ROUND = "digrf_round";
    public static final String KEY_ROUND_STATUS = "digrf_round_status";
    public static final String KEY_STATE = "digrf_state";
    private static final int MSG_NW_RF_OFF = 6;
    public static final int MSG_TEST_PERIOD_END = 7;
    private static final int OPEN = 1;
    private static final int PAUSE = 3;
    private static final int SIM_CARD_INSERT = 2;
    private static final int START = 0;
    public static final int STATE_CLOSED = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_OPENED = 3;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_STARTED = 1;
    public static final String TAG = "RfDesenseDigRFTest";
    public static final String TEST_RESULT_TITLE = "Test Result:\n\n";
    private static final int UPDATE_BUTTON = 5;
    private static final int VRB_LENGTH_MAX = 0;
    private static final int VRB_LENGTH_MIN = 0;
    private static final int VRB_START_MAX = 0;
    private static final int VRB_START_MIN = 0;
    HashMap<Integer, Integer> TestPeriodMap = new HashMap<Integer, Integer>() {
        {
            put(0, 1);
            put(1, 10);
            put(2, 60);
            put(3, 180);
            put(4, 600);
            put(5, 1800);
        }
    };
    boolean isPeriod = false;
    boolean isRound = false;
    private Button mButtonPause;
    private Button mButtonStart;
    private int mCellCount = 1;
    private Handler mHandler = new Handler() {
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x004a, code lost:
            if (((android.os.AsyncResult) r6.obj).exception != null) goto L_0x0053;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x004c, code lost:
            r5.this$0.oneResult[1] = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0053, code lost:
            com.mediatek.engineermode.Elog.d(com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.TAG, "CLOSE Command failed.");
            r5.this$0.oneResult[1] = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x005e, code lost:
            com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.access$700(r5.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0074, code lost:
            if (((android.os.AsyncResult) r6.obj).exception != null) goto L_0x008b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x007c, code lost:
            if (com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.access$200(r5.this$0) == 2) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x007e, code lost:
            com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.access$202(r5.this$0, 3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0084, code lost:
            r5.this$0.oneResult[0] = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x008b, code lost:
            com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.access$1000(r5.this$0, "OPEN Command failed.");
            r5.this$0.oneResult[0] = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0098, code lost:
            r0 = r5.this$0;
            r0.startTimer(com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.access$1100(r0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r6) {
            /*
                r5 = this;
                int r0 = r6.what
                java.lang.String r1 = "RfDesenseDigRFTest"
                r2 = 2
                r3 = 1
                r4 = 0
                switch(r0) {
                    case 0: goto L_0x0064;
                    case 1: goto L_0x006e;
                    case 2: goto L_0x0044;
                    case 3: goto L_0x003a;
                    case 4: goto L_0x000a;
                    case 5: goto L_0x0034;
                    case 6: goto L_0x0024;
                    case 7: goto L_0x000c;
                    default: goto L_0x000a;
                }
            L_0x000a:
                goto L_0x00a2
            L_0x000c:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                boolean[] r0 = r0.oneResult
                boolean r0 = r0[r4]
                if (r0 == 0) goto L_0x001d
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                java.lang.String r1 = "AT+EGCMD=4896,1,\"05\""
                r0.sendAtCommand(r1, r2)
                goto L_0x00a2
            L_0x001d:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                r0.testRoundEnd()
                goto L_0x00a2
            L_0x0024:
                java.lang.String r0 = "The rf is off"
                com.mediatek.engineermode.Elog.d(r1, r0)
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                int unused = r0.mState = r4
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                r0.updateButtons()
                goto L_0x00a2
            L_0x0034:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                r0.updateButtons()
                goto L_0x00a2
            L_0x003a:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                int unused = r0.mState = r2
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                r0.updateButtons()
            L_0x0044:
                java.lang.Object r0 = r6.obj
                android.os.AsyncResult r0 = (android.os.AsyncResult) r0
                java.lang.Throwable r0 = r0.exception
                if (r0 != 0) goto L_0x0053
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                boolean[] r0 = r0.oneResult
                r0[r3] = r3
                goto L_0x005e
            L_0x0053:
                java.lang.String r0 = "CLOSE Command failed."
                com.mediatek.engineermode.Elog.d(r1, r0)
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                boolean[] r0 = r0.oneResult
                r0[r3] = r4
            L_0x005e:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                r0.testRoundEnd()
                goto L_0x00a2
            L_0x0064:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                int unused = r0.mState = r3
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                r0.updateButtons()
            L_0x006e:
                java.lang.Object r0 = r6.obj
                android.os.AsyncResult r0 = (android.os.AsyncResult) r0
                java.lang.Throwable r0 = r0.exception
                if (r0 != 0) goto L_0x008b
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                int r0 = r0.mState
                if (r0 == r2) goto L_0x0084
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                r1 = 3
                int unused = r0.mState = r1
            L_0x0084:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                boolean[] r0 = r0.oneResult
                r0[r4] = r3
                goto L_0x0098
            L_0x008b:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                java.lang.String r1 = "OPEN Command failed."
                r0.showToast(r1)
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                boolean[] r0 = r0.oneResult
                r0[r4] = r4
            L_0x0098:
                com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest r0 = com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.this
                int r1 = r0.period
                r0.startTimer(r1)
            L_0x00a2:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.rfdesense.RfDesenseDigRFTest.AnonymousClass6.handleMessage(android.os.Message):void");
        }
    };
    private final AdapterView.OnItemSelectedListener mItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
            if (arg0 == RfDesenseDigRFTest.this.testPeriodSpinner) {
                RfDesenseDigRFTest rfDesenseDigRFTest = RfDesenseDigRFTest.this;
                int unused = rfDesenseDigRFTest.testPeriod = rfDesenseDigRFTest.TestPeriodMap.get(Integer.valueOf(arg0.getSelectedItemPosition())).intValue();
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            int unused = RfDesenseDigRFTest.this.testPeriod = 0;
        }
    };
    private CompoundButton.OnCheckedChangeListener mOnCheckListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.rb_digrf_period:
                    RfDesenseDigRFTest.this.isPeriod = isChecked;
                    return;
                case R.id.rb_digrf_round:
                    RfDesenseDigRFTest.this.isRound = isChecked;
                    return;
                default:
                    return;
            }
        }
    };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_cancel:
                    RfDesenseDigRFTest.this.disableAllButtons();
                    int unused = RfDesenseDigRFTest.this.mState = 2;
                    return;
                case R.id.button_start:
                    RfDesenseDigRFTest.this.disableAllButtons();
                    RfDesenseDigRFTest.this.testStart();
                    return;
                default:
                    return;
            }
        }
    };
    private Phone mPhone;
    /* access modifiers changed from: private */
    public ProgressBar mProgress;
    /* access modifiers changed from: private */
    public int mState = 0;
    private Toast mToast = null;
    boolean[] oneResult = new boolean[2];
    /* access modifiers changed from: private */
    public int period;
    private int phoneid = 0;
    List<String> resultList = new ArrayList();
    String resultText;
    private int round;
    private int testFailedNum = 0;
    /* access modifiers changed from: private */
    public int testNum = 0;
    /* access modifiers changed from: private */
    public int testPeriod = 0;
    private CheckBox testPeriodCb;
    /* access modifiers changed from: private */
    public Spinner testPeriodSpinner;
    private TextView testResultOverallTv;
    private TextView testResultTitleTv;
    private TextView testResultTv;
    private int testRound = 0;
    private CheckBox testRoundCb;
    private EditText testRoundEt;
    private int testSucceedNum = 0;
    CountDownTimer timer;

    private int getTestPeriodIndex(int period2) {
        for (int i = 0; i < this.TestPeriodMap.size(); i++) {
            if (this.TestPeriodMap.get(Integer.valueOf(i)).intValue() == period2) {
                return i;
            }
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void testStart() {
        if (this.testRoundEt.getText().toString().equals("")) {
            this.round = 1;
        } else {
            try {
                int intValue = Integer.valueOf(this.testRoundEt.getText().toString()).intValue();
                this.testRound = intValue;
                if (!this.isRound) {
                    intValue = 1;
                }
                this.round = intValue;
            } catch (NumberFormatException e) {
                Elog.e(TAG, "NumberFormatException for " + this.testRoundEt.getText() + " " + e.getMessage());
                this.round = 1;
            }
        }
        if (this.round <= 0) {
            this.round = 1;
        }
        int intValue2 = this.TestPeriodMap.get(Integer.valueOf(this.testPeriodSpinner.getSelectedItemPosition())).intValue();
        this.testPeriod = intValue2;
        if (!this.isPeriod) {
            intValue2 = 0;
        }
        this.period = intValue2;
        this.mState = 1;
        this.testResultTitleTv.setVisibility(0);
        this.resultText = "";
        this.testResultOverallTv.setText("");
        this.testResultOverallTv.setVisibility(0);
        this.resultList.clear();
        this.testResultTv.setText("");
        this.testResultTv.setVisibility(0);
        this.mProgress.setProgress(0);
        this.mProgress.setSecondaryProgress(1);
        this.mProgress.setVisibility(0);
        this.testNum = 0;
        this.testSucceedNum = 0;
        this.testFailedNum = 0;
        this.oneResult = new boolean[]{false, false};
        sendAtCommand(AT_CMD_START, 0);
        Elog.d(TAG, "Test Start -> period: " + this.period + ",round:  " + this.round);
    }

    public void startTimer(int waitTime) {
        Elog.d(TAG, "waitTime = " + waitTime + "s");
        ProgressBar progressBar = this.mProgress;
        progressBar.setSecondaryProgress(progressBar.getProgress() + 1);
        final int i = waitTime;
        AnonymousClass5 r2 = new CountDownTimer(((long) waitTime) * 1000, 100) {
            public void onTick(long millisUntilFinishied) {
                ProgressBar access$500 = RfDesenseDigRFTest.this.mProgress;
                int i = i;
                access$500.setSecondaryProgress((int) ((((long) (i * 1000)) - millisUntilFinishied) / ((long) (i * 10))));
                Elog.d(RfDesenseDigRFTest.TAG, "millisUntilFinishied: " + millisUntilFinishied);
                if (millisUntilFinishied > 1000 && RfDesenseDigRFTest.this.mState == 2) {
                    onFinish();
                }
            }

            public void onFinish() {
                if (RfDesenseDigRFTest.this.oneResult[0]) {
                    RfDesenseDigRFTest rfDesenseDigRFTest = RfDesenseDigRFTest.this;
                    int i = 2;
                    if (rfDesenseDigRFTest.mState == 2) {
                        i = 3;
                    }
                    rfDesenseDigRFTest.sendAtCommand(RfDesenseDigRFTest.AT_CMD_STOP, i);
                } else {
                    RfDesenseDigRFTest.this.testRoundEnd();
                }
                Elog.d(RfDesenseDigRFTest.TAG, "test closed, round : " + RfDesenseDigRFTest.this.testNum + ", period: " + i + ", mState: " + RfDesenseDigRFTest.this.mState);
                if (RfDesenseDigRFTest.this.timer != null) {
                    RfDesenseDigRFTest.this.timer.cancel();
                }
                RfDesenseDigRFTest.this.timer = null;
            }
        };
        this.timer = r2;
        r2.start();
    }

    private void testStop() {
        this.mState = 2;
        CountDownTimer countDownTimer = this.timer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.timer = null;
        }
        Elog.d(TAG, "Test Stop -> period: " + this.period + ",round:  " + this.round + ", testNum:" + this.testNum);
        sendAtCommand(AT_CMD_STOP, 2);
    }

    /* access modifiers changed from: private */
    public void testRoundEnd() {
        ProgressBar progressBar = this.mProgress;
        progressBar.setSecondaryProgress(progressBar.getProgress() + 1);
        if (this.resultList.size() >= 99) {
            List<String> list = this.resultList;
            list.remove(list.size() - 1);
        }
        boolean[] zArr = this.oneResult;
        if (!zArr[0] || !zArr[1]) {
            this.resultList.add(0, "Round " + this.testNum + " : NG\n");
            this.testFailedNum = this.testFailedNum + 1;
        } else {
            this.resultList.add(0, "Round " + this.testNum + " : PASS\n");
            this.testSucceedNum = this.testSucceedNum + 1;
        }
        String str = "NG: " + this.testFailedNum + " round, PASS: " + this.testSucceedNum + " round\n";
        this.resultText = str;
        this.testResultOverallTv.setText(str);
        this.testResultTv.setText(" " + this.resultList.toString().replace("[", "").replace("]", "").replace(",", ""));
        int i = this.testNum + 1;
        this.testNum = i;
        if (i >= this.round || this.mState == 2) {
            this.testResultTv.setVisibility(8);
            this.mHandler.sendEmptyMessageDelayed(5, 1000);
            Elog.d(TAG, "Test stoped round  " + this.testNum + ": (" + this.testSucceedNum + " : " + this.testFailedNum + ").");
            this.mProgress.setProgress((this.testNum * 100) / this.round);
            if (this.testNum >= this.round) {
                this.mState = 0;
                this.mProgress.setVisibility(8);
                this.testResultTv.setVisibility(8);
                return;
            }
            return;
        }
        sendAtCommand(AT_CMD_START, 1);
        this.mState = 4;
        this.mProgress.setProgress((this.testNum * 100) / this.round);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(128);
        setContentView(R.layout.rf_desense_digrf_test);
        this.mButtonStart = (Button) findViewById(R.id.button_start);
        this.mButtonPause = (Button) findViewById(R.id.button_cancel);
        this.testPeriodSpinner = (Spinner) findViewById(R.id.test_period);
        this.testPeriodCb = (CheckBox) findViewById(R.id.rb_digrf_period);
        this.testRoundEt = (EditText) findViewById(R.id.test_round);
        this.testRoundCb = (CheckBox) findViewById(R.id.rb_digrf_round);
        this.testResultTv = (TextView) findViewById(R.id.test_result);
        this.mProgress = (ProgressBar) findViewById(R.id.test_progress);
        this.testResultTitleTv = (TextView) findViewById(R.id.test_result_title);
        this.testResultOverallTv = (TextView) findViewById(R.id.test_result_overall);
        this.mButtonStart.setOnClickListener(this.mOnClickListener);
        this.mButtonPause.setOnClickListener(this.mOnClickListener);
        this.testPeriodSpinner.setOnItemSelectedListener(this.mItemSelectedListener);
        this.testPeriodCb.setOnCheckedChangeListener(this.mOnCheckListener);
        this.testRoundCb.setOnCheckedChangeListener(this.mOnCheckListener);
        this.testRoundEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        disableAllButtons();
        if (!EmUtils.ifAirplaneModeEnabled()) {
            Elog.d(TAG, "it is in AirplaneMode");
            EmUtils.setAirplaneModeEnabled(true);
        }
        restoreState();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        saveState();
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d(TAG, "onResume");
        if (ModemCategory.isSimReady(-1)) {
            Elog.d(TAG, "some card insert");
            showDialog(2);
        }
    }

    public void onBackPressed() {
        int i = this.mState;
        if (i == 1 || i == 3) {
            showDialog(1);
            return;
        }
        EmUtils.setAirplaneModeEnabled(false);
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this).setTitle("Hint").setMessage("Please pause the test first!").setPositiveButton("Confirm", (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle("Notice").setMessage("Please pull out the sim card before test").setPositiveButton("Confirm", (DialogInterface.OnClickListener) null).create();
            default:
                return super.onCreateDialog(id);
        }
    }

    private void saveState() {
        SharedPreferences.Editor editor = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0).edit();
        this.testPeriod = this.TestPeriodMap.get(Integer.valueOf(this.testPeriodSpinner.getSelectedItemPosition())).intValue();
        if (this.testRoundEt.getText().toString().equals("")) {
            this.testRound = 1;
        } else {
            try {
                this.testRound = Integer.valueOf(this.testRoundEt.getText().toString()).intValue();
            } catch (NumberFormatException e) {
                Elog.e(TAG, "NumberFormatException for " + this.testRoundEt.getText() + " " + e.getMessage());
                this.testRound = 1;
            }
        }
        this.isPeriod = this.testPeriodCb.isChecked();
        this.isPeriod = this.testPeriodCb.isChecked();
        editor.putInt(KEY_STATE, this.mState);
        editor.putInt(KEY_PERIOD, this.testPeriod);
        editor.putInt(KEY_ROUND, this.testRound);
        editor.putBoolean(KEY_PERIOD_STATUS, this.isPeriod);
        editor.putBoolean(KEY_ROUND_STATUS, this.isRound);
        editor.commit();
    }

    private void restoreState() {
        SharedPreferences pref = getSharedPreferences(RfDesenseTxTest.PREF_FILE, 0);
        this.testPeriod = pref.getInt(KEY_PERIOD, 0);
        this.testRound = pref.getInt(KEY_ROUND, 0);
        this.isPeriod = pref.getBoolean(KEY_PERIOD_STATUS, false);
        this.isRound = pref.getBoolean(KEY_ROUND_STATUS, false);
        this.testPeriodSpinner.setSelection(getTestPeriodIndex(this.testPeriod));
        this.testPeriodCb.setChecked(this.isPeriod);
        this.testRoundCb.setChecked(this.isRound);
        EditText editText = this.testRoundEt;
        editText.setText(this.testRound + "");
        updateButtons();
    }

    /* access modifiers changed from: private */
    public void disableAllButtons() {
        this.mButtonStart.setEnabled(false);
        this.mButtonPause.setEnabled(false);
    }

    /* access modifiers changed from: private */
    public void updateButtons() {
        Elog.d(TAG, "[updateButtons] mState :" + this.mState);
        disableAllButtons();
        int i = this.mState;
        ((i == 1 || i == 3) ? this.mButtonPause : this.mButtonStart).setEnabled(true);
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        Elog.d(TAG, msg);
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 1);
        this.mToast = makeText;
        makeText.show();
    }

    /* access modifiers changed from: private */
    public void sendAtCommand(String str, int what) {
        String[] cmd = {str, ""};
        Elog.d(TAG, "send: " + cmd[0]);
        EmUtils.invokeOemRilRequestStringsEm(cmd, this.mHandler.obtainMessage(what));
    }
}
