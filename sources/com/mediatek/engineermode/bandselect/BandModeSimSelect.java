package com.mediatek.engineermode.bandselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class BandModeSimSelect extends Activity implements AdapterView.OnItemClickListener {
    public void onCreate(Bundle savedInstanceState) {
        ArrayAdapter arrayAdapter;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bandmodesimselect);
        ListView simTypeListView = (ListView) findViewById(R.id.listview_bandmode_sim_select);
        if (TelephonyManager.getDefault().getSimCount() > 1) {
            ArrayList<String> array = new ArrayList<>();
            array.add(getString(R.string.bandmode_sim1));
            array.add(getString(R.string.bandmode_sim2));
            if (TelephonyManager.getDefault().getSimCount() >= 3) {
                array.add(getString(R.string.bandmode_sim3));
            }
            if (TelephonyManager.getDefault().getSimCount() == 4) {
                array.add(getString(R.string.bandmode_sim4));
            }
            arrayAdapter = new ArrayAdapter(this, 17367043, array);
        } else {
            ArrayList<String> array2 = new ArrayList<>();
            array2.add(getString(R.string.bandmode_sim1));
            arrayAdapter = new ArrayAdapter(this, 17367043, array2);
        }
        simTypeListView.setAdapter(arrayAdapter);
        simTypeListView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, BandSelect.class);
                intent.putExtra("mSimType", 0);
                break;
            case 1:
                intent.setClass(this, BandSelect.class);
                intent.putExtra("mSimType", 1);
                break;
            case 2:
                intent.setClass(this, BandSelect.class);
                intent.putExtra("mSimType", 2);
                break;
            case 3:
                intent.setClass(this, BandSelect.class);
                intent.putExtra("mSimType", 3);
                break;
        }
        startActivity(intent);
    }
}
