package com.mediatek.engineermode.voicesettings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class VoiceList extends ListActivity {
    private static final int DIALOG_NOT_SUPPORT = 0;
    private static final String MTK_VOW_SUPPORT = "MTK_VOW_SUPPORT";
    private static final String MTK_VOW_SUPPORT_ON = "MTK_VOW_SUPPORT=true";
    private static final String MTK_VOW_TRIGGER_SUPPORT = "MTK_VOW_2E2K_SUPPORT";
    private static final String MTK_VOW_TRIGGER_SUPPORT_ON = "MTK_VOW_2E2K_SUPPORT=true";
    private ArrayList<String> mModuleList = null;

    private static boolean isVoiceWakeupSupported(Context context) {
        String state;
        AudioManager am = (AudioManager) context.getSystemService("audio");
        if (am == null || (state = am.getParameters(MTK_VOW_SUPPORT)) == null) {
            return false;
        }
        return state.equalsIgnoreCase(MTK_VOW_SUPPORT_ON);
    }

    public static boolean isVoiceTriggerSupported(Context context) {
        String state;
        AudioManager am = (AudioManager) context.getSystemService("audio");
        if (am == null || (state = am.getParameters(MTK_VOW_TRIGGER_SUPPORT)) == null) {
            return false;
        }
        return state.equalsIgnoreCase(MTK_VOW_TRIGGER_SUPPORT_ON);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_list);
        if (!isVoiceWakeupSupported(this)) {
            showDialog(0);
            return;
        }
        this.mModuleList = new ArrayList<>();
        if (isVoiceWakeupSupported(this)) {
            this.mModuleList.add(getString(R.string.voice_wakeup_detector));
            this.mModuleList.add(getString(R.string.voice_wakeup_recognition));
        }
        setListAdapter(new ArrayAdapter<>(this, 17367043, this.mModuleList));
    }

    /* access modifiers changed from: protected */
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (this.mModuleList.get(position).equals(getString(R.string.voice_settings_wakeup))) {
            startActivity(new Intent(this, VoiceWakeup.class));
        } else if (this.mModuleList.get(position).equals(getString(R.string.voice_wakeup_detector))) {
            startActivity(new Intent(this, VoiceWakeupDetector.class));
        } else if (this.mModuleList.get(position).equals(getString(R.string.voice_wakeup_recognition))) {
            startActivity(new Intent(this, VoiceWakeupRecognition.class));
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this).setTitle(R.string.voice_settings_warning).setCancelable(false).setMessage(getString(R.string.voice_settings_not_support)).setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        VoiceList.this.finish();
                    }
                }).create();
            default:
                return null;
        }
    }
}
