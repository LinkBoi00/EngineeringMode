package com.mediatek.engineermode.rttn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.widget.TableRow;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;

public class RtnInfo extends Activity {
    public static final String HARDWARE_DEFAULT = "V1";
    private static final String PRLVERSION = "cdma.prl.version";
    public static int RIL_NV_CDMA_MDN = 3;
    public static int RIL_NV_CDMA_MEID = 1;
    public static int RIL_NV_CDMA_MIN = 2;
    public static int RIL_NV_CDMA_PRL_VERSION = 51;
    public static int RIL_NV_OMADM_HFA_LEVEL = 18;
    public static int RIL_NV_RTN_ACTIVATION_DATE = 13;
    public static int RIL_NV_RTN_LIFE_CALLS = 15;
    public static int RIL_NV_RTN_LIFE_DATA_RX = 17;
    public static int RIL_NV_RTN_LIFE_DATA_TX = 16;
    public static int RIL_NV_RTN_LIFE_TIMER = 14;
    public static int RIL_NV_RTN_RECONDITIONED_STATUS = 12;
    private static String TAG = "EM/Rtninfo";
    protected String MyPhoneNumber = "0000000000000";
    protected String Subscriber_id;
    protected NetworkStats.Bucket bucket = null;
    protected long call_count = 0;
    protected long call_duration = 0;
    protected long mobileBytes = 0;
    protected long mobileBytes_rx = 0;
    protected long mobileBytes_tx = 0;
    protected String rtn_activation_date;
    protected String rtn_life_calls;
    protected String rtn_life_data_rx = "0";
    protected String rtn_life_data_tx = "0";
    protected String rtn_life_hfa;
    protected String rtn_life_timer;
    protected String rtn_prl_version;
    protected String rtn_reconditioned_status;
    protected TableRow temp_row;
    protected String temp_text;
    protected TelephonyManager tm;
    protected TextView tmp_text_view;

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        TableRow tableRow = (TableRow) findViewById(R.id.recoditioned_status_row);
        this.temp_row = tableRow;
        TextView textView = (TextView) tableRow.getChildAt(1);
        this.tmp_text_view = textView;
        textView.setText(this.rtn_reconditioned_status);
        TableRow tableRow2 = (TableRow) findViewById(R.id.life_data_row);
        this.temp_row = tableRow2;
        TextView textView2 = (TextView) tableRow2.getChildAt(1);
        this.tmp_text_view = textView2;
        textView2.setText(this.mobileBytes + "kB");
        TableRow tableRow3 = (TableRow) findViewById(R.id.life_timer_row);
        this.temp_row = tableRow3;
        TextView textView3 = (TextView) tableRow3.getChildAt(1);
        this.tmp_text_view = textView3;
        textView3.setText(this.rtn_life_timer + " Minutes");
        TableRow tableRow4 = (TableRow) findViewById(R.id.life_calls_row);
        this.temp_row = tableRow4;
        TextView textView4 = (TextView) tableRow4.getChildAt(1);
        this.tmp_text_view = textView4;
        textView4.setText(this.rtn_life_calls + " Calls");
        TableRow tableRow5 = (TableRow) findViewById(R.id.PRL_row);
        this.temp_row = tableRow5;
        TextView textView5 = (TextView) tableRow5.getChildAt(1);
        this.tmp_text_view = textView5;
        textView5.setText(this.rtn_prl_version);
        TableRow tableRow6 = (TableRow) findViewById(R.id.activation_date_row);
        this.temp_row = tableRow6;
        TextView textView6 = (TextView) tableRow6.getChildAt(1);
        this.tmp_text_view = textView6;
        textView6.setText(this.rtn_activation_date);
    }

    public void showInfoDialog(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage(content).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                RtnInfo.this.InitRtnInfo();
                dialog.cancel();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                RtnInfo.this.finish();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        this.tm = (TelephonyManager) getSystemService("phone");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rtn_info);
        showInfoDialog(this, "Allow EngineerMode to access your personal data?");
    }

    /* access modifiers changed from: protected */
    public void InitRtnInfo() {
        TableRow tableRow = (TableRow) findViewById(R.id.mobile_num_row);
        this.temp_row = tableRow;
        this.tmp_text_view = (TextView) tableRow.getChildAt(1);
        try {
            this.MyPhoneNumber = this.tm.getLine1Number(Rtnmain.sim_id);
        } catch (NullPointerException e) {
        }
        this.tmp_text_view.setText(this.MyPhoneNumber);
        this.Subscriber_id = this.tm.getSubscriberId(Rtnmain.sim_id);
        String str = TAG;
        Elog.d(str, "Telephone number as follow " + this.tm.getLine1Number(Rtnmain.sim_id));
        TableRow tableRow2 = (TableRow) findViewById(R.id.msid_imsi_row);
        this.temp_row = tableRow2;
        TextView textView = (TextView) tableRow2.getChildAt(1);
        this.tmp_text_view = textView;
        textView.setText(this.tm.getSubscriberId(Rtnmain.sim_id));
        TableRow tableRow3 = (TableRow) findViewById(R.id.sw_version_row);
        this.temp_row = tableRow3;
        TextView textView2 = (TextView) tableRow3.getChildAt(1);
        this.tmp_text_view = textView2;
        textView2.setText(this.tm.getDeviceSoftwareVersion(Rtnmain.sim_id));
        String str2 = TAG;
        Elog.d(str2, "Device software  as follow " + this.tm.getDeviceSoftwareVersion(Rtnmain.sim_id));
        String strTime = new SimpleDateFormat("MM/dd/yyyy").format(Long.valueOf(Build.TIME));
        TableRow tableRow4 = (TableRow) findViewById(R.id.date_of_manufacture_row);
        this.temp_row = tableRow4;
        TextView textView3 = (TextView) tableRow4.getChildAt(1);
        this.tmp_text_view = textView3;
        textView3.setText(strTime);
        TableRow tableRow5 = (TableRow) findViewById(R.id.os_version_row);
        this.temp_row = tableRow5;
        TextView textView4 = (TextView) tableRow5.getChildAt(1);
        this.tmp_text_view = textView4;
        textView4.setText(Build.VERSION.RELEASE);
        String str3 = TAG;
        Elog.d(str3, "base os version as follow " + Build.VERSION.RELEASE);
        TableRow tableRow6 = (TableRow) findViewById(R.id.hw_version_row);
        this.temp_row = tableRow6;
        TextView textView5 = (TextView) tableRow6.getChildAt(1);
        this.tmp_text_view = textView5;
        textView5.setText("dm Hw verV1");
        TableRow tableRow7 = (TableRow) findViewById(R.id.esn_meid_imei_row);
        this.temp_row = tableRow7;
        TextView textView6 = (TextView) tableRow7.getChildAt(1);
        this.tmp_text_view = textView6;
        textView6.setText(this.tm.getDeviceId(Rtnmain.sim_id));
        TableRow tableRow8 = (TableRow) findViewById(R.id.radio_version_row);
        this.temp_row = tableRow8;
        TextView textView7 = (TextView) tableRow8.getChildAt(1);
        this.tmp_text_view = textView7;
        textView7.setInputType(131072);
        this.tmp_text_view.setText(Build.getRadioVersion());
        new Rtn_async_task().execute(new String[]{"rtn_reconditioned_status"});
    }

    protected class Rtn_async_task extends AsyncTask<String, String, String> {
        protected ProgressDialog progressDialog;

        protected Rtn_async_task() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            RtnInfo rtnInfo = RtnInfo.this;
            rtnInfo.rtn_reconditioned_status = rtnInfo.tm.nvReadItem(RtnInfo.RIL_NV_RTN_RECONDITIONED_STATUS);
            RtnInfo rtnInfo2 = RtnInfo.this;
            rtnInfo2.rtn_activation_date = rtnInfo2.tm.nvReadItem(RtnInfo.RIL_NV_RTN_ACTIVATION_DATE);
            RtnInfo rtnInfo3 = RtnInfo.this;
            rtnInfo3.rtn_prl_version = rtnInfo3.tm.nvReadItem(RtnInfo.RIL_NV_CDMA_PRL_VERSION);
            RtnInfo rtnInfo4 = RtnInfo.this;
            rtnInfo4.rtn_life_timer = rtnInfo4.tm.nvReadItem(RtnInfo.RIL_NV_RTN_LIFE_TIMER);
            RtnInfo rtnInfo5 = RtnInfo.this;
            rtnInfo5.rtn_life_data_rx = rtnInfo5.tm.nvReadItem(RtnInfo.RIL_NV_RTN_LIFE_DATA_RX);
            RtnInfo rtnInfo6 = RtnInfo.this;
            rtnInfo6.rtn_life_calls = rtnInfo6.tm.nvReadItem(RtnInfo.RIL_NV_RTN_LIFE_CALLS);
            if (RtnInfo.this.rtn_life_data_rx != null && !RtnInfo.this.rtn_life_data_rx.equals("")) {
                RtnInfo rtnInfo7 = RtnInfo.this;
                rtnInfo7.mobileBytes_rx = Long.parseLong(rtnInfo7.rtn_life_data_rx);
            }
            RtnInfo rtnInfo8 = RtnInfo.this;
            rtnInfo8.rtn_life_data_tx = rtnInfo8.tm.nvReadItem(RtnInfo.RIL_NV_RTN_LIFE_DATA_TX);
            if (RtnInfo.this.rtn_life_data_tx != null && !RtnInfo.this.rtn_life_data_tx.equals("")) {
                RtnInfo rtnInfo9 = RtnInfo.this;
                rtnInfo9.mobileBytes_tx = Long.parseLong(rtnInfo9.rtn_life_data_tx);
            }
            return new String(EnvironmentCompat.MEDIA_UNKNOWN);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String result) {
            this.progressDialog.dismiss();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            ProgressDialog progressDialog2 = new ProgressDialog(RtnInfo.this);
            this.progressDialog = progressDialog2;
            progressDialog2.setIndeterminate(true);
            this.progressDialog.setCancelable(false);
            this.progressDialog.setTitle(RtnInfo.this.getString(R.string.rtn_main));
            this.progressDialog.setMessage(RtnInfo.this.getString(R.string.Wait_and_load));
            this.progressDialog.show();
        }
    }
}
