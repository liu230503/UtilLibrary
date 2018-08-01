package org.lmy.open.utillibrary.imageload;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author lmy
 * @ClassName: CustomImageLoader
 * @Package: org.lmy.open.utillibrary.imageload
 * @Date: 2018/08/01
 */
final class CustomImageLoader extends ImageLoader {
    /**
     * 单例对象
     */
    private static CustomImageLoader sCustomImageLoader = null;
    /**
     * 上一次设置的config
     */
    private ImageLoaderConfiguration mLastConfig;

    private CustomImageLoader() {
    }

    /**
     * 单例方法
     *
     * @return 单例对象
     */
    public static CustomImageLoader getInstance() {
        if (sCustomImageLoader == null) {
            synchronized (CustomImageLoader.class) {
                if (sCustomImageLoader == null) {
                    sCustomImageLoader = new CustomImageLoader();
                }
            }
        }
        return sCustomImageLoader;
    }

    @Override
    public synchronized void init(ImageLoaderConfiguration configuration) {
        if (isInited()) {
            if (configuration == mLastConfig) {
                return;
            }
            destroy();
        }
        super.init(configuration);
        mLastConfig = configuration;
    }
}
