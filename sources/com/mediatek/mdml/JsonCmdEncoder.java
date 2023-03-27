package com.mediatek.mdml;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

class JsonCmdEncoder {
    private static final int SID_ERR = 0;
    private static final String TAG = "MDML/JsonCmdEncoder";
    private Context mClientContext = null;
    private MCPTransmitter m_McpTransmitter;
    private HIDLMCPTransmitter m_hidlMcpTransmitter;

    public JsonCmdEncoder(MCPTransmitter mcpTransmitter, Context context) {
        this.m_McpTransmitter = mcpTransmitter;
        if (context != null) {
            this.mClientContext = context;
            Log.d(TAG, "JsonCmdEncoder with MCPTransmitter Constructor done!");
            return;
        }
        throw new Error("MDML/JsonCmdEncoder context should not be null!");
    }

    public JsonCmdEncoder(HIDLMCPTransmitter mcpTransmitter, Context context) {
        this.m_hidlMcpTransmitter = mcpTransmitter;
        if (context != null) {
            this.mClientContext = context;
            Log.d(TAG, "JsonCmdEncoder with HIDLMCPTransmitter  Constructor done!");
            return;
        }
        throw new Error("MDML/JsonCmdEncoder context should not be null!");
    }

    public class ValueHolder<T> {
        public T value;

        public ValueHolder() {
        }
    }

    /* access modifiers changed from: package-private */
    public boolean Send(MCPInfo cmd, MCPInfo reply) {
        HIDLMCPTransmitter hIDLMCPTransmitter = this.m_hidlMcpTransmitter;
        if (hIDLMCPTransmitter == null) {
            return false;
        }
        return hIDLMCPTransmitter.Send(cmd, reply);
    }

    private MONITOR_CMD_RESP ROUTINE_TASK(MONITOR_CMD_CODE cmdCode, JSONObject jCmd, ValueHolder<JSONObject> jReply) {
        ValueHolder<JSONObject> jReply2;
        JSONObject jSONObject = jCmd;
        ApiInfo apiInfo = new ApiInfo(cmdCode);
        try {
            jSONObject.put(JsonCmd.STR_API_KEY, apiInfo.GetApi());
            jSONObject.put(JsonCmd.STR_METHOD_KEY, apiInfo.GetMethod());
            jSONObject.put(JsonCmd.STR_VERSION_KEY, apiInfo.GetVersion());
            MCPInfo cmd = new MCPInfo();
            String strCmd = jCmd.toString();
            byte[] cmdData = (strCmd + 0).getBytes();
            cmd.SetData(MCP_TYPE.MCP_TYPE_JSON_CMD, cmdData.length, cmdData);
            MCPInfo reply = new MCPInfo();
            if (!Send(cmd, reply)) {
                Log.e(TAG, "Send command failed.");
                return MONITOR_CMD_RESP.MONITOR_CMD_RESP_NO_CONNECTION;
            } else if (MCP_TYPE.MCP_TYPE_UNDEFINED == reply.GetType()) {
                Log.e(TAG, "No reply received.");
                return MONITOR_CMD_RESP.MONITOR_CMD_RESP_NO_CONNECTION;
            } else if (MCP_TYPE.MCP_TYPE_JSON_CMD_RESP != reply.GetType()) {
                Log.e(TAG, "Reply type not in Json. [" + reply.GetType().ordinal() + "]");
                return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
            } else {
                if (jReply == null) {
                    jReply2 = new ValueHolder<>();
                } else {
                    jReply2 = jReply;
                }
                try {
                    String strReply = new String(reply.GetData(), "UTF-8");
                    Log.d(TAG, "Receive reply [" + strReply + "]");
                    jReply2.value = new JSONObject(strReply);
                    try {
                        String strApi = ((JSONObject) jReply2.value).getString(JsonCmd.STR_API_KEY);
                        if (!strApi.equals(apiInfo.GetApi())) {
                            Log.e(TAG, "Reply API [" + strApi + "] != Command API [" + apiInfo.GetApi() + "]");
                            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                        }
                        try {
                            String strMethod = ((JSONObject) jReply2.value).getString(JsonCmd.STR_METHOD_KEY);
                            if (!strMethod.equals(apiInfo.GetMethod())) {
                                Log.e(TAG, "Reply Method [" + strMethod + "] != Command Method [" + apiInfo.GetMethod() + "]");
                                return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                            }
                            try {
                                int version = ((JSONObject) jReply2.value).getInt(JsonCmd.STR_VERSION_KEY);
                                if (version != apiInfo.GetVersion()) {
                                    Log.e(TAG, "Reply Version [" + version + "] != Command Version [" + apiInfo.GetVersion() + "]");
                                    return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                                }
                                try {
                                    int ret = ((JSONObject) jReply2.value).getInt(JsonCmd.STR_RET_KEY);
                                    if (ret >= 0) {
                                        if (MONITOR_CMD_RESP.MONITOR_CMD_RESP_SIZE.ordinal() > ret) {
                                            return MONITOR_CMD_RESP.values()[ret];
                                        }
                                    }
                                    Log.e(TAG, "ret is invalid. [" + ret + "]");
                                    return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                                } catch (Exception e) {
                                    Log.e(TAG, "No key: [ret] in JSON reply: " + ((JSONObject) jReply2.value).toString());
                                    return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                                }
                            } catch (Exception e2) {
                                Log.e(TAG, "No key: [version] in JSON reply: " + ((JSONObject) jReply2.value).toString());
                                return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                            }
                        } catch (Exception e3) {
                            Log.e(TAG, "No key: [method] in JSON reply: " + ((JSONObject) jReply2.value).toString());
                            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                        }
                    } catch (Exception e4) {
                        Log.e(TAG, "No key: [api] in JSON reply: " + ((JSONObject) jReply2.value).toString());
                        return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                    }
                } catch (Exception e5) {
                    Log.e(TAG, "Failed to parse reply string to Json.");
                    return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_REPLY;
                }
            }
        } catch (Exception e6) {
            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_INTERNAL_ERROR;
        }
    }

