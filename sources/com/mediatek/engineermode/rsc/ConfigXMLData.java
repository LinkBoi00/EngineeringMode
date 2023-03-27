package com.mediatek.engineermode.rsc;

import java.util.ArrayList;

public class ConfigXMLData {
    String mMagic;
    ArrayList<ProjectData> mProjectList = new ArrayList<>();
    String mTarPartName;
    String mTarPartOffset;
    int mVersion;

    /* access modifiers changed from: package-private */
    public void setTarPartName(String str) {
        this.mTarPartName = str;
    }

    /* access modifiers changed from: package-private */
    public String getTarPartName() {
        return this.mTarPartName;
    }

    /* access modifiers changed from: package-private */
    public void setTarPartOffset(String str) {
        this.mTarPartOffset = str;
    }

    /* access modifiers changed from: package-private */
    public String getTarPartOffset() {
        return this.mTarPartOffset;
    }

    /* access modifiers changed from: package-private */
    public void addProjectName(ProjectData proj) {
        this.mProjectList.add(proj);
    }

    /* access modifiers changed from: package-private */
    public void setVersion(int version) {
        this.mVersion = version;
    }

    /* access modifiers changed from: package-private */
    public void setMagic(String magic) {
        this.mMagic = magic;
    }

    /* access modifiers changed from: package-private */
    public int getVersion() {
        return this.mVersion;
    }

    /* access modifiers changed from: package-private */
    public String getMagic() {
        return this.mMagic;
    }

    /* access modifiers changed from: package-private */
    public ArrayList<ProjectData> getProjectList() {
        return this.mProjectList;
    }

    class ProjectData {
        private int mPrjIndex;
        private String mPrjName;
        private String mPrjOptr;

        ProjectData() {
        }

        /* access modifiers changed from: package-private */
        public void setIndex(int index) {
            this.mPrjIndex = index;
        }

        /* access modifiers changed from: package-private */
        public void setName(String name) {
            this.mPrjName = name;
        }

        /* access modifiers changed from: package-private */
        public void setOptr(String optr) {
            this.mPrjOptr = optr;
        }

        /* access modifiers changed from: package-private */
        public String getName() {
            return this.mPrjName;
        }

        /* access modifiers changed from: package-private */
        public String getOptr() {
            return this.mPrjOptr;
        }

        /* access modifiers changed from: package-private */
        public int getIndex() {
            return this.mPrjIndex;
        }
    }
}
