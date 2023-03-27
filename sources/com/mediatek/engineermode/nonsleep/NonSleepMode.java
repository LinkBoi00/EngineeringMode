package com.mediatek.engineermode.nonsleep;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.nonsleep.EMWakeLockService;
import java.util.List;

public class NonSleepMode extends Activity implements View.OnClickListener, ServiceConnection {
    private static final String TAG = "NonSleep";
    private Button mSetButton;
    private EMWakeLockService mWakeLockServ = null;

    private static boolean isServiceRunning(Context context, Class<? extends Service> clazz) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        int maxCount = 100;
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        while (runningServices.size() == maxCount) {
            maxCount += 50;
            runningServices = am.getRunningServices(maxCount);
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.non_sleep_mode);
        Button button = (Button) findViewById(R.id.non_sleep_switch);
        this.mSetButton = button;
        button.setOnClickListener(this);
        if (!isServiceRunning(this, EMWakeLockService.class)) {
            startService(new Intent(this, EMWakeLockService.class));
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mSetButton.setEnabled(false);
        bindService(new Intent(this, EMWakeLockService.class), this, 1);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        unbindService(this);
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EMWakeLockService eMWakeLockService;
        if (isServiceRunning(this, EMWakeLockService.class) && (eMWakeLockService = this.mWakeLockServ) != null && !eMWakeLockService.isHeld()) {
            stopService(new Intent(this, EMWakeLockService.class));
        }
        super.onDestroy();
    }

    public void onClick(View arg0) {
        if (arg0 != this.mSetButton) {
            return;
        }
        if (getString(R.string.non_sleep_enable).equals(this.mSetButton.getText())) {
            this.mSetButton.setText(R.string.non_sleep_disable);
            if (!this.mWakeLockServ.isHeld()) {
                this.mWakeLockServ.acquire(NonSleepMode.class);
                return;
            }
            return;
        }
        this.mSetButton.setText(R.string.non_sleep_enable);
        if (this.mWakeLockServ.isHeld()) {
            this.mWakeLockServ.release();
        }
    }

    public void onServiceConnected(ComponentName className, IBinder service) {
        EMWakeLockService service2 = ((EMWakeLockService.LocalBinder) service).getService();
        this.mWakeLockServ = service2;
        if (service2 == null) {
            Toast.makeText(this, "Connect PowerManager fail, finish activity", 0).show();
            finish();
        }
        this.mSetButton.setEnabled(true);
        if (this.mWakeLockServ.isHeld()) {
            this.mSetButton.setText(R.string.non_sleep_disable);
        } else {
            this.mSetButton.setText(R.string.non_sleep_enable);
        }
    }

    public void onServiceDisconnected(ComponentName className) {
        Elog.d(TAG, "onServiceDisconnected");
    }
}
