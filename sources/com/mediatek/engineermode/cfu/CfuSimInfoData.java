package com.mediatek.engineermode.cfu;

public class CfuSimInfoData {
    String[] EF_Path;
    int EF_id;
    int Family;
    String name;
    String type;

    public CfuSimInfoData(String name2, int EF_id2, String[] EF_Path2, int Family2, String type2) {
        this.name = name2;
        this.EF_id = EF_id2;
        this.EF_Path = EF_Path2;
        this.Family = Family2;
        this.type = type2;
    }
}
