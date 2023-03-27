package com.mediatek.engineermode.forceant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class ForceTxSelect extends Activity implements AdapterView.OnItemClickListener {
    public static final String TAG = "ForceTx/select";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_test);
        ListView simTypeListView = (ListView) findViewById(R.id.list);
        ArrayList<String> items = new ArrayList<>();
        if (FeatureSupport.is95Modem()) {
            items.add(getString(R.string.force_ant_95));
        } else if (FeatureSupport.is97ModemAndAbove()) {
            items.add(getString(R.string.force_ant_97));
        } else {
            items.add(getString(R.string.force_ant));
        }
        simTypeListView.setAdapter(new ArrayAdapter<>(this, 17367043, items));
        simTypeListView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                if (FeatureSupport.is95ModemAndAbove()) {
                    intent.setClass(this, ForceTx9597Menu.class);
                } else {
                    intent.setClass(this, ForceTx.class);
                }
                startActivity(intent);
                return;
            default:
                return;
        }
    }
}
