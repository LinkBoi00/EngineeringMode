package com.mediatek.engineermode;

import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserManager;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.mediatek.engineermode.cxp.CarrierExpressUtil;
import com.mediatek.engineermode.dm.DiagnosticMonitoringSettingActivity;
import com.mediatek.engineermode.rsc.RuntimeSwitchConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public class PrefsFragment extends PreferenceFragment {
    private static final int[] FRAGMENT_RES = {R.xml.telephony, R.xml.connectivity, R.xml.hardware_testing, R.xml.location, R.xml.log_and_debugging, R.xml.others};
    private static final int[] FRAGMENT_RES_WIFIONLY = {R.xml.connectivity, R.xml.hardware_testing, R.xml.location, R.xml.log_and_debugging, R.xml.others};
    private static final String INNER_LOAD_INDICATOR_FILE = "/vendor/etc/system_update/address.xml";
    private static final String[] KEY_REMOVE_ARRAY = {"display", "battery_log", "memory"};
    private static final String PROPERTY_MCF_SUPPORT = "ro.vendor.mtk_mcf_support";
    public static final String PR_MODEM_MONITOR_SUPPORT = "ro.vendor.mtk_modem_monitor_support";
    public static final String PR_OPERATOR_OPTR = "persist.vendor.operator.optr";
    public static final String PR_OPERATOR_SEG = "persist.vendor.operator.seg";
    public static final String PR_SIMME_LOCK_MODE = "ro.vendor.sim_me_lock_mode";
    public static final String PR_SIM_RIL_TESTSIM = "vendor.gsm.sim.ril.testsim";
    public static final String PR_SIM_RIL_TESTSIM2 = "vendor.gsm.sim.ril.testsim.2";
    public static final String PR_SIM_RIL_TESTSIM3 = "vendor.gsm.sim.ril.testsim.3";
    public static final String PR_SIM_RIL_TESTSIM4 = "vendor.gsm.sim.ril.testsim.4";
    private static final String TAG = "PrefsFragment";
    private int[] mFragmentRest;
    private boolean mIsInit = false;
    private int mXmlResId;

    public static boolean isMcfSupport() {
        return SystemProperties.getInt(PROPERTY_MCF_SUPPORT, 0) == 1;
    }

    public void setResource(int resIndex) {
        this.mXmlResId = resIndex;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (EngineerMode.sWifiOnly) {
            this.mFragmentRest = FRAGMENT_RES_WIFIONLY;
        } else if (!UserManager.supportsMultipleUsers() || UserManager.get(getActivity()).getUserHandle() == 0) {
            this.mFragmentRest = FRAGMENT_RES;
        } else {
            this.mFragmentRest = FRAGMENT_RES_WIFIONLY;
        }
    }

    public boolean onPreferenceTreeClick(PreferenceScreen screen, Preference pref) {
        if (FeatureSupport.isSupportedEmSrv()) {
            return super.onPreferenceTreeClick(screen, pref);
        }
        String id = pref.getKey();
        for (String c : KEY_REMOVE_ARRAY) {
            if (id.equals(c)) {
                Toast.makeText(getActivity(), R.string.notice_wo_emsvr, 1).show();
                return true;
            }
        }
        return super.onPreferenceTreeClick(screen, pref);
    }

    public void onStart() {
        super.onStart();
    }

    private void removePreference(PreferenceScreen prefScreen, String prefId) {
        Preference pref = findPreference(prefId);
        if (pref != null) {
            prefScreen.removePreference(pref);
        }
    }

    private void removeItemsByCustomization(PreferenceScreen screen) {
        UiCusXmlParser xmlParser = new UiCusXmlParser();
        xmlParser.parse(EmApplication.getContext());
        for (String key : xmlParser.getItemsToRemove()) {
            removePreference(screen, key);
        }
    }

    private void removeItemsByBuildType(PreferenceScreen screen) {
        if (!isTestSim() && !FeatureSupport.isEngLoad() && !FeatureSupport.isUserDebugLoad() && !ChipSupport.isFeatureSupported(0)) {
            removePreference(screen, "auto_answer");
            Elog.d(TAG, "Hide the auto_answer item");
        }
        if (!ChipSupport.isFeatureSupported(0) && !FeatureSupport.isEngLoad() && !FeatureSupport.isUserDebugLoad()) {
            Elog.i(TAG, "it is customer user load!");
            if (SystemProperties.get("ro.debuggable", "0").equals("0")) {
                Elog.i(TAG, "only remove atci on not root version");
                removePreference(screen, "atci");
            }
            removePreference(screen, "voice_settings");
            removePreference(screen, "md_log_filter");
            removePreference(screen, "non_sleep_mode");
            removeItemsByCustomization(screen);
        }
        if (FeatureSupport.isUserDebugLoad()) {
            removePreference(screen, "battery_log");
        }
        if (FeatureSupport.isUserLoad()) {
            removePreference(screen, "amr_wb");
            removePreference(screen, "lte_tool");
            removePreference(screen, "cmas");
            removePreference(screen, "diagnostic");
            removePreference(screen, "dsda_setting");
            removePreference(screen, "fast_dormancy");
            removePreference(screen, "gprs");
            removePreference(screen, "hspa_info");
            removePreference(screen, "iatype");
            removePreference(screen, "md_em_filter");
            removePreference(screen, "sbp");
            removePreference(screen, "spc");
            removePreference(screen, "sim_switch");
            removePreference(screen, "simme_lock1");
            removePreference(screen, "simme_lock2");
            removePreference(screen, "u3_phy");
            removePreference(screen, "gwsd_setting");
            removePreference(screen, "vodata");
            removePreference(screen, "noise_profiling_tool");
            removePreference(screen, "xpxtaging");
            removePreference(screen, "multi_sim_switch");
            removePreference(screen, "nrmapconfig");
            removePreference(screen, "network_slice");
            removePreference(screen, "md_thermal");
            removePreference(screen, "md_tx_div_dual");
            removePreference(screen, "bt_test_tool");
            removePreference(screen, "cds_information");
            removePreference(screen, "cs_reevaluation");
            removePreference(screen, "fm_receiver");
            removePreference(screen, "fm_transmitter");
            removePreference(screen, "wfd_settings");
            removePreference(screen, "wifi_em_config");
            removePreference(screen, "aal");
            removePreference(screen, "aol_test");
            removePreference(screen, "memory");
            removePreference(screen, "power");
            removePreference(screen, "usbmode_switch");
            removePreference(screen, "usb");
            removePreference(screen, "usb_speed");
            removePreference(screen, "clk_quality_at");
            removePreference(screen, "cw_test");
            removePreference(screen, "fused_location_provider");
            removePreference(screen, "desense_at");
            removePreference(screen, "dcb_cal");
            removePreference(screen, "mnl_config_editor");
            removePreference(screen, "battery_log");
            removePreference(screen, "modem_reset_delay");
            removePreference(screen, "modem_warning");
            removePreference(screen, "wcn_core_dump");
            removePreference(screen, "carrier_express");
            removePreference(screen, "mdml_sample");
            removePreference(screen, "swla");
            removePreference(screen, "system_update");
            removePreference(screen, "usbacm");
            removePreference(screen, "usb_checker_enabler");
        }
    }

    private void removeItemsOnTc1Load(PreferenceScreen screen) {
        if (ChipSupport.isFeatureSupported(1)) {
            Elog.d(TAG, "it is tc1 branch");
            removePreference(screen, "force_tx");
            removePreference(screen, "apc");
            removePreference(screen, "lte_tool");
            removePreference(screen, "auto_answer");
            removePreference(screen, "cfu");
            removePreference(screen, "cmas");
            removePreference(screen, "diagnostic");
            removePreference(screen, "dsda_setting");
            removePreference(screen, "ehrpd_bg_data");
            removePreference(screen, "emergency_num_key");
            removePreference(screen, "gwsd_setting");
            removePreference(screen, "vodata");
            removePreference(screen, "iatype");
            removePreference(screen, "ims");
            removePreference(screen, "iot_config");
            removePreference(screen, "ltecaconfig");
            removePreference(screen, "lte_hpue_configue");
            removePreference(screen, "mcf_config");
            removePreference(screen, "md_em_filter");
            removePreference(screen, "md_low_power_monitor");
            removePreference(screen, "misc_config");
            removePreference(screen, "mobile_data_prefer");
            removePreference(screen, "noise_profiling_tool");
            removePreference(screen, "nrmapconfig");
            removePreference(screen, "ota_airplane_mode");
            removePreference(screen, "rf_desense_test");
            removePreference(screen, "rtn");
            removePreference(screen, "spc");
            removePreference(screen, "sim_info");
            removePreference(screen, "sim_switch");
            removePreference(screen, "simme_lock1");
            removePreference(screen, "simme_lock2");
            removePreference(screen, "sim_recoverytest_tool");
            removePreference(screen, "test_sim_switch");
            removePreference(screen, "u3_phy");
            removePreference(screen, "usb_tethering");
            removePreference(screen, "vilte");
            removePreference(screen, "wifi_calling");
            removePreference(screen, "modem_switch");
            removePreference(screen, "bt_test_tool");
            removePreference(screen, "connsys_patch_info");
            removePreference(screen, "nfc_st");
            removePreference(screen, "wfd_settings");
            removePreference(screen, "modem_reset_delay");
            removePreference(screen, "modem_warning");
            removePreference(screen, "wcn_core_dump");
            removePreference(screen, "carrier_express");
            removePreference(screen, "moms");
            removePreference(screen, "voice_settings");
            removePreference(screen, "smart_rat_switch");
            removePreference(screen, "network_slice");
            removePreference(screen, "aol_test");
            return;
        }
        removePreference(screen, "security_status");
        removePreference(screen, "antenna_diversity");
    }

    private void removeItemsForNonTelAddOn(PreferenceScreen screen) {
        if (!FeatureSupport.isMtkTelephonyAddOnPolicyEnable()) {
            removePreference(screen, "aal");
            removePreference(screen, "camerasolo");
            removePreference(screen, "voice_settings");
            removePreference(screen, "apc");
            removePreference(screen, "bt_test_tool");
            removePreference(screen, "cfu");
            removePreference(screen, "cmas");
            removePreference(screen, "diagnostic");
            removePreference(screen, "dsda_setting");
            removePreference(screen, "gprs");
            removePreference(screen, "gwsd_setting");
            removePreference(screen, "vodata");
            removePreference(screen, "iatype");
            removePreference(screen, "iot_config");
            removePreference(screen, "misc_config");
            removePreference(screen, "modem_switch");
            removePreference(screen, "rtn");
            removePreference(screen, "sim_info");
            removePreference(screen, "sim_recoverytest_tool");
            removePreference(screen, "simme_lock1");
            removePreference(screen, "simme_lock2");
            removePreference(screen, "smart_rat_switch");
            removePreference(screen, "test_sim_switch");
            removePreference(screen, "wfd_settings");
            removePreference(screen, "nrmapconfig");
            removePreference(screen, "network_slice");
            removePreference(screen, "aol_test");
        }
    }

    private void removeItemsByOptr(PreferenceScreen screen) {
        String mOptr = SystemProperties.get(PR_OPERATOR_OPTR);
        if (!"OP12".equals(mOptr)) {
            removePreference(screen, "spc");
        }
        if (!"OP07".equals(mOptr)) {
            removePreference(screen, "sim_info");
            removePreference(screen, "diagnostic");
        }
        if (!"OP20".equals(mOptr)) {
            removePreference(screen, "rtn");
        }
    }

    private void removeItemsByModemType(PreferenceScreen screen) {
        if (FeatureSupport.is90Modem() || FeatureSupport.is3GOnlyModem()) {
            removePreference(screen, "mdm_em_info");
            removePreference(screen, "ltecaconfig");
        }
        if (FeatureSupport.is93ModemAndAbove()) {
            removePreference(screen, "u3_phy");
            removePreference(screen, "test_sim_switch");
            removePreference(screen, "iatype");
        }
        if (FeatureSupport.is91Modem() || FeatureSupport.is92Modem()) {
            removePreference(screen, "md_low_power_monitor");
        }
        if (FeatureSupport.is90Modem() || FeatureSupport.is91Modem() || FeatureSupport.is3GOnlyModem()) {
            removePreference(screen, "anttunerdebug");
        }
        if (!FeatureSupport.is95ModemAndAbove()) {
            removePreference(screen, "lte_rx_mimo_configure");
        }
        if (!ModemCategory.isCdma()) {
            removePreference(screen, "bypass");
        }
        if (!FeatureSupport.is98Modem()) {
            removePreference(screen, "md_sar_and_pd");
            removePreference(screen, "md_thermal");
            removePreference(screen, "md_tx_div_dual");
        }
    }

    private void removeItemsByFeatureSupport(PreferenceScreen screen) {
        if (!DiagnosticMonitoringSettingActivity.isSupport()) {
            removePreference(screen, "diagnostic_monitoring_setting");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_GNSS_L5_SUPPORT)) {
            removePreference(screen, "dcb_cal");
        }
        if (!isVoiceCapable() || EngineerMode.sWifiOnly) {
            removePreference(screen, "auto_dialer");
        }
        if (!FeatureSupport.isGpsSupport(getActivity())) {
            removePreference(screen, "ygps");
            removePreference(screen, "cw_test");
        }
        if (!FeatureSupport.isWifiSupport(getActivity())) {
            removePreference(screen, "wifi");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_APC_SUPPORT)) {
            removePreference(screen, "apc");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_FD_SUPPORT)) {
            removePreference(screen, "fast_dormancy");
        }
        if (!SystemProperties.get(PR_MODEM_MONITOR_SUPPORT).equals("1")) {
            removePreference(screen, "mdml_sample");
            removePreference(screen, "mdm_em_info");
            removePreference(screen, "mdm_config");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_DEVREG_APP)) {
            removePreference(screen, "device_register");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_WFD_SUPPORT)) {
            removePreference(screen, "wfd_settings");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_IMS_SUPPORT)) {
            removePreference(screen, "ims");
        }
        if (!SystemProperties.get(PR_SIMME_LOCK_MODE, "0").equals("0") && !SystemProperties.get(PR_SIMME_LOCK_MODE, "0").equals("2") && !SystemProperties.get(PR_SIMME_LOCK_MODE, "0").equals("3")) {
            removePreference(screen, "simme_lock1");
            removePreference(screen, "simme_lock2");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_AAL_SUPPORT)) {
            removePreference(screen, "aal");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_VILTE_SUPPORT)) {
            removePreference(screen, "vilte");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_MD_WM_SUPPORT)) {
            removePreference(screen, "rat_config");
        }
        if (!WorldModeUtil.isWorldPhoneSupport() && !WorldModeUtil.isNrSupported()) {
            removePreference(screen, "world_mode");
            removePreference(screen, "modem_switch");
        } else if (WorldModeUtil.isWorldModeSupport()) {
            removePreference(screen, "modem_switch");
        } else {
            removePreference(screen, "world_mode");
        }
        if (!isMcfSupport()) {
            removePreference(screen, "mcf_config");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_GWSD_SUPPORT)) {
            removePreference(screen, "gwsd_setting");
        }
        if (!FeatureSupport.isSupported(FeatureSupport.FK_VODATA_SUPPORT)) {
            removePreference(screen, "vodata");
        }
    }

    private void removeItemsForOtherCondition(PreferenceScreen screen) {
        if (!RuntimeSwitchConfig.supportRsc()) {
            removePreference(screen, "rsc");
        }
        if (TelephonyManager.getDefault().getSimCount() > 1) {
            removePreference(screen, "simme_lock1");
        } else {
            removePreference(screen, "simme_lock2");
        }
        if (!new File(INNER_LOAD_INDICATOR_FILE).exists()) {
            removePreference(screen, "system_update");
        }
        if (!FeatureSupport.isPackageExisted(getActivity(), FeatureSupport.PK_CDS_EM)) {
            removePreference(screen, "cds_information");
        }
        Preference pref = findPreference("cmas");
        if (pref != null && !isActivityAvailable(pref.getIntent())) {
            removePreference(screen, "cmas");
        }
        if (!CarrierExpressUtil.isCarrierExpressSupported()) {
            removePreference(screen, "carrier_express");
        }
    }

    private void removeUnsupportedItems() {
        PreferenceScreen screen = getPreferenceScreen();
        removeItemsByBuildType(screen);
        removeItemsOnTc1Load(screen);
        removeItemsForNonTelAddOn(screen);
        removeItemsByOptr(screen);
        removeItemsByModemType(screen);
        removeItemsByFeatureSupport(screen);
        removeItemsForOtherCondition(screen);
        removePreference(screen, "md_log_filter");
    }

    private boolean isVoiceCapable() {
        TelephonyManager telephony = (TelephonyManager) getActivity().getSystemService("phone");
        boolean bVoiceCapable = telephony != null && telephony.isVoiceCapable();
        Elog.i(TAG, "sIsVoiceCapable : " + bVoiceCapable);
        return bVoiceCapable;
    }

    private boolean isTestSim() {
        return SystemProperties.get(PR_SIM_RIL_TESTSIM).equals("1") || SystemProperties.get(PR_SIM_RIL_TESTSIM2).equals("1") || SystemProperties.get(PR_SIM_RIL_TESTSIM3).equals("1") || SystemProperties.get(PR_SIM_RIL_TESTSIM4).equals("1");
    }

    private boolean isActivityAvailable(Intent intent) {
        return getActivity().getPackageManager().resolveActivity(intent, 0) != null;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!this.mIsInit) {
                addPreferencesFromResource(this.mFragmentRest[this.mXmlResId]);
                removeUnsupportedItems();
                this.mIsInit = true;
            }
            PreferenceScreen screen = getPreferenceScreen();
            for (int i = screen.getPreferenceCount() - 1; i >= 0; i--) {
                Preference pre = screen.getPreference(i);
                if (pre != null) {
                    Intent intent = pre.getIntent();
                    if (!isActivityAvailable(intent)) {
                        screen.removePreference(pre);
                        Elog.i(TAG, "intent : " + intent);
                    }
                }
            }
        }
    }

    class UiCusXmlParser {
        private static final String ATTR_IF_SHOW = "ifshow";
        private static final String ATTR_PREF_KEY = "key";
        private static final String ATTR_VALUE_NOT_SHOW = "false";
        private static final String TAG_FEATURE = "feature";
        private ArrayList<String> mItemsToRemove = new ArrayList<>();

        UiCusXmlParser() {
        }

        /* access modifiers changed from: package-private */
        public List<String> getItemsToRemove() {
            return Collections.unmodifiableList(this.mItemsToRemove);
        }

        /* access modifiers changed from: package-private */
        public void parse(Context context) {
            this.mItemsToRemove.clear();
            XmlResourceParser xmlParser = context.getResources().getXml(R.xml.ui_customize);
            try {
                for (int eventType = xmlParser.getEventType(); eventType != 1; eventType = xmlParser.next()) {
                    if (eventType == 2 && TAG_FEATURE.equals(xmlParser.getName()) && xmlParser.getAttributeValue((String) null, ATTR_IF_SHOW).equals(ATTR_VALUE_NOT_SHOW)) {
                        this.mItemsToRemove.add(xmlParser.getAttributeValue((String) null, ATTR_PREF_KEY));
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            if (xmlParser != null) {
                xmlParser.close();
            }
        }
    }
}
