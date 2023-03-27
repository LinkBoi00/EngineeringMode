package com.mediatek.engineermode.rfdesense;

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

public class RfDesenseTest extends Activity implements AdapterView.OnItemClickListener {
    public static final String TAG = "RfDesense/TestMenu";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_test);
        ListView simTypeListView = (ListView) findViewById(R.id.list);
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.rf_desense_tx_test));
        items.add(getString(R.string.rf_desense_gsm_control));
        if (FeatureSupport.is95Modem()) {
            items.add(getString(R.string.rf_desense_tx_test_lte_CA));
        }
        if (FeatureSupport.is97ModemAndAbove()) {
            items.add(getString(R.string.rf_desense_digrf_label));
        }
        simTypeListView.setAdapter(new ArrayAdapter<>(this, 17367043, items));
        simTypeListView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, RfDesenseTxTest.class);
                startActivity(intent);
                return;
            case 1:
                intent.setClass(this, RfDesenseGsmPowerControl.class);
                startActivity(intent);
                return;
            case 2:
                if (FeatureSupport.is95Modem()) {
                    intent.setClass(this, RfDesenseTxTestLteCA.class);
                } else if (FeatureSupport.is97ModemAndAbove()) {
                    intent.setClass(this, RfDesenseDigRFTest.class);
                }
                startActivity(intent);
                return;
            case 3:
                intent.setClass(this, RfDesenseDigRFTest.class);
                startActivity(intent);
                return;
            default:
                return;
        }
    }
}
