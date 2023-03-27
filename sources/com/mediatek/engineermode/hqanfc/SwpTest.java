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
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class SwpTest extends Activity {
    private static final int DIALOG_ID_WAIT = 0;
    private static final int HANDLER_MSG_GET_RSP = 200;
    /* access modifiers changed from: private */
    public Button mBtnReturn;
    /* access modifiers changed from: private */
    public Button mBtnStart;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[SwpTest]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(SwpTest.this.mBtnStart)) {
                SwpTest.this.showDialog(0);
                SwpTest swpTest = SwpTest.this;
                swpTest.doTestAction(Boolean.valueOf(swpTest.mBtnStart.getText().equals(SwpTest.this.getString(R.string.hqa_nfc_start))));
            } else if (arg0.equals(SwpTest.this.mBtnReturn)) {
                SwpTest.this.onBackPressed();
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
                SwpTest.this.dismissDialog(0);
                switch (SwpTest.this.mResponse.mResult) {
                    case 0:
                        toastMsg = "SWP Test Rsp Result: SUCCESS";
                        if (!SwpTest.this.mBtnStart.getText().equals(SwpTest.this.getString(R.string.hqa_nfc_start))) {
                            SwpTest.this.setButtonsStatus(true);
                            break;
                        } else {
                            SwpTest.this.setButtonsStatus(false);
                            break;
                        }
                    case 1:
                        toastMsg = "SWP Test Rsp Result: FAIL";
                        break;
                    default:
                        toastMsg = "SWP Test Rsp Result: ERROR";
                        break;
                }
                Toast.makeText(SwpTest.this, toastMsg, 0).show();
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[SwpTest]mReceiver onReceive");
            if ("com.mediatek.hqanfc.203".equals(intent.getAction())) {
                byte[] unused = SwpTest.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (SwpTest.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(SwpTest.this.mRspArray);
                    NfcEmReqRsp.NfcEmSwpRsp unused2 = SwpTest.this.mResponse = new NfcEmReqRsp.NfcEmSwpRsp();
                    SwpTest.this.mResponse.readRaw(buffer);
                    SwpTest.this.mHandler.sendEmptyMessage(200);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "[SwpTest]Other response");
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmSwpRsp mResponse;
    /* access modifiers changed from: private */
    public byte[] mRspArray;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_swp_test);
        Button button = (Button) findViewById(R.id.hqa_swp_btn_start_stop);
        this.mBtnStart = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_swp_btn_return);
        this.mBtnReturn = button2;
        button2.setOnClickListener(this.mClickListener);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.203");
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
        Elog.v(NfcMainPage.TAG, "[SwpTest]setButtonsStatus " + b);
        if (b) {
            this.mBtnStart.setText(R.string.hqa_nfc_start);
        } else {
            this.mBtnStart.setText(R.string.hqa_nfc_stop);
        }
        this.mEnableBackKey = b;
        this.mBtnReturn.setEnabled(b);
    }

    /* access modifiers changed from: private */
    public void doTestAction(Boolean bStart) {
        sendCommand(bStart);
    }

    private void sendCommand(Boolean bStart) {
        NfcEmReqRsp.NfcEmSwpReq requestCmd = new NfcEmReqRsp.NfcEmSwpReq();
        fillRequest(bStart, requestCmd);
        NfcClient.getInstance().sendCommand(NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_REQ, requestCmd);
    }

    private void fillRequest(Boolean bStart, NfcEmReqRsp.NfcEmSwpReq requestCmd) {
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
