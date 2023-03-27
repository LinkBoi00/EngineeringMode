package com.mediatek.mdml;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

class GlobalID {
    private HashMap<Integer, String> idNameMap = new HashMap<>();
    private HashMap<String, Integer> nameIdMap = new HashMap<>();

    GlobalID() {
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.idNameMap.clear();
        this.nameIdMap.clear();
    }

    /* access modifiers changed from: package-private */
    public boolean readLine(String line) {
        String[] s = line.split("[\\(\\)\\s]+");
        if (s.length != 2) {
            return false;
        }
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].trim();
        }
        String name = s[0];
        int id = Integer.parseInt(s[1]);
        this.idNameMap.put(Integer.valueOf(id), name);
        this.nameIdMap.put(name, Integer.valueOf(id));
        return true;
    }

    public String[] getList() {
        Set<String> mapKeySet = this.nameIdMap.keySet();
        String[] keyArray = (String[]) mapKeySet.toArray(new String[mapKeySet.size()]);
        Arrays.sort(keyArray);
        return keyArray;
    }

    /* access modifiers changed from: package-private */
    public String getName(Integer value) {
        return this.idNameMap.get(value);
    }

    /* access modifiers changed from: package-private */
    public Integer getValue(String name) {
        return this.nameIdMap.get(name);
    }
}
