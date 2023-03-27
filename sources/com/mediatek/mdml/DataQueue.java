package com.mediatek.mdml;

import java.util.ArrayList;

class DataQueue {
    private static final String TAG = "MDML/DataQueue";
    private byte[] m_data = new byte[262144];
    private int m_size = 0;
    private int m_unusedSize = 0;

    public void Clear() {
        this.m_size = 0;
        this.m_unusedSize = 0;
    }

    public int Size() {
        return this.m_size;
    }

    public int SpaceSize() {
        return (this.m_data.length - this.m_size) - this.m_unusedSize;
    }

    public int FrontOffset() {
        return this.m_unusedSize;
    }

    public void Pop() {
        Pop(1);
    }

    public byte[] GetData() {
        return this.m_data;
    }

    public void Pop(int size) {
        int i = this.m_size;
        if (i <= size) {
            this.m_size = 0;
            this.m_unusedSize = 0;
            return;
        }
        int i2 = this.m_unusedSize + size;
        this.m_unusedSize = i2;
        int i3 = i - size;
        this.m_size = i3;
        if (size == 0 || i2 + i3 > (this.m_data.length >> 1)) {
            byte[] bArr = this.m_data;
            System.arraycopy(bArr, i2, bArr, 0, i3);
            this.m_unusedSize = 0;
        }
    }

    public boolean Push(byte[] data, int offset, int nLen) {
        if (data == null || offset < 0) {
            return false;
        }
        if (nLen == 0) {
            return true;
        }
        int i = this.m_size;
        int i2 = nLen + i;
        byte[] bArr = this.m_data;
        if (i2 > bArr.length) {
            return false;
        }
        int i3 = this.m_unusedSize;
        if (nLen + i3 + i > bArr.length) {
            System.arraycopy(bArr, i3, bArr, 0, i);
            this.m_unusedSize = 0;
        }
        System.arraycopy(data, offset, this.m_data, this.m_unusedSize + this.m_size, nLen);
        this.m_size += nLen;
        return true;
    }

    public boolean PushArrayList(ArrayList<Byte> list) {
        if (list == null) {
            return false;
        }
        if (list.size() == 0) {
            return true;
        }
        if (list.size() + this.m_size > this.m_data.length) {
            return false;
        }
        int size = list.size();
        int i = this.m_unusedSize;
        int i2 = this.m_size;
        int i3 = size + i + i2;
        byte[] bArr = this.m_data;
        if (i3 > bArr.length) {
            System.arraycopy(bArr, i, bArr, 0, i2);
            this.m_unusedSize = 0;
        }
        for (int i4 = 0; i4 < list.size(); i4++) {
            this.m_data[this.m_unusedSize + this.m_size + i4] = list.get(i4).byteValue();
        }
        this.m_size += list.size();
        return true;
    }

    public int SpaceStartOffset() {
        return this.m_unusedSize + this.m_size;
    }

    public void IncSize(int size) {
        if (size > SpaceSize()) {
            size = SpaceSize();
        }
        this.m_size += size;
    }
}
