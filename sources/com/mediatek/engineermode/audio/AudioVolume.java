package com.mediatek.engineermode.audio;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class AudioVolume extends ListActivity {
    public static final String TAG = "Audio/Volume";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_volume);
        ArrayList<String> itemList = new ArrayList<>();
        itemList.add(getString(R.string.audio_volume_voice));
        itemList.add(getString(R.string.audio_volume_voip));
        itemList.add(getString(R.string.audio_volume_playback));
        itemList.add(getString(R.string.audio_volume_record));
        setListAdapter(new ArrayAdapter<>(this, 17367043, itemList));
    }

    /* access modifiers changed from: protected */
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, AudioVolumeVoice.class));
                return;
            case 1:
                startActivity(new Intent(this, AudioVolumeVoIP.class));
                return;
            case 2:
                startActivity(new Intent(this, AudioVolumePlayback.class));
                return;
            case 3:
                startActivity(new Intent(this, AudioVolumeRecord.class));
                return;
            default:
                return;
        }
    }
}
