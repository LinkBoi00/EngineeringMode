package com.mediatek.engineermode.networkinfotc1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.mdmcomponent.MDMContent;
import com.mediatek.engineermode.networkinfotc1.MDMCoreOperation;
import com.mediatek.mdml.Msg;
import java.util.ArrayList;
import java.util.List;

public class SecurityStatus extends Activity implements MDMCoreOperation.IMDMSeiviceInterface {
    private static final String TAG = "SecurityStatus";
    private String[] SubscribeMsgIdName = {"MSG_ID_EM_RRM_CHANNEL_DESCR_INFO_IND", "MSG_ID_EM_LLC_STATUS_IND", "MSG_ID_EM_RRCE_3G_SECURITY_CONFIGURATION_STATUS_IND", "MSG_ID_EM_ERRC_SEC_PARAM_IND", "MSG_ID_EM_EMM_SEC_INFO_IND", MDMContent.MSG_ID_EM_ERRC_STATE_IND};
    private List<MdmBaseComponent> componentsArray = new ArrayList();
    private TextView m2gCipher;
    String[] m2gCipherInfo = {"No Ciphering", "A5/1", "A5/2", "A5/3", "A5/4", "A5/5", "A5/6", "A5/7"};
    private TextView m2gGprs;
    String[] m2gGprsInfo = {"No Ciphering", "GEA1", "GEA2", "GEA3"};
    private TextView m3gCipher;
    String[] m3gCipherInfo = {"No Ciphering", "UEA0", "UEA1", "", "UEA2"};
    private TextView m3gIntegrity;
    String[] m3gIntegrityInfo = {"No Integrity", "UIA1", "UIA2"};
    private TextView m4gEnasCipher;
    String[] m4gEnasCipherInfo = {"EEA0(NULL)", "EEA1(SNOW3G)", "EEA2(AES)", "EEA3(ZUC)"};
    private TextView m4gEnasIntegrity;
    String[] m4gEnasIntegrityInfo = {"EIA0(NULL)", "EIA1(SNOW3G)", "EIA2(AES)", "EIA3(ZUC)"};
    private TextView m4gErrcCipher;
    String[] m4gErrcCipherInfo = {"EEA0(NULL)", "EEA1(SNOW3G)", "EEA2(AES)", "EEA3(ZUC)"};
    private TextView m4gErrcIntegrity;
    String[] m4gErrcIntegrityInfo = {"EIA0(NULL)", "EIA1(SNOW3G)", "EIA2(AES)", "EIA3(ZUC)"};
    private int mSimTypeToShow = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_status);
        this.m2gCipher = (TextView) findViewById(R.id.security_status_2g_cipher);
        this.m2gGprs = (TextView) findViewById(R.id.security_status_2g_gprs);
        this.m3gCipher = (TextView) findViewById(R.id.security_status_3g_cipher);
        this.m3gIntegrity = (TextView) findViewById(R.id.security_status_3g_integrity);
        this.m4gEnasCipher = (TextView) findViewById(R.id.security_status_4g_enas_cipher);
        this.m4gEnasIntegrity = (TextView) findViewById(R.id.security_status_4g_enas_integrity);
        this.m4gErrcCipher = (TextView) findViewById(R.id.security_status_4g_errc_cipher);
        this.m4gErrcIntegrity = (TextView) findViewById(R.id.security_status_4g_errc_integrity);
        this.mSimTypeToShow = 0;
        MDMCoreOperation.getInstance().mdmInitialize(this);
        MDMCoreOperation.getInstance().setOnMDMChangedListener(this);
        MDMCoreOperation.getInstance().mdmParametersSeting(this.componentsArray, this.mSimTypeToShow);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        MDMCoreOperation.getInstance().mdmlUnSubscribe();
    }

    private void registerNetworkInfo() {
        MdmBaseComponent components = new MdmBaseComponent();
        components.setEmComponentName(this.SubscribeMsgIdName);
        this.componentsArray.add(components);
        MDMCoreOperation.getInstance().mdmlSubscribe();
    }

    /* access modifiers changed from: package-private */
    public int getFieldValue(Msg data, String key, boolean signed) {
        return MDMCoreOperation.getInstance().getFieldValue(data, key, signed);
    }

    /* access modifiers changed from: package-private */
    public int getFieldValue(Msg data, String key) {
        return MDMCoreOperation.getInstance().getFieldValue(data, key, false);
    }

    public void onUpdateMDMData(String name, Msg data) {
        Elog.d(TAG, "update = " + name);
        if (name.equals("MSG_ID_EM_RRM_CHANNEL_DESCR_INFO_IND")) {
            int cipher_algo = getFieldValue(data, "rr_em_channel_descr_info.cipher_algo");
            this.m2gCipher.setText(this.m2gCipherInfo[cipher_algo]);
            Elog.d(TAG, "2G cipher_algo = " + cipher_algo + " = " + this.m2gCipherInfo[cipher_algo]);
        } else if (name.equals("MSG_ID_EM_LLC_STATUS_IND")) {
            int cipher_algo2 = getFieldValue(data, "cipher_algo");
            this.m2gGprs.setText(this.m2gGprsInfo[cipher_algo2]);
            Elog.d(TAG, "2G cipher_algo = " + cipher_algo2 + " = " + this.m2gGprsInfo[cipher_algo2]);
        } else {
            String str = "N/A";
            if (name.equals("MSG_ID_EM_RRCE_3G_SECURITY_CONFIGURATION_STATUS_IND")) {
                int ciphering_alg = getFieldValue(data, "uCipheringAlgorithm");
                int integrity_alg = getFieldValue(data, "uIntegrityAlgorithm");
                this.m3gCipher.setText(this.m3gCipherInfo[ciphering_alg]);
                this.m3gIntegrity.setText(this.m3gIntegrityInfo[integrity_alg]);
                StringBuilder sb = new StringBuilder();
                sb.append("3G ciphering_alg = ");
                sb.append(ciphering_alg);
                sb.append(" = ");
                sb.append(ciphering_alg == 255 ? str : this.m3gCipherInfo[ciphering_alg]);
                Elog.d(TAG, sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("3G integrity_alg = ");
                sb2.append(integrity_alg);
                sb2.append(" = ");
                if (integrity_alg != 255) {
                    str = this.m3gIntegrityInfo[integrity_alg];
                }
                sb2.append(str);
                Elog.d(TAG, sb2.toString());
            } else if (name.equals("MSG_ID_EM_EMM_SEC_INFO_IND")) {
                int ciphering_alg2 = getFieldValue(data, "ciphering_alg");
                int integrity_alg2 = getFieldValue(data, "integrity_alg");
                if (ciphering_alg2 == 255) {
                    this.m4gEnasCipher.setText(str);
                } else {
                    this.m4gEnasCipher.setText(this.m4gEnasCipherInfo[ciphering_alg2]);
                }
                if (integrity_alg2 == 255) {
                    this.m4gEnasIntegrity.setText(str);
                } else {
                    this.m4gEnasIntegrity.setText(this.m4gEnasIntegrityInfo[integrity_alg2]);
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("4G ciphering_alg = ");
                sb3.append(ciphering_alg2);
                sb3.append(" = ");
                sb3.append(ciphering_alg2 == 255 ? str : this.m4gEnasCipherInfo[ciphering_alg2]);
                Elog.d(TAG, sb3.toString());
                StringBuilder sb4 = new StringBuilder();
                sb4.append("4G integrity_alg = ");
                sb4.append(integrity_alg2);
                sb4.append(" = ");
                if (integrity_alg2 != 255) {
                    str = this.m4gEnasIntegrityInfo[integrity_alg2];
                }
                sb4.append(str);
                Elog.d(TAG, sb4.toString());
            } else if (name.equals("MSG_ID_EM_ERRC_SEC_PARAM_IND")) {
                int enc_algo = getFieldValue(data, "enc_algo");
                int int_algo = getFieldValue(data, "int_algo");
                if (enc_algo == 255) {
                    this.m4gErrcCipher.setText(str);
                } else {
                    this.m4gErrcCipher.setText(this.m4gErrcCipherInfo[enc_algo]);
                }
                if (int_algo == 255) {
                    this.m4gErrcIntegrity.setText(str);
                } else {
                    this.m4gErrcIntegrity.setText(this.m4gErrcIntegrityInfo[int_algo]);
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append("enc_algo = ");
                sb5.append(enc_algo);
                sb5.append(" = ");
                sb5.append(enc_algo <= 3 ? str : this.m4gErrcCipherInfo[enc_algo]);
                Elog.d(TAG, sb5.toString());
                StringBuilder sb6 = new StringBuilder();
                sb6.append("int_algo = ");
                sb6.append(int_algo);
                sb6.append(" = ");
                if (int_algo > 3) {
                    str = this.m4gErrcIntegrityInfo[int_algo];
                }
                sb6.append(str);
                Elog.d(TAG, sb6.toString());
            } else if (name.equals(MDMContent.MSG_ID_EM_ERRC_STATE_IND)) {
                int errc_sts = getFieldValue(data, MDMContent.EM_ERRC_STATE_ERRC_STS);
                Elog.d(TAG, "errc_sts = " + errc_sts);
                if (errc_sts != 3 && errc_sts != 6) {
                    this.m4gErrcCipher.setText(str);
                    this.m4gErrcIntegrity.setText(str);
                }
            }
        }
    }

    public void onUpdateMDMStatus(int msg_id) {
        switch (msg_id) {
            case 0:
                Elog.d(TAG, "MDM Service init done");
                registerNetworkInfo();
                return;
            case 1:
                Elog.d(TAG, "Subscribe message id done");
                MDMCoreOperation.getInstance().mdmlEnableSubscribe();
                return;
            case 4:
                Elog.d(TAG, "UnSubscribe message id done");
                MDMCoreOperation.getInstance().mdmlClosing();
                return;
            default:
                return;
        }
    }
}
