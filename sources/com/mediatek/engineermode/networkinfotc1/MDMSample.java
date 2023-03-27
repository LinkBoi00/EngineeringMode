package com.mediatek.engineermode.networkinfotc1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.mdmcomponent.MDMContent;
import com.mediatek.engineermode.networkinfotc1.MDMCoreOperation;
import com.mediatek.mdml.Msg;
import java.util.ArrayList;
import java.util.List;

public class MDMSample extends Activity implements View.OnClickListener, MDMCoreOperation.IMDMSeiviceInterface {
    private static final int MSG_UPDATE_UI = 0;
    private static final String TAG = "MDMSample";
    private String[] SubscribeMsgIdName = {MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND, MDMContent.MSG_ID_EM_EL1_STATUS_IND, "EM_EL1_STATUS_DL_INFO"};
    private List<MdmBaseComponent> componentsArray = new ArrayList();
    private String info = "";
    private TextView mResult;
    private int mSimTypeToShow = 0;
    private Button mStart_listen;
    private Button mStop_listen;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdm_sample);
        bindViews();
        this.mSimTypeToShow = 0;
        MdmBaseComponent components = new MdmBaseComponent();
        components.setEmComponentName(this.SubscribeMsgIdName);
        this.componentsArray.add(components);
        MDMCoreOperation.getInstance().mdmParametersSeting(this.componentsArray, this.mSimTypeToShow);
        MDMCoreOperation.getInstance().setOnMDMChangedListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_listen:
                MDMCoreOperation.getInstance().mdmInitialize(this);
                return;
            case R.id.stop_listen:
                MDMCoreOperation.getInstance().mdmlUnSubscribe();
                return;
            default:
                return;
        }
    }

    private void bindViews() {
        this.mStart_listen = (Button) findViewById(R.id.start_listen);
        this.mStop_listen = (Button) findViewById(R.id.stop_listen);
        this.mResult = (TextView) findViewById(R.id.result);
        this.mStart_listen.setOnClickListener(this);
        this.mStop_listen.setOnClickListener(this);
    }

    public void onUpdateMDMStatus(int msg_id) {
        switch (msg_id) {
            case 0:
                Elog.d(TAG, "MDM Service init done");
                MDMCoreOperation.getInstance().mdmlSubscribe();
                return;
            case 1:
                Elog.d(TAG, "Subscribe message id done");
                MDMCoreOperation.getInstance().mdmlEnableSubscribe();
                return;
            case 4:
                Elog.d(TAG, "UnSubscribe message id done");
                MDMCoreOperation.getInstance().mdmlClosing();
                return;
            default:
                return;
        }
    }

    public void onUpdateMDMData(String name, Msg data) {
        Elog.d(TAG, "update = " + name);
        if (name.equals(MDMContent.MSG_ID_EM_ERRC_MOB_MEAS_INTRARAT_INFO_IND)) {
            this.info = "";
            int rsrp = MDMCoreOperation.getInstance().getFieldValue(data, "serving_info." + "rsrp", true);
            int rsrq = MDMCoreOperation.getInstance().getFieldValue(data, "serving_info." + "rsrq", true);
            int sinr = MDMCoreOperation.getInstance().getFieldValue(data, "serving_info." + "rs_snr_in_qdb", true);
            int earfcn = MDMCoreOperation.getInstance().getFieldValue(data, "serving_info." + "earfcn");
            int pci = MDMCoreOperation.getInstance().getFieldValue(data, "serving_info." + "pci");
            int band = MDMCoreOperation.getInstance().getFieldValue(data, "serving_info." + "serv_lte_band");
            int dlBandwidth = MDMCoreOperation.getInstance().getFieldValue(data, "serving_info." + MDMContent.DL_BANDWIDTH, true);
            this.info += "rsrp: " + rsrp + "\n";
            this.info += "rsrq: " + rsrq + "\n";
            this.info += "sinr: " + sinr + "\n";
            this.info += "earfcn: " + earfcn + "\n";
            this.info += "pci: " + pci + "\n";
            this.info += "band: " + band + "\n";
            this.info += "dlBandwidth: " + dlBandwidth + "\n";
            this.info += "ulBandwidth: " + MDMCoreOperation.getInstance().getFieldValue(data, "serving_info." + MDMContent.UL_BANDWIDTH, true) + "\n";
            Elog.d(TAG, "info = " + this.info);
            this.mResult.setText(this.info);
        }
    }
}
