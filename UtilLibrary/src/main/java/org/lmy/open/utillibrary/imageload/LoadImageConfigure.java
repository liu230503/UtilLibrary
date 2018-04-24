package org.lmy.open.utillibrary.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.ArrayMap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.lmy.open.utillibrary.LoggerUtil;
import org.lmy.open.utillibrary.MyResource;
import org.lmy.open.utillibrary.UtilApplication;
import org.lmy.open.utillibrary.path.PathUtil;

import java.io.File;
import java.util.Map;

/**********************************************************************
 *
 *
 * @类名 LoadImageConfigure
 * @包名 org.lmy.open.utillibrary.ImageLoad
 * @author lmy
 * @创建日期 2018/3/5
 ***********************************************************************/
public class LoadImageConfigure {
    /**
     * 内存判断条件
     */
    private static final int CONDITION = 256;

    /**
     * 选项集合
     */
    private Map<Integer, DisplayImageOptions> mOptionsMap;

    public LoadImageConfigure(){
        mOptionsMap = new ArrayMap<>();
    }
    /**
     * 获取缓存路径
     *
     * @param image 图片类型
     * @return 路径
     */
    public String getCachePath(EnumImage image) {
        switch (image) {
            case BANNER:
                return PathUtil.getInstance().getBannerCachePath();
            default:
                return PathUtil.getInstance().getImageCachePath();
        }
    }

    /**
     * 获取缩略图缓存路径
     *
     * @param image 图片类型
     * @return 路径
     */
    public String getThumbCachePath(EnumImage image) {
        switch (image) {
            default:
                return "";
        }
    }


    /**
     * 计算图片缓存最大空间
     *
     * @return 缓存空间
     */
    public int getMaxMemory() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxMemoryM = (maxMemory) / 1024 / 1024;
        if (maxMemoryM > CONDITION) {
            maxMemory = (int) (maxMemory * 0.65);
        } else {
            maxMemory = (int) (maxMemory * 0.3);
        }
        return maxMemory;
    }

    /**
     * 生成选项
     *
     * @param type 图片类型
     * @return 选项
     */
    public DisplayImageOptions getOrCreateOptions(int type) {
        DisplayImageOptions options = mOptionsMap.get(type);
        if (options != null) {
            return options;
        }
        switch (type) {
            case 0:
                options = new DisplayImageOptions.Builder()
                        .showImageForEmptyUri(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "picture_error"))
                        .showImageOnFail(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "picture_error"))
                        .showImageOnLoading(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "picture_error"))
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .build();

                break;
            case 2:
                options = new DisplayImageOptions.Builder()
                        .showImageForEmptyUri(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "ic_launcher_round"))
                        .showImageOnFail(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "ic_launcher_round"))
                        .showImageOnLoading(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "ic_launcher_round"))
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .build();
                break;
            case 3:
                options = new DisplayImageOptions.Builder()
                        .showImageForEmptyUri(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "wetalk_default"))
                        .showImageOnFail(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "wetalk_default"))
                        .showImageOnLoading(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "wetalk_default"))
                        .cacheOnDisk(true)
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .build();
                break;
            default:
                options = new DisplayImageOptions.Builder()
                        .build();
                break;
        }
        mOptionsMap.put(type, options);
        return options;
    }

    public ImageLoaderConfiguration buildConfig(Context context) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .threadPoolSize(1)
                .threadPriority(Thread.MIN_PRIORITY)
                .memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .diskCache(new UnlimitedDiskCache(new File(getCachePath(EnumImage.DEFAULT))))
                .diskCacheSize(getMaxMemory())
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs();
        ImageLoaderConfiguration config = builder.build();
        return config;
    }
}
