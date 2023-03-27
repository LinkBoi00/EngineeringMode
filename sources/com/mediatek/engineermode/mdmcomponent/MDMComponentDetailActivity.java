package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.ModemCategory;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.mdmcomponent.MDMContentICD;
import com.mediatek.mdml.Msg;
import com.mediatek.mdml.TRAP_TYPE;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MDMComponentDetailActivity extends Activity implements View.OnClickListener {
    private static final int DIALOG_MDM_ERROR = 1;
    private static final int DIALOG_WAIT_UNSUBSCRIB = 0;
    private static final int ENABLETRAP_DONE = 3;
    private static final int MDM_ERROR = 4;
    public static final int MSG_INIT_UI = 5;
    private static final int MSG_MDM_ENABLETRAP = 0;
    public static final int MSG_MDM_UNSUBSCRIBE = 1;
    public static final int MSG_TASK_DONE = 1;
    public static final int MSG_TASK_START = 0;
    public static final int MSG_TASK_STOP = 2;
    private static final String TAG = "EmInfo/MDMComponentDetailActivity";
    private static final int UNSUBSCRIBE_DONE = 2;
    /* access modifiers changed from: private */
    public static boolean isMsgShow = false;
    public static int mModemType;
    public static String[] mSimMccMnc = new String[2];
    public static int mSimType;
    public static Handler mUpdateUiHandler;
    public static UpdateTaskDriven taskDriver;
    private List<MDMComponent> allComponents;
    private ArrayList<String> checked;
    private View content;
    private int leftEdge;
    ArrayList<Long> mCheckedEmInteger = new ArrayList<>();
    List<MDMComponent> mComponents;
    private MDMComponent mCurrentItem;
    private int mCurrentItemIndex = 0;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Elog.d(MDMComponentDetailActivity.TAG, "Wait unSubscribe message done");
                    if (MDMComponentDetailActivity.this.mProgressDialog != null && MDMComponentDetailActivity.this.mProgressDialog.isShowing()) {
                        MDMComponentDetailActivity.this.removeDialog(0);
                        MDMComponentDetailActivity.this.finish();
                        return;
                    }
                    return;
                case 3:
                    if (!ComponentSelectActivity.checkMonitorAlive()) {
                        Elog.d(MDMComponentDetailActivity.TAG, "[InitTask] m_cmdProxy is null, md_monitor is stopped, finish");
                        MDMComponentDetailActivity.this.showProgressDialog(1);
                        return;
                    }
                    if (MDMComponentDetailActivity.taskDriver == null) {
                        MDMComponentDetailActivity.taskDriver = new UpdateTaskDriven();
                    }
                    MDMComponentDetailActivity.this.updateUI();
                    MDMComponentDetailActivity.this.taskExcute(0);
                    boolean unused = MDMComponentDetailActivity.isMsgShow = true;
                    Elog.d(MDMComponentDetailActivity.TAG, "init MDMComponentDetailActivity done, select " + MDMComponentDetailActivity.this.mItemCount);
                    return;
                case 4:
                    MDMComponentDetailActivity.this.showProgressDialog(1);
                    return;
                default:
                    return;
            }
        }
    };
    private FrameLayout mInfoFrameLayout;
    /* access modifiers changed from: private */
    public int mItemCount = 0;
    private Button mPageDown;
    private Button mPageUp;
    /* access modifiers changed from: private */
    public ProgressDialog mProgressDialog;
    private TextView mTitle;
    private View menu;
    private int menuPadding = 80;
    private LinearLayout.LayoutParams menuParams;
    private int screenHeight;
    private int screenWidth;

    /* access modifiers changed from: private */
    public void showProgressDialog(int dialogNum) {
        if (!isFinishing()) {
            showDialog(dialogNum);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Elog.d(TAG, "init MDMComponentDetailActivity ..");
        setContentView(R.layout.mdm_component_detail);
        this.mTitle = (TextView) findViewById(R.id.detail_title_mdm);
        this.mInfoFrameLayout = (FrameLayout) findViewById(R.id.detail_frame_mdm);
        this.mPageUp = (Button) findViewById(R.id.NetworkInfo_PageUp_mdm);
        this.mPageDown = (Button) findViewById(R.id.NetworkInfo_PageDown_mdm);
        this.mPageUp.setOnClickListener(this);
        this.mPageDown.setOnClickListener(this);
        Intent intent = getIntent();
        this.checked = (ArrayList) intent.getSerializableExtra("mChecked");
        mSimType = intent.getIntExtra("mSimType", 0);
        mModemType = intent.getIntExtra("mModemType", 1);
        this.allComponents = MDMComponent.getComponents(this);
        this.mComponents = new ArrayList();
        mUpdateUiHandler = new Handler() {
            public void handleMessage(Message msg) {
                Task task = (Task) msg.obj;
                switch (msg.what) {
                    case 1:
                        ByteBuffer icdPacket = (ByteBuffer) task.getExtraMsg();
                        MDMContentICD.MDMHeaderICD IcdHeader = task.getIcdHeader();
                        String msgIDStr = Long.toHexString((long) IcdHeader.msg_id).toUpperCase();
                        for (MDMComponent c : MDMComponentDetailActivity.this.mComponents) {
                            if (c.hasEmType(msgIDStr) && MDMComponentDetailActivity.isMsgShow) {
                                if (IcdHeader.protocol_id != MDMComponentDetailActivity.mModemType) {
                                    Elog.d(MDMComponentDetailActivity.TAG, "ICD Msg has an unSelected sim, [" + c.getName() + "] msgIDStr = " + msgIDStr + ",Slot = " + (MDMComponentDetailActivity.mSimType + 1) + ",Protocol = " + MDMComponentDetailActivity.mModemType + ",getSimIdx = " + IcdHeader.protocol_id);
                                    MDMComponentDetailActivity.this.taskExcute(1);
                                    return;
                                }
                                c.update(msgIDStr, icdPacket);
                            }
                        }
                        break;
                    case 3:
                        Msg msgObj = (Msg) task.getExtraMsg();
                        if (msgObj != null && msgObj.getMsgName() != null) {
                            String name = msgObj.getMsgName().toUpperCase();
                            Integer msgIdInt = ComponentSelectActivity.getDecoder(MDMComponentDetailActivity.this).msgInfo_getMsgID(name);
                            if (msgIdInt != null) {
                                int msgID = Integer.valueOf(msgIdInt.intValue()).intValue();
                                for (MDMComponent c2 : MDMComponentDetailActivity.this.mComponents) {
                                    if (c2.hasEmType(name) && MDMComponentDetailActivity.isMsgShow) {
                                        if (msgObj.getSimIdx() == MDMComponentDetailActivity.mModemType || ComponentSelectActivity.mSelected_show_both_sim.contains(c2.getName())) {
                                            c2.update(name, msgObj);
                                        } else {
                                            Elog.d(MDMComponentDetailActivity.TAG, "EM Msg has an unSelected sim, [" + c2.getName() + "] msgIDStr = " + msgID + ",Protocol = " + MDMComponentDetailActivity.mModemType + ",getSimIdx = " + msgObj.getSimIdx() + ", msg: " + Long.toHexString((long) msgID));
                                            MDMComponentDetailActivity.this.taskExcute(1);
                                            return;
                                        }
                                    }
                                }
                                break;
                            } else {
                                Elog.e(MDMComponentDetailActivity.TAG, "The msgid is not support, msgName = " + name);
                                return;
                            }
                        } else {
                            Elog.e(MDMComponentDetailActivity.TAG, "msgObj is null");
                            break;
                        }
                        break;
                    default:
                        Elog.d(MDMComponentDetailActivity.TAG, "Unsupported op: " + msg.what);
                        break;
                }
                MDMComponentDetailActivity.this.taskExcute(1);
            }
        };
        if (taskDriver == null) {
            taskDriver = new UpdateTaskDriven();
        }
        new MdmDetailThread(0).start();
    }

    public void taskExcute(int cmd) {
        UpdateTaskDriven updateTaskDriven = taskDriver;
        if (updateTaskDriven == null) {
            Elog.d(TAG, "taskDriver is null, not receiver. return " + cmd);
            return;
        }
        switch (cmd) {
            case 0:
                updateTaskDriven.taskStart();
                return;
            case 1:
                updateTaskDriven.taskDone();
                return;
            case 2:
                updateTaskDriven.taskStop();
                return;
            default:
                return;
        }
    }

    public static MDMContentICD.MDMHeaderICD DecodeICDPacketHeader(ByteBuffer icdPacket) {
        MDMContentICD.MDMHeaderICD headIcd = new MDMContentICD.MDMHeaderICD();
        headIcd.type = icdPacket.get(0) & 15;
        headIcd.version = (icdPacket.get(0) & 240) >> 4;
        switch (headIcd.type) {
            case 0:
                headIcd.type = icdPacket.get(0) & 15;
                headIcd.version = (icdPacket.get(0) & 240) >> 4;
                switch (headIcd.version) {
                    case 1:
                        headIcd.total_size = (icdPacket.get(1) & 255) | ((icdPacket.get(2) & 255) << 8);
                        headIcd.timestamp_type = icdPacket.get(3) & 15;
                        headIcd.protocol_id = (icdPacket.get(3) & 240) >> 4;
                        headIcd.msg_id = (icdPacket.get(4) & 255) | ((icdPacket.get(5) & 255) << 8);
                        break;
                    case 2:
                        headIcd.total_size = (icdPacket.get(1) & 255) | ((icdPacket.get(2) & 255) << 8) | ((icdPacket.get(2) & 255) << 16);
                        headIcd.timestamp_type = icdPacket.get(4) & 15;
                        headIcd.protocol_id = (icdPacket.get(4) & 240) >> 4;
                        headIcd.msg_id = (icdPacket.get(6) & 255) | ((icdPacket.get(7) & 255) << 8);
                        break;
                }
            case 1:
                headIcd.type = icdPacket.get(0) & 15;
                headIcd.version = (icdPacket.get(0) & 240) >> 4;
                switch (headIcd.version) {
                    case 1:
                    case 2:
                        headIcd.total_size = (icdPacket.get(1) & 255) | ((icdPacket.get(2) & 255) << 8) | ((icdPacket.get(2) & 255) << 16);
                        headIcd.timestamp_type = icdPacket.get(4) & 15;
                        headIcd.protocol_id = (icdPacket.get(4) & 240) >> 4;
                        headIcd.msg_id = (icdPacket.get(6) & 255) | ((icdPacket.get(7) & 255) << 8);
                        break;
                }
            default:
                Elog.d(TAG, "type = " + Integer.toHexString(headIcd.type));
                break;
        }
        return headIcd;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d(TAG, "onResume");
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        int phoneIdMain = ModemCategory.getCapabilitySim();
        mSimMccMnc[0] = telephonyManager.getSimOperatorNumericForPhone(phoneIdMain);
        mSimMccMnc[1] = telephonyManager.getSimOperatorNumericForPhone((phoneIdMain + 1) % 2);
        Elog.d(TAG, "Read SIM MCC+MNC(PS1, PS2):" + mSimMccMnc[0] + ", " + mSimMccMnc[1]);
        updateModemTypeStatus();
    }

    public void onStop() {
        super.onStop();
        Elog.d(TAG, "onStop");
    }

    public void onDestroy() {
        Elog.d(TAG, "onDestroy");
        taskExcute(2);
        taskDriver = null;
        if (isMsgShow) {
            isMsgShow = false;
            showDialog(0);
            new MdmDetailThread(1).start();
        }
        super.onDestroy();
    }

    public void onBackPressed() {
        isMsgShow = false;
        showDialog(0);
        new MdmDetailThread(1).start();
    }

    public void onClick(View arg0) {
        if (this.mItemCount == 0) {
            Elog.e(TAG, "mComponents size =0");
        } else if (arg0.getId() == this.mPageUp.getId()) {
            int i = this.mItemCount;
            this.mCurrentItemIndex = ((this.mCurrentItemIndex - 1) + i) % i;
            updateUI();
        } else if (arg0.getId() == this.mPageDown.getId()) {
            this.mCurrentItemIndex = (this.mCurrentItemIndex + 1) % this.mItemCount;
            updateUI();
        }
    }

    public void updateUI() {
        if (this.mComponents.size() == 0) {
            Elog.e(TAG, "mComponents size =0");
            return;
        }
        MDMComponent mDMComponent = this.mCurrentItem;
        if (mDMComponent != null) {
            mDMComponent.removeView();
        }
        MDMComponent mDMComponent2 = this.mComponents.get(this.mCurrentItemIndex);
        this.mCurrentItem = mDMComponent2;
        if (mDMComponent2 instanceof FTNetworkInfo) {
            Elog.d(TAG, "Selected Sim: " + getIntent().getIntExtra("mSelectSim", -1));
            ((FTNetworkInfo) this.mCurrentItem).setCurrentSimID(getIntent().getIntExtra("mSelectSim", -1));
        }
        TextView textView = this.mTitle;
        textView.setText(this.mCurrentItem.getName() + "(SimSlot=" + (mSimType + 1) + ",ModemProtocol=" + mModemType + ")");
        View view = this.mCurrentItem.getView();
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        this.mInfoFrameLayout.removeAllViews();
        this.mInfoFrameLayout.addView(view);
        Elog.d(TAG, "updateUI done!");
    }

    public void unSubscribeTrap() {
        Elog.d(TAG, "Before unSubscribeTrap..");
        Long[] array = (Long[]) ComponentSelectActivity.mCheckedEmTypes.toArray(new Long[ComponentSelectActivity.mCheckedEmTypes.size()]);
        Elog.d(TAG, "unSubscribeTrap msg id = " + Arrays.toString(array));
        ComponentSelectActivity.getCmdProxy().onUnsubscribeMultiTrap(ComponentSelectActivity.m_sid, TRAP_TYPE.TRAP_TYPE_EM, ComponentSelectActivity.toPrimitives(array));
        Long[] array2 = (Long[]) ComponentSelectActivity.mCheckedIcdRecordTypes.toArray(new Long[ComponentSelectActivity.mCheckedIcdRecordTypes.size()]);
        Elog.d(TAG, "unSubscribeTrap icd record msg id = " + Arrays.toString(array2));
        ComponentSelectActivity.getCmdProxy().onUnsubscribeMultiTrap(ComponentSelectActivity.m_sid, TRAP_TYPE.TRAP_TYPE_ICD_RECORD, ComponentSelectActivity.toPrimitives(array2));
        Long[] array3 = (Long[]) ComponentSelectActivity.mCheckedIcdEventTypes.toArray(new Long[ComponentSelectActivity.mCheckedIcdEventTypes.size()]);
        Elog.d(TAG, "unSubscribeTrap icd event msg id = " + Arrays.toString(array3));
        ComponentSelectActivity.getCmdProxy().onUnsubscribeMultiTrap(ComponentSelectActivity.m_sid, TRAP_TYPE.TRAP_TYPE_ICD_EVENT, ComponentSelectActivity.toPrimitives(array3));
        this.mHandler.sendEmptyMessage(2);
    }

    /* access modifiers changed from: private */
    public void initValues() {
        WindowManager window = (WindowManager) getSystemService("window");
        this.screenWidth = window.getDefaultDisplay().getWidth();
        this.screenHeight = window.getDefaultDisplay().getHeight() - 200;
        this.content = findViewById(R.id.content_mdm);
        View findViewById = findViewById(R.id.menu_mdm);
        this.menu = findViewById;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById.getLayoutParams();
        this.menuParams = layoutParams;
        layoutParams.width = this.screenWidth - this.menuPadding;
        int i = -this.menuParams.width;
        this.leftEdge = i;
        this.menuParams.leftMargin = i;
        this.content.getLayoutParams().width = this.screenWidth;
        this.content.getLayoutParams().height = this.screenHeight;
        ArrayList<String> arrayList = this.checked;
        if (arrayList != null) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                String s = it.next();
                Iterator<MDMComponent> it2 = this.allComponents.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    MDMComponent c = it2.next();
                    if (s.equals(c.getName())) {
                        this.mComponents.add(c);
                        break;
                    }
                }
            }
        } else {
            Elog.d(TAG, "checked is null");
        }
        this.mItemCount = this.mComponents.size();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case 0:
                Elog.d(TAG, "Wait unSubscribe message..");
                ProgressDialog progressDialog = new ProgressDialog(this);
                this.mProgressDialog = progressDialog;
                progressDialog.setTitle("Waiting");
                this.mProgressDialog.setMessage("Wait unSubscribe message..");
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setIndeterminate(true);
                return this.mProgressDialog;
            case 1:
                Elog.d(TAG, "MDM error, need to finish self!");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("ERROR Warning");
                builder.setCancelable(false);
                builder.setMessage("MDM occurs error, MDM occurs error, please try re-enter");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MDMComponentDetailActivity.this.finish();
                    }
                });
                return builder.create();
            default:
                return super.onCreateDialog(id);
        }
    }

    private void updateModemTypeStatus() {
        Elog.d(TAG, "updateModemTypeStatus");
        int capabilitySims = ModemCategory.getCapabilitySim();
        if (capabilitySims == 0) {
            int i = mSimType;
            if (i == 0) {
                mModemType = 1;
            } else if (i == 1) {
                mModemType = 2;
            } else {
                mModemType = 3;
            }
        } else if (capabilitySims == 1) {
            int i2 = mSimType;
            if (i2 == 0) {
                mModemType = 2;
            } else if (i2 == 1) {
                mModemType = 1;
            } else {
                mModemType = 3;
            }
        } else if (capabilitySims == 2) {
            if (FeatureSupport.is93ModemAndAbove()) {
                int i3 = mSimType;
                if (i3 == 0) {
                    mModemType = 2;
                } else if (i3 == 1) {
                    mModemType = 3;
                } else {
                    mModemType = 1;
                }
            } else {
                int i4 = mSimType;
                if (i4 == 0) {
                    mModemType = 3;
                } else if (i4 == 1) {
                    mModemType = 2;
                } else {
                    mModemType = 1;
                }
            }
        }
        updateUI();
        Elog.d(TAG, "mSimType = " + mSimType + ",mModemType = " + mModemType);
    }

    public class MdmDetailThread extends Thread {
        private int action;

        public MdmDetailThread(int action2) {
            this.action = action2;
        }

        public void run() {
            switch (this.action) {
                case 0:
                    MDMComponentDetailActivity.this.initValues();
                    if (ComponentSelectActivity.checkMonitorAlive()) {
                        try {
                            ComponentSelectActivity.getCmdProxy().onEnableTrap(ComponentSelectActivity.m_sid);
                            MDMComponentDetailActivity.this.mHandler.sendEmptyMessage(3);
                            return;
                        } catch (Exception e) {
                            Elog.e(MDMComponentDetailActivity.TAG, "ComponentSelectActivity.m_cmdProxy.onEnableTrap failed! " + e.toString());
                            return;
                        }
                    } else {
                        Elog.d(MDMComponentDetailActivity.TAG, "[InitTask] m_cmdProxy is null, md_monitor is stopped, finish");
                        MDMComponentDetailActivity.this.mHandler.sendEmptyMessage(4);
                        return;
                    }
                case 1:
                    boolean unused = MDMComponentDetailActivity.this.unSubscribeMdmMsg();
                    MDMComponentDetailActivity.this.mHandler.sendEmptyMessage(2);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean unSubscribeMdmMsg() {
        if (!ComponentSelectActivity.checkMonitorAlive()) {
            Elog.d(TAG, "[unSubscribeTask] m_cmdProxy is null, md_monitor is stopped, finish");
            finish();
            return true;
        }
        unSubscribeTrap();
        return true;
    }
}
