package com.mediatek.engineermode.mcfconfig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class McfSimSelectActivity extends Activity implements AdapterView.OnItemClickListener {
    public static int SHOW_CERTIFICATION_VIEW = -3;
    public static int SHOW_GENERAL_SIM_VIEW = -1;
    public static int SHOW_GENERAL_VIEW = -2;
    public static int SHOW_LOAD_INFO_VIEW = -4;
    private static final String TAG = "McfConfig/McfSimSelectActivity";
    ArrayAdapter<String> adapter = null;
    Map<String, Integer> itemMap;
    ArrayList<String> items = null;
    ListView simTypeListView = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dualtalk_networkinfo);
        this.simTypeListView = (ListView) findViewById(R.id.ListView_dualtalk_networkinfo);
        this.items = new ArrayList<>();
        this.itemMap = new HashMap();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367043, this.items);
        this.adapter = arrayAdapter;
        this.simTypeListView.setAdapter(arrayAdapter);
        this.simTypeListView.setOnItemClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.items.clear();
        this.itemMap.clear();
        if (FeatureSupport.is98Modem()) {
            this.items.add("MCF Load Information");
            this.itemMap.put("MCF Load Information", Integer.valueOf(SHOW_LOAD_INFO_VIEW));
        }
        if (ModemCategory.getCapabilitySim() == 0) {
            ArrayList<String> arrayList = this.items;
            arrayList.add("Sim1: " + getString(R.string.mdm_em_components_sim1));
            Map<String, Integer> map = this.itemMap;
            map.put("Sim1: " + getString(R.string.mdm_em_components_sim1), 0);
            ArrayList<String> arrayList2 = this.items;
            arrayList2.add("Sim2: " + getString(R.string.mdm_em_components_sim2));
            Map<String, Integer> map2 = this.itemMap;
            map2.put("Sim2: " + getString(R.string.mdm_em_components_sim2), 1);
        } else if (ModemCategory.getCapabilitySim() == 1) {
            ArrayList<String> arrayList3 = this.items;
            arrayList3.add("Sim1: " + getString(R.string.mdm_em_components_sim2));
            Map<String, Integer> map3 = this.itemMap;
            map3.put("Sim1: " + getString(R.string.mdm_em_components_sim2), 0);
            ArrayList<String> arrayList4 = this.items;
            arrayList4.add("Sim2: " + getString(R.string.mdm_em_components_sim1));
            Map<String, Integer> map4 = this.itemMap;
            map4.put("Sim2: " + getString(R.string.mdm_em_components_sim1), 1);
        }
        if (FeatureSupport.is98Modem()) {
            this.items.add("General Sim");
            this.itemMap.put("General Sim", Integer.valueOf(SHOW_GENERAL_SIM_VIEW));
        }
        this.items.add("General");
        this.itemMap.put("General", Integer.valueOf(SHOW_GENERAL_VIEW));
        this.items.add("Certification");
        this.itemMap.put("Certification", Integer.valueOf(SHOW_CERTIFICATION_VIEW));
        this.adapter.notifyDataSetInvalidated();
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        Intent intent = new Intent();
        if (FeatureSupport.is98Modem()) {
            Elog.d(TAG, "support modem > 98");
            intent.setClassName(this, "com.mediatek.engineermode.mcfconfig.McfConfigActivity98");
        } else if (FeatureSupport.is95ModemAndAbove()) {
            Elog.d(TAG, "support modem > 95");
            intent.setClassName(this, "com.mediatek.engineermode.mcfconfig.McfConfigActivity");
        } else {
            Elog.d(TAG, "support modem < 95");
            intent.setClassName(this, "com.mediatek.engineermode.mcfconfig.McfConfigActivity93");
        }
        intent.putExtra("mSimType", this.itemMap.get(this.items.get(position)));
        startActivity(intent);
    }
}
