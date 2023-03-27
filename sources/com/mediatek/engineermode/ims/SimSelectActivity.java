package com.mediatek.engineermode.ims;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class SimSelectActivity extends Activity implements AdapterView.OnItemClickListener {
    public static final String MULT_IMS_SUPPORT = "persist.vendor.mims_support";
    private static final String TAG = "Ims/simSelect";
    int mims_num = 0;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dualtalk_networkinfo);
        ListView simTypeListView = (ListView) findViewById(R.id.ListView_dualtalk_networkinfo);
        ArrayList<String> items = new ArrayList<>();
        this.mims_num = Integer.valueOf(SystemProperties.get(MULT_IMS_SUPPORT, "1").toString()).intValue();
        Elog.d(TAG, "persist.vendor.mims_support = " + this.mims_num);
        int i = this.mims_num;
        if (i == 1) {
            if (ModemCategory.getCapabilitySim() == 0) {
                items.add("sim1: " + getString(R.string.ims_primary_card));
            } else if (ModemCategory.getCapabilitySim() == 1) {
                items.add("sim2: " + getString(R.string.ims_primary_card));
            }
        } else if (i == 2) {
            if (ModemCategory.getCapabilitySim() == 0) {
                items.add("sim1: " + getString(R.string.ims_primary_card));
                items.add("sim2: " + getString(R.string.ims_secondary_card));
            } else if (ModemCategory.getCapabilitySim() == 1) {
                items.add("sim1: " + getString(R.string.ims_secondary_card));
                items.add("sim2: " + getString(R.string.ims_primary_card));
            }
        } else if (i == 3) {
            items.add(getString(R.string.bandmode_sim1));
            items.add(getString(R.string.bandmode_sim2));
            items.add(getString(R.string.bandmode_sim3));
        } else if (i == 4) {
            items.add(getString(R.string.bandmode_sim1));
            items.add(getString(R.string.bandmode_sim2));
            items.add(getString(R.string.bandmode_sim3));
            items.add(getString(R.string.bandmode_sim4));
        }
        simTypeListView.setAdapter(new ArrayAdapter<>(this, 17367043, items));
        simTypeListView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        int simType;
        Intent intent = new Intent();
        intent.setClassName(this, "com.mediatek.engineermode.ims.ImsActivity");
        switch (position) {
            case 0:
                if (this.mims_num != 1 || ModemCategory.getCapabilitySim() != 1) {
                    simType = 0;
                    break;
                } else {
                    simType = 1;
                    break;
                }
            case 1:
                simType = 1;
                break;
            case 2:
                simType = 2;
                break;
            case 3:
                simType = 3;
                break;
            default:
                simType = 0;
                break;
        }
        Elog.d(TAG, "mSimType = " + simType);
        intent.putExtra("mSimType", simType);
        startActivity(intent);
    }
}
