package com.mediatek.engineermode.vilte;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class VilteMenuOperator extends Activity implements AdapterView.OnItemClickListener {
    private final String TAG = getClass().getSimpleName();
    ArrayList<String> items = null;
    private MenuListAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vilte_menu_select);
        ListView simTypeListView = (ListView) findViewById(R.id.vilte_main_menu_select);
        ArrayList<String> arrayList = new ArrayList<>();
        this.items = arrayList;
        arrayList.add(getString(R.string.vilte_menu_operator_ims_framework));
        this.items.add(getString(R.string.vilte_menu_operator_vt_service));
        this.items.add(getString(R.string.vilte_menu_operator_media));
        this.items.add(getString(R.string.vilte_menu_operator_codec));
        this.items.add(getString(R.string.vilte_menu_operator_modem));
        MenuListAdapter menuListAdapter = new MenuListAdapter(this);
        this.mAdapter = menuListAdapter;
        simTypeListView.setAdapter(menuListAdapter);
        simTypeListView.setOnItemClickListener(this);
        updateListView();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        String str = this.TAG;
        Elog.d(str, "Click item = " + this.items.get(position));
        if (this.items.get(position).equals(getString(R.string.vilte_menu_operator_ims_framework))) {
            intent.setClass(this, VilteImsFramework.class);
        } else if (this.items.get(position).equals(getString(R.string.vilte_menu_operator_vt_service))) {
            intent.setClass(this, ViLTEVtService.class);
        } else if (this.items.get(position).equals(getString(R.string.vilte_menu_operator_media))) {
            intent.setClass(this, VilteMenuMedia.class);
        } else if (this.items.get(position).equals(getString(R.string.vilte_menu_operator_codec))) {
            intent.setClass(this, VilteMenuCodec.class);
        } else if (this.items.get(position).equals(getString(R.string.vilte_menu_operator_modem))) {
            return;
        }
        startActivity(intent);
    }

    private void updateListView() {
        this.mAdapter.clear();
        this.mAdapter.addAll(this.items);
        this.mAdapter.notifyDataSetChanged();
    }

    private class MenuListAdapter extends ArrayAdapter<String> {
        MenuListAdapter(Context activity) {
            super(activity, 0);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = VilteMenuOperator.this.getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.vilte_menu_operator_entry, (ViewGroup) null);
                holder = new ViewHolder();
                holder.label = (TextView) convertView.findViewById(R.id.column1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String testItem = (String) getItem(position);
            holder.label.setText(testItem.toString());
            holder.label.setTextColor(-3355444);
            if (testItem.equals(VilteMenuOperator.this.getString(R.string.vilte_menu_operator_modem))) {
                convertView.setEnabled(false);
                holder.label.setTextColor(-7829368);
            } else {
                holder.label.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
            return convertView;
        }

        private class ViewHolder {
            public TextView label;

            private ViewHolder() {
            }
        }
    }
}
