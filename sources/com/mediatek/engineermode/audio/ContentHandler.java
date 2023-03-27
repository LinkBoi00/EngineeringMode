package com.mediatek.engineermode.audio;

import com.mediatek.engineermode.Elog;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {
    private static final String TAG = "Audio/ContentHandler";
    AudioLoggerXMLData mAudioLoggerXMLData;
    DumpOptions mDumpOptions = null;
    private String mNodeName;
    private StringBuilder mOption;

    public ContentHandler(AudioLoggerXMLData xmlData) {
        this.mAudioLoggerXMLData = xmlData;
    }

    public void startDocument() throws SAXException {
        this.mOption = new StringBuilder();
        Elog.d(TAG, "startDocument");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Elog.d(TAG, "uri:" + uri + " localName:" + localName + " qName:" + qName);
        this.mNodeName = localName;
        if ("Category".equals(localName)) {
            String myTitle = attributes.getValue("title");
            if (!myTitle.isEmpty()) {
                Elog.d(TAG, "myTitle:" + myTitle);
                DumpOptions dumpOptions = new DumpOptions();
                this.mDumpOptions = dumpOptions;
                dumpOptions.mCategoryTitle = myTitle;
            }
        } else if ("Option".equals(this.mNodeName)) {
            String type = attributes.getValue(JsonCmd.STR_TYPE_KEY);
            String mCmd = attributes.getValue("cmd");
            String check = attributes.getValue("check");
            String uncheck = attributes.getValue("uncheck");
            Elog.d(TAG, "attributes.getValue(type):" + type);
            Elog.d(TAG, "attributes.getValue(cmd):" + mCmd);
            Elog.d(TAG, "attributes.getValue(check):" + check);
            Elog.d(TAG, "attributes.getValue(uncheck):" + uncheck);
            this.mDumpOptions.mType.add(type);
            this.mDumpOptions.mCmd.add(mCmd);
            this.mDumpOptions.mCheck.add(check);
            this.mDumpOptions.mUncheck.add(uncheck);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("Option".equals(this.mNodeName)) {
            this.mOption.append(ch, start, length);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("Category".equals(localName)) {
            Elog.d(TAG, "endElement,Category->mOption:" + this.mOption.toString());
            if (!this.mOption.toString().isEmpty()) {
                String[] str = this.mOption.toString().trim().replaceAll("\r|\n", ",").replaceAll("\\s*", "").split(",");
                for (String add : str) {
                    this.mDumpOptions.mCmdName.add(add);
                }
                this.mAudioLoggerXMLData.mAudioDumpOperation.add(this.mDumpOptions);
            }
            this.mOption.setLength(0);
        } else if ("SetAudioCommand".equals(localName)) {
            String[] str2 = this.mOption.toString().trim().replaceAll("\r|\n", "@").replaceAll("\\s*", "").split("@");
            for (String audioCommandSetOperation : str2) {
                this.mAudioLoggerXMLData.setAudioCommandSetOperation(audioCommandSetOperation);
            }
            this.mOption.setLength(0);
        } else if ("GetAudioCommand".equals(localName)) {
            String[] str3 = this.mOption.toString().trim().replaceAll("\r|\n", ",").replaceAll("\\s*", "").split(",");
            for (String audioCommandGetOperation : str3) {
                this.mAudioLoggerXMLData.setAudioCommandGetOperation(audioCommandGetOperation);
            }
            this.mOption.setLength(0);
        } else if ("SetParameters".equals(localName)) {
            String[] str4 = this.mOption.toString().trim().replaceAll("\r|\n", ",").replaceAll("\\s*", "").split(",");
            for (String parametersSetOperation : str4) {
                this.mAudioLoggerXMLData.setParametersSetOperation(parametersSetOperation);
            }
            this.mOption.setLength(0);
        } else if ("GetParameters".equals(localName)) {
            String[] str5 = this.mOption.toString().trim().replaceAll("\r|\n", ",").replaceAll("\\s*", "").split(",");
            for (String parametersGetOperation : str5) {
                this.mAudioLoggerXMLData.setParametersGetOperation(parametersGetOperation);
            }
            this.mOption.setLength(0);
        }
    }

    public void endDocument() throws SAXException {
        Elog.d(TAG, "endDocument");
        for (int i = 0; i < this.mAudioLoggerXMLData.mAudioCommandSetOperation.size(); i++) {
            Elog.d(TAG, "mAudioLoggerXMLData.mAudioCommandSetOperation:" + i + " : " + this.mAudioLoggerXMLData.mAudioCommandSetOperation.get(i));
        }
        for (int i2 = 0; i2 < this.mAudioLoggerXMLData.mAudioCommandGetOperation.size(); i2++) {
            Elog.d(TAG, "mAudioLoggerXMLData.mAudioCommandGetOperation:" + i2 + " : " + this.mAudioLoggerXMLData.mAudioCommandGetOperation.get(i2));
        }
        for (int i3 = 0; i3 < this.mAudioLoggerXMLData.mParametersSetOperationItems.size(); i3++) {
            Elog.d(TAG, "mAudioLoggerXMLData.mParametersSetOperationItems:" + i3 + " : " + this.mAudioLoggerXMLData.mParametersSetOperationItems.get(i3));
        }
        for (int i4 = 0; i4 < this.mAudioLoggerXMLData.mParametersGetOperationItems.size(); i4++) {
            Elog.d(TAG, "mAudioLoggerXMLData.mParametersGetOperationItems:" + i4 + " : " + this.mAudioLoggerXMLData.mParametersGetOperationItems.get(i4));
        }
        for (int i5 = 0; i5 < this.mAudioLoggerXMLData.mAudioDumpOperation.size(); i5++) {
            Elog.d(TAG, "mAudioLoggerXMLData.mAudioDumpOperation,title:" + i5 + " : " + this.mAudioLoggerXMLData.mAudioDumpOperation.get(i5).mCategoryTitle);
            for (int j = 0; j < this.mAudioLoggerXMLData.mAudioDumpOperation.get(i5).mCmdName.size(); j++) {
                Elog.d(TAG, "mAudioLoggerXMLData.mAudioDumpOperation,mCmd:" + this.mAudioLoggerXMLData.mAudioDumpOperation.get(i5).mCmd.get(j));
                Elog.d(TAG, "mAudioLoggerXMLData.mAudioDumpOperation,mCmd name:" + this.mAudioLoggerXMLData.mAudioDumpOperation.get(i5).mCmdName.get(j));
            }
        }
    }
}
