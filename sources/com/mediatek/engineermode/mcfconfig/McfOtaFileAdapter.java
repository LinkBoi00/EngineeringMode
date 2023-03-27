package com.mediatek.engineermode.mcfconfig;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

class McfOtaFileAdapter extends ArrayAdapter<FileInfo> {
    private final Activity context;
    int lineNum = 0;
    /* access modifiers changed from: private */
    public ArrayList<FileInfo> list;
    int listPosititon;

    public McfOtaFileAdapter(Activity context2, ArrayList<FileInfo> list2) {
        super(context2, R.layout.em_mcf_file_row, list2);
        this.context = context2;
        this.list = list2;
    }

    public void refresh(ArrayList<FileInfo> list2) {
        this.list = list2;
        notifyDataSetChanged();
    }

    public ArrayList<FileInfo> getList() {
        return this.list;
    }

    public void resetAdapter(ArrayList<FileInfo> list2) {
        this.lineNum = 0;
        this.list = list2;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        protected CheckBox isSelected;
        protected TextView otaFilePath;

        ViewHolder() {
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter$ViewHolder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r7, android.view.View r8, android.view.ViewGroup r9) {
        /*
            r6 = this;
            r6.listPosititon = r7
            r0 = 0
            if (r8 != 0) goto L_0x0053
            com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter$1 r1 = new com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter$1
            r1.<init>()
            android.app.Activity r2 = r6.context
            android.view.LayoutInflater r2 = r2.getLayoutInflater()
            r3 = 2131230809(0x7f080059, float:1.8077681E38)
            r4 = 0
            android.view.View r8 = r2.inflate(r3, r4)
            com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter$ViewHolder r3 = new com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter$ViewHolder
            r3.<init>()
            r0 = r3
            r3 = 2131101083(0x7f06059b, float:1.7814566E38)
            android.view.View r4 = r8.findViewById(r3)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r0.otaFilePath = r4
            r4 = 2131101084(0x7f06059c, float:1.7814568E38)
            android.view.View r5 = r8.findViewById(r4)
            android.widget.CheckBox r5 = (android.widget.CheckBox) r5
            r0.isSelected = r5
            r8.setTag(r0)
            android.widget.CheckBox r5 = r0.isSelected
            r8.setTag(r4, r5)
            android.widget.TextView r4 = r0.otaFilePath
            r8.setTag(r3, r4)
            android.widget.TextView r3 = r0.otaFilePath
            r3.setOnClickListener(r1)
            android.widget.CheckBox r3 = r0.isSelected
            com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter$2 r4 = new com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter$2
            r4.<init>()
            r3.setOnCheckedChangeListener(r4)
            goto L_0x005a
        L_0x0053:
            java.lang.Object r1 = r8.getTag()
            r0 = r1
            com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter$ViewHolder r0 = (com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter.ViewHolder) r0
        L_0x005a:
            android.widget.TextView r1 = r0.otaFilePath
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            r1.setTag(r2)
            android.widget.CheckBox r1 = r0.isSelected
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            r1.setTag(r2)
            android.widget.TextView r1 = r0.otaFilePath
            java.util.ArrayList<com.mediatek.engineermode.mcfconfig.FileInfo> r2 = r6.list
            java.lang.Object r2 = r2.get(r7)
            com.mediatek.engineermode.mcfconfig.FileInfo r2 = (com.mediatek.engineermode.mcfconfig.FileInfo) r2
            java.lang.String r2 = r2.getFileAbsolutePath()
            r1.setText(r2)
            android.widget.CheckBox r1 = r0.isSelected
            java.util.ArrayList<com.mediatek.engineermode.mcfconfig.FileInfo> r2 = r6.list
            java.lang.Object r2 = r2.get(r7)
            com.mediatek.engineermode.mcfconfig.FileInfo r2 = (com.mediatek.engineermode.mcfconfig.FileInfo) r2
            boolean r2 = r2.isChecked()
            r1.setChecked(r2)
            int r1 = r6.lineNum
            if (r1 != 0) goto L_0x0098
            android.widget.CheckBox r1 = r0.isSelected
            r2 = 4
            r1.setVisibility(r2)
        L_0x0098:
            int r1 = r6.lineNum
            int r1 = r1 + 1
            r6.lineNum = r1
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mcfconfig.McfOtaFileAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }
}
