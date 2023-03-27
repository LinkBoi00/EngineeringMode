package com.mediatek.engineermode.anttunerdebug;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import com.mediatek.engineermode.R;

public class AntTunerDebugInterfaceSelect extends TabActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.ant_tuner_debug_mipi).toString()).setIndicator((CharSequence) null, getApplicationContext().getDrawable(R.drawable.ic_m)).setContent(new Intent(this, AntTunerDebugMIPI.class)));
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.ant_tuner_debug_bpi).toString()).setIndicator((CharSequence) null, getApplicationContext().getDrawable(R.drawable.ic_b)).setContent(new Intent(this, AntTunerDebugBPI.class)));
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.ant_tuner_debug_dat).toString()).setIndicator((CharSequence) null, getApplicationContext().getDrawable(R.drawable.ic_d)).setContent(new Intent(this, AntTunerDebugDAT.class)));
        tabHost.setCurrentTab(0);
    }
}
