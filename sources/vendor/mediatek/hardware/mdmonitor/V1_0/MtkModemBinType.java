package vendor.mediatek.hardware.mdmonitor.V1_0;

import java.util.ArrayList;

public final class MtkModemBinType {
    public static final int MTK_MODEM_BIN_TYPE_ALL_ICD_JSON = 1;
    public static final int MTK_MODEM_BIN_TYPE_LAYOUT_DESC = 0;

    public static final String toString(int o) {
        if (o == 0) {
            return "MTK_MODEM_BIN_TYPE_LAYOUT_DESC";
        }
        if (o == 1) {
            return "MTK_MODEM_BIN_TYPE_ALL_ICD_JSON";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("MTK_MODEM_BIN_TYPE_LAYOUT_DESC");
        if ((o & 1) == 1) {
            list.add("MTK_MODEM_BIN_TYPE_ALL_ICD_JSON");
            flipped = 0 | 1;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
