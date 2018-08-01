package org.lmy.open.utillibrary.imageload.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.lmy.open.utillibrary.LogHelper;
import org.lmy.open.utillibrary.imageload.cache.DiskCache;
import org.lmy.open.utillibrary.imageload.cache.NameGenerator;
import org.lmy.open.utillibrary.path.PathUtil;

import java.io.File;
import java.util.Map;

/**
 * 图片加载配置类
 *
 * @author lmy
 * @ClassName: BaseLoadImageConfigure
 * @Package: org.lmy.open.utillibrary.imageload
 * @Date: 2018/07/31
 */
public abstract class BaseLoadImageConfigure {
    /**
     * 当前设备的内存大小判断条件
     */
    private static final int MAX_MEMORY = 256;
    /**
     * CPU核心数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    /**
     * 选项集合
     */
    protected Map<Integer, DisplayImageOptions> mOptionsMap;
    /**
     * 路径集合
     */
    private Map<Integer, String> mPathMap;
    /**
     * 加载图片集合
     */
    private Map<String, Integer> mLoadUrlMap;
    /**
     * 文件名生成器
     */
    private FileNameGenerator mFileNameGenerator;

    public BaseLoadImageConfigure() {
        mOptionsMap = new ArrayMap<>();
        mPathMap = getPathMap();
        LogHelper.d(getPathMap() + "");
        mLoadUrlMap = new ArrayMap<>();
        mFileNameGenerator = new NameGenerator();
    }

    /**
     * 由子类实现 返回一个缓存路径集合
     *
     * @return 缓存路径集合
     */
    protected abstract Map<Integer, String> getPathMap();

    /**
     * 生成选项
     *
     * @param type 图片类型
     * @return 选项
     */
    public abstract DisplayImageOptions getOrCreateOptions(int type);

    /**
     * 构建配置
     *
     * @param context 上下文
     * @return 配置
     */
    public ImageLoaderConfiguration buildConfig(Context context) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .threadPoolSize(CORE_POOL_SIZE)
                .threadPriority(Thread.MIN_PRIORITY)
                .memoryCacheExtraOptions(getMaxImageWidthForMemoryCache(), getMaxImageHeightForMemoryCache())
                .diskCacheExtraOptions(getMaxImageWidthForDiskCache(), getMaxImageHeightForDiskCache(), null)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(getMaxSizeForMemoryCache()))
                .memoryCacheSize(getMaxSizeForMemoryCache())
                .memoryCacheSizePercentage(getAvailableMemoryPercent())
                .diskCache(new DiskCache(mFileNameGenerator, this))
                .diskCacheSize(getMaxMemory())
                .diskCacheFileCount(getMaxFileCountForDiskCache())
                .diskCacheFileNameGenerator(mFileNameGenerator)
                .imageDownloader(new BaseImageDownloader(context))
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs();
        ImageLoaderConfiguration config = builder.build();
        return config;
    }

    /**
     * 内存缓存最大宽度
     *
     * @return 宽度
     */
    protected abstract int getMaxImageWidthForMemoryCache();

    /**
     * 内存缓存最大高度
     *
     * @return 高度
     */
    protected abstract int getMaxImageHeightForMemoryCache();

    /**
     * 磁盘缓存最大宽度
     *
     * @return 宽度
     */
    protected abstract int getMaxImageWidthForDiskCache();

    /**
     * 磁盘缓存最大高度
     *
     * @return 高度
     */
    protected abstract int getMaxImageHeightForDiskCache();

    /**
     * 内存缓存最大值
     *
     * @return 缓存空间
     */
    protected abstract int getMaxSizeForMemoryCache();

    /**
     * 设置可用内存百分比
     *
     * @return 可用内存百分比 0-100
     */
    protected abstract int getAvailableMemoryPercent();

    /**
     * 计算图片缓存最大空间
     *
     * @return 缓存空间
     */
    protected int getMaxMemory() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxMemoryM = (maxMemory) / 1024 / 1024;
        if (maxMemoryM > MAX_MEMORY) {
            maxMemory = (int) (maxMemory * 0.65);
        } else {
            maxMemory = (int) (maxMemory * 0.3);
        }
        return maxMemory;
    }

    /**
     * 最大磁盘缓存图片数量
     *
     * @return 图片数量
     */
    protected abstract int getMaxFileCountForDiskCache();

    /**
     * 获取缓存路径
     *
     * @param url 访问地址
     * @return 路径
     */
    public String getCachePath(String url) {
        File cacheFile = new File(PathUtil.getInstance().getImageCachePath() + mPathMap.get(mLoadUrlMap.get(url)));
        if (TextUtils.isEmpty(cacheFile.getAbsolutePath())) {
            return "";
        }
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        return cacheFile.getAbsolutePath();
    }


    /**
     * 添加加载图片地址
     *
     * @param url  地址
     * @param type 类型
     */
    public void addLoadUrl(String url, int type) {
        mLoadUrlMap.remove(url);
        mLoadUrlMap.put(url, type);
    }

}
