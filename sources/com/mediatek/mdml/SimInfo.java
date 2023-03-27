package com.mediatek.mdml;

import java.util.HashMap;

class SimInfo {
    private int currentSimIdx;
    private HashMap<Integer, Integer> simIdxMap = new HashMap<>();

    SimInfo() {
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.simIdxMap.clear();
        this.currentSimIdx = -1;
    }

    /* access modifiers changed from: package-private */
    public boolean readTag(String tag) {
        int lastIdx = tag.lastIndexOf("_INFO");
        if (!tag.substring(0, "SIM".length()).equals("SIM") || lastIdx <= 0) {
            this.currentSimIdx = -1;
            return false;
        }
        this.currentSimIdx = Integer.parseInt(tag.substring("SIM".length(), lastIdx));
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean readLine(String line) {
        this.simIdxMap.put(Integer.valueOf(Integer.parseInt(line.trim())), Integer.valueOf(this.currentSimIdx));
        return true;
    }

    /* access modifiers changed from: package-private */
    public Integer getSimIdx(Integer id) {
        return this.simIdxMap.get(id);
    }
}
