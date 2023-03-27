package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import java.lang.reflect.Array;

/* compiled from: MDMComponent */
abstract class ArrayTableComponent extends MDMComponent {
    TableInfoAdapter mAdapter;
    String[] mLabels;
    ListView mListView;

    /* access modifiers changed from: package-private */
    public abstract String[] getLabels();

    public ArrayTableComponent(Activity context) {
        super(context);
        if (this.mAdapter == null) {
            this.mAdapter = new TableInfoAdapter(this.mActivity);
        }
        if (this.mListView == null) {
            this.mListView = new ListView(this.mActivity);
        }
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        if (this.mLabels == null) {
            this.mLabels = getLabels();
        }
        if (this.mAdapter.getCount() == 0) {
            this.mAdapter.add(this.mLabels);
        }
        this.mListView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        return this.mListView;
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
        this.mListView.setAdapter((ListAdapter) null);
    }

    /* access modifiers changed from: package-private */
    public void clearData() {
        if (this.mLabels == null) {
            this.mLabels = getLabels();
        }
        this.mAdapter.clear();
        this.mAdapter.add(this.mLabels);
        this.mAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public void addData(Object... data) {
        String[] strings = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            strings[i] = String.valueOf(data[i]);
        }
        addData(strings);
    }

    /* access modifiers changed from: package-private */
    public void addData(String[] data) {
        this.mAdapter.add(data);
        this.mAdapter.notifyDataSetChanged();
        this.mListView.setAdapter(this.mAdapter);
    }

    /* access modifiers changed from: package-private */
    public void setDataAtPosition(int position, String[] data) {
        if (this.mLabels == null) {
            this.mLabels = getLabels();
        }
        String[] strArr = this.mLabels;
        if (position >= strArr.length) {
            Elog.e("EmInfo/MDMComponent", "Set data position error: " + position);
            return;
        }
        int length = data.length;
        int[] iArr = new int[2];
        iArr[1] = strArr.length;
        iArr[0] = length;
        String[][] items = (String[][]) Array.newInstance(String.class, iArr);
        for (int i = 0; i < data.length; i++) {
            if (i + 1 < this.mAdapter.getCount()) {
                items[i] = (String[]) this.mAdapter.getItem(i + 1);
            }
            items[i][position] = data[i];
        }
        this.mAdapter.clear();
        this.mAdapter.add(this.mLabels);
        for (int i2 = 0; i2 < data.length; i2++) {
            this.mAdapter.add(items[i2]);
        }
        this.mAdapter.notifyDataSetChanged();
        this.mListView.setAdapter(this.mAdapter);
    }

    /* access modifiers changed from: package-private */
    public void setDataAtPosition(int row, int column, Object data) {
        if (this.mLabels == null) {
            this.mLabels = getLabels();
        }
        if (column >= this.mLabels.length) {
            Elog.e("EmInfo/MDMComponent", "Set data position error: " + row + ", " + column);
            return;
        }
        if (this.mAdapter.getCount() == 0) {
            this.mAdapter.add(this.mLabels);
        }
        if (row + 1 >= this.mAdapter.getCount()) {
            for (int i = this.mAdapter.getCount(); i <= row + 1; i++) {
                this.mAdapter.add(new String[this.mLabels.length]);
            }
        }
        ((String[]) this.mAdapter.getItem(row + 1))[column] = data.toString();
        this.mListView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public void addData(String[][] data) {
        if (this.mLabels == null) {
            this.mLabels = getLabels();
        }
        this.mAdapter.clear();
        this.mAdapter.add(this.mLabels);
        int i = 0;
        while (i < data.length && data[i] != null) {
            this.mAdapter.add(data[i]);
            i++;
        }
        this.mAdapter.notifyDataSetChanged();
        this.mListView.setAdapter(this.mAdapter);
    }
}
