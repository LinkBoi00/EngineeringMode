package com.mediatek.engineermode.anttunerdebug;

import android.app.Activity;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.List;

public class AntTunerDebugBPI extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final int BPI_QUANTITY_BIT = 32;
    private static final int OP_BPI_NON_SIGNALING = 3;
    public static final int OP_BPI_READ = 0;
    private static final int OP_BPI_SIGNALING = 2;
    public static final int OP_BPI_WRITE = 1;
    private static final String RESPONSE_CMD = "+ERFTX: ";
    public static final String TAG = "AntTunerDebugBPI";
    ArrayAdapter<String> adapterPattern = null;
    private BpiDataAdapter bpiAdapter;
    List<BpiBinaryData> bpiBinaryList = new ArrayList();
    private String[] bpiDatas = {null, null};
    private ListView bpiListView;
    /* access modifiers changed from: private */
    public int bpiQuantity = 0;
    private String[] bpiQuantityType = new String[32];
    private TableRow bpiTableRowData1;
    private TableRow bpiTableRowData2;
    private TableRow bpiTableRowData3;
    private Button btnNonSignaling;
    private Button btnSignaling;
    private RadioGroup groupBpiCode;
    private final Handler mATHandler = new Handler() {
        private String[] mReturnData = new String[2];

        public void handleMessage(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            if (msg.what == 0) {
                if (ar.exception == null) {
                    Elog.i(AntTunerDebugBPI.TAG, "BPI read successful.");
                    this.mReturnData = (String[]) ar.result;
                    Elog.d(AntTunerDebugBPI.TAG, "mReturnData = " + this.mReturnData[0]);
                    EmUtils.showToast(this.mReturnData[0], 0);
                    AntTunerDebugBPI.this.handleQuery(this.mReturnData);
                    return;
                }
                EmUtils.showToast("BPI read failed.", 0);
                Elog.e(AntTunerDebugBPI.TAG, "BPI read failed.");
            } else if (msg.what == 1) {
                if (ar.exception == null) {
                    Elog.d(AntTunerDebugBPI.TAG, "BPI write successful.");
                    return;
                }
                EmUtils.showToast("BPI write failed.", 0);
                Elog.e(AntTunerDebugBPI.TAG, "BPI write failed.");
            } else if (msg.what == 2) {
                if (ar.exception == null) {
                    Elog.d(AntTunerDebugBPI.TAG, "BPI Signaling successful.");
                    return;
                }
                EmUtils.showToast("BPI Signaling failed.", 0);
                Elog.e(AntTunerDebugBPI.TAG, "BPI Signaling failed.");
            } else if (msg.what != 3) {
            } else {
                if (ar.exception == null) {
                    Elog.d(AntTunerDebugBPI.TAG, "BPI Non-Signaling successful.");
                    return;
                }
                EmUtils.showToast("BPI Non-Signaling failed.", 0);
                Elog.e(AntTunerDebugBPI.TAG, "BPI Non-Signaling failed.");
            }
        }
    };
    private String mBpiData1 = null;
    private String mBpiData2 = null;
    private String mBpiMode = null;
    private Button mBtnBpiRead;
    private Button mBtnBpiWrite;
    private EditText mEdBpiData1;
    private EditText mEdBpiData2;
    private Spinner mSpQuantityType;
    private boolean showBinary = false;

    /* access modifiers changed from: private */
    public void handleQuery(String[] result) {
        if (result == null || result.length <= 0) {
            Elog.e(TAG, "Modem return null");
            return;
        }
        Elog.v(TAG, "Modem return: " + result[0]);
        String[] values = result[0].substring(RESPONSE_CMD.length(), result[0].length()).trim().split(",");
        if (values != null && values.length > 0) {
            if (values[2] != null || values[3] != null) {
                String[] strArr = this.bpiDatas;
                strArr[0] = values[2];
                strArr[1] = values[3];
                updateBpiData();
            }
        }
    }

    public void updateBpiData() {
        BpiBinaryData bpiBinaryData;
        BpiBinaryData bpiBinaryData2;
        if (this.showBinary) {
            this.bpiTableRowData1.setVisibility(8);
            this.bpiTableRowData2.setVisibility(8);
            this.bpiTableRowData3.setVisibility(0);
            this.bpiListView.setVisibility(0);
            findViewById(R.id.BpiBinarySpiner).setVisibility(0);
            findViewById(R.id.BpiHexSpiner).setVisibility(0);
        } else {
            this.bpiTableRowData1.setVisibility(0);
            this.bpiTableRowData2.setVisibility(0);
            this.bpiTableRowData3.setVisibility(8);
            this.bpiListView.setVisibility(8);
            findViewById(R.id.BpiBinarySpiner).setVisibility(8);
            findViewById(R.id.BpiHexSpiner).setVisibility(0);
        }
        String[] strArr = this.bpiDatas;
        if (strArr[0] != null || strArr[1] != null) {
            String[] bpiHexDatas = {"", ""};
            String[] bpiBinaryDatas = {"", ""};
            List<BpiBinaryData> bpiList = this.bpiAdapter.getList();
            String[] strArr2 = this.bpiDatas;
            if (strArr2[0] != null) {
                bpiHexDatas[0] = Long.toHexString(Long.parseLong(strArr2[0]));
                bpiBinaryDatas[0] = Long.toBinaryString(Long.parseLong(this.bpiDatas[0]));
                bpiBinaryDatas[0] = new StringBuffer(bpiBinaryDatas[0]).reverse().toString();
                Elog.d(TAG, "bpiBinaryDatas[0]" + bpiBinaryDatas[0] + ",bpiHexDatas[0]" + bpiHexDatas[0]);
                this.mEdBpiData1.setText(bpiHexDatas[0]);
                for (int i = 0; i < bpiList.size(); i++) {
                    int pin = bpiList.get(i).getValue().equals("") ? -1 : Integer.valueOf(bpiList.get(i).getValue()).intValue();
                    if (pin != -1 && pin < bpiBinaryDatas[0].length()) {
                        if (bpiBinaryDatas[0].charAt(pin) == '1') {
                            bpiBinaryData2 = new BpiBinaryData("BPI Pin#", Integer.valueOf(pin), true);
                        } else {
                            bpiBinaryData2 = new BpiBinaryData("BPI Pin#", Integer.valueOf(pin), false);
                        }
                        bpiList.set(i, bpiBinaryData2);
                    }
                }
            }
            String[] strArr3 = this.bpiDatas;
            if (strArr3[1] == null || strArr3[1].equals("0") || this.bpiDatas[1].replace("", " ").equals("")) {
                String[] strArr4 = this.bpiDatas;
                if (strArr4[1] != null && !this.showBinary) {
                    bpiHexDatas[1] = Long.toHexString(Long.parseLong(strArr4[1]));
                    this.mEdBpiData2.setText(bpiHexDatas[1]);
                }
            } else {
                bpiHexDatas[1] = Long.toHexString(Long.parseLong(this.bpiDatas[1]));
                bpiBinaryDatas[1] = Long.toBinaryString(Long.parseLong(this.bpiDatas[1]));
                bpiBinaryDatas[1] = new StringBuffer(bpiBinaryDatas[1]).reverse().toString();
                this.mEdBpiData2.setText(bpiHexDatas[1]);
                int i2 = 0;
                while (i2 < bpiBinaryDatas[1].length() && i2 < this.bpiBinaryList.size()) {
                    if (bpiBinaryDatas[1].charAt(i2) == '1') {
                        bpiBinaryData = new BpiBinaryData("BPI2 Pin#", Integer.valueOf(i2), true);
                    } else {
                        bpiBinaryData = new BpiBinaryData("BPI2 Pin#", Integer.valueOf(i2));
                    }
                    this.bpiBinaryList.add(i2, bpiBinaryData);
                    i2++;
                }
                Elog.d(TAG, "bpiBinaryDatas[1]" + bpiBinaryDatas[1] + ",bpiHexDatas[1]" + bpiHexDatas[1]);
            }
            updateListView(bpiList);
            for (BpiBinaryData bpiData : bpiList) {
                Elog.d(TAG, "bpiList:" + bpiData.toString());
            }
            for (BpiBinaryData bpiData2 : this.bpiBinaryList) {
                Elog.d(TAG, "bpiBinaryList:" + bpiData2.toString());
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ant_tuner_debug_bpi);
        this.mEdBpiData1 = (EditText) findViewById(R.id.ant_tuner_debug_bpi_data1);
        this.mEdBpiData2 = (EditText) findViewById(R.id.ant_tuner_debug_bpi_data2);
        this.mBtnBpiRead = (Button) findViewById(R.id.ant_tuner_debug_bpi_read);
        this.mBtnBpiWrite = (Button) findViewById(R.id.ant_tuner_debug_bpi_write);
        this.mBtnBpiRead.setOnClickListener(this);
        this.mBtnBpiWrite.setOnClickListener(this);
        this.mSpQuantityType = (Spinner) findViewById(R.id.ant_tuner_debug_bpi_quantity);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, initQuantityTypeArray(32));
        this.adapterPattern = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mSpQuantityType.setAdapter(this.adapterPattern);
        this.bpiQuantity = 16;
        this.mSpQuantityType.setSelection(16 - 1);
        this.mSpQuantityType.setOnItemSelectedListener(new SpinnerSelectedListener());
        this.bpiListView = (ListView) findViewById(R.id.ant_tuner_debug_bpi_list);
        this.bpiBinaryList = initBpiBinaryList(32);
        BpiDataAdapter bpiDataAdapter = new BpiDataAdapter(this, this.bpiBinaryList);
        this.bpiAdapter = bpiDataAdapter;
        this.bpiListView.setAdapter(bpiDataAdapter);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.bpi_code_rgroup);
        this.groupBpiCode = radioGroup;
        radioGroup.setOnCheckedChangeListener(this);
        Button button = (Button) findViewById(R.id.bpi_signaling);
        this.btnSignaling = button;
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.bpi_non_signaling);
        this.btnNonSignaling = button2;
        button2.setOnClickListener(this);
        this.bpiTableRowData1 = (TableRow) findViewById(R.id.BpiTableRow01);
        this.bpiTableRowData2 = (TableRow) findViewById(R.id.BpiTableRow02);
        this.bpiTableRowData3 = (TableRow) findViewById(R.id.BpiTableRow03);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.bpi_binary:
                this.showBinary = true;
                break;
            case R.id.bpi_hex:
                this.showBinary = false;
                break;
        }
        updateBpiData();
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        SpinnerSelectedListener() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            int unused = AntTunerDebugBPI.this.bpiQuantity = arg2 + 1;
            AntTunerDebugBPI antTunerDebugBPI = AntTunerDebugBPI.this;
            AntTunerDebugBPI.this.updateListView(antTunerDebugBPI.initBpiBinaryList(Integer.valueOf(antTunerDebugBPI.bpiQuantity)));
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* access modifiers changed from: package-private */
    public String[] initQuantityTypeArray(int quantity) {
        String[] strArr = new String[quantity];
        for (int i = 0; i < quantity; i++) {
            this.bpiQuantityType[i] = String.valueOf(i + 1);
        }
        return this.bpiQuantityType;
    }

    /* access modifiers changed from: package-private */
    public List<BpiBinaryData> initBpiBinaryList(Object quantity) {
        List<BpiBinaryData> bpiList = new ArrayList<>();
        for (int i = 0; i < Integer.valueOf(quantity.toString()).intValue(); i++) {
            bpiList.add(new BpiBinaryData("BPI Pin#"));
        }
        return bpiList;
    }

    public void updateListView(List<BpiBinaryData> bpiList) {
        this.bpiAdapter.clear();
        this.bpiAdapter.addAll(bpiList);
        this.bpiAdapter.refresh(bpiList);
        this.bpiAdapter.notifyDataSetChanged();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.ant_tuner_debug_bpi_read:
                if (valueGetAndCheck(0)) {
                    sendAtCommand(new String[]{"AT+ERFTX=12,1,0", "+ERFTX:"}, 0);
                    return;
                }
                return;
            case R.id.ant_tuner_debug_bpi_write:
                if (!this.showBinary && valueGetAndCheck(1)) {
                    this.mBpiData1 = Long.toString(Long.parseLong(this.mBpiData1, 16));
                    this.mBpiData2 = Long.toString(Long.parseLong(this.mBpiData2, 16));
                    sendAtCommand(new String[]{"AT+ERFTX=12,1,1," + this.mBpiData1 + "," + this.mBpiData2, ""}, 1);
                    return;
                } else if (this.showBinary && binaryValueGetAndCheck(1)) {
                    sendAtCommand(new String[]{"AT+ERFTX=12,1,1," + this.bpiDatas[0] + "," + this.bpiDatas[1], ""}, 1);
                    return;
                } else {
                    return;
                }
            case R.id.bpi_non_signaling:
                sendAtCommand(new String[]{"AT+CFUN=0", ""}, 3);
                sendAtCommand(new String[]{"AT+EGCMD=53", ""}, 3);
                return;
            case R.id.bpi_signaling:
                sendAtCommand(new String[]{"AT+CFUN=1", ""}, 2);
                return;
            default:
                return;
        }
    }

    private void sendAtCommand(String[] command, int msg) {
        Elog.d(TAG, "sendAtCommand() " + command[0]);
        EmUtils.invokeOemRilRequestStringsEm(command, this.mATHandler.obtainMessage(msg));
    }

    private boolean binaryValueGetAndCheck(int flag) {
        List<BpiBinaryData> bpiList = this.bpiAdapter.getList();
        char[] bpiData1 = new char[32];
        for (int i = 0; i < 32; i++) {
            bpiData1[i] = '0';
        }
        int max = 0;
        for (int i2 = 0; i2 < bpiList.size(); i2++) {
            int pin = bpiList.get(i2).getValue().equals("") ? -1 : Integer.valueOf(bpiList.get(i2).getValue()).intValue();
            max = (pin <= max || pin >= 32) ? max : pin;
            if (pin < 0 || pin >= 32) {
                EmUtils.showToast("BPI Pin# should be (0-31) ", 0);
                return false;
            }
            if (pin != -1) {
                bpiData1[pin] = bpiList.get(i2).isSelected() ? '1' : '0';
            }
            Elog.d(TAG, "pin: " + pin + ", bpiData1[pin]: " + bpiData1[pin]);
        }
        String bpiData = new String(bpiData1).substring(0, max + 1);
        Elog.d(TAG, "max: " + max + ", bpiData: " + bpiData);
        try {
            this.bpiDatas[0] = Long.toString(Long.parseLong(new StringBuffer(bpiData).reverse().toString(), 2));
            this.bpiDatas[1] = "0";
            return true;
        } catch (NumberFormatException e) {
            EmUtils.showToast("BPI Pin# should be (0-31)", 0);
            return false;
        }
    }

    private boolean valueGetAndCheck(int flag) {
        this.mBpiData1 = this.mEdBpiData1.getText().toString();
        this.mBpiData2 = this.mEdBpiData2.getText().toString();
        String str = flag == 0 ? "0" : "1";
        this.mBpiMode = str;
        if (!str.equals("1")) {
            return true;
        }
        if (this.mBpiData1.equals("")) {
            EmUtils.showToast("Data1 should not be empty", 0);
            return false;
        }
        try {
            Long.parseLong(this.mBpiData1, 16);
            if (!this.mBpiData2.equals("")) {
                try {
                    Long.parseLong(this.mBpiData2, 16);
                    return true;
                } catch (NumberFormatException e) {
                    EmUtils.showToast("Data2 should be 16 HEX", 0);
                    return false;
                }
            } else {
                this.mBpiData2 = "0";
                return true;
            }
        } catch (NumberFormatException e2) {
            EmUtils.showToast("Data1 should be 16 HEX", 0);
            return false;
        }
    }
}
