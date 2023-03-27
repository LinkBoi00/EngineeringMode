package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mediatek.engineermode.R;

public class MdmLinearLayout extends LinearLayout {
    private static final String TAG = "EMInfo_MdmLinearLayout";
    private View convertView;
    int lastCount = 0;
    private ListView listView;
    private Activity mActivity;
    private TextView textView;

    public MdmLinearLayout(Activity context) {
        super(context);
        this.mActivity = context;
        initView(context);
    }

    public MdmLinearLayout(Activity context, AttributeSet attrs) {
        super(context, attrs);
        this.mActivity = context;
        initView(context);
    }

    public void initView(Activity context) {
        View inflate = context.getLayoutInflater().inflate(R.layout.em_muti_info_entry, (ViewGroup) null);
        this.convertView = inflate;
        addView(inflate);
        this.textView = (TextView) this.convertView.findViewById(R.id.detail_title_muti_mdm);
        this.listView = (ListView) this.convertView.findViewById(R.id.detail_listview_muti_mdm);
    }

    public void setTextContent(String content) {
        this.textView.setText(content);
    }

    public void hideView() {
        this.convertView.setVisibility(8);
    }

    public void showView() {
        this.convertView.setVisibility(0);
    }

    public void hideTitle() {
        this.textView.setVisibility(8);
    }

    public void showTitle() {
        this.textView.setVisibility(0);
    }

    public void setAdapter(ListAdapter adapter) {
        this.listView.setAdapter(adapter);
    }

    /* access modifiers changed from: package-private */
    public View getListView() {
        return this.listView;
    }

    /* access modifiers changed from: package-private */
    public View getTextView() {
        return this.textView;
    }

    public void setBackgroudColor(int resid) {
        this.listView.setBackgroundResource(resid);
    }

    public void setTextBackgroudColor(int resid) {
        this.textView.setBackgroundResource(resid);
    }

    public void setListViewHeightBasedOnChildren() {
        ListAdapter listAdapter = this.listView.getAdapter();
        if (listAdapter != null && listAdapter.getCount() != 0 && listAdapter.getCount() != this.lastCount) {
            int totalHeight = 0;
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mActivity.getWindowManager().getDefaultDisplay().getWidth(), Integer.MIN_VALUE);
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, -2);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, (View) null, this.listView);
                if (listItem instanceof ViewGroup) {
                    listItem.setLayoutParams(lp);
                }
                listItem.measure(widthMeasureSpec, heightMeasureSpec);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = this.listView.getLayoutParams();
            params.height = totalHeight + this.listView.getPaddingTop() + this.listView.getPaddingBottom() + (this.listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 20;
            this.listView.setLayoutParams(params);
            this.listView.requestLayout();
            this.lastCount = listAdapter.getCount();
        }
    }
}
