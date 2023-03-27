package com.mediatek.engineermode.iotconfig;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class ApnConfigFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final int DIALOG_MCCMNC_INVALID = 1;
    private static final int DIALOG_MCCMNC_READFAIL = 0;
    private static final String[] IOT_APN_DEFAULT_LABLES = {"mcc", "mnc", "apn", JsonCmd.STR_TYPE_KEY, "protocol", "roaming_protocol"};
    private static final int MCC_LENGTH = 3;
    private static final int SIM_CARD_INVALID = -1;
    private static final String TAG = "Iot/ApnConfigFragment";
    private Button btnCancel;
    private Button btnConfirm;
    private ArrayList<String> items;
    private TableInfoAdapter mAdapter;
    /* access modifiers changed from: private */
    public EditText mEtMccMnc;
    private FrameLayout mInfoFrameLayout;
    private ListView mListView;
    /* access modifiers changed from: private */
    public boolean mMccMncReadSim = false;
    private String mMccMncText;
    /* access modifiers changed from: private */
    public String mSimMccMnc;
    /* access modifiers changed from: private */
    public int mSimType;
    private Spinner mSpinner;
    private int mSubId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iot_apn_config, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        this.mSpinner = (Spinner) view.findViewById(R.id.spinnernp1);
        EditText editText = (EditText) view.findViewById(R.id.input_mccmnc);
        this.mEtMccMnc = editText;
        limitEditText(editText, 6);
        this.mInfoFrameLayout = (FrameLayout) view.findViewById(R.id.detail_frame_apn);
        this.btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        this.btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.input_mode, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.mSpinner.setAdapter(adapter);
        this.mMccMncReadSim = true;
        this.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (arg2 == 1) {
                    boolean unused = ApnConfigFragment.this.mMccMncReadSim = false;
                    ApnConfigFragment.this.mEtMccMnc.setText("");
                    return;
                }
                boolean unused2 = ApnConfigFragment.this.mMccMncReadSim = true;
                if (ApnConfigFragment.this.mSimType != -1) {
                    ApnConfigFragment.this.mEtMccMnc.setText(ApnConfigFragment.this.mSimMccMnc);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (this.mAdapter == null) {
            this.mAdapter = new TableInfoAdapter(getActivity());
        }
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ApnConfigFragment.this.mEtMccMnc.setText("");
            }
        });
        this.btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ApnConfigFragment apnConfigFragment = ApnConfigFragment.this;
                apnConfigFragment.hideInputMethod(apnConfigFragment.mEtMccMnc);
                ApnConfigFragment.this.queryAndShowApn();
            }
        });
        this.mEtMccMnc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event == null || event.getKeyCode() != 66) && actionId != 6) {
                    return false;
                }
                ApnConfigFragment apnConfigFragment = ApnConfigFragment.this;
                apnConfigFragment.hideInputMethod(apnConfigFragment.mEtMccMnc);
                return false;
            }
        });
    }

    public void hideInputMethod(View view) {
        if (getActivity() != null && view == null) {
            view = getActivity().getCurrentFocus();
        }
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService("input_method");
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void queryAndShowApn() {
        String replace = this.mEtMccMnc.getText().toString().replace(" ", "");
        this.mMccMncText = replace;
        if (!checkValidMCCMNC(replace)) {
            showDialog(1, getString(R.string.invalidfail) + this.mMccMncText);
            return;
        }
        this.mAdapter.clear();
        String mcc = this.mMccMncText.substring(0, 3);
        String str = this.mMccMncText;
        String mnc = str.substring(3, str.length());
        Elog.d(TAG, "mcc: " + mcc + "mnc: " + mnc);
        StringBuilder sb = new StringBuilder();
        sb.append("numeric=\"");
        sb.append(this.mMccMncText);
        sb.append("\"");
        String queryAPn = sb.toString();
        Elog.d(TAG, "queryAPn: " + queryAPn);
        Cursor apns = getActivity().getContentResolver().query(Telephony.Carriers.CONTENT_URI, (String[]) null, queryAPn, (String[]) null, "name ASC");
        if (apns != null) {
            try {
                if (apns.moveToFirst() && apns.getCount() > 0) {
                    Elog.d(TAG, "[queryAndShowApn]apns count is: " + apns.getCount());
                    int apnNum = 0;
                    if (this.mSpinner.getSelectedItemPosition() == 0 && this.mMccMncText.equals(this.mSimMccMnc)) {
                        TableInfoAdapter tableInfoAdapter = this.mAdapter;
                        tableInfoAdapter.add(new String[]{"Read from  Sim" + (this.mSimType + 1)});
                    }
                    do {
                        apnNum++;
                        TableInfoAdapter tableInfoAdapter2 = this.mAdapter;
                        tableInfoAdapter2.add(new String[]{"APN " + apnNum});
                        int i = 0;
                        while (true) {
                            String[] strArr = IOT_APN_DEFAULT_LABLES;
                            if (i >= strArr.length) {
                                break;
                            }
                            int index = apns.getColumnIndex(strArr[i]);
                            if (index != -1) {
                                this.mAdapter.add(new String[]{apns.getColumnNames()[index], apns.getString(index)});
                            }
                            i++;
                        }
                    } while (apns.moveToNext() != 0);
                }
            } catch (Throwable th) {
                if (apns != null) {
                    apns.close();
                }
                updateUI();
                throw th;
            }
        }
        if (apns != null) {
            apns.close();
        }
        updateUI();
    }

    public void updateUI() {
        this.mInfoFrameLayout.removeAllViews();
        View listView = getListView();
        if (this.mInfoFrameLayout.getChildCount() <= 0 && listView != null) {
            if (listView.getParent() != null) {
                ((ViewGroup) listView.getParent()).removeView(listView);
            }
            this.mInfoFrameLayout.addView(listView);
        }
    }

    public View getListView() {
        if (this.mAdapter == null) {
            this.mAdapter = new TableInfoAdapter(getActivity());
        }
        if (this.mListView == null) {
            this.mListView = new ListView(getActivity());
        }
        if (this.mAdapter.getCount() == 0) {
            int i = 0;
            while (true) {
                String[] strArr = IOT_APN_DEFAULT_LABLES;
                if (i >= strArr.length) {
                    break;
                }
                this.mAdapter.add(new String[]{strArr[i], ""});
                i++;
            }
        }
        this.mListView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        return this.mListView;
    }

    private boolean checkValidMCCMNC(String text) {
        if (5 > text.length() || 6 < text.length()) {
            return false;
        }
        return true;
    }

    public void limitEditText(EditText et, int limitLength) {
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(limitLength)});
        et.setKeyListener(new NumberKeyListener() {
            /* access modifiers changed from: protected */
            public char[] getAcceptedChars() {
                return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            }

            public int getInputType() {
                return 2;
            }
        });
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            hideInputMethod((View) null);
        }
    }

    public void onResume() {
        String str;
        super.onResume();
        initMccMncValue();
        if (this.mMccMncReadSim && this.mSimType != -1 && (str = this.mSimMccMnc) != null && !str.replace(" ", "").equals("")) {
            this.mEtMccMnc.setText(this.mSimMccMnc);
            queryAndShowApn();
        }
    }

    public void initMccMncValue() {
        if (TelephonyManager.getDefault().getPhoneCount() >= 1 && isSimReady(0)) {
            this.mSimType = 0;
        } else if (TelephonyManager.getDefault().getPhoneCount() <= 1 || !isSimReady(1)) {
            this.mSimType = -1;
        } else {
            this.mSimType = 1;
        }
        int i = this.mSimType;
        if (i != -1) {
            int[] SubId = SubscriptionManager.getSubId(i);
            if (SubId != null && SubId.length > 0) {
                this.mSubId = SubId[0];
            }
            FragmentActivity activity = getActivity();
            getActivity();
            this.mSimMccMnc = ((TelephonyManager) activity.getSystemService("phone")).getSimOperator(this.mSubId);
        }
        if (this.mSimMccMnc == null) {
            Elog.d(TAG, "Fail to read SIM MCC+MNC!");
            return;
        }
        Elog.d(TAG, "Read SIM MCC+MNC: " + this.mSimMccMnc);
    }

    /* access modifiers changed from: protected */
    public boolean isSimReady(int mSlotId) {
        FragmentActivity activity = getActivity();
        getActivity();
        if (1 == ((TelephonyManager) activity.getSystemService("phone")).getSimState(mSlotId)) {
            return false;
        }
        return true;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
    }

    /* access modifiers changed from: protected */
    public Dialog showDialog(int id, String info) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(getActivity()).setMessage(info).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ApnConfigFragment.this.mEtMccMnc.setText("");
                        dialog.cancel();
                    }
                }).show();
            case 1:
                return new AlertDialog.Builder(getActivity()).setMessage(info).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
            default:
                return null;
        }
    }
}
