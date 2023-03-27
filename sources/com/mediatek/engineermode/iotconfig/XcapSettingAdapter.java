package com.mediatek.engineermode.iotconfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.mediatek.engineermode.R;
import java.util.List;

/* compiled from: XCAPConfigFragment */
class XcapSettingAdapter extends ArrayAdapter<XCAPModel> {
    private final Activity context;
    /* access modifiers changed from: private */
    public List<XCAPModel> list;
    int listPosititon;
    private View.OnClickListener onClickLinstener = new View.OnClickListener() {
        public void onClick(View v) {
            XcapSettingAdapter.this.showConfirmDialog(v, ((Integer) v.getTag()).intValue());
        }
    };

    public XcapSettingAdapter(Activity context2, List<XCAPModel> list2) {
        super(context2, R.layout.iot_xcap_row, list2);
        this.context = context2;
        this.list = list2;
    }

    public void refresh(List<XCAPModel> list2) {
        this.list = list2;
        notifyDataSetChanged();
    }

    public List<XCAPModel> getList() {
        return this.list;
    }

    /* compiled from: XCAPConfigFragment */
    static class ViewHolder {
        protected Button btnSet;
        protected CheckBox checkbox;
        protected TextView text;
        protected EditText value;

        ViewHolder() {
        }
    }

