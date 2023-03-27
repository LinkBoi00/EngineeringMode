package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class EmacInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_EL2_OV_STATUS_IND, MDMContent.MSG_ID_EM_EMAC_CONFIG_REPORT_IND};
    int cellId = 0;
    int dl_sps_configured = 0;
    int drx_long_cycle = 0;
    int drx_short_cycle = 0;
    int earfcn = 0;
    HashMap<Integer, String> mMappingInfo = new HashMap<Integer, String>() {
        {
            put(0, "DISABLED");
            put(1, "NORMAL");
            put(2, "ENHANCED_FDD");
            put(3, " ");
        }
    };
    int sr_periodicity = 0;
    int tti_bundling = 0;
    String tti_bundling_s = "";
    int ul_sps_configured = 0;

    public EmacInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "EMAC Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "5. LTE EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"DRX Long Cycle", "DRX Short Cycle", "TTI Bundling", "DL SPS Configured", "UL SPS Configured", "SR Periodicity", "EARFCN", "Cell ID"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        if (name.equals(MDMContent.MSG_ID_EM_EMAC_CONFIG_REPORT_IND)) {
            int fieldValue = getFieldValue(data, "tti_bundling");
            this.tti_bundling = fieldValue;
            if (fieldValue < 0 || fieldValue > 2) {
                this.tti_bundling_s = this.mMappingInfo.get(3);
            } else {
                this.tti_bundling_s = this.mMappingInfo.get(Integer.valueOf(fieldValue));
            }
            this.dl_sps_configured = getFieldValue(data, "dl_sps_configured");
            this.ul_sps_configured = getFieldValue(data, "ul_sps_configured");
        } else if (name.equals(MDMContent.MSG_ID_EM_EL2_OV_STATUS_IND)) {
            this.drx_long_cycle = getFieldValue(data, "emac_stats.drx_long_cycle");
            this.drx_short_cycle = getFieldValue(data, "emac_stats.drx_short_cycle");
            this.sr_periodicity = getFieldValue(data, "emac_stats.sr_periodicity");
            this.earfcn = getFieldValue(data, "emac_stats.earfcn");
            this.cellId = getFieldValue(data, "emac_stats.phys_cell_id");
        }
        clearData();
        addData(Integer.valueOf(this.drx_long_cycle), Integer.valueOf(this.drx_short_cycle), this.tti_bundling_s, Integer.valueOf(this.dl_sps_configured), Integer.valueOf(this.ul_sps_configured), Integer.valueOf(this.sr_periodicity), Integer.valueOf(this.earfcn), Integer.valueOf(this.cellId));
    }
}
