package com.mediatek.engineermode.otaairplanemode;

import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;

class MdmBaseComponent {
    private static final String TAG = "MdmBaseComponent";
    private String[] emComponentName = null;

    MdmBaseComponent() {
    }

    public String[] getEmComponentName() {
        return this.emComponentName;
    }

    public void setEmComponentName(String[] emComponentName2) {
        this.emComponentName = emComponentName2;
    }

    public void update(String name, Msg data) {
        Elog.d(TAG, "update = " + name);
    }

    public boolean hasEmType(String type) {
        String[] types = getEmComponentName();
        if (types == null) {
            return false;
        }
        for (String equals : types) {
            if (equals.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
