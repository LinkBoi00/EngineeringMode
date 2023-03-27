package com.mediatek.engineermode.hqanfc;

import android.content.Context;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ReceiveThread implements Runnable {
    private Context mContext;
    private DataInputStream mInputStream;
    private boolean mRunning = false;

    public ReceiveThread(DataInputStream is, Context context) {
        this.mContext = context;
        this.mInputStream = is;
    }

    public void setRunning(boolean running) {
        this.mRunning = running;
    }

    public void run() {
        boolean z;
        NfcCommand mainMessage;
        if (this.mInputStream == null) {
            Elog.d(NfcMainPage.TAG, "[ReceiveThread]The dispatcher or stream object is null!");
            return;
        }
        this.mRunning = true;
        NfcCommandHandler commandHandler = NfcCommandHandler.getInstance(this.mContext);
        while (true) {
            z = this.mRunning;
            if (!z) {
                break;
            }
            try {
                byte[] b = new byte[1024];
                this.mInputStream.read(b);
                for (int i = 0; i < b.length; i++) {
                    Elog.d(NfcMainPage.TAG, b[i] + " ");
                }
                Elog.d(NfcMainPage.TAG, "done receive");
                ByteBuffer buffer = ByteBuffer.wrap(b);
                byte[] type = new byte[4];
                buffer.get(type);
                int msgType = NfcCommand.DataConvert.byteToInt(type);
                Elog.d(NfcMainPage.TAG, "[ReceiveThread:info]Recieved data message type is " + msgType);
                if (msgType > 204) {
                    Elog.v(NfcMainPage.TAG, "[ReceiveThread]receive message is not the correct msg and the content: " + new String(b));
                } else {
                    byte[] lenght = new byte[4];
                    buffer.get(lenght);
                    int msgLen = NfcCommand.DataConvert.byteToInt(lenght);
                    Elog.d(NfcMainPage.TAG, "[ReceiveThread:info]Recieved data message lenght is " + msgLen);
                    if (msgLen == 0) {
                        mainMessage = new NfcCommand(msgType, (ByteBuffer) null);
                    } else {
                        byte[] bufferCont = new byte[msgLen];
                        buffer.get(bufferCont);
                        mainMessage = new NfcCommand(msgType, ByteBuffer.wrap(bufferCont));
                    }
                    commandHandler.execute(mainMessage);
                }
            } catch (IOException e) {
                String errorMsg = e.getMessage();
                if (!errorMsg.equals("Try again")) {
                    this.mRunning = false;
                    e.printStackTrace();
                }
                Elog.v(NfcMainPage.TAG, "[ReceiveThread]receive thread IOException: " + errorMsg);
            }
        }
        if (!z) {
            commandHandler.destroy();
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }
}
