package com.mediatek.engineermode.ims;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.ims.ImsManager;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import vendor.mediatek.hardware.engineermode.V1_3.IEmd;

public class ImsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int CMW500_REQUEST_READ_PHONE_STATE = 1000;
    private static final int DISABLE_MODE_ADD_RULE_DEACTIVATED_TAG = 2;
    private static final int DISABLE_MODE_DELETE_RULE = 1;
    private static final String DISABLE_VOLTE_HD_CALL = "Disable VoLTE HD call";
    private static final String ENABLE_VOLTE_HD_CALL = "Enable VoLTE HD call";
    private static final int IMS_MODE_DISABLED = 0;
    private static final int IMS_MODE_ENABLED = 1;
    private static final String IMS_OVER_SGS_PREFERE_92MODEM = "ims_over_sgs_prefere_92modem";
    private static final String IMS_SIGNAL_DEFAULT = "5";
    private static final String IMS_SIGNAL_VALUE = "5";
    private static final String IMS_VOLTE_SETTING_SHAREPRE = "telephony_ims_volte_settings";
    private static final String MODE_SS_CS = "Prefer CS";
    private static final String MODE_SS_XCAP = "Prefer XCAP";
    private static final int MSG_GET_VDP = 5;
    private static final int MSG_IMS_OVER_SGS_DISABLE = 49;
    private static final int MSG_IMS_OVER_SGS_ENABLE = 48;
    private static final int MSG_QUERY_IMS_OVER_SGS_MODEM_90 = 32;
    private static final int MSG_QUERY_IMS_OVER_SGS_MODEM_91 = 33;
    private static final int MSG_QUERY_IMS_OVER_SGS_MODEM_92 = 34;
    private static final int MSG_QUERY_IMS_OVER_SGS_MODEM_93 = 35;
    private static final int MSG_SET_IMS_MODE = 6;
    private static final int MSG_SET_IMS_OVER_SGS_MODEM_90 = 16;
    private static final int MSG_SET_IMS_OVER_SGS_MODEM_91 = 17;
    private static final int MSG_SET_IMS_OVER_SGS_MODEM_92 = 18;
    private static final int MSG_SET_IMS_OVER_SGS_MODEM_93 = 19;
    private static final int MSG_SET_IMS_SIGNAL = 1;
    private static final int MSG_SET_OPERATOR_CODE = 0;
    private static final int MSG_SET_PRECONDITION = 2;
    private static final int MSG_SET_VDP = 4;
    private static final int MSG_SET_VOLTE_SETTING = 3;
    private static final String OPERATOR_CODE_DEFAULT = "0";
    private static final String OPERATOR_CODE_VALUE = "16386";
    private static final String PRECONDITION_DEFAULT = "1";
    private static final String PRECONDITION_VALUE = "0";
    private static final String PROP_DYNAMIC_SBP = "persist.vendor.radio.mtk_dsbp_support";
    private static final String PROP_IMS_MODE = "persist.vendor.radio.imstestmode";
    private static final String PROP_SMS_OVER_IMS_TEST_MODE = "persist.vendor.radio.smsformat";
    private static final String PROP_SS_CFNUM = "persist.vendor.radio.xcap.cfn";
    private static final String PROP_SS_DISABLE_METHOD = "persist.vendor.radio.ss.xrdm";
    private static final String PROP_SS_MODE = "persist.vendor.radio.ss.mode";
    private static final String SET_IMS_SIGNAL = "ims_signaling_qci";
    private static final String SET_OPERATOR_CODE = "operator_code";
    private static final String SET_PRECONDITION = "UA_call_precondition";
    private static final String SMS_FORMAT_3GPP = "3gpp";
    private static final String SMS_FORMAT_3GPP2 = "3gpp2";
    private static final String SMS_FORMAT_NONE = "none";
    private static final String TAG = "Ims/ImsActivity";
    private static final String TYPE_IP = "IP";
    private static final String TYPE_IPV4V6 = "IPV4V6";
    private static final String TYPE_IPV6 = "IPV6";
    private static final int VOLTE_REQUEST_READ_PHONE_STATE = 1001;
    private static final String[] mImsOverSGSPrefereDisabled = {"AT+ESBP=1,88,0", "AT+ESBP=5,\"SBP_SDM_PREFER_SMS_OVER_SGS_TO_IMS\",0", "AT+EGCMD=6,2,\"SDM_ADS_PREFER_SMS_OVER_SGS_TO_IMS\"", "AT+ECFGSET=\"sdm_profile_prefer_sms_over_sgs_to_ims\",\"0\""};
    private static final String[] mImsOverSGSPrefereEnabled = {"AT+ESBP=1,88,1", "AT+ESBP=5,\"SBP_SDM_PREFER_SMS_OVER_SGS_TO_IMS\",1", "AT+EGCMD=6,1,\"SDM_ADS_PREFER_SMS_OVER_SGS_TO_IMS\"", "AT+ECFGSET=\"sdm_profile_prefer_sms_over_sgs_to_ims\",\"1\""};
    private static int mImsOverSGSPrefereModemType = 0;
    private static final String[] mImsOverSGSPrefereQuerry = {"AT+ESBP=3,88", "AT+ESBP=7,\"SBP_SDM_PREFER_SMS_OVER_SGS_TO_IMS\"", "", "AT+ECFGGET=\"sdm_profile_prefer_sms_over_sgs_to_ims\""};
    private static final String[] mImsOverSGSPrefereQuerryRes = {"+ESBP:", "+ESBP:", "", "+ECFGGET:"};
    /* access modifiers changed from: private */
    public static int mVolteSettingFlag = 0;
    private final Handler mATHandler = new Handler() {
        private String[] mReturnData = new String[2];

        public void handleMessage(Message msg) {
            String error_num;
            if (msg.what != 0) {
                String str = "1";
                if (msg.what == 1) {
                    if (((AsyncResult) msg.obj).exception == null) {
                        Elog.d(ImsActivity.TAG, "Set ims_signaling_qci successful.");
                        ImsActivity imsActivity = ImsActivity.this;
                        if (ImsActivity.mVolteSettingFlag == 1) {
                            str = "0";
                        }
                        imsActivity.sendCommand(ImsActivity.SET_PRECONDITION, str, 2);
                        return;
                    }
                    ImsActivity.this.showToast("Set ims_signaling_qci failed.");
                } else if (msg.what == 2) {
                    if (((AsyncResult) msg.obj).exception == null) {
                        Elog.d(ImsActivity.TAG, "Set UA_call_precondition successful.");
                        Message msgSetting = new Message();
                        msgSetting.what = 3;
                        ImsActivity.this.mSettingHandler.sendMessage(msgSetting);
                        return;
                    }
                    ImsActivity.this.showToast("Set UA_call_precondition failed.");
                } else if (msg.what == 16 || msg.what == 17 || msg.what == 18 || msg.what == 19) {
                    if (((AsyncResult) msg.obj).exception == null) {
                        ImsActivity.this.showToast("Set succeed.");
                        Elog.d(ImsActivity.TAG, "Set ims over SGS prefer successful.");
                        return;
                    }
                    ImsActivity.this.showToast("Set failed.");
                    Elog.d(ImsActivity.TAG, "Set ims over SGS prefer failed.");
                } else if (msg.what == 4) {
                    if (((AsyncResult) msg.obj).exception == null) {
                        ImsActivity.this.showToast("Set VDP successfully.");
                    } else {
                        ImsActivity.this.showToast("Set VDP failed.");
                    }
                } else if (msg.what == 5) {
                    AsyncResult asyncResult = (AsyncResult) msg.obj;
                    if (asyncResult != null && asyncResult.exception == null && asyncResult.result != null) {
                        ImsActivity.this.mVdp.setText(((String[]) asyncResult.result)[0].substring("+CEVDP:".length()).trim());
                    }
                } else if (msg.what == 6) {
                    AsyncResult ar = (AsyncResult) msg.obj;
                    if (ar.exception == null) {
                        String error_num2 = "";
                        String txt = new String((byte[]) ar.result);
                        Elog.d(ImsActivity.TAG, "Result(byte): " + txt);
                        if (txt.trim().equals("OK")) {
                            error_num = "0";
                        } else {
                            try {
                                error_num2 = txt.split(": ")[1].trim();
                                Elog.d(ImsActivity.TAG, "error_num: " + error_num2);
                                error_num = error_num2;
                            } catch (Exception e) {
                                e.printStackTrace();
                                error_num = error_num2;
                            }
                        }
                        if (error_num.equals("100") || error_num.equals("0")) {
                            try {
                                IEmd emHidlService = EmUtils.getEmHidlService();
                                if (!ImsActivity.this.mRadioImsModeEnabled.isChecked()) {
                                    str = "0";
                                }
                                emHidlService.setImsTestMode(str);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                Elog.e(ImsActivity.TAG, "set property failed ...");
                            }
                            ImsActivity.this.showToast("Set test mode done");
                            Elog.i(ImsActivity.TAG, "Set test mode done");
                            return;
                        }
                        ImsActivity.this.showToast("Set ims test mode failed.");
                        Elog.i(ImsActivity.TAG, "Set test mode failed");
                        return;
                    }
                    ImsActivity.this.showToast("Set ims test mode failed.");
                    Elog.i(ImsActivity.TAG, "Set test mode failed");
                } else if (msg.what == 32 || msg.what == 33 || msg.what == 35) {
                    AsyncResult ar2 = (AsyncResult) msg.obj;
                    if (ar2.exception == null) {
                        String[] strArr = (String[]) ar2.result;
                        this.mReturnData = strArr;
                        if (strArr.length > 0) {
                            Elog.d(ImsActivity.TAG, "mReturnData = " + this.mReturnData[0]);
                            String result = "";
                            try {
                                if (FeatureSupport.is93ModemAndAbove()) {
                                    result = this.mReturnData[0].split(",")[1];
                                    if (result.equals("\"0\"")) {
                                        result = "0";
                                    } else if (result.equals("\"1\"")) {
                                        result = str;
                                    }
                                } else {
                                    result = this.mReturnData[0].split(": ")[1];
                                }
                                Elog.d(ImsActivity.TAG, "result = " + result);
                            } catch (Exception e3) {
                                Elog.e(ImsActivity.TAG, "mReturnData error ");
                            }
                            Message msgSetting2 = new Message();
                            if (result.equals(str)) {
                                msgSetting2.what = 48;
                            } else if (result.equals("0")) {
                                msgSetting2.what = 49;
                            }
                            ImsActivity.this.mSettingHandler.sendMessage(msgSetting2);
                            return;
                        }
                        return;
                    }
                    Elog.d(ImsActivity.TAG, "quary QUERY_IMS_OVER_SGS failed.");
                    Elog.d(ImsActivity.TAG, ar2.exception.getMessage());
                }
            } else if (((AsyncResult) msg.obj).exception == null) {
                Elog.d(ImsActivity.TAG, "Set operator_code successful.");
                ImsActivity imsActivity2 = ImsActivity.this;
                int unused = ImsActivity.mVolteSettingFlag;
                imsActivity2.sendCommand(ImsActivity.SET_IMS_SIGNAL, "5", 1);
            } else {
                ImsActivity.this.showToast("Set operator_code failed.");
            }
        }
    };
    /* access modifiers changed from: private */
    public Context mAppContext = null;
    private Button mButtonSetDynamicSbp;
    private Button mButtonSetImsFormat;
    private Button mButtonSetImsMode;
    private Button mButtonSetImsOverSGSPrefere;
    private Button mButtonSetSSDisable;
    private Button mButtonSetSSMode;
    private Button mButtonSetVdp;
    private Button mButtonSetVolte;
    private Button mButtonSetVolteCall;
    private Button mButtonSetXcapCFNum;
    private ListView mCategoryList;
    private TextView mImsMultStatus;
    private TextView mImsStatus;
    private RadioButton mRadioDynamicSbpDisabled;
    private RadioButton mRadioDynamicSbpEnabled;
    private RadioButton mRadioImsFormat3gpp;
    private RadioButton mRadioImsFormat3gpp2;
    private RadioButton mRadioImsFormatNone;
    private RadioButton mRadioImsModeDisabled;
    /* access modifiers changed from: private */
    public RadioButton mRadioImsModeEnabled;
    /* access modifiers changed from: private */
    public RadioButton mRadioImsOverSGSPrefereDisabled;
    /* access modifiers changed from: private */
    public RadioButton mRadioImsOverSGSPrefereEnabled;
    private RadioButton mRadioSSCs;
    private RadioButton mRadioSSDisableDel;
    private RadioButton mRadioSSDisableTag;
    private RadioButton mRadioSSXcap;
    private RadioButton mRadioSetVolteOff;
    private RadioButton mRadioSetVolteOn;
    /* access modifiers changed from: private */
    public final Handler mSettingHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 3) {
                String[] permissions = {"android.permission.READ_PHONE_STATE"};
                if (ImsActivity.this.mAppContext.checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                    ImsActivity.this.requestPermissions(permissions, 1000);
                    return;
                }
                ImsActivity.this.set4gLte();
                if (ImsActivity.mVolteSettingFlag == 1) {
                    ImsActivity.this.showToast("Set CMW500 setting successful.");
                } else {
                    ImsActivity.this.showToast("Set Default setting successful.");
                }
            } else if (msg.what == 48) {
                ImsActivity.this.mRadioImsOverSGSPrefereEnabled.setChecked(true);
            } else if (msg.what == 49) {
                ImsActivity.this.mRadioImsOverSGSPrefereDisabled.setChecked(true);
            }
        }
    };
    private int mSimType;
    private int mSubId;
    private Toast mToast = null;
    /* access modifiers changed from: private */
    public EditText mVdp;
    private EditText mXcapCFNum;

    public static void write92modemImsOverSGSPrefereSharedPreference(Context context, boolean check) {
        SharedPreferences.Editor editor = context.getSharedPreferences(IMS_OVER_SGS_PREFERE_92MODEM, 0).edit();
        editor.putBoolean(context.getString(R.string.ims_over_SGS_prefer), check);
        editor.commit();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Elog.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ims);
        this.mAppContext = getApplicationContext();
        this.mSimType = getIntent().getIntExtra("mSimType", 0);
        this.mImsStatus = (TextView) findViewById(R.id.ims_status);
        this.mImsMultStatus = (TextView) findViewById(R.id.ims_mult_status);
        this.mXcapCFNum = (EditText) findViewById(R.id.ims_ss_cf_num);
        this.mRadioSSXcap = (RadioButton) findViewById(R.id.ims_ss_mode_xcap);
        this.mRadioSSCs = (RadioButton) findViewById(R.id.ims_ss_mode_cs);
        Button button = (Button) findViewById(R.id.ims_ss_set_mode);
        this.mButtonSetSSMode = button;
        button.setOnClickListener(this);
        this.mRadioSSDisableTag = (RadioButton) findViewById(R.id.ims_ss_disable_tag);
        this.mRadioSSDisableDel = (RadioButton) findViewById(R.id.ims_ss_disable_del);
        Button button2 = (Button) findViewById(R.id.ims_ss_set_disable);
        this.mButtonSetSSDisable = button2;
        button2.setOnClickListener(this);
        Button button3 = (Button) findViewById(R.id.ims_set_ss_cf_num);
        this.mButtonSetXcapCFNum = button3;
        button3.setOnClickListener(this);
        this.mRadioSetVolteOff = (RadioButton) findViewById(R.id.volte_set_off);
        this.mRadioSetVolteOn = (RadioButton) findViewById(R.id.volte_set_on);
        Button button4 = (Button) findViewById(R.id.volte_set);
        this.mButtonSetVolte = button4;
        button4.setOnClickListener(this);
        Button button5 = (Button) findViewById(R.id.ims_dynamic_sbp_set);
        this.mButtonSetDynamicSbp = button5;
        button5.setOnClickListener(this);
        this.mRadioDynamicSbpEnabled = (RadioButton) findViewById(R.id.ims_dynamic_sbp_enable);
        this.mRadioDynamicSbpDisabled = (RadioButton) findViewById(R.id.ims_dynamic_sbp_disable);
        Button button6 = (Button) findViewById(R.id.ims_test_mode_set);
        this.mButtonSetImsMode = button6;
        button6.setOnClickListener(this);
        Button button7 = (Button) findViewById(R.id.ims_test_format);
        this.mButtonSetImsFormat = button7;
        button7.setOnClickListener(this);
        this.mRadioImsModeEnabled = (RadioButton) findViewById(R.id.ims_test_mode_enable);
        this.mRadioImsModeDisabled = (RadioButton) findViewById(R.id.ims_test_mode_disable);
        this.mRadioImsFormatNone = (RadioButton) findViewById(R.id.ims_format_none);
        this.mRadioImsFormat3gpp = (RadioButton) findViewById(R.id.ims_format_3gpp);
        this.mRadioImsFormat3gpp2 = (RadioButton) findViewById(R.id.ims_format_3gpp2);
        Button button8 = (Button) findViewById(R.id.ims_over_SGS_prefer_set);
        this.mButtonSetImsOverSGSPrefere = button8;
        button8.setOnClickListener(this);
        this.mRadioImsOverSGSPrefereEnabled = (RadioButton) findViewById(R.id.ims_over_SGS_prefer_enable);
        this.mRadioImsOverSGSPrefereDisabled = (RadioButton) findViewById(R.id.ims_over_SGS_prefer_disable);
        this.mVdp = (EditText) findViewById(R.id.ims_vdp);
        Button button9 = (Button) findViewById(R.id.ims_vdp_set);
        this.mButtonSetVdp = button9;
        button9.setOnClickListener(this);
        if (getSharedPreferences(IMS_VOLTE_SETTING_SHAREPRE, 0).getBoolean(getString(R.string.volte_set_check), true)) {
            this.mRadioSetVolteOn.setChecked(true);
        } else {
            this.mRadioSetVolteOff.setChecked(true);
        }
        this.mCategoryList = (ListView) findViewById(R.id.ims_category_list);
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.ims_category_common));
        items.add(getString(R.string.ims_category_registration));
        items.add(getString(R.string.ims_category_call));
        items.add(getString(R.string.ims_category_sms));
        items.add(getString(R.string.ims_category_bearer));
        items.add(getString(R.string.ims_category_pcscf));
        items.add(getString(R.string.ims_category_ussd));
        this.mCategoryList.setAdapter(new ArrayAdapter<>(this, 17367043, items));
        this.mCategoryList.setOnItemClickListener(this);
        setListViewItemsHeight(this.mCategoryList);
        Button button10 = (Button) findViewById(R.id.volte_call_setting);
        this.mButtonSetVolteCall = button10;
        button10.setOnClickListener(this);
    }

    private void setListViewItemsHeight(ListView listview) {
        if (listview != null) {
            ListAdapter adapter = listview.getAdapter();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View itemView = adapter.getView(i, (View) null, listview);
                itemView.measure(0, 0);
                totalHeight += itemView.getMeasuredHeight();
            }
            int totalHeight2 = totalHeight + ((adapter.getCount() - 1) * listview.getDividerHeight());
            ViewGroup.LayoutParams params = listview.getLayoutParams();
            params.height = totalHeight2;
            listview.setLayoutParams(params);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        Intent intent = new Intent(this, ConfigIMSActivity.class);
        intent.putExtra("mSimType", this.mSimType);
        switch (arg2) {
            case 0:
                intent.putExtra("category", getString(R.string.ims_category_common));
                break;
            case 1:
                intent.putExtra("category", getString(R.string.ims_category_registration));
                break;
            case 2:
                intent.putExtra("category", getString(R.string.ims_category_call));
                break;
            case 3:
                intent.putExtra("category", getString(R.string.ims_category_sms));
                break;
            case 4:
                intent.putExtra("category", getString(R.string.ims_category_bearer));
                break;
            case 5:
                intent.putExtra("category", getString(R.string.ims_category_pcscf));
                break;
            case 6:
                intent.putExtra("category", getString(R.string.ims_category_ussd));
                break;
        }
        startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Elog.d(TAG, "onResume()");
        super.onResume();
        Elog.d(TAG, "mSimType " + this.mSimType);
        int[] SubId = SubscriptionManager.getSubId(this.mSimType);
        if (SubId != null && SubId.length > 0) {
            this.mSubId = SubId[0];
        }
        Elog.d(TAG, "mSubId " + this.mSubId);
        boolean status = TelephonyManager.getDefault().isImsRegistered(this.mSubId);
        Elog.d(TAG, "getImsRegInfo(): " + status);
        TextView textView = this.mImsStatus;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.ims_status));
        sb.append(status ? "true" : "false");
        textView.setText(sb.toString());
        sendATCommand("AT+CEVDP?", "+CEVDP:", 5);
        TextView textView2 = this.mImsMultStatus;
        textView2.setText("persist.vendor.mims_support: " + SystemProperties.get(SimSelectActivity.MULT_IMS_SUPPORT, "-"));
        String ssmode = SystemProperties.get(PROP_SS_MODE, MODE_SS_XCAP);
        String ssdisableMethod = SystemProperties.get(PROP_SS_DISABLE_METHOD, Integer.toString(2));
        String xcapCFNum = SystemProperties.get(PROP_SS_CFNUM, "");
        String dynamicSbp = SystemProperties.get(PROP_DYNAMIC_SBP, "0");
        String imsMode = SystemProperties.get(PROP_IMS_MODE, "0");
        String smsformat = SystemProperties.get(PROP_SMS_OVER_IMS_TEST_MODE, SMS_FORMAT_NONE);
        Elog.d(TAG, "persist.vendor.radio.ss.mode: " + ssmode);
        Elog.d(TAG, "persist.vendor.radio.ss.xrdm: " + ssdisableMethod);
        Elog.d(TAG, "persist.vendor.radio.xcap.cfn:" + xcapCFNum);
        Elog.d(TAG, "persist.vendor.radio.mtk_dsbp_support:" + dynamicSbp);
        Elog.d(TAG, "persist.vendor.radio.imstestmode:" + imsMode);
        Elog.d(TAG, "persist.vendor.radio.smsformat:" + smsformat);
        queryImsOverSGSPrefereStatus();
        if (MODE_SS_XCAP.equals(ssmode)) {
            this.mRadioSSXcap.setChecked(true);
        } else if (MODE_SS_CS.equals(ssmode)) {
            this.mRadioSSCs.setChecked(true);
        } else {
            showToast("Got invalid SS Mode: \"" + ssmode + "\"");
        }
        if (2 == Integer.parseInt(ssdisableMethod)) {
            this.mRadioSSDisableTag.setChecked(true);
        } else if (1 == Integer.parseInt(ssdisableMethod)) {
            this.mRadioSSDisableDel.setChecked(true);
        } else {
            showToast("Got invalid SS Disable Method: \"" + ssdisableMethod + "\"");
        }
        if (1 == Integer.parseInt(dynamicSbp)) {
            this.mRadioDynamicSbpEnabled.setChecked(true);
        } else if (Integer.parseInt(dynamicSbp) == 0) {
            this.mRadioDynamicSbpDisabled.setChecked(true);
        }
        if (1 == Integer.parseInt(imsMode)) {
            this.mRadioImsModeEnabled.setChecked(true);
        } else if (Integer.parseInt(imsMode) == 0) {
            this.mRadioImsModeDisabled.setChecked(true);
        }
        if (SMS_FORMAT_NONE.equals(smsformat)) {
            this.mRadioImsFormatNone.setChecked(true);
        } else if (SMS_FORMAT_3GPP.equals(smsformat)) {
            this.mRadioImsFormat3gpp.setChecked(true);
        } else if (SMS_FORMAT_3GPP2.equals(smsformat)) {
            this.mRadioImsFormat3gpp2.setChecked(true);
        }
        this.mXcapCFNum.setText(xcapCFNum);
        if (ImsManager.getInstance(this, this.mSimType).isEnhanced4gLteModeSettingEnabledByUser()) {
            this.mButtonSetVolteCall.setText(DISABLE_VOLTE_HD_CALL);
        } else {
            this.mButtonSetVolteCall.setText(ENABLE_VOLTE_HD_CALL);
        }
    }

    public void onDestroy() {
        writeVolteSettingSharedPreference(this.mRadioSetVolteOn.isChecked());
        super.onDestroy();
    }

    public void onClick(View v) {
        ImsManager imsMgr;
        int select;
        String smsformat;
        String str;
        String ssdisableMethod;
        String ssmode;
        if (v == this.mButtonSetSSMode) {
            if (this.mRadioSSXcap.isChecked()) {
                ssmode = MODE_SS_XCAP;
            } else if (this.mRadioSSCs.isChecked()) {
                ssmode = MODE_SS_CS;
            } else {
                return;
            }
            try {
                EmUtils.getEmHidlService().setEmConfigure(PROP_SS_MODE, ssmode);
                Elog.d(TAG, "Set persist.vendor.radio.ss.mode = " + ssmode);
                showToast("Set SS Mode done");
            } catch (Exception e) {
                e.printStackTrace();
                Elog.e(TAG, "Set SS Mode failed ...");
                showToast("Set SS Mode failed");
            }
        } else if (v == this.mButtonSetSSDisable) {
            if (this.mRadioSSDisableTag.isChecked()) {
                ssdisableMethod = Integer.toString(2);
            } else if (this.mRadioSSDisableDel.isChecked()) {
                ssdisableMethod = Integer.toString(1);
            } else {
                return;
            }
            try {
                EmUtils.getEmHidlService().setEmConfigure(PROP_SS_DISABLE_METHOD, ssdisableMethod);
                Elog.d(TAG, "Set persist.vendor.radio.ss.xrdm = " + ssdisableMethod);
                showToast("Set SS Disable done");
            } catch (Exception e2) {
                e2.printStackTrace();
                Elog.e(TAG, "Set SS Disable failed ...");
                showToast("Set SS Disable failed");
            }
        } else if (v == this.mButtonSetXcapCFNum) {
            try {
                EmUtils.getEmHidlService().setEmConfigure(PROP_SS_CFNUM, this.mXcapCFNum.getText().toString());
                Elog.d(TAG, "Set persist.vendor.radio.xcap.cfn = " + this.mXcapCFNum.getText().toString());
                showToast("Set SS CF Number done");
            } catch (Exception e3) {
                e3.printStackTrace();
                Elog.e(TAG, "Set SS CF Number failed ...");
                showToast("Set SS CF Number failed");
            }
        } else {
            String str2 = "0";
            if (v == this.mButtonSetVolte) {
                if (this.mRadioSetVolteOff.isChecked()) {
                    mVolteSettingFlag = 0;
                } else if (this.mRadioSetVolteOn.isChecked()) {
                    mVolteSettingFlag = 1;
                } else {
                    return;
                }
                Elog.d(TAG, "Set VOLTE");
                if (mVolteSettingFlag == 1) {
                    str2 = OPERATOR_CODE_VALUE;
                }
                sendCommand(SET_OPERATOR_CODE, str2, 0);
            } else if (v == this.mButtonSetDynamicSbp) {
                StringBuilder sb = new StringBuilder();
                sb.append("Set persist.vendor.radio.mtk_dsbp_support = ");
                if (this.mRadioDynamicSbpEnabled.isChecked()) {
                    str = "1";
                } else {
                    str = str2;
                }
                sb.append(str);
                Elog.d(TAG, sb.toString());
                try {
                    IEmd emHidlService = EmUtils.getEmHidlService();
                    if (this.mRadioDynamicSbpEnabled.isChecked()) {
                        str2 = "1";
                    }
                    emHidlService.setDsbpSupport(str2);
                } catch (Exception e4) {
                    e4.printStackTrace();
                    Elog.e(TAG, "set property failed ...");
                }
                showToast("Set Dynamic SBP done");
            } else if (v == this.mButtonSetImsMode) {
                if (!FeatureSupport.is95ModemAndAbove()) {
                    try {
                        IEmd emHidlService2 = EmUtils.getEmHidlService();
                        if (this.mRadioImsModeEnabled.isChecked()) {
                            str2 = "1";
                        }
                        emHidlService2.setImsTestMode(str2);
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        Elog.e(TAG, "set property failed ...");
                    }
                    showToast("Set test mode done");
                } else if (this.mRadioImsModeEnabled.isChecked()) {
                    sendAtCommandRaw("AT+EIMSTESTMODE=1", 6);
                } else {
                    sendAtCommandRaw("AT+EIMSTESTMODE=0", 6);
                }
            } else if (v == this.mButtonSetImsFormat) {
                if (this.mRadioImsFormatNone.isChecked()) {
                    smsformat = SMS_FORMAT_NONE;
                } else if (this.mRadioImsFormat3gpp.isChecked()) {
                    smsformat = SMS_FORMAT_3GPP;
                } else if (this.mRadioImsFormat3gpp2.isChecked()) {
                    smsformat = SMS_FORMAT_3GPP2;
                } else {
                    return;
                }
                try {
                    EmUtils.getEmHidlService().setSmsFormat(smsformat);
                } catch (Exception e6) {
                    e6.printStackTrace();
                    Elog.e(TAG, "set property failed ...");
                }
                showToast("Set ims format test mode done");
            } else if (v == this.mButtonSetImsOverSGSPrefere) {
                setImsOverSGSPrefereMode();
            } else if (v == this.mButtonSetVdp) {
                try {
                    select = Integer.valueOf(this.mVdp.getText().toString()).intValue();
                } catch (NumberFormatException e7) {
                    select = 0;
                }
                if (select > 4 || select < 1) {
                    showToast("The input of VDP is wrong, please check!");
                    return;
                }
                sendATCommand("AT+CEVDP=" + select, "", 4);
            } else if (v == this.mButtonSetVolteCall && (imsMgr = ImsManager.getInstance(this, this.mSimType)) != null) {
                String[] permissions = {"android.permission.READ_PHONE_STATE"};
                if (getApplicationContext().checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                    requestPermissions(permissions, 1001);
                } else if (!imsMgr.isVolteEnabledByPlatform()) {
                    Toast.makeText(this, "Current SIM doesn't support VoLTE, set fail", 0).show();
                } else {
                    boolean enabled = imsMgr.isEnhanced4gLteModeSettingEnabledByUser();
                    Elog.d(TAG, "setVolteSetting old is " + enabled);
                    if (enabled) {
                        this.mButtonSetVolteCall.setText(ENABLE_VOLTE_HD_CALL);
                    } else {
                        this.mButtonSetVolteCall.setText(DISABLE_VOLTE_HD_CALL);
                    }
                    imsMgr.setEnhanced4gLteModeSetting(!enabled);
                }
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(this, "Permission denied, failed to change VoLTE setting.", 0).show();
                    Elog.d(TAG, "rejected permision for set4gLte");
                    return;
                }
                set4gLte();
                if (mVolteSettingFlag == 1) {
                    showToast("Set CMW500 setting successful.");
                    return;
                } else {
                    showToast("Set Default setting successful.");
                    return;
                }
            case 1001:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(this, "Permission denied, failed to change VoLTE setting.", 0).show();
                    Elog.d(TAG, "rejected permision for setVolteSetting");
                    return;
                }
                ImsManager imsMgr = ImsManager.getInstance(this, this.mSimType);
                boolean enabled = imsMgr.isEnhanced4gLteModeSettingEnabledByUser();
                Elog.d(TAG, "got permision, setVolteSetting old is " + enabled);
                if (enabled) {
                    this.mButtonSetVolteCall.setText(ENABLE_VOLTE_HD_CALL);
                } else {
                    this.mButtonSetVolteCall.setText(DISABLE_VOLTE_HD_CALL);
                }
                imsMgr.setEnhanced4gLteModeSetting(!enabled);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }

    /* access modifiers changed from: private */
    public void sendCommand(String name, String value, int msgtype) {
        Message msg = this.mATHandler.obtainMessage(msgtype);
        int i = this.mSimType;
        EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+ECFGSET=\"" + name + "\",\"" + value + "\"", ""}, msg);
    }

    private void sendATCommand(String cmd, String value, int msgtype) {
        Elog.d(TAG, "cmd = " + cmd);
        Message msg = this.mATHandler.obtainMessage(msgtype);
        EmUtils.invokeOemRilRequestStringsEm(this.mSimType, new String[]{cmd, value}, msg);
    }

    private void sendAtCommandRaw(String str, int message) {
        Elog.d(TAG, "sendAtCommand() " + str);
        byte[] rawData = str.getBytes();
        byte[] cmdByte = new byte[(rawData.length + 1)];
        System.arraycopy(rawData, 0, cmdByte, 0, rawData.length);
        cmdByte[cmdByte.length - 1] = 0;
        EmUtils.invokeOemRilRequestRawEm(this.mSimType, cmdByte, this.mATHandler.obtainMessage(message));
    }

    /* access modifiers changed from: private */
    public void set4gLte() {
        Elog.d(TAG, "set4gLte mVolteSettingFlag = " + mVolteSettingFlag);
        boolean enabled = false;
        if (mVolteSettingFlag == 1) {
            enabled = true;
        }
        ImsManager imsMgr = ImsManager.getInstance(this, this.mSimType);
        if (imsMgr != null) {
            imsMgr.setEnhanced4gLteModeSetting(enabled);
            Elog.d(TAG, "set4gLte enabled = " + enabled);
        }
    }

    private void writeVolteSettingSharedPreference(boolean check) {
        SharedPreferences.Editor editor = getSharedPreferences(IMS_VOLTE_SETTING_SHAREPRE, 0).edit();
        editor.putBoolean(getString(R.string.volte_set_check), check);
        editor.commit();
    }

    private void setImsOverSGSPrefereMode() {
        if (FeatureSupport.is90Modem()) {
            Elog.i(TAG, "it is 90 modem");
            mImsOverSGSPrefereModemType = 0;
        } else if (FeatureSupport.is91Modem()) {
            Elog.i(TAG, "it is 91 modem");
            mImsOverSGSPrefereModemType = 1;
        } else if (FeatureSupport.is92Modem()) {
            Elog.i(TAG, "it is 92 modem");
            mImsOverSGSPrefereModemType = 2;
        } else if (FeatureSupport.is93ModemAndAbove()) {
            Elog.i(TAG, "it is 93 modem");
            mImsOverSGSPrefereModemType = 3;
        } else {
            Elog.i(TAG, "it is 90 before modem");
            mImsOverSGSPrefereModemType = 2;
        }
        if (this.mRadioImsOverSGSPrefereEnabled.isChecked()) {
            String[] strArr = mImsOverSGSPrefereEnabled;
            int i = mImsOverSGSPrefereModemType;
            sendAtCommand(new String[]{strArr[i], ""}, i | 16);
            if (mImsOverSGSPrefereModemType == 2) {
                write92modemImsOverSGSPrefereSharedPreference(this, true);
            }
        } else if (this.mRadioImsOverSGSPrefereDisabled.isChecked()) {
            String[] strArr2 = mImsOverSGSPrefereDisabled;
            int i2 = mImsOverSGSPrefereModemType;
            sendAtCommand(new String[]{strArr2[i2], ""}, i2 | 16);
            if (mImsOverSGSPrefereModemType == 2) {
                write92modemImsOverSGSPrefereSharedPreference(this, false);
            }
        } else {
            showToast("please select the ebabled or disabled");
        }
    }

    private void queryImsOverSGSPrefereStatus() {
        if (FeatureSupport.is90Modem()) {
            Elog.i(TAG, "it is 90 modem");
            mImsOverSGSPrefereModemType = 0;
        } else if (FeatureSupport.is91Modem()) {
            Elog.i(TAG, "it is 91 modem");
            mImsOverSGSPrefereModemType = 1;
        } else if (FeatureSupport.is92Modem()) {
            Elog.i(TAG, "it is 92 modem");
            mImsOverSGSPrefereModemType = 2;
        } else if (FeatureSupport.is93ModemAndAbove()) {
            Elog.i(TAG, "it is 93 modem");
            mImsOverSGSPrefereModemType = 3;
        } else {
            Elog.i(TAG, "it is 90 before modem");
            mImsOverSGSPrefereModemType = 2;
        }
        int i = mImsOverSGSPrefereModemType;
        if (i != 2) {
            sendAtCommand(new String[]{mImsOverSGSPrefereQuerry[i], mImsOverSGSPrefereQuerryRes[i]}, i | 32);
        } else if (read92modemImsOverSGSPrefereSharedPreference()) {
            this.mRadioImsOverSGSPrefereEnabled.setChecked(true);
        } else {
            this.mRadioImsOverSGSPrefereDisabled.setChecked(true);
        }
    }

    private void sendAtCommand(String[] command, int msg) {
        Elog.d(TAG, "sendAtCommand() " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(this.mSimType, command, this.mATHandler.obtainMessage(msg));
    }

    private boolean read92modemImsOverSGSPrefereSharedPreference() {
        return getSharedPreferences(IMS_OVER_SGS_PREFERE_92MODEM, 0).getBoolean(getString(R.string.ims_over_SGS_prefer), false);
    }
}
