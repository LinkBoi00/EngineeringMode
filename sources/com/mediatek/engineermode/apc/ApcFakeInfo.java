package com.mediatek.engineermode.apc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.channellock.ChannelLockReceiver;
import com.mediatek.internal.telephony.PseudoCellInfo;

public class ApcFakeInfo extends Activity {
    private static final String ACTION_APC_INFO_NOTIFY = "com.mediatek.phone.ACTION_APC_INFO_NOTIFY";
    private static final String EXTRA_APC_INFO = "info";
    private static final String EXTRA_APC_PHONE = "phoneId";
    /* access modifiers changed from: private */
    public static String TAG = "ApcFakeInfo";
    /* access modifiers changed from: private */
    public static int mRowNum = 0;
    private static String[] mTitle = {"TYPE", "PLMN", "LAC", "Cell ID", ChannelLockReceiver.EXTRAL_CHANNELLOCK_ARFCN, "BSIC"};
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String access$000 = ApcFakeInfo.TAG;
            Elog.d(access$000, "onReceive: " + intent.getAction());
            if (intent.getAction().equals(ApcFakeInfo.ACTION_APC_INFO_NOTIFY)) {
                if (ApcFakeInfo.mRowNum >= 10) {
                    ApcFakeInfo.this.clearTableRow();
                    int unused = ApcFakeInfo.mRowNum = 0;
                }
                int phoneId = intent.getIntExtra(ApcFakeInfo.EXTRA_APC_PHONE, 0);
                String access$0002 = ApcFakeInfo.TAG;
                Elog.d(access$0002, "phoneId: " + phoneId);
                PseudoCellInfo info = intent.getParcelableExtra(ApcFakeInfo.EXTRA_APC_INFO);
                String access$0003 = ApcFakeInfo.TAG;
                Elog.d(access$0003, "info: " + info.toString());
                ApcFakeInfo.this.addTableRow(info);
            }
        }
    };
    private TableLayout mTableLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apc_fake_info);
        this.mTableLayout = (TableLayout) findViewById(R.id.table_layout);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        addTableTitle();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_APC_INFO_NOTIFY);
        Elog.d(TAG, "before registerReceiver: com.mediatek.phone.ACTION_APC_INFO_NOTIFY");
        registerReceiver(this.mReceiver, intentFilter);
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void clearTableRow() {
        this.mTableLayout.removeAllViews();
    }

    /* access modifiers changed from: private */
    public void addTableRow(PseudoCellInfo pbr) {
        int num = pbr.getCellCount();
        for (int i = 0; i < num; i++) {
            TableRow tableRow = new TableRow(this);
            TextView tvType = new TextView(this);
            Elog.d(TAG, "tvType = " + tvType);
            tvType.setText(String.valueOf(pbr.getType(i)));
            tvType.setGravity(17);
            tableRow.addView(tvType);
            TextView tvPlmn = new TextView(this);
            Elog.d(TAG, "tvPlmn = " + tvPlmn);
            tvPlmn.setText(String.valueOf(pbr.getPlmn(i)));
            tvPlmn.setGravity(17);
            tableRow.addView(tvPlmn);
            TextView tvLai = new TextView(this);
            Elog.d(TAG, "tvLai = " + tvLai);
            tvLai.setText(Integer.toHexString(pbr.getLac(i)));
            tvLai.setGravity(17);
            tableRow.addView(tvLai);
            TextView tvCi = new TextView(this);
            Elog.d(TAG, "tvCi = " + tvCi);
            tvCi.setText(Integer.toHexString(pbr.getCid(i)));
            tvCi.setGravity(17);
            tableRow.addView(tvCi);
            TextView tvArfcn = new TextView(this);
            Elog.d(TAG, "tvArfcn = " + tvArfcn);
            tvArfcn.setText(String.valueOf(pbr.getArfcn(i)));
            tvArfcn.setGravity(17);
            tableRow.addView(tvArfcn);
            TextView tvBsic = new TextView(this);
            Elog.d(TAG, "tvBsic = " + tvBsic);
            tvBsic.setText(String.valueOf(pbr.getBsic(i)));
            tvBsic.setGravity(17);
            tableRow.addView(tvBsic);
            this.mTableLayout.addView(tableRow, new TableLayout.LayoutParams(-1, -2));
            mRowNum++;
        }
    }

    private void addTableTitle() {
        TableRow tableRow = new TableRow(this);
        for (String text : mTitle) {
            TextView title = new TextView(this);
            title.setText(text);
            title.setGravity(17);
            tableRow.addView(title);
        }
        this.mTableLayout.addView(tableRow, new TableLayout.LayoutParams(-1, -2));
        mRowNum++;
    }
}
