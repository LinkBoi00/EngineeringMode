package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: MDMComponent */
abstract class CombinationTableComponent extends MDMComponent {
    ArrayList<String> arrayTypeLabels;
    private int curTab = 0;
    List<HashMap<String, TableInfoAdapter>> hmapAdapterList = new ArrayList();
    List<LinkedHashMap<String, LinkedHashMap>> hmapLabelsList = new ArrayList();
    List<HashMap<String, MdmLinearLayout>> hmapViewList = new ArrayList();
    private int infoValid = 1;
    LinearLayout layout;
    String[] logInfo = new String[10];
    Activity mContext;
    ScrollView scrollView;
    private int tabCount = 0;
    LinearLayout tabTatileLayout;
    List<String> tabTitleList = new ArrayList();

    /* access modifiers changed from: package-private */
    public abstract ArrayList<String> getArrayTypeKey();

    /* access modifiers changed from: package-private */
    public abstract LinkedHashMap<String, LinkedHashMap> getHashMapLabels(int i);

    /* access modifiers changed from: package-private */
    public abstract boolean isLabelArrayType(String str);

    public CombinationTableComponent(Activity context) {
        super(context);
        this.mContext = context;
    }

    public boolean isInfoValid() {
        if (this.infoValid == 1) {
            return true;
        }
        return false;
    }

    public void setInfoValid(int utasInfoValid) {
        if (utasInfoValid != this.infoValid) {
            Elog.d("EmInfo/MDMComponent", "Utas Info Valid -> Current :" + utasInfoValid + ", Last: " + this.infoValid);
            if (utasInfoValid == 1) {
                setViewAsTable();
                setCurTab(this.curTab);
            } else {
                setViewAsPlank();
            }
        }
        this.infoValid = utasInfoValid;
    }

    public int getTabCount() {
        return this.tabCount;
    }

    public void setTabCount(int tabCount2) {
        this.tabCount = tabCount2;
    }

    public void initTableComponent(String[] tabTitles) {
        String[] strArr = this.logInfo;
        Elog.d("EmInfo/MDMComponent", strArr, "[initTableComponent]" + Arrays.asList(tabTitles).toString());
        this.tabCount = tabTitles.length;
        this.tabTitleList.clear();
        this.arrayTypeLabels = getArrayTypeKey();
        for (int i = 0; i < getTabCount(); i++) {
            this.tabTitleList.add(i, tabTitles[i]);
            this.hmapLabelsList.add(i, new LinkedHashMap());
            this.hmapViewList.add(i, new HashMap());
            this.hmapAdapterList.add(i, new HashMap());
        }
        initHashMapList();
        if (this.scrollView == null) {
            ScrollView scrollView2 = new ScrollView(this.mContext);
            this.scrollView = scrollView2;
            scrollView2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        }
        if (this.layout == null) {
            LinearLayout linearLayout = new LinearLayout(this.mContext);
            this.layout = linearLayout;
            linearLayout.setOrientation(1);
            this.layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        }
        if (this.tabTatileLayout == null) {
            LinearLayout linearLayout2 = new LinearLayout(this.mContext);
            this.tabTatileLayout = linearLayout2;
            linearLayout2.setOrientation(0);
            this.tabTatileLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        }
    }

    /* access modifiers changed from: package-private */
    public void initHashMapList() {
        for (int i = 0; i < getTabCount(); i++) {
            if (this.hmapLabelsList.get(i) == null || this.hmapLabelsList.get(i).keySet().size() == 0) {
                this.hmapLabelsList.add(i, getHashMapLabels(i));
                this.hmapViewList.get(i).clear();
                this.hmapAdapterList.get(i).clear();
                for (String key : this.hmapLabelsList.get(i).keySet()) {
                    this.hmapAdapterList.get(i).put(key, new TableInfoAdapter(this.mContext));
                    this.hmapViewList.get(i).put(key, new MdmLinearLayout(this.mContext));
                }
            }
        }
    }

