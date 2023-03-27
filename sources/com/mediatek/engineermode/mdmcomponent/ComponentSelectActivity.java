package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.os.SystemService;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmApplication;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.mdmcomponent.MDMContentICD;
import com.mediatek.mdml.MONITOR_CMD_RESP;
import com.mediatek.mdml.MonitorCmdProxy;
import com.mediatek.mdml.MonitorTrapReceiver;
import com.mediatek.mdml.Msg;
import com.mediatek.mdml.PlainDataDecoder;
import com.mediatek.mdml.TRAP_TYPE;
import com.mediatek.mdml.TrapHandlerInterface;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class ComponentSelectActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private static final int CHECK_INFOMATION_ID = 1;
    private static final String COMPONENT_RSRPSINR_CONFIG_SHAREPRE = "telephony_rsrpsinr_config_settings";
    private static final int DIALOG_MDM_ERROR = 2;
    private static final int DIALOG_WAIT_INIT = 0;
    private static final int DIALOG_WAIT_SUBSCRIB = 1;
    private static final String KEY_SELECTED = "selected";
    private static final int LOADING_DONE = 1;
    private static final int MDM_ERROR = 4;
    private static final int MEDIUMWEAK_POINT1_X_DEFAULT = -140;
    private static final int MEDIUMWEAK_POINT1_Y_DEFAULT = 10;
    private static final int MEDIUMWEAK_POINT2_X_DEFAULT = -90;
    private static final int MEDIUMWEAK_POINT2_Y_DEFAULT = 10;
    private static final int MEDIUMWEAK_POINT3_X_DEFAULT = -90;
    private static final int MEDIUMWEAK_POINT3_Y_DEFAULT = -20;
    private static final int MSG_MDM_DISABLE = 2;
    private static final int MSG_MDM_LOADING = 0;
    private static final int MSG_MDM_SUBSCRIBE = 1;
    public static final int MSG_UPDATE_UI_URC_EM = 3;
    public static final int MSG_UPDATE_UI_URC_ICD = 1;
    private static final String PREF_FILE = "pref_file";
    private static final int SELECT_ALL_ID = 2;
    private static final int SELECT_NONE_ID = 3;
    private static final String SERVICE_NAME = "md_monitor";
    private static final int STRONG_POINT1_X_DEFAULT = -80;
    private static final int STRONG_POINT1_Y_DEFAULT = 30;
    private static final int STRONG_POINT2_X_DEFAULT = -80;
    private static final int STRONG_POINT2_Y_DEFAULT = 20;
    private static final int STRONG_POINT3_X_DEFAULT = -30;
    private static final int STRONG_POINT3_Y_DEFAULT = 20;
    private static final int SUBSCRIBE_DONE = 2;
    private static final String TAG = "EmInfo/ComponentSelectActivity";
    private static final int UPDATE_INTERVAL = 800;
    private static final int UPDATE_UI = 3;
    private static final int WAIT_TIMEOUT = 3000;
    private static final int WEAK_POINT1_X_DEFAULT = -140;
    private static final int WEAK_POINT1_Y_DEFAULT = 5;
    private static final int WEAK_POINT2_X_DEFAULT = -100;
    private static final int WEAK_POINT2_Y_DEFAULT = 5;
    private static final int WEAK_POINT3_X_DEFAULT = -100;
    private static final int WEAK_POINT3_Y_DEFAULT = -20;
    public static boolean initDone = false;
    /* access modifiers changed from: private */
    public static int k = 1;
    public static String mAutoRecordFlag = "0";
    public static String mAutoRecordFlagItem = "0";
    public static String[] mAutoRecordItem = {"Pcell and Scell basic info", "Handover (Intra-LTE)", "LTE ERRC OOS Event Info", "LTE ERRC ELF Event Info", "LTE EMAC Rach Failure Info", "MD Feature Detection"};
    public static ArrayList<Long> mCheckedEmTypes = new ArrayList<>();
    public static ArrayList<Long> mCheckedIcdEventTypes = new ArrayList<>();
    public static ArrayList<Long> mCheckedIcdRecordTypes = new ArrayList<>();
    /* access modifiers changed from: private */
    public static int mMediumWeakPoint1X = -140;
    /* access modifiers changed from: private */
    public static int mMediumWeakPoint1Y = 10;
    /* access modifiers changed from: private */
    public static int mMediumWeakPoint2X = -90;
    /* access modifiers changed from: private */
    public static int mMediumWeakPoint2Y = 10;
    /* access modifiers changed from: private */
    public static int mMediumWeakPoint3X = -90;
    /* access modifiers changed from: private */
    public static int mMediumWeakPoint3Y = -20;
    public static int mModemType;
    public static Set<String> mSelected_show_both_sim;
    public static int mSimType;
    /* access modifiers changed from: private */
    public static int mStrongPoint1X = -80;
    /* access modifiers changed from: private */
    public static int mStrongPoint1Y = 30;
    /* access modifiers changed from: private */
    public static int mStrongPoint2X = -80;
    /* access modifiers changed from: private */
    public static int mStrongPoint2Y = 20;
    /* access modifiers changed from: private */
    public static int mStrongPoint3X = STRONG_POINT3_X_DEFAULT;
    /* access modifiers changed from: private */
    public static int mStrongPoint3Y = 20;
    /* access modifiers changed from: private */
    public static int mWeakPoint1X = -140;
    /* access modifiers changed from: private */
    public static int mWeakPoint1Y = 5;
    /* access modifiers changed from: private */
    public static int mWeakPoint2X = -100;
    /* access modifiers changed from: private */
    public static int mWeakPoint2Y = 5;
    /* access modifiers changed from: private */
    public static int mWeakPoint3X = -100;
    /* access modifiers changed from: private */
    public static int mWeakPoint3Y = -20;
    public static MonitorCmdProxy m_cmdProxy;
    private static PlainDataDecoder m_plainDataDecoder = null;
    public static long m_sid;
    private List<MDMComponent> allComponents;
    ArrayList<String> components = null;
    private List<CheckBox> mCheckBoxes;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ComponentSelectActivity.this.removeDialog(0);
                    Elog.d(ComponentSelectActivity.TAG, "Wait MDML init done");
                    if ("1".equals(ComponentSelectActivity.mAutoRecordFlag)) {
                        ComponentSelectActivity.this.OptionsItemSelected();
                        return;
                    }
                    return;
                case 2:
                    ComponentSelectActivity.this.removeDialog(1);
                    Elog.d(ComponentSelectActivity.TAG, "Wait subscribe message done");
                    int unused = ComponentSelectActivity.k = 1;
                    Intent intent = new Intent(ComponentSelectActivity.this, MDMComponentDetailActivity.class);
                    intent.putExtra("mChecked", ComponentSelectActivity.this.components);
                    intent.putExtra("mSimType", ComponentSelectActivity.mSimType);
                    intent.putExtra("mModemType", ComponentSelectActivity.mModemType);
                    ComponentSelectActivity.this.startActivity(intent);
                    return;
                case 3:
                    ProgressDialog access$100 = ComponentSelectActivity.this.mProgressDialog1;
                    access$100.setMessage("Wait subscribe message:" + ComponentSelectActivity.access$008());
                    return;
                case 4:
                    ComponentSelectActivity.this.showProgressDialog(2);
                    return;
                default:
                    return;
            }
        }
    };
    private SharedPreferences mPref;
    private ProgressDialog mProgressDialog;
    /* access modifiers changed from: private */
    public ProgressDialog mProgressDialog1;
    /* access modifiers changed from: private */
    public int mSelectSim = 0;
    private Set<String> mSelected;
    MonitorTrapReceiver m_trapReceiver;

    private interface InputDialogOnClickListener {
        void onClick(DialogInterface dialogInterface, int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18);
    }

    static /* synthetic */ int access$008() {
        int i = k;
        k = i + 1;
        return i;
    }

    public static boolean checkMonitorAlive() {
        if (m_cmdProxy != null || !initDone) {
            return true;
        }
        Elog.d(TAG, "[checkMonitorAlive] dead!");
        return false;
    }

    public static MonitorCmdProxy getCmdProxy() {
        if (m_cmdProxy == null) {
            try {
                m_cmdProxy = new MonitorCmdProxy(EmApplication.getContext());
                Elog.d(TAG, "m_cmdProxy is null, Create MonitorCmdProxy");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return m_cmdProxy;
    }

    public static PlainDataDecoder getDecoder(Context context) {
        if (m_plainDataDecoder == null) {
            try {
                m_plainDataDecoder = PlainDataDecoder.getInstance((String) null, context);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return m_plainDataDecoder;
    }

    public static long[] toPrimitives(Long... objects) {
        long[] primitives = new long[objects.length];
        for (int i = 0; i < objects.length; i++) {
            primitives[i] = objects[i].longValue();
        }
        return primitives;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitUI();
        Elog.d(TAG, "Init ComponentSelectActivity UI done");
        showDialog(0);
        new MdmCoreThread(0).start();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Elog.d(TAG, "onResume initDone: " + initDone);
        if (!checkMonitorAlive()) {
            Elog.d(TAG, "m_cmdProxy is null, return...");
            this.mHandler.sendEmptyMessage(4);
        }
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
        new MdmCoreThread(2).start();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case 0:
                Elog.d(TAG, "Wait MDML init");
                ProgressDialog progressDialog = new ProgressDialog(this);
                this.mProgressDialog = progressDialog;
                progressDialog.setTitle("Waiting");
                this.mProgressDialog.setMessage("Wait MDML init");
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setIndeterminate(true);
                return this.mProgressDialog;
            case 1:
                Elog.d(TAG, "Before Wait subscribe message..");
                ProgressDialog progressDialog2 = new ProgressDialog(this);
                this.mProgressDialog1 = progressDialog2;
                progressDialog2.setTitle("Waiting");
                this.mProgressDialog1.setMessage("Wait subscribe message..");
                this.mProgressDialog1.setCancelable(false);
                this.mProgressDialog1.setIndeterminate(true);
                return this.mProgressDialog1;
            case 2:
                Elog.d(TAG, "MDM error, need to finish self!");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("ERROR Warning");
                builder.setCancelable(false);
                builder.setMessage("MDM occurs error, please try re-enter");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ComponentSelectActivity.this.finish();
                    }
                });
                return builder.create();
            default:
                return super.onCreateDialog(id);
        }
    }

    private boolean StartMDLService() {
        Elog.d(TAG, "before StartMDLService");
        boolean isRunning = SystemService.isRunning(SERVICE_NAME);
        if (!isRunning) {
            Elog.v(TAG, "start md_monitor prop");
            SystemProperties.set("ctl.start", SERVICE_NAME);
            try {
                SystemService.waitForState(SERVICE_NAME, SystemService.State.RUNNING, 3000);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            isRunning = SystemService.isRunning(SERVICE_NAME);
            if (!isRunning) {
                Elog.e(TAG, "start md_monitor failed time out");
            }
        }
        Elog.d(TAG, "after StartMDLService isRunning:" + isRunning);
        return isRunning;
    }

    private boolean InitMDML(Context context) {
        MonitorCmdProxy cmdProxy = getCmdProxy();
        m_cmdProxy = cmdProxy;
        long onCreateSession = cmdProxy.onCreateSession();
        m_sid = onCreateSession;
        MonitorTrapReceiver monitorTrapReceiver = new MonitorTrapReceiver(onCreateSession, "demo_receiver");
        this.m_trapReceiver = monitorTrapReceiver;
        if (m_cmdProxy != null) {
            monitorTrapReceiver.SetTrapHandler(new DemoHandler());
            m_cmdProxy.onSetTrapReceiver(m_sid, "demo_receiver");
            Elog.e(TAG, "InitMDML success!");
            return true;
        }
        Elog.e(TAG, "InitMDML occurs error!");
        return false;
    }

    class Sortbyroll implements Comparator<String> {
        Sortbyroll() {
        }

        public int compare(String a, String b) {
            return Integer.valueOf(a.split(" ")[0].replace(".", "")).intValue() - Integer.valueOf(b.split(" ")[0].replace(".", "")).intValue();
        }
    }

    private void InitUI() {
        setContentView(R.layout.component_select);
        ViewGroup list = (ViewGroup) findViewById(R.id.list);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-1, -2);
        param.setMargins(0, 5, 0, 5);
        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(-1, -2);
        param2.setMargins(30, 5, 0, 5);
        Intent intent = getIntent();
        mSimType = intent.getIntExtra("mSimType", 0);
        mModemType = intent.getIntExtra("mModemType", 1);
        this.mSelectSim = mSimType;
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE + mSimType, 0);
        this.mPref = sharedPreferences;
        Set<String> stringSet = sharedPreferences.getStringSet(KEY_SELECTED, (Set) null);
        this.mSelected = stringSet;
        if (stringSet == null) {
            this.mSelected = new HashSet();
        }
        if (mSelected_show_both_sim == null) {
            HashSet hashSet = new HashSet();
            mSelected_show_both_sim = hashSet;
            hashSet.clear();
        }
        if (FeatureSupport.isEngLoad() || FeatureSupport.isUserDebugLoad()) {
            mAutoRecordFlag = EmUtils.systemPropertyGet("persist.radio.record.auto", "0");
            mAutoRecordFlagItem = EmUtils.systemPropertyGet("persist.radio.record.auto.item", "0");
            mSelected_show_both_sim.clear();
        }
        if ("1".equals(mAutoRecordFlag)) {
            this.mSelected.clear();
            if (mAutoRecordFlagItem.equals("0")) {
                int i = 0;
                while (true) {
                    String[] strArr = mAutoRecordItem;
                    if (i >= strArr.length) {
                        break;
                    }
                    this.mSelected.add(strArr[i]);
                    i++;
                }
            } else {
                String[] autoRecordFlagItem = mAutoRecordFlagItem.split(",");
                int i2 = 0;
                while (autoRecordFlagItem != null && i2 < autoRecordFlagItem.length) {
                    if (autoRecordFlagItem[i2].equals("1")) {
                        this.mSelected.add(mAutoRecordItem[i2]);
                    }
                    i2++;
                }
            }
            mSelected_show_both_sim.addAll(this.mSelected);
            Elog.d(TAG, "mSelected = " + this.mSelected);
        }
        mSelected_show_both_sim.add("FT Network Info");
        List<MDMComponent> components2 = MDMComponent.getComponents(this);
        this.allComponents = components2;
        MDMComponent[] tmp = (MDMComponent[]) components2.toArray(new MDMComponent[components2.size()]);
        Arrays.sort(tmp);
        this.allComponents = Arrays.asList(tmp);
        List<String> groups = new ArrayList<>();
        for (MDMComponent c : this.allComponents) {
            if (!groups.contains(c.getGroup())) {
                groups.add(c.getGroup());
            }
        }
        Collections.sort(groups, new Sortbyroll());
        this.mCheckBoxes = new ArrayList();
        for (String g : groups) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(g);
            ArrayList<CheckBox> groupComponents = new ArrayList<>();
            checkBox.setTag(groupComponents);
            checkBox.setOnCheckedChangeListener(this);
            checkBox.setChecked(this.mSelected.contains(g));
            list.addView(checkBox, param);
            this.mCheckBoxes.add(checkBox);
            for (MDMComponent c2 : this.allComponents) {
                int i3 = mModemType;
                if ((!(i3 == 2 || i3 == 3) || c2.supportMultiSIM()) && c2.getGroup() != null && c2.getGroup().equals(g)) {
                    CheckBox checkBox2 = new CheckBox(this);
                    checkBox2.setText(c2.getName());
                    checkBox2.setTag(c2);
                    checkBox2.setOnCheckedChangeListener(this);
                    checkBox2.setChecked(this.mSelected.contains(c2.getName()));
                    list.addView(checkBox2, param2);
                    this.mCheckBoxes.add(checkBox2);
                    groupComponents.add(checkBox2);
                }
            }
        }
    }

    private boolean InitDecoder(Context context) {
        PlainDataDecoder decoder = getDecoder(context);
        m_plainDataDecoder = decoder;
        if (decoder == null) {
            Elog.e(TAG, "InitDecoder occurs error!");
            return false;
        }
        Elog.d(TAG, "InitDecoder success!");
        return true;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getTag() != null && (buttonView.getTag() instanceof ArrayList)) {
            Iterator<CheckBox> it = ((ArrayList) buttonView.getTag()).iterator();
            while (it.hasNext()) {
                it.next().setChecked(isChecked);
            }
        }
        if (isChecked) {
            this.mSelected.add(buttonView.getText().toString());
        } else {
            this.mSelected.remove(buttonView.getText().toString());
        }
        this.mPref.edit().putStringSet(KEY_SELECTED, this.mSelected).commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, getString(R.string.networkinfo_check));
        menu.add(0, 2, 0, "Select All");
        menu.add(0, 3, 0, "Select None");
        return true;
    }

    /* access modifiers changed from: package-private */
    public void OptionsItemSelected() {
        ArrayList<String> components2 = new ArrayList<>();
        boolean bNeedShowConfig = false;
        boolean bNeedShowSimConfig = false;
        for (int i = 0; i < this.mCheckBoxes.size(); i++) {
            CheckBox c = this.mCheckBoxes.get(i);
            if (c.isChecked() && (c.getTag() instanceof MDMComponent)) {
                components2.add(c.getText().toString());
                if (c.getText().toString().equals("CC0/CC1 RSRP and SINR")) {
                    bNeedShowConfig = true;
                }
                if (c.getText().toString().equals("FT Network Info")) {
                    bNeedShowSimConfig = true;
                }
            }
        }
        if (bNeedShowConfig) {
            showRsrpSinrConfigDlg(components2, bNeedShowSimConfig);
        } else if (bNeedShowSimConfig) {
            showSimSelectedDialog(getResources().getStringArray(R.array.sim_display_info));
        } else if (components2.size() > 0) {
            Elog.d(TAG, "check infomation id");
            showDialog(1);
            new MdmCoreThread(1).start();
        } else {
            Toast.makeText(this, getString(R.string.networkinfo_msg), 1).show();
        }
    }

    /* access modifiers changed from: private */
    public void showProgressDialog(int dialogNum) {
        if (!isFinishing()) {
            showDialog(dialogNum);
        }
    }

    /* access modifiers changed from: private */
    public void showSimSelectedDialog(String[] items) {
        AlertDialog show = new AlertDialog.Builder(this).setTitle("Sim Card for FT Network Info").setSingleChoiceItems(items, this.mSelectSim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int unused = ComponentSelectActivity.this.mSelectSim = which;
                Elog.d(ComponentSelectActivity.TAG, "Do Select Sim Action, Select Sim " + which);
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ComponentSelectActivity.this.showDialog(1);
                new MdmCoreThread(1).start();
            }
        }).show();
    }

    public boolean onOptionsItemSelected(MenuItem aMenuItem) {
        switch (aMenuItem.getItemId()) {
            case 1:
                OptionsItemSelected();
                break;
            case 2:
                for (CheckBox c : this.mCheckBoxes) {
                    c.setChecked(true);
                }
                break;
            case 3:
                for (CheckBox c2 : this.mCheckBoxes) {
                    c2.setChecked(false);
                }
                break;
        }
        return super.onOptionsItemSelected(aMenuItem);
    }

    private void showMultiInputDlg(CharSequence title, InputDialogOnClickListener listener) {
        final InputDialogOnClickListener inputDialogOnClickListener = listener;
        View inputView = getLayoutInflater().inflate(R.layout.em_multi_input_layout, (ViewGroup) null);
        EditText strongPoint1XEdit = (EditText) inputView.findViewById(R.id.strong_point1_x_edit);
        final EditText editText = strongPoint1XEdit;
        EditText strongPoint1YEdit = (EditText) inputView.findViewById(R.id.strong_point1_y_edit);
        final EditText editText2 = strongPoint1YEdit;
        EditText editText3 = strongPoint1YEdit;
        EditText strongPoint2XEdit = (EditText) inputView.findViewById(R.id.strong_point2_x_edit);
        final EditText editText4 = strongPoint2XEdit;
        EditText editText5 = strongPoint2XEdit;
        EditText strongPoint2YEdit = (EditText) inputView.findViewById(R.id.strong_point2_y_edit);
        final EditText editText6 = strongPoint2YEdit;
        EditText editText7 = strongPoint2YEdit;
        EditText strongPoint3XEdit = (EditText) inputView.findViewById(R.id.strong_point3_x_edit);
        final EditText editText8 = strongPoint3XEdit;
        EditText editText9 = strongPoint3XEdit;
        EditText strongPoint3XEdit2 = strongPoint3XEdit;
        EditText strongPoint3YEdit = (EditText) inputView.findViewById(R.id.strong_point3_y_edit);
        final EditText editText10 = strongPoint3YEdit;
        EditText strongPoint3YEdit2 = strongPoint3YEdit;
        EditText strongPoint3XEdit3 = strongPoint3XEdit2;
        EditText strongPoint3XEdit4 = strongPoint2YEdit;
        EditText mediumweakPoint1XEdit = (EditText) inputView.findViewById(R.id.mediumweak_point1_x_edit);
        final EditText editText11 = mediumweakPoint1XEdit;
        EditText mediumweakPoint1XEdit2 = mediumweakPoint1XEdit;
        EditText strongPoint2YEdit2 = strongPoint3XEdit4;
        EditText strongPoint2XEdit2 = strongPoint2XEdit;
        EditText mediumweakPoint1YEdit = (EditText) inputView.findViewById(R.id.mediumweak_point1_y_edit);
        final EditText editText12 = mediumweakPoint1YEdit;
        EditText editText13 = strongPoint1XEdit;
        EditText mediumweakPoint1YEdit2 = mediumweakPoint1YEdit;
        EditText strongPoint2XEdit3 = strongPoint2XEdit2;
        EditText strongPoint2XEdit4 = strongPoint1YEdit;
        EditText mediumweakPoint2XEdit = (EditText) inputView.findViewById(R.id.mediumweak_point2_x_edit);
        final EditText editText14 = mediumweakPoint2XEdit;
        EditText editText15 = editText3;
        EditText mediumweakPoint2XEdit2 = mediumweakPoint2XEdit;
        EditText strongPoint1YEdit2 = strongPoint2XEdit4;
        EditText strongPoint1XEdit2 = strongPoint1XEdit;
        EditText mediumweakPoint2YEdit = (EditText) inputView.findViewById(R.id.mediumweak_point2_y_edit);
        final EditText editText16 = mediumweakPoint2YEdit;
        EditText editText17 = editText5;
        EditText mediumweakPoint2YEdit2 = mediumweakPoint2YEdit;
        EditText strongPoint1XEdit3 = strongPoint1XEdit2;
        Button cancelBtn = (Button) inputView.findViewById(R.id.em_multi_input_cancel_btn);
        EditText mediumweakPoint3XEdit = (EditText) inputView.findViewById(R.id.mediumweak_point3_x_edit);
        final EditText editText18 = mediumweakPoint3XEdit;
        EditText mediumweakPoint3XEdit2 = mediumweakPoint3XEdit;
        Button cancelBtn2 = cancelBtn;
        Button okBtn = (Button) inputView.findViewById(R.id.em_multi_input_ok_btn);
        EditText mediumweakPoint3YEdit = (EditText) inputView.findViewById(R.id.mediumweak_point3_y_edit);
        final EditText editText19 = mediumweakPoint3YEdit;
        EditText mediumweakPoint3YEdit2 = mediumweakPoint3YEdit;
        Button okBtn2 = okBtn;
        EditText editText20 = editText7;
        EditText weakPoint1XEdit = (EditText) inputView.findViewById(R.id.weak_point1_x_edit);
        final EditText editText21 = weakPoint1XEdit;
        final EditText editText22 = weakPoint1XEdit;
        EditText editText23 = editText9;
        EditText weakPoint1YEdit = (EditText) inputView.findViewById(R.id.weak_point1_y_edit);
        final EditText editText24 = weakPoint1YEdit;
        final EditText editText25 = weakPoint1YEdit;
        EditText editText26 = strongPoint3YEdit;
        EditText weakPoint2XEdit = (EditText) inputView.findViewById(R.id.weak_point2_x_edit);
        final EditText editText27 = weakPoint2XEdit;
        final EditText editText28 = weakPoint2XEdit;
        EditText editText29 = mediumweakPoint1XEdit;
        EditText weakPoint2YEdit = (EditText) inputView.findViewById(R.id.weak_point2_y_edit);
        final EditText editText30 = weakPoint2YEdit;
        final EditText editText31 = weakPoint2YEdit;
        EditText editText32 = mediumweakPoint1YEdit;
        EditText weakPoint3XEdit = (EditText) inputView.findViewById(R.id.weak_point3_x_edit);
        final EditText editText33 = weakPoint3XEdit;
        final EditText editText34 = weakPoint3XEdit;
        EditText editText35 = mediumweakPoint2XEdit;
        EditText weakPoint3YEdit = (EditText) inputView.findViewById(R.id.weak_point3_y_edit);
        final EditText editText36 = weakPoint3YEdit;
        final EditText editText37 = weakPoint3YEdit;
        EditText editText38 = mediumweakPoint2YEdit;
        SharedPreferences rsrpSinrConfigSh = getSharedPreferences(COMPONENT_RSRPSINR_CONFIG_SHAREPRE, 0);
        EditText editText39 = mediumweakPoint3YEdit;
        int strongPoint1X = rsrpSinrConfigSh.getInt(getString(R.string.strong_point_1x), -80);
        int strongPoint1Y = rsrpSinrConfigSh.getInt(getString(R.string.strong_point_1y), 30);
        int strongPoint2X = rsrpSinrConfigSh.getInt(getString(R.string.strong_point_2x), -80);
        int strongPoint2Y = rsrpSinrConfigSh.getInt(getString(R.string.strong_point_2y), 20);
        int strongPoint3X = rsrpSinrConfigSh.getInt(getString(R.string.strong_point_3x), STRONG_POINT3_X_DEFAULT);
        int strongPoint3Y = rsrpSinrConfigSh.getInt(getString(R.string.strong_point_3y), 20);
        int mediumWeakPoint1X = rsrpSinrConfigSh.getInt(getString(R.string.mediumweak_point_1x), -140);
        int mediumWeakPoint1Y = rsrpSinrConfigSh.getInt(getString(R.string.mediumweak_point_1y), 10);
        int mediumWeakPoint2X = rsrpSinrConfigSh.getInt(getString(R.string.mediumweak_point_2x), -90);
        int mediumWeakPoint2Y = rsrpSinrConfigSh.getInt(getString(R.string.mediumweak_point_2y), 10);
        int mediumWeakPoint3X = rsrpSinrConfigSh.getInt(getString(R.string.mediumweak_point_3x), -90);
        int mediumWeakPoint3Y = rsrpSinrConfigSh.getInt(getString(R.string.mediumweak_point_3y), -20);
        int weakPoint1X = rsrpSinrConfigSh.getInt(getString(R.string.weak_point_1x), -140);
        int weakPoint1Y = rsrpSinrConfigSh.getInt(getString(R.string.weak_point_1y), 5);
        int weakPoint2X = rsrpSinrConfigSh.getInt(getString(R.string.weak_point_2x), -100);
        int weakPoint2Y = rsrpSinrConfigSh.getInt(getString(R.string.weak_point_2y), 5);
        int weakPoint3X = rsrpSinrConfigSh.getInt(getString(R.string.weak_point_3x), -100);
        int weakPoint3Y = rsrpSinrConfigSh.getInt(getString(R.string.weak_point_3y), -20);
        strongPoint1XEdit3.setText(String.valueOf(strongPoint1X));
        strongPoint1YEdit2.setText(String.valueOf(strongPoint1Y));
        strongPoint2XEdit3.setText(String.valueOf(strongPoint2X));
        strongPoint2YEdit2.setText(String.valueOf(strongPoint2Y));
        strongPoint3XEdit3.setText(String.valueOf(strongPoint3X));
        EditText strongPoint3YEdit3 = strongPoint3YEdit2;
        strongPoint3YEdit3.setText(String.valueOf(strongPoint3Y));
        EditText strongPoint3XEdit5 = strongPoint3XEdit3;
        mediumweakPoint1XEdit2.setText(String.valueOf(mediumWeakPoint1X));
        mediumweakPoint1YEdit2.setText(String.valueOf(mediumWeakPoint1Y));
        mediumweakPoint2XEdit2.setText(String.valueOf(mediumWeakPoint2X));
        mediumweakPoint2YEdit2.setText(String.valueOf(mediumWeakPoint2Y));
        mediumweakPoint3XEdit2.setText(String.valueOf(mediumWeakPoint3X));
        EditText mediumweakPoint3YEdit3 = mediumweakPoint3YEdit2;
        mediumweakPoint3YEdit3.setText(String.valueOf(mediumWeakPoint3Y));
        weakPoint1XEdit.setText(String.valueOf(weakPoint1X));
        weakPoint1YEdit.setText(String.valueOf(weakPoint1Y));
        weakPoint2XEdit.setText(String.valueOf(weakPoint2X));
        weakPoint2YEdit.setText(String.valueOf(weakPoint2Y));
        weakPoint3XEdit.setText(String.valueOf(weakPoint3X));
        weakPoint3YEdit.setText(String.valueOf(weakPoint3Y));
        AlertDialog dialog = new AlertDialog.Builder(this).setCancelable(false).setTitle(title).setView(inputView).create();
        final AlertDialog alertDialog = dialog;
        View view = inputView;
        final AlertDialog alertDialog2 = dialog;
        AnonymousClass5 r85 = r0;
        final EditText editText40 = mediumweakPoint3XEdit;
        EditText editText41 = weakPoint3YEdit;
        final EditText weakPoint3YEdit2 = editText35;
        EditText editText42 = weakPoint2YEdit;
        final EditText weakPoint2YEdit2 = editText29;
        EditText editText43 = weakPoint1YEdit;
        final EditText weakPoint1YEdit2 = editText23;
        EditText editText44 = mediumweakPoint3YEdit3;
        EditText editText45 = editText13;
        EditText editText46 = strongPoint2YEdit2;
        final InputDialogOnClickListener inputDialogOnClickListener2 = listener;
        EditText editText47 = strongPoint1YEdit2;
        final EditText strongPoint1YEdit3 = editText15;
        EditText editText48 = strongPoint3YEdit3;
        final EditText strongPoint3YEdit4 = editText39;
        SharedPreferences sharedPreferences = rsrpSinrConfigSh;
        final EditText editText49 = editText38;
        EditText editText50 = weakPoint3XEdit;
        final EditText weakPoint3XEdit2 = editText32;
        EditText editText51 = weakPoint2XEdit;
        final EditText weakPoint2XEdit2 = editText26;
        EditText editText52 = weakPoint1XEdit;
        final EditText weakPoint1XEdit2 = editText20;
        EditText editText53 = mediumweakPoint3XEdit2;
        EditText mediumweakPoint3XEdit3 = mediumweakPoint2YEdit2;
        EditText mediumweakPoint2YEdit3 = mediumweakPoint2XEdit2;
        EditText mediumweakPoint2XEdit3 = mediumweakPoint1XEdit2;
        EditText mediumweakPoint1XEdit3 = strongPoint2XEdit3;
        final EditText strongPoint2XEdit5 = editText45;
        EditText editText54 = strongPoint3XEdit5;
        EditText strongPoint3XEdit6 = strongPoint1XEdit3;
        final EditText strongPoint1XEdit4 = editText17;
        EditText editText55 = mediumweakPoint1YEdit2;
        EditText mediumweakPoint1YEdit3 = editText54;
        AnonymousClass5 r0 = new View.OnClickListener(this) {
            final /* synthetic */ ComponentSelectActivity this$0;

            {
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                inputDialogOnClickListener2.onClick(alertDialog2, -1, strongPoint2XEdit5.getText().toString(), strongPoint1YEdit3.getText().toString(), strongPoint1XEdit4.getText().toString(), weakPoint1XEdit2.getText().toString(), weakPoint1YEdit2.getText().toString(), weakPoint2XEdit2.getText().toString(), weakPoint2YEdit2.getText().toString(), weakPoint3XEdit2.getText().toString(), weakPoint3YEdit2.getText().toString(), editText49.getText().toString(), editText40.getText().toString(), strongPoint3YEdit4.getText().toString(), editText22.getText().toString(), editText25.getText().toString(), editText28.getText().toString(), editText31.getText().toString(), editText34.getText().toString(), editText37.getText().toString());
            }
        };
        okBtn2.setOnClickListener(r85);
        cancelBtn2.setOnClickListener(new View.OnClickListener(this) {
            final /* synthetic */ ComponentSelectActivity this$0;

            {
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                inputDialogOnClickListener.onClick(alertDialog, -2, editText.getText().toString(), editText2.getText().toString(), editText4.getText().toString(), editText6.getText().toString(), editText8.getText().toString(), editText10.getText().toString(), editText11.getText().toString(), editText12.getText().toString(), editText14.getText().toString(), editText16.getText().toString(), editText18.getText().toString(), editText19.getText().toString(), editText21.getText().toString(), editText24.getText().toString(), editText27.getText().toString(), editText30.getText().toString(), editText33.getText().toString(), editText36.getText().toString());
            }
        });
        dialog.show();
    }

    private void showRsrpSinrConfigDlg(ArrayList<String> arrayList, final boolean bNeedShowSimConfig) {
        showMultiInputDlg(getString(R.string.rsrp_sinr_config_input_tip), new InputDialogOnClickListener() {
            public void onClick(DialogInterface dialog, int which, String inputStrong1XStr, String inputStrong1YStr, String inputStrong2XStr, String inputStrong2YStr, String inputStrong3XStr, String inputStrong3YStr, String inputMediumWeak1XStr, String inputMediumWeak1YStr, String inputMediumWeak2XStr, String inputMediumWeak2YStr, String inputMediumWeak3XStr, String inputMediumWeak3YStr, String inputWeak1XStr, String inputWeak1YStr, String inputWeak2XStr, String inputWeak2YStr, String inputWeak3XStr, String inputWeak3YStr) {
                int i = which;
                if (i == -1) {
                    boolean validInput = true;
                    try {
                        int unused = ComponentSelectActivity.mStrongPoint1X = Integer.parseInt(inputStrong1XStr);
                        int unused2 = ComponentSelectActivity.mStrongPoint1Y = Integer.parseInt(inputStrong1YStr);
                        int unused3 = ComponentSelectActivity.mStrongPoint2X = Integer.parseInt(inputStrong2XStr);
                        int unused4 = ComponentSelectActivity.mStrongPoint2Y = Integer.parseInt(inputStrong2YStr);
                        int unused5 = ComponentSelectActivity.mStrongPoint3X = Integer.parseInt(inputStrong3XStr);
                        int unused6 = ComponentSelectActivity.mStrongPoint3Y = Integer.parseInt(inputStrong3YStr);
                        int unused7 = ComponentSelectActivity.mMediumWeakPoint1X = Integer.parseInt(inputMediumWeak1XStr);
                        int unused8 = ComponentSelectActivity.mMediumWeakPoint1Y = Integer.parseInt(inputMediumWeak1YStr);
                        int unused9 = ComponentSelectActivity.mMediumWeakPoint2X = Integer.parseInt(inputMediumWeak2XStr);
                        int unused10 = ComponentSelectActivity.mMediumWeakPoint2Y = Integer.parseInt(inputMediumWeak2YStr);
                        int unused11 = ComponentSelectActivity.mMediumWeakPoint3X = Integer.parseInt(inputMediumWeak3XStr);
                        int unused12 = ComponentSelectActivity.mMediumWeakPoint3Y = Integer.parseInt(inputMediumWeak3YStr);
                        int unused13 = ComponentSelectActivity.mWeakPoint1X = Integer.parseInt(inputWeak1XStr);
                        int unused14 = ComponentSelectActivity.mWeakPoint1Y = Integer.parseInt(inputWeak1YStr);
                        int unused15 = ComponentSelectActivity.mWeakPoint2X = Integer.parseInt(inputWeak2XStr);
                        int unused16 = ComponentSelectActivity.mWeakPoint2Y = Integer.parseInt(inputWeak2YStr);
                        int unused17 = ComponentSelectActivity.mWeakPoint3X = Integer.parseInt(inputWeak3XStr);
                        int unused18 = ComponentSelectActivity.mWeakPoint3Y = Integer.parseInt(inputWeak3YStr);
                    } catch (NumberFormatException e) {
                        validInput = false;
                    }
                    if (!validInput) {
                        Toast.makeText(ComponentSelectActivity.this, "Invalid RSRP/SINR", 0).show();
                    } else if (ComponentSelectActivity.mStrongPoint1X < -140 || ComponentSelectActivity.mStrongPoint1X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT || ComponentSelectActivity.mStrongPoint2X < -140 || ComponentSelectActivity.mStrongPoint2X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT || ComponentSelectActivity.mStrongPoint3X < -140 || ComponentSelectActivity.mStrongPoint3X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT || ComponentSelectActivity.mMediumWeakPoint1X < -140 || ComponentSelectActivity.mMediumWeakPoint1X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT || ComponentSelectActivity.mMediumWeakPoint2X < -140 || ComponentSelectActivity.mMediumWeakPoint2X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT || ComponentSelectActivity.mMediumWeakPoint3X < -140 || ComponentSelectActivity.mMediumWeakPoint3X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT || ComponentSelectActivity.mWeakPoint1X < -140 || ComponentSelectActivity.mWeakPoint1X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT || ComponentSelectActivity.mWeakPoint2X < -140 || ComponentSelectActivity.mWeakPoint2X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT || ComponentSelectActivity.mWeakPoint3X < -140 || ComponentSelectActivity.mWeakPoint3X > ComponentSelectActivity.STRONG_POINT3_X_DEFAULT) {
                        Toast.makeText(ComponentSelectActivity.this, "The Point X must be -140 ~ -30", 0).show();
                    } else if (ComponentSelectActivity.mStrongPoint1Y < -20 || ComponentSelectActivity.mStrongPoint1Y > 30 || ComponentSelectActivity.mStrongPoint2Y < -20 || ComponentSelectActivity.mStrongPoint2Y > 30 || ComponentSelectActivity.mStrongPoint3Y < -20 || ComponentSelectActivity.mStrongPoint3Y > 30 || ComponentSelectActivity.mMediumWeakPoint1Y < -20 || ComponentSelectActivity.mMediumWeakPoint1Y > 30 || ComponentSelectActivity.mMediumWeakPoint2Y < -20 || ComponentSelectActivity.mMediumWeakPoint2Y > 30 || ComponentSelectActivity.mMediumWeakPoint3Y < -20 || ComponentSelectActivity.mMediumWeakPoint3Y > 30 || ComponentSelectActivity.mWeakPoint1Y < -20 || ComponentSelectActivity.mWeakPoint1Y > 30 || ComponentSelectActivity.mWeakPoint2Y < -20 || ComponentSelectActivity.mWeakPoint2Y > 30 || ComponentSelectActivity.mWeakPoint3Y < -20 || ComponentSelectActivity.mWeakPoint3Y > 30) {
                        Toast.makeText(ComponentSelectActivity.this, "The Point Y must be -20 ~ 30", 0).show();
                    } else {
                        dialog.dismiss();
                        ComponentSelectActivity.this.writeRsrpSinrSharedPreference(ComponentSelectActivity.mStrongPoint1X, ComponentSelectActivity.mStrongPoint1Y, ComponentSelectActivity.mStrongPoint2X, ComponentSelectActivity.mStrongPoint2Y, ComponentSelectActivity.mStrongPoint3X, ComponentSelectActivity.mStrongPoint3Y, ComponentSelectActivity.mMediumWeakPoint1X, ComponentSelectActivity.mMediumWeakPoint1Y, ComponentSelectActivity.mMediumWeakPoint2X, ComponentSelectActivity.mMediumWeakPoint2Y, ComponentSelectActivity.mMediumWeakPoint3X, ComponentSelectActivity.mMediumWeakPoint3Y, ComponentSelectActivity.mWeakPoint1X, ComponentSelectActivity.mWeakPoint1Y, ComponentSelectActivity.mWeakPoint2X, ComponentSelectActivity.mWeakPoint2Y, ComponentSelectActivity.mWeakPoint3X, ComponentSelectActivity.mWeakPoint3Y);
                        if (bNeedShowSimConfig) {
                            ComponentSelectActivity componentSelectActivity = ComponentSelectActivity.this;
                            componentSelectActivity.showSimSelectedDialog(componentSelectActivity.getResources().getStringArray(R.array.sim_display_info));
                            return;
                        }
                        ComponentSelectActivity.this.showDialog(1);
                        new MdmCoreThread(1).start();
                    }
                } else if (i == -2) {
                    int unused19 = ComponentSelectActivity.mStrongPoint1X = -80;
                    int unused20 = ComponentSelectActivity.mStrongPoint1Y = 30;
                    int unused21 = ComponentSelectActivity.mStrongPoint2X = -80;
                    int unused22 = ComponentSelectActivity.mStrongPoint2Y = 20;
                    int unused23 = ComponentSelectActivity.mStrongPoint3X = ComponentSelectActivity.STRONG_POINT3_X_DEFAULT;
                    int unused24 = ComponentSelectActivity.mStrongPoint3Y = 20;
                    int unused25 = ComponentSelectActivity.mMediumWeakPoint1X = -140;
                    int unused26 = ComponentSelectActivity.mMediumWeakPoint1Y = 10;
                    int unused27 = ComponentSelectActivity.mMediumWeakPoint2X = -90;
                    int unused28 = ComponentSelectActivity.mMediumWeakPoint2Y = 10;
                    int unused29 = ComponentSelectActivity.mMediumWeakPoint3X = -90;
                    int unused30 = ComponentSelectActivity.mMediumWeakPoint3Y = -20;
                    int unused31 = ComponentSelectActivity.mWeakPoint1X = -140;
                    int unused32 = ComponentSelectActivity.mWeakPoint1Y = 5;
                    int unused33 = ComponentSelectActivity.mWeakPoint2X = -100;
                    int unused34 = ComponentSelectActivity.mWeakPoint2Y = 5;
                    int unused35 = ComponentSelectActivity.mWeakPoint3X = -100;
                    int unused36 = ComponentSelectActivity.mWeakPoint3Y = -20;
                    dialog.dismiss();
                    ComponentSelectActivity.this.writeRsrpSinrSharedPreference(ComponentSelectActivity.mStrongPoint1X, ComponentSelectActivity.mStrongPoint1Y, ComponentSelectActivity.mStrongPoint2X, ComponentSelectActivity.mStrongPoint2Y, ComponentSelectActivity.mStrongPoint3X, ComponentSelectActivity.mStrongPoint3Y, ComponentSelectActivity.mMediumWeakPoint1X, ComponentSelectActivity.mMediumWeakPoint1Y, ComponentSelectActivity.mMediumWeakPoint2X, ComponentSelectActivity.mMediumWeakPoint2Y, ComponentSelectActivity.mMediumWeakPoint3X, ComponentSelectActivity.mMediumWeakPoint3Y, ComponentSelectActivity.mWeakPoint1X, ComponentSelectActivity.mWeakPoint1Y, ComponentSelectActivity.mWeakPoint2X, ComponentSelectActivity.mWeakPoint2Y, ComponentSelectActivity.mWeakPoint3X, ComponentSelectActivity.mWeakPoint3Y);
                    if (bNeedShowSimConfig) {
                        ComponentSelectActivity componentSelectActivity2 = ComponentSelectActivity.this;
                        componentSelectActivity2.showSimSelectedDialog(componentSelectActivity2.getResources().getStringArray(R.array.sim_display_info));
                        return;
                    }
                    ComponentSelectActivity.this.showDialog(1);
                    new MdmCoreThread(1).start();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void writeRsrpSinrSharedPreference(int strongPoint1X, int strongPoint1Y, int strongPoint2X, int strongPoint2Y, int strongPoint3X, int strongPoint3Y, int mediumWeakPoint1X, int mediumWeakPoint1Y, int mediumWeakPoint2X, int mediumWeakPoint2Y, int mediumWeakPoint3X, int mediumWeakPoint3Y, int weakPoint1X, int weakPoint1Y, int weakPoint2X, int weakPoint2Y, int weakPoint3X, int weakPoint3Y) {
        SharedPreferences rsrpSinrConfigSh = getSharedPreferences(COMPONENT_RSRPSINR_CONFIG_SHAREPRE, 0);
        SharedPreferences.Editor editor = rsrpSinrConfigSh.edit();
        editor.putInt(getString(R.string.strong_point_1x), strongPoint1X);
        editor.putInt(getString(R.string.strong_point_1y), strongPoint1Y);
        editor.putInt(getString(R.string.strong_point_2x), strongPoint2X);
        editor.putInt(getString(R.string.strong_point_2y), strongPoint2Y);
        editor.putInt(getString(R.string.strong_point_3x), strongPoint3X);
        editor.putInt(getString(R.string.strong_point_3y), strongPoint3Y);
        editor.putInt(getString(R.string.mediumweak_point_1x), mediumWeakPoint1X);
        editor.putInt(getString(R.string.mediumweak_point_1y), mediumWeakPoint1Y);
        editor.putInt(getString(R.string.mediumweak_point_2x), mediumWeakPoint2X);
        editor.putInt(getString(R.string.mediumweak_point_2y), mediumWeakPoint2Y);
        editor.putInt(getString(R.string.mediumweak_point_3x), mediumWeakPoint3X);
        editor.putInt(getString(R.string.mediumweak_point_3y), mediumWeakPoint3Y);
        SharedPreferences sharedPreferences = rsrpSinrConfigSh;
        editor.putInt(getString(R.string.weak_point_1x), weakPoint1X);
        editor.putInt(getString(R.string.weak_point_1y), weakPoint1Y);
        editor.putInt(getString(R.string.weak_point_2x), weakPoint2X);
        editor.putInt(getString(R.string.weak_point_2y), weakPoint2Y);
        editor.putInt(getString(R.string.weak_point_3x), weakPoint3X);
        editor.putInt(getString(R.string.weak_point_3y), weakPoint3Y);
        editor.commit();
    }

    private class DemoHandler implements TrapHandlerInterface {
        private DemoHandler() {
        }

        public void ProcessTrap(long timestamp, TRAP_TYPE type, int len, byte[] data, int offset) {
            switch (AnonymousClass8.$SwitchMap$com$mediatek$mdml$TRAP_TYPE[type.ordinal()]) {
                case 1:
                    Msg EmMsg = ComponentSelectActivity.getDecoder(ComponentSelectActivity.this).msgInfo_getMsg(data, offset);
                    if (EmMsg == null) {
                        Elog.d(ComponentSelectActivity.TAG, "msg is null in ProcessTrap : " + Arrays.toString(data));
                        return;
                    }
                    String msgName = EmMsg.getMsgName().toUpperCase();
                    int msgID = ComponentSelectActivity.getDecoder(ComponentSelectActivity.this).msgInfo_getMsgID(msgName).intValue();
                    Elog.d(ComponentSelectActivity.TAG, "msgIDStr: " + msgID + ", msgName: " + msgName);
                    if (MDMComponentDetailActivity.taskDriver == null) {
                        Elog.d(ComponentSelectActivity.TAG, "taskDriver is null, return : " + type + "[" + msgID + "]");
                        return;
                    }
                    MDMComponentDetailActivity.taskDriver.appendEmTask(EmMsg, msgID);
                    return;
                case 2:
                case 3:
                    ByteBuffer icdPacket = ByteBuffer.wrap(Arrays.copyOfRange(data, offset, len + offset));
                    MDMContentICD.MDMHeaderICD IcdHeader = MDMComponentDetailActivity.DecodeICDPacketHeader(icdPacket);
                    String msgIDStr = Long.toHexString((long) IcdHeader.msg_id).toUpperCase();
                    if (MDMComponentDetailActivity.taskDriver == null) {
                        Elog.d(ComponentSelectActivity.TAG, "taskDriver is null, return : " + type + "[" + msgIDStr + "]");
                        return;
                    }
                    MDMComponentDetailActivity.taskDriver.appendIcdTask(IcdHeader, icdPacket);
                    return;
                case 4:
                    Elog.d(ComponentSelectActivity.TAG, "[Discard ... ]");
                    return;
                default:
                    Elog.d(ComponentSelectActivity.TAG, "[Unknown Type, Error ...] type: " + type);
                    return;
            }
        }
    }

    /* renamed from: com.mediatek.engineermode.mdmcomponent.ComponentSelectActivity$8  reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$mdml$TRAP_TYPE;

        static {
            int[] iArr = new int[TRAP_TYPE.values().length];
            $SwitchMap$com$mediatek$mdml$TRAP_TYPE = iArr;
            try {
                iArr[TRAP_TYPE.TRAP_TYPE_EM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_ICD_RECORD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_ICD_EVENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$mediatek$mdml$TRAP_TYPE[TRAP_TYPE.TRAP_TYPE_DISCARDINFO.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean loadingMdmService() {
        Elog.d(TAG, "before loadingMdmService");
        if (!StartMDLService() || !InitMDML(this) || !InitDecoder(this)) {
            this.mHandler.sendEmptyMessage(4);
            Elog.e(TAG, "loadingMdmService failed");
            return false;
        }
        Elog.d(TAG, "after loadingMdmService");
        return true;
    }

    public class MdmCoreThread extends Thread {
        private int action;

        public MdmCoreThread(int action2) {
            this.action = action2;
        }

        public void run() {
            switch (this.action) {
                case 0:
                    if (ComponentSelectActivity.this.loadingMdmService()) {
                        ComponentSelectActivity.this.mHandler.sendEmptyMessage(1);
                        ComponentSelectActivity.initDone = true;
                        return;
                    }
                    return;
                case 1:
                    if (ComponentSelectActivity.this.subscribeMdmMsg()) {
                        ComponentSelectActivity.this.mHandler.sendEmptyMessage(2);
                        return;
                    }
                    return;
                case 2:
                    boolean unused = ComponentSelectActivity.this.disableTrap();
                    return;
                default:
                    return;
            }
        }
    }

    private boolean isRecordType(long value) {
        if (value >= PlaybackStateCompat.ACTION_PLAY_FROM_URI && value < 8320) {
            return true;
        }
        if (value >= 28672 && value < 29312) {
            return true;
        }
        if (value < 36864 || value >= 37504) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean subscribeMdmMsg() {
        int i;
        int k2;
        long msgId;
        int i2;
        long msgId2;
        int length;
        boolean z = false;
        if (!checkMonitorAlive()) {
            this.mHandler.sendEmptyMessage(4);
            return false;
        }
        int k3 = 1;
        this.components = new ArrayList<>();
        mCheckedEmTypes.clear();
        mCheckedIcdEventTypes.clear();
        mCheckedIcdRecordTypes.clear();
        long msgId3 = 0;
        int i3 = 0;
        while (i3 < this.mCheckBoxes.size()) {
            CheckBox c = this.mCheckBoxes.get(i3);
            if (!c.isChecked() || !(c.getTag() instanceof MDMComponent)) {
                k2 = k3;
                msgId = msgId3;
                i2 = i3;
            } else {
                this.mHandler.sendEmptyMessage(3);
                this.components.add(c.getText().toString());
                for (MDMComponent com2 : this.allComponents) {
                    if (c.getText().toString().equals(com2.getName())) {
                        String[] emComponentName = com2.getEmComponentName();
                        if (emComponentName == null) {
                            Elog.d(TAG, com2.getName() + " getEmComponentName is null, return");
                            return z;
                        }
                        int recodeIndex = com2.getTypeIndex(MDMContentICD.MSG_TYPE_ICD_RECORD);
                        int eventIndex = com2.getTypeIndex(MDMContentICD.MSG_TYPE_ICD_EVENT);
                        int emIndex = (recodeIndex == 0 || eventIndex == 0) ? -1 : z;
                        Elog.d(TAG, "Index: " + recodeIndex + ", " + eventIndex + ", " + emIndex);
                        k2 = k3;
                        if (recodeIndex >= 0) {
                            int length2 = eventIndex >= 0 ? eventIndex : emComponentName.length;
                            Elog.i(TAG, "the type is icd record," + com2.getName());
                            int j = recodeIndex + 1;
                            while (j < length2) {
                                long msgId4 = msgId3;
                                int i4 = i3;
                                long msgIdIcd = Long.parseLong(emComponentName[j], 16);
                                if (!mCheckedIcdRecordTypes.contains(Long.valueOf(msgIdIcd))) {
                                    mCheckedIcdRecordTypes.add(Long.valueOf(msgIdIcd));
                                    Elog.d(TAG, "onSubscribeTrap ID = " + emComponentName[j] + ", " + isRecordType(msgIdIcd));
                                }
                                j++;
                                msgId3 = msgId4;
                                i3 = i4;
                            }
                            msgId2 = msgId3;
                            i = i3;
                        } else {
                            msgId2 = msgId3;
                            i = i3;
                        }
                        if (eventIndex >= 0) {
                            Elog.i(TAG, "the type is icd event," + com2.getName());
                            for (int j2 = eventIndex + 1; j2 < emComponentName.length; j2++) {
                                long msgIdIcd2 = Long.parseLong(emComponentName[j2], 16);
                                if (!mCheckedIcdEventTypes.contains(Long.valueOf(msgIdIcd2))) {
                                    mCheckedIcdEventTypes.add(Long.valueOf(msgIdIcd2));
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("onSubscribeTrap ID: ");
                                    sb.append(emComponentName[j2]);
                                    sb.append(", ");
                                    sb.append(!isRecordType(msgIdIcd2));
                                    Elog.d(TAG, sb.toString());
                                }
                            }
                        }
                        if (emIndex >= 0) {
                            if (recodeIndex > 0) {
                                length = recodeIndex;
                            } else {
                                length = eventIndex > 0 ? eventIndex : emComponentName.length;
                            }
                            msgId3 = msgId2;
                            for (int j3 = 0; j3 < length; j3++) {
                                Integer msgIdInt = getDecoder(this).msgInfo_getMsgID(emComponentName[j3]);
                                if (msgIdInt != null) {
                                    msgId3 = msgIdInt.longValue();
                                    if (!mCheckedEmTypes.contains(Long.valueOf(msgId3))) {
                                        mCheckedEmTypes.add(Long.valueOf(msgId3));
                                        Elog.d(TAG, "onSubscribeTrap msg Name = " + emComponentName[j3]);
                                    }
                                    if (!mCheckedEmTypes.contains(Long.valueOf(msgId3))) {
                                        mCheckedEmTypes.add(Long.valueOf(msgId3));
                                        Elog.d(TAG, "onSubscribeTrap msgName = " + emComponentName[j3]);
                                    }
                                } else {
                                    Elog.e(TAG, "The msgid is not support, msgName = " + emComponentName[j3]);
                                }
                            }
                            i3 = i + 1;
                            k3 = k2;
                            z = false;
                        }
                    } else {
                        k2 = k3;
                        msgId2 = msgId3;
                        i = i3;
                    }
                    k3 = k2;
                    msgId3 = msgId2;
                    i3 = i;
                    z = false;
                }
                k2 = k3;
                msgId = msgId3;
                i2 = i3;
            }
            msgId3 = msgId;
            i3 = i + 1;
            k3 = k2;
            z = false;
        }
        long j4 = msgId3;
        int i5 = i3;
        int size = mCheckedEmTypes.size();
        if (size > 0) {
            Long[] array = (Long[]) mCheckedEmTypes.toArray(new Long[size]);
            Elog.d(TAG, "onSubscribeTrap em msg id = " + Arrays.toString(array));
            getCmdProxy().onSubscribeMultiTrap(m_sid, TRAP_TYPE.TRAP_TYPE_EM, toPrimitives(array));
        }
        int size2 = mCheckedIcdRecordTypes.size();
        if (size2 > 0) {
            Long[] array2 = (Long[]) mCheckedIcdRecordTypes.toArray(new Long[size2]);
            Elog.d(TAG, "onSubscribeTrap icd record msg id = " + Arrays.toString(array2));
            getCmdProxy().onSubscribeMultiTrap(m_sid, TRAP_TYPE.TRAP_TYPE_ICD_RECORD, toPrimitives(array2));
        }
        int size3 = mCheckedIcdEventTypes.size();
        if (size3 > 0) {
            Long[] array3 = (Long[]) mCheckedIcdEventTypes.toArray(new Long[size3]);
            Elog.d(TAG, "onSubscribeTrap icd event msg id = " + Arrays.toString(array3));
            getCmdProxy().onSubscribeMultiTrap(m_sid, TRAP_TYPE.TRAP_TYPE_ICD_EVENT, toPrimitives(array3));
        }
        Elog.d(TAG, "onSubscribeTrap done");
        return true;
    }

    /* access modifiers changed from: private */
    public boolean disableTrap() {
        boolean ret = true;
        if (!checkMonitorAlive()) {
            initDone = false;
            Elog.e(TAG, "[deleteTask] m_cmdProxy is null, md_monitor service is stopped!");
            ret = false;
        } else {
            if (MONITOR_CMD_RESP.MONITOR_CMD_RESP_SUCCESS != getCmdProxy().onDisableTrap(m_sid)) {
                ret = false;
                Elog.d(TAG, "Disable Trap fail");
            }
            if (MONITOR_CMD_RESP.MONITOR_CMD_RESP_SUCCESS != getCmdProxy().onCloseSession(m_sid)) {
                ret = false;
                Elog.d(TAG, "Close Session fail");
            }
        }
        SystemProperties.set("ctl.stop", SERVICE_NAME);
        m_cmdProxy = null;
        initDone = false;
        Elog.d(TAG, "Disable mdm monitor");
        return ret;
    }
}
