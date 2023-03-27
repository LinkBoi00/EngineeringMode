package com.mediatek.engineermode.rsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.emsvr.AFMFunctionCallEx;
import com.mediatek.engineermode.emsvr.FunctionReturn;
import com.mediatek.engineermode.rsc.ConfigXMLData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class RuntimeSwitchConfig extends Activity {
    static final int DIALOG_LOADING_PROJECT = 3;
    static final int DIALOG_WARNING_REBOOT = 1;
    static final int DIALOG_WRITING_SELECTION = 2;
    static final String PROJECT_LIST_FILE_PATH = "/system/etc/rsc/rsc.xml";
    private static final String PROJ_PROP = "ro.boot.rsc";
    private static final String TAG = "rcs/RuntimeSwitchConfig";
    private Button mBtnApply = null;
    /* access modifiers changed from: private */
    public ConfigXMLData mConfigData = new ConfigXMLData();
    /* access modifiers changed from: private */
    public String mProjCurName = SystemProperties.get(PROJ_PROP);
    /* access modifiers changed from: private */
    public ArrayAdapter<String> mSpProjAdapter;
    /* access modifiers changed from: private */
    public Spinner mSpProject = null;

    public static boolean supportRsc() {
        if (new File(PROJECT_LIST_FILE_PATH).exists()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runtime_switch_config);
        this.mSpProjAdapter = new ArrayAdapter<>(this, 17367048);
        this.mSpProject = (Spinner) findViewById(R.id.rsc_project_spinner);
        Button button = (Button) findViewById(R.id.rsc_btn_apply);
        this.mBtnApply = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int index = RuntimeSwitchConfig.this.mSpProject.getSelectedItemPosition();
                if (RuntimeSwitchConfig.this.mProjCurName == null || !RuntimeSwitchConfig.this.mProjCurName.equals(RuntimeSwitchConfig.this.mSpProjAdapter.getItem(index))) {
                    RuntimeSwitchConfig.this.showDialog(1);
                } else {
                    Toast.makeText(RuntimeSwitchConfig.this, R.string.rsc_proj_unchanged, 1).show();
                }
            }
        });
        new ProjectLoadTask().execute(new Void[0]);
    }

    private class ProjectLoadTask extends AsyncTask<Void, Void, Void> {
        private int mProjCurIndex;

        private ProjectLoadTask() {
            this.mProjCurIndex = -1;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            RuntimeSwitchConfig.this.showDialog(3);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            loadProjects();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            updateProjList();
            RuntimeSwitchConfig.this.removeDialog(3);
        }

        private boolean parseXMLWithSAX(InputStream inStream, ConfigXMLData data) throws SAXException, ParserConfigurationException, IOException {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler(data);
            if (xmlReader == null) {
                return false;
            }
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(inStream));
            return true;
        }

        private void updateProjList() {
            RuntimeSwitchConfig.this.mSpProject.setAdapter(RuntimeSwitchConfig.this.mSpProjAdapter);
            if (this.mProjCurIndex != -1) {
                RuntimeSwitchConfig.this.mSpProject.setSelection(this.mProjCurIndex);
            }
        }

        private void loadProjects() {
            try {
                if (!parseXMLWithSAX(new FileInputStream(new File(RuntimeSwitchConfig.PROJECT_LIST_FILE_PATH)), RuntimeSwitchConfig.this.mConfigData)) {
                    Elog.e(RuntimeSwitchConfig.TAG, "xml parse error");
                    return;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (SAXException e2) {
                e2.printStackTrace();
            } catch (ParserConfigurationException e3) {
                e3.printStackTrace();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            RuntimeSwitchConfig.this.mSpProjAdapter.setDropDownViewResource(17367049);
            int size = RuntimeSwitchConfig.this.mConfigData.getProjectList().size();
            for (int k = 0; k < size; k++) {
                ConfigXMLData.ProjectData projData = RuntimeSwitchConfig.this.mConfigData.getProjectList().get(k);
                RuntimeSwitchConfig.this.mSpProjAdapter.add(projData.getName());
                if (this.mProjCurIndex == -1 && RuntimeSwitchConfig.this.mProjCurName != null && RuntimeSwitchConfig.this.mProjCurName.equals(projData.getName())) {
                    this.mProjCurIndex = k;
                }
            }
        }
    }

    private class ProjectSwitchTask extends AsyncTask<Void, Void, Void> {
        private ConfigXMLData.ProjectData mProj;

        private ProjectSwitchTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            RuntimeSwitchConfig.this.showDialog(2);
            this.mProj = RuntimeSwitchConfig.this.mConfigData.getProjectList().get(RuntimeSwitchConfig.this.mSpProject.getSelectedItemPosition());
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            storeProjSelected();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            RuntimeSwitchConfig.this.removeDialog(2);
            doFactoryReset();
        }

        private void storeProjSelected() {
            FunctionReturn funcRet;
            Elog.i(RuntimeSwitchConfig.TAG, "storeProjSelected");
            AFMFunctionCallEx functionCall = new AFMFunctionCallEx();
            if (functionCall.startCallFunctionStringReturn(AFMFunctionCallEx.FUNCTION_EM_RSC_WRITE)) {
                Elog.i(RuntimeSwitchConfig.TAG, "start write");
                functionCall.writeParamInt(RuntimeSwitchConfig.this.mConfigData.getVersion());
                functionCall.writeParamInt(this.mProj.getIndex());
                functionCall.writeParamString(this.mProj.getName());
                functionCall.writeParamString(this.mProj.getOptr());
                functionCall.writeParamString(RuntimeSwitchConfig.this.mConfigData.getTarPartOffset());
                Elog.i(RuntimeSwitchConfig.TAG, "end write");
                do {
                    funcRet = functionCall.getNextResult();
                    Elog.i(RuntimeSwitchConfig.TAG, "funcRet:" + funcRet);
                } while (funcRet.mReturnCode == 1);
            }
        }

        private void doFactoryReset() {
            Intent intent = new Intent("android.intent.action.FACTORY_RESET");
            intent.setPackage("android");
            intent.addFlags(268435456);
            intent.putExtra("android.intent.extra.REASON", "rsc");
            RuntimeSwitchConfig.this.sendBroadcast(intent);
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.rsc_dlg_reboot_title);
                builder.setMessage(getString(R.string.rsc_dlg_reboot_content));
                builder.setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new ProjectSwitchTask().execute(new Void[0]);
                    }
                });
                builder.setNegativeButton(R.string.rsc_cancel, (DialogInterface.OnClickListener) null);
                return builder.create();
            case 2:
                ProgressDialog dlgWriteSel = new ProgressDialog(this);
                dlgWriteSel.setMessage(getString(R.string.rsc_writing_selection));
                dlgWriteSel.setCancelable(false);
                dlgWriteSel.setIndeterminate(true);
                return dlgWriteSel;
            case 3:
                ProgressDialog dlgLoadProj = new ProgressDialog(this);
                dlgLoadProj.setMessage(getString(R.string.rsc_loading_project));
                dlgLoadProj.setCancelable(false);
                dlgLoadProj.setIndeterminate(true);
                return dlgLoadProj;
            default:
                return null;
        }
    }
}
