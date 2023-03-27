package android.support.v4.content.res;

import android.content.res.Resources;
import android.os.Build;

public final class ConfigurationHelper {
    private ConfigurationHelper() {
    }

    public static int getDensityDpi(Resources resources) {
        if (Build.VERSION.SDK_INT >= 17) {
            return resources.getConfiguration().densityDpi;
        }
        return resources.getDisplayMetrics().densityDpi;
    }
}
