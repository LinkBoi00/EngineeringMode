package com.mediatek.engineermode.mnldinterface;

import com.mediatek.socket.base.SocketUtils;
import com.mediatek.socket.base.UdpClient;

public class Debug2MnldInterface {
    public static final int DEBUG_MNLD_NE_MSG = 1;
    public static final int DEBUG_MNLD_RADIO_MSG = 2;
    public static final int DEBUG_REQ_MNLD_MSG = 0;
    public static final int MAX_BUFF_SIZE = 45;
    public static final int PROTOCOL_TYPE = 305;

    public static class Debug2MnldInterfaceSender {
        public boolean debugReqMnldMsg(UdpClient client, DebugReqStatusCategory status) {
            synchronized (client) {
                if (!client.connect()) {
                    return false;
                }
                SocketUtils.BaseBuffer buff = client.getBuff();
                buff.putInt(Debug2MnldInterface.PROTOCOL_TYPE);
                buff.putInt(0);
                buff.putCodable(status);
                boolean _ret = client.write();
                client.close();
                return _ret;
            }
        }

        public boolean debugMnldNeMsg(UdpClient client, boolean enabled) {
            synchronized (client) {
                if (!client.connect()) {
                    return false;
                }
                SocketUtils.BaseBuffer buff = client.getBuff();
                buff.putInt(Debug2MnldInterface.PROTOCOL_TYPE);
                buff.putInt(1);
                buff.putBool(enabled);
                boolean _ret = client.write();
                client.close();
                return _ret;
            }
        }

        public boolean debugMnldRadioMsg(UdpClient client, String value) {
            synchronized (client) {
                if (!client.connect()) {
                    return false;
                }
                SocketUtils.BaseBuffer buff = client.getBuff();
                buff.putInt(Debug2MnldInterface.PROTOCOL_TYPE);
                buff.putInt(2);
                SocketUtils.assertSize(value, 32, 0);
                buff.putString(value);
                boolean _ret = client.write();
                client.close();
                return _ret;
            }
        }
    }

    public static abstract class Debug2MnldInterfaceReceiver implements SocketUtils.ProtocolHandler {
        public abstract void debugMnldNeMsg(boolean z);

        public abstract void debugMnldRadioMsg(String str);

        public abstract void debugReqMnldMsg(DebugReqStatusCategory debugReqStatusCategory);

        public boolean readAndDecode(SocketUtils.UdpServerInterface server) {
            if (!server.read()) {
                return false;
            }
            return decode(server);
        }

        public int getProtocolType() {
            return Debug2MnldInterface.PROTOCOL_TYPE;
        }

        public boolean decode(SocketUtils.UdpServerInterface server) {
            SocketUtils.BaseBuffer buff = server.getBuff();
            buff.setOffset(4);
            switch (buff.getInt()) {
                case 0:
                    debugReqMnldMsg((DebugReqStatusCategory) buff.getCodable(DebugReqStatusCategory._instance));
                    return true;
                case 1:
                    debugMnldNeMsg(buff.getBool());
                    return true;
                case 2:
                    debugMnldRadioMsg(buff.getString());
                    return true;
                default:
                    return false;
            }
        }
    }
}
