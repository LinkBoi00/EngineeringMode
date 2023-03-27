package com.mediatek.engineermode.wifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmApplication;
import com.mediatek.engineermode.R;

public class MtkCTIATestDialog extends AlertDialog implements DialogInterface.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String CTIA_PREF = "CTIA_PREF";
    private static final int NEGATIVE_BUTTON = -2;
    private static final int NOTIF_ID_ENABLE_CTIA = 10;
    private static final int POSITIVE_BUTTON = -1;
    private static final String PREF_CTIA_DUMP_BEACON = "CTIA_DUMP_1";
    private static final String PREF_CTIA_DUMP_COUNTER = "CTIA_DUMP_2";
    private static final String PREF_CTIA_DUMP_INTERVAL = "CTIA_DUMP_3";
    private static final String PREF_CTIA_ENABLE = "CTIA_ENABLE";
    private static final String PREF_CTIA_RATE = "CTIA_RATE";
    private static final String TAG = "WifiCTIA";
    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            String str;
            if (v == MtkCTIATestDialog.this.mSetBtn) {
                try {
                    int id = (int) Long.parseLong(MtkCTIATestDialog.this.mIdEditText.getText().toString(), 16);
                    int val = (int) Long.parseLong(MtkCTIATestDialog.this.mValEditText.getText().toString(), 16);
                    int ret = EMWifi.doCTIATestSet((long) id, (long) val);
                    if (ret != 0) {
                        MtkCTIATestDialog.this.mValEditText.setText("ERROR");
                    }
                    Elog.i(MtkCTIATestDialog.TAG, "Set ret: " + ret + " ID: " + id + " VAL: " + val);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    MtkCTIATestDialog.this.mValEditText.setText("0");
                }
            } else if (v == MtkCTIATestDialog.this.mGetBtn) {
                try {
                    int id2 = (int) Long.parseLong(MtkCTIATestDialog.this.mIdEditText.getText().toString(), 16);
                    long[] value = {0};
                    int ret2 = EMWifi.doCTIATestGet((long) id2, value);
                    Elog.i(MtkCTIATestDialog.TAG, "Get ret: " + ret2 + " ID: " + id2 + " VAL: " + value[0]);
                    EditText access$200 = MtkCTIATestDialog.this.mValEditText;
                    if (ret2 == 0) {
                        str = Long.toString(value[0], 16);
                    } else {
                        str = "UNKNOWN";
                    }
                    access$200.setText(str);
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                    MtkCTIATestDialog.this.mValEditText.setText("0");
                }
            }
        }
    };
    private CheckBox mCheckbox = null;
    private Context mContext = null;
    private CheckBox mDumpBeaconCheckbox = null;
    private CheckBox mDumpCounterCheckbox = null;
    /* access modifiers changed from: private */
    public Button mGetBtn = null;
    /* access modifiers changed from: private */
    public EditText mIdEditText = null;
    private EditText mIntervalEditText = null;
    private String[] mRate = {"Automatic", "1M", "2M", "5_5M", "11M", "6M", "9M", "12M", "18M", "24M", "36M", "48M", "54M", "20MCS0800", "20MCS01800", "20MCS2800", "20MCS3800", "20MCS4800", "20MCS5800", "20MCS6800", "20MCS7800", "20MCS0400", "20MCS1400", "20MCS2400", "20MCS3400", "20MCS4400", "20MCS5400", "20MCS6400", "20MCS7400", "40MCS0800", "40MCS1800", "40MCS2800", "40MCS3800", "40MCS4800", "40MCS5800", "40MCS6800", "40MCS7800", "40MCS32800", "40MCS0400", "40MCS1400", "40MCS2400", "40MCS3400", "40MCS4400", "40MCS5400", "40MCS6400", "40MCS7400", "40MCS32400"};
    private Spinner mRateSpinner = null;
    private int mRateVal = 0;
    /* access modifiers changed from: private */
    public Button mSetBtn = null;
    /* access modifiers changed from: private */
    public EditText mValEditText = null;
    private View mView = null;
    private WifiManager mWm = null;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected MtkCTIATestDialog(Context context) {
        super(context);
        Context context2 = context;
        this.mContext = context2;
        this.mWm = (WifiManager) context2.getSystemService("wifi");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        initLayout();
        restorePrefs();
        super.onCreate(savedInstanceState);
    }

    private void setLayout(int layoutResId) {
        View inflate = getLayoutInflater().inflate(layoutResId, (ViewGroup) null);
        this.mView = inflate;
        setView(inflate);
        onReferenceViews(this.mView);
    }

    private void initLayout() {
        setLayout(R.layout.ctiasetting);
        setButtons(R.string.em_ok, R.string.wifi_cancel, 0);
    }

    private void savePrefs() {
        int tmpInterval;
        SharedPreferences.Editor editor = this.mContext.getSharedPreferences(CTIA_PREF, 0).edit();
        editor.putBoolean(PREF_CTIA_ENABLE, this.mCheckbox.isChecked());
        editor.putInt(PREF_CTIA_RATE, getRateFromSpinner());
        editor.putBoolean(PREF_CTIA_DUMP_BEACON, this.mDumpBeaconCheckbox.isChecked());
        editor.putBoolean(PREF_CTIA_DUMP_COUNTER, this.mDumpCounterCheckbox.isChecked());
        try {
            tmpInterval = Integer.parseInt(this.mIntervalEditText.getText().toString());
        } catch (NumberFormatException e) {
            tmpInterval = 1;
        }
        if (tmpInterval > 255) {
            tmpInterval = 255;
        } else if (tmpInterval < 1) {
            tmpInterval = 1;
        }
        editor.putInt(PREF_CTIA_DUMP_INTERVAL, tmpInterval);
        editor.commit();
    }

    private void restorePrefs() {
        SharedPreferences settings = this.mContext.getSharedPreferences(CTIA_PREF, 0);
        boolean prefEnableCtia = settings.getBoolean(PREF_CTIA_ENABLE, false);
        int prefRate = settings.getInt(PREF_CTIA_RATE, 0);
        boolean prefDumpBeacon = settings.getBoolean(PREF_CTIA_DUMP_BEACON, false);
        boolean prefDumpCounter = settings.getBoolean(PREF_CTIA_DUMP_COUNTER, false);
        int interval = settings.getInt(PREF_CTIA_DUMP_INTERVAL, 1);
        this.mCheckbox.setChecked(prefEnableCtia);
        this.mRateSpinner.setSelection(prefRate);
        this.mDumpBeaconCheckbox.setChecked(prefDumpBeacon);
        this.mDumpCounterCheckbox.setChecked(prefDumpCounter);
        EditText editText = this.mIntervalEditText;
        editText.setText(interval + "");
    }

    private void onReferenceViews(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.rate_spinner);
        this.mRateSpinner = spinner;
        setSpinnerAdapter(spinner, this.mRate);
        this.mGetBtn = (Button) view.findViewById(R.id.get_btn);
        Button button = (Button) view.findViewById(R.id.set_btn);
        this.mSetBtn = button;
        button.setOnClickListener(this.mBtnClickListener);
        this.mGetBtn.setOnClickListener(this.mBtnClickListener);
        this.mRateSpinner.setOnItemSelectedListener(this);
        this.mCheckbox = (CheckBox) view.findViewById(R.id.enable_checkbox);
        this.mIdEditText = (EditText) view.findViewById(R.id.idedittext);
        this.mValEditText = (EditText) view.findViewById(R.id.valedittext);
        this.mIntervalEditText = (EditText) view.findViewById(R.id.interval_edittext);
        this.mDumpBeaconCheckbox = (CheckBox) view.findViewById(R.id.enable_dump_checkbox);
        this.mDumpCounterCheckbox = (CheckBox) view.findViewById(R.id.enable_dump_counter_checkbox);
    }

    private void setSpinnerAdapter(Spinner spinner, String[] items) {
        if (items != null) {
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getContext(), 17367048, items);
            adapter.setDropDownViewResource(17367049);
            spinner.setAdapter(adapter);
        }
    }

    private void setButtons(int positiveResId, int negativeResId, int neutralResId) {
        Context context = getContext();
        if (positiveResId > 0) {
            setButton(context.getString(positiveResId), this);
        }
        if (negativeResId > 0) {
            setButton2(context.getString(negativeResId), this);
        }
        if (neutralResId > 0) {
            setButton3(context.getString(neutralResId), this);
        }
    }

    private void handleRateChange(int rate) {
        this.mRateVal = rate;
    }

    private int getRateFromSpinner() {
        return this.mRateSpinner.getSelectedItemPosition();
    }

    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        if (parent == this.mRateSpinner) {
            handleRateChange(getRateFromSpinner());
        }
    }

    private boolean switchCTIATestMode(boolean on) {
        if (on) {
            if (!EMWifi.doCtiaTestOn()) {
                Elog.e(TAG, "doCTIATestOn failed");
                return false;
            }
            if (((WifiManager) getContext().getSystemService("wifi")).getWifiState() == 3) {
                notifyCtiaEnabled(getContext());
            }
            Elog.i(TAG, "enableService true");
            WifiWatchService.enableService(this.mContext, true);
        } else if (!EMWifi.doCtiaTestOff()) {
            Elog.e(TAG, "doCTIATestOff failed");
            return false;
        } else {
            dismissCtiaEnabledNotify(getContext());
            Elog.i(TAG, "enableService false");
            WifiWatchService.enableService(this.mContext, false);
        }
        return true;
    }

    private boolean setParams() {
        int tmpInterval;
        if (!EMWifi.doCtiaTestRate(this.mRateVal)) {
            Elog.e(TAG, "doCtiaTestRate failed");
            return false;
        } else if (!setCTIAParamItem((long) ((int) Long.parseLong("10020000", 16)), (long) this.mDumpBeaconCheckbox.isChecked())) {
            return false;
        } else {
            try {
                tmpInterval = Integer.parseInt(this.mIntervalEditText.getText().toString());
            } catch (NumberFormatException e) {
                tmpInterval = 1;
                this.mIntervalEditText.setText("1");
            }
            if (tmpInterval > 255) {
                this.mIntervalEditText.setText("255");
                tmpInterval = 255;
            } else if (tmpInterval < 1) {
                this.mIntervalEditText.setText("1");
                tmpInterval = 1;
            }
            int id = (int) Long.parseLong("10020001", 16);
            StringBuilder sb = new StringBuilder();
            sb.append("0000");
            sb.append(Integer.toHexString(tmpInterval));
            sb.append(this.mDumpCounterCheckbox.isChecked() ? "01" : "00");
            if (!setCTIAParamItem((long) id, (long) ((int) Long.parseLong(sb.toString(), 16)))) {
                return false;
            }
            return true;
        }
    }

    public void onClick(DialogInterface arg0, int arg1) {
        int i;
        if (arg1 == -1) {
            if (!switchCTIATestMode(this.mCheckbox.isChecked())) {
                Context context = this.mContext;
                if (this.mCheckbox.isChecked()) {
                    i = R.string.wifi_ctia_enable_failed;
                } else {
                    i = R.string.wifi_ctia_disable_failed;
                }
                Toast.makeText(context, i, 0).show();
            } else if (!setParams()) {
                Toast.makeText(this.mContext, R.string.wifi_ctia_set_params_failed, 0).show();
            } else {
                savePrefs();
                dismiss();
            }
        } else if (arg1 == -2) {
            Elog.v(TAG, "cancel");
            dismiss();
        }
    }

    private static boolean setCTIAParamItem(long id, long val) {
        int result = EMWifi.doCTIATestSet(id, val);
        Elog.i(TAG, "doCTIATestSet: id: " + id + " val: " + val + " result: " + result);
        return result == 0;
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    static void initWifiCtiaOnEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        SharedPreferences preferences = context.getSharedPreferences(CTIA_PREF, 0);
        boolean enableCtia = preferences.getBoolean(PREF_CTIA_ENABLE, false);
        int rate = preferences.getInt(PREF_CTIA_RATE, 0);
        int enableDumpBeacon = preferences.getBoolean(PREF_CTIA_DUMP_BEACON, false);
        boolean enableDumpCounter = preferences.getBoolean(PREF_CTIA_DUMP_COUNTER, false);
        int interval = preferences.getInt(PREF_CTIA_DUMP_INTERVAL, 1);
        boolean result = enableCtia ? EMWifi.doCtiaTestOn() : EMWifi.doCtiaTestOff();
        Elog.i(TAG, "switch ctia mode " + enableCtia + " with result " + result);
        if (!EMWifi.doCtiaTestRate(rate)) {
            Elog.e(TAG, "doCTIATestRate failed");
        }
        WifiManager wifiManager2 = wifiManager;
        setCTIAParamItem((long) ((int) Long.parseLong("10020000", 16)), (long) enableDumpBeacon);
        int id = (int) Long.parseLong("10020001", 16);
        StringBuilder sb = new StringBuilder();
        sb.append("0000");
        sb.append(Integer.toHexString(interval));
        sb.append(enableDumpCounter ? "01" : "00");
        setCTIAParamItem((long) id, (long) ((int) Long.parseLong(sb.toString(), 16)));
    }

    public static boolean isWifiCtiaEnabled(Context context) {
        boolean enabled = context.getSharedPreferences(CTIA_PREF, 0).getBoolean(PREF_CTIA_ENABLE, false);
        Elog.i(TAG, "isWifiCtiaEnabled:" + enabled);
        return enabled;
    }

    static void notifyCtiaEnabled(Context context) {
        emitNotif(context, 10, "WIFI CTIA is Enabled", "click here to switch CTIA mode", WifiTestSetting.class);
    }

    static void dismissCtiaEnabledNotify(Context context) {
        ((NotificationManager) context.getSystemService("notification")).cancel(10);
    }

    static void emitNotif(Context context, int id, String title, String content, Class<? extends Activity> targetClass) {
        Notification notif = new Notification.Builder(context, EmApplication.getSilentNotificationChannelID()).setSmallIcon(17301659).setContentTitle(title).build();
        notif.flags |= 32;
        Intent intent = new Intent(context, targetClass);
        intent.setFlags(335544320);
        notif.setLatestEventInfo(context, title, content, PendingIntent.getActivity(context, 0, intent, 67108864));
        ((NotificationManager) context.getSystemService("notification")).notify(id, notif);
    }
}
