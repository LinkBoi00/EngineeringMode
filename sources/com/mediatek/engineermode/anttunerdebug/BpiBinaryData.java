package com.mediatek.engineermode.anttunerdebug;

/* compiled from: AntTunerDebugBPI */
class BpiBinaryData {
    private String name;
    private boolean selected = false;
    private String value = "";

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public BpiBinaryData(String name2, Object value2) {
        this.name = name2;
        if (value2 != null) {
            this.value = value2.toString();
        }
    }

    public BpiBinaryData(String name2, Object value2, boolean selected2) {
        this.name = name2;
        if (value2 != null) {
            this.value = value2.toString();
        }
        this.selected = selected2;
    }

    public BpiBinaryData(String name2) {
        this.name = name2;
        this.value = "";
    }

    public String getName() {
        return this.name;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected2) {
        this.selected = selected2;
    }

    public String toString() {
        return getName() + "," + getValue() + "," + isSelected();
    }
}
