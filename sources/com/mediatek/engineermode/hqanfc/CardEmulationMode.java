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
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class CardEmulationMode extends Activity {
    private static final int DIALOG_ID_WAIT = 0;
    private static final int HANDLER_MSG_GET_RSP = 200;
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
    private CheckBox mCbTypeB2;
    private CheckBox mCbTypeF;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(CardEmulationMode.this.mBtnStart)) {
                CardEmulationMode.this.showDialog(0);
                CardEmulationMode cardEmulationMode = CardEmulationMode.this;
                cardEmulationMode.doTestAction(Boolean.valueOf(cardEmulationMode.mBtnStart.getText().equals(CardEmulationMode.this.getString(R.string.hqa_nfc_start))));
            } else if (arg0.equals(CardEmulationMode.this.mBtnSelectAll)) {
                CardEmulationMode.this.changeAllSelect(true);
            } else if (arg0.equals(CardEmulationMode.this.mBtnClearAll)) {
                CardEmulationMode.this.changeAllSelect(false);
            } else if (arg0.equals(CardEmulationMode.this.mBtnReturn)) {
                CardEmulationMode.this.onBackPressed();
            } else if (arg0.equals(CardEmulationMode.this.mBtnRunInBack)) {
                CardEmulationMode.this.doTestAction((Boolean) null);
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addFlags(268435456);
                intent.addCategory("android.intent.category.HOME");
                CardEmulationMode.this.startActivity(intent);
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
                CardEmulationMode.this.dismissDialog(0);
                switch (CardEmulationMode.this.mResponse.mResult) {
                    case 0:
                        toastMsg = "CardEmulation Rsp Result: SUCCESS";
                        if (!CardEmulationMode.this.mBtnStart.getText().equals(CardEmulationMode.this.getString(R.string.hqa_nfc_start))) {
                            CardEmulationMode.this.setButtonsStatus(true);
                            break;
                        } else {
                            CardEmulationMode.this.setButtonsStatus(false);
                            break;
                        }
                    case 1:
                        toastMsg = "CardEmulation Rsp Result: FAIL";
                        break;
                    case NfcCommand.RspResult.NFC_STATUS_NO_SIM /*225*/:
                        toastMsg = "CardEmulation Rsp Result: No SIM";
                        break;
                    default:
                        toastMsg = "CardEmulation Rsp Result: ERROR";
                        break;
                }
                Toast.makeText(CardEmulationMode.this, toastMsg, 0).show();
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "mReceiver onReceive");
            if ("com.mediatek.hqanfc.108".equals(intent.getAction())) {
                byte[] unused = CardEmulationMode.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (CardEmulationMode.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(CardEmulationMode.this.mRspArray);
                    NfcEmReqRsp.NfcEmAlsCardmRsp unused2 = CardEmulationMode.this.mResponse = new NfcEmReqRsp.NfcEmAlsCardmRsp();
                    CardEmulationMode.this.mResponse.readRaw(buffer);
                    CardEmulationMode.this.mHandler.sendEmptyMessage(200);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "Other response");
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsCardmRsp mResponse;
    private RadioGroup mRgSwio;
    /* access modifiers changed from: private */
    public byte[] mRspArray;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_card_emulation_mode);
        initComponents();
        changeAllSelect(true);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.108");
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
        Elog.v(NfcMainPage.TAG, "initComponents");
        this.mRgSwio = (RadioGroup) findViewById(R.id.hqa_cardmode_rg_swio);
        this.mCbTypeA = (CheckBox) findViewById(R.id.hqa_cardmode_cb_typea);
        this.mCbTypeB = (CheckBox) findViewById(R.id.hqa_cardmode_cb_typeb);
        this.mCbTypeF = (CheckBox) findViewById(R.id.hqa_cardmode_cb_typef);
        this.mCbTypeB2 = (CheckBox) findViewById(R.id.hqa_cardmode_cb_typeb2);
        Button button = (Button) findViewById(R.id.hqa_cardmode_btn_select_all);
        this.mBtnSelectAll = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_cardmode_btn_clear_all);
        this.mBtnClearAll = button2;
        button2.setOnClickListener(this.mClickListener);
        Button button3 = (Button) findViewById(R.id.hqa_cardmode_btn_start_stop);
        this.mBtnStart = button3;
        button3.setOnClickListener(this.mClickListener);
        Button button4 = (Button) findViewById(R.id.hqa_cardmode_btn_return);
        this.mBtnReturn = button4;
        button4.setOnClickListener(this.mClickListener);
        Button button5 = (Button) findViewById(R.id.hqa_cardmode_btn_run_back);
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
        Elog.v(NfcMainPage.TAG, "changeDisplay status is " + checked);
        if (checked) {
            this.mRgSwio.check(R.id.hqa_cardmode_rb_swio1);
        }
        this.mCbTypeA.setChecked(checked);
        this.mCbTypeB.setChecked(checked);
        this.mCbTypeF.setChecked(checked);
        this.mCbTypeB2.setChecked(checked);
    }

    /* access modifiers changed from: private */
    public void doTestAction(Boolean bStart) {
        sendCommand(bStart);
    }

    private void sendCommand(Boolean bStart) {
        NfcEmReqRsp.NfcEmAlsCardmReq requestCmd = new NfcEmReqRsp.NfcEmAlsCardmReq();
        fillRequest(bStart, requestCmd);
        NfcClient.getInstance().sendCommand(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ, requestCmd);
    }

    private void fillRequest(Boolean bStart, NfcEmReqRsp.NfcEmAlsCardmReq requestCmd) {
        int i = 2;
        if (bStart == null) {
            requestCmd.mAction = 2;
        } else if (bStart.booleanValue()) {
            requestCmd.mAction = 0;
        } else {
            requestCmd.mAction = 1;
        }
        int temp = 0 | this.mCbTypeA.isChecked();
        if (!this.mCbTypeB.isChecked()) {
            i = 0;
        }
        requestCmd.mSupportType = (int) (i | temp | (this.mCbTypeF.isChecked() ? 4 : 0) | (this.mCbTypeB2.isChecked() ? 16 : 0));
        int temp2 = 0;
        switch (this.mRgSwio.getCheckedRadioButtonId()) {
            case R.id.hqa_cardmode_rb_swio1:
                temp2 = 1;
                break;
            case R.id.hqa_cardmode_rb_swio2:
                temp2 = 2;
                break;
            case R.id.hqa_cardmode_rb_swio_se:
                temp2 = 4;
                break;
        }
        requestCmd.mSwNum = temp2;
        requestCmd.mFgVirtualCard = 0;
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
