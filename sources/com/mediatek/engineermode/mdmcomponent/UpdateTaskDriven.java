package com.mediatek.engineermode.mdmcomponent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.mdmcomponent.MDMContentICD;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class UpdateTaskDriven extends Handler {
    private static final int DRIVER_DEAD = 0;
    private static final int DRIVER_NOT_READY = 2;
    private static final int DRIVER_READY = 1;
    private static final int EVENT_DONE = 2;
    private static final int EVENT_EXEC_NEXT = 1;
    private static final int STATE_DOING = 1;
    private static final int STATE_DONE = 2;
    private static final int STATE_NO_PENDING = 0;
    private static final String TAG = "EmInfo/UpdateTaskDriven";
    protected static final int TASK_INIT_VIEW = 0;
    protected static final int TASK_REMOVE_VIEW = 2;
    protected static final int TASK_UPDATE_DATA = 1;
    private int mDriverState = 2;
    private Object mDriverStateLock = new Object();
    private ArrayList<Task> mPendingTask = new ArrayList<>();
    private int mState = 0;
    private Object mStateLock = new Object();
    private Object mTaskLock = new Object();

    public UpdateTaskDriven() {
    }

    public UpdateTaskDriven(Looper looper) {
        super(looper);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004f, code lost:
        if (isDriverReady() == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0051, code lost:
        obtainMessage(1).sendToTarget();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void appendTask(com.mediatek.engineermode.mdmcomponent.Task r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mTaskLock
            monitor-enter(r0)
            boolean r1 = r4.isDriverDead()     // Catch:{ all -> 0x005a }
            if (r1 == 0) goto L_0x0025
            java.lang.String r1 = "EmInfo/UpdateTaskDriven"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x005a }
            r2.<init>()     // Catch:{ all -> 0x005a }
            java.lang.String r3 = "Driver dead! current task returned "
            r2.append(r3)     // Catch:{ all -> 0x005a }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x005a }
            r2.append(r3)     // Catch:{ all -> 0x005a }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x005a }
            com.mediatek.engineermode.Elog.d(r1, r2)     // Catch:{ all -> 0x005a }
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            return
        L_0x0025:
            r1 = 1
        L_0x0026:
            java.util.ArrayList<com.mediatek.engineermode.mdmcomponent.Task> r2 = r4.mPendingTask     // Catch:{ all -> 0x005a }
            int r2 = r2.size()     // Catch:{ all -> 0x005a }
            if (r1 >= r2) goto L_0x0045
            java.util.ArrayList<com.mediatek.engineermode.mdmcomponent.Task> r2 = r4.mPendingTask     // Catch:{ all -> 0x005a }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x005a }
            com.mediatek.engineermode.mdmcomponent.Task r2 = (com.mediatek.engineermode.mdmcomponent.Task) r2     // Catch:{ all -> 0x005a }
            boolean r2 = r4.isTaskRepeate(r5, r2)     // Catch:{ all -> 0x005a }
            if (r2 == 0) goto L_0x0042
            java.util.ArrayList<com.mediatek.engineermode.mdmcomponent.Task> r2 = r4.mPendingTask     // Catch:{ all -> 0x005a }
            r2.remove(r1)     // Catch:{ all -> 0x005a }
            goto L_0x0045
        L_0x0042:
            int r1 = r1 + 1
            goto L_0x0026
        L_0x0045:
            java.util.ArrayList<com.mediatek.engineermode.mdmcomponent.Task> r1 = r4.mPendingTask     // Catch:{ all -> 0x005a }
            r1.add(r5)     // Catch:{ all -> 0x005a }
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            boolean r0 = r4.isDriverReady()
            if (r0 == 0) goto L_0x0059
            r0 = 1
            android.os.Message r0 = r4.obtainMessage(r0)
            r0.sendToTarget()
        L_0x0059:
            return
        L_0x005a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mdmcomponent.UpdateTaskDriven.appendTask(com.mediatek.engineermode.mdmcomponent.Task):void");
    }

    public void appendIcdTask(MDMContentICD.MDMHeaderICD IcdHeader, ByteBuffer icdPacket) {
        appendTask(new Task(1, IcdHeader, icdPacket, 1));
    }

    public void appendEmTask(Object EmMsg, int msgId) {
        appendTask(new Task(1, EmMsg, msgId, 3));
    }

    public void resetDriver() {
        clearPendingTask();
        setDriverState(2);
    }

    private int getState() {
        int i;
        synchronized (this.mStateLock) {
            i = this.mState;
        }
        return i;
    }

    private int getDriverState() {
        int i;
        synchronized (this.mDriverStateLock) {
            i = this.mDriverState;
        }
        return i;
    }

    private void setDriverState(int driverState) {
        synchronized (this.mDriverStateLock) {
            this.mDriverState = driverState;
        }
    }

    public boolean isDriverDead() {
        return getDriverState() == 0;
    }

    public boolean isTaskRunning() {
        return getState() == 1;
    }

    public boolean isDriverReady() {
        return getDriverState() == 1;
    }

    public void setDriverReady() {
        setDriverState(1);
    }

    public boolean isStopTask(Task task) {
        return task.getTaskId() == 2;
    }

    public boolean isTaskRepeate(Task A, Task B) {
        return false;
    }

    private void setState(int state) {
        synchronized (this.mStateLock) {
            this.mState = state;
        }
    }

    private Task getCurrentPendingTask() {
        synchronized (this.mTaskLock) {
            if (this.mPendingTask.size() == 0) {
                return null;
            }
            Task task = this.mPendingTask.get(0);
            return task;
        }
    }

    private void removePendingTask(int index) {
        synchronized (this.mTaskLock) {
            if (this.mPendingTask.size() > 0) {
                this.mPendingTask.remove(index);
            }
        }
    }

    public void clearPendingTask() {
        synchronized (this.mTaskLock) {
            this.mPendingTask.clear();
        }
    }

    public void exec() {
        Task task = getCurrentPendingTask();
        if (task == null) {
            setState(0);
        } else if (getState() != 1) {
            if (!isDriverDead() || isStopTask(task)) {
                setState(1);
                switch (task.getTaskId()) {
                    case 1:
                        Message msgToUiEm = MDMComponentDetailActivity.mUpdateUiHandler.obtainMessage();
                        msgToUiEm.what = task.getType();
                        msgToUiEm.obj = task;
                        MDMComponentDetailActivity.mUpdateUiHandler.sendMessage(msgToUiEm);
                        return;
                    default:
                        taskDone();
                        return;
                }
            } else {
                Elog.d(TAG, "Driver is Stopped");
                taskDone();
            }
        }
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                break;
            case 2:
                removePendingTask(0);
                setState(2);
                break;
            default:
                return;
        }
        exec();
    }

    private String stateToString(int state) {
        switch (state) {
            case 0:
                return "STATE_NO_PENDING";
            case 1:
                return "STATE_DOING";
            case 2:
                return "STATE_DONE";
            default:
                return "UNKNOWN_STATE";
        }
    }

    private String eventToString(int event) {
        switch (event) {
            case 1:
                return "EVENT_EXEC_NEXT";
            case 2:
                return "EVENT_DONE";
            default:
                return "UNKNOWN_EVENT";
        }
    }

    /* access modifiers changed from: package-private */
    public void taskDone() {
        obtainMessage(2).sendToTarget();
    }

    public void taskStop() {
        setDriverState(0);
        clearPendingTask();
    }

    public void taskStart() {
        setDriverState(1);
        exec();
    }
}
