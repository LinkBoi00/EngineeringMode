package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.view.View;
import android.widget.ListAdapter;
import com.mediatek.engineermode.Elog;

/* compiled from: MDMComponent */
abstract class NormalTableTasComponent extends NormalTableComponent {
    private int infoValid = 0;

    public NormalTableTasComponent(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        Elog.d("EmInfo/MDMComponent", "getView");
        if (isInfoValid()) {
            clearData();
            TableInfoAdapter tableInfoAdapter = this.mAdapter;
            tableInfoAdapter.add(new String[]{"Use " + getName().replace("TAS", "UTAS")});
        } else {
            if (this.mLabels == null) {
                this.mLabels = getLabels();
            }
            if (this.mAdapter.getCount() == 0) {
                for (int i = 0; i < this.mLabels.length; i++) {
                    this.mAdapter.add(new String[]{this.mLabels[i], ""});
                }
            }
        }
        this.mListView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        return this.mListView;
    }

    public boolean isInfoValid() {
        if (this.infoValid == 1) {
            return true;
        }
        return false;
    }

    public void setInfoValid(int infoValid2) {
        this.infoValid = infoValid2;
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
        this.mListView.setAdapter((ListAdapter) null);
        setInfoValid(0);
    }
}
