package com.mediatek.engineermode.emsvr;

import com.mediatek.engineermode.Elog;
import java.io.EOFException;
import java.io.IOException;

public class AFMFunctionCallEx {
    private static final String ERROR = "ERROR ";
    public static final int FUNCTION_EM_MD_LOG_FILTER_GEN = 10001;
    public static final int FUNCTION_EM_READ_USB_RAWBULK_FILE = 50001;
    public static final int FUNCTION_EM_RSC_WRITE = 20001;
    public static final int FUNCTION_EM_SHELL_CMD_EXECUTION = 80001;
    public static final int RESULT_CONTINUE = 1;
    public static final int RESULT_FIN = 0;
    public static final int RESULT_IO_ERR = -1;
    private static final String TAG = "EmSvr";
    private Client mSocket = null;

    public boolean startCallFunctionStringReturn(int functionId) {
        Client client = new Client();
        this.mSocket = client;
        client.startClient();
        try {
            this.mSocket.writeFunctionNo(String.valueOf(functionId));
            return true;
        } catch (IOException e) {
            Elog.e(TAG, "StartCallFunctionStringReturnEXP " + e.getMessage());
            return false;
        }
    }

    public boolean writeParamNo(int number) {
        try {
            this.mSocket.writeParamNo(number);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeParamInt(int param) {
        try {
            this.mSocket.writeParamInt(param);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeParamString(String string) {
        try {
            this.mSocket.writeParamString(string);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public FunctionReturn getNextResult() {
        FunctionReturn ret = new FunctionReturn();
        try {
            ret.mReturnString = this.mSocket.read();
            if (ret.mReturnString.isEmpty()) {
                ret.mReturnCode = 0;
                endCallFunction();
            } else {
                ret.mReturnCode = 1;
            }
        } catch (EOFException e) {
            endCallFunction();
            ret.mReturnCode = 0;
            ret.mReturnString = "";
        } catch (IOException e2) {
            endCallFunction();
            ret.mReturnCode = -1;
            ret.mReturnString = ERROR + e2.getMessage();
        }
        return ret;
    }

    private void endCallFunction() {
        this.mSocket.stopClient();
    }
}
