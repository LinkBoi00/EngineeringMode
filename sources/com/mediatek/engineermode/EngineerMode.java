package com.mediatek.engineermode;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.LogWriter;
import android.view.View;
import android.widget.Toast;
import com.android.internal.util.IndentingPrintWriter;
import java.util.List;

public class EngineerMode extends Activity {
    private static final int DIALOG_BACKGROUD_PERMISSION_WARNING = 1;
    private static final int DIALOG_DEVELOPMENT_SETTINGS_DISABLED = 0;
    private static final String PROP_MONKEY = "ro.monkey";
    private static final int RUNTIME_PERMISSION_REQUEST_CODE = 100;
    private static final int TAB_COUNT = 6;
    private static final int TAB_COUNT_WIFIONLY = 5;
    private static final int[] TAB_TITLE_IDS = {R.string.tab_telephony, R.string.tab_connectivity, R.string.tab_hardware_testing, R.string.tab_location, R.string.tab_log_and_debugging, R.string.tab_others};
    private static final int[] TAB_TITLE_IDS_WIFIONLY = {R.string.tab_connectivity, R.string.tab_hardware_testing, R.string.tab_location, R.string.tab_log_and_debugging, R.string.tab_others};
    private static final String TAG = "MainEntrance";
    public static boolean sWifiOnly = false;
    private boolean mAllRuntimePermissionGranted = false;
    private boolean mLastEnabledState;
    private MyPagerAdapter mPagerAdapter;
    private RuntimePermissionManager mPermissionMgr = new RuntimePermissionManager(this, 100);
    private boolean mRestore = false;
    /* access modifiers changed from: private */
    public int mTabCount;
    /* access modifiers changed from: private */
    public int[] mTabTitleList;
    /* access modifiers changed from: private */
    public PrefsFragment[] mTabs = new PrefsFragment[6];
    private ViewPager mViewPager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isAutoTest()) {
            finish();
            return;
        }
        IndentingPrintWriter pw = new IndentingPrintWriter(new LogWriter(5, TAG), "  ");
        pw.println("Bundle stats:");
        Bundle.dumpStats(pw, savedInstanceState);
        if (savedInstanceState != null) {
            this.mRestore = true;
            savedInstanceState.remove("android:fragments");
            pw.println("Bundle stats:");
            Bundle.dumpStats(pw, savedInstanceState);
        }
        boolean z = Settings.Global.getInt(getContentResolver(), "development_settings_enabled", 0) != 0;
        this.mLastEnabledState = z;
        if (z || ChipSupport.isFeatureSupported(0) || FeatureSupport.isEngLoad() || FeatureSupport.isUserDebugLoad()) {
            Elog.v(TAG, "mLastEnabledState=" + this.mLastEnabledState);
            if (this.mPermissionMgr.requestRuntimePermissions()) {
                this.mAllRuntimePermissionGranted = true;
                initFragment();
                return;
            }
            return;
        }
        Elog.i(TAG, "you must a developer,mLastEnabledState=" + this.mLastEnabledState);
        showDialog(0);
    }

    private void initFragment() {
        Elog.i(TAG, "initFragment");
        setContentView(R.layout.main);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isWifiOnly()) {
            this.mTabTitleList = TAB_TITLE_IDS_WIFIONLY;
            this.mTabCount = 5;
        } else if (!UserManager.supportsMultipleUsers() || UserManager.get(this).getUserHandle() == 0) {
            this.mTabTitleList = TAB_TITLE_IDS;
            this.mTabCount = 6;
        } else {
            this.mTabTitleList = TAB_TITLE_IDS_WIFIONLY;
            this.mTabCount = 5;
        }
        if (this.mRestore) {
            for (int i = 0; i < this.mTabCount; i++) {
                PrefsFragment fragment = (PrefsFragment) fragmentManager.findFragmentByTag(String.valueOf(i));
                if (fragment != null) {
                    Elog.i(TAG, "old fragment:" + i);
                    transaction.remove(fragment);
                }
            }
        }
        for (int i2 = 0; i2 < this.mTabCount; i2++) {
            this.mTabs[i2] = new PrefsFragment();
            this.mTabs[i2].setResource(i2);
            transaction.add(R.id.viewpager, this.mTabs[i2], String.valueOf(i2));
            transaction.hide(this.mTabs[i2]);
        }
        this.mViewPager = (ViewPager) findViewById(R.id.viewpager);
        ((PagerTitleStrip) findViewById(R.id.pagertitle)).setTextSpacing(100);
        transaction.commitAllowingStateLoss();
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        this.mPagerAdapter = myPagerAdapter;
        this.mViewPager.setAdapter(myPagerAdapter);
        this.mViewPager.setSaveEnabled(false);
        this.mViewPager.setCurrentItem(0);
    }

    private boolean isWifiOnly() {
        if (((ConnectivityManager) getSystemService("connectivity")) != null) {
            sWifiOnly = false;
            Elog.i(TAG, "sWifiOnly: " + sWifiOnly);
        }
        return sWifiOnly;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        updateFragment();
    }

    private void updateFragment() {
        if (this.mLastEnabledState && this.mAllRuntimePermissionGranted) {
            this.mPagerAdapter.updateCurrentFragment();
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private Fragment mCurPrimaryItem;
        private FragmentTransaction mCurTransaction = null;
        private final FragmentManager mFragmentManager;

        MyPagerAdapter() {
            this.mFragmentManager = EngineerMode.this.getFragmentManager();
        }

        public int getCount() {
            return EngineerMode.this.mTabCount;
        }

        public void destroyItem(View container, int position, Object object) {
            if (this.mCurTransaction == null) {
                this.mCurTransaction = this.mFragmentManager.beginTransaction();
            }
            this.mCurTransaction.hide((Fragment) object);
        }

        public CharSequence getPageTitle(int position) {
            EngineerMode engineerMode = EngineerMode.this;
            return engineerMode.getString(engineerMode.mTabTitleList[position]).toString();
        }

        public Object instantiateItem(View container, int position) {
            if (this.mCurTransaction == null) {
                this.mCurTransaction = this.mFragmentManager.beginTransaction();
            }
            Fragment fragment = getFragment(position);
            this.mCurTransaction.show(fragment);
            fragment.setUserVisibleHint(fragment.equals(this.mCurPrimaryItem));
            return fragment;
        }

        public void finishUpdate(View container) {
            FragmentTransaction fragmentTransaction = this.mCurTransaction;
            if (fragmentTransaction != null) {
                fragmentTransaction.commitAllowingStateLoss();
                this.mCurTransaction = null;
                this.mFragmentManager.executePendingTransactions();
            }
        }

        public boolean isViewFromObject(View view, Object object) {
            return ((Fragment) object).getView() == view;
        }

        public void setPrimaryItem(View container, int position, Object object) {
            Fragment fragment = (Fragment) object;
            if (!fragment.equals(this.mCurPrimaryItem)) {
                Fragment fragment2 = this.mCurPrimaryItem;
                if (fragment2 != null) {
                    fragment2.setUserVisibleHint(false);
                }
                this.mCurPrimaryItem = fragment;
                fragment.setUserVisibleHint(true);
            }
        }

        private Fragment getFragment(int position) {
            if (position < EngineerMode.this.mTabCount) {
                return EngineerMode.this.mTabs[position];
            }
            throw new IllegalArgumentException("position: " + position);
        }

        public void updateCurrentFragment() {
            Fragment fragment = this.mCurPrimaryItem;
            if (fragment != null) {
                fragment.setUserVisibleHint(true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Notice:");
                builder.setCancelable(false);
                builder.setMessage("EM is an advanced debug mode. if you want to entry the EM,please entry the Developer options at settings!");
                builder.setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EngineerMode.this.finish();
                    }
                });
                return builder.create();
            case 1:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(R.string.em_warning_title);
                builder2.setMessage(R.string.denied_bg_permission_warning);
                builder2.setPositiveButton(R.string.em_ok, (DialogInterface.OnClickListener) null);
                return builder2.create();
            default:
                Elog.d(TAG, "error dialog ID");
                return null;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Elog.i(TAG, "onRequestPermissionsResult(), requestCode = " + requestCode);
        if (grantResults != null && grantResults.length != 0) {
            if (requestCode == 100) {
                List<String> permission = this.mPermissionMgr.getPermissionNotGranted();
                if (permission == null || permission.size() == 0) {
                    showUI();
                    return;
                }
                Toast.makeText(this, R.string.denied_required_permission, 1).show();
                finish();
                return;
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showUI() {
        this.mAllRuntimePermissionGranted = true;
        initFragment();
        updateFragment();
    }

    public static boolean isAutoTest() {
        boolean isMonkeyRunning = SystemProperties.getBoolean(PROP_MONKEY, false);
        Elog.i(TAG, "isAutoTest()-> Monkey running flag is " + isMonkeyRunning);
        boolean isUserAMonkey = ActivityManager.isUserAMonkey();
        Elog.i(TAG, "isAutoTest()-> isUserAMonkey=" + isUserAMonkey);
        if (isMonkeyRunning || isUserAMonkey) {
            return true;
        }
        return false;
    }
}
