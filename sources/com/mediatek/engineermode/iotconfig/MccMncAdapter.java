package com.mediatek.engineermode.iotconfig;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

/* compiled from: WfcConfigFragment */
class MccMncAdapter extends ArrayAdapter<String> {
    private final Activity context;
    int listPosititon;
    private ArrayList<String> mccMncList;

    public MccMncAdapter(Activity context2, ArrayList<String> mccMncList2) {
        super(context2, R.layout.iot_wfc_row);
        this.context = context2;
        this.mccMncList = mccMncList2;
    }

    public void refresh(ArrayList<String> mccMncList2) {
        this.mccMncList = mccMncList2;
        notifyDataSetChanged();
    }

    public ArrayList<String> getList() {
        return this.mccMncList;
    }

    /* compiled from: WfcConfigFragment */
    static class ViewHolder {
        protected TextView text;

        ViewHolder() {
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.mediatek.engineermode.iotconfig.MccMncAdapter$ViewHolder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r5, android.view.View r6, android.view.ViewGroup r7) {
        /*
            r4 = this;
            r4.listPosititon = r5
            r0 = 0
            if (r6 != 0) goto L_0x002e
            android.app.Activity r1 = r4.context
            android.view.LayoutInflater r1 = r1.getLayoutInflater()
            r2 = 2131230859(0x7f08008b, float:1.8077783E38)
            r3 = 0
            android.view.View r6 = r1.inflate(r2, r3)
            com.mediatek.engineermode.iotconfig.MccMncAdapter$ViewHolder r2 = new com.mediatek.engineermode.iotconfig.MccMncAdapter$ViewHolder
            r2.<init>()
            r0 = r2
            r2 = 2131100370(0x7f0602d2, float:1.781312E38)
            android.view.View r3 = r6.findViewById(r2)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r0.text = r3
            r6.setTag(r0)
            android.widget.TextView r3 = r0.text
            r6.setTag(r2, r3)
            goto L_0x0035
        L_0x002e:
            java.lang.Object r1 = r6.getTag()
            r0 = r1
            com.mediatek.engineermode.iotconfig.MccMncAdapter$ViewHolder r0 = (com.mediatek.engineermode.iotconfig.MccMncAdapter.ViewHolder) r0
        L_0x0035:
            java.util.ArrayList<java.lang.String> r1 = r4.mccMncList
            if (r1 == 0) goto L_0x0047
            android.widget.TextView r1 = r0.text
            java.util.ArrayList<java.lang.String> r2 = r4.mccMncList
            java.lang.Object r2 = r2.get(r5)
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r1.setText(r2)
            goto L_0x004e
        L_0x0047:
            android.widget.TextView r1 = r0.text
            java.lang.String r2 = ""
            r1.setText(r2)
        L_0x004e:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.iotconfig.MccMncAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }
}
