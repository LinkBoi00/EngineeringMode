package com.mediatek.engineermode.misc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.List;

public class PresenceActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String[] ITEM_INTENT_STRING = {"PresenceSet", AospPresenceTestActivity.TAG};
    private static final String[] ITEM_STRINGS = {"Presence Setting", "AOSP Presence Test"};
    private static final String PACKAGE_NAME = "com.mediatek.engineermode.misc";
    private static final String TAG = "Presence/Activity";
    private List<String> mListData;
    private ListView mMenuListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presence);
        ListView listView = (ListView) findViewById(R.id.ListViewPresence);
        this.mMenuListView = listView;
        if (listView == null) {
            Log.e(TAG, "Resource could not be allocated");
            return;
        }
        listView.setOnItemClickListener(this);
        Log.i(TAG, "onCreate in dsActivity");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mListData = getData();
        this.mMenuListView.setAdapter(new ArrayAdapter<>(this, 17367043, this.mListData));
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int menuId, long arg3) {
        Intent intent = new Intent();
        int i = 0;
        while (true) {
            String[] strArr = ITEM_STRINGS;
            if (i >= strArr.length) {
                break;
            } else if (strArr[i] == this.mListData.get(menuId)) {
                StringBuilder sb = new StringBuilder();
                sb.append("com.mediatek.engineermode.misc.");
                String[] strArr2 = ITEM_INTENT_STRING;
                sb.append(strArr2[i]);
                intent.setClassName(this, sb.toString());
                Log.i(TAG, "Start activity:" + strArr[i] + " inent:" + strArr2[i]);
                break;
            } else {
                i++;
            }
        }
        startActivity(intent);
    }

    private List<String> getData() {
        List<String> items = new ArrayList<>();
        int i = 0;
        while (true) {
            String[] strArr = ITEM_STRINGS;
            if (i >= strArr.length) {
                return items;
            }
            items.add(strArr[i]);
            i++;
        }
    }
}
