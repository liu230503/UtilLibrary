package org.lmy.open.utillibrary.imageload;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import org.lmy.open.utillibrary.imageload.base.BaseLoadImageConfigure;

/**********************************************************************
 *
 *
 * @类名 LoadImageHelper
 * @包名 org.lmy.open.utillibrary.imageload
 * @author lmy
 * @创建日期 2018/3/5
 ***********************************************************************/
public final class LoadImageHelper implements ILoadImage {
    /**
     * SD卡路径前缀
     */
    private static final String PREFIX_SD_CARD = "file://";
    /**
     * content provider路径前缀
     */
    private static final String PREFIX_CONTENT_PROVIDER = "content://";

    /**
     * 项目内assets内文件路径前缀
     */
    private static final String PREFIX_ASSETS = "assets://";

    /**
     * 项目内资源文件路径前缀
     */
    private static final String PREFIX_DRAWABLE = "drawable://";
    /**
     * 单例对象
     */
    private static LoadImageHelper sLoadImageProxy = null;
    /**
     * 图片加载实现类
     */
    private ILoadImage mLoaderHelper;

    private LoadImageHelper() {
        mLoaderHelper = new ImageLoaderImpl();
    }

    /**
     * 单例方法
     *
     * @return LoadImageHelper
     */
    public static LoadImageHelper getInstance() {
        if (sLoadImageProxy == null) {
            synchronized (LoadImageHelper.class) {
                if (sLoadImageProxy == null) {
                    sLoadImageProxy = new LoadImageHelper();
                }
            }
        }
        return sLoadImageProxy;
    }

    /**
     * 加载图片
     *
     * @param imageView view
     * @param url       图片地址
     * @param listener  监听器
     */
    public void loadImage(ImageView imageView, String url, Listener listener) {
        loadImage(imageView, url, 0, listener);
    }

    @Override
    public void loadImage(ImageView imageView, String url, int type, Listener listener) {
        mLoaderHelper.loadImage(imageView, url, type, listener);
    }

    @Override
    public void setPauseLoadOnScroll(boolean pauseOnScroll, boolean pauseOnFling) {
        mLoaderHelper.setPauseLoadOnScroll(pauseOnScroll, pauseOnFling);
    }

    @Override
    public void init(Context context) {
        mLoaderHelper.init(context);
    }

    @Override
    public void init(Context context, BaseLoadImageConfigure imageConfigure) {
        mLoaderHelper.init(context, imageConfigure);
    }

    /**
     * 加载图片
     *
     * @param imageView view
     * @param url       图片地址
     */
    public void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, 0);
    }

    /**
     * 加载图片
     *
     * @param imageView view
     * @param url       图片地址
     * @param type      图片类型
     */
    public void loadImage(ImageView imageView, String url, int type) {
        loadImage(imageView, url, type, null);
    }

    /**
     * 将url转换为sd卡路径
     *
     * @param url 文件路径
     * @return imageloader可识别的sd卡路径
     */
    public String getSdCardUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return PREFIX_SD_CARD + url;
    }

    /**
     * 将url转换为content provider路径
     *
     * @param url 文件路径
     * @return imageloader可识别的content provider路径
     */
    public String getContentProviderUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return PREFIX_CONTENT_PROVIDER + url;
    }

    /**
     * 将url转换为项目内assets内文件路径
     *
     * @param url 文件路径
     * @return imageloader可识别的项目内assets内文件路径
     */
    public String getAssetsUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return PREFIX_ASSETS + url;
    }

    /**
     * 将资源id转换为项目内资源路径
     *
     * @param drawableId 资源id
     * @return imageloader可识别的项目内Drawable文件路径
     */
    public String getDrawable(int drawableId) {
        if (drawableId <= 0) {
            return "";
        }
        return PREFIX_DRAWABLE + drawableId;
    }
}
