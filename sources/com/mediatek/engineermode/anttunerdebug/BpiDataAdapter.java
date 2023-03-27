package com.mediatek.engineermode.anttunerdebug;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.mediatek.engineermode.R;
import java.util.List;

/* compiled from: AntTunerDebugBPI */
class BpiDataAdapter extends ArrayAdapter<BpiBinaryData> {
    private final Activity context;
    /* access modifiers changed from: private */
    public List<BpiBinaryData> list;

    public BpiDataAdapter(Activity context2, List<BpiBinaryData> list2) {
        super(context2, R.layout.ant_tuner_debug_bpi_row, list2);
        this.context = context2;
        this.list = list2;
    }

    public void refresh(List<BpiBinaryData> list2) {
        this.list = list2;
        notifyDataSetChanged();
    }

    public List<BpiBinaryData> getList() {
        return this.list;
    }

    /* compiled from: AntTunerDebugBPI */
    static class ViewHolder {
        protected TextView text;
        protected Spinner type;
        protected EditText value;

        ViewHolder() {
        }
    }

    /* compiled from: AntTunerDebugBPI */
    class EditTextWatcher implements TextWatcher {
        protected ViewHolder viewHolder;

        EditTextWatcher(ViewHolder viewHolder2) {
            this.viewHolder = viewHolder2;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            ((BpiBinaryData) BpiDataAdapter.this.list.get(((Integer) this.viewHolder.value.getTag()).intValue())).setValue(s.toString());
        }
    }

    /* compiled from: AntTunerDebugBPI */
    class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
        protected ViewHolder viewHolder;

