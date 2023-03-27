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
import android.widget.EditText;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class PnfcCommand extends Activity {
    private static final int DIALOG_ID_WAIT = 0;
    private static final String END_STR = "*";
    private static final int HANDLER_MSG_GET_RSP = 200;
    private static final String START_STR = "$PNFC";
    /* access modifiers changed from: private */
    public Button mBtnReturn;
    /* access modifiers changed from: private */
    public Button mBtnSend;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[PnfcCommand]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(PnfcCommand.this.mBtnSend)) {
                PnfcCommand.this.mResultTv.setText("");
                PnfcCommand.this.showDialog(0);
                PnfcCommand.this.sendCommand();
            } else if (arg0.equals(PnfcCommand.this.mBtnReturn)) {
                PnfcCommand.this.onBackPressed();
            }
        }
    };
    private EditText mEtPnfc;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String toastMsg;
            super.handleMessage(msg);
            if (200 == msg.what) {
                PnfcCommand.this.dismissDialog(0);
                switch (PnfcCommand.this.mResponse.mResult) {
                    case 0:
                        toastMsg = "PnfcCommand Rsp Result: SUCCESS";
                        break;
                    case 1:
                        toastMsg = "PnfcCommand Rsp Result: FAIL";
                        break;
                    default:
                        toastMsg = "PnfcCommand Rsp Result: ERROR";
                        break;
                }
                String infoMsg = new String(PnfcCommand.this.mResponse.mData);
                Elog.v(NfcMainPage.TAG, toastMsg);
                Elog.v(NfcMainPage.TAG, infoMsg);
                TextView access$300 = PnfcCommand.this.mResultTv;
                access$300.setText(toastMsg + "\n" + infoMsg);
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[PnfcCommand]mReceiver onReceive");
            if ("com.mediatek.hqanfc.116".equals(intent.getAction())) {
                byte[] unused = PnfcCommand.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (PnfcCommand.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(PnfcCommand.this.mRspArray);
                    NfcEmReqRsp.NfcEmPnfcRsp unused2 = PnfcCommand.this.mResponse = new NfcEmReqRsp.NfcEmPnfcRsp();
                    PnfcCommand.this.mResponse.readRaw(buffer);
                    PnfcCommand.this.mHandler.sendEmptyMessage(200);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "[PnfcCommand]Other response");
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmPnfcRsp mResponse;
    /* access modifiers changed from: private */
    public TextView mResultTv;
    /* access modifiers changed from: private */
    public byte[] mRspArray;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_pnfc_command);
        Button button = (Button) findViewById(R.id.hqa_pnfc_btn_return);
        this.mBtnReturn = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_pnfc_btn_send);
        this.mBtnSend = button2;
        button2.setOnClickListener(this.mClickListener);
        EditText editText = (EditText) findViewById(R.id.hqa_pnfc_et_pnfc);
        this.mEtPnfc = editText;
        editText.setSelection(0);
        this.mResultTv = (TextView) findViewById(R.id.hqa_pnfc_tv_reslut);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.116");
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
        NfcEmReqRsp.NfcEmPnfcReq requestCmd = new NfcEmReqRsp.NfcEmPnfcReq();
        fillRequest(requestCmd);
        NfcClient.getInstance().sendCommand(115, requestCmd);
    }

    private void fillRequest(NfcEmReqRsp.NfcEmPnfcReq requestCmd) {
        requestCmd.mAction = 0;
        byte[] cmdArray = (START_STR + this.mEtPnfc.getText() + END_STR).getBytes();
        System.arraycopy(cmdArray, 0, requestCmd.mData, 0, cmdArray.length);
        requestCmd.mDataLen = cmdArray.length;
    }
}
