package com.mediatek.engineermode.rfdesense;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.HashMap;

public class RfDesenseGsmPowerControl extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String BUNDLE_GSM_QUERY_RESULT = "pcl_result";
    private static final int DIALOG_CONFIRM = 0;
    private static final int DIALOG_QUERY_PCL = 0;
    private static final int RF_DESENSE_GSM_QUERY = 2;
    private static final int RF_DESENSE_GSM_SET = 0;
    private static final int RF_DESENSE_GSM_SET_MAX_POWER = 1;
    public static final String TAG = "RfDesense/GsmPowerControl";
    private ArrayAdapter<CharSequence> adapterLevel;
    private ArrayAdapter<CharSequence> adapterPower;
    private Button btnQuery;
    private Button btnSet;
    private Button btnSetMaxPower;
    private final HashMap<Integer, String> gsmBandMapping = new HashMap<Integer, String>() {
        {
            put(1, "Band 850");
            put(2, "Band 900");
            put(3, "Band 1800");
            put(4, "Band 1900");
        }
    };
    private final HashMap<Integer, String> gsmModTypeMapping = new HashMap<Integer, String>() {
        {
            put(0, "GMSK");
            put(1, "8PSK");
        }
    };
    private final Handler mATHandler = new Handler() {
        public void handleMessage(Message msg) {
            String text;
            if (RfDesenseGsmPowerControl.this.mIsActive) {
                AsyncResult ar = (AsyncResult) msg.obj;
                switch (msg.what) {
                    case 0:
                        String text2 = "Set PCL Value ";
                        if (ar == null || ar.exception != null) {
                            text2 = text2 + "Failed.";
                        } else if (ar.result != null) {
                            text2 = text2 + "Succeed.";
                        }
                        RfDesenseGsmPowerControl.this.showToast(text2);
                        Elog.d(RfDesenseGsmPowerControl.TAG, text2);
                        return;
                    case 1:
                        String text3 = "Set Max PCL Value ";
                        if (ar == null || ar.exception != null) {
                            text3 = text3 + "Failed.";
                        } else if (ar.result != null) {
                            text3 = text3 + "Succeed.";
                        }
                        RfDesenseGsmPowerControl.this.showToast(text3);
                        Elog.d(RfDesenseGsmPowerControl.TAG, text3);
                        return;
                    case 2:
                        if (ar == null || ar.exception != null || ar.result == null) {
                            text = "Query PCL Value " + "Failed.";
                            RfDesenseGsmPowerControl.this.showToast(text);
                        } else {
                            String result = ((String[]) ar.result)[0].substring("+EPCL:".length()).trim();
                            Elog.d(RfDesenseGsmPowerControl.TAG, result);
                            Bundle bundle = new Bundle();
                            bundle.putString(RfDesenseGsmPowerControl.BUNDLE_GSM_QUERY_RESULT, result);
                            RfDesenseGsmPowerControl.this.showDialog(0, bundle);
                            text = "Query PCL Value " + "Succeed.";
                        }
                        Elog.d(RfDesenseGsmPowerControl.TAG, text);
                        return;
                    default:
                        return;
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsActive = true;
    private Spinner mSpPowerBand;
    private Spinner mSpPowerLevel;
    private int selectedGsmBand;
    private int selectedGsmLevel;

    public int[] stringToIntArray(String src) {
        String[] strArray = src.split(",");
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            try {
                intArray[i] = Integer.parseInt(strArray[i].trim());
            } catch (NumberFormatException e) {
                intArray[i] = -1;
            }
        }
        return intArray;
    }

    /* access modifiers changed from: protected */
    public void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
        String str;
        switch (id) {
            case 0:
                int[] pclResult = stringToIntArray((String) bundle.getSerializable(BUNDLE_GSM_QUERY_RESULT));
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                };
                String alert1 = "GSM Band: " + this.gsmBandMapping.get(Integer.valueOf(pclResult[0]));
                StringBuilder sb = new StringBuilder();
                sb.append("PCL Value: ");
                String str2 = "Invalid";
                if (pclResult[1] == -1) {
                    str = str2;
                } else {
                    str = "" + pclResult[1];
                }
                sb.append(str);
                String alert2 = sb.toString();
                String alert3 = "Mod Type: " + this.gsmModTypeMapping.get(Integer.valueOf(pclResult[2]));
                StringBuilder sb2 = new StringBuilder();
                sb2.append("APC DAC: ");
                if (pclResult[3] != -1) {
                    str2 = "" + pclResult[3];
                }
                sb2.append(str2);
                ((AlertDialog) dialog).setMessage(alert1 + "\n\n" + alert2 + "\n\n" + alert3 + "\n\n" + sb2.toString());
                break;
        }
        super.onPrepareDialog(id, dialog, bundle);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id, Bundle bundle) {
        String str;
        switch (id) {
            case 0:
                int[] pclResult = stringToIntArray((String) bundle.getSerializable(BUNDLE_GSM_QUERY_RESULT));
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                };
                String alert1 = "GSM Band: " + this.gsmBandMapping.get(Integer.valueOf(pclResult[0]));
                StringBuilder sb = new StringBuilder();
                sb.append("PCL Value: ");
                String str2 = "Invalid";
                if (pclResult[1] == -1) {
                    str = str2;
                } else {
                    str = "" + pclResult[1];
                }
                sb.append(str);
                String alert2 = sb.toString();
                String alert3 = "Mod Type: " + this.gsmModTypeMapping.get(Integer.valueOf(pclResult[2]));
                StringBuilder sb2 = new StringBuilder();
                sb2.append("APC DAC: ");
                if (pclResult[3] != -1) {
                    str2 = "" + pclResult[3];
                }
                sb2.append(str2);
                String alert4 = sb2.toString();
                return new AlertDialog.Builder(this).setTitle("PCL Query Result").setMessage(alert1 + "\n\n" + alert2 + "\n\n" + alert3 + "\n\n" + alert4).setPositiveButton("OK", listener).create();
            default:
                return null;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_desense_gsm_power_control);
        initUI();
    }

    private void initUI() {
        this.mSpPowerBand = (Spinner) findViewById(R.id.rf_desense_power_band);
        ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(this, R.array.rf_desense_test_gsm_power_control_band, 17367048);
        this.adapterPower = createFromResource;
        createFromResource.setDropDownViewResource(17367049);
        this.mSpPowerBand.setAdapter(this.adapterPower);
        this.mSpPowerBand.setOnItemSelectedListener(this);
        this.btnSet = (Button) findViewById(R.id.rf_desense_gsm_set);
        this.btnSetMaxPower = (Button) findViewById(R.id.rf_desense_gsm_set_max_power);
        this.btnQuery = (Button) findViewById(R.id.rf_desense_gsm_query);
        this.btnSet.setOnClickListener(this);
        this.btnSetMaxPower.setOnClickListener(this);
        this.btnQuery.setOnClickListener(this);
        this.mSpPowerLevel = (Spinner) findViewById(R.id.rf_desense_gsm_power_level);
        ArrayAdapter<CharSequence> createFromResource2 = ArrayAdapter.createFromResource(this, R.array.rf_desense_test_gsm_power_control_level, 17367048);
        this.adapterLevel = createFromResource2;
        createFromResource2.setDropDownViewResource(17367049);
        this.mSpPowerLevel.setAdapter(this.adapterLevel);
        this.mSpPowerLevel.setOnItemSelectedListener(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        this.mIsActive = false;
        super.onDestroy();
    }

    private void sendAtCommand(String[] command, int msg) {
        Elog.d(TAG, "sendAtCommand() " + command[0]);
        showToast("sendAtCommand: " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(command, this.mATHandler.obtainMessage(msg));
    }

    /* access modifiers changed from: private */
    public void showToast(String msg) {
        Toast.makeText(this, msg, 0).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.rf_desense_power_band) {
            int i = position + 1;
            this.selectedGsmBand = i;
            setOptionalGsmLevel(i, parent);
        } else if (parent.getId() == R.id.rf_desense_gsm_power_level) {
            this.selectedGsmLevel = Integer.valueOf(parent.getItemAtPosition(position).toString()).intValue();
        }
    }

    private void setOptionalGsmLevel(int selectedGsmBand2, AdapterView<?> adapterView) {
        int i = this.selectedGsmBand;
        if (i == 1 || i == 2) {
            String[] s1 = new String[15];
            for (int i2 = 0; i2 < s1.length; i2++) {
                s1[i2] = String.valueOf(i2 + 5);
            }
            this.adapterLevel = new ArrayAdapter<>(this, 17367048, s1);
        } else if (i == 3 || i == 4) {
            String[] s12 = new String[16];
            for (int i3 = 0; i3 < s12.length; i3++) {
                s12[i3] = String.valueOf(i3);
            }
            this.adapterLevel = new ArrayAdapter<>(this, 17367048, s12);
        }
        this.adapterLevel.setDropDownViewResource(17367049);
        this.mSpPowerLevel.setAdapter(this.adapterLevel);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onClick(View view) {
        String[] cmd = new String[2];
        switch (view.getId()) {
            case R.id.rf_desense_gsm_query:
                cmd[0] = "AT+EPCL=2";
                cmd[1] = "+EPCL:";
                sendAtCommand(cmd, 2);
                return;
            case R.id.rf_desense_gsm_set:
                cmd[0] = "AT+EPCL=0," + this.selectedGsmBand + "," + this.selectedGsmLevel;
                cmd[1] = "";
                sendAtCommand(cmd, 0);
                return;
            case R.id.rf_desense_gsm_set_max_power:
                cmd[0] = "AT+EPCL=1," + this.selectedGsmBand;
                cmd[1] = "";
                sendAtCommand(cmd, 1);
                return;
            default:
                return;
        }
    }
}
