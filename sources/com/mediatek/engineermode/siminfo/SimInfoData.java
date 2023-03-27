package com.mediatek.engineermode.siminfo;

public class SimInfoData {
    String[] EF_Path;
    int EF_id;
    int Family;
    String name;
    boolean need_to_check;
    String num;
    String type;

    public SimInfoData() {
    }

    public SimInfoData(String num2, String name2, int EF_id2, String[] EF_Path2, int Family2, String type2, boolean need_to_check2) {
        this.num = num2;
        this.name = name2;
        this.EF_id = EF_id2;
        this.EF_Path = EF_Path2;
        this.Family = Family2;
        this.type = type2;
        this.need_to_check = need_to_check2;
    }
}
