package org.lmy.open.utillibrary;

import android.app.Activity;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.Stack;

/**********************************************************************
 *
 *
 * @类名 ActivityManager
 * @包名 org.lmy.open.utillibrary
 * @author lmy
 * @创建日期 2018/2/28
 ***********************************************************************/
public class ActivityManager {
    private static ActivityManager sInstance;
    private final Stack<Activity> mActivityStack;

    public ActivityManager() {
        mActivityStack = new Stack<>();
    }

    public static ActivityManager getInstance() {
        if (sInstance == null) {
            synchronized (ActivityManager.class) {
                if (sInstance == null) {
                    sInstance = new ActivityManager();
                }
            }
        }
        return sInstance;
    }

    public Activity getCurrentActivity() {
        if (null == mActivityStack || mActivityStack.isEmpty()) {
            return null;
        }
        Activity activity = mActivityStack.peek();
        if (activity.isDestroyed()) {
            popActivity(activity);
            return getCurrentActivity();
        }
        return activity;

    }

    public boolean isCurrentActivity(String className) {
        if (TextUtils.isEmpty(className)) {
            return false;
        }
        if (null == mActivityStack || mActivityStack.isEmpty()) {
            return false;
        }
        Activity activity = mActivityStack.peek();
        if (null == activity) {
            return false;
        }
        return TextUtils.equals(className, activity.getClass().getName());
    }

    public void pushActivity(Activity activity) {
        mActivityStack.push(activity);
    }

    public void popActivity() {
        if (null == mActivityStack || mActivityStack.isEmpty()) {
            return;
        }
        popActivity(mActivityStack.peek());
    }

    public void popActivity(Activity activity) {
        if (null == activity) {
            return;
        }
        activity.finish();
        mActivityStack.remove(activity);
        activity = null;
    }

    public void popActivity(String className) {
        if (TextUtils.isEmpty(className)) {
            return;
        }
        if (null == mActivityStack || mActivityStack.isEmpty()) {
            return;
        }
        Iterator var2 = mActivityStack.iterator();
        while (var2.hasNext()) {
            Activity activity = (Activity) var2.next();
            if (null == activity) {
                continue;
            }
            if (TextUtils.equals(className, activity.getClass().getName())) {
                popActivity(activity);
                return;
            }
        }
    }

    public void popAllActivity() {
        while (true) {
            Activity activity = this.getCurrentActivity();
            if (null == activity) {
                return;
            }
            popActivity(activity);
        }
    }

    public void popAllActivityExceptOne(Class clazz) {
        if (null == clazz) {
            return;
        }
        while (true) {
            Activity activity = this.getCurrentActivity();
            if (null == activity) {
                continue;
            }
            if (clazz.equals(activity.getClass())) {
                continue;
            }
            popActivity(activity);
        }
    }
}

