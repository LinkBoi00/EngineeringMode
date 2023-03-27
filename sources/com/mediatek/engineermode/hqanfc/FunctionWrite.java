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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcEmReqRsp;
import java.nio.ByteBuffer;

public class FunctionWrite extends Activity {
    protected static final int HANDLER_MSG_GET_RSP = 200;
    /* access modifiers changed from: private */
    public Button mBtnCancel;
    /* access modifiers changed from: private */
    public Button mBtnWrite;
    private final RadioGroup.OnCheckedChangeListener mCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Elog.v(NfcMainPage.TAG, "[FunctionWrite]onCheckedChanged checkedId is " + checkedId);
            FunctionWrite.this.checkedChange(checkedId);
        }
    };
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            Elog.v(NfcMainPage.TAG, "[FunctionWrite]onClick button view is " + ((Button) arg0).getText());
            if (arg0.equals(FunctionWrite.this.mBtnWrite)) {
                FunctionWrite.this.doWrite();
            } else if (arg0.equals(FunctionWrite.this.mBtnCancel)) {
                FunctionWrite.this.onBackPressed();
            } else {
                Elog.v(NfcMainPage.TAG, "[FunctionWrite]onClick noting.");
            }
        }
    };
    private EditText mEtCompany;
    private EditText mEtText;
    private EditText mEtUrl;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String toastMsg;
            super.handleMessage(msg);
            if (200 == msg.what) {
                switch (FunctionWrite.this.mOptRsp.mResult) {
                    case 0:
                        toastMsg = "Function Write Rsp Result: SUCCESS";
                        break;
                    case 1:
                        toastMsg = "Function Write Rsp Result: FAIL";
                        break;
                    default:
                        toastMsg = "Function Write Rsp Result: ERROR";
                        break;
                }
                Toast.makeText(FunctionWrite.this, toastMsg, 0).show();
            }
        }
    };
    /* access modifiers changed from: private */
    public NfcEmReqRsp.NfcEmAlsReadermOptRsp mOptRsp;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.v(NfcMainPage.TAG, "[FunctionWrite]mReceiver onReceive");
            if ("com.mediatek.hqanfc.104".equals(intent.getAction())) {
                byte[] unused = FunctionWrite.this.mRspArray = intent.getExtras().getByteArray(NfcCommand.MESSAGE_CONTENT_KEY);
                if (FunctionWrite.this.mRspArray != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(FunctionWrite.this.mRspArray);
                    NfcEmReqRsp.NfcEmAlsReadermOptRsp unused2 = FunctionWrite.this.mOptRsp = new NfcEmReqRsp.NfcEmAlsReadermOptRsp();
                    FunctionWrite.this.mOptRsp.readRaw(buffer);
                    FunctionWrite.this.mHandler.sendEmptyMessage(200);
                    return;
                }
                return;
            }
            Elog.v(NfcMainPage.TAG, "[FunctionWrite]Other response");
        }
    };
    private RadioGroup mRgTagType;
    /* access modifiers changed from: private */
    public byte[] mRspArray;
    private Spinner mSpLang;
    private TextView mTvCompany;
    private TextView mTvText;
    private TextView mTvUrl;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hqa_nfc_function_write);
        initComponents();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mediatek.hqanfc.104");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        super.onStop();
    }

    private void initComponents() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.hqa_write_rg_tag_type);
        this.mRgTagType = radioGroup;
        radioGroup.setOnCheckedChangeListener(this.mCheckedChangeListener);
        this.mSpLang = (Spinner) findViewById(R.id.hqa_write_sp_lang);
        this.mEtCompany = (EditText) findViewById(R.id.hqa_write_et_company);
        this.mEtUrl = (EditText) findViewById(R.id.hqa_write_et_url);
        this.mEtText = (EditText) findViewById(R.id.hqa_write_et_text);
        Button button = (Button) findViewById(R.id.hqa_write_btn_write);
        this.mBtnWrite = button;
        button.setOnClickListener(this.mClickListener);
        Button button2 = (Button) findViewById(R.id.hqa_write_btn_cancel);
        this.mBtnCancel = button2;
        button2.setOnClickListener(this.mClickListener);
        this.mTvCompany = (TextView) findViewById(R.id.hqa_write_tv_company);
        this.mTvUrl = (TextView) findViewById(R.id.hqa_write_tv_url);
        this.mTvText = (TextView) findViewById(R.id.hqa_write_tv_text);
        this.mEtUrl.setSelection(0);
        this.mRgTagType.check(R.id.hqa_write_rb_type_uri);
    }

    /* access modifiers changed from: protected */
    public void checkedChange(int checkedId) {
        switch (checkedId) {
            case R.id.hqa_write_rb_type_text:
                this.mSpLang.setEnabled(true);
                this.mEtCompany.setVisibility(8);
                this.mTvCompany.setVisibility(8);
                this.mEtText.setVisibility(0);
                this.mTvText.setVisibility(0);
                this.mEtUrl.setVisibility(8);
                this.mTvUrl.setVisibility(8);
                this.mEtText.setText("");
                EditText editText = this.mEtText;
                editText.setSelection(editText.getText().length());
                return;
            case R.id.hqa_write_rb_type_uri:
                this.mSpLang.setEnabled(false);
                this.mEtCompany.setVisibility(8);
                this.mTvCompany.setVisibility(8);
                this.mEtText.setVisibility(8);
                this.mTvText.setVisibility(8);
                this.mEtUrl.setVisibility(0);
                this.mTvUrl.setVisibility(0);
                this.mEtUrl.setText("");
                this.mEtUrl.setSelection(0);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void doWrite() {
        int tempInt;
        if (checkInput()) {
            NfcEmReqRsp.NfcEmAlsReadermOptReq request = new NfcEmReqRsp.NfcEmAlsReadermOptReq();
            request.mAction = 1;
            switch (this.mRgTagType.getCheckedRadioButtonId()) {
                case R.id.hqa_write_rb_type_text:
                    tempInt = 1;
                    break;
                case R.id.hqa_write_rb_type_uri:
                    tempInt = 0;
                    break;
                default:
                    tempInt = 3;
                    break;
            }
            request.mTagWriteNdef.mNdefType.mEnumValue = tempInt;
            request.mTagWriteNdef.mNdefLangType.mEnumValue = this.mSpLang.getSelectedItemPosition();
            switch (tempInt) {
                case 0:
                    NfcEmReqRsp.UrlT url = new NfcEmReqRsp.UrlT();
                    byte[] urlArray = this.mEtUrl.getText().toString().getBytes();
                    System.arraycopy(urlArray, 0, url.mUrlData, 0, urlArray.length);
                    url.mUrlDataLength = (short) url.mUrlData.length;
                    byte[] arrayU = url.getByteArray();
                    System.arraycopy(arrayU, 0, request.mTagWriteNdef.mNdefData.mData, 0, arrayU.length);
                    request.mTagWriteNdef.mLength = arrayU.length;
                    break;
                case 1:
                    NfcEmReqRsp.TextT text = new NfcEmReqRsp.TextT();
                    byte[] textArray = this.mEtText.getText().toString().getBytes();
                    System.arraycopy(textArray, 0, text.mData, 0, textArray.length);
                    text.mDataLength = (short) textArray.length;
                    byte[] arrayT = text.getByteArray();
                    System.arraycopy(arrayT, 0, request.mTagWriteNdef.mNdefData.mData, 0, arrayT.length);
                    request.mTagWriteNdef.mLength = arrayT.length;
                    break;
            }
            NfcClient.getInstance().sendCommand(103, request);
            return;
        }
        Toast.makeText(this, "Input error", 0).show();
    }

    private boolean checkInput() {
        if (-1 == this.mRgTagType.getCheckedRadioButtonId()) {
            return false;
        }
        return true;
    }
}
