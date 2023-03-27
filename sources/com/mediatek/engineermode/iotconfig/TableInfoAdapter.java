package com.mediatek.engineermode.iotconfig;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mediatek.engineermode.R;

/* compiled from: ApnConfigFragment */
class TableInfoAdapter extends ArrayAdapter<String[]> {
    private Activity mActivity;

    public TableInfoAdapter(Activity activity) {
        super(activity, 0);
        this.mActivity = activity;
    }

    /* compiled from: ApnConfigFragment */
    private class ViewHolder {
        public TextView[] texts;

        private ViewHolder() {
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = this.mActivity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.em_info_entry, (ViewGroup) null);
            holder = new ViewHolder();
            holder.texts = new TextView[10];
            holder.texts[0] = (TextView) convertView.findViewById(R.id.info1);
            holder.texts[1] = (TextView) convertView.findViewById(R.id.info2);
            holder.texts[2] = (TextView) convertView.findViewById(R.id.info3);
            holder.texts[3] = (TextView) convertView.findViewById(R.id.info4);
            holder.texts[4] = (TextView) convertView.findViewById(R.id.info5);
            holder.texts[5] = (TextView) convertView.findViewById(R.id.info6);
            holder.texts[6] = (TextView) convertView.findViewById(R.id.info7);
            holder.texts[7] = (TextView) convertView.findViewById(R.id.info8);
            holder.texts[8] = (TextView) convertView.findViewById(R.id.info9);
            holder.texts[9] = (TextView) convertView.findViewById(R.id.info10);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] text = (String[]) getItem(position);
        for (int i = 0; i < text.length; i++) {
            holder.texts[i].setText(text[i]);
            holder.texts[i].setVisibility(0);
        }
        for (int i2 = text.length; i2 < holder.texts.length; i2++) {
            holder.texts[i2].setVisibility(8);
        }
        return convertView;
    }
}
