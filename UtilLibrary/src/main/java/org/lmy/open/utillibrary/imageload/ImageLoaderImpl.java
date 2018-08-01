package org.lmy.open.utillibrary.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import org.lmy.open.utillibrary.imageload.base.BaseLoadImageConfigure;

/**********************************************************************
 *
 *
 * @类名 ImageLoaderImpl
 * @包名 org.lmy.open.utillibrary.ImageLoad
 * @author lmy
 * @创建日期 2018/3/5
 ***********************************************************************/
final class ImageLoaderImpl implements ILoadImage, ImageLoadingListener, ImageLoadingProgressListener {
    /**
     * imageloader
     */
    private CustomImageLoader mImageLoader;
    /**
     * 监听器
     */
    private Listener mListener;
    /**
     * 配置
     */
    private BaseLoadImageConfigure mConfigure;

    ImageLoaderImpl() {
        mImageLoader = CustomImageLoader.getInstance();
    }

    @Override
    public void loadImage(ImageView imageView, String url, int type, Listener listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        if (!mImageLoader.isInited()) {
            init(imageView.getContext());
        }
        mListener = listener;
        mConfigure.addLoadUrl(url, type);
        mImageLoader.displayImage(url, imageView, mConfigure.getOrCreateOptions(type), this);
    }

    @Override
    public void setPauseLoadOnScroll(boolean pauseOnScroll, boolean pauseOnFling) {
        new PauseOnScrollListener(mImageLoader, pauseOnScroll, pauseOnFling);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    @Override
    public void init(Context context) {
        init(context, null);
    }

    @Override
    public void init(Context context, BaseLoadImageConfigure imageConfigure) {
        if (imageConfigure == null) {
            imageConfigure = new LoadImageConfigure();
        }
        mConfigure = imageConfigure;
        mImageLoader.init(mConfigure.buildConfig(context));
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

    @Override
    public void onProgressUpdate(String imageUri, View view, int current, int total) {
        if (mListener != null) {
            mListener.onProgressUpdate(imageUri, view, current, total);
        }
    }
}
