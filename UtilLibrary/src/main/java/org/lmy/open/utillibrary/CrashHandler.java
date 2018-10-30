package org.lmy.open.utillibrary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 异常捕获处理类
 *
 * @author lmy
 * @ClassName: CrashHandler
 * @Package: com.mxnavi.busines.core.util
 * @Date: 2018/08/28
 */
public final class CrashHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 单例对象.
     */
    private static CrashHandler sInstance;
    /**
     * 系统默认的UncaughtExceptionHandler.
     */
    private Thread.UncaughtExceptionHandler mHandler;

    /**
     * 私有构造函数.
     */
    private CrashHandler() {
    }

    /**
     * @return 单例对象
     */
    public static CrashHandler getInstance() {
        if (sInstance == null) {
            sInstance = new CrashHandler();
        }
        return sInstance;
    }

    /**
     * 初始化.
     */
    public void init() {
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当异常发生时，捕获异常.
     *
     * @param thread 线程
     * @param ex     异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mHandler != null) {
            mHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (UtilApplication.getInstance().getAbnormalRestartActivity() != null) {
                Intent mStartActivity = new Intent(UtilApplication.getInstance().getContext(), UtilApplication.getInstance().getAbnormalRestartActivity());
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(UtilApplication.getInstance().getContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) UtilApplication.getInstance().getContext().getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0);
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

            }
        }
    }

    /**
     * 自定义异常处理.
     *
     * @param ex 异常
     * @return boolean 是否处理
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        this.saveCrashInfoToFile(ex);
        return false;
    }

    /**
     * 保存异常信息到文件中.
     *
     * @param ex 异常
     * @return String 异常信息
     */
    public String saveCrashInfoToFile(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String info = writer.toString();
        printWriter.close();
        LogUtil.eFullPath("收集错误信息：\n " + info);
        return info;
    }
}
