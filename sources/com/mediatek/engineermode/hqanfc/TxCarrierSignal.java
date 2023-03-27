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
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class TxCarrierSignal extends Activity {
    private static final int DIALOG_ID_WAIT = 0;
    private static final int HANDLER_MSG_GET_RSP = 200;
    /* access modifiers changed from: private */
    public Button mBtnReturn;
    /* access modifiers changed from: private */
    public Button mBtnRunInBack;
    /* access modifiers changed from: private */
    public Button mBtnStart;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[TxCarrierSignal]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(TxCarrierSignal.this.mBtnStart)) {
                TxCarrierSignal.this.showDialog(0);
                TxCarrierSignal txCarrierSignal = TxCarrierSignal.this;
                txCarrierSignal.doTestAction(Boolean.valueOf(txCarrierSignal.mBtnStart.getText().equals(TxCarrierSignal.this.getString(R.string.hqa_nfc_start))));
            } else if (arg0.equals(TxCarrierSignal.this.mBtnReturn)) {
                TxCarrierSignal.this.onBackPressed();
            } else if (arg0.equals(TxCarrierSignal.this.mBtnRunInBack)) {
                TxCarrierSignal.this.doTestAction((Boolean) null);
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addFlags(268435456);
                intent.addCategory("android.intent.category.HOME");
                TxCarrierSignal.this.startActivity(intent);
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
                TxCarrierSignal.this.dismissDialog(0);
                switch (TxCarrierSignal.this.mResponse.mResult) {
                    case 0:
                        toastMsg = "TxCarrierSignal Rsp Result: SUCCESS";
                        if (!TxCarrierSignal.this.mBtnStart.getText().equals(TxCarrierSignal.this.getString(R.string.hqa_nfc_start))) {
                            TxCarrierSignal.this.setButtonsStatus(true);
                            break;
                        } else {
                            TxCarrierSignal.this.setButtonsStatus(false);
                            break;
                        }
                    case 1:
                        toastMsg = "TxCarrierSignal Rsp Result: FAIL";
                        break;
                    default:
                        toastMsg = "TxCarrierSignal Rsp Result: ERROR";
                        break;
                }
                Toast.makeText(TxCarrierSignal.this, toastMsg, 0).show();
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[TxCarrierSignal]mReceiver onReceive");
            if ("com.mediatek.hqanfc.112".equals(intent.getAction())) {
                byte[] unused = TxCarrierSignal.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (TxCarrierSignal.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(TxCarrierSignal.this.mRspArray);
                    NfcEmReqRsp.NfcEmTxCarrAlsOnRsp unused2 = TxCarrierSignal.this.mResponse = new NfcEmReqRsp.NfcEmTxCarrAlsOnRsp();
                    TxCarrierSignal.this.mResponse.readRaw(buffer);
                    TxCarrierSignal.this.mHandler.sendEmptyMessage(200);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "[TxCarrierSignal]Other response");
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmTxCarrAlsOnRsp mResponse;
    /* access modifiers changed from: private */
    public byte[] mRspArray;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_tx_carrier_signal);
        Button button = (Button) findViewById(R.id.hqa_carrier_btn_start_stop);
        this.mBtnStart = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_carrier_btn_return);
        this.mBtnReturn = button2;
        button2.setOnClickListener(this.mClickListener);
        Button button3 = (Button) findViewById(R.id.hqa_carrier_btn_run_back);
        this.mBtnRunInBack = button3;
        button3.setOnClickListener(this.mClickListener);
        this.mBtnRunInBack.setEnabled(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.112");
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

    /* access modifiers changed from: private */
    public void setButtonsStatus(boolean b) {
        Elog.v(NfcMainPage.TAG, "[TxCarrierSignal]setButtonsStatus " + b);
        if (b) {
            this.mBtnStart.setText(R.string.hqa_nfc_start);
        } else {
            this.mBtnStart.setText(R.string.hqa_nfc_stop);
        }
        this.mBtnRunInBack.setEnabled(!b);
        this.mEnableBackKey = b;
        this.mBtnReturn.setEnabled(b);
    }

    /* access modifiers changed from: private */
    public void doTestAction(Boolean bStart) {
        sendCommand(bStart);
    }

    private void sendCommand(Boolean bStart) {
        NfcEmReqRsp.NfcEmTxCarrAlsOnReq requestCmd = new NfcEmReqRsp.NfcEmTxCarrAlsOnReq();
        fillRequest(bStart, requestCmd);
        NfcClient.getInstance().sendCommand(111, requestCmd);
    }

    private void fillRequest(Boolean bStart, NfcEmReqRsp.NfcEmTxCarrAlsOnReq requestCmd) {
        if (bStart == null) {
            requestCmd.mAction = 2;
        } else if (bStart.booleanValue()) {
            requestCmd.mAction = 0;
        } else {
            requestCmd.mAction = 1;
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
