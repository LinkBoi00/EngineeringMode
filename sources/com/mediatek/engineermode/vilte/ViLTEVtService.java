package com.mediatek.engineermode.vilte;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class ViLTEVtService extends Activity implements View.OnClickListener {
    private AlertDialog M = null;
    private final String PROP_VILTE_TEST_OP_CODE = "persist.vendor.vt.lab_op_code";
    private final String TAG = "Vilte/VtService";
    private final int TEST_OP_CODE = 100;
    /* access modifiers changed from: private */
    public final String[][] Test_OP_CODE_VALUES = {new String[]{"Null", "0"}, new String[]{"CMCC", "1"}, new String[]{"CU", "2"}, new String[]{"Orange", "3"}, new String[]{"DTAG", "5"}, new String[]{"Vodafone", "6"}, new String[]{"AT&T", "7"}, new String[]{"TMO US", "8"}, new String[]{"CT", "9"}, new String[]{"H3G", "11"}, new String[]{"Verizon", "12"}, new String[]{"DoCoMo", "17"}, new String[]{"Reliance Jio", "18"}, new String[]{"Telstra", "19"}, new String[]{"Softbank", "50"}, new String[]{"CSL", "100"}, new String[]{"3HK", "106"}, new String[]{"Smartfren", "117"}, new String[]{"APTG", "124"}, new String[]{"SmartTone", "138"}, new String[]{"CMHK", "149"}};
    private final String[] Test_OP_CODE_label = {"Null(0)", "CMCC(1)", "CU(2)", "Orange(3)", "DTAG(5)", "Vodafone(6)", "AT&T(7)", "TMO US(8)", "CT(9)", "H3G(11)", "Verizon(12)", "DoCoMo(17)", "Reliance Jio(18)", "Telstra(19)", "Softbank(50)", "CSL(100)", "3HK(106)", "Smartfren(117)", "APTG(124)", "SmartTone(138)", "CMHK(149)"};
    private Button mButtonTestOpCode;
    private TextView mTextviewTestOpCode;
    private int test_op_index = 0;

    public ViLTEVtService() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vilte_menu_vt_service);
        this.mTextviewTestOpCode = (TextView) findViewById(R.id.vilte_test_op_code_status);
        Button button = (Button) findViewById(R.id.vilte_test_op_code);
        this.mButtonTestOpCode = button;
        button.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.d("Vilte/VtService", "onResume()");
        queryTestOpMode();
    }

    /* access modifiers changed from: package-private */
    public void queryTestOpMode() {
        String testOpCode = EmUtils.systemPropertyGet("persist.vendor.vt.lab_op_code", "0");
        TextView textView = this.mTextviewTestOpCode;
        textView.setText("persist.vendor.vt.lab_op_code = " + testOpCode);
        int i = 0;
        while (true) {
            String[][] strArr = this.Test_OP_CODE_VALUES;
            if (i >= strArr.length) {
                break;
            } else if (testOpCode.equals(strArr[i][1])) {
                this.test_op_index = i;
                break;
            } else {
                i++;
            }
        }
        Elog.d("Vilte/VtService", "test_op_index: " + this.test_op_index);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 100:
                AlertDialog create = new AlertDialog.Builder(this).setCancelable(false).setTitle("test op code").setSingleChoiceItems(this.Test_OP_CODE_label, this.test_op_index, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Elog.d("Vilte/VtService", "Set test op code: " + ViLTEVtService.this.Test_OP_CODE_VALUES[which][0] + ": " + ViLTEVtService.this.Test_OP_CODE_VALUES[which][1]);
                        try {
                            EmUtils.getEmHidlService().setEmConfigure("persist.vendor.vt.lab_op_code", ViLTEVtService.this.Test_OP_CODE_VALUES[which][1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Elog.e("Vilte/VtService", "set property failed ...");
                        }
                    }
                }).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ViLTEVtService.this.queryTestOpMode();
                    }
                }).create();
                this.M = create;
                return create;
            default:
                return null;
        }
    }

    public void onClick(View v) {
        if (v == this.mButtonTestOpCode) {
            queryTestOpMode();
            showDialog(100);
        }
    }
}
