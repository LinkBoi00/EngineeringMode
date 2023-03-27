package com.mediatek.engineermode.wificalling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class WifiCallingConfigActivity extends Activity implements AdapterView.OnItemClickListener {
    private static String TAG = "WifiCallingConfigActivity";
    private ListView itemListView;
    private ArrayList<String> items;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(this).inflate(R.layout.base_listview, (ViewGroup) null));
        this.itemListView = (ListView) findViewById(R.id.base_listview_id);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        ArrayList<String> arrayList = new ArrayList<>();
        this.items = arrayList;
        arrayList.add(getString(R.string.entitlement_config));
        if (FeatureSupport.is93ModemAndAbove() && FeatureSupport.isSupportWfc() && !FeatureSupport.isUserLoad()) {
            this.items.add(getString(R.string.epdg_config));
        }
        this.itemListView.setAdapter(new ArrayAdapter<>(this, 17367043, this.items));
        this.itemListView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClassName(this, "com.mediatek.engineermode.wificalling.EntitlementConfigActivity");
                break;
            case 1:
                intent.setClassName(this, "com.mediatek.engineermode.epdgconfig.epdgConfigSimSelect");
                break;
        }
        startActivity(intent);
    }
}
