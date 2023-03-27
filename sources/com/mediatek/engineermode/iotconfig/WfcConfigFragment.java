package com.mediatek.engineermode.iotconfig;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.wfo.IWifiOffloadService;
import com.mediatek.wfo.WifiOffloadManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class WfcConfigFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Iot/WfcConfigFragment";
    public static final int UNKNOW = 2;
    public static final int WFC_ACTION_RESET_MCCMNC = 1;
    public static final int WFC_ACTION_SET_MCCMNC = 0;
    public static final int WFC_SET_MCCMNC_FAILED = 4;
    public static final int WFC_SET_MCCMNC_INPUT_ILLEGAL = 2;
    public static final int WFC_SET_MCCMNC_LENGTH_TOO_LONG = 1;
    public static final int WFC_SET_MCCMNC_OK = 3;
    public static final int WFC_SHOW_ADDED_MCCMNC_LIST = 1;
    public static final int WFC_SHOW_ALL_MCCMNC_LIST = 2;
    public static final int WFC_SHOW_EXISTED_MCCMNC_LIST = 0;
    private LinearLayout addMccMncLayout;
    private Button btnAddCancel;
    private Button btnAddMccMnc;
    private Button btnAddOk;
    private Button btnResetMccMnc;
    /* access modifiers changed from: private */
    public int currentList = 0;
    private LinearLayout defaultAddMccMncLayout;
    /* access modifiers changed from: private */
    public EditText inputMccMnc;
    private MccMncAdapter mAdapter;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    WfcConfigFragment.this.showToast("MCC/MNC input illegal!");
                    return;
                case 3:
                    WfcConfigFragment.this.setAddMccMncLayoutVisibility(false);
                    WfcConfigFragment wfcConfigFragment = WfcConfigFragment.this;
                    wfcConfigFragment.updateUI(wfcConfigFragment.currentList);
                    WfcConfigFragment.this.showToast("Set/Reset MCC/MNC ok!");
                    return;
                case 4:
                    WfcConfigFragment.this.showToast("Set MCC/MNC failed!");
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public IWifiOffloadService mWfoService;
    private ListView mccMncListView;
    private Spinner mccMncSpinner;

    public void setAddMccMncLayoutVisibility(boolean visibility) {
        if (visibility) {
            String mccMncContent = "";
            if (getMccMncList(1) != null) {
                mccMncContent = TextUtils.join(",", getMccMncList(1));
            }
            this.inputMccMnc.setText(mccMncContent);
            this.addMccMncLayout.setVisibility(0);
            this.defaultAddMccMncLayout.setVisibility(8);
            limitEditText(this.inputMccMnc, 92);
            return;
        }
        this.inputMccMnc.setText("");
        this.addMccMncLayout.setVisibility(8);
        this.defaultAddMccMncLayout.setVisibility(0);
        hideInputMethod(this.inputMccMnc);
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iot_wfc_config, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        this.mccMncSpinner = (Spinner) view.findViewById(R.id.mccmnc_list_spinner);
        this.btnAddMccMnc = (Button) view.findViewById(R.id.add_mccmnc);
        this.btnResetMccMnc = (Button) view.findViewById(R.id.reset_mccmnc);
        this.mccMncListView = (ListView) view.findViewById(R.id.mccmnc_list);
        this.addMccMncLayout = (LinearLayout) view.findViewById(R.id.add_mccmnc_layout);
        this.defaultAddMccMncLayout = (LinearLayout) view.findViewById(R.id.reset_mccmnc_layout);
        this.btnAddOk = (Button) view.findViewById(R.id.add_mccmnc_ok);
        this.btnAddCancel = (Button) view.findViewById(R.id.add_mccmnc_cancel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.mccmnc_display_mode, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.mccMncSpinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener l = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        WfcConfigFragment.this.updateUI(0);
                        return;
                    case 1:
                        WfcConfigFragment.this.updateUI(1);
                        return;
                    case 2:
                        WfcConfigFragment.this.updateUI(2);
                        return;
                    default:
                        return;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        this.inputMccMnc = (EditText) view.findViewById(R.id.wfc_input_mccmnc);
        setAddMccMncLayoutVisibility(false);
        this.mccMncSpinner.setOnItemSelectedListener(l);
        this.mWfoService = IWifiOffloadService.Stub.asInterface(ServiceManager.getService(WifiOffloadManager.WFO_SERVICE));
        MccMncAdapter mccMncAdapter = new MccMncAdapter(getActivity(), getMccMncList(this.currentList));
        this.mAdapter = mccMncAdapter;
        this.mccMncListView.setAdapter(mccMncAdapter);
        this.btnAddMccMnc.setOnClickListener(this);
        this.btnResetMccMnc.setOnClickListener(this);
        this.btnAddOk.setOnClickListener(this);
        this.btnAddCancel.setOnClickListener(this);
        this.inputMccMnc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event == null || event.getKeyCode() != 66) && actionId != 6) {
                    return false;
                }
                WfcConfigFragment wfcConfigFragment = WfcConfigFragment.this;
                wfcConfigFragment.hideInputMethod(wfcConfigFragment.inputMccMnc);
                return false;
            }
        });
    }

    public void limitEditText(EditText et, int limitLength) {
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(limitLength)});
        et.setKeyListener(new NumberKeyListener() {
            /* access modifiers changed from: protected */
            public char[] getAcceptedChars() {
                return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ','};
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_mccmnc:
                setAddMccMncLayoutVisibility(true);
                return;
            case R.id.add_mccmnc_cancel:
                setAddMccMncLayoutVisibility(false);
                return;
            case R.id.add_mccmnc_ok:
                String mccMncContent = this.inputMccMnc.getText().toString().replace(" ", "");
                if (mccMncContent != null && !mccMncContent.equals("")) {
                    new SetMccMncThread(0, mccMncContent).start();
                    return;
                }
                return;
            case R.id.reset_mccmnc:
                new SetMccMncThread(1, "").start();
                return;
            default:
                return;
        }
    }

    public class SetMccMncThread extends Thread {
        private int action;
        private String mccMncContent;

        public SetMccMncThread(int action2, String mccMncContent2) {
            this.action = action2;
            this.mccMncContent = mccMncContent2;
        }

        public void run() {
            Message msg = new Message();
            switch (this.action) {
                case 0:
                    if (this.mccMncContent.length() <= 92) {
                        String[] mccMncArray = this.mccMncContent.split(",");
                        if (!WfcConfigFragment.this.checkInputMccMnc(mccMncArray)) {
                            msg.what = 2;
                            break;
                        } else {
                            boolean setMccMncOk = false;
                            try {
                                setMccMncOk = WfcConfigFragment.this.mWfoService.setMccMncAllowList(mccMncArray);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            if (!setMccMncOk) {
                                msg.what = 4;
                                break;
                            } else {
                                msg.what = 3;
                                break;
                            }
                        }
                    } else {
                        msg.what = 1;
                        break;
                    }
                case 1:
                    boolean setMccMncOk2 = false;
                    try {
                        setMccMncOk2 = WfcConfigFragment.this.mWfoService.setMccMncAllowList((String[]) null);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                    if (!setMccMncOk2) {
                        msg.what = 4;
                        break;
                    } else {
                        msg.what = 3;
                        break;
                    }
                default:
                    msg.what = 2;
                    break;
            }
            WfcConfigFragment.this.mHandler.sendMessage(msg);
        }
    }

    public void showToast(String content) {
        Toast.makeText(getActivity(), content, 0).show();
    }

    public boolean checkInputMccMnc(String[] mccMncArray) {
        for (String mccMnc : mccMncArray) {
            if (5 > mccMnc.length() || 6 < mccMnc.length()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getMccMncList(int type) {
        ArrayList<String> mMccMncList = new ArrayList<>();
        IWifiOffloadService iWifiOffloadService = this.mWfoService;
        if (iWifiOffloadService == null) {
            return mMccMncList;
        }
        try {
            String[] mccMncResult = iWifiOffloadService.getMccMncAllowList(type);
            if (mccMncResult == null) {
                return null;
            }
            ArrayList<String> mMccMncList2 = new ArrayList<>(Arrays.asList(mccMncResult));
            Elog.d(TAG, "Read MccMncList : " + mMccMncList2.toString());
            return mMccMncList2;
        } catch (RemoteException e) {
            Elog.e(TAG, "RemoteException ImsCallSessionProxy()");
            return mMccMncList;
        }
    }

    public void updateUI(int type) {
        this.currentList = type;
        MccMncAdapter mccMncAdapter = this.mAdapter;
        if (mccMncAdapter == null) {
            this.mAdapter = new MccMncAdapter(getActivity(), getMccMncList(type));
        } else {
            mccMncAdapter.clear();
            if (getMccMncList(type) != null) {
                Iterator<String> it = getMccMncList(type).iterator();
                while (it.hasNext()) {
                    this.mAdapter.add(it.next());
                }
            }
            this.mAdapter.refresh(getMccMncList(type));
        }
        this.mccMncListView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
    }

    public void onResume() {
        super.onResume();
        updateUI(this.currentList);
    }
}