    public void setViewAsPlank() {
        resetView();
        TextView textView = new TextView(this.mContext);
        textView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        textView.setPadding(20, 0, 20, 0);
        textView.setText("Use " + getName().replace("UTAS", "TAS"));
        textView.setTextSize(16.0f);
        this.scrollView.addView(textView);
    }

    public void setViewAsTable() {
        resetView();
        this.layout.addView(this.tabTatileLayout);
        for (int i = 0; i < this.tabCount; i++) {
            if (this.tabTatileLayout.getChildAt(i) == null) {
                Button codeBtn = new Button(this.mContext);
                setBtnAttribute(codeBtn, this.tabTitleList.get(i), i, -1, 16);
                this.tabTatileLayout.addView(codeBtn);
            }
            if (this.hmapLabelsList.get(i) != null) {
                for (String label : this.hmapLabelsList.get(i).keySet()) {
                    if (((TableInfoAdapter) this.hmapAdapterList.get(i).get(label)).getCount() == 0) {
                        setHmapAdapterByLabel(label, i);
                    }
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(label)).setAdapter((ListAdapter) this.hmapAdapterList.get(i).get(label));
                    ((TableInfoAdapter) this.hmapAdapterList.get(i).get(label)).notifyDataSetChanged();
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(label)).setListViewHeightBasedOnChildren();
                    if (((MdmLinearLayout) this.hmapViewList.get(i).get(label)).getParent() != null) {
                        ((ViewGroup) ((MdmLinearLayout) this.hmapViewList.get(i).get(label)).getParent()).removeView((View) this.hmapViewList.get(i).get(label));
                    }
                    this.layout.addView((View) this.hmapViewList.get(i).get(label));
                }
            }
        }
        this.scrollView.removeAllViews();
        this.scrollView.addView(this.layout);
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        this.layout.removeAllViews();
        this.tabTatileLayout.removeAllViews();
        if (!isInfoValid()) {
            setViewAsPlank();
            return this.scrollView;
        }
        setViewAsTable();
        setCurTab(0);
        return this.scrollView;
    }

    /* access modifiers changed from: package-private */
    public void clearData() {
        resetHashMapKeyValues();
        for (int i = 0; i < getTabCount(); i++) {
            for (String Label : this.hmapLabelsList.get(i).keySet()) {
                clearData(Label, i);
            }
        }
    }

    private void setBtnAttribute(Button codeBtn, String btnContent, int index, int textColor, int textSize) {
        if (codeBtn != null) {
            codeBtn.setTextColor(textColor >= 0 ? textColor : -1);
            codeBtn.setTextSize(textSize > 16 ? (float) textSize : 16.0f);
            codeBtn.setText(btnContent);
            codeBtn.setTag(Integer.valueOf(index));
            codeBtn.setBackground(this.mContext.getResources().getDrawable(R.drawable.selector_button));
            codeBtn.setGravity(17);
            codeBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CombinationTableComponent.this.setCurTab(((Integer) v.getTag()).intValue());
                }
            });
            codeBtn.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 1.0f));
        }
    }

    /* access modifiers changed from: package-private */
    public void inithmapLabelsList(int tabNum, String label) {
        if (this.hmapLabelsList.get(tabNum) == null || this.hmapLabelsList.get(tabNum).keySet().size() == 0 || this.hmapLabelsList.get(tabNum).get(label) == null || ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).keySet().size() == 0) {
            this.hmapLabelsList.add(tabNum, getHashMapLabels(tabNum));
            String[] strArr = this.logInfo;
            Elog.d("EmInfo/MDMComponent", strArr, "[inithmapLabelsList] tabNum:" + tabNum + ",label:" + label + "List:" + this.hmapLabelsList.get(tabNum));
        }
    }

    private int getCurTab() {
        return this.curTab;
    }

    /* access modifiers changed from: private */
    public void setCurTab(int tabNum) {
        this.curTab = tabNum;
        for (int i = 0; i < this.tabCount; i++) {
            for (String key : this.hmapViewList.get(i).keySet()) {
                if (i == tabNum) {
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(key)).showView();
                    if (this.tabTatileLayout.findViewWithTag(Integer.valueOf(i)) != null) {
                        this.tabTatileLayout.findViewWithTag(Integer.valueOf(i)).setSelected(true);
                    }
                } else {
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(key)).hideView();
                    if (this.tabTatileLayout.findViewWithTag(Integer.valueOf(i)) != null) {
                        this.tabTatileLayout.findViewWithTag(Integer.valueOf(i)).setSelected(false);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
        setInfoValid(1);
        resetView();
    }

    /* access modifiers changed from: package-private */
    public void resetView() {
        LinearLayout linearLayout = this.layout;
        if (linearLayout != null && linearLayout.getChildCount() > 0) {
            this.layout.removeAllViews();
        }
        ScrollView scrollView2 = this.scrollView;
        if (scrollView2 != null && scrollView2.getChildCount() > 0) {
            this.scrollView.removeAllViews();
        }
        for (int i = 0; i < this.hmapViewList.size(); i++) {
            if (this.hmapViewList.get(i) != null) {
                for (String key : this.hmapViewList.get(i).keySet()) {
                    ((TableInfoAdapter) this.hmapAdapterList.get(i).get(key)).clear();
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(key)).setAdapter((ListAdapter) null);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void clearData(String key, int tabNum) {
        if (!isValidTabNum(tabNum)) {
            for (int i = 0; i < this.hmapLabelsList.size(); i++) {
                if (this.hmapAdapterList.get(i).get(key) != null) {
                    ((TableInfoAdapter) this.hmapAdapterList.get(i).get(key)).clear();
                }
            }
        } else if (this.hmapAdapterList.get(tabNum).get(key) != null) {
            ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(key)).clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void displayView(String label, int tabNum, boolean isShow) {
        if (isValidTabNum(tabNum)) {
            String[] strArr = this.logInfo;
            Elog.d("EmInfo/MDMComponent", strArr, "[ShowView] label:" + label + "," + isShow);
            if (!isShow || (isValidTabNum(getCurTab()) && getCurTab() != tabNum)) {
                ((MdmLinearLayout) this.hmapViewList.get(tabNum).get(label)).hideView();
            } else {
                ((MdmLinearLayout) this.hmapViewList.get(tabNum).get(label)).showView();
            }
        } else {
            for (int i = 0; i < this.tabCount; i++) {
                if (isShow) {
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(label)).showView();
                } else {
                    ((MdmLinearLayout) this.hmapViewList.get(i).get(label)).hideView();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isValidTabNum(int tabNum) {
        if (tabNum >= this.tabCount || tabNum < 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void addDataByArray(String label, int tabNum, String[] data) {
        if (isValidTabNum(tabNum)) {
            if (this.hmapLabelsList.get(tabNum) == null || this.hmapLabelsList.get(tabNum).keySet().size() == 0) {
                inithmapLabelsList(tabNum, label);
            }
            int count = ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).getCount();
            ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).add(data);
            ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).notifyDataSetChanged();
            ((MdmLinearLayout) this.hmapViewList.get(tabNum).get(label)).setListViewHeightBasedOnChildren();
        }
    }

    /* access modifiers changed from: package-private */
    public void addData(String label, int tabNum) {
        inithmapLabelsList(tabNum, label);
        setHmapAdapterByLabel(label, tabNum);
        ((MdmLinearLayout) this.hmapViewList.get(tabNum).get(label)).setAdapter((ListAdapter) this.hmapAdapterList.get(tabNum).get(label));
        ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).notifyDataSetChanged();
        ((MdmLinearLayout) this.hmapViewList.get(tabNum).get(label)).setListViewHeightBasedOnChildren();
    }

    /* access modifiers changed from: package-private */
    public void notifyDataSetChanged(String label, int tabNum) {
        if (isValidTabNum(tabNum)) {
            if (this.hmapLabelsList.get(tabNum) == null || this.hmapLabelsList.get(tabNum).keySet().size() == 0) {
                inithmapLabelsList(tabNum, label);
            }
            if (((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).getCount() < ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).size()) {
                this.hmapAdapterList.get(tabNum).clear();
                setHmapAdapterByLabel(label, tabNum);
            }
            ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).notifyDataSetChanged();
            ((MdmLinearLayout) this.hmapViewList.get(tabNum).get(label)).setListViewHeightBasedOnChildren();
        }
    }

    /* access modifiers changed from: package-private */
    public void setHmapAdapterByLabel(String label, int tabNum) {
        Object obj;
        String str;
        if (isValidTabNum(tabNum)) {
            Elog.d("EmInfo/MDMComponent", this.logInfo, "[setHmapAdapterByLabel] label: " + label + ",tabNum: " + tabNum);
            ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).clear();
            String[] keys = new String[((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).size()];
            ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).keySet().toArray(keys);
            if (this.arrayTypeLabels.contains(label)) {
                List<String[]> cells = new ArrayList<>();
                ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).add(keys);
                int cellNum = 0;
                for (String str2 : keys) {
                    Object objValues = ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).get(str2);
                    String[] values = new String[0];
                    if (objValues != null) {
                        values = objValues instanceof String[] ? (String[]) objValues : new String[]{objValues.toString()};
                        cellNum = values.length > cellNum ? values.length : cellNum;
                    }
                    cells.add(values);
                }
                for (int j = 0; j < cellNum; j++) {
                    String[] values2 = new String[keys.length];
                    for (int i = 0; i < keys.length; i++) {
                        if (i >= cells.size() || cells.get(i) == null || j >= cells.get(i).length) {
                            str = "";
                        } else {
                            str = cells.get(i)[j];
                        }
                        values2[i] = str;
                    }
                    ((TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label)).add(values2);
                }
                return;
            }
            for (int i2 = 0; i2 < keys.length; i2++) {
                TableInfoAdapter tableInfoAdapter = (TableInfoAdapter) this.hmapAdapterList.get(tabNum).get(label);
                String[] strArr = new String[2];
                strArr[0] = keys[i2];
                if (((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).get(keys[i2]) == null) {
                    obj = "";
                } else {
                    obj = ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).get(keys[i2]);
                }
                strArr[1] = (String) obj;
                tableInfoAdapter.add(strArr);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, String> initHashMap(Object[] keys) {
        LinkedHashMap<String, String> hashMapObj = new LinkedHashMap<>();
        for (String put : keys) {
            hashMapObj.put(put, "");
        }
        return hashMapObj;
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, String> setHashMap(Object[] keys, Object[] values) {
        LinkedHashMap<String, String> hashMapObj = new LinkedHashMap<>();
        if (keys.length != values.length) {
            return hashMapObj;
        }
        for (int i = 0; i < keys.length; i++) {
            hashMapObj.put(keys[i], values[i].toString());
        }
        return hashMapObj;
    }

    /* access modifiers changed from: package-private */
    public LinkedHashMap<String, String[]> initArrayHashMap(Object[] keys) {
        LinkedHashMap<String, String[]> hashMapObj = new LinkedHashMap<>();
        for (String put : keys) {
            hashMapObj.put(put, (Object) null);
        }
        return hashMapObj;
    }

    /* access modifiers changed from: package-private */
    public void initHmapLabelsList() {
        if (this.hmapLabelsList == null) {
            this.hmapLabelsList = new ArrayList();
        }
        for (int i = 0; i < getTabCount(); i++) {
            if (this.hmapLabelsList.get(i) == null || this.hmapLabelsList.get(i).size() == 0) {
                this.hmapLabelsList.add(i, getHashMapLabels(i));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void resetHashMapKeyValues() {
        LinkedHashMap map;
        for (int i = 0; i < this.tabCount; i++) {
            for (String Label : this.hmapLabelsList.get(i).keySet()) {
                inithmapLabelsList(i, Label);
                new LinkedHashMap();
                if (isLabelArrayType(Label)) {
                    map = initArrayHashMap(((LinkedHashMap) this.hmapLabelsList.get(i).get(Label)).keySet().toArray());
                } else {
                    map = initHashMap(((LinkedHashMap) this.hmapLabelsList.get(i).get(Label)).keySet().toArray());
                }
                ((LinkedHashMap) this.hmapLabelsList.get(i).get(Label)).clear();
                ((LinkedHashMap) this.hmapLabelsList.get(i).get(Label)).putAll(map);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyValues(String label, int tabNum, String key, Object value) {
        inithmapLabelsList(tabNum, label);
        if (!isLabelArrayType(label)) {
            ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).put(key, String.valueOf(value));
            String[] strArr = this.logInfo;
            Elog.d("EmInfo/MDMComponent", strArr, "[setHashMapKeyValues]Label: " + label + ", tabNum: " + tabNum + ":" + this.hmapLabelsList.get(tabNum).get(label));
        } else if (value instanceof String[]) {
            ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).put(key, (String[]) value);
            String[] strArr2 = this.logInfo;
            Elog.d("EmInfo/MDMComponent", strArr2, "[setHashMapKeyValues] key: " + key + ",values:" + Arrays.toString((String[]) value));
        } else if (value != null && !value.equals("")) {
            ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).keySet().toArray(new String[((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).keySet().size()]);
            String[] oldValues = (String[]) ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).get(key);
            String[] values = (String[]) Arrays.copyOf(oldValues, oldValues.length + 1);
            values[oldValues.length] = value.toString();
            ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).put(key, values);
            String[] strArr3 = this.logInfo;
            Elog.d("EmInfo/MDMComponent", strArr3, "[setHashMapKeyValues] key: " + key + ",values:" + Arrays.toString(values));
        }
    }

    private boolean isEmaptyStrArray(String[] value) {
        for (int i = 0; i < value.length; i++) {
            if (value[i] != null && !value[i].equals("")) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyValues(String Label, int tabNum, LinkedHashMap<String, String> keyValues) {
        inithmapLabelsList(tabNum, Label);
        this.hmapLabelsList.get(tabNum).put(Label, keyValues);
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyArrays(String Label, int tabNum, LinkedHashMap<String, String[]> keyValues) {
        inithmapLabelsList(tabNum, Label);
        this.hmapLabelsList.get(tabNum).put(Label, keyValues);
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyValues(String Label, int tabNum, String key, String[] values) {
        inithmapLabelsList(tabNum, Label);
        ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(Label)).put(key, values);
    }

    /* access modifiers changed from: package-private */
    public void setHashMapKeyValues(String label, int tabNum, String[] keys, Object[] values) {
        if (keys.length == values.length) {
            inithmapLabelsList(tabNum, label);
            String[] strArr = this.logInfo;
            Elog.d("EmInfo/MDMComponent", strArr, "[setHashMapKeyValues] tabNum: " + tabNum + ",label:" + label + ", keys: " + Arrays.toString(keys) + ", values:" + Arrays.toString(values));
            if (isLabelArrayType(label)) {
                for (int i = 0; i < keys.length; i++) {
                    if (!((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).containsKey(keys[i]) || ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).get(keys[i]) == null) {
                        ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).put(keys[i], new String[]{values[i].toString()});
                    } else {
                        String[] oldValues = (String[]) ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).get(keys[i]);
                        String[] newValues = (String[]) Arrays.copyOf(oldValues, oldValues.length + 1);
                        newValues[oldValues.length] = values[i].toString();
                        ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).put(keys[i], newValues);
                    }
                }
                return;
            }
            for (int i2 = 0; i2 < keys.length; i2++) {
                ((LinkedHashMap) this.hmapLabelsList.get(tabNum).get(label)).put(keys[i2], values[i2] == null ? "" : values[i2].toString());
            }
        }
    }
}
