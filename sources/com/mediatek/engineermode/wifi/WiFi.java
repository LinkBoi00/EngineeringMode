package com.mediatek.engineermode.wifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiFi extends Activity implements AdapterView.OnItemClickListener {
    private static final int BIT_16 = 16;
    private static final int BIT_8 = 8;
    private static final String BRANCH_PATTERN = "t-neptune[\\w-]*SOC[a-zA-Z0-9]*_[a-zA-Z0-9]*_";
    private static final String CHIP_6632 = "6632";
    private static final String CHIP_ADV = "0";
    private static final String CHIP_FORMAT = "1";
    private static final int DIALOG_WIFI_ENABLE_FAIL = 2;
    private static final int DIALOG_WIFI_ENTER_TEST_MODE_FAILED = 3;
    private static final int DIALOG_WIFI_INIT = 0;
    private static final int DIALOG_WIFI_WARN = 1;
    private static final long FUNC_INDEX_VERSION = 47;
    static final String KEY_11AC_CAP = "key_11ac";
    static final String KEY_11AX_CAP = "key_11ax";
    static final String KEY_2BY2_CAP = "key_2by2";
    static final String KEY_ANT_SWAP = "key_ant_swap";
    static final String KEY_BW_160C_CAP = "key_bw_160c";
    static final String KEY_BW_160NC_CAP = "key_bw_160nc";
    static final String KEY_CH_BAND = "key_ch_band";
    static final String KEY_HW_TX_CAP = "key_hw_tx";
    static final String KEY_LDPC_CAP = "key_ldpc";
    static final String KEY_WIFI_TYPE = "key_wifi_type";
    private static final long MASK_32_BIT = -1;
    private static final long MASK_8_BIT = 255;
    private static final long MASK_HIGH_16_BIT = -65536;
    private static final long MASK_HIGH_8_BIT = 65280;
    private static final String TAG = "WifiMainpage";
    private static final String VER_SPLIT = "-";
    /* access modifiers changed from: private */
    public WifiCapability mCapability;
    /* access modifiers changed from: private */
    public ArrayList<String> mListData;
    /* access modifiers changed from: private */
    public ListView mLvTestItem;
    /* access modifiers changed from: private */
    public String mWifiChip = null;
    /* access modifiers changed from: private */
    public WifiManager mWifiManager = null;
    /* access modifiers changed from: private */
    public WifiType mWifiType = null;

    public enum WifiType {
        WIFI_6620,
        WIFI_6632,
        WIFI_FORMAT
    }

    private class LoadTask extends AsyncTask<Void, Void, WifiEmState> {
        private static final int RESULT_ENABLE_TM_FAILED = 2;
        private static final int RESULT_ENABLE_WIFI_FAILED = 1;
        private static final int RESULT_INIT_DONE = 0;
        private String mStrManifestInfo;
        private String mStrVersion;

        private LoadTask() {
            this.mStrManifestInfo = null;
            this.mStrVersion = null;
        }

        private String getBranch(String strInfo) {
            Matcher matcher = Pattern.compile(WiFi.BRANCH_PATTERN).matcher(strInfo);
            if (matcher.find()) {
                return matcher.group(0);
            }
            return null;
        }

        private String getVer(String strInfo) {
            String strVersion = strInfo;
            int index = strInfo.lastIndexOf(WiFi.VER_SPLIT);
            if (index > 0 && strInfo.length() > index + 1) {
                strVersion = strInfo.substring(index + 1);
            }
            if (strVersion.length() < 8) {
                return null;
            }
            String strYear = strVersion.substring(0, 4);
            String strMonth = strVersion.substring(4, 6);
            String strDay = strVersion.substring(6, 8);
            StringBuilder sbVer = new StringBuilder(strYear);
            sbVer.append(WiFi.VER_SPLIT);
            sbVer.append(strMonth);
            sbVer.append(WiFi.VER_SPLIT);
            sbVer.append(strDay);
            if (strVersion.length() > 8) {
                sbVer.append(WiFi.VER_SPLIT);
                sbVer.append(strVersion.substring(8));
            }
            return sbVer.toString();
        }

        private void getVersion() {
            String fwManifestVersion = EMWifi.getFwManifestVersion();
            this.mStrManifestInfo = fwManifestVersion;
            if (fwManifestVersion == null || fwManifestVersion.isEmpty()) {
                StringBuilder stringBuild = new StringBuilder();
                stringBuild.append("VERSION: CHIP = MT");
                long[] version = new long[2];
                if (EMWifi.getATParam(WiFi.FUNC_INDEX_VERSION, version) == 0) {
                    Elog.v(WiFi.TAG, "version number is: 0x" + Long.toHexString(version[0]));
                    stringBuild.append(Long.toHexString((version[0] & WiFi.MASK_HIGH_16_BIT) >> 16));
                    stringBuild.append("  FW VER = v");
                    stringBuild.append(Long.toHexString((version[0] & WiFi.MASK_HIGH_8_BIT) >> 8));
                    stringBuild.append(".");
                    stringBuild.append(Long.toHexString(version[0] & 255));
                    stringBuild.append(".");
                    stringBuild.append(Long.toHexString(version[1] & -1));
                    this.mStrVersion = stringBuild.toString();
                }
            }
        }

        private void showVersion() {
            TextView tvVerOri = (TextView) WiFi.this.findViewById(R.id.wifi_version);
            String str = this.mStrManifestInfo;
            if (str != null && !str.isEmpty()) {
                Elog.v(WiFi.TAG, "strManifestInfo:" + this.mStrManifestInfo);
                WiFi.this.findViewById(R.id.wifi_branch_layout).setVisibility(0);
                WiFi.this.findViewById(R.id.wifi_ver_layout).setVisibility(0);
                tvVerOri.setVisibility(8);
                String strBranch = getBranch(this.mStrManifestInfo);
                if (strBranch != null) {
                    String strBranch2 = strBranch.substring(0, strBranch.length() - 1);
                    Elog.v(WiFi.TAG, "strBranch:" + strBranch2);
                    ((TextView) WiFi.this.findViewById(R.id.wifi_branch_tv)).setText(strBranch2);
                }
                String strVer = getVer(this.mStrManifestInfo);
                Elog.v(WiFi.TAG, "strVer:" + strVer);
                if (strVer != null) {
                    ((TextView) WiFi.this.findViewById(R.id.wifi_ver_tv)).setText(strVer);
                }
            } else if (this.mStrVersion != null) {
                Elog.v(WiFi.TAG, "mStrVersion:" + this.mStrVersion);
                tvVerOri.setText(this.mStrVersion);
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            WiFi.this.showDialog(0);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(WifiEmState result) {
            super.onPostExecute(result);
            switch (AnonymousClass3.$SwitchMap$com$mediatek$engineermode$wifi$WifiEmState[result.ordinal()]) {
                case 1:
                    WiFi.this.removeDialog(0);
                    WiFi.this.showDialog(2);
                    return;
                case 2:
                    WiFi.this.removeDialog(0);
                    WiFi.this.showDialog(3);
                    return;
                case 3:
                    ArrayList unused = WiFi.this.mListData = new ArrayList();
                    WiFi.this.mListData.add(WiFi.this.getString(R.string.wifi_item_tx));
                    WiFi.this.mListData.add(WiFi.this.getString(R.string.wifi_item_rx));
                    if (!FeatureSupport.isUserLoad()) {
                        WiFi.this.mListData.add(WiFi.this.getString(R.string.wifi_item_mcr));
                        if (!WiFi.this.mWifiType.equals(WifiType.WIFI_6620)) {
                            WiFi.this.mListData.add(WiFi.this.getString(R.string.wifi_item_rfcr));
                        }
                        WiFi.this.mListData.add(WiFi.this.getString(R.string.wifi_item_nvram));
                    }
                    if (WiFi.this.mCapability.isDbdcSupport()) {
                        WiFi.this.mListData.add(WiFi.this.getString(R.string.wifi_item_dbdc_mode));
                    }
                    WiFi wiFi = WiFi.this;
                    WiFi.this.mLvTestItem.setAdapter(new ArrayAdapter<>(wiFi, 17367043, wiFi.mListData));
                    showVersion();
                    WiFi.this.removeDialog(0);
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: protected */
        public WifiEmState doInBackground(Void... params) {
            WifiEmState state = EMWifi.startTestMode(WiFi.this.mWifiManager);
            if (WifiEmState.WIFI_EM_STATE_READY.equals(state)) {
                String unused = WiFi.this.mWifiChip = WiFi.getWifiChip();
                WiFi wiFi = WiFi.this;
                WifiType unused2 = wiFi.mWifiType = WiFi.getWifiType(wiFi.mWifiChip);
                Elog.i(WiFi.TAG, "wifi chip:" + WiFi.this.mWifiChip + " wifi type:" + WiFi.this.mWifiType);
                WiFi wiFi2 = WiFi.this;
                WifiCapability unused3 = wiFi2.mCapability = WifiCapability.getWifiCapability(wiFi2.mWifiChip, WiFi.this.mWifiType);
                getVersion();
                EMWifi.stopTestMode();
            }
            return state;
        }
    }

    public static WifiType getWifiType(String wifiChip) {
        if (CHIP_6632.equals(wifiChip) || "0".equals(wifiChip)) {
            return WifiType.WIFI_6632;
        }
        if ("1".equals(wifiChip)) {
            return WifiType.WIFI_FORMAT;
        }
        return WifiType.WIFI_6620;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi);
        WifiManager wifiManager = (WifiManager) getSystemService("wifi");
        this.mWifiManager = wifiManager;
        if (1 != wifiManager.getWifiState() || this.mWifiManager.isScanAlwaysAvailable()) {
            showDialog(1);
            return;
        }
        ListView listView = (ListView) findViewById(R.id.wifi_item_lv);
        this.mLvTestItem = listView;
        listView.setOnItemClickListener(this);
        new LoadTask().execute(new Void[0]);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                ProgressDialog innerDialog = new ProgressDialog(this);
                innerDialog.setTitle(R.string.wifi_dialog_init);
                innerDialog.setMessage(getString(R.string.wifi_dialog_init_message));
                innerDialog.setCancelable(false);
                innerDialog.setIndeterminate(true);
                return innerDialog;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.em_error);
                builder.setCancelable(false);
                builder.setMessage(getString(R.string.wifi_dialog_warn_message));
                builder.setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WiFi.this.finish();
                    }
                });
                return builder.create();
            case 2:
                Elog.e(TAG, "turn on wifi failed with airplane mode" + EmUtils.ifAirplaneModeEnabled());
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setCancelable(false);
                builder2.setTitle(R.string.em_warning);
                builder2.setMessage(getString(R.string.wifi_turn_on_fail));
                builder2.setPositiveButton(R.string.em_ok, (DialogInterface.OnClickListener) null);
                return builder2.create();
            case 3:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setTitle(R.string.em_error);
                builder3.setCancelable(false);
                builder3.setMessage(getString(R.string.wifi_int_test_mode_failed));
                builder3.setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WiFi.this.finish();
                    }
                });
                return builder3.create();
            default:
                return null;
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        String itemName = this.mListData.get(arg2);
        if (itemName != null) {
            Intent intent = null;
            if (itemName.equals(getString(R.string.wifi_item_tx))) {
                switch (AnonymousClass3.$SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType[this.mWifiType.ordinal()]) {
                    case 1:
                        intent = new Intent(this, WiFiTx6620.class);
                        intent.putExtra(KEY_11AC_CAP, this.mCapability.is11AcSupport());
                        intent.putExtra(KEY_ANT_SWAP, this.mCapability.isAntSwapSupport());
                        break;
                    case 2:
                        intent = new Intent(this, WiFiTx6632.class);
                        intent.putExtra(KEY_ANT_SWAP, this.mCapability.isAntSwapSupport());
                        break;
                    case 3:
                        intent = new Intent(this, WiFiTxFormat.class);
                        intent.putExtra(KEY_2BY2_CAP, this.mCapability.is2by2Support());
                        intent.putExtra(KEY_ANT_SWAP, this.mCapability.isAntSwapSupport());
                        intent.putExtra(KEY_11AX_CAP, this.mCapability.is11AxSupport());
                        intent.putExtra(KEY_HW_TX_CAP, this.mCapability.isHwTxSupport());
                        intent.putExtra(KEY_LDPC_CAP, this.mCapability.isLdpcSupport());
                        intent.putExtra(KEY_BW_160C_CAP, this.mCapability.is160cSupport());
                        intent.putExtra(KEY_BW_160NC_CAP, this.mCapability.is160ncSupport());
                        intent.putExtra(KEY_CH_BAND, this.mCapability.getCapChBand().ordinal());
                        break;
                    default:
                        return;
                }
            } else if (itemName.equals(getString(R.string.wifi_item_rx))) {
                switch (AnonymousClass3.$SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType[this.mWifiType.ordinal()]) {
                    case 1:
                        intent = new Intent(this, WiFiRx6620.class);
                        intent.putExtra(KEY_11AC_CAP, this.mCapability.is11AcSupport());
                        intent.putExtra(KEY_ANT_SWAP, this.mCapability.isAntSwapSupport());
                        break;
                    case 2:
                        intent = new Intent(this, WiFiRx6632.class);
                        intent.putExtra(KEY_ANT_SWAP, this.mCapability.isAntSwapSupport());
                        break;
                    case 3:
                        intent = new Intent(this, WiFiRxFormat.class);
                        intent.putExtra(KEY_2BY2_CAP, this.mCapability.is2by2Support());
                        intent.putExtra(KEY_ANT_SWAP, this.mCapability.isAntSwapSupport());
                        intent.putExtra(KEY_11AX_CAP, this.mCapability.is11AxSupport());
                        intent.putExtra(KEY_BW_160C_CAP, this.mCapability.is160cSupport());
                        intent.putExtra(KEY_BW_160NC_CAP, this.mCapability.is160ncSupport());
                        intent.putExtra(KEY_CH_BAND, this.mCapability.getCapChBand().ordinal());
                        break;
                    default:
                        return;
                }
            } else if (itemName.equals(getString(R.string.wifi_item_mcr))) {
                try {
                    intent = new Intent(this, Class.forName("com.mediatek.engineermode.wifi.WiFiMcr"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
            } else if (itemName.equals(getString(R.string.wifi_item_rfcr))) {
                try {
                    intent = new Intent(this, Class.forName("com.mediatek.engineermode.wifi.WiFiRFCR"));
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                    return;
                }
            } else if (itemName.equals(getString(R.string.wifi_item_nvram))) {
                try {
                    intent = new Intent(this, Class.forName("com.mediatek.engineermode.wifi.WiFiEeprom"));
                } catch (ClassNotFoundException e3) {
                    e3.printStackTrace();
                    return;
                }
            } else if (itemName.equals(getString(R.string.wifi_item_dbdc_mode))) {
                intent = new Intent(this, WiFiDBDC.class);
                intent.putExtra(KEY_WIFI_TYPE, this.mWifiType.ordinal());
                intent.putExtra(KEY_ANT_SWAP, this.mCapability.isAntSwapSupport());
                intent.putExtra(KEY_11AX_CAP, this.mCapability.is11AxSupport());
                intent.putExtra(KEY_HW_TX_CAP, this.mCapability.isHwTxSupport());
                intent.putExtra(KEY_LDPC_CAP, this.mCapability.isLdpcSupport());
                intent.putExtra(KEY_BW_160C_CAP, this.mCapability.is160cSupport());
                intent.putExtra(KEY_BW_160NC_CAP, this.mCapability.is160ncSupport());
                intent.putExtra(KEY_CH_BAND, this.mCapability.getCapChBand().ordinal());
            }
            startActivity(intent);
        }
    }

    /* renamed from: com.mediatek.engineermode.wifi.WiFi$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType;
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$wifi$WifiEmState;

        static {
            int[] iArr = new int[WifiType.values().length];
            $SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType = iArr;
            try {
                iArr[WifiType.WIFI_6620.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType[WifiType.WIFI_6632.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WiFi$WifiType[WifiType.WIFI_FORMAT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[WifiEmState.values().length];
            $SwitchMap$com$mediatek$engineermode$wifi$WifiEmState = iArr2;
            try {
                iArr2[WifiEmState.WIFI_EM_STATE_ENABLE_FAIL.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WifiEmState[WifiEmState.WIFI_EM_STATE_SET_TM_FAIL.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$wifi$WifiEmState[WifiEmState.WIFI_EM_STATE_READY.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public static String getWifiChip() {
        long[] version = new long[2];
        if (EMWifi.getATParam(FUNC_INDEX_VERSION, version) != 0) {
            return null;
        }
        return Long.toHexString((version[0] & MASK_HIGH_16_BIT) >> 16);
    }
}
