package org.lmy.open.utillibrary.imageload;

import android.content.Context;
import android.widget.ImageView;

/**********************************************************************
 *
 *
 * @类名 LoadImageProxy
 * @包名 org.lmy.open.utillibrary.imageload
 * @author lmy
 * @创建日期 2018/3/5
 ***********************************************************************/
public final class LoadImageProxy implements ILoadImage {
    /**
     * 单例对象
     */
    private static LoadImageProxy sLoadImageProxy = null;
    /**
     * 图片加载实现类
     */
    private ImageLoaderHelper mLoaderHelper;

    private LoadImageProxy() {
        mLoaderHelper = new ImageLoaderHelper();
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        mLoaderHelper.init(context);
    }

    /**
     * 单例方法
     *
     * @return LoadImageProxy
     */
    public static LoadImageProxy getInstance() {
        if (sLoadImageProxy == null) {
            synchronized (LoadImageProxy.class) {
                if (sLoadImageProxy == null) {
                    sLoadImageProxy = new LoadImageProxy();
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
     * @param type     图片类型
     */
    public void loadImage(ImageView imageView, String url, int type) {
        loadImage(imageView, url, type, null);
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

    /**
     * 加载图片
     *
     * @param imageView view
     * @param url       图片地址
     */
    public void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, 0);
    }

    @Override
    public void loadImage(ImageView imageView, String url, int type, Listener listener) {
        mLoaderHelper.loadImage(imageView, url, type, listener);
    }
}
