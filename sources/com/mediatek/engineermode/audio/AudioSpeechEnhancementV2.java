package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.audio.Audio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioSpeechEnhancementV2 extends Activity implements View.OnClickListener {
    private static final String CMD_GET_CATEGORY = "APP_GET_CATEGORY=Speech#%s";
    private static final String CMD_GET_CATEGORY_PREFIX = "APP_GET_CATEGORY=";
    private static final String CMD_GET_COMMON_PARAMETER = "APP_GET_PARAM=SpeechGeneral#CategoryLayer,Common#speech_common_para";
    private static final String CMD_GET_DEBUG_INFO = "APP_GET_PARAM=SpeechGeneral#CategoryLayer,Common#debug_info";
    private static final String CMD_GET_PARAMETER = "APP_GET_PARAM=Speech#%s#speech_mode_para";
    private static final String CMD_GET_PARA_PREFIX = "APP_GET_PARAM=";
    private static final String CMD_SET_COMMON_PARAMETER = "APP_SET_PARAM=SpeechGeneral#CategoryLayer,Common#speech_common_para#";
    private static final String CMD_SET_DEBUG_INFO = "APP_SET_PARAM=SpeechGeneral#CategoryLayer,Common#debug_info#";
    private static final String CMD_SET_PARAMETER = "APP_SET_PARAM=Speech#%s#speech_mode_para#";
    private static final String CMD_SPH_PARSER_SET = "SPH_PARSER_SET_PARAM,PHONE_CALL,TestMode,";
    private static final String CUST_XML_PARAM = "GET_CUST_XML_ENABLE";
    private static final String CUST_XML_SET_SUPPORT_PARAM = "SET_CUST_XML_ENABLE=1";
    private static final String CUST_XML_SET_UNSUPPORT_PARAM = "SET_CUST_XML_ENABLE=0";
    private static final int DIALOG_GET_DATA_ERROR = 1;
    private static final int DIALOG_GET_WBDATA_ERROR = 2;
    private static final int DIALOG_SET_SE_ERROR = 4;
    private static final int DIALOG_SET_SE_SUCCESS = 3;
    private static final int DIALOG_SET_WB_ERROR = 6;
    private static final int DIALOG_SET_WB_SUCCESS = 5;
    private static final int INDEX_COMMON = 0;
    private static final int INDEX_DEBUG = 1;
    private static final int INDEX_DEBUGMODE_DEFAULT = 0;
    private static final String OPTION_SCENE_SUPPORT = "VIR_SCENE_CUSTOMIZATION_SUPPORT";
    private static final String PARAM_DEVIDER = "#";
    private static final String RESULT_SUPPORT = "GET_CUST_XML_ENABLE=1";
    private static final String RESULT_UNSUPPORT = "GET_CUST_XML_ENABLE=0";
    public static final String TAG = "Audio/SpeechEnhancement2";
    private static final String TAG_SPEECH_MODE = "speech_mode_para";
    private static final String TYPE_MODE = "Band";
    private static final String TYPE_NETWORK = "Network";
    private static final String TYPE_PROFILE = "Profile";
    private static final String TYPE_SCENE = "Scene";
    private static final String TYPE_VOLINDEX = "VolIndex";
    private static final String VERSION_DEVIDER = "=";
    private MyAdapter mAdapter;
    private AudioManager mAudioManager;
    private Button mBtnSet;
    /* access modifiers changed from: private */
    public boolean mIsHandleListOk;
    private boolean mIsSupportScene;
    private ListView mList;
    private ArrayAdapter<String> mModeAdatper;
    private List<String> mModeEntries = new ArrayList();
    private List<String> mModeEntryValues = new ArrayList();
    /* access modifiers changed from: private */
    public int mModeIndex;
    private Spinner mModeSpinner;
    private Spinner mNetworkSpinner;
    private List<String> mNetworkValues = new ArrayList();
    private AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            String cmd = String.format(AudioSpeechEnhancementV2.CMD_GET_PARAMETER, new Object[]{AudioSpeechEnhancementV2.this.getTypeString()});
            Elog.d(AudioSpeechEnhancementV2.TAG, "cmd= " + cmd);
            AudioSpeechEnhancementV2 audioSpeechEnhancementV2 = AudioSpeechEnhancementV2.this;
            audioSpeechEnhancementV2.handleParameters(audioSpeechEnhancementV2.getParameters(cmd));
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            Elog.i(AudioSpeechEnhancementV2.TAG, "do noting...");
        }
    };
    private ArrayAdapter<String> mParaAdatper;
    private List<String> mParaEntries = new ArrayList();
    private List<String> mParaEntryValues = new ArrayList();
    private Spinner mParaSpinner;
    /* access modifiers changed from: private */
    public List<View> mParameterViews = new ArrayList();
    /* access modifiers changed from: private */
    public List<String> mParameters = new ArrayList();
    private String[] mParserEntries;
    private Map<String, String> mParserMap = new HashMap();
    private Spinner mSceneSpinner;
    private List<String> mSceneValues = new ArrayList();
    private ArrayAdapter<String> mVolumeAdatper;
    private List<String> mVolumeEntries = new ArrayList();
    private List<String> mVolumeEntryValues = new ArrayList();
    private Spinner mVolumeSpinner;

    private class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context activity) {
            super(activity, 0);
        }

        private class ViewHolder {
            public EditText editor;
            public TextView label;

            private ViewHolder() {
            }
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final int pos = position;
            LayoutInflater inflater = AudioSpeechEnhancementV2.this.getLayoutInflater();
            View view = (View) AudioSpeechEnhancementV2.this.mParameterViews.get(position);
            if (view == null) {
                view = inflater.inflate(R.layout.audio_speechenhancement_entry, (ViewGroup) null);
                holder = new ViewHolder();
                holder.label = (TextView) view.findViewById(R.id.label);
                holder.editor = (EditText) view.findViewById(R.id.editor);
                view.setTag(holder);
                AudioSpeechEnhancementV2.this.mParameterViews.set(position, view);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.editor.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                public void afterTextChanged(Editable s) {
                    if (pos < AudioSpeechEnhancementV2.this.mParameters.size() && holder.editor.getText() != null) {
                        AudioSpeechEnhancementV2.this.mParameters.set(pos, holder.editor.getText().toString());
                    }
                }
            });
            TextView textView = holder.label;
            textView.setText("Index " + pos);
            holder.editor.setText(String.valueOf(getItem(position)));
            return view;
        }
    }

    /* access modifiers changed from: private */
    public String getParameters(String command) {
        if (Audio.AudioTuningVer.VER_2_2 != Audio.getAudioTuningVer()) {
            String ret = this.mAudioManager.getParameters(command);
            Elog.i(TAG, "getParameters " + command + " return " + ret);
            int prefixLength = CMD_GET_PARA_PREFIX.length();
            if (command.contains(CMD_GET_CATEGORY_PREFIX)) {
                prefixLength = CMD_GET_CATEGORY_PREFIX.length();
            }
            if (ret != null && ret.length() > prefixLength) {
                ret = ret.substring(prefixLength);
            }
            Elog.i(TAG, "getParameters return " + ret);
            return ret;
        }
        String command2 = command.substring(command.indexOf(VERSION_DEVIDER) + 1);
        String[] params = command2.split(PARAM_DEVIDER);
        String result = null;
        if (params.length == 3) {
            result = AudioTuningJni.getParams(params[0], params[1], params[2]);
        } else if (params.length == 2) {
            result = AudioTuningJni.getCategory(params[0], params[1]);
        } else {
            Elog.i(TAG, "error parameter");
        }
        Elog.i(TAG, "getParameters " + command2 + " return " + result);
        return result;
    }

    private void setParameters(String command) {
        if (Audio.AudioTuningVer.VER_2_2 != Audio.getAudioTuningVer()) {
            this.mAudioManager.setParameters(command);
        } else {
            command = command.substring(command.indexOf(VERSION_DEVIDER) + 1);
            String[] params = command.split(PARAM_DEVIDER);
            if (params.length == 4) {
                AudioTuningJni.setParams(params[0], params[1], params[2], params[3]);
                AudioTuningJni.saveToWork(params[0]);
            }
        }
        Elog.i(TAG, "setParameters " + command);
    }

    /* access modifiers changed from: private */
    public void handleParameters(String data) {
        String entry;
        if (data != null) {
            String[] entries = data.split(",");
            this.mParameters.clear();
            this.mParameterViews.clear();
            for (String entry2 : entries) {
                if (entry2 == null || entry2.length() <= 2) {
                    entry = "ERROR";
                } else {
                    try {
                        entry = Long.valueOf(entry2.substring(2), 16).toString();
                    } catch (NumberFormatException e) {
                        entry = "ERROR";
                    }
                }
                this.mParameters.add(entry);
                this.mParameterViews.add((Object) null);
            }
            this.mAdapter.clear();
            this.mAdapter.addAll(this.mParameters);
        }
    }

    private boolean handleModeList(String data) {
        if (data == null) {
            return false;
        }
        String[] values = data.split(",");
        if (values.length <= 0 || values.length % 2 != 0) {
            return false;
        }
        this.mModeEntries.clear();
        this.mModeEntryValues.clear();
        this.mModeEntries.add("Common Parameter");
        this.mModeEntries.add("Debug Info");
        this.mModeEntryValues.add("");
        this.mModeEntryValues.add("");
        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) {
                this.mModeEntryValues.add(values[i]);
            } else {
                this.mModeEntries.add(values[i]);
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.mModeEntries);
        this.mModeAdatper = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mModeSpinner.setAdapter(this.mModeAdatper);
        return true;
    }

    private boolean handleParameterList(String data) {
        if (data == null) {
            return false;
        }
        String[] values = data.split(",");
        if (values.length <= 0 || values.length % 2 != 0) {
            return false;
        }
        this.mParaEntries.clear();
        this.mParaEntryValues.clear();
        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) {
                this.mParaEntryValues.add(values[i]);
            } else {
                this.mParaEntries.add(values[i]);
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.mParaEntries);
        this.mParaAdatper = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mParaSpinner.setAdapter(this.mParaAdatper);
        return true;
    }

    private boolean handleVolindexList(String data) {
        if (data == null) {
            return false;
        }
        String[] values = data.split(",");
        if (values.length <= 0 || values.length % 2 != 0) {
            return false;
        }
        this.mVolumeEntries.clear();
        this.mVolumeEntryValues.clear();
        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) {
                this.mVolumeEntryValues.add(values[i]);
            } else {
                this.mVolumeEntries.add(values[i]);
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, 17367048, this.mVolumeEntries);
        this.mVolumeAdatper = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        this.mVolumeSpinner.setAdapter(this.mVolumeAdatper);
        return true;
    }

    private boolean handleList(String data, List<String> entryValues, Spinner spinner) {
        if (data == null) {
            return false;
        }
        String[] values = data.split(",");
        if (values.length <= 0 || values.length % 2 != 0) {
            return false;
        }
        List<String> entries = new ArrayList<>();
        entryValues.clear();
        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) {
                entryValues.add(values[i]);
            } else {
                entries.add(values[i]);
            }
        }
        ArrayAdapter adatper = new ArrayAdapter(this, 17367048, entries);
        adatper.setDropDownViewResource(17367049);
        spinner.setAdapter(adatper);
        return true;
    }

    private void initComponents() {
        this.mAudioManager = (AudioManager) getSystemService("audio");
        this.mSceneSpinner = (Spinner) findViewById(R.id.Audio_SpEnhancement_Scene);
        this.mModeSpinner = (Spinner) findViewById(R.id.Audio_SpEnhancement_ModeType);
        this.mParaSpinner = (Spinner) findViewById(R.id.Audio_SpEnhancement_ParaType);
        this.mVolumeSpinner = (Spinner) findViewById(R.id.Audio_SpEnhancement_Vol_spin);
        this.mNetworkSpinner = (Spinner) findViewById(R.id.Audio_SpEnhancement_Network);
        this.mList = (ListView) findViewById(R.id.Audio_SpEnhancement_ListView);
        MyAdapter myAdapter = new MyAdapter(this);
        this.mAdapter = myAdapter;
        this.mList.setAdapter(myAdapter);
        Button button = (Button) findViewById(R.id.Audio_SpEnhancement_Button);
        this.mBtnSet = button;
        button.setOnClickListener(this);
        boolean handleModeList = handleModeList(getParameters(String.format(CMD_GET_CATEGORY, new Object[]{TYPE_MODE})));
        this.mIsHandleListOk = handleModeList;
        boolean handleVolindexList = handleModeList & handleVolindexList(getParameters(String.format(CMD_GET_CATEGORY, new Object[]{TYPE_VOLINDEX})));
        this.mIsHandleListOk = handleVolindexList;
        this.mIsHandleListOk = handleVolindexList & handleParameterList(getParameters(String.format(CMD_GET_CATEGORY, new Object[]{TYPE_PROFILE})));
        if (this.mIsSupportScene) {
            this.mIsHandleListOk &= handleList(getParameters(String.format(CMD_GET_CATEGORY, new Object[]{TYPE_SCENE})), this.mSceneValues, this.mSceneSpinner);
        }
        boolean handleList = this.mIsHandleListOk & handleList(getParameters(String.format(CMD_GET_CATEGORY, new Object[]{TYPE_NETWORK})), this.mNetworkValues, this.mNetworkSpinner);
        this.mIsHandleListOk = handleList;
        if (!handleList) {
            Toast.makeText(this, "Audio XML File Wrong Format", 0).show();
        } else {
            this.mSceneSpinner.setOnItemSelectedListener(this.mOnItemSelectedListener);
            this.mParaSpinner.setOnItemSelectedListener(this.mOnItemSelectedListener);
            this.mVolumeSpinner.setOnItemSelectedListener(this.mOnItemSelectedListener);
            this.mNetworkSpinner.setOnItemSelectedListener(this.mOnItemSelectedListener);
        }
        this.mModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                int unused = AudioSpeechEnhancementV2.this.mModeIndex = arg2;
                switch (AudioSpeechEnhancementV2.this.mModeIndex) {
                    case 0:
                        AudioSpeechEnhancementV2 audioSpeechEnhancementV2 = AudioSpeechEnhancementV2.this;
                        audioSpeechEnhancementV2.handleParameters(audioSpeechEnhancementV2.getParameters(AudioSpeechEnhancementV2.CMD_GET_COMMON_PARAMETER));
                        AudioSpeechEnhancementV2.this.setTpyeSpnVisibility(8);
                        return;
                    case 1:
                        AudioSpeechEnhancementV2 audioSpeechEnhancementV22 = AudioSpeechEnhancementV2.this;
                        audioSpeechEnhancementV22.handleParameters(audioSpeechEnhancementV22.getParameters(AudioSpeechEnhancementV2.CMD_GET_DEBUG_INFO));
                        AudioSpeechEnhancementV2.this.setTpyeSpnVisibility(8);
                        return;
                    default:
                        if (!AudioSpeechEnhancementV2.this.mIsHandleListOk) {
                            Toast.makeText(AudioSpeechEnhancementV2.this, "Audio XML File Wrong Format", 0).show();
                            return;
                        }
                        AudioSpeechEnhancementV2.this.setTpyeSpnVisibility(0);
                        String cmd = String.format(AudioSpeechEnhancementV2.CMD_GET_PARAMETER, new Object[]{AudioSpeechEnhancementV2.this.getTypeString()});
                        Elog.d(AudioSpeechEnhancementV2.TAG, "cmd= " + cmd);
                        AudioSpeechEnhancementV2 audioSpeechEnhancementV23 = AudioSpeechEnhancementV2.this;
                        audioSpeechEnhancementV23.handleParameters(audioSpeechEnhancementV23.getParameters(cmd));
                        return;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                Elog.i(AudioSpeechEnhancementV2.TAG, "do noting...");
            }
        });
    }

    /* access modifiers changed from: private */
    public void setTpyeSpnVisibility(int isVisible) {
        if (this.mIsSupportScene) {
            this.mSceneSpinner.setVisibility(isVisible);
        }
        this.mParaSpinner.setVisibility(isVisible);
        this.mVolumeSpinner.setVisibility(isVisible);
        this.mNetworkSpinner.setVisibility(isVisible);
    }

    /* access modifiers changed from: private */
    public String getTypeString() {
        StringBuilder builder = new StringBuilder();
        if (this.mIsSupportScene) {
            int index = this.mSceneSpinner.getSelectedItemPosition();
            builder.append(TYPE_SCENE);
            builder.append(",");
            builder.append(this.mSceneValues.get(index));
            builder.append(",");
        }
        builder.append(TYPE_MODE);
        builder.append(",");
        builder.append(this.mModeEntryValues.get(this.mModeIndex));
        builder.append(",");
        int index2 = this.mParaSpinner.getSelectedItemPosition();
        builder.append(TYPE_PROFILE);
        builder.append(",");
        builder.append(this.mParaEntryValues.get(index2));
        builder.append(",");
        int index3 = this.mVolumeSpinner.getSelectedItemPosition();
        builder.append(TYPE_VOLINDEX);
        builder.append(",");
        builder.append(this.mVolumeEntryValues.get(index3));
        builder.append(",");
        int index4 = this.mNetworkSpinner.getSelectedItemPosition();
        builder.append(TYPE_NETWORK);
        builder.append(",");
        builder.append(this.mNetworkValues.get(index4));
        return builder.toString();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_speechenhancement_new);
        this.mIsSupportScene = AudioTuningJni.isFeatureOptionEnabled(OPTION_SCENE_SUPPORT);
        initComponents();
        this.mParserEntries = getResources().getStringArray(R.array.speech_enhance_parser);
        int i = 0;
        while (true) {
            String[] strArr = this.mParserEntries;
            if (i < strArr.length) {
                this.mParserMap.put(strArr[i], String.valueOf(i));
                i++;
            } else {
                return;
            }
        }
    }

    private void setDebugInfoIndex10Param() {
        Elog.v(TAG, "setDebugInfoIndex10Param");
        try {
            int index = Integer.parseInt(this.mParameters.get(10));
            if (index >= 0) {
                String[] strArr = this.mParserEntries;
                if (index < strArr.length) {
                    String cmd = strArr[index];
                    AudioManager audioManager = this.mAudioManager;
                    audioManager.setParameters(CMD_SPH_PARSER_SET + cmd);
                    Elog.i(TAG, "setDebugInfoIndex10Param " + cmd);
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Wrong format", 0).show();
        }
    }

    public void onClick(View arg0) {
        String cmd;
        if (arg0.equals(this.mBtnSet)) {
            if (!FeatureSupport.isEngLoad()) {
                String check = this.mAudioManager.getParameters(CUST_XML_PARAM);
                if (check == null || !RESULT_SUPPORT.equals(check)) {
                    Elog.d(TAG, "set CUST_XML_PARAM = 1");
                    this.mAudioManager.setParameters(CUST_XML_SET_SUPPORT_PARAM);
                    AudioTuningJni.CustXmlEnableChanged(1);
                } else {
                    Elog.d(TAG, "get CUST_XML_PARAM = 1");
                }
            }
            switch (this.mModeIndex) {
                case 0:
                    cmd = CMD_SET_COMMON_PARAMETER;
                    break;
                case 1:
                    setDebugInfoIndex10Param();
                    cmd = CMD_SET_DEBUG_INFO;
                    break;
                default:
                    if (this.mModeEntryValues.size() > 0 && this.mParaEntryValues.size() > 0) {
                        cmd = String.format(CMD_SET_PARAMETER, new Object[]{getTypeString()});
                        break;
                    } else {
                        return;
                    }
                    break;
            }
            for (String p : this.mParameters) {
                try {
                    cmd = cmd + "0x" + Long.toString(Long.parseLong(p), 16) + ",";
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Wrong format", 0).show();
                    return;
                }
            }
            setParameters(cmd.substring(0, cmd.length() - 1));
            Toast.makeText(this, "Set parameter done", 0).show();
        }
    }

    public Dialog onCreateDialog(int dialogId) {
        switch (dialogId) {
            case 1:
                return new AlertDialog.Builder(this).setTitle(R.string.get_data_error_title).setMessage(R.string.get_data_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioSpeechEnhancementV2.this.finish();
                    }
                }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle(R.string.get_wbdata_error_title).setMessage(R.string.get_wbdata_error_msg).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioSpeechEnhancementV2.this.finish();
                    }
                }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
            case 3:
                return new AlertDialog.Builder(this).setTitle(R.string.set_success_title).setMessage(R.string.set_speech_enhance_success).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 4:
                return new AlertDialog.Builder(this).setTitle(R.string.set_error_title).setMessage(R.string.set_speech_enhance_failed).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 5:
                return new AlertDialog.Builder(this).setTitle(R.string.set_success_title).setMessage(R.string.set_wb_success).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 6:
                return new AlertDialog.Builder(this).setTitle(R.string.set_error_title).setMessage(R.string.set_wb_failed).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            default:
                return null;
        }
    }
}
