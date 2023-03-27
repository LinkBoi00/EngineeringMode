package com.mediatek.engineermode.mnldinterface;

import com.mediatek.socket.base.SocketUtils;

public enum DebugReqStatusCategory implements SocketUtils.Codable {
    stopDebug,
    startDebug,
    end;
    
    public static final DebugReqStatusCategory _instance = null;

    static {
        DebugReqStatusCategory debugReqStatusCategory;
        _instance = debugReqStatusCategory;
    }

    /* renamed from: com.mediatek.engineermode.mnldinterface.DebugReqStatusCategory$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mediatek$engineermode$mnldinterface$DebugReqStatusCategory = null;

        static {
            int[] iArr = new int[DebugReqStatusCategory.values().length];
            $SwitchMap$com$mediatek$engineermode$mnldinterface$DebugReqStatusCategory = iArr;
            try {
                iArr[DebugReqStatusCategory.stopDebug.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$mnldinterface$DebugReqStatusCategory[DebugReqStatusCategory.startDebug.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mediatek$engineermode$mnldinterface$DebugReqStatusCategory[DebugReqStatusCategory.end.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public void encode(SocketUtils.BaseBuffer buff) {
        switch (AnonymousClass1.$SwitchMap$com$mediatek$engineermode$mnldinterface$DebugReqStatusCategory[ordinal()]) {
            case 1:
                buff.putInt(0);
                return;
            case 2:
                buff.putInt(1);
                return;
            case 3:
                buff.putInt(Integer.MAX_VALUE);
                return;
            default:
                return;
        }
    }

    public DebugReqStatusCategory decode(SocketUtils.BaseBuffer buff) {
        switch (buff.getInt()) {
            case 0:
                return stopDebug;
            case 1:
                return startDebug;
            case Integer.MAX_VALUE:
                return end;
            default:
                return null;
        }
    }

    public DebugReqStatusCategory[] getArray(SocketUtils.Codable[] data) {
        DebugReqStatusCategory[] _out = new DebugReqStatusCategory[data.length];
        for (int _i = 0; _i < data.length; _i++) {
            _out[_i] = data[_i];
        }
        return _out;
    }
}
