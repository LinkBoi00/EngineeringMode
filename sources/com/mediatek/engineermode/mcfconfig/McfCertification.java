package com.mediatek.engineermode.mcfconfig;

import java.io.File;
import java.lang.reflect.Array;

public class McfCertification {
    private boolean[][] cmdResult = ((boolean[][]) Array.newInstance(boolean.class, new int[]{2, 3}));
    private int[] currentCmd = new int[2];
    private String fileList;
    private String filePath;
    private String opName;
    private int opid;
    private String opotaFileName;
    private String otaFileName;
    private int pathFlag;

    public int getOpid() {
        return this.opid;
    }

    public void setOpid(int opid2) {
        this.opid = opid2;
    }

    public String getFileList() {
        return this.fileList;
    }

    public void setFileList(String fileList2) {
        this.fileList = fileList2;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath2) {
        this.filePath = filePath2;
    }

    public String getOpotaFileName() {
        return this.opotaFileName;
    }

    public void setOpotaFileName(String sbpFileName) {
        this.opotaFileName = sbpFileName;
    }

    public String getOtaFileName() {
        return this.otaFileName;
    }

    public void setOtaFileName(String otaFileName2) {
        this.otaFileName = otaFileName2;
    }

    public String getOtaFilePath() {
        return this.filePath + File.separator + this.otaFileName;
    }

    public String getOpotaFilePath() {
        return this.filePath + File.separator + this.opotaFileName;
    }

    public int[] getCurrentCmd() {
        return this.currentCmd;
    }

    public int getCurrentCmd(int index) {
        return this.currentCmd[index];
    }

    public void setCurrentCmd(int index, int currentCmd2) {
        this.currentCmd[index] = currentCmd2;
    }

    public void setCurrentCmd(int[] currentCmd2) {
        this.currentCmd = currentCmd2;
    }

    public int getPathFlag() {
        return this.pathFlag;
    }

    public void setPathFlag(int pathFlag2) {
        this.pathFlag = pathFlag2;
    }

    public String getOpName() {
        return this.opName;
    }

    public void setOpName(String opName2) {
        this.opName = opName2;
    }

    public boolean[][] getCmdResult() {
        return this.cmdResult;
    }

    public boolean getCmdResult(int index, int cmd) {
        return this.cmdResult[index][cmd - 1];
    }

    public boolean getCurCmdResult(int index) {
        return this.cmdResult[index][getCurrentCmd(index) - 1];
    }

    public void setCmdResult(boolean[][] cmdResult2) {
        this.cmdResult = cmdResult2;
    }

    public void setCmdResult(int index, int cmd, boolean cmdResult2) {
        this.cmdResult[index][cmd - 1] = cmdResult2;
    }

    public void setCmdResult(int index, boolean[] cmdResult2) {
        int i = 0;
        while (true) {
            boolean[][] zArr = this.cmdResult;
            if (i < zArr[index].length) {
                zArr[index][i] = cmdResult2[i];
                i++;
            } else {
                return;
            }
        }
    }
}
