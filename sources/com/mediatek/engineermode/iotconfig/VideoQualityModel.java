package com.mediatek.engineermode.iotconfig;

public class VideoQualityModel {
    private String format;
    private String level;
    private String name;
    private String profile;

    public VideoQualityModel(String name2, String format2, String profile2, String level2) {
        this.format = format2;
        this.level = level2;
        this.profile = profile2;
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format2) {
        this.format = format2;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level2) {
        this.level = level2;
    }

    public String getProfile() {
        return this.profile;
    }

    public void setProfile(String profile2) {
        this.profile = profile2;
    }
}
