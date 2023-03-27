package com.mediatek.engineermode.mcfconfig;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.EmUtils;
import com.mediatek.engineermode.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import vendor.mediatek.hardware.engineermode.V1_0.IEmCallback;

public class McfFileSelectActivity extends Activity implements AdapterView.OnItemClickListener {
    public static final int DIALOG_LOAD_FILES = 0;
    public static final int GEN_OPOTA_FILE_CODE = 2;
    public static final String MTK_OTA_CERT = "MTK_OTA_CERT_";
    public static final int OPOTA_FILE_CODE = 1;
    public static final String[] OP_OTA_SUFFIX = {"mcfopota", "bin"};
    public static final String OTA_AUTO_SELECT = "_AUTO_SELECT";
    public static final String[] OTA_DEFAULT = {"T", "S"};
    public static final int OTA_FILE_CODE = 0;
    public static final String OTA_PARENT = "T:";
    public static final String[] OTA_SUFFIX = {"mcfota", "bin"};
    public static final String RUNTIME_PARENT = "S:/mdota";
    public static final String SAVED_PATH_KEY = "saved_path";
    protected static final String TAG = "McfConfig/McfFileSelectActivity";
    private FileInfoAdapter adapter;
    /* access modifiers changed from: private */
    public boolean allowMutilFile;
    private Button btnAdd;
    private Button btnCancel;
    private String currentPath;
    private McfFileSelectActivity instance = this;
    /* access modifiers changed from: private */
    public ListView lvContent;
    /* access modifiers changed from: private */
    public IEmCallback mEmCallback = new IEmCallback.Stub() {
        public boolean callbackToClient(String dataStr) throws RemoteException {
            Elog.d(McfFileSelectActivity.TAG, "callbackToClient data = " + dataStr);
            McfFileSelectActivity.this.setFilePathListFromServer(dataStr);
            return true;
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<FileInfo> mFileList = new ArrayList<>();
    protected Bundle mSavedInstanceState = null;
    private int mcfFileType;
    private String root;

    public static void actionStart(Activity mActivity, String root2, int REQUEST, boolean allowMutilFile2) {
        Intent intent = new Intent(mActivity, McfFileSelectActivity.class);
        intent.putExtra("root", root2);
        intent.putExtra("file_type", REQUEST);
        intent.putExtra("mutil_file", allowMutilFile2);
        mActivity.startActivityForResult(intent, REQUEST);
    }

    public static void actionStart(Activity mActivity, String root2, int REQUEST) {
        actionStart(mActivity, root2, REQUEST, false);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = getIntent().getStringExtra("root");
        this.mcfFileType = getIntent().getIntExtra("file_type", 0);
        this.allowMutilFile = getIntent().getBooleanExtra("mutil_file", false);
        startSelectFile(this.root);
    }

    public void startSelectFile(String path) {
        this.currentPath = path;
        Elog.d(TAG, "current path: " + path + ", file_type:" + this.mcfFileType);
        new FileLoadTask().execute(new String[]{this.currentPath});
        initLayout();
    }

    public void initLayout() {
        String savePath;
        setContentView(R.layout.select_file_main);
        this.btnCancel = (Button) findViewById(R.id.select_cancel);
        this.btnAdd = (Button) findViewById(R.id.select_add);
        ListView listView = (ListView) findViewById(R.id.list_view);
        this.lvContent = listView;
        if (listView != null) {
            listView.setEmptyView(findViewById(R.id.empty_view));
            this.lvContent.setOnItemClickListener(this);
            this.lvContent.setFastScrollEnabled(true);
            this.lvContent.setVerticalScrollBarEnabled(true);
        }
        Bundle bundle = this.mSavedInstanceState;
        if (!(bundle == null || (savePath = bundle.getString(SAVED_PATH_KEY)) == null)) {
            this.currentPath = savePath;
            Elog.d(TAG, "onCreate, mCurrentPath updated to = " + this.currentPath);
        }
        initEvent();
    }

    private void initEvent() {
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Elog.d(McfFileSelectActivity.TAG, "click 'Cancel' to quit directly ");
                McfFileSelectActivity.this.finish();
            }
        });
        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Elog.d(McfFileSelectActivity.TAG, "click 'Add' to quit directly ");
                ArrayList<Uri> selecteFileList = new ArrayList<>();
                Intent intent = new Intent();
                for (int i = 0; i < McfFileSelectActivity.this.lvContent.getCount(); i++) {
                    if (((FileInfoAdapter) McfFileSelectActivity.this.lvContent.getAdapter()).getItem(i).isChecked()) {
                        selecteFileList.add(((FileInfo) McfFileSelectActivity.this.lvContent.getItemAtPosition(i)).getUri());
                    }
                }
                Elog.d(McfFileSelectActivity.TAG, "onItemClick RESULT_OK, uri : " + Arrays.toString(selecteFileList.toArray()));
                intent.putParcelableArrayListExtra("multipleFile", selecteFileList);
                McfFileSelectActivity.this.setResult(-1, intent);
                McfFileSelectActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    public void refreshView(boolean allowMutilFile2) {
        FileInfoAdapter fileInfoAdapter = this.adapter;
        if (fileInfoAdapter == null) {
            FileInfoAdapter fileInfoAdapter2 = new FileInfoAdapter(this.instance, this.mFileList);
            this.adapter = fileInfoAdapter2;
            this.lvContent.setAdapter(fileInfoAdapter2);
            this.lvContent.setOnItemClickListener(this);
        } else {
            fileInfoAdapter.setAdapterList(this.mFileList);
            this.adapter.notifyDataSetChanged();
            this.lvContent.setAdapter(this.adapter);
        }
        if (allowMutilFile2) {
            this.adapter.allowMutilFile();
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id, Bundle bundle) {
        switch (id) {
            case 0:
                ProgressDialog dlgQueryFilters = new ProgressDialog(this);
                dlgQueryFilters.setMessage(getString(R.string.md_log_filter_query_filters));
                dlgQueryFilters.setCancelable(false);
                dlgQueryFilters.setIndeterminate(true);
                return dlgQueryFilters;
            default:
                return null;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileInfo selecteItemFileInfo = (FileInfo) parent.getItemAtPosition(position);
        if (selecteItemFileInfo.isDir()) {
            Elog.d(TAG, "select " + selecteItemFileInfo.getFileAbsolutePath());
            startSelectFile(this.currentPath + FileUtils.SEPARATOR + selecteItemFileInfo.getFileName());
            return;
        }
        Intent intent = new Intent();
        Uri uri = selecteItemFileInfo.getUri();
        Elog.d(TAG, "onItemClick RESULT_OK, uri : " + uri);
        intent.setData(uri);
        setResult(-1, intent);
        finish();
    }

    public void onBackPressed() {
        String str = this.currentPath;
        if (str == null || str.equals(this.root)) {
            super.onBackPressed();
            return;
        }
        Elog.d(TAG, "onBackPressed :" + this.currentPath + ", " + this.root);
        startSelectFile(FileUtils.getFilePath(this.currentPath));
    }

    public boolean checkFileValid(FileInfo file) {
        String fileExtension = FileUtils.getFileExtension(file.getFileName());
        switch (this.mcfFileType) {
            case 0:
                return Arrays.asList(OTA_SUFFIX).contains(fileExtension);
            case 1:
            case 2:
                return Arrays.asList(OP_OTA_SUFFIX).contains(fileExtension);
            default:
                return true;
        }
    }

    public void setFilePathListFromServer(String mReturnString) {
        this.mFileList.clear();
        if (mReturnString == null || mReturnString.trim().equals("")) {
            Elog.d(TAG, "[setFilePathListFromServer] return empty");
            return;
        }
        Elog.i(TAG, "add fileInfo:" + mReturnString);
        String[] fileArray = mReturnString.split(";");
        for (String split : fileArray) {
            FileInfo fileInfo = null;
            String[] file = split.split(":");
            if (file != null) {
                if (file.length == 2 && file[0] != null) {
                    try {
                        fileInfo = new FileInfo(file[0], Long.parseLong(file[1]));
                    } catch (NumberFormatException e) {
                        fileInfo = new FileInfo(file[0], -1);
                        Elog.e(TAG, "getFileSize occurs exception:" + e.getMessage());
                    }
                } else if (file.length == 1 && file[0] != null) {
                    fileInfo = new FileInfo(file[0], -1);
                }
                if (fileInfo != null && (fileInfo.isDir() || checkFileValid(fileInfo))) {
                    this.mFileList.add(fileInfo);
                }
            }
        }
    }

    class FileLoadTask extends AsyncTask<String, Void, Void> {
        FileLoadTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            McfFileSelectActivity.this.showDialog(0);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... params) {
            try {
                EmUtils.getEmHidlService().getFilePathListWithCallBack(params[0], McfFileSelectActivity.this.mEmCallback);
                Elog.d(McfFileSelectActivity.TAG, "" + Arrays.toString(McfFileSelectActivity.this.mFileList.toArray()));
                Collections.sort(McfFileSelectActivity.this.mFileList, new Comparator<FileInfo>() {
                    public int compare(FileInfo lhs, FileInfo rhs) {
                        return lhs.getFileName().compareTo(rhs.getFileName());
                    }
                });
                return null;
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            McfFileSelectActivity.this.removeDialog(0);
            McfFileSelectActivity mcfFileSelectActivity = McfFileSelectActivity.this;
            mcfFileSelectActivity.refreshView(mcfFileSelectActivity.allowMutilFile);
        }
    }
}
