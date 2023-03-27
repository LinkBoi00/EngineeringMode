package com.mediatek.engineermode.iotconfig;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.support.v4.app.Fragment;
import android.telephony.SubscriptionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmApplication;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class XCAPConfigFragment extends Fragment implements AdapterView.OnItemClickListener {
    protected static final String DEFAULT_DISABLE_VALUE = "0";
    protected static final String DEFAULT_ENABLE_VALUE = "1";
    protected static final String DEFAULT_VALUE_XCAP_BOOLEAN = "0";
    protected static final String DEFAULT_VALUE_XCAP_INTEGER = "-1";
    protected static final int DEFAULT_VALUE_XCAP_READY = 0;
    protected static final String DEFAULT_VALUE_XCAP_STRING = "";
    private static final int OPERATION_TIME_OUT_MILLIS = 3000;
    protected static final int RESET_VALUE_XCAP_OK = 3;
    protected static final int SET_SS_PROPERTY = 4;
    protected static final int SET_VALUE_XCAP_FAIL = 2;
    protected static final int SET_VALUE_XCAP_OK = 1;
    protected static final String TAG = "Iot/XCAPConfigFragment";
    public static final int UNKNOW = -1;
    protected static final int XCAP_ACTION_GET = 0;
    protected static final int XCAP_ACTION_RESET = 2;
    protected static final int XCAP_ACTION_SET = 1;
    protected static final String mATCMD = "SET_SS_PROP";
    private HashMap<String, String> XcapItemTypes;
    private LinkedHashMap<String, String> XcapItems;
    private XcapSettingAdapter adapter;
    private Button btnCancel;
    private Button btnSave;
    private ListView listView;
    /* access modifiers changed from: private */
    public CountDownLatch mCallbackLatch;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    XCAPConfigFragment.this.updateUI((List) msg.obj);
                    return;
                case 1:
                    XCAPConfigFragment.this.updateUI((List) msg.obj);
                    EmUtils.showToast("Set values done!");
                    return;
                case 2:
                    EmUtils.showToast("Set values failed!");
                    new AlertDialog.Builder(EmApplication.getContext()).setMessage("Set values failed!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();
                    return;
                case 3:
                    XCAPConfigFragment.this.updateUI((List) msg.obj);
                    EmUtils.showToast("Reset values done!");
                    return;
                case 4:
                    AsyncResult ar = (AsyncResult) msg.obj;
                    if (ar.exception != null) {
                        Elog.e(XCAPConfigFragment.TAG, "SET_SS_PROPERTY: ar.exception=" + ar.exception);
                        EmUtils.showToast("Set values to modem failed!");
                    }
                    if (XCAPConfigFragment.this.mCallbackLatch != null) {
                        XCAPConfigFragment.this.mCallbackLatch.countDown();
                    }
                    Elog.d(XCAPConfigFragment.TAG, "SET_SS_PROPERTY done");
                    return;
                default:
                    return;
            }
        }
    };
    private boolean mIs95Modem = false;
    public int mPhoneId = 0;
    private String[] mXcapBoolCfg;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPhoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
        this.mIs95Modem = SystemProperties.get(FeatureSupport.FK_MTK_95_SUPPORT).equals("1");
        Elog.d(TAG, "mIs95Modem = " + this.mIs95Modem);
        if (FeatureSupport.is93ModemAndAbove() && !this.mIs95Modem) {
            this.mXcapBoolCfg = IotConfigConstant.mXcapBoolCfgFor93;
            this.XcapItems = IotConfigConstant.mXcapItemsFor93;
            this.XcapItemTypes = IotConfigConstant.mXcapItemTypesFor93;
        } else if (this.mIs95Modem) {
            this.mXcapBoolCfg = IotConfigConstant.mXcapBoolCfgBeyond93;
            this.XcapItems = IotConfigConstant.mXcapItemsBeyond93;
            this.XcapItemTypes = IotConfigConstant.mXcapItemTypesBeyond93;
        } else {
            this.mXcapBoolCfg = IotConfigConstant.mXcapBoolCfgBelow93;
            this.XcapItems = IotConfigConstant.mXcapItemsBelow93;
            this.XcapItemTypes = IotConfigConstant.mXcapItemTypesBelow93;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iot_xcap_config, (ViewGroup) null);
        this.listView = (ListView) view.findViewById(R.id.xcap_list);
        this.btnSave = (Button) view.findViewById(R.id.btnSave);
        this.btnCancel = (Button) view.findViewById(R.id.btnCancel);
        XcapSettingAdapter xcapSettingAdapter = new XcapSettingAdapter(getActivity(), getEmptyModel());
        this.adapter = xcapSettingAdapter;
        this.listView.setAdapter(xcapSettingAdapter);
        this.btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new LoadModelThread(1).start();
            }
        });
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new LoadModelThread(2).start();
            }
        });
        return view;
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

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            hideInputMethod((View) null);
        }
    }

    public class LoadModelThread extends Thread {
        private int action;

        public LoadModelThread(int action2) {
            this.action = action2;
        }

        public void run() {
            Message msg = new Message();
            switch (this.action) {
                case 0:
                    msg.obj = XCAPConfigFragment.this.getModel();
                    msg.what = 0;
                    break;
                case 1:
                    if (!XCAPConfigFragment.this.setModel()) {
                        msg.what = 2;
                        break;
                    } else {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        msg.obj = XCAPConfigFragment.this.getModel();
                        msg.what = 1;
                        break;
                    }
                case 2:
                    msg.obj = XCAPConfigFragment.this.resetModel();
                    msg.what = 3;
                    break;
                default:
                    msg.what = -1;
                    break;
            }
            XCAPConfigFragment.this.mHandler.sendMessage(msg);
        }
    }

    public void updateUI(List<XCAPModel> updateModel) {
        this.adapter.clear();
        this.adapter.addAll(updateModel);
        this.adapter.notifyDataSetChanged();
    }

    public void sendATcmdToRIL(int phoneid, String[] cmd, Message response) {
        Elog.d(TAG, "sendATcmdToRIL : " + Arrays.toString(cmd));
        this.mCallbackLatch = new CountDownLatch(1);
        EmUtils.invokeOemRilRequestStringsEm(phoneid, cmd, this.mHandler.obtainMessage(4));
        if (!isCallbackDone()) {
            Elog.e(TAG, "waitForCallback: callback is not done!");
        }
    }

    private boolean isCallbackDone() {
        boolean isDone;
        try {
            isDone = this.mCallbackLatch.await(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            isDone = false;
        }
        Elog.d(TAG, "waitForCallback: isDone=" + isDone);
        return isDone;
    }

    /* access modifiers changed from: private */
    public boolean setModel() {
        int mBoolConfig = 0;
        int mBoolValue = 0;
        List<XCAPModel> mList = this.adapter.getList();
        Elog.d(TAG, "[setModel]listSize : " + mList.size());
        String[] cmd = new String[2];
        for (int i = 0; i < mList.size(); i++) {
            if (i < this.mXcapBoolCfg.length) {
                if (this.mIs95Modem) {
                    if (mList.get(i).isConfiged()) {
                        cmd[0] = mATCMD;
                        StringBuilder sb = new StringBuilder();
                        sb.append(IotConfigConstant.SS_PREFIX);
                        sb.append(IotConfigConstant.mXcapBoolPropertyBeyond93[i]);
                        sb.append(",");
                        sb.append(mList.get(i).isSelected() ? "1" : "0");
                        cmd[1] = sb.toString();
                        sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
                    }
                } else if (mList.get(i).isConfiged()) {
                    if (mList.get(i).isSelected()) {
                        mBoolValue |= 1 << i;
                    } else {
                        mBoolValue &= ~(1 << i);
                    }
                    mBoolConfig |= 1 << i;
                } else {
                    mBoolConfig &= ~(1 << i);
                }
            } else if (!DEFAULT_VALUE_XCAP_STRING.equals(mList.get(i).getValue())) {
                cmd[0] = mATCMD;
                cmd[1] = this.XcapItems.get(mList.get(i).getName()) + "," + mList.get(i).getValue();
                sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
            }
        }
        if (this.mIs95Modem == 0) {
            Elog.d(TAG, "[setModel]BoolValue: " + mBoolValue + " BoolConfig: " + mBoolConfig);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("persist.vendor.ss.boolvalue,");
            sb2.append(mBoolValue);
            sendATcmdToRIL(this.mPhoneId, new String[]{mATCMD, sb2.toString()}, this.mHandler.obtainMessage(4));
            sendATcmdToRIL(this.mPhoneId, new String[]{mATCMD, "persist.vendor.ss.boolconfig," + mBoolConfig}, this.mHandler.obtainMessage(4));
        }
        return true;
    }

    /* access modifiers changed from: private */
    public List<XCAPModel> resetModel() {
        ArrayList<XCAPModel> mList = new ArrayList<>();
        String[] cmd = new String[2];
        if (!this.mIs95Modem) {
            sendATcmdToRIL(this.mPhoneId, new String[]{mATCMD, "persist.vendor.ss.boolvalue,0"}, this.mHandler.obtainMessage(4));
            sendATcmdToRIL(this.mPhoneId, new String[]{mATCMD, "persist.vendor.ss.boolconfig,0"}, this.mHandler.obtainMessage(4));
        } else {
            cmd[0] = mATCMD;
            cmd[1] = IotConfigConstant.FK_SS_CONFIG_RESET_95;
            sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
        }
        int i = 0;
        while (true) {
            String[] strArr = this.mXcapBoolCfg;
            if (i >= strArr.length) {
                break;
            }
            mList.add(new XCAPModel(strArr[i], IotConfigConstant.BOOLEANTYPE));
            if (this.mIs95Modem) {
                cmd[0] = mATCMD;
                cmd[1] = IotConfigConstant.SS_PREFIX + IotConfigConstant.mXcapBoolPropertyBeyond93[i] + ",";
                sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
            }
            i++;
        }
        for (String str : this.XcapItems.keySet()) {
            if (!this.mIs95Modem) {
                if (this.XcapItemTypes.get(str).equals(IotConfigConstant.STRINGTYPE)) {
                    cmd[0] = mATCMD;
                    cmd[1] = this.XcapItems.get(str) + "," + DEFAULT_VALUE_XCAP_STRING;
                    sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
                    mList.add(new XCAPModel(str, SystemProperties.get(this.XcapItems.get(str)), this.XcapItemTypes.get(str)));
                } else if (this.XcapItemTypes.get(str).equals(IotConfigConstant.INTEGERTYPE)) {
                    cmd[0] = mATCMD;
                    cmd[1] = this.XcapItems.get(str) + "," + String.valueOf(DEFAULT_VALUE_XCAP_INTEGER);
                    sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
                    mList.add(new XCAPModel(str, SystemProperties.get(this.XcapItems.get(str)), this.XcapItemTypes.get(str)));
                } else {
                    cmd[0] = mATCMD;
                    cmd[1] = this.XcapItems.get(str) + "," + "0";
                    sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
                    mList.add(new XCAPModel(str, SystemProperties.get(this.XcapItems.get(str)).equals("1"), this.XcapItemTypes.get(str)));
                }
            } else if (this.XcapItemTypes.get(str).equals(IotConfigConstant.STRINGTYPE)) {
                cmd[0] = mATCMD;
                cmd[1] = this.XcapItems.get(str) + ",";
                sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
                mList.add(new XCAPModel(str, SystemProperties.get(this.XcapItems.get(str)), this.XcapItemTypes.get(str)));
            } else if (this.XcapItemTypes.get(str).equals(IotConfigConstant.INTEGERTYPE)) {
                cmd[0] = mATCMD;
                cmd[1] = this.XcapItems.get(str) + ",";
                sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
                mList.add(new XCAPModel(str, SystemProperties.get(this.XcapItems.get(str)), this.XcapItemTypes.get(str)));
            } else {
                cmd[0] = mATCMD;
                cmd[1] = this.XcapItems.get(str) + ",";
                sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
                mList.add(new XCAPModel(str, SystemProperties.get(this.XcapItems.get(str)).equals("1"), this.XcapItemTypes.get(str)));
            }
        }
        if (this.mIs95Modem) {
            cmd[0] = mATCMD;
            cmd[1] = IotConfigConstant.FK_SS_CONFIG_RESET_DONE_95;
            sendATcmdToRIL(this.mPhoneId, cmd, this.mHandler.obtainMessage(4));
        }
        return mList;
    }

    private List<XCAPModel> getEmptyModel() {
        ArrayList<XCAPModel> mList = new ArrayList<>();
        int i = 0;
        while (true) {
            String[] strArr = this.mXcapBoolCfg;
            if (i >= strArr.length) {
                break;
            }
            mList.add(new XCAPModel(strArr[i], IotConfigConstant.BOOLEANTYPE));
            i++;
        }
        for (String str : this.XcapItems.keySet()) {
            mList.add(new XCAPModel(str, this.XcapItemTypes.get(str)));
        }
        return mList;
    }

    /* access modifiers changed from: private */
    public List<XCAPModel> getModel() {
        ArrayList<XCAPModel> mList = new ArrayList<>();
        if (!this.mIs95Modem) {
            String boolValue = Integer.toBinaryString(Integer.valueOf(SystemProperties.get(IotConfigConstant.FK_SS_BOOLVALUE, "0")).intValue());
            String boolConfig = Integer.toBinaryString(Integer.valueOf(SystemProperties.get(IotConfigConstant.FK_SS_BOOLCONFIG, "0")).intValue());
            Elog.d(TAG, "[getModel]BoolValue: " + boolValue + " BoolConfig: " + boolConfig);
            char[] cArr = new char[boolValue.length()];
            char[] cArr2 = new char[boolConfig.length()];
            char[] results = boolValue.toCharArray();
            char[] configs = boolConfig.toCharArray();
            int i = 0;
            while (true) {
                String[] strArr = this.mXcapBoolCfg;
                if (i >= strArr.length) {
                    break;
                }
                boolean itemConfiged = false;
                if (i < configs.length && configs[(configs.length - 1) - i] == '1') {
                    itemConfiged = true;
                }
                boolean itemSelected = false;
                if (i < results.length && results[(results.length - 1) - i] == '1') {
                    itemSelected = true;
                }
                mList.add(new XCAPModel(strArr[i], itemConfiged, itemSelected, IotConfigConstant.BOOLEANTYPE));
                i++;
            }
        } else {
            for (int i2 = 0; i2 < this.mXcapBoolCfg.length; i2++) {
                boolean result = SystemProperties.get(IotConfigConstant.SS_PREFIX + IotConfigConstant.mXcapBoolPropertyBeyond93[i2]).equals("1");
                mList.add(new XCAPModel(this.mXcapBoolCfg[i2], result, result, IotConfigConstant.BOOLEANTYPE));
            }
        }
        for (String str : this.XcapItems.keySet()) {
            if (this.XcapItemTypes.get(str).equals(IotConfigConstant.BOOLEANTYPE)) {
                boolean itemSelected2 = false;
                if (SystemProperties.get(this.XcapItems.get(str)).equals("1")) {
                    itemSelected2 = true;
                }
                mList.add(new XCAPModel(str, itemSelected2, this.XcapItemTypes.get(str)));
            } else {
                mList.add(new XCAPModel(str, SystemProperties.get(this.XcapItems.get(str), DEFAULT_VALUE_XCAP_STRING), this.XcapItemTypes.get(str)));
            }
        }
        return mList;
    }

    public void onResume() {
        new LoadModelThread(0).start();
        super.onResume();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
    }
}
