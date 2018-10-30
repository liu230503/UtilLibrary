package org.lmy.open.utillibrary;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import org.lmy.open.utillibrary.path.PathUtil;

/**********************************************************************
 *
 *
 * @类名 LogUtil
 * @包名 org.lmy.open.LogUtil
 * @author lmy
 * @创建日期 2018/2/28
 ***********************************************************************/
public final class LogUtil {
    /**
     * LOG配置信息
     */
    public static final String LOG_ENEABLE = "logconfig.enable";
    /**
     * 打印文本日志信息
     */
    public static final String LOG_PRINT_FILE = "logconfig.printfile";
    /**
     * 标签
     */
    private static final String TAG = "mxNavi-log";
    /**
     * 分隔符
     */
    private static final String SPLIT = "====";
    /**
     * 分隔符
     */
    private static final String SPLIT_ARROW = "---->>>>";
    /**
     * debug级别LOG
     */
    private static final int LOG_LEVEL_DEBUG = 1;
    /**
     * info级别LOG
     */
    private static final int LOG_LEVEL_INFO = 2;
    /**
     * warn级别LOG
     */
    private static final int LOG_LEVEL_WARN = 3;
    /**
     * error级别LOG
     */
    private static final int LOG_LEVEL_ERROR = 4;
    /**
     * 全栈级别LOG
     */
    private static final int LOG_LEVEL_FULL_PAT = 5;
    /**
     * 保存LOG到文件消息
     */
    private static final int MESSAGE_SAVE_LOG = 1001;
    /**
     * 清理过期LOG消息
     */
    private static final int MESSAGE_CLEAN_OLD_LOG = 1002;
    /**
     * 最大存储天数
     */
    private static final int MAX_LOGIN_FILE_COUNT = 7;
    /**
     * 输出log到logcat
     */
    private static boolean sIsPrintToLogcat = true;
    /**
     * 输出log到文件
     */
    private static boolean sIsPrintToFile = false;
    /**
     * 子线程
     */
    private static HandlerThread sHandlerThread;
    /**
     * 任务处理器
     */
    private static Handler sHandler;

    /**
     * 是否可以使用LOG
     *
     * @param isEnable 状态
     */
    public static void enableLog2Logcat(boolean isEnable) {
        PreferenceUtil.getInstance(UtilApplication.getInstance().getContext()).putBoolean(LOG_ENEABLE, isEnable);
        sIsPrintToLogcat = isEnable;
    }

    /**
     * 是否允许打印LOG到文件
     *
     * @param isEnable 状态
     */
    public static void enableLog2File(boolean isEnable) {
        PreferenceUtil.getInstance(UtilApplication.getInstance().getContext()).putBoolean(LOG_PRINT_FILE, isEnable);
        sIsPrintToFile = isEnable;
    }

    /**
     * 打印debug级别LOG
     *
     * @param msg 内容
     */
    public static void d(String msg) {
        d(null, msg);
    }

    /**
     * 打印debug级别LOG
     *
     * @param tag 标签
     * @param msg 内容
     */
    public static void d(String tag, String msg) {
        log(LOG_LEVEL_DEBUG, tag, msg);
    }

    /**
     * 输出LOG
     *
     * @param level 级别
     * @param tag   标签
     * @param msg   内容
     */
    private static synchronized void log(int level, String tag, String msg) {
        if (!sIsPrintToLogcat) {
            return;
        }
        tag = TextUtils.isEmpty(tag) ? TAG : tag;
        if (level != LOG_LEVEL_FULL_PAT) {
            StackTraceElement[] e = new Throwable().getStackTrace();
            for (StackTraceElement element : e) {
                if (TextUtils.equals(element.getFileName(), "LogUtil.java")) {
                    continue;
                }
                msg = format(element, msg, tag);
                break;
            }
        }
        switch (level) {
            case LOG_LEVEL_DEBUG:
                Log.d(tag, msg);
                break;
            case LOG_LEVEL_INFO:
                Log.i(tag, msg);
                break;
            case LOG_LEVEL_WARN:
                Log.w(tag, msg);
                break;
            case LOG_LEVEL_ERROR:
            case LOG_LEVEL_FULL_PAT:
                Log.e(tag, msg);
                break;
            default:
                break;
        }

        if (level == LOG_LEVEL_DEBUG) {
            return;
        }
        if (!sIsPrintToFile) {
            return;
        }
        Message message = new Message();
        message.what = MESSAGE_SAVE_LOG;
        message.obj = msg;
        sHandler.sendMessage(message);
    }

