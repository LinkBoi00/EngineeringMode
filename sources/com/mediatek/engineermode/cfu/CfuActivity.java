package com.mediatek.engineermode.cfu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccUtils;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.telephony.MtkTelephonyManagerEx;
import java.util.List;

public class CfuActivity extends Activity {
    private static final int EF_INVALID = 0;
    private static final int EF_VALID = 1;
    private static final int EVENT_EF_CFF_CPHS_SIM_INFO_UPDATE = 3;
    private static final int EVENT_EF_CFIS_SIM_INFO_UPDATE = 2;
    private static final int EVENT_ICC_READY = 1;
    private static final String TAG = "CFU";
    private Button btRefresh;
    CfuSimInfoData mCffCphsSimInfoData = null;
    CfuSimInfoData mCfisSimInfoData = null;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int efValid = msg.arg1;
            String info = msg.obj.toString();
            switch (msg.what) {
                case 2:
                    if (efValid == 0) {
                        CfuActivity.this.mTvEfcfisStatus.setTextColor(Color.parseColor("#a00000"));
                    } else {
                        CfuActivity.this.mTvEfcfisStatus.setTextColor(Color.parseColor("#004cc6"));
                    }
                    CfuActivity.this.mTvEfcfisStatus.setText(info);
                    return;
                case 3:
                    if (efValid == 0) {
                        CfuActivity.this.mTvEfCffCphsStatus.setTextColor(Color.parseColor("#a00000"));
                    } else {
                        CfuActivity.this.mTvEfCffCphsStatus.setTextColor(Color.parseColor("#004cc6"));
                    }
                    CfuActivity.this.mTvEfCffCphsStatus.setText(info);
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public int mIccCardType = 0;
    private String[] mIccCardTypes = {"SIM", "USIM", "UNSUPPORT"};
    private IccRecords mIccRecords = null;
    private int mSlot = 0;
    private int mSubId = -1;
    /* access modifiers changed from: private */
    public TextView mTvEfCffCphsStatus;
    /* access modifiers changed from: private */
    public TextView mTvEfcfisStatus;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cfu_activity);
        Elog.d(TAG, "onCreate");
        this.mTvEfcfisStatus = (TextView) findViewById(R.id.cfu_efcfis_status_text);
        this.mTvEfCffCphsStatus = (TextView) findViewById(R.id.cfu_efcff_cphs_status_text);
        Button button = (Button) findViewById(R.id.cfu_refresh_button);
        this.btRefresh = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Elog.d(CfuActivity.TAG, "onClick: Refresh");
                CfuActivity.this.refreshPage();
            }
        });
        createCfuSimInfoData();
        refreshPage();
    }

    /* access modifiers changed from: private */
    public void refreshPage() {
        if (ModemCategory.isSimReady(this.mSlot)) {
            loadInCardInfoBackground();
            return;
        }
        this.mTvEfcfisStatus.setTextColor(Color.parseColor("#000000"));
        this.mTvEfcfisStatus.setText("N/A");
        this.mTvEfCffCphsStatus.setTextColor(Color.parseColor("#000000"));
        this.mTvEfCffCphsStatus.setText("N/A");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Elog.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    private void createCfuSimInfoData() {
        this.mCfisSimInfoData = new CfuSimInfoData("EF_CFIS", 28619, new String[]{"7F20", "7FFF"}, 1, "linear fixed");
        this.mCffCphsSimInfoData = new CfuSimInfoData("EF_CFF_CPHS", 28435, new String[]{"7F20", "7F20"}, 1, "transparent");
    }

    private void loadInCardInfoBackground() {
        new Thread() {
            public void run() {
                CfuActivity.this.loadSubId();
                CfuActivity.this.loadIccCardType();
                if (CfuActivity.this.mIccCardType < 2) {
                    CfuActivity.this.loadCfuSimInfoData();
                    return;
                }
                CfuActivity.this.mHandler.sendMessage(CfuActivity.this.mHandler.obtainMessage(2, 0, 0, "Not Supported for this card type"));
                CfuActivity.this.mHandler.sendMessage(CfuActivity.this.mHandler.obtainMessage(3, 0, 0, "Not Supported for this card type"));
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public void loadSubId() {
        int[] subId = SubscriptionManager.getSubId(this.mSlot);
        if (subId != null) {
            for (int i = 0; i < subId.length; i++) {
                Elog.d(TAG, "subId[" + i + "]: " + subId[i]);
            }
        }
        if (subId == null || subId.length == 0 || !SubscriptionManager.isValidSubscriptionId(subId[0])) {
            Elog.e(TAG, "Invalid sub id");
        } else {
            this.mSubId = subId[0];
        }
    }

    /* access modifiers changed from: private */
    public void loadIccCardType() {
        String iccCardType = MtkTelephonyManagerEx.getDefault().getIccCardType(this.mSubId);
        if (iccCardType == null) {
            this.mIccCardType = 2;
            return;
        }
        if (iccCardType.equals("SIM")) {
            this.mIccCardType = 0;
        } else if (iccCardType.equals("USIM")) {
            this.mIccCardType = 1;
        } else {
            this.mIccCardType = 2;
        }
        Elog.d(TAG, "IccCardType: " + iccCardType + " = " + this.mIccCardType);
    }

    /* access modifiers changed from: private */
    public void loadCfuSimInfoData() {
        if (this.mCfisSimInfoData.EF_Path[this.mIccCardType].equals("x")) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(2, 0, 0, "Not Valid"));
        } else {
            List<String> data = MtkTelephonyManagerEx.getDefault().loadEFLinearFixedAll(this.mSlot, this.mCfisSimInfoData.Family, this.mCfisSimInfoData.EF_id, this.mCfisSimInfoData.EF_Path[this.mIccCardType]);
            if (data != null) {
                int j = 0;
                while (data != null && j < data.size()) {
                    Elog.d(TAG, this.mCfisSimInfoData.name + "[" + j + "] : " + data.get(j).toUpperCase());
                    j++;
                }
                byte[] cfisBytes = IccUtils.hexStringToBytes(data.get(0));
                if (cfisBytes == null || cfisBytes.length != 16) {
                    Handler handler2 = this.mHandler;
                    handler2.sendMessage(handler2.obtainMessage(2, 0, 0, "Not Valid"));
                } else {
                    Handler handler3 = this.mHandler;
                    handler3.sendMessage(handler3.obtainMessage(2, 1, 0, "Valid (" + data.get(0) + ")"));
                }
            } else {
                Elog.d(TAG, this.mCfisSimInfoData.name + ": is empty");
                Handler handler4 = this.mHandler;
                handler4.sendMessage(handler4.obtainMessage(2, 0, 0, "Not Valid"));
            }
        }
        if (this.mCffCphsSimInfoData.EF_Path[this.mIccCardType].equals("x")) {
            Handler handler5 = this.mHandler;
            handler5.sendMessage(handler5.obtainMessage(3, 0, 0, "Not Valid"));
            return;
        }
        byte[] data2 = MtkTelephonyManagerEx.getDefault().loadEFTransparent(this.mSlot, this.mCffCphsSimInfoData.Family, this.mCffCphsSimInfoData.EF_id, this.mCffCphsSimInfoData.EF_Path[this.mIccCardType]);
        if (data2 != null) {
            String cffString = IccUtils.bytesToHexString(data2);
            Elog.d(TAG, this.mCffCphsSimInfoData.name + ":" + cffString.toUpperCase());
            Handler handler6 = this.mHandler;
            handler6.sendMessage(handler6.obtainMessage(3, 1, 0, "Valid (" + cffString + ")"));
            return;
        }
        Elog.d(TAG, this.mCffCphsSimInfoData.name + ": is empty");
        Handler handler7 = this.mHandler;
        handler7.sendMessage(handler7.obtainMessage(3, 0, 0, "Not Valid"));
    }
}
