package com.mediatek.mdml;

import android.os.RemoteException;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import vendor.mediatek.hardware.mdmonitor.V1_0.IMDMonitorService;

class MCPHandler {
    private static final byte[] HEADER_MAGIC = {-82, -51, -24, -12};
    private static final int PAYLOAD_LEN_BYTES = 4;
    private static final String TAG = "MDML/MCPHandler";
    private static final int TYPE_MAGIC_BYTES = 2;
    private static final TypeMagic[] m_typeMagicTable = {new TypeMagic(MCP_TYPE.MCP_TYPE_JSON_CMD, new byte[]{117, 87}), new TypeMagic(MCP_TYPE.MCP_TYPE_JSON_CMD_RESP, new byte[]{117, 88}), new TypeMagic(MCP_TYPE.MCP_TYPE_TRAP, new byte[]{-84, -54})};

    MCPHandler() {
    }

    static boolean WriteToConnection(SocketConnection connection, MCPInfo cmd, MCPInfo reply) {
        if (connection == null || cmd == null) {
            Log.e(TAG, "connection or cmd is null");
            return false;
        } else if (cmd.GetData() == null || cmd.GetLen() == 0 || cmd.GetType() == null) {
            Log.e(TAG, "cmd data/type is null or length is 0.");
            return true;
        } else {
            byte[] GetTypeMagic = GetTypeMagic(cmd.GetType());
            byte[] typeMagic = GetTypeMagic;
            if (GetTypeMagic == null) {
                Log.e(TAG, "Undefined type! type = [" + cmd.GetType().name() + "]. DROP.");
                return true;
            }
            int len = cmd.GetLen();
            if (!IsPayloadLenValid(len)) {
                Log.e(TAG, "Payload length is invalid! len = [" + len + "]. DROP.");
                return true;
            }
            byte[] bArr = HEADER_MAGIC;
            if (!connection.Write(bArr, bArr.length)) {
                Log.e(TAG, "MCPHandler::WriteToConnection (HEADER_MAGIC) failed!");
                return false;
            } else if (!connection.Write(typeMagic, 2)) {
                Log.e(TAG, "MCPHandler::WriteToConnection (TYPE_Magic) failed!");
                return false;
            } else {
                byte[] lenByteArray = MonitorUtils.IntToByteArray(len);
                if (!connection.Write(lenByteArray, lenByteArray.length)) {
                    Log.e(TAG, "MCPHandler::WriteToConnection (Len) failed!");
                    return false;
                } else if (!connection.Write(cmd.GetData(), len)) {
                    Log.e(TAG, "MCPHandler::WriteToConnection (Data) failed!");
                    return false;
                } else if (reply == null) {
                    return true;
                } else {
                    reply.Reset();
                    int i = 0;
                    while (true) {
                        if (i > 10) {
                            break;
                        } else if (!connection.ReadToBuffer()) {
                            Log.e(TAG, "Failed to refresh buffer. (read error)");
                            break;
                        } else {
                            while (true) {
                                DataQueue dataQ = connection.GetDataQueue();
                                if (dataQ.Size() == 0) {
                                    break;
                                } else if (reply.GetLen() == 0) {
                                    if (!SearchHeader(reply, dataQ)) {
                                        break;
                                    }
                                    Log.d(TAG, "Header is found! Type = [" + reply.GetType() + "] Payload length = [" + reply.GetLen() + "]");
                                } else if (dataQ.Size() < reply.GetLen()) {
                                    Log.d(TAG, "Data size [" + dataQ.Size() + "] < Payload length [" + reply.GetLen() + "]. Wait for next time.");
                                } else {
                                    if (dataQ.FrontOffset() != 0) {
                                        dataQ.Pop(0);
                                    }
                                    reply.SetData(reply.GetType(), reply.GetLen(), dataQ.GetData(), true);
                                    dataQ.Pop(reply.GetLen());
                                    return true;
                                }
                            }
                            Log.d(TAG, "No packet is processed. RETRY.");
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                            }
                            i++;
                        }
                    }
                    return true;
                }
            }
        }
    }

    static boolean WriteToArrayList(ArrayList<Byte> list, byte[] data, int len) {
        for (int i = 0; i < len; i++) {
            list.add(Byte.valueOf(data[i]));
        }
        return true;
    }

    static boolean WriteToHIDLService(IMDMonitorService connection, MCPInfo cmd, MCPInfo reply) {
        Log.d(TAG, "WriteToHIDLService start");
        if (connection == null || cmd == null) {
            Log.e(TAG, "connection or cmd is null");
            return false;
        } else if (cmd.GetData() == null || cmd.GetLen() == 0 || cmd.GetType() == null) {
            Log.e(TAG, "cmd data/type is null or length is 0.");
            return true;
        } else {
            byte[] GetTypeMagic = GetTypeMagic(cmd.GetType());
            byte[] typeMagic = GetTypeMagic;
            if (GetTypeMagic == null) {
                Log.e(TAG, "Undefined type! type = [" + cmd.GetType().name() + "]. DROP.");
                return true;
            }
            int len = cmd.GetLen();
            if (!IsPayloadLenValid(len)) {
                Log.e(TAG, "Payload length is invalid! len = [" + len + "]. DROP.");
                return true;
            }
            ArrayList<Byte> inputList = new ArrayList<>();
            byte[] bArr = HEADER_MAGIC;
            if (!WriteToArrayList(inputList, bArr, bArr.length)) {
                Log.e(TAG, "MCPHandler::WriteToConnection (HEADER_MAGIC) failed!");
                return false;
            } else if (!WriteToArrayList(inputList, typeMagic, 2)) {
                Log.e(TAG, "MCPHandler::WriteToConnection (TYPE_Magic) failed!");
                return false;
            } else {
                byte[] lenByteArray = MonitorUtils.IntToByteArray(len);
                if (!WriteToArrayList(inputList, lenByteArray, lenByteArray.length)) {
                    Log.e(TAG, "MCPHandler::WriteToConnection (Len) failed!");
                    return false;
                } else if (!WriteToArrayList(inputList, cmd.GetData(), len)) {
                    Log.e(TAG, "MCPHandler::WriteToConnection (Data) failed!");
                    return false;
                } else if (connection != null) {
                    Log.d(TAG, "Write MCP Payload to HIDL connection.");
                    try {
                        ArrayList<Byte> resultList = connection.sendMCPData(inputList);
                        Log.d(TAG, "WriteToHIDLService send done");
                        if (reply == null) {
                            return true;
                        }
                        Log.d(TAG, "WriteToHIDLService wait reply start");
                        reply.Reset();
                        if (resultList == null || resultList.size() <= 0) {
                            Log.e(TAG, "HIDL Return Data size [" + resultList.size() + "] is 0 or null.");
                            return false;
                        }
                        DataQueue dataQ = new DataQueue();
                        dataQ.PushArrayList(resultList);
                        if (!SearchHeader(reply, dataQ)) {
                            Log.e(TAG, "HIDL return data have no MCP header!");
                            return false;
                        }
                        Log.d(TAG, "Header is found! Type = [" + reply.GetType() + "] Payload length = [" + reply.GetLen() + "]");
                        if (dataQ.Size() < reply.GetLen()) {
                            Log.e(TAG, "Data size [" + dataQ.Size() + "] < Payload length [" + reply.GetLen() + "].");
                            return false;
                        }
                        if (dataQ.FrontOffset() != 0) {
                            dataQ.Pop(0);
                        }
                        reply.SetData(reply.GetType(), reply.GetLen(), dataQ.GetData(), true);
                        Log.d(TAG, "WriteToHIDLService wait reply done");
                        return true;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.e(TAG, "MCPHandler::WriteToConnection HIDL call failed with RemoteException exception!");
                        return false;
                    }
                } else {
                    Log.e(TAG, "MCPHandler::WriteToConnection HIDL connection is null!");
                    return false;
                }
            }
        }
    }

    static boolean SearchHeader(MCPInfo mcpInfo, DataQueue dataQ) {
        MCP_TYPE mcp_type = MCP_TYPE.MCP_TYPE_UNDEFINED;
        if (mcpInfo == null || dataQ == null) {
            Log.e(TAG, "mcpInfo is null || dataQ is null.");
            return false;
        }
        while (true) {
            int Size = dataQ.Size();
            byte[] bArr = HEADER_MAGIC;
            if (Size < bArr.length + 2 + 4) {
                Log.d(TAG, "MCPHandler::SearchHeader failed!");
                return false;
            } else if (!IsHeaderMagic(dataQ.GetData(), dataQ.FrontOffset())) {
                Log.d(TAG, "Not magic header. Pop(1)");
                dataQ.Pop(1);
            } else {
                dataQ.Pop(bArr.length);
                MCP_TYPE IsTypeMagic = IsTypeMagic(dataQ.GetData(), dataQ.FrontOffset());
                MCP_TYPE type_r = IsTypeMagic;
                if (IsTypeMagic == null) {
                    Log.d(TAG, "Type magic is invalid!");
                } else {
                    dataQ.Pop(2);
                    int packetLen_r = MonitorUtils.ByteArrayToInt(dataQ.GetData(), dataQ.FrontOffset(), 4);
                    if (!IsPayloadLenValid(packetLen_r)) {
                        Log.d(TAG, "Command length is invalid! len = [" + packetLen_r + "]");
                    } else {
                        dataQ.Pop(4);
                        mcpInfo.SetData(type_r, packetLen_r, (byte[]) null);
                        return true;
                    }
                }
            }
        }
    }

    private static boolean IsHeaderMagic(byte[] buffer, int startOffset) {
        if (buffer == null) {
            return false;
        }
        int length = buffer.length - startOffset;
        byte[] bArr = HEADER_MAGIC;
        if (length < bArr.length) {
            return false;
        }
        return ByteBuffer.wrap(buffer, startOffset, bArr.length).equals(ByteBuffer.wrap(bArr, 0, bArr.length));
    }

    private static MCP_TYPE IsTypeMagic(byte[] data, int startOffset) {
        for (TypeMagic aM_typeMagicTable : m_typeMagicTable) {
            if (data.length - startOffset >= aM_typeMagicTable.m_magic.length && ByteBuffer.wrap(data, startOffset, aM_typeMagicTable.m_magic.length).equals(ByteBuffer.wrap(aM_typeMagicTable.m_magic, 0, aM_typeMagicTable.m_magic.length))) {
                return aM_typeMagicTable.m_type;
            }
        }
        return null;
    }

    private static byte[] GetTypeMagic(MCP_TYPE type) {
        if (type == null) {
            return null;
        }
        int i = 0;
        while (true) {
            TypeMagic[] typeMagicArr = m_typeMagicTable;
            if (i >= typeMagicArr.length) {
                return null;
            }
            if (type.equals(typeMagicArr[i].m_type)) {
                return typeMagicArr[i].m_magic;
            }
            i++;
        }
    }

    private static boolean IsPayloadLenValid(int len) {
        return len <= 131072 && len > 0;
    }

    private static class TypeMagic {
        public byte[] m_magic;
        public MCP_TYPE m_type;

        public TypeMagic(MCP_TYPE type, byte[] magic) {
            this.m_type = type;
            this.m_magic = magic;
        }
    }
}
