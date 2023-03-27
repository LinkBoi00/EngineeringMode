package com.mediatek.engineermode.mdmcomponent;

import com.mediatek.engineermode.mdmcomponent.MDMContentICD;
import java.nio.ByteBuffer;

/* compiled from: UpdateTaskDriven */
class Task {
    MDMContentICD.MDMHeaderICD mIcdHeader = null;
    Object mMsg = null;
    private int mMsgId = -1;
    private int mTaskId = -1;
    private int mType = -1;

    public Task(int taskId) {
        this.mTaskId = taskId;
    }

    public Task(int taskId, MDMContentICD.MDMHeaderICD IcdHeader, ByteBuffer icdPacket, int type) {
        this.mTaskId = taskId;
        this.mIcdHeader = IcdHeader;
        this.mMsg = icdPacket;
        this.mType = type;
        this.mMsgId = IcdHeader.msg_id;
    }

    public Task(int taskId, Object emMsg, int msgId, int type) {
        this.mTaskId = taskId;
        this.mMsg = emMsg;
        this.mType = type;
        this.mMsgId = msgId;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public int getMsgId() {
        return this.mMsgId;
    }

    public MDMContentICD.MDMHeaderICD getIcdHeader() {
        return this.mIcdHeader;
    }

    public Object getExtraMsg() {
        return this.mMsg;
    }

    public int getType() {
        return this.mType;
    }

    public void setmType(int mType2) {
        this.mType = mType2;
    }

    public String toString() {
        return "id:" + getTaskId() + ", type:" + getType() + ", msgId: " + Long.toHexString((long) getMsgId());
    }
}
