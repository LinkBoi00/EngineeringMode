package com.mediatek.engineermode.siminfo;

import android.app.Activity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;

/* compiled from: SimInfoUpdate */
abstract class NormalTableComponent implements Comparable<NormalTableComponent> {
    private static final String TAG = "siminfo";
    protected Activity mActivity;
    TableInfoAdapter mAdapter;
    String[] mLabels;
    ListView mListView;

    /* access modifiers changed from: package-private */
    public abstract String[] getLabels();

    /* access modifiers changed from: package-private */
    public abstract void update(int i, String str);

    public NormalTableComponent(Activity context) {
        this.mActivity = context;
        if (this.mAdapter == null) {
            this.mAdapter = new TableInfoAdapter(this.mActivity);
        }
        if (this.mListView == null) {
            this.mListView = new ListView(this.mActivity);
        }
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        Elog.d(TAG, "getView");
        if (this.mLabels == null) {
            this.mLabels = getLabels();
        }
        if (this.mAdapter.getCount() == 0) {
            int i = 0;
            while (true) {
                String[] strArr = this.mLabels;
                if (i >= strArr.length) {
                    break;
                }
                this.mAdapter.add(new String[]{strArr[i], ""});
                i++;
            }
        }
        this.mListView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        return this.mListView;
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
        Elog.d(TAG, "removeView");
        this.mListView.setAdapter((ListAdapter) null);
    }

    /* access modifiers changed from: package-private */
    public void clearData() {
        this.mAdapter.clear();
    }

    /* access modifiers changed from: package-private */
    public void addData(Object... data) {
        for (Object valueOf : data) {
            addData(String.valueOf(valueOf));
        }
    }

    /* access modifiers changed from: package-private */
    public void addDataAtPostion(int pos, Object... data) {
        for (Object valueOf : data) {
            addDataAtPostion(pos, String.valueOf(valueOf));
        }
    }

    /* access modifiers changed from: package-private */
    public void addData(String data) {
        this.mLabels = getLabels();
        int position = this.mAdapter.getCount();
        TableInfoAdapter tableInfoAdapter = this.mAdapter;
        String[] strArr = new String[2];
        String[] strArr2 = this.mLabels;
        strArr[0] = strArr2[position % strArr2.length];
        strArr[1] = data == null ? "" : data;
        tableInfoAdapter.add(strArr);
        this.mAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public void addDataAtPostion(int pos, String data) {
        if (this.mLabels == null) {
            this.mLabels = getLabels();
        }
        for (int i = this.mAdapter.getCount(); i <= pos; i++) {
            this.mAdapter.add(new String[]{this.mLabels[i], ""});
        }
        ((String[]) this.mAdapter.getItem(pos))[1] = data;
        this.mAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public void notifyDataSetChanged() {
        if (this.mLabels == null) {
            this.mLabels = getLabels();
        }
        if (this.mAdapter.getCount() < this.mLabels.length) {
            int i = this.mAdapter.getCount();
            while (true) {
                String[] strArr = this.mLabels;
                if (i >= strArr.length) {
                    break;
                }
                this.mAdapter.add(new String[]{strArr[i], ""});
                i++;
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }
}
