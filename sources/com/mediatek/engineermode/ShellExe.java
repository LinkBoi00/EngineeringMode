package com.mediatek.engineermode;

import com.mediatek.engineermode.emsvr.AFMFunctionCallEx;
import com.mediatek.engineermode.emsvr.FunctionReturn;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ShellExe {
    public static final String ERROR = "ERROR";
    private static final String OPERATION_ERROR_PREFIX = "#$ERROR^&";
    public static final int RESULT_EXCEPTION = -2;
    public static final int RESULT_FAIL = -1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "ShellExe";
    private static StringBuilder sResultBuilder = new StringBuilder("");

    public static String getOutput() {
        return sResultBuilder.toString();
    }

    public static int execCommand(String command) throws IOException {
        return execCommand(new String[]{"sh", "-c", command});
    }

    public static int execCommand(String[] command) throws IOException {
        return execCommandOnServer(command);
    }

    public static int execCommand(String command, boolean execOnLocal) throws IOException {
        return execCommand(new String[]{"sh", "-c", command}, execOnLocal);
    }

    private static int execCommand(String[] command, boolean execOnLocal) throws IOException {
        if (execOnLocal) {
            return execCommandLocal(command);
        }
        return execCommandOnServer(command);
    }

    private static int execCommandLocal(String[] command) throws IOException {
        int result;
        StringBuilder sb;
        Process proc = Runtime.getRuntime().exec(command);
        BufferedReader bufferedReader = null;
        StringBuilder sb2 = sResultBuilder;
        sb2.delete(0, sb2.length());
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(proc.getInputStream(), Charset.defaultCharset()));
            if (proc.waitFor() == 0) {
                String line = bufferedReader2.readLine();
                if (line != null) {
                    sResultBuilder.append(line);
                    while (true) {
                        String line2 = bufferedReader2.readLine();
                        if (line2 == null) {
                            break;
                        }
                        sResultBuilder.append(10);
                        sResultBuilder.append(line2);
                    }
                }
                result = 0;
            } else {
                Elog.e(TAG, "exit value = " + proc.exitValue());
                sResultBuilder.append("ERROR");
                result = -1;
            }
            try {
                bufferedReader2.close();
            } catch (IOException e) {
                e = e;
                sb = new StringBuilder();
            }
        } catch (InterruptedException e2) {
            Elog.e(TAG, "exe shell command InterruptedException: " + e2.getMessage());
            sResultBuilder.append("ERROR");
            result = -2;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e3) {
                    e = e3;
                    sb = new StringBuilder();
                }
            }
        } catch (Throwable th) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    Elog.e(TAG, "close reader in finally block exception: " + e4.getMessage());
                }
            }
            throw th;
        }
        return result;
        sb.append("close reader in finally block exception: ");
        sb.append(e.getMessage());
        Elog.e(TAG, sb.toString());
        return result;
    }

    private static int execCommandOnServer(String[] command) throws IOException {
        FunctionReturn funcRet;
        if (command == null || command.length == 0) {
            throw new IllegalArgumentException("Invalid shell command to execute");
        }
        StringBuilder cmdBuilder = new StringBuilder();
        int i = 0;
        if ("sh".equals(command[0]) || command[0].endsWith("/sh")) {
            if (command.length >= 3) {
                i = 2;
            } else {
                throw new IllegalArgumentException("invalid or unknown cmd to execute");
            }
        }
        while (i < command.length) {
            cmdBuilder.append(command[i]);
            if (i < command.length - 1) {
                cmdBuilder.append(" ");
            }
            i++;
        }
        String cmd = cmdBuilder.toString().trim();
        StringBuilder sb = sResultBuilder;
        sb.delete(0, sb.length());
        AFMFunctionCallEx functionCall = new AFMFunctionCallEx();
        if (functionCall.startCallFunctionStringReturn(AFMFunctionCallEx.FUNCTION_EM_SHELL_CMD_EXECUTION)) {
            functionCall.writeParamNo(1);
            functionCall.writeParamString(cmd);
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
