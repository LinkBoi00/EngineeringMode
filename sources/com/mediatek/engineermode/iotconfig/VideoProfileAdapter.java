package com.mediatek.engineermode.iotconfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.regex.Pattern;

/* compiled from: VilteConfigFragment */
class VideoProfileAdapter extends ArrayAdapter<VideoProfileModel> {
    /* access modifiers changed from: private */
    public final Activity context;
    /* access modifiers changed from: private */
    public ArrayList<VideoProfileModel> list;
    int listPosititon;

    public VideoProfileAdapter(Activity context2, ArrayList<VideoProfileModel> list2) {
        super(context2, R.layout.iot_video_profile_row, list2);
        this.context = context2;
        this.list = list2;
    }

    public void refresh(ArrayList<VideoProfileModel> list2) {
        this.list = list2;
        notifyDataSetChanged();
    }

    public ArrayList<VideoProfileModel> getList() {
        return this.list;
    }

    /* compiled from: VilteConfigFragment */
    static class ViewHolder {
        protected TextView Iinterval;
        protected CheckBox checkbox;
        protected TextView framerate;
        protected TextView height;
        protected TextView level;
        protected TextView maxBitRate;
        protected TextView minBitRate;
        protected TextView name;
        protected TextView profile;
        protected TextView quality;
        protected TextView width;

        ViewHolder() {
        }
    }

