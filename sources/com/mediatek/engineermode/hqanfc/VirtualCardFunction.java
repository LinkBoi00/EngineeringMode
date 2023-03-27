package com.mediatek.engineermode.hqanfc;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class VirtualCardFunction extends Activity {
    private static final int CHECKBOXS_NUMBER = 6;
    private static final int CHECKBOX_TYPEA = 0;
    private static final int CHECKBOX_TYPEB = 1;
    private static final int CHECKBOX_TYPEB2 = 5;
    private static final int CHECKBOX_TYPEF = 2;
    private static final int CHECKBOX_TYPEF_212 = 3;
    private static final int CHECKBOX_TYPEF_424 = 4;
    private static final int DIALOG_ID_WAIT = 0;
    private static final int HANDLER_MSG_GET_RSP = 200;
    private static final int RADIO_NUMBER = 2;
    private static final int RADIO_TYPEF_212 = 0;
    private static final int RADIO_TYPEF_424 = 1;
    /* access modifiers changed from: private */
    public Button mBtnClearAll;
    /* access modifiers changed from: private */
    public Button mBtnReturn;
    /* access modifiers changed from: private */
    public Button mBtnRunInBack;
    /* access modifiers changed from: private */
    public Button mBtnSelectAll;
    /* access modifiers changed from: private */
    public Button mBtnStart;
    private final CompoundButton.OnCheckedChangeListener mCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
            Elog.v(NfcMainPage.TAG, "[VirtualCardFunction]onCheckedChanged view is " + buttonView.getText() + " value is " + checked);
            if (buttonView.equals(VirtualCardFunction.this.mSettingsCkBoxs[2])) {
                VirtualCardFunction.this.mSettingsCkBoxs[3].setEnabled(checked);
                VirtualCardFunction.this.mSettingsCkBoxs[4].setEnabled(checked);
            }
        }
    };
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[VirtualCardFunction]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(VirtualCardFunction.this.mBtnStart)) {
                VirtualCardFunction.this.showDialog(0);
                VirtualCardFunction virtualCardFunction = VirtualCardFunction.this;
                virtualCardFunction.doTestAction(Boolean.valueOf(virtualCardFunction.mBtnStart.getText().equals(VirtualCardFunction.this.getString(R.string.hqa_nfc_start))));
            } else if (arg0.equals(VirtualCardFunction.this.mBtnSelectAll)) {
                VirtualCardFunction.this.changeAllSelect(true);
            } else if (arg0.equals(VirtualCardFunction.this.mBtnClearAll)) {
                VirtualCardFunction.this.changeAllSelect(false);
            } else if (arg0.equals(VirtualCardFunction.this.mBtnReturn)) {
                VirtualCardFunction.this.onBackPressed();
            } else if (arg0.equals(VirtualCardFunction.this.mBtnRunInBack)) {
                VirtualCardFunction.this.doTestAction((Boolean) null);
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addFlags(268435456);
                intent.addCategory("android.intent.category.HOME");
                VirtualCardFunction.this.startActivity(intent);
            }
        }
    };
    private boolean mEnableBackKey = true;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String toastMsg;
            super.handleMessage(msg);
            if (200 == msg.what) {
                VirtualCardFunction.this.dismissDialog(0);
                switch (VirtualCardFunction.this.mResponse.mResult) {
                    case 0:
                        toastMsg = "VirtualCardFunction Rsp Result: SUCCESS";
                        if (!VirtualCardFunction.this.mBtnStart.getText().equals(VirtualCardFunction.this.getString(R.string.hqa_nfc_start))) {
                            VirtualCardFunction.this.setButtonsStatus(true);
                            break;
                        } else {
                            VirtualCardFunction.this.setButtonsStatus(false);
                            break;
                        }
                    case 1:
                        toastMsg = "VirtualCardFunction Rsp Result: FAIL";
                        break;
                    case NfcCommand.RspResult.NFC_STATUS_REMOVE_SE:
                        toastMsg = "Please Remove SIM or uSD";
                        break;
                    default:
                        toastMsg = "VirtualCardFunction Rsp Result: ERROR";
                        break;
                }
                Toast.makeText(VirtualCardFunction.this, toastMsg, 0).show();
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[VirtualCardFunction]mReceiver onReceive");
            if ("com.mediatek.hqanfc.114".equals(intent.getAction())) {
                byte[] unused = VirtualCardFunction.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (VirtualCardFunction.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(VirtualCardFunction.this.mRspArray);
                    NfcEmReqRsp.NfcEmVirtualCardRsp unused2 = VirtualCardFunction.this.mResponse = new NfcEmReqRsp.NfcEmVirtualCardRsp();
                    VirtualCardFunction.this.mResponse.readRaw(buffer);
                    VirtualCardFunction.this.mHandler.sendEmptyMessage(200);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "[VirtualCardFunction]Other response");
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmVirtualCardRsp mResponse;
    /* access modifiers changed from: private */
    public byte[] mRspArray;
    /* access modifiers changed from: private */
    public CheckBox[] mSettingsCkBoxs = new CheckBox[6];

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_virtualcard_function);
        initComponents();
        changeAllSelect(true);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.114");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    public void onBackPressed() {
        if (this.mEnableBackKey) {
            super.onBackPressed();
        }
    }

    private void initComponents() {
        Elog.v(NfcMainPage.TAG, "[VirtualCardFunction]initComponents");
        this.mSettingsCkBoxs[0] = (CheckBox) findViewById(R.id.hqa_virtual_cb_typea);
        this.mSettingsCkBoxs[1] = (CheckBox) findViewById(R.id.hqa_virtual_cb_typeb);
        this.mSettingsCkBoxs[2] = (CheckBox) findViewById(R.id.hqa_virtual_cb_typef);
        this.mSettingsCkBoxs[2].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[3] = (CheckBox) findViewById(R.id.hqa_virtual_cb_typef_212);
        this.mSettingsCkBoxs[4] = (CheckBox) findViewById(R.id.hqa_virtual_cb_typef_424);
        this.mSettingsCkBoxs[5] = (CheckBox) findViewById(R.id.hqa_virtual_cb_typeb2);
        Button button = (Button) findViewById(R.id.hqa_virtual_btn_select_all);
        this.mBtnSelectAll = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_virtual_btn_clear_all);
        this.mBtnClearAll = button2;
        button2.setOnClickListener(this.mClickListener);
        Button button3 = (Button) findViewById(R.id.hqa_virtual_btn_start_stop);
        this.mBtnStart = button3;
        button3.setOnClickListener(this.mClickListener);
        Button button4 = (Button) findViewById(R.id.hqa_virtual_btn_return);
        this.mBtnReturn = button4;
        button4.setOnClickListener(this.mClickListener);
        Button button5 = (Button) findViewById(R.id.hqa_virtual_btn_run_back);
        this.mBtnRunInBack = button5;
        button5.setOnClickListener(this.mClickListener);
        this.mBtnRunInBack.setEnabled(false);
    }

    /* access modifiers changed from: private */
    public void setButtonsStatus(boolean b) {
        if (b) {
            this.mBtnStart.setText(R.string.hqa_nfc_start);
        } else {
            this.mBtnStart.setText(R.string.hqa_nfc_stop);
        }
        this.mBtnRunInBack.setEnabled(!b);
        this.mEnableBackKey = b;
        this.mBtnReturn.setEnabled(b);
        this.mBtnSelectAll.setEnabled(b);
        this.mBtnClearAll.setEnabled(b);
    }

    /* access modifiers changed from: private */
    public void changeAllSelect(boolean checked) {
        CheckBox[] checkBoxArr;
        Elog.v(NfcMainPage.TAG, "[VirtualCardFunction]changeAllSelect status is " + checked);
        int i = 0;
        while (true) {
            checkBoxArr = this.mSettingsCkBoxs;
            if (i >= checkBoxArr.length) {
                break;
            }
            checkBoxArr[i].setChecked(checked);
            i++;
        }
        if (checked) {
            checkBoxArr[4].setChecked(false);
        }
    }

    /* access modifiers changed from: private */
    public void doTestAction(Boolean bStart) {
        sendCommand(bStart);
    }

    private void sendCommand(Boolean bStart) {
        NfcEmReqRsp.NfcEmVirtualCardReq requestCmd = new NfcEmReqRsp.NfcEmVirtualCardReq();
        fillRequest(bStart, requestCmd);
        NfcClient.getInstance().sendCommand(113, requestCmd);
    }

    private void fillRequest(Boolean bStart, NfcEmReqRsp.NfcEmVirtualCardReq requestCmd) {
        int i = 2;
        int i2 = 0;
        if (bStart == null) {
            requestCmd.mAction = 2;
        } else if (bStart.booleanValue()) {
            requestCmd.mAction = 0;
        } else {
            requestCmd.mAction = 1;
        }
        requestCmd.mSupportType = (int) ((this.mSettingsCkBoxs[1].isChecked() ? 2 : 0) | 0 | this.mSettingsCkBoxs[0].isChecked() | (this.mSettingsCkBoxs[2].isChecked() ? 4 : 0) | (this.mSettingsCkBoxs[5].isChecked() ? 16 : 0));
        if (!this.mSettingsCkBoxs[3].isChecked()) {
            i = 0;
        }
        int rateVaule = i | 0;
        if (this.mSettingsCkBoxs[4].isChecked()) {
            i2 = 4;
        }
        requestCmd.mTypeFDataRate = rateVaule | i2;
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        if (id != 0) {
            return null;
        }
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.hqa_nfc_dialog_wait_message));
        dialog.setProgressStyle(0);
        dialog.setCancelable(false);
        return dialog;
    }
}
