package com.mediatek.mdml;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import com.mediatek.engineermode.FeatureSupport;
import com.mediatek.engineermode.mcfconfig.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import vendor.mediatek.hardware.mdmonitor.V1_0.IMDMonitorService;

public class PlainDataDecoder {
    private static final String TAG = "MDML/PlainDataDecoder";
    private static HashMap<String, PlainDataDecoder> sPathDecoderMap = new HashMap<>();
    private InfoType currentBlock = InfoType.NONE;
    private InfoDat infoDat = new InfoDat();
    private IMDMonitorService mdmConnection;
    private int verMain = 0;
    private int verSub = 0;

    private String SearchLayoutFile(String path) {
        String fileName = null;
        if (path == null) {
            return null;
        }
        File folder = new File(path);
        try {
            String[] allFiles = folder.list();
            if (allFiles == null) {
                Log.d(TAG, path + "is not exsist");
                return null;
            }
            int i = 0;
            while (true) {
                if (i >= allFiles.length) {
                    break;
                } else if (allFiles[i].startsWith("mdm_layout_desc") && allFiles[i].endsWith(".dat")) {
                    fileName = path + allFiles[i];
                    Log.d(TAG, "Layout file: [" + fileName + "] was found!");
                    break;
                } else {
                    i++;
                }
            }
            if (i == allFiles.length) {
                Log.e(TAG, "Can't find layout file. in " + folder);
            }
            return fileName;
        } catch (SecurityException e) {
            Log.e(TAG, "checkRead method denies read access to the file");
            return null;
        }
    }

