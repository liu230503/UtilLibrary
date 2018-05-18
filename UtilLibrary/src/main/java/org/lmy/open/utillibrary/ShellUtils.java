/**********************************************************************
 * Company: 广州蛙鸣智能科技有限公司.
 * Copyright: Copyright (c) 2016
 ***********************************************************************/
package org.lmy.open.utillibrary;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**********************************************************************
 * shell实用工具类
 *
 * @类名 ToastUtil
 * @包名 org.lmy.open.utillibrary
 * @author lmy
 * @创建日期 2018/3/27
 ***********************************************************************/

public final class ShellUtils {

    /**
     * su命令
     */
    public static final String COMMAND_SU = "su";

    /**
     * sh命令
     */
    public static final String COMMAND_SH = "sh";

    /**
     * exit命令
     */
    public static final String COMMAND_EXIT = "exit\n";

    /**
     * 回车命令
     */
    public static final String COMMAND_LINE_END = "\n";

    private ShellUtils() {
        throw new AssertionError();
    }

    /**
     * 检查是否有root权限
     *
     * @return true:有,false:没有
     */
    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).mResult == 0;
    }

    /**
     * 执行shell命令,默认返回结果msg
     *
     * @param command command
     * @param isRoot  是否需要root权限
     * @return 结果msg
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, true);
    }

    /**
     * 执行shell命令,默认返回结果msg
     *
     * @param commands command list
     * @param isRoot   是否需要root权限
     * @return 结果msg
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, true);
    }

    /**
     * 执行shell命令,默认返回结果msg
     *
     * @param commands command array
     * @param isRoot   是否需要root权限
     * @return 结果msg
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    /**
     * 执行shell命令
     *
     * @param command         command
     * @param isRoot          是否需要root权限
     * @param isNeedResultMsg 是否需要返回结果
     * @return 结果msg
     */
    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
    }

    /**
     * 执行shell命令
     *
     * @param commands        command list
     * @param isRoot          是否需要root权限
     * @param isNeedResultMsg 是否需要返回结果msg
     * @return 结果msg
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, isNeedResultMsg);
    }

    /**
     * 执行shell命令
     *
     * @param commands        command array
     * @param isRoot          是否需要root权限
     * @param isNeedResultMsg 是否需要返回结果msg
     * @return 结果msg
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }

                // donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();
            result = process.waitFor();
            // get command result
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
                : errorMsg.toString());
    }

    /**********************************************************************
     * Shell命令执行结果.
     *
     * @author sundi
     * @类名 CommandResult
     * @类指责描述 内部类, command执行结果
     * @创建日期 16/5/17
     ***********************************************************************/
    public static class CommandResult {

        /**
         * 命令的执行结果
         **/
        private int mResult;
        /**
         * 成功的msg
         **/
        private String mSuccessMsg;
        /**
         * 失败的msg
         **/
        private String mErrorMsg;


        /**
         * 获取结果
         *
         * @return 执行结果
         */
        public int getResult() {
            return mResult;
        }

        /**
         * 获取成功msg
         *
         * @return 成功msg
         */
        public String getSuccessMsg() {
            return mSuccessMsg;
        }

        /**
         * 获取失败msg
         *
         * @return 失败msg
         */
        public String getErrorMsg() {
            return mErrorMsg;
        }

        /**
         * 构造函数
         *
         * @param result 执行结果
         */
        public CommandResult(int result) {
            this.mResult = result;
        }

        /**
         * 构造函数
         *
         * @param result     执行结果
         * @param successMsg 成功msg
         * @param errorMsg   失败msg
         */
        public CommandResult(int result, String successMsg, String errorMsg) {
            this.mResult = result;
            this.mSuccessMsg = successMsg;
            this.mErrorMsg = errorMsg;
        }
    }
}