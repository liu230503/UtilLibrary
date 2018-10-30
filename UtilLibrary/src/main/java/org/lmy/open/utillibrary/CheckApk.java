package org.lmy.open.utillibrary;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Process;

import java.util.List;

/**
 * @author lmy
 * @ClassName: CheckApk
 * @Package: org.lmy.open.utillibrary
 * @Date: 2018/08/27
 */
public final class CheckApk {
    /**
     * 检测当前模式
     *
     * @param context 上下文
     * @return 是否为debug模式
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检测是否在主进程
     *
     * @param context 上下文
     * @return 结果
     */
    public static boolean isMainProcess(Context context) {
        String pkgName = context.getPackageName();
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processesList = am.getRunningAppProcesses();
        if (null != processesList) {
            for (ActivityManager.RunningAppProcessInfo info : processesList) {
                if (null != info && info.pid == pid) {
                    return pkgName.equals(info.processName);
                }
            }
        }

        return false;
    }
}
