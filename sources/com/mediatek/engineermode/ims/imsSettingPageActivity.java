package com.mediatek.engineermode.ims;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class imsSettingPageActivity extends Activity implements View.OnClickListener {
    private static final int DIALOG_MANUAL = 1;
    private static final int MSG_QUERY = 0;
    private static final int MSG_SET = 1;
    private static final String PROP_IMS_PCT_CONFIG = "vendor.ril.volte.mal.pctid";
    private static final String TAG = "Ims/imsSettingPage";
    private static final int TYPE_MULTI = 3;
    private static final int TYPE_NUMBER = 0;
    private static final int TYPE_SINGLE = 2;
    private static final int TYPE_SPINNER = 4;
    private static final int TYPE_TEXT = 1;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                AsyncResult ar = (AsyncResult) msg.obj;
                if (ar.exception == null) {
                    String[] data = (String[]) ar.result;
                    if (data != null && data.length > 0 && data[0] != null) {
                        ((SettingView) ar.userObj).setValue(imsSettingPageActivity.this.parseCommandResponse(data[0]));
                        if (((SettingView) ar.userObj).setting.label.equals("mncmcc check") && imsSettingPageActivity.this.parseCommandResponse(data[0]).equals("1")) {
                            imsSettingPageActivity imssettingpageactivity = imsSettingPageActivity.this;
                            imssettingpageactivity.sendCommand("mncmcc_whitelist", (View) imssettingpageactivity.getSettingView("white list"));
                        }
                        if (((SettingView) ar.userObj).setting.label.equals("white list")) {
                            TextView textView = ((SettingView) ar.userObj).label;
                            textView.setText("white list: \n" + imsSettingPageActivity.this.parseCommandResponse(data[0]));
                            return;
                        }
                        return;
                    }
                    return;
                }
                imsSettingPageActivity imssettingpageactivity2 = imsSettingPageActivity.this;
                Toast.makeText(imssettingpageactivity2, "Query failed for " + ((SettingView) ar.userObj).setting.label, 0).show();
            } else if (msg.what != 1) {
            } else {
                if (((AsyncResult) msg.obj).exception == null) {
                    imsSettingPageActivity.this.showToast("Set successful.");
                    Elog.d(imsSettingPageActivity.TAG, "Set successful.");
                    return;
                }
                imsSettingPageActivity.this.showToast("Set failed.");
                Elog.d(imsSettingPageActivity.TAG, "Set failed.");
            }
        }
    };
    private ViewGroup mList;
    private ArrayList<Setting> mSetting = new ArrayList<>();
    /* access modifiers changed from: private */
    public String mSettingRule = "Setting Rule:<digit of list num><list num><mnc_len><MNC><mcc_len><MCC>...";
    private ArrayList<SettingView> mSettingView = new ArrayList<>();
    private int mSimType;
    private Toast mToast;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ims_setting);
        this.mList = (ViewGroup) findViewById(R.id.ims_setting_view);
        this.mSetting = (ArrayList) getIntent().getSerializableExtra("mSettingDisplay");
        this.mSimType = getIntent().getIntExtra("mSimType", 0);
        Elog.d(TAG, "mSimType " + this.mSimType);
        initializeViews();
    }

    private void initializeViews() {
        this.mList.removeAllViews();
        ArrayList<Setting> arrayList = this.mSetting;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<Setting> it = this.mSetting.iterator();
            while (it.hasNext()) {
                Setting setting = it.next();
                SettingView view = null;
                switch (setting.getType()) {
                    case 0:
                    case 1:
                        view = new SettingEditTextView(this, setting, setting.getType());
                        break;
                    case 2:
                        view = new SettingSingleSelectView(this, setting);
                        break;
                    case 3:
                        view = new SettingMultiSelectView(this, setting);
                        break;
                    case 4:
                        view = new SettingSpinnerSelectView(this, setting);
                        break;
                }
                if (view != null) {
                    if (!setting.getLabel().equals("manual_impi") && !setting.getLabel().equals("manual_impu") && !setting.getLabel().equals("manual_domain_name")) {
                        this.mList.addView(view);
                    }
                    this.mSettingView.add(view);
                }
            }
        }
    }

    public void onClick(View arg0) {
        Elog.d(TAG, "onClick");
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case 1:
                View dialogView = getLayoutInflater().inflate(R.layout.ims_dialog_config, (ViewGroup) null);
                ViewGroup list = (ViewGroup) dialogView.findViewById(R.id.ims_item_list);
                final ArrayList<SettingView> originalViews = new ArrayList<>();
                final ArrayList<SettingView> views = new ArrayList<>();
                Iterator<SettingView> it = this.mSettingView.iterator();
                while (it.hasNext()) {
                    SettingView s = it.next();
                    if (s.setting.getLabel().equals("manual_impi") || s.setting.getLabel().equals("manual_impu") || s.setting.getLabel().equals("manual_domain_name")) {
                        SettingView v = new SettingEditTextView(this, s.setting, 1);
                        v.setValue(s.getValue());
                        originalViews.add(s);
                        views.add(v);
                        v.findViewById(R.id.ims_config_set).setVisibility(8);
                        list.addView(v);
                    }
                }
                return new AlertDialog.Builder(this).setTitle("Manual Settings").setView(dialogView).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        for (int i = 0; i < views.size(); i++) {
                            ((SettingView) views.get(i)).send();
                            ((SettingView) originalViews.get(i)).setValue(((SettingView) views.get(i)).getValue());
                        }
                        ((SettingView) originalViews.get(0)).requestFocus();
                        imsSettingPageActivity.this.sendCommand("force_user_account_by_manual", "1");
                    }
                }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create();
            default:
                return super.onCreateDialog(id);
        }
    }

    /* access modifiers changed from: private */
    public void sendCommandWansOpId(String name, String value) {
        Message msg = this.mHandler.obtainMessage(1);
        Elog.d(TAG, "AT+EIWLCFGSET=\"" + name + "\",\"" + value + "\"");
        int i = this.mSimType;
        EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+EIWLCFGSET=\"" + name + "\",\"" + value + "\"", ""}, msg);
    }

    /* access modifiers changed from: private */
    public void sendCommand(String name, String value) {
        Message msg = this.mHandler.obtainMessage(1);
        Elog.d(TAG, "AT+ECFGSET=\"" + name + "\",\"" + value + "\"");
        int i = this.mSimType;
        EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+ECFGSET=\"" + name + "\",\"" + value + "\"", ""}, msg);
    }

    /* access modifiers changed from: private */
    public void sendCommand(String name) {
        Message msg = this.mHandler.obtainMessage(1);
        Elog.d(TAG, "AT+ECFGSET=\"" + name + "\"");
        int i = this.mSimType;
        EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+ECFGSET=\"" + name + "\"", ""}, msg);
    }

    /* access modifiers changed from: private */
    public void sendCommand(String name, View obj) {
        Message msg = this.mHandler.obtainMessage(0, obj);
        Elog.d(TAG, "AT+ECFGGET=\"" + name + "\"");
        int i = this.mSimType;
        EmUtils.invokeOemRilRequestStringsEm(i, new String[]{"AT+ECFGGET=\"" + name + "\"", "+ECFGGET:"}, msg);
    }

    /* access modifiers changed from: private */
    public String parseCommandResponse(String data) {
        Elog.d(TAG, "raw data: " + data);
        Matcher m = Pattern.compile("\\+ECFGGET:\\s*\".*\"\\s*,\\s*\"(.*)\"").matcher(data);
        if (m.find()) {
            String value = m.group(1);
            Elog.d(TAG, "value: " + value);
            return value;
        }
        Elog.d(TAG, "wrong format: " + data);
        showToast("wrong format: " + data);
        return "";
    }

    /* access modifiers changed from: private */
    public int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            showToast("Wrong integer format: " + s);
            return -1;
        }
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }

    /* access modifiers changed from: private */
    public SettingView getSettingView(String label) {
        Iterator<SettingView> it = this.mSettingView.iterator();
        while (it.hasNext()) {
            SettingView settingView = it.next();
            if (settingView.setting.label.equals(label)) {
                return settingView;
            }
        }
        return null;
    }

    private abstract class SettingView extends FrameLayout {
        public Button button = ((Button) findViewById(R.id.ims_config_set));
        public TextView label = ((TextView) findViewById(R.id.ims_config_label));
        public Setting setting;

        /* access modifiers changed from: protected */
        public abstract String getValue();

        /* access modifiers changed from: protected */
        public abstract void setValue(String str);

        public SettingView(Context context, Setting setting2) {
            super(context);
            addView(imsSettingPageActivity.this.getLayoutInflater().inflate(R.layout.ims_config_view, (ViewGroup) null));
            this.setting = setting2;
            TextView textView = this.label;
            textView.setText(setting2.label + ":");
            ((TextView) findViewById(R.id.ims_config_suffix)).setText(setting2.suffix);
            if (setting2.label.equals("white list")) {
                Elog.d(imsSettingPageActivity.TAG, "setting.label" + setting2.label);
                this.button.setVisibility(8);
            }
            this.button.setOnClickListener(new View.OnClickListener(imsSettingPageActivity.this) {
                public void onClick(View view) {
                    SettingView.this.send();
                }
            });
            if (setting2.label.equals("Authentication")) {
                imsSettingPageActivity.this.sendCommand("UA_reg_http_digest", (View) this);
            } else if (setting2.label.equals("Security")) {
                imsSettingPageActivity.this.sendCommand("UA_net_ipsec", (View) this);
            } else if (setting2.label.equals("USSD Support")) {
                imsSettingPageActivity.this.sendCommand("ussd_support", (View) this);
            } else if (setting2.label.equals("USSD NW Timeout")) {
                imsSettingPageActivity.this.sendCommand("ussd_nw_timeout_timer", (View) this);
            } else if (setting2.label.equals("USSD Setup Timeout")) {
                imsSettingPageActivity.this.sendCommand("ussd_setup_timeout_timer", (View) this);
            } else if (setting2.label.equals("mncmcc check")) {
                imsSettingPageActivity.this.sendCommand("mncmcc_pass_flag", (View) this);
            } else if (setting2.label.equals("white list")) {
                Elog.d(imsSettingPageActivity.TAG, "Don't do anything!");
            } else if (setting2.label.equals(imsSettingPageActivity.this.mSettingRule)) {
                Elog.d(imsSettingPageActivity.TAG, "Don't do anything!");
            } else if (setting2.label.equals("operator_code_textview")) {
                imsSettingPageActivity.this.sendCommand("operator_code", (View) this);
            } else if (setting2.label.equals("reset_ims_to_default")) {
                Elog.d(imsSettingPageActivity.TAG, "Don't do anything!");
            } else {
                imsSettingPageActivity.this.sendCommand(setting2.label, (View) this);
            }
        }

        public void send() {
            if (this.setting.label.equals("Authentication")) {
                imsSettingPageActivity.this.sendCommand("UA_reg_http_digest", getValue());
            } else if (this.setting.label.equals("force_user_account_by_manual") && getValue().equals("1")) {
                imsSettingPageActivity.this.showDialog(1);
            } else if (this.setting.label.equals("Security")) {
                imsSettingPageActivity.this.sendCommand("UA_net_ipsec", getValue());
            } else if (this.setting.label.equals("USSD Support")) {
                imsSettingPageActivity.this.sendCommand("ussd_support", getValue());
            } else if (this.setting.label.equals("USSD NW Timeout")) {
                imsSettingPageActivity.this.sendCommand("ussd_nw_timeout_timer", getValue());
            } else if (this.setting.label.equals("USSD Setup Timeout")) {
                imsSettingPageActivity.this.sendCommand("ussd_setup_timeout_timer", getValue());
            } else if (this.setting.label.equals("mncmcc check")) {
                Elog.d(imsSettingPageActivity.TAG, "button.getText().toString()" + this.button.getText().toString());
                imsSettingPageActivity.this.sendCommand("mncmcc_pass_flag", getValue());
            } else if (this.setting.label.equals("white list")) {
                imsSettingPageActivity.this.sendCommand("mncmcc_whitelist", (View) this);
            } else if (this.setting.label.equals(imsSettingPageActivity.this.mSettingRule)) {
                imsSettingPageActivity.this.sendCommand("mncmcc_whitelist", getValue());
            } else if (this.setting.label.equals("ch_send")) {
                int chsend = imsSettingPageActivity.this.parseInt(getValue());
                if (chsend < 1 || chsend > 10) {
                    imsSettingPageActivity.this.showToast("The ch_send should be 1~10, please reset it");
                } else {
                    imsSettingPageActivity.this.sendCommand(this.setting.label, getValue());
                }
            } else if (this.setting.label.equals("ch_recv")) {
                int chrecv = imsSettingPageActivity.this.parseInt(getValue());
                if (chrecv < 1 || chrecv > 10) {
                    imsSettingPageActivity.this.showToast("The ch_recv should be 1~10, please reset it");
                } else {
                    imsSettingPageActivity.this.sendCommand(this.setting.label, getValue());
                }
            } else if (this.setting.label.equals("reset_ims_to_default")) {
                imsSettingPageActivity.this.sendCommand(this.setting.label);
            } else if (this.setting.label.equals("operator_code_textview")) {
                imsSettingPageActivity.this.sendCommand("operator_code", getValue());
            } else {
                imsSettingPageActivity.this.sendCommand(this.setting.label, getValue());
                if (this.setting.label.equals("operator_code")) {
                    String info = getValue();
                    if (getValue().equals("16387")) {
                        info = "OP16387";
                        imsSettingPageActivity.this.sendCommandWansOpId("wans_op_id", getValue());
                    }
                    try {
                        EmUtils.getEmHidlService().setVolteMalPctid(info);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Elog.e(imsSettingPageActivity.TAG, "set property failed ...");
                    }
                }
            }
        }
    }

    private class SettingEditTextView extends SettingView {
        public EditText mEditText;

        public SettingEditTextView(Context context, Setting setting, int type) {
            super(context, setting);
            EditText editText = (EditText) findViewById(R.id.ims_config_edit_text);
            this.mEditText = editText;
            if (type == 0) {
                editText.setInputType(2);
            }
            findViewById(R.id.ims_config_edit_layout).setVisibility(0);
            this.mEditText.setText(setting.defaultValue);
            if (setting.label.equals("white list")) {
                this.mEditText.setVisibility(8);
            }
        }

        /* access modifiers changed from: protected */
        public String getValue() {
            return this.mEditText.getText().toString();
        }

        /* access modifiers changed from: protected */
        public void setValue(String value) {
            this.mEditText.setText(value);
        }
    }

    private class SettingSingleSelectView extends SettingView {
        private RadioGroup mRadioGroup;
        private RadioButton[] mRadios;

        public SettingSingleSelectView(Context context, Setting setting) {
            super(context, setting);
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.ims_config_radio_group);
            this.mRadioGroup = radioGroup;
            radioGroup.setVisibility(0);
            this.mRadioGroup.removeAllViews();
            this.mRadios = new RadioButton[setting.entries.size()];
            for (int i = 0; i < setting.entries.size(); i++) {
                RadioButton radio = new RadioButton(imsSettingPageActivity.this);
                radio.setText(setting.entries.get(i));
                radio.setTag(setting.values.get(i));
                this.mRadioGroup.addView(radio);
                this.mRadios[i] = radio;
                if (setting.values.get(i).intValue() == imsSettingPageActivity.this.parseInt(setting.defaultValue)) {
                    this.mRadioGroup.check(radio.getId());
                }
            }
        }

        /* access modifiers changed from: protected */
        public String getValue() {
            int i = 0;
            while (true) {
                RadioButton[] radioButtonArr = this.mRadios;
                if (i >= radioButtonArr.length) {
                    return "";
                }
                if (radioButtonArr[i].isChecked()) {
                    return String.valueOf((Integer) this.mRadios[i].getTag());
                }
                i++;
            }
        }

        /* access modifiers changed from: protected */
        public void setValue(String value) {
            Elog.i(imsSettingPageActivity.TAG, "setValue " + this.setting.label + ", " + value);
            int integerValue = imsSettingPageActivity.this.parseInt(value);
            int i = 0;
            while (true) {
                RadioButton[] radioButtonArr = this.mRadios;
                if (i < radioButtonArr.length) {
                    if (integerValue == ((Integer) radioButtonArr[i].getTag()).intValue()) {
                        this.mRadioGroup.check(this.mRadios[i].getId());
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private class SettingSpinnerSelectView extends SettingView {
        public int[] mAdapterValues;
        public ArrayList<String> mListData;
        public Spinner mSpinner = ((Spinner) findViewById(R.id.ims_config_spinner));

        public SettingSpinnerSelectView(Context context, Setting setting) {
            super(context, setting);
            findViewById(R.id.ims_config_spinner_layout).setVisibility(0);
            this.mListData = new ArrayList<>();
            this.mAdapterValues = new int[setting.entries.size()];
            for (int i = 0; i < setting.entries.size(); i++) {
                this.mListData.add(setting.entries.get(i));
                this.mAdapterValues[i] = setting.values.get(i).intValue();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(imsSettingPageActivity.this, 17367048, this.mListData);
            adapter.setDropDownViewResource(17367049);
            this.mSpinner.setAdapter(adapter);
            for (int j = 0; j < setting.entries.size(); j++) {
                if (this.mAdapterValues[j] == imsSettingPageActivity.this.parseInt(setting.defaultValue)) {
                    this.mSpinner.setSelection(j, true);
                }
            }
        }

        /* access modifiers changed from: protected */
        public String getValue() {
            int id = this.mSpinner.getSelectedItemPosition();
            if (id < 0) {
                return "";
            }
            int[] iArr = this.mAdapterValues;
            if (id < iArr.length) {
                return String.valueOf(iArr[id]);
            }
            return "";
        }

        /* access modifiers changed from: protected */
        public void setValue(String value) {
            Elog.d(imsSettingPageActivity.TAG, "setValue " + this.setting.label + ", " + value);
            int integerValue = imsSettingPageActivity.this.parseInt(value);
            int i = 0;
            while (true) {
                int[] iArr = this.mAdapterValues;
                if (i < iArr.length) {
                    if (integerValue == iArr[i]) {
                        this.mSpinner.setSelection(i, true);
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private class SettingMultiSelectView extends SettingView {
        private CheckBox[] mCheckboxes;

        public SettingMultiSelectView(Context context, Setting setting) {
            super(context, setting);
            ViewGroup checkboxList = (ViewGroup) findViewById(R.id.ims_config_checkbox_list);
            checkboxList.setVisibility(0);
            checkboxList.removeAllViews();
            this.mCheckboxes = new CheckBox[setting.entries.size()];
            for (int i = 0; i < setting.entries.size(); i++) {
                CheckBox checkbox = new CheckBox(imsSettingPageActivity.this);
                checkbox.setText(setting.entries.get(i));
                checkbox.setTag(setting.values.get(i));
                if ((setting.values.get(i).intValue() & imsSettingPageActivity.this.parseInt(setting.defaultValue)) > 0) {
                    checkbox.setChecked(true);
                }
                checkboxList.addView(checkbox);
                this.mCheckboxes[i] = checkbox;
            }
        }

        /* access modifiers changed from: protected */
        public String getValue() {
            int checked = 0;
            int i = 0;
            while (true) {
                CheckBox[] checkBoxArr = this.mCheckboxes;
                if (i >= checkBoxArr.length) {
                    return String.valueOf(checked);
                }
                if (checkBoxArr[i].isChecked()) {
                    checked |= ((Integer) this.mCheckboxes[i].getTag()).intValue();
                }
                i++;
            }
        }

        /* access modifiers changed from: protected */
        public void setValue(String value) {
            int integerValue = imsSettingPageActivity.this.parseInt(value);
            int i = 0;
            while (true) {
                CheckBox[] checkBoxArr = this.mCheckboxes;
                if (i < checkBoxArr.length) {
                    if (integerValue <= 0 || (((Integer) checkBoxArr[i].getTag()).intValue() & integerValue) <= 0) {
                        this.mCheckboxes[i].setChecked(false);
                    } else {
                        this.mCheckboxes[i].setChecked(true);
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
