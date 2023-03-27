package com.mediatek.engineermode.dm;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.engineermode.R;

public class DiagnosticMonitoringSettingActivity extends Activity {
    private static final String ACTION_START_APM_SERVICE = "com.mediatek.apmonitor.ACTION_START_APM_SERVICE";
    private static final String ACTION_STOP_APM_SERVICE = "com.mediatek.apmonitor.ACTION_STOP_APM_SERVICE";
    private static final String APM_PROCESS_NAME = "com.mediatek.apmonitor";
    private static final String APM_SERVICE_NAME = "com.mediatek.apmonitor.ApmService";
    private static final int CONTROL_APM_SERVICE_RETRY = 5;
    private static final int CONTROL_APM_SERVICE_TIMEOUT = 1000;
    private static final int DLG_DM_APM_ACTIVATION_SWITCHING = 3;
    private static final int DLG_DM_APM_SERVICE_SWITCHING = 2;
    private static final int DLG_DM_LOG_PKM_SETTING_SWITCHING = 1;
    private static final int DLG_DM_LOG_SETTING_SWITCHING = 0;
    private static final String TAG = "DM-Setting";
    /* access modifiers changed from: private */
    public static final ComponentName mApmServiceComp = new ComponentName(APM_PROCESS_NAME, APM_SERVICE_NAME);
    private Button mBtnApmActivationSetting = null;
    private Button mBtnApmServiceSetting = null;
    private Button mBtnLogPkmSetting = null;
    private Button mBtnLogSetting = null;
    /* access modifiers changed from: private */
    public DmSettingController mDmSettingCtrl = null;
    private TextView mTvDmcFo = null;
    private TextView mTvMapiFo = null;
    private TextView mTvMdmiFo = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnostic_monitoring_setting);
        this.mTvDmcFo = (TextView) findViewById(R.id.dm_fo_dmc_text);
        this.mTvMapiFo = (TextView) findViewById(R.id.dm_fo_mapi_text);
        this.mTvMdmiFo = (TextView) findViewById(R.id.dm_fo_mdmi_text);
        this.mBtnLogSetting = (Button) findViewById(R.id.dm_log_setting_button);
        this.mBtnLogPkmSetting = (Button) findViewById(R.id.dm_log_pkm_setting_button);
        this.mBtnApmServiceSetting = (Button) findViewById(R.id.dm_apm_service_setting_button);
        this.mBtnApmActivationSetting = (Button) findViewById(R.id.dm_apm_activation_setting_button);
        this.mDmSettingCtrl = new DmSettingController(this);
        updateUI();
        this.mBtnLogSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DiagnosticMonitoringSettingActivity.this.showDialog(0);
                new LogSettingTask().execute(new Void[0]);
            }
        });
        this.mBtnLogPkmSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DiagnosticMonitoringSettingActivity.this.showDialog(1);
                new LogPkmSettingTask().execute(new Void[0]);
            }
        });
        if (DmSettingController.isDmcSystemEnabled()) {
            this.mBtnApmServiceSetting.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DiagnosticMonitoringSettingActivity.this.showDialog(2);
                    if (DiagnosticMonitoringSettingActivity.this.isApmServiceRunning()) {
                        new ApmServiceSettingTask(false).execute(new Void[0]);
                    } else {
                        new ApmServiceSettingTask(true).execute(new Void[0]);
                    }
                }
            });
            this.mBtnApmActivationSetting.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DiagnosticMonitoringSettingActivity.this.showDialog(3);
                    new ApmActivationSettingTask().execute(new Void[0]);
                }
            });
            return;
        }
        Log.d(TAG, "Not support APM service");
        this.mBtnApmServiceSetting.setEnabled(false);
        this.mBtnApmActivationSetting.setEnabled(false);
    }

    /* access modifiers changed from: private */
    public void updateUI() {
        StringBuilder dmcFoString = new StringBuilder();
        StringBuilder mapiFoString = new StringBuilder();
        StringBuilder mdmiFoString = new StringBuilder();
        dmcFoString.append("DMC: system(" + DmSettingController.isDmcSystemEnabled() + ") ");
        dmcFoString.append("vendor(" + DmSettingController.isDmcVendorEnabled() + ")");
        this.mTvDmcFo.setText(dmcFoString.toString());
        mapiFoString.append("MAPI: system(" + DmSettingController.isMapiSystemEnabled() + ") ");
        mapiFoString.append("vendor(" + DmSettingController.isMapiVendorEnabled() + ")");
        this.mTvMapiFo.setText(mapiFoString.toString());
        mdmiFoString.append("MDMI: system(" + DmSettingController.isMdmiSystemEnabled() + ") ");
        mdmiFoString.append("vendor(" + DmSettingController.isMdmiVendorEnabled() + ")");
        this.mTvMdmiFo.setText(mdmiFoString.toString());
        if (this.mDmSettingCtrl.isDmLogEnabled()) {
            this.mBtnLogSetting.setText(R.string.dm_log_button_disable);
        } else {
            this.mBtnLogSetting.setText(R.string.dm_log_button_enable);
        }
        if (this.mDmSettingCtrl.isPkmLogEnabled()) {
            this.mBtnLogPkmSetting.setText(R.string.dm_log_pkm_button_disable);
        } else {
            this.mBtnLogPkmSetting.setText(R.string.dm_log_pkm_button_enable);
        }
        if (DmSettingController.isDmcSystemEnabled()) {
            if (isApmServiceRunning()) {
                this.mBtnApmServiceSetting.setText(R.string.dm_apm_button_stop);
            } else {
                this.mBtnApmServiceSetting.setText(R.string.dm_apm_button_start);
            }
            if (DmSettingController.isDmcApmActivated()) {
                this.mBtnApmActivationSetting.setText(R.string.dm_apm_button_deactivate);
            } else {
                this.mBtnApmActivationSetting.setText(R.string.dm_apm_button_activate);
            }
        } else {
            this.mBtnApmServiceSetting.setText(R.string.dm_apm_not_support);
        }
    }

    private class LogSettingTask extends AsyncTask<Void, Void, Boolean> {
        private LogSettingTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            return Boolean.valueOf(DiagnosticMonitoringSettingActivity.this.mDmSettingCtrl.enableDmLog(!Boolean.valueOf(DiagnosticMonitoringSettingActivity.this.mDmSettingCtrl.isDmLogEnabled()).booleanValue()));
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            DiagnosticMonitoringSettingActivity.this.updateUI();
            DiagnosticMonitoringSettingActivity.this.removeDialog(0);
            if (DiagnosticMonitoringSettingActivity.this.isActivityAlive()) {
                new AlertDialog.Builder(DiagnosticMonitoringSettingActivity.this).setTitle(result.booleanValue() ? R.string.set_success_message : R.string.set_fail_message).show();
            }
        }
    }

    private class LogPkmSettingTask extends AsyncTask<Void, Void, Boolean> {
        private LogPkmSettingTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            return Boolean.valueOf(DiagnosticMonitoringSettingActivity.this.mDmSettingCtrl.enablePkmLog(!Boolean.valueOf(DiagnosticMonitoringSettingActivity.this.mDmSettingCtrl.isPkmLogEnabled()).booleanValue()));
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            DiagnosticMonitoringSettingActivity.this.updateUI();
            DiagnosticMonitoringSettingActivity.this.removeDialog(1);
            if (DiagnosticMonitoringSettingActivity.this.isActivityAlive()) {
                new AlertDialog.Builder(DiagnosticMonitoringSettingActivity.this).setTitle(result.booleanValue() ? R.string.set_success_message : R.string.set_fail_message).show();
            }
        }
    }

    private class ApmServiceSettingTask extends AsyncTask<Void, Void, Boolean> {
        private boolean mOperationStart;

        private ApmServiceSettingTask() {
        }

        public ApmServiceSettingTask(boolean bStart) {
            this.mOperationStart = bStart;
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            boolean success = false;
            Intent intent = new Intent();
            intent.setComponent(DiagnosticMonitoringSettingActivity.mApmServiceComp);
            if (!this.mOperationStart) {
                Log.d(DiagnosticMonitoringSettingActivity.TAG, "Stop APM service");
                intent.setAction(DiagnosticMonitoringSettingActivity.ACTION_STOP_APM_SERVICE);
                DiagnosticMonitoringSettingActivity.this.startForegroundService(intent);
                int i = 0;
                while (true) {
                    if (i >= 5) {
                        break;
                    }
                    DiagnosticMonitoringSettingActivity.sleep(1000);
                    if (!DiagnosticMonitoringSettingActivity.this.isApmServiceRunning()) {
                        success = true;
                        Log.d(DiagnosticMonitoringSettingActivity.TAG, "Stop APM service successfully!");
                        break;
                    }
                    Log.e(DiagnosticMonitoringSettingActivity.TAG, "Stop APM service failed!");
                    i++;
                }
            } else {
                Log.d(DiagnosticMonitoringSettingActivity.TAG, "Start APM service");
                intent.setAction(DiagnosticMonitoringSettingActivity.ACTION_START_APM_SERVICE);
                DiagnosticMonitoringSettingActivity.this.startForegroundService(intent);
                int i2 = 0;
                while (true) {
                    if (i2 >= 5) {
                        break;
                    }
                    DiagnosticMonitoringSettingActivity.sleep(1000);
                    if (DiagnosticMonitoringSettingActivity.this.isApmServiceRunning()) {
                        success = true;
                        Log.d(DiagnosticMonitoringSettingActivity.TAG, "Start APM service successfully!");
                        break;
                    }
                    Log.e(DiagnosticMonitoringSettingActivity.TAG, "Start APM service failed!");
                    i2++;
                }
            }
            return Boolean.valueOf(success);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            DiagnosticMonitoringSettingActivity.this.updateUI();
            DiagnosticMonitoringSettingActivity.this.removeDialog(2);
            if (DiagnosticMonitoringSettingActivity.this.isActivityAlive()) {
                new AlertDialog.Builder(DiagnosticMonitoringSettingActivity.this).setTitle(result.booleanValue() ? R.string.set_success_message : R.string.set_fail_message).show();
            }
        }
    }

    private class ApmActivationSettingTask extends AsyncTask<Void, Void, Boolean> {
        private ApmActivationSettingTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            DmSettingController unused = DiagnosticMonitoringSettingActivity.this.mDmSettingCtrl;
            return Boolean.valueOf(DiagnosticMonitoringSettingActivity.this.mDmSettingCtrl.activeApm(!Boolean.valueOf(DmSettingController.isDmcApmActivated()).booleanValue()));
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            DiagnosticMonitoringSettingActivity.this.updateUI();
            DiagnosticMonitoringSettingActivity.this.removeDialog(3);
            if (DiagnosticMonitoringSettingActivity.this.isActivityAlive()) {
                new AlertDialog.Builder(DiagnosticMonitoringSettingActivity.this).setTitle(result.booleanValue() ? R.string.set_success_message : R.string.set_fail_message).show();
            }
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
            case 1:
            case 2:
            case 3:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage(getString(R.string.dm_executing));
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                return dialog;
            default:
                return null;
        }
    }

    public static boolean isSupport() {
        if (DmSettingController.isDmcVendorEnabled()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean isActivityAlive() {
        return !isFinishing() && !isDestroyed();
    }

    /* access modifiers changed from: private */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public boolean isApmServiceRunning() {
        for (ActivityManager.RunningServiceInfo serviceInfo : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            Log.d(TAG, serviceInfo.service.getClassName());
            if (APM_SERVICE_NAME.equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
