package com.mediatek.engineermode.hqanfc;

import android.app.Activity;
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
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class RwFunction extends Activity {
    private static final int HANDLER_MSG_GET_NTF = 100;
    private static final int HANDLER_MSG_GET_POLLING_NTF = 200;
    protected static final int HANDLER_MSG_GET_RSP = 300;
    /* access modifiers changed from: private */
    public Button mBtnFormat;
    /* access modifiers changed from: private */
    public Button mBtnRead;
    /* access modifiers changed from: private */
    public Button mBtnWrite;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[RwFunction]onClick button view is " + ((Button) arg0).getText());
            Intent intent = new Intent();
            if (arg0.equals(RwFunction.this.mBtnRead)) {
                intent.setClass(RwFunction.this, FunctionRead.class);
                intent.putExtra("parent_ui_id", 0);
                RwFunction.this.startActivity(intent);
            } else if (arg0.equals(RwFunction.this.mBtnWrite)) {
                intent.setClass(RwFunction.this, FunctionWrite.class);
                RwFunction.this.startActivity(intent);
            } else if (arg0.equals(RwFunction.this.mBtnFormat)) {
                RwFunction.this.doFormat();
            } else {
                Elog.v(NfcMainPage.TAG, "[RwFunction]onClick noting.");
            }
        }
    };
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String toastMsg = null;
            if (300 != msg.what) {
                if (100 != msg.what) {
                    if (200 == msg.what) {
                        switch (RwFunction.this.mPollingNty.mDetectType) {
                            case 1:
                                RwFunction.this.mReceivedReadermNtf.readRaw(ByteBuffer.wrap(RwFunction.this.mPollingNty.mData));
                                sendEmptyMessage(100);
                                break;
                            default:
                                toastMsg = "Please back to polling loop mode screen";
                                break;
                        }
                    }
                } else {
                    switch (RwFunction.this.mReceivedReadermNtf.mResult) {
                        case 0:
                            toastMsg = "ReaderMode Ntf Result: CONNECT";
                            if (RwFunction.this.mReceivedReadermNtf.mIsNdef == 0 || RwFunction.this.mReceivedReadermNtf.mIsNdef == 1 || RwFunction.this.mReceivedReadermNtf.mIsNdef == 2) {
                                if (RwFunction.this.mRspArray != null) {
                                    RwFunction rwFunction = RwFunction.this;
                                    rwFunction.updateButtonUI(rwFunction.mReceivedReadermNtf.mIsNdef);
                                    RwFunction.this.updateUid();
                                    break;
                                } else {
                                    toastMsg = "Not get the response";
                                    RwFunction.this.finish();
                                    break;
                                }
                            }
                        case 1:
                            toastMsg = "ReaderMode Ntf Result: FAIL";
                            if (RwFunction.this.mRspArray != null) {
                                RwFunction.this.updateButtonUI(3);
                                RwFunction.this.updateUid();
                                break;
                            } else {
                                toastMsg = "Not get the response";
                                RwFunction.this.finish();
                                break;
                            }
                        case 2:
                            toastMsg = "ReaderMode Ntf Result: DISCONNECT";
                            RwFunction.this.updateButtonUI(4);
                            RwFunction.this.mTvUid.setText("Tag disconnect...and re-polling...");
                            break;
                        default:
                            toastMsg = "Tag is not NDEF format";
                            if (RwFunction.this.mRspArray != null) {
                                RwFunction.this.updateButtonUI(3);
                                RwFunction.this.updateUid();
                                break;
                            } else {
                                toastMsg = "Not get the response";
                                RwFunction.this.finish();
                                break;
                            }
                    }
                }
            } else {
                switch (RwFunction.this.mOptRsp.mResult) {
                    case 0:
                        toastMsg = "Rw Format Rsp Result: SUCCESS";
                        RwFunction.this.mBtnFormat.setEnabled(false);
                        RwFunction.this.mBtnRead.setEnabled(true);
                        RwFunction.this.mBtnWrite.setEnabled(true);
                        break;
                    case 1:
                        toastMsg = "Rw Format Rsp Result: FAIL";
                        break;
                    default:
                        toastMsg = "Rw Format Rsp Result: ERROR";
                        break;
                }
            }
            Elog.v(NfcMainPage.TAG, "[RwFunction]" + toastMsg);
            if (toastMsg != null) {
                Toast.makeText(RwFunction.this, toastMsg, 0).show();
            }
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsReadermOptRsp mOptRsp;
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmPollingNty mPollingNty;
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsReadermNtf mReceivedReadermNtf;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[RwFunction]mReceiver onReceive");
            String action = intent.getAction();
            byte[] unused = RwFunction.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
            if ("com.mediatek.hqanfc.104".equals(action)) {
                if (RwFunction.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(RwFunction.this.mRspArray);
                    NfcEmReqRsp.NfcEmAlsReadermOptRsp unused2 = RwFunction.this.mOptRsp = new NfcEmReqRsp.NfcEmAlsReadermOptRsp();
                    RwFunction.this.mOptRsp.readRaw(buffer);
                    RwFunction.this.mHandler.sendEmptyMessage(300);
                }
            } else if ("com.mediatek.hqanfc.118".equals(action)) {
                if (RwFunction.this.mRspArray != null) {
                    RwFunction.this.mReceivedReadermNtf.readRaw(ByteBuffer.wrap(RwFunction.this.mRspArray));
                    RwFunction.this.mHandler.sendEmptyMessage(100);
                }
            } else if (!"com.mediatek.hqanfc.117".equals(action)) {
                Elog.v(NfcMainPage.TAG, "[RwFunction]Other response");
            } else if (RwFunction.this.mRspArray != null) {
                ByteBuffer buffer2 = ByteBuffer.wrap(RwFunction.this.mRspArray);
                NfcEmReqRsp.NfcEmPollingNty unused3 = RwFunction.this.mPollingNty = new NfcEmReqRsp.NfcEmPollingNty();
                RwFunction.this.mPollingNty.readRaw(buffer2);
                RwFunction.this.mHandler.sendEmptyMessage(200);
            }
        }
    };
    /* access modifiers changed from: private */
    public byte[] mRspArray;
    /* access modifiers changed from: private */
    public TextView mTvUid;
    private boolean mUnregisterReceiver = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_rw_function);
        this.mTvUid = (TextView) findViewById(R.id.hqa_nfc_rw_tv_uid);
        this.mBtnRead = (Button) findViewById(R.id.hqa_nfc_rw_btn_read);
        this.mBtnWrite = (Button) findViewById(R.id.hqa_nfc_rw_btn_write);
        this.mBtnFormat = (Button) findViewById(R.id.hqa_nfc_rw_btn_format);
        this.mBtnRead.setOnClickListener(this.mClickListener);
        this.mBtnWrite.setOnClickListener(this.mClickListener);
        this.mBtnFormat.setOnClickListener(this.mClickListener);
        Intent intent = getIntent();
        updateButtonUI(intent.getIntExtra("reader_mode_rsp_ndef", 1));
        byte[] byteArrayExtra = intent.getByteArrayExtra("reader_mode_rsp_array");
        this.mRspArray = byteArrayExtra;
        if (byteArrayExtra == null) {
            Toast.makeText(this, "Not get the response", 0).show();
            finish();
            return;
        }
        if (this.mReceivedReadermNtf == null) {
            this.mReceivedReadermNtf = new NfcEmReqRsp.NfcEmAlsReadermNtf();
        }
        this.mReceivedReadermNtf.readRaw(ByteBuffer.wrap(this.mRspArray));
        this.mHandler.sendEmptyMessage(100);
    }

    /* access modifiers changed from: private */
    public void updateButtonUI(int ndefType) {
        this.mBtnRead.setVisibility(0);
        this.mBtnFormat.setVisibility(0);
        this.mBtnWrite.setVisibility(0);
        if (ndefType == 1) {
            this.mBtnRead.setEnabled(true);
            this.mBtnWrite.setEnabled(true);
            this.mBtnFormat.setEnabled(false);
        } else if (ndefType == 0) {
            this.mBtnFormat.setEnabled(true);
            this.mBtnRead.setEnabled(false);
            this.mBtnWrite.setEnabled(false);
        } else if (ndefType == 2) {
            this.mBtnRead.setEnabled(true);
            this.mBtnFormat.setEnabled(false);
            this.mBtnWrite.setEnabled(false);
        } else if (ndefType == 3) {
            this.mBtnRead.setEnabled(false);
            this.mBtnFormat.setEnabled(false);
            this.mBtnWrite.setEnabled(false);
            Elog.v(NfcMainPage.TAG, "ReaderModeRspResult.FAIL, disabe all buttons");
        } else if (ndefType == 4) {
            this.mBtnRead.setVisibility(8);
            this.mBtnFormat.setVisibility(8);
            this.mBtnWrite.setVisibility(8);
            Elog.v(NfcMainPage.TAG, "ReaderModeRspResult.DISCONNECT, Hide all buttons");
        }
    }

    /* access modifiers changed from: private */
    public void updateUid() {
        TextView textView = this.mTvUid;
        textView.setText("UID: " + NfcCommand.DataConvert.printHexString(this.mReceivedReadermNtf.mUid, this.mReceivedReadermNtf.mUidLen));
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Elog.v(NfcMainPage.TAG, "[RwFunction]onStart");
        super.onStart();
        updateUid();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.104");
        filter.addAction("com.mediatek.hqanfc.118");
        filter.addAction("com.mediatek.hqanfc.117");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
        this.mUnregisterReceiver = false;
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        Elog.v(NfcMainPage.TAG, "[RwFunction]onStop");
        if (!this.mUnregisterReceiver) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        }
        super.onStop();
    }

    public void onBackPressed() {
        Elog.v(NfcMainPage.TAG, "[RwFunction]onBackPressed");
        this.mUnregisterReceiver = true;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Elog.v(NfcMainPage.TAG, "[RwFunction]onDestroy");
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void doFormat() {
        NfcEmReqRsp.NfcEmAlsReadermOptReq request = new NfcEmReqRsp.NfcEmAlsReadermOptReq();
        request.mAction = 2;
        NfcClient.getInstance().sendCommand(103, request);
    }
}
