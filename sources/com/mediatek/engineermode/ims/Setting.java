package com.mediatek.engineermode.ims;

import java.io.Serializable;
import java.util.ArrayList;

public class Setting implements Serializable {
    public String defaultValue;
    public ArrayList<String> entries = new ArrayList<>();
    public String label;
    public String suffix = "";
    public int type = 0;
    public ArrayList<Integer> values = new ArrayList<>();

    public void setLabel(String label2) {
        this.label = label2;
    }

    public String getLabel() {
        return this.label;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getType() {
        return this.type;
    }

    public void setEntries(ArrayList<String> entries2) {
        this.entries = entries2;
    }

    public ArrayList<String> getEntries() {
        return this.entries;
    }

    public void setValues(ArrayList<Integer> values2) {
        this.values = values2;
    }

    public ArrayList<Integer> getValues() {
        return this.values;
    }

    public void setDefaultValue(String defaultValue2) {
        this.defaultValue = defaultValue2;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setSuffix(String suffix2) {
        this.suffix = suffix2;
    }

    public String getSuffix() {
        return this.suffix;
    }
}
