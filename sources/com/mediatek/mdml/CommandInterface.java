package com.mediatek.mdml;

interface CommandInterface {
    MONITOR_CMD_RESP onCloseSession(long j);

    long onCreateSession();

    MONITOR_CMD_RESP onDisableTrap(long j);

    MONITOR_CMD_RESP onEnableTrap(long j);

    MONITOR_CMD_RESP onSetTrapReceiver(long j, String str);

    MONITOR_CMD_RESP onSubscribeMultiTrap(long j, TRAP_TYPE trap_type, long[] jArr);

    MONITOR_CMD_RESP onSubscribeTrap(long j, TRAP_TYPE trap_type, long j2);

    MONITOR_CMD_RESP onUnsubscribeMultiTrap(long j, TRAP_TYPE trap_type, long[] jArr);

    MONITOR_CMD_RESP onUnsubscribeTrap(long j, TRAP_TYPE trap_type, long j2);
}
