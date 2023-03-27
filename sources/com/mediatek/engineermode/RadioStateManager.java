package com.mediatek.engineermode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncResult;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

public class RadioStateManager {
    public static final int DIALOG_WAIT_REBOOT = 1001;
    public static final int MSG_RADIO_STATE_CHANGED = 2001;
    public static final String TAG = "RadioStateManager";
    public static final int WAIT_TIME = 30;
    private Activity mActivity;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 2001:
                    int[] state = (int[]) ar.result;
                    if (state[0] == 1) {
                        Elog.v(RadioStateManager.TAG, "RADIO_POWER_ON");
                        if (RadioStateManager.this.mTimer != null) {
                            RadioStateManager.this.mTimer.cancel();
                            CountDownTimer unused = RadioStateManager.this.mTimer = null;
                        }
                        if (RadioStateManager.this.mResetMdDialog != null) {
                            RadioStateManager.this.mResetMdDialog.dismiss();
                        }
                        if (RadioStateManager.this.mRadioListener != null) {
                            RadioStateManager.this.mRadioListener.onRadioPowerOn();
                            return;
                        }
                        return;
                    } else if (state[0] == 0) {
                        if (RadioStateManager.this.mResetMdDialog == null || !RadioStateManager.this.mResetMdDialog.isShowing()) {
                            if (RadioStateManager.this.mTimer != null) {
                                RadioStateManager.this.mTimer.cancel();
                                CountDownTimer unused2 = RadioStateManager.this.mTimer = null;
                            }
                            Elog.v(RadioStateManager.TAG, "RADIO_POWER_OFF");
                            if (RadioStateManager.this.mRadioListener != null) {
                                RadioStateManager.this.mRadioListener.onRadioPowerOff();
                                return;
                            }
                            return;
                        }
                        return;
                    } else if (state[0] == 2) {
                        Elog.v(RadioStateManager.TAG, "RADIO_POWER_UNAVAILABLE");
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public RadioListener mRadioListener;
    /* access modifiers changed from: private */
    public ProgressDialog mResetMdDialog;
    /* access modifiers changed from: private */
    public CountDownTimer mTimer;

    public interface RadioListener {
        void onRadioPowerOff();

        void onRadioPowerOn();
    }

    public RadioStateManager(Activity activity) {
        this.mActivity = activity;
    }

    public void registerRadioStateChanged(RadioListener listener) {
        EmUtils.registerForradioStateChanged(0, this.mHandler, 2001);
        Elog.v(TAG, "registerRadioStateChanged " + listener);
        this.mRadioListener = listener;
    }

    public void unregisterRadioStateChanged() {
        EmUtils.unregisterradioStateChanged(0);
        CountDownTimer countDownTimer = this.mTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mTimer = null;
        }
        ProgressDialog progressDialog = this.mResetMdDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mResetMdDialog = null;
        }
        this.mRadioListener = null;
    }

    public void setAirplaneModeEnabled(boolean on) {
        startTimer();
        EmUtils.setAirplaneModeEnabled(on);
    }

    public void rebootModem() {
        startTimer();
        this.mActivity.showDialog(1001);
        EmUtils.rebootModem();
    }

    public ProgressDialog getRebootModemDialog() {
        Elog.v(TAG, "+++getRebootModemDialog+++");
        ProgressDialog progressDialog = new ProgressDialog(this.mActivity);
        this.mResetMdDialog = progressDialog;
        progressDialog.setTitle("Waiting");
        this.mResetMdDialog.setMessage("Please wait until modem reset succeed");
        this.mResetMdDialog.setCancelable(false);
        this.mResetMdDialog.setIndeterminate(true);
        return this.mResetMdDialog;
    }

    private void startTimer() {
        if (this.mTimer != null) {
            Elog.d(TAG, "start timer again, cancel it");
            this.mTimer.cancel();
            this.mTimer = null;
        }
        AnonymousClass1 r2 = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinishied) {
            }

            public void onFinish() {
                Elog.d(RadioStateManager.TAG, "CountDownTimer finish");
                if (RadioStateManager.this.mResetMdDialog != null) {
                    RadioStateManager.this.mResetMdDialog.dismiss();
                }
                if (EmUtils.ifAirplaneModeEnabled()) {
                    Elog.d(RadioStateManager.TAG, "[CountDownTimer onFinish]it is in Airplane Mode");
                    if (RadioStateManager.this.mRadioListener != null) {
                        RadioStateManager.this.mRadioListener.onRadioPowerOff();
                    }
                } else if (RadioStateManager.this.mRadioListener != null) {
                    RadioStateManager.this.mRadioListener.onRadioPowerOn();
                }
                CountDownTimer unused = RadioStateManager.this.mTimer = null;
            }
        };
        this.mTimer = r2;
        r2.start();
        Elog.d(TAG, "timer started");
    }
}
