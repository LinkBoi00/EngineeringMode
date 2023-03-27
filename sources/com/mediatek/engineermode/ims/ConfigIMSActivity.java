package com.mediatek.engineermode.ims;

import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public class ConfigIMSActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "Ims/ImsConfig";
    private String mCategory = null;
    private ListView mList;
    private ArrayList<Setting> mSettingDisplay;
    private String mSettingRule = "Setting Rule:<digit of list num><list num><mnc_len><MNC><mcc_len><MCC>...";
    private ArrayList<Setting> mSettings = new ArrayList<>();
    private int mSimType;
    private Toast mToast;
    private List<String> mlabelName = new ArrayList();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ims_config);
        this.mCategory = getIntent().getStringExtra("category");
        this.mSimType = getIntent().getIntExtra("mSimType", 0);
        setTitle(this.mCategory);
        this.mList = (ListView) findViewById(R.id.ims_item_list);
        getXMLContent(getResources().getXml(R.xml.ims_config));
        List<String> list = this.mlabelName;
        String[] tmp = (String[]) list.toArray(new String[list.size()]);
        Arrays.sort(tmp);
        this.mlabelName = Arrays.asList(tmp);
        initializeViews();
    }

    private void setListViewItemsHeight(ListView listview) {
        if (listview != null) {
            ListAdapter adapter = listview.getAdapter();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View itemView = adapter.getView(i, (View) null, listview);
                itemView.measure(0, 0);
                totalHeight += itemView.getMeasuredHeight();
            }
            int totalHeight2 = totalHeight + ((adapter.getCount() - 1) * listview.getDividerHeight());
            ViewGroup.LayoutParams params = listview.getLayoutParams();
            params.height = totalHeight2;
            listview.setLayoutParams(params);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    private void showToast(String msg) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this, msg, 0);
        this.mToast = makeText;
        makeText.show();
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            showToast("Wrong integer format: " + s);
            return -1;
        }
    }

    private void getXMLContent(XmlResourceParser parser) {
        Setting setting = new Setting();
        String text = "";
        String category = "";
        try {
            int eventType = parser.getEventType();
            while (eventType != 1) {
                switch (eventType) {
                    case 2:
                        if ("setting".equals(parser.getName())) {
                            setting = new Setting();
                            category = "";
                        } else if ("option".equals(parser.getName())) {
                            setting.getEntries().add(parser.getAttributeValue((String) null, "name"));
                            setting.getValues().add(Integer.valueOf(parseInt(parser.getAttributeValue((String) null, "value"))));
                        }
                        text = "";
                        break;
                    case 3:
                        String name = parser.getName();
                        if (!"label".equals(name)) {
                            if (!"suffix".equals(name)) {
                                if (!"category".equals(name)) {
                                    if (!JsonCmd.STR_TYPE_KEY.equals(name)) {
                                        if (!"default".equals(name)) {
                                            if ("setting".equals(name) && this.mCategory.equals(category)) {
                                                this.mSettings.add(setting);
                                                if (!setting.label.equals("white list") && !setting.label.equals(this.mSettingRule) && !setting.label.equals("operator_code_textview")) {
                                                    this.mlabelName.add(setting.label);
                                                    break;
                                                }
                                            }
                                        } else {
                                            setting.setDefaultValue(text);
                                            break;
                                        }
                                    } else {
                                        setting.setType(parseInt(text));
                                        break;
                                    }
                                } else {
                                    category = text;
                                    break;
                                }
                            } else {
                                setting.setSuffix(text);
                                break;
                            }
                        } else {
                            setting.setLabel(text);
                            break;
                        }
                    case 4:
                        text = parser.getText();
                        break;
                }
                eventType = parser.next();
            }
            parser.close();
        } catch (IOException e) {
            Elog.e(TAG, "");
        } catch (XmlPullParserException e2) {
            Elog.e(TAG, "");
        } catch (Throwable th) {
            parser.close();
            throw th;
        }
        parser.close();
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        Intent intent = new Intent(this, imsSettingPageActivity.class);
        TextView textView = (TextView) arg1;
        ArrayList<Setting> arrayList = new ArrayList<>();
        this.mSettingDisplay = arrayList;
        arrayList.add(getSetting(textView.getText().toString()));
        if (textView.getText().equals("mncmcc check")) {
            this.mSettingDisplay.add(getSetting("white list"));
            this.mSettingDisplay.add(getSetting(this.mSettingRule));
        } else if (textView.getText().equals("force_user_account_by_manual")) {
            this.mSettingDisplay.add(getSetting("manual_impi"));
            this.mSettingDisplay.add(getSetting("manual_impu"));
            this.mSettingDisplay.add(getSetting("manual_domain_name"));
        } else if (textView.getText().equals("operator_code")) {
            this.mSettingDisplay.add(getSetting("operator_code_textview"));
        }
        intent.putExtra("mSettingDisplay", this.mSettingDisplay);
        intent.putExtra("mSimType", this.mSimType);
        startActivity(intent);
    }

    private void initializeViews() {
        this.mList.setAdapter(new ArrayAdapter<>(this, 17367043, this.mlabelName));
        this.mList.setOnItemClickListener(this);
        setListViewItemsHeight(this.mList);
    }

    private Setting getSetting(String label) {
        Elog.d(TAG, "the label is" + label);
        Iterator<Setting> it = this.mSettings.iterator();
        while (it.hasNext()) {
            Setting setting = it.next();
            Elog.d(TAG, "the label in setting is" + setting.getLabel());
            if (setting.getLabel().equals(label)) {
                return setting;
            }
        }
        return null;
    }
}
