package com.mediatek.engineermode.lterxmimoconfigure;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class LteRxMimoConfigure extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final int MSG_LTE_BAND_SETTING_4MIMO = 103;
    private static final int MSG_LTE_BAND_SETTING_4RX = 104;
    private static final int MSG_QUERY_CMD = 101;
    private static final int MSG_SET_CMD = 102;
    private static final String TAG = "LteRx/MimoConfigure";
    private static final int WARNING_MSG_REBOOT = 1;
    private Handler mCommandHander = new Handler() {
        public void handleMessage(Message msg) {
            AsyncResult asyncResult = (AsyncResult) msg.obj;
            switch (msg.what) {
                case 101:
                    String[] result = (String[]) asyncResult.result;
                    if (result == null || result.length <= 0) {
                        Elog.e(LteRxMimoConfigure.TAG, "LteRxMimo Query Failed!");
                        EmUtils.showToast("LteRxMimo Query Failed!");
                    } else {
                        Elog.d(LteRxMimoConfigure.TAG, "LteRxMimo Query Succeed,result = " + result[0]);
                        EmUtils.showToast("LteRxMimo Query Succeed!");
                        LteRxMimoConfigure.this.parseCurrentLteMode(result[0]);
                    }
                    LteRxMimoConfigure.this.updateButtonStatus();
                    return;
                case 102:
                    if (asyncResult.exception == null) {
                        EmUtils.showToast("LteRxMimo Set Succeed!");
                        Elog.d(LteRxMimoConfigure.TAG, "LteRxMimo Set Succeed!");
                        LteRxMimoConfigure.this.showDialog(1);
                        return;
                    }
                    EmUtils.showToast("LteRxMimo Set failed!");
                    Elog.e(LteRxMimoConfigure.TAG, "LteRxMimo Set failed!");
                    return;
                case 103:
                    if (asyncResult.exception == null) {
                        EmUtils.showToast("4x4MIMO single band setting  Succeed!");
                        Elog.d(LteRxMimoConfigure.TAG, "4x4MIMO single band setting Succeed!");
                        return;
                    }
                    EmUtils.showToast("4x4MIMO single band setting failed!");
                    Elog.e(LteRxMimoConfigure.TAG, "4x4MIMO single band setting failed!");
                    return;
                case 104:
                    if (asyncResult.exception == null) {
                        EmUtils.showToast("4RX single band setting Succeed!");
                        Elog.d(LteRxMimoConfigure.TAG, "4RX single band setting Succeed!");
                        return;
                    }
                    EmUtils.showToast("4RX single band setting failed!");
                    Elog.e(LteRxMimoConfigure.TAG, "4RX single band setting failed!");
                    return;
                default:
                    return;
            }
        }
    };
    private CheckBox mRxMimo44MomoCb;
    private int mRxMimo44MomoCurStatus = 0;
    private CheckBox mRxMimo44MomoUnderCCACb;
    private int mRxMimo44MomoUnderCCACurStatuss = 0;
    private Button mRxMimo44SingleBandSettingBt;
    private CheckBox mRxMimoFeatureEnableCb;
    private int mRxMimoFeatureEnableCurStatus = 0;
    private CheckBox mRxMimoRas2Rx1RxCb;
    private int mRxMimoRas2Rx1RxCurStatus = 0;
    private CheckBox mRxMimoRas4Rx2RxCb;
    private int mRxMimoRas4Rx2RxCurStatus = 0;
    private CheckBox mRxMimoRas4Rx2RxUnderCCACb;
    private int mRxMimoRas4Rx2RxUnderCCACurStatus = 0;
    private Button mRxMimoRas4RxSingleBandSettingBt;
    private Button mSetBt;

    /* access modifiers changed from: private */
    public void parseCurrentLteMode(String data) {
        try {
            String[] info = data.split(",");
            this.mRxMimoFeatureEnableCurStatus = Integer.valueOf(info[1].trim()).intValue();
            this.mRxMimo44MomoCurStatus = Integer.valueOf(info[2].trim()).intValue();
            this.mRxMimo44MomoUnderCCACurStatuss = Integer.valueOf(info[3].trim()).intValue();
            this.mRxMimoRas4Rx2RxCurStatus = Integer.valueOf(info[4].trim()).intValue();
            this.mRxMimoRas4Rx2RxUnderCCACurStatus = Integer.valueOf(info[5].trim()).intValue();
            this.mRxMimoRas2Rx1RxCurStatus = Integer.valueOf(info[6].trim()).intValue();
        } catch (NumberFormatException e) {
            Elog.e(TAG, "Wrong current mode format: " + data);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lte_rx_mimo_configure);
        this.mRxMimoFeatureEnableCb = (CheckBox) findViewById(R.id.lte_rx_mimo_feature_enable);
        this.mRxMimo44MomoCb = (CheckBox) findViewById(R.id.lte_rx_mimo_4_momo);
        this.mRxMimo44MomoUnderCCACb = (CheckBox) findViewById(R.id.lte_rx_mimo_4_momo_under_cca);
        this.mRxMimoRas4Rx2RxCb = (CheckBox) findViewById(R.id.lte_rx_mimo_ras_4rx_2rx);
        this.mRxMimoRas4Rx2RxUnderCCACb = (CheckBox) findViewById(R.id.lte_rx_mimo_ras_4rx_2rx_under_cca);
        this.mRxMimoRas2Rx1RxCb = (CheckBox) findViewById(R.id.lte_rx_mimo_ras_2rx_1rx);
        this.mRxMimo44SingleBandSettingBt = (Button) findViewById(R.id.lte_4x4MIMO_single_band_setting);
        this.mRxMimoRas4RxSingleBandSettingBt = (Button) findViewById(R.id.lte_4RX_single_band_setting);
        Button button = (Button) findViewById(R.id.lte_rx_mimo_set_button);
        this.mSetBt = button;
        button.setOnClickListener(this);
        this.mRxMimo44SingleBandSettingBt.setOnClickListener(this);
        this.mRxMimoRas4RxSingleBandSettingBt.setOnClickListener(this);
        this.mRxMimoFeatureEnableCb.setOnCheckedChangeListener(this);
        this.mRxMimo44MomoCb.setOnCheckedChangeListener(this);
        this.mRxMimoRas4Rx2RxCb.setOnCheckedChangeListener(this);
        queryStatus();
    }

    private void queryStatus() {
        sendCommand(new String[]{"AT+EGMC=0,\"rx_mimo_set\"", "+EGMC:"}, 101);
    }

    private void sendCommand(String[] command, int msg) {
        Elog.d(TAG, "sendCommand " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(command, this.mCommandHander.obtainMessage(msg));
    }

    /* access modifiers changed from: package-private */
    public void set4RxMimoConfigure() {
        boolean isChecked = this.mRxMimoFeatureEnableCb.isChecked();
        this.mRxMimoFeatureEnableCurStatus = isChecked ? 1 : 0;
        if (isChecked) {
            boolean isChecked2 = this.mRxMimo44MomoCb.isChecked();
            this.mRxMimo44MomoCurStatus = isChecked2 ? 1 : 0;
            if (isChecked2) {
                this.mRxMimo44MomoUnderCCACurStatuss = this.mRxMimo44MomoUnderCCACb.isChecked() ? 1 : 0;
            } else {
                this.mRxMimo44MomoUnderCCACurStatuss = 0;
            }
            boolean isChecked3 = this.mRxMimoRas4Rx2RxCb.isChecked();
            this.mRxMimoRas4Rx2RxCurStatus = isChecked3 ? 1 : 0;
            if (isChecked3) {
                this.mRxMimoRas4Rx2RxUnderCCACurStatus = this.mRxMimoRas4Rx2RxUnderCCACb.isChecked() ? 1 : 0;
            } else {
                this.mRxMimoRas4Rx2RxUnderCCACurStatus = 0;
            }
            this.mRxMimoRas2Rx1RxCurStatus = this.mRxMimoRas2Rx1RxCb.isChecked() ? 1 : 0;
        } else {
            this.mRxMimo44MomoCurStatus = 0;
            this.mRxMimo44MomoUnderCCACurStatuss = 0;
            this.mRxMimoRas4Rx2RxCurStatus = 0;
            this.mRxMimoRas4Rx2RxUnderCCACurStatus = 0;
            this.mRxMimoRas2Rx1RxCurStatus = 0;
        }
        sendCommand(new String[]{"AT+EGMC=1,\"rx_mimo_set\"," + this.mRxMimoFeatureEnableCurStatus + "," + this.mRxMimo44MomoCurStatus + "," + this.mRxMimo44MomoUnderCCACurStatuss + "," + this.mRxMimoRas4Rx2RxCurStatus + "," + this.mRxMimoRas4Rx2RxUnderCCACurStatus + "," + this.mRxMimoRas2Rx1RxCurStatus, ""}, 102);
    }

    /* access modifiers changed from: package-private */
    public void setLteSingleBandSetting() {
        Elog.d(TAG, "LteBandSettingModeHistory = " + LteSingleBandSetting.LteBandSettingModeHistory);
        if (LteSingleBandSetting.LteBandSettingModeHistory == 0) {
            Elog.d(TAG, "skip LteSingleBandSetting");
        }
        if ((LteSingleBandSetting.LteBandSettingModeHistory & 1) == 1 && this.mRxMimo44MomoCurStatus == 1) {
            sendCommand(new String[]{"AT+EGMC=1,\"sb_mimo_set\"" + LteSingleBandSetting.mSbMimoSetString[0], ""}, 103);
        }
        if ((LteSingleBandSetting.LteBandSettingModeHistory & 2) == 2 && this.mRxMimoRas4Rx2RxCurStatus == 1) {
            sendCommand(new String[]{"AT+EGMC=1,\"sb_4rx_set\"" + LteSingleBandSetting.mSbMimoSetString[1], ""}, 104);
        }
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, LteSingleBandSetting.class);
        if (R.id.lte_rx_mimo_set_button == v.getId()) {
            set4RxMimoConfigure();
            setLteSingleBandSetting();
            Elog.d(TAG, "lte_rx_mimo_set_button click");
        } else if (R.id.lte_4x4MIMO_single_band_setting == v.getId()) {
            Elog.d(TAG, "4x4MIMO single band setting click");
            intent.putExtra("band_setting_type", 0);
            startActivity(intent);
        } else if (R.id.lte_4RX_single_band_setting == v.getId()) {
            Elog.d(TAG, "4RX single band setting click");
            intent.putExtra("band_setting_type", 1);
            startActivity(intent);
        }
    }

    /* access modifiers changed from: package-private */
    public void updateButtonStatus() {
        Elog.d(TAG, "updateButtonStatus");
        this.mRxMimoFeatureEnableCb.setChecked(this.mRxMimoFeatureEnableCurStatus == 1);
        this.mRxMimo44MomoCb.setChecked(this.mRxMimo44MomoCurStatus == 1);
        this.mRxMimo44MomoUnderCCACb.setChecked(this.mRxMimo44MomoUnderCCACurStatuss == 1);
        this.mRxMimoRas4Rx2RxCb.setChecked(this.mRxMimoRas4Rx2RxCurStatus == 1);
        this.mRxMimoRas4Rx2RxUnderCCACb.setChecked(this.mRxMimoRas4Rx2RxUnderCCACurStatus == 1);
        this.mRxMimoRas2Rx1RxCb.setChecked(this.mRxMimoRas2Rx1RxCurStatus == 1);
        if (this.mRxMimoFeatureEnableCurStatus == 0) {
            this.mRxMimo44MomoCb.setEnabled(false);
            this.mRxMimo44MomoUnderCCACb.setEnabled(false);
            this.mRxMimoRas4Rx2RxCb.setEnabled(false);
            this.mRxMimoRas4Rx2RxUnderCCACb.setEnabled(false);
            this.mRxMimo44SingleBandSettingBt.setEnabled(false);
            this.mRxMimoRas2Rx1RxCb.setEnabled(false);
        } else {
            this.mRxMimo44MomoCb.setEnabled(true);
            this.mRxMimo44MomoUnderCCACb.setEnabled(true);
            this.mRxMimoRas4Rx2RxCb.setEnabled(true);
            this.mRxMimoRas4Rx2RxUnderCCACb.setEnabled(true);
            this.mRxMimoRas2Rx1RxCb.setEnabled(true);
            this.mRxMimo44SingleBandSettingBt.setEnabled(true);
        }
        if (this.mRxMimo44MomoCurStatus == 1 && this.mRxMimoFeatureEnableCurStatus == 1) {
            this.mRxMimo44MomoUnderCCACb.setEnabled(true);
            this.mRxMimo44SingleBandSettingBt.setEnabled(true);
        } else {
            this.mRxMimo44MomoUnderCCACb.setEnabled(false);
            this.mRxMimo44SingleBandSettingBt.setEnabled(false);
        }
        if (this.mRxMimoRas4Rx2RxCurStatus == 1 && this.mRxMimoFeatureEnableCurStatus == 1) {
            this.mRxMimoRas4Rx2RxUnderCCACb.setEnabled(true);
            this.mRxMimoRas4RxSingleBandSettingBt.setEnabled(true);
            return;
        }
        this.mRxMimoRas4Rx2RxUnderCCACb.setEnabled(false);
        this.mRxMimoRas4RxSingleBandSettingBt.setEnabled(false);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this).setTitle("LteRxMimoConfigure").setMessage("It needs to reboot modem to enable this setting\n").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {
                            EmUtils.rebootModem();
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create();
            default:
                return null;
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.lte_rx_mimo_4_momo:
                this.mRxMimo44MomoCurStatus = isChecked;
                Elog.d(TAG, "4X4 MIMO clicked");
                break;
            case R.id.lte_rx_mimo_feature_enable:
                this.mRxMimoFeatureEnableCurStatus = isChecked;
                Elog.d(TAG, "feature_enable clicked");
                break;
            case R.id.lte_rx_mimo_ras_4rx_2rx:
                this.mRxMimoRas4Rx2RxCurStatus = isChecked;
                Elog.d(TAG, "RAS_4RX_2RX switching");
                break;
        }
        updateButtonStatus();
    }
}
