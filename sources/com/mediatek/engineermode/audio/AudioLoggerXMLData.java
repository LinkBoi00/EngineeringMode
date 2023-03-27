package com.mediatek.engineermode.audio;

import java.util.ArrayList;

public class AudioLoggerXMLData {
    ArrayList<String> mAudioCommandGetOperation = new ArrayList<>();
    ArrayList<String> mAudioCommandSetOperation = new ArrayList<>();
    ArrayList<DumpOptions> mAudioDumpOperation = new ArrayList<>();
    ArrayList<String> mParametersGetOperationItems = new ArrayList<>();
    ArrayList<String> mParametersSetOperationItems = new ArrayList<>();

    public void setAudioCommandSetOperation(String operation) {
        this.mAudioCommandSetOperation.add(operation);
    }

    public void setAudioCommandGetOperation(String operation) {
        this.mAudioCommandGetOperation.add(operation);
    }

    public void setParametersSetOperation(String operation) {
        this.mParametersSetOperationItems.add(operation);
    }

    public void setParametersGetOperation(String operation) {
        this.mParametersGetOperationItems.add(operation);
    }
}
