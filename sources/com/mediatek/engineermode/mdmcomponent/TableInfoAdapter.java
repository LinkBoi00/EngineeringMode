package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.mediatek.engineermode.R;

/* compiled from: MDMComponent */
class TableInfoAdapter extends ArrayAdapter<String[]> {
    private Activity mActivity;

    public TableInfoAdapter(Activity activity) {
        super(activity, 0);
        this.mActivity = activity;
    }

    public void notifyDataSetChanged(ListView listView, int position) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            getView(position, listView.getChildAt(position - firstVisiblePosition), listView);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater layoutInflater = this.mActivity.getLayoutInflater();
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mActivity).inflate(R.layout.em_info_entry, parent, false);
            holder = new ViewHolder();
            holder.texts = new MdmTextView[16];
            holder.seps = new View[15];
            holder.texts[0] = (MdmTextView) convertView.findViewById(R.id.info1);
            holder.texts[1] = (MdmTextView) convertView.findViewById(R.id.info2);
            holder.texts[2] = (MdmTextView) convertView.findViewById(R.id.info3);
            holder.texts[3] = (MdmTextView) convertView.findViewById(R.id.info4);
            holder.texts[4] = (MdmTextView) convertView.findViewById(R.id.info5);
            holder.texts[5] = (MdmTextView) convertView.findViewById(R.id.info6);
            holder.texts[6] = (MdmTextView) convertView.findViewById(R.id.info7);
            holder.texts[7] = (MdmTextView) convertView.findViewById(R.id.info8);
            holder.texts[8] = (MdmTextView) convertView.findViewById(R.id.info9);
            holder.texts[9] = (MdmTextView) convertView.findViewById(R.id.info10);
            holder.texts[10] = (MdmTextView) convertView.findViewById(R.id.info11);
            holder.texts[11] = (MdmTextView) convertView.findViewById(R.id.info12);
            holder.texts[12] = (MdmTextView) convertView.findViewById(R.id.info13);
            holder.texts[13] = (MdmTextView) convertView.findViewById(R.id.info14);
            holder.texts[14] = (MdmTextView) convertView.findViewById(R.id.info15);
            holder.texts[15] = (MdmTextView) convertView.findViewById(R.id.info16);
            for (int i = 0; i < holder.seps.length; i++) {
                View[] viewArr = holder.seps;
                viewArr[i] = convertView.findViewWithTag(i + "");
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] text = (String[]) getItem(position);
        int i2 = 0;
        while (i2 < text.length && i2 < holder.texts.length) {
            holder.texts[i2].setText(text[i2]);
            holder.texts[i2].setVisibility(0);
            if (i2 < holder.seps.length && holder.seps[i2] != null) {
                LinearLayout.LayoutParams st = (LinearLayout.LayoutParams) holder.seps[i2].getLayoutParams();
                st.width = 1;
                holder.seps[i2].setLayoutParams(st);
            }
            i2++;
        }
        for (int i3 = text.length; i3 < holder.texts.length; i3++) {
            holder.texts[i3].setVisibility(8);
            if (i3 < holder.seps.length && holder.seps[i3] != null) {
                LinearLayout.LayoutParams st2 = (LinearLayout.LayoutParams) holder.seps[i3].getLayoutParams();
                st2.width = 0;
                holder.seps[i3].setLayoutParams(st2);
            }
        }
        return convertView;
    }

    /* compiled from: MDMComponent */
    private class ViewHolder {
        public View[] seps;
        public TextView[] texts;

        private ViewHolder() {
        }
    }
}
