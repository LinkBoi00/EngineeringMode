package com.mediatek.engineermode.rfdesense;

public class RfDesenseServiceData {
    private int band;
    private int bw = -1;
    private int channel;
    private int power;
    private String rat;
    private int rb = -1;
    private int time;

    public RfDesenseServiceData() {
    }

    public RfDesenseServiceData(int channel2, int power2, int band2, int time2) {
        this.channel = channel2;
        this.power = power2;
        this.band = band2;
        this.time = time2;
    }

    public String getRat() {
        return this.rat;
    }

    public void setRat(String rat2) {
        this.rat = rat2;
    }

    public int getBand() {
        return this.band;
    }

    public void setBand(int band2) {
        this.band = band2;
    }

    public int getChannel() {
        return this.channel;
    }

    public void setChannel(int channel2) {
        this.channel = channel2;
    }

    public int getPower() {
        return this.power;
    }

    public void setPower(int power2) {
        this.power = power2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public int getRb() {
        return this.rb;
    }

    public void setRb(int rb2) {
        this.rb = rb2;
    }

    public int getBw() {
        return this.bw;
    }

    public void setBw(int bw2) {
        this.bw = bw2;
    }

    public String toString() {
        return "RfDesenseServiceData{rat='" + this.rat + '\'' + ", channel=" + this.channel + ", power=" + this.power + ", band=" + this.band + ", time=" + this.time + '}';
    }
}
