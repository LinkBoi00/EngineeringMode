package com.mediatek.engineermode.wifi;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.wifi.WiFi;
import com.mediatek.engineermode.wifi.WifiCapability;

public class WiFiDBDC extends TabActivity {
    static final String KEY_MODE = "mode";
    static final String RX_BAND_0 = "RX Band0";
    static final String RX_BAND_1 = "RX Band1";
    private static final String TAG = "EM/WiFiDBDC";
    static final String TX_BAND_0 = "TX Band0";
    static final String TX_BAND_1 = "TX Band1";
    private boolean mCap11ax = false;
    private boolean mCapAntSwap = false;
    private boolean mCapBw160c = false;
    private boolean mCapBw160nc = false;
    private int mCapChBand = WifiCapability.CapChBand.CAP_CH_BAND_DEFAULT.ordinal();
    private boolean mCapHwTx = false;
    private boolean mCapLdpc = false;
    private TabHost mTabHost = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Elog.d(TAG, "onCreate");
        Intent it = getIntent();
        this.mCapAntSwap = it.getBooleanExtra("key_ant_swap", false);
        this.mCap11ax = it.getBooleanExtra("key_11ax", false);
        this.mCapHwTx = it.getBooleanExtra("key_hw_tx", false);
        this.mCapLdpc = it.getBooleanExtra("key_ldpc", false);
        this.mCapBw160c = it.getBooleanExtra("key_bw_160c", false);
        this.mCapBw160nc = it.getBooleanExtra("key_bw_160nc", false);
        this.mCapChBand = it.getIntExtra("key_ch_band", WifiCapability.CapChBand.CAP_CH_BAND_DEFAULT.ordinal());
        this.mTabHost = getTabHost();
        if (getIntent().getIntExtra("key_wifi_type", WiFi.WifiType.WIFI_6632.ordinal()) == WiFi.WifiType.WIFI_FORMAT.ordinal()) {
            TabHost tabHost = this.mTabHost;
            tabHost.addTab(tabHost.newTabSpec(TX_BAND_0).setIndicator(TX_BAND_0).setContent(configIntentParam(new Intent(this, WiFiTxFormat.class).putExtra(KEY_MODE, TX_BAND_0), true)));
            TabHost tabHost2 = this.mTabHost;
            tabHost2.addTab(tabHost2.newTabSpec(RX_BAND_0).setIndicator(RX_BAND_0).setContent(configIntentParam(new Intent(this, WiFiRxFormat.class).putExtra(KEY_MODE, RX_BAND_0), false)));
            TabHost tabHost3 = this.mTabHost;
            tabHost3.addTab(tabHost3.newTabSpec(TX_BAND_1).setIndicator(TX_BAND_1).setContent(configIntentParam(new Intent(this, WiFiTxFormat.class).putExtra(KEY_MODE, TX_BAND_1), true)));
            TabHost tabHost4 = this.mTabHost;
            tabHost4.addTab(tabHost4.newTabSpec(RX_BAND_1).setIndicator(RX_BAND_1).setContent(configIntentParam(new Intent(this, WiFiRxFormat.class).putExtra(KEY_MODE, RX_BAND_1), false)));
            return;
        }
        TabHost tabHost5 = this.mTabHost;
        tabHost5.addTab(tabHost5.newTabSpec(TX_BAND_0).setIndicator(TX_BAND_0).setContent(new Intent(this, WiFiTx6632.class).putExtra(KEY_MODE, TX_BAND_0)));
        TabHost tabHost6 = this.mTabHost;
        tabHost6.addTab(tabHost6.newTabSpec(RX_BAND_0).setIndicator(RX_BAND_0).setContent(new Intent(this, WiFiRx6632.class).putExtra(KEY_MODE, RX_BAND_0)));
        TabHost tabHost7 = this.mTabHost;
        tabHost7.addTab(tabHost7.newTabSpec(TX_BAND_1).setIndicator(TX_BAND_1).setContent(new Intent(this, WiFiTx6632.class).putExtra(KEY_MODE, TX_BAND_1)));
        TabHost tabHost8 = this.mTabHost;
        tabHost8.addTab(tabHost8.newTabSpec(RX_BAND_1).setIndicator(RX_BAND_1).setContent(new Intent(this, WiFiRx6632.class).putExtra(KEY_MODE, RX_BAND_1)));
    }

    private Intent configIntentParam(Intent it, boolean isTx) {
        it.putExtra("key_ant_swap", this.mCapAntSwap).putExtra("key_11ax", this.mCap11ax).putExtra("key_bw_160c", this.mCapBw160c).putExtra("key_bw_160nc", this.mCapBw160nc).putExtra("key_ch_band", this.mCapChBand);
        if (isTx) {
            it.putExtra("key_hw_tx", this.mCapHwTx).putExtra("key_ldpc", this.mCapLdpc);
        }
        return it;
    }
}
