package com.mediatek.engineermode.networkselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class NetworkSelectSimType extends Activity implements AdapterView.OnItemClickListener {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_select_sim_type);
        ListView simTypeListView = (ListView) findViewById(R.id.ListView_SimSelect);
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.networkinfo_sim1));
        if (TelephonyManager.getDefault().getSimCount() > 1) {
            items.add(getString(R.string.networkinfo_sim2));
        }
        simTypeListView.setAdapter(new ArrayAdapter<>(this, 17367043, items));
        simTypeListView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        Intent intent = new Intent();
        String className = "com.mediatek.engineermode.networkselect.NetworkSelectActivity";
        if (FeatureSupport.is93ModemAndAbove()) {
            className = "com.mediatek.engineermode.networkselect.NetworkSelectActivity93";
        }
        switch (position) {
            case 0:
                intent.setClassName(this, className);
                intent.putExtra("mSimType", 0);
                break;
            case 1:
                intent.setClassName(this, className);
                intent.putExtra("mSimType", 1);
                break;
        }
        startActivity(intent);
    }
}
