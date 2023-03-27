package com.mediatek.engineermode;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

class RuntimePermissionManager {
    private static final String TAG = "RuntimePermissionManager";
    private final Activity mActivity;
    private int mRequestCode = 100;
    private List<String> mRuntimePermissionList = new ArrayList();

    RuntimePermissionManager(Activity activity, int requestCode) {
        this.mActivity = activity;
        this.mRequestCode = requestCode;
        initPermissionList();
    }

    private void initPermissionList() {
        this.mRuntimePermissionList.add("android.permission.ACCESS_FINE_LOCATION");
        this.mRuntimePermissionList.add("android.permission.ACCESS_COARSE_LOCATION");
        this.mRuntimePermissionList.add("android.permission.CAMERA");
        this.mRuntimePermissionList.add("android.permission.BLUETOOTH_CONNECT");
        this.mRuntimePermissionList.add("android.permission.BLUETOOTH_SCAN");
    }

    /* access modifiers changed from: package-private */
    public List<String> getPermissionNotGranted() {
        if (this.mRuntimePermissionList.size() == 0) {
            return null;
        }
        List<String> permList = new ArrayList<>();
        for (String permission : this.mRuntimePermissionList) {
            if (ContextCompat.checkSelfPermission(this.mActivity, permission) != 0) {
                Log.i(TAG, "getNeedCheckPermissionList() permission =" + permission);
                permList.add(permission);
            }
        }
        Elog.i(TAG, "Permission not granted:");
        for (String str : permList) {
            Elog.i(TAG, str);
        }
        return permList;
    }

    /* access modifiers changed from: package-private */
    public boolean requestRuntimePermissions() {
        List<String> permList = getPermissionNotGranted();
        if (permList == null || permList.size() <= 0) {
            Log.i(TAG, "requestLocationLaunchPermissions(), all on");
            return true;
        }
        ActivityCompat.requestPermissions(this.mActivity, (String[]) permList.toArray(new String[permList.size()]), this.mRequestCode);
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isPermissionsResultReady(String[] permissions, int[] grantResults) {
        for (int k = 0; k < grantResults.length; k++) {
            if (grantResults[k] != 0) {
                Log.e(TAG, "permissions not granted for " + permissions[k]);
                return false;
            }
        }
        return true;
    }
}
