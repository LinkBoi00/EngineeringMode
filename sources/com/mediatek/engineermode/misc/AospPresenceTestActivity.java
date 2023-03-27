package com.mediatek.engineermode.misc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.List;

public class AospPresenceTestActivity extends Activity {
    public static final String FUNCTION_OPTIONS_ADDLISTENER = "[OPTIONS]addListener";
    public static final String FUNCTION_OPTIONS_GETCONTACTCAP = "[OPTIONS]getContactCap";
    public static final String FUNCTION_OPTIONS_GETCONTACTLISTCAP = "[OPTIONS]getContactListCap";
    public static final String FUNCTION_OPTIONS_GETMYINFO = "[OPTIONS]getMyInfo";
    public static final String FUNCTION_OPTIONS_GETVERSION = "[OPTIONS]getVersion";
    public static final String FUNCTION_OPTIONS_REMOVELISTENER = "[OPTIONS]removeListener";
    public static final String FUNCTION_OPTIONS_RESPONSEINCOMINGOPTIONS = "[OPTIONS]responseIncomingOptions";
    public static final String FUNCTION_OPTIONS_SETMYINFO = "[OPTIONS]setMyInfo";
    public static final String FUNCTION_PRESENCE_ADDLISTENER = "[PRESENCE]addListener";
    public static final String FUNCTION_PRESENCE_GETCONTACTCAP = "[PRESENCE]getContactCap";
    public static final String FUNCTION_PRESENCE_GETCONTACTLISTCAP = "[PRESENCE]getContactListCap";
    public static final String FUNCTION_PRESENCE_GETVERSION = "[PRESENCE]getVersion";
    public static final String FUNCTION_PRESENCE_PUBLISHMYCAP = "[PRESENCE]publishMyCap";
    public static final String FUNCTION_PRESENCE_REENABLESERVICE = "[PRESENCE]reenableService";
    public static final String FUNCTION_PRESENCE_REMOVELISTENER = "[PRESENCE]removeListener";
    public static final String FUNCTION_UCE_CREATEOPTIONSSERVICE = "[UCE]createOptionsService";
    public static final String FUNCTION_UCE_CREATEPRESENCESERVICE = "[UCE]createPresenceService";
    public static final String FUNCTION_UCE_DESTROYOPTIONSSERVICE = "[UCE]destroyOptionsService";
    public static final String FUNCTION_UCE_DESTROYPRESENCESERVICE = "[UCE]destroyPresenceService";
    public static final String FUNCTION_UCE_GETOPTIONSSERVICE = "[UCE]getOptionsService";
    public static final String FUNCTION_UCE_GETPRESENCESERVICE = "[UCE]getPresenceService";
    public static final String FUNCTION_UCE_GETSERVICESTATUS = "[UCE]getServiceStatus";
    public static final String FUNCTION_UCE_ISSERVICESTARTED = "[UCE]isServiceStarted";
    public static final String FUNCTION_UCE_STARTSERVICE = "[UCE]startService";
    public static final String FUNCTION_UCE_STOPSERVICE = "[UCE]stopService";
    public static final String PREF_KEY_CONTACT1 = "Contact1PhoneNum";
    public static final String PREF_KEY_CONTACT2 = "Contact2PhoneNum";
    public static final String PREF_NAME = "PhoneNum";
    public static final String TAG = "AospPresenceTestActivity";
    private Button mButtonContact1;
    private Button mButtonContact2;
    /* access modifiers changed from: private */
    public EditText mEditTextContact1;
    /* access modifiers changed from: private */
    public EditText mEditTextContact2;
    private List<String> mFunctionList;
    private ListView mListView;
    /* access modifiers changed from: private */
    public SharedPreferences sharedPreferences;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aosp_presence_listview);
        this.mListView = (ListView) findViewById(R.id.Aosp_Presence_ListView);
        this.mButtonContact1 = (Button) findViewById(R.id.Contact1_Button);
        this.mButtonContact2 = (Button) findViewById(R.id.Contact2_Button);
        this.mEditTextContact1 = (EditText) findViewById(R.id.Contact1_EditText);
        this.mEditTextContact2 = (EditText) findViewById(R.id.Contact2_EditText);
        this.sharedPreferences = getSharedPreferences(PREF_NAME, 0);
        initClickListener();
        initFunctionList();
        Elog.d(TAG, "onCreate");
        this.mListView.setAdapter(new AospPresenceListAdapter(this.mFunctionList));
    }

    private void initFunctionList() {
        ArrayList arrayList = new ArrayList();
        this.mFunctionList = arrayList;
        arrayList.add(FUNCTION_UCE_STARTSERVICE);
        this.mFunctionList.add(FUNCTION_UCE_STOPSERVICE);
        this.mFunctionList.add(FUNCTION_UCE_ISSERVICESTARTED);
        this.mFunctionList.add(FUNCTION_UCE_CREATEOPTIONSSERVICE);
        this.mFunctionList.add(FUNCTION_UCE_DESTROYOPTIONSSERVICE);
        this.mFunctionList.add(FUNCTION_UCE_CREATEPRESENCESERVICE);
        this.mFunctionList.add(FUNCTION_UCE_DESTROYPRESENCESERVICE);
        this.mFunctionList.add(FUNCTION_UCE_GETSERVICESTATUS);
        this.mFunctionList.add(FUNCTION_UCE_GETPRESENCESERVICE);
        this.mFunctionList.add(FUNCTION_UCE_GETOPTIONSSERVICE);
        this.mFunctionList.add(FUNCTION_PRESENCE_ADDLISTENER);
        this.mFunctionList.add(FUNCTION_PRESENCE_REMOVELISTENER);
        this.mFunctionList.add(FUNCTION_PRESENCE_GETVERSION);
        this.mFunctionList.add(FUNCTION_PRESENCE_REENABLESERVICE);
        this.mFunctionList.add(FUNCTION_PRESENCE_PUBLISHMYCAP);
        this.mFunctionList.add(FUNCTION_PRESENCE_GETCONTACTCAP);
        this.mFunctionList.add(FUNCTION_PRESENCE_GETCONTACTLISTCAP);
        this.mFunctionList.add(FUNCTION_OPTIONS_ADDLISTENER);
        this.mFunctionList.add(FUNCTION_OPTIONS_REMOVELISTENER);
        this.mFunctionList.add(FUNCTION_OPTIONS_GETVERSION);
        this.mFunctionList.add(FUNCTION_OPTIONS_SETMYINFO);
        this.mFunctionList.add(FUNCTION_OPTIONS_GETMYINFO);
        this.mFunctionList.add(FUNCTION_OPTIONS_GETCONTACTCAP);
        this.mFunctionList.add(FUNCTION_OPTIONS_GETCONTACTLISTCAP);
        this.mFunctionList.add(FUNCTION_OPTIONS_RESPONSEINCOMINGOPTIONS);
    }

    private void initClickListener() {
        this.mButtonContact1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AospPresenceTestActivity.this.sharedPreferences.edit().putString(AospPresenceTestActivity.PREF_KEY_CONTACT1, AospPresenceTestActivity.this.mEditTextContact1.getText().toString()).apply();
            }
        });
        this.mButtonContact2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AospPresenceTestActivity.this.sharedPreferences.edit().putString(AospPresenceTestActivity.PREF_KEY_CONTACT2, AospPresenceTestActivity.this.mEditTextContact2.getText().toString()).apply();
            }
        });
    }
}
