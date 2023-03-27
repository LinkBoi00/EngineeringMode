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
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class Option extends Activity {
    private static final int DIALOG_ID_WAIT = 0;
    private static final int HANDLER_MSG_GET_RSP = 200;
    /* access modifiers changed from: private */
    public Button mBtnReturn;
    /* access modifiers changed from: private */
    public Button mBtnSet;
    private CheckBox mCbAutoCheck;
    private CheckBox mCbForceDownload;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[Option]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(Option.this.mBtnSet)) {
                Option.this.showDialog(0);
                Option.this.sendCommand();
            } else if (arg0.equals(Option.this.mBtnReturn)) {
                Option.this.onBackPressed();
            }
        }
    };
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String toastMsg;
            super.handleMessage(msg);
            if (200 == msg.what) {
                Option.this.dismissDialog(0);
                switch (Option.this.mResponse.mResult) {
                    case 0:
                        toastMsg = "Option Rsp Result: SUCCESS";
                        break;
                    case 1:
                        toastMsg = "Option Rsp Result: FAIL";
                        break;
                    default:
                        toastMsg = "Option Rsp Result: ERROR";
                        break;
                }
                Toast.makeText(Option.this, toastMsg, 0).show();
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[Option]mReceiver onReceive");
            if ("com.mediatek.hqanfc.128".equals(intent.getAction())) {
                byte[] unused = Option.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (Option.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(Option.this.mRspArray);
                    NfcEmReqRsp.NfcEmOptionRsp unused2 = Option.this.mResponse = new NfcEmReqRsp.NfcEmOptionRsp();
                    Option.this.mResponse.readRaw(buffer);
                    Option.this.mHandler.sendEmptyMessage(200);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "[Option]Other response");
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmOptionRsp mResponse;
    /* access modifiers changed from: private */
    public byte[] mRspArray;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_option);
        Button button = (Button) findViewById(R.id.hqa_option_btn_return);
        this.mBtnReturn = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_option_btn_set);
        this.mBtnSet = button2;
        button2.setOnClickListener(this.mClickListener);
        this.mCbForceDownload = (CheckBox) findViewById(R.id.hqa_option_force_download);
        CheckBox checkBox = (CheckBox) findViewById(R.id.hqa_option_auto_check);
        this.mCbAutoCheck = checkBox;
        checkBox.setChecked(true);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.128");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    public void onBackPressed() {
        super.onBackPressed();
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

    /* access modifiers changed from: private */
    public void sendCommand() {
        NfcEmReqRsp.NfcEmOptionReq requestCmd = new NfcEmReqRsp.NfcEmOptionReq();
        fillRequest(requestCmd);
        NfcClient.getInstance().sendCommand(127, requestCmd);
    }

    private void fillRequest(NfcEmReqRsp.NfcEmOptionReq requestCmd) {
        requestCmd.mForceDownload = this.mCbForceDownload.isChecked() ? (short) 1 : 0;
        requestCmd.mAutoCheck = this.mCbAutoCheck.isChecked() ? (short) 1 : 0;
    }
}
