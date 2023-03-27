package com.mediatek.engineermode.mdlogfilters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.emsvr.AFMFunctionCallEx;
import com.mediatek.engineermode.mcfconfig.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MDLogFiltersActivity extends Activity {
    private static final String ACTION_ASK_LOG_PATH = "com.debug.loggerui.ADB_CMD";
    private static final String ACTION_GET_LOG_PATH = "com.debug.loggerui.result";
    private static final String BIN_RECORD_PATH = "filter_config";
    private static final String DEFAULT_FILTER = "_Default";
    private static final int DIALOG_NO_FILTER_RECORD_FOLDER = 4;
    private static final int DIALOG_QUERY_FILTERS = 2;
    private static final int DIALOG_UPDATE_DONE = 3;
    private static final int DIALOG_UPDATE_PATH = 1;
    private static final String FILTER_TAR_PATH = "mdlog1_config/";
    private static final String[] GEN_FILTER_FILE_LIST = {"catcher_filter_1_ulwctg_n__Default.bin", "catcher_filter_1_ulwctg_n_FullLog.bin", "catcher_filter_1_ulwctg_n_meta.bin", "catcher_filter_1_ulwctg_n_SlimLog_DspAllOff.bin", "catcher_filter_1_ulwctg_n_LowPowerMonitor.bin", "catcher_filter_1_ulwctg_n_1_Moderate.bin", "catcher_filter_1_ulwctg_n_2_Standard.bin", "catcher_filter_1_ulwctg_n_3_Slim.bin", "catcher_filter_1_ulwctg_n_4_UltraSlim.bin"};
    private static final String KEY_ASK_LOG_PATH = "cmd_name";
    private static final String KEY_GET_LOG_PATH = "result_value";
    private static final String[] KEY_WORD_LIST = {"md1_filter__Default", "md1_filter_FullLog", "md1_filter_meta", "md1_filter_SlimLog_DspAllOff", "md1_filter_LowPowerMonitor", "md1_filter_1_Moderate", "md1_filter_2_Standard", "md1_filter_3_Slim", "md1_filter_4_UltraSlim"};
    private static final String LOG_CMD_CLASS = "com.debug.loggerui.framework.LogReceiver";
    private static final String LOG_CMD_PKG = "com.debug.loggerui";
    private static final String LOG_PATH_PERMISSION = "android.permission.SET_DEBUG_APP";
    private static final int MSG_FILE_FORMAT_WRONG = 2;
    private static final int MSG_FILE_UPDATE_RESULT = 1;
    private static final String PLS_PS_ONLY_FILTER = "PLS_PS_ONLY";
    private static final String POST_FIX = ".bin";
    private static final String PRIMARY_STORAGE = "primary";
    private static final int REQUEST_SELECT_BIN = 1;
    private static final String STORAGE_AUTHORITY = "com.android.externalstorage.documents";
    private static final String TAG = "MDLogFilters";
    private static final byte[] VALID_BIN_BYTES = {-51, -85, 84, 36};
    private static final String VALUE_ASK_LOG_PATH = "get_mtklog_path";
    private static final String mDefaultFilterPath = "/vendor/etc/firmware/customfilter/";
    private static final String mGenFilterPath = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/mdlogfilters/");
    /* access modifiers changed from: private */
    public Button mBtnMore;
    /* access modifiers changed from: private */
    public Button mBtnRestore;
    /* access modifiers changed from: private */
    public Button mBtnUpdate;
    /* access modifiers changed from: private */
    public ArrayList<FilterInfo> mFilterList = new ArrayList<>();
    /* access modifiers changed from: private */
    public Handler mHandler = new MyHandler();
    private View mMoreFilterView;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.equals(MDLogFiltersActivity.this.mBtnMore)) {
                Intent it = new Intent("android.intent.action.GET_CONTENT");
                it.setType("*/*");
                MDLogFiltersActivity.this.startActivityForResult(it, 1);
            } else if (v.equals(MDLogFiltersActivity.this.mBtnUpdate)) {
                RadioButton rb = (RadioButton) MDLogFiltersActivity.this.findViewById(MDLogFiltersActivity.this.mRgFilterList.getCheckedRadioButtonId());
                if (rb != null) {
                    String unused = MDLogFiltersActivity.this.mSrcBinName = ((FilterInfo) rb.getTag()).getBinName();
                    MDLogFiltersActivity.this.enableUI(false);
                    new Thread() {
                        public void run() {
                            if (!MDLogFiltersActivity.this.checkBinValid(MDLogFiltersActivity.this.mSrcBinName)) {
                                MDLogFiltersActivity.this.mHandler.sendEmptyMessage(2);
                                return;
                            }
                            Message message = MDLogFiltersActivity.this.mHandler.obtainMessage(1);
                            message.arg1 = MDLogFiltersActivity.this.recordFilterPath(MDLogFiltersActivity.this.mSrcBinName).ordinal();
                            MDLogFiltersActivity.this.mHandler.sendMessage(message);
                        }
                    }.start();
                    return;
                }
                Toast.makeText(MDLogFiltersActivity.this, R.string.md_log_filter_no_filter_selected, 0).show();
            } else if (v.equals(MDLogFiltersActivity.this.mBtnRestore)) {
                MDLogFiltersActivity.this.enableUI(false);
                new Thread() {
                    public void run() {
                        Message message = MDLogFiltersActivity.this.mHandler.obtainMessage(1);
                        message.arg1 = MDLogFiltersActivity.this.recordFilterPath("").ordinal();
                        MDLogFiltersActivity.this.mHandler.sendMessage(message);
                    }
                }.start();
            }
        }
    };
    private int mPreFixIndex = -1;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Elog.d(MDLogFiltersActivity.TAG, "onReceive: " + intent.getAction());
            if (intent.getAction().equals(MDLogFiltersActivity.ACTION_GET_LOG_PATH)) {
                String path = intent.getStringExtra(MDLogFiltersActivity.KEY_GET_LOG_PATH);
                Elog.i(MDLogFiltersActivity.TAG, "path: " + path);
                if (path != null) {
                    MDLogFiltersActivity mDLogFiltersActivity = MDLogFiltersActivity.this;
                    String unused = mDLogFiltersActivity.mTarPath = path + MDLogFiltersActivity.FILTER_TAR_PATH;
                }
                MDLogFiltersActivity.this.removeDialog(1);
            }
        }
    };
    /* access modifiers changed from: private */
    public RadioGroup mRgFilterList;
    /* access modifiers changed from: private */
    public String mSrcBinName;
    /* access modifiers changed from: private */
    public String mSrcPath = null;
    /* access modifiers changed from: private */
    public String mTarPath = null;

    enum UpdateFilterResult {
        UPDATE_FILTER_RESULT_DONE,
        UPDATE_FILTER_RESULT_NO_RECORD_FILE,
        UPDATE_FILTER_RESULT_IO_ERROR
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_log_filter);
        this.mRgFilterList = (RadioGroup) findViewById(R.id.md_log_filter_rg);
        Button button = (Button) findViewById(R.id.md_log_filter_more_btn);
        this.mBtnMore = button;
        button.setOnClickListener(this.mOnClickListener);
        Button button2 = (Button) findViewById(R.id.md_log_filter_update_btn);
        this.mBtnUpdate = button2;
        button2.setOnClickListener(this.mOnClickListener);
        Button button3 = (Button) findViewById(R.id.md_log_filter_restore_btn);
        this.mBtnRestore = button3;
        button3.setOnClickListener(this.mOnClickListener);
        this.mTarPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "debuglogger" + FILTER_TAR_PATH;
        StringBuilder sb = new StringBuilder();
        sb.append("mTarPath is ");
        sb.append(this.mTarPath);
        Elog.i(TAG, sb.toString());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_GET_LOG_PATH);
        registerReceiver(this.mReceiver, intentFilter, LOG_PATH_PERMISSION, (Handler) null);
        new FileLoadTask().execute(new Void[0]);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        askLogPath();
        showDialog(1);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id, Bundle bundle) {
        switch (id) {
            case 1:
                ProgressDialog dlgUpdatePath = new ProgressDialog(this);
                dlgUpdatePath.setMessage(getString(R.string.md_log_filter_update_log_path));
                dlgUpdatePath.setCancelable(false);
                dlgUpdatePath.setIndeterminate(true);
                return dlgUpdatePath;
            case 2:
                ProgressDialog dlgQueryFilters = new ProgressDialog(this);
                dlgQueryFilters.setMessage(getString(R.string.md_log_filter_query_filters));
                dlgQueryFilters.setCancelable(false);
                dlgQueryFilters.setIndeterminate(true);
                return dlgQueryFilters;
            case 3:
                return new AlertDialog.Builder(this).setTitle(R.string.md_log_filter).setMessage(R.string.md_log_filter_update_success).setCancelable(false).setPositiveButton(R.string.em_ok, (DialogInterface.OnClickListener) null).create();
            case 4:
                return new AlertDialog.Builder(this).setTitle(R.string.md_log_filter).setMessage(R.string.md_log_filter_no_record_path).setCancelable(false).setPositiveButton(R.string.em_ok, (DialogInterface.OnClickListener) null).create();
            default:
                return null;
        }
    }

    private void askLogPath() {
        Intent intentDone = new Intent(ACTION_ASK_LOG_PATH);
        intentDone.setClassName(LOG_CMD_PKG, LOG_CMD_CLASS);
        intentDone.putExtra(KEY_ASK_LOG_PATH, VALUE_ASK_LOG_PATH);
        sendBroadcast(intentDone);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == -1) {
            View view = this.mMoreFilterView;
            if (view != null) {
                this.mRgFilterList.removeView(view);
            }
            Uri uri = data.getData();
            Elog.i(TAG, "uri:" + uri);
            String path = getPathFromUri(uri);
            if (path != null) {
                String strFilterName = extractFilterName(path, false);
                if (strFilterName == null) {
                    Toast.makeText(this, R.string.md_log_filter_wrong_filter_name, 0).show();
                    return;
                }
                FilterInfo filterInfo = new FilterInfo(strFilterName, path);
                RadioButton radio = new RadioButton(this);
                radio.setText(filterInfo.getFilterName());
                radio.setTag(filterInfo);
                this.mRgFilterList.addView(radio);
                Elog.i(TAG, "mRgFilterList addView:" + filterInfo.getFilterName());
                this.mRgFilterList.check(radio.getId());
                this.mMoreFilterView = radio;
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] pathArray;
        if (uri == null) {
            return null;
        }
        if (!STORAGE_AUTHORITY.equals(uri.getAuthority())) {
            Elog.e(TAG, "not support:" + uri.getAuthority());
            Toast.makeText(this, R.string.md_log_filter_wrong_filter_path, 0).show();
            return null;
        }
        String strLastPathSegment = uri.getLastPathSegment();
        Elog.i(TAG, "strLastPathSegment:" + strLastPathSegment);
        if (!(strLastPathSegment == null || (pathArray = strLastPathSegment.split(":")) == null || pathArray.length < 2)) {
            if (PRIMARY_STORAGE.equalsIgnoreCase(pathArray[0])) {
                return Environment.getExternalStorageDirectory().getAbsolutePath() + FileUtils.SEPARATOR + pathArray[1];
            }
            String strPath = getExternalSDPath(pathArray[0]);
            if (strPath != null) {
                return strPath + FileUtils.SEPARATOR + pathArray[1];
            }
        }
        return null;
    }

    private String getExternalSDPath(String name) {
        if (name == null || name.length() == 0) {
            return null;
        }
        StorageVolume[] volumes = ((StorageManager) getSystemService("storage")).getVolumeList();
        int length = volumes.length;
        int i = 0;
        while (i < length) {
            StorageVolume volume = volumes[i];
            String volumePathStr = volume.getPath();
            if (!volumePathStr.contains(name) || !"mounted".equalsIgnoreCase(volume.getState())) {
                i++;
            } else {
                Elog.i(TAG, "volumePathStr:" + volumePathStr);
                return volumePathStr;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void enableUI(boolean enable) {
        this.mBtnRestore.setEnabled(enable);
        this.mBtnUpdate.setEnabled(enable);
        this.mBtnMore.setEnabled(enable);
        int count = this.mRgFilterList.getChildCount();
        for (int k = 0; k < count; k++) {
            this.mRgFilterList.getChildAt(k).setEnabled(enable);
        }
    }

    /* access modifiers changed from: private */
    public UpdateFilterResult recordFilterPath(String srcFile) {
        Elog.i(TAG, "recordFilterPath " + srcFile + " " + this.mTarPath);
        FileOutputStream osRecord = null;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mTarPath);
        sb.append(BIN_RECORD_PATH);
        File recordFile = new File(sb.toString());
        if (!recordFile.exists()) {
            return UpdateFilterResult.UPDATE_FILTER_RESULT_NO_RECORD_FILE;
        }
        try {
            Elog.i(TAG, "recordFile is:" + recordFile.getAbsolutePath());
            FileOutputStream osRecord2 = new FileOutputStream(recordFile);
            if (new File(srcFile).exists()) {
                osRecord2.write(srcFile.getBytes());
            }
            try {
                osRecord2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return UpdateFilterResult.UPDATE_FILTER_RESULT_DONE;
        } catch (IOException e2) {
            e2.printStackTrace();
            UpdateFilterResult updateFilterResult = UpdateFilterResult.UPDATE_FILTER_RESULT_IO_ERROR;
            if (osRecord != null) {
                try {
                    osRecord.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return updateFilterResult;
        } catch (Throwable th) {
            if (osRecord != null) {
                try {
                    osRecord.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    private class MyHandler extends Handler {
        private MyHandler() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                MDLogFiltersActivity.this.enableUI(true);
                switch (AnonymousClass3.$SwitchMap$com$mediatek$engineermode$mdlogfilters$MDLogFiltersActivity$UpdateFilterResult[UpdateFilterResult.values()[msg.arg1].ordinal()]) {
                    case 1:
                        MDLogFiltersActivity.this.showDialog(3);
                        Elog.i(MDLogFiltersActivity.TAG, "Update filter success");
                        return;
                    case 2:
                        Toast.makeText(MDLogFiltersActivity.this, R.string.md_log_filter_update_fail, 0).show();
                        Elog.i(MDLogFiltersActivity.TAG, "Update filter fail for IO error");
                        return;
                    case 3:
                        MDLogFiltersActivity.this.showDialog(4);
                        return;
                    default:
                        return;
                }
            } else if (msg.what == 2) {
                MDLogFiltersActivity.this.enableUI(true);
                Toast.makeText(MDLogFiltersActivity.this, R.string.md_log_filter_wrong_filter_format, 0).show();
                Elog.i(MDLogFiltersActivity.TAG, "Update filter fail for wrong filter format");
            }
        }
    }

    /* renamed from: com.mediatek.engineermode.mdlogfilters.MDLogFiltersActivity$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$mdlogfilters$MDLogFiltersActivity$UpdateFilterResult;

        static {
            int[] iArr = new int[UpdateFilterResult.values().length];
            $SwitchMap$com$mediatek$engineermode$mdlogfilters$MDLogFiltersActivity$UpdateFilterResult = iArr;
            try {
                iArr[UpdateFilterResult.UPDATE_FILTER_RESULT_DONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$mdlogfilters$MDLogFiltersActivity$UpdateFilterResult[UpdateFilterResult.UPDATE_FILTER_RESULT_IO_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$mdlogfilters$MDLogFiltersActivity$UpdateFilterResult[UpdateFilterResult.UPDATE_FILTER_RESULT_NO_RECORD_FILE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void extractFilterFiles() {
        if (FeatureSupport.isSupported(FeatureSupport.FK_SINGLE_BIN_MODEM_SUPPORT)) {
            File path = new File(mGenFilterPath);
            if (!path.isDirectory()) {
                path.mkdirs();
            }
            Elog.i(TAG, "extractFilterFiles with new version");
            int k = 0;
            while (true) {
                String[] strArr = KEY_WORD_LIST;
                if (k < strArr.length) {
                    AFMFunctionCallEx functionCall = new AFMFunctionCallEx();
                    if (functionCall.startCallFunctionStringReturn(AFMFunctionCallEx.FUNCTION_EM_MD_LOG_FILTER_GEN)) {
                        functionCall.writeParamNo(2);
                        functionCall.writeParamString(strArr[k]);
                        functionCall.writeParamString(mGenFilterPath + GEN_FILTER_FILE_LIST[k]);
                        do {
                        } while (functionCall.getNextResult().mReturnCode == 1);
                    }
                    k++;
                } else {
                    this.mSrcPath = mGenFilterPath;
                    return;
                }
            }
        } else {
            this.mSrcPath = mDefaultFilterPath;
        }
    }

    private class FileLoadTask extends AsyncTask<Void, Void, Void> {
        private FileLoadTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            MDLogFiltersActivity.this.showDialog(2);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            MDLogFiltersActivity.this.extractFilterFiles();
            Elog.i(MDLogFiltersActivity.TAG, "default filter path:" + MDLogFiltersActivity.this.mSrcPath);
            File srcFolder = new File(MDLogFiltersActivity.this.mSrcPath);
            if (!srcFolder.exists() || !srcFolder.isDirectory()) {
                return null;
            }
            File[] fileList = srcFolder.listFiles();
            ArrayList<String> tempFileList = new ArrayList<>();
            if (fileList == null) {
                return null;
            }
            for (File file : fileList) {
                String filePath = file.getAbsolutePath();
                Elog.i(MDLogFiltersActivity.TAG, "filePath:" + filePath);
                String filterName = MDLogFiltersActivity.this.extractFilterName(filePath, true);
                Elog.i(MDLogFiltersActivity.TAG, "filterName:" + filterName);
                if (filterName != null && !MDLogFiltersActivity.PLS_PS_ONLY_FILTER.equalsIgnoreCase(filterName)) {
                    if (MDLogFiltersActivity.DEFAULT_FILTER.equals(filterName)) {
                        MDLogFiltersActivity.this.mFilterList.add(new FilterInfo(filterName, filePath));
                        Elog.i(MDLogFiltersActivity.TAG, "add fileInfo:" + filterName + " " + filePath);
                    } else {
                        tempFileList.add(filePath);
                    }
                }
            }
            Collections.sort(tempFileList);
            Iterator<String> it = tempFileList.iterator();
            while (it.hasNext()) {
                String filePath2 = it.next();
                String filterName2 = MDLogFiltersActivity.this.extractFilterName(filePath2, true);
                if (filterName2 != null) {
                    String filterName3 = filterName2.replaceAll("_", " ");
                    MDLogFiltersActivity.this.mFilterList.add(new FilterInfo(filterName3, filePath2));
                    Elog.i(MDLogFiltersActivity.TAG, "add fileInfo:" + filterName3 + " " + filePath2);
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            MDLogFiltersActivity.this.removeDialog(2);
            MDLogFiltersActivity.this.showFilterList();
        }
    }

    /* access modifiers changed from: private */
    public void showFilterList() {
        Iterator<FilterInfo> it = this.mFilterList.iterator();
        while (it.hasNext()) {
            FilterInfo filterInfo = it.next();
            RadioButton radio = new RadioButton(this);
            radio.setText(filterInfo.getFilterName());
            radio.setTag(filterInfo);
            this.mRgFilterList.addView(radio);
            Elog.i(TAG, "mRgFilterList addView:" + filterInfo.getFilterName());
        }
        this.mRgFilterList.clearCheck();
    }

    /* access modifiers changed from: private */
    public boolean checkBinValid(String filePath) {
        Elog.i(TAG, "checkBinValid:" + filePath);
        InputStream is = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                is = new FileInputStream(file);
                byte[] bArr = VALID_BIN_BYTES;
                byte[] buffer = new byte[bArr.length];
                if (is.read(buffer) < bArr.length) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
                int k = 0;
                while (true) {
                    byte[] bArr2 = VALID_BIN_BYTES;
                    if (k >= bArr2.length) {
                        try {
                            is.close();
                            return true;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            return true;
                        }
                    } else if (bArr2[k] != buffer[k]) {
                        try {
                            is.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                        return false;
                    } else {
                        k++;
                    }
                }
            } else {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                return false;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public String extractFilterName(String name, boolean checkRule) {
        if (name == null) {
            return null;
        }
        Elog.i(TAG, "extractFilterName:" + name);
        int indexName = name.lastIndexOf(FileUtils.SEPARATOR);
        if (indexName == -1) {
            return null;
        }
        String name2 = name.substring(indexName + 1);
        if (!checkRule) {
            return name2.trim();
        }
        if (this.mPreFixIndex == -1) {
            for (int k = 0; k < 5; k++) {
                int indexOf = name2.indexOf("_", this.mPreFixIndex + 1);
                this.mPreFixIndex = indexOf;
                if (indexOf == -1) {
                    return null;
                }
            }
        }
        int postFixIndex = name2.indexOf(POST_FIX);
        int i = this.mPreFixIndex;
        if (i == -1 || postFixIndex == -1 || i >= postFixIndex) {
            return null;
        }
        return name2.substring(i + 1, postFixIndex).trim();
    }

    private class FilterInfo {
        private String mBinName;
        private String mFilterName;

        FilterInfo(String filterName, String binName) {
            this.mFilterName = filterName;
            this.mBinName = binName;
        }

        public String getFilterName() {
            return this.mFilterName;
        }

        public String getBinName() {
            return this.mBinName;
        }
    }
}
