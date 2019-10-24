package org.lmy.open.utillibrary;

import android.app.Application;
import android.content.Context;

import org.lmy.open.utillibrary.imageload.LoadImageHelper;
import org.lmy.open.utillibrary.path.PathUtil;

/**********************************************************************
 *
 *
 * @类名 UtilApplication
 * @包名 org.lmy.open.utillibrary
 * @author lmy
 * @创建日期 2018/3/5
 ***********************************************************************/
public abstract class UtilApplication extends Application {
    /**
     * TAG
     */
    private static final String TAG = UtilApplication.class.getName();
    /**
     * 单例对象
     */
    private static UtilApplication sInstance;
    /**
     * 上下文
     */
    private Context mContext;

    public static UtilApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        if (CheckApk.isMainProcess(mContext)) {
            initForMainProcess(mContext);
        } else {
            initForOtherProcess(mContext);
        }
        // 此处只能打印debug级别log 其他级别log有可能会写入文件中 而此时LogUtil还未初始化会导致异常
        LogUtil.d("================= onCreate UtilApplication =================");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.i("================= onTerminate NaviApplication =================");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.i("================= onLowMemory NaviApplication =================");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.d("================= onTrimMemory :" + level + " in NaviApplication =================");
    }

    /**
     * 初始化主线程内容
     *
     * @param context 上下文
     */
    protected void initForMainProcess(Context context) {
        PathUtil.getInstance().init(context);
        PreferenceUtil.getInstance().init(context);
        LoadImageHelper.getInstance().init(context);
        LogUtil.enableLog2File(true);
        LogUtil.enableLog2Logcat(true);
        LogUtil.init("Log");
        CrashHandler.getInstance().init();
    }

    /**
     * 初始化其他线程内容
     *
     * @param context 上下文
     */
    protected void initForOtherProcess(Context context) {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = base;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 获取异常重启的Activity
     *
     * @return class
     */
    public abstract Class getAbnormalRestartActivity();

}
