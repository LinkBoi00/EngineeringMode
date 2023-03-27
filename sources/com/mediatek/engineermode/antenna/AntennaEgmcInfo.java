package com.mediatek.engineermode.antenna;

public class AntennaEgmcInfo {
    private boolean cssFollowPcc;
    private boolean forceRx;
    private boolean[] pCell2Rx = new boolean[2];
    private boolean[] pCell4Rx = new boolean[4];
    private boolean[] sCell2Rx = new boolean[2];
    private boolean[] sCell4Rx = new boolean[4];
    private boolean showStatus = false;
    private int sim = 0;

    public static int booleanToInt(boolean[] data) {
        int intValue = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i]) {
                intValue |= 1 << i;
            } else {
                intValue &= ~(1 << i);
            }
        }
        return intValue;
    }

    public static boolean[] intToBoolean(int data, int length) {
        boolean[] boolResults = new boolean[length];
        char[] results = Integer.toBinaryString(data).toCharArray();
        for (int i = 0; i < length - results.length; i++) {
            boolResults[(length - 1) - i] = false;
        }
        for (int i2 = 0; i2 < results.length; i2++) {
            boolean z = true;
            int length2 = (results.length - 1) - i2;
            if (results[i2] != '1') {
                z = false;
            }
            boolResults[length2] = z;
        }
        return boolResults;
    }

    public boolean isShow() {
        return this.showStatus;
    }

    public void show() {
        this.showStatus = true;
    }

    public void hide() {
        this.showStatus = false;
    }

    public AntennaEgmcInfo(int forceRx2, int cssFollowPcc2, int pCell2Rx2, int pCell4Rx2, int sCell2Rx2, int sCell4Rx2) {
        setForceRx(forceRx2);
        setCssFollowPcc(cssFollowPcc2);
        setPCell2Rx(pCell2Rx2);
        setPCell4Rx(pCell4Rx2);
        setSCell2Rx(sCell2Rx2);
        setSCell4Rx(sCell4Rx2);
    }

    public AntennaEgmcInfo(int[] egmcInfoArray) {
        setForceRx(egmcInfoArray[0]);
        setCssFollowPcc(egmcInfoArray[1]);
        setPCell2Rx(egmcInfoArray[2]);
        setPCell4Rx(egmcInfoArray[3]);
        setSCell2Rx(egmcInfoArray[4]);
        setSCell4Rx(egmcInfoArray[5]);
    }

    public void updateAntennaEgmcInfo(int[] egmcInfoArray) {
        setForceRx(egmcInfoArray[0]);
        setCssFollowPcc(egmcInfoArray[1]);
        setPCell2Rx(egmcInfoArray[2]);
        setPCell4Rx(egmcInfoArray[3]);
        setSCell2Rx(egmcInfoArray[4]);
        setSCell4Rx(egmcInfoArray[5]);
    }

    public void updateAntennaEgmcInfo(int forceRx2, int cssFollowPcc2, int pCell2Rx2, int pCell4Rx2, int sCell2Rx2, int sCell4Rx2) {
        setForceRx(forceRx2);
        setCssFollowPcc(cssFollowPcc2);
        setPCell2Rx(pCell2Rx2);
        setPCell4Rx(pCell4Rx2);
        setSCell2Rx(sCell2Rx2);
        setSCell4Rx(sCell4Rx2);
        hide();
    }

    public AntennaEgmcInfo() {
        setForceRx(0);
        setCssFollowPcc(0);
        setPCell2Rx(0);
        setPCell4Rx(0);
        setSCell2Rx(0);
        setSCell4Rx(0);
        hide();
    }

    public void CloseForceRxFor() {
        setForceRx(false);
        setCssFollowPcc(false);
        setPCell2Rx(0);
        setPCell4Rx(0);
        setSCell2Rx(0);
        setSCell4Rx(0);
    }

    public boolean getForceRx() {
        return this.forceRx;
    }

    public int getIntForceRx() {
        if (this.forceRx) {
            return 1;
        }
        return 0;
    }

    public void setForceRx(boolean forceRx2) {
        this.forceRx = forceRx2;
    }

    public void setForceRx(int forceRx2) {
        this.forceRx = forceRx2 != 0;
    }

    public boolean getCssFollowPcc() {
        return this.cssFollowPcc;
    }

    public int getIntCssFollowPcc() {
        if (this.cssFollowPcc) {
            return 1;
        }
        return 0;
    }

    public void setCssFollowPcc(boolean cssFollowPcc2) {
        this.cssFollowPcc = cssFollowPcc2;
    }

    public void setCssFollowPcc(int cssFollowPcc2) {
        this.cssFollowPcc = cssFollowPcc2 != 0;
    }

    public int getPCell2Rx() {
        return booleanToInt(this.pCell2Rx);
    }

    public boolean[] getBoolArrayPCell2Rx() {
        return this.pCell2Rx;
    }

    public void setPCell2Rx(int pCell2Rx2) {
        this.pCell2Rx = intToBoolean(pCell2Rx2, 2);
    }

    public void updatePCell2Rx(boolean value, int pos) {
        boolean[] zArr = this.pCell2Rx;
        if (pos < zArr.length) {
            zArr[pos] = value;
        }
    }

    public int getPCell4Rx() {
        return booleanToInt(this.pCell4Rx);
    }

    public boolean[] getBoolArrayPCell4Rx() {
        return this.pCell4Rx;
    }

    public void setPCell4Rx(int pCell4Rx2) {
        this.pCell4Rx = intToBoolean(pCell4Rx2, 4);
    }

    public void updatePCell4Rx(boolean value, int pos) {
        boolean[] zArr = this.pCell4Rx;
        if (pos < zArr.length) {
            zArr[pos] = value;
        }
    }

    public int getSCell2Rx() {
        return booleanToInt(this.sCell2Rx);
    }

    public boolean[] getBoolArraySCell2Rx() {
        return this.sCell2Rx;
    }

    public void setSCell2Rx(int sCell2Rx2) {
        this.sCell2Rx = intToBoolean(sCell2Rx2, 2);
    }

    public void updateSCell2Rx(boolean value, int pos) {
        boolean[] zArr = this.sCell2Rx;
        if (pos < zArr.length) {
            zArr[pos] = value;
        }
    }

    public int getSCell4Rx() {
        return booleanToInt(this.sCell4Rx);
    }

    public boolean[] getBoolArraySCell4Rx() {
        return this.sCell4Rx;
    }

    public void setSCell4Rx(int sCell4Rx2) {
        this.sCell4Rx = intToBoolean(sCell4Rx2, 4);
    }

    public void updateSCell4Rx(boolean value, int pos) {
        boolean[] zArr = this.sCell4Rx;
        if (pos < zArr.length) {
            zArr[pos] = value;
        }
    }

    public int getSim() {
        return this.sim;
    }

    public void setSim(int sim2) {
        this.sim = sim2;
    }
}
