package com.mediatek.engineermode.vilte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.ArrayList;

public class VilteMenuSelect extends Activity implements AdapterView.OnItemClickListener {
    private final String TAG = "Vilte/MenuSelect";
    ArrayList<String> items = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vilte_menu_select);
        ListView simTypeListView = (ListView) findViewById(R.id.vilte_main_menu_select);
        ArrayList<String> arrayList = new ArrayList<>();
        this.items = arrayList;
        arrayList.add(getString(R.string.vilte_menu_common));
        this.items.add(getString(R.string.vilte_menu_operator));
        simTypeListView.setAdapter(new ArrayAdapter<>(this, 17367043, this.items));
        simTypeListView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        Elog.d("Vilte/MenuSelect", "Click item = " + this.items.get(position));
        if (this.items.get(position).equals(getString(R.string.vilte_menu_common))) {
            intent.setClass(this, VilteMenuCommon.class);
        } else if (this.items.get(position).equals(getString(R.string.vilte_menu_operator))) {
            intent.setClass(this, VilteMenuOperator.class);
        }
        startActivity(intent);
    }
}
