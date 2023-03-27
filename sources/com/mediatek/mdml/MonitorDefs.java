package com.mediatek.mdml;

public class MonitorDefs {
    static final int ANDROID_P_VERSION = 28;
    static final String DEFAULT_LAYOUT_DESC_DIR_SEARCH_PATH = "/vendor/etc/mddb/";
    static final String DEFAULT_LAYOUT_DESC_DIR_SEARCH_PATH_DATA = "/data/md_mon/";
    static final String DEFAULT_LAYOUT_DESC_PREFIX = "mdm_layout_desc";
    static final String DEFAULT_LAYOUT_DESC_SUFFIX = ".dat";
    static final int DHL_MAX_FRAME_SIZE = 65536;
    static final int MAX_PAYLOAD_LEN = 131072;
    static final int MAX_RAWDATA_LEN = ((TRAP_TYPE.TRAP_TYPE_SIZE.ordinal() * 5) + 65545);
    static final int MAX_READ_BUFFER_SIZE = 262144;
    static final String MONITOR_COMMAND_SERVICE_ABSTRACT_NAME = "com.mediatek.mdmonitor.command";
    static final String PROPERTY_SDK_VERSION = "ro.build.version.sdk";
    static final String PROPERTY_SINGLE_BIN_MODEM_SUPPORT_O = "ro.mtk_single_bin_modem_support";
    static final String PROPERTY_SINGLE_BIN_MODEM_SUPPORT_P = "ro.vendor.mtk_single_bin_modem_support";
}