        ItemSelectedListener(ViewHolder viewHolder2) {
            this.viewHolder = viewHolder2;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            ((BpiBinaryData) BpiDataAdapter.this.list.get(((Integer) this.viewHolder.type.getTag()).intValue())).setSelected(pos != 0);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.mediatek.engineermode.anttunerdebug.BpiDataAdapter$ViewHolder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r10, android.view.View r11, android.view.ViewGroup r12) {
        /*
            r9 = this;
            r0 = 0
            if (r11 != 0) goto L_0x0091
            java.lang.String r1 = "Low"
            java.lang.String r2 = "Hign"
            java.lang.String[] r1 = new java.lang.String[]{r1, r2}
            android.app.Activity r2 = r9.context
            android.view.LayoutInflater r2 = r2.getLayoutInflater()
            r3 = 2131230723(0x7f080003, float:1.8077507E38)
            r4 = 0
            android.view.View r11 = r2.inflate(r3, r4)
            com.mediatek.engineermode.anttunerdebug.BpiDataAdapter$ViewHolder r3 = new com.mediatek.engineermode.anttunerdebug.BpiDataAdapter$ViewHolder
            r3.<init>()
            r0 = r3
            r3 = 2131100090(0x7f0601ba, float:1.7812552E38)
            android.view.View r4 = r11.findViewById(r3)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r0.text = r4
            r4 = 2131100092(0x7f0601bc, float:1.7812556E38)
            android.view.View r5 = r11.findViewById(r4)
            android.widget.EditText r5 = (android.widget.EditText) r5
            r0.value = r5
            r5 = 2131100091(0x7f0601bb, float:1.7812554E38)
            android.view.View r6 = r11.findViewById(r5)
            android.widget.Spinner r6 = (android.widget.Spinner) r6
            r0.type = r6
            android.widget.ArrayAdapter r6 = new android.widget.ArrayAdapter
            android.app.Activity r7 = r9.context
            r8 = 17367048(0x1090008, float:2.5162948E-38)
            r6.<init>(r7, r8, r1)
            r7 = 17367049(0x1090009, float:2.516295E-38)
            r6.setDropDownViewResource(r7)
            android.widget.Spinner r7 = r0.type
            r7.setAdapter(r6)
            android.widget.EditText r7 = r0.value
            java.lang.Integer r8 = java.lang.Integer.valueOf(r10)
            r7.setTag(r8)
            android.widget.Spinner r7 = r0.type
            java.lang.Integer r8 = java.lang.Integer.valueOf(r10)
            r7.setTag(r8)
            android.widget.EditText r7 = r0.value
            com.mediatek.engineermode.anttunerdebug.BpiDataAdapter$EditTextWatcher r8 = new com.mediatek.engineermode.anttunerdebug.BpiDataAdapter$EditTextWatcher
            r8.<init>(r0)
            r7.addTextChangedListener(r8)
            android.widget.Spinner r7 = r0.type
            com.mediatek.engineermode.anttunerdebug.BpiDataAdapter$ItemSelectedListener r8 = new com.mediatek.engineermode.anttunerdebug.BpiDataAdapter$ItemSelectedListener
            r8.<init>(r0)
            r7.setOnItemSelectedListener(r8)
            r11.setTag(r0)
            android.widget.TextView r7 = r0.text
            r11.setTag(r3, r7)
            android.widget.EditText r3 = r0.value
            r11.setTag(r4, r3)
            android.widget.Spinner r3 = r0.type
            r11.setTag(r5, r3)
            goto L_0x00aa
        L_0x0091:
            java.lang.Object r1 = r11.getTag()
            r0 = r1
            com.mediatek.engineermode.anttunerdebug.BpiDataAdapter$ViewHolder r0 = (com.mediatek.engineermode.anttunerdebug.BpiDataAdapter.ViewHolder) r0
            android.widget.EditText r1 = r0.value
            java.lang.Integer r2 = java.lang.Integer.valueOf(r10)
            r1.setTag(r2)
            android.widget.Spinner r1 = r0.type
            java.lang.Integer r2 = java.lang.Integer.valueOf(r10)
            r1.setTag(r2)
        L_0x00aa:
            android.widget.TextView r1 = r0.text
            java.util.List<com.mediatek.engineermode.anttunerdebug.BpiBinaryData> r2 = r9.list
            java.lang.Object r2 = r2.get(r10)
            java.lang.String r3 = ""
            if (r2 == 0) goto L_0x00c3
            java.util.List<com.mediatek.engineermode.anttunerdebug.BpiBinaryData> r2 = r9.list
            java.lang.Object r2 = r2.get(r10)
            com.mediatek.engineermode.anttunerdebug.BpiBinaryData r2 = (com.mediatek.engineermode.anttunerdebug.BpiBinaryData) r2
            java.lang.String r2 = r2.getName()
            goto L_0x00c4
        L_0x00c3:
            r2 = r3
        L_0x00c4:
            r1.setText(r2)
            android.widget.EditText r1 = r0.value
            java.util.List<com.mediatek.engineermode.anttunerdebug.BpiBinaryData> r2 = r9.list
            java.lang.Object r2 = r2.get(r10)
            if (r2 == 0) goto L_0x00de
            java.util.List<com.mediatek.engineermode.anttunerdebug.BpiBinaryData> r2 = r9.list
            java.lang.Object r2 = r2.get(r10)
            com.mediatek.engineermode.anttunerdebug.BpiBinaryData r2 = (com.mediatek.engineermode.anttunerdebug.BpiBinaryData) r2
            java.lang.String r3 = r2.getValue()
            goto L_0x00df
        L_0x00de:
        L_0x00df:
            r1.setText(r3)
            android.widget.Spinner r1 = r0.type
            java.util.List<com.mediatek.engineermode.anttunerdebug.BpiBinaryData> r2 = r9.list
            java.lang.Object r2 = r2.get(r10)
            if (r2 == 0) goto L_0x00fc
            java.util.List<com.mediatek.engineermode.anttunerdebug.BpiBinaryData> r2 = r9.list
            java.lang.Object r2 = r2.get(r10)
            com.mediatek.engineermode.anttunerdebug.BpiBinaryData r2 = (com.mediatek.engineermode.anttunerdebug.BpiBinaryData) r2
            boolean r2 = r2.isSelected()
            if (r2 == 0) goto L_0x00fc
            r2 = 1
            goto L_0x00fd
        L_0x00fc:
            r2 = 0
        L_0x00fd:
            r1.setSelection(r2)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.anttunerdebug.BpiDataAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }
}