    public long createSession() {
        JSONObject jCmd = new JSONObject();
        try {
            JSONObject jArg = new JSONObject();
            jArg.put(JsonCmd.STR_CLIENTPACKAGE_KEY, this.mClientContext.getPackageName());
            jCmd.put(JsonCmd.STR_ARG_KEY, jArg);
            ValueHolder<JSONObject> jReply = new ValueHolder<>();
            MONITOR_CMD_RESP ret = ROUTINE_TASK(MONITOR_CMD_CODE.MONITOR_CMD_CODE_SESSION_CREATE, jCmd, jReply);
            if (MONITOR_CMD_RESP.MONITOR_CMD_RESP_SUCCESS == ret) {
                try {
                    return ((JSONObject) jReply.value).getJSONObject(JsonCmd.STR_DATA_KEY).getLong(JsonCmd.STR_SESSIONID_KEY);
                } catch (Exception e) {
                    Log.e(TAG, "No key: [api] in JSON reply: " + ((JSONObject) jReply.value).toString());
                    return 0;
                }
            } else if (MONITOR_CMD_RESP.MONITOR_CMD_RESP_UNAUTHORIZED != ret) {
                return 0;
            } else {
                throw new Error("Unauthorized to use MDML!");
            }
        } catch (Exception e2) {
            Log.e(TAG, "Failed to insert data into Json cmd.");
            return 0;
        }
    }

