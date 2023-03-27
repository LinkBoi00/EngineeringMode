package com.mediatek.engineermode.iotconfig;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.iotconfig.LinkedHorizontalScrollView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VilteConfigFragment extends Fragment {
    private static final String TAG = "Iot/VilteConfigFragment";
    public static final int UNKNOW = 4;
    private static final int VILTE_ACTION_GET_DEFAULT_PROFILE = 0;
    private static final int VILTE_ACTION_WRITE_PROFILE = 1;
    private static final String VILTE_DEFAULT_FILE_NAME = "viLTE_media_profiles.xml";
    private static final String VILTE_DEFAULT_TEMPLATE_FILE_NAME = "viLTE_media_profiles_opxx.xml";
    public static final int VILTE_READ_DEFAULT_PROFILE_FAILED = 0;
    public static final int VILTE_READ_DEFAULT_PROFILE_OK = 1;
    public static final int VILTE_WRITE_PROFILE_FAILED = 2;
    public static final int VILTE_WRITE_PROFILE_OK = 3;
    public static ArrayList<VideoProfileModel> selectProfileList;
    public static ArrayList<VideoQualityModel> selectqualityList;
    private HashMap<String, Integer> VideoQualityMapping = new HashMap<>();
    private View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_profile_cancel:
                    new XmlParserThread(0).start();
                    return;
                case R.id.btn_profile_reset:
                    VilteConfigFragment.this.deleteFileFromInnerSDCard(VilteConfigFragment.VILTE_DEFAULT_FILE_NAME);
                    new XmlParserThread(0).start();
                    return;
                case R.id.btn_profile_save:
                    VilteConfigFragment vilteConfigFragment = VilteConfigFragment.this;
                    vilteConfigFragment.refreshSelectProfileList(vilteConfigFragment.profileAdapter.getList());
                    VilteConfigFragment.this.refreshVideoProfileView(true);
                    new XmlParserThread(1).start();
                    return;
                case R.id.btn_show_help:
                    VilteConfigFragment vilteConfigFragment2 = VilteConfigFragment.this;
                    vilteConfigFragment2.showInfoDialog(vilteConfigFragment2.getActivity(), VilteConfigFragment.this.getActivity().getResources().getString(R.string.vilte_help_content));
                    return;
                default:
                    return;
            }
        }
    };
    private Button btnProfileCancel;
    private Button btnProfileReset;
    private Button btnProfileSave;
    private Button btnShowHelp;
    /* access modifiers changed from: private */
    public String curFileName = "";
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    VilteConfigFragment.this.showToast("Read profile template failed!");
                    return;
                case 1:
                    VilteConfigFragment.this.showToast("Read profile template completed!");
                    VilteConfigFragment.this.updateVideoProfileUI();
                    return;
                case 2:
                    VilteConfigFragment.this.showToast("Write profile failed!");
                    return;
                case 3:
                    VilteConfigFragment.this.showToast("Write profile completed!");
                    VilteConfigFragment.this.operatorId.setText("");
                    new XmlParserThread(0).start();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public EditText operatorId;
    /* access modifiers changed from: private */
    public VideoProfileAdapter profileAdapter;
    private LinkedHorizontalScrollView profileContentScrollView;
    ArrayList<VideoProfileModel> profileModelList;
    private HorizontalScrollView profileTitleScrollView;
    private LinearLayout videoProfileContent;
    private ListView videoProfileListView;

    public static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public void showInfoDialog(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage(content).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public boolean deleteFileFromInnerSDCard(String fileName) {
        File file = new File(getInnerSDCardPath() + File.separator + fileName);
        if (!file.isFile() || !file.exists()) {
            return false;
        }
        file.delete();
        return true;
    }

    public String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public void showToast(String content) {
        Toast.makeText(getActivity(), content, 0).show();
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x001c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkSelectQualityList(java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoQualityModel> r6) {
        /*
            r5 = this;
            java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoQualityModel> r0 = selectqualityList
            int r0 = r0.size()
            r1 = 0
            r2 = 4
            if (r0 == r2) goto L_0x000b
            return r1
        L_0x000b:
            java.util.HashMap<java.lang.String, java.lang.Integer> r0 = r5.VideoQualityMapping
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0015:
            boolean r2 = r0.hasNext()
            r3 = 1
            if (r2 == 0) goto L_0x003b
            java.lang.Object r2 = r0.next()
            java.lang.String r2 = (java.lang.String) r2
            java.util.HashMap<java.lang.String, java.lang.Integer> r4 = r5.VideoQualityMapping
            java.lang.Object r4 = r4.get(r2)
            if (r4 == 0) goto L_0x003a
            java.util.HashMap<java.lang.String, java.lang.Integer> r4 = r5.VideoQualityMapping
            java.lang.Object r4 = r4.get(r2)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 == r3) goto L_0x0039
            goto L_0x003a
        L_0x0039:
            goto L_0x0015
        L_0x003a:
            return r1
        L_0x003b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.iotconfig.VilteConfigFragment.checkSelectQualityList(java.util.ArrayList):boolean");
    }

    /* access modifiers changed from: private */
    public void refreshSelectProfileList(ArrayList<VideoProfileModel> list) {
        selectProfileList.clear();
        selectqualityList.clear();
        resetVideoQualityMapping();
        Iterator<VideoProfileModel> it = list.iterator();
        while (it.hasNext()) {
            VideoProfileModel profileModel = it.next();
            if (profileModel.isSelected()) {
                selectProfileList.add(profileModel);
                if (!profileModel.getQuality().equals("")) {
                    selectqualityList.add(new VideoQualityModel(profileModel.getQuality(), profileModel.getName(), profileModel.getProfile(), profileModel.getLevel()));
                    Elog.d(TAG, "[refreshSelectProfileList]getQuality() key: " + profileModel.getQuality() + " value: " + this.VideoQualityMapping.get(profileModel.getQuality()));
                    this.VideoQualityMapping.put(profileModel.getQuality(), Integer.valueOf(this.VideoQualityMapping.get(profileModel.getQuality()).intValue() + 1));
                }
            }
        }
        if (!checkSelectQualityList(selectqualityList)) {
            selectProfileList.clear();
            selectqualityList.clear();
            resetVideoQualityMapping();
            showInfoDialog(getActivity(), getActivity().getResources().getString(R.string.vilte_quality_size_error));
        }
    }

    public void updateVideoProfileUI() {
        if (this.profileAdapter == null) {
            VideoProfileAdapter videoProfileAdapter = new VideoProfileAdapter(getActivity(), this.profileModelList);
            this.profileAdapter = videoProfileAdapter;
            this.videoProfileListView.setAdapter(videoProfileAdapter);
        }
        this.profileAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public void refreshVideoProfileView(boolean isShow) {
        if (isShow) {
            updateVideoProfileUI();
            this.videoProfileContent.setVisibility(0);
            return;
        }
        this.videoProfileContent.setVisibility(8);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iot_vilte_config, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        resetVideoQualityMapping(view);
        selectProfileList = new ArrayList<>();
        this.profileModelList = new ArrayList<>();
        selectqualityList = new ArrayList<>();
        this.videoProfileContent = (LinearLayout) view.findViewById(R.id.video_profile_content);
        Button button = (Button) view.findViewById(R.id.btn_show_help);
        this.btnShowHelp = button;
        button.setOnClickListener(this.btnOnClickListener);
        this.operatorId = (EditText) view.findViewById(R.id.operator_id_input);
        Button button2 = (Button) view.findViewById(R.id.btn_profile_save);
        this.btnProfileSave = button2;
        button2.setOnClickListener(this.btnOnClickListener);
        Button button3 = (Button) view.findViewById(R.id.btn_profile_cancel);
        this.btnProfileCancel = button3;
        button3.setOnClickListener(this.btnOnClickListener);
        Button button4 = (Button) view.findViewById(R.id.btn_profile_reset);
        this.btnProfileReset = button4;
        button4.setOnClickListener(this.btnOnClickListener);
        ListView listView = (ListView) view.findViewById(R.id.video_profile_list);
        this.videoProfileListView = listView;
        listView.setAdapter(this.profileAdapter);
        this.profileTitleScrollView = (HorizontalScrollView) view.findViewById(R.id.video_profile_title);
        LinkedHorizontalScrollView linkedHorizontalScrollView = (LinkedHorizontalScrollView) view.findViewById(R.id.video_profile_view);
        this.profileContentScrollView = linkedHorizontalScrollView;
        combination(this.videoProfileListView, this.profileTitleScrollView, linkedHorizontalScrollView);
        refreshVideoProfileView(true);
    }

    public void resetVideoQualityMapping(View view) {
        this.VideoQualityMapping.clear();
        this.VideoQualityMapping.put(view.getResources().getStringArray(R.array.video_quality_mode)[1], 0);
        this.VideoQualityMapping.put(view.getResources().getStringArray(R.array.video_quality_mode)[2], 0);
        this.VideoQualityMapping.put(view.getResources().getStringArray(R.array.video_quality_mode)[3], 0);
        this.VideoQualityMapping.put(view.getResources().getStringArray(R.array.video_quality_mode)[4], 0);
    }

    public void resetVideoQualityMapping() {
        for (String key : this.VideoQualityMapping.keySet()) {
            this.VideoQualityMapping.put(key, 0);
        }
    }

    public class XmlParserThread extends Thread {
        private int action;

        public XmlParserThread(int action2) {
            this.action = action2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:117:?, code lost:
            r4.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x0356, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:121:0x035b, code lost:
            r11.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x035f, code lost:
            r0.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
            r4.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:0x036c, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:129:0x0371, code lost:
            r11.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:130:0x0375, code lost:
            r0.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:154:0x048f, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:155:0x0490, code lost:
            r4 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:156:0x0492, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x0493, code lost:
            r4 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:158:0x0495, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:159:0x0496, code lost:
            r2 = r0;
            r4 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:160:0x0499, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:0x049a, code lost:
            r2 = r0;
            r4 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:162:0x049d, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:163:0x049e, code lost:
            r3.what = 0;
            r0.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:0x02ac, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:83:0x02ad, code lost:
            r5 = r0;
            r2 = r18;
            r4 = r22;
            r7 = r23;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:96:0x02fa, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:97:0x02fb, code lost:
            r5 = r0;
            r2 = r18;
            r4 = r22;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:98:0x0302, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:0x0303, code lost:
            r7 = r23;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:116:0x0352 A[SYNTHETIC, Splitter:B:116:0x0352] */
        /* JADX WARNING: Removed duplicated region for block: B:121:0x035b A[Catch:{ IOException -> 0x0356 }] */
        /* JADX WARNING: Removed duplicated region for block: B:124:0x0368 A[SYNTHETIC, Splitter:B:124:0x0368] */
        /* JADX WARNING: Removed duplicated region for block: B:129:0x0371 A[Catch:{ IOException -> 0x036c }] */
        /* JADX WARNING: Removed duplicated region for block: B:162:0x049d A[ExcHandler: IOException (r0v4 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:134:0x0385] */
        /* JADX WARNING: Removed duplicated region for block: B:70:0x0220 A[Catch:{ Exception -> 0x02ac, all -> 0x02fa }] */
        /* JADX WARNING: Removed duplicated region for block: B:71:0x022b A[ADDED_TO_REGION, Catch:{ Exception -> 0x02ac, all -> 0x02fa }] */
        /* JADX WARNING: Removed duplicated region for block: B:96:0x02fa A[ExcHandler: all (r0v22 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r22 
          PHI: (r22v5 'br' java.io.BufferedReader) = (r22v4 'br' java.io.BufferedReader), (r22v4 'br' java.io.BufferedReader), (r22v4 'br' java.io.BufferedReader), (r22v7 'br' java.io.BufferedReader) binds: [B:85:0x02c7, B:88:0x02e2, B:89:?, B:57:0x0167] A[DONT_GENERATE, DONT_INLINE], Splitter:B:57:0x0167] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r28 = this;
                r1 = r28
                java.lang.String r2 = "viLTE_media_profiles_opxx.xml"
                android.os.Message r3 = new android.os.Message
                r3.<init>()
                int r4 = r1.action
                java.lang.String r5 = "vilte_media_profiles_opxx.xml"
                java.lang.String r6 = "viLTE_media_profiles.xml"
                java.lang.String r7 = "Iot/VilteConfigFragment"
                java.lang.String r9 = ""
                switch(r4) {
                    case 0: goto L_0x0380;
                    case 1: goto L_0x001b;
                    default: goto L_0x0016;
                }
            L_0x0016:
                r2 = 4
                r3.what = r2
                goto L_0x04b8
            L_0x001b:
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r2 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this
                android.widget.EditText r2 = r2.operatorId
                android.text.Editable r2 = r2.getText()
                java.lang.String r2 = r2.toString()
                java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r4 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.selectProfileList
                int r4 = r4.size()
                if (r4 == 0) goto L_0x037b
                java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoQualityModel> r4 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.selectqualityList
                int r4 = r4.size()
                if (r4 != 0) goto L_0x003b
                goto L_0x037b
            L_0x003b:
                r4 = 0
                r11 = 0
                if (r2 == 0) goto L_0x0063
                java.lang.String r12 = " "
                java.lang.String r12 = r2.replace(r12, r9)     // Catch:{ Exception -> 0x005f }
                boolean r12 = r12.equals(r9)     // Catch:{ Exception -> 0x005f }
                if (r12 != 0) goto L_0x0063
                boolean r12 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.isNumeric(r2)     // Catch:{ Exception -> 0x005f }
                if (r12 == 0) goto L_0x0063
                int r12 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x005f }
                java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ Exception -> 0x005f }
                r2 = r12
                goto L_0x0063
            L_0x005b:
                r0 = move-exception
                r5 = r0
                goto L_0x0366
            L_0x005f:
                r0 = move-exception
                r5 = r0
                goto L_0x0330
            L_0x0063:
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                r12.<init>()     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                java.lang.String r13 = "Write  "
                r12.append(r13)     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r13 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                java.lang.String r13 = r13.curFileName     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                r12.append(r13)     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                com.mediatek.engineermode.Elog.d(r7, r12)     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r12 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                java.lang.String r12 = r12.curFileName     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                boolean r5 = r12.equals(r5)     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                if (r5 == 0) goto L_0x00ad
                java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x005f }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r12 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ Exception -> 0x005f }
                android.support.v4.app.FragmentActivity r12 = r12.getActivity()     // Catch:{ Exception -> 0x005f }
                android.content.Context r12 = r12.getApplicationContext()     // Catch:{ Exception -> 0x005f }
                android.content.res.AssetManager r12 = r12.getAssets()     // Catch:{ Exception -> 0x005f }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r13 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ Exception -> 0x005f }
                java.lang.String r13 = r13.curFileName     // Catch:{ Exception -> 0x005f }
                java.io.InputStream r12 = r12.open(r13)     // Catch:{ Exception -> 0x005f }
                r5.<init>(r12)     // Catch:{ Exception -> 0x005f }
                java.io.BufferedReader r12 = new java.io.BufferedReader     // Catch:{ Exception -> 0x005f }
                r12.<init>(r5)     // Catch:{ Exception -> 0x005f }
                r4 = r12
                goto L_0x00c3
            L_0x00ad:
                java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r12 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                java.lang.String r12 = r12.curFileName     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                r5.<init>(r12)     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                java.io.BufferedReader r12 = new java.io.BufferedReader     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                java.io.FileReader r13 = new java.io.FileReader     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                r13.<init>(r5)     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                r12.<init>(r13)     // Catch:{ Exception -> 0x032c, all -> 0x0327 }
                r4 = r12
            L_0x00c3:
                java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x0320, all -> 0x0319 }
                r5.<init>()     // Catch:{ Exception -> 0x0320, all -> 0x0319 }
                r12 = r9
            L_0x00c9:
                java.lang.String r13 = r4.readLine()     // Catch:{ Exception -> 0x0320, all -> 0x0319 }
                r12 = r13
                if (r13 == 0) goto L_0x00d4
                r5.add(r12)     // Catch:{ Exception -> 0x005f }
                goto L_0x00c9
            L_0x00d4:
                r13 = 0
                r14 = 0
                java.lang.StringBuffer r15 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0320, all -> 0x0319 }
                r15.<init>()     // Catch:{ Exception -> 0x0320, all -> 0x0319 }
                r16 = r9
                r17 = r9
                java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0320, all -> 0x0319 }
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0320, all -> 0x0319 }
                r8.<init>()     // Catch:{ Exception -> 0x0320, all -> 0x0319 }
                r18 = r2
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r2 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                java.lang.String r2 = r2.getInnerSDCardPath()     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r8.append(r2)     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                java.lang.String r2 = java.io.File.separator     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r8.append(r2)     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r8.append(r6)     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                java.lang.String r2 = r8.toString()     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r10.<init>(r2)     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r2 = r10
                java.io.BufferedWriter r6 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                java.io.FileWriter r8 = new java.io.FileWriter     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r10 = 0
                r8.<init>(r2, r10)     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r6.<init>(r8)     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r11 = r6
                java.util.Iterator r6 = r5.iterator()     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r8 = r16
                r10 = r17
            L_0x0115:
                boolean r16 = r6.hasNext()     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                if (r16 == 0) goto L_0x02b6
                java.lang.Object r16 = r6.next()     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                java.lang.String r16 = (java.lang.String) r16     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r17 = r16
                r16 = r2
                java.lang.String r2 = "<VideoProfile name="
                r19 = r5
                r5 = r17
                boolean r2 = r5.contains(r2)     // Catch:{ Exception -> 0x0312, all -> 0x030b }
                r17 = r6
                java.lang.String r6 = "name"
                if (r2 == 0) goto L_0x0153
                java.lang.String[] r2 = r5.split(r6)     // Catch:{ Exception -> 0x014d, all -> 0x0147 }
                r6 = 0
                r2 = r2[r6]     // Catch:{ Exception -> 0x014d, all -> 0x0147 }
                r8 = r2
                r22 = r4
                r23 = r7
                r20 = r12
                r21 = r15
                goto L_0x029c
            L_0x0147:
                r0 = move-exception
                r5 = r0
                r2 = r18
                goto L_0x0366
            L_0x014d:
                r0 = move-exception
                r5 = r0
                r2 = r18
                goto L_0x0330
            L_0x0153:
                java.lang.String r2 = "\"/>"
                r20 = r12
                java.lang.String r12 = "\" level=\""
                r21 = r15
                java.lang.String r15 = "\" profile=\""
                r22 = r4
                java.lang.String r4 = "name=\""
                r23 = r7
                java.lang.String r7 = "\r\n"
                if (r13 != 0) goto L_0x0212
                boolean r24 = r8.equals(r9)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                if (r24 != 0) goto L_0x020d
                java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoProfileModel> r24 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.selectProfileList     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.util.Iterator r24 = r24.iterator()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
            L_0x0173:
                boolean r25 = r24.hasNext()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                if (r25 == 0) goto L_0x0207
                java.lang.Object r25 = r24.next()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                com.mediatek.engineermode.iotconfig.VideoProfileModel r25 = (com.mediatek.engineermode.iotconfig.VideoProfileModel) r25     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r26 = r13
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.<init>()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r4)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r27 = r8
                java.lang.String r8 = r25.getName()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r15)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r25.getProfile()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r12)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r25.getLevel()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = "\" width=\""
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r25.getWidth()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = "\" height=\""
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r25.getHeight()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = "\" framerate=\""
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r25.getFramerate()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = "\" Iinterval=\""
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r25.getIinterval()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = "\" minBitRate=\""
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r25.getMinBitRate()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = "\" maxBitRate=\""
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r25.getMaxBitRate()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13.append(r2)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r8 = r13.toString()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r11.write(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r11.write(r7)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r11.flush()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r13 = r26
                r8 = r27
                goto L_0x0173
            L_0x0207:
                r27 = r8
                r26 = r13
                r13 = 1
                goto L_0x0218
            L_0x020d:
                r27 = r8
                r26 = r13
                goto L_0x0216
            L_0x0212:
                r27 = r8
                r26 = r13
            L_0x0216:
                r13 = r26
            L_0x0218:
                java.lang.String r8 = "<VideoQuality name="
                boolean r8 = r5.contains(r8)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                if (r8 == 0) goto L_0x022b
                java.lang.String[] r2 = r5.split(r6)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r4 = 0
                r2 = r2[r4]     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r10 = r2
                r8 = r27
                goto L_0x029c
            L_0x022b:
                if (r14 != 0) goto L_0x0291
                boolean r6 = r10.equals(r9)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                if (r6 != 0) goto L_0x0291
                java.util.ArrayList<com.mediatek.engineermode.iotconfig.VideoQualityModel> r6 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.selectqualityList     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.util.Iterator r6 = r6.iterator()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
            L_0x0239:
                boolean r8 = r6.hasNext()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                if (r8 == 0) goto L_0x0290
                java.lang.Object r8 = r6.next()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                com.mediatek.engineermode.iotconfig.VideoQualityModel r8 = (com.mediatek.engineermode.iotconfig.VideoQualityModel) r8     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r24 = r6
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.<init>()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r10)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r4)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r25 = r4
                java.lang.String r4 = r8.getName()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r4)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r4 = "\" format=\""
                r6.append(r4)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r4 = r8.getFormat()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r4)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r15)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r4 = r8.getProfile()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r4)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r12)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r4 = r8.getLevel()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r4)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6.append(r2)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                java.lang.String r4 = r6.toString()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r11.write(r4)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r11.write(r7)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r11.flush()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r6 = r24
                r4 = r25
                goto L_0x0239
            L_0x0290:
                r14 = 1
            L_0x0291:
                r11.write(r5)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r11.write(r7)     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r11.flush()     // Catch:{ Exception -> 0x02ac, all -> 0x02fa }
                r8 = r27
            L_0x029c:
                r2 = r16
                r6 = r17
                r5 = r19
                r12 = r20
                r15 = r21
                r4 = r22
                r7 = r23
                goto L_0x0115
            L_0x02ac:
                r0 = move-exception
                r5 = r0
                r2 = r18
                r4 = r22
                r7 = r23
                goto L_0x0330
            L_0x02b6:
                r16 = r2
                r22 = r4
                r19 = r5
                r23 = r7
                r27 = r8
                r20 = r12
                r26 = r13
                r21 = r15
                r2 = 3
                r3.what = r2     // Catch:{ Exception -> 0x0302, all -> 0x02fa }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0302, all -> 0x02fa }
                r2.<init>()     // Catch:{ Exception -> 0x0302, all -> 0x02fa }
                java.lang.String r4 = "Write success:"
                r2.append(r4)     // Catch:{ Exception -> 0x0302, all -> 0x02fa }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r4 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ Exception -> 0x0302, all -> 0x02fa }
                java.lang.String r4 = r4.curFileName     // Catch:{ Exception -> 0x0302, all -> 0x02fa }
                r2.append(r4)     // Catch:{ Exception -> 0x0302, all -> 0x02fa }
                java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0302, all -> 0x02fa }
                r7 = r23
                com.mediatek.engineermode.Elog.d(r7, r2)     // Catch:{ Exception -> 0x02f8, all -> 0x02fa }
                r22.close()     // Catch:{ IOException -> 0x02ee }
                r11.close()     // Catch:{ IOException -> 0x02ee }
                goto L_0x02f4
            L_0x02ee:
                r0 = move-exception
                r2 = r0
                r2.printStackTrace()
            L_0x02f4:
                r2 = r18
                goto L_0x0364
            L_0x02f8:
                r0 = move-exception
                goto L_0x0305
            L_0x02fa:
                r0 = move-exception
                r5 = r0
                r2 = r18
                r4 = r22
                goto L_0x0366
            L_0x0302:
                r0 = move-exception
                r7 = r23
            L_0x0305:
                r5 = r0
                r2 = r18
                r4 = r22
                goto L_0x0330
            L_0x030b:
                r0 = move-exception
                r22 = r4
                r5 = r0
                r2 = r18
                goto L_0x0366
            L_0x0312:
                r0 = move-exception
                r22 = r4
                r5 = r0
                r2 = r18
                goto L_0x0330
            L_0x0319:
                r0 = move-exception
                r18 = r2
                r22 = r4
                r5 = r0
                goto L_0x0366
            L_0x0320:
                r0 = move-exception
                r18 = r2
                r22 = r4
                r5 = r0
                goto L_0x0330
            L_0x0327:
                r0 = move-exception
                r18 = r2
                r5 = r0
                goto L_0x0366
            L_0x032c:
                r0 = move-exception
                r18 = r2
                r5 = r0
            L_0x0330:
                r5.printStackTrace()     // Catch:{ all -> 0x005b }
                r6 = 2
                r3.what = r6     // Catch:{ all -> 0x005b }
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x005b }
                r6.<init>()     // Catch:{ all -> 0x005b }
                java.lang.String r8 = "Read failed:"
                r6.append(r8)     // Catch:{ all -> 0x005b }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r8 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ all -> 0x005b }
                java.lang.String r8 = r8.curFileName     // Catch:{ all -> 0x005b }
                r6.append(r8)     // Catch:{ all -> 0x005b }
                java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x005b }
                com.mediatek.engineermode.Elog.d(r7, r6)     // Catch:{ all -> 0x005b }
                if (r4 == 0) goto L_0x0359
                r4.close()     // Catch:{ IOException -> 0x0356 }
                goto L_0x0359
            L_0x0356:
                r0 = move-exception
                r5 = r0
                goto L_0x035f
            L_0x0359:
                if (r11 == 0) goto L_0x0363
                r11.close()     // Catch:{ IOException -> 0x0356 }
                goto L_0x0363
            L_0x035f:
                r5.printStackTrace()
                goto L_0x0364
            L_0x0363:
            L_0x0364:
                goto L_0x04b8
            L_0x0366:
                if (r4 == 0) goto L_0x036f
                r4.close()     // Catch:{ IOException -> 0x036c }
                goto L_0x036f
            L_0x036c:
                r0 = move-exception
                r6 = r0
                goto L_0x0375
            L_0x036f:
                if (r11 == 0) goto L_0x0379
                r11.close()     // Catch:{ IOException -> 0x036c }
                goto L_0x0379
            L_0x0375:
                r6.printStackTrace()
                goto L_0x037a
            L_0x0379:
            L_0x037a:
                throw r5
            L_0x037b:
                r4 = 2
                r3.what = r4
                goto L_0x04b8
            L_0x0380:
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r4 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this
                java.lang.String unused = r4.curFileName = r9
                org.xmlpull.v1.XmlPullParserFactory r4 = org.xmlpull.v1.XmlPullParserFactory.newInstance()     // Catch:{ XmlPullParserException -> 0x04af, FileNotFoundException -> 0x04a6, IOException -> 0x049d }
                r8 = 1
                r4.setNamespaceAware(r8)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                org.xmlpull.v1.XmlPullParser r10 = r4.newPullParser()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r11 = 0
                java.io.File r12 = new java.io.File     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r13.<init>()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r14 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r14 = r14.getInnerSDCardPath()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r13.append(r14)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r14 = java.io.File.separator     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r13.append(r14)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r13.append(r6)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r13 = r13.toString()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.<init>(r13)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                boolean r12 = r12.exists()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                if (r12 == 0) goto L_0x03d8
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r2 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.<init>()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r13 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r13 = r13.getInnerSDCardPath()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r13)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r13 = java.io.File.separator     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r13)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r6)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r6 = r12.toString()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String unused = r2.curFileName = r6     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r11 = 1
                goto L_0x041c
            L_0x03d8:
                java.io.File r6 = new java.io.File     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.<init>()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r13 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r13 = r13.getInnerSDCardPath()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r13)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r13 = java.io.File.separator     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r13)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r2)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r12 = r12.toString()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r6.<init>(r12)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                boolean r6 = r6.exists()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                if (r6 == 0) goto L_0x041c
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r6 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.<init>()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r13 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r13 = r13.getInnerSDCardPath()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r13)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r13 = java.io.File.separator     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r13)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r12.append(r2)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r2 = r12.toString()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String unused = r6.curFileName = r2     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
            L_0x041c:
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r2 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r2 = r2.curFileName     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                boolean r2 = r2.equals(r9)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                if (r2 != 0) goto L_0x0456
                java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r5 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r5 = r5.curFileName     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r2.<init>(r5)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r5.<init>()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r6 = "Read from"
                r5.append(r6)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r6 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r6 = r6.curFileName     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r5.append(r6)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r5 = r5.toString()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.Elog.d(r7, r5)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r5.<init>(r2)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r10.setInput(r5)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                goto L_0x0480
            L_0x0456:
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r2 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String unused = r2.curFileName = r5     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r5 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                android.support.v4.app.FragmentActivity r5 = r5.getActivity()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                android.content.Context r5 = r5.getApplicationContext()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                android.content.res.AssetManager r5 = r5.getAssets()     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r6 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r6 = r6.curFileName     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.io.InputStream r5 = r5.open(r6)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r2.<init>(r5)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                r10.setInput(r2)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                java.lang.String r2 = "Read from R.xml.vilte_media_profiles_opxx"
                com.mediatek.engineermode.Elog.d(r7, r2)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
            L_0x0480:
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r2 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                boolean r2 = r2.getXMLContent(r10, r11)     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                if (r2 == 0) goto L_0x048b
                r3.what = r8     // Catch:{ XmlPullParserException -> 0x0499, FileNotFoundException -> 0x0495, IOException -> 0x049d }
                goto L_0x048e
            L_0x048b:
                r2 = 0
                r3.what = r2     // Catch:{ XmlPullParserException -> 0x0492, FileNotFoundException -> 0x048f, IOException -> 0x049d }
            L_0x048e:
                goto L_0x04b8
            L_0x048f:
                r0 = move-exception
                r4 = r2
                goto L_0x04a8
            L_0x0492:
                r0 = move-exception
                r4 = r2
                goto L_0x04b1
            L_0x0495:
                r0 = move-exception
                r2 = r0
                r4 = 0
                goto L_0x04a9
            L_0x0499:
                r0 = move-exception
                r2 = r0
                r4 = 0
                goto L_0x04b2
            L_0x049d:
                r0 = move-exception
                r2 = r0
                r4 = 0
                r3.what = r4
                r2.printStackTrace()
                goto L_0x04b8
            L_0x04a6:
                r0 = move-exception
                r4 = 0
            L_0x04a8:
                r2 = r0
            L_0x04a9:
                r3.what = r4
                r2.printStackTrace()
                goto L_0x04b8
            L_0x04af:
                r0 = move-exception
                r4 = 0
            L_0x04b1:
                r2 = r0
            L_0x04b2:
                r3.what = r4
                r2.printStackTrace()
            L_0x04b8:
                com.mediatek.engineermode.iotconfig.VilteConfigFragment r2 = com.mediatek.engineermode.iotconfig.VilteConfigFragment.this
                android.os.Handler r2 = r2.mHandler
                r2.sendMessage(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.iotconfig.VilteConfigFragment.XmlParserThread.run():void");
        }
    }

    private void combination(ListView lvDetail, final HorizontalScrollView title, LinkedHorizontalScrollView content) {
        content.setMyScrollChangeListener(new LinkedHorizontalScrollView.LinkScrollChangeListener() {
            public void onscroll(LinkedHorizontalScrollView view, int x, int y, int oldx, int oldy) {
                title.scrollTo(x, y);
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean getXMLContent(XmlPullParser parser, boolean supported) {
        XmlPullParser xmlPullParser = parser;
        try {
            this.profileModelList.clear();
            int eventType = parser.getEventType();
            while (eventType != 1) {
                switch (eventType) {
                    case 2:
                        if (!"VideoProfile".equals(parser.getName())) {
                            if ("VideoQuality".equals(parser.getName())) {
                                Iterator<VideoProfileModel> it = this.profileModelList.iterator();
                                while (it.hasNext()) {
                                    VideoProfileModel model = it.next();
                                    if (model.getName().equals(xmlPullParser.getAttributeValue((String) null, "format")) && model.getProfile().equals(xmlPullParser.getAttributeValue((String) null, "profile")) && model.getLevel().equals(xmlPullParser.getAttributeValue((String) null, "level"))) {
                                        model.setQuality(xmlPullParser.getAttributeValue((String) null, "name"));
                                    }
                                }
                                break;
                            }
                        } else {
                            this.profileModelList.add(new VideoProfileModel(xmlPullParser.getAttributeValue((String) null, "name"), xmlPullParser.getAttributeValue((String) null, "profile"), xmlPullParser.getAttributeValue((String) null, "level"), xmlPullParser.getAttributeValue((String) null, "width"), xmlPullParser.getAttributeValue((String) null, "height"), xmlPullParser.getAttributeValue((String) null, "framerate"), xmlPullParser.getAttributeValue((String) null, "Iinterval"), xmlPullParser.getAttributeValue((String) null, "minBitRate"), xmlPullParser.getAttributeValue((String) null, "maxBitRate"), supported));
                            break;
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                eventType = parser.next();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void onResume() {
        super.onResume();
        new XmlParserThread(0).start();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