    public boolean checkNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (str.equals("") || !pattern.matcher(str).matches() || Integer.parseInt(str) <= 0) {
            return false;
        }
        return true;
    }

    public void showDialog(final View view) {
        View dialogView = LayoutInflater.from(this.context).inflate(R.layout.iot_et_dialog, (ViewGroup) null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.context);
        alertDialogBuilder.setView(dialogView);
        final EditText userInput = (EditText) dialogView.findViewById(R.id.et_dialog_input);
        userInput.setText(((TextView) view).getText());
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                boolean isLegal = true;
                String newValue = userInput.getText().toString().trim().replaceFirst("^0*", "");
                if (VideoProfileAdapter.this.checkNumeric(newValue)) {
                    switch (view.getId()) {
                        case R.id.video_profile_Iinterval:
                            ((VideoProfileModel) VideoProfileAdapter.this.list.get(((Integer) view.getTag()).intValue())).setIinterval(newValue);
                            break;
                        case R.id.video_profile_framerate:
                            ((VideoProfileModel) VideoProfileAdapter.this.list.get(((Integer) view.getTag()).intValue())).setFramerate(newValue);
                            break;
                        case R.id.video_profile_height:
                            ((VideoProfileModel) VideoProfileAdapter.this.list.get(((Integer) view.getTag()).intValue())).setHeight(newValue);
                            break;
                        case R.id.video_profile_maxBitRate:
                            if (Integer.valueOf(newValue).intValue() >= Integer.valueOf(((VideoProfileModel) VideoProfileAdapter.this.list.get(((Integer) view.getTag()).intValue())).getMinBitRate()).intValue()) {
                                ((VideoProfileModel) VideoProfileAdapter.this.list.get(((Integer) view.getTag()).intValue())).setMaxBitRate(newValue);
                                break;
                            } else {
                                isLegal = false;
                                break;
                            }
                        case R.id.video_profile_minBitRate:
                            if (Integer.valueOf(newValue).intValue() <= Integer.valueOf(((VideoProfileModel) VideoProfileAdapter.this.list.get(((Integer) view.getTag()).intValue())).getMaxBitRate()).intValue()) {
                                ((VideoProfileModel) VideoProfileAdapter.this.list.get(((Integer) view.getTag()).intValue())).setMinBitRate(newValue);
                                break;
                            } else {
                                isLegal = false;
                                break;
                            }
                        case R.id.video_profile_width:
                            ((VideoProfileModel) VideoProfileAdapter.this.list.get(((Integer) view.getTag()).intValue())).setWidth(newValue);
                            break;
                    }
                } else {
                    isLegal = false;
                }
                if (isLegal) {
                    ((TextView) view).setText(newValue);
                } else {
                    Toast.makeText(VideoProfileAdapter.this.context, VideoProfileAdapter.this.context.getResources().getString(R.string.vilte_input_illegal), 0).show();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }

    public void showSpinnerDialog(final View view) {
        View dialogView = LayoutInflater.from(this.context).inflate(R.layout.iot_spinner_dialog, (ViewGroup) null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.context);
        alertDialogBuilder.setView(dialogView);
        final Spinner userInput = (Spinner) dialogView.findViewById(R.id.spinner_dialog_input);
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(this.context, 17367048, this.context.getResources().getStringArray(R.array.video_quality_mode));
        arrayAdapter.setDropDownViewResource(17367049);
        userInput.setAdapter(arrayAdapter);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (userInput.getSelectedItem() != null) {
                    String newValue = userInput.getSelectedItem().toString();
                    switch (view.getId()) {
                        case R.id.video_quality:
                            int mPosition = ((Integer) view.getTag()).intValue();
                            if (((VideoProfileModel) VideoProfileAdapter.this.list.get(mPosition)).isSelected() || newValue.equals("")) {
                                ((TextView) view).setText(newValue);
                                ((VideoProfileModel) VideoProfileAdapter.this.list.get(mPosition)).setQuality(newValue);
                                return;
                            }
                            Toast.makeText(VideoProfileAdapter.this.context, VideoProfileAdapter.this.context.getResources().getString(R.string.vilte_quality_false), 0).show();
                            return;
                        default:
                            return;
                    }
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: com.mediatek.engineermode.iotconfig.VideoProfileAdapter$ViewHolder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r18, android.view.View r19, android.view.ViewGroup r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r0.listPosititon = r1
            r2 = 0
            if (r19 != 0) goto L_0x0127
            com.mediatek.engineermode.iotconfig.VideoProfileAdapter$5 r3 = new com.mediatek.engineermode.iotconfig.VideoProfileAdapter$5
            r3.<init>()
            android.app.Activity r4 = r0.context
            android.view.LayoutInflater r4 = r4.getLayoutInflater()
            r5 = 2131230856(0x7f080088, float:1.8077777E38)
            r6 = 0
            android.view.View r5 = r4.inflate(r5, r6)
            com.mediatek.engineermode.iotconfig.VideoProfileAdapter$ViewHolder r6 = new com.mediatek.engineermode.iotconfig.VideoProfileAdapter$ViewHolder
            r6.<init>()
            r2 = r6
            r6 = 2131101493(0x7f060735, float:1.7815397E38)
            android.view.View r7 = r5.findViewById(r6)
            android.widget.CheckBox r7 = (android.widget.CheckBox) r7
            r2.checkbox = r7
            r7 = 2131101501(0x7f06073d, float:1.7815413E38)
            android.view.View r8 = r5.findViewById(r7)
            android.widget.TextView r8 = (android.widget.TextView) r8
            r2.name = r8
            r8 = 2131101506(0x7f060742, float:1.7815424E38)
            android.view.View r9 = r5.findViewById(r8)
            android.widget.TextView r9 = (android.widget.TextView) r9
            r2.quality = r9
            r9 = 2131101502(0x7f06073e, float:1.7815416E38)
            android.view.View r10 = r5.findViewById(r9)
            android.widget.TextView r10 = (android.widget.TextView) r10
            r2.profile = r10
            r10 = 2131101497(0x7f060739, float:1.7815405E38)
            android.view.View r11 = r5.findViewById(r10)
            android.widget.TextView r11 = (android.widget.TextView) r11
            r2.level = r11
            r11 = 2131101492(0x7f060734, float:1.7815395E38)
            android.view.View r12 = r5.findViewById(r11)
            android.widget.TextView r12 = (android.widget.TextView) r12
            r2.Iinterval = r12
            r12 = 2131101505(0x7f060741, float:1.7815422E38)
            android.view.View r13 = r5.findViewById(r12)
            android.widget.TextView r13 = (android.widget.TextView) r13
            r2.width = r13
            r13 = 2131101496(0x7f060738, float:1.7815403E38)
            android.view.View r14 = r5.findViewById(r13)
            android.widget.TextView r14 = (android.widget.TextView) r14
            r2.height = r14
            r14 = 2131101499(0x7f06073b, float:1.781541E38)
            android.view.View r15 = r5.findViewById(r14)
            android.widget.TextView r15 = (android.widget.TextView) r15
            r2.maxBitRate = r15
            r15 = 2131101500(0x7f06073c, float:1.7815411E38)
            android.view.View r16 = r5.findViewById(r15)
            r15 = r16
            android.widget.TextView r15 = (android.widget.TextView) r15
            r2.minBitRate = r15
            r15 = 2131101495(0x7f060737, float:1.7815401E38)
            android.view.View r16 = r5.findViewById(r15)
            r14 = r16
            android.widget.TextView r14 = (android.widget.TextView) r14
            r2.framerate = r14
            r5.setTag(r2)
            android.widget.CheckBox r14 = r2.checkbox
            r5.setTag(r6, r14)
            android.widget.TextView r6 = r2.quality
            r5.setTag(r8, r6)
            android.widget.TextView r6 = r2.name
            r5.setTag(r7, r6)
            android.widget.TextView r6 = r2.profile
            r5.setTag(r9, r6)
            android.widget.TextView r6 = r2.level
            r5.setTag(r10, r6)
            android.widget.TextView r6 = r2.framerate
            r5.setTag(r15, r6)
            android.widget.TextView r6 = r2.profile
            r5.setTag(r12, r6)
            android.widget.TextView r6 = r2.height
            r5.setTag(r13, r6)
            android.widget.TextView r6 = r2.Iinterval
            r5.setTag(r11, r6)
            android.widget.TextView r6 = r2.maxBitRate
            r7 = 2131101499(0x7f06073b, float:1.781541E38)
            r5.setTag(r7, r6)
            android.widget.TextView r6 = r2.minBitRate
            r7 = 2131101500(0x7f06073c, float:1.7815411E38)
            r5.setTag(r7, r6)
            android.widget.CheckBox r6 = r2.checkbox
            com.mediatek.engineermode.iotconfig.VideoProfileAdapter$6 r7 = new com.mediatek.engineermode.iotconfig.VideoProfileAdapter$6
            r7.<init>()
            r6.setOnCheckedChangeListener(r7)
            android.widget.TextView r6 = r2.name
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.profile
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.level
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.quality
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.framerate
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.minBitRate
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.maxBitRate
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.height
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.width
            r6.setOnClickListener(r3)
            android.widget.TextView r6 = r2.Iinterval
            r6.setOnClickListener(r3)
            goto L_0x0130
        L_0x0127:
            java.lang.Object r3 = r19.getTag()
            r2 = r3
            com.mediatek.engineermode.iotconfig.VideoProfileAdapter$ViewHolder r2 = (com.mediatek.engineermode.iotconfig.VideoProfileAdapter.ViewHolder) r2
            r5 = r19
        L_0x0130:
            android.widget.CheckBox r3 = r2.checkbox
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.quality
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.framerate
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.minBitRate
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.maxBitRate
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.height
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.width
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.Iinterval
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.name
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.profile
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.level
            java.lang.Integer r4 = java.lang.Integer.valueOf(r18)
            r3.setTag(r4)
            android.widget.TextView r3 = r2.name
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getName()
            r3.setText(r4)
            android.widget.CheckBox r3 = r2.checkbox
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            boolean r4 = r4.isSelected()
            r3.setChecked(r4)
            android.widget.TextView r3 = r2.quality
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getQuality()
            r3.setText(r4)
            android.widget.TextView r3 = r2.profile
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getProfile()
            r3.setText(r4)
            android.widget.TextView r3 = r2.level
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getLevel()
            r3.setText(r4)
            android.widget.TextView r3 = r2.width
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getWidth()
            r3.setText(r4)
            android.widget.TextView r3 = r2.height
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getHeight()
            r3.setText(r4)
            android.widget.TextView r3 = r2.Iinterval
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getIinterval()
            r3.setText(r4)
            android.widget.TextView r3 = r2.maxBitRate
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getMaxBitRate()
            r3.setText(r4)
            android.widget.TextView r3 = r2.minBitRate
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getMinBitRate()
            r3.setText(r4)
            android.widget.TextView r3 = r2.framerate
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = r0.list
            java.lang.Object r4 = r4.get(r1)
            com.mediatek.engineermode.iotconfig.VideoProfileModel r4 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r4
            java.lang.String r4 = r4.getFramerate()
            r3.setText(r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.iotconfig.VideoProfileAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }
}
