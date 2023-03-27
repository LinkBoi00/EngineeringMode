package com.mediatek.engineermode.rsc;

import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.rsc.ConfigXMLData;
import java.util.Objects;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {
    private static final String INDEX_ATTR = "index";
    private static final String MAGIC_TAG = "magic";
    private static final String NAME_TAG = "name";
    private static final String OFFSET_TAG = "offset";
    private static final String OPTR_TAG = "operator";
    private static final String PROJ_TAG = "proj_item";
    private static final String TAG = "rcs/ContentHandler";
    private static final String TAR_PARTITON_TAG = "part_info";
    private static final String VER_ATTR = "version";
    private static final String VER_TAG = "runtime_switchable_config";
    private ConfigXMLData mConfigXmlData;
    private boolean mIsTarPart = false;
    private String mNodeName;
    private ConfigXMLData.ProjectData mProjData;
    private StringBuilder mTempStr;

    public ContentHandler(ConfigXMLData xmlData) {
        this.mConfigXmlData = xmlData;
        this.mTempStr = new StringBuilder();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.mNodeName = qName;
        this.mTempStr.setLength(0);
        if (TAR_PARTITON_TAG.equals(qName)) {
            this.mIsTarPart = true;
        } else if (VER_TAG.equals(qName)) {
            this.mConfigXmlData.setVersion(Integer.valueOf(attributes.getValue("version")).intValue());
        } else if (PROJ_TAG.equals(qName)) {
            ConfigXMLData configXMLData = this.mConfigXmlData;
            Objects.requireNonNull(configXMLData);
            this.mProjData = new ConfigXMLData.ProjectData();
            this.mProjData.setIndex(Integer.valueOf(attributes.getValue(INDEX_ATTR)).intValue());
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        this.mTempStr.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (TAR_PARTITON_TAG.equals(qName)) {
            this.mIsTarPart = false;
        } else if (PROJ_TAG.equals(qName)) {
            this.mConfigXmlData.addProjectName(this.mProjData);
        } else if (MAGIC_TAG.equals(this.mNodeName)) {
            this.mConfigXmlData.setMagic(this.mTempStr.toString());
        } else if (NAME_TAG.equals(this.mNodeName)) {
            if (this.mIsTarPart) {
                this.mConfigXmlData.setTarPartName(this.mTempStr.toString());
                return;
            }
            this.mProjData.setName(this.mTempStr.toString());
            Elog.d(TAG, "addProjectName:" + this.mTempStr);
        } else if (OPTR_TAG.equals(this.mNodeName)) {
            this.mProjData.setOptr(this.mTempStr.toString());
        } else if (OFFSET_TAG.equals(this.mNodeName)) {
            this.mConfigXmlData.setTarPartOffset(this.mTempStr.toString());
        }
    }
}
