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
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class VersionQuery extends Activity {
    private static final int DIALOG_ID_WAIT = 0;
    private static final String END_STR = "*";
    private static final int HANDLER_MSG_GET_RSP = 200;
    private static final String START_STR = "$PNFC";
    /* access modifiers changed from: private */
    public Button mBtnReturn;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[VersionQuery]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(VersionQuery.this.mBtnReturn)) {
                VersionQuery.this.onBackPressed();
            }
        }
    };
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (200 == msg.what) {
                VersionQuery.this.dismissDialog(0);
                String mwVersion = new String(VersionQuery.this.mResponse.mMwVersion);
                String fwVersion = Integer.toHexString(VersionQuery.this.mResponse.mFwVersion);
                String hwVersion = Integer.toHexString(VersionQuery.this.mResponse.mHwVersion);
                Elog.v(NfcMainPage.TAG, mwVersion);
                Elog.v(NfcMainPage.TAG, String.valueOf(VersionQuery.this.mResponse.mFwVersion));
                Elog.v(NfcMainPage.TAG, String.valueOf(VersionQuery.this.mResponse.mHwVersion));
                TextView access$300 = VersionQuery.this.mTvMwVersion;
                access$300.setText(VersionQuery.this.getString(R.string.hqa_nfc_mw_version) + mwVersion);
                TextView access$400 = VersionQuery.this.mTvFwVersion;
                access$400.setText(VersionQuery.this.getString(R.string.hqa_nfc_fw_version) + fwVersion);
                TextView access$500 = VersionQuery.this.mTvHwVersion;
                access$500.setText(VersionQuery.this.getString(R.string.hqa_nfc_hw_version) + hwVersion);
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[VersionQuery]mReceiver onReceive");
            if ("com.mediatek.hqanfc.132".equals(intent.getAction())) {
                byte[] unused = VersionQuery.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (VersionQuery.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(VersionQuery.this.mRspArray);
                    NfcEmReqRsp.NfcEmVersionRsp unused2 = VersionQuery.this.mResponse = new NfcEmReqRsp.NfcEmVersionRsp();
                    VersionQuery.this.mResponse.readRaw(buffer);
                    VersionQuery.this.mHandler.sendEmptyMessage(200);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "[VersionQuery]Other response");
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmVersionRsp mResponse;
    /* access modifiers changed from: private */
    public byte[] mRspArray;
    /* access modifiers changed from: private */
    public TextView mTvFwVersion;
    /* access modifiers changed from: private */
    public TextView mTvHwVersion;
    /* access modifiers changed from: private */
    public TextView mTvMwVersion;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_version_query);
        Button button = (Button) findViewById(R.id.hqa_version_btn_return);
        this.mBtnReturn = button;
        button.setOnClickListener(this.mClickListener);
        this.mTvMwVersion = (TextView) findViewById(R.id.hqa_version_tv_mw_version);
        this.mTvFwVersion = (TextView) findViewById(R.id.hqa_version_tv_fw_version);
        this.mTvHwVersion = (TextView) findViewById(R.id.hqa_version_tv_hw_version);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.132");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        showDialog(0);
        sendCommand();
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

    private void sendCommand() {
        NfcClient.getInstance().sendCommand(131, (NfcEmReqRsp.NfcEmReq) null);
    }
}