    public void showConfirmDialog(View view, final int getPosition) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.context);
        alertDialogBuilder.setCancelable(false).setMessage(this.context.getResources().getString(R.string.set_xcap_dialog_message)).setTitle(this.context.getResources().getString(R.string.set_xcap_dialog_title)).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((XCAPModel) XcapSettingAdapter.this.list.get(getPosition)).setConfiged(true);
                ((XCAPModel) XcapSettingAdapter.this.list.get(getPosition)).setSelected(false);
                XcapSettingAdapter.this.notifyDataSetChanged();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                ((XCAPModel) XcapSettingAdapter.this.list.get(getPosition)).setConfiged(false);
            }
        });
        alertDialogBuilder.create().show();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.mediatek.engineermode.iotconfig.XcapSettingAdapter$ViewHolder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(final int r9, android.view.View r10, android.view.ViewGroup r11) {
        /*
            r8 = this;
            r8.listPosititon = r9
            r0 = 0
            if (r10 != 0) goto L_0x0092
            android.app.Activity r1 = r8.context
            android.view.LayoutInflater r1 = r1.getLayoutInflater()
            r2 = 2131230861(0x7f08008d, float:1.8077787E38)
            r3 = 0
            android.view.View r10 = r1.inflate(r2, r3)
            com.mediatek.engineermode.iotconfig.XcapSettingAdapter$ViewHolder r2 = new com.mediatek.engineermode.iotconfig.XcapSettingAdapter$ViewHolder
            r2.<init>()
            r0 = r2
            r2 = 2131101778(0x7f060852, float:1.7815975E38)
            android.view.View r3 = r10.findViewById(r2)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r0.text = r3
            r3 = 2131101776(0x7f060850, float:1.7815971E38)
            android.view.View r4 = r10.findViewById(r3)
            android.widget.CheckBox r4 = (android.widget.CheckBox) r4
            r0.checkbox = r4
            r4 = 2131101780(0x7f060854, float:1.781598E38)
            android.view.View r5 = r10.findViewById(r4)
            android.widget.EditText r5 = (android.widget.EditText) r5
            r0.value = r5
            r5 = 2131101297(0x7f060671, float:1.7815E38)
            android.view.View r6 = r10.findViewById(r5)
            android.widget.Button r6 = (android.widget.Button) r6
            r0.btnSet = r6
            android.widget.Button r6 = r0.btnSet
            android.view.View$OnClickListener r7 = r8.onClickLinstener
            r6.setOnClickListener(r7)
            android.widget.CheckBox r6 = r0.checkbox
            com.mediatek.engineermode.iotconfig.XcapSettingAdapter$4 r7 = new com.mediatek.engineermode.iotconfig.XcapSettingAdapter$4
            r7.<init>()
            r6.setOnCheckedChangeListener(r7)
            android.widget.EditText r6 = r0.value
            com.mediatek.engineermode.iotconfig.XcapSettingAdapter$5 r7 = new com.mediatek.engineermode.iotconfig.XcapSettingAdapter$5
            r7.<init>(r9)
            r6.addTextChangedListener(r7)
            android.widget.EditText r6 = r0.value
            r7 = 6
            r6.setImeOptions(r7)
            android.widget.EditText r6 = r0.value
            r7 = 1
            r6.setRawInputType(r7)
            android.widget.EditText r6 = r0.value
            com.mediatek.engineermode.iotconfig.XcapSettingAdapter$6 r7 = new com.mediatek.engineermode.iotconfig.XcapSettingAdapter$6
            r7.<init>()
            r6.setOnTouchListener(r7)
            r10.setTag(r0)
            android.widget.TextView r6 = r0.text
            r10.setTag(r2, r6)
            android.widget.EditText r2 = r0.value
            r10.setTag(r4, r2)
            android.widget.CheckBox r2 = r0.checkbox
            r10.setTag(r3, r2)
            android.widget.Button r2 = r0.btnSet
            r10.setTag(r5, r2)
            goto L_0x0099
        L_0x0092:
            java.lang.Object r1 = r10.getTag()
            r0 = r1
            com.mediatek.engineermode.iotconfig.XcapSettingAdapter$ViewHolder r0 = (com.mediatek.engineermode.iotconfig.XcapSettingAdapter.ViewHolder) r0
        L_0x0099:
            android.widget.CheckBox r1 = r0.checkbox
            java.lang.Integer r2 = java.lang.Integer.valueOf(r9)
            r1.setTag(r2)
            android.widget.Button r1 = r0.btnSet
            java.lang.Integer r2 = java.lang.Integer.valueOf(r9)
            r1.setTag(r2)
            android.widget.EditText r1 = r0.value
            java.lang.Integer r2 = java.lang.Integer.valueOf(r9)
            r1.setTag(r2)
            android.widget.TextView r1 = r0.text
            java.lang.Integer r2 = java.lang.Integer.valueOf(r9)
            r1.setTag(r2)
            android.widget.TextView r1 = r0.text
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.util.List<com.mediatek.engineermode.iotconfig.XCAPModel> r3 = r8.list
            java.lang.Object r3 = r3.get(r9)
            com.mediatek.engineermode.iotconfig.XCAPModel r3 = (com.mediatek.engineermode.iotconfig.XCAPModel) r3
            java.lang.String r3 = r3.getName()
            r2.append(r3)
            java.lang.String r3 = "("
            r2.append(r3)
            java.util.List<com.mediatek.engineermode.iotconfig.XCAPModel> r3 = r8.list
            java.lang.Object r3 = r3.get(r9)
            com.mediatek.engineermode.iotconfig.XCAPModel r3 = (com.mediatek.engineermode.iotconfig.XCAPModel) r3
            java.lang.String r3 = r3.getType()
            r2.append(r3)
            java.lang.String r3 = ")"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "(boolean)"
            java.lang.String r4 = "?"
            java.lang.String r2 = r2.replace(r3, r4)
            r1.setText(r2)
            r1 = 2131101777(0x7f060851, float:1.7815973E38)
            android.view.View r1 = r10.findViewById(r1)
            android.widget.LinearLayout r1 = (android.widget.LinearLayout) r1
            java.util.List<com.mediatek.engineermode.iotconfig.XCAPModel> r2 = r8.list
            java.lang.Object r2 = r2.get(r9)
            com.mediatek.engineermode.iotconfig.XCAPModel r2 = (com.mediatek.engineermode.iotconfig.XCAPModel) r2
            java.lang.String r2 = r2.getValue()
            java.lang.String r3 = ""
            if (r2 == 0) goto L_0x0149
            java.util.List<com.mediatek.engineermode.iotconfig.XCAPModel> r2 = r8.list
            java.lang.Object r2 = r2.get(r9)
            com.mediatek.engineermode.iotconfig.XCAPModel r2 = (com.mediatek.engineermode.iotconfig.XCAPModel) r2
            java.lang.String r2 = r2.getValue()
            java.lang.String r4 = "-1"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x0149
            android.widget.EditText r2 = r0.value
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.util.List<com.mediatek.engineermode.iotconfig.XCAPModel> r5 = r8.list
            java.lang.Object r5 = r5.get(r9)
            com.mediatek.engineermode.iotconfig.XCAPModel r5 = (com.mediatek.engineermode.iotconfig.XCAPModel) r5
            java.lang.String r5 = r5.getValue()
            r4.append(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            r2.setText(r3)
            goto L_0x014e
        L_0x0149:
            android.widget.EditText r2 = r0.value
            r2.setText(r3)
        L_0x014e:
            java.util.List<com.mediatek.engineermode.iotconfig.XCAPModel> r2 = r8.list
            java.lang.Object r2 = r2.get(r9)
            com.mediatek.engineermode.iotconfig.XCAPModel r2 = (com.mediatek.engineermode.iotconfig.XCAPModel) r2
            java.lang.String r2 = r2.getType()
            java.lang.String r3 = "boolean"
            boolean r2 = r2.equals(r3)
            r3 = 8
            r4 = 0
            if (r2 == 0) goto L_0x01a3
            r1.setVisibility(r4)
            android.widget.EditText r2 = r0.value
            r2.setVisibility(r3)
            java.util.List<com.mediatek.engineermode.iotconfig.XCAPModel> r2 = r8.list
            java.lang.Object r2 = r2.get(r9)
            com.mediatek.engineermode.iotconfig.XCAPModel r2 = (com.mediatek.engineermode.iotconfig.XCAPModel) r2
            boolean r2 = r2.isConfiged()
            r3 = 4
            if (r2 == 0) goto L_0x0198
            android.widget.CheckBox r2 = r0.checkbox
            r2.setVisibility(r4)
            android.widget.Button r2 = r0.btnSet
            r2.setVisibility(r3)
            android.widget.CheckBox r2 = r0.checkbox
            java.util.List<com.mediatek.engineermode.iotconfig.XCAPModel> r3 = r8.list
            java.lang.Object r3 = r3.get(r9)
            com.mediatek.engineermode.iotconfig.XCAPModel r3 = (com.mediatek.engineermode.iotconfig.XCAPModel) r3
            boolean r3 = r3.isSelected()
            r2.setChecked(r3)
            goto L_0x01ab
        L_0x0198:
            android.widget.CheckBox r2 = r0.checkbox
            r2.setVisibility(r3)
            android.widget.Button r2 = r0.btnSet
            r2.setVisibility(r4)
            goto L_0x01ab
        L_0x01a3:
            r1.setVisibility(r3)
            android.widget.EditText r2 = r0.value
            r2.setVisibility(r4)
        L_0x01ab:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.iotconfig.XcapSettingAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }
}
