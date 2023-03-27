package com.mediatek.engineermode.hqanfc;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NfcCommandHandler {
    private static final int QUEUE_SIZE = 50;
    private static NfcCommandHandler sInstance;
    /* access modifiers changed from: private */
    public BlockingQueue<NfcCommand> mCommandQueue = new ArrayBlockingQueue(50);
    private Thread mConsumerThr;
    private Context mContext;

    public static synchronized NfcCommandHandler getInstance(Context context) {
        NfcCommandHandler nfcCommandHandler;
        synchronized (NfcCommandHandler.class) {
            if (sInstance == null) {
                sInstance = new NfcCommandHandler(context);
            }
            nfcCommandHandler = sInstance;
        }
        return nfcCommandHandler;
    }

    public NfcCommandHandler(Context context) {
        Elog.w(NfcMainPage.TAG, "[NfcCommandHandler]NfcCommandHandler NfcCommandHandler()");
        this.mContext = context;
        Thread thread = new Thread(new Consumer());
        this.mConsumerThr = thread;
        thread.start();
    }

    public void destroy() {
        this.mCommandQueue.clear();
        this.mConsumerThr.interrupt();
    }

    public boolean execute(NfcCommand entity) {
        Elog.w(NfcMainPage.TAG, "[NfcCommandHandler] execute()");
        try {
            this.mCommandQueue.put(entity);
            return true;
        } catch (InterruptedException ex) {
            Elog.w(NfcMainPage.TAG, "[NfcCommandHandler]execute InterruptedException: " + ex.getMessage());
            return false;
        }
    }

    private class Consumer implements Runnable {
        private Consumer() {
        }

        public void run() {
            while (true) {
                try {
                    Elog.w(NfcMainPage.TAG, "[NfcCommandHandler] Consumer take.");
                    if (NfcCommandHandler.this.mCommandQueue == null) {
                        Elog.w(NfcMainPage.TAG, "[NfcCommandHandler] mCommandQueue is null.");
                    } else {
                        NfcCommandHandler.this.processCommand((NfcCommand) NfcCommandHandler.this.mCommandQueue.take());
                    }
                } catch (InterruptedException ex) {
                    Elog.w(NfcMainPage.TAG, "[NfcCommandHandler]Consumer InterruptedException: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void processCommand(NfcCommand receiveData) {
        Intent intent = new Intent();
        intent.setAction(NfcCommand.ACTION_PRE + receiveData.getMessageType());
        ByteBuffer content = receiveData.getMessageContent();
        intent.putExtra(NfcCommand.MESSAGE_CONTENT_KEY, content == null ? null : content.array());
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }
}
