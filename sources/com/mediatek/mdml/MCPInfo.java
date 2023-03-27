package com.mediatek.mdml;

class MCPInfo {
    private static final String TAG = "MDML/MCPInfo";
    private byte[] m_data = null;
    private int m_len = 0;
    private MCP_TYPE m_type = MCP_TYPE.MCP_TYPE_UNDEFINED;

    public void finalize() {
        Reset();
    }

    public void Reset() {
        this.m_type = MCP_TYPE.MCP_TYPE_UNDEFINED;
        this.m_len = 0;
        this.m_data = null;
    }

    public MCP_TYPE GetType() {
        return this.m_type;
    }

    public int GetLen() {
        return this.m_len;
    }

    public byte[] GetData() {
        return this.m_data;
    }

    public void SetData(MCP_TYPE type, int len, byte[] data, boolean bCopyData) {
        if (len > 131072) {
            len = 131072;
        }
        Reset();
        if (type != null) {
            this.m_type = type;
        }
        if (len > 0) {
            this.m_len = len;
        }
        if (data == null) {
            this.m_data = null;
        } else if (!bCopyData) {
            this.m_data = data;
        } else if (len == 0) {
            this.m_data = data;
        } else {
            int i = this.m_len;
            byte[] bArr = new byte[i];
            this.m_data = bArr;
            System.arraycopy(data, 0, bArr, 0, i);
        }
    }

    public void SetData(MCP_TYPE type, int len, byte[] data) {
        SetData(type, len, data, false);
    }
}
