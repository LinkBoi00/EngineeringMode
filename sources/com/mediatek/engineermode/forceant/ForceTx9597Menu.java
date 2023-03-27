package com.mediatek.engineermode.forceant;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;

public class ForceTx9597Menu extends TabActivity {
    public static final String TAG = "ForceTx/9597Menu";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Class classe_name;
        super.onCreate(savedInstanceState);
        if (FeatureSupport.is95Modem()) {
            classe_name = ForceTx95.class;
            setTitle(R.string.force_ant_95);
        } else {
            classe_name = ForceTx97.class;
            setTitle(R.string.force_ant_97);
        }
        TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.force_ant_by_states).toString()).setIndicator(getString(R.string.force_ant_by_states).toString()).setContent(new Intent(this, classe_name).putExtra("MODE", "by_states")));
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.force_ant_by_index).toString()).setIndicator(getString(R.string.force_ant_by_index).toString()).setContent(new Intent(this, classe_name).putExtra("MODE", "by_index")));
    }
}
