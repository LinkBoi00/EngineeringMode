package com.mediatek.engineermode.siminfo;

import android.app.Activity;

class SimInfoUpdate extends NormalTableComponent {
    public SimInfoUpdate(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"CDR-PER-512", "CDR-PER-514", "CDR-PER-516", "CDR-PER-518", "CDR-PER-520", "CDR-PER-522", "CDR-PER-524", "CDR-PER-526", "CDR-PER-528", "CDR-PER-530", "CDR-PER-536", "CDR-PER-538", "CDR-PER-540", "CDR-PER-542", "CDR-PER-544"};
    }

    /* access modifiers changed from: package-private */
    public void update(int type, String data) {
        if (type == 0) {
            clearData();
        } else if (type == 1) {
            addData(data);
        } else {
            notifyDataSetChanged();
        }
    }

    public int compareTo(NormalTableComponent another) {
        return 0;
    }
}
