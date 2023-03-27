package com.mediatek.engineermode.usbtethering;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.widget.Button;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;

public class UsbTethering extends Activity implements View.OnClickListener {
    private static final String TAG = "UsbTethering";
    private static final String USB_TETHERING_PROPERTY = "persist.vendor.net.tethering";
    private Button mSetButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usb_tethering);
        Button button = (Button) findViewById(R.id.usbtethering_switch);
        this.mSetButton = button;
        button.setOnClickListener(this);
        String buttonFlag = SystemProperties.get(USB_TETHERING_PROPERTY, "false");
        Elog.v(TAG, "buttonFlag is:" + buttonFlag);
        if (buttonFlag.equals("true")) {
            this.mSetButton.setText(R.string.UsbTethering_disable);
        } else {
            this.mSetButton.setText(R.string.UsbTethering_enable);
        }
    }

    public void onClick(View arg0) {
        if (arg0 != this.mSetButton) {
            return;
        }
        if (getString(R.string.UsbTethering_enable).equals(this.mSetButton.getText())) {
            if (writeSystemProperties("true")) {
                this.mSetButton.setText(R.string.UsbTethering_disable);
                Elog.v(TAG, "set succeed");
                EmUtils.showToast("set tethering to true succeed");
                return;
            }
            Elog.v(TAG, "set failed");
            EmUtils.showToast("set tethering to true failed");
        } else if (writeSystemProperties("false")) {
            this.mSetButton.setText(R.string.UsbTethering_enable);
            Elog.v(TAG, "set succeed");
            EmUtils.showToast("set tethering to false succeed");
        } else {
            Elog.v(TAG, "set failed");
            EmUtils.showToast("set tethering to false failed");
        }
    }

    private boolean writeSystemProperties(String flag) {
        SystemProperties.set(USB_TETHERING_PROPERTY, flag);
        Elog.v(TAG, "persist.vendor.net.tethering,set to: " + flag);
        String value = SystemProperties.get(USB_TETHERING_PROPERTY);
        Elog.v(TAG, "persist.vendor.net.tethering,read from system is: " + value);
        if (value.equals(flag)) {
            return true;
        }
        return false;
    }
}
