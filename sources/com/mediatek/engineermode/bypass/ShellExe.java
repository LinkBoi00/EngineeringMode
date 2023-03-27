package com.mediatek.engineermode.bypass;

import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.emsvr.AFMFunctionCallEx;
import com.mediatek.engineermode.emsvr.FunctionReturn;
import java.io.IOException;

public class ShellExe {
    public static final String ERROR = "ERROR";
    private static final String OPERATION_ERROR_PREFIX = "#$ERROR^&";
    public static final int RESULT_FAIL = -1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "Bypass/ReadFile";
    private static StringBuilder sResultBuilder = new StringBuilder("");

    public static String getOutput() {
        return sResultBuilder.toString();
    }

    public static int readTextFile(int index) throws IOException {
        FunctionReturn funcRet;
        StringBuilder sb = sResultBuilder;
        sb.delete(0, sb.length());
        AFMFunctionCallEx functionCall = new AFMFunctionCallEx();
        if (functionCall.startCallFunctionStringReturn(AFMFunctionCallEx.FUNCTION_EM_READ_USB_RAWBULK_FILE)) {
            functionCall.writeParamNo(1);
            functionCall.writeParamInt(index);
            do {
                funcRet = functionCall.getNextResult();
                if (funcRet.mReturnString != null && funcRet.mReturnString.length() > 0) {
                    sResultBuilder.append(funcRet.mReturnString);
                }
            } while (funcRet.mReturnCode == 1);
            while (true) {
                int len = sResultBuilder.length();
                if (len > 0 && sResultBuilder.charAt(len - 1) == 10) {
                    sResultBuilder.delete(len - 1, len);
                }
            }
            String output = sResultBuilder.toString();
            if (output == null || !output.startsWith(OPERATION_ERROR_PREFIX)) {
                return 0;
            }
            Elog.e(TAG, "error operation:" + output);
            sResultBuilder.delete(0, OPERATION_ERROR_PREFIX.length() + 1);
            return -1;
        }
        Elog.e(TAG, "Function call start fail");
        sResultBuilder.append("ERROR");
        return -1;
    }
}
