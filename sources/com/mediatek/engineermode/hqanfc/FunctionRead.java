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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class FunctionRead extends Activity {
    protected static final String BYTE_EXTRA_STR = "byte_data";
    protected static final int HANDLER_MSG_GET_RSP = 300;
    protected static final String PARENT_EXTRA_STR = "parent_ui_id";
    /* access modifiers changed from: private */
    public Button mBtnCancel;
    /* access modifiers changed from: private */
    public Button mBtnRead;
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[FunctionRead]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(FunctionRead.this.mBtnRead)) {
                FunctionRead.this.doRead();
            } else if (arg0.equals(FunctionRead.this.mBtnCancel)) {
                FunctionRead.this.onBackPressed();
            } else {
                Elog.v(NfcMainPage.TAG, "[FunctionRead]onClick noting.");
            }
        }
    };
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String toastMsg;
            super.handleMessage(msg);
            if (300 == msg.what) {
                switch (FunctionRead.this.mOptRsp.mResult) {
                    case 0:
                        toastMsg = "Function Read Rsp Result: SUCCESS";
                        FunctionRead functionRead = FunctionRead.this;
                        functionRead.updateUi(functionRead.mOptRsp.mTagReadNdef);
                        break;
                    case 1:
                        toastMsg = "Function Read Rsp Result: FAIL";
                        break;
                    default:
                        toastMsg = "Function Read Rsp Result: ERROR";
                        break;
                }
                Toast.makeText(FunctionRead.this, toastMsg, 0).show();
            }
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsReadermOptRsp mOptRsp;
    private RadioButton mRbTypeOthers;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[FunctionRead]mReceiver onReceive");
            if ("com.mediatek.hqanfc.104".equals(intent.getAction())) {
                byte[] unused = FunctionRead.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (FunctionRead.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(FunctionRead.this.mRspArray);
                    NfcEmReqRsp.NfcEmAlsReadermOptRsp unused2 = FunctionRead.this.mOptRsp = new NfcEmReqRsp.NfcEmAlsReadermOptRsp();
                    FunctionRead.this.mOptRsp.readRaw(buffer);
                    FunctionRead.this.mHandler.sendEmptyMessage(300);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "[FunctionRead]Other response");
        }
    };
    private RadioGroup mRgTagType;
    /* access modifiers changed from: private */
    public byte[] mRspArray;
    private EditText mTvLang;
    private EditText mTvPayloadAscii;
    private EditText mTvPayloadHex;
    private EditText mTvPayloadLength;
    private EditText mTvRecordFlag;
    private EditText mTvRecordId;
    private EditText mTvRecordInf;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_function_read);
        initComponents();
        if (1 == getIntent().getIntExtra(PARENT_EXTRA_STR, 0)) {
            this.mBtnRead.setEnabled(false);
            byte[] optData = getIntent().getByteArrayExtra(BYTE_EXTRA_STR);
            NfcEmReqRsp.NfcEmAlsReadermOptRsp optRsp = new NfcEmReqRsp.NfcEmAlsReadermOptRsp();
            optRsp.readRaw(ByteBuffer.wrap(optData));
            updateUi(optRsp.mTagReadNdef);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.104");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        super.onPause();
    }

    private void initComponents() {
        this.mRgTagType = (RadioGroup) findViewById(R.id.hqa_read_rg_tag_type);
        RadioButton radioButton = (RadioButton) findViewById(R.id.hqa_read_rb_type_others);
        this.mRbTypeOthers = radioButton;
        radioButton.setVisibility(8);
        EditText editText = (EditText) findViewById(R.id.hqa_read_tv_lang);
        this.mTvLang = editText;
        editText.setInputType(0);
        EditText editText2 = (EditText) findViewById(R.id.hqa_read_tv_flag);
        this.mTvRecordFlag = editText2;
        editText2.setInputType(0);
        EditText editText3 = (EditText) findViewById(R.id.hqa_read_tv_id);
        this.mTvRecordId = editText3;
        editText3.setInputType(0);
        EditText editText4 = (EditText) findViewById(R.id.hqa_read_tv_inf);
        this.mTvRecordInf = editText4;
        editText4.setInputType(0);
        EditText editText5 = (EditText) findViewById(R.id.hqa_read_tv_length);
        this.mTvPayloadLength = editText5;
        editText5.setInputType(0);
        EditText editText6 = (EditText) findViewById(R.id.hqa_read_tv_hex);
        this.mTvPayloadHex = editText6;
        editText6.setInputType(0);
        EditText editText7 = (EditText) findViewById(R.id.hqa_read_tv_ascii);
        this.mTvPayloadAscii = editText7;
        editText7.setInputType(0);
        Button button = (Button) findViewById(R.id.hqa_read_btn_read);
        this.mBtnRead = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_read_btn_cancel);
        this.mBtnCancel = button2;
        button2.setOnClickListener(this.mClickListener);
        this.mRgTagType.check(-1);
    }

    /* access modifiers changed from: private */
    public void doRead() {
        NfcEmReqRsp.NfcEmAlsReadermOptReq request = new NfcEmReqRsp.NfcEmAlsReadermOptReq();
        request.mAction = 0;
        NfcClient.getInstance().sendCommand(103, request);
    }

    /* access modifiers changed from: private */
    public void updateUi(NfcEmReqRsp.NfcTagReadNdef info) {
        int tempInt = -1;
        switch (info.mNdefType.mEnumValue) {
            case 0:
                tempInt = R.id.hqa_read_rb_type_uri;
                break;
            case 1:
                tempInt = R.id.hqa_read_rb_type_text;
                break;
            case 2:
                tempInt = R.id.hqa_read_rb_type_smart;
                break;
            case 3:
                tempInt = R.id.hqa_read_rb_type_others;
                break;
            default:
                Elog.d(NfcMainPage.TAG, "[FunctionRead]NfcNdefType is error");
                break;
        }
        this.mRgTagType.check(tempInt);
        this.mTvRecordFlag.setText(NfcCommand.DataConvert.printHexString(info.mRecordFlags));
        this.mTvRecordId.setText(NfcCommand.DataConvert.printHexString(info.mRecordId, 0));
        this.mTvRecordInf.setText(NfcCommand.DataConvert.printHexString(info.mRecordTnf));
        this.mTvPayloadLength.setText(String.valueOf(info.mLength));
        this.mTvLang.setText(new String(info.mLang));
        this.mTvPayloadAscii.setText(new String(info.mData, 0, info.mLength));
        this.mTvPayloadHex.setText(NfcCommand.DataConvert.printHexString(info.mData, info.mLength));
    }
}
