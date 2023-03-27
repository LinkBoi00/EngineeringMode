package com.mediatek.mdml;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class Util {
    Util() {
    }

    static byte[] getValue(byte[] buffer, int startPos, OffsetAndSize offsetAndSize) {
        int size;
        if (buffer == null || (size = offsetAndSize.size) == 0) {
            return null;
        }
        if (size == -1) {
            size = (buffer.length - startPos) - offsetAndSize.offset;
        }
        if (size <= 0 || buffer.length - startPos < offsetAndSize.offset + size) {
            return null;
        }
        byte[] b = new byte[size];
        System.arraycopy(buffer, offsetAndSize.offset + startPos, b, 0, size);
        return b;
    }

    static int getIntValue(byte[] buffer, int startPos, OffsetAndSize offsetAndSize) {
        byte[] byteArray = getValue(buffer, startPos, offsetAndSize);
        if (byteArray == null) {
            return -1;
        }
        switch (byteArray.length) {
            case 1:
                return byteArray[0];
            case 2:
                return ByteBuffer.wrap(byteArray).order(ByteOrder.LITTLE_ENDIAN).getShort();
            case 3:
                return byteArray[0] + (byteArray[1] << 8) + (byteArray[2] << 16);
            case 4:
                return ByteBuffer.wrap(byteArray).order(ByteOrder.LITTLE_ENDIAN).getInt();
            default:
                return -1;
        }
    }
}
