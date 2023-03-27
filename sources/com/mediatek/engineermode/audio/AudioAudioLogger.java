package com.mediatek.engineermode.audio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioSystem;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class AudioAudioLogger extends Activity {
    private static final int DIALOG_ID_NO_SDCARD = 1;
    private static final int DIALOG_ID_SDCARD_BUSY = 2;
    private static final int DIALOG_ID_SHOW_CHECKBOX_RET = 5;
    private static final int DIALOG_ID_SHOW_GET_RET = 3;
    private static final int DIALOG_ID_SHOW_SET_RET = 4;
    private static final String GET_AUDIO_COMMAND = "GetAudioCommand";
    private static final String GET_PARAMETERS = "GetParameters";
    private static final String SET_AUDIO_COMMAND = "SetAudioCommand";
    private static final String SET_PARAMETERS = "SetParameters";
    private static final String TAG = "Audio/AudioLogger";
    private static final String mFileName = "/vendor/etc/audio_em.xml";
    private final View.OnClickListener mAudioButtonClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            if (AudioAudioLogger.this.checkSDCardIsAvaliable().booleanValue()) {
                switch (arg0.getId()) {
                    case R.id.Audio_GetAudioCommand:
                        String str = AudioAudioLogger.this.mEditGetAudioCommandText.getText().toString();
                        Elog.d(AudioAudioLogger.TAG, "Audio_GetAudioCommand=" + str);
                        try {
                            String unused = AudioAudioLogger.this.mAudioGetRet = String.valueOf(AudioTuningJni.getAudioCommand(Integer.decode(str).intValue()));
                            Elog.d(AudioAudioLogger.TAG, "Audio_GetAudioCommand=" + str + ",ret= " + AudioAudioLogger.this.mAudioGetRet);
                            String unused2 = AudioAudioLogger.this.mAudioTitle = str;
                            AudioAudioLogger audioAudioLogger = AudioAudioLogger.this;
                            Toast.makeText(audioAudioLogger, audioAudioLogger.mAudioGetRet, 0).show();
                            AudioAudioLogger.this.showDialog(3);
                            return;
                        } catch (NumberFormatException e) {
                            AudioAudioLogger audioAudioLogger2 = AudioAudioLogger.this;
                            Toast.makeText(audioAudioLogger2, audioAudioLogger2.getString(R.string.number_arrage_tip, new Object[]{Integer.MAX_VALUE}), 0).show();
                            return;
                        }
                    case R.id.Audio_GetAudioParameter:
                        String str2 = AudioAudioLogger.this.mEditGetAudioParameterText.getText().toString();
                        String unused3 = AudioAudioLogger.this.mAudioGetRet = AudioSystem.getParameters(str2);
                        Elog.d(AudioAudioLogger.TAG, "Audio_GetAudioParameter=" + str2 + ",ret=" + AudioAudioLogger.this.mAudioGetRet);
                        String unused4 = AudioAudioLogger.this.mAudioTitle = str2;
                        AudioAudioLogger audioAudioLogger3 = AudioAudioLogger.this;
                        Toast.makeText(audioAudioLogger3, audioAudioLogger3.mAudioGetRet, 0).show();
                        AudioAudioLogger.this.showDialog(3);
                        return;
                    case R.id.Audio_SetAudioCommand:
                        String str3 = AudioAudioLogger.this.mEditSetAudioCommandText.getText().toString();
                        String[] cmdStr = str3.replaceAll("\\s*", "").split(",");
                        try {
                            int ret = AudioTuningJni.setAudioCommand(Integer.decode(cmdStr[0]).intValue(), Integer.decode(cmdStr[1]).intValue());
                            Elog.d(AudioAudioLogger.TAG, "Audio_SetAudioCommand=" + str3 + ",ret= " + ret);
                            int unused5 = AudioAudioLogger.this.mAudioSetRet = ret;
                            String unused6 = AudioAudioLogger.this.mAudioTitle = str3;
                            AudioAudioLogger audioAudioLogger4 = AudioAudioLogger.this;
                            Toast.makeText(audioAudioLogger4, String.valueOf(audioAudioLogger4.mAudioSetRet), 0).show();
                            AudioAudioLogger.this.showDialog(4);
                            return;
                        } catch (NumberFormatException e2) {
                            AudioAudioLogger audioAudioLogger5 = AudioAudioLogger.this;
                            Toast.makeText(audioAudioLogger5, audioAudioLogger5.getString(R.string.number_arrage_tip, new Object[]{Integer.MAX_VALUE}), 0).show();
                            return;
                        } catch (ArrayIndexOutOfBoundsException e3) {
                            AudioAudioLogger audioAudioLogger6 = AudioAudioLogger.this;
                            Toast.makeText(audioAudioLogger6, audioAudioLogger6.getString(R.string.set_failed_tip), 0).show();
                            return;
                        }
                    case R.id.Audio_SetAudioParameter:
                        String str4 = AudioAudioLogger.this.mEditSetAudioParameterText.getText().toString();
                        int ret2 = AudioSystem.setParameters(str4);
                        Elog.d(AudioAudioLogger.TAG, "Audio_SetAudioParameter=" + str4 + ",ret= " + ret2);
                        int unused7 = AudioAudioLogger.this.mAudioSetRet = ret2;
                        String unused8 = AudioAudioLogger.this.mAudioTitle = str4;
                        AudioAudioLogger audioAudioLogger7 = AudioAudioLogger.this;
                        Toast.makeText(audioAudioLogger7, String.valueOf(audioAudioLogger7.mAudioSetRet), 0).show();
                        AudioAudioLogger.this.showDialog(4);
                        return;
                    default:
                        return;
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<String> mAudioCheckBoxRet = null;
    /* access modifiers changed from: private */
    public String mAudioGetRet = null;
    /* access modifiers changed from: private */
    public AudioLoggerXMLData mAudioLoggerXMLData = null;
    /* access modifiers changed from: private */
    public int mAudioSetRet = 0;
    /* access modifiers changed from: private */
    public String mAudioTitle = null;
    private Button mBtGetAudioCommand = null;
    private Button mBtGetAudioParameter = null;
    private Button mBtSetAudioCommand = null;
    private Button mBtSetAudioParameter = null;
    /* access modifiers changed from: private */
    public boolean mCategoryTitleFlag = false;
    private LinearLayout mCheckBoxLinearLayout = null;
    private final CompoundButton.OnCheckedChangeListener mCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int ret;
            String str;
            int mCheckBoxID = buttonView.getId();
            String mCheckBoxName = (String) buttonView.getText();
            int cmdID = mCheckBoxID >> 8;
            int cmdParameter = mCheckBoxID & 255;
            if (AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCategoryTitle.equals(mCheckBoxName)) {
                boolean unused = AudioAudioLogger.this.mCategoryTitleFlag = true;
                for (int i = 0; i < AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCmdName.size(); i++) {
                    ((CheckBox) AudioAudioLogger.this.findViewById(mCheckBoxID + i + 1)).setChecked(buttonView.isChecked());
                }
                if (AudioAudioLogger.this.mAudioCheckBoxRet != null && !AudioAudioLogger.this.mAudioCheckBoxRet.isEmpty()) {
                    Elog.d(AudioAudioLogger.TAG, "CategoryTitle is checked, show dialog");
                    String unused2 = AudioAudioLogger.this.mAudioTitle = mCheckBoxName;
                    if (isChecked && ("Output".equals(AudioAudioLogger.this.mAudioTitle) || "Input".equals(AudioAudioLogger.this.mAudioTitle) || "USB".equals(AudioAudioLogger.this.mAudioTitle))) {
                        Toast.makeText(AudioAudioLogger.this, R.string.audio_dump_path_change_msg, 1).show();
                    }
                    AudioAudioLogger.this.showDialog(5);
                }
                boolean unused3 = AudioAudioLogger.this.mCategoryTitleFlag = false;
                ret = true;
                int i2 = mCheckBoxID;
            } else if (AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCmdName.get(cmdParameter - 1).equals(mCheckBoxName)) {
                if (AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mType.get(cmdParameter - 1).equals(AudioAudioLogger.SET_AUDIO_COMMAND)) {
                    int mCmd = Integer.decode(AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCmd.get(cmdParameter - 1)).intValue();
                    int mCheck = Integer.decode(AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCheck.get(cmdParameter - 1)).intValue();
                    int mUncheck = Integer.decode(AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mUncheck.get(cmdParameter - 1)).intValue();
                    StringBuilder sb = new StringBuilder();
                    sb.append(Integer.toHexString(mCmd));
                    sb.append(", ");
                    if (isChecked) {
                        str = String.valueOf(mCheck);
                    } else {
                        str = String.valueOf(mUncheck);
                    }
                    sb.append(str);
                    String cmdSend = sb.toString();
                    Elog.d(AudioAudioLogger.TAG, "cmdSend: " + cmdSend);
                    int ret2 = AudioTuningJni.setAudioCommand(mCmd, isChecked ? mCheck : mUncheck);
                    ArrayList access$1000 = AudioAudioLogger.this.mAudioCheckBoxRet;
                    StringBuilder sb2 = new StringBuilder();
                    int i3 = mCheckBoxID;
                    sb2.append("Set: 0x");
                    sb2.append(cmdSend);
                    sb2.append(" Ret: ");
                    sb2.append(String.valueOf(ret2));
                    access$1000.add(sb2.toString());
                } else {
                    int i4 = mCheckBoxID;
                    if (AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mType.get(cmdParameter - 1).equals(AudioAudioLogger.SET_PARAMETERS)) {
                        String mCmd2 = AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCmd.get(cmdParameter - 1);
                        String mCheck2 = AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCheck.get(cmdParameter - 1);
                        String mUncheck2 = AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mUncheck.get(cmdParameter - 1);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(mCmd2);
                        sb3.append("=");
                        sb3.append(isChecked ? mCheck2 : mUncheck2);
                        String cmdSend2 = sb3.toString();
                        Elog.d(AudioAudioLogger.TAG, "cmdSend: " + cmdSend2);
                        int ret3 = AudioSystem.setParameters(cmdSend2);
                        ArrayList access$10002 = AudioAudioLogger.this.mAudioCheckBoxRet;
                        StringBuilder sb4 = new StringBuilder();
                        String str2 = mCmd2;
                        sb4.append("Set: ");
                        sb4.append(cmdSend2);
                        sb4.append("   Ret: ");
                        sb4.append(String.valueOf(ret3));
                        access$10002.add(sb4.toString());
                        int i5 = ret3;
                    } else if (AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mType.get(cmdParameter - 1).equals(AudioAudioLogger.GET_AUDIO_COMMAND)) {
                        String str3 = AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCmd.get(cmdParameter - 1);
                        int mCmd3 = Integer.decode(str3).intValue();
                        String cmdSend3 = str3;
                        Elog.d(AudioAudioLogger.TAG, "cmdSend: " + cmdSend3);
                        int ret4 = AudioTuningJni.getAudioCommand(mCmd3);
                        AudioAudioLogger.this.mAudioCheckBoxRet.add("Get: " + cmdSend3 + "  Ret: " + String.valueOf(ret4));
                        int i6 = ret4;
                    } else if (AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mType.get(cmdParameter - 1).equals(AudioAudioLogger.GET_PARAMETERS)) {
                        String str4 = AudioAudioLogger.this.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCmd.get(cmdParameter - 1);
                        String cmdSend4 = str4;
                        String retString = AudioSystem.getParameters(str4);
                        Elog.d(AudioAudioLogger.TAG, "cmdSend: " + cmdSend4);
                        AudioAudioLogger.this.mAudioCheckBoxRet.add("Get: " + cmdSend4 + "   Ret: " + retString);
                    }
                }
                if (!AudioAudioLogger.this.mCategoryTitleFlag) {
                    Elog.d(AudioAudioLogger.TAG, "mCmdName is checked, show dialog");
                    AudioAudioLogger audioAudioLogger = AudioAudioLogger.this;
                    String unused4 = audioAudioLogger.mAudioTitle = audioAudioLogger.mAudioLoggerXMLData.mAudioDumpOperation.get(cmdID).mCategoryTitle;
                    if (isChecked && ("Output".equals(AudioAudioLogger.this.mAudioTitle) || "Input".equals(AudioAudioLogger.this.mAudioTitle) || "USB".equals(AudioAudioLogger.this.mAudioTitle))) {
                        Toast.makeText(AudioAudioLogger.this, R.string.audio_dump_path_change_msg, 1).show();
                    }
                    AudioAudioLogger.this.showDialog(5);
                    return;
                }
                return;
            } else {
                ret = true;
                int i7 = mCheckBoxID;
            }
        }
    };
    private ArrayList<CheckBox> mDumpOptionsCheck;
    /* access modifiers changed from: private */
    public EditText mEditGetAudioCommandText = null;
    /* access modifiers changed from: private */
    public EditText mEditGetAudioParameterText = null;
    /* access modifiers changed from: private */
    public EditText mEditSetAudioCommandText = null;
    /* access modifiers changed from: private */
    public EditText mEditSetAudioParameterText = null;
    private InputStream mInputStream = null;
    private Spinner mSpGetAudioCommand = null;
    private Spinner mSpGetAudioParameter = null;
    private Spinner mSpSetAudioCommand = null;
    private Spinner mSpSetAudioParameter = null;
    private final AdapterView.OnItemSelectedListener mSpinnerListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Elog.d(AudioAudioLogger.TAG, "id:" + parent.getId());
            switch (parent.getId()) {
                case R.id.Audio_GetAudioCommandSpinner:
                    AudioAudioLogger.this.mEditGetAudioCommandText.setText(parent.getItemAtPosition(position).toString());
                    return;
                case R.id.Audio_GetAudioParameterSpinner:
                    AudioAudioLogger.this.mEditGetAudioParameterText.setText(parent.getItemAtPosition(position).toString());
                    return;
                case R.id.Audio_SetAudioCommandSpinner:
                    AudioAudioLogger.this.mEditSetAudioCommandText.setText(parent.getItemAtPosition(position).toString());
                    return;
                case R.id.Audio_SetAudioParameterSpinner:
                    AudioAudioLogger.this.mEditSetAudioParameterText.setText(parent.getItemAtPosition(position).toString());
                    return;
                default:
                    return;
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private File mXmlFile = null;

    private boolean initmSpSetAudioCommand(Spinner audioSpn, AudioLoggerXMLData mAudioLoggerXMLData2) {
        List<String> audioList = mAudioLoggerXMLData2.mAudioCommandSetOperation;
        int mAudioCount = audioList.size();
        if (mAudioCount > 0) {
            ArrayAdapter<String> audioAdatper = new ArrayAdapter<>(this, 17367048, audioList);
            audioAdatper.setDropDownViewResource(17367049);
            audioSpn.setAdapter(audioAdatper);
            return true;
        }
        Elog.d(TAG, "init audio spinner fail; mmAudioCount:" + mAudioCount);
        return false;
    }

    private boolean initmSpGetAudioCommand(Spinner audioSpn, AudioLoggerXMLData mAudioLoggerXMLData2) {
        List<String> audioList = mAudioLoggerXMLData2.mAudioCommandGetOperation;
        int mAudioCount = audioList.size();
        if (mAudioCount > 0) {
            ArrayAdapter<String> audioAdatper = new ArrayAdapter<>(this, 17367048, audioList);
            audioAdatper.setDropDownViewResource(17367049);
            audioSpn.setAdapter(audioAdatper);
            return true;
        }
        Elog.d(TAG, "init audio spinner fail; mmAudioCount:" + mAudioCount);
        return false;
    }

    private boolean initmSpSetAudioParameter(Spinner audioSpn, AudioLoggerXMLData mAudioLoggerXMLData2) {
        List<String> audioList = mAudioLoggerXMLData2.mParametersSetOperationItems;
        int mAudioCount = audioList.size();
        if (mAudioCount > 0) {
            ArrayAdapter<String> audioAdatper = new ArrayAdapter<>(this, 17367048, audioList);
            audioAdatper.setDropDownViewResource(17367049);
            audioSpn.setAdapter(audioAdatper);
            return true;
        }
        Elog.d(TAG, "init audio spinner fail; mAudioCount:" + mAudioCount);
        return false;
    }

    private boolean initmSpGetAudioParameter(Spinner audioSpn, AudioLoggerXMLData mAudioLoggerXMLData2) {
        List<String> audioList = mAudioLoggerXMLData2.mParametersGetOperationItems;
        int mAudioCount = audioList.size();
        if (mAudioCount > 0) {
            ArrayAdapter<String> audioAdatper = new ArrayAdapter<>(this, 17367048, audioList);
            audioAdatper.setDropDownViewResource(17367049);
            audioSpn.setAdapter(audioAdatper);
            return true;
        }
        Elog.d(TAG, "init audio spinner fail; mAudioCount:" + mAudioCount);
        return false;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_audiologger);
        this.mAudioLoggerXMLData = new AudioLoggerXMLData();
        this.mAudioCheckBoxRet = new ArrayList<>();
        File file = new File(mFileName);
        this.mXmlFile = file;
        if (!file.exists()) {
            Elog.d(TAG, "mFileName:/vendor/etc/audio_em.xmlis not exists");
            Toast.makeText(this, "/vendor/etc/audio_em.xmlis not exists", 1).show();
            finish();
            return;
        }
        try {
            this.mInputStream = new FileInputStream(this.mXmlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            parseXMLWithSAX(this.mInputStream, this.mAudioLoggerXMLData);
        } catch (SAXException e2) {
            e2.printStackTrace();
        } catch (ParserConfigurationException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        Elog.d(TAG, "parseXMLWithSAX pass!");
        this.mSpSetAudioCommand = (Spinner) findViewById(R.id.Audio_SetAudioCommandSpinner);
        this.mSpGetAudioCommand = (Spinner) findViewById(R.id.Audio_GetAudioCommandSpinner);
        this.mSpSetAudioParameter = (Spinner) findViewById(R.id.Audio_SetAudioParameterSpinner);
        this.mSpGetAudioParameter = (Spinner) findViewById(R.id.Audio_GetAudioParameterSpinner);
        this.mEditSetAudioCommandText = (EditText) findViewById(R.id.SetAudioCommandText);
        this.mEditGetAudioCommandText = (EditText) findViewById(R.id.GetAudioCommandText);
        this.mEditSetAudioParameterText = (EditText) findViewById(R.id.SetAudioParameterText);
        this.mEditGetAudioParameterText = (EditText) findViewById(R.id.GetAudioParameterText);
        this.mBtGetAudioCommand = (Button) findViewById(R.id.Audio_GetAudioCommand);
        this.mBtSetAudioCommand = (Button) findViewById(R.id.Audio_SetAudioCommand);
        this.mBtSetAudioParameter = (Button) findViewById(R.id.Audio_SetAudioParameter);
        this.mBtGetAudioParameter = (Button) findViewById(R.id.Audio_GetAudioParameter);
        this.mCheckBoxLinearLayout = (LinearLayout) findViewById(R.id.LinearLayoutCheck);
        this.mDumpOptionsCheck = new ArrayList<>();
        for (int i = 0; i < this.mAudioLoggerXMLData.mAudioDumpOperation.size(); i++) {
            String title = this.mAudioLoggerXMLData.mAudioDumpOperation.get(i).mCategoryTitle;
            Elog.d(TAG, "title:" + i + " : " + title);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(title);
            checkBox.setId(i << 8);
            this.mDumpOptionsCheck.add(checkBox);
            this.mCheckBoxLinearLayout.addView(checkBox);
            boolean isAllChecked = true;
            for (int j = 0; j < this.mAudioLoggerXMLData.mAudioDumpOperation.get(i).mCmdName.size(); j++) {
                String mCmd = this.mAudioLoggerXMLData.mAudioDumpOperation.get(i).mCmd.get(j);
                String mCmdName = this.mAudioLoggerXMLData.mAudioDumpOperation.get(i).mCmdName.get(j);
                String mType = this.mAudioLoggerXMLData.mAudioDumpOperation.get(i).mType.get(j);
                CheckBox tempCheckBox = new CheckBox(this);
                tempCheckBox.setText(mCmdName);
                tempCheckBox.setX(30.0f);
                tempCheckBox.setId((i << 8) + j + 1);
                if (mType.equals(SET_AUDIO_COMMAND)) {
                    String valueOf = String.valueOf(AudioTuningJni.getAudioCommand(Integer.decode(mCmd).intValue() + 1));
                    this.mAudioGetRet = valueOf;
                    if (!valueOf.isEmpty()) {
                        Elog.d(TAG, "Audio_GetAudioCommand:" + mCmd + ",ret= " + this.mAudioGetRet);
                        tempCheckBox.setChecked(this.mAudioGetRet.equals("1"));
                    }
                } else if (mType.equals(SET_PARAMETERS)) {
                    String parameters = AudioSystem.getParameters(mCmd);
                    this.mAudioGetRet = parameters;
                    if (!parameters.isEmpty()) {
                        String[] strs = this.mAudioGetRet.split("=");
                        Elog.d(TAG, "Audio_GetAudioParameter:" + mCmd + ",ret=" + this.mAudioGetRet);
                        tempCheckBox.setChecked(strs[1].equals("1"));
                    }
                }
                this.mDumpOptionsCheck.add(tempCheckBox);
                this.mCheckBoxLinearLayout.addView(tempCheckBox);
                Elog.d(TAG, "mAudioDumpOperation,mCmd:" + mCmd);
                Elog.d(TAG, "mAudioDumpOperation,mCmd name:" + mCmdName);
                isAllChecked &= tempCheckBox.isChecked();
            }
            checkBox.setChecked(isAllChecked);
        }
        for (int i2 = 0; i2 < this.mDumpOptionsCheck.size(); i2++) {
            this.mDumpOptionsCheck.get(i2).setOnCheckedChangeListener(this.mCheckedListener);
        }
        this.mSpSetAudioCommand.setOnItemSelectedListener(this.mSpinnerListener);
        this.mSpGetAudioCommand.setOnItemSelectedListener(this.mSpinnerListener);
        this.mSpSetAudioParameter.setOnItemSelectedListener(this.mSpinnerListener);
        this.mSpGetAudioParameter.setOnItemSelectedListener(this.mSpinnerListener);
        this.mBtSetAudioCommand.setOnClickListener(this.mAudioButtonClickListener);
        this.mBtGetAudioCommand.setOnClickListener(this.mAudioButtonClickListener);
        this.mBtSetAudioParameter.setOnClickListener(this.mAudioButtonClickListener);
        this.mBtGetAudioParameter.setOnClickListener(this.mAudioButtonClickListener);
        if (!initmSpSetAudioCommand(this.mSpSetAudioCommand, this.mAudioLoggerXMLData)) {
            Toast.makeText(this, "mSpSetAudioCommand spinner fail", 0).show();
            finish();
        } else if (!initmSpGetAudioCommand(this.mSpGetAudioCommand, this.mAudioLoggerXMLData)) {
            Toast.makeText(this, "mSpSetAudioCommand spinner fail", 0).show();
            finish();
        } else if (!initmSpSetAudioParameter(this.mSpSetAudioParameter, this.mAudioLoggerXMLData)) {
            Toast.makeText(this, "mSpSetAudioCommand spinner fail", 0).show();
            finish();
        } else if (!initmSpGetAudioParameter(this.mSpGetAudioParameter, this.mAudioLoggerXMLData)) {
            Toast.makeText(this, "mSpSetAudioCommand spinner fail", 0).show();
            finish();
        }
    }

    /* access modifiers changed from: private */
    public Boolean checkSDCardIsAvaliable() {
        String state = Environment.getExternalStorageState();
        Elog.i(Audio.TAG, "Environment.getExternalStorageState() is : " + state);
        if (state.equals("removed")) {
            showDialog(1);
            return false;
        } else if (!state.equals("shared")) {
            return true;
        } else {
            showDialog(2);
            return false;
        }
    }

    public Dialog onCreateDialog(int dialogId) {
        switch (dialogId) {
            case 1:
                return new AlertDialog.Builder(this).setTitle(R.string.no_sdcard_title).setMessage(R.string.no_sdcard_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 2:
                return new AlertDialog.Builder(this).setTitle(R.string.sdcard_busy_title).setMessage(R.string.sdcard_busy_msg).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
            case 3:
                AlertDialog.Builder title = new AlertDialog.Builder(this).setTitle(this.mAudioTitle);
                return title.setMessage("ret : " + this.mAudioGetRet).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AudioAudioLogger.this.removeDialog(3);
                    }
                }).create();
            case 4:
                AlertDialog.Builder title2 = new AlertDialog.Builder(this).setTitle(this.mAudioTitle);
                return title2.setMessage("ret : " + String.valueOf(this.mAudioSetRet)).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioAudioLogger.this.removeDialog(4);
                    }
                }).create();
            case 5:
                ArrayList<String> arrayList = this.mAudioCheckBoxRet;
                return new AlertDialog.Builder(this).setTitle(this.mAudioTitle).setItems((CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]), (DialogInterface.OnClickListener) null).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AudioAudioLogger.this.mAudioCheckBoxRet.clear();
                        AudioAudioLogger.this.removeDialog(5);
                    }
                }).create();
            default:
                return null;
        }
    }

    private void parseXMLWithSAX(InputStream xmlData, AudioLoggerXMLData mAudioLoggerXMLData2) throws SAXException, ParserConfigurationException, IOException {
        XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        ContentHandler handler = new ContentHandler(mAudioLoggerXMLData2);
        if (xmlReader != null) {
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(xmlData));
        }
    }
}
