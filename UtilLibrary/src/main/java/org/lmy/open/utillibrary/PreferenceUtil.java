package org.lmy.open.utillibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**********************************************************************
 *
 *
 * @类名 PreferenceUtil
 * @包名 org.lmy.open.utillibrary
 * @author lmy
 * @创建日期 2018/2/27
 ***********************************************************************/
public final class PreferenceUtil {
    /**
     * 配置存储
     */
    private static SharedPreferences sPreference;
    /**
     * 单例对象
     */
    private static PreferenceUtil sPreferenceUtil = null;
    /**
     * 编辑器
     */
    private Editor mEditor;

    private PreferenceUtil() {
    }

    /**
     * 单例方法
     *
     * @return AnimationsFactory
     */
    public static PreferenceUtil getInstance() {
        if (null == sPreferenceUtil) {
            synchronized (PreferenceUtil.class) {
                if (null == sPreferenceUtil) {
                    sPreferenceUtil = new PreferenceUtil();
                }
            }
        }
        return sPreferenceUtil;
    }

    public static SharedPreferences getPreference() {
        return sPreference;
    }

    public void init(Context context) {
        sPreference = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = sPreference.edit();
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public String getString(String key) {
        return sPreference.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return sPreference.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean value) {
        return sPreference.getBoolean(key, value);
    }

    public void putLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public long getLong(String key) {
        return sPreference.getLong(key, 0L);
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public int getInt(String key) {
        return sPreference.getInt(key, 0);
    }
}
