package com.mediatek.engineermode.iotconfig;

public class XCAPModel {
    private boolean configed = false;
    private String name;
    private boolean selected = false;
    private String type;
    private String value = "";

    public String getValue() {
        if (this.type.equals(IotConfigConstant.BOOLEANTYPE)) {
            return this.selected ? "1" : "0";
        }
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public XCAPModel(String name2, String value2, String type2) {
        this.name = name2;
        this.type = type2;
        this.value = value2;
    }

    public XCAPModel(String name2, String type2) {
        this.name = name2;
        this.type = type2;
        this.configed = false;
    }

    public XCAPModel(String name2, boolean selected2, String type2) {
        this.name = name2;
        this.type = type2;
        this.selected = selected2;
        this.configed = true;
    }

    public XCAPModel(String name2, boolean isConfig, boolean selected2, String type2) {
        this.name = name2;
        this.type = type2;
        this.configed = isConfig;
        this.selected = selected2;
    }

    public String getName() {
        return this.name;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected2) {
        this.selected = selected2;
        this.value = selected2 ? "1" : "0";
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        return "name=" + getName() + ";value=" + getValue() + ";type=" + getType();
    }

    public boolean isConfiged() {
        return this.configed;
    }

    public void setConfiged(boolean isConfig) {
        this.configed = isConfig;
    }
}
