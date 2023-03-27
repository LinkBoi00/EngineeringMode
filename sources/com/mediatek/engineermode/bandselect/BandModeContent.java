package com.mediatek.engineermode.bandselect;

public class BandModeContent {
    public static final int EVENT_QUERY_CURRENT_CDMA = 102;
    public static final int EVENT_QUERY_CURRENT_GSM = 101;
    public static final int EVENT_QUERY_CURRENT_NR = 104;
    public static final int EVENT_QUERY_SUPPORTED_GSM = 100;
    public static final int EVENT_QUERY_SUPPORTED_NR = 103;
    public static final int EVENT_RESET = 2;
    public static final int EVENT_SET_BUTTON = 110;
    public static final int EVENT_SET_CDMA = 111;
    public static final int EVENT_SET_FAIL = 1;
    public static final int EVENT_SET_NR = 112;
    public static final int[] GSM_BAND_BIT = {1, 3, 4, 7};
    public static final int GSM_DCS1800_BIT = 3;
    public static final int GSM_EGSM900_BIT = 1;
    public static final int GSM_GSM850_BIT = 7;
    public static final long GSM_MAX_VALUE = 255;
    public static final int GSM_PCS1900_BIT = 4;
    public static final long LTE_MAX_VALUE = 4294967295L;
    public static final String QUERY_CURRENT_COMMAND = "AT+EPBSE?";
    public static final String QUERY_CURRENT_COMMAND_CDMA = "AT+ECBANDCFG?";
    public static final String QUERY_CURRENT_NR_COMMAND = "AT+EPBSEH?";
    public static final String QUERY_SUPPORT_COMMAND = "AT+EPBSE=?";
    public static final String QUERY_SUPPORT_NR_COMMAND = "AT+EPBSEH=?";
    public static final String SAME_COMMAND = "+EPBSE:";
    public static final String SAME_COMMAND_CDMA = "+ECBANDCFG:";
    public static final String SAME_NR_COMMAND = "+EPBSEH:";
    public static final String SET_COMMAND = "AT+EPBSE=";
    public static final String SET_COMMAND_CDMA = "AT+ECBANDCFG=";
    public static final String SET_NR_COMMAND = "AT+EPBSEH=";
    public static final long UMTS_MAX_VALUE = 65535;
}
