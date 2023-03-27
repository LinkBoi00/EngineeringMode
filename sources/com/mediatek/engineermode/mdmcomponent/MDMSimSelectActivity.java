package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class MDMSimSelectActivity extends Activity implements AdapterView.OnItemClickListener {
    public static final int MODEM_PROTOCOL_1 = 1;
    public static final int MODEM_PROTOCOL_2 = 2;
    public static final int MODEM_PROTOCOL_3 = 3;
    private static final String TAG = "EmInfo/MDMSimSelectActivity";
    public static String[] mSimMccMnc = new String[3];
    ArrayAdapter<String> adapter = null;
    public int defaultDataPhoneID = 0;
    ArrayList<String> items = null;
    int[] modemTypeArray = new int[3];
    ListView simTypeListView = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dualtalk_networkinfo);
        this.simTypeListView = (ListView) findViewById(R.id.ListView_dualtalk_networkinfo);
        this.items = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367043, this.items);
        this.adapter = arrayAdapter;
        this.simTypeListView.setAdapter(arrayAdapter);
        this.simTypeListView.setOnItemClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        String str;
        super.onResume();
        this.items.clear();
        this.defaultDataPhoneID = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        int phoneIdMain = ModemCategory.getCapabilitySim();
        if (phoneIdMain == 0) {
            mSimMccMnc[0] = telephonyManager.getSimOperatorNumericForPhone(0);
            this.items.add("sim1(" + mSimMccMnc[0] + "): Protocol 1(Primary Card)");
            if (TelephonyManager.getDefault().getPhoneCount() > 1) {
                mSimMccMnc[1] = telephonyManager.getSimOperatorNumericForPhone(1);
                this.items.add("sim2(" + mSimMccMnc[1] + "): Protocol 2");
            }
            if (TelephonyManager.getDefault().getPhoneCount() > 2) {
                mSimMccMnc[2] = telephonyManager.getSimOperatorNumericForPhone(2);
                this.items.add("sim3(" + mSimMccMnc[2] + "): Protocol 3");
            }
            int[] iArr = this.modemTypeArray;
            iArr[0] = 1;
            iArr[1] = 2;
            iArr[2] = 3;
        } else if (phoneIdMain == 1) {
            mSimMccMnc[0] = telephonyManager.getSimOperatorNumericForPhone(1);
            mSimMccMnc[1] = telephonyManager.getSimOperatorNumericForPhone(0);
            this.items.add("sim1(" + mSimMccMnc[1] + "): Protocol 2");
            this.items.add("sim2(" + mSimMccMnc[0] + "): Protocol 1(Primary Card)");
            if (TelephonyManager.getDefault().getPhoneCount() > 2) {
                mSimMccMnc[2] = telephonyManager.getSimOperatorNumericForPhone(2);
                this.items.add("sim3(" + mSimMccMnc[2] + "): Protocol 3");
            }
            int[] iArr2 = this.modemTypeArray;
            iArr2[0] = 2;
            iArr2[1] = 1;
            iArr2[2] = 3;
        } else if (phoneIdMain == 2) {
            if (FeatureSupport.is93ModemAndAbove()) {
                mSimMccMnc[0] = telephonyManager.getSimOperatorNumericForPhone(2);
                mSimMccMnc[1] = telephonyManager.getSimOperatorNumericForPhone(0);
                mSimMccMnc[2] = telephonyManager.getSimOperatorNumericForPhone(1);
                this.items.add("sim1(" + mSimMccMnc[1] + "): Protocol 2");
                this.items.add("sim2(" + mSimMccMnc[2] + "): Protocol 3");
                this.items.add("sim3(" + mSimMccMnc[0] + "): Protocol 1(Primary Card)");
                int[] iArr3 = this.modemTypeArray;
                iArr3[0] = 2;
                iArr3[1] = 3;
                iArr3[2] = 1;
            } else {
                mSimMccMnc[0] = telephonyManager.getSimOperatorNumericForPhone(2);
                mSimMccMnc[1] = telephonyManager.getSimOperatorNumericForPhone(1);
                mSimMccMnc[2] = telephonyManager.getSimOperatorNumericForPhone(0);
                this.items.add("sim1(" + mSimMccMnc[2] + "): Protocol 3");
                this.items.add("sim2(" + mSimMccMnc[1] + "): Protocol 2");
                this.items.add("sim3(" + mSimMccMnc[0] + "): Protocol 1(Primary Card)");
                int[] iArr4 = this.modemTypeArray;
                iArr4[0] = 3;
                iArr4[1] = 2;
                iArr4[2] = 1;
            }
        }
        ArrayList<String> arrayList = this.items;
        StringBuilder sb = new StringBuilder();
        sb.append("Default data is ");
        String str2 = "null";
        if (this.defaultDataPhoneID == -1) {
            str = str2;
        } else {
            str = "sim" + (this.defaultDataPhoneID + 1);
        }
        sb.append(str);
        arrayList.add(sb.toString());
        Elog.d(TAG, "Read SIM MCC+MNC(PS1):" + mSimMccMnc[0]);
        Elog.d(TAG, "Read SIM MCC+MNC(PS2):" + mSimMccMnc[1]);
        Elog.d(TAG, "Read SIM MCC+MNC(PS3):" + mSimMccMnc[2]);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Default data is ");
        if (this.defaultDataPhoneID != -1) {
            str2 = "sim" + (this.defaultDataPhoneID + 1);
        }
        sb2.append(str2);
        Elog.d(TAG, sb2.toString());
        this.adapter.notifyDataSetInvalidated();
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        Intent intent = new Intent();
        int simType = 0;
        int modemType = 1;
        intent.setClassName(this, "com.mediatek.engineermode.mdmcomponent.ComponentSelectActivity");
        switch (position) {
            case 0:
                simType = 0;
                modemType = this.modemTypeArray[0];
                break;
            case 1:
                simType = 1;
                modemType = this.modemTypeArray[1];
                break;
            case 2:
                if (TelephonyManager.getDefault().getPhoneCount() > 2) {
                    simType = 2;
                    modemType = this.modemTypeArray[2];
                    break;
                } else {
                    return;
                }
        }
        intent.putExtra("mSimType", simType);
        intent.putExtra("mModemType", modemType);
        if (!ComponentSelectActivity.initDone) {
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.mdm_not_close), 1).show();
        }
    }
}
