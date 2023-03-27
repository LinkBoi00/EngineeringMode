package com.mediatek.engineermode.rfdesense;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RfDesenseRatAdapter extends BaseAdapter {
    private static final String TAG = "RfDesense/RatAdapter";
    private Context mContext = null;
    private LayoutInflater mLayoutInflater = null;
    /* access modifiers changed from: private */
    public List<RfDesenseRatInfo> mRatList = null;

    public RfDesenseRatAdapter(Context context, ArrayList<RfDesenseRatInfo> list) {
        this.mContext = context;
        this.mRatList = list;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public void setData(List<RfDesenseRatInfo> list) {
        this.mRatList = list;
    }

    public int getCount() {
        List<RfDesenseRatInfo> list = this.mRatList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        List<RfDesenseRatInfo> list = this.mRatList;
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.mediatek.engineermode.rfdesense.RfDesenseRatAdapter$ViewHolder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r5, android.view.View r6, android.view.ViewGroup r7) {
        /*
            r4 = this;
            r0 = 0
            if (r6 != 0) goto L_0x0057
            android.view.LayoutInflater r1 = r4.mLayoutInflater
            r2 = 2131230931(0x7f0800d3, float:1.8077929E38)
            r3 = 0
            android.view.View r6 = r1.inflate(r2, r3)
            com.mediatek.engineermode.rfdesense.RfDesenseRatAdapter$ViewHolder r1 = new com.mediatek.engineermode.rfdesense.RfDesenseRatAdapter$ViewHolder
            r1.<init>()
            r0 = r1
            r1 = 2131101197(0x7f06060d, float:1.7814797E38)
            android.view.View r2 = r6.findViewById(r1)
            android.widget.CheckBox r2 = (android.widget.CheckBox) r2
            r0.RatCheckState = r2
            r2 = 2131101195(0x7f06060b, float:1.7814793E38)
            android.view.View r2 = r6.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r0.rat_name = r2
            r2 = 2131101192(0x7f060608, float:1.7814787E38)
            android.view.View r2 = r6.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r0.rat_cmd = r2
            r6.setTag(r0)
            android.widget.CheckBox r2 = r0.RatCheckState
            r6.setTag(r1, r2)
            android.widget.CheckBox r1 = r0.RatCheckState
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            r1.setTag(r2)
            android.widget.TextView r1 = r0.rat_cmd
            r2 = 8
            r1.setVisibility(r2)
            android.widget.CheckBox r1 = r0.RatCheckState
            com.mediatek.engineermode.rfdesense.RfDesenseRatAdapter$1 r2 = new com.mediatek.engineermode.rfdesense.RfDesenseRatAdapter$1
            r2.<init>()
            r1.setOnClickListener(r2)
            goto L_0x005e
        L_0x0057:
            java.lang.Object r1 = r6.getTag()
            r0 = r1
            com.mediatek.engineermode.rfdesense.RfDesenseRatAdapter$ViewHolder r0 = (com.mediatek.engineermode.rfdesense.RfDesenseRatAdapter.ViewHolder) r0
        L_0x005e:
            android.widget.TextView r1 = r0.rat_name
            java.util.List<com.mediatek.engineermode.rfdesense.RfDesenseRatInfo> r2 = r4.mRatList
            java.lang.Object r2 = r2.get(r5)
            com.mediatek.engineermode.rfdesense.RfDesenseRatInfo r2 = (com.mediatek.engineermode.rfdesense.RfDesenseRatInfo) r2
            java.lang.String r2 = r2.getRatName()
            r1.setText(r2)
            android.widget.TextView r1 = r0.rat_cmd
            java.util.List<com.mediatek.engineermode.rfdesense.RfDesenseRatInfo> r2 = r4.mRatList
            java.lang.Object r2 = r2.get(r5)
            com.mediatek.engineermode.rfdesense.RfDesenseRatInfo r2 = (com.mediatek.engineermode.rfdesense.RfDesenseRatInfo) r2
            java.lang.String r2 = r2.getRatCmdStart()
            r1.setText(r2)
            android.widget.CheckBox r1 = r0.RatCheckState
            java.util.List<com.mediatek.engineermode.rfdesense.RfDesenseRatInfo> r2 = r4.mRatList
            java.lang.Object r2 = r2.get(r5)
            com.mediatek.engineermode.rfdesense.RfDesenseRatInfo r2 = (com.mediatek.engineermode.rfdesense.RfDesenseRatInfo) r2
            java.lang.Boolean r2 = r2.getRatCheckState()
            boolean r2 = r2.booleanValue()
            r1.setChecked(r2)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.rfdesense.RfDesenseRatAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    class ViewHolder {
        CheckBox RatCheckState;
        TextView rat_cmd;
        TextView rat_name;

        ViewHolder() {
        }
    }
}
