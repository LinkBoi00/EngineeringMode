package com.mediatek.engineermode.iotconfig;

public class VideoProfileModel {
    private String Iinterval;
    private String Profile;
    private String framerate;
    private String height;
    private boolean isSelected;
    private String level;
    private String maxBitRate;
    private String minBitRate;
    private String name;
    private String quality;
    private String width;

    public VideoProfileModel(String name2, String profile, String level2, String width2, String height2, String framerate2, String iinterval, String minbitrate, String maxBitRate2, boolean isSelected2) {
        this.name = name2;
        this.Profile = profile;
        this.level = level2;
        this.width = width2;
        this.height = height2;
        this.framerate = framerate2;
        this.Iinterval = iinterval;
        this.minBitRate = minbitrate;
        this.maxBitRate = maxBitRate2;
        this.isSelected = isSelected2;
        this.quality = "";
    }

    public VideoProfileModel(String name2, String profile, String level2, String width2, String height2, String framerate2, String iinterval, String minbitrate, String maxBitRate2) {
        this.name = name2;
        this.Profile = profile;
        this.level = level2;
        this.width = width2;
        this.height = height2;
        this.framerate = framerate2;
        this.Iinterval = iinterval;
        this.minBitRate = minbitrate;
        this.maxBitRate = maxBitRate2;
        this.isSelected = false;
        this.quality = "";
    }

    public VideoProfileModel() {
        this.isSelected = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getProfile() {
        return this.Profile;
    }

    public void setProfile(String profile) {
        this.Profile = profile;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level2) {
        this.level = level2;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width2) {
        this.width = width2;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height2) {
        this.height = height2;
    }

    public String getFramerate() {
        return this.framerate;
    }

    public void setFramerate(String framerate2) {
        this.framerate = framerate2;
    }

    public String getIinterval() {
        return this.Iinterval;
    }

    public void setIinterval(String iinterval) {
        this.Iinterval = iinterval;
    }

    public String getMinBitRate() {
        return this.minBitRate;
    }

    public void setMinBitRate(String minbitrate) {
        this.minBitRate = minbitrate;
    }

    public String getMaxBitRate() {
        return this.maxBitRate;
    }

    public void setMaxBitRate(String maxBitRate2) {
        this.maxBitRate = maxBitRate2;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean isSelected2) {
        this.isSelected = isSelected2;
    }

    public String getQuality() {
        return this.quality;
    }

    public void setQuality(String quality2) {
        this.quality = quality2;
    }
}