    public MONITOR_CMD_RESP closeSession(long sessionId) {
        JSONObject jCmd = new JSONObject();
        try {
            JSONObject jArg = new JSONObject();
            jArg.put(JsonCmd.STR_SESSIONID_KEY, sessionId);
            jCmd.put(JsonCmd.STR_ARG_KEY, jArg);
            return ROUTINE_TASK(MONITOR_CMD_CODE.MONITOR_CMD_CODE_SESSION_CLOSE, jCmd, (ValueHolder<JSONObject>) null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to insert data into Json cmd.");
            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_INTERNAL_ERROR;
        }
    }

    public MONITOR_CMD_RESP subscribeTrap(long sessionId, TRAP_TYPE type, long msgId) {
        return subscribeMultiTrap(sessionId, type, new long[]{msgId});
    }

    public MONITOR_CMD_RESP subscribeMultiTrap(long sessionId, TRAP_TYPE type, long[] msgId) {
        JSONObject jCmd = new JSONObject();
        try {
            JSONObject jArg = new JSONObject();
            jArg.put(JsonCmd.STR_SESSIONID_KEY, sessionId);
            String strTrapType = TRAP_TYPE.ToTrapStr(type);
            if (strTrapType == null) {
                Log.e(TAG, "TRAP_TYPE is invalid. type = " + type.ordinal());
                return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_PARAMETERS;
            }
            jArg.put(JsonCmd.STR_TYPE_KEY, strTrapType);
            if (msgId.length == 1) {
                jArg.put(JsonCmd.STR_MSGID_KEY, msgId[0]);
            } else {
                JSONArray idArray = new JSONArray();
                for (long put : msgId) {
                    idArray.put(put);
                }
                jArg.put(JsonCmd.STR_MSGID_KEY, idArray);
            }
            jCmd.put(JsonCmd.STR_ARG_KEY, jArg);
            return ROUTINE_TASK(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_SUBSCRIBE, jCmd, (ValueHolder<JSONObject>) null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to insert data into Json cmd.");
            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_INTERNAL_ERROR;
        }
    }

    public MONITOR_CMD_RESP unsubscribeTrap(long sessionId, TRAP_TYPE type, long msgId) {
        return unsubscribeMultiTrap(sessionId, type, new long[]{msgId});
    }

    public MONITOR_CMD_RESP unsubscribeMultiTrap(long sessionId, TRAP_TYPE type, long[] msgId) {
        JSONObject jCmd = new JSONObject();
        try {
            JSONObject jArg = new JSONObject();
            jArg.put(JsonCmd.STR_SESSIONID_KEY, sessionId);
            String strTrapType = TRAP_TYPE.ToTrapStr(type);
            if (strTrapType == null) {
                Log.e(TAG, "TRAP_TYPE is invalid. type = " + type.ordinal());
                return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_PARAMETERS;
            }
            jArg.put(JsonCmd.STR_TYPE_KEY, strTrapType);
            if (msgId.length == 1) {
                jArg.put(JsonCmd.STR_MSGID_KEY, msgId[0]);
            } else {
                JSONArray idArray = new JSONArray();
                for (long put : msgId) {
                    idArray.put(put);
                }
                jArg.put(JsonCmd.STR_MSGID_KEY, idArray);
            }
            jCmd.put(JsonCmd.STR_ARG_KEY, jArg);
            return ROUTINE_TASK(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_UNSUBSCRIBE, jCmd, (ValueHolder<JSONObject>) null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to insert data into Json cmd.");
            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_INTERNAL_ERROR;
        }
    }

    public MONITOR_CMD_RESP enableTrap(long sessionId) {
        JSONObject jCmd = new JSONObject();
        try {
            JSONObject jArg = new JSONObject();
            jArg.put(JsonCmd.STR_SESSIONID_KEY, sessionId);
            jCmd.put(JsonCmd.STR_ARG_KEY, jArg);
            return ROUTINE_TASK(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_ENABLE, jCmd, (ValueHolder<JSONObject>) null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to insert data into Json cmd.");
            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_INTERNAL_ERROR;
        }
    }

    public MONITOR_CMD_RESP disableTrap(long sessionId) {
        JSONObject jCmd = new JSONObject();
        try {
            JSONObject jArg = new JSONObject();
            jArg.put(JsonCmd.STR_SESSIONID_KEY, sessionId);
            jCmd.put(JsonCmd.STR_ARG_KEY, jArg);
            return ROUTINE_TASK(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_DISABLE, jCmd, (ValueHolder<JSONObject>) null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to insert data into Json cmd.");
            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_INTERNAL_ERROR;
        }
    }

    public MONITOR_CMD_RESP setTrapReceiver(long sessionId, String szServerName) {
        JSONObject jCmd = new JSONObject();
        if (szServerName == null) {
            Log.e(TAG, "server name is null.");
            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_BAD_PARAMETERS;
        }
        try {
            JSONObject jArg = new JSONObject();
            jArg.put(JsonCmd.STR_SESSIONID_KEY, sessionId);
            jArg.put(JsonCmd.STR_SERVERNAME_KEY, String.valueOf(sessionId) + szServerName);
            jCmd.put(JsonCmd.STR_ARG_KEY, jArg);
            return ROUTINE_TASK(MONITOR_CMD_CODE.MONITOR_CMD_CODE_TRAP_RECEIVER_SET, jCmd, (ValueHolder<JSONObject>) null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to insert data into Json cmd.");
            return MONITOR_CMD_RESP.MONITOR_CMD_RESP_INTERNAL_ERROR;
        }
    }
}
