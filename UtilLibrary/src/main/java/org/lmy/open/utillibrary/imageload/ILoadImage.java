package org.lmy.open.utillibrary.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import org.lmy.open.utillibrary.imageload.base.BaseLoadImageConfigure;

/**********************************************************************
 *
 *
 * @类名 ILoadImage
 * @包名 org.lmy.open.utillibrary.ImageLoad
 * @author lmy
 * @创建日期 2018/3/5
 ***********************************************************************/
interface ILoadImage {
    /**
     * 加载图片
     *
     * @param imageView 组件
     * @param url       图片地址
     * @param type      图片类型
     * @param listener  监听器
     */
    void loadImage(ImageView imageView, String url, int type, Listener listener);

    /**
     * 设置在滑动时是否暂停加载图片
     *
     * @param pauseOnScroll 滑动时是否暂停加载
     * @param pauseOnFling  猛的滑动时是否暂停加载
     */
    void setPauseLoadOnScroll(boolean pauseOnScroll, boolean pauseOnFling);

    /**
     * 初始化
     *
     * @param context 上下文
     */
    void init(Context context);

    /**
     * 初始化
     *
     * @param context        上下文
     * @param imageConfigure 配置
     */
    void init(Context context, BaseLoadImageConfigure imageConfigure);

    interface Listener {
        /**
         * 开始加载
         *
         * @param imageUri 图片地址
         * @param view     view
         */
        void onLoadingStarted(String imageUri, View view);

        /**
         * 加载失败
         *
         * @param imageUri 图片地址
         * @param view     view
         */
        void onLoadingFailed(String imageUri, View view);

        /**
         * 加载成功
         *
         * @param imageUri    图片地址
         * @param view        view
         * @param loadedImage bitmap
         */
        void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

        /**
         * 加载完成
         *
         * @param imageUri 图片地址
         * @param view     view
         */
        void onLoadingCancelled(String imageUri, View view);

        /**
         * 进度条刷新
         *
         * @param imageUri 访问地址
         * @param view     装载图片的view
         * @param current  当前位置
         * @param total    总量
         */
        void onProgressUpdate(String imageUri, View view, int current, int total);
    }
}