    private int getLayoutFileDataFromMDMAndSaveToPath(String path) throws Exception {
        connectMDMHIDL();
        if (this.mdmConnection == null) {
            return -1;
        }
        FileOutputStream os = new FileOutputStream(new File(path + "mdm_layout_desc.dat"));
        int start_pos = 0;
        Log.d(TAG, "getModemBinData request buffer is " + (65536 / 1024) + "K");
        do {
            try {
                ArrayList<Byte> resultList = this.mdmConnection.getModemBinData(0, start_pos, 65536);
                if (resultList.size() == 0) {
                    os.close();
                    Log.d(TAG, "getModemBinData request buffer end");
                    return 0;
                }
                byte[] result = new byte[resultList.size()];
                for (int i = 0; i < resultList.size(); i++) {
                    result[i] = resultList.get(i).byteValue();
                }
                os.write(result);
                start_pos += 65536;
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "getModemBinData HIDL call failed with RemoteException exception!");
                os.close();
                return -2;
            }
        } while (start_pos <= 52428800);
        Log.e(TAG, "getModemBinData size out of 50 MB, somthing wrong!");
        os.close();
        return -3;
    }

    private void connectMDMHIDL() {
        try {
            Log.d(TAG, "connectMDMHIDL");
            this.mdmConnection = IMDMonitorService.getService(true);
            Log.d(TAG, "connectMDMHIDL done!");
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "IMDMonitorService RemoteException ...");
            this.mdmConnection = null;
        } catch (NoSuchElementException e2) {
            e2.printStackTrace();
            Log.e(TAG, "IMDMonitorService NoSuchElementException ...");
            this.mdmConnection = null;
        }
    }

    @Deprecated
    public PlainDataDecoder(String fileName) throws Exception {
        Log.e(TAG, "Old PlainDataDecoder constructor, it was phase out. Please use new interface of PlainDataDecoder constructor.");
        throw new Exception("Old PlainDataDecoder constructor, it was phase out. Please use new interface of PlainDataDecoder constructor.");
    }

    public PlainDataDecoder(String fileName, Context context) throws Exception {
        String propertyName;
        if (fileName == null) {
            if (SystemProperties.getInt("ro.build.version.sdk", 28) >= 28) {
                propertyName = FeatureSupport.FK_SINGLE_BIN_MODEM_SUPPORT;
            } else {
                propertyName = "ro.mtk_single_bin_modem_support";
            }
            if (SystemProperties.getInt(propertyName, 0) == 1) {
                String appLocalFilePath = context.getCacheDir().getAbsolutePath() + FileUtils.SEPARATOR;
                Log.d(TAG, "search app local directory: " + appLocalFilePath);
                String SearchLayoutFile = SearchLayoutFile(appLocalFilePath);
                fileName = SearchLayoutFile;
                if (SearchLayoutFile == null) {
                    if (getLayoutFileDataFromMDMAndSaveToPath(appLocalFilePath) == 0) {
                        String SearchLayoutFile2 = SearchLayoutFile(appLocalFilePath);
                        fileName = SearchLayoutFile2;
                        if (SearchLayoutFile2 == null) {
                            throw new Exception("Single bin modem support, Can't find layout file in folder = " + appLocalFilePath);
                        }
                    } else {
                        throw new Exception("Single bin modem support, error in getLayoutFileDataFromMDMAndSaveToPath() for extrace bin to = " + appLocalFilePath);
                    }
                }
            } else {
                String SearchLayoutFile3 = SearchLayoutFile("/vendor/etc/mddb/");
                fileName = SearchLayoutFile3;
                if (SearchLayoutFile3 == null) {
                    throw new Exception("Can't find layout file in modem single bin OFF case.");
                }
            }
        }
        this.infoDat.otaInfo.clear();
        this.infoDat.msgInfo.clear();
        this.infoDat.globalID.clear();
        this.infoDat.simInfo.clear();
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        while (br.ready()) {
            String line = br.readLine();
            if (line != null && line.length() > 0) {
                if (line.charAt(0) != '#' || !readTag(line.substring(1))) {
                    readContent(line);
                }
            }
        }
        fr.close();
    }

    @Deprecated
    public static synchronized PlainDataDecoder getInstance(String fileName) throws Exception {
        synchronized (PlainDataDecoder.class) {
            Log.e(TAG, "Old PlainDataDecoder getInstance function, it was phase out. Please use new interface of PlainDataDecoder getInstance.");
            throw new Exception("Old PlainDataDecoder getInstance function, it was phase out. Please use new interface of PlainDataDecoder getInstance.");
        }
    }

    public static synchronized PlainDataDecoder getInstance(String fileName, Context context) {
        PlainDataDecoder plainDataDecoder;
        synchronized (PlainDataDecoder.class) {
            String filepath = "null";
            if (fileName != null) {
                filepath = fileName;
            }
            plainDataDecoder = sPathDecoderMap.get(filepath);
            if (plainDataDecoder == null) {
                try {
                    plainDataDecoder = new PlainDataDecoder(fileName, context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sPathDecoderMap.put(filepath, plainDataDecoder);
            }
        }
        return plainDataDecoder;
    }

    public final Msg otaInfo_getMsg(byte[] buffer, int startPos) {
        return commonGetMsg(buffer, startPos, InfoType.OTA_INFO);
    }

    public String[] otaInfo_getMsgList() {
        return this.infoDat.otaInfo.getMsgList();
    }

    public final Integer otaInfo_getMsgID(String msgName) {
        return this.infoDat.otaInfo.getMsgID(msgName);
    }

    public final String otaInfo_getMsgName(Integer id) {
        return this.infoDat.otaInfo.getMsgName(id);
    }

    public final Msg msgInfo_getMsg(byte[] buffer, int startPos) {
        return commonGetMsg(buffer, startPos, InfoType.MSG_INFO);
    }

    public String[] msgInfo_getMsgList() {
        return this.infoDat.msgInfo.getMsgList();
    }

    public final Integer msgInfo_getMsgID(String msgName) {
        return this.infoDat.msgInfo.getMsgID(msgName);
    }

    public final String msgInfo_getMsgName(Integer id) {
        return this.infoDat.msgInfo.getMsgName(id);
    }

    public String[] globalId_getList() {
        return this.infoDat.globalID.getList();
    }

    public final Integer globalId_getValue(String name) {
        return this.infoDat.globalID.getValue(name);
    }

    public final String globalId_getName(Integer id) {
        return this.infoDat.globalID.getName(id);
    }

    private boolean readTag(String tag) {
        String tag2 = tag.trim();
        this.infoDat.msgInfo.setMsgHasPeerBufferHeader(false);
        if (tag2.equals("OTA_INFO")) {
            this.currentBlock = InfoType.OTA_INFO;
        } else if (tag2.equals("GLOBAL_ID")) {
            this.currentBlock = InfoType.GLOBAL_ID;
        } else if (tag2.equals("MSG_INFO")) {
            this.currentBlock = InfoType.MSG_INFO;
        } else if (tag2.equals("PEER_INFO")) {
            this.currentBlock = InfoType.PEER_INFO;
            this.infoDat.msgInfo.setMsgHasPeerBufferHeader(true);
        } else if (tag2.equals("PEER_INFO_WITHOUT_HEADER")) {
            this.currentBlock = InfoType.PEER_INFO_WITHOUT_HEADER;
        } else if (this.infoDat.simInfo.readTag(tag2)) {
            this.currentBlock = InfoType.SIM_INFO;
        } else if (!readVersion(tag2)) {
            Log.d(TAG, "Un-Support section block name [" + tag2 + "]!");
            this.currentBlock = InfoType.NONE;
        }
        return true;
    }

    private boolean readVersion(String tag) {
        int lastIdx = tag.lastIndexOf("######");
        if (!tag.substring(0, "##### VER".length()).equals("##### VER") || lastIdx <= 0) {
            return false;
        }
        String[] v = tag.substring("##### VER".length(), lastIdx).trim().split("\\.");
        this.verMain = Integer.parseInt(v[0]);
        this.verSub = Integer.parseInt(v[1]);
        return true;
    }

    private boolean readContent(String line) {
        int i = this.verMain;
        if (i > 2 || (i == 1 && this.verSub > 0)) {
            Log.e(TAG, "Version not support! Main =" + this.verMain + ", SubVersion = " + this.verSub);
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$mediatek$mdml$InfoType[this.currentBlock.ordinal()]) {
            case 1:
                Log.d(TAG, "Skip line content in un-support section!");
                return false;
            case 2:
                return this.infoDat.otaInfo.readLine(line);
            case 3:
            case 4:
            case 5:
                return this.infoDat.msgInfo.readLine(line);
            case 6:
                return this.infoDat.globalID.readLine(line);
            case 7:
                return this.infoDat.simInfo.readLine(line);
            default:
                Log.e(TAG, "Skip line content in un-know section!");
                return false;
        }
    }

    private Msg commonGetMsg(byte[] buffer, int startPos, InfoType infoType) {
        OffsetAndSize msgIdOffsetAndSize;
        switch (infoType) {
            case OTA_INFO:
                msgIdOffsetAndSize = this.infoDat.otaInfo.msgIdOffsetAndSize;
                break;
            case MSG_INFO:
                msgIdOffsetAndSize = this.infoDat.msgInfo.msgIdOffsetAndSize;
                break;
            default:
                return null;
        }
        Msg m = commonGetMsg(Integer.valueOf(Util.getIntValue(buffer, startPos, msgIdOffsetAndSize)), infoType);
        if (m != null) {
            m.buf = buffer;
            m.startPos = startPos;
        }
        return m;
    }

    private Msg commonGetMsg(Integer id, InfoType infoType) {
        CommonMsgInfo msgInfo = getMsgInfo(infoType);
        if (msgInfo == null) {
            return null;
        }
        return setBaseMsg2Msg(msgInfo.getBaseMsg(id), infoType);
    }

    private Msg commonGetMsg(String name, InfoType infoType) {
        CommonMsgInfo msgInfo = getMsgInfo(infoType);
        if (msgInfo == null) {
            return null;
        }
        return setBaseMsg2Msg(msgInfo.getBaseMsg(name), infoType);
    }

    private Msg setBaseMsg2Msg(BaseMsg baseMsg, InfoType type) {
        if (baseMsg == null) {
            return null;
        }
        Msg msg = new Msg();
        msg.baseMsg = baseMsg;
        msg.type = type;
        switch (type) {
            case OTA_INFO:
                msg.simIdxOffsetAndSize = this.infoDat.otaInfo.simIdxOffsetAndSize;
                msg.peerBufSizeOffsetAndSize = null;
                msg.globalID = this.infoDat.globalID;
                msg.simInfo = this.infoDat.simInfo;
                break;
            case MSG_INFO:
                msg.simIdxOffsetAndSize = this.infoDat.msgInfo.simIdxOffsetAndSize;
                msg.peerBufSizeOffsetAndSize = this.infoDat.msgInfo.peerBufSizeOffsetAndSize;
                msg.globalID = null;
                msg.simInfo = this.infoDat.simInfo;
                break;
            default:
                msg.simIdxOffsetAndSize = null;
                msg.peerBufSizeOffsetAndSize = null;
                msg.globalID = null;
                msg.simInfo = null;
                break;
        }
        return msg;
    }

    private CommonMsgInfo getMsgInfo(InfoType infoType) {
        switch (infoType) {
            case OTA_INFO:
                return this.infoDat.otaInfo;
            case MSG_INFO:
                return this.infoDat.msgInfo;
            default:
                return null;
        }
    }

    public static String getIcdJsonPath() {
        return "/vendor/etc/AllICD.json";
    }
}
