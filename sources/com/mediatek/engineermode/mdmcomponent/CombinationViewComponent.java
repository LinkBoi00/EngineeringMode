package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: MDMComponentFT */
abstract class CombinationViewComponent extends MDMComponent {
    private static final int SHOW_SIM_ALL = -1;
    private static final int SHOW_SIM_ONE = 0;
    private static final int SHOW_SIM_TWO = 1;
    TableInfoAdapter commonInfoAdapter;
    MdmLinearLayout commonView;
    private int currentSimID = 0;
    List<HashMap<String, TableInfoAdapter>> hmapAdapterList = null;
    List<LinkedHashMap<String, LinkedHashMap>> hmapLabelsList = null;
    List<HashMap<String, MdmLinearLayout>> hmapViewList = null;
    String[] imsi;
    LinearLayout layout;
    String location;
    Activity mContext;
    HandlerThread mHandlerThread;
    UpdateTaskDriven mUpdateTaskDriven;
    ScrollView scrollView;
    private int supportSimCount = 2;

    /* access modifiers changed from: package-private */
    public abstract ArrayList<LinkedHashMap> initHashMapValues();

    /* access modifiers changed from: package-private */
    public abstract String[] initLabels();

    /* access modifiers changed from: package-private */
    public abstract boolean isLabelArrayType(String str);

    /* access modifiers changed from: package-private */
    public abstract void registeListener();

    /* access modifiers changed from: package-private */
    public abstract void startUpdateProcess(Task task);

    /* access modifiers changed from: package-private */
    public abstract void unRegisteListener();

    public int getSupportSimCount() {
        return this.supportSimCount;
    }

    public int getCurrentSimID() {
        return this.currentSimID;
    }

    public void setCurrentSimID(int currentSimID2) {
        this.currentSimID = currentSimID2;
    }

    public CombinationViewComponent(Activity context, int simCount) {
        super(context);
        this.mContext = context;
        this.commonView = new MdmLinearLayout(context);
        this.commonInfoAdapter = new TableInfoAdapter(context);
        this.supportSimCount = simCount;
        this.imsi = new String[getSupportSimCount()];
        if (this.scrollView == null) {
            ScrollView scrollView2 = new ScrollView(this.mContext);
            this.scrollView = scrollView2;
            scrollView2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        }
        if (this.layout == null) {
            LinearLayout linearLayout = new LinearLayout(this.mContext);
            this.layout = linearLayout;
            linearLayout.setOrientation(1);
            this.layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        }
        if (this.hmapLabelsList == null) {
            this.hmapLabelsList = new ArrayList();
            this.hmapViewList = new ArrayList();
            this.hmapAdapterList = new ArrayList();
            for (int i = 0; i < getSupportSimCount(); i++) {
                this.hmapLabelsList.add(i, new LinkedHashMap());
                this.hmapViewList.add(i, new HashMap());
                this.hmapAdapterList.add(i, new HashMap());
            }
        }
        if (this.mHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread("MtkMdmViewManager");
            this.mHandlerThread = handlerThread;
            handlerThread.start();
            this.mUpdateTaskDriven = new UpdateTaskDriven(this.mHandlerThread.getLooper());
            return;
        }
        this.mUpdateTaskDriven.setDriverState(2);
    }

    /* access modifiers changed from: package-private */
    public void taskDone() {
        this.mUpdateTaskDriven.obtainMessage(2).sendToTarget();
    }

    /* access modifiers changed from: protected */
    public void taskStop() {
        this.mUpdateTaskDriven.addFirstTask(new Task(2), 0);
    }

    private void taskStart() {
        this.mUpdateTaskDriven.addFirstTask(new Task(0), 2);
    }

    /* compiled from: MDMComponentFT */
    class Task {
        /* access modifiers changed from: private */
        public boolean canRepeate = true;
        private Object mData = null;
        private String mName = "";
        private int mSimID = 0;
        private int mTaskId = -1;

        public Task(int taskId, String name, Msg data, int simID) {
            this.mTaskId = taskId;
            this.mName = name;
            this.mData = data;
            this.mSimID = simID;
        }

