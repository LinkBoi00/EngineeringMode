package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/* compiled from: MDMComponent */
abstract class NormalTableComponent extends MDMComponent {
    TableInfoAdapter mAdapter;
    String[] mLabels;
    ListView mListView;
    int scrollPos;
    int scrollTop;

    /* access modifiers changed from: package-private */
    public abstract String[] getLabels();

    public NormalTableComponent(Activity context) {
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
        this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int firstVisible;
            int lastVisibleIndex;
            int totalCount;
            int visibleCount;

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case 0:
                        NormalTableComponent normalTableComponent = NormalTableComponent.this;
                        normalTableComponent.scrollPos = normalTableComponent.mListView.getFirstVisiblePosition();
                        int i = 0;
                        View viewItem = NormalTableComponent.this.mListView.getChildAt(0);
                        NormalTableComponent normalTableComponent2 = NormalTableComponent.this;
                        if (viewItem != null) {
                            i = viewItem.getTop();
                        }
                        normalTableComponent2.scrollTop = i;
                        return;
                    default:
                        return;
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.firstVisible = firstVisibleItem;
                this.visibleCount = visibleItemCount;
                this.totalCount = totalItemCount;
                this.lastVisibleIndex = (firstVisibleItem + visibleItemCount) - 1;
            }
        });
        return this.mListView;
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
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
        for (int i = 0; i < data.length; i++) {
            pos += i;
            addDataAtPostion(pos, String.valueOf(data[i]));
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
        this.mListView.setAdapter(this.mAdapter);
        TableInfoAdapter tableInfoAdapter2 = this.mAdapter;
        tableInfoAdapter2.notifyDataSetChanged(this.mListView, tableInfoAdapter2.getCount() - 1);
        this.mListView.setSelectionFromTop(this.scrollPos, this.scrollTop);
    }

    /* access modifiers changed from: package-private */
    public void setData(int pos, Object data) {
        this.mLabels = getLabels();
        String value = String.valueOf(data);
        if (pos < this.mLabels.length) {
            if (pos >= this.mAdapter.getCount()) {
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
            ((String[]) this.mAdapter.getItem(pos))[1] = value;
            this.mListView.setAdapter(this.mAdapter);
            this.mAdapter.notifyDataSetChanged(this.mListView, pos);
            this.mListView.setSelectionFromTop(this.scrollPos, this.scrollTop);
        }
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
        this.mListView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged(this.mListView, pos);
        this.mListView.setSelectionFromTop(this.scrollPos, this.scrollTop);
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
        this.mListView.setSelectionFromTop(this.scrollPos, this.scrollTop);
    }
}
