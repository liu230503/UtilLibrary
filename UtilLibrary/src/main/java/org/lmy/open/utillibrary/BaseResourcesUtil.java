package org.lmy.open.utillibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2019/11/11
 * @description: xxxx
 *
 *********************************************************/
public abstract class BaseResourcesUtil {
    /**
     * 资源类型
     */
    public static final String DEFAULT_DRAWABLE = "drawable";

    public static final String LOG_TAG = "Resource";

    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 资源管理器
     */
    protected Resources mResources;

    /**
     * 根据资源id 获取整数
     *
     * @param resId 资源id
     * @return 结果
     */
    public int getInteger(int resId) {
        int value = -1;
        if (null == mResources) {
            return value;
        }
        try {
            value = mResources.getInteger(resId);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return value;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    protected abstract void init(Context context);

    /**
     * 根据资源ID获取字符串
     *
     * @param resId 资源id
     * @return 字符串
     */
    public String getString(int resId) {
        String retStr = "";
        if (null == mResources) {
            return retStr;
        }
        try {
            retStr = mResources.getString(resId);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return retStr;
    }

    /**
     * 获取单复数形式的String字符串
     *
     * @param resId 资源id
     * @param size  个数
     * @return 字符串资源
     */
    public String getQuantityString(int resId, int size) {
        String retStr = "";
        if (null == mResources) {
            return retStr;
        }
        try {
            retStr = mResources.getQuantityString(resId, size);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return retStr;
    }

    /**
     * 获取单复数形式的String字符串
     *
     * @param resId 资源id
     * @param size  个数
     * @param args  格式化内容
     * @return 字符串资源
     */
    public String getQuantityString(int resId, int size, Object... args) {
        String retStr = "";
        if (null == mResources) {
            return retStr;
        }
        try {
            retStr = mResources.getQuantityString(resId, size, args);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return retStr;
    }

    /**
     * 自动填充获取到的字符串
     *
     * @param resId  资源id
     * @param format 填充内容
     * @return 结果
     */
    public String getString(int resId, Object... format) {
        String retStr = "";
        if (mResources == null) {
            return retStr;
        }
        try {
            retStr = mResources.getString(resId, format);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return retStr;
    }

    /**
     * 根据资源id 获取字符串数组
     *
     * @param resId 资源id
     * @return 结果
     */
    public String[] getStringArray(int resId) {
        String[] retArray = new String[]{""};
        if (null == mResources) {
            return retArray;
        }
        try {
            retArray = mResources.getStringArray(resId);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return retArray;
    }

    /**
     * 根据资源ID获取状态颜色集合
     *
     * @param resId 资源id
     * @return 结果
     */
    public ColorStateList getColorStateList(int resId) {
        ColorStateList colorStateList = new ColorStateList(new int[1][1], new int[]{1});
        if (null == mResources) {
            return colorStateList;
        }
        try {
            colorStateList = mResources.getColorStateList(resId);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return colorStateList;
    }

    /**
     * 根据资源id获取颜色
     *
     * @param resId 资源id
     * @return 结果
     */
    public int getColor(int resId) {
        int value = -1;
        if (null == mResources) {
            return value;
        }
        try {
            value = mResources.getColor(resId);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return value;
    }

    /**
     * 根据资源ID获取可拉伸资源
     *
     * @param resId 资源id
     * @return 结果
     */
    public Drawable getDrawable(int resId) {
        Drawable drawable = null;
        if (null == mResources) {
            return drawable;
        }
        try {
            if (android.os.Build.VERSION.SDK_INT < LOLLIPOP_MR1) {
                return mResources.getDrawable(resId);
            } else {
                return mResources.getDrawable(resId, null);
            }
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return drawable;
    }

    /**
     * 根据资源id获取像素大小
     *
     * @param resId 资源id
     * @return 结果
     */
    public float getDimension(int resId) {
        float retDim = 0F;
        if (mResources == null) {
            return retDim;
        }
        try {
            retDim = mResources.getDimension(resId);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return retDim;
    }

    /**
     * 根据资源id获取布尔值
     *
     * @param resId 资源id
     * @return 结果
     */
    public boolean getBoolean(int resId) {
        boolean retBol = false;
        if (null == mResources) {
            return retBol;
        }
        try {
            retBol = mResources.getBoolean(resId);
        } catch (Resources.NotFoundException e) {
            LogUtil.d(LOG_TAG, e.toString());
        }
        return retBol;
    }

    /**
     * 获取当前上下文资源管理器
     *
     * @return 资源管理器
     */
    public Resources getContextResources() {
        if (null == mResources) {
            mResources = mContext.getResources();
        }
        return mResources;
    }

    /**
     * 填充布局
     *
     * @param resId          xml布局id
     * @param viewGroup      父布局
     * @param isAttachedRoot 是否添加到父布局中
     * @return view
     */
    public View inflate(int resId, ViewGroup viewGroup, boolean isAttachedRoot) {
        return LayoutInflater.from(mContext).inflate(resId, viewGroup, isAttachedRoot);
    }

    /**
     * 加载动画
     *
     * @param resId 资源id
     * @return 动画资源
     */
    public Animation loadAnimation(int resId) {
        return AnimationUtils.loadAnimation(mContext, resId);
    }
}