        public Task(int taskId, String name, Msg data, int simID, boolean canRepeate2) {
            this.mTaskId = taskId;
            this.mName = name;
            this.mData = data;
            this.mSimID = simID;
            this.canRepeate = canRepeate2;
        }

        public Task(int taskId) {
            this.mTaskId = taskId;
        }

        public int getTaskId() {
            return this.mTaskId;
        }

        public String getExtraName() {
            return this.mName;
        }

        public Object getExtraMsg() {
            return this.mData;
        }

        public int getExtraSimID() {
            return this.mSimID;
        }

        public String toString() {
            return "Task ID: " + this.mTaskId + ", msgName: " + this.mName + ", mSimID: " + this.mSimID;
        }
    }

    /* compiled from: MDMComponentFT */
    class UpdateTaskDriven extends Handler {
        private static final int DRIVER_DEAD = 0;
        private static final int DRIVER_NOT_READY = 2;
        private static final int DRIVER_READY = 1;
        private static final int EVENT_DONE = 2;
        private static final int EVENT_EXEC_NEXT = 1;
        private static final int STATE_DOING = 1;
        private static final int STATE_DONE = 2;
        private static final int STATE_NO_PENDING = 0;
        private static final String TAG = "EmInfo/MDMComponentFT/UpdateTaskDriven";
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

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
            if (isDriverReady() == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
            obtainMessage(1).sendToTarget();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void appendTask(com.mediatek.engineermode.mdmcomponent.CombinationViewComponent.Task r5) {
            /*
                r4 = this;
                java.lang.Object r0 = r4.mTaskLock
                monitor-enter(r0)
                boolean r1 = r4.isDriverDead()     // Catch:{ all -> 0x003a }
                if (r1 == 0) goto L_0x0025
                java.lang.String r1 = "EmInfo/MDMComponentFT/UpdateTaskDriven"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x003a }
                r2.<init>()     // Catch:{ all -> 0x003a }
                java.lang.String r3 = "Driver dead! current task returned "
                r2.append(r3)     // Catch:{ all -> 0x003a }
                java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x003a }
                r2.append(r3)     // Catch:{ all -> 0x003a }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x003a }
                com.mediatek.engineermode.Elog.d(r1, r2)     // Catch:{ all -> 0x003a }
                monitor-exit(r0)     // Catch:{ all -> 0x003a }
                return
            L_0x0025:
                java.util.ArrayList<com.mediatek.engineermode.mdmcomponent.CombinationViewComponent$Task> r1 = r4.mPendingTask     // Catch:{ all -> 0x003a }
                r1.add(r5)     // Catch:{ all -> 0x003a }
                monitor-exit(r0)     // Catch:{ all -> 0x003a }
                boolean r0 = r4.isDriverReady()
                if (r0 == 0) goto L_0x0039
                r0 = 1
                android.os.Message r0 = r4.obtainMessage(r0)
                r0.sendToTarget()
            L_0x0039:
                return
            L_0x003a:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x003a }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mdmcomponent.CombinationViewComponent.UpdateTaskDriven.appendTask(com.mediatek.engineermode.mdmcomponent.CombinationViewComponent$Task):void");
        }

        public void addFirstTask(Task task, int driverState) {
            setDriverState(driverState);
            synchronized (this.mTaskLock) {
                int i = 0;
                if (driverState == 0) {
                    try {
                        if (isTaskRunning()) {
                            this.mPendingTask.clear();
                            this.mPendingTask.add(this.mPendingTask.get(0));
                        } else {
                            this.mPendingTask.clear();
                        }
                        this.mPendingTask.add(task);
                    } catch (Throwable th) {
                        while (true) {
                            throw th;
                        }
                    }
                } else {
                    int start = 0;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= this.mPendingTask.size()) {
                            break;
                        } else if (this.mPendingTask.get(i2).getTaskId() == 2) {
                            start = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    int j = isTaskRunning();
                    while (j <= start && j < this.mPendingTask.size()) {
                        this.mPendingTask.remove(j);
                        j++;
                    }
                    ArrayList<Task> arrayList = this.mPendingTask;
                    if (isTaskRunning()) {
                        i = 1;
                    }
                    arrayList.add(i, task);
                }
            }
            obtainMessage(1).sendToTarget();
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

        private String driverStateToStr(int state) {
            switch (state) {
                case 0:
                    return "Dead";
                case 1:
                    return "Ready";
                case 2:
                    return "Not Ready";
                default:
                    return "Unknown: " + state;
            }
        }

        /* access modifiers changed from: private */
        public void setDriverState(int driverState) {
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

        public boolean isStopTask(Task task) {
            return task.getTaskId() == 2;
        }

        public boolean isTaskRepeate(Task A, Task B) {
            if (!A.canRepeate && A.getTaskId() == B.getTaskId() && A.getExtraName() == B.getExtraName() && A.getExtraSimID() == B.getExtraSimID()) {
                return true;
            }
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
                        case 0:
                            new InitViewTask().execute(new Void[0]);
                            return;
                        case 1:
                            CombinationViewComponent.this.startUpdateProcess(task);
                            return;
                        case 2:
                            CombinationViewComponent.this.taskDone();
                            return;
                        default:
                            CombinationViewComponent.this.taskDone();
                            return;
                    }
                } else {
                    CombinationViewComponent.this.taskStop();
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
    }

    /* access modifiers changed from: package-private */
    public void clearViewData(int SimID) {
        if (!isValidSimID(SimID)) {
            for (int i = 0; i < getSupportSimCount(); i++) {
                if (this.hmapLabelsList.size() > i && this.hmapLabelsList.get(i) != null) {
                    this.hmapLabelsList.get(i).clear();
                    this.hmapViewList.get(i).clear();
                    this.hmapAdapterList.get(i).clear();
                }
            }
        } else if (this.hmapLabelsList.contains(Integer.valueOf(SimID))) {
            this.hmapLabelsList.get(SimID).clear();
            this.hmapViewList.get(SimID).clear();
            this.hmapAdapterList.get(SimID).clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void initHashMap(int simID) {
        if (isValidSimID(simID)) {
            initHashMapByID(simID);
            return;
        }
        for (int i = 0; i < getSupportSimCount(); i++) {
            initHashMapByID(i);
        }
    }

    /* access modifiers changed from: package-private */
    public void initHashMapByID(int id) {
        if (id < this.hmapLabelsList.size() && this.hmapLabelsList.get(id).isEmpty()) {
            this.hmapLabelsList.set(id, getHashMapLabels());
            for (String key : this.hmapLabelsList.get(id).keySet()) {
                this.hmapAdapterList.get(id).put(key, new TableInfoAdapter(this.mContext));
                this.hmapViewList.get(id).put(key, new MdmLinearLayout(this.mContext));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, String> initHashMap(Object[] keys) {
        LinkedHashMap<String, String> hashMapObj = new LinkedHashMap<>();
        for (String put : keys) {
            hashMapObj.put(put, (Object) null);
        }
        return hashMapObj;
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, LinkedHashMap> getHashMapLabels() {
        LinkedHashMap<String, LinkedHashMap> hashMapkeyValues = new LinkedHashMap<>();
        if (hashMapkeyValues.size() == 0) {
            ArrayList<LinkedHashMap> hashMapValues = initHashMapValues();
            String[] labelKeys = initLabels();
            for (int i = 0; i < labelKeys.length; i++) {
                hashMapkeyValues.put(labelKeys[i], hashMapValues.get(i));
            }
        }
        return hashMapkeyValues;
    }

    /* access modifiers changed from: package-private */
    public void resetHashMapKeyValues(String Label, int simID) {
        if (isValidSimID(simID)) {
            new LinkedHashMap();
            LinkedHashMap map = initHashMap(((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).keySet().toArray());
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).clear();
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).putAll(map);
            return;
        }
        for (int i = 0; i < getSupportSimCount(); i++) {
            new LinkedHashMap();
            LinkedHashMap map2 = initHashMap(((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).keySet().toArray());
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).clear();
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).putAll(map2);
        }
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyValues(String label, int simID, String key, Object value) {
        if (!isLabelArrayType(label)) {
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).put(key, String.valueOf(value));
        } else if (value instanceof String[]) {
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).put(key, (String[]) value);
        } else {
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).put(key, new String[]{value.toString()});
        }
    }

    /* access modifiers changed from: package-private */
    public void addHashMapKeyValues(String label, int simID, String key, Object value) {
        if (isLabelArrayType(label)) {
            String[] oldValue = ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(key) != null ? (String[]) ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(key) : new String[0];
            String[] setValue = value instanceof String[] ? (String[]) value : new String[]{value.toString()};
            String[] newValue = new String[(oldValue.length + setValue.length)];
            System.arraycopy(oldValue, 0, newValue, 0, oldValue.length);
            System.arraycopy(setValue, 0, newValue, oldValue.length, setValue.length);
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).put(key, newValue);
            return;
        }
        setHashMapKeyValues(label, simID, key, value);
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyValues(String Label, int simID, LinkedHashMap<String, String> keyValues) {
        this.hmapLabelsList.get(simID).put(Label, keyValues);
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyValues(String Label, int simID, String[] values) {
        String[] keys = new String[((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).keySet().size()];
        ((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            if (isLabelArrayType(Label)) {
                addHashMapKeyValues(Label, simID, keys[i], values[i]);
            } else {
                setHashMapKeyValues(Label, simID, keys[i], (Object) values[i]);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyValues(String Label, int simID, String key, String[] values) {
        if (isLabelArrayType(Label)) {
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).put(key, values);
        } else {
            setHashMapKeyValues(Label, simID, values);
        }
    }

    /* access modifiers changed from: package-private */
    public void addHashMapKeyValues(String Label, int simID, String[] values) {
        String[] keys = new String[((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).keySet().size()];
        ((LinkedHashMap) this.hmapLabelsList.get(simID).get(Label)).keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            addHashMapKeyValues(Label, simID, keys[i], values[i]);
        }
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        long startTime = System.currentTimeMillis();
        registeListener();
        if (this.scrollView.getParent() != null) {
            ((ViewGroup) this.scrollView.getParent()).removeView(this.scrollView);
        }
        if (this.scrollView.getChildCount() > 0) {
            this.scrollView.removeAllViews();
        }
        if (this.layout.getParent() != null) {
            ((ViewGroup) this.layout.getParent()).removeView(this.layout);
        }
        safeAddView(this.scrollView, this.layout);
        updateCommonView(getCurrentSimID());
        safeAddView(this.layout, this.commonView);
        taskStart();
        Elog.d("EmInfo/MDMComponent", "getView done! cost: " + (System.currentTimeMillis() - startTime) + "ms");
        return this.scrollView;
    }

    /* compiled from: MDMComponentFT */
    private class InitViewTask extends AsyncTask<Void, String[], Void> {
        long startTime;

        private InitViewTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            this.startTime = System.currentTimeMillis();
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            for (int i = 0; i < CombinationViewComponent.this.getSupportSimCount(); i++) {
                CombinationViewComponent.this.initHashMap(i);
                if (CombinationViewComponent.this.hmapLabelsList.get(i) != null) {
                    for (String label : CombinationViewComponent.this.hmapLabelsList.get(i).keySet()) {
                        if (label.split("-").length > 1 && label.split("-")[1].indexOf(".") < 0) {
                            publishProgress(new String[][]{new String[]{i + "", label}});
                        }
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(String[]... values) {
            super.onProgressUpdate(values);
            int index = Integer.valueOf(values[0][0]).intValue();
            String label = values[0][1];
            if (label.split("-").length > 1 && label.split("-")[1].indexOf(".") < 0) {
                ((MdmLinearLayout) CombinationViewComponent.this.hmapViewList.get(index).get(label)).showTitle();
                ((MdmLinearLayout) CombinationViewComponent.this.hmapViewList.get(index).get(label)).setTextContent("SIM" + (index + 1) + ":" + label);
            }
            CombinationViewComponent.this.setHmapAdapterByLabel(label, index);
            ((MdmLinearLayout) CombinationViewComponent.this.hmapViewList.get(index).get(label)).setAdapter((ListAdapter) CombinationViewComponent.this.hmapAdapterList.get(index).get(label));
            ((TableInfoAdapter) CombinationViewComponent.this.hmapAdapterList.get(index).get(label)).notifyDataSetChanged();
            ((MdmLinearLayout) CombinationViewComponent.this.hmapViewList.get(index).get(label)).setListViewHeightBasedOnChildren();
            if (((MdmLinearLayout) CombinationViewComponent.this.hmapViewList.get(index).get(label)).getParent() != null) {
                ((ViewGroup) ((MdmLinearLayout) CombinationViewComponent.this.hmapViewList.get(index).get(label)).getParent()).removeView((View) CombinationViewComponent.this.hmapViewList.get(index).get(label));
            }
            CombinationViewComponent combinationViewComponent = CombinationViewComponent.this;
            combinationViewComponent.safeAddView(combinationViewComponent.layout, (View) CombinationViewComponent.this.hmapViewList.get(index).get(label));
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CombinationViewComponent.this.mUpdateTaskDriven.setDriverState(1);
            CombinationViewComponent.this.taskDone();
            Elog.d("EmInfo/MDMComponent", "InitViewTask cost: " + (System.currentTimeMillis() - this.startTime) + "ms");
        }
    }

    public void updateCommonView(int simID) {
        this.commonInfoAdapter.clear();
        if (isValidSimID(simID)) {
            TableInfoAdapter tableInfoAdapter = this.commonInfoAdapter;
            tableInfoAdapter.add(new String[]{"Location", "IMSI" + (simID + 1)});
            this.commonInfoAdapter.add(new String[]{this.location, this.imsi[simID]});
        } else {
            String[] commonTitles = new String[(getSupportSimCount() + 1)];
            String[] commonValues = new String[(getSupportSimCount() + 1)];
            commonTitles[0] = "Location";
            commonValues[0] = this.location;
            for (int i = 0; i < getSupportSimCount(); i++) {
                commonTitles[i + 1] = "IMSI" + (i + 1);
                int i2 = i + 1;
                String[] strArr = this.imsi;
                commonValues[i2] = strArr[i] == null ? " " : strArr[i];
            }
            this.commonInfoAdapter.add(commonTitles);
            this.commonInfoAdapter.add(commonValues);
        }
        this.commonView.setAdapter(this.commonInfoAdapter);
        this.commonView.setTextContent("Common");
        this.commonInfoAdapter.notifyDataSetChanged();
        this.commonView.setListViewHeightBasedOnChildren();
    }

    public void safeAddView(ViewGroup parent, View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        parent.addView(view);
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
        unRegisteListener();
        LinearLayout linearLayout = this.layout;
        if (linearLayout != null && linearLayout.getChildCount() > 0) {
            this.layout.removeAllViews();
            this.scrollView.removeAllViews();
        }
        taskStop();
    }

    /* access modifiers changed from: package-private */
    public void clearData(String key, int simID) {
        if (!isValidSimID(simID)) {
            for (int i = 0; i < this.hmapLabelsList.size(); i++) {
                if (this.hmapAdapterList.get(i).get(key) != null) {
                    ((TableInfoAdapter) this.hmapAdapterList.get(i).get(key)).clear();
                }
            }
        } else if (this.hmapAdapterList.get(simID).get(key) != null) {
            ((TableInfoAdapter) this.hmapAdapterList.get(simID).get(key)).clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void displayView(String label, int simID, boolean isShow) {
        if (this.mUpdateTaskDriven.isDriverDead()) {
            Elog.e("EmInfo/MDMComponent", "Exit displayView, Driver dead");
        } else if (!isValidSimID(simID)) {
            for (int i = 0; i < getSupportSimCount(); i++) {
                if (isShow) {
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(label)).showView();
                } else {
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(label)).hideView();
                }
            }
        } else if (!isShow || (isValidSimID(getCurrentSimID()) && getCurrentSimID() != simID)) {
            ((MdmLinearLayout) this.hmapViewList.get(simID).get(label)).hideView();
        } else {
            ((MdmLinearLayout) this.hmapViewList.get(simID).get(label)).showView();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isValidSimID(int simID) {
        if (simID >= getSupportSimCount() || simID < 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void addData(String label, int simID) {
        if (isValidSimID(simID)) {
            setHmapAdapterByLabel(label, simID);
            ((MdmLinearLayout) this.hmapViewList.get(simID).get(label)).setAdapter((ListAdapter) this.hmapAdapterList.get(simID).get(label));
            ((TableInfoAdapter) this.hmapAdapterList.get(simID).get(label)).notifyDataSetChanged();
            ((MdmLinearLayout) this.hmapViewList.get(simID).get(label)).setListViewHeightBasedOnChildren();
        }
    }

    /* access modifiers changed from: package-private */
    public void setHmapAdapter(int simID) {
        for (String label : this.hmapLabelsList.get(simID).keySet()) {
            if (((TableInfoAdapter) this.hmapAdapterList.get(simID).get(label)).getCount() == 0) {
                setHmapAdapterByLabel(label, simID);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setHmapAdapterByLabel(String label, int simID) {
        Object obj;
        Object obj2;
        Object obj3;
        String str;
        if (isValidSimID(simID)) {
            ((TableInfoAdapter) this.hmapAdapterList.get(simID).get(label)).clear();
            String[] keys = new String[((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).keySet().size()];
            ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).keySet().toArray(keys);
            if (isLabelArrayType(label)) {
                Map<String, String[]> arrayValuesMap = new HashMap<>();
                int length = 0;
                ((TableInfoAdapter) this.hmapAdapterList.get(simID).get(label)).add(keys);
                for (int i = 0; i < keys.length; i++) {
                    if (((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(keys[i]) == null) {
                        arrayValuesMap.put(keys[i], new String[]{""});
                    } else {
                        Object[] values = (Object[]) ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(keys[i]);
                        length = length < values.length ? values.length : length;
                        arrayValuesMap.put(keys[i], (String[]) values);
                    }
                }
                for (int i2 = 0; i2 < length; i2++) {
                    String[] arrayValue = new String[keys.length];
                    boolean valid = false;
                    for (int j = 0; j < keys.length; j++) {
                        if (arrayValuesMap.get(keys[j]) == null || i2 >= arrayValuesMap.get(keys[j]).length) {
                            str = "";
                        } else {
                            str = arrayValuesMap.get(keys[j])[i2];
                        }
                        arrayValue[j] = str;
                        if (arrayValue[j] != null && !arrayValue[j].equals("")) {
                            valid = true;
                        }
                    }
                    if (valid) {
                        ((TableInfoAdapter) this.hmapAdapterList.get(simID).get(label)).add(arrayValue);
                        Elog.d("EmInfo/MDMComponent", "[setHmapAdapter][ArrayType] label: " + label + " ,values: " + Arrays.toString(arrayValue));
                    }
                }
                return;
            }
            for (int i3 = 0; i3 < keys.length; i3 += 2) {
                if (i3 + 1 < keys.length) {
                    TableInfoAdapter tableInfoAdapter = (TableInfoAdapter) this.hmapAdapterList.get(simID).get(label);
                    String[] strArr = new String[4];
                    strArr[0] = keys[i3];
                    if (((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(keys[i3]) == null) {
                        obj2 = "";
                    } else {
                        obj2 = ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(keys[i3]);
                    }
                    strArr[1] = (String) obj2;
                    strArr[2] = keys[i3 + 1];
                    if (((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(keys[i3 + 1]) == null) {
                        obj3 = "";
                    } else {
                        obj3 = ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(keys[i3 + 1]);
                    }
                    strArr[3] = (String) obj3;
                    tableInfoAdapter.add(strArr);
                } else {
                    TableInfoAdapter tableInfoAdapter2 = (TableInfoAdapter) this.hmapAdapterList.get(simID).get(label);
                    String[] strArr2 = new String[4];
                    strArr2[0] = keys[i3];
                    if (((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(keys[i3]) == null) {
                        obj = "";
                    } else {
                        obj = ((LinkedHashMap) this.hmapLabelsList.get(simID).get(label)).get(keys[i3]);
                    }
                    strArr2[1] = (String) obj;
                    strArr2[2] = "";
                    strArr2[3] = "";
                    tableInfoAdapter2.add(strArr2);
                }
            }
        }
    }
}
