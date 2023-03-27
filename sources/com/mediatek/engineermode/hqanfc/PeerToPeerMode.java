package com.mediatek.engineermode.hqanfc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class PeerToPeerMode extends Activity {
    private static final int CHECKBOX_ACTIVE_MODE = 3;
    private static final int CHECKBOX_DISABLE_CARD = 6;
    private static final int CHECKBOX_INITIATOR = 4;
    private static final int CHECKBOX_NUMBER = 7;
    private static final int CHECKBOX_PASSIVE_MODE = 2;
    private static final int CHECKBOX_TARGET = 5;
    private static final int CHECKBOX_TYPEA = 0;
    private static final int CHECKBOX_TYPEF = 1;
    private static final int DIALOG_ID_RESULT = 0;
    private static final int DIALOG_ID_WAIT = 1;
    private static final int HANDLER_MSG_GET_NTF = 201;
    private static final int HANDLER_MSG_GET_RSP = 200;
    protected static final String KEY_P2P_RSP_ARRAY = "p2p_rsp_array";
    private static final int RADIO_NUMBER = 6;
    private static final int RADIO_P2P_TYPEA_106 = 0;
    private static final int RADIO_P2P_TYPEA_212 = 1;
    private static final int RADIO_P2P_TYPEA_424 = 2;
    private static final int RADIO_P2P_TYPEA_848 = 3;
    private static final int RADIO_P2P_TYPEF_212 = 4;
    private static final int RADIO_P2P_TYPEF_424 = 5;
    /* access modifiers changed from: private */
    public AlertDialog mAlertDialog = null;
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
            Elog.v(NfcMainPage.TAG, "[PeerToPeerMode]onCheckedChanged view is " + buttonView.getText() + " value is " + checked);
            if (buttonView.equals(PeerToPeerMode.this.mSettingsCkBoxs[0])) {
                for (int i = 0; i < PeerToPeerMode.this.mRgTypeA.getChildCount(); i++) {
                    PeerToPeerMode.this.mRgTypeA.getChildAt(i).setEnabled(checked);
                }
            } else if (buttonView.equals(PeerToPeerMode.this.mSettingsCkBoxs[1])) {
                for (int i2 = 0; i2 < PeerToPeerMode.this.mRgTypeF.getChildCount(); i2++) {
                    PeerToPeerMode.this.mRgTypeF.getChildAt(i2).setEnabled(checked);
                }
            }
        }
    };
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[PeerToPeerMode]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(PeerToPeerMode.this.mBtnStart)) {
                if (!PeerToPeerMode.this.checkRoleSelect()) {
                    Toast.makeText(PeerToPeerMode.this, R.string.hqa_nfc_p2p_role_tip, 1).show();
                    return;
                }
                PeerToPeerMode.this.showDialog(1);
                PeerToPeerMode peerToPeerMode = PeerToPeerMode.this;
                peerToPeerMode.doTestAction(Boolean.valueOf(peerToPeerMode.mBtnStart.getText().equals(PeerToPeerMode.this.getString(R.string.hqa_nfc_start))));
            } else if (arg0.equals(PeerToPeerMode.this.mBtnSelectAll)) {
                PeerToPeerMode.this.changeAllSelect(true);
            } else if (arg0.equals(PeerToPeerMode.this.mBtnClearAll)) {
                PeerToPeerMode.this.changeAllSelect(false);
            } else if (arg0.equals(PeerToPeerMode.this.mBtnReturn)) {
                PeerToPeerMode.this.onBackPressed();
            } else if (arg0.equals(PeerToPeerMode.this.mBtnRunInBack)) {
                PeerToPeerMode.this.doTestAction((Boolean) null);
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addFlags(268435456);
                intent.addCategory("android.intent.category.HOME");
                PeerToPeerMode.this.startActivity(intent);
            }
        }
    };
    private boolean mEnableBackKey = true;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String toastMsg = null;
            if (201 != msg.what) {
                if (200 == msg.what) {
                    PeerToPeerMode.this.dismissDialog(1);
                    switch (PeerToPeerMode.this.mP2pRsp.mResult) {
                        case 0:
                            toastMsg = "P2P Mode Rsp Result: SUCCESS";
                            if (!PeerToPeerMode.this.mBtnStart.getText().equals(PeerToPeerMode.this.getString(R.string.hqa_nfc_start))) {
                                PeerToPeerMode.this.setButtonsStatus(true);
                                break;
                            } else {
                                PeerToPeerMode.this.setButtonsStatus(false);
                                break;
                            }
                        case 1:
                            toastMsg = "P2P Mode Rsp Result: FAIL";
                            break;
                        default:
                            toastMsg = "P2P Mode Rsp Result: ERROR";
                            break;
                    }
                }
            } else {
                switch (PeerToPeerMode.this.mP2pNtf.mResult) {
                    case 0:
                        if (PeerToPeerMode.this.mAlertDialog != null) {
                            PeerToPeerMode.this.mAlertDialog.dismiss();
                            break;
                        }
                        break;
                    case 1:
                        PeerToPeerMode.this.showDialog(0);
                        break;
                    default:
                        toastMsg = "P2P Data Exchange is ERROR";
                        break;
                }
            }
            Toast.makeText(PeerToPeerMode.this, toastMsg, 0).show();
        }
    };
    private String mNtfContent;
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsP2pNtf mP2pNtf;
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsP2pRsp mP2pRsp;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Elog.v(NfcMainPage.TAG, "[PeerToPeerMode]mReceiver onReceive: " + action);
            byte[] unused = PeerToPeerMode.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
            if ("com.mediatek.hqanfc.119".equals(action)) {
                if (PeerToPeerMode.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(PeerToPeerMode.this.mRspArray);
                    NfcEmReqRsp.NfcEmAlsP2pNtf unused2 = PeerToPeerMode.this.mP2pNtf = new NfcEmReqRsp.NfcEmAlsP2pNtf();
                    PeerToPeerMode.this.mP2pNtf.readRaw(buffer);
                    PeerToPeerMode.this.mHandler.sendEmptyMessage(201);
                }
            } else if (!"com.mediatek.hqanfc.106".equals(action)) {
                Elog.v(NfcMainPage.TAG, "[PeerToPeerMode]Other response");
            } else if (PeerToPeerMode.this.mRspArray != null) {
                ByteBuffer buffer2 = ByteBuffer.wrap(PeerToPeerMode.this.mRspArray);
                NfcEmReqRsp.NfcEmAlsP2pRsp unused3 = PeerToPeerMode.this.mP2pRsp = new NfcEmReqRsp.NfcEmAlsP2pRsp();
                PeerToPeerMode.this.mP2pRsp.readRaw(buffer2);
                PeerToPeerMode.this.mHandler.sendEmptyMessage(200);
            }
        }
    };
    /* access modifiers changed from: private */
    public RadioGroup mRgTypeA;
    /* access modifiers changed from: private */
    public RadioGroup mRgTypeF;
    /* access modifiers changed from: private */
    public byte[] mRspArray;
    /* access modifiers changed from: private */
    public CheckBox[] mSettingsCkBoxs = new CheckBox[7];
    private RadioButton[] mSettingsRadioButtons = new RadioButton[6];

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_p2p_mode);
        initComponents();
        changeAllSelect(true);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.106");
        filter.addAction("com.mediatek.hqanfc.119");
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
        Elog.v(NfcMainPage.TAG, "[PeerToPeerMode]initComponents");
        this.mSettingsCkBoxs[0] = (CheckBox) findViewById(R.id.hqa_p2pmode_cb_typea);
        this.mSettingsCkBoxs[0].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[1] = (CheckBox) findViewById(R.id.hqa_p2pmode_cb_typef);
        this.mSettingsCkBoxs[1].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[2] = (CheckBox) findViewById(R.id.hqa_p2pmode_cb_passive_mode);
        this.mSettingsCkBoxs[3] = (CheckBox) findViewById(R.id.hqa_p2pmode_cb_active_mode);
        this.mSettingsCkBoxs[4] = (CheckBox) findViewById(R.id.hqa_p2pmode_cb_initiator);
        this.mSettingsCkBoxs[5] = (CheckBox) findViewById(R.id.hqa_p2pmode_cb_target);
        this.mSettingsCkBoxs[6] = (CheckBox) findViewById(R.id.hqa_p2pmode_cb_disable_card_emul);
        this.mSettingsRadioButtons[0] = (RadioButton) findViewById(R.id.hqa_p2pmode_rb_typea_106);
        this.mSettingsRadioButtons[1] = (RadioButton) findViewById(R.id.hqa_p2pmode_rb_typea_212);
        this.mSettingsRadioButtons[2] = (RadioButton) findViewById(R.id.hqa_p2pmode_rb_typea_424);
        this.mSettingsRadioButtons[3] = (RadioButton) findViewById(R.id.hqa_p2pmode_rb_typea_848);
        this.mSettingsRadioButtons[4] = (RadioButton) findViewById(R.id.hqa_p2pmode_rb_typef_212);
        this.mSettingsRadioButtons[5] = (RadioButton) findViewById(R.id.hqa_p2pmode_rb_typef_424);
        this.mRgTypeA = (RadioGroup) findViewById(R.id.hqa_p2pmode_rg_typea);
        this.mRgTypeF = (RadioGroup) findViewById(R.id.hqa_p2pmode_rg_typef);
        Button button = (Button) findViewById(R.id.hqa_p2pmode_btn_select_all);
        this.mBtnSelectAll = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_p2pmode_btn_clear_all);
        this.mBtnClearAll = button2;
        button2.setOnClickListener(this.mClickListener);
        Button button3 = (Button) findViewById(R.id.hqa_p2pmode_btn_start_stop);
        this.mBtnStart = button3;
        button3.setOnClickListener(this.mClickListener);
        Button button4 = (Button) findViewById(R.id.hqa_p2pmode_btn_return);
        this.mBtnReturn = button4;
        button4.setOnClickListener(this.mClickListener);
        Button button5 = (Button) findViewById(R.id.hqa_p2pmode_btn_run_back);
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
        this.mEnableBackKey = b;
        this.mBtnRunInBack.setEnabled(!b);
        this.mBtnReturn.setEnabled(b);
        this.mBtnSelectAll.setEnabled(b);
        this.mBtnClearAll.setEnabled(b);
    }

    /* access modifiers changed from: private */
    public void changeAllSelect(boolean checked) {
        CheckBox[] checkBoxArr;
        Elog.v(NfcMainPage.TAG, "[PeerToPeerMode]changeAllSelect status is " + checked);
        int i = 0;
        while (true) {
            checkBoxArr = this.mSettingsCkBoxs;
            if (i >= checkBoxArr.length) {
                break;
            }
            checkBoxArr[i].setChecked(checked);
            i++;
        }
        checkBoxArr[6].setChecked(false);
        if (checked) {
            this.mRgTypeA.check(R.id.hqa_p2pmode_rb_typea_106);
            this.mRgTypeF.check(R.id.hqa_p2pmode_rb_typef_212);
        }
    }

    /* access modifiers changed from: private */
    public void doTestAction(Boolean bStart) {
        sendCommand(bStart);
    }

    private void sendCommand(Boolean bStart) {
        NfcEmReqRsp.NfcEmAlsP2pReq requestCmd = new NfcEmReqRsp.NfcEmAlsP2pReq();
        fillRequest(bStart, requestCmd);
        NfcClient.getInstance().sendCommand(105, requestCmd);
    }

    private void fillRequest(Boolean bStart, NfcEmReqRsp.NfcEmAlsP2pReq requestCmd) {
        int i = 1;
        int i2 = 2;
        if (bStart == null) {
            requestCmd.mAction = 2;
        } else if (bStart.booleanValue()) {
            requestCmd.mAction = 0;
        } else {
            requestCmd.mAction = 1;
        }
        requestCmd.mSupportType = (int) (0 | this.mSettingsCkBoxs[0].isChecked() | (this.mSettingsCkBoxs[1].isChecked() ? 4 : 0));
        RadioButton[] radioButtonArr = this.mSettingsRadioButtons;
        requestCmd.mTypeADataRate = NfcCommand.BitMapValue.getTypeAbDataRateValue(new RadioButton[]{radioButtonArr[0], radioButtonArr[1], radioButtonArr[2], radioButtonArr[3]});
        RadioButton[] radioButtonArr2 = this.mSettingsRadioButtons;
        requestCmd.mTypeFDataRate = NfcCommand.BitMapValue.getTypeFDataRateValue(new RadioButton[]{radioButtonArr2[4], radioButtonArr2[5]});
        if (!this.mSettingsCkBoxs[6].isChecked()) {
            i = 0;
        }
        requestCmd.mIsDisableCardM = i;
        requestCmd.mRole = (int) (0 | this.mSettingsCkBoxs[4].isChecked() | (this.mSettingsCkBoxs[5].isChecked() ? 2 : 0));
        int temp = 0 | this.mSettingsCkBoxs[2].isChecked();
        if (!this.mSettingsCkBoxs[3].isChecked()) {
            i2 = 0;
        }
        requestCmd.mMode = (int) (temp | i2);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        if (1 == id) {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage(getString(R.string.hqa_nfc_dialog_wait_message));
            dialog.setProgressStyle(0);
            dialog.setCancelable(false);
            return dialog;
        } else if (id != 0) {
            return null;
        } else {
            AlertDialog create = new AlertDialog.Builder(this).setTitle(R.string.hqa_nfc_p2p_mode_ntf_title).setMessage("P2P link is up").setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            this.mAlertDialog = create;
            return create;
        }
    }

    /* access modifiers changed from: private */
    public boolean checkRoleSelect() {
        boolean result = true;
        if (!this.mSettingsCkBoxs[4].isChecked() && !this.mSettingsCkBoxs[5].isChecked()) {
            result = false;
        }
        if (this.mSettingsCkBoxs[2].isChecked() || this.mSettingsCkBoxs[3].isChecked()) {
            return result;
        }
        return false;
    }
}
