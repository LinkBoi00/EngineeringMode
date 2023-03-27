package com.mediatek.engineermode.rfdesense;

public class RfDesenseRatInfo {
    public final int DEFAULT_BAND_WIDTH = 3;
    public final int DEFAULT_VRB_LENGTH = 1;
    private Boolean RatCheckState;
    private String RatCmdAntSwitch;
    private int RatCmdLteBw;
    private int RatCmdLteRb;
    private String RatCmdPowerRead;
    private String RatCmdStart;
    private String RatCmdStop;
    private String RatCmdSwitch;
    private String RatName;
    private String RatPowerSet;
    private Boolean RatSendState;
    private int RatTxtimes;
    private String Ratband;

    public String getRatName() {
        return this.RatName;
    }

    public void setRatName(String mRatname) {
        if (mRatname != null) {
            this.RatName = mRatname;
        }
    }

    public Boolean getRatCheckState() {
        return this.RatCheckState;
    }

    public void setRatCheckState(Boolean mRatCheckState) {
        this.RatCheckState = mRatCheckState;
    }

    public Boolean getRatSendState() {
        return this.RatSendState;
    }

    public void setRatSendState(Boolean mRatSendState) {
        this.RatSendState = mRatSendState;
    }

    public String getRatCmdStart() {
        return this.RatCmdStart;
    }

    public String getRatCmdAntSwitch() {
        return this.RatCmdAntSwitch;
    }

    public void setRatCmdAntSwitch(String ratCmdAntSwitch) {
        this.RatCmdAntSwitch = ratCmdAntSwitch;
    }

    public void setRatCmdStart(String mRatCmdStart) {
        if (mRatCmdStart != null) {
            this.RatCmdStart = mRatCmdStart;
        }
    }

    public String getRatCmdStop() {
        return this.RatCmdStop;
    }

    public void setRatCmdStop(String mRatCmdStop) {
        if (mRatCmdStop != null) {
            this.RatCmdStop = mRatCmdStop;
        }
    }

    public String getRatCmdSwitch() {
        return this.RatCmdSwitch;
    }

    public void setRatCmdSwitch(String mRatCmdSwitch) {
        this.RatCmdSwitch = mRatCmdSwitch;
    }

    public String getRatCmdPowerRead() {
        return this.RatCmdPowerRead;
    }

    public void setRatCmdLteBwRb(int ratCmdLteBw, int ratCmdLteRb) {
        if (ratCmdLteBw == -1) {
            this.RatCmdLteBw = 3;
        } else {
            this.RatCmdLteBw = ratCmdLteBw;
        }
        if (ratCmdLteRb == -1) {
            this.RatCmdLteRb = 1;
        } else {
            this.RatCmdLteRb = ratCmdLteRb;
        }
    }

    public void setRatCmdStart(String rat, int channel, int power, int band) {
        String command = "";
        if (rat.equals(RfDesenseTxTest.mRatName[0])) {
            command = "AT+ERFTX=2,1," + channel + "," + 4100 + "," + band + "," + 0 + "," + power + "," + 0;
        } else if (rat.equals(RfDesenseTxTest.mRatName[1])) {
            command = "AT+ERFTX=0,0," + band + "," + channel + "," + power;
        } else if (rat.equals(RfDesenseTxTest.mRatName[2])) {
            command = "AT+ERFTX=0,0," + band + "," + channel + "," + power;
        } else if (rat.equals(RfDesenseTxTest.mRatName[3])) {
            command = "AT+ERFTX=6,0,2," + band + "," + this.RatCmdLteBw + "," + channel + ",1,0,0,0," + this.RatCmdLteRb + ",0," + power;
        } else if (rat.equals(RfDesenseTxTest.mRatName[4])) {
            command = "AT+ERFTX=6,0,2," + band + "," + this.RatCmdLteBw + "," + channel + ",0,0,0,0," + this.RatCmdLteRb + ",0," + power;
        } else if (rat.equals(RfDesenseTxTest.mRatName[5])) {
            command = "AT+ERFTX=13,4," + channel + "," + band + "," + power;
        } else if (rat.equals(RfDesenseTxTest.mRatName[6])) {
            command = "AT+ECRFTX=1," + channel + "," + band + "," + power + ",0";
        }
        this.RatCmdStart = command;
    }

    public void setRatPowerRead(String mRatCmdPowerRead) {
        this.RatCmdPowerRead = mRatCmdPowerRead;
    }

    public String getRatband() {
        return this.Ratband;
    }

    public void setRatband(String ratband) {
        this.Ratband = ratband;
    }

    public String getRatPowerSet() {
        return this.RatPowerSet;
    }

    public void setRatPowerSet(String ratPowerSet) {
        this.RatPowerSet = ratPowerSet;
    }

    public int getRatTxtimes() {
        return this.RatTxtimes;
    }

    public void setRatTxtimes(int ratTxtimes) {
        this.RatTxtimes = ratTxtimes;
    }
}
