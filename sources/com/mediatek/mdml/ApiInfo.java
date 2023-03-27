package com.mediatek.mdml;

class ApiInfo {
    private CmdApiMap[] cmdTable;
    private String m_api;
    private MONITOR_CMD_CODE m_cmdCode;
    private String m_method;
    private int m_version;

    public ApiInfo(String szApi, String szMethod) {
        this(szApi, szMethod, 1);
    }

    public ApiInfo(String szApi, String szMethod, int version) {
        int i = 0;
        this.cmdTable = new CmdApiMap[]{new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_SESSION_CREATE, "MDMonitor.Session", "create"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_SESSION_CLOSE, "MDMonitor.Session", "close"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_SUBSCRIBE, "MDMonitor.Trap", "subscribe"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_UNSUBSCRIBE, "MDMonitor.Trap", "unsubscribe"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_ENABLE, "MDMonitor.Trap", "enable"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_DISABLE, "MDMonitor.Trap", "disable"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_RECEIVER_SET, "MDMonitor.Trap.Receiver", "set")};
        this.m_version = version;
        this.m_api = szApi;
        this.m_method = szMethod;
        this.m_cmdCode = MONITOR_CMD_CODE.MONITOR_CMD_CODE_UNDEFINED;
        CmdApiMap[] cmdApiMapArr = this.cmdTable;
        int length = cmdApiMapArr.length;
        while (i < length) {
            CmdApiMap aCmdTable = cmdApiMapArr[i];
            if (!szApi.equals(aCmdTable.m_szApi) || !szMethod.equals(aCmdTable.m_szMethod)) {
                i++;
            } else {
                this.m_cmdCode = aCmdTable.m_cmdCode;
                return;
            }
        }
    }

    public ApiInfo(MONITOR_CMD_CODE code) {
        CmdApiMap[] cmdApiMapArr = {new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_SESSION_CREATE, "MDMonitor.Session", "create"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_SESSION_CLOSE, "MDMonitor.Session", "close"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_SUBSCRIBE, "MDMonitor.Trap", "subscribe"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_UNSUBSCRIBE, "MDMonitor.Trap", "unsubscribe"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_ENABLE, "MDMonitor.Trap", "enable"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_DISABLE, "MDMonitor.Trap", "disable"), new CmdApiMap(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_RECEIVER_SET, "MDMonitor.Trap.Receiver", "set")};
        this.cmdTable = cmdApiMapArr;
        this.m_version = 0;
        this.m_api = null;
        this.m_method = null;
        this.m_cmdCode = code;
        for (CmdApiMap aCmdTable : cmdApiMapArr) {
            if (code.equals(aCmdTable.m_cmdCode)) {
                this.m_api = aCmdTable.m_szApi;
                this.m_method = aCmdTable.m_szMethod;
                this.m_version = 1;
                return;
            }
        }
    }

    public MONITOR_CMD_CODE GetCmdCode() {
        return this.m_cmdCode;
    }

    public String GetApi() {
        return this.m_api;
    }

    public String GetMethod() {
        return this.m_method;
    }

    public int GetVersion() {
        return this.m_version;
    }

    class CmdApiMap {
        public MONITOR_CMD_CODE m_cmdCode;
        public String m_szApi;
        public String m_szMethod;

        public CmdApiMap(MONITOR_CMD_CODE cmd_code, String szApi, String szMethod) {
            this.m_cmdCode = cmd_code;
            this.m_szApi = szApi;
            this.m_szMethod = szMethod;
        }
    }
}
