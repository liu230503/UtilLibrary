package org.lmy.open.utillibrary.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**********************************************************************
 *
 *
 * @类名 ImageLoaderHelper
 * @包名 org.lmy.open.utillibrary.ImageLoad
 * @author lmy
 * @创建日期 2018/3/5
 ***********************************************************************/
final class ImageLoaderHelper implements ILoadImage, ImageLoadingListener {
    /**
     * imageloader
     */
    private ImageLoader mImageLoader;
    /**
     * 监听器
     */
    private Listener mListener;
    /**
     * 配置
     */
    private LoadImageConfigure mConfigure;

    public ImageLoaderHelper() {
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        mConfigure = new LoadImageConfigure();
        for (EnumImage image : EnumImage.values()) {
            mConfigure.getCachePath(image);
            mConfigure.getThumbCachePath(image);
        }
        mImageLoader.init(mConfigure.buildConfig(context));
    }

    @Override
    public void loadImage(ImageView imageView, String url, int type, Listener listener) {
        mListener = listener;
        mImageLoader.displayImage(url, imageView, mConfigure.getOrCreateOptions(type), this);
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        if (mListener != null) {
            mListener.onLoadingStarted(imageUri, view);
        }
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        if (mListener != null) {
            mListener.onLoadingFailed(imageUri, view);
        }
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        if (mListener != null) {
            mListener.onLoadingComplete(imageUri, view, loadedImage);
        }
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        if (mListener != null) {
            mListener.onLoadingCancelled(imageUri, view);
        }
    }
}
