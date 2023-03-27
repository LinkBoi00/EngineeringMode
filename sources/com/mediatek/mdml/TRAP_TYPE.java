package com.mediatek.mdml;

public enum TRAP_TYPE {
    TRAP_TYPE_UNDEFINED,
    TRAP_TYPE_DISCARDINFO,
    TRAP_TYPE_OTA,
    TRAP_TYPE_EM,
    TRAP_TYPE_PHASEOUT_1,
    TRAP_TYPE_PSTIME,
    TRAP_TYPE_PHASEOUT_2,
    TRAP_TYPE_ICD_RECORD,
    TRAP_TYPE_ICD_EVENT,
    TRAP_TYPE_SIZE;
    
    private static final TrapMap[] trapTable = null;

    static {
        TRAP_TYPE trap_type;
        TRAP_TYPE trap_type2;
        TRAP_TYPE trap_type3;
        TRAP_TYPE trap_type4;
        TRAP_TYPE trap_type5;
        TRAP_TYPE trap_type6;
        TRAP_TYPE trap_type7;
        trapTable = new TrapMap[]{new TrapMap(trap_type, "OTA"), new TrapMap(trap_type2, "EM"), new TrapMap(trap_type3, "PHASEOUT_1"), new TrapMap(trap_type4, "PSTIME"), new TrapMap(trap_type5, "PHASEOUT_2"), new TrapMap(trap_type6, "ICD_RECORD"), new TrapMap(trap_type7, "ICD_EVENT")};
    }

    public int GetValue() {
        return ordinal();
    }

    private static class TrapMap {
        String m_szType;
        TRAP_TYPE m_type;

        TrapMap(TRAP_TYPE type, String szType) {
            this.m_type = type;
            this.m_szType = szType;
        }
    }

    public static String ToTrapStr(TRAP_TYPE type) {
        int i = 0;
        while (true) {
            TrapMap[] trapMapArr = trapTable;
            if (i >= trapMapArr.length) {
                return null;
            }
            if (type.equals(trapMapArr[i].m_type)) {
                return trapMapArr[i].m_szType;
            }
            i++;
        }
    }
}
