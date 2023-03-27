package com.mediatek.engineermode.otaairplanemode;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.internal.view.SupportMenu;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.mdmcomponent.MDMContent;
import com.mediatek.engineermode.otaairplanemode.MDMCoreOperation;
import com.mediatek.mdml.Msg;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OtaAirplaneModeActivity extends Activity implements View.OnClickListener, MDMCoreOperation.IMDMSeiviceInterface {
    private static final float DEFAULT_AIRPLANE_TIMEOUT = 1.0f;
    private static final int DEFAULT_DL_TPUT_TIMEOUT = 10000;
    private static final String KEY_FLAG_ENABLE = "enable_status";
    private static final String KEY_LAST_TIME = "last_time";
    private static final int MSG_ID_DL_TPUT_0_TIMEOUT = 2;
    private static final int MSG_ID_TIMEOUT = 1;
    private static final String SHREDPRE_NAME = "OtaAirplaneMode";
    private static final String[] SubscribeMsgIdName = {MDMContent.MSG_ID_EM_EL1_STATUS_IND};
    private static final String TAG = "OtaAirplaneMode";
    private static TextView airplane_network_status;
    private static List<MdmBaseComponent> componentsArray = new ArrayList();
    /* access modifiers changed from: private */
    public static TextView mAirplaneCountTv;
    private static EditText mAirplaneLastTimeEt;
    /* access modifiers changed from: private */
    public static int mCount = 0;
    private static CheckBox mEnableFeatureCb;
    /* access modifiers changed from: private */
    public static int mLastCallState = 0;
    /* access modifiers changed from: private */
    public static ServiceState mLastSS = null;
    private static Button mLastTimeSetBt;
    private static TextView mSimCardStatusTv;
    private float mAirplanTimeout = DEFAULT_AIRPLANE_TIMEOUT;
    private SimpleDateFormat mCurrectTime = null;
    private int mDlTputTimeout = DEFAULT_DL_TPUT_TIMEOUT;
    private MsgHandler mHandler = new MsgHandler();
    private int mLastDlTput = 0;
    private PhoneStateListener mPhoneStateListener;
    private int mSimTypeToShow = 0;
    private TelephonyManager mTelephonyManager;

    static /* synthetic */ int access$1004() {
        int i = mCount + 1;
        mCount = i;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ota_airplane_mode);
        log("Enter: onCreate");
        mSimCardStatusTv = (TextView) findViewById(R.id.airplan_simcard_status);
        mLastTimeSetBt = (Button) findViewById(R.id.airplane_time_last_set);
        CheckBox checkBox = (CheckBox) findViewById(R.id.airplane_enable);
        mEnableFeatureCb = checkBox;
        checkBox.setOnCheckedChangeListener(new CheckBoxListener());
        mAirplaneCountTv = (TextView) findViewById(R.id.airplane_count);
        mAirplaneLastTimeEt = (EditText) findViewById(R.id.airplane_time_last);
        airplane_network_status = (TextView) findViewById(R.id.airplane_network_status);
        mLastTimeSetBt.setOnClickListener(this);
        this.mCurrectTime = new SimpleDateFormat("HH:mm:ss.S");
        if (!ModemCategory.isSimReady(-1)) {
            mSimCardStatusTv.setText("please insert only one card");
            log("please insert only one card");
            mLastTimeSetBt.setEnabled(false);
            mEnableFeatureCb.setEnabled(false);
            return;
        }
        log("The sim insert ");
        mSimCardStatusTv.setText("The sim insert ");
        this.mSimTypeToShow = 0;
        MdmBaseComponent components = new MdmBaseComponent();
        components.setEmComponentName(SubscribeMsgIdName);
        componentsArray.add(components);
        MDMCoreOperation.getInstance().mdmParametersSeting(componentsArray, this.mSimTypeToShow);
        MDMCoreOperation.getInstance().setOnMDMChangedListener(this);
        this.mTelephonyManager = (TelephonyManager) getSystemService("phone");
        this.mPhoneStateListener = new PhoneStateListener() {
            public void onServiceStateChanged(ServiceState serviceStatus) {
                if (OtaAirplaneModeActivity.this.is2GCSHasService(serviceStatus) != OtaAirplaneModeActivity.this.is2GCSHasService(OtaAirplaneModeActivity.mLastSS)) {
                    OtaAirplaneModeActivity.this.updateNetworkstatus("The 2G service status changed:", -16776961);
                    OtaAirplaneModeActivity otaAirplaneModeActivity = OtaAirplaneModeActivity.this;
                    otaAirplaneModeActivity.updateNetworkstatus("\tis2GCSHasService(mLastSS): " + OtaAirplaneModeActivity.this.is2GCSHasService(OtaAirplaneModeActivity.mLastSS), -16776961);
                    OtaAirplaneModeActivity otaAirplaneModeActivity2 = OtaAirplaneModeActivity.this;
                    otaAirplaneModeActivity2.updateNetworkstatus("\tis2GCSHasService(serviceState): " + OtaAirplaneModeActivity.this.is2GCSHasService(serviceStatus), -16776961);
                }
                if (OtaAirplaneModeActivity.this.is3G4GPSHasService(serviceStatus) != OtaAirplaneModeActivity.this.is3G4GPSHasService(OtaAirplaneModeActivity.mLastSS)) {
                    OtaAirplaneModeActivity.this.updateNetworkstatus("The 3G/4G service status changed:", -16776961);
                    OtaAirplaneModeActivity otaAirplaneModeActivity3 = OtaAirplaneModeActivity.this;
                    otaAirplaneModeActivity3.updateNetworkstatus("\tis3G4GPSHasService(mLastSS): " + OtaAirplaneModeActivity.this.is3G4GPSHasService(OtaAirplaneModeActivity.mLastSS), -16776961);
                    OtaAirplaneModeActivity otaAirplaneModeActivity4 = OtaAirplaneModeActivity.this;
                    otaAirplaneModeActivity4.updateNetworkstatus("\tis3G4GPSHasService(serviceState):" + OtaAirplaneModeActivity.this.is3G4GPSHasService(serviceStatus), -16776961);
                }
                if (!OtaAirplaneModeActivity.this.is2GCSHasService(serviceStatus) && OtaAirplaneModeActivity.this.is2GCSHasService(OtaAirplaneModeActivity.mLastSS)) {
                    OtaAirplaneModeActivity.this.updateNetworkstatus("For 2G CS NO SERVICE:Call OTA Request", SupportMenu.CATEGORY_MASK);
                    OtaAirplaneModeActivity.this.handleOTARequest();
                } else if (!OtaAirplaneModeActivity.this.is3G4GPSHasService(serviceStatus) && OtaAirplaneModeActivity.this.is3G4GPSHasService(OtaAirplaneModeActivity.mLastSS)) {
                    OtaAirplaneModeActivity.this.updateNetworkstatus("For 3G/4G CS NO SERVICE:Call OTA Request", SupportMenu.CATEGORY_MASK);
                    OtaAirplaneModeActivity.this.handleOTARequest();
                }
                ServiceState unused = OtaAirplaneModeActivity.mLastSS = serviceStatus;
            }

            public void onCallStateChanged(int state, String incomingNumber) {
                if (OtaAirplaneModeActivity.this.isInCall(state) != OtaAirplaneModeActivity.this.isInCall(OtaAirplaneModeActivity.mLastCallState)) {
                    OtaAirplaneModeActivity otaAirplaneModeActivity = OtaAirplaneModeActivity.this;
                    otaAirplaneModeActivity.updateNetworkstatus(OtaAirplaneModeActivity.this.getCurrectTime() + ":", -16776961);
                    OtaAirplaneModeActivity.this.updateNetworkstatus("The call status changed:", -16776961);
                    OtaAirplaneModeActivity otaAirplaneModeActivity2 = OtaAirplaneModeActivity.this;
                    otaAirplaneModeActivity2.updateNetworkstatus("\tcall status(LastStatus): " + OtaAirplaneModeActivity.this.isInCall(OtaAirplaneModeActivity.mLastCallState), -16776961);
                    OtaAirplaneModeActivity otaAirplaneModeActivity3 = OtaAirplaneModeActivity.this;
                    otaAirplaneModeActivity3.updateNetworkstatus("\tcall status(CurrentState): " + OtaAirplaneModeActivity.this.isInCall(state), -16776961);
                    if (!OtaAirplaneModeActivity.this.isInCall(state) && OtaAirplaneModeActivity.this.isInCall(OtaAirplaneModeActivity.mLastCallState)) {
                        OtaAirplaneModeActivity.this.updateNetworkstatus("For Call hangup: Call OTA Request", SupportMenu.CATEGORY_MASK);
                        OtaAirplaneModeActivity.this.handleOTARequest();
                    }
                }
                int unused = OtaAirplaneModeActivity.mLastCallState = state;
            }
        };
        float parseFloat = Float.parseFloat(EmUtils.readSharedPreferences("OtaAirplaneMode", KEY_LAST_TIME, String.valueOf(DEFAULT_AIRPLANE_TIMEOUT)));
        this.mAirplanTimeout = parseFloat;
        mAirplaneLastTimeEt.setText(String.valueOf(parseFloat));
        if (EmUtils.readSharedPreferences("OtaAirplaneMode", KEY_FLAG_ENABLE, "0").equals("1")) {
            mEnableFeatureCb.setChecked(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    /* access modifiers changed from: private */
    public String getCurrectTime() {
        return this.mCurrectTime.format(new Date());
    }

    /* access modifiers changed from: private */
    public void updateNetworkstatus(String msg, int color) {
        Spannable WordToSpan = new SpannableString(msg + "\r\n");
        WordToSpan.setSpan(new ForegroundColorSpan(color), 0, WordToSpan.length(), 33);
        airplane_network_status.append(WordToSpan);
    }

    /* access modifiers changed from: package-private */
    public void registerNetworkListen() {
        this.mTelephonyManager.listen(this.mPhoneStateListener, 33);
        MDMCoreOperation.getInstance().mdmInitialize(this);
    }

    /* access modifiers changed from: package-private */
    public void unRegisterNetworkListen() {
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        MDMCoreOperation.getInstance().mdmlUnSubscribe();
        this.mHandler.removeCallbacksAndMessages((Object) null);
    }

    /* access modifiers changed from: package-private */
    public void setupOtaRequest(boolean enable) {
        if (enable) {
            registerNetworkListen();
            airplane_network_status.setText("");
            mAirplaneCountTv.setText(String.valueOf(mCount));
            log("Enable the status listen succeed");
            EmUtils.showToast("Enable the status listen succeed");
            EmUtils.writeSharedPreferences("OtaAirplaneMode", KEY_FLAG_ENABLE, "1");
            return;
        }
        mCount = 0;
        unRegisterNetworkListen();
        log("Disable the listen succeed");
        EmUtils.showToast("Disable the listen succeed");
        EmUtils.writeSharedPreferences("OtaAirplaneMode", KEY_FLAG_ENABLE, "0");
    }

    /* access modifiers changed from: private */
    public void handleOTARequest() {
        log("handleOTARequest");
        setAirplaneMode(true);
        MsgHandler msgHandler = this.mHandler;
        msgHandler.sendMessageDelayed(msgHandler.obtainMessage(1), (long) ((int) (this.mAirplanTimeout * 1000.0f)));
    }

    /* access modifiers changed from: private */
    public void setAirplaneMode(boolean onOff) {
        updateNetworkstatus(getCurrectTime() + ":", -16711936);
        updateNetworkstatus("setAirplaneMode, onOff: " + onOff, -16711936);
        EmUtils.setAirplaneModeEnabled(onOff);
    }

    /* access modifiers changed from: private */
    public boolean is2GCSHasService(ServiceState ss) {
        if (ss == null) {
            return false;
        }
        int voiceNetworkType = ss.getVoiceNetworkType();
        if (ss.getVoiceRegState() != 0 || !is2G(voiceNetworkType)) {
            return false;
        }
        return true;
    }

    private boolean is2G(int networkType) {
        return networkType == 1 || networkType == 2 || networkType == 16 || networkType == 4 || networkType == 7;
    }

    /* access modifiers changed from: private */
    public boolean is3G4GPSHasService(ServiceState ss) {
        if (ss == null) {
            return false;
        }
        int dataNetworkType = ss.getDataNetworkType();
        if (ss.getDataRegState() != 0 || !is3G4G(dataNetworkType)) {
            return false;
        }
        return true;
    }

    private boolean is3G4G(int networkType) {
        return networkType == 3 || networkType == 8 || networkType == 9 || networkType == 10 || networkType == 13 || networkType == 15 || networkType == 17 || networkType == 5 || networkType == 6 || networkType == 12 || networkType == 14;
    }

    /* access modifiers changed from: private */
    public boolean isInCall(int state) {
        return state != 0;
    }

    public void setAirplaneSecond() {
        log("onClick, setAirplaneSecond");
        String input = mAirplaneLastTimeEt.getText().toString();
        if (input == null || input.equals("")) {
            EmUtils.showToast("Use the default value: 1s");
            return;
        }
        float parseFloat = Float.parseFloat(input);
        this.mAirplanTimeout = parseFloat;
        EmUtils.writeSharedPreferences("OtaAirplaneMode", KEY_LAST_TIME, String.valueOf(parseFloat));
        EmUtils.showToast("Set the Airplane Last time to(s):" + this.mAirplanTimeout);
        log("Set the Airplane Last time to(s): " + this.mAirplanTimeout);
    }

    /* access modifiers changed from: private */
    public void log(String s) {
        Elog.i("OtaAirplaneMode", s);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.airplane_time_last_set:
                setAirplaneSecond();
                return;
            default:
                return;
        }
    }

    public void onUpdateMDMStatus(int msg_id) {
        switch (msg_id) {
            case 0:
                log("MDM Service init done");
                MDMCoreOperation.getInstance().mdmlSubscribe();
                return;
            case 1:
                log("Subscribe message id done");
                MDMCoreOperation.getInstance().mdmlEnableSubscribe();
                return;
            case 4:
                log("UnSubscribe message id done");
                MDMCoreOperation.getInstance().mdmlClosing();
                return;
            default:
                return;
        }
    }

    public void onUpdateMDMData(String name, Msg data) {
        if (name.equals(MDMContent.MSG_ID_EM_EL1_STATUS_IND)) {
            MDMCoreOperation instance = MDMCoreOperation.getInstance();
            int dl_tput = instance.getFieldValue(data, "dl_info[0]." + MDMContent.EM_EL1_STATUS_DL_INFO_DL_TPUT, true);
            log("PrimaryCellDlThroughput,dl_tput : " + dl_tput);
            if (isOtaPSDisconnect(dl_tput) && !isOtaPSDisconnect(this.mLastDlTput)) {
                log("The Ota PSD status changed:");
                log("PSD status(Last):" + this.mLastDlTput);
                log("PSD status(Serving):" + dl_tput);
                handleOtaPSDisconnect(true);
            } else if (!isOtaPSDisconnect(dl_tput) && isOtaPSDisconnect(this.mLastDlTput)) {
                log("The Ota PSD status changed:");
                log("PSD status(Last):" + this.mLastDlTput);
                log("PSD status(Serving):" + dl_tput);
                handleOtaPSDisconnect(false);
            }
            this.mLastDlTput = dl_tput;
        }
    }

    private boolean isOtaPSDisconnect(int dl_tput) {
        return dl_tput == 0;
    }

    private void handleOtaPSDisconnect(boolean disconnect) {
        log("PSD status:" + disconnect);
        if (disconnect) {
            MsgHandler msgHandler = this.mHandler;
            msgHandler.sendMessageDelayed(msgHandler.obtainMessage(2), (long) this.mDlTputTimeout);
            return;
        }
        this.mHandler.removeMessages(2);
    }

    private class MsgHandler extends Handler {
        private MsgHandler() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    OtaAirplaneModeActivity.this.setAirplaneMode(false);
                    OtaAirplaneModeActivity.access$1004();
                    OtaAirplaneModeActivity.mAirplaneCountTv.setText(String.valueOf(OtaAirplaneModeActivity.mCount));
                    OtaAirplaneModeActivity otaAirplaneModeActivity = OtaAirplaneModeActivity.this;
                    otaAirplaneModeActivity.log("Set AirplaneMode off, mCount: " + OtaAirplaneModeActivity.mCount);
                    return;
                case 2:
                    OtaAirplaneModeActivity.this.updateNetworkstatus("PSD disabled timeout:Call OTA Request", SupportMenu.CATEGORY_MASK);
                    OtaAirplaneModeActivity.this.handleOTARequest();
                    return;
                default:
                    return;
            }
        }
    }

    class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        CheckBoxListener() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            OtaAirplaneModeActivity otaAirplaneModeActivity = OtaAirplaneModeActivity.this;
            otaAirplaneModeActivity.log("Enable listen the network status changed: " + isChecked);
            OtaAirplaneModeActivity.this.setupOtaRequest(isChecked);
        }
    }
}