    /**
     * 格式化Log
     *
     * @param e   StackTraceElement
     * @param log log内容
     * @param tag 标签
     * @return 格式化结果
     */
    private static String format(StackTraceElement e, String log, String tag) {
        if (log == null) {
            log = "";
        }
        if (e == null) {
            return log;
        }
        StringBuffer stringBuffer = new StringBuffer(TAG);
        stringBuffer.append(SPLIT);
        stringBuffer.append(tag);
        stringBuffer.append(SPLIT);
        stringBuffer.append("[");
        stringBuffer.append(e.getFileName());
        stringBuffer.append("#");
        stringBuffer.append(e.getLineNumber());
        stringBuffer.append("#");
        stringBuffer.append(e.getMethodName());
        stringBuffer.append("]");
        stringBuffer.append(SPLIT);
        stringBuffer.append(log);
        return stringBuffer.toString();
    }

    /**
     * 初始化LOG工具
     */
    public static void init() {
        sIsPrintToLogcat = enableLog2Logcat();
        sIsPrintToFile = enableLog2File();
        sHandlerThread = new HandlerThread(LogUtil.class.getName());
        sHandlerThread.start();
        sHandler = new Handler(sHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_SAVE_LOG:
                        FileUtil.saveLog((String) msg.obj);
                        break;
                    case MESSAGE_CLEAN_OLD_LOG:
                        FileUtil.deleteOldFiles(PathUtil.getInstance().getLogPath(), MAX_LOGIN_FILE_COUNT);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        sHandler.sendEmptyMessage(MESSAGE_CLEAN_OLD_LOG);
    }

    /**
     * 获取是否支持将Log打印至Logcat
     *
     * @return 状态
     */
    public static boolean enableLog2Logcat() {
        return PreferenceUtil.getInstance(UtilApplication.getInstance().getContext()).getBoolean(LOG_ENEABLE, true);
    }

    /**
     * 获取是否支持将Log打印到文件
     *
     * @return 状态
     */
    public static boolean enableLog2File() {
        return PreferenceUtil.getInstance(UtilApplication.getInstance().getContext()).getBoolean(LOG_PRINT_FILE, true);
    }

    /**
     * 打印info级别LOG
     *
     * @param msg 内容
     */
    public static void i(String msg) {
        i(null, msg);
    }

    /**
     * 打印info级别LOG
     *
     * @param tag 标签
     * @param msg 内容
     */
    public static void i(String tag, String msg) {
        log(LOG_LEVEL_INFO, null, msg);
    }

    /**
     * 打印warn级别LOG
     *
     * @param msg 内容
     */
    public static void w(String msg) {
        w(null, msg);
    }

    /**
     * 打印warn级别LOG
     *
     * @param tag 标签
     * @param msg 内容
     */
    public static void w(String tag, String msg) {
        log(LOG_LEVEL_WARN, null, msg);
    }

    /**
     * 打印error级别LOG
     *
     * @param msg 内容
     */
    public static void e(String msg) {
        e(null, msg);
    }

    /**
     * 打印error级别LOG
     *
     * @param tag 标签
     * @param msg 内容
     */
    public static void e(String tag, String msg) {
        log(LOG_LEVEL_ERROR, null, msg);
    }

    /**
     * 全路径显示调用栈
     *
     * @param msg log内容
     */
    public static void eFullPath(String msg) {
        String result;
        StackTraceElement[] e = new Throwable().getStackTrace();
        if (e.length >= 1) {
            StringBuffer stringBuffer = new StringBuffer(TAG);
            for (int i = e.length - 1; i >= 0; i--) {
                StackTraceElement stackTraceElement = e[i];
                if (TextUtils.equals(stackTraceElement.getFileName(), "LogUtil.java")) {
                    continue;
                }
                stringBuffer.append("\n");
                stringBuffer.append(SPLIT_ARROW);
                stringBuffer.append("[");
                stringBuffer.append(stackTraceElement.getFileName());
                stringBuffer.append("#");
                stringBuffer.append(stackTraceElement.getLineNumber());
                stringBuffer.append("#");
                stringBuffer.append(stackTraceElement.getMethodName());
                stringBuffer.append("]");
            }
            stringBuffer.append(SPLIT);
            result = stringBuffer.toString() + msg;
            log(LOG_LEVEL_FULL_PAT, null, result);
        }
    }
}
