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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class PollingLoopMode extends Activity {
    private static final int CHECKBOXS_NUMBER = 18;
    private static final int CHECKBOX_CARD_MODE = 14;
    private static final int CHECKBOX_CARD_MODE_TYPEA = 15;
    private static final int CHECKBOX_CARD_MODE_TYPEB = 16;
    private static final int CHECKBOX_CARD_MODE_TYPEF = 17;
    private static final int CHECKBOX_P2P_ACTIVE_MODE = 10;
    private static final int CHECKBOX_P2P_DISABLE_CARD = 13;
    private static final int CHECKBOX_P2P_INITIATOR = 11;
    private static final int CHECKBOX_P2P_MODE = 6;
    private static final int CHECKBOX_P2P_PASSIVE_MODE = 9;
    private static final int CHECKBOX_P2P_TARGET = 12;
    private static final int CHECKBOX_P2P_TYPEA = 7;
    private static final int CHECKBOX_P2P_TYPEF = 8;
    private static final int CHECKBOX_READER_KOVIO = 5;
    private static final int CHECKBOX_READER_MODE = 0;
    private static final int CHECKBOX_READER_TYPEA = 1;
    private static final int CHECKBOX_READER_TYPEB = 2;
    private static final int CHECKBOX_READER_TYPEF = 3;
    private static final int CHECKBOX_READER_TYPEV = 4;
    private static final int DIALOG_ID_RESULT = 0;
    private static final int DIALOG_ID_WAIT = 1;
    private static final int HANDLER_MSG_GET_NTF = 100;
    private static final int HANDLER_MSG_GET_RSP = 200;
    private static final int RADIO_CARD_SWIO1 = 18;
    private static final int RADIO_CARD_SWIO2 = 19;
    private static final int RADIO_CARD_SWIOSE = 20;
    private static final int RADIO_NUMBER = 21;
    private static final int RADIO_P2P_TYPEA_106 = 12;
    private static final int RADIO_P2P_TYPEA_212 = 13;
    private static final int RADIO_P2P_TYPEA_424 = 14;
    private static final int RADIO_P2P_TYPEA_848 = 15;
    private static final int RADIO_P2P_TYPEF_212 = 16;
    private static final int RADIO_P2P_TYPEF_424 = 17;
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
    private CheckBox mCbTypeA;
    private CheckBox mCbTypeB;
    private CheckBox mCbTypeF;
    private final CompoundButton.OnCheckedChangeListener mCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
            Elog.v(NfcMainPage.TAG, "[PollingLoopMode]onCheckedChanged view is " + buttonView.getText() + " value is " + checked);
            if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[0])) {
                for (int i = 1; i <= 5; i++) {
                    PollingLoopMode.this.mSettingsCkBoxs[i].setEnabled(checked);
                }
                PollingLoopMode pollingLoopMode = PollingLoopMode.this;
                pollingLoopMode.setRadioGroup(pollingLoopMode.mRgTypeA, checked);
                PollingLoopMode pollingLoopMode2 = PollingLoopMode.this;
                pollingLoopMode2.setRadioGroup(pollingLoopMode2.mRgTypeB, checked);
                PollingLoopMode pollingLoopMode3 = PollingLoopMode.this;
                pollingLoopMode3.setRadioGroup(pollingLoopMode3.mRgTypeF, checked);
                PollingLoopMode pollingLoopMode4 = PollingLoopMode.this;
                pollingLoopMode4.setRadioGroup(pollingLoopMode4.mRgTypeVSubCarrier, checked);
                PollingLoopMode pollingLoopMode5 = PollingLoopMode.this;
                pollingLoopMode5.setRadioGroup(pollingLoopMode5.mRgTypeVMode, checked);
                PollingLoopMode pollingLoopMode6 = PollingLoopMode.this;
                pollingLoopMode6.setRadioGroup(pollingLoopMode6.mRgTypeVRate, checked);
            } else if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[1])) {
                PollingLoopMode pollingLoopMode7 = PollingLoopMode.this;
                pollingLoopMode7.setRadioGroup(pollingLoopMode7.mRgTypeA, checked);
            } else if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[2])) {
                PollingLoopMode pollingLoopMode8 = PollingLoopMode.this;
                pollingLoopMode8.setRadioGroup(pollingLoopMode8.mRgTypeB, checked);
            } else if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[3])) {
                PollingLoopMode pollingLoopMode9 = PollingLoopMode.this;
                pollingLoopMode9.setRadioGroup(pollingLoopMode9.mRgTypeF, checked);
            } else if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[4])) {
                PollingLoopMode pollingLoopMode10 = PollingLoopMode.this;
                pollingLoopMode10.setRadioGroup(pollingLoopMode10.mRgTypeVSubCarrier, checked);
                PollingLoopMode pollingLoopMode11 = PollingLoopMode.this;
                pollingLoopMode11.setRadioGroup(pollingLoopMode11.mRgTypeVMode, checked);
                PollingLoopMode pollingLoopMode12 = PollingLoopMode.this;
                pollingLoopMode12.setRadioGroup(pollingLoopMode12.mRgTypeVRate, checked);
            } else if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[6])) {
                for (int i2 = 7; i2 <= 13; i2++) {
                    PollingLoopMode.this.mSettingsCkBoxs[i2].setEnabled(checked);
                }
                PollingLoopMode pollingLoopMode13 = PollingLoopMode.this;
                pollingLoopMode13.setRadioGroup(pollingLoopMode13.mRgP2pTypeA, checked);
                PollingLoopMode pollingLoopMode14 = PollingLoopMode.this;
                pollingLoopMode14.setRadioGroup(pollingLoopMode14.mRgP2pTypeF, checked);
            } else if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[7])) {
                PollingLoopMode pollingLoopMode15 = PollingLoopMode.this;
                pollingLoopMode15.setRadioGroup(pollingLoopMode15.mRgP2pTypeA, checked);
            } else if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[8])) {
                PollingLoopMode pollingLoopMode16 = PollingLoopMode.this;
                pollingLoopMode16.setRadioGroup(pollingLoopMode16.mRgP2pTypeF, checked);
            } else if (buttonView.equals(PollingLoopMode.this.mSettingsCkBoxs[14])) {
                PollingLoopMode pollingLoopMode17 = PollingLoopMode.this;
                pollingLoopMode17.setRadioGroup(pollingLoopMode17.mRgCardSwio, checked);
                for (int i3 = 15; i3 <= 17; i3++) {
                    PollingLoopMode.this.mSettingsCkBoxs[i3].setEnabled(checked);
                }
            }
        }
    };
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[PollingLoopMode]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(PollingLoopMode.this.mBtnStart)) {
                if (!PollingLoopMode.this.checkRoleSelect()) {
                    Toast.makeText(PollingLoopMode.this, R.string.hqa_nfc_p2p_role_tip, 1).show();
                    return;
                }
                PollingLoopMode.this.showDialog(1);
                PollingLoopMode pollingLoopMode = PollingLoopMode.this;
                pollingLoopMode.doTestAction(Boolean.valueOf(pollingLoopMode.mBtnStart.getText().equals(PollingLoopMode.this.getString(R.string.hqa_nfc_start))));
            } else if (arg0.equals(PollingLoopMode.this.mBtnSelectAll)) {
                PollingLoopMode.this.changeAllSelect(true);
            } else if (arg0.equals(PollingLoopMode.this.mBtnClearAll)) {
                PollingLoopMode.this.changeAllSelect(false);
            } else if (arg0.equals(PollingLoopMode.this.mBtnReturn)) {
                PollingLoopMode.this.onBackPressed();
            } else if (arg0.equals(PollingLoopMode.this.mBtnRunInBack)) {
                PollingLoopMode.this.doTestAction((Boolean) null);
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addFlags(268435456);
                intent.addCategory("android.intent.category.HOME");
                PollingLoopMode.this.startActivity(intent);
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
                    switch (PollingLoopMode.this.mPollingNty.mDetectType) {
                        case 1:
                            NfcEmReqRsp.NfcEmAlsReadermNtf readermNtf = new NfcEmReqRsp.NfcEmAlsReadermNtf();
                            readermNtf.readRaw(ByteBuffer.wrap(PollingLoopMode.this.mPollingNty.mData));
                            Intent intent = new Intent();
                            intent.putExtra("reader_mode_rsp_array", PollingLoopMode.this.mPollingNty.mData);
                            intent.putExtra("reader_mode_rsp_ndef", readermNtf.mIsNdef);
                            intent.setClass(PollingLoopMode.this, RwFunction.class);
                            PollingLoopMode pollingLoopMode = PollingLoopMode.this;
                            pollingLoopMode.unregisterReceiver(pollingLoopMode.mReceiver);
                            boolean unused = PollingLoopMode.this.mUnregisterReceiver = true;
                            PollingLoopMode.this.startActivity(intent);
                            break;
                        case 2:
                            NfcEmReqRsp.NfcEmAlsCardmRsp alsCardRsp = new NfcEmReqRsp.NfcEmAlsCardmRsp();
                            alsCardRsp.readRaw(ByteBuffer.wrap(PollingLoopMode.this.mPollingNty.mData));
                            if (alsCardRsp.mResult != 0) {
                                if (1 != alsCardRsp.mResult) {
                                    if (225 != alsCardRsp.mResult) {
                                        toastMsg = "CardEmulation Rsp Result: ERROR";
                                        break;
                                    } else {
                                        toastMsg = "CardEmulation Rsp Result: No SIM";
                                        break;
                                    }
                                } else {
                                    toastMsg = "CardEmulation Rsp Result: FAIL";
                                    break;
                                }
                            } else {
                                toastMsg = "CardEmulation Rsp Result: SUCCESS";
                                break;
                            }
                        case 4:
                            NfcEmReqRsp.NfcEmAlsP2pNtf alsP2pNtf = new NfcEmReqRsp.NfcEmAlsP2pNtf();
                            alsP2pNtf.readRaw(ByteBuffer.wrap(PollingLoopMode.this.mPollingNty.mData));
                            if (1 != alsP2pNtf.mResult) {
                                if (alsP2pNtf.mResult == 0) {
                                    if (PollingLoopMode.this.mAlertDialog != null) {
                                        PollingLoopMode.this.mAlertDialog.dismiss();
                                        break;
                                    }
                                } else {
                                    toastMsg = "P2P Data Exchange is ERROR";
                                    break;
                                }
                            } else {
                                PollingLoopMode.this.showDialog(0);
                                break;
                            }
                            break;
                    }
                }
            } else {
                PollingLoopMode.this.dismissDialog(1);
                switch (PollingLoopMode.this.mPollingRsp.mResult) {
                    case 0:
                        toastMsg = "Poling Loop Mode Rsp Result: SUCCESS";
                        if (!PollingLoopMode.this.mBtnStart.getText().equals(PollingLoopMode.this.getString(R.string.hqa_nfc_start))) {
                            PollingLoopMode.this.setButtonsStatus(true);
                            break;
                        } else {
                            PollingLoopMode.this.setButtonsStatus(false);
                            break;
                        }
                    case 1:
                        toastMsg = "Poling Loop Mode Rsp Result: FAIL";
                        break;
                    case NfcCommand.RspResult.NFC_STATUS_NO_SIM:
                        toastMsg = "Poling Loop Mode Rsp Result: No SIM";
                        break;
                    default:
                        toastMsg = "Poling Loop Mode Rsp Result: ERROR";
                        break;
                }
            }
            Toast.makeText(PollingLoopMode.this, toastMsg, 0).show();
        }
    };
    private String mNtfContent;
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmPollingNty mPollingNty;
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmPollingRsp mPollingRsp;
    private RadioButton mRbPollingSelectListen;
    private RadioButton mRbPollingSelectPause;
    /* access modifiers changed from: private */
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Elog.v(NfcMainPage.TAG, "[PollingLoopMode]mReceiver onReceive: " + action);
            byte[] unused = PollingLoopMode.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
            if ("com.mediatek.hqanfc.110".equals(action)) {
                if (PollingLoopMode.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(PollingLoopMode.this.mRspArray);
                    NfcEmReqRsp.NfcEmPollingRsp unused2 = PollingLoopMode.this.mPollingRsp = new NfcEmReqRsp.NfcEmPollingRsp();
                    PollingLoopMode.this.mPollingRsp.readRaw(buffer);
                    PollingLoopMode.this.mHandler.sendEmptyMessage(200);
                }
            } else if (!"com.mediatek.hqanfc.117".equals(action)) {
                Elog.v(NfcMainPage.TAG, "[PollingLoopMode]Other response");
            } else if (PollingLoopMode.this.mRspArray != null) {
                ByteBuffer buffer2 = ByteBuffer.wrap(PollingLoopMode.this.mRspArray);
                NfcEmReqRsp.NfcEmPollingNty unused3 = PollingLoopMode.this.mPollingNty = new NfcEmReqRsp.NfcEmPollingNty();
                PollingLoopMode.this.mPollingNty.readRaw(buffer2);
                PollingLoopMode.this.mHandler.sendEmptyMessage(100);
            }
        }
    };
    /* access modifiers changed from: private */
    public RadioGroup mRgCardSwio;
    /* access modifiers changed from: private */
    public RadioGroup mRgP2pTypeA;
    /* access modifiers changed from: private */
    public RadioGroup mRgP2pTypeF;
    private RadioGroup mRgPollingSelect;
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
    public CheckBox[] mSettingsCkBoxs = new CheckBox[18];
    private RadioButton[] mSettingsRadioButtons = new RadioButton[21];
    private EditText mTvPeriod;
    /* access modifiers changed from: private */
    public boolean mUnregisterReceiver = false;

    /* access modifiers changed from: private */
    public void setRadioGroup(RadioGroup rg, boolean checked) {
        for (int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(checked);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_pollingloop_mode);
        initComponents();
        changeAllSelect(true);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.110");
        filter.addAction("com.mediatek.hqanfc.117");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Elog.v(NfcMainPage.TAG, "[PollingLoopMode]onStart");
        if (this.mUnregisterReceiver) {
            Elog.v(NfcMainPage.TAG, "register receiver");
            this.mUnregisterReceiver = false;
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.mediatek.hqanfc.110");
            filter.addAction("com.mediatek.hqanfc.117");
            LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
        }
        super.onStart();
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
        Elog.v(NfcMainPage.TAG, "[PollingLoopMode]initComponents");
        this.mRgPollingSelect = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_polling_select);
        this.mRbPollingSelectListen = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_polling_listen);
        this.mRbPollingSelectPause = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_polling_pause);
        this.mTvPeriod = (EditText) findViewById(R.id.hqa_pollingmode_et_polling_period);
        this.mSettingsCkBoxs[0] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_reader_mode);
        this.mSettingsCkBoxs[0].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[1] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_typea);
        this.mSettingsCkBoxs[1].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[2] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_typeb);
        this.mSettingsCkBoxs[2].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[3] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_typef);
        this.mSettingsCkBoxs[3].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[4] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_typev);
        this.mSettingsCkBoxs[4].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[5] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_kovio);
        this.mSettingsRadioButtons[0] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typea_106);
        this.mSettingsRadioButtons[1] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typea_212);
        this.mSettingsRadioButtons[2] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typea_424);
        this.mSettingsRadioButtons[3] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typea_848);
        this.mSettingsRadioButtons[4] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typeb_106);
        this.mSettingsRadioButtons[5] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typeb_212);
        this.mSettingsRadioButtons[6] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typeb_424);
        this.mSettingsRadioButtons[7] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typeb_848);
        this.mSettingsRadioButtons[8] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typef_212);
        this.mSettingsRadioButtons[9] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typef_424);
        this.mSettingsRadioButtons[10] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typev_166);
        this.mSettingsRadioButtons[11] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_typev_2648);
        this.mRgTypeA = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_typea);
        this.mRgTypeB = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_typeb);
        this.mRgTypeF = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_typef);
        this.mRgTypeVSubCarrier = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_typev_subcarrier);
        this.mRgTypeVMode = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_typev_mode);
        this.mRgTypeVRate = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_typev_rate);
        this.mSettingsCkBoxs[6] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_p2p_mode);
        this.mSettingsCkBoxs[6].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[7] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_p2p_typea);
        this.mSettingsCkBoxs[7].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[8] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_p2p_typef);
        this.mSettingsCkBoxs[8].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[9] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_p2p_passive_mode);
        this.mSettingsCkBoxs[10] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_p2p_active_mode);
        this.mSettingsCkBoxs[11] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_p2p_initiator);
        this.mSettingsCkBoxs[12] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_p2p_target);
        this.mSettingsCkBoxs[13] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_p2p_disable_card_emu);
        this.mSettingsRadioButtons[12] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_p2p_typea_106);
        this.mSettingsRadioButtons[13] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_p2p_typea_212);
        this.mSettingsRadioButtons[14] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_p2p_typea_424);
        this.mSettingsRadioButtons[15] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_p2p_typea_848);
        this.mSettingsRadioButtons[16] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_p2p_typef_212);
        this.mSettingsRadioButtons[17] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_p2p_typef_424);
        this.mRgP2pTypeA = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_p2p_typea);
        this.mRgP2pTypeF = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_p2p_typef);
        this.mSettingsCkBoxs[14] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_card_emu_mode);
        this.mSettingsCkBoxs[14].setOnCheckedChangeListener(this.mCheckedListener);
        this.mSettingsCkBoxs[15] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_card_emu_typea);
        this.mSettingsCkBoxs[16] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_card_emu_typeb);
        this.mSettingsCkBoxs[17] = (CheckBox) findViewById(R.id.hqa_pollingmode_cb_card_emu_typef);
        this.mSettingsRadioButtons[18] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_card_emu_swio1);
        this.mSettingsRadioButtons[19] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_card_emu_swio2);
        this.mSettingsRadioButtons[20] = (RadioButton) findViewById(R.id.hqa_pollingmode_rb_card_emu_swiose);
        this.mRgCardSwio = (RadioGroup) findViewById(R.id.hqa_pollingmode_rg_swio);
        Button button = (Button) findViewById(R.id.hqa_pollingmode_btn_select_all);
        this.mBtnSelectAll = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_pollingmode_btn_clear_all);
        this.mBtnClearAll = button2;
        button2.setOnClickListener(this.mClickListener);
        Button button3 = (Button) findViewById(R.id.hqa_pollingmode_btn_start_stop);
        this.mBtnStart = button3;
        button3.setOnClickListener(this.mClickListener);
        Button button4 = (Button) findViewById(R.id.hqa_pollingmode_btn_return);
        this.mBtnReturn = button4;
        button4.setOnClickListener(this.mClickListener);
        Button button5 = (Button) findViewById(R.id.hqa_pollingmode_btn_run_back);
        this.mBtnRunInBack = button5;
        button5.setOnClickListener(this.mClickListener);
        this.mBtnRunInBack.setEnabled(false);
        this.mRgPollingSelect.check(R.id.hqa_pollingmode_rb_polling_listen);
        this.mTvPeriod.setText("500");
        EditText editText = this.mTvPeriod;
        editText.setSelection(editText.getText().toString().length());
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
        Elog.v(NfcMainPage.TAG, "[PollingLoopMode]changeDisplay status is " + checked);
        this.mRgPollingSelect.check(R.id.hqa_pollingmode_rb_polling_listen);
        this.mTvPeriod.setText("500");
        int i = 0;
        while (true) {
            checkBoxArr = this.mSettingsCkBoxs;
            if (i >= checkBoxArr.length) {
                break;
            }
            checkBoxArr[i].setChecked(checked);
            i++;
        }
        checkBoxArr[13].setChecked(false);
        if (checked) {
            this.mRgTypeA.check(R.id.hqa_pollingmode_rb_typea_106);
            this.mRgTypeB.check(R.id.hqa_pollingmode_rb_typeb_106);
            this.mRgTypeF.check(R.id.hqa_pollingmode_rb_typef_212);
            this.mRgTypeVSubCarrier.check(R.id.hqa_pollingmode_rb_typev_dual_subcarrier);
            this.mRgTypeVMode.check(R.id.hqa_pollingmode_rb_typev_mode_4);
            this.mRgTypeVRate.check(R.id.hqa_pollingmode_rb_typev_166);
            this.mRgP2pTypeA.check(R.id.hqa_pollingmode_rb_p2p_typea_106);
            this.mRgP2pTypeF.check(R.id.hqa_pollingmode_rb_p2p_typef_212);
            this.mRgCardSwio.check(R.id.hqa_pollingmode_rb_card_emu_swio1);
        }
    }

    /* access modifiers changed from: private */
    public void doTestAction(Boolean bStart) {
        sendCommand(bStart);
    }

    private void sendCommand(Boolean bStart) {
        NfcEmReqRsp.NfcEmPollingReq requestCmd = new NfcEmReqRsp.NfcEmPollingReq();
        fillRequest(bStart, requestCmd);
        NfcClient.getInstance().sendCommand(NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ, requestCmd);
    }

    private void fillRequest(Boolean bStart, NfcEmReqRsp.NfcEmPollingReq requestCmd) {
        NfcEmReqRsp.NfcEmPollingReq nfcEmPollingReq = requestCmd;
        if (bStart == null) {
            nfcEmPollingReq.mAction = 2;
            nfcEmPollingReq.mP2pmReq.mAction = 2;
            nfcEmPollingReq.mReadermReq.mAction = 2;
            nfcEmPollingReq.mCardmReq.mAction = 2;
        } else if (bStart.booleanValue()) {
            nfcEmPollingReq.mAction = 0;
            nfcEmPollingReq.mP2pmReq.mAction = 0;
            nfcEmPollingReq.mReadermReq.mAction = 0;
            nfcEmPollingReq.mCardmReq.mAction = 0;
        } else {
            nfcEmPollingReq.mAction = 1;
            nfcEmPollingReq.mP2pmReq.mAction = 1;
            nfcEmPollingReq.mReadermReq.mAction = 1;
            nfcEmPollingReq.mCardmReq.mAction = 1;
        }
        nfcEmPollingReq.mPhase = this.mRbPollingSelectListen.isChecked() ^ true ? 1 : 0;
        try {
            nfcEmPollingReq.mPeriod = Integer.valueOf(this.mTvPeriod.getText().toString()).intValue();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please input the right Period.", 0).show();
        }
        CheckBox[] checkBoxArr = this.mSettingsCkBoxs;
        nfcEmPollingReq.mEnableFunc = NfcCommand.BitMapValue.getFunctionValue(new CheckBox[]{checkBoxArr[0], checkBoxArr[14], checkBoxArr[6]});
        int i = 4;
        nfcEmPollingReq.mP2pmReq.mSupportType = (int) (0 | this.mSettingsCkBoxs[7].isChecked() | (this.mSettingsCkBoxs[8].isChecked() ? 4 : 0));
        RadioButton[] radioButtonArr = this.mSettingsRadioButtons;
        nfcEmPollingReq.mP2pmReq.mTypeADataRate = NfcCommand.BitMapValue.getTypeAbDataRateValue(new RadioButton[]{radioButtonArr[12], radioButtonArr[13], radioButtonArr[14], radioButtonArr[15]});
        RadioButton[] radioButtonArr2 = this.mSettingsRadioButtons;
        nfcEmPollingReq.mP2pmReq.mTypeFDataRate = NfcCommand.BitMapValue.getTypeFDataRateValue(new RadioButton[]{radioButtonArr2[16], radioButtonArr2[17]});
        nfcEmPollingReq.mP2pmReq.mIsDisableCardM = this.mSettingsCkBoxs[13].isChecked() ? 1 : 0;
        nfcEmPollingReq.mP2pmReq.mRole = (int) (0 | this.mSettingsCkBoxs[11].isChecked() | (this.mSettingsCkBoxs[12].isChecked() ? 2 : 0));
        nfcEmPollingReq.mP2pmReq.mMode = (int) (0 | this.mSettingsCkBoxs[9].isChecked() | (this.mSettingsCkBoxs[10].isChecked() ? 2 : 0));
        CheckBox[] checkBoxArr2 = this.mSettingsCkBoxs;
        nfcEmPollingReq.mReadermReq.mSupportType = NfcCommand.BitMapValue.getTypeValue(new CheckBox[]{checkBoxArr2[1], checkBoxArr2[2], checkBoxArr2[3], checkBoxArr2[4], checkBoxArr2[5]});
        RadioButton[] radioButtonArr3 = this.mSettingsRadioButtons;
        nfcEmPollingReq.mReadermReq.mTypeADataRate = NfcCommand.BitMapValue.getTypeAbDataRateValue(new RadioButton[]{radioButtonArr3[0], radioButtonArr3[1], radioButtonArr3[2], radioButtonArr3[3]});
        RadioButton[] radioButtonArr4 = this.mSettingsRadioButtons;
        nfcEmPollingReq.mReadermReq.mTypeBDataRate = NfcCommand.BitMapValue.getTypeAbDataRateValue(new RadioButton[]{radioButtonArr4[4], radioButtonArr4[5], radioButtonArr4[6], radioButtonArr4[7]});
        RadioButton[] radioButtonArr5 = this.mSettingsRadioButtons;
        nfcEmPollingReq.mReadermReq.mTypeFDataRate = NfcCommand.BitMapValue.getTypeFDataRateValue(new RadioButton[]{radioButtonArr5[8], radioButtonArr5[9]});
        RadioButton[] radioButtonArr6 = this.mSettingsRadioButtons;
        nfcEmPollingReq.mReadermReq.mTypeVDataRate = NfcCommand.BitMapValue.getTypeVDataRateValue(new RadioButton[]{radioButtonArr6[10], radioButtonArr6[11]});
        if (this.mRgTypeVSubCarrier.getCheckedRadioButtonId() == R.id.hqa_pollingmode_rb_typev_subcarrier) {
            nfcEmPollingReq.mReadermReq.mTypeVSubcarrier = 0;
        } else {
            nfcEmPollingReq.mReadermReq.mTypeVSubcarrier = 1;
        }
        if (this.mRgTypeVMode.getCheckedRadioButtonId() == R.id.hqa_pollingmode_rb_typev_mode_4) {
            nfcEmPollingReq.mReadermReq.mTypeVCodingMode = 0;
        } else {
            nfcEmPollingReq.mReadermReq.mTypeVCodingMode = 1;
        }
        int cardTemp = 0 | this.mSettingsCkBoxs[15].isChecked() | (this.mSettingsCkBoxs[16].isChecked() ? 2 : 0);
        if (!this.mSettingsCkBoxs[17].isChecked()) {
            i = 0;
        }
        nfcEmPollingReq.mCardmReq.mSupportType = (int) (cardTemp | i);
        int cardTemp2 = 0;
        switch (this.mRgCardSwio.getCheckedRadioButtonId()) {
            case R.id.hqa_pollingmode_rb_card_emu_swio1:
                cardTemp2 = 1;
                break;
            case R.id.hqa_pollingmode_rb_card_emu_swio2:
                cardTemp2 = 2;
                break;
            case R.id.hqa_pollingmode_rb_card_emu_swiose:
                cardTemp2 = 4;
                break;
        }
        nfcEmPollingReq.mCardmReq.mSwNum = cardTemp2;
        nfcEmPollingReq.mCardmReq.mFgVirtualCard = 0;
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        if (1 == id) {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage(getString(R.string.hqa_nfc_dialog_wait_message));
            dialog.setProgressStyle(0);
            dialog.setCancelable(false);
            return dialog;
        } else if (id == 0) {
            return new AlertDialog.Builder(this).setTitle(R.string.hqa_nfc_p2p_mode_ntf_title).setMessage("P2P link is up").setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
        } else {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public boolean checkRoleSelect() {
        boolean result = true;
        if (!this.mSettingsCkBoxs[11].isChecked() && !this.mSettingsCkBoxs[12].isChecked()) {
            result = false;
        }
        if (this.mSettingsCkBoxs[9].isChecked() || this.mSettingsCkBoxs[10].isChecked()) {
            return result;
        }
        return false;
    }
}
