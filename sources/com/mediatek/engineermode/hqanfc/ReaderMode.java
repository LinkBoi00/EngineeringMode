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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class ReaderMode extends Activity {
    private static final int CHECKBOXS_NUMBER = 5;
    private static final int CHECKBOX_READER_KOVIO = 4;
    private static final int CHECKBOX_READER_TYPEA = 0;
    private static final int CHECKBOX_READER_TYPEB = 1;
    private static final int CHECKBOX_READER_TYPEF = 2;
    private static final int CHECKBOX_READER_TYPEV = 3;
    private static final int DIALOG_ID_WAIT = 0;
    private static final int HANDLER_MSG_GET_NTF = 100;
    private static final int HANDLER_MSG_GET_RSP = 200;
    protected static final String KEY_READER_MODE_RSP_ARRAY = "reader_mode_rsp_array";
    protected static final String KEY_READER_MODE_RSP_NDEF = "reader_mode_rsp_ndef";
    private static final int RADIO_NUMBER = 12;
    private static final int RADIO_READER_TYPEA_106 = 0;
    private static final int RADIO_READER_TYPEA_212 = 1;
    private static final int RADIO_READER_TYPEA_424 = 2;
    private static final int RADIO_READER_TYPEA_848 = 3;
    private static final int RADIO_READER_TYPEB_106 = 4;
    private static final int RADIO_READER_TYPEB_212 = 5;
    private static final int RADIO_READER_TYPEB_424 = 6;
    private static final int RADIO_READER_TYPEB_848 = 7;
    private static final int RADIO_READER_TYPEF_212 = 8;
    private static final int RADIO_READER_TYPEF_424 = 9;
    private static final int RADIO_READER_TYPEV_166 = 10;
    private static final int RADIO_READER_TYPEV_2648 = 11;
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
            Elog.v(NfcMainPage.TAG, "[ReaderMode]onCheckedChanged view is " + buttonView.getText() + " value is " + checked);
            if (buttonView.equals(ReaderMode.this.mSettingsCkBoxs[0])) {
                for (int i = 0; i < ReaderMode.this.mRgTypeA.getChildCount(); i++) {
                    ReaderMode.this.mRgTypeA.getChildAt(i).setEnabled(checked);
                }
            } else if (buttonView.equals(ReaderMode.this.mSettingsCkBoxs[1])) {
                for (int i2 = 0; i2 < ReaderMode.this.mRgTypeB.getChildCount(); i2++) {
                    ReaderMode.this.mRgTypeB.getChildAt(i2).setEnabled(checked);
                }
            } else if (buttonView.equals(ReaderMode.this.mSettingsCkBoxs[2])) {
                for (int i3 = 0; i3 < ReaderMode.this.mRgTypeF.getChildCount(); i3++) {
                    ReaderMode.this.mRgTypeF.getChildAt(i3).setEnabled(checked);
                }
            } else if (buttonView.equals(ReaderMode.this.mSettingsCkBoxs[3])) {
                for (int i4 = 0; i4 < ReaderMode.this.mRgTypeVSubCarrier.getChildCount(); i4++) {
                    ReaderMode.this.mRgTypeVSubCarrier.getChildAt(i4).setEnabled(checked);
                }
                for (int i5 = 0; i5 < ReaderMode.this.mRgTypeVMode.getChildCount(); i5++) {
                    ReaderMode.this.mRgTypeVMode.getChildAt(i5).setEnabled(checked);
                }
                for (int i6 = 0; i6 < ReaderMode.this.mRgTypeVRate.getChildCount(); i6++) {
                    ReaderMode.this.mRgTypeVRate.getChildAt(i6).setEnabled(checked);
                }
            }
        }
    };
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[ReaderMode]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(ReaderMode.this.mBtnStart)) {
                ReaderMode.this.showDialog(0);
                ReaderMode readerMode = ReaderMode.this;
                readerMode.doTestAction(Boolean.valueOf(readerMode.mBtnStart.getText().equals(ReaderMode.this.getString(R.string.hqa_nfc_start))));
            } else if (arg0.equals(ReaderMode.this.mBtnSelectAll)) {
                ReaderMode.this.changeAllSelect(true);
            } else if (arg0.equals(ReaderMode.this.mBtnClearAll)) {
                ReaderMode.this.changeAllSelect(false);
            } else if (arg0.equals(ReaderMode.this.mBtnReturn)) {
                ReaderMode.this.onBackPressed();
            } else if (arg0.equals(ReaderMode.this.mBtnRunInBack)) {
                ReaderMode.this.doTestAction((Boolean) null);
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addFlags(268435456);
                intent.addCategory("android.intent.category.HOME");
                ReaderMode.this.startActivity(intent);
                boolean unused = ReaderMode.this.mRunInBack = true;
            }
        }
    };
    private boolean mEnableBackKey = true;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String toastMsg = null;
            if (200 != msg.what) {
                if (100 == msg.what) {
                    switch (ReaderMode.this.mReadermNtf.mResult) {
                        case 0:
                            toastMsg = "ReaderMode Ntf Result: CONNECT";
                            if (!ReaderMode.this.mRunInBack && (ReaderMode.this.mReadermNtf.mIsNdef == 0 || ReaderMode.this.mReadermNtf.mIsNdef == 1 || ReaderMode.this.mReadermNtf.mIsNdef == 2)) {
                                Intent intent = new Intent();
                                intent.putExtra(ReaderMode.KEY_READER_MODE_RSP_ARRAY, ReaderMode.this.mRspArray);
                                intent.putExtra(ReaderMode.KEY_READER_MODE_RSP_NDEF, ReaderMode.this.mReadermNtf.mIsNdef);
                                intent.setClass(ReaderMode.this, RwFunction.class);
                                LocalBroadcastManager.getInstance(ReaderMode.this).unregisterReceiver(ReaderMode.this.mReceiver);
                                boolean unused = ReaderMode.this.mUnregisterReceiver = true;
                                ReaderMode.this.startActivity(intent);
                                break;
                            }
                        case 1:
                            Elog.v(NfcMainPage.TAG, "[ReaderMode]Ntf Result: FAIL");
                            if (!ReaderMode.this.mRunInBack) {
                                Intent intent2 = new Intent();
                                intent2.putExtra(ReaderMode.KEY_READER_MODE_RSP_ARRAY, ReaderMode.this.mRspArray);
                                intent2.putExtra(ReaderMode.KEY_READER_MODE_RSP_NDEF, 3);
                                intent2.setClass(ReaderMode.this, RwFunction.class);
                                LocalBroadcastManager.getInstance(ReaderMode.this).unregisterReceiver(ReaderMode.this.mReceiver);
                                boolean unused2 = ReaderMode.this.mUnregisterReceiver = true;
                                ReaderMode.this.startActivity(intent2);
                                break;
                            } else {
                                toastMsg = "ReaderMode Ntf Result: FAIL";
                                break;
                            }
                        case 2:
                            toastMsg = "ReaderMode Ntf Result: DISCONNECT";
                            break;
                        default:
                            Elog.v(NfcMainPage.TAG, "ReaderMode Ntf Result: ERROR");
                            if (!ReaderMode.this.mRunInBack) {
                                Intent defaultIntent = new Intent();
                                defaultIntent.putExtra(ReaderMode.KEY_READER_MODE_RSP_ARRAY, ReaderMode.this.mRspArray);
                                defaultIntent.putExtra(ReaderMode.KEY_READER_MODE_RSP_NDEF, 3);
                                defaultIntent.setClass(ReaderMode.this, RwFunction.class);
                                LocalBroadcastManager.getInstance(ReaderMode.this).unregisterReceiver(ReaderMode.this.mReceiver);
                                boolean unused3 = ReaderMode.this.mUnregisterReceiver = true;
                                ReaderMode.this.startActivity(defaultIntent);
                                break;
                            } else {
                                toastMsg = "Tag is not NDEF format";
                                break;
                            }
                    }
                }
            } else {
                ReaderMode.this.dismissDialog(0);
                switch (ReaderMode.this.mResponse.mResult) {
                    case 0:
                        toastMsg = "ReaderMode Rsp Result: SUCCESS";
                        if (!ReaderMode.this.mBtnStart.getText().equals(ReaderMode.this.getString(R.string.hqa_nfc_start))) {
                            ReaderMode.this.setButtonsStatus(true);
                            break;
                        } else {
                            ReaderMode.this.setButtonsStatus(false);
                            break;
                        }
                    case 1:
                        toastMsg = "ReaderMode Rsp Result: FAIL";
                        break;
                    case 10:
                        toastMsg = "ReaderMode Rsp Result: NOT_SUPPORT";
                        break;
                    case 32:
                        toastMsg = "ReaderMode Rsp Result: INVALID_NDEF_FORMAT";
                        break;
                    case 33:
                        toastMsg = "ReaderMode Rsp Result: INVALID_FORMAT";
                        break;
                    case 34:
                        toastMsg = "ReaderMode Rsp Result: NDEF_EOF_REACHED";
                        break;
                    default:
                        toastMsg = "ReaderMode Rsp Result: ERROR";
                        break;
                }
            }
            if (toastMsg != null) {
                Elog.v(NfcMainPage.TAG, "Toast: " + toastMsg);
                Toast.makeText(ReaderMode.this, toastMsg, 0).show();
            }
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsReadermNtf mReadermNtf;
    /* access modifiers changed from: private */
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Elog.v(NfcMainPage.TAG, "[ReaderMode]mReceiver onReceive: " + action);
            byte[] unused = ReaderMode.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
            if ("com.mediatek.hqanfc.102".equals(action)) {
                if (ReaderMode.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(ReaderMode.this.mRspArray);
                    NfcEmReqRsp.NfcEmAlsReadermRsp unused2 = ReaderMode.this.mResponse = new NfcEmReqRsp.NfcEmAlsReadermRsp();
                    ReaderMode.this.mResponse.readRaw(buffer);
                    ReaderMode.this.mHandler.sendEmptyMessage(200);
                }
            } else if (!"com.mediatek.hqanfc.118".equals(action)) {
                Elog.v(NfcMainPage.TAG, "[ReaderMode]Other response");
            } else if (ReaderMode.this.mRspArray != null) {
                ByteBuffer buffer2 = ByteBuffer.wrap(ReaderMode.this.mRspArray);
                NfcEmReqRsp.NfcEmAlsReadermNtf unused3 = ReaderMode.this.mReadermNtf = new NfcEmReqRsp.NfcEmAlsReadermNtf();
                ReaderMode.this.mReadermNtf.readRaw(buffer2);
                ReaderMode.this.mHandler.sendEmptyMessage(100);
            }
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsReadermRsp mResponse;
    /* access modifiers changed from: private */
    public RadioGroup mRgTypeA;
    /* access modifiers changed from: private */
    public RadioGroup mRgTypeB;
    /* access modifiers changed from: private */
    public RadioGroup mRgTypeF;
    /* access modifiers changed from: private */
    public RadioGroup mRgTypeVMode;
    /* access modifiers changed from: private */
    public RadioGroup mRgTypeVRate;
    /* access modifiers changed from: private */
    public RadioGroup mRgTypeVSubCarrier;
    /* access modifiers changed from: private */
    public byte[] mRspArray;
    /* access modifiers changed from: private */
    public boolean mRunInBack = false;
    /* access modifiers changed from: private */
    public CheckBox[] mSettingsCkBoxs = new CheckBox[5];
    private RadioButton[] mSettingsRadioButtons = new RadioButton[12];
    /* access modifiers changed from: private */
    public boolean mUnregisterReceiver = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Elog.v(NfcMainPage.TAG, "[ReaderMode]onCreate");
        setContentView(R.layout.hqa_nfc_reader_mode);
        initComponents();
        changeAllSelect(true);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.102");
        filter.addAction("com.mediatek.hqanfc.118");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        Elog.v(NfcMainPage.TAG, "[ReaderMode]onStop");
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Elog.v(NfcMainPage.TAG, "[ReaderMode]onStart");
        this.mRunInBack = false;
        if (this.mUnregisterReceiver) {
            Elog.v(NfcMainPage.TAG, "register receiver");
            this.mUnregisterReceiver = false;
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.mediatek.hqanfc.102");
            filter.addAction("com.mediatek.hqanfc.118");
            LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
        }
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Elog.v(NfcMainPage.TAG, "[ReaderMode]onDestroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    public void onBackPressed() {
        if (this.mEnableBackKey) {
            super.onBackPressed();
        }
    }

    private void initComponents() {
        Elog.v(NfcMainPage.TAG, "[ReaderMode]initComponents");
        this.mSettingsCkBoxs[0] = (CheckBox) findViewById(R.id.hqa_readermode_cb_typea);
        this.mSettingsCkBoxs[0].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[1] = (CheckBox) findViewById(R.id.hqa_readermode_cb_typeb);
        this.mSettingsCkBoxs[1].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[2] = (CheckBox) findViewById(R.id.hqa_readermode_cb_typef);
        this.mSettingsCkBoxs[2].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[3] = (CheckBox) findViewById(R.id.hqa_readermode_cb_typev);
        this.mSettingsCkBoxs[3].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsRadioButtons[0] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typea_106);
        this.mSettingsRadioButtons[1] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typea_212);
        this.mSettingsRadioButtons[2] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typea_424);
        this.mSettingsRadioButtons[3] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typea_848);
        this.mSettingsRadioButtons[4] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typeb_106);
        this.mSettingsRadioButtons[5] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typeb_212);
        this.mSettingsRadioButtons[6] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typeb_424);
        this.mSettingsRadioButtons[7] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typeb_848);
        this.mSettingsRadioButtons[8] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typef_212);
        this.mSettingsRadioButtons[9] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typef_424);
        this.mSettingsRadioButtons[10] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typev_166);
        this.mSettingsRadioButtons[11] = (RadioButton) findViewById(R.id.hqa_readermode_rb_typev_2648);
        this.mRgTypeA = (RadioGroup) findViewById(R.id.hqa_readermode_rg_typea);
        this.mRgTypeB = (RadioGroup) findViewById(R.id.hqa_readermode_rg_typeb);
        this.mRgTypeF = (RadioGroup) findViewById(R.id.hqa_readermode_rg_typef);
        this.mRgTypeVSubCarrier = (RadioGroup) findViewById(R.id.hqa_readermode_rg_typev_subcarrier);
        this.mRgTypeVMode = (RadioGroup) findViewById(R.id.hqa_readermode_rg_typev_mode);
        this.mRgTypeVRate = (RadioGroup) findViewById(R.id.hqa_readermode_rg_typev_rate);
        this.mSettingsCkBoxs[4] = (CheckBox) findViewById(R.id.hqa_readermode_cb_kovio);
        Button button = (Button) findViewById(R.id.hqa_readermode_btn_select_all);
        this.mBtnSelectAll = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_readermode_btn_clear_all);
        this.mBtnClearAll = button2;
        button2.setOnClickListener(this.mClickListener);
        Button button3 = (Button) findViewById(R.id.hqa_readermode_btn_start_stop);
        this.mBtnStart = button3;
        button3.setOnClickListener(this.mClickListener);
        Button button4 = (Button) findViewById(R.id.hqa_readermode_btn_return);
        this.mBtnReturn = button4;
        button4.setOnClickListener(this.mClickListener);
        Button button5 = (Button) findViewById(R.id.hqa_readermode_btn_run_back);
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
        Elog.v(NfcMainPage.TAG, "[ReaderMode]changeDisplay status is " + checked);
        int i = 0;
        while (true) {
            CheckBox[] checkBoxArr = this.mSettingsCkBoxs;
            if (i >= checkBoxArr.length) {
                break;
            }
            checkBoxArr[i].setChecked(checked);
            i++;
        }
        if (checked) {
            this.mRgTypeA.check(R.id.hqa_readermode_rb_typea_106);
            this.mRgTypeB.check(R.id.hqa_readermode_rb_typeb_106);
            this.mRgTypeF.check(R.id.hqa_readermode_rb_typef_212);
            this.mRgTypeVSubCarrier.check(R.id.hqa_readermode_rb_typev_dual_subcarrier);
            this.mRgTypeVMode.check(R.id.hqa_readermode_rb_typev_mode_4);
            this.mRgTypeVRate.check(R.id.hqa_readermode_rb_typev_166);
        }
    }

    /* access modifiers changed from: private */
    public void doTestAction(Boolean bStart) {
        sendCommand(bStart);
    }

    private void sendCommand(Boolean bStart) {
        NfcEmReqRsp.NfcEmAlsReadermReq requestCmd = new NfcEmReqRsp.NfcEmAlsReadermReq();
        fillRequest(bStart, requestCmd);
        NfcClient.getInstance().sendCommand(101, requestCmd);
    }

    private void fillRequest(Boolean bStart, NfcEmReqRsp.NfcEmAlsReadermReq requestCmd) {
        if (bStart == null) {
            requestCmd.mAction = 2;
        } else if (bStart.booleanValue()) {
            requestCmd.mAction = 0;
        } else {
            requestCmd.mAction = 1;
        }
        CheckBox[] checkBoxArr = this.mSettingsCkBoxs;
        requestCmd.mSupportType = NfcCommand.BitMapValue.getTypeValue(new CheckBox[]{checkBoxArr[0], checkBoxArr[1], checkBoxArr[2], checkBoxArr[3], checkBoxArr[4]});
        RadioButton[] radioButtonArr = this.mSettingsRadioButtons;
        requestCmd.mTypeADataRate = NfcCommand.BitMapValue.getTypeAbDataRateValue(new RadioButton[]{radioButtonArr[0], radioButtonArr[1], radioButtonArr[2], radioButtonArr[3]});
        RadioButton[] radioButtonArr2 = this.mSettingsRadioButtons;
        requestCmd.mTypeBDataRate = NfcCommand.BitMapValue.getTypeAbDataRateValue(new RadioButton[]{radioButtonArr2[4], radioButtonArr2[5], radioButtonArr2[6], radioButtonArr2[7]});
        RadioButton[] radioButtonArr3 = this.mSettingsRadioButtons;
        requestCmd.mTypeFDataRate = NfcCommand.BitMapValue.getTypeFDataRateValue(new RadioButton[]{radioButtonArr3[8], radioButtonArr3[9]});
        RadioButton[] radioButtonArr4 = this.mSettingsRadioButtons;
        requestCmd.mTypeVDataRate = NfcCommand.BitMapValue.getTypeVDataRateValue(new RadioButton[]{radioButtonArr4[10], radioButtonArr4[11]});
        if (this.mRgTypeVSubCarrier.getCheckedRadioButtonId() == R.id.hqa_readermode_rb_typev_subcarrier) {
            requestCmd.mTypeVSubcarrier = 0;
        } else {
            requestCmd.mTypeVSubcarrier = 1;
        }
        if (this.mRgTypeVMode.getCheckedRadioButtonId() == R.id.hqa_readermode_rb_typev_mode_4) {
            requestCmd.mTypeVCodingMode = 0;
        } else {
            requestCmd.mTypeVCodingMode = 1;
        }
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
