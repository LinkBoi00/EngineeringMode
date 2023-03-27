package com.mediatek.engineermode.iotconfig;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.List;

public class IotConfigurationActivity extends FragmentActivity {
    /* access modifiers changed from: private */
    public static int[] TAB_TITLE_IOT;
    private static int[] TAB_TITLE_IOT_NO_WFC = {R.string.iot_xcap, R.string.iot_vilte};
    private static int[] TAB_TITLE_IOT_WFC = {R.string.iot_xcap, R.string.iot_wfc, R.string.iot_vilte};
    private static String TAG = "Iot/IotConfigurationActivity";
    private List<Fragment> mFragments;
    private IotViewPager mViewPager;
    private PagerTitleStrip pagerTitleStrip;
    private List<String> titleList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iot_config);
        this.mViewPager = (IotViewPager) findViewById(R.id.viewpager);
        PagerTitleStrip pagerTitleStrip2 = (PagerTitleStrip) findViewById(R.id.pagertitle);
        this.pagerTitleStrip = pagerTitleStrip2;
        pagerTitleStrip2.setTextSpacing(100);
        this.titleList = new ArrayList();
        TAB_TITLE_IOT = isSupportWFC() ? TAB_TITLE_IOT_WFC : TAB_TITLE_IOT_NO_WFC;
        int i = 0;
        while (true) {
            int[] iArr = TAB_TITLE_IOT;
            if (i >= iArr.length) {
                break;
            }
            this.titleList.add(getString(iArr[i]).toString());
            i++;
        }
        ArrayList arrayList = new ArrayList();
        this.mFragments = arrayList;
        arrayList.add(new XCAPConfigFragment());
        if (isSupportWFC()) {
            this.mFragments.add(new WfcConfigFragment());
        }
        this.mFragments.add(new VilteConfigFragment());
        this.mViewPager.setAdapter(new FragPagerAdapter(getSupportFragmentManager(), this.mFragments, this.titleList));
    }

    public boolean isSupportWFC() {
        if (!FeatureSupport.isSupportWfc() || FeatureSupport.is93ModemAndAbove()) {
            return false;
        }
        return true;
    }

    public class FragPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;
        private List<String> mTitle;

        public FragPagerAdapter(FragmentManager fm, List<Fragment> mFragments2, List<String> title) {
            super(fm);
            this.mFragments = mFragments2;
            this.mTitle = title;
        }

        public Fragment getItem(int arg0) {
            return this.mFragments.get(arg0);
        }

        public int getCount() {
            return this.mFragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return IotConfigurationActivity.this.getString(IotConfigurationActivity.TAB_TITLE_IOT[position]).toString();
        }
    }
}